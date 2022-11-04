package front.actionform;

import java.util.List;

import org.apache.struts.validator.ValidatorForm;

import actionform.CategoryActionForm;
import actionform.SchoolExtendActionForm;

/**
 * トップ画面で使用するデータを格納するためのアクションフォーム
 */
public class FrontIndexActionForm extends ValidatorForm {

    private List<CategoryActionForm> categoryFormList; // カテゴリーのアクションフォームのリスト
    private List<SchoolExtendActionForm> schoolExtendFormList; // スクールのアクションフォームのリスト
    private List<SchoolExtendActionForm> displayedSchoolList; // 表示するスクールのアクションフォームのリスト

    public FrontIndexActionForm() {}

    /**
     * categoryFormListを取得する。
     */
    public List<CategoryActionForm> getCategoryFormList() {
        return categoryFormList;
    }

    /**
     * categoryFormListを格納する。
     */
    public void setCategoryFormList(List<CategoryActionForm> categoryFormList) {
        this.categoryFormList = categoryFormList;
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
