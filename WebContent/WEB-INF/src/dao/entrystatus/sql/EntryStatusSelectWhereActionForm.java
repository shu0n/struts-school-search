package dao.entrystatus.sql;

import org.apache.struts.action.ActionForm;

/**
 * 申込ステータステーブルから取得するレコードの条件に使用するデータを格納するためのアクションフォーム
 */
public class EntryStatusSelectWhereActionForm extends ActionForm {

    private int entryStatusId; // 申込ステータスID
    private String entryStatusName; // 申込ステータス名

    public EntryStatusSelectWhereActionForm() {}

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
