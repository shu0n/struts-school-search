package actionform;

import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

/**
 * カテゴリー機能で使用するデータを格納するためのアクションフォーム
 */
public class CategoryExtendActionForm extends CategoryActionForm {

    private String seniorCategoryName; // 上位カテゴリー名
    private List<LabelValueBean> seniorCategoryList; // カテゴリーステータスリスト
    private Map<String, String> categoryStatusMap; // カテゴリーステータスマップ
    private String categoryStatusName; // カテゴリーステータス名

    public CategoryExtendActionForm() {}

    /**
     * seniorCategoryNameを取得する。
     */
    public String getSeniorCategoryName() {
        return seniorCategoryName;
    }

    /**
     * seniorCategoryNameを格納する。
     */
    public void setSeniorCategoryName(String seniorCategoryName) {
        this.seniorCategoryName = seniorCategoryName;
    }

    /**
     * seniorCategoryListを取得する。
     */
    public List<LabelValueBean> getSeniorCategoryList() {
        return seniorCategoryList;
    }

    /**
     * seniorCategoryListを格納する。
     */
    public void setSeniorCategoryList(List<LabelValueBean> seniorCategoryList) {
        this.seniorCategoryList = seniorCategoryList;
    }

    /**
     * categoryStatusMapを取得する。
     */
    public Map<String, String> getCategoryStatusMap() {
        return categoryStatusMap;
    }

    /**
     * categoryStatusMapを格納する。
     */
    public void setCategoryStatusMap(Map<String, String> categoryStatusMap) {
        this.categoryStatusMap = categoryStatusMap;
    }

    /**
     * categoryStatusNameを取得する。
     */
    public String getCategoryStatusName() {
        return categoryStatusName;
    }

    /**
     * categoryStatusNameを格納する。
     */
    public void setCategoryStatusName(String categoryStatusName) {
        this.categoryStatusName = categoryStatusName;
    }

}
