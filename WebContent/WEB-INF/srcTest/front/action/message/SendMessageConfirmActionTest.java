package front.action.message;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.message.FrontSendMessageActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class SendMessageConfirmActionTest {

    public static class executeTest extends MockStrutsTestCase {

        private static final String ACTION_PATH = "/sendMessageConfirm.do";
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
            FrontSendMessageActionForm inForm = new FrontSendMessageActionForm();
            inForm.setEntryId(7);
            inForm.setSenderAccountId(2);
            inForm.setRecipientAccountId(4);
            inForm.setMessageSubject("申込に対するメッセージ");
            inForm.setMessageBody("お申込みいただいたありがとうございます。");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("success");
        }

        @Test
        public void testExecuteToForwardInvalid() {
            FrontSendMessageActionForm inForm = new FrontSendMessageActionForm();
            inForm.setEntryId(7);
            inForm.setSenderAccountId(2);
            inForm.setRecipientAccountId(4);
            inForm.setMessageSubject("申込に対するメッセージ");
            inForm.setMessageBody("お申込みいただいたありがとうございます。");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            actionPerform();

            verifyForward("invalid");
        }

    }

}
