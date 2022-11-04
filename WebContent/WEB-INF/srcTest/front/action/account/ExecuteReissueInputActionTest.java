package front.action.account;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.account.FrontExecuteReissueActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class ExecuteReissueInputActionTest {

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
            "setUpDataSet.ExecuteReissueInputActionTest.xml",
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

        private static final String ACTION_PATH = "/executeReissueInput.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() {
            addRequestParameter("mailAddress", "tanaka@example.com");
            addRequestParameter("reissueToken", "validReissueToken");
            actionPerform();

            verifyForward("success");

            FrontExecuteReissueActionForm expected = new FrontExecuteReissueActionForm();
            expected.setAccountId(1);
            expected.setMailAddress("tanaka@example.com");

            FrontExecuteReissueActionForm actual
                    = (FrontExecuteReissueActionForm) getSession().getAttribute("FrontExecuteReissueActionForm");

            assertThat(actual, is(samePropertyValueAs(expected)));
        }

        @Test
        public void testExecuteToForwardFailInCaseNoParameterExist() {
            actionPerform();

            verifyForward("fail");
        }

        @Test
        public void testExecuteToForwardFailInCaseReissueEffectiveDateIsExpired() {
            addRequestParameter("mailAddress", "yamada@example.com");
            addRequestParameter("reissueToken", "validReissueToken");
            actionPerform();

            verifyForward("fail");
        }

    }

}
