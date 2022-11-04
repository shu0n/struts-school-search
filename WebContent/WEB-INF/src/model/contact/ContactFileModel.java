package model.contact;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import actionform.ContactExtendActionForm;
import util.CsvUtil;

public class ContactFileModel {

    /**
     * お問合せのデータを書き込んだCSVファイルをレスポンスに追加するためのメソッド
     * @param contactExtendFormList お問合せのデータを格納したアクションフォームのリスト
     * @param response レスポンス
     * @return CSVファイルのデータを追加したレスポンス
     * @throws IOException
     */
    public HttpServletResponse setDownloadCsvFileData(
            List<ContactExtendActionForm> contactExtendFormList,
            HttpServletResponse response) throws IOException {
        // ヘッダー行の配列を生成する。
        String[] headerArray =
                {"お問合せID", "お問合せ者アカウントID", "お問合せ日時",
                "お問合せステータスID", "お問合せステータス名",
                "お問合せ者姓", "お問合せ者名", "お問合せ者姓(フリガナ)",
                "お問合せ者名(フリガナ)", "お問合せ内容", "お問合せ更新日時"};

        // データ行の配列を格納するためのリストを生成する。
        List<String[]> dataArrayList = new ArrayList<>();
        // リストにデータ行の配列を追加する。
        for(ContactExtendActionForm eachForm: contactExtendFormList) {
            // データ行を格納する配列を生成する。
            String[] dataArray = new String[11];
            dataArray[0] = String.valueOf(eachForm.getContactId());
            if(eachForm.getContactAccountId() == 0) {
                dataArray[1] = "";
            } else {
                dataArray[1] = String.valueOf(eachForm.getContactAccountId());
            }
            dataArray[2] = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(eachForm.getContactedAt());
            dataArray[3] = String.valueOf(eachForm.getContactStatusId());
            dataArray[4] = eachForm.getContactStatusName();
            dataArray[5] = eachForm.getContactLastName();
            dataArray[6] = eachForm.getContactFirstName();
            dataArray[7] = eachForm.getContactLastNameKana();
            dataArray[8] = eachForm.getContactFirstNameKana();
            dataArray[9] = eachForm.getContactContent().replaceAll("\\r\\n|\\r|\\n", "");
            dataArray[10] = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(eachForm.getContactUpdatedAt());

            // リストにデータ行の配列を追加する。
            dataArrayList.add(dataArray);
        }

        // ファイル名を生成する。
        String fileName
                = "contacts_"
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
