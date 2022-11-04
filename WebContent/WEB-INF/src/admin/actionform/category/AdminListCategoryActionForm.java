package admin.actionform.category;

import java.util.List;

import actionform.CategoryExtendActionForm;

/**
 * 管理画面 カテゴリー一覧画面で使用するデータを格納するためのアクションフォーム
 */
public class AdminListCategoryActionForm extends CategoryExtendActionForm {

    private int totalForm; // 全アクションフォームの件数
    private int totalPage; // 全ページ数
    private int currentPage; // 現在のページ
    private List<CategoryExtendActionForm> categoryExtendFormList; // カテゴリーのアクションフォームのリスト
    private List<CategoryExtendActionForm> displayedCategoryList; // 表示するカテゴリーのアクションフォームのリスト

    public AdminListCategoryActionForm() {}

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
     * categoryExtendFormListを取得する。
     */
    public List<CategoryExtendActionForm> getCategoryExtendFormList() {
        return categoryExtendFormList;
    }

    /**
     * categoryExtendFormListを格納する。
     */
    public void setCategoryExtendFormList(List<CategoryExtendActionForm> categoryExtendFormList) {
        this.categoryExtendFormList = categoryExtendFormList;
    }

    /**
     * displayedCategoryListを取得する。
     */
    public List<CategoryExtendActionForm> getDisplayedCategoryList() {
        return displayedCategoryList;
    }

    /**
     * displayedCategoryListを格納する。
     */
    public void setDisplayedCategoryList(List<CategoryExtendActionForm> displayedCategoryList) {
        this.displayedCategoryList = displayedCategoryList;
    }

}
