package admin.actionform.account;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import actionform.AccountExtendActionForm;

/**
 * 管理画面 アカウント一覧画面で使用するデータを格納するためのアクションフォーム
 */
public class AdminListAccountActionForm extends AccountExtendActionForm {

    private boolean sortFlag; // ソートフラグ
    private List<LabelValueBean> sortKindForAccountList; // アカウントソート種別リスト
    private String sortKindForAccount; // アカウントソート種別
    private int totalForm; // 全アクションフォームの件数
    private int totalPage; // 全ページ数
    private int currentPage; // 現在のページ
    private List<AccountExtendActionForm> accountExtendFormList; // アカウントのアクションフォームリスト
    private List<AccountExtendActionForm> displayedAccountList; // 表示するアカウントのアクションフォームのリスト

    public AdminListAccountActionForm() {}

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
     * sortKindForAccountListを取得する。
     */
    public List<LabelValueBean> getSortKindForAccountList() {
        return sortKindForAccountList;
    }

    /**
     * sortKindForAccountListを格納する。
     */
    public void setSortKindForAccountList(List<LabelValueBean> sortKindForAccountList) {
        this.sortKindForAccountList = sortKindForAccountList;
    }

    /**
     * sortKindForAccountを取得する。
     */
    public String getSortKindForAccount() {
        return sortKindForAccount;
    }

    /**
     * sortKindForAccountを格納する。
     */
    public void setSortKindForAccount(String sortKindForAccount) {
        this.sortKindForAccount = sortKindForAccount;
    }

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
     * accountExtendFormListを取得する。
     */
    public List<AccountExtendActionForm> getAccountExtendFormList() {
        return accountExtendFormList;
    }

    /**
     * accountExtendFormListを格納する。
     */
    public void setAccountExtendFormList(List<AccountExtendActionForm> accountExtendFormList) {
        this.accountExtendFormList = accountExtendFormList;
    }

    /**
     * displayedAccountListを取得する。
     */
    public List<AccountExtendActionForm> getDisplayedAccountList() {
        return displayedAccountList;
    }

    /**
     * displayedAccountListを格納する。
     */
    public void setDisplayedAccountList(List<AccountExtendActionForm> displayedAccountList) {
        this.displayedAccountList = displayedAccountList;
    }

}
