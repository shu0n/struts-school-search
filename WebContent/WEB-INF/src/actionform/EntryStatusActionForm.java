package actionform;

import org.apache.struts.validator.ValidatorForm;

/**
 * 申込ステータステーブルのデータを格納するためのアクションフォーム
 */
public class EntryStatusActionForm extends ValidatorForm {

    private int entryStatusId; // 申込ステータスID
    private String entryStatusName; // 申込ステータス名

    public EntryStatusActionForm() {}

    /**
     * entryStatusIdを取得する。
     */
    public int getEntryStatusId() {
        return entryStatusId;
    }

    /**
     * entryStatusIdを格納する。
     */
    public void setEntryStatusId(int entryStatusId) {
        this.entryStatusId = entryStatusId;
    }

    /**
     * entryStatusNameを取得する。
     */
    public String getEntryStatusName() {
        return entryStatusName;
    }

    /**
     * entryStatusNameを格納する。
     */
    public void setEntryStatusName(String entryStatusName) {
        this.entryStatusName = entryStatusName;
    }

}
