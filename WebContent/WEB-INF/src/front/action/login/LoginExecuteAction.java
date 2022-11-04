package front.action.login;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import actionform.AccountActionForm;
import dao.account.SelectAccountDAO;
import dao.account.sql.AccountSelectWhereActionForm;
import exception.NoDataExistException;
import front.actionform.login.FrontLoginExecuteActionForm;
import model.login.LoginStatusModel;
import util.LogUtil;
import util.PasswordUtil;
import util.RedirectUtil;

public class LoginExecuteAction extends Action {

    /**
     * ログイン処理を実行して実行結果に合わせた画面にフォワードするためのメソッド
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request リクエスト
     * @param response レスポンス
     * @return 遷移先画面の指定
     */
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {
        if(!isTokenValid(request, true)) {
            // トークンが一致しない場合はログイン画面にリダイレクトする。
            return mapping.findForward("invalid");
        }
        // セッションを取得する。
        HttpSession session = request.getSession();
        // アクションフォームを取得する。
        FrontLoginExecuteActionForm inForm = (FrontLoginExecuteActionForm) form;

        try {
            String mailAddress = inForm.getMailAddress(); // メールアドレス
            String password = inForm.getPassword(); // パスワード
            String redirectUrl = inForm.getRedirectUrl(); // リダイレクトURL

            // 安全なパスワードを取得する。
            String safetyPassword = new PasswordUtil().getSafetyPassword(password, mailAddress);
            // DAOメソッドに引き渡すアクションフォームを生成してメールアドレス、安全なパスワード、アカウントステータス("1"(有効))を格納する。
            AccountSelectWhereActionForm accountSelectWhereForm = new AccountSelectWhereActionForm();
            accountSelectWhereForm.setMailAddress(mailAddress);
            accountSelectWhereForm.setPassword(safetyPassword);
            // アカウントテーブルからメールアドレス、安全なパスワード、アカウントステータスに紐づくレコードを取得する。
            List<AccountActionForm> accountFormList
                    = new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm);
            if(accountFormList.isEmpty()) {
                // 取得できなかった場合はNULLを戻す。

                // アクションメッセージのインスタンスを生成する。
                ActionMessages errors = new ActionMessages();
                // インスタンスにエラーメッセージを格納する。
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                        "メールアドレスまたはパスワードが誤っています。", false));
                // リクエストにエラーメッセージを格納したインスタンスを追加する。
                saveErrors(request, errors);

                // トークンを作成する。
                saveToken(request);
                // ログイン画面にフォワードする。
                return mapping.findForward("redo");
            }

            try {
                // アカウントIDをもとにログイン済状態に変更する。
                session = new LoginStatusModel().stateToLogined(accountFormList.get(0).getAccountId(), session);
            } catch(NoDataExistException e) {
                // データ不存在例を受け取った場合

                // アクションメッセージのインスタンスを生成する。
                ActionMessages errors = new ActionMessages();
                // インスタンスにエラーメッセージを格納する。
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("アカウントが有効化されていません。", false));
                // リクエストにエラーメッセージを格納したインスタンスを追加する。
                saveErrors(request, errors);

                // トークンを作成する。
                saveToken(request);
                // ログイン画面にフォワードする。
                return mapping.findForward("redo");
            }

            // セッションからアクションフォームを削除する。
            session.removeAttribute("FrontLoginExecuteActionForm");

            if(StringUtils.isEmpty(redirectUrl)) {
                // リダイレクトURLが指定されていない場合はトップ画面にリダイレクトする。
                return new RedirectUtil().getRedirectAction("/");
            } else {
                // 指定されている場合はリダイレクトURLにリダイレクトする。
                return new RedirectUtil().getRedirectAction(redirectUrl);
            }
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontLoginExecuteActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
