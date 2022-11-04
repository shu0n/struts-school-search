package dao.school;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import dao.ConnectorDAO;
import exception.ReferredByEntryException;

public class DeleteSchoolDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * スクールテーブルから該当レコードを論理削除するためのメソッド
     * @param schoolId スクールID
     * @return 実行結果
     * @throws ReferredByEntryException
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean deleteSchoolLogically(int schoolId)
            throws ReferredByEntryException, ClassNotFoundException, IOException, SQLException {
        try {
            if(isReferredByEntry(schoolId)) {
                // 当該スクールIDが申込から参照されている場合は申込参照例外を投げる。
                throw new ReferredByEntryException("申込があります。");
            }

            // テーブルを指定するためのUPDATE文を生成する。
            String tableSql = "UPDATE schools SET ";

            // 更新する列名とデータを指定するためのStringBuilderを呼び出す。
            StringBuilder dataSb = new StringBuilder();

            // スクール公開可否
            dataSb.append("school_release_propriety='0'");

            // スクール申込可否
            dataSb.append(", school_entry_propriety='0'");

            // スクール削除フラグ
            dataSb.append(", school_delete_flag='1'");

            // 現在日時(文字列型)を取得する。
            String now = getStringCurrentTimestamp();
            // スクール更新日時
            dataSb.append(
                    ", school_updated_at=TO_TIMESTAMP('"
                    + now
                    + "', 'YYYY-MM-DD HH24:MI:SS.FF')");
            // スクール削除日時
            dataSb.append(
                    ", school_deleted_at=TO_TIMESTAMP('"
                    + now
                    + "', 'YYYY-MM-DD HH24:MI:SS.FF') ");

            // 更新するレコードを指定するためのWHERE文を生成する。
            String whereSql = "WHERE school_id=" + schoolId;

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
     * 特定のスクールIDが申込から参照されているかを判定するためのメソッド
     * @param schoolId スクールID
     * @return 判定結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    private boolean isReferredByEntry(int schoolId)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // SELECT文を生成する。
            String sql
                    = "SELECT entry_id FROM entries "
                    + "WHERE entry_delete_flag='0' "
                    + "AND entry_school_id=" + schoolId;

            // DBとの接続状態を取得する。
            connection = getConnection();
            // SELECT文をPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // SELECT文の実行結果を変数に格納する。
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                // レコードが存在する場合はtrueを戻す。
                return true;
            }

            // レコードが存在しない場合はfalseを戻す。
            return false;
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
