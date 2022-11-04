package admin.action.contact;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import actionform.ContactExtendActionForm;
import admin.actionform.contact.AdminListContactActionForm;
import admin.actionform.contact.AdminSearchContactActionForm;
import dao.contact.SelectContactJoinDAO;
import dao.contact.sql.ContactSelectJoinWhereActionForm;
import model.contact.ContactFormPartsModel;
import model.contact.ContactListModel;
import model.login.LoginAdminStatusModel;
import util.ListUtil;
import util.LogUtil;
import util.RedirectAdminUtil;

public class ListContactAction extends Action {
    int DISPLAYED_RESULT = 20; // 表示件数/ページ

    /**
     * 管理画面 お問合せ一覧画面にフォワードするためのメソッド
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
        AdminListContactActionForm inForm = (AdminListContactActionForm) form;

        try {
            if(!new LoginAdminStatusModel().isAdminLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectAdminUtil().getRedirectAdminLoginAction("/listContact.do");
            }

            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターから現在のページ(文字列型)を取得する。
            String strCurrentPage = request.getParameter("currentPage");
            // パラメータに現在のページが追加されているかを判定した結果を格納する変数を生成する。
            boolean isPageParamExist = true;
            if(StringUtils.isEmpty(strCurrentPage)) {
                // 取得できない場合は現在のページに"1"を格納する。
                strCurrentPage = "1";
                // 変数にfalseを格納する。
                isPageParamExist = false;
            }
            // 現在のページ(整数型)を取得する。
            int currentPage = Integer.parseInt(strCurrentPage);

            // お問合せ一覧画面へのアクセス時の状態を判定する。
            if(request.getAttribute("AdminSearchAccountActionForm") != null
                    || (!isPageParamExist && !inForm.isSortFlag())
                    || CollectionUtils.isEmpty(inForm.getContactExtendFormList())) {
                // リクエストにAdminSearchContactActionFormが追加されている場合
                // またはパラメーターに現在のページが追加されていないかつソートフラグがfalseの場合
                // またはアクションフォームに格納されたスクールのリストの中身がNULLまたは空の場合

                // DBから取得したアカウントのアクションフォームを格納するためのリストを生成する。
                List<ContactExtendActionForm> contactExtendFormList = new ArrayList<>();
                if(request.getAttribute("AdminSearchContactActionForm") != null) {
                    // リクエストにAdminSearchContactActionFormが追加されている場合
                    // (検索フォームを利用してお問合せ一覧画面にアクセスした場合)

                    // リクエストからAdminSearchContactActionFormを取得する。
                    AdminSearchContactActionForm adminSearchContactForm
                            = (AdminSearchContactActionForm) request.getAttribute("AdminSearchContactActionForm");
                    // 検索条件に一致したお問合せのアクションフォームを格納したリストを取得する。
                    contactExtendFormList = adminSearchContactForm.getContactExtendFormList();
                } else {
                    // 上記以外の場合(お問合せ一覧画面に初めてアクセスした場合)

                    // 全てのお問合せのアクションフォーム格納したリストを取得する。
                    contactExtendFormList =
                            new SelectContactJoinDAO().selectMatchedContact(new ContactSelectJoinWhereActionForm());
                }
                // お問合せ日時の降順で並び替える。
                contactExtendFormList = new ContactListModel().sortContactExtendFormList(
                        contactExtendFormList, "byDescendingContactedAt");
                // アクションフォームに取得したリストを格納する。
                inForm.setContactExtendFormList(new ContactListModel().setListContactData(contactExtendFormList));

                // アクションフォームに取得したお問合せのアクションフォームの件数を格納する。
                inForm.setTotalForm(contactExtendFormList.size());
                // アクションフォームに全ページ数を格納する。
                inForm.setTotalPage(new ListUtil().calcTotalPage(contactExtendFormList.size(), DISPLAYED_RESULT));

                // お問合せステータスリストを取得してアクションフォームに格納する。
                inForm.setContactStatusList(new ContactFormPartsModel().getContactStatusListWithoutEmptyValue());
                // お問合せソート種別リストを取得してアクションフォームに格納する。
                inForm.setSortKindForContactList(new ContactFormPartsModel().getSortKindForContactListWithEmptyValue());
                // falseの場合はお問合せソート種別にNULLを格納する。
                inForm.setSortKindForContact(null);
            }

            // ソートフラグを判定する。
            if(inForm.isSortFlag()) {
                // trueの場合

                // リストをお問合せソート種別をもとにソートしてアクションフォームに格納する。
                inForm.setContactExtendFormList(new ContactListModel().sortContactExtendFormList(
                        inForm.getContactExtendFormList(), inForm.getSortKindForContact()));
                // ソートフラグにfalseを格納する。
                inForm.setSortFlag(false);
            }

            // アクションフォームに現在のページを格納する。
            inForm.setCurrentPage(currentPage);
            // アクションフォームに現在のページに表示するお問合せのリストを格納する。
            inForm.setDisplayedContactList(new ContactListModel().makeDisplayedContactList(
                    inForm.getContactExtendFormList(), currentPage, DISPLAYED_RESULT));

            // お問合せ一覧画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("AdminListContactActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
