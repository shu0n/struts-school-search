package dao.contact;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.ConnectorDAO;
import dao.contact.sql.ContactInsertDataActionForm;

public class InsertContactDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * お問合せテーブルにレコードを作成するためのメソッド
     * @param insertDataForm 登録するデータを格納したアクションフォーム
     * @return お問合せID
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public int insertContact(ContactInsertDataActionForm insertDataForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルを指定するためのINSERT文を生成する。
            String tableSql = "INSERT INTO contacts (";

            // 列名を追加するためのStringBuilderを呼び出す。
            StringBuilder columnSb = new StringBuilder();
            // 各列名を一度リスト変数に格納して繰り返し処理で追加する。
            List<String> columnList = new ArrayList<>();
            columnList.addAll(Arrays.asList(
                    "contact_last_name", // お問合せ者姓
                    "contact_first_name", // お問合せ者名
                    "contact_last_name_kana", // お問合せ者姓(フリガナ)
                    "contact_first_name_kana", // お問合せ者名(フリガナ)
                    "contact_mail_address", // お問合せ者メールアドレス
                    "contact_content" // お問合せ内容
                    ));
            for(String value : columnList) {
                columnSb.append(", " + value);
            }
            columnSb.delete(0,2);

            // データを追加するためのStringBuilderを呼び出す。
            StringBuilder dataSb = new StringBuilder();
            // 各データを一度リスト変数に格納して繰り返し処理で追加する。
            List<String> dataList = new ArrayList<>();
            dataList.addAll(Arrays.asList(
                    insertDataForm.getContactLastName(), // お問合せ者姓
                    insertDataForm.getContactFirstName(), // お問合せ者名
                    insertDataForm.getContactLastNameKana(), // お問合せ者姓(フリガナ)
                    insertDataForm.getContactFirstNameKana(), // お問合せ者名(フリガナ)
                    insertDataForm.getContactMailAddress(), // お問合せ者メールアドレス
                    insertDataForm.getContactContent() // お問合せ内容
                    ));
            for(String value : dataList) {
                dataSb.append(", '" + value + "'");
            }
            dataSb.delete(0,2);

            // お問合せステータスID
            columnSb.append(", contact_status_id");
            dataSb.append(", " + insertDataForm.getContactStatusId());

            // NULL可またはデフォルト値がある項目は入力値によって列名とデータを追加する。
            // お問合せ者アカウントID
            int contactAccountId = insertDataForm.getContactAccountId();
            if(contactAccountId != 0) {
                // 0以外の場合は列名とデータを追加する。
                columnSb.append(", contact_account_id");
                dataSb.append(", " + contactAccountId);
            }

            columnSb.append(") VALUES(");
            dataSb.append(")");

            // StringBuilderで生成した文をString型に変換して結合してINSERT全文を生成する。
            String sql =  tableSql + columnSb.toString() + dataSb.toString();

            // 主キーの列名を変数に格納する。
            String[] keyColNames = {"contact_id"};
            // DBとの接続状態を取得する。
            connection = getConnection();
            // DBにINSERT文を送信するために変数sqlと主キーの列名を格納した変数をPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql, keyColNames);
            // INSERT文を実行する。
            ps.executeUpdate();
            // 実行結果から自動採番される申込IDを取得して変数に格納する。
            ResultSet rs = ps.getGeneratedKeys();

            // お問合せIDを格納するための変数を生成する。
            int contactId = 0;
            while(rs.next()) {
                // 取得したメッセージIDを変数に格納する。
                contactId = rs.getInt(1);
                break;
            }

            // お問合せIDを戻す。
            return contactId;
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
