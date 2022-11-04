package admin.actionform.school;

import org.apache.struts.upload.FormFile;

import actionform.SchoolExtendActionForm;

/**
 * 管理画面 スクール一覧画面でCSVをアップロードする際に使用するデータを格納するためのアクションフォーム
 */
public class AdminUploadSchoolCsvActionForm extends SchoolExtendActionForm {

    private FormFile schoolCsv; // スクール情報を記載したCSVファイル

    public AdminUploadSchoolCsvActionForm() {}

    /**
     * schoolCsvを取得する。
     */
    public FormFile getSchoolCsv() {
        return schoolCsv;
    }

    /**
     * schoolCsvを格納する。
     */
    public void setSchoolCsv(FormFile schoolCsv) {
        this.schoolCsv = schoolCsv;
    }

}
