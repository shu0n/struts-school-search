package model.category;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.upload.FormFile;

import actionform.CategoryExtendActionForm;
import dao.category.InsertCategoryDAO;
import dao.category.UpdateCategoryDAO;
import dao.category.sql.CategoryInsertDataActionForm;
import dao.category.sql.CategoryUpdateDataActionForm;
import exception.ReferredByCategoryException;
import exception.ReferredBySchoolException;
import util.CsvUtil;

public class CategoryFileModel {

    /**
     * カテゴリーのデータを書き込んだCSVファイルをレスポンスに追加するためのメソッド
     * @param categoryExtendFormList カテゴリーのデータを格納したアクションフォームのリスト
     * @param response レスポンス
     * @return CSVファイルのデータを追加したレスポンス
     * @throws IOException
     */
    public HttpServletResponse setDownloadCsvFileData(
            List<CategoryExtendActionForm> categoryExtendFormList,
            HttpServletResponse response) throws IOException {
        // ヘッダー行の配列を生成する。
        String[] headerArray = {"カテゴリーID", "カテゴリー名", "上位カテゴリーID", "上位カテゴリー名", "カテゴリーステータス"};

        // データ行の配列を格納するためのリストを生成する。
        List<String[]> dataArrayList = new ArrayList<>();
        // リストにデータ行の配列を追加する。
        for(CategoryExtendActionForm eachForm: categoryExtendFormList) {
            // データ行を格納する配列を生成する。
            String[] dataArray = new String[5];
            dataArray[0] = String.valueOf(eachForm.getCategoryId());
            dataArray[1] = eachForm.getCategoryName();
            if(eachForm.getSeniorCategoryId() == 0) {
                dataArray[2] = "";
            } else {
                dataArray[2] = String.valueOf(eachForm.getSeniorCategoryId());
            }
            if(StringUtils.isEmpty(eachForm.getSeniorCategoryName())) {
                dataArray[3] = "";
            } else {
                dataArray[3] = eachForm.getSeniorCategoryName();
            }
            dataArray[4] = eachForm.getCategoryStatus();

            // リストにデータ行の配列を追加する。
            dataArrayList.add(dataArray);
        }

        // ファイル名を生成する。
        String fileName
                = "categories_"
                + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(getCurrentTimestamp())
                + ".csv";

        // レスポンスにCSVファイルを追加して戻す。
        return new CsvUtil().writeCsv(headerArray, dataArrayList, fileName, response);
    }

    /**
     * アップロード用CSVファイルのテンプレートをレスポンスに追加するためのメソッド
     * @param response レスポンス
     * @return CSVファイルのデータを追加したレスポンス
     * @throws IOException
     */
    public HttpServletResponse setDownloadTemplateCsvFileData(HttpServletResponse response) throws IOException {
        // ヘッダー行の配列を生成する。
        String[] headerArray = {"カテゴリーID", "カテゴリー名", "上位カテゴリーID", "カテゴリーステータス"};

        // ファイル名を生成する。
        String fileName = "categories_for_upload_template.csv";

        // レスポンスにテンプレートのCSVファイルを追加する。
        return new CsvUtil().writeCsv(headerArray, null, fileName, response);
    }

    /**
     * カテゴリーのデータが書き込まれているCSVファイルを読み込んでレコードを登録または更新するためのメソッド
     * @param uploadFile アップロードされたCSVファイルのデータ
     * @throws IllegalArgumentException
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ReferredBySchoolException
     * @throws ReferredByCategoryException
     */
    public void upsertCsvFileData(FormFile uploadFile) throws
            IllegalArgumentException,
            FileNotFoundException,
            IOException,
            ClassNotFoundException,
            SQLException,
            ReferredBySchoolException,
            ReferredByCategoryException {
        // CSVファイルをもとにデータを格納したリストを取得する。
        List<String[]> dataList = new CsvUtil().readCsv(uploadFile);

        // アクションフォームを格納するためのリストを生成する。
        // 新規登録用データのリスト
        List<CategoryInsertDataActionForm> insertFormList = new ArrayList<>();
        // 更新用データのリスト
        List<CategoryUpdateDataActionForm> updateFormList = new ArrayList<>();
        // リストのデータをアクションフォームに格納する。
        for(int i = 1; i < dataList.size(); i++) {
            // リストに格納された配列を取得する。
            String[] dataArray = dataList.get(i);
            if(StringUtils.isEmpty(dataArray[0]) || "0".equals(dataArray[0])) {
                // カテゴリーIDが入力なしまたは"0"が入力されている場合は新規にカテゴリーを登録するデータとして扱う。

                // データを格納するためのアクションフォームを生成する。
                CategoryInsertDataActionForm insertForm = new CategoryInsertDataActionForm();
                // カテゴリー名
                insertForm.setCategoryName(dataArray[1]);
                // 上位カテゴリーID
                if(StringUtils.isEmpty(dataArray[2])) {
                    // NULLまたは空文字以外の場合は0を格納する。
                    insertForm.setSeniorCategoryId(0);
                } else {
                    insertForm.setSeniorCategoryId(Integer.valueOf(dataArray[2]));
                }
                // カテゴリーステータス
                insertForm.setCategoryStatus(dataArray[3]);

                // 格納されているデータを検証する。
                boolean result = new CategoryValidatorModel().validateInsertCategoryData(insertForm);
                if(!result) {
                    // 検証結果に不正があった場合は不正引数例外を投げる。
                    throw new IllegalArgumentException(String.valueOf(i) + "番目のデータ行に誤りがあります。");
                } else {
                    // 上記以外の場合は新規登録用データのリストにアクションフォームを格納する。
                    insertFormList.add(insertForm);
                }
            } else {
                // 上記以外の場合は登録済のカテゴリーを更新するデータとして扱う。

                // データを格納するためのアクションフォームを生成する。
                CategoryUpdateDataActionForm updateForm = new CategoryUpdateDataActionForm();
                // カテゴリーID
                updateForm.setCategoryId(Integer.valueOf(dataArray[0]));
                // カテゴリー名
                updateForm.setCategoryName(dataArray[1]);
                // 上位カテゴリーID
                if(StringUtils.isEmpty(dataArray[2])) {
                    // NULLまたは空文字以外の場合は上位カテゴリーID-NULL設定フラグにtrueを設定する。
                    updateForm.setSeniorCategoryIdToNullFlag(true);
                } else {
                    updateForm.setSeniorCategoryId(Integer.valueOf(dataArray[2]));
                }
                // カテゴリーステータス
                updateForm.setCategoryStatus(dataArray[3]);

                // 格納されているデータを検証する。
                boolean result = new CategoryValidatorModel().validateUpdateCategoryData(updateForm);
                if(!result) {
                    // 検証結果に不正があった場合は不正引数例外を投げる。
                    throw new IllegalArgumentException(String.valueOf(i) + "番目のデータ行に誤りがあります。");
                } else {
                    // 不正がなかった場合は更新用データのリストにアクションフォームを格納する。
                    updateFormList.add(updateForm);
                }
            }
        }

        // カテゴリーのレコード作成を扱うクラスのインスタンスを生成する。
        InsertCategoryDAO insertCategoryDao = new InsertCategoryDAO();
        // 新規登録用データの件数分のカテゴリーの情報を登録する。
        for(CategoryInsertDataActionForm insertForm: insertFormList) {
            insertCategoryDao.insertCategory(insertForm);
        }

        // カテゴリーのレコード更新を扱うクラスのインスタンスを生成する。
        UpdateCategoryDAO updateCategoryDao = new UpdateCategoryDAO();
        // 更新用データの件数分のカテゴリーの情報を更新する。
        for(CategoryUpdateDataActionForm updateForm: updateFormList) {
            updateCategoryDao.updateCategory(updateForm);
        }
    }

    /**
     * 現在日時(日時型)を取得するためのメソッド
     */
    Timestamp getCurrentTimestamp() {
        // 現在日時(日時型)を戻す。
        return new Timestamp(System.currentTimeMillis());
    }

}
