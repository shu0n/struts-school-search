package admin.actionform.category;

import org.apache.struts.upload.FormFile;

import actionform.CategoryExtendActionForm;

/**
 * 管理画面 カテゴリー一覧画面でCSVをアップロードする際に使用するデータを格納するためのアクションフォーム
 */
public class AdminUploadCategoryCsvActionForm extends CategoryExtendActionForm {

    private FormFile categoryCsv; // カテゴリー情報を記載したCSVファイル

    public AdminUploadCategoryCsvActionForm() {}

    public FormFile getCategoryCsv() {
        return categoryCsv;
    }

    public void setCategoryCsv(FormFile categoryCsv) {
        this.categoryCsv = categoryCsv;
    }

}
