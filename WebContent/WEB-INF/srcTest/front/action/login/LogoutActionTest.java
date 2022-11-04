package front.action.login;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import servletunit.struts.MockStrutsTestCase;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class LogoutActionTest {

    public static class executeTest extends MockStrutsTestCase {

        private static final String ACTION_PATH = "/logout.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() {
            getSession().setAttribute("accountId", "1");
            getSession().setAttribute("lastName", "山田");
            getSession().setAttribute("logined", "true");
            actionPerform();

            verifyForward("success");

            HttpSession actualSession = getSession();

            assertThat(actualSession.getAttribute("accountId"), is(nullValue()));
            assertThat(actualSession.getAttribute("lastName"), is(nullValue()));
            assertThat(actualSession.getAttribute("logined"), is(nullValue()));
        }

        @Test
        public void testExecuteToForwardAdminLogout() {
            actionPerform();

            verifyForward("logout");
        }

    }

}
