package dao.category;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.property.PropertyTester.*;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import dao.category.sql.CategoryUpdateDataActionForm;
import exception.ReferredByCategoryException;
import exception.ReferredBySchoolException;
import test.db.DbUnitTester;

public class UpdateCategoryDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.UpdateCategoryDAOTest.xml") {
        @Override
        protected void before() throws Exception {
            executeQuery("DELETE FROM mail_templetes");
            executeQuery("DELETE FROM contacts");
            executeQuery("DELETE FROM contact_statuses");
            executeQuery("DELETE FROM favorites");
            executeQuery("DELETE FROM messages");
            executeQuery("DELETE FROM entries");
            executeQuery("DELETE FROM entry_statuses");
            executeQuery("DELETE FROM schools");
            executeQuery("DELETE FROM categories");
            executeQuery("DELETE FROM accounts");
            executeQuery("DELETE FROM prefectures");
            executeQuery("DELETE FROM sexes");
            executeQuery("DELETE FROM admins");
            executeQuery("DELETE FROM admin_departments");
        }
    };

    @Test
    public void testUpdateCategoryToUpdateOneCategory() throws Exception {
        UpdateCategoryDAO sut = new UpdateCategoryDAO() {
            String getStringCurrentTimestamp() {
                return "2022-03-04 05:06:07.891";
            }
        };
        CategoryUpdateDataActionForm dataForm = new CategoryUpdateDataActionForm();
        dataForm.setCategoryId(7);
        dataForm.setCategoryName("表千家流");
        dataForm.setSeniorCategoryId(4);
        dataForm.setCategoryStatus("0");
        boolean actual = sut.updateCategory(dataForm);

        assertThat(actual, is(true));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.UpdateCategoryDAOTest.testUpdateCategoryToUpdateOneCategory.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("categories");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "categories";
        String sqlQuery = "SELECT * FROM categories WHERE category_id=7";
        String[] ignoreCols = {};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test
    public void testUpdateCategoryToSetNull() throws Exception {
        UpdateCategoryDAO sut = new UpdateCategoryDAO() {
            String getStringCurrentTimestamp() {
                return "2022-03-04 05:06:07.891";
            }
        };
        CategoryUpdateDataActionForm dataForm = new CategoryUpdateDataActionForm();
        dataForm.setCategoryId(8);
        dataForm.setSeniorCategoryIdToNullFlag(true);
        boolean actual = sut.updateCategory(dataForm);

        assertThat(actual, is(true));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.UpdateCategoryDAOTest.testUpdateCategoryToSetToNull.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("categories");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "categories";
        String sqlQuery = "SELECT * FROM categories WHERE category_id=8";
        String[] ignoreCols = {};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test
    public void testUpdateCategoryToUpdateNoCategory() throws Exception {
        UpdateCategoryDAO sut = new UpdateCategoryDAO();
        CategoryUpdateDataActionForm dataForm = new CategoryUpdateDataActionForm();
        dataForm.setCategoryId(9);
        dataForm.setCategoryName("不存在カテゴリー");
        dataForm.setSeniorCategoryId(4);
        dataForm.setCategoryStatus("0");
        boolean actual = sut.updateCategory(dataForm);

        assertThat(actual, is(false));
    }

    @Test(expected = ReferredBySchoolException.class)
    public void testUpdateCategoryInCaseReferredBySchool() throws Exception {
        UpdateCategoryDAO sut = new UpdateCategoryDAO();
        CategoryUpdateDataActionForm dataForm = new CategoryUpdateDataActionForm();
        dataForm.setCategoryId(5);
        dataForm.setCategoryStatus("0");
        sut.updateCategory(dataForm);
    }

    @Test(expected = ReferredByCategoryException.class)
    public void testUpdateCategoryInCaseReferredByOtherCategory() throws Exception {
        UpdateCategoryDAO sut = new UpdateCategoryDAO();
        CategoryUpdateDataActionForm dataForm = new CategoryUpdateDataActionForm();
        dataForm.setCategoryId(1);
        dataForm.setCategoryStatus("0");
        sut.updateCategory(dataForm);
    }

}
