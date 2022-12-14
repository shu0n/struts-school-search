package front.action.account;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.property.PropertyTester.*;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.account.FrontActivateAccountActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class ActivateAccountActionTest {

    public static final Map<String, Integer> relativeDateMap = new HashMap<>();
    static {
        relativeDateMap.put("[now-1d]", -1);
        relativeDateMap.put("[now+1d]", +1);
    }

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.ActivateAccountActionTest.xml",
            relativeDateMap) {
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

        private static final String ACTION_PATH = "/activateAccount.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() throws Exception {
            addRequestParameter("mailAddress", "tanaka@example.com");
            addRequestParameter("activateToken", "activatingToken");
            actionPerform();

            verifyForward("success");

            FrontActivateAccountActionForm actual
                    = (FrontActivateAccountActionForm) getSession().getAttribute("ActivateAccountActionForm");
            HttpSession actualSession = getSession();

            assertThat(actual, is(nullValue()));
            assertThat(actualSession.getAttribute("accountId"), is("1"));
            assertThat(actualSession.getAttribute("lastName"), is("??????"));
            assertThat(actualSession.getAttribute("logined"), is("true"));

            // ?????????????????????????????????????????????????????????????????????????????????????????????????????????
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.ActivateAccountActionTest.testExecuteToForwardSuccess.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("accounts");

            // ????????????????????????????????????????????????????????????????????????
            String tableName = "accounts";
            String sqlQuery = "SELECT * FROM accounts WHERE account_id=1";
            String[] ignoreCols =
                    {"sex_id", "birth_date", "profile_image_file_name", "self_introduction",
                    "reissue_token", "reissue_effective_date",
                    "account_created_at", "account_updated_at",
                    "account_delete_flag", "account_deleted_at"};

            Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
        }

        @Test
        public void testExecuteToForwardFailInCaseNoParameterExist() throws Exception {
            actionPerform();

            verifyForward("fail");

            FrontActivateAccountActionForm actual
                    = (FrontActivateAccountActionForm) getSession().getAttribute("FrontActivateAccountActionForm");

            assertThat(actual, is(nullValue()));
        }

        @Test
        public void testExecuteToForwardFailInCaseActivateEffectiveDateIsExpired() throws Exception {
            addRequestParameter("mailAddress", "sato@example.com");
            addRequestParameter("activateToken", "activationToken");
            actionPerform();

            verifyForward("fail");

            FrontActivateAccountActionForm actual
                    = (FrontActivateAccountActionForm) getSession().getAttribute("FrontActivateAccountActionForm");

            assertThat(actual, is(nullValue()));
        }

        @Test
        public void testExecuteToForwardFailInCaseAccountIsAlreadyValid() throws Exception {
            addRequestParameter("mailAddress", "takahashi@example.com");
            addRequestParameter("activateToken", "activatedToken");
            actionPerform();

            verifyForward("fail");

            FrontActivateAccountActionForm actual
                    = (FrontActivateAccountActionForm) getSession().getAttribute("FrontActivateAccountActionForm");

            assertThat(actual, is(nullValue()));
        }

    }

}
