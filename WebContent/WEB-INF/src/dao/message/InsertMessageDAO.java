package dao.message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.ConnectorDAO;
import dao.message.sql.MessageInsertDataActionForm;

public class InsertMessageDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * メッセージテーブルにレコードを作成するためのメソッド
     * @param insertDataForm 登録するデータを格納したアクションフォーム
     * @return 発番されたメッセージID
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public int insertMessage(MessageInsertDataActionForm insertDataForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルを指定するためのINSERT文を生成する。
            String tableSql = "INSERT INTO messages (";

            // 列名を追加するためのStringBuilderを呼び出す。
            StringBuilder columnSb = new StringBuilder();
            // 各列名を一度リスト変数に格納して繰り返し処理で追加する。
            List<String> intColumnList = new ArrayList<>();
            intColumnList.addAll(Arrays.asList(
                    "entry_id", // 申込ID
                    "sender_account_id", // 差出アカウントID
                    "recipient_account_id", // 受取アカウントID
                    "message_subject", // 件名
                    "message_body" // 本文
                    ));
            for(String value : intColumnList) {
                columnSb.append(", " + value);
            }
            columnSb.delete(0,2);

            // データを追加するためのStringBuilderを呼び出す。
            StringBuilder dataSb = new StringBuilder();
            // 各データ(整数型)を一度リスト変数に格納して繰り返し処理で追加する。
            List<Integer> intDataList = new ArrayList<>();
            intDataList.addAll(Arrays.asList(
                    insertDataForm.getEntryId(), // 申込ID
                    insertDataForm.getSenderAccountId(), // 差出アカウントID
                    insertDataForm.getRecipientAccountId() // 受取アカウントID
                    ));
            for(int value : intDataList) {
                dataSb.append(", " + value);
            }
            // 各データ(文字列型)を一度リスト変数に格納して繰り返し処理で追加する。
            List<String> strDataList = new ArrayList<>();
            strDataList.addAll(Arrays.asList(
                    insertDataForm.getMessageSubject(), // 件名
                    insertDataForm.getMessageBody() // 本文
                    ));
            for(String value : strDataList) {
                dataSb.append(", '" + value + "'");
            }
            dataSb.delete(0,2);

            // NULL可またはデフォルト値がある項目は入力値によって列名とデータを追加する。
            // 返信メッセージID
            int replyMessageId = insertDataForm.getReplyMessageId();
            if(replyMessageId != 0) {
                // 0以外の場合は追加する。
                columnSb.append(", reply_message_id");
                dataSb.append(", " + replyMessageId);
            }

            columnSb.append(") VALUES(");
            dataSb.append(")");

            // StringBuilderで生成した文をString型に変換して結合してINSERT全文を生成する。
            String sql =  tableSql + columnSb.toString() + dataSb.toString();

            // 主キーの列名を変数に格納する。
            String[] keyColNames = {"message_id"};
            // DBとの接続状態を取得する。
            connection = getConnection();
            // DBにINSERT文を送信するために変数sqlと主キーの列名を格納した変数をPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql, keyColNames);
            // INSERT文を実行する。
            ps.executeUpdate();
            // 実行結果から自動採番された申込IDを取得して変数に格納する。
            ResultSet rs = ps.getGeneratedKeys();

            // メッセージIDを格納するための変数を生成する。
            int messageId = 0;
            while(rs.next()) {
                // 取得したメッセージIDを変数に格納する。
                messageId = rs.getInt(1);
               break;
            }

            // メッセージIDを戻す。
            return messageId;
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
