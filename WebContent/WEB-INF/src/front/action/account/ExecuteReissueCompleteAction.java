package front.action.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import front.actionform.account.FrontExecuteReissueActionForm;
import model.account.AccountPasswordModel;
import model.login.LoginStatusModel;
import util.LogUtil;

public class ExecuteReissueCompleteAction extends Action {

    /**
     * パスワード再発行 新しいパスワード 完了画面にフォワードするためのメソッド
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
        FrontExecuteReissueActionForm inForm = (FrontExecuteReissueActionForm) form;

        try {
            // パスワードを更新する。
            new AccountPasswordModel().executeReissue(
                    inForm.getAccountId(), inForm.getMailAddress(), inForm.getPassword());

            // アカウントIDをもとにログイン済状態に変更する。
            session = new LoginStatusModel().stateToLogined(inForm.getAccountId(), session);

            // セッションからアクションフォームを削除する。
            session.removeAttribute("FrontExecuteReissueActionForm");
            // 完了画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォーム、アカウントID、メールアドレスを削除する。
        session.removeAttribute("FrontExecuteReissueActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
