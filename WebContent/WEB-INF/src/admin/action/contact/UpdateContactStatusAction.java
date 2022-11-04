package admin.action.contact;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import admin.actionform.contact.AdminViewDetailedContactActionForm;
import dao.contact.UpdateContactDAO;
import dao.contact.sql.ContactUpdateDataActionForm;
import util.LogUtil;
import util.RedirectAdminUtil;

public class UpdateContactStatusAction extends Action {

    /**
     * お問合せステータス更新時に管理画面 お問合せ詳細画面にフォワードするためのメソッド
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
        // アクションフォームを取得する。
        AdminViewDetailedContactActionForm inForm = (AdminViewDetailedContactActionForm) form;

        try {
            // お問合せIDを取得する。
            int contactId = inForm.getContactId();

            // DAOメソッドに引き渡すアクションフォームを生成して更新するデータを格納する。
            ContactUpdateDataActionForm contactUpdateDataForm = new ContactUpdateDataActionForm();
            contactUpdateDataForm.setContactId(contactId); // お問合せID
            contactUpdateDataForm.setContactStatusId(inForm.getContactStatusId()); // お問合せステータスID
            // お問合せのレコードを更新する。
            new UpdateContactDAO().updateContact(contactUpdateDataForm);

            // お問合せ詳細画面にリダイレクトする。
            return new RedirectAdminUtil().getRedirectAdminAction(
                    "/viewDetailedContact.do", "contactId", String.valueOf(contactId));
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
