package dao.account;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import dao.ConnectorDAO;
import exception.ReferredBySchoolException;

public class DeleteAccountDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * アカウントテーブルから特定のアカウントIDに紐づくレコードを論理削除するためのメソッド
     * @param account アカウントID
     * @return 実行結果
     * @throws ReferredBySchoolException
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean deleteAccountLogically(int accountId)
            throws ReferredBySchoolException, ClassNotFoundException, IOException, SQLException {
        try {
            // 当該アカウントIDがスクールから参照されているかを判定する。
            if(isReferredBySchool(accountId)) {
                // 参照されている場合はスクール参照例外を投げる
                throw new ReferredBySchoolException("スクールの登録者アカウントに登録されています。");
            }

            // テーブルを指定するUPDATE文を生成する。
            String tableSql = "UPDATE accounts SET ";

            // 更新する列名とデータを指定するためのStringBuilderを呼び出す。
            StringBuilder dataSb = new StringBuilder();

            // 同一メールアドレスでアカウントの再作成を可能とするためにメールアドレスの末尾に"_"を付与する。
            dataSb.append("mail_address='" + getMailAddress(accountId) + "_'");

            // アカウントステータス
            dataSb.append(", account_status='0'");

            // アカウント削除フラグ
            dataSb.append(", account_delete_flag='1'");

            // 現在日時(文字列型)を取得する。
            String now = getStringCurrentTimestamp();
            // アカウント更新日時
            dataSb.append(
                    ", account_updated_at=TO_TIMESTAMP('"
                    + now
                    + "', 'YYYY-MM-DD HH24:MI:SS.FF3')");
            // アカウント削除日時
            dataSb.append(
                    ", account_deleted_at=TO_TIMESTAMP('"
                    + now
                    + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");

            // レコードを指定するためのWHERE文を生成する。
            String whereSql = "WHERE account_id=" + accountId;

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
     * 指定したアカウントIDがスクールから参照されているかを判定するためのメソッド
     * @param accountId アカウントID
     * @return 判定結果
     * @throws ClassNotFoundExceptio
     * @throws IOException
     * @throws SQLException
     */
    private boolean isReferredBySchool(int accountId)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // SELECT文を生成する。
            String sql
                    = "SELECT school_id FROM schools "
                    + "WHERE school_delete_flag='0' "
                    + "AND registrant_account_id=" + accountId;

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
     * アカウントIDに紐づくメールアドレスを取得するためのメソッド
     * @param accountId アカウントID
     * @return アカウントIDに紐づくメールアドレス
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    private String getMailAddress(int accountId)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // SELECT文を生成する。
            String sql
                    = "SELECT mail_address FROM accounts "
                    + "WHERE account_delete_flag='0' "
                    + "AND account_id=" + accountId;

            // DBとの接続状態を取得する。
            connection = getConnection();
            // SELECT文をPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // SELECT文の実行結果を変数に格納する。
            ResultSet rs = ps.executeQuery();

            // メールアドレスを格納する変数を生成する。
            String mailAddress = "";
            while(rs.next()) {
                // 取得したメールアドレスを変数に格納する。
                mailAddress = rs.getString("mail_address");
                break;
            }

            // 変数を戻す。
            return mailAddress;
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
