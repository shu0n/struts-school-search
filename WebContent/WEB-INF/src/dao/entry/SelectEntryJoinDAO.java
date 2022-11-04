package dao.entry;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import actionform.EntryExtendActionForm;
import dao.ConnectorDAO;
import dao.entry.sql.EntrySelectJoinWhereActionForm;

public class SelectEntryJoinDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * 申込テーブルを申込ステータステーブル、アカウントテーブル、スクールテーブルと外部結合して指定した検索条件に一致するレコードを取得するためのメソッド
     * @param whereForm 検索条件を指定するデータを格納したアクションフォーム
     * @return 検索条件に一致した申込のアクションフォームを申込IDの昇順で格納したリスト
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<EntryExtendActionForm> selectMatchedEntry(EntrySelectJoinWhereActionForm whereForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // 外部結合するためのSELECT文を生成する。
            String joinSql
                    = "SELECT * FROM ((("
                    + "entries LEFT OUTER JOIN entry_statuses ON entries.entry_status_id=entry_statuses.entry_status_id) "
                    + "LEFT OUTER JOIN accounts ON entries.applicant_account_id=accounts.account_id) "
                    + "LEFT OUTER JOIN schools ON entries.entry_school_id=schools.school_id) ";

            // 検索条件を指定するためのStringBuilderを呼び出す。
            StringBuilder whereSb = new StringBuilder();
            whereSb.append("WHERE entry_delete_flag='0' ");

            // 申込IDおよび申込ID配列
            int entryId = whereForm.getEntryId();
            String[] entryIdArray = whereForm.getEntryIdArray();
            if(entryId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND entry_id=" + entryId + " ");
            } else if(!StringUtils.isAllEmpty(entryIdArray)) {
                // 申込ID配列がNULLまたは空要素以外の場合は取得する。
                if(entryIdArray.length == 1) {
                    // 要総数が1の場合は検索条件(AND)に追加する。
                    whereSb.append("AND entry_id=" + entryIdArray[0] + " ");
                } else {
                    // 上記以外の場合

                    // 格納されている全ての申込IDを検索条件(AND(OR))に追加する。
                    whereSb.append("AND (");
                    // 検索条件に追加するためのStringBuilderを生成する。
                    StringBuilder entryIdSb = new StringBuilder();
                    for(int i = 0; i < entryIdArray.length; i++) {
                        // 格納されている申込IDを検索条件に追加する。
                        entryIdSb.append("OR entry_id=" + entryIdArray[i] + " ");
                    }
                    // 先頭3文字を削除する
                    entryIdSb.delete(0,3);
                    // 検索条件を指定するためのStringBuilderに追加する。
                    whereSb.append(entryIdSb.toString() + ") ");
                }
            }

            // 申込先スクールIDおよび申込ID配列
            int entrySchoolId = whereForm.getEntrySchoolId();
            String[] entrySchoolIdArray = whereForm.getEntrySchoolIdArray();
            if(entrySchoolId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND entry_school_id=" + entrySchoolId + " ");
            } else if(!StringUtils.isAllEmpty(entrySchoolIdArray)) {
                // 申込先スクールID配列がNULLまたは空要素以外の場合

                if(entrySchoolIdArray.length == 1) {
                    // 要総数が1の場合は検索条件(AND)に追加する。
                    whereSb.append("AND entry_school_id=" + entrySchoolIdArray[0] + " ");
                } else {
                    // 上記以外の場合

                    // 格納されている全ての申込先スクールIDを検索条件(AND(OR))に追加する。
                    whereSb.append("AND (");
                    // 検索条件に追加するためのStringBuilderを生成する。
                    StringBuilder entrySchoolIdSb = new StringBuilder();
                    for(int i = 0; i < entrySchoolIdArray.length; i++) {
                        // 格納されている申込先スクールIDを検索条件に追加する。
                        entrySchoolIdSb.append("OR entry_school_id=" + entrySchoolIdArray[i] + " ");
                    }
                    // 先頭3文字を削除する
                    entrySchoolIdSb.delete(0,3);
                    // 検索条件を指定するためのStringBuilderに追加する。
                    whereSb.append(entrySchoolIdSb.toString() + ") ");
                }
            }

            // 申込者アカウントIDおよび申込アカウントID配列
            int applicantAccountId = whereForm.getApplicantAccountId();
            String[] applicantAccountIdArray = whereForm.getApplicantAccountIdArray();
            if(applicantAccountId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND applicant_account_id=" + applicantAccountId + " ");
            } else if(!StringUtils.isAllEmpty(applicantAccountIdArray)) {
                // 申込者アカウントID配列がNULLまたは空要素以外の場合

                if(applicantAccountIdArray.length == 1) {
                    // 要総数が1の場合は検索条件(AND)に追加する。
                    whereSb.append("AND applicant_account_id=" + applicantAccountIdArray[0] + " ");
                } else {
                    // 上記以外の場合

                    // 格納されている全ての申込者アカウントIDを検索条件(AND(OR))に追加する。
                    whereSb.append("AND (");
                    // 検索条件に追加するためのStringBuilderを生成する。
                    StringBuilder applicantAccountIdSb = new StringBuilder();
                    for(int i = 0; i < applicantAccountIdArray.length; i++) {
                        // 格納されている申込者アカウントIDを検索条件に追加する。
                        applicantAccountIdSb.append("OR applicant_account_id=" + applicantAccountIdArray[i] + " ");
                    }
                    // 先頭3文字を削除する
                    applicantAccountIdSb.delete(0,3);
                    // 検索条件を指定するためのStringBuilderに追加する。
                    whereSb.append(applicantAccountIdSb.toString() + ") ");
                }
            }

            // 申込先スクール名および申込先スクール名(類似)
            String entrySchoolName = whereForm.getEntrySchoolName();
            String likeEntrySchoolName = whereForm.getLikeEntrySchoolName();
            if(!StringUtils.isEmpty(entrySchoolName)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND school_name='" + entrySchoolName + "' ");
            } else if(!StringUtils.isEmpty(likeEntrySchoolName)) {
                // NULLまたは空文字以外の場合は検索条件(LIKE)に追加する。
                whereSb.append("AND school_name LIKE '%" + likeEntrySchoolName + "%' ");
            }

            // 申込者姓および申込者姓(類似)
            String applicantLastName = whereForm.getApplicantLastName();
            String likeApplicantLastName = whereForm.getLikeApplicantLastName();
            if(!StringUtils.isEmpty(applicantLastName)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND last_name='" + applicantLastName + "' ");
            } else if(!StringUtils.isEmpty(likeApplicantLastName)) {
                // NULLまたは空文字以外の場合は検索条件(LIKE)に追加する。
                whereSb.append("AND last_name LIKE '%" + likeApplicantLastName + "%' ");
            }

            // 申込者名および申込者名(類似)
            String applicantFirstName = whereForm.getApplicantFirstName();
            String likeApplicantFirstName = whereForm.getLikeApplicantFirstName();
            if(!StringUtils.isEmpty(applicantFirstName)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND first_name='" + applicantFirstName + "' ");
            } else if(!StringUtils.isEmpty(likeApplicantFirstName)) {
                // NULLまたは空文字以外の場合は検索条件(LIKE)に追加する。
                whereSb.append("AND first_name LIKE '%" + likeApplicantFirstName + "%' ");
            }

            // 申込者姓(フリガナ)および申込者姓(フリガナ、類似)
            String applicantLastNameKana = whereForm.getApplicantLastNameKana();
            String likeApplicantLastNameKana = whereForm.getLikeApplicantLastNameKana();
            if(!StringUtils.isEmpty(applicantLastNameKana)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND last_name_kana='" + applicantLastNameKana + "' ");
            } else if(!StringUtils.isEmpty(likeApplicantLastNameKana)) {
                // NULLまたは空文字以外の場合は検索条件(LIKE)に追加する。
                whereSb.append("AND last_name_kana LIKE '%" + likeApplicantLastNameKana + "%' ");
            }

            // 申込者名(フリガナ)および申込者名(フリガナ、類似)
            String applicantFirstNameKana = whereForm.getApplicantFirstNameKana();
            String likeApplicantFirstNameKana = whereForm.getLikeApplicantFirstNameKana();
            if(!StringUtils.isEmpty(applicantFirstNameKana)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND first_name_kana='" + applicantFirstNameKana + "' ");
            } else if(!StringUtils.isEmpty(likeApplicantFirstNameKana)) {
                // NULLまたは空文字以外の場合は検索条件(LIKE)に追加する。
                whereSb.append("AND first_name_kana LIKE '%" + likeApplicantFirstNameKana + "%' ");
            }

            // 申込ステータスIDおよび申込ステータスID配列
            int entryStatusId = whereForm.getEntryStatusId();
            String[] entryStatusIdArray = whereForm.getEntryStatusIdArray();
            if(entryStatusId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND entries.entry_status_id=" + entryStatusId + " ");
            } else if(!StringUtils.isAllEmpty(entryStatusIdArray)) {
                // 申込ステータスID配列がNULLまたは空要素以外の場合

                if(entryStatusIdArray.length == 1) {
                    // 要総数が1の場合は検索条件(AND)に追加する。
                    whereSb.append("AND entries.entry_status_id=" + entryStatusIdArray[0] + " ");
                } else {
                    // 上記以外の場合

                    // 格納されている全ての申込ステータスを検索条件(AND(OR))に追加する。
                    whereSb.append("AND (");
                    // 検索条件に追加するためのStringBuilderを生成する。
                    StringBuilder entryStatusSb = new StringBuilder();
                    for(int i = 0; i < entryStatusIdArray.length; i++) {
                        // 格納されている申込ステータスを検索条件に追加する。
                        entryStatusSb.append("OR entries.entry_status_id=" + entryStatusIdArray[i] + " ");
                    }
                    // 先頭3文字を削除する
                    entryStatusSb.delete(0,3);
                    // 検索条件を指定するためのStringBuilderに追加する。
                    whereSb.append(entryStatusSb.toString() + ") ");
                }
            }

            // 申込日時(From)
            String fromEntriedDate = whereForm.getFromEntriedDate();
            if(!StringUtils.isEmpty(fromEntriedDate)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append(
                        "AND entried_at>=TO_TIMESTAMP('"
                        + fromEntriedDate
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");
            }

            // 申込日時(To)
            String toEntriedDate = whereForm.getToEntriedDate();
            if(!StringUtils.isEmpty(toEntriedDate)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append(
                        "AND entried_at<=TO_TIMESTAMP('"
                        + toEntriedDate
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");
            }

            // 申込更新日時(From)
            String fromEntryUpdatedDate = whereForm.getFromEntryUpdatedDate();
            if(!StringUtils.isEmpty(fromEntryUpdatedDate)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append(
                        "AND entry_updated_at>=TO_TIMESTAMP('"
                        + fromEntryUpdatedDate
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");
            }

            // 申込更新日時(To)
            String toEntryUpdatedDate = whereForm.getToEntryUpdatedDate();
            if(!StringUtils.isEmpty(toEntryUpdatedDate)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append(
                        "AND entry_updated_at<=TO_TIMESTAMP('"
                        + toEntryUpdatedDate
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");
            }

            // StringBuilderで生成した文をString型に変換した文と申込IDの昇順を指定する文を結合してSELECT全文を生成する。
            String sql = joinSql + whereSb.toString() + "ORDER BY entry_id ASC";

            // DBとの接続状態を取得する。
            connection = getConnection();
            // SELECT文をPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // SELECT文の実行結果を変数に格納する。
            ResultSet rs = ps.executeQuery();

            // アクションフォームを格納するリストを生成する。
            List<EntryExtendActionForm> entryExtendFormList = new ArrayList<>();
            // 取得したレコードの件数分、処理を繰り返す。
            while(rs.next()) {
                // レコードから各列のデータを取得してアクションフォームに格納する。
                EntryExtendActionForm outForm = new EntryExtendActionForm();
                outForm.setEntryId(rs.getInt("entry_id")); // 申込ID
                outForm.setEntrySchoolId(rs.getInt("entry_school_id")); // 申込先スクールID
                outForm.setEntrySchoolName(rs.getString("school_name")); // 申込先スクール名
                outForm.setApplicantAccountId(rs.getInt("applicant_account_id")); // 申込者アカウントID
                outForm.setApplicantLastName(rs.getString("last_name")); // 申込者姓
                outForm.setApplicantFirstName(rs.getString("first_name")); // 申込者名
                outForm.setApplicantLastNameKana(rs.getString("last_name_kana")); // 申込者姓(フリガナ)
                outForm.setApplicantFirstNameKana(rs.getString("first_name_kana")); // 申込者名(フリガナ)
                outForm.setEntryQuestion(rs.getString("entry_question")); // 質問
                outForm.setEntryStatusId(rs.getInt("entry_status_id")); // 申込ステータスID
                outForm.setEntryStatusName(rs.getString("entry_status_name")); // 申込ステータス名
                outForm.setEntriedAt(rs.getTimestamp("entried_at")); // 申込日時
                outForm.setEntryUpdatedAt(rs.getTimestamp("entry_updated_at")); // 申込更新日時
                // リストにアクションフォームを追加する。
                entryExtendFormList.add(outForm);
            }

            // アクションフォームを格納したリストを戻す。
            return entryExtendFormList;
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
