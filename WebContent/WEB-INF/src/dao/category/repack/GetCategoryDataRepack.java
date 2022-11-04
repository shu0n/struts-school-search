package dao.category.repack;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import actionform.CategoryExtendActionForm;
import dao.category.SelectCategoryJoinDAO;
import dao.category.sql.CategorySelectJoinWhereActionForm;
import exception.NoDataExistException;

public class GetCategoryDataRepack {

    /**
     * 特定のカテゴリーIDに紐づくカテゴリーの情報を取得してアクションフォームに格納するためのメソッド
     * @param whereForm 検索条件を指定するデータを格納したアクションフォーム
     * @param inForm カテゴリーのデータを格納するためのアクションフォーム
     * @return 検索条件に一致したカテゴリーのデータを格納したアクションフォーム
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoDataExistException
     */
    public CategoryExtendActionForm getCategoryData(
            CategorySelectJoinWhereActionForm whereForm,
            CategoryExtendActionForm inForm)
            throws ClassNotFoundException, IOException, SQLException, NoDataExistException {
        // カテゴリーテーブルから検索条件に一致するレコードのリストを取得する。
        List<CategoryExtendActionForm> categoryExtendFormList
                = new SelectCategoryJoinDAO().selectMatchedCategory(whereForm);
        if(categoryExtendFormList.isEmpty()) {
            // 取得結果が0件の場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        }

        // リストの1件目のアクションフォームを取得する。
        CategoryExtendActionForm categoryExtendForm = categoryExtendFormList.get(0);
        // アクションフォームに取得したレコードのデータを格納する。
        inForm.setCategoryId(categoryExtendForm.getCategoryId()); // カテゴリーID
        inForm.setCategoryName(categoryExtendForm.getCategoryName()); // カテゴリー名
        inForm.setSeniorCategoryId(categoryExtendForm.getSeniorCategoryId()); // 上位カテゴリーID
        inForm.setSeniorCategoryName(categoryExtendForm.getSeniorCategoryName()); // 上位カテゴリー名
        inForm.setCategoryStatus(categoryExtendForm.getCategoryStatus()); // カテゴリーステータス
        inForm.setCategoryCreatedAt(categoryExtendForm.getCategoryCreatedAt()); // カテゴリー作成日時
        inForm.setCategoryUpdatedAt(categoryExtendForm.getCategoryUpdatedAt()); // カテゴリー更新日時

        // カテゴリーのデータを格納したアクションフォームを戻す。
        return inForm;
    }

}
