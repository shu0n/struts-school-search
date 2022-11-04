package dao.school.repack;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import actionform.SchoolExtendActionForm;
import dao.school.SelectSchoolJoinDAO;
import dao.school.sql.SchoolSelectJoinWhereActionForm;
import exception.NoDataExistException;

public class GetSchoolDataRepack {

    /**
     * スクールテーブルから特定のスクールIDに紐づくレコードを取得してアクションフォームに格納するためのメソッド
     * @param schoolId スクールID
     * @param inForm スクールのデータを格納するアクションフォーム
     * @return 検索条件に一致したスクールのアクションフォーム
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoDataExistException
     */
    public SchoolExtendActionForm getSchoolData(int schoolId, SchoolExtendActionForm inForm)
            throws ClassNotFoundException, IOException, SQLException, NoDataExistException {
        // DAOメソッドに引き渡すアクションフォームを生成してスクールIDを格納する。
        SchoolSelectJoinWhereActionForm schoolJoinClauseForm = new SchoolSelectJoinWhereActionForm();
        schoolJoinClauseForm.setSchoolId(schoolId);
        // スクールテーブルからスクールIDに紐づくレコードを取得する。
        List<SchoolExtendActionForm> schoolExtendFormList
                = new SelectSchoolJoinDAO().selectMatchedSchool(schoolJoinClauseForm);
        if(schoolExtendFormList.isEmpty()) {
            // 取得結果が0件の場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        }

        // リストから1件目のアクションフォームを取得する。
        SchoolExtendActionForm schoolExtendForm = schoolExtendFormList.get(0);
        // 取得したレコードのデータをアクションフォームに格納する。
        inForm.setSchoolId(schoolId); // スクールID
        inForm.setRegistrantAccountId(schoolExtendForm.getRegistrantAccountId()); // 登録者アカウントID
        inForm.setRegistrantLastName(schoolExtendForm.getRegistrantLastName()); // 登録者姓
        inForm.setRegistrantFirstName(schoolExtendForm.getRegistrantFirstName()); // 登録者名
        inForm.setRegistrantLastNameKana(schoolExtendForm.getRegistrantLastNameKana()); // 登録者姓(フリガナ)
        inForm.setRegistrantFirstNameKana(schoolExtendForm.getRegistrantFirstNameKana()); // 登録者名(フリガナ)
        inForm.setRegistrantAdminId(schoolExtendForm.getRegistrantAdminId()); // 登録者管理者ID
        inForm.setSchoolName(schoolExtendForm.getSchoolName()); // スクール名
        inForm.setSchoolCategoryId(schoolExtendForm.getSchoolCategoryId()); // スクールカテゴリーID
        inForm.setSchoolCategoryName(schoolExtendForm.getSchoolCategoryName()); // スクールカテゴリー名
        inForm.setSchoolSummary(schoolExtendForm.getSchoolSummary()); // スクール概要
        inForm.setSchoolDescription(schoolExtendForm.getSchoolDescription()); // スクール詳細
        inForm.setSchoolZipCode1(schoolExtendForm.getSchoolZipCode1()); // スクール郵便番号1
        inForm.setSchoolZipCode2(schoolExtendForm.getSchoolZipCode2()); // スクール郵便番号2
        inForm.setSchoolPrefectureId(schoolExtendForm.getSchoolPrefectureId()); // スクール都道府県ID
        inForm.setSchoolPrefectureName(schoolExtendForm.getSchoolPrefectureName()); // スクール都道府県名
        inForm.setSchoolCity(schoolExtendForm.getSchoolCity()); // スクール市区町村
        inForm.setSchoolAddress1(schoolExtendForm.getSchoolAddress1()); // スクール住所1
        inForm.setSchoolAddress2(schoolExtendForm.getSchoolAddress2()); // スクール住所2
        inForm.setSchoolFee(schoolExtendForm.getSchoolFee()); // スクール費用
        inForm.setStrSchoolFee(schoolExtendForm.getSchoolFee().toString()); // スクール費用(文字列型)
        inForm.setSupplementaryFee(schoolExtendForm.getSupplementaryFee()); // 費用補足
        inForm.setSchoolUrl(schoolExtendForm.getSchoolUrl()); // スクールURL
        inForm.setSchoolReleasePropriety(schoolExtendForm.getSchoolReleasePropriety()); // スクール公開可否
        inForm.setSchoolEntryPropriety(schoolExtendForm.getSchoolEntryPropriety()); // スクール申込可否
        inForm.setSummaryImageFileName(schoolExtendForm.getSummaryImageFileName()); // 一覧画面画像ファイル名
        inForm.setDetailImage1FileName(schoolExtendForm.getDetailImage1FileName()); // 詳細画面画像1ファイル名
        inForm.setDetailImage2FileName(schoolExtendForm.getDetailImage2FileName()); // 詳細画面画像2ファイル名
        inForm.setDetailImage3FileName(schoolExtendForm.getDetailImage3FileName()); // 詳細画面画像3ファイル名
        inForm.setDetailImage4FileName(schoolExtendForm.getDetailImage4FileName()); // 詳細画面画像4ファイル名
        inForm.setDetailImage5FileName(schoolExtendForm.getDetailImage5FileName()); // 詳細画面画像5ファイル名
        inForm.setDetailImage6FileName(schoolExtendForm.getDetailImage6FileName()); // 詳細画面画像6ファイル名
        inForm.setDetailImage7FileName(schoolExtendForm.getDetailImage7FileName()); // 詳細画面画像7ファイル名
        inForm.setDetailImage8FileName(schoolExtendForm.getDetailImage8FileName()); // 詳細画面画像8ファイル名
        inForm.setSchoolRegisteredAt(schoolExtendForm.getSchoolRegisteredAt()); // スクール登録日時
        inForm.setSchoolUpdatedAt(schoolExtendForm.getSchoolUpdatedAt()); // スクール更新日時

        // 文字列型に変換したスクール登録日時をアクションフォームに格納する。
        inForm.setStrSchoolRegisteredAt(getStrTimestamp(schoolExtendForm.getSchoolRegisteredAt()));
        // 文字列型に変換したスクール更新日時をアクションフォームに格納する。
        inForm.setStrSchoolUpdatedAt(getStrTimestamp(schoolExtendForm.getSchoolUpdatedAt()));

        // 画像の配置先パスを取得する。
        String imagePath = getImagePath();

        // 一覧画像画像ファイル名を取得する。
        String summaryImageFileName = inForm.getSummaryImageFileName();
        if(summaryImageFileName != null) {
            // 一覧画像画像ファイル名を取得できた場合は配置先パスと結合してアクションフォームに格納する。
            inForm.setSummaryImageFilePath(imagePath + summaryImageFileName);
        }else {
            // 上記以外の場合は空文字を設定する。
            inForm.setSummaryImageFilePath("");
        }

        // 詳細画面画像ファイル名をマップに格納する。
        Map<Integer, String> detailImageFileNameMap = new HashMap<>();
        detailImageFileNameMap.put(1, inForm.getDetailImage1FileName()); // 詳細画面画像1ファイル名
        detailImageFileNameMap.put(2, inForm.getDetailImage2FileName()); // 詳細画面画像2ファイル名
        detailImageFileNameMap.put(3, inForm.getDetailImage3FileName()); // 詳細画面画像3ファイル名
        detailImageFileNameMap.put(4, inForm.getDetailImage4FileName()); // 詳細画面画像4ファイル名
        detailImageFileNameMap.put(5, inForm.getDetailImage5FileName()); // 詳細画面画像5ファイル名
        detailImageFileNameMap.put(6, inForm.getDetailImage6FileName()); // 詳細画面画像6ファイル名
        detailImageFileNameMap.put(7, inForm.getDetailImage7FileName()); // 詳細画面画像7ファイル名
        detailImageFileNameMap.put(8, inForm.getDetailImage8FileName()); // 詳細画面画像8ファイル名
        // 詳細画面画像ファイルパスを取得してアクションフォームに格納する。
        for(Map.Entry<Integer, String> entry : detailImageFileNameMap.entrySet()) {
            String detailFileName = entry.getValue(); // 詳細画面画像ファイル名
            // キーに応じて格納するプロパティを判定する。
            switch(entry.getKey()) {
                case 1:
                    if(detailFileName != null) {
                        // ファイル名が存在する場合は配置先パスと結合してアクションフォームに格納する。
                        inForm.setDetailImage1FilePath(imagePath + detailFileName);
                    } else {
                        // 上記以外の場合は空文字を設定する。
                        inForm.setDetailImage1FilePath("");
                    }
                    break;
                case 2:
                    if(detailFileName != null) {
                        // ファイル名が存在する場合は配置先パスと結合してアクションフォームに格納する。
                        inForm.setDetailImage2FilePath(imagePath + detailFileName);
                    } else {
                        // 上記以外の場合は空文字を設定する。
                        inForm.setDetailImage2FilePath("");
                    }
                    break;
                case 3:
                    if(detailFileName != null) {
                        // ファイル名が存在する場合は配置先パスと結合してアクションフォームに格納する。
                        inForm.setDetailImage3FilePath(imagePath + detailFileName);
                    } else {
                        // 上記以外の場合は空文字を設定する。
                        inForm.setDetailImage3FilePath("");
                    }
                    break;
                case 4:
                    if(detailFileName != null) {
                        // ファイル名が存在する場合は配置先パスと結合してアクションフォームに格納する。
                        inForm.setDetailImage4FilePath(imagePath + detailFileName);
                    } else {
                        // 上記以外の場合は空文字を設定する。
                        inForm.setDetailImage4FilePath("");
                    }
                    break;
                case 5:
                    if(detailFileName != null) {
                        // ファイル名が存在する場合は配置先パスと結合してアクションフォームに格納する。
                        inForm.setDetailImage5FilePath(imagePath + detailFileName);
                    } else {
                        // 上記以外の場合は空文字を設定する。
                        inForm.setDetailImage5FilePath("");
                    }
                    break;
                case 6:
                    if(detailFileName != null) {
                        // ファイル名が存在する場合は配置先パスと結合してアクションフォームに格納する。
                        inForm.setDetailImage6FilePath(imagePath + detailFileName);
                    } else {
                        // 上記以外の場合は空文字を設定する。
                        inForm.setDetailImage6FilePath("");
                    }
                    break;
                case 7:
                    if(detailFileName != null) {
                        // ファイル名が存在する場合は配置先パスと結合してアクションフォームに格納する。
                        inForm.setDetailImage7FilePath(imagePath + detailFileName);
                    } else {
                        // 上記以外の場合は空文字を設定する。
                        inForm.setDetailImage7FilePath("");
                    }
                    break;
                case 8:
                    if(detailFileName != null) {
                        // ファイル名が存在する場合は配置先パスと結合してアクションフォームに格納する。
                        inForm.setDetailImage8FilePath(imagePath + detailFileName);
                    } else {
                        // 上記以外の場合は空文字を設定する。
                        inForm.setDetailImage8FilePath("");
                    }
                    break;
            }
        }

        // スクールの情報を格納したアクションフォームを戻す。
        return inForm;
    }

    /**
     * スクール登録者のプロフィール情報を取得するためのメソッド
     * @param schoolId スクールID
     * @param inForm スクールのデータを格納するアクションフォーム
     * @return 検索条件に一致したスクールのアクションフォーム
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoDataExistException
     */
    public SchoolExtendActionForm getRegistrantProfile(int schoolId, SchoolExtendActionForm inForm)
            throws ClassNotFoundException, IOException, SQLException, NoDataExistException {
        // DAOメソッドに引き渡すアクションフォームを生成してスクールIDを格納する。
        SchoolSelectJoinWhereActionForm schoolSelectJoinWhereForm = new SchoolSelectJoinWhereActionForm();
        schoolSelectJoinWhereForm.setSchoolId(schoolId);
        // アカウントテーブルからアカウントIDに紐づくレコードを取得する。
        List<SchoolExtendActionForm> schoolExtendFormList
                = new SelectSchoolJoinDAO().selectMatchedSchool(schoolSelectJoinWhereForm);
        if(schoolExtendFormList.isEmpty()) {
            // 取得結果が0件の場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        }

        // リストの1件目のアクションフォームを取得する。
        SchoolExtendActionForm schoolExtendForm = schoolExtendFormList.get(0);
        if(schoolExtendForm.getRegistrantAccountId() == 0) {
            // 登録者アカウントIDが0の場合はNULLを戻す。
            return null;
        }
        // 取得したレコードのデータをアクションフォームに格納する。
        inForm.setSchoolId(schoolId); // スクールID
        inForm.setRegistrantLastName(schoolExtendForm.getRegistrantLastName()); // 登録者姓
        inForm.setRegistrantFirstName(schoolExtendForm.getRegistrantFirstName()); // 登録者名
        inForm.setRegistrantLastNameKana(schoolExtendForm.getRegistrantLastNameKana()); // 登録者姓(フリガナ)
        inForm.setRegistrantFirstNameKana(schoolExtendForm.getRegistrantFirstNameKana()); // 登録者名(フリガナ)
        inForm.setSelfIntroduction(schoolExtendForm.getSelfIntroduction()); // 自己紹介
        inForm.setSchoolName(schoolExtendForm.getSchoolName()); // スクール名

        // プロフィール画像のファイル名を取得する。
        String profileImagefileName = schoolExtendForm.getProfileImageFileName();
        if(profileImagefileName != null) {
            // 画像の配置先パスを取得する。
            String imagePath = getImagePath();
            // 取得した配置先パスとプロフィール画像のファイル名を結合してアクションフォームに格納する。
            inForm.setProfileImageFilePath(imagePath + profileImagefileName);
        } else {
            // 上記以外の場合は空文字を設定する。
            inForm.setProfileImageFilePath("");
        }

        // スクール登録者のプロフィール情報を格納したアクションフォームを戻す。
        return inForm;
    }

    /**
     * 日時(文字列型)を取得するためのメソッド
     */
    String getStrTimestamp(Timestamp timstamp) {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(timstamp);
    }

    /**
     * 画像の配置先パスを取得するためのメソッド
     */
    String getImagePath() throws IOException {
        // プロパティ情報を扱うクラスのインスタンスを生成する。
        Properties props = new Properties();
        // プロパティファイル名をもとに内容を読み込む。
        InputStream inputStream
                = GetSchoolDataRepack.class.getClassLoader().getResourceAsStream("environment.properties");
        if(Objects.isNull(inputStream)) {
            // 読み込んだオブジェクトがNULLの場合はI/O例外を投げる。
            throw new IOException();
        }
        // 読み込んだ内容をもとにプロパティ情報を生成する。
        props.load(inputStream);

        // プロパティ情報から画像の配置先パスを取得して戻す。
        return props.getProperty("img.path");
    }

}
