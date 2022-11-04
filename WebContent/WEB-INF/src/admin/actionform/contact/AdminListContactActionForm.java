package admin.actionform.contact;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import actionform.ContactExtendActionForm;

/**
 * 管理画面 お問合せ一覧画面で使用するデータを格納するためのアクションフォーム
 */
public class AdminListContactActionForm extends ContactExtendActionForm {

    private boolean sortFlag; // ソートフラグ
    private List<LabelValueBean> sortKindForContactList; // お問合せソート種別リスト
    private String sortKindForContact; // お問せソート種別
    private int totalForm; // 全アクションフォームの件数
    private int totalPage; // 全ページ数
    private int currentPage; // 現在のページ
    private List<ContactExtendActionForm> contactExtendFormList; // お問合せのアクションフォームのリスト
    private List<ContactExtendActionForm> displayedContactList; // 表示するお問合せのアクションフォームのリスト

    /**
     * sortFlagを取得する。
     */
    public boolean isSortFlag() {
        return sortFlag;
    }

    /**
     * sortFlagを格納する。
     */
    public void setSortFlag(boolean sortFlag) {
        this.sortFlag = sortFlag;
    }

    /**
     * sortKindForContactListを取得する。
     */
    public List<LabelValueBean> getSortKindForContactList() {
        return sortKindForContactList;
    }

    /**
     * sortKindForContactListを格納する。
     */
    public void setSortKindForContactList(List<LabelValueBean> sortKindForContactList) {
        this.sortKindForContactList = sortKindForContactList;
    }

    /**
     * sortKindForContactを取得する。
     */
    public String getSortKindForContact() {
        return sortKindForContact;
    }

    /**
     * sortKindForContactを格納する。
     */
    public void setSortKindForContact(String sortKindForContact) {
        this.sortKindForContact = sortKindForContact;
    }

    public AdminListContactActionForm() {}

    /**
     * totalFormを取得する。
     */
    public int getTotalForm() {
        return totalForm;
    }

    /**
     * totalFormを格納する。
     */
    public void setTotalForm(int totalForm) {
        this.totalForm = totalForm;
    }

    /**
     * totalPageを取得する。
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * totalPageを格納する。
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * currentPageを取得する。
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * currentPageを格納する。
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * contactExtendFormListを取得する。
     */
    public List<ContactExtendActionForm> getContactExtendFormList() {
        return contactExtendFormList;
    }

    /**
     * contactExtendFormListを格納する。
     */
    public void setContactExtendFormList(List<ContactExtendActionForm> contactExtendFormList) {
        this.contactExtendFormList = contactExtendFormList;
    }

    /**
     * displayedContactListを取得する。
     */
    public List<ContactExtendActionForm> getDisplayedContactList() {
        return displayedContactList;
    }

    /**
     * displayedContactListを格納する。
     */
    public void setDisplayedContactList(List<ContactExtendActionForm> displayedContactList) {
        this.displayedContactList = displayedContactList;
    }

}
