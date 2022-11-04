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

import dao.category.sql.CategoryInsertDataActionForm;
import test.db.DbUnitTester;

public class InsertCategoryDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.InsertCategoryDAOTest.xml") {
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
    public void testInsertAccountToCreateOneFirstLevelCategory() throws Exception {
        InsertCategoryDAO sut = new InsertCategoryDAO();
        CategoryInsertDataActionForm dataForm = new CategoryInsertDataActionForm();
        dataForm.setCategoryName("ビジネス");
        dataForm.setCategoryStatus("1");
        int actual = sut.insertCategory(dataForm);

        assertThat(actual, is(not(0)));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.InsertCategoryDAOTest.testInsertCategoryToCreateOneFirstLevelCategory.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("categories");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "categories";
        String sqlQuery = "SELECT * FROM categories WHERE category_name='ビジネス'";
        String[] ignoreCols = {"category_id", "category_created_at", "category_updated_at"};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test
    public void testInsertAccountToCreateOneSecondLevelCategory() throws Exception {
        InsertCategoryDAO sut = new InsertCategoryDAO();
        CategoryInsertDataActionForm dataForm = new CategoryInsertDataActionForm();
        dataForm.setCategoryName("勅使河原流");
        dataForm.setSeniorCategoryId(1);
        dataForm.setCategoryStatus("1");
        int actual = sut.insertCategory(dataForm);

        assertThat(actual, is(not(0)));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.InsertCategoryDAOTest.testInsertCategoryToCreateOneSecondLevelCategory.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("categories");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "categories";
        String sqlQuery = "SELECT * FROM categories WHERE category_name='勅使河原流'";
        String[] ignoreCols = {"category_id", "category_created_at", "category_updated_at"};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

}
