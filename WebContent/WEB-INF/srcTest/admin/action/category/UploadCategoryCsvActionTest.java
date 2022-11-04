package admin.action.category;

import static test.property.PropertyTester.*;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import admin.actionform.category.AdminUploadCategoryCsvActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.generator.GenerateFormFile;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class UploadCategoryCsvActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.UploadCategoryCsvActionTest.xml") {
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

    public static class executeTest extends MockStrutsTestCase {

        private static final String ACTION_PATH = "/uploadCategoryCsv.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteInCaseUpsertCategorySuccessfully() throws Exception {
            AdminUploadCategoryCsvActionForm inForm = new AdminUploadCategoryCsvActionForm();
            inForm.setCategoryCsv(new GenerateFormFile(
                    null,
                    "admin/action/category/UploadCategoryCsvActionTest.testExecuteInCaseUpsertCategorySuccessfully.csv",
                    0));
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listCategory.do");

            // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.UploadCategoryCsvActionTest.testExecuteInCaseUpsertCategorySuccessfully.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("categories");

            // 実際のテーブル情報の取得に必要な変数を生成する。
            String tableName = "categories";
            String sqlQuery = "SELECT * FROM categories ORDER BY category_id ASC";
            String[] ignoreCols =
                    {"category_id", "category_created_at", "category_updated_at",
                    "category_delete_flag", "category_deleted_at"};

            Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
        }

        public void testExecuteInCaseLogout() throws Exception {
            AdminUploadCategoryCsvActionForm inForm = new AdminUploadCategoryCsvActionForm();
            inForm.setCategoryCsv(new GenerateFormFile(
                    null,
                    "admin/action/category/UploadCategoryCsvActionTest.testExecuteInCaseUpsertCategorySuccessfully.csv",
                    0));
            setActionForm(inForm);
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/listCategory.do");
        }

        public void testExecuteInCaseCatchIllegalArgumentException() throws Exception {
            AdminUploadCategoryCsvActionForm inForm = new AdminUploadCategoryCsvActionForm();
            inForm.setCategoryCsv(new GenerateFormFile(
                    null,
                    "admin/action/category/UploadCategoryCsvActionTest.testExecuteInCaseCatchIllegalArgumentException.csv",
                    0));
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listCategory.do");
            verifyActionErrors(new String[] {"1番目のデータ行に誤りがあります。"});
        }

        public void testExecuteInCaseError() throws Exception {
            AdminUploadCategoryCsvActionForm inForm = new AdminUploadCategoryCsvActionForm();
            inForm.setCategoryCsv(new GenerateFormFile(null, null, 0));
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listCategory.do");
            verifyActionErrors(new String[] {"アップロードに失敗しました。"});
        }

    }

}
