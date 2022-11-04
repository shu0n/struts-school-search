package dao.account;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import dao.ConnectorDAO;
import dao.account.sql.AccountUpdateDataActionForm;

public class UpdateAccountDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * 特定のアカウントIDに紐づくアカウントテーブルのレコードを更新するためのメソッド
     * @param updateDataForm 更新するデータを格納したアクションフォーム
     * @return 実行結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean updateAccount(AccountUpdateDataActionForm updateDataForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルを指定するためのUPDATE文を生成する。
            String tableSql = "UPDATE accounts SET ";

            // 更新する列名とデータを指定するためのStringBuilderを呼び出す。
            StringBuilder dataSb = new StringBuilder();

            // 姓
            String lastName = updateDataForm.getLastName();
            if(lastName != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", last_name='" + lastName +"'");
            }

            // 名
            String firstName = updateDataForm.getFirstName();
            if(firstName != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", first_name='" + firstName + "'");
            }

            // 姓(フリガナ)
            String lastNameKana = updateDataForm.getLastNameKana();
            if(lastNameKana != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", last_name_kana='" + lastNameKana + "'");
            }

            // 名(フリガナ)
            String firstNameKana = updateDataForm.getFirstNameKana();
            if(firstNameKana != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", first_name_kana='" + firstNameKana + "'");
            }

            // 性別
            int sexId = updateDataForm.getSexId();
            if(sexId != 0) {
                // 0以外の場合は追加する。
                dataSb.append(", sex_id=" + sexId);
            }

            // 生年月日
            Timestamp birthDate = updateDataForm.getBirthDate();
            if(birthDate != null) {
                // NULL以外の場合は値に日時を設定する。
                dataSb.append(
                        ", birth_date=TO_TIMESTAMP('"
                        + birthDate.toString()
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3')");
            } else if(updateDataForm.isBirthDateToNullFlag()) {
                // 生年月日NULL設定フラグがtrueの場合は値にNULLを設定する。
                dataSb.append(", birth_date=null");
            }

            // 都道府県ID
            int prefectureId = updateDataForm.getPrefectureId();
            if(prefectureId != 0) {
                // 0以外の場合は追加する。
                dataSb.append(", prefecture_id=" + prefectureId);
            }

            // メールアドレス
            String mailAddress = updateDataForm.getMailAddress();
            if(mailAddress != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", mail_address='" + mailAddress + "'");
            }

            // 自己紹介
            String selfIntroduction = updateDataForm.getSelfIntroduction();
            if(selfIntroduction != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", self_introduction='" + selfIntroduction + "'");
            }

            // プロフィール画像ファイル名
            String profileImageFileName = updateDataForm.getProfileImageFileName();
            if(profileImageFileName != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", profile_image_file_name='" + profileImageFileName + "'");
            }

            // プロフィール画像ファイル名
            String password = updateDataForm.getPassword();
            if(password != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", password='" + password + "'");
            }

            // アカウントステータス
            String accountStatus = updateDataForm.getAccountStatus();
            if(accountStatus != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", account_status='" + accountStatus + "'");
            }

            // 有効化トークン
            String activateToken = updateDataForm.getActivateToken();
            if(activateToken != null) {
                // NULL以外の場合は値に有効化トークンを追加する。
                dataSb.append(", activate_token='" + activateToken + "'");
            } else if(updateDataForm.isActivateTokenToToNullFlag()) {
                // 有効化トークンNULL設定フラグがtrueの場合は値にnullを設定する。
                dataSb.append(", activate_token=null");
            }

            // 有効化トークン有効期限
            Timestamp activateEffectiveDate = updateDataForm.getActivateEffectiveDate();
            if(activateEffectiveDate != null) {
                // NULL以外の場合は値に日時を設定する。
                dataSb.append(
                        ", activate_effective_date=TO_TIMESTAMP('"
                        + activateEffectiveDate.toString()
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3')");
            } else if(updateDataForm.isActivateEffectiveDateToNullFlag()) {
                // 有効化トークン有効期限NULL設定フラグがtrueの場合は値にnullを設定する。
                dataSb.append(", activate_effective_date=null");
            }

            // 再発行トークンおよび再発行トークン有効期限
            String reissueToken = updateDataForm.getReissueToken();
            if(reissueToken != null) {
                // NULL以外の場合は値に再発行トークンを追加する。
                dataSb.append(", reissue_token='" + reissueToken + "'");
            } else if(updateDataForm.isReissueTokenToNullFlag()) {
                // 再発行トークンNULL設定フラグがtrueの場合は値にnullを設定する。
                dataSb.append(", reissue_token=null");
            }

            Timestamp reissueEffectiveDate = updateDataForm.getReissueEffctiveDate();
            if(reissueEffectiveDate != null) {
                // NULL以外の場合は値に日時を設定する。
                dataSb.append(
                        ", reissue_effective_date=TO_TIMESTAMP('"
                        + reissueEffectiveDate.toString()
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3')");
            } else if(updateDataForm.isReissueEffctiveDateToNULLFlag()) {
                // 再発行トークン有効期限NULL設定フラグがtrueの場合は値にnullを設定する。
                dataSb.append(", reissue_effective_date=null");
            }

            // 先頭の", "を削除する。
            dataSb.delete(0,2);

            // 現在日時(文字列型)を取得する。
            String now = getStringCurrentTimestamp();
            // アカウント更新日時
            dataSb.append(
                    ", account_updated_at=TO_TIMESTAMP('"
                    + now
                    + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");

            // 更新するレコードを指定するためのWHERE文を生成する。
            String whereSql = "WHERE account_id=" + updateDataForm.getAccountId();

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
