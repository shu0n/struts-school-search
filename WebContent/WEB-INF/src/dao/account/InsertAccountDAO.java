package dao.account;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.ConnectorDAO;
import dao.account.sql.AccountInsertDataActionForm;

public class InsertAccountDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * アカウントテーブルにレコードを作成するためのメソッド
     * @param insertDataForm 登録するデータを格納したアクションフォーム
     * @return 発番されたアカウントID
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public int insertAccount(AccountInsertDataActionForm insertDataForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルを指定するためのINSERT文を生成する。
            String tableSql = "INSERT INTO accounts (";

            // 列名を追加するためのStringBuilderを呼び出す。
            StringBuilder columnSb = new StringBuilder();
            // 各列名を一度リスト変数に格納して繰り返し処理で追加する。
            List<String> columnList = new ArrayList<>();
            columnList.addAll(Arrays.asList(
                    "last_name", // 姓
                    "first_name", // 名
                    "last_name_kana", // 姓(フリガナ)
                    "first_name_kana", // 名(フリガナ)
                    "mail_address", // メールアドレス
                    "password" // パスワード
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
                    insertDataForm.getLastName(), // 姓
                    insertDataForm.getFirstName(), // 名
                    insertDataForm.getLastNameKana(), // 姓(フリガナ)
                    insertDataForm.getFirstNameKana(), // 名(フリガナ)
                    insertDataForm.getMailAddress(), // メールアドレス
                    insertDataForm.getPassword() // パスワード
                    ));
            for(String value : dataList) {
                dataSb.append(", '" + value + "'");
            }
            dataSb.delete(0,2);

            // 都道府県IDはint型のため単独で追加する。
            columnSb.append(", prefecture_id");
            dataSb.append(", " + insertDataForm.getPrefectureId());

            // NULL可またはデフォルト値がある項目は入力値によって列名とデータを追加する。
            // 性別
            int sexId = insertDataForm.getSexId(); // 性別ID
            if(sexId != 0) {
                // 0以外の場合は追加する。
                columnSb.append(", sex_id");
                dataSb.append(", " + sexId);
            }

            // 生年月日
            Timestamp birthDate = insertDataForm.getBirthDate();
            if(birthDate != null) {
                // NULL以外の場合は追加する。
                columnSb.append(", birth_date");
                dataSb.append(
                        ", TO_TIMESTAMP('"
                        + birthDate.toString()
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF')");
            }

            // 自己紹介
            String selfIntroduction = insertDataForm.getSelfIntroduction();
            if(selfIntroduction != null) {
                // NULL以外の場合は追加する。
                columnSb.append(", self_introduction");
                dataSb.append(", '" + selfIntroduction + "'");
            }

            // プロフィール画像(ファイル名)
            String profileImageFileName = insertDataForm.getProfileImageFileName();
            if(profileImageFileName != null) {
                // NULL以外の場合は追加する。
                columnSb.append(", profile_image_file_name");
                dataSb.append(", '" + profileImageFileName + "'");
            }

            // アカウントステータス
            String accountStatus = insertDataForm.getAccountStatus();
            if(accountStatus != null) {
                // NULL以外の場合は追加する。
                columnSb.append(", account_status");
                dataSb.append(", '" + accountStatus + "'");
            }

            // 有効化トークン
            String activateToken = insertDataForm.getActivateToken();
            if(activateToken != null) {
                // NULL以外の場合は追加する。
                columnSb.append(", activate_token");
                dataSb.append(", '" + activateToken + "'");
            }

            // 有効化有効期限
            Timestamp activateEffectiveDate = insertDataForm.getActivateEffectiveDate();
            if(activateEffectiveDate != null) {
                // NULL以外の場合は追加する。
                columnSb.append(", activate_effective_date");
                dataSb.append(
                        ", TO_TIMESTAMP('"
                        + activateEffectiveDate.toString()
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF')");
            }

            columnSb.append(") VALUES(");
            dataSb.append(")");

            // StringBuilderで生成した文をString型に変換して結合してINSERT全文を生成する。
            String sql = tableSql + columnSb.toString() + dataSb.toString();

            // 主キーの列名を変数に格納する。
            String[] keyColNames = {"account_id"};
            // DBとの接続状態を取得する。
            connection = getConnection();
            // DBにINSERT文を送信するために変数sqlと主キーの列名を格納した変数をPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql, keyColNames);
            // INSERT文を実行する。
            ps.executeUpdate();
            // 実行結果から自動採番されるアカウントIDを取得して変数に格納する。
            ResultSet rs = ps.getGeneratedKeys();

            // アカウントIDを格納するための変数を生成する。
            int accountId = 0;
            while(rs.next()) {
                // 取得したアカウントIDを変数に格納する。
                accountId = rs.getInt(1);
                break;
            }

            // アカウントIDを戻す。
            return accountId;
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
