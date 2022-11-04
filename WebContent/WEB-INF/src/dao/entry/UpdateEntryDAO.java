package dao.entry;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import dao.ConnectorDAO;
import dao.entry.sql.EntryUpdateDataActionForm;

public class UpdateEntryDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * 指定した申込IDに紐づく申込テーブルのレコードを更新するためのメソッド
     * @param updateDataForm 更新するデータを格納したアクションフォーム
     * @return 実行結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean updateEntry(EntryUpdateDataActionForm updateDataForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルを指定するためのUPDATE文を生成する。
            String tableSql = "UPDATE entries SET ";

            // 更新する列名とデータを指定するためのStringBuilderを呼び出す。
            StringBuilder dataSb = new StringBuilder();

            // 申込先スクールID
            int entrySchoolId = updateDataForm.getEntrySchoolId();
            if(entrySchoolId != 0) {
                // 0以外の場合は追加する。
                dataSb.append(", entry_school_id=" + entrySchoolId);
            }

            // 申込者アカウントID
            int applicantAccountId = updateDataForm.getApplicantAccountId();
            if(applicantAccountId != 0) {
                // 0以外の場合は追加する。
                dataSb.append(", applicant_account_id=" + applicantAccountId);
            }

            // 質問
            String entryQuestion = updateDataForm.getEntryQuestion();
            if(entryQuestion != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", entry_question='" + entryQuestion + "'");
            }

            // 申込ステータスID
            int entryStatusId = updateDataForm.getEntryStatusId();
            if(entryStatusId != 0) {
                // 0以外の場合は追加する。
                dataSb.append(", entry_status_id=" + entryStatusId);
            }

            // 先頭の", "を削除する。
            dataSb.delete(0,2);

            // 現在日時(文字列型)を取得する。
            String now = getStringCurrentTimestamp();
            // お問合せ更新日時
            dataSb.append(
                    ", entry_updated_at=TO_TIMESTAMP('"
                    + now
                    + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");

            // 更新するレコードを指定するためのWHERE文を生成する。
            String whereSql = "WHERE entry_id=" + updateDataForm.getEntryId();

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
