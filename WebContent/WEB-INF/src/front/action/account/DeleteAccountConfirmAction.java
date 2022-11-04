package front.action.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import front.actionform.account.FrontDeleteAccountActionForm;
import model.login.LoginStatusModel;
import util.LogUtil;
import util.RedirectUtil;

public class DeleteAccountConfirmAction extends Action {

    /**
     * 退会 確認画面にフォワードするためのメソッド
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
        // セッションを取得する。
        HttpSession session = request.getSession();
        // アクションフォームを生成する。
        FrontDeleteAccountActionForm outForm = new FrontDeleteAccountActionForm();

        try {
            if(!new LoginStatusModel().isLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectUtil().getRedirectLoginAction("/deleteAccountConfirm.do");
            }

            // セッションからアカウントIDを取得してアクションフォームに格納する。
            outForm.setAccountId(new LoginStatusModel().getAccountId(session));

            // トークンを生成する。
            saveToken(request);
            // セッションにアクションフォームを格納する。
            session.setAttribute("FrontDeleteAccountActionForm", outForm);
            // 確認画面にフォワードする。
            return mapping.findForward("success");
        } catch (Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
