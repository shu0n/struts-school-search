package model.school;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.upload.FormFile;

import actionform.SchoolExtendActionForm;
import dao.school.InsertSchoolDAO;
import dao.school.UpdateSchoolDAO;
import dao.school.sql.SchoolInsertDataActionForm;
import dao.school.sql.SchoolUpdateDataActionForm;
import util.CsvUtil;
import util.ImageFileUtil;

public class SchoolFileModel {

    /**
     * アップロードされた一覧画面画像と詳細画面画像のパスとファイル名をリクエストに追加するためのメソッド
     * @param inForm スクールのアクションフォーム
     * @param request リクエスト
     * @return 一覧画面画像と詳細画面画像のパスとファイル名を追加したリクエスト
     * @throws IOException
     */
    public HttpServletRequest setFileInfo(SchoolExtendActionForm inForm, HttpServletRequest request)
            throws IOException {
        // アップロードされたファイルを処理するクラスのインスタンスを生成する。
        ImageFileUtil imageFileUtil = new ImageFileUtil();

        // アップロードされた一覧画面画像のファイル情報を取得する。
        FormFile summaryImageFile = inForm.getSummaryImageFile();
        if(summaryImageFile.getFileSize() > 0) {
            // アップロードファイルが存在する場合

            // ファイル名と格納先パスを格納したマップを取得する。
            Map<String, String> summaryImageMap = imageFileUtil.getImageMap(summaryImageFile);
            // 一覧画面画像のパスをマップから取得してリクエストに追加する。
            request.setAttribute("summaryImageFilePath", summaryImageMap.get("imgPath"));
            // 一覧画面画像のファイル名をマップから取得してリクエストに追加する。
            request.setAttribute("summaryImageFileName", summaryImageMap.get("fileName"));
        } else if(inForm.isDeleteSummaryImageFileFlag()) {
            // 一覧画面画像削除フラグがtrueの場合はリクエストに空文字を追加する。
            request.setAttribute("summaryImageFileName", "");
        } else if(!StringUtils.isEmpty(inForm.getSummaryImageFilePath())) {
            // 一覧画面画像のパスが存在する場合はリクエストに追加する。
            request.setAttribute("summaryImageFilePath", inForm.getSummaryImageFilePath());
        }

        // 各詳細画面画像のファイル情報をもとにパスとファイル名を取得してリクエストに追加する。
        for(int i = 0; i < 9; i++) {
            FormFile detailImageFile = null;
            switch(i) {
                case 1:
                    detailImageFile = inForm.getDetailImage1File();
                    if(detailImageFile.getFileSize() > 0) {
                        // アップロードファイルが存在する場合

                        // ファイル名と格納先パスを格納したマップを取得する。
                        Map<String, String> detailImageMap = imageFileUtil.getImageMap(detailImageFile);
                        // 詳細画面画像のパスをマップから取得してリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FilePath",
                                detailImageMap.get("imgPath"));
                        // 詳細画面画像のファイル名をマップから取得してリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FileName",
                                detailImageMap.get("fileName"));
                    } else if(inForm.isDeleteDetailImage1FileFlag()) {
                        // 詳細画面画像削除フラグがtrueの場合はリクエストに空文字を追加する。
                        request.setAttribute("detailImage" + String.valueOf(i) + "FileName", "");
                    } else if(!StringUtils.isEmpty(inForm.getDetailImage1FilePath())) {
                        // 詳細画面画像のパスが存在する場合はリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FilePath",
                                inForm.getDetailImage1FilePath());
                    }
                    break;
                case 2:
                    detailImageFile = inForm.getDetailImage2File();
                    if(detailImageFile.getFileSize() > 0) {
                        // アップロードファイルが存在する場合

                        // ファイル名と格納先パスを格納したマップを取得する。
                        Map<String, String> detailImageMap = imageFileUtil.getImageMap(detailImageFile);
                        // 詳細画面画像のパスをマップから取得してリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FilePath",
                                detailImageMap.get("imgPath"));
                        // 詳細画面画像のファイル名をマップから取得してリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FileName",
                                detailImageMap.get("fileName"));
                    } else if(inForm.isDeleteDetailImage2FileFlag()) {
                        // 詳細画面画像削除フラグがtrueの場合はリクエストに空文字を追加する。
                        request.setAttribute("detailImage" + String.valueOf(i) + "FileName", "");
                    } else if(!StringUtils.isEmpty(inForm.getDetailImage2FilePath())) {
                        // 詳細画面画像のパスが存在する場合はリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FilePath",
                                inForm.getDetailImage2FilePath());
                    }
                    break;
                case 3:
                    detailImageFile = inForm.getDetailImage3File();
                    if(detailImageFile.getFileSize() > 0) {
                        // アップロードファイルが存在する場合

                        // ファイル名と格納先パスを格納したマップを取得する。
                        Map<String, String> detailImageMap = imageFileUtil.getImageMap(detailImageFile);
                        // 詳細画面画像のパスをマップから取得してリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FilePath",
                                detailImageMap.get("imgPath"));
                        // 詳細画面画像のファイル名をマップから取得してリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FileName",
                                detailImageMap.get("fileName"));
                    } else if(inForm.isDeleteDetailImage3FileFlag()) {
                        // 詳細画面画像削除フラグがtrueの場合はリクエストに空文字を追加する。
                        request.setAttribute("detailImage" + String.valueOf(i) + "FileName", "");
                    } else if(!StringUtils.isEmpty(inForm.getDetailImage3FilePath())) {
                        // 詳細画面画像のパスが存在する場合はリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FilePath",
                                inForm.getDetailImage3FilePath());
                    }
                    break;
                case 4:
                    detailImageFile = inForm.getDetailImage4File();
                    if(detailImageFile.getFileSize() > 0) {
                        // アップロードファイルが存在する場合

                        // ファイル名と格納先パスを格納したマップを取得する。
                        Map<String, String> detailImageMap = imageFileUtil.getImageMap(detailImageFile);
                        // 詳細画面画像のパスをマップから取得してリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FilePath",
                                detailImageMap.get("imgPath"));
                        // 詳細画面画像のファイル名をマップから取得してリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FileName",
                                detailImageMap.get("fileName"));
                    } else if(inForm.isDeleteDetailImage4FileFlag()) {
                        // 詳細画面画像削除フラグがtrueの場合はリクエストに空文字を追加する。
                        request.setAttribute("detailImage" + String.valueOf(i) + "FileName", "");
                    } else if(!StringUtils.isEmpty(inForm.getDetailImage4FilePath())) {
                        // 詳細画面画像のパスが存在する場合はリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FilePath",
                                inForm.getDetailImage4FilePath());
                    }
                    break;
                case 5:
                    detailImageFile = inForm.getDetailImage5File();
                    if(detailImageFile.getFileSize() > 0) {
                        // アップロードファイルが存在する場合

                        // ファイル名と格納先パスを格納したマップを取得する。
                        Map<String, String> detailImageMap = imageFileUtil.getImageMap(detailImageFile);
                        // 詳細画面画像のパスをマップから取得してリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FilePath",
                                detailImageMap.get("imgPath"));
                        // 詳細画面画像のファイル名をマップから取得してリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FileName",
                                detailImageMap.get("fileName"));
                    } else if(inForm.isDeleteDetailImage5FileFlag()) {
                        // 詳細画面画像削除フラグがtrueの場合はリクエストに空文字を追加する。
                        request.setAttribute("detailImage" + String.valueOf(i) + "FileName", "");
                    } else if(!StringUtils.isEmpty(inForm.getDetailImage5FilePath())) {
                        // 詳細画面画像のパスが存在する場合はリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FilePath",
                                inForm.getDetailImage5FilePath());
                    }
                    break;
                case 6:
                    detailImageFile = inForm.getDetailImage6File();
                    if(detailImageFile.getFileSize() > 0) {
                        // アップロードファイルが存在する場合

                        // ファイル名と格納先パスを格納したマップを取得する。
                        Map<String, String> detailImageMap = imageFileUtil.getImageMap(detailImageFile);
                        // 詳細画面画像のパスをマップから取得してリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FilePath",
                                detailImageMap.get("imgPath"));
                        // 詳細画面画像のファイル名をマップから取得してリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FileName",
                                detailImageMap.get("fileName"));
                    } else if(inForm.isDeleteDetailImage6FileFlag()) {
                        // 詳細画面画像削除フラグがtrueの場合はリクエストに空文字を追加する。
                        request.setAttribute("detailImage" + String.valueOf(i) + "FileName", "");
                    } else if(!StringUtils.isEmpty(inForm.getDetailImage6FilePath())) {
                        // 詳細画面画像のパスが存在する場合はリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FilePath",
                                inForm.getDetailImage6FilePath());
                    }
                    break;
                case 7:
                    detailImageFile = inForm.getDetailImage7File();
                    if(detailImageFile.getFileSize() > 0) {
                        // アップロードファイルが存在する場合

                        // ファイル名と格納先パスを格納したマップを取得する。
                        Map<String, String> detailImageMap = imageFileUtil.getImageMap(detailImageFile);
                        // 詳細画面画像のパスをマップから取得してリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FilePath",
                                detailImageMap.get("imgPath"));
                        // 詳細画面画像のファイル名をマップから取得してリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FileName",
                                detailImageMap.get("fileName"));
                    } else if(inForm.isDeleteDetailImage7FileFlag()) {
                        // 詳細画面画像削除フラグがtrueの場合はリクエストに空文字を追加する。
                        request.setAttribute("detailImage" + String.valueOf(i) + "FileName", "");
                    } else if(!StringUtils.isEmpty(inForm.getDetailImage7FilePath())) {
                        // 詳細画面画像のパスが存在する場合はリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FilePath",
                                inForm.getDetailImage7FilePath());
                    }
                    break;
                case 8:
                    detailImageFile = inForm.getDetailImage8File();
                    if(detailImageFile.getFileSize() > 0) {
                        // アップロードファイルが存在する場合

                        // ファイル名と格納先パスを格納したマップを取得する。
                        Map<String, String> detailImageMap = imageFileUtil.getImageMap(detailImageFile);
                        // 詳細画面画像のパスをマップから取得してリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FilePath",
                                detailImageMap.get("imgPath"));
                        // 詳細画面画像のファイル名をマップから取得してリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FileName",
                                detailImageMap.get("fileName"));
                    } else if(inForm.isDeleteDetailImage8FileFlag()) {
                        // 詳細画面画像削除フラグがtrueの場合はリクエストに空文字を追加する。
                        request.setAttribute("detailImage" + String.valueOf(i) + "FileName", "");
                    } else if(!StringUtils.isEmpty(inForm.getDetailImage8FilePath())) {
                        // 詳細画面画像のパスが存在する場合はリクエストに追加する。
                        request.setAttribute(
                                "detailImage" + String.valueOf(i) + "FilePath",
                                inForm.getDetailImage8FilePath());
                    }
                    break;
            }
        }

        // 画像のファイルパスを格納したリクエストを戻す。
        return request;
    }

    /**
     * アクションフォームに更新された一覧画面画像と詳細画面画像のファイル名を格納するためのメソッド
     * @param inForm スクールのアクションフォーム
     * @return 更新された一覧画面画像と詳細画面画像のファイル名を格納したスクールのアクションフォーム
     */
    public SchoolExtendActionForm setUpdateFileName(SchoolExtendActionForm inForm) {
        // 一覧画面画像ファイル名(更新)を取得する。
        String summaryImageFileNameUpdate = inForm.getSummaryImageFileNameUpdate();
        if(!StringUtils.isEmpty(summaryImageFileNameUpdate)) {
            // 一覧画面画像ファイル名(更新)が存在する場合は一覧画面画像ファイル名に格納する。
            inForm.setSummaryImageFileName(summaryImageFileNameUpdate);
        } else {
            // 上記以外の場合は空文字を設定する。
            inForm.setSummaryImageFileName("");
        }

        for(int i = 1; i < 9; i++) {
            String detailImageFileNameUpdate = "";
            switch(i) {
                case 1:
                    // 詳細画面画像1を取得する。
                    detailImageFileNameUpdate = inForm.getDetailImage1FileNameUpdate();
                    if(!StringUtils.isEmpty(detailImageFileNameUpdate)) {
                        // 詳細画面画像1ファイル名(更新)が存在する場合は詳細画面画像1ファイル名に格納する。
                        inForm.setDetailImage1FileName(detailImageFileNameUpdate);
                    } else {
                        // 上記以外の場合は空文字を設定する。
                        inForm.setDetailImage1FileName("");
                    }
                    break;
                case 2:
                    // 詳細画面画像2を取得する。
                    detailImageFileNameUpdate = inForm.getDetailImage2FileNameUpdate();
                    if(!StringUtils.isEmpty(detailImageFileNameUpdate)) {
                        // 詳細画面画像2ファイル名(更新)が存在する場合は詳細画面画像1ファイル名に格納する。
                        inForm.setDetailImage2FileName(detailImageFileNameUpdate);
                    } else {
                     // 上記以外の場合は空文字を設定する。
                        inForm.setDetailImage2FileName("");
                    }
                    break;
                case 3:
                    // 詳細画面画像3を取得する。
                    detailImageFileNameUpdate = inForm.getDetailImage3FileNameUpdate();
                    if(!StringUtils.isEmpty(detailImageFileNameUpdate)) {
                        // 詳細画面画像3ファイル名(更新)が存在する場合は詳細画面画像1ファイル名に格納する。
                        inForm.setDetailImage3FileName(detailImageFileNameUpdate);
                    } else {
                        // 上記以外の場合は空文字を設定する。
                        inForm.setDetailImage3FileName("");
                    }
                    break;
                case 4:
                    // 詳細画面画像4を取得する。
                    detailImageFileNameUpdate = inForm.getDetailImage4FileNameUpdate();
                    if(!StringUtils.isEmpty(summaryImageFileNameUpdate)) {
                        // 詳細画面画像4ファイル名(更新)が存在する場合は詳細画面画像4ファイル名に格納する。
                        inForm.setDetailImage4FileName(detailImageFileNameUpdate);
                    } else {
                        // 上記以外の場合は空文字を設定する。
                        inForm.setDetailImage4FileName("");
                    }
                    break;
                case 5:
                    // 詳細画面画像5を取得する。
                    detailImageFileNameUpdate = inForm.getDetailImage5FileNameUpdate();
                    if(!StringUtils.isEmpty(summaryImageFileNameUpdate)) {
                        // 詳細画面画像5ファイル名(更新)が存在する場合は詳細画面画像1ファイル名に格納する。
                        inForm.setDetailImage5FileName(detailImageFileNameUpdate);
                    } else {
                        // 上記以外の場合は空文字を設定する。
                        inForm.setDetailImage5FileName("");
                    }
                    break;
                case 6:
                    // 詳細画面画像6を取得する。
                    detailImageFileNameUpdate = inForm.getDetailImage6FileNameUpdate();
                    if(!StringUtils.isEmpty(summaryImageFileNameUpdate)) {
                        // 詳細画面画像6ファイル名(更新)が存在する場合は詳細画面画像1ファイル名に格納する。
                        inForm.setDetailImage6FileName(detailImageFileNameUpdate);
                    } else {
                        // 上記以外の場合は空文字を設定する。
                        inForm.setDetailImage6FileName("");
                    }
                    break;
                case 7:
                    // 詳細画面画像7を取得する。
                    detailImageFileNameUpdate = inForm.getDetailImage7FileNameUpdate();
                    if(!StringUtils.isEmpty(summaryImageFileNameUpdate)) {
                        // 詳細画面画像7ファイル名(更新)が存在する場合は詳細画面画像1ファイル名に格納する。
                        inForm.setDetailImage7FileName(detailImageFileNameUpdate);
                    } else {
                        // 上記以外の場合は空文字を設定する。
                        inForm.setDetailImage7FileName("");
                    }
                    break;
                case 8:
                    // 詳細画面画像8を取得する。
                    detailImageFileNameUpdate = inForm.getDetailImage8FileNameUpdate();
                    if(!StringUtils.isEmpty(summaryImageFileNameUpdate)) {
                        // 詳細画面画像8ファイル名(更新)が存在する場合は詳細画面画像1ファイル名に格納する。
                        inForm.setDetailImage8FileName(detailImageFileNameUpdate);
                    } else {
                        // 上記以外の場合は空文字を設定する。
                        inForm.setDetailImage8FileName("");
                    }
                    break;
            }
        }

        // 更新された一覧画面画像と詳細画面画像を格納したアクションフォームを格納する。
        return inForm;
    }

    /**
     *
     * @param inForm スクールのアクションフォーム
     * @return 詳細画面画像のパスを格納したリスト
     */
    public List<String> listDetailImageFilePath(SchoolExtendActionForm inForm) {
        // 詳細画面画像のパスを格納するリストを生成する。
        List<String> detailImageFilePathList = new ArrayList<>();
        for(int i = 1; i < 9; i++) {
            String detailImageFilePath = "";
            switch(i) {
                case 1:
                    // 詳細画面画像1を取得する。
                    detailImageFilePath = inForm.getDetailImage1FilePath();
                    if(!StringUtils.isEmpty(detailImageFilePath)) {
                        // 詳細画面画像1ファイル名が存在する場合は詳細画面画像1ファイルパスに格納する。
                        detailImageFilePathList.add(detailImageFilePath);
                    }
                    break;
                case 2:
                    // 詳細画面画像2を取得する。
                    detailImageFilePath = inForm.getDetailImage2FilePath();
                    if(!StringUtils.isEmpty(detailImageFilePath)) {
                        // 詳細画面画像2ファイル名が存在する場合は詳細画面画像2ファイルパスに格納する。
                        detailImageFilePathList.add(detailImageFilePath);
                    }
                    break;
                case 3:
                    // 詳細画面画像3を取得する。
                    detailImageFilePath = inForm.getDetailImage3FilePath();
                    if(!StringUtils.isEmpty(detailImageFilePath)) {
                        // 詳細画面画像3ファイル名が存在する場合は詳細画面画像3ファイルパスに格納する。
                        detailImageFilePathList.add(detailImageFilePath);
                    }
                    break;
                case 4:
                    // 詳細画面画像4を取得する。
                    detailImageFilePath = inForm.getDetailImage4FilePath();
                    if(!StringUtils.isEmpty(detailImageFilePath)) {
                        // 詳細画面画像4ァイル名が存在する場合は詳細画面画像4ファイルパスに格納する。
                        detailImageFilePathList.add(detailImageFilePath);
                    }
                    break;
                case 5:
                    // 詳細画面画像5を取得する。
                    detailImageFilePath = inForm.getDetailImage5FilePath();
                    if(!StringUtils.isEmpty(detailImageFilePath)) {
                        // 詳細画面画像5ファイル名が存在する場合は詳細画面画像5ファイルパスに格納する。
                        detailImageFilePathList.add(detailImageFilePath);
                    }
                    break;
                case 6:
                    // 詳細画面画像6を取得する。
                    detailImageFilePath = inForm.getDetailImage6FilePath();
                    if(!StringUtils.isEmpty(detailImageFilePath)) {
                        // 詳細画面画像6ファイル名が存在する場合は詳細画面画像6ファイルパスに格納する。
                        detailImageFilePathList.add(detailImageFilePath);
                    }
                    break;
                case 7:
                    // 詳細画面画像7を取得する。
                    detailImageFilePath = inForm.getDetailImage7FilePath();
                    if(!StringUtils.isEmpty(detailImageFilePath)) {
                        // 詳細画面画像7ファイル名が存在する場合は詳細画面画像7ファイルパスに格納する。
                        detailImageFilePathList.add(detailImageFilePath);
                    }
                    break;
                case 8:
                    // 詳細画面画像8を取得する。
                    detailImageFilePath = inForm.getDetailImage8FilePath();
                    if(!StringUtils.isEmpty(detailImageFilePath)) {
                        // 詳細画面画像1ファイル名が存在する場合は詳細画面画像8ファイルパスに格納する。
                        detailImageFilePathList.add(detailImageFilePath);
                    }
                    break;
            }
        }

        // 詳細画面画像のパスを格納したリストを戻す。
        return detailImageFilePathList;
    }

    /**
     * スクールのデータを書き込んだCSVファイルをレスポンスに追加するためのメソッド
     * @param schoolExtendFormList スクールのデータを格納したアクションフォームのリスト
     * @param response レスポンス
     * @return CSVファイルのデータを追加したレスポンス
     * @throws IOException
     */
    public HttpServletResponse setDownloadCsvFileData(
            List<SchoolExtendActionForm> schoolExtendFormList,
            HttpServletResponse response) throws IOException {
        // ヘッダー行の配列を生成する。
        String[] headerArray =
                {"スクールID", "登録者アカウントID", "登録者管理者ID",
                "スクール名", "カテゴリーID", "カテゴリー名",
                "スクール概要", "スクール詳細", "郵便番号1", "郵便番号2",
                "都道府県ID", "都道府県名", "市区町村", "住所1", "住所2",
                "費用", "費用補足", "スクールURL", "公開可否", "申込可否", "一覧画面画像",
                "詳細画面画像1ファイル名", "詳細画面画像2ファイル名", "詳細画面画像3ファイル名", "詳細画面画像4ファイル名",
                "詳細画面画像5ファイル名", "詳細画面画像6ファイル名", "詳細画面画像7ファイル名", "詳細画面画像8ファイル名",
                "スクール登録日時", "スクール更新日時"};

        // データ行の配列を格納するためのリストを生成する。
        List<String[]> dataArrayList = new ArrayList<>();
        // リストにデータ行の配列を追加する。
        for(SchoolExtendActionForm eachForm: schoolExtendFormList) {
            // データ行を格納する配列を生成する。
            String[] dataArray = new String[31];
            dataArray[0] = String.valueOf(eachForm.getSchoolId());
            if(eachForm.getRegistrantAccountId() == 0) {
                dataArray[1] = "";
            } else {
                dataArray[1] = String.valueOf(eachForm.getRegistrantAccountId());
            }
            if(eachForm.getRegistrantAdminId() == 0 ) {
                dataArray[2] = "";
            } else {
                dataArray[2] = String.valueOf(eachForm.getRegistrantAdminId());
            }
            dataArray[3] = eachForm.getSchoolName();
            dataArray[4] = String.valueOf(eachForm.getSchoolCategoryId());
            dataArray[5] = eachForm.getSchoolCategoryName();
            dataArray[6] = eachForm.getSchoolSummary().replaceAll("\\r\\n|\\r|\\n", "");
            dataArray[7] = eachForm.getSchoolDescription().replaceAll("\\r\\n|\\r|\\n", "");
            dataArray[8] = eachForm.getSchoolZipCode1();
            dataArray[9] = eachForm.getSchoolZipCode2();
            dataArray[10] = String.valueOf(eachForm.getSchoolPrefectureId());
            dataArray[11] = eachForm.getSchoolCity();
            dataArray[12] = eachForm.getSchoolPrefectureName();
            dataArray[13] = eachForm.getSchoolAddress1();
            if(StringUtils.isEmpty(eachForm.getSchoolAddress2())) {
                dataArray[14] = "";
            } else {
                dataArray[14] = eachForm.getSchoolAddress2();
            }
            dataArray[15] = eachForm.getSchoolFee().toString();
            if(StringUtils.isEmpty(eachForm.getSupplementaryFee())) {
                dataArray[16] = "";
            } else {
                dataArray[16] = eachForm.getSupplementaryFee();
            }
            if(StringUtils.isEmpty(eachForm.getSchoolUrl())) {
                dataArray[17] = "";
            } else {
                dataArray[17] = eachForm.getSchoolUrl();
            }
            dataArray[18] = String.valueOf(eachForm.getSchoolReleasePropriety());
            dataArray[19] = String.valueOf(eachForm.getSchoolEntryPropriety());
            if(StringUtils.isEmpty(eachForm.getSummaryImageFileName())) {
                dataArray[20] = "";
            } else {
                dataArray[20] = eachForm.getSummaryImageFileName();
            }
            if(StringUtils.isEmpty(eachForm.getDetailImage1FileName())) {
                dataArray[21] = "";
            } else {
                dataArray[21] = eachForm.getDetailImage1FileName();
            }
            if(StringUtils.isEmpty(eachForm.getDetailImage2FileName())) {
                dataArray[22] = "";
            } else {
                dataArray[22] = eachForm.getDetailImage2FileName();
            }
            if(StringUtils.isEmpty(eachForm.getDetailImage3FileName())) {
                dataArray[23] = "";
            } else {
                dataArray[23] = eachForm.getDetailImage3FileName();
            }
            if(StringUtils.isEmpty(eachForm.getDetailImage4FileName())) {
                dataArray[24] = "";
            } else {
                dataArray[24] = eachForm.getDetailImage4FileName();
            }
            if(StringUtils.isEmpty(eachForm.getDetailImage5FileName())) {
                dataArray[25] = "";
            } else {
                dataArray[25] = eachForm.getDetailImage5FileName();
            }
            if(StringUtils.isEmpty(eachForm.getDetailImage6FileName())) {
                dataArray[26] = "";
            } else {
                dataArray[26] = eachForm.getDetailImage6FileName();
            }
            if(StringUtils.isEmpty(eachForm.getDetailImage7FileName())) {
                dataArray[27] = "";
            } else {
                dataArray[27] = eachForm.getDetailImage7FileName();
            }
            if(StringUtils.isEmpty(eachForm.getDetailImage8FileName())) {
                dataArray[28] = "";
            } else {
                dataArray[28] = eachForm.getDetailImage8FileName();
            }
            dataArray[29] = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(eachForm.getSchoolRegisteredAt());
            dataArray[30] = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(eachForm.getSchoolUpdatedAt());

            // リストにデータ行の配列を追加する。
            dataArrayList.add(dataArray);
        }

        // ファイル名を生成する。
        String fileName
                = "schools_"
                + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(getCurrentTimestamp())
                + ".csv";

        // レスポンスにCSVファイルを追加する。
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
        String[] headerArray =
                {"スクールID", "登録者アカウントID", "登録者管理者ID", "スクール名", "カテゴリーID",
                "スクール概要", "スクール詳細", "郵便番号1", "郵便番号2", "都道府県ID", "市区町村", "住所1", "住所2",
                "費用", "費用補足", "スクールURL", "公開可否", "申込可否", "一覧画面画像",
                "詳細画面画像1ファイル名", "詳細画面画像2ファイル名", "詳細画面画像3ファイル名", "詳細画面画像4ファイル名",
                "詳細画面画像5ファイル名", "詳細画面画像6ファイル名", "詳細画面画像7ファイル名", "詳細画面画像8ファイル名"};

        // ファイル名を生成する。
        String fileName = "schools_for_upload_template.csv";

        // レスポンスにテンプレートのCSVファイルを追加する。
        return new CsvUtil().writeCsv(headerArray, null, fileName, response);
    }

    /**
     * スクールのデータが書き込まれているCSVファイルを読み込んでレコードを登録または更新するためのメソッド
     * @param FormFile アップロードされたCSVファイルのデータ
     * @throws FileNotFoundException
     * @throws IllegalArgumentException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void upsertCsvFileData(FormFile uploadFile)
            throws FileNotFoundException, IllegalArgumentException, IOException, ClassNotFoundException, SQLException {
        // CSVファイルをもとにデータを格納したリストを取得する。
        List<String[]> dataList = new CsvUtil().readCsv(uploadFile);

        // アクションフォームを格納するためのリストを生成する。
        List<SchoolInsertDataActionForm> insertFormList = new ArrayList<>(); // 新規登録用データのリスト
        List<SchoolUpdateDataActionForm> updateFormList = new ArrayList<>(); // 更新用データのリスト
        // リストのデータをアクションフォームに格納する。
        for(int i = 1; i < dataList.size(); i++) {
            // リストに格納された配列を取得する。
            String[] dataArray = dataList.get(i);
            if(StringUtils.isEmpty(dataArray[0]) || dataArray[0].equals("0")) {
                // スクールIDが入力なしまたは"0"が入力されている場合は新規にスクールを登録するデータとして扱う。

                // データを格納するためのアクションフォームを生成する。
                SchoolInsertDataActionForm insertForm = new SchoolInsertDataActionForm();
                if(!StringUtils.isEmpty(dataArray[1])) {
                    insertForm.setRegistrantAccountId(Integer.valueOf(dataArray[1]));
                } else if(!StringUtils.isEmpty(dataArray[2])) {
                    insertForm.setRegistrantAdminId(Integer.valueOf(dataArray[2]));
                }
                insertForm.setSchoolName(dataArray[3]);
                insertForm.setSchoolCategoryId(Integer.valueOf(dataArray[4]));
                insertForm.setSchoolSummary(dataArray[5]);
                insertForm.setSchoolDescription(dataArray[6]);
                insertForm.setSchoolZipCode1(dataArray[7]);
                insertForm.setSchoolZipCode2(dataArray[8]);
                insertForm.setSchoolPrefectureId(Integer.valueOf(dataArray[9]));
                insertForm.setSchoolCity(dataArray[10]);
                insertForm.setSchoolAddress1(dataArray[11]);
                insertForm.setSchoolAddress2(dataArray[12]);
                insertForm.setSchoolFee(new BigDecimal(dataArray[13]));
                insertForm.setSupplementaryFee(dataArray[14]);
                insertForm.setSchoolUrl(dataArray[15]);
                insertForm.setSchoolReleasePropriety(dataArray[16]);
                insertForm.setSchoolEntryPropriety(dataArray[17]);
                insertForm.setSummaryImageFileName(dataArray[18]);
                insertForm.setDetailImage1FileName(dataArray[19]);
                insertForm.setDetailImage2FileName(dataArray[20]);
                insertForm.setDetailImage3FileName(dataArray[21]);
                insertForm.setDetailImage4FileName(dataArray[22]);
                insertForm.setDetailImage5FileName(dataArray[23]);
                insertForm.setDetailImage6FileName(dataArray[24]);
                insertForm.setDetailImage7FileName(dataArray[25]);
                insertForm.setDetailImage8FileName(dataArray[26]);
                // 格納されているデータを検証する。
                boolean result = new SchoolValidatorModel().validateInsertSchoolData(insertForm);
                if(!result) {
                    // 検証結果に不正があった場合は不正引数例外を投げる。
                    throw new IllegalArgumentException(String.valueOf(i) + "番目のデータ行に誤りがあります。");
                } else {
                    // 上記以外の場合は新規登録用データのリストにアクションフォームを格納する。
                    insertFormList.add(insertForm);
                }
            } else {
                // 上記以外の場合は登録済のスクールを更新するデータとして扱う。

                // データを格納するためのアクションフォームを生成する。
                SchoolUpdateDataActionForm updateForm = new SchoolUpdateDataActionForm();
                updateForm.setSchoolId(Integer.valueOf(dataArray[0]));
                if(!StringUtils.isEmpty(dataArray[1])) {
                    updateForm.setRegistrantAccountId(Integer.valueOf(dataArray[1]));
                } else if(!StringUtils.isEmpty(dataArray[2])) {
                    updateForm.setRegistrantAdminId(Integer.valueOf(dataArray[2]));
                }
                updateForm.setSchoolName(dataArray[3]);
                updateForm.setSchoolCategoryId(Integer.valueOf(dataArray[4]));
                updateForm.setSchoolSummary(dataArray[5]);
                updateForm.setSchoolDescription(dataArray[6]);
                updateForm.setSchoolZipCode1(dataArray[7]);
                updateForm.setSchoolZipCode2(dataArray[8]);
                updateForm.setSchoolPrefectureId(Integer.valueOf(dataArray[9]));
                updateForm.setSchoolCity(dataArray[10]);
                updateForm.setSchoolAddress1(dataArray[11]);
                updateForm.setSchoolAddress2(dataArray[12]);
                updateForm.setSchoolFee(new BigDecimal(dataArray[13]));
                updateForm.setSupplementaryFee(dataArray[14]);
                updateForm.setSchoolUrl(dataArray[15]);
                updateForm.setSchoolReleasePropriety(dataArray[16]);
                updateForm.setSchoolEntryPropriety(dataArray[17]);
                updateForm.setSummaryImageFileName(dataArray[18]);
                updateForm.setDetailImage1FileName(dataArray[19]);
                updateForm.setDetailImage2FileName(dataArray[20]);
                updateForm.setDetailImage3FileName(dataArray[21]);
                updateForm.setDetailImage4FileName(dataArray[22]);
                updateForm.setDetailImage5FileName(dataArray[23]);
                updateForm.setDetailImage6FileName(dataArray[24]);
                updateForm.setDetailImage7FileName(dataArray[25]);
                updateForm.setDetailImage8FileName(dataArray[26]);
                // 格納されているデータを検証する。
                boolean result = new SchoolValidatorModel().validateUpdateSchoolData(updateForm);
                if(!result) {
                    // 検証結果に不正があった場合は不正引数例外を投げる。
                    throw new IllegalArgumentException(String.valueOf(i) + "番目のデータ行に誤りがあります。");
                } else {
                    // 上記以外の場合は更新用データのリストにアクションフォームを格納する。
                    updateFormList.add(updateForm);
                }
            }
        }

        // スクールのレコード作成を扱うクラスのインスタンスを生成する。
        InsertSchoolDAO insertSchoolDao = new InsertSchoolDAO();
        // 新規登録用データのリストの件数分のスクールの情報を登録する。
        for(SchoolInsertDataActionForm insertForm: insertFormList) {
            insertSchoolDao.insertSchool(insertForm);
        }

        // スクールのレコード更新を扱うクラスのインスタンスを生成する。
        UpdateSchoolDAO updateSchoolDao = new UpdateSchoolDAO();
        // 更新用データのリストの件数分のスクールの情報を更新する。
        for(SchoolUpdateDataActionForm updateForm: updateFormList) {
            updateSchoolDao.updateSchool(updateForm);
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
