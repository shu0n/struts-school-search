package dao.message.sql;

import java.sql.Timestamp;

import org.apache.struts.action.ActionForm;

/**
 * メッセージテーブルから取得するレコードの条件に使用するデータを格納するためのアクションフォーム
 */
public class MessageSelectWhereActionForm extends ActionForm {

    private int messageId; // メッセージID
    private int replyMessageId; // 返信メッセージID
    private int entryId; // 申込ID
    private int senderAccountId; // 差出アカウントID
    private int recipientAccountId; // 受取アカウントID
    private String openedFlag; // 開封済フラグ
    private Timestamp openedAt; // 開封日時
    private String senderDeleteFlag; // 差出アカウント側削除フラグ
    private String recipientDeleteFlag; // 受取アカウント側削除フラグ

    public MessageSelectWhereActionForm() {}

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
     * replyMessageIdを取得する。
     */
    public int getReplyMessageId() {
        return replyMessageId;
    }

    /**
     * replyMessageIdを格納する。
     */
    public void setReplyMessageId(int replyMessageId) {
        this.replyMessageId = replyMessageId;
    }

    /**
     * entryIdを取得する。
     */
    public int getEntryId() {
        return entryId;
    }

    /**
     * entryIdを格納する。
     */
    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    /**
     * senderAccountIdを取得する。
     */
    public int getSenderAccountId() {
        return senderAccountId;
    }

    /**
     * senderAccountIdを格納する。
     */
    public void setSenderAccountId(int senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    /**
     * recipientAccountIdを取得する。
     */
    public int getRecipientAccountId() {
        return recipientAccountId;
    }

    /**
     * recipientAccountIdを格納する。
     */
    public void setRecipientAccountId(int recipientAccountId) {
        this.recipientAccountId = recipientAccountId;
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

    /**
     * senderDeleteFlagを取得する。
     */
    public String getSenderDeleteFlag() {
        return senderDeleteFlag;
    }

    /**
     * senderDeleteFlagを格納する。
     */
    public void setSenderDeleteFlag(String senderDeleteFlag) {
        this.senderDeleteFlag = senderDeleteFlag;
    }

    /**
     * recipientDeleteFlagを取得する。
     */
    public String getRecipientDeleteFlag() {
        return recipientDeleteFlag;
    }

    /**
     * recipientDeleteFlagを格納する。
     */
    public void setRecipientDeleteFlag(String recipientDeleteFlag) {
        this.recipientDeleteFlag = recipientDeleteFlag;
    }

}
