package model.entry;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import actionform.EntryExtendActionForm;
import util.CsvUtil;

public class EntryFileModel {

    /**
     * 申込受付のデータを書き込んだCSVファイルをレスポンスに追加するためのメソッド
     * @param entryExtendFormList 申込のデータを格納したアクションフォームのリスト
     * @param response レスポンス
     * @return response CSVファイルのデータを追加したレスポンス
     * @throws IOException
     */
    public HttpServletResponse setDownloadCsvFileData(
            List<EntryExtendActionForm> entryExtendFormList,
            HttpServletResponse response) throws IOException {
        // ヘッダー行の配列を生成する。
        String[] headerArray =
                {"申込ID", "申込者アカウントID", "申込日時", "申込ステータスID", "申込ステータス名",
                "申込先スクールID", "申込先スクール名", "申込者姓", "申込者名", "申込者姓(フリガナ)", "申込者名(フリガナ)",
                "質問", "申込更新日時"};

        // データ行の配列を格納するためのリストを生成する。
        List<String[]> dataArrayList = new ArrayList<>();
        for(EntryExtendActionForm eachForm: entryExtendFormList) {
            // データ行を格納する配列を生成する。
            String[] dataArray = new String[13];
            dataArray[0] = String.valueOf(eachForm.getEntryId());
            dataArray[1] = String.valueOf(eachForm.getRegistrantAccountId());
            dataArray[2] = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(eachForm.getEntriedAt());
            dataArray[3] = String.valueOf(eachForm.getEntryStatusId());
            dataArray[4] = eachForm.getEntryStatusName();
            dataArray[5] = String.valueOf(eachForm.getEntrySchoolId());
            dataArray[6] = eachForm.getEntrySchoolName();
            dataArray[7] = eachForm.getRegistrantLastName();
            dataArray[8] = eachForm.getRegistrantFirstName();
            dataArray[9] = eachForm.getRegistrantLastNameKana();
            dataArray[10] = eachForm.getRegistrantFirstNameKana();
            if(StringUtils.isEmpty(eachForm.getEntryQuestion())) {
                dataArray[11] = "";
            } else {
                dataArray[11] = eachForm.getEntryQuestion().replaceAll("\\r\\n|\\r|\\n", "");
            }
            dataArray[12] = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(eachForm.getEntryUpdatedAt());

            // リストにデータ行の配列を追加する。
            dataArrayList.add(dataArray);
        }

        // ファイル名を生成する。
        String fileName
                = "receivedEntries_"
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
