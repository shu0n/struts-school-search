package model.category;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import actionform.CategoryActionForm;
import dao.category.SelectCategoryDAO;
import dao.category.sql.CategorySelectWhereActionForm;
import dao.school.SelectSchoolDAO;
import dao.school.sql.SchoolSelectWhereActionForm;

public class CategoryStatusModel {

    /**
     * カテゴリー名が登録済みであるかを判定するためのメソッド
     * @param categoryName カテゴリー名
     * @param categoryId カテゴリーID
     * @param isCreatinCategory カテゴリー作成時点か否か
     * @return 判定結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean isCategoryNameExist(String categoryName, int categoryId, boolean isCreatingCategory)
            throws ClassNotFoundException, IOException, SQLException {
        // DAOメソッドに引き渡すためのアクションフォームを生成してカテゴリー名を格納する。
        CategorySelectWhereActionForm categorySelectWhereForm = new CategorySelectWhereActionForm();
        categorySelectWhereForm.setCategoryName(categoryName);
        if(isCreatingCategory) {
            // カテゴリー作成時の場合

            if(new SelectCategoryDAO().selectMatchedCategory(categorySelectWhereForm).isEmpty()) {
                // 入力されたカテゴリー名に紐づくレコードが取得できない場合はfalseを戻す。
                return false;
            } else {
                // 上記以外の場合はtrueを戻す。
                return true;
            }
        } else {
            // 上記以外の場合(カテゴリー編集時点)

            // 入力されたカテゴリー名に紐づくレコードを取得する。
            List<CategoryActionForm> categoryFormList
                    = new SelectCategoryDAO().selectMatchedCategory(categorySelectWhereForm);
            if(!categoryFormList.isEmpty()) {
                // レコードが存在する場合

                if(categoryId == categoryFormList.get(0).getCategoryId()) {
                    // 自カテゴリーのカテゴリー名に登録されている場合はfalseを戻す。
                    return false;
                } else {
                    // 上記以外の場合はtrueを戻す。
                    return true;
                }
            } else {
                // 上記以外の場合はfalseを戻す。
                return false;
            }
        }
    }

    /**
     * 指定したカテゴリーIDが別カテゴリーまたはスクールから参照されているかを検証するためのメソッド
     * @param categoryId カテゴリーID
     * @return 検証結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean isCategoryReferred(int categoryId) throws ClassNotFoundException, IOException, SQLException {
        // DAOメソッドに引き渡すためのアクションフォームを生成してスクールカテゴリーIDを格納する。
        SchoolSelectWhereActionForm schoolSelectWhereForm = new SchoolSelectWhereActionForm();
        schoolSelectWhereForm.setSchoolCategoryId(categoryId);
        if(!new SelectSchoolDAO().selectMatchedSchool(schoolSelectWhereForm).isEmpty()) {
            // カテゴリーテーブルから条件に一致するレコードが取得できた場合はtrueを戻す。
            return true;
        }

        // DAOメソッドに引き渡すためのアクションフォームを生成して上位カテゴリーIDを格納する。
        CategorySelectWhereActionForm categorySelectWhereForm = new CategorySelectWhereActionForm();
        categorySelectWhereForm.setSeniorCategoryId(categoryId);
        if(!new SelectCategoryDAO().selectMatchedCategory(categorySelectWhereForm).isEmpty()) {
            // カテゴリーテーブルから条件に一致するレコードが取得できた場合はtrueを戻す。
            return true;
        }

        // 参照しているカテゴリーおよびスクールが存在しない場合はfalseを戻す。
        return false;
    }

    /**
     * 特定のカテゴリーIDが"有効"のステータスでカテゴリーテーブルに登録されているかを判定するためのメソッド
     * @param categoryId スクールID
     * @return 判定結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean isCategoryEnable(int categoryId) throws ClassNotFoundException, IOException, SQLException {
        // DAOメソッドに引き渡すアクションフォームを生成してカテゴリIDとカテゴリーステータス"1"(有効)を格納する。
        CategorySelectWhereActionForm categorySelectWhereForm = new CategorySelectWhereActionForm();
        categorySelectWhereForm.setCategoryId(categoryId);
        categorySelectWhereForm.setCategoryStatus("1");
        if(new SelectCategoryDAO().selectMatchedCategory(categorySelectWhereForm).isEmpty()) {
            // カテゴリーテーブルから条件に一致するレコードが取得できない場合はfalseを戻す。
            return false;
        } else {
            // 上記以外の場合はtrueを戻す。
            return true;
        }
    }

}
