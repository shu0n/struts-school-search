package admin.action.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import admin.actionform.account.AdminDeleteAccountActionForm;
import dao.account.DeleteAccountDAO;
import exception.ReferredBySchoolException;
import model.entry.EntryDeleteModel;
import util.LogUtil;

public class DeleteAccountCompleteAction extends Action {

    /**
     * 管理画面 アカウント削除 完了画面にフォワードするためのメソッド
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
        AdminDeleteAccountActionForm inForm = (AdminDeleteAccountActionForm) form;

        try {
            // アカウントIDに紐づく申込を論理削除する。
            new EntryDeleteModel().deleteEntryForDeleteAccount(inForm.getAccountId());

            try {
                // アカウントを論理削除する。
                new DeleteAccountDAO().deleteAccountLogically(inForm.getAccountId());
            } catch (ReferredBySchoolException e) {
                // スクール参照例外を受け取った場合

                // アクションメッセージのインスタンスを生成する。
                ActionMessages errors = new ActionMessages();
                // インスタンスにエラーメッセージを格納する。
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                        e.getMessage()
                        + "削除するためには当該アカウントで登録しているスクールを削除してください。",
                        false));
                // リクエストにエラーメッセージを格納したインスタンスを追加する。
                saveErrors(request, errors);

                // アカウント削除 確認画面にフォワードする。
                return new ActionForward("/deleteAccountConfirm.do?accountId=" + inForm.getAccountId());
            }

            // セッションからアクションフォームを削除する。
            session.removeAttribute("AdminDeleteAccountActionForm");
            // 完了画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("AdminDeleteAccountActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}