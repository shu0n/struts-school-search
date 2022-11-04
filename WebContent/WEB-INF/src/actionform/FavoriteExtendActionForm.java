package actionform;

import java.math.BigDecimal;

/**
 * お気に入り機能で使用するデータを格納するためのアクションフォーム
 */
public class FavoriteExtendActionForm extends FavoriteActionForm {

    private int registrantAccountId; // スクール登録者アカウントID
    private String registrantLastName; // スクール登録者姓
    private String registrantFirstName; // スクール登録者名
    private int registrantAdminId; // スクール登録者管理者ID
    private String schoolName; // スクール名
    private int schoolCategoryId; // カテゴリーID
    private String schoolCategoryName; // カテゴリー名
    private String schoolSummary; // 概要
    private int schoolPrefectureId; // 都道府県ID
    private String schoolPrefectureName; // 都道府県名
    private BigDecimal schoolFee; // 費用
    private String summaryImageFileName; // 一覧画面画像
    private String summaryImageFilePath; // 一覧画面画像のパス
    private String strFavoriteAddedAt; // お気に入り追加日時(文字列型)

    public FavoriteExtendActionForm() {}

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
     * registrantLastNameを取得する。
     */
    public String getRegistrantLastName() {
        return registrantLastName;
    }

    /**
     * registrantLastNameを格納する。
     */
    public void setRegistrantLastName(String registrantLastName) {
        this.registrantLastName = registrantLastName;
    }

    /**
     * registrantFirstNameを取得する。
     */
    public String getRegistrantFirstName() {
        return registrantFirstName;
    }

    /**
     * registrantFirstNameを格納する。
     */
    public void setRegistrantFirstName(String registrantFirstName) {
        this.registrantFirstName = registrantFirstName;
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
     * schoolCategoryNameを取得する。
     */
    public String getSchoolCategoryName() {
        return schoolCategoryName;
    }

    /**
     * schoolCategoryNameを格納する。
     */
    public void setSchoolCategoryName(String schoolCategoryName) {
        this.schoolCategoryName = schoolCategoryName;
    }

    /**
     * schoolSummaryを取得する。
     */
    public String getSchoolSummary() {
        return schoolSummary;
    }

    /**
     * schoolSummaryを格納する。
     */
    public void setSchoolSummary(String schoolSummary) {
        this.schoolSummary = schoolSummary;
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
     * schoolPrefectureNameを取得する。
     */
    public String getSchoolPrefectureName() {
        return schoolPrefectureName;
    }

    /**
     * schoolPrefectureNameを格納する。
     */
    public void setSchoolPrefectureName(String schoolPrefectureName) {
        this.schoolPrefectureName = schoolPrefectureName;
    }

    /**
     * schoolFeeを取得する。
     */
    public BigDecimal getSchoolFee() {
        return schoolFee;
    }

    /**
     * schoolFeeを格納する。
     */
    public void setSchoolFee(BigDecimal schoolFee) {
        this.schoolFee = schoolFee;
    }

    /**
     * summaryImageFileNameを取得する。
     */
    public String getSummaryImageFileName() {
        return summaryImageFileName;
    }

    /**
     * summaryImageFileNameを格納する。
     */
    public void setSummaryImageFileName(String summaryImageFileName) {
        this.summaryImageFileName = summaryImageFileName;
    }

    /**
     * summaryImageFilePathを取得する。
     */
    public String getSummaryImageFilePath() {
        return summaryImageFilePath;
    }

    /**
     * summaryImageFilePathを格納する。
     */
    public void setSummaryImageFilePath(String summaryImageFilePath) {
        this.summaryImageFilePath = summaryImageFilePath;
    }

    /**
     * strFavoriteAddedAtを取得する。
     */
    public String getStrFavoriteAddedAt() {
        return strFavoriteAddedAt;
    }

    /**
     * strFavoriteAddedAtを格納する。
     */
    public void setStrFavoriteAddedAt(String strFavoriteAddedAt) {
        this.strFavoriteAddedAt = strFavoriteAddedAt;
    }

}
