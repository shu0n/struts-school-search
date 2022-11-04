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
import front.actionform.account.FrontRequestReissueActionForm;
import model.account.AccountMailModel;
import model.account.AccountPasswordModel;
import util.LogUtil;

public class RequestReissueCompleteAction extends Action {

    /**
     * パスワード再発行 申請 完了画面にフォワードするためのメソッド
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
        FrontRequestReissueActionForm inForm = (FrontRequestReissueActionForm) form;

        try {
            // 再発行トークンを格納する変数を生成する。
            String reissueToken = "";
            try {
                // 再発行トークンと再発行有効期限を登録する。
                reissueToken = new AccountPasswordModel().setReissueInfo(inForm.getMailAddress());
            } catch(NoDataExistException e) {
                // データ不存在例外を受け取った場合

                // アクションメッセージのインスタンスを生成する。
                ActionMessages errors = new ActionMessages();
                // インスタンスにエラーメッセージを格納する。
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("メールアドレスが誤っています。", false));
                // リクエストにエラーメッセージを格納したインスタンスを追加する。
                saveErrors(request, errors);

                // トークンを作成する。
                saveToken(request);
                // 入力画面にフォワードする。
                return mapping.findForward("redo");
            }

            // パスワード再発行申請受付完了通知メールを送信する。
            new AccountMailModel().sendReissueMail(inForm.getMailAddress(), reissueToken);

            // セッションからアクションフォームを削除する。
            session.removeAttribute("FrontRequestReissueActionForm");
            // 完了画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontRequestReissueActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
