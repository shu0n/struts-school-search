package front.action.school;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import dao.school.DeleteSchoolDAO;
import exception.ReferredByEntryException;
import front.actionform.school.FrontDeleteSchoolActionForm;
import util.LogUtil;

public class DeleteSchoolCompleteAction extends Action {

    /**
     * スクール削除 完了画面にフォワードするためのメソッド
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
            // トークンが一致しない場合は確認画面にリダイレクトする。
            return mapping.findForward("invalid");
        }
        // セッションを取得する。
        HttpSession session = request.getSession();
        // アクションフォームを取得する。
        FrontDeleteSchoolActionForm inForm = (FrontDeleteSchoolActionForm) form;

        try {
            try {
                // スクールのレコードを論理削除する。
                new DeleteSchoolDAO().deleteSchoolLogically(inForm.getSchoolId());
            } catch(ReferredByEntryException e) {
                // 申込参照例外を受け取った場合

                // アクションメッセージのインスタンスを生成する。
                ActionMessages errors = new ActionMessages();
                // インスタンスにエラーメッセージを格納する。
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                        e.getMessage() + "申込が1件以上存在する場合は削除できません。", false));
                // リクエストにエラーメッセージを格納したインスタンスを追加する。
                saveErrors(request, errors);

                // スクール削除 確認画面にフォワードする。
                return new ActionForward("/deleteSchoolConfirm.do?schoolId=" + inForm.getSchoolId());
            }

            // セッションからアクションフォームを削除する。
            session.removeAttribute("FrontDeleteSchoolActionForm");
            // 完了画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontDeleteSchoolActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
