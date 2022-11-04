package dao.message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import dao.ConnectorDAO;
import dao.message.sql.MessageUpdateDataActionForm;

public class UpdateMessageDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * 指定したメッセージIDに紐づくメッセージテーブルのレコードを更新するためのメソッド
     * @param updateDataForm 更新するデータを格納したアクションフォーム
     * @return 実行結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean updateMessage(MessageUpdateDataActionForm updateDataForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルを指定するためのUPDATE文を生成する。
            String tableSql = "UPDATE messages SET ";

            // 更新する列名とデータを指定するためのStringBuilderを呼び出す。
            StringBuilder dataSb = new StringBuilder();

            // 開封済フラグ
            String openedFlag = updateDataForm.getOpenedFlag();
            if(openedFlag != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", opened_flag='" + openedFlag + "'");
            }

            // 開封日時
            Timestamp openedAt = updateDataForm.getOpenedAt();
            if(openedAt != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", opened_at=TO_TIMESTAMP('" + openedAt.toString() + "', 'YYYY-MM-DD HH24:MI:SS.FF')");
            }

            // 先頭の", "を削除する。
            dataSb.delete(0,2);

            // 更新するレコードを指定するためのWHERE文を生成する。
            String whereSql = "WHERE message_id=" + updateDataForm.getMessageId();

            // StringBuilderで生成した文を結合してUPDATE全文を生成する。
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
     * DBとの接続状態を取得するためのメソッド
     */
    Connection getConnection() throws ClassNotFoundException, IOException, SQLException {
        // DBとの接続状態を戻す。
        return new ConnectorDAO().connectDatabase();
    }

}
