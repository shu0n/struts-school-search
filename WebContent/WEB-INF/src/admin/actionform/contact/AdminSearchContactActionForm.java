package admin.actionform.contact;

import java.util.List;

import actionform.ContactExtendActionForm;

/**
 * 管理画面 お問合せ一覧画面の検索で使用するデータを格納するためのアクションフォーム
 */
public class AdminSearchContactActionForm extends ContactExtendActionForm {

    private String strContactId; // お問合せID(文字列型)
    private String strContactAccountId; // お問合せアカウントID(文字列型)
    private String likeContactLastName; // お問合せ姓(類似)
    private String likeContactFirstName; // お問合せ名(類似)
    private String likeContactLastNameKana; // お問合せ姓(フリガナ、類似)
    private String likeContactFirstNameKana; // // お問合せ名(フリガナ、類似)
    private String likeContactMailAddress; // お問合せ者メールアドレス(類似)
    private String[] contactStatusIdArray; // お問合せステータスIDリスト
    private String fromContactedYear; // お問合せ作成日時(年、From)
    private String fromContactedMonth; // お問合せ作成日時(月、From)
    private String fromContactedDay; // お問合せ作成日時(日、From)
    private String toContactedYear; // お問合せ作成日時(年、To)
    private String toContactedMonth; // お問合せ作成日時(月、To)
    private String toContactedDay; // お問合せ作成日時(日、To)
    private String fromContactUpdatedYear; // お問合せ更新日時(年、From)
    private String fromContactUpdatedMonth; // お問合せ更新日時(月、From)
    private String fromContactUpdatedDay; // お問合せ更新日時(日、From)
    private String toContactUpdatedYear; // お問合せ更新日時(年、To)
    private String toContactUpdatedMonth; // お問合せ更新日時(月、To)
    private String toContactUpdatedDay; // お問合せ更新日時(日、To)
    private List<ContactExtendActionForm> contactExtendFormList; // お問合せのリスト

    public AdminSearchContactActionForm() {}

    /**
     * strContactIdを取得する。
     */
    public String getStrContactId() {
        return strContactId;
    }

    /**
     * strContactIdを格納する。
     */
    public void setStrContactId(String strContactId) {
        this.strContactId = strContactId;
    }

    /**
     * strContactAccountIdを取得する。
     */
    public String getStrContactAccountId() {
        return strContactAccountId;
    }

    /**
     * strContactAccountIdを格納する。
     */
    public void setStrContactAccountId(String strContactAccountId) {
        this.strContactAccountId = strContactAccountId;
    }

    /**
     * likeContactLastNameを取得する。
     */
    public String getLikeContactLastName() {
        return likeContactLastName;
    }

    /**
     * likeContactLastNameを格納する。
     */
    public void setLikeContactLastName(String likeContactLastName) {
        this.likeContactLastName = likeContactLastName;
    }

    /**
     * likeContactFirstNameを取得する。
     */
    public String getLikeContactFirstName() {
        return likeContactFirstName;
    }

    /**
     * likeContactFirstNameを格納する。
     */
    public void setLikeContactFirstName(String likeContactFirstName) {
        this.likeContactFirstName = likeContactFirstName;
    }

    /**
     * likeContactLastNameKanaを取得する。
     */
    public String getLikeContactLastNameKana() {
        return likeContactLastNameKana;
    }

    /**
     * likeContactLastNameKanaを格納する。
     */
    public void setLikeContactLastNameKana(String likeContactLastNameKana) {
        this.likeContactLastNameKana = likeContactLastNameKana;
    }

    /**
     * likeContactFirstNameKanaを取得する。
     */
    public String getLikeContactFirstNameKana() {
        return likeContactFirstNameKana;
    }

    /**
     * likeContactFirstNameKanaを格納する。
     */
    public void setLikeContactFirstNameKana(String likeContactFirstNameKana) {
        this.likeContactFirstNameKana = likeContactFirstNameKana;
    }

    /**
     * likeContactMailAddressを取得する。
     */
    public String getLikeContactMailAddress() {
        return likeContactMailAddress;
    }

    /**
     * likeContactMailAddressを格納する。
     */
    public void setLikeContactMailAddress(String likeContactMailAddress) {
        this.likeContactMailAddress = likeContactMailAddress;
    }

    /**
     * contactStatusIdArrayを取得する。
     */
    public String[] getContactStatusIdArray() {
        return contactStatusIdArray;
    }

    /**
     * contactStatusIdArrayを格納する。
     */
    public void setContactStatusIdArray(String[] contactStatusIdArray) {
        this.contactStatusIdArray = contactStatusIdArray;
    }

    /**
     * fromContactedYearを取得する。
     */
    public String getFromContactedYear() {
        return fromContactedYear;
    }

    /**
     * fromContactedYearを格納する。
     */
    public void setFromContactedYear(String fromContactedYear) {
        this.fromContactedYear = fromContactedYear;
    }

    /**
     * fromContactedMonthを取得する。
     */
    public String getFromContactedMonth() {
        return fromContactedMonth;
    }

    /**
     * fromContactedMonthを格納する。
     */
    public void setFromContactedMonth(String fromContactedMonth) {
        this.fromContactedMonth = fromContactedMonth;
    }

    /**
     * fromContactedDayを取得する。
     */
    public String getFromContactedDay() {
        return fromContactedDay;
    }

    /**
     * fromContactedDayを格納する。
     */
    public void setFromContactedDay(String fromContactedDay) {
        this.fromContactedDay = fromContactedDay;
    }

    /**
     * toContactedYearを取得する。
     */
    public String getToContactedYear() {
        return toContactedYear;
    }

    /**
     * toContactedYearを格納する。
     */
    public void setToContactedYear(String toContactedYear) {
        this.toContactedYear = toContactedYear;
    }

    /**
     * toContactedMonthを取得する。
     */
    public String getToContactedMonth() {
        return toContactedMonth;
    }

    /**
     * toContactedMonthを格納する。
     */
    public void setToContactedMonth(String toContactedMonth) {
        this.toContactedMonth = toContactedMonth;
    }

    /**
     * toContactedDayを取得する。
     */
    public String getToContactedDay() {
        return toContactedDay;
    }

    /**
     * toContactedDayを格納する。
     */
    public void setToContactedDay(String toContactedDay) {
        this.toContactedDay = toContactedDay;
    }

    /**
     * fromContactUpdatedYearを取得する。
     */
    public String getFromContactUpdatedYear() {
        return fromContactUpdatedYear;
    }

    /**
     * fromContactUpdatedYearを格納する。
     */
    public void setFromContactUpdatedYear(String fromContactUpdatedYear) {
        this.fromContactUpdatedYear = fromContactUpdatedYear;
    }

    /**
     * fromContactUpdatedMonthを取得する。
     */
    public String getFromContactUpdatedMonth() {
        return fromContactUpdatedMonth;
    }

    /**
     * fromContactUpdatedMonthを格納する。
     */
    public void setFromContactUpdatedMonth(String fromContactUpdatedMonth) {
        this.fromContactUpdatedMonth = fromContactUpdatedMonth;
    }

    /**
     * fromContactUpdatedDayを取得する。
     */
    public String getFromContactUpdatedDay() {
        return fromContactUpdatedDay;
    }

    /**
     * fromContactUpdatedDayを格納する。
     */
    public void setFromContactUpdatedDay(String fromContactUpdatedDay) {
        this.fromContactUpdatedDay = fromContactUpdatedDay;
    }

    /**
     * toContactUpdatedYearを取得する。
     */
    public String getToContactUpdatedYear() {
        return toContactUpdatedYear;
    }

    /**
     * toContactUpdatedYearを格納する。
     */
    public void setToContactUpdatedYear(String toContactUpdatedYear) {
        this.toContactUpdatedYear = toContactUpdatedYear;
    }

    /**
     * toContactUpdatedMonthを取得する。
     */
    public String getToContactUpdatedMonth() {
        return toContactUpdatedMonth;
    }

    /**
     * toContactUpdatedMonthを格納する。
     */
    public void setToContactUpdatedMonth(String toContactUpdatedMonth) {
        this.toContactUpdatedMonth = toContactUpdatedMonth;
    }

    /**
     * toContactUpdatedDayを取得する。
     */
    public String getToContactUpdatedDay() {
        return toContactUpdatedDay;
    }

    /**
     * toContactUpdatedDayを格納する。
     */
    public void setToContactUpdatedDay(String toContactUpdatedDay) {
        this.toContactUpdatedDay = toContactUpdatedDay;
    }

    /**
     * contactExtendFormListを取得する。
     */
    public List<ContactExtendActionForm> getContactExtendFormList() {
        return contactExtendFormList;
    }

    /**
     * contactExtendFormListを格納する。
     */
    public void setContactExtendFormList(List<ContactExtendActionForm> contactExtendFormList) {
        this.contactExtendFormList = contactExtendFormList;
    }

}
