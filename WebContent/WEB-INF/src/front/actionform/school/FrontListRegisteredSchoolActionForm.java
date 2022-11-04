package front.actionform.school;

import java.util.List;

import actionform.SchoolExtendActionForm;

/**
 * 登録スクール一覧画面で使用するデータを格納するためのアクションフォーム
 */
public class FrontListRegisteredSchoolActionForm extends SchoolExtendActionForm {

    private int totalForm; // 全アクションフォームの件数
    private int totalPage; // 全ページ数
    private int currentPage; // 現在のページ
    private List<SchoolExtendActionForm> schoolExtendFormList; // スクールのアクションフォームのリスト
    private List<SchoolExtendActionForm> displayedSchoolList; // 表示するスクールのアクションフォームのリスト

    public FrontListRegisteredSchoolActionForm() {}

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
     * schoolExtendFormListを取得する。
     */
    public List<SchoolExtendActionForm> getSchoolExtendFormList() {
        return schoolExtendFormList;
    }

    /**
     * schoolExtendFormListを格納する。
     */
    public void setSchoolExtendFormList(List<SchoolExtendActionForm> schoolExtendFormList) {
        this.schoolExtendFormList = schoolExtendFormList;
    }

    /**
     * displayedSchoolListを取得する。
     */
    public List<SchoolExtendActionForm> getDisplayedSchoolList() {
        return displayedSchoolList;
    }

    /**
     * displayedSchoolListを格納する。
     */
    public void setDisplayedSchoolList(List<SchoolExtendActionForm> displayedSchoolList) {
        this.displayedSchoolList = displayedSchoolList;
    }

}
