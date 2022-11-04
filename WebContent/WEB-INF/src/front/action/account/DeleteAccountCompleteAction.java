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

import dao.account.DeleteAccountDAO;
import dao.account.SelectAccountDAO;
import dao.account.sql.AccountSelectWhereActionForm;
import exception.ReferredBySchoolException;
import front.actionform.account.FrontDeleteAccountActionForm;
import model.account.AccountMailModel;
import model.entry.EntryDeleteModel;
import model.login.LoginStatusModel;
import util.LogUtil;

public class DeleteAccountCompleteAction extends Action {

    /**
     * 退会 完了画面にフォワードするためのメソッド
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
        FrontDeleteAccountActionForm inForm = (FrontDeleteAccountActionForm) form;

        try {
            // アカウントIDを取得する。
            int accountId = inForm.getAccountId();

            // DAOメソッドに引き渡すアクションフォームを生成してアカウントIDを格納する。
            AccountSelectWhereActionForm accountSelectWhereForm = new AccountSelectWhereActionForm();
            accountSelectWhereForm.setAccountId(accountId);
            // 論理削除前にアカウントIDに紐づくメールアドレスを取得する。
            String mailAddress
                    = new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm).get(0).getMailAddress();

            // アカウントIDに紐づく申込を論理削除する。
            new EntryDeleteModel().deleteEntryForDeleteAccount(inForm.getAccountId());

            try {
                // アカウントを論理削除する。
                new DeleteAccountDAO().deleteAccountLogically(inForm.getAccountId());
            } catch(ReferredBySchoolException e) {
                // スクール参照例外を受け取った場合

                // アクションメッセージのインスタンスを生成する。
                ActionMessages errors = new ActionMessages();
                // インスタンスにエラーメッセージを格納する。
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                        e.getMessage() + "スクールを1件以上登録している場合は退会できません。", false));
                // リクエストにエラーメッセージを格納したインスタンスを追加する。
                saveErrors(request, errors);

                // トークンを生成する。
                saveToken(request);
                // 確認画面にフォワードする。
                return mapping.findForward("redo");
            }

            // 退会完了通知メールを送信する。
            new AccountMailModel().sendDeleteAccountMail(mailAddress);

            // ログアウト状態にする。
            new LoginStatusModel().stateToLogout(session);

            // セッションからアクションフォームを削除する。
            session.removeAttribute("FrontDeleteAccountActionForm");
            // 完了画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外をログファイルに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontDeleteAccountActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
