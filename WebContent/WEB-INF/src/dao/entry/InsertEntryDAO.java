package dao.entry;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.ConnectorDAO;
import dao.entry.sql.EntryInsertDataActionForm;

public class InsertEntryDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * 申込テーブルにレコードを作成するためのメソッド
     * @param insertDataForm 登録するデータを格納したアクションフォーム
     * @return 発番された申込ID
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public int insertEntry(EntryInsertDataActionForm insertDataForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルを指定するためのINSERT文を生成する。
            String tableSql = "INSERT INTO entries (";

            // 列名を追加するためのStringBuilderを呼び出す。
            StringBuilder columnSb = new StringBuilder();
            // 各列名を一度リスト変数に格納して繰り返し処理で追加する。
            List<String> columnList = new ArrayList<>();
            columnList.addAll(Arrays.asList(
                    "entry_school_id", // 申込先スクールID
                    "applicant_account_id", // 申込者アカウントID
                    "entry_status_id" // 申込ステータスID
                    ));
            for(String value : columnList) {
                columnSb.append(", " + value);
            }
            columnSb.delete(0,2);

            // データを追加するためのStringBuilderを呼び出す。
            StringBuilder dataSb = new StringBuilder();
            // 各データを一度リスト変数に格納して繰り返し処理で追加する。
            List<Integer> dataList = new ArrayList<>();
            dataList.addAll(Arrays.asList(
                    insertDataForm.getEntrySchoolId(), // 申込先スクールID
                    insertDataForm.getApplicantAccountId(), // 申込者アカウントID
                    insertDataForm.getEntryStatusId() // 申込ステータスID
                    ));
            for(int value : dataList) {
                dataSb.append(", " + value);
            }
            dataSb.delete(0,2);

            // NULL可またはデフォルト値がある項目は入力値によって列名とデータを追加する。
            // 質問
            String entryQuestion = insertDataForm.getEntryQuestion();
            if(entryQuestion != null) {
                // NULLの場合は追加する。
                columnSb.append(", entry_question");
                dataSb.append(", '" + entryQuestion + "'");
            }

            columnSb.append(") VALUES(");
            dataSb.append(")");

            // StringBuilderで生成した文をString型に変換して結合してINSERT全文を生成する。
            String sql =  tableSql + columnSb.toString() + dataSb.toString();

            // 主キーの列名を変数に格納する。
            String[] keyColNames = {"entry_id"};
            // DBとの接続状態を取得する。
            connection = getConnection();
            // DBにINSERT文を送信するために変数sqlと主キーの列名を格納した変数をPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql, keyColNames);
            // INSERT文を実行する。
            ps.executeUpdate();
            // 実行結果から自動採番された申込IDを取得して変数に格納する。
            ResultSet rs = ps.getGeneratedKeys();

            // 申込IDを格納するための変数を生成する。
            int entryId = 0;
            while(rs.next()) {
                // 取得した申込IDを変数に格納する。
                entryId = rs.getInt(1);
               break;
            }

            // 申込IDを戻す。
            return entryId;
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
