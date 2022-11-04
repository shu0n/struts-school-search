package dao.school;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import actionform.SchoolActionForm;
import dao.ConnectorDAO;
import dao.school.sql.SchoolSelectWhereActionForm;

public class SelectSchoolDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * スクールテーブルから指定した検索条件に一致するレコードを取得するためのメソッド
     * @param whereForm 検索条件を指定するデータを格納したアクションフォーム
     * @return 検索条件に一致したスクールのアクションフォームをスクールIDの昇順で格納したリスト
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<SchoolActionForm> selectMatchedSchool(SchoolSelectWhereActionForm whereForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルをしているためのSELECT文を生成する。
            String tableSql = "SELECT * FROM schools ";

            // 検索条件を指定するためのStringBuilderを呼び出す。
            StringBuilder whereSb = new StringBuilder();
            whereSb.append("WHERE school_delete_flag='0' ");

            // スクールIDおよびスクールID配列
            int schoolId = whereForm.getSchoolId();
            String[] schoolIdArray = whereForm.getSchoolIdArray();
            if(schoolId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND school_id=" + schoolId + " ");
            } else if(!StringUtils.isAllEmpty(schoolIdArray)) {
                // スクールID配列がNULLまたは空要素以外の場合

                if(schoolIdArray.length == 1) {
                    // 要総数が1の場合は検索条件(AND)に追加する。
                    whereSb.append("AND school_id=" + schoolIdArray[0] + " ");
                } else {
                    // 上記以外の場合

                    // 格納されている全てのスクールIDを検索条件(AND(OR))に追加する。
                    whereSb.append("AND (");
                    // 検索条件に追加するためのStringBuilderを生成する。
                    StringBuilder schoolIdSb = new StringBuilder();
                    for(int i = 0; i < schoolIdArray.length; i++) {
                        // 格納されているスクールIDを検索条件に追加する。
                        schoolIdSb.append("OR school_id=" + schoolIdArray[i] + " ");
                    }
                    // 先頭3文字を削除する
                    schoolIdSb.delete(0,3);
                    // 検索条件を指定するためのStringBuilderに追加する。
                    whereSb.append(schoolIdSb.toString() + ") ");
                }
            }

            // 登録者アカウントIDおよび登録者アカウントID配列
            int registrantAccountId = whereForm.getRegistrantAccountId();
            String[] registrantAccountIdArray = whereForm.getRegistrantAccountIdArray();
            if(registrantAccountId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND registrant_account_id=" + registrantAccountId + " ");
            } else if(!StringUtils.isAllEmpty(registrantAccountIdArray)) {
                // 登録者アカウントID配列がNULLまたは空要素以外の場合

                if(registrantAccountIdArray.length == 1) {
                    // 要総数が1の場合は検索条件(AND)に追加する。
                    whereSb.append("AND registrant_account_id=" + registrantAccountIdArray[0] + " ");
                } else {
                    // 上記以外の場合

                    // 格納されている全ての登録者アカウントIDを検索条件(AND(OR))に追加する。
                    whereSb.append("AND (");
                    // 検索条件に追加するためのStringBuilderを生成する。
                    StringBuilder registrantAccountIdSb = new StringBuilder();
                    for(int i = 0; i < registrantAccountIdArray.length; i++) {
                        // 格納されている登録者アカウントIDを検索条件に追加する。
                        registrantAccountIdSb.append("OR registrant_account_id=" + registrantAccountIdArray[i] + " ");
                    }
                    // 先頭3文字を削除する
                    registrantAccountIdSb.delete(0,3);
                    // 検索条件を指定するためのStringBuilderに追加する。
                    whereSb.append(registrantAccountIdSb.toString() + ") ");
                }
            }

            // 登録者管理者IDおよび登録者管理者ID配列
            int registrantdminId = whereForm.getRegistrantAdminId();
            String[] registrantAdminIdArray = whereForm.getRegistrantAdminIdArray();
            if(registrantdminId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND registrant_admin_id=" + registrantdminId + " ");
            } else if(!StringUtils.isAllEmpty(registrantAdminIdArray)) {
                // 登録者管理者ID配列がNULLまたは空要素以外の場合

                if(registrantAdminIdArray.length == 1) {
                    // 要総数が1の場合は検索条件(AND)に追加する。
                    whereSb.append("AND registrant_admin_id=" + registrantAdminIdArray[0] + " ");
                } else {
                    // 上記以外の場合

                    // 格納されている全ての登録者管理者IDを検索条件(AND(OR))に追加する。
                    whereSb.append("AND (");
                    // 検索条件に追加するためのStringBuilderを生成する。
                    StringBuilder registrantAdminIdSb = new StringBuilder();
                    for(int i = 0; i < registrantAdminIdArray.length; i++) {
                        // 格納されている登録者管理者IDを検索条件に追加する。
                        registrantAdminIdSb.append("OR registrant_admin_id=" + registrantAdminIdArray[i] + " ");
                    }
                    // 先頭3文字を削除する
                    registrantAdminIdSb.delete(0,3);
                    // 検索条件を指定するためのStringBuilderに追加する。
                    whereSb.append(registrantAdminIdSb.toString() + ") ");
                }
            }

            // スクール名およびスクール名(類似)
            String schoolName = whereForm.getSchoolName();
            String likeSchoolName = whereForm.getLikeSchoolName();
            if(schoolName != null) {
                // スクール名がNULL以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND school_name='" + schoolName + "' ");
            } else if(likeSchoolName != null) {
                // スクール名(類似)がNULL以外の場合は検索条件(LIKE)に追加する。
                whereSb.append("AND school_name LIKE '%" + likeSchoolName + "%' ");
            }

            // スクール費用
            String schoolFee = whereForm.getSchoolFee();
            if(!StringUtils.isEmpty(schoolFee)) {
                // NULLまたは空文字以外の場合は検索条件(AND)追加する。
                whereSb.append("AND school_fee='" + schoolFee + "' ");
            } else {
                // 上記以外の場合

                // スクール費用(下限)
                String minSchoolFee = whereForm.getMinSchoolFee();
                if(!StringUtils.isEmpty(minSchoolFee)) {
                    // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                    whereSb.append("AND school_fee>=" + minSchoolFee + " ");
                }

                // スクール費用(上限)
                String maxSchoolFee = whereForm.getMaxSchoolFee();
                if(!StringUtils.isEmpty(maxSchoolFee)) {
                    // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                    whereSb.append("AND school_fee<=" + maxSchoolFee + " ");
                }
            }

            // スクールカテゴリーIDおよびスクールカテゴリーID配列
            int schoolCategoryId = whereForm.getSchoolCategoryId();
            String[] schoolCategoryIdArray = whereForm.getSchoolCategoryIdArray();
            if(schoolCategoryId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND school_category_id=" + schoolCategoryId + " ");
            } else if(!StringUtils.isAllEmpty(schoolCategoryIdArray)) {
                // スクールカテゴリーID配列がNULLまたは空要素以外の場合

                if(schoolCategoryIdArray.length == 1) {
                    // 要総数が1の場合は検索条件(AND)に追加する。
                    whereSb.append("AND school_category_id=" + schoolCategoryIdArray[0] + " ");
                } else {
                    // 上記以外の場合

                    // 格納されている全てのスクールカテゴリーIDを検索条件(AND(OR))に追加する。
                    whereSb.append("AND (");
                    // 検索条件に追加するためのStringBuilderを生成する。
                    StringBuilder schoolCategoryIdSb = new StringBuilder();
                    for(int i = 0; i < schoolCategoryIdArray.length; i++) {
                        // 格納されているスクールカテゴリーIDを検索条件に追加する。
                        schoolCategoryIdSb.append("OR school_category_id=" + schoolCategoryIdArray[i] + " ");
                    }
                    // 先頭3文字を削除する
                    schoolCategoryIdSb.delete(0,3);
                    // 検索条件を指定するためのStringBuilderに追加する。
                    whereSb.append(schoolCategoryIdSb.toString() + ") ");
                }
            }

            // スクール都道府県IDおよびスクール都道府県ID配列
            int schoolPrefectureId = whereForm.getSchoolPrefectureId();
            String[] schoolPrefectureIdArray = whereForm.getSchoolPrefectureIdArray();
            if(schoolPrefectureId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND school_prefecture_id=" + schoolPrefectureId + " ");
            } else if(!StringUtils.isAllEmpty(schoolPrefectureIdArray)) {
                // スクール都道府県ID配列がNULLまたは空要素以外の場合

                if(schoolPrefectureIdArray.length == 1) {
                    // 要総数が1の場合は検索条件(AND)に追加する。
                    whereSb.append("AND school_prefecture_id=" + schoolPrefectureIdArray[0] + " ");
                } else {
                    // 上記以外の場合

                    // 格納されている全てのスクール都道府県IDを検索条件(AND(OR))に追加する。
                    whereSb.append("AND (");
                    // 検索条件に追加するためのStringBuilderを生成する。
                    StringBuilder schoolPrefectureIdSb = new StringBuilder();
                    for(int i = 0; i < schoolPrefectureIdArray.length; i++) {
                        // 格納されているスクール都道府県IDを検索条件に追加する。
                        schoolPrefectureIdSb.append("OR school_prefecture_id=" + schoolPrefectureIdArray[i] + " ");
                    }
                    // 先頭3文字を削除する
                    schoolPrefectureIdSb.delete(0,3);
                    // 検索条件を指定するためのStringBuilderに追加する。
                    whereSb.append(schoolPrefectureIdSb.toString() + ") ");
                }
            }

            // スクール公開可否およびスクール公開可否配列
            String schoolReleasePropriety = whereForm.getSchoolReleasePropriety();
            String[] schoolReleaseProprietyArray = whereForm.getSchoolReleaseProprietyArray();
            if(!StringUtils.isEmpty(schoolReleasePropriety)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND school_release_propriety='" + schoolReleasePropriety + "' ");
            } else if(!StringUtils.isAllEmpty(schoolReleaseProprietyArray)) {
                // スクール公開可否配列がNULLまたは空要素以外の場合

                if(schoolReleaseProprietyArray.length == 1) {
                    // 要総数が1の場合は検索条件(AND)に追加する。
                    whereSb.append("AND school_release_propriety='" + schoolReleaseProprietyArray[0] + "' ");
                } else {
                    // 上記以外の場合

                    // 格納されている全てのスクール公開可否を検索条件(AND(OR))に追加する。
                    whereSb.append("AND (");
                    // 検索条件に追加するためのStringBuilderを生成する。
                    StringBuilder schoolReleaseProprietySb = new StringBuilder();
                    for(int i = 0; i < schoolReleaseProprietyArray.length; i++) {
                        // 格納されているスクール公開可否を検索条件に追加する。
                        schoolReleaseProprietySb.append(
                                "OR school_release_propriety='" + schoolReleaseProprietyArray[i] + "' ");
                    }
                    // 先頭3文字を削除する
                    schoolReleaseProprietySb.delete(0,3);
                    // 検索条件を指定するためのStringBuilderに追加する。
                    whereSb.append(schoolReleaseProprietySb.toString() + ") ");
                }
            }

            // スクール申込可否およびスクール申込可否配列
            String schoolEntryPropriety = whereForm.getSchoolEntryPropriety();
            String[] schoolEntryProprietyArray = whereForm.getSchoolEntryProprietyArray();
            if(!StringUtils.isEmpty(schoolEntryPropriety)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND school_entry_propriety='" + schoolEntryPropriety + "' ");
            } else if(!StringUtils.isAllEmpty(schoolEntryProprietyArray)) {
                // スクール申込可否配列がNULLまたは空要素以外の場合

                if(schoolEntryProprietyArray.length == 1) {
                    // 要総数が1の場合は検索条件(AND)に追加する。
                    whereSb.append("AND school_entry_propriety='" + schoolEntryProprietyArray[0] + "' ");
                } else {
                    // 上記以外の場合

                    // 格納されている全てのスクール申込可否を検索条件(AND(OR))に追加する。
                    whereSb.append("AND (");
                    // 検索条件に追加するためのStringBuilderを生成する。
                    StringBuilder schoolEntryProprietySb = new StringBuilder();
                    for(int i = 0; i < schoolEntryProprietyArray.length; i++) {
                        // 格納されているスクール申込可否を検索条件に追加する。
                        schoolEntryProprietySb.append(
                                "OR school_entry_propriety='" + schoolEntryProprietyArray[i] + "' ");
                    }
                    // 先頭3文字を削除する
                    schoolEntryProprietySb.delete(0,3);
                    // 検索条件を指定するためのStringBuilderに追加する。
                    whereSb.append(schoolEntryProprietySb.toString() + ") ");
                }
            }

            // スクール登録日時(From)
            String fromSchoolRegisteredDate = whereForm.getFromSchoolRegisteredDate();
            if(!StringUtils.isEmpty(fromSchoolRegisteredDate)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append(
                        "AND school_registered_at>=TO_TIMESTAMP('"
                        + fromSchoolRegisteredDate
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");
            }

            // スクール登録日時(To)
            String toSchoolRegisteredDate = whereForm.getToSchoolRegisteredDate();
            if(!StringUtils.isEmpty(toSchoolRegisteredDate)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append(
                        "AND school_registered_at<=TO_TIMESTAMP('"
                        + toSchoolRegisteredDate
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");
            }

            // スクール更新日時(From)
            String fromSchoolUpdatedDate = whereForm.getFromSchoolUpdatedDate();
            if(!StringUtils.isEmpty(fromSchoolUpdatedDate)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append(
                        "AND school_updated_at>=TO_TIMESTAMP('"
                        + fromSchoolUpdatedDate
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");
            }

            // スクール更新日時(To)
            String toSchoolUpdatedDate = whereForm.getToSchoolUpdatedDate();
            if(!StringUtils.isEmpty(toSchoolUpdatedDate)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append(
                        "AND school_updated_at<=TO_TIMESTAMP('"
                        + toSchoolUpdatedDate
                        + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");
            }

            // StringBuilderで生成した文をString型に変換した文とスクールIDの昇順を指定する文を結合してSELECT全文を生成する。
            String sql = tableSql + whereSb.toString() + "ORDER BY school_id ASC";

            // DBとの接続状態を取得する。
            connection = getConnection();
            // SELECT文をPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // SELECT文の実行結果を変数に格納する。
            ResultSet rs = ps.executeQuery();

            // アクションフォームを格納するためのリストを生成する。
            List<SchoolActionForm> schoolFormList = new ArrayList<>();
            // 取得したレコードの件数分、処理を繰り返す。
            while(rs.next()) {
                // レコードから各列のデータを取得してアクションフォームに格納する。
                SchoolActionForm outForm = new SchoolActionForm();
                outForm.setSchoolId(rs.getInt("school_id")); // スクールID
                outForm.setRegistrantAccountId(rs.getInt("registrant_account_id")); // 登録者アカウントID
                outForm.setRegistrantAdminId(rs.getInt("registrant_admin_id")); // 登録者管理者ID
                outForm.setSchoolName(rs.getString("school_name")); // スクール名
                outForm.setSchoolCategoryId(rs.getInt("school_category_id")); // スクールカテゴリーID
                outForm.setSchoolSummary(rs.getString("school_summary")); // スクール概要
                outForm.setSchoolDescription(rs.getString("school_description")); // スクール詳細
                outForm.setSchoolZipCode1(rs.getString("school_zip_code1")); // スクール郵便番号1
                outForm.setSchoolZipCode2(rs.getString("school_zip_code2")); // スクール郵便番号2
                outForm.setSchoolPrefectureId(rs.getInt("school_prefecture_id")); // スクール都道府県ID
                outForm.setSchoolCity(rs.getString("school_city")); // スクール市区町村
                outForm.setSchoolAddress1(rs.getString("school_address1")); // スクール住所1
                outForm.setSchoolAddress2(rs.getString("school_address2")); // スクール住所2
                outForm.setSchoolFee(new BigDecimal(rs.getInt("school_fee"))); // スクール費用
                outForm.setSupplementaryFee(rs.getString("supplementary_fee")); // 費用補足
                outForm.setSchoolUrl(rs.getString("school_url")); // スクールURL
                outForm.setSchoolReleasePropriety(rs.getString("school_release_propriety")); // スクール公開可否
                outForm.setSchoolEntryPropriety(rs.getString("school_entry_propriety")); // スクール申込可否
                outForm.setSummaryImageFileName(rs.getString("summary_image_file_name")); // 一覧画面画像ファイル名
                outForm.setDetailImage1FileName(rs.getString("detail_image1_file_name")); // 詳細画面画像1ファイル名
                outForm.setDetailImage2FileName(rs.getString("detail_image2_file_name")); // 詳細画面画像2ファイル名
                outForm.setDetailImage3FileName(rs.getString("detail_image3_file_name")); // 詳細画面画像3ファイル名
                outForm.setDetailImage4FileName(rs.getString("detail_image4_file_name")); // 詳細画面画像4ファイル名
                outForm.setDetailImage5FileName(rs.getString("detail_image5_file_name")); // 詳細画面画像5ファイル名
                outForm.setDetailImage6FileName(rs.getString("detail_image6_file_name")); // 詳細画面画像6ファイル名
                outForm.setDetailImage7FileName(rs.getString("detail_image7_file_name")); // 詳細画面画像7ファイル名
                outForm.setDetailImage8FileName(rs.getString("detail_image8_file_name")); // 詳細画面画像8ファイル名
                outForm.setSchoolRegisteredAt(rs.getTimestamp("school_registered_at")); // スクール登録日時
                outForm.setSchoolUpdatedAt(rs.getTimestamp("school_updated_at")); // スクール更新日時
                // リストのアクションフォームを格納する。
                schoolFormList.add(outForm);
            }

            // アクションフォームを格納したリストを戻す。
            return schoolFormList;
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
