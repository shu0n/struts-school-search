package front.actionform.message;

import java.util.List;

import actionform.MessageExtendActionForm;

/**
 * 受信メッセージ詳細画面で使用するデータを格納するためのアクションフォーム
 */
public class FrontViewReceivedMessageActionForm extends MessageExtendActionForm {

    private List<MessageExtendActionForm> replySourceMessageList; // 返信元メッセージのアクションフォームのリスト

    public FrontViewReceivedMessageActionForm() {}

    /**
     * replySourceMessageListを取得する。
     */
    public List<MessageExtendActionForm> getReplySourceMessageList() {
        return replySourceMessageList;
    }

    /**
     * replySourceMessageListを格納する。
     */
    public void setReplySourceMessageList(List<MessageExtendActionForm> replySourceMessageList) {
        this.replySourceMessageList = replySourceMessageList;
    }

}
