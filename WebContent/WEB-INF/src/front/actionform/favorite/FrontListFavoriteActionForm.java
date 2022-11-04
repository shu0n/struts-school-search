package front.actionform.favorite;

import java.util.List;

import actionform.FavoriteExtendActionForm;

/**
 * お気に入りスクール一覧画面で使用するデータを格納するためのアクションフォーム
 */
public class FrontListFavoriteActionForm extends FavoriteExtendActionForm {

    private int totalForm; // 全アクションフォームの件数
    private int totalPage; // 全ページ数
    private int currentPage; // 現在のページ
    private List<FavoriteExtendActionForm> favoriteExtendFormList; // お気に入りのアクションフォームのリスト
    private List<FavoriteExtendActionForm> displayedFavoriteList; // 表示するお気に入りのアクションフォームのリスト

    public FrontListFavoriteActionForm() {}

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
     * favoriteExtendFormListを取得する。
     */
    public List<FavoriteExtendActionForm> getFavoriteExtendFormList() {
        return favoriteExtendFormList;
    }

    /**
     * favoriteExtendFormListを格納する。
     */
    public void setFavoriteExtendFormList(List<FavoriteExtendActionForm> favoriteExtendFormList) {
        this.favoriteExtendFormList = favoriteExtendFormList;
    }

    /**
     * displayedFavoriteListを取得する。
     */
    public List<FavoriteExtendActionForm> getDisplayedFavoriteList() {
        return displayedFavoriteList;
    }

    /**
     * displayedFavoriteListを格納する。
     */
    public void setDisplayedFavoriteList(List<FavoriteExtendActionForm> displayedFavoriteList) {
        this.displayedFavoriteList = displayedFavoriteList;
    }

}
