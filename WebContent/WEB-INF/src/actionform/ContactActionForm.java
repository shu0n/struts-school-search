package actionform;

import java.sql.Timestamp;

import org.apache.struts.validator.ValidatorForm;

/**
 * お問合せテーブルのデータを格納するためのアクションフォーム
 */
public class ContactActionForm extends ValidatorForm {

    private int contactId; // お問合せID
    private int contactAccountId; // お問合せ者アカウントID
    private String contactLastName; // お問合せ者姓
    private String contactFirstName; // お問合せ者名
    private String contactLastNameKana; // お問合せ者姓(フリガナ)
    private String contactFirstNameKana; // お問合せ者名(フリガナ)
    private String contactMailAddress; // お問合せ者メールアドレス
    private String contactContent; // お問合せ内容
    private int contactStatusId; // お問合せステータスID
    private Timestamp contactedAt; // お問合せ日時
    private Timestamp contactUpdatedAt; // お問合せ更新日時
    private String contactDeleteFlag; // お問合せ削除フラグ
    private Timestamp contactDeletedAt; // お問合せ削除日時

    public ContactActionForm() {}

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
     * contactContentを取得する。
     */
    public String getContactContent() {
        return contactContent;
    }

    /**
     * contactContentを格納する。
     */
    public void setContactContent(String contactContent) {
        this.contactContent = contactContent;
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
     * contactedAtを取得する。
     */
    public Timestamp getContactedAt() {
        return contactedAt;
    }

    /**
     * contactedAtを格納する。
     */
    public void setContactedAt(Timestamp contactedAt) {
        this.contactedAt = contactedAt;
    }

    /**
     * contactUpdatedAtを取得する。
     */
    public Timestamp getContactUpdatedAt() {
        return contactUpdatedAt;
    }

    /**
     * contactUpdatedAtを格納する。
     */
    public void setContactUpdatedAt(Timestamp contactUpdatedAt) {
        this.contactUpdatedAt = contactUpdatedAt;
    }

    /**
     * contactDeleteFlagを取得する。
     */
    public String getContactDeleteFlag() {
        return contactDeleteFlag;
    }

    /**
     * contactDeleteFlagを格納する。
     */
    public void setContactDeleteFlag(String contactDeleteFlag) {
        this.contactDeleteFlag = contactDeleteFlag;
    }

    /**
     * contactDeletedAtを取得する。
     */
    public Timestamp getContactDeletedAt() {
        return contactDeletedAt;
    }

    /**
     * contactDeletedAtを格納する。
     */
    public void setContactDeletedAt(Timestamp contactDeletedAt) {
        this.contactDeletedAt = contactDeletedAt;
    }

}
