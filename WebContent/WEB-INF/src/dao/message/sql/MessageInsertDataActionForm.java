package dao.message.sql;

import org.apache.struts.action.ActionForm;

/**
 * メッセージテーブルに作成するレコードのデータを格納するためのアクションフォーム
 */
public class MessageInsertDataActionForm extends ActionForm {

    private int replyMessageId; // 返信メッセージID
    private int entryId; // 申込ID
    private int senderAccountId; // 差出アカウントID
    private int recipientAccountId; // 受取アカウントID
    private String messageSubject; // 件名
    private String messageBody; // 本文

    public MessageInsertDataActionForm() {}

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
     * messageSubjectを取得する。
     */
    public String getMessageSubject() {
        return messageSubject;
    }

    /**
     * messageSubjectを格納する。
     */
    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }

    /**
     * messageBodyを取得する。
     */
    public String getMessageBody() {
        return messageBody;
    }

    /**
     * messageBodyを格納する。
     */
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

}
