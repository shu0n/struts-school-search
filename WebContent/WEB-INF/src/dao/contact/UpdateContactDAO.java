package dao.contact;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import dao.ConnectorDAO;
import dao.contact.sql.ContactUpdateDataActionForm;

public class UpdateContactDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * 特定のお問合せIDに紐づくお問合せテーブルのレコードを更新するためのメソッド
     * @param updateDataForm 更新するデータを格納したアクションフォーム
     * @return 実行結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean updateContact(ContactUpdateDataActionForm updateDataForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルを指定するためのUPDATE文を生成する。
            String tableSql = "UPDATE contacts SET ";

            // 更新する列名とデータを指定するためのStringBuilderを呼び出す。
            StringBuilder dataSb = new StringBuilder();

            // お問合せ者アカウントID
            int contactAccountId = updateDataForm.getContactAccountId();
            if(contactAccountId != 0) {
                // 0以外の場合は追加する。
                dataSb.append(", contact_account_id=" + contactAccountId);
            }

            // お問合せ者姓
            String contactLastName = updateDataForm.getContactLastName();
            if(contactLastName != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", contact_last_name='" + contactLastName + "'");
            }

            // お問合せ者名
            String contactFirstName = updateDataForm.getContactFirstName();
            if(contactFirstName != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", contact_first_name='" + contactFirstName + "'");
            }

            // お問合せ者姓(フリガナ)
            String contactLastNameKana = updateDataForm.getContactLastNameKana();
            if(contactLastNameKana != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", contact_last_name_kana='" + contactLastNameKana + "'");
            }

            // お問合せ者名(フリガナ)
            String contactFirstNameKana = updateDataForm.getContactFirstNameKana();
            if(contactFirstNameKana != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", contact_first_name_kana='" + contactFirstNameKana + "'");
            }

            // お問合せ者メールアドレス
            String contactMailAddress = updateDataForm.getContactMailAddress();
            if(contactMailAddress != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", contact_mail_address='" + contactMailAddress + "'");
            }

            // お問合せ内容
            String contactContent = updateDataForm.getContactContent();
            if(contactContent != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", contact_content='" + contactContent + "'");
            }

            // お問合せステータスID
            int contactStatusId = updateDataForm.getContactStatusId();
            if(contactStatusId != 0) {
                // 0以外の場合は追加する。
                dataSb.append(", contact_status_id=" + contactStatusId);
            }

            // 先頭の", "を削除する。
            dataSb.delete(0,2);

            // 現在日時(文字列型)をする。
            String now = getStringCurrentTimestamp();
            // お問合せ更新日時
            dataSb.append(
                    ", contact_updated_at=TO_TIMESTAMP('"
                    + now
                    + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");

            // 更新するレコードを指定するためのWHERE文を生成する。
            String whereSql = "WHERE contact_id=" + updateDataForm.getContactId();

            // StringBuilderで生成した文を結合してUPDATE全文を生成する。
            String sql = tableSql + dataSb.toString() + whereSql;

            // DBとの接続状態を取得する。
            connection = getConnection();
            // DBにUPDATE文を送信するために変数sqlをPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // UPDATE文の実行結果を変数に格納する。
            int rs = ps.executeUpdate();

            if(rs == 0) {
                // 更新されたレコードが0件の場合はtfalseを戻す。
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
