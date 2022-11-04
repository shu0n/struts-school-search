package actionform;

import org.apache.struts.validator.ValidatorForm;

/**
 * お問合せステータステーブルのデータを格納するためのアクションフォーム
 */
public class ContactStatusActionForm extends ValidatorForm {

    private int contactStatusId; // お問合せステータスID
    private String contactStatusName; // お問合せステータス名

    public ContactStatusActionForm() {}

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
     * contactStatusNameを取得する。
     */
    public String getContactStatusName() {
        return contactStatusName;
    }

    /**
     * contactStatusNameを格納する。
     */
    public void setContactStatusName(String contactStatusName) {
        this.contactStatusName = contactStatusName;
    }

}
