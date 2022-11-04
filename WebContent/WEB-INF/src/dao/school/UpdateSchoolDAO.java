package dao.school;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import dao.ConnectorDAO;
import dao.school.sql.SchoolUpdateDataActionForm;

public class UpdateSchoolDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * 指定したスクールIDに紐づくスクールテーブルのレコードを更新するためのメソッド
     * @param updateDataForm 更新するデータを格納したアクションフォーム
     * @return 実行結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean updateSchool(SchoolUpdateDataActionForm updateDataForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルを指定するためのUPDATE文を生成する。
            String tableSql = "UPDATE schools SET ";

            // 更新する列名とデータを指定するためのStringBuilderを呼び出す。
            StringBuilder dataSb = new StringBuilder();

            // 登録者アカウントIDおよび登録者管理者ID
            int registrantAccountId = updateDataForm.getRegistrantAccountId();
            int registrantAdminId = updateDataForm.getRegistrantAdminId();
            if(registrantAccountId != 0 && registrantAdminId != 0) {
                // 登録者アカウントIDおよび登録者管理者IDが0以外の場合はfalseを戻す。
                return false;
            } else if(registrantAccountId != 0) {
                // 登録者アカウントIDが0以外の場合は追加する。
                dataSb.append(", registrant_account_id=" + registrantAccountId);
                dataSb.append(", registrant_admin_id=null");
            } else if(registrantAdminId != 0) {
                // 登録者管理者IDが0以外の場合は追加する。
                dataSb.append(", registrant_account_id=null");
                dataSb.append(", registrant_admin_id=" + registrantAdminId);
            }

            // スクール名
            String schoolName = updateDataForm.getSchoolName();
            if(schoolName != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", school_name='" + schoolName + "'");
            }

            // スクールカテゴリーID
            int schoolCategoryId = updateDataForm.getSchoolCategoryId();
            if(schoolCategoryId != 0) {
                // 0以外の場合は追加する。
                dataSb.append(", school_category_id=" + schoolCategoryId);
            }

            // スクール概要
            String schoolSummary = updateDataForm.getSchoolSummary();
            if(schoolSummary != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", school_summary='" + schoolSummary + "'");
            }

            // スクール詳細
            String schoolDescription = updateDataForm.getSchoolDescription();
            if(schoolDescription != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", school_description='" + schoolDescription + "'");
            }

            // スクール郵便番号1
            String schoolZipCode1 = updateDataForm.getSchoolZipCode1();
            if(schoolZipCode1 != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", school_zip_code1='" + schoolZipCode1 + "'");
            }

            // スクール郵便番号2
            String schoolZipCode2 = updateDataForm.getSchoolZipCode2();
            if(schoolZipCode2 != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", school_zip_code2='" + schoolZipCode2 + "'");
            }

            // 都道府県ID
            int schoolPrefectureId = updateDataForm.getSchoolPrefectureId();
            if(schoolPrefectureId != 0) {
                // 0以外の場合は追加する。
                dataSb.append(", school_prefecture_id=" + schoolPrefectureId);
            }

            // スクール市区町村
            String schoolCity = updateDataForm.getSchoolCity();
            if(schoolCity != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", school_city='" + schoolCity + "'");
            }

            // スクール住所1
            String schoolAddress1 = updateDataForm.getSchoolAddress1();
            if(schoolAddress1 != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", school_address1='" + schoolAddress1 + "'");
            }

            // スクール住所2
            String schoolAddress2 = updateDataForm.getSchoolAddress2();
            if(schoolAddress2 != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", school_address2='" + schoolAddress2 + "'");
            }

            // スクール費用
            BigDecimal schoolFee = updateDataForm.getSchoolFee();
            if(schoolFee != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", school_fee=" + schoolFee);
            }

            // 費用補足
            String supplementaryFee = updateDataForm.getSupplementaryFee();
            if(supplementaryFee != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", supplementary_fee='" + supplementaryFee + "'");
            }

            // スクールサイトURL
            String schoolUrl = updateDataForm.getSchoolUrl();
            if(schoolUrl != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", school_url='" + schoolUrl + "'");
            }

            // スクール申込可否
            String schoolEntryPropriety = updateDataForm.getSchoolEntryPropriety();
            if(schoolEntryPropriety != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", school_entry_propriety='" + schoolEntryPropriety + "'");
            }

            // スクール公開可否
            String schoolReleasePropriety = updateDataForm.getSchoolReleasePropriety();
            if(schoolReleasePropriety != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", school_release_propriety='" + schoolReleasePropriety + "'");
            }

            // 一覧画面画像
            String summaryImageFileName = updateDataForm.getSummaryImageFileName();
            if(summaryImageFileName != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", summary_image_file_name='" + summaryImageFileName + "'");
            }

            // 詳細画面画像1
            String detailImage1FileName = updateDataForm.getDetailImage1FileName();
            if(detailImage1FileName != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", detail_image1_file_name='" + detailImage1FileName + "'");
            }

            // 詳細画面画像2
            String detailImage2FileName = updateDataForm.getDetailImage2FileName();
            if(detailImage2FileName != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", detail_image2_file_name='" + detailImage2FileName + "'");
            }

            // 詳細画面画像3
            String detailImage3FileName = updateDataForm.getDetailImage3FileName();
            if(detailImage3FileName != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", detail_image3_file_name='" + detailImage3FileName + "'");
            }

            // 詳細画面画像4
            String detailImage4FileName = updateDataForm.getDetailImage4FileName();
            if(detailImage4FileName != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", detail_image4_file_name='" + detailImage4FileName + "'");
            }

            // 詳細画面画像5
            String detailImage5FileName = updateDataForm.getDetailImage5FileName();
            if(detailImage5FileName != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", detail_image5_file_name='" + detailImage5FileName + "'");
            }

            // 詳細画面画像6
            String detailImage6FileName = updateDataForm.getDetailImage6FileName();
            if(detailImage6FileName != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", detail_image6_file_name='" + detailImage6FileName + "'");
            }

            // 詳細画面画像7
            String detailImage7FileName = updateDataForm.getDetailImage7FileName();
            if(detailImage7FileName != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", detail_image7_file_name='" + detailImage7FileName + "'");
            }

            // 詳細画面画像8
            String detailImage8FileName = updateDataForm.getDetailImage8FileName();
            if(detailImage8FileName != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", detail_image8_file_name='" + detailImage8FileName + "'");
            }

            // 先頭の", "を削除する。
            dataSb.delete(0,2);

            // 現在日時(文字列型)を取得する。
            String now = getStringCurrentTimestamp();
            // スクール更新日時
            dataSb.append(
                    ", school_updated_at=TO_TIMESTAMP('"
                    + now
                    + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");

            // 更新するレコードを指定するためのWHERE文を生成する。
            String whereSql = "WHERE school_id=" + updateDataForm.getSchoolId();

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
