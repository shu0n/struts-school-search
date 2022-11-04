package front.action.account;

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

import front.actionform.account.FrontChangePasswordActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class ChangePasswordCompleteActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.ChangePasswordCompleteActionTest.xml") {
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

        private static final String ACTION_PATH = "/changePasswordComplete.do";
        private static final String VALID_TOKEN = "validToken";
        private static final String INVALID_TOKEN = "invalidToken";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() throws Exception {
            FrontChangePasswordActionForm inForm = new FrontChangePasswordActionForm();
            inForm.setPassword("password");
            inForm.setNewPassword("newPassword");
            setActionForm(inForm);
            getSession().setAttribute("accountId", "1");
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("success");

            FrontChangePasswordActionForm actual = (FrontChangePasswordActionForm) getActionForm();

            assertThat(actual, is(nullValue()));

            // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.ChangePasswordCompleteActionTest.testExecuteToForwardSuccess.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("accounts");

            // 実際のテーブル情報の取得に必要な変数を生成する。
            String tableName = "accounts";
            String sqlQuery = "SELECT * FROM accounts WHERE account_id=1";
            String[] ignoreCols =
                    {"sex_id", "birth_date", "profile_image_file_name", "self_introduction",
                    "activate_token", "activate_effective_date",
                    "reissue_token", "reissue_effective_date",
                    "account_created_at", "account_updated_at",
                    "account_delete_flag", "account_deleted_at"};

            Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
        }

        @Test
        public void testExecuteInCaseInvalidPassword() {
            FrontChangePasswordActionForm inForm = new FrontChangePasswordActionForm();
            inForm.setPassword("pdssword");
            inForm.setNewPassword("newPdssword");
            setActionForm(inForm);
            getSession().setAttribute("accountId", "2");
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("redo");
            verifyActionErrors(new String[] {"パスワードが誤っています。"});

            FrontChangePasswordActionForm actual = (FrontChangePasswordActionForm) getActionForm();

            assertThat(actual, is(nullValue()));
        }

        @Test
        public void testExecuteToForwardInvalid() {
            FrontChangePasswordActionForm inForm = new FrontChangePasswordActionForm();
            inForm.setPassword("pcssword");
            inForm.setNewPassword("newPcssword");
            setActionForm(inForm);
            getSession().setAttribute("accountId", "3");
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            actionPerform();

            verifyForward("invalid");
        }

    }

}
