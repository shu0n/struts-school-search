package front.action.account;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.account.FrontDeleteAccountActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class DeleteAccountConfirmActionTest {

    public static class executeTest extends MockStrutsTestCase {

        private static final String ACTION_PATH = "/deleteAccountConfirm.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() {
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "1");
            actionPerform();

            verifyForward("success");

            FrontDeleteAccountActionForm expected = new FrontDeleteAccountActionForm();
            expected.setAccountId(1);

            FrontDeleteAccountActionForm actual
                    = (FrontDeleteAccountActionForm) getSession().getAttribute("FrontDeleteAccountActionForm");

            assertThat(actual, is(samePropertyValueAs(expected)));
        }

        @Test
        public void testExecuteInCaseLogout() {
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/deleteAccountConfirm.do");
        }

    }

}
