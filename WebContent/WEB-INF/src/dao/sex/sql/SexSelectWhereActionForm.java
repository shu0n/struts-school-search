package dao.sex.sql;

import org.apache.struts.action.ActionForm;

/**
 * 性別テーブルから取得するレコードの条件に使用するデータを格納するためのアクションフォーム
 */
public class SexSelectWhereActionForm extends ActionForm {

    private int sexId; // 性別ID
    private String sexName; // 性別名

    public SexSelectWhereActionForm() {}

    /**
     * sexIdを取得する。
     */
    public int getSexId() {
        return sexId;
    }

    /**
     * sexIdを格納する。
     */
    public void setSexId(int sexId) {
        this.sexId = sexId;
    }

    /**
     * sexNameを取得する。
     */
    public String getSexName() {
        return sexName;
    }

    /**
     * sexNameを格納する。
     */
    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

}
