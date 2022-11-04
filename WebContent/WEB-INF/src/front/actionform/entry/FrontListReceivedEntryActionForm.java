package front.actionform.entry;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import actionform.EntryExtendActionForm;

/**
 * 申込受付一覧画面で使用するデータを格納するためのアクションフォーム
 */
public class FrontListReceivedEntryActionForm extends EntryExtendActionForm {

    private boolean sortFlag; // ソートフラグ
    private List<LabelValueBean> sortKindForEntryList; // 申込ソート種別リスト
    private String sortKindForEntry; // 申込ソート種別
    private int totalForm; // 全アクションフォームの件数
    private int totalPage; // 全ページ数
    private int currentPage; // 現在のページ
    private List<EntryExtendActionForm> entryExtendFormList; // 申込のアクションフォームのリスト
    private List<EntryExtendActionForm> displayedEntryList; // 表示する申込のアクションフォームのリスト

    public FrontListReceivedEntryActionForm() {}

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
     * sortKindForEntryListを取得する。
     */
    public List<LabelValueBean> getSortKindForEntryList() {
        return sortKindForEntryList;
    }

    /**
     * sortKindForEntryListを格納する。
     */
    public void setSortKindForEntryList(List<LabelValueBean> sortKindForEntryList) {
        this.sortKindForEntryList = sortKindForEntryList;
    }

    /**
     * sortKindForEntryを取得する。
     */
    public String getSortKindForEntry() {
        return sortKindForEntry;
    }

    /**
     * sortKindForEntryを格納する。
     */
    public void setSortKindForEntry(String sortKindForEntry) {
        this.sortKindForEntry = sortKindForEntry;
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
     * entryExtendFormListを取得する。
     */
    public List<EntryExtendActionForm> getEntryExtendFormList() {
        return entryExtendFormList;
    }

    /**
     * entryExtendFormListを格納する。
     */
    public void setEntryExtendFormList(List<EntryExtendActionForm> entryExtendFormList) {
        this.entryExtendFormList = entryExtendFormList;
    }

    /**
     * displayedEntryListを取得する。
     */
    public List<EntryExtendActionForm> getDisplayedEntryList() {
        return displayedEntryList;
    }

    /**
     * displayedEntryListを格納する。
     */
    public void setDisplayedEntryList(List<EntryExtendActionForm> displayedEntryList) {
        this.displayedEntryList = displayedEntryList;
    }

}
