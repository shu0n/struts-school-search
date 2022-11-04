package front.action.contact;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.contact.FrontMakeContactActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class MakeContactConfirmActionTest {

    public static class executeTest extends MockStrutsTestCase {

        private static final String ACTION_PATH = "/makeContactConfirm.do";
        private static final String VALID_TOKEN = "validToken";
        private static final String INVALID_TOKEN = "invalidToken";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() {
            FrontMakeContactActionForm inForm = new FrontMakeContactActionForm();
            inForm.setContactAccountId(1);
            inForm.setContactLastName("田中");
            inForm.setContactFirstName("太郎");
            inForm.setContactLastNameKana("タナカ");
            inForm.setContactFirstNameKana("タロウ");
            inForm.setContactMailAddress("tanaka@example.com");
            inForm.setContactContent("問い合わせです。");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("success");
        }

        @Test
        public void testExecuteToForwardInvalid() {
            FrontMakeContactActionForm inForm = new FrontMakeContactActionForm();
            inForm.setContactAccountId(1);
            inForm.setContactLastName("田中");
            inForm.setContactFirstName("太郎");
            inForm.setContactLastNameKana("タナカ");
            inForm.setContactFirstNameKana("タロウ");
            inForm.setContactMailAddress("tanaka@example.com");
            inForm.setContactContent("問い合わせです。");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            actionPerform();

            verifyForward("invalid");
        }

    }

}
