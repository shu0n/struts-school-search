package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.upload.FormFile;

public class CsvUtil {

    /**
     * データをCSVファイルとしてレスポンスに追加するためのメソッド
     * @param headerArray ヘッダー行の項目を格納した配列
     * @param dataArrayList データ配列を格納したリスト
     * @param fileName ファイル名
     * @param response レスポンス
     * @return ヘッダー行の項目とデータを書き込んだレスポンス
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public HttpServletResponse writeCsv(
            String[] headerArray,
            List<String[]> dataArrayList,
            String fileName,
            HttpServletResponse response)
            throws IllegalArgumentException, IOException {
        if(ArrayUtils.isEmpty(headerArray) || StringUtils.isEmpty(fileName)) {
            // ヘッダー行またはファイル名が空要素または空文字またはNULLの場合は不正引数例外を投げる。
            throw new IllegalArgumentException();
        }

        // レスポンスにコンテンツタイプを設定する。
        response.setContentType("text/csv;charset=UTF8");
        // レスポンスにHTTPヘッダーを設定する。
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // データを書き込むためのストリームを取得する。
        PrintWriter writer = response.getWriter();

        // ヘッダー行の文字列を格納する変数を生成する。
        String headerRow = "";
        for(String header: headerArray) {
            // 配列の各文字列をカンマで区切って変数に結合する。
            headerRow += header + ",";
        }
        // 生成されたヘッダー行の文字列から最後のカンマを削除する。
        headerRow = headerRow.substring(0, headerRow.length() - 1);
        // ストリームにヘッダー行を書き込み改行する。
        writer.println(headerRow);

        if(!CollectionUtils.isEmpty(dataArrayList)) {
            // データ配列を格納したリストがNULLまたは空要素以外の場合はリストからデータ配列を取得してデータ行として順次書き込む。
            for(String[] dataArray: dataArrayList) {
                // データ行の文字列を格納するためのStringBuilderを呼び出す。
                StringBuilder dataRow = new StringBuilder();
                for(String data: dataArray) {
                    // 配列の各文字列をカンマで区切って変数に結合する。
                    dataRow.append("," + data);
                }
                if(!StringUtils.isEmpty(dataRow.toString())) {
                    // データ行が1行以上存在する場合

                    // 生成された文字列の最初のカンマを削除する。
                    dataRow.delete(0, 1);
                    // ストリームににデータ行を書き込み改行する。
                    writer.println(dataRow);
                }
            }
        }

        // CSVファイルが追加されたレスポンスを戻す。
        return response;
    }

    /**
     * アップロードされたCSVファイルから取得したデータをリストに格納するためのメソッド
     * @param uploadFile アップロードファイル
     * @return CSVファイルのデータを格納したリスト
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<String[]> readCsv(FormFile uploadFile) throws FileNotFoundException, IOException {
        try {
            // アップロードされたCSVファイルを読み込む。
            BufferedReader buffer = new BufferedReader(new InputStreamReader(uploadFile.getInputStream()));

            // CSVファイルのデータを格納するためのリストを生成する。
            List<String[]> dataList = new ArrayList<>();
            // 1行ごとのデータを格納する変数を生成する。
            String line;
            // 行数分、処理を繰り返す。
            while ((line = buffer.readLine()) != null) {
                // 行データのバイト数を取得する。
                byte[] b = line.getBytes();
                // バイト行を文字列に変換して変数に格納する。
                line = new String(b, "UTF-8");
                // 変数に格納された文字列をカンマで区切って配列に格納する。
                String[] dataArray = line.split(",", -1);
                // 配列をリストに格納する。
                dataList.add(dataArray);
            }

            // ストリームを閉じる。
            buffer.close();

            // データの配列を格納したリストを戻す。
            return dataList;
        } finally {
            // 一時ディレクトリに保存されたアップロード画像を削除する。
            uploadFile.destroy();
        }
    }

}
