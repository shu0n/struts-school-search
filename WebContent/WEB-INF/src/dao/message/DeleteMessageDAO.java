package dao.message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import dao.ConnectorDAO;

public class DeleteMessageDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * 受取アカウントでメッセージテーブルから該当レコードを論理削除するためのメソッド
     * @param messageId メッセージID
     * @return 実行結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean deleteReceivedMessageLogically(int messageId)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルを指定するためのUPDATE文を生成する。
            String tableSql = "UPDATE messages SET ";

            // 更新する列名とデータを指定するためのStringBuilderを呼び出す。
            StringBuilder dataSb = new StringBuilder();

            // 受取アカウント側削除フラグ
            dataSb.append("recipient_delete_flag='1'");

            // 現在日時(文字列型)を取得する。
            String now = getStringCurrentTimestamp();
            // 受取アカウント側削除フラグ更新日時
            dataSb.append(
                    ", recipient_flag_updated_at=TO_TIMESTAMP('"
                    + now
                    + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");

            // 更新するレコードを指定するためのWHERE文を生成する。
            String whereSql = "WHERE message_id=" + messageId;

            // StringBuilderで生成した文をString型に変換して結合してUPDATE全文を生成する。
            String sql = tableSql + dataSb.toString() + whereSql;

            // DBとの接続状態を取得する。
            connection = getConnection();
            // DBにUPDATE文を送信するために変数sqlをPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // UPDATE文の実行結果を変数に格納する。
            int rs = ps.executeUpdate();

            if(rs == 0) {
                // 更新されたレコードが0件の場合はfalseを戻す。
                return false;
            } else {
                // 上記以外の場合はtrueを戻す。
                return true;
            }
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
     * 差出アカウントでメッセージテーブルから該当レコードを論理削除するためのメソッド
     * @param messageId メッセージID
     * @return 実行結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean deleteSendedMessageLogically(int messageId)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルを指定するためのUPDATE文を生成する。
            String tableSql = "UPDATE messages SET ";

            // 更新する列名とデータを指定するためのStringBuilderを呼び出す。
            StringBuilder dataSb = new StringBuilder();

            // 差出アカウント側削除フラグ
            dataSb.append("sender_delete_flag='1'");

            // 現在日時(文字列型)を取得する。
            String now = getStringCurrentTimestamp();
            // 差出アカウント側削除フラグ更新日時
            dataSb.append(
                    ", sender_flag_updated_at=TO_TIMESTAMP('"
                    + now
                    + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");

            // 更新するレコードを指定するためのWHERE文を生成する。
            String whereSql = "WHERE message_id=" + messageId;

            // StringBuilderで生成した文をString型に変換して結合してUPDATE全文を生成する。
            String sql = tableSql + dataSb.toString() + whereSql;

            // DBとの接続状態を取得する。
            connection = getConnection();
            // DBにUPDATE文を送信するために変数sqlをPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // UPDATE文の実行結果を変数に格納する。
            int rs = ps.executeUpdate();

            if(rs <= 0) {
                // 作成された行数が0件以下の場合はfalseを戻す。
                return false;
            } else {
                // 上記以外の場合はtrueを戻す。
                return true;
            }
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
     * 現在日時(文字列型)を取得するためのメソッド
     */
    String getStringCurrentTimestamp() {
        // 現在日時(文字列型)を戻す。
        return new Timestamp(System.currentTimeMillis()).toString();
    }

    /**
     * DBとの接続状態を取得するためのメソッド
     */
    Connection getConnection() throws ClassNotFoundException, IOException, SQLException {
        // DBとの接続状態を戻す。
        return new ConnectorDAO().connectDatabase();
    }

}
