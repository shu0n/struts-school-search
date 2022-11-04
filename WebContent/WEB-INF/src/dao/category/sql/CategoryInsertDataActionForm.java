package dao.category.sql;

import org.apache.struts.action.ActionForm;

/**
 * カテゴリーテーブルに作成するレコードのデータを格納するためのアクションフォーム
 */
public class CategoryInsertDataActionForm extends ActionForm {

    private String categoryName; // カテゴリー名
    private int seniorCategoryId; // 上位カテゴリーID
    private String categoryStatus; // カテゴリーステータス

    public CategoryInsertDataActionForm() {}

    /**
     * categoryNameを取得する。
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * categoryNameを格納する。
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * seniorCategoryIdを取得する。
     */
    public int getSeniorCategoryId() {
        return seniorCategoryId;
    }

    /**
     * seniorCategoryIdを格納する。
     */
    public void setSeniorCategoryId(int seniorCategoryId) {
        this.seniorCategoryId = seniorCategoryId;
    }

    /**
     * categoryStatusを取得する。
     */
    public String getCategoryStatus() {
        return categoryStatus;
    }

    /**
     * categoryStatusを格納する。
     */
    public void setCategoryStatus(String categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

}
