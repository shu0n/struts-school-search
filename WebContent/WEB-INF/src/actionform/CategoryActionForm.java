package actionform;

import java.sql.Timestamp;

import org.apache.struts.validator.ValidatorForm;

/**
 * カテゴリーテーブルのデータを格納するためのアクションフォーム
 */
public class CategoryActionForm extends ValidatorForm {

    private int categoryId; // カテゴリーID
    private String categoryName; // カテゴリー名
    private int seniorCategoryId; // 上位カテゴリーID
    private String categoryStatus; // カテゴリーステータス
    private Timestamp categoryCreatedAt; // カテゴリー作成日時
    private Timestamp categoryUpdatedAt; // カテゴリー更新日時
    private String categoryDeleteFlag; // カテゴリー削除フラグ
    private Timestamp categoryDeletedAt; // カテゴリー削除日時

    public CategoryActionForm() {}

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

    /**
     * categoryCreatedAtを取得する。
     */
    public Timestamp getCategoryCreatedAt() {
        return categoryCreatedAt;
    }

    /**
     * categoryCreatedAtを格納する。
     */
    public void setCategoryCreatedAt(Timestamp categoryCreatedAt) {
        this.categoryCreatedAt = categoryCreatedAt;
    }

    /**
     * categoryUpdatedAtを取得する。
     */
    public Timestamp getCategoryUpdatedAt() {
        return categoryUpdatedAt;
    }

    /**
     * categoryUpdatedAtを格納する。
     */
    public void setCategoryUpdatedAt(Timestamp categoryUpdatedAt) {
        this.categoryUpdatedAt = categoryUpdatedAt;
    }

    /**
     * categoryDeleteFlagを取得する。
     */
    public String getCategoryDeleteFlag() {
        return categoryDeleteFlag;
    }

    /**
     * categoryDeleteFlagを格納する。
     */
    public void setCategoryDeleteFlag(String categoryDeleteFlag) {
        this.categoryDeleteFlag = categoryDeleteFlag;
    }

    /**
     * categoryDeletedAtを取得する。
     */
    public Timestamp getCategoryDeletedAt() {
        return categoryDeletedAt;
    }

    /**
     * categoryDeletedAtを格納する。
     */
    public void setCategoryDeletedAt(Timestamp categoryDeletedAt) {
        this.categoryDeletedAt = categoryDeletedAt;
    }

}
