package dao.message.sql;

import java.sql.Timestamp;

import org.apache.struts.action.ActionForm;

/**
 * メッセージテーブルのレコードで更新するデータを格納するためのアクションフォーム
 */
public class MessageUpdateDataActionForm extends ActionForm {

    private int messageId; // メッセージID
    private String openedFlag; // 開封済フラグ
    private Timestamp openedAt; // 開封日時

    public MessageUpdateDataActionForm() {}

    /**
     * messageIdを取得する。
     */
    public int getMessageId() {
        return messageId;
    }

    /**
     * messageIdを格納する。
     */
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    /**
     * openedFlagを取得する。
     */
    public String getOpenedFlag() {
        return openedFlag;
    }

    /**
     * openedFlagを格納する。
     */
    public void setOpenedFlag(String openedFlag) {
        this.openedFlag = openedFlag;
    }

    /**
     * openedAtを取得する。
     */
    public Timestamp getOpenedAt() {
        return openedAt;
    }

    /**
     * openedAtを格納する。
     */
    public void setOpenedAt(Timestamp openedAt) {
        this.openedAt = openedAt;
    }

}
