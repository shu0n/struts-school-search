package front.action.account;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import servletunit.struts.MockStrutsTestCase;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class RequestReissueInputActionTest {

    public static class executeTest extends MockStrutsTestCase {

        private static final String ACTION_PATH = "/requestReissueInput.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExectuteToForwardSuccess() {
            actionPerform();

            verifyForward("success");
        }

        @Test
        public void testExectuteToForwardLogined() {
            getSession().setAttribute("logined", "true");
            actionPerform();

            verifyForward("logined");
        }

    }

}
