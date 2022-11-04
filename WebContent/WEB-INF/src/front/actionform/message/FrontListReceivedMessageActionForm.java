package front.actionform.message;

import java.util.List;

import actionform.MessageExtendActionForm;

/**
 * 受信メッセージ一覧画面で使用するデータを格納するためのアクションフォーム
 */
public class FrontListReceivedMessageActionForm extends MessageExtendActionForm {

    private int totalForm; // 全アクションフォームの件数
    private int totalPage; // 全ページ数
    private int currentPage; // 現在のページ
    private List<MessageExtendActionForm> messageExtendFormList; // メッセージのアクションフォームのリスト
    private List<MessageExtendActionForm> displayedMessageList; // 表示するメッセージのアクションフォームのリスト

    public FrontListReceivedMessageActionForm() {}

    /**
     * totalFormを取得する。
     */
    public int getTotalForm() {
        return totalForm;
    }

    /**
     * totalFormを格納する。
     */
    public void setTotalForm(int totalForm) {
        this.totalForm = totalForm;
    }

    /**
     * totalPageを取得する。
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * totalPageを格納する。
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * currentPageを取得する。
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * currentPageを格納する。
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * messageExtendFormListを取得する。
     */
    public List<MessageExtendActionForm> getMessageExtendFormList() {
        return messageExtendFormList;
    }

    /**
     * messageExtendFormListを格納する。
     */
    public void setMessageExtendFormList(List<MessageExtendActionForm> messageExtendFormList) {
        this.messageExtendFormList = messageExtendFormList;
    }

    /**
     * displayedMessageListを取得する。
     */
    public List<MessageExtendActionForm> getDisplayedMessageList() {
        return displayedMessageList;
    }

    /**
     * displayedMessageListを格納する。
     */
    public void setDisplayedMessageList(List<MessageExtendActionForm> displayedMessageList) {
        this.displayedMessageList = displayedMessageList;
    }

}
