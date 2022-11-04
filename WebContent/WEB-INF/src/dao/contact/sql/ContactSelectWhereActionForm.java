package dao.contact.sql;

import org.apache.struts.action.ActionForm;

/**
 * お問合せテーブルから取得するレコードの条件に使用するデータを格納するためのアクションフォーム
 */
public class ContactSelectWhereActionForm extends ActionForm {

    private int contactId; // お問合せID
    private String[] contactIdArray; // お問合せID配列
    private int contactAccountId; // お問合せ者アカウントID
    private String[] contactAccountIdArray; // お問合せ者アカウントID配列
    private String contactLastName; // お問合せ者姓
    private String likeContactLastName; // お問合せ者姓(類似)
    private String contactFirstName; // お問合せ者名
    private String likeContactFirstName; // お問合せ者名(類似)
    private String contactLastNameKana; // お問合せ者姓(フリガナ)
    private String likeContactLastNameKana; // お問合せ者姓(フリガナ、類似)
    private String contactFirstNameKana; // お問合せ者名(フリガナ)
    private String likeContactFirstNameKana; // お問合せ者名(フリガナ、類似)
    private String contactMailAddress; // お問合せ者メールアドレス
    private String likeContactMailAddress; // お問合せ者メールアドレス(類似)
    private int contactStatusId; // お問合せステータスID
    private String[] contactStatusIdArray; // お問合せステータスID配列
    private String fromContactedDate; // お問合せ作成日時(From)
    private String toContactedDate; // お問合せ作成日時(To)
    private String fromContactUpdatedDate; // お問合せ更新日時(From)
    private String toContactUpdatedDate; // お問合せ更新日時(To)

    public ContactSelectWhereActionForm() {}

    /**
     * contactIdを取得する。
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * contactIdを格納する。
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * contactIdArrayを取得する。
     */
    public String[] getContactIdArray() {
        return contactIdArray;
    }

    /**
     * contactIdArrayを格納する。
     */
    public void setContactIdArray(String[] contactIdArray) {
        this.contactIdArray = contactIdArray;
    }

    /**
     * contactAccountIdを取得する。
     */
    public int getContactAccountId() {
        return contactAccountId;
    }

    /**
     * contactAccountIdを格納する。
     */
    public void setContactAccountId(int contactAccountId) {
        this.contactAccountId = contactAccountId;
    }

    /**
     * contactAccountIdArrayを取得する。
     */
    public String[] getContactAccountIdArray() {
        return contactAccountIdArray;
    }

    /**
     * contactAccountIdArrayを格納する。
     */
    public void setContactAccountIdArray(String[] contactAccountIdArray) {
        this.contactAccountIdArray = contactAccountIdArray;
    }

    /**
     * contactLastNameを取得する。
     */
    public String getContactLastName() {
        return contactLastName;
    }

    /**
     * contactLastNameを格納する。
     */
    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
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
     * contactFirstNameを取得する。
     */
    public String getContactFirstName() {
        return contactFirstName;
    }

    /**
     * contactFirstNameを格納する。
     */
    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
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
     * contactLastNameKanaを取得する。
     */
    public String getContactLastNameKana() {
        return contactLastNameKana;
    }

    /**
     * contactLastNameKanaを格納する。
     */
    public void setContactLastNameKana(String contactLastNameKana) {
        this.contactLastNameKana = contactLastNameKana;
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
     * contactFirstNameKanaを取得する。
     */
    public String getContactFirstNameKana() {
        return contactFirstNameKana;
    }

    /**
     * contactFirstNameKanaを格納する。
     */
    public void setContactFirstNameKana(String contactFirstNameKana) {
        this.contactFirstNameKana = contactFirstNameKana;
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
     * contactMailAddressを取得する。
     */
    public String getContactMailAddress() {
        return contactMailAddress;
    }

    /**
     * contactMailAddressを格納する。
     */
    public void setContactMailAddress(String contactMailAddress) {
        this.contactMailAddress = contactMailAddress;
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
     * contactStatusIdを取得する。
     */
    public int getContactStatusId() {
        return contactStatusId;
    }

    /**
     * contactStatusIdを格納する。
     */
    public void setContactStatusId(int contactStatusId) {
        this.contactStatusId = contactStatusId;
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
     * fromContactedDateを取得する。
     */
    public String getFromContactedDate() {
        return fromContactedDate;
    }

    /**
     * fromContactedDateを格納する。
     */
    public void setFromContactedDate(String fromContactedDate) {
        this.fromContactedDate = fromContactedDate;
    }

    /**
     * toContactedDateを取得する。
     */
    public String getToContactedDate() {
        return toContactedDate;
    }

    /**
     * toContactedDateを格納する。
     */
    public void setToContactedDate(String toContactedDate) {
        this.toContactedDate = toContactedDate;
    }

    /**
     * fromContactUpdatedDateを取得する。
     */
    public String getFromContactUpdatedDate() {
        return fromContactUpdatedDate;
    }

    /**
     * fromContactUpdatedDateを格納する。
     */
    public void setFromContactUpdatedDate(String fromContactUpdatedDate) {
        this.fromContactUpdatedDate = fromContactUpdatedDate;
    }

    /**
     * toContactUpdatedDateを取得する。
     */
    public String getToContactUpdatedDate() {
        return toContactUpdatedDate;
    }

    /**
     * toContactUpdatedDateを格納する。
     */
    public void setToContactUpdatedDate(String toContactUpdatedDate) {
        this.toContactUpdatedDate = toContactUpdatedDate;
    }

}
