package dao.contact.sql;

import org.apache.struts.action.ActionForm;

/**
 * お問合せテーブルに作成するレコードのデータを格納するためのアクションフォーム
 */
public class ContactInsertDataActionForm extends ActionForm {

    private int contactAccountId; // お問合せアカウントID
    private String contactLastName; // お問合せ者姓
    private String contactFirstName; // お問合せ者名
    private String contactLastNameKana; // お問合せ者姓(フリガナ)
    private String contactFirstNameKana; // お問合せ者名(フリガナ)
    private String contactMailAddress; // お問合せ者メールアドレス
    private String contactContent; // お問合せ内容
    private int contactStatusId; // お問合せステータスID

    public ContactInsertDataActionForm() {}

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

}
