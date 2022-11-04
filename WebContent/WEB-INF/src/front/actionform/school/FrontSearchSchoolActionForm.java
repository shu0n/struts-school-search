package front.actionform.school;

import java.util.List;

import actionform.SchoolExtendActionForm;

/**
 * スクール一覧画面の検索で使用するデータを格納するためのアクションフォーム
 */
public class FrontSearchSchoolActionForm extends SchoolExtendActionForm {

    private String likeSchoolName; // スクール名(類似)
    private String[] schoolCategoryIdArray; // スクールカテゴリーID配列
    private String[] schoolPrefectureIdArray; // スクール都道府県ID配列
    private String minSchoolFee; // スクール費用(下限)
    private String maxSchoolFee; // スクール費用(上限)
    private List<SchoolExtendActionForm> schoolExtendFormList; // スクールのリスト

    public FrontSearchSchoolActionForm() {}

    /**
     * likeSchoolNameを取得する。
     */
    public String getLikeSchoolName() {
        return likeSchoolName;
    }

    /**
     * likeSchoolNameを格納する。
     */
    public void setLikeSchoolName(String likeSchoolName) {
        this.likeSchoolName = likeSchoolName;
    }

    /**
     * schoolCategoryIdArrayを取得する。
     */
    public String[] getSchoolCategoryIdArray() {
        return schoolCategoryIdArray;
    }

    /**
     * schoolCategoryIdArrayを格納する。
     */
    public void setSchoolCategoryIdArray(String[] schoolCategoryIdArray) {
        this.schoolCategoryIdArray = schoolCategoryIdArray;
    }

    /**
     * schoolPrefectureIdArrayを取得する。
     */
    public String[] getSchoolPrefectureIdArray() {
        return schoolPrefectureIdArray;
    }

    /**
     * schoolPrefectureIdArrayを格納する。
     */
    public void setSchoolPrefectureIdArray(String[] schoolPrefectureIdArray) {
        this.schoolPrefectureIdArray = schoolPrefectureIdArray;
    }

    /**
     * minSchoolFeeを取得する。
     */
    public String getMinSchoolFee() {
        return minSchoolFee;
    }

    /**
     * minSchoolFeeを格納する。
     */
    public void setMinSchoolFee(String minSchoolFee) {
        this.minSchoolFee = minSchoolFee;
    }

    /**
     * maxSchoolFeeを取得する。
     */
    public String getMaxSchoolFee() {
        return maxSchoolFee;
    }

    /**
     * maxSchoolFeeを格納する。
     */
    public void setMaxSchoolFee(String maxSchoolFee) {
        this.maxSchoolFee = maxSchoolFee;
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

}
