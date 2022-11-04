package dao.account;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import actionform.AccountExtendActionForm;
import dao.ConnectorDAO;
import dao.account.sql.AccountSelectJoinWhereActionForm;

public class SelectAccountJoinDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * アカウントテーブルを都道府県テーブル、性別テーブルと外部結合して指定した検索条件に一致するレコードを取得するためのメソッド
     * @param whereForm 検索条件を指定するデータを格納したアクションフォーム
     * @return 検索条件に一致したアカウントのアクションフォームをアカウントIDの昇順で格納したリスト
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<AccountExtendActionForm> selectMatchedAccount(AccountSelectJoinWhereActionForm whereForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // 外部結合するためのSELECT文を生成する。
            String joinSql
                    = "SELECT * FROM"
                    + "((accounts LEFT OUTER JOIN prefectures ON accounts.prefecture_id=prefectures.prefecture_id) "
                    + "LEFT OUTER JOIN sexes ON accounts.sex_id=sexes.sex_id) ";

            // 検索条件を指定するためのStringBuilderを呼び出す。
            StringBuilder whereSb = new StringBuilder();
            whereSb.append("WHERE account_delete_flag='0' ");

            // アカウントIDおよびアカウントID配列
            int accountId = whereForm.getAccountId();
            String[] accountIdArray = whereForm.getAccountIdArray();
            if(accountId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND account_id=" + accountId + " ");
            } else if(!StringUtils.isAllEmpty(accountIdArray)) {
                // アカウントID配列がNULLまたは空要素以外の場合

                if(accountIdArray.length == 1) {
                    // 要総数が1の場合は検索条件(AND)に追加する。
                    whereSb.append("AND account_id=" + accountIdArray[0] + " ");
                } else {
                    // 上記以外の場合

                    // 格納されている全てのアカウントIDを検索条件(AND(OR))に追加する。
                    whereSb.append("AND (");
                    // 検索条件に追加するためのStringBuilderを生成する。
                    StringBuilder accountIdSb = new StringBuilder();
                    for(int i = 0; i < accountIdArray.length; i++) {
                        // 格納されているアカウントIDを検索条件に追加する。
                        accountIdSb.append("OR account_id=" + accountIdArray[i] + " ");
                    }
                    // 先頭3文字を削除する
                    accountIdSb.delete(0,3);
                    // 検索条件を指定するためのStringBuilderに追加する。
                    whereSb.append(accountIdSb.toString() + ") ");
                }
            }

            // 姓および姓(類似)
            String lastName = whereForm.getLastName();
            String likeLastName = whereForm.getLikeLastName();
            if(!StringUtils.isEmpty(lastName)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND last_name='" + lastName + "' ");
            } else if(!StringUtils.isEmpty(likeLastName)) {
                // NULLまたは空文字以外の場合は検索条件(LIKE)に追加する。
                whereSb.append("AND last_name LIKE '%" + likeLastName + "%' ");
            }

            // 名および名(類似)
            String firstName = whereForm.getFirstName();
            String likeFirstName = whereForm.getLikeFirstName();
            if(!StringUtils.isEmpty(firstName)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND first_name='" + firstName + "' ");
            } else if(!StringUtils.isEmpty(likeFirstName)) {
                // NULLまたは空文字以外の場合は検索条件(LIKE)に追加する。
                whereSb.append("AND first_name LIKE '%" + likeFirstName + "%' ");
            }

            // 姓(フリガナ)および姓(フリガナ、類似)
            String lastNameKana = whereForm.getLastNameKana();
            String likeLastNameKana = whereForm.getLikeLastNameKana();
            if(!StringUtils.isEmpty(lastNameKana)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND last_name_kana='" + lastNameKana + "' ");
            } else if(!StringUtils.isEmpty(likeLastNameKana)) {
                // NULLまたは空文字以外の場合は検索条件(LIKE)に追加する。
                whereSb.append("AND last_name_kana LIKE '%" + likeLastNameKana + "%' ");
            }

            // 名(フリガナ)および名(フリガナ、類似)
            String firstNameKana = whereForm.getFirstNameKana();
            String likeFirstNameKana = whereForm.getLikeFirstNameKana();
            if(!StringUtils.isEmpty(firstNameKana)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND first_name_kana='" + firstNameKana + "' ");
            } else if(!StringUtils.isEmpty(likeFirstNameKana)) {
                // NULLまたは空文字以外の場合は検索条件(LIKE)に追加する。
                whereSb.append("AND first_name_kana LIKE '%" + likeFirstNameKana + "%' ");
            }

            // 性別IDおよび性別ID配列
            int sexId = whereForm.getSexId();
            String[] sexIdArray = whereForm.getSexIdArray();
            if(sexId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND accounts.sex_id=" + sexId + " ");
            } else if(!StringUtils.isAllEmpty(sexIdArray)) {
                // 性別ID配列がNULLまたは空要素以外の場合

                if(sexIdArray.length == 1) {
                    // 要総数が1の場合は検索条件(AND)に追加する。
                    whereSb.append("AND accounts.sex_id=" + sexIdArray[0] + " ");
                } else {
                    // 上記以外の場合

                    // 格納されている全ての性別IDを検索条件(AND(OR))に追加する。
                    whereSb.append("AND (");
                    // 検索条件に追加するためのStringBuilderを生成する。
                    StringBuilder sexIdSb = new StringBuilder();
                    for(int i = 0; i < sexIdArray.length; i++) {
                        // 格納されている性別IDを検索条件に追加する。
                        sexIdSb.append("OR accounts.sex_id=" + sexIdArray[i] + " ");
                    }
                    // 先頭3文字を削除する
                    sexIdSb.delete(0,3);
                    // 検索条件を指定するためのStringBuilderに追加する。
                    whereSb.append(sexIdSb.toString() + ") ");
                }
            }

            // 生年月日
            Timestamp birthdate = whereForm.getBirthDate();
            if(birthdate != null) {
                // 生年月日がNULL以外の場合は検索条件(AND)に追加する。
                whereSb.append(
                        "AND birth_date=TO_TIMESTAMP('"
                        + birthdate.toString()
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");
            } else {
                // 上記以外の場合

                // 生年月日(From)
                String fromBirthDate = whereForm.getFromBirthDate();
                if(!StringUtils.isEmpty(fromBirthDate)) {
                    // 生年月日(From)がNULLまたは空文字以外の場合は検索条件(AND)に追加する。
                    whereSb.append(
                            "AND birth_date>=TO_TIMESTAMP('"
                            + fromBirthDate
                            + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");
                }

                // 生年月日(To)
                String toBirthDate = whereForm.getToBirthDate();
                if(!StringUtils.isEmpty(toBirthDate)) {
                    // 生年月日(To)がNULLまたは空文字以外の場合は検索条件(AND)に追加する。
                    whereSb.append(
                            "AND birth_date<=TO_TIMESTAMP('"
                            + toBirthDate
                            + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");
                }
            }

            // 都道府県IDおよび都道府県ID配列
            int prefectureId = whereForm.getPrefectureId();
            String[] prefectureIdArray = whereForm.getPrefectureIdArray();
            if(prefectureId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND accounts.prefecture_id=" + prefectureId + " ");
            } else if(!StringUtils.isAllEmpty(prefectureIdArray)) {
                // 都道府県IDリストがNULLまたは空要素以外の場合

                if(prefectureIdArray.length == 1) {
                    // 要総数が1の場合は検索条件(AND)に追加する。
                    whereSb.append("AND accounts.prefecture_id=" + prefectureIdArray[0] + " ");
                } else {
                    // 上記以外の場合

                    // 格納されている全ての都道府県IDを検索条件(AND(OR))に追加する。
                    whereSb.append("AND (");
                    // 検索条件に追加するためのStringBuilderを生成する。
                    StringBuilder prefectureIdSb = new StringBuilder();
                    for(int i = 0; i < prefectureIdArray.length; i++) {
                        // 格納されている都道府県IDを検索条件に追加する。
                        prefectureIdSb.append("OR accounts.prefecture_id=" + prefectureIdArray[i] + " ");
                    }
                    // 先頭3文字を削除する
                    prefectureIdSb.delete(0,3);
                    // 検索条件を指定するためのStringBuilderに追加する。
                    whereSb.append(prefectureIdSb.toString() + ") ");
                }
            }

            // メールアドレスおよびメールアドレス(類似)
            String mailAddress = whereForm.getMailAddress();
            String likeMailAddress = whereForm.getLikeMailAddress();
            if(!StringUtils.isEmpty(mailAddress)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND mail_address='" + mailAddress + "' ");
            } else if(!StringUtils.isEmpty(likeMailAddress)) {
                // NULLまたは空文字以外の場合は検索条件(LIKE)に追加する。
                whereSb.append("AND mail_address LIKE '%" + likeMailAddress + "%' ");
            }

            // パスワード
            String password = whereForm.getPassword();
            if(!StringUtils.isEmpty(password)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND password='" + password + "' ");
            }

            // アカウントステータスおよびアカウントステータス配列
            String accountStatus = whereForm.getAccountStatus();
            String[] accountStatusArray = whereForm.getAccountStatusArray();
            if(!StringUtils.isEmpty(accountStatus)) {
                // NUULまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND account_status='" + accountStatus + "' ");
            } else if(!StringUtils.isAllEmpty(accountStatusArray)) {
                // アカウントステータス配列がNULLまたは空要素以外の場合は取得する。
                if(accountStatusArray.length == 1) {
                    // 要総数が1の場合は検索条件(AND)に追加する。
                    whereSb.append("AND account_status='" + accountStatusArray[0] + "' ");
                } else {
                    // 上記以外の場合

                    // 格納されている全てのアカウントステータスを検索条件(AND(OR))に追加する。
                    whereSb.append("AND (");
                    // 検索条件に追加するためのStringBuilderを生成する。
                    StringBuilder accountStatusSb = new StringBuilder();
                    for(int i = 0; i < accountStatusArray.length; i++) {
                        // 格納されているアカウントステータスを検索条件に追加する。
                        accountStatusSb.append("OR account_status='" + accountStatusArray[i] + "' ");
                    }
                    // 先頭3文字を削除する
                    accountStatusSb.delete(0,3);
                    // 検索条件を指定するためのStringBuilderに追加する。
                    whereSb.append(accountStatusSb.toString() + ") ");
                }
            }

            // 有効化トークン
            String activateToken = whereForm.getActivateToken();
            if(!StringUtils.isEmpty(activateToken)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND activate_token='" + activateToken + "' ");
            }

            // 再発行トークン
            String reissueToken = whereForm.getReissueToken();
            if(!StringUtils.isEmpty(reissueToken)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND reissue_token='" + reissueToken + "' ");
            }

            // アカウント作成日時(From)
            String fromAccountCreatedDate = whereForm.getFromAccountCreatedDate();
            // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
            if(!StringUtils.isEmpty(fromAccountCreatedDate)) {
                whereSb.append(
                        "AND account_created_at>=TO_TIMESTAMP("
                        + "'" + fromAccountCreatedDate
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");
            }

            // アカウント作成日時(To)
            String toAccountCreatedDate = whereForm.getToAccountCreatedDate();
            // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
            if(!StringUtils.isEmpty(toAccountCreatedDate)) {
                whereSb.append(
                        "AND account_created_at<=TO_TIMESTAMP("
                        + "'" + toAccountCreatedDate
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");
            }

            // アカウント更新日時(From)
            String fromAccountUpdatedDate = whereForm.getFromAccountUpdatedDate();
            // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
            if(!StringUtils.isEmpty(fromAccountUpdatedDate)) {
                whereSb.append(
                        "AND account_updated_at>=TO_TIMESTAMP('"
                        + fromAccountUpdatedDate
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");
            }

            // アカウント更新日時(To)
            String toAccountUpdatedDate = whereForm.getToAccountUpdatedDate();
            // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
            if(!StringUtils.isEmpty(toAccountUpdatedDate)) {
                whereSb.append(
                        "AND account_updated_at<=TO_TIMESTAMP('"
                        + toAccountUpdatedDate
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");
            }

            // StringBuilderで生成した文をString型に変換した文とアカウントIDの昇順を指定する文を結合してSELECT全文を生成する。
            String sql =  joinSql + whereSb.toString() + "ORDER BY account_id ASC";

            // DBとの接続状態を取得する。
            connection = getConnection();
            // SELECT文をPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // SELECT文の実行結果を変数に格納する。
            ResultSet rs = ps.executeQuery();

            // アクションフォームを格納するためのリストを生成する。
            List<AccountExtendActionForm> accountExtedFormList = new ArrayList<>();
            // 取得したレコードの件数分、処理を繰り返す。
            while(rs.next()) {
                // レコードから各列のデータを取得してアクションフォームに格納する。
                AccountExtendActionForm outForm = new AccountExtendActionForm();
                outForm.setAccountId(rs.getInt("account_id")); // アカウントID
                outForm.setLastName(rs.getString("last_name")); // 姓
                outForm.setFirstName(rs.getString("first_name")); // 名
                outForm.setLastNameKana(rs.getString("last_name_kana")); // 姓(フリガナ)
                outForm.setFirstNameKana(rs.getString("first_name_kana")); // 名(フリガナ)
                outForm.setSexId(rs.getInt("sex_id")); // 性別ID
                outForm.setSexName(rs.getString("sex_name")); // 性別名
                outForm.setBirthDate(rs.getTimestamp("birth_date")); // 生年月日
                outForm.setPrefectureId(rs.getInt("prefecture_id")); // 都道府県ID
                outForm.setPrefectureName(rs.getString("prefecture_name")); // 都道府県名
                outForm.setMailAddress(rs.getString("mail_address")); // メールアドレス
                outForm.setProfileImageFileName(rs.getString("profile_image_file_name")); // プロフィール画像ファイル名
                outForm.setSelfIntroduction(rs.getString("self_introduction")); // 自己紹介
                outForm.setAccountStatus(rs.getString("account_status")); // アカウントステータス
                outForm.setActivateToken(rs.getString("activate_token")); // 有効化トークン
                outForm.setActivateEffectiveDate(rs.getTimestamp("activate_effective_date")); // 有効化トークン有効期限
                outForm.setReissueToken(rs.getString("reissue_token")); // 再発行トークン
                outForm.setReissueEffctiveDate(rs.getTimestamp("reissue_effective_date")); // 再発行トークン有効期限
                outForm.setAccountCreatedAt(rs.getTimestamp("account_created_at")); // アカウント作成日時
                outForm.setAccountUpdatedAt(rs.getTimestamp("account_updated_at")); // アカウント更新日時
                // リストにアクションフォームを追加する。
                accountExtedFormList.add(outForm);
            }

            // アクションフォームを格納したリストを戻す。
            return accountExtedFormList;
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
