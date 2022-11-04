package front.action.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import exception.NoDataExistException;
import front.actionform.account.FrontChangePasswordActionForm;
import model.account.AccountPasswordModel;
import model.login.LoginStatusModel;
import util.LogUtil;

public class ChangePasswordCompleteAction extends Action {

    /**
     * パスワード変更 完了画面にフォワードするためのメソッド
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
            // トークンが一致しない場合は入力画面にリダイレクトする。
            return mapping.findForward("invalid");
        }
        // セッションを取得する。
        HttpSession session = request.getSession();
        // アクションフォームを取得する。
        FrontChangePasswordActionForm inForm = (FrontChangePasswordActionForm) form;

        try {
            try {
                // パスワードを更新する
                new AccountPasswordModel().changeAccountPassword(
                        new LoginStatusModel().getAccountId(session), inForm.getPassword(), inForm.getNewPassword());
            } catch(NoDataExistException e) {
                // データ不存在例外を受け取った場合

                // アクションメッセージのインスタンスを生成する。
                ActionMessages errors = new ActionMessages();
                // インスタンスにエラーメッセージを格納する。
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("パスワードが誤っています。", false));
                // リクエストにエラーメッセージを格納したインスタンスを追加する。
                saveErrors(request, errors);

                // セッションからアクションフォームを削除する。
                session.removeAttribute("FrontChangePasswordActionForm");
                // 入力画面にフォワードする。
                return mapping.findForward("redo");
            }

            // セッションからアクションフォームを削除する。
            session.removeAttribute("FrontChangePasswordActionForm");
            // 完了画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontChangePasswordActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
