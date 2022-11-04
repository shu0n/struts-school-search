package actionform;

/**
 * メッセージ機能で使用するデータを格納するためのアクションフォーム
 */
public class MessageExtendActionForm extends MessageActionForm {

    private String senderLastName; // 差出側姓
    private String senderFirstName; // 差出側名
    private String recipientLastName; // 受取側姓
    private String recipientFirstName; // 受取側名
    private String strSendedAt; // 送信日時(文字列型)

    public MessageExtendActionForm() {}

    /**
     * senderLastNameを取得する。
     */
    public String getSenderLastName() {
        return senderLastName;
    }

    /**
     * senderLastNameを格納する。
     */
    public void setSenderLastName(String senderLastName) {
        this.senderLastName = senderLastName;
    }

    /**
     * senderFirstNameを取得する。
     */
    public String getSenderFirstName() {
        return senderFirstName;
    }

    /**
     * senderFirstNameを格納する。
     */
    public void setSenderFirstName(String senderFirstName) {
        this.senderFirstName = senderFirstName;
    }

    /**
     * recipientLastNameを取得する。
     */
    public String getRecipientLastName() {
        return recipientLastName;
    }

    /**
     * recipientLastNameを格納する。
     */
    public void setRecipientLastName(String recipientLastName) {
        this.recipientLastName = recipientLastName;
    }

    /**
     * recipientFirstNameを取得する。
     */
    public String getRecipientFirstName() {
        return recipientFirstName;
    }

    /**
     * recipientFirstNameを格納する。
     */
    public void setRecipientFirstName(String recipientFirstName) {
        this.recipientFirstName = recipientFirstName;
    }

    /**
     * strSendedAtを取得する。
     */
    public String getStrSendedAt() {
        return strSendedAt;
    }

    /**
     * strSendedAtを格納する。
     */
    public void setStrSendedAt(String strSendedAt) {
        this.strSendedAt = strSendedAt;
    }

}
