package dao.school;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.ConnectorDAO;
import dao.school.sql.SchoolInsertDataActionForm;

public class InsertSchoolDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * 指定したスクールIDに紐づくスクールテーブルのレコードを更新するためのメソッド
     * @param insertDataForm 登録するデータを格納したアクションフォーム
     * @return 発番されたスクールID
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public int insertSchool(SchoolInsertDataActionForm insertDataForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルを指定するためのINSERT文を生成する。
            String tableSql = "INSERT INTO schools (";

            // 列名(文字列型)を追加するためのStringBuilderを呼び出す。
            StringBuilder strColumnSb = new StringBuilder();
            // 各列名を一度リスト変数に格納して繰り返し処理で追加する。
            List<String> strColumnList = new ArrayList<>();
            strColumnList.addAll(Arrays.asList(
                    "school_name", // スクール名
                    "school_summary", // スクール概要
                    "school_description", // スクール詳細
                    "school_zip_code1", // スクール郵便番号1
                    "school_zip_code2", // スクール郵便番号2
                    "school_city", // スクール市区町村
                    "school_address1" // スクール住所1
                    ));
            for(String value : strColumnList) {
                strColumnSb.append(", " + value);
            }
            strColumnSb.delete(0,2);

            // データ(文字列型)を追加するためのStringBuilderを呼び出す。
            StringBuilder strDataSb = new StringBuilder();
            // 各データを一度リスト変数に格納して繰り返し処理で追加する。
            List<String> strDataList = new ArrayList<>();
            strDataList.addAll(Arrays.asList(
                    insertDataForm.getSchoolName(), // スクール名
                    insertDataForm.getSchoolSummary(), // スクール概要
                    insertDataForm.getSchoolDescription(), // スクール詳細
                    insertDataForm.getSchoolZipCode1(), // スクール郵便番号1
                    insertDataForm.getSchoolZipCode2(), // スクール郵便番号2
                    insertDataForm.getSchoolCity(), // スクール市区町村
                    insertDataForm.getSchoolAddress1() // スクール住所1
                    ));
            for(String value : strDataList) {
                strDataSb.append(", '" + value + "'");
            }
            strDataSb.delete(0,2);

            // 列名(整数型)を追加するためのStringBuilderを呼び出す。
            StringBuilder intColumnSb = new StringBuilder();
            // 各列名を一度リスト変数に格納して繰り返し処理で追加する。
            List<String> intColumnList = new ArrayList<>();
            intColumnList.addAll(Arrays.asList(
                    "school_category_id", // スクールカテゴリーID
                    "school_prefecture_id", // スクール都道府県ID
                    "school_fee" // スクール費用
                    ));
            for(String value : intColumnList) {
                intColumnSb.append(", " + value);
            }

            // データ(整数型)を追加するためのStringBuilderを呼び出す。
            StringBuilder intDataSb = new StringBuilder();
            // 各データを一度リスト変数に格納して繰り返し処理で追加する。
            List<Integer> intDataList = new ArrayList<>();
            intDataList.addAll(Arrays.asList(
                    insertDataForm.getSchoolCategoryId(), // スクールカテゴリーID
                    insertDataForm.getSchoolPrefectureId(), // スクール都道府県ID
                    insertDataForm.getSchoolFee().intValue() // スクール費用
                    ));
            for(int value : intDataList) {
                intDataSb.append(", " + value);
            }

            // NULL可またはデフォルト値がある項目は入力値によって列名とデータを追加する。
            // 登録者アカウントIDおよび登録者管理者ID
            int registrantAccountId = insertDataForm.getRegistrantAccountId();
            int registrantAdminId = insertDataForm.getRegistrantAdminId();
            if(registrantAccountId != 0 && registrantAdminId != 0) {
                // 登録者アカウントIDおよび登録者管理者IDが0以外の場合は0を戻す。
                return 0;
            } else if(registrantAccountId != 0) {
                // 登録者アカウントIDが0以外の場合は追加する。
                intColumnSb.append(", registrant_account_id");
                intDataSb.append(", " + registrantAccountId);
            } else if(registrantAdminId != 0) {
                // 登録者管理者IDが0以外の場合は追加する。
                intColumnSb.append(", registrant_admin_id");
                intDataSb.append(", " + registrantAdminId);
            }

            // スクール住所2
            String schoolAddress2 = insertDataForm.getSchoolAddress2();
            if(schoolAddress2 != null) {
                // NULL以外の場合は追加する。
                strColumnSb.append(", school_address2");
                strDataSb.append(", '" + schoolAddress2 + "'");
            }

            // 費用補足
            String supplementaryFee = insertDataForm.getSupplementaryFee();
            if(supplementaryFee != null) {
                // NULL以外の場合は追加する。
                strColumnSb.append(", supplementary_fee");
                strDataSb.append(", '" + supplementaryFee + "'");
            }

            // スクールサイトURL
            String schoolUrl = insertDataForm.getSchoolUrl();
            if(schoolUrl != null) {
                // NULL以外の場合は追加する。
                strColumnSb.append(", school_url");
                strDataSb.append(", '" + schoolUrl + "'");
            }

            // スクール申込可否
            String schoolEntryPropriety = insertDataForm.getSchoolEntryPropriety();
            if(schoolEntryPropriety != null) {
                // NULL以外の場合は追加する。
                strColumnSb.append(", school_entry_propriety");
                strDataSb.append(", '" + schoolEntryPropriety + "'");
            }

            // スクール公開可否
            String schoolReleasePropriety = insertDataForm.getSchoolReleasePropriety();
            if(schoolEntryPropriety != null) {
                // NULL以外の場合は追加する。
                strColumnSb.append(", school_release_propriety");
                strDataSb.append(", '" + schoolReleasePropriety + "'");
            }

            // 一覧画面画像
            String summaryImageFileName = insertDataForm.getSummaryImageFileName();
            if(summaryImageFileName != null) {
                // NULL以外の場合は追加する。
                strColumnSb.append(", summary_image_file_name");
                strDataSb.append(", '" + summaryImageFileName + "'");
            }

            // 詳細画面画像1
            String detailImage1FileName = insertDataForm.getDetailImage1FileName();
            if(detailImage1FileName != null) {
                // NULL以外の場合は追加する。
                strColumnSb.append(", detail_image1_file_name");
                strDataSb.append(", '" + detailImage1FileName + "'");
            }

            // 詳細画面画像2
            String detailImage2FileName = insertDataForm.getDetailImage2FileName();
            if(detailImage2FileName != null) {
                // NULL以外の場合は追加する。
                strColumnSb.append(", detail_image2_file_name");
                strDataSb.append(", '" + detailImage2FileName + "'");
            }

            // 詳細画面画像3
            String detailImage3FileName = insertDataForm.getDetailImage3FileName();
            if(detailImage3FileName != null) {
                // NULL以外の場合は追加する。
                strColumnSb.append(", detail_image3_file_name");
                strDataSb.append(", '" + detailImage3FileName + "'");
            }

            // 詳細画面画像4
            String detailImage4FileName = insertDataForm.getDetailImage4FileName();
            if(detailImage4FileName != null) {
                // NULL以外の場合は追加する。
                strColumnSb.append(", detail_image4_file_name");
                strDataSb.append(", '" + detailImage4FileName + "'");
            }

            // 詳細画面画像5
            String detailImage5FileName = insertDataForm.getDetailImage5FileName();
            if(detailImage5FileName != null) {
                // NULL以外の場合は追加する。
                strColumnSb.append(", detail_image5_file_name");
                strDataSb.append(", '" + detailImage5FileName + "'");
            }

            // 詳細画面画像6
            String detailImage6FileName = insertDataForm.getDetailImage6FileName();
            if(detailImage6FileName != null) {
                // NULL以外の場合は追加する。
                strColumnSb.append(", detail_image6_file_name");
                strDataSb.append(", '" + detailImage6FileName + "'");
            }

            // 詳細画面画像7
            String detailImage7FileName = insertDataForm.getDetailImage7FileName();
            if(detailImage7FileName != null) {
                // NULL以外の場合は追加する。
                strColumnSb.append(", detail_image7_file_name");
                strDataSb.append(", '" + detailImage7FileName + "'");
            }

            // 詳細画面画像8
            String detailImage8FileName = insertDataForm.getDetailImage8FileName();
            if(detailImage8FileName != null) {
                // NULL以外の場合は追加する。
                strColumnSb.append(", detail_image8_file_name");
                strDataSb.append(", '" + detailImage8FileName + "'");
            }

            intColumnSb.append(") VALUES(");
            intDataSb.append(")");

            // StringBuilderで生成した文をString型に変換して結合してINSERT全文を生成する。
            String sql
                    = tableSql
                    + strColumnSb.toString()
                    + intColumnSb.toString()
                    + strDataSb.toString()
                    + intDataSb.toString();

            // 主キーの列名を変数に格納する。
            String[] keyColNames = {"school_id"};
            // DBとの接続状態を取得する。
            connection = getConnection();
            // DBにINSERT文を送信するために変数sqlと主キーの列名を格納した変数をPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql, keyColNames);
            // INSERT文を実行する。
            ps.executeUpdate();
            // 実行結果から自動採番されたスクールIDを取得して変数に格納する。
            ResultSet rs = ps.getGeneratedKeys();

            // スクールIDを格納するための変数を生成する。
            int schoolId = 0;
            while(rs.next()) {
                // 取得したスクールIDを変数に格納する。
                schoolId = rs.getInt(1);
               break;
            }

            // スクールIDを戻す。
            return schoolId;
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
