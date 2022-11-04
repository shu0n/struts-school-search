package dao.contactstatus.sql;

import org.apache.struts.action.ActionForm;

/**
 * お問合せステータステーブルから取得するレコードの条件に使用するデータを格納するためのアクションフォーム
 */
public class ContactStatusSelectWhereActionForm extends ActionForm {

    private int contactStatusId; // お問合せステータスID
    private String contactStatusName; // お問合せステータス名

    public ContactStatusSelectWhereActionForm() {}

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
