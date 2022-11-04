package admin.action.entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import admin.actionform.entry.AdminViewReceivedEntryActionForm;
import dao.entry.UpdateEntryDAO;
import dao.entry.sql.EntryUpdateDataActionForm;
import util.LogUtil;

public class UpdateEntryStatusAction extends Action {

    /**
     * 申込ステータス更新時に管理画面 申込受付詳細画面にフォワードするためのメソッド
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request リクエスト
     * @param response レスポンス
     * @return 遷移先画面の指定
     * */
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        if(!isTokenValid(request, true)) {
            // トークンが一致しない場合は入力画面にリダイレクトする。
            return mapping.findForward("invalid");
        }
        // セッションを取得する。
        HttpSession session = request.getSession();
        // アクションフォームを取得する。
        AdminViewReceivedEntryActionForm inForm = (AdminViewReceivedEntryActionForm) form;

        try {
            // DAOメソッドに引き渡すアクションフォームを生成して更新するデータを格納する。
            EntryUpdateDataActionForm entryUpdateForm = new EntryUpdateDataActionForm();
            entryUpdateForm.setEntryId(inForm.getEntryId()); // 申込ID
            entryUpdateForm.setEntryStatusId(inForm.getEntryStatusId()); // 申込ステータスID
            // 申込のレコードを更新する。
            new UpdateEntryDAO().updateEntry(entryUpdateForm);

            // 申込受付詳細画面にフォワードする。
            return new ActionForward("/viewReceivedEntry.do?entryId=" + inForm.getEntryId());
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("AdminViewReceivedEntryActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
