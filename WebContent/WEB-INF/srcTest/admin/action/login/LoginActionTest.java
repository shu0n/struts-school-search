package admin.action.login;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import servletunit.struts.MockStrutsTestCase;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class LoginActionTest {

    public static class executeTest extends MockStrutsTestCase {

        private static final String ACTION_PATH = "/login.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccessInCaseNoRedirectUrlExist() {
            actionPerform();

            verifyForward("success");

            String actual = (String) getRequest().getAttribute("redirectUrl");

            assertThat(actual, is(nullValue()));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseRedirectUrlExist() {
            addRequestParameter("redirectUrl", "/listAccount.do");
            actionPerform();

            verifyForward("success");

            String expectedRedirectUrl = "/listAccount.do";

            String actual = (String) getRequest().getAttribute("redirectUrl");

            assertThat(actual, is(expectedRedirectUrl));
        }

        @Test
        public void testExecuteInCaseLogined() {
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("adminLogined");
        }

    }

}
