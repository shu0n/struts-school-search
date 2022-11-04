package model.message;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import actionform.MessageActionForm;
import dao.message.SelectMessageDAO;
import dao.message.UpdateMessageDAO;
import dao.message.sql.MessageSelectWhereActionForm;
import dao.message.sql.MessageUpdateDataActionForm;

public class MessageStatusModel {

    /**
     * アクセスしたアカウントが受取側アカウントであるかを判定するためのメソッド
     * @param messageId メッセージID
     * @param accountId 受取側アカウントID
     * @return 判定結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean isRecipient(int messageId, int accountId) throws ClassNotFoundException, IOException, SQLException {
        // DAOメソッドに引き渡すアクションフォームを生成してメッセージID、受取側アカウントID、受取側アカウント削除フラグを格納する。
        MessageSelectWhereActionForm messageSelectWhereForm = new MessageSelectWhereActionForm();
        messageSelectWhereForm.setMessageId(messageId);
        messageSelectWhereForm.setRecipientAccountId(accountId);
        messageSelectWhereForm.setRecipientDeleteFlag("0");
        // メッセージテーブルからメッセージIDに紐づくレコードを取得する。
        List<MessageActionForm> messageFormList = new SelectMessageDAO().selectMatchedMessage(messageSelectWhereForm);
        if(messageFormList.isEmpty()) {
            // 取得できない場合はfalseを戻す。
            return false;
        } else {
            // 上記以外の場合はtrueを戻す。
            return true;
        }
    }

    /**
     * アクセスしたアカウントが差出側アカウントであるかを判定するためのメソッド
     * @param messageId メッセージID
     * @param accountId 差出側アカウントID
     * @return 判定結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean isSender(int messageId, int accountId) throws ClassNotFoundException, IOException, SQLException {
        // DAOメソッドに引き渡すアクションフォームを生成してメッセージID、受取側アカウントID、受取側アカウント削除フラグを格納する。
        MessageSelectWhereActionForm messageSelectWhereForm = new MessageSelectWhereActionForm();
        messageSelectWhereForm.setMessageId(messageId);
        messageSelectWhereForm.setSenderAccountId(accountId);
        messageSelectWhereForm.setSenderDeleteFlag("0");
        // メッセージテーブルからメッセージIDに紐づくレコードを取得する。
        List<MessageActionForm> messageFormList = new SelectMessageDAO().selectMatchedMessage(messageSelectWhereForm);
        if(messageFormList.isEmpty()) {
            // 取得できない場合はfalseを戻す。
            return false;
        } else {
            // 上記以外の場合はtrueを戻す。
            return true;
        }
    }

    /**
     * 特定のメッセージIDに紐づくメッセージを"開封済"に更新するためのメソッド
     * @param messageId メッセージID
     * @return "0"(未開封)"または"1"(開封済)
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public String updateReceivedMessageOpened(int messageId) throws ClassNotFoundException, IOException, SQLException {
        // DAOメソッドに引き渡すアクションフォームを生成してメッセージID、開封済フラグ"1"(開封済)、を格納する。
        MessageUpdateDataActionForm messageUpdateDataForm = new MessageUpdateDataActionForm();
        messageUpdateDataForm.setMessageId(messageId);
        messageUpdateDataForm.setOpenedFlag("1");
        messageUpdateDataForm.setOpenedAt(getCurrentTimestamp());
        // メッセージIDに紐づくレコードを更新する。
        boolean result = new UpdateMessageDAO().updateMessage(messageUpdateDataForm);
        if(!result) {
            // レコードの更新が失敗した場合は"0"(未開封)を戻す。
            return "0";
        } else {
            // 上記以外の場合は"1"(開封済)を戻す。
            return "1";
        }
    }

    /**
     * 現在日時(日時型)を取得するためのメソッド
     */
     Timestamp getCurrentTimestamp() {
        // 現在日時(日時型)を戻す。
        return new Timestamp(System.currentTimeMillis());
    }

}
