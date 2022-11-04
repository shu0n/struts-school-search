package dao.category.sql;

import org.apache.struts.action.ActionForm;

/**
 * カテゴリーテーブルで更新するレコードのデータを格納するためのアクションフォーム
 */
public class CategoryUpdateDataActionForm extends ActionForm {

    private int categoryId; // カテゴリーID
    private String categoryName; // カテゴリー名
    private int seniorCategoryId; // 上位カテゴリーID
    private boolean seniorCategoryIdToNullFlag; // 上位カテゴリーID-NULL設定フラグ
    private String categoryStatus; // カテゴリーステータス

    public CategoryUpdateDataActionForm() {}

    /**
     * categoryIdを取得する。
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * categoryIdを格納する。
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

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
     * seniorCategoryIdToNullFlagを取得する。
     */
    public boolean isSeniorCategoryIdToNullFlag() {
        return seniorCategoryIdToNullFlag;
    }

    /**
     * seniorCategoryIdToNullFlagを格納する。
     */
    public void setSeniorCategoryIdToNullFlag(boolean seniorCategoryIdToNullFlag) {
        this.seniorCategoryIdToNullFlag = seniorCategoryIdToNullFlag;
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
