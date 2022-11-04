package admin.action.category;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.property.PropertyTester.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import admin.actionform.category.AdminCreateCategoryActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class CreateCategoryCompleteActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.CreateCategoryCompleteActionTest.xml") {
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

        private static final String ACTION_PATH = "/createCategoryComplete.do";
        private static final String VALID_TOKEN = "validToken";
        private static final String INVALID_TOKEN = "invalidToken";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() throws Exception {
            AdminCreateCategoryActionForm inForm = new AdminCreateCategoryActionForm();
            List<LabelValueBean> categoryListWithEmpty = new ArrayList<>();
            categoryListWithEmpty.add(new LabelValueBean("選択してください", ""));
            categoryListWithEmpty.add(new LabelValueBean("文化", "1"));
            categoryListWithEmpty.add(new LabelValueBean("運動", "2"));
            inForm.setSeniorCategoryList(categoryListWithEmpty);
            Map<String, String> categoryStatusMap = new TreeMap<>();
            categoryStatusMap.put("0", "無効");
            categoryStatusMap.put("1", "有効");
            inForm.setCategoryStatusMap(categoryStatusMap);
            inForm.setCategoryName("追加カテゴリー");
            inForm.setSeniorCategoryId(1);
            inForm.setCategoryStatus("1");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("success");

            AdminCreateCategoryActionForm actual
                    = (AdminCreateCategoryActionForm) getSession().getAttribute("AdminCreateCategoryActionForm");

            assertThat(actual, is(nullValue()));

            // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.CreateCategoryCompleteActionTest.testExecuteToForwardSuccess.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("categories");

            // 実際のテーブル情報の取得に必要な変数を生成する。
            String tableName = "categories";
            String sqlQuery = "SELECT * FROM categories WHERE category_name='追加カテゴリー'";
            String[] ignoreCols =
                    {"category_id", "category_created_at", "category_updated_at",
                    "category_delete_flag", "category_deleted_at"};

            Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
        }

        @Test
        public void testExecuteToForwardInvalid() throws Exception {
            AdminCreateCategoryActionForm inForm = new AdminCreateCategoryActionForm();
            List<LabelValueBean> categoryListWithEmpty = new ArrayList<>();
            categoryListWithEmpty.add(new LabelValueBean("選択してください", ""));
            categoryListWithEmpty.add(new LabelValueBean("文化", "1"));
            categoryListWithEmpty.add(new LabelValueBean("運動", "2"));
            inForm.setSeniorCategoryList(categoryListWithEmpty);
            Map<String, String> categoryStatusMap = new TreeMap<>();
            categoryStatusMap.put("0", "無効");
            categoryStatusMap.put("1", "有効");
            inForm.setCategoryStatusMap(categoryStatusMap);
            inForm.setCategoryName("追加カテゴリー２");
            inForm.setSeniorCategoryId(2);
            inForm.setCategoryStatus("1");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            actionPerform();

            verifyForward("invalid");
        }

        @Test
        public void testExecuteToForwardRedo() throws Exception {
            AdminCreateCategoryActionForm inForm = new AdminCreateCategoryActionForm();
            List<LabelValueBean> categoryListWithEmpty = new ArrayList<>();
            categoryListWithEmpty.add(new LabelValueBean("選択してください", ""));
            categoryListWithEmpty.add(new LabelValueBean("文化", "1"));
            categoryListWithEmpty.add(new LabelValueBean("運動", "2"));
            inForm.setSeniorCategoryList(categoryListWithEmpty);
            Map<String, String> categoryStatusMap = new TreeMap<>();
            categoryStatusMap.put("0", "無効");
            categoryStatusMap.put("1", "有効");
            inForm.setCategoryStatusMap(categoryStatusMap);
            inForm.setCategoryName("文化");
            inForm.setSeniorCategoryId(2);
            inForm.setCategoryStatus("1");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("redo");
            verifyActionErrors(new String[] {"登録済のカテゴリー名です。"});
        }

    }

}
