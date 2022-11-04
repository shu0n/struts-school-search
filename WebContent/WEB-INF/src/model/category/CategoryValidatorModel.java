package model.category;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

import dao.category.SelectCategoryDAO;
import dao.category.sql.CategoryInsertDataActionForm;
import dao.category.sql.CategorySelectWhereActionForm;
import dao.category.sql.CategoryUpdateDataActionForm;
import dao.school.SelectSchoolDAO;
import dao.school.sql.SchoolSelectWhereActionForm;

public class CategoryValidatorModel {

    /**
     * インポート(登録)するカテゴリーのデータを検証するためのメソッド
     * @param insertDataForm 登録するデータを格納したアクションフォーム
     * @return 検証結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean validateInsertCategoryData(CategoryInsertDataActionForm insertDataForm)
            throws ClassNotFoundException, IOException, SQLException {
        // カテゴリー名
        if(!checkMaxLength(insertDataForm.getCategoryName(), 50)) {
            // 文字数が上限を超えている場合はflaseを戻す。
            return false;
        }

        // 上位カテゴリーID
        if(insertDataForm.getSeniorCategoryId() != 0) {
            // 入力されている場合

            // カテゴリーテーブルから上位カテゴリーIDに紐づくレコードを取得する。
            CategorySelectWhereActionForm categorySelectWhereForm = new CategorySelectWhereActionForm();
            categorySelectWhereForm.setSeniorCategoryId(insertDataForm.getSeniorCategoryId());
            if(new SelectCategoryDAO().selectMatchedCategory(categorySelectWhereForm).isEmpty()) {
                // レコードが取得できない(未登録)場合はfalseを戻す。
                return false;
            }
        }

        // カテゴリーステータス
        if(!"0".equals(insertDataForm.getCategoryStatus()) && !"1".equals(insertDataForm.getCategoryStatus())) {
            // "0"(無効)または"1"(有効)以外の場合はfalseを戻す。
            return false;
        }

        // 検証結果を戻す。
        return true;
    }

    /**
     * インポート(更新)するカテゴリーのデータを検証するためのメソッド
     * @param updateDataForm 更新するデータを格納したアクションフォーム
     * @return 検証結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean validateUpdateCategoryData(CategoryUpdateDataActionForm updateDataForm)
            throws ClassNotFoundException, IOException, SQLException {
        // カテゴリーのレコード取得を扱うクラスのインスタンスを生成する。
        SelectCategoryDAO selectCategoryDao = new SelectCategoryDAO();
        // DAOメソッドに引き渡すアクションフォームを格納する変数を生成する。
        CategorySelectWhereActionForm categorySelectWhereForm;

        // カテゴリーID
        if(updateDataForm.getCategoryId() == 0) {
            // 入力されていない場合はfalseを戻す。
            return false;
        } else {
            // 上記以外の場合は当該カテゴリーIDが登録済かを判定する。
            categorySelectWhereForm = new CategorySelectWhereActionForm();
            categorySelectWhereForm.setCategoryId(updateDataForm.getCategoryId());
            if(selectCategoryDao.selectMatchedCategory(categorySelectWhereForm).isEmpty()) {
                // 未登録の場合はfalseを戻す。
                return false;
            }
        }

        // カテゴリー名
        if(!checkMaxLength(updateDataForm.getCategoryName(), 50)) {
            // 文字数が上限を超えている場合はflaseを戻す。
            return false;
        } else {
            // 上記以外の場合

            // カテゴリーテーブルからカテゴリー名に紐づくレコードを取得する。
            categorySelectWhereForm = new CategorySelectWhereActionForm();
            categorySelectWhereForm.setCategoryName(updateDataForm.getCategoryName());
            if(!selectCategoryDao.selectMatchedCategory(categorySelectWhereForm).isEmpty()) {
                // レコードが取得できた場合
                int categoryId
                        = selectCategoryDao.selectMatchedCategory(categorySelectWhereForm).get(0).getCategoryId();
                if(categoryId != updateDataForm.getCategoryId()) {
                    // 入力されたカテゴリーIDと一致しない(他カテゴリーIDでカテゴリー名が登録済)場合はfalseを戻す。
                    return false;
                }
            }
        }

        // 上位カテゴリーID
        if(updateDataForm.getSeniorCategoryId() != 0) {
            // 入力されている場合

            // カテゴリーテーブルから上位カテゴリーIDに紐づくレコードを取得する。
            categorySelectWhereForm = new CategorySelectWhereActionForm();
            categorySelectWhereForm.setCategoryId(updateDataForm.getSeniorCategoryId());
            if(selectCategoryDao.selectMatchedCategory(categorySelectWhereForm).isEmpty()) {
                // レコードが取得できない(未登録)場合はfalseを戻す。
                return false;
            }

            // カテゴリーテーブルから入力されたカテゴリーIDの直系の下位カテゴリーのレコードを取得する。
            List<String[]> descendantCategoryList
                    = selectCategoryDao.selectDescendantCategory(updateDataForm.getCategoryId());
            for(String[] descendantCategoryArray: descendantCategoryList) {
                if(Integer.parseInt(descendantCategoryArray[0]) == updateDataForm.getSeniorCategoryId()) {
                    // 入力された上位カテゴリーIDがカテゴリーIDの直系の下位カテゴリーの場合はfalseを戻す。
                    return false;
                }
            }
        }

        // カテゴリーステータス
        if(!"0".equals(updateDataForm.getCategoryStatus()) && !"1".equals(updateDataForm.getCategoryStatus())) {
            // "0"(無効)または"1"(有効)以外の場合はfalseを戻す。
            return false;
        } else {
            // 上記以外の場合

            // カテゴリーテーブルから入力されたカテゴリーIDを上位カテゴリーIDに設定しているレコード(子カテゴリー)を取得する。
            categorySelectWhereForm = new CategorySelectWhereActionForm();
            categorySelectWhereForm.setSeniorCategoryId(updateDataForm.getCategoryId());
            if(!selectCategoryDao.selectMatchedCategory(categorySelectWhereForm).isEmpty()
                    && "0".equals(updateDataForm.getCategoryStatus())) {
                // 子カテゴリーが存在してかつカテゴリーステータスに"0"(無効)が入力されている場合はfalseを戻す。
                return false;
            }

            // スクールテーブルから入力されたカテゴリーIDをスクールカテゴリーIDに設定しているレコード(子カテゴリー)を取得する。
            SchoolSelectWhereActionForm schoolSelectWhereActionForm = new SchoolSelectWhereActionForm();
            schoolSelectWhereActionForm.setSchoolCategoryId(updateDataForm.getCategoryId());
            if(!new SelectSchoolDAO().selectMatchedSchool(schoolSelectWhereActionForm).isEmpty()
                    && "0".equals(updateDataForm.getCategoryStatus())) {
                // 子カテゴリーが存在してかつカテゴリーステータスに"0"(無効)が入力されている場合はfalseを戻す。
                return false;
            }
        }

        // 検証結果を戻す。
        return true;
    }

    /**
     * データがNULL/空文字ではなく最大文字数以下であるかを検証するためのメソッド
     * @param data 検証対象のデータ
     * @param maxLength 最大文字数
     * @return 検証結果
     */
    private boolean checkMaxLength(String data, int maxLength) {
        if(GenericValidator.isBlankOrNull(data) || !GenericValidator.maxLength(data, maxLength)) {
            // NULLまたは空文字または最大文字数超である場合はfalseを戻す。
            return false;
        } else {
            // 上記以外の場合はtrueを戻す。
            return true;
        }
    }

}
