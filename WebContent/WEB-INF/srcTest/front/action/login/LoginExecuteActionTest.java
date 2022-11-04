package front.action.login;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.property.PropertyTester.*;

import javax.servlet.http.HttpSession;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.login.FrontLoginExecuteActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class LoginExecuteActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.LoginExecuteActionTest.xml") {
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

        private static final String ACTION_PATH = "/loginExecute.do";
        private static final String VALID_TOKEN = "validToken";
        private static final String INVALID_TOKEN = "invalidToken";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteInCaseRedirectIndex() {
            FrontLoginExecuteActionForm inForm = new FrontLoginExecuteActionForm();
            inForm.setMailAddress("tanaka@example.com");
            inForm.setPassword("password");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForwardPath("/");

            FrontLoginExecuteActionForm actual = (FrontLoginExecuteActionForm) getActionForm();
            HttpSession actualSession = getSession();

            assertThat(actual, is(nullValue()));
            assertThat(actualSession.getAttribute("accountId"), is("1"));
            assertThat(actualSession.getAttribute("lastName"), is("田中"));
            assertThat(actualSession.getAttribute("logined"), is("true"));
        }

        @Test
        public void testExecuteInCaseRedirectRedirectUrl() {
            FrontLoginExecuteActionForm inForm = new FrontLoginExecuteActionForm();
            inForm.setMailAddress("tanaka@example.com");
            inForm.setPassword("password");
            inForm.setRedirectUrl("/listAccount.do");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForwardPath("/listAccount.do");

            FrontLoginExecuteActionForm actual = (FrontLoginExecuteActionForm) getActionForm();
            HttpSession actualSession = getSession();

            assertThat(actual, is(nullValue()));
            assertThat(actualSession.getAttribute("accountId"), is("1"));
            assertThat(actualSession.getAttribute("lastName"), is("田中"));
            assertThat(actualSession.getAttribute("logined"), is("true"));
        }

        @Test
        public void testExecuteToForwardInvalid() {
            FrontLoginExecuteActionForm inForm = new FrontLoginExecuteActionForm();
            inForm.setMailAddress("tanaka@example.com");
            inForm.setPassword("password");
            inForm.setRedirectUrl("/listAccount.do");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            actionPerform();

            verifyForward("invalid");
        }

        @Test
        public void testExecuteInCaseInvalidPassword() {
            FrontLoginExecuteActionForm inForm = new FrontLoginExecuteActionForm();
            inForm.setMailAddress("tanaka@example.com");
            inForm.setPassword("pcssword");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("redo");
            verifyActionErrors(new String[] {"メールアドレスまたはパスワードが誤っています。"});
        }

        @Test
        public void testExecuteInCaseInvalidAccountStatus() {
            FrontLoginExecuteActionForm inForm = new FrontLoginExecuteActionForm();
            inForm.setMailAddress("yamada@example.com");
            inForm.setPassword("pbssword");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("redo");
            verifyActionErrors(new String[] {"アカウントが有効化されていません。"});
        }

    }

}
