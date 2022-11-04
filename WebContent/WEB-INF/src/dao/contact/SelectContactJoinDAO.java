package dao.contact;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import actionform.ContactExtendActionForm;
import dao.ConnectorDAO;
import dao.contact.sql.ContactSelectJoinWhereActionForm;

public class SelectContactJoinDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * お問合せテーブルをお問合せステータステーブルと外部結合して指定した検索条件に一致するレコードを取得するためのメソッド
     * @param whereForm 検索条件を指定するデータを格納したアクションフォーム
     * @return 検索条件に一致したお問合せのアクションフォームをお問合せIDの昇順で格納したリスト
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<ContactExtendActionForm> selectMatchedContact(ContactSelectJoinWhereActionForm whereForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // 外部結合するためのSELECT文を生成する。
            String joinSql
                    = "SELECT * FROM ("
                    + "contacts LEFT OUTER JOIN contact_statuses "
                    + "ON contacts.contact_status_id=contact_statuses.contact_status_id) ";

            // 検索条件を指定するためのStringBuilderを呼び出す。
            StringBuilder whereSb = new StringBuilder();
            whereSb.append("WHERE contact_delete_flag='0' ");

            // お問合せIDおよびお問合せID配列
            int contactId = whereForm.getContactId();
            String[] contactIdArray = whereForm.getContactIdArray();
            if(contactId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND contact_id=" + contactId + " ");
            } else if(!StringUtils.isAllEmpty(contactIdArray)) {
                // お問合せID配列がNULLまたは空要素以外の場合

                if(contactIdArray.length == 1) {
                    // 要総数が1の場合は検索条件(AND)に追加する。
                    whereSb.append("AND contact_id=" + contactIdArray[0] + " ");
                } else {
                    // 上記以外の場合

                    // 格納されている全てのお問合せIDを検索条件(AND(OR))に追加する。
                    whereSb.append("AND (");
                    // 検索条件に追加するためのStringBuilderを生成する。
                    StringBuilder contactIdSb = new StringBuilder();
                    for(int i = 0; i < contactIdArray.length; i++) {
                        // 格納されているお問合せIDを検索条件に追加する。
                        contactIdSb.append("OR contact_id=" + contactIdArray[i] + " ");
                    }
                    // 先頭3文字を削除する
                    contactIdSb.delete(0,3);
                    // 検索条件を指定するためのStringBuilderに追加する。
                    whereSb.append(contactIdSb.toString() + ") ");
                }
            }

            // お問合せ者アカウントIDおよびお問合せ者アカウントID配列
            int contactAccountId = whereForm.getContactAccountId();
            String[] contactAccountIdArray = whereForm.getContactAccountIdArray();
            if(contactAccountId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND contact_account_id=" + contactAccountId + " ");
            } else if(!StringUtils.isAllEmpty(contactAccountIdArray)) {
                // お問合せ者アカウントID配列がNULLまたは空要素以外の場合

                if(contactAccountIdArray.length == 1) {
                    // 要総数が1の場合は検索条件(AND)に追加する。
                    whereSb.append("AND contact_account_id=" + contactAccountIdArray[0] + " ");
                } else {
                    // 上記以外の場合

                    // 格納されている全てのお問合せ者アカウントIDを検索条件(AND(OR))に追加する。
                    whereSb.append("AND (");
                    // 検索条件に追加するためのStringBuilderを生成する。
                    StringBuilder contactAccountIdSb = new StringBuilder();
                    for(int i = 0; i < contactAccountIdArray.length; i++) {
                        // 格納されているお問合せ者アカウントIDを条件に追加する。
                        contactAccountIdSb.append("OR contact_account_id=" + contactAccountIdArray[i] + " ");
                    }
                    // 先頭3文字を削除する
                    contactAccountIdSb.delete(0,3);
                    // 検索条件を指定するためのStringBuilderに追加する。
                    whereSb.append(contactAccountIdSb.toString() + ") ");
                }
            }

            // お問合せ者姓およびお問合せ者姓(類似)
            String contactLastName = whereForm.getContactLastName();
            String likeContactLastName = whereForm.getLikeContactLastName();
            if(!StringUtils.isEmpty(contactLastName)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND contact_last_name='" + contactLastName + "' ");
            } else if(!StringUtils.isEmpty(likeContactLastName)) {
                // NULLまたは空文字以外の場合は検索条件(LIKE)に追加する。
                whereSb.append("AND contact_last_name LIKE '%" + likeContactLastName + "%' ");
            }

            // お問合せ者名およびお問合せ者名(類似)
            String contactFirstName = whereForm.getContactFirstName();
            String likeContactFirstName = whereForm.getLikeContactFirstName();
            if(!StringUtils.isEmpty(contactFirstName)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND contact_first_name='" + contactFirstName + "' ");
            } else if(!StringUtils.isEmpty(likeContactFirstName)) {
                // NULLまたは空文字以外の場合は検索条件(LIKE)に追加する。
                whereSb.append("AND contact_first_name LIKE '%" + likeContactFirstName + "%' ");
            }

            // お問合せ者姓(フリガナ)およびお問合せ者姓(フリガナ、類似)
            String contactLastNameKana = whereForm.getContactLastNameKana();
            String likeContactLastNameKana = whereForm.getLikeContactLastNameKana();
            if(!StringUtils.isEmpty(contactLastNameKana)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND contact_last_name_kana='" + contactLastNameKana + "' ");
            } else if(!StringUtils.isEmpty(likeContactLastNameKana)) {
                // NULLまたは空文字以外の場合は検索条件(LIKE)に追加する。
                whereSb.append("AND contact_last_name_kana LIKE '%" + likeContactLastNameKana + "%' ");
            }

            // お問合せ者名(フリガナ)およびお問合せ者名(フリガナ、類似)
            String contactFirstNameKana = whereForm.getContactFirstNameKana();
            String likeContactFirstNameKana = whereForm.getLikeContactFirstNameKana();
            if(!StringUtils.isEmpty(contactFirstNameKana)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND contact_first_name_kana='" + contactFirstNameKana + "' ");
            } else if(!StringUtils.isEmpty(likeContactFirstNameKana)) {
                // NULLまたは空文字以外の場合は検索条件(LIKE)に追加する。
                whereSb.append("AND contact_first_name_kana LIKE '%" + likeContactFirstNameKana + "%' ");
            }

            // お問合せ者メールアドレスおよびお問合せ者メールアドレス(類似)
            String contactMailAddress = whereForm.getContactMailAddress();
            String likeContactMailAddress = whereForm.getLikeContactMailAddress();
            if(!StringUtils.isEmpty(contactMailAddress)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND contact_mail_address='" + contactMailAddress + "' ");
            } else if(!StringUtils.isEmpty(likeContactMailAddress)) {
                // NULLまたは空文字以外の場合は検索条件(LIKE)に追加する。
                whereSb.append("AND contact_mail_address LIKE '%" + likeContactMailAddress + "%' ");
            }

            // お問合せステータスIDおよびお問合せステータスID配列
            int contactStatusId = whereForm.getContactStatusId();
            String[] contactStatusIdArray = whereForm.getContactStatusIdArray();
            if(contactStatusId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND contacts.contact_status_id=" + contactStatusId + " ");
            } else if(!StringUtils.isAllEmpty(contactStatusIdArray)) {
                // お問合せステータスID配列がNULLまたは空要素以外の場合

                if(contactStatusIdArray.length == 1) {
                    // 要総数が1の場合は検索条件(AND)に追加する。
                    whereSb.append("AND contacts.contact_status_id=" + contactStatusIdArray[0] + " ");
                } else {
                    // 上記以外の場合

                    // 格納されている全てのお問合せステータスを検索条件(AND(OR))に追加する。
                    whereSb.append("AND (");
                    // 検索条件に追加するためのStringBuilderを生成する。
                    StringBuilder contactStatusIdSb = new StringBuilder();
                    for(int i = 0; i < contactStatusIdArray.length; i++) {
                        // 格納されているお問合せステータスを検索条件に追加する。
                        contactStatusIdSb.append("OR contacts.contact_status_id=" + contactStatusIdArray[i] + " ");
                    }
                    // 先頭3文字を削除する
                    contactStatusIdSb.delete(0,3);
                    // 検索条件を指定するためのStringBuilderに追加する。
                    whereSb.append(contactStatusIdSb.toString() + ") ");
                }
            }

            // お問合せ日時(From)
            String fromContactedDate = whereForm.getFromContactedDate();
            if(!StringUtils.isEmpty(fromContactedDate)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append(
                        "AND contacted_at>=TO_TIMESTAMP('"
                        + fromContactedDate
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");
            }

            // お問合せ日時(To)
            String toContactedDate = whereForm.getToContactedDate();
            if(!StringUtils.isEmpty(toContactedDate)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append(
                        "AND contacted_at<=TO_TIMESTAMP('"
                        + toContactedDate
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");
            }

            // お問合せ更新日時(From)
            String fromContactUpdatedDate = whereForm.getFromContactUpdatedDate();
            if(!StringUtils.isEmpty(fromContactUpdatedDate)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append(
                        "AND contact_updated_at>=TO_TIMESTAMP('"
                        + fromContactUpdatedDate
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");
            }

            // お問合せ更新日時(To)
            String toContactUpdatedDate = whereForm.getToContactUpdatedDate();
            if(!StringUtils.isEmpty(toContactUpdatedDate)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append(
                        "AND contact_updated_at<=TO_TIMESTAMP('"
                        + toContactUpdatedDate
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");
            }

            // StringBuilderで生成した文をString型に変換した文とお問合せIDの昇順を指定する文を結合してSELECT全文を生成する。
            String sql = joinSql + whereSb.toString() + "ORDER BY contact_id ASC";

            // DBとの接続状態を取得する。
            connection = getConnection();
            // SELECT文をPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // SELECT文の実行結果を変数に格納する。
            ResultSet rs = ps.executeQuery();

            // アクションフォームを格納するためのリストを生成する。
            List<ContactExtendActionForm> contactExtendFormList = new ArrayList<>();
            // 取得したレコードの件数分、処理を繰り返す。
            while(rs.next()) {
                // レコードから各列のデータを取得してアクションフォームに格納する。
                ContactExtendActionForm outForm = new ContactExtendActionForm();
                outForm.setContactId(rs.getInt("contact_id")); // お問合せID
                outForm.setContactAccountId(rs.getInt("contact_account_id")); // お問合せ者アカウントID
                outForm.setContactLastName(rs.getString("contact_last_name")); // お問合せ者姓
                outForm.setContactFirstName(rs.getString("contact_first_name")); // お問合せ者名
                outForm.setContactLastNameKana(rs.getString("contact_last_name_kana")); // お問合せ者姓(フリガナ)
                outForm.setContactFirstNameKana(rs.getString("contact_first_name_kana")); // お問合せ者名(フリガナ)
                outForm.setContactMailAddress(rs.getString("contact_mail_address")); // お問合せ者メールアドレス
                outForm.setContactContent(rs.getString("contact_content")); // お問合せ内容
                outForm.setContactStatusId(rs.getInt("contact_status_id")); // お問合せステータスID
                outForm.setContactStatusName(rs.getString("contact_status_name")); // お問合せステータス名
                outForm.setContactedAt(rs.getTimestamp("contacted_at")); // お問合せ日時
                outForm.setContactUpdatedAt(rs.getTimestamp("contact_updated_at")); // お問合せ更新日時
                // リストにアクションフォームを追加する。
                contactExtendFormList.add(outForm);
            }

            // アクションフォームを格納したリストを戻す。
            return contactExtendFormList;
        } finally {
            // DBに接続されている場合
            if(connection != null) {
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
