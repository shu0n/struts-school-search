package admin.actionform.school;

import java.util.List;

import actionform.SchoolExtendActionForm;

/**
 * 管理画面 スクール一覧画面の検索で使用するデータを格納するためのアクションフォーム
 */
public class AdminSearchSchoolActionForm extends SchoolExtendActionForm {

    private String strSchoolId; // スクールID(文字列型)
    private String strRegistrantAccountId; // 登録者アカウントID(文字列型)
    private String strRegistrantAdminId; // 登録者管理者ID(文字列型)
    private String likeSchoolName; // スクール名(類似)
    private String[] schoolCategoryIdArray; // スクールカテゴリーID配列
    private String[] schoolPrefectureIdArray; // スクール都道府県ID配列
    private String minSchoolFee; // スクール費用(下限)
    private String maxSchoolFee; // スクール費用(上限)
    private String[] schoolReleaseProprietyArray; // スクール公開可否配列
    private String[] schoolEntryProprietyArray; // スクール申込可否配列
    private String fromSchoolRegisteredYear; // スクール登録日時(年、From)
    private String fromSchoolRegisteredMonth; // スクール登録日時(月、From)
    private String fromSchoolRegisteredDay; // スクール登録日時(日、From)
    private String toSchoolRegisteredYear; // スクール登録日時(年、To)
    private String toSchoolRegisteredMonth; // スクール登録日時(月、To)
    private String toSchoolRegisteredDay; // スクール登録日時(日、To)
    private String fromSchoolUpdatedYear; // スクール更新(年、From)
    private String fromSchoolUpdatedMonth; // スクール更新(月、From)
    private String fromSchoolUpdatedDay; // スクール更新(日、From)
    private String toSchoolUpdatedYear; // スクール更新(年、To)
    private String toSchoolUpdatedMonth; // スクール更新(月、To)
    private String toSchoolUpdatedDay; // スクール更新(日、To)
    private List<SchoolExtendActionForm> schoolExtendFormList; // スクールのリスト

    public AdminSearchSchoolActionForm() {}

    /**
     * strSchoolIdを取得する。
     */
    public String getStrSchoolId() {
        return strSchoolId;
    }

    /**
     * strSchoolIdを格納する。
     */
    public void setStrSchoolId(String strSchoolId) {
        this.strSchoolId = strSchoolId;
    }

    /**
     * strRegistrantAccountIdを取得する。
     */
    public String getStrRegistrantAccountId() {
        return strRegistrantAccountId;
    }

    /**
     * strRegistrantAccountIdを格納する。
     */
    public void setStrRegistrantAccountId(String strRegistrantAccountId) {
        this.strRegistrantAccountId = strRegistrantAccountId;
    }

    /**
     * strRegistrantAdminIdを取得する。
     */
    public String getStrRegistrantAdminId() {
        return strRegistrantAdminId;
    }

    /**
     * strRegistrantAdminIdを格納する。
     */
    public void setStrRegistrantAdminId(String strRegistrantAdminId) {
        this.strRegistrantAdminId = strRegistrantAdminId;
    }

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
     * schoolReleaseProprietyArrayを取得する。
     */
    public String[] getSchoolReleaseProprietyArray() {
        return schoolReleaseProprietyArray;
    }

    /**
     * schoolReleaseProprietyArrayを格納する。
     */
    public void setSchoolReleaseProprietyArray(String[] schoolReleaseProprietyArray) {
        this.schoolReleaseProprietyArray = schoolReleaseProprietyArray;
    }

    /**
     * schoolEntryProprietyArrayを取得する。
     */
    public String[] getSchoolEntryProprietyArray() {
        return schoolEntryProprietyArray;
    }

    /**
     * schoolEntryProprietyArrayを格納する。
     */
    public void setSchoolEntryProprietyArray(String[] schoolEntryProprietyArray) {
        this.schoolEntryProprietyArray = schoolEntryProprietyArray;
    }

    /**
     * fromSchoolRegisteredYearを取得する。
     */
    public String getFromSchoolRegisteredYear() {
        return fromSchoolRegisteredYear;
    }

    /**
     * fromSchoolRegisteredYearを格納する。
     */
    public void setFromSchoolRegisteredYear(String fromSchoolRegisteredYear) {
        this.fromSchoolRegisteredYear = fromSchoolRegisteredYear;
    }

    /**
     * fromSchoolRegisteredMonthを取得する。
     */
    public String getFromSchoolRegisteredMonth() {
        return fromSchoolRegisteredMonth;
    }

    /**
     * fromSchoolRegisteredMonthを格納する。
     */
    public void setFromSchoolRegisteredMonth(String fromSchoolRegisteredMonth) {
        this.fromSchoolRegisteredMonth = fromSchoolRegisteredMonth;
    }

    /**
     * fromSchoolRegisteredDayを取得する。
     */
    public String getFromSchoolRegisteredDay() {
        return fromSchoolRegisteredDay;
    }

    /**
     * fromSchoolRegisteredDayを格納する。
     */
    public void setFromSchoolRegisteredDay(String fromSchoolRegisteredDay) {
        this.fromSchoolRegisteredDay = fromSchoolRegisteredDay;
    }

    /**
     * toSchoolRegisteredYearを取得する。
     */
    public String getToSchoolRegisteredYear() {
        return toSchoolRegisteredYear;
    }

    /**
     * toSchoolRegisteredYearを格納する。
     */
    public void setToSchoolRegisteredYear(String toSchoolRegisteredYear) {
        this.toSchoolRegisteredYear = toSchoolRegisteredYear;
    }

    /**
     * toSchoolRegisteredMonthを取得する。
     */
    public String getToSchoolRegisteredMonth() {
        return toSchoolRegisteredMonth;
    }

    /**
     * toSchoolRegisteredMonthを格納する。
     */
    public void setToSchoolRegisteredMonth(String toSchoolRegisteredMonth) {
        this.toSchoolRegisteredMonth = toSchoolRegisteredMonth;
    }

    /**
     * toSchoolRegisteredDayを取得する。
     */
    public String getToSchoolRegisteredDay() {
        return toSchoolRegisteredDay;
    }

    /**
     * toSchoolRegisteredDayを格納する。
     */
    public void setToSchoolRegisteredDay(String toSchoolRegisteredDay) {
        this.toSchoolRegisteredDay = toSchoolRegisteredDay;
    }

    /**
     * fromSchoolUpdatedYearを取得する。
     */
    public String getFromSchoolUpdatedYear() {
        return fromSchoolUpdatedYear;
    }

    /**
     * fromSchoolUpdatedYearを格納する。
     */
    public void setFromSchoolUpdatedYear(String fromSchoolUpdatedYear) {
        this.fromSchoolUpdatedYear = fromSchoolUpdatedYear;
    }

    /**
     * fromSchoolUpdatedMonthを取得する。
     */
    public String getFromSchoolUpdatedMonth() {
        return fromSchoolUpdatedMonth;
    }

    /**
     * fromSchoolUpdatedMonthを格納する。
     */
    public void setFromSchoolUpdatedMonth(String fromSchoolUpdatedMonth) {
        this.fromSchoolUpdatedMonth = fromSchoolUpdatedMonth;
    }

    /**
     * fromSchoolUpdatedDayを取得する。
     */
    public String getFromSchoolUpdatedDay() {
        return fromSchoolUpdatedDay;
    }

    /**
     * fromSchoolUpdatedDayを格納する。
     */
    public void setFromSchoolUpdatedDay(String fromSchoolUpdatedDay) {
        this.fromSchoolUpdatedDay = fromSchoolUpdatedDay;
    }

    /**
     * toSchoolUpdatedYearを取得する。
     */
    public String getToSchoolUpdatedYear() {
        return toSchoolUpdatedYear;
    }

    /**
     * toSchoolUpdatedYearを格納する。
     */
    public void setToSchoolUpdatedYear(String toSchoolUpdatedYear) {
        this.toSchoolUpdatedYear = toSchoolUpdatedYear;
    }

    /**
     * toSchoolUpdatedMonthを取得する。
     */
    public String getToSchoolUpdatedMonth() {
        return toSchoolUpdatedMonth;
    }

    /**
     * toSchoolUpdatedMonthを格納する。
     */
    public void setToSchoolUpdatedMonth(String toSchoolUpdatedMonth) {
        this.toSchoolUpdatedMonth = toSchoolUpdatedMonth;
    }

    /**
     * toSchoolUpdatedDayを取得する。
     */
    public String getToSchoolUpdatedDay() {
        return toSchoolUpdatedDay;
    }

    /**
     * toSchoolUpdatedDayを格納する。
     */
    public void setToSchoolUpdatedDay(String toSchoolUpdatedDay) {
        this.toSchoolUpdatedDay = toSchoolUpdatedDay;
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
