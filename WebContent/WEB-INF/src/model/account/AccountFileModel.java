package model.account;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.upload.FormFile;

import actionform.AccountExtendActionForm;
import util.CsvUtil;
import util.ImageFileUtil;

public class AccountFileModel {

    /**
     * リクエストにアップロードされたプロフィール画像のパスとファイル名を追加するためのメソッド
     * @param uploadFile アップロードファイル
     * @param request リクエスト
     * @return アップロード画像の情報を書き込んだリクエスト
     * @throws IOException
     */
    public HttpServletRequest setFileInfo(FormFile uploadFile, HttpServletRequest request) throws IOException {
        // ファイル名と格納先パスを格納したマップを取得する。
        Map<String, String> imageMap = new ImageFileUtil().getImageMap(uploadFile);
        // プロフィール画像のパスをマップから取得してリクエストに追加する。
        request.setAttribute("profileImageFilePath", imageMap.get("imgPath"));
        // プロフィール画像のファイル名をマップから取得してリクエストに追加する。
        request.setAttribute("profileImageFileName", imageMap.get("fileName"));

        // アップロードされた画像の情報を追加したリクエストを戻す。
        return request;
    }

    /**
     * アカウントのデータを書き込んだCSVファイルをレスポンスに追加するためのメソッド
     * @param accountExtendFormList アカウントのデータを格納したアクションフォームのリスト
     * @param response レスポンス
     * @return CSVファイルのデータを追加したレスポンス
     * @throws IOException
     */
    public HttpServletResponse setDownloadCsvFileData(
            List<AccountExtendActionForm> accountExtendFormList,
            HttpServletResponse response) throws IOException {
        // ヘッダー行の配列を生成する。
        String[] headerArray =
                {"アカウントID", "姓", "名", "姓(フリガナ)", "名(フリガナ)",
                "性別ID", "性別名", "生年月日", "都道府県ID", "都道府県名", "メールアドレス",
                "プロフィール画像", "自己紹介", "アカウントステータス", "アカウント作成日時", "アカウント更新日時"};

        // データ行の配列を格納するためのリストを生成する。
        List<String[]> dataArrayList = new ArrayList<>();
        // リストにデータ行の配列を追加する。
        for(AccountExtendActionForm eachForm: accountExtendFormList) {
            // データ行を格納する配列を生成する。
            String[] dataArray = new String[16];
            dataArray[0] = String.valueOf(eachForm.getAccountId());
            dataArray[1] = eachForm.getLastName();
            dataArray[2] = eachForm.getFirstName();
            dataArray[3] = eachForm.getLastNameKana();
            dataArray[4] = eachForm.getFirstNameKana();
            if(eachForm.getSexId() == 0) {
                dataArray[5] = "";
                dataArray[6] = "";
            } else {
                dataArray[5] = String.valueOf(eachForm.getSexId());
                dataArray[6] = eachForm.getSexName();
            }
            if(StringUtils.isEmpty(eachForm.getStrBirthDate())) {
                dataArray[7] = "";
            } else {
                dataArray[7] = eachForm.getStrBirthDate();
            }
            dataArray[8] = String.valueOf(eachForm.getPrefectureId());
            dataArray[9] = eachForm.getPrefectureName();
            dataArray[10] = eachForm.getMailAddress();
            if(StringUtils.isEmpty(eachForm.getProfileImageFileName())) {
                dataArray[11] = "";
            } else {
                dataArray[11] = eachForm.getProfileImageFileName();
            }
            if(StringUtils.isEmpty(eachForm.getSelfIntroduction())) {
                dataArray[12] = "";
            } else {
                dataArray[12] = eachForm.getSelfIntroduction().replaceAll("\\r\\n|\\r|\\n", "");
            }
            dataArray[13] = eachForm.getAccountStatus();
            dataArray[14] = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(eachForm.getAccountCreatedAt());
            dataArray[15] = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(eachForm.getAccountUpdatedAt());
            // リストにデータ行の配列を追加する。
            dataArrayList.add(dataArray);
        }

        // ファイル名を生成する。
        String fileName
                = "accounts_"
                + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(getCurrentTimestamp())
                + ".csv";

        // レスポンスにCSVファイルを追加して戻す。
        return new CsvUtil().writeCsv(headerArray, dataArrayList, fileName, response);
    }

    /**
     * 現在日時(日時型)を取得するためのメソッド
     */
    Timestamp getCurrentTimestamp() {
        // 現在日時(日時型)を戻す。
        return new Timestamp(System.currentTimeMillis());
    }

}
