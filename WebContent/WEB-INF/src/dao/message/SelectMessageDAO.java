package dao.message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import actionform.MessageActionForm;
import dao.ConnectorDAO;
import dao.message.sql.MessageSelectWhereActionForm;

public class SelectMessageDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * メッセージテーブルから指定した検索条件に一致するレコードを取得するためのメソッド
     * @param whereForm 検索条件を指定するデータを格納したアクションフォーム
     * @return 検索条件に一致したメッセージのアクションフォームをメッセージIDの昇順で格納したリスト
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<MessageActionForm> selectMatchedMessage(MessageSelectWhereActionForm whereForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルを指定するためのSELECT文を生成する。
            String sql = "SELECT * FROM messages ";

            // 検索条件の指定有無を格納する変数を生成してfalseを設定する。
            boolean isSpecified = false;

            // 条件を指定するためのStringBuilderを呼び出す。
            StringBuilder whereSb = new StringBuilder();
            whereSb.append("WHERE ");

            // メッセージID
            int messageId = whereForm.getMessageId();
            if(messageId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("message_id=" + messageId + " AND ");
                // 変数にtrueを設定する。
                isSpecified = true;
            }

            // 返信メッセージID
            int replyMessageId = whereForm.getReplyMessageId();
            if(replyMessageId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("reply_message_id=" + replyMessageId + " AND ");
                // 変数にtrueを設定する。
                isSpecified = true;
            }

            // 申込ID
            int entryId = whereForm.getEntryId();
            if(entryId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("entry_id=" + entryId + " AND ");
                // 変数にtrueを設定する。
                isSpecified = true;
            }

            // 差出アカウントID
            int senderAccountId = whereForm.getSenderAccountId();
            if(senderAccountId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("sender_account_id=" + senderAccountId + " AND ");
                // 変数にtrueを設定する。
                isSpecified = true;
            }

            // 受取アカウントID
            int recipientAccountId = whereForm.getRecipientAccountId();
            if(recipientAccountId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("recipient_account_id=" + recipientAccountId + " AND ");
                // 変数にtrueを設定する。
                isSpecified = true;
            }

            // 開封済フラグ
            String openedFlag = whereForm.getOpenedFlag();
            if(openedFlag != null) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("opened_flag='" + openedFlag + "' AND ");
                // 変数にtrueを設定する。
                isSpecified = true;
            }

            // 差出アカウント側削除フラグ
            String senderDeleteFlag = whereForm.getSenderDeleteFlag();
            if(senderDeleteFlag != null) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("sender_delete_flag='" + senderDeleteFlag + "' AND ");
                // 変数にtrueを設定する。
                isSpecified = true;
            }

            // 受取アカウント側削除フラグ
            String recipientDeleteFlag = whereForm.getRecipientDeleteFlag();
            if(recipientDeleteFlag != null) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("recipient_delete_flag='" + recipientDeleteFlag + "' AND ");
                // 変数にtrueを設定する。
                isSpecified = true;
            }

            if(isSpecified) {
                // 検索条件が指定されている場合

                // 末尾の" AND "を削除する。
                whereSb.delete(whereSb.length()-5, whereSb.length());
                // StringBuilderで生成した文をString型に変換した文とメッセージIDの昇順を指定する文を結合してSELECT全文を生成する。
                sql += whereSb.toString()+ "ORDER BY message_id ASC";
            } else {
                // 上記以外の場合はメッセージIDの昇順を指定する文を結合してSELECT全文を生成する。
                sql += "ORDER BY message_id ASC";
            }

            // DBとの接続状態を取得する。
            connection = getConnection();
            // SELECT文をPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // SELECT文の実行結果を変数に格納する。
            ResultSet rs = ps.executeQuery();

            // アクションフォームを格納するためのリストを生成する。
            List<MessageActionForm> messageFormList = new ArrayList<>();
            // 取得したレコードの件数分、処理を繰り返す。
            while(rs.next()) {
                // レコードから各列のデータを取得してアクションフォームに格納する。
                MessageActionForm outForm = new MessageActionForm();
                outForm.setMessageId(rs.getInt("message_id")); // メッセージID
                outForm.setReplyMessageId(rs.getInt("reply_message_id")); // 返信メッセージID
                outForm.setEntryId(rs.getInt("entry_id")); // 申込ID
                outForm.setSenderAccountId(rs.getInt("sender_account_id")); // 差出アカウントID
                outForm.setRecipientAccountId(rs.getInt("recipient_account_id")); // 受取アカウントID
                outForm.setMessageSubject(rs.getString("message_subject")); // 件名
                outForm.setMessageBody(rs.getString("message_body")); // 本文
                outForm.setSendedAt(rs.getTimestamp("sended_at")); // 送信日時
                outForm.setOpenedFlag(rs.getString("opened_flag")); // 開封済フラグ
                outForm.setOpenedAt(rs.getTimestamp("opened_at")); // 開封日時
                outForm.setSenderDeleteFlag(rs.getString("sender_delete_flag")); // 差出アカウント側削除フラグ
                outForm.setSenderFlagUpdatedAt(rs.getTimestamp("sender_flag_updated_at")); // 差出アカウント側削除フラグ更新日時
                outForm.setRecipientDeleteFlag(rs.getString("recipient_delete_flag")); // 受取アカウント側削除フラグ
                outForm.setRecipientFlagUpdatedAt(rs.getTimestamp("recipient_flag_updated_at")); // 受取アカウント側削除フラグ更新日時
                // リストにアクションフォームを追加する。
                messageFormList.add(outForm);
            }

            // アクションフォームを戻す。
            return messageFormList;
        } finally {
            if(connection != null) {
                // DBに接続されている場合
                try {
                    // DB接続を切断する。
                    connection.close();
                } catch(SQLException e) {
                    // 例外を投げる。
                    throw e;
                }
            }
        }
    }

    /**
     * DBとの接続状態を取得するためのメソッド
     */
    Connection getConnection() throws ClassNotFoundException, IOException, SQLException {
        // DBとの接続状態を戻す。
        return new ConnectorDAO().connectDatabase();
    }

}
