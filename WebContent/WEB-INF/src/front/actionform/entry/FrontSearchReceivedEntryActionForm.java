package front.actionform.entry;

import java.util.List;

import actionform.EntryExtendActionForm;

/**
 * 申込受付一覧画面の検索で使用するデータを格納するためのアクションフォーム
 */
public class FrontSearchReceivedEntryActionForm extends EntryExtendActionForm {

    private String likeApplicantLastName; // 申込者姓(類似)
    private String likeApplicantFirstName; // 申込者名(類似)
    private String likeApplicantLastNameKana; // 申込者姓(フリガナ、類似)
    private String likeApplicantFirstNameKana; // // 申込者名(フリガナ、類似)
    private String[] entryStatusIdArray; // 申込ステータスID配列
    private String fromEntriedYear; // 申込日時(年、From)
    private String fromEntriedMonth; // 申込日時(月、From)
    private String fromEntriedDay; // 申込日時(日、From)
    private String toEntriedYear; // 申込日時(年、To)
    private String toEntriedMonth; // 申込日時(月、To)
    private String toEntriedDay; // 申込日時(日、To)
    private String fromEntryUpdatedYear; // 申込更新日時(年、From)
    private String fromEntryUpdatedMonth; // 申込更新日時(月、From)
    private String fromEntryUpdatedDay; // 申込更新日時(日、From)
    private String toEntryUpdatedYear; // 申込更新(年、To)
    private String toEntryUpdatedMonth; // 申込更新日時(月、To)
    private String toEntryUpdatedDay; // 申込更新日時(日、To)
    private List<EntryExtendActionForm> entryExtendFormList; // 申込のリスト

    public FrontSearchReceivedEntryActionForm() {}

    /**
     * likeApplicantLastNameを取得する。
     */
    public String getLikeApplicantLastName() {
        return likeApplicantLastName;
    }

    /**
     * likeApplicantLastNameを格納する。
     */
    public void setLikeApplicantLastName(String likeApplicantLastName) {
        this.likeApplicantLastName = likeApplicantLastName;
    }

    /**
     * likeApplicantFirstNameを取得する。
     */
    public String getLikeApplicantFirstName() {
        return likeApplicantFirstName;
    }

    /**
     * likeApplicantFirstNameを格納する。
     */
    public void setLikeApplicantFirstName(String likeApplicantFirstName) {
        this.likeApplicantFirstName = likeApplicantFirstName;
    }

    /**
     * likeApplicantLastNameKanaを取得する。
     */
    public String getLikeApplicantLastNameKana() {
        return likeApplicantLastNameKana;
    }

    /**
     * likeApplicantLastNameKanaを格納する。
     */
    public void setLikeApplicantLastNameKana(String likeApplicantLastNameKana) {
        this.likeApplicantLastNameKana = likeApplicantLastNameKana;
    }

    /**
     * likeApplicantFirstNameKanaを取得する。
     */
    public String getLikeApplicantFirstNameKana() {
        return likeApplicantFirstNameKana;
    }

    /**
     * likeApplicantFirstNameKanaを格納する。
     */
    public void setLikeApplicantFirstNameKana(String likeApplicantFirstNameKana) {
        this.likeApplicantFirstNameKana = likeApplicantFirstNameKana;
    }

    /**
     * entryStatusIdArrayを取得する。
     */
    public String[] getEntryStatusIdArray() {
        return entryStatusIdArray;
    }

    /**
     * entryStatusIdArrayを格納する。
     */
    public void setEntryStatusIdArray(String[] entryStatusIdArray) {
        this.entryStatusIdArray = entryStatusIdArray;
    }

    /**
     * fromEntriedYearを取得する。
     */
    public String getFromEntriedYear() {
        return fromEntriedYear;
    }

    /**
     * fromEntriedYearを格納する。
     */
    public void setFromEntriedYear(String fromEntriedYear) {
        this.fromEntriedYear = fromEntriedYear;
    }

    /**
     * fromEntriedMonthを取得する。
     */
    public String getFromEntriedMonth() {
        return fromEntriedMonth;
    }

    /**
     * fromEntriedMonthを格納する。
     */
    public void setFromEntriedMonth(String fromEntriedMonth) {
        this.fromEntriedMonth = fromEntriedMonth;
    }

    /**
     * fromEntriedDayを取得する。
     */
    public String getFromEntriedDay() {
        return fromEntriedDay;
    }

    /**
     * fromEntriedDayを格納する。
     */
    public void setFromEntriedDay(String fromEntriedDay) {
        this.fromEntriedDay = fromEntriedDay;
    }

    /**
     * toEntriedYearを取得する。
     */
    public String getToEntriedYear() {
        return toEntriedYear;
    }

    /**
     * toEntriedYearを格納する。
     */
    public void setToEntriedYear(String toEntriedYear) {
        this.toEntriedYear = toEntriedYear;
    }

    /**
     * toEntriedMonthを取得する。
     */
    public String getToEntriedMonth() {
        return toEntriedMonth;
    }

    /**
     * toEntriedMonthを格納する。
     */
    public void setToEntriedMonth(String toEntriedMonth) {
        this.toEntriedMonth = toEntriedMonth;
    }

    /**
     * toEntriedDayを取得する。
     */
    public String getToEntriedDay() {
        return toEntriedDay;
    }

    /**
     * toEntriedDayを格納する。
     */
    public void setToEntriedDay(String toEntriedDay) {
        this.toEntriedDay = toEntriedDay;
    }

    /**
     * fromEntryUpdatedYearを取得する。
     */
    public String getFromEntryUpdatedYear() {
        return fromEntryUpdatedYear;
    }

    /**
     * fromEntryUpdatedYearを格納する。
     */
    public void setFromEntryUpdatedYear(String fromEntryUpdatedYear) {
        this.fromEntryUpdatedYear = fromEntryUpdatedYear;
    }

    /**
     * fromEntryUpdatedMonthを取得する。
     */
    public String getFromEntryUpdatedMonth() {
        return fromEntryUpdatedMonth;
    }

    /**
     * fromEntryUpdatedMonthを格納する。
     */
    public void setFromEntryUpdatedMonth(String fromEntryUpdatedMonth) {
        this.fromEntryUpdatedMonth = fromEntryUpdatedMonth;
    }

    /**
     * fromEntryUpdatedDayを取得する。
     */
    public String getFromEntryUpdatedDay() {
        return fromEntryUpdatedDay;
    }

    /**
     * fromEntryUpdatedDayを格納する。
     */
    public void setFromEntryUpdatedDay(String fromEntryUpdatedDay) {
        this.fromEntryUpdatedDay = fromEntryUpdatedDay;
    }

    /**
     * toEntryUpdatedYearを取得する。
     */
    public String getToEntryUpdatedYear() {
        return toEntryUpdatedYear;
    }

    /**
     * toEntryUpdatedYearを格納する。
     */
    public void setToEntryUpdatedYear(String toEntryUpdatedYear) {
        this.toEntryUpdatedYear = toEntryUpdatedYear;
    }

    /**
     * toEntryUpdatedMonthを取得する。
     */
    public String getToEntryUpdatedMonth() {
        return toEntryUpdatedMonth;
    }

    /**
     * toEntryUpdatedMonthを格納する。
     */
    public void setToEntryUpdatedMonth(String toEntryUpdatedMonth) {
        this.toEntryUpdatedMonth = toEntryUpdatedMonth;
    }

    /**
     * toEntryUpdatedDayを取得する。
     */
    public String getToEntryUpdatedDay() {
        return toEntryUpdatedDay;
    }

    /**
     * toEntryUpdatedDayを格納する。
     */
    public void setToEntryUpdatedDay(String toEntryUpdatedDay) {
        this.toEntryUpdatedDay = toEntryUpdatedDay;
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

}
