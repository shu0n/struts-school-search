package dao.category.sql;

import org.apache.struts.action.ActionForm;

/**
 * カテゴリーテーブルから取得するレコードの条件に使用するデータを格納するためのアクションフォーム
 */
public class CategorySelectWhereActionForm extends ActionForm {

    private int categoryId; // カテゴリーID
    private String categoryName; // カテゴリー名
    private int seniorCategoryId; // 上位カテゴリーID
    private boolean seniorCategoryIdNullFlag; // 上位カテゴリーID-NULL判定フラグ
    private String categoryStatus; // カテゴリーステータス

    public CategorySelectWhereActionForm() {}

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
     * seniorCategoryIdNullFlagを取得する。
     */
    public boolean isSeniorCategoryIdNullFlag() {
        return seniorCategoryIdNullFlag;
    }

    /**
     * seniorCategoryIdNullFlagを格納する。
     */
    public void setSeniorCategoryIdNullFlag(boolean seniorCategoryIdNullFlag) {
        this.seniorCategoryIdNullFlag = seniorCategoryIdNullFlag;
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
