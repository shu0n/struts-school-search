package admin.action.category;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
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

import admin.actionform.category.AdminEditCategoryActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class EditCategoryCompleteActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.EditCategoryCompleteActionTest.xml") {
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

        private static final String ACTION_PATH = "/editCategoryComplete.do";
        private static final String VALID_TOKEN = "validToken";
        private static final String INVALID_TOKEN = "invalidToken";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccessInCaseChangeSeniorCategoryId() throws Exception {
            AdminEditCategoryActionForm inForm = new AdminEditCategoryActionForm();
            inForm.setCategoryId(7);
            inForm.setCategoryName("表千家流");
            inForm.setSeniorCategoryId(3);
            inForm.setCategoryStatus("0");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("success");

            AdminEditCategoryActionForm actual
                    = (AdminEditCategoryActionForm) getSession().getAttribute("AdminEditCategoryActionForm");

            assertThat(actual, is(nullValue()));

            // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.EditCategoryCompleteActionTest.testExecuteToForwardSuccessInCaseChangeSeniorCategoryId.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("categories");

            // 実際のテーブル情報の取得に必要な変数を生成する。
            String tableName = "categories";
            String sqlQuery = "SELECT * FROM categories WHERE category_id=7";
            String[] ignoreCols =
                    {"category_created_at", "category_updated_at", "category_delete_flag", "category_deleted_at"};

            Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
        }

        @Test
        public void testExecuteToForwardSuccessInCaseSetSeniorCategoryIdNull() throws Exception {
            AdminEditCategoryActionForm inForm = new AdminEditCategoryActionForm();
            inForm.setCategoryId(8);
            inForm.setCategoryName("裏千家");
            inForm.setSeniorCategoryId(0);
            inForm.setCategoryStatus("0");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("success");

            AdminEditCategoryActionForm actual
                    = (AdminEditCategoryActionForm) getSession().getAttribute("AdminEditCategoryActionForm");

            assertThat(actual, is(nullValue()));

            // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.EditCategoryCompleteActionTest.testExecuteToForwardSuccessInCaseSetSeniorCategoryIdNull.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("categories");

            // 実際のテーブル情報の取得に必要な変数を生成する。
            String tableName = "categories";
            String sqlQuery = "SELECT * FROM categories WHERE category_id=8";
            String[] ignoreCols =
                    {"category_created_at", "category_updated_at", "category_delete_flag", "category_deleted_at"};

            Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
        }

        @Test
        public void testExecuteToForwardInvalid() throws Exception {
            AdminEditCategoryActionForm inForm = new AdminEditCategoryActionForm();
            inForm.setCategoryId(5);
            inForm.setCategoryName("更新茶道");
            inForm.setSeniorCategoryId(3);
            inForm.setCategoryStatus("1");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            actionPerform();

            verifyForward("invalid");
        }

        @Test
        public void testExecuteToForwardRedo() throws Exception {
            AdminEditCategoryActionForm inForm = new AdminEditCategoryActionForm();
            inForm.setCategoryId(5);
            inForm.setCategoryName("日本文化");
            inForm.setSeniorCategoryId(3);
            inForm.setCategoryStatus("1");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("redo");
            verifyActionErrors(new String[] {"登録済のカテゴリー名です。"});
        }

        @Test
        public void testExecuteInCaseRefferredBySchool() throws Exception {
            AdminEditCategoryActionForm inForm = new AdminEditCategoryActionForm();
            inForm.setCategoryId(5);
            inForm.setCategoryName("茶道");
            inForm.setSeniorCategoryId(3);
            inForm.setCategoryStatus("0");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForwardPath("/editCategoryInput.do?categoryId=5");
            verifyActionErrors(new String[] {
                    "スクールのカテゴリーに登録されています。"
                    + "1件以上のスクールのカテゴリーとして登録されている場合はカテゴリーステータスを無効にできません。"});
        }

        @Test
        public void testExecuteInCaseRefferredByCategory() throws Exception {
            AdminEditCategoryActionForm inForm = new AdminEditCategoryActionForm();
            inForm.setCategoryId(3);
            inForm.setCategoryName("日本文化");
            inForm.setSeniorCategoryId(1);
            inForm.setCategoryStatus("0");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForwardPath("/editCategoryInput.do?categoryId=3");
            verifyActionErrors(new String[] {
                    "上位カテゴリーに登録されています。"
                    + "他のカテゴリーの上位カテゴリーに設定されている場合はカテゴリーステータスを無効にできません。"});
        }

    }

}
