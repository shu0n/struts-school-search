package admin.action.contact;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import admin.actionform.contact.AdminViewDetailedContactActionForm;
import dao.contact.repack.GetContactDataRepack;
import dao.contact.sql.ContactSelectJoinWhereActionForm;
import exception.NoDataExistException;
import model.contact.ContactFormPartsModel;
import model.login.LoginAdminStatusModel;
import util.LogUtil;
import util.RedirectAdminUtil;

public class ViewDetailedContactAction extends Action {

    /**
     * 管理画面 お問合せ詳細画面にフォワードするためのメソッド
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
        // アクションフォームを取得する。
        AdminViewDetailedContactActionForm inForm = (AdminViewDetailedContactActionForm) form;

        try {
            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターからお問合せID(文字列型)を取得する。
            String strContactId = request.getParameter("contactId");
            if(StringUtils.isEmpty(strContactId)) {
                // 取得できない場合

                // セッションからアカウションフォームを削除する。
                session.removeAttribute("AdminViewDetailedContactActionForm");
                // 該当お問合せなしの画面にフォワードする。
                return mapping.findForward("unexistence");
            }
            // お問合せID(整数型)を取得する。
            int contactId = Integer.parseInt(strContactId);

            if(!new LoginAdminStatusModel().isAdminLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectAdminUtil().getRedirectAdminLoginAction(
                        "/viewDetailedContact.do", "contactId", strContactId);
            }

            // DAOメソッドに引き渡すアクションフォームを生成してお問合せIDを格納する。
            ContactSelectJoinWhereActionForm contactSelectJoinWhereForm = new ContactSelectJoinWhereActionForm();
            contactSelectJoinWhereForm.setContactId(contactId); // お問合せID
            try {
                // お問合せの情報を取得する。
                inForm = (AdminViewDetailedContactActionForm) new GetContactDataRepack().getContactData(
                        contactSelectJoinWhereForm, inForm);
            } catch(NoDataExistException e) {
                // データ不存在例外を受け取った場合

                // セッションからアカウションフォームを削除する。
                session.removeAttribute("AdminViewDetailedContactActionForm");
                // 該当お問合せなし画面にフォワードする。
                return mapping.findForward("unexistence");
            }

            // お問合せステータスマップとお問合せステータスリストを取得する。
            inForm.setContactStatusMap(new ContactFormPartsModel().getContactStatusMap());
            inForm.setContactStatusList(new ContactFormPartsModel().getContactStatusListWithoutEmptyValue());

            // トークンを作成する。
            saveToken(request);
            // お問合せ詳細画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアカウションフォームを削除する。
        session.removeAttribute("AdminViewDetailedContactActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
