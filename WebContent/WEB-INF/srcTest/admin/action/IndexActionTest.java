package admin.action;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import servletunit.struts.MockStrutsTestCase;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class IndexActionTest {

    public static class executeTest extends MockStrutsTestCase {

        private static final String ACTION_PATH = "/index.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() {
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");
        }

        @Test
        public void testExecuteToForwardInCaseLogout() {
            actionPerform();

            verifyForwardPath("/login.do");
        }

    }

}
