package model.category;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.util.LabelValueBean;

import actionform.CategoryActionForm;
import dao.category.SelectCategoryDAO;
import dao.category.sql.CategorySelectWhereActionForm;

public class CategoryFormPartsModel {

    /**
     * 全てのカテゴリーIDとカテゴリー名を格納したマップを取得するためのメソッド
     * @return マップ(キー：カテゴリーID、バリュー：カテゴリー名)
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public Map<Integer, String> getCategoryMap() throws ClassNotFoundException, IOException, SQLException {
        // カテゴリーテーブルの全レコードを取得する。
        List<CategoryActionForm> categoryFormList
                = new SelectCategoryDAO().selectMatchedCategory(new CategorySelectWhereActionForm());

        // カテゴリーIDとカテゴリー名を格納するマップを生成する。
        Map<Integer, String> categoryMap = new TreeMap<>();
        // 取得したレコードの件数分、処理を繰り返す。
        for(CategoryActionForm value : categoryFormList) {
            // カテゴリーIDとカテゴリー名を取得してマップに格納する。
            categoryMap.put(value.getCategoryId(), value.getCategoryName());
        }

        // カテゴリーIDとカテゴリー名を格納したマップを戻す。
        return categoryMap;
    }

    /**
     * 全てのインデントを付与したカテゴリー名とカテゴリーIDを格納したリストを取得するためのメソッド
     * @return リスト(ラベル：カテゴリー名、バリュー：カテゴリーID)
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<LabelValueBean> getCategoryListWithEmptyValue()
            throws ClassNotFoundException, IOException, SQLException {
        // カテゴリーID、カテゴリー名、階層レベルの配列を格納したリストを取得する。
        List<String[]> descendantCategoryList = new SelectCategoryDAO().selectDescendantCategory(0);

        // カテゴリー名とカテゴリーIDを格納するリストを生成する。
        List<LabelValueBean> categoryList = new ArrayList<>();
        categoryList.add(new LabelValueBean("選択してください", ""));
        // 取得したリストの件数分、処理を繰り返す。
        for(String[] descendantCategoryArray: descendantCategoryList) {
            // 階層レベルに応じたインデントを生成してカテゴリー名と結合する。
            String categoryNameWithIndent
                    = StringUtils.repeat("　", Integer.parseInt(descendantCategoryArray[2]) - 1)
                    + descendantCategoryArray[1];
            // リストにインデントを付与したカテゴリー名とカテゴリーIDを格納する。
            categoryList.add(new LabelValueBean(categoryNameWithIndent, String.valueOf(descendantCategoryArray[0])));
        }

        // カテゴリー名とカテゴリーIDを格納したリストを戻す。
        return categoryList;
    }

    /**
     * 全てのインデントを付与したカテゴリー名とカテゴリーIDを格納したリストを取得するためのメソッド
     * @return リスト(ラベル：カテゴリー名、バリュー：カテゴリーID)
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<LabelValueBean> getCategoryListWithoutEmptyValue()
            throws ClassNotFoundException, IOException, SQLException {
        // カテゴリーID、カテゴリー名、階層レベルの配列を格納したリストを取得する。
        List<String[]> descendantCategoryList = new SelectCategoryDAO().selectDescendantCategory(0);

        // カテゴリー名とカテゴリーIDを格納するリストを生成する。
        List<LabelValueBean> categoryList = new ArrayList<>();
        // 取得したリストの件数分、処理を繰り返す。
        for(String[] descendantCategoryArray: descendantCategoryList) {
            // 階層レベルに応じたインデントを生成してカテゴリー名と結合する。
            String categoryNameWithIndent
                    = StringUtils.repeat("　", Integer.parseInt(descendantCategoryArray[2]) - 1)
                    + descendantCategoryArray[1];
            // リストにインデントを付与したカテゴリー名とカテゴリーIDを格納する。
            categoryList.add(new LabelValueBean(categoryNameWithIndent, String.valueOf(descendantCategoryArray[0])));
        }

        // カテゴリー名とカテゴリーIDを格納したリストを戻す。
        return categoryList;
    }

    /**
     * 自カテゴリーおよび直系の下位のカテゴリーを除く全てのカテゴリー名とカテゴリーIDを格納したリストを取得するためのメソッド
     * @param categoryId カテゴリーID
     * @return リスト(ラベル：カテゴリー名、バリュー：カテゴリーID)
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<LabelValueBean> getCategoryListWithEmptyValueExcludeSelfCategoryGroup(int categoryId)
            throws ClassNotFoundException, IOException, SQLException {
        // 全てのカテゴリーID、カテゴリー名、階層レベルの配列を格納したリストを取得する。
        List<String[]> descendantCategoryList = new SelectCategoryDAO().selectDescendantCategory(0);
        // 自カテゴリーの直系の下位カテゴリーのカテゴリーIDと階層レベルを格納したリストを取得する。
        List<String[]> descendantCategoryOfSelfCategoryList
                = new SelectCategoryDAO().selectDescendantCategory(categoryId);
        // リストに自カテゴリーのカテゴリーIDと階層レベルを格納する。
        descendantCategoryOfSelfCategoryList.add(new String[] {String.valueOf(categoryId), "", ""});

        // 全てのカテゴリーID、カテゴリー名、階層レベルの配列を格納したリストから自カテゴリーと直系の下位カテゴリーを削除する。
        List<String[]> descendantCategoryListExcludeSelfCategoryGroup = new ArrayList<>();
        for(int i = 0; i < descendantCategoryList.size(); i++) {
            boolean includeFlag = false;
            for(String[] descendantCategoryOfSelfCategoryArray: descendantCategoryOfSelfCategoryList) {
                if(descendantCategoryList.get(i)[0].equals(descendantCategoryOfSelfCategoryArray[0])) {
                    includeFlag = true;
                    break;
                }
            }
            if(!includeFlag) {
                descendantCategoryListExcludeSelfCategoryGroup.add(descendantCategoryList.get(i));
            }
        }

        // カテゴリー名とカテゴリーIDを格納するリストを生成する。
        List<LabelValueBean> categoryList = new ArrayList<>();
        categoryList.add(new LabelValueBean("選択してください", ""));
        // 取得したリストの件数分、処理を繰り返す。
        for(String[] descendantCategoryArrayExcludeSelfCategoryGroup: descendantCategoryListExcludeSelfCategoryGroup) {
            // 階層レベルに応じたインデントを生成してカテゴリー名と結合する。
            String categoryNameWithIndent
                    = StringUtils.repeat("　", Integer.parseInt(descendantCategoryArrayExcludeSelfCategoryGroup[2]) - 1)
                    + descendantCategoryArrayExcludeSelfCategoryGroup[1];
            // リストにインデントを付与したカテゴリー名とカテゴリーIDを格納する。
            categoryList.add(new LabelValueBean(
                    categoryNameWithIndent,
                    String.valueOf(descendantCategoryArrayExcludeSelfCategoryGroup[0])));
        }

        // カテゴリー名とカテゴリーIDを格納したリストを戻す。
        return categoryList;
    }

    /**
     * 全てのカテゴリステータスIDとカテゴリーステータス名を格納したマップを取得するためのメソッド
     * @return マップ(キー：カテゴリステータスID、バリュー：カテゴリステータス名)
     */
    public Map<String, String> getCategoryStatusMap() {
        // カテゴリステータスIDとカテゴリステータス名を格納するマップを生成する。
        Map<String, String> categoryStatusMap = new TreeMap<>();
        // カテゴリステータスIDとカテゴリステータス名を取得してマップに格納する。
        categoryStatusMap.put("0", "無効");
        categoryStatusMap.put("1", "有効");

        // カテゴリーステータスIDとカテゴリーステータス名を格納したマップを戻す。
        return categoryStatusMap;
    }

}
