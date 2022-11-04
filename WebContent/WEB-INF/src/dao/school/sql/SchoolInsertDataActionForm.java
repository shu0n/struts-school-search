package dao.school.sql;

import java.math.BigDecimal;

import org.apache.struts.action.ActionForm;

/**
 * スクールテーブルに作成するレコードのデータを格納するためのアクションフォーム
 */
public class SchoolInsertDataActionForm extends ActionForm {

    private int registrantAccountId; // 登録者アカウントID
    private int registrantAdminId; // 登録者管理者ID
    private String schoolName; // スクール名
    private int schoolCategoryId; // スクールカテゴリーID
    private String schoolSummary; // スクール概要
    private String schoolDescription; // スクール詳細
    private String schoolZipCode1; // スクール郵便番号1
    private String schoolZipCode2; // スクール郵便番号2
    private int schoolPrefectureId; // スクール都道府県名ID
    private String schoolCity; // スクール市区町村
    private String schoolAddress1; // スクール住所1
    private String schoolAddress2; // スクール住所2
    private BigDecimal schoolFee; // スクール費用
    private String supplementaryFee; // 費用補足
    private String schoolUrl; // スクールサイトURL
    private String schoolReleasePropriety; // スクール公開可否
    private String schoolEntryPropriety; // スクール申込可否
    private String summaryImageFileName; // 一覧画面画像ファイル名
    private String detailImage1FileName; // 詳細画面画像1ファイル名
    private String detailImage2FileName; // 詳細画面画像2ファイル名
    private String detailImage3FileName; // 詳細画面画像3ファイル名
    private String detailImage4FileName; // 詳細画面画像4ファイル名
    private String detailImage5FileName; // 詳細画面画像5ファイル名
    private String detailImage6FileName; // 詳細画面画像6ファイル名
    private String detailImage7FileName; // 詳細画面画像7ファイル名
    private String detailImage8FileName; // 詳細画面画像8ファイル名

    public SchoolInsertDataActionForm() {}

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
     * schoolDescriptionを取得する。
     */
    public String getSchoolDescription() {
        return schoolDescription;
    }

    /**
     * schoolDescriptionを格納する。
     */
    public void setSchoolDescription(String schoolDescription) {
        this.schoolDescription = schoolDescription;
    }

    /**
     * schoolZipCode1を取得する。
     */
    public String getSchoolZipCode1() {
        return schoolZipCode1;
    }

    /**
     * schoolZipCode1を格納する。
     */
    public void setSchoolZipCode1(String schoolZipCode1) {
        this.schoolZipCode1 = schoolZipCode1;
    }

    /**
     * schoolZipCode2を取得する。
     */
    public String getSchoolZipCode2() {
        return schoolZipCode2;
    }

    /**
     * schoolZipCode2を格納する。
     */
    public void setSchoolZipCode2(String schoolZipCode2) {
        this.schoolZipCode2 = schoolZipCode2;
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
     * schoolCityを取得する。
     */
    public String getSchoolCity() {
        return schoolCity;
    }

    /**
     * schoolCityを格納する。
     */
    public void setSchoolCity(String schoolCity) {
        this.schoolCity = schoolCity;
    }

    /**
     * schoolAddress1を取得する。
     */
    public String getSchoolAddress1() {
        return schoolAddress1;
    }

    /**
     * schoolAddress1を格納する。
     */
    public void setSchoolAddress1(String schoolAddress1) {
        this.schoolAddress1 = schoolAddress1;
    }

    /**
     * schoolAddress2を取得する。
     */
    public String getSchoolAddress2() {
        return schoolAddress2;
    }

    /**
     * schoolAddress2を格納する。
     */
    public void setSchoolAddress2(String schoolAddress2) {
        this.schoolAddress2 = schoolAddress2;
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
     * supplementaryFeeを取得する。
     */
    public String getSupplementaryFee() {
        return supplementaryFee;
    }

    /**
     * supplementaryFeeを格納する。
     */
    public void setSupplementaryFee(String supplementaryFee) {
        this.supplementaryFee = supplementaryFee;
    }

    /**
     * schoolUrlを取得する。
     */
    public String getSchoolUrl() {
        return schoolUrl;
    }

    /**
     * schoolUrlを格納する。
     */
    public void setSchoolUrl(String schoolUrl) {
        this.schoolUrl = schoolUrl;
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
     * detailImage1FileNameを取得する。
     */
    public String getDetailImage1FileName() {
        return detailImage1FileName;
    }

    /**
     * detailImage1FileNameを格納する。
     */
    public void setDetailImage1FileName(String detailImage1FileName) {
        this.detailImage1FileName = detailImage1FileName;
    }

    /**
     * detailImage2FileNameを取得する。
     */
    public String getDetailImage2FileName() {
        return detailImage2FileName;
    }

    /**
     * detailImage2FileNameを格納する。
     */
    public void setDetailImage2FileName(String detailImage2FileName) {
        this.detailImage2FileName = detailImage2FileName;
    }

    /**
     * detailImage3FileNameを取得する。
     */
    public String getDetailImage3FileName() {
        return detailImage3FileName;
    }

    /**
     * detailImage3FileNameを格納する。
     */
    public void setDetailImage3FileName(String detailImage3FileName) {
        this.detailImage3FileName = detailImage3FileName;
    }

    /**
     * detailImage4FileNameを取得する。
     */
    public String getDetailImage4FileName() {
        return detailImage4FileName;
    }

    /**
     * detailImage4FileNameを格納する。
     */
    public void setDetailImage4FileName(String detailImage4FileName) {
        this.detailImage4FileName = detailImage4FileName;
    }

    /**
     * detailImage5FileNameを取得する。
     */
    public String getDetailImage5FileName() {
        return detailImage5FileName;
    }

    /**
     * detailImage5FileNameを格納する。
     */
    public void setDetailImage5FileName(String detailImage5FileName) {
        this.detailImage5FileName = detailImage5FileName;
    }

    /**
     * detailImage6FileNameを取得する。
     */
    public String getDetailImage6FileName() {
        return detailImage6FileName;
    }

    /**
     * detailImage6FileNameを格納する。
     */
    public void setDetailImage6FileName(String detailImage6FileName) {
        this.detailImage6FileName = detailImage6FileName;
    }

    /**
     * detailImage7FileNameを取得する。
     */
    public String getDetailImage7FileName() {
        return detailImage7FileName;
    }

    /**
     * detailImage7FileNameを格納する。
     */
    public void setDetailImage7FileName(String detailImage7FileName) {
        this.detailImage7FileName = detailImage7FileName;
    }

    /**
     * detailImage8FileNameを取得する。
     */
    public String getDetailImage8FileName() {
        return detailImage8FileName;
    }

    /**
     * detailImage8FileNameを格納する。
     */
    public void setDetailImage8FileName(String detailImage8FileName) {
        this.detailImage8FileName = detailImage8FileName;
    }

}
