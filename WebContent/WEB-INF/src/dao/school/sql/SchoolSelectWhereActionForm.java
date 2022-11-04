package dao.school.sql;

import org.apache.struts.action.ActionForm;

/**
 * スクールテーブルから取得するレコードの条件に使用するデータを格納するためのアクションフォーム
 */
public class SchoolSelectWhereActionForm extends ActionForm {

    private int schoolId; // スクールID
    private String[] schoolIdArray; // スクールID配列
    private int registrantAccountId; // 登録者アカウントID
    private String[] registrantAccountIdArray; // 登録者アカウントID配列
    private int registrantAdminId; // 登録者管理者ID
    private String[] registrantAdminIdArray; // 登録者管理者ID配列
    private String schoolName; // スクール名
    private String likeSchoolName; // スクール名(類似)
    private int schoolCategoryId; // スクールカテゴリーID
    private String[] schoolCategoryIdArray; // スクールカテゴリーID配列
    private int schoolPrefectureId; // スクール都道府県名ID
    private String[] schoolPrefectureIdArray; // スクール都道府県ID配列
    private String schoolFee; // スクール費用
    private String minSchoolFee; // スクール料金(下限)
    private String maxSchoolFee; // スクール料金(上限)
    private String schoolReleasePropriety; // スクール公開可否
    private String[] schoolReleaseProprietyArray; // スクール公開可否配列
    private String schoolEntryPropriety; // スクール申込可否
    private String[] schoolEntryProprietyArray; // スクール申込可否配列
    private String fromSchoolRegisteredDate; // スクール登録日時(From)
    private String toSchoolRegisteredDate; // スクール登録日時(To)
    private String fromSchoolUpdatedDate; // スクール更新(From)
    private String toSchoolUpdatedDate; // スクール更新(To)

    public SchoolSelectWhereActionForm() {}

    /**
     * schoolIdを取得する。
     */
    public int getSchoolId() {
        return schoolId;
    }

    /**
     * schoolIdを格納する。
     */
    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * schoolIdArrayを取得する。
     */
    public String[] getSchoolIdArray() {
        return schoolIdArray;
    }

    /**
     * schoolIdArrayを格納する。
     */
    public void setSchoolIdArray(String[] schoolIdArray) {
        this.schoolIdArray = schoolIdArray;
    }

    /**
     * registrantAccountIdを取得する。
     */
    public int getRegistrantAccountId() {
        return registrantAccountId;
    }

    /**
     * registrantAccountIdを格納する。
     */
    public void setRegistrantAccountId(int registrantAccountId) {
        this.registrantAccountId = registrantAccountId;
    }

    /**
     * registrantAccountIdArrayを取得する。
     */
    public String[] getRegistrantAccountIdArray() {
        return registrantAccountIdArray;
    }

    /**
     * registrantAccountIdArrayを格納する。
     */
    public void setRegistrantAccountIdArray(String[] registrantAccountIdArray) {
        this.registrantAccountIdArray = registrantAccountIdArray;
    }

    /**
     * registrantAdminIdを取得する。
     */
    public int getRegistrantAdminId() {
        return registrantAdminId;
    }

    /**
     * registrantAdminIdを格納する。
     */
    public void setRegistrantAdminId(int registrantAdminId) {
        this.registrantAdminId = registrantAdminId;
    }

    /**
     * registrantAdminIdArrayを取得する。
     */
    public String[] getRegistrantAdminIdArray() {
        return registrantAdminIdArray;
    }

    /**
     * registrantAdminIdArrayを格納する。
     */
    public void setRegistrantAdminIdArray(String[] registrantAdminIdArray) {
        this.registrantAdminIdArray = registrantAdminIdArray;
    }

    /**
     * schoolNameを取得する。
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * schoolNameを格納する。
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
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
     * schoolCategoryIdを取得する。
     */
    public int getSchoolCategoryId() {
        return schoolCategoryId;
    }

    /**
     * schoolCategoryIdを格納する。
     */
    public void setSchoolCategoryId(int schoolCategoryId) {
        this.schoolCategoryId = schoolCategoryId;
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
     * schoolPrefectureIdを取得する。
     */
    public int getSchoolPrefectureId() {
        return schoolPrefectureId;
    }

    /**
     * schoolPrefectureIdを格納する。
     */
    public void setSchoolPrefectureId(int schoolPrefectureId) {
        this.schoolPrefectureId = schoolPrefectureId;
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
     * schoolFeeを取得する。
     */
    public String getSchoolFee() {
        return schoolFee;
    }

    /**
     * schoolFeeを格納する。
     */
    public void setSchoolFee(String schoolFee) {
        this.schoolFee = schoolFee;
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
     * schoolReleaseProprietyを取得する。
     */
    public String getSchoolReleasePropriety() {
        return schoolReleasePropriety;
    }

    /**
     * schoolReleaseProprietyを格納する。
     */
    public void setSchoolReleasePropriety(String schoolReleasePropriety) {
        this.schoolReleasePropriety = schoolReleasePropriety;
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
     * schoolEntryProprietyを取得する。
     */
    public String getSchoolEntryPropriety() {
        return schoolEntryPropriety;
    }

    /**
     * schoolEntryProprietyを格納する。
     */
    public void setSchoolEntryPropriety(String schoolEntryPropriety) {
        this.schoolEntryPropriety = schoolEntryPropriety;
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
     * fromSchoolRegisteredDateを取得する。
     */
    public String getFromSchoolRegisteredDate() {
        return fromSchoolRegisteredDate;
    }

    /**
     * fromSchoolRegisteredDateを格納する。
     */
    public void setFromSchoolRegisteredDate(String fromSchoolRegisteredDate) {
        this.fromSchoolRegisteredDate = fromSchoolRegisteredDate;
    }

    /**
     * toSchoolRegisteredDateを取得する。
     */
    public String getToSchoolRegisteredDate() {
        return toSchoolRegisteredDate;
    }

    /**
     * toSchoolRegisteredDateを格納する。
     */
    public void setToSchoolRegisteredDate(String toSchoolRegisteredDate) {
        this.toSchoolRegisteredDate = toSchoolRegisteredDate;
    }

    /**
     * fromSchoolUpdatedDateを取得する。
     */
    public String getFromSchoolUpdatedDate() {
        return fromSchoolUpdatedDate;
    }

    /**
     * fromSchoolUpdatedDateを格納する。
     */
    public void setFromSchoolUpdatedDate(String fromSchoolUpdatedDate) {
        this.fromSchoolUpdatedDate = fromSchoolUpdatedDate;
    }

    /**
     * toSchoolUpdatedDateを取得する。
     */
    public String getToSchoolUpdatedDate() {
        return toSchoolUpdatedDate;
    }

    /**
     * toSchoolUpdatedDateを格納する。
     */
    public void setToSchoolUpdatedDate(String toSchoolUpdatedDate) {
        this.toSchoolUpdatedDate = toSchoolUpdatedDate;
    }

}
