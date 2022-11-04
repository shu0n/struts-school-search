package front.action.message;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.message.FrontReplyMessageActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class ReplyMessageConfirmActionTest {

    public static class executeTest extends MockStrutsTestCase {

        private static final String ACTION_PATH = "/replyMessageConfirm.do";
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
            FrontReplyMessageActionForm inForm = new FrontReplyMessageActionForm();
            inForm.setReplyMessageId(32);
            inForm.setEntryId(7);
            inForm.setSenderAccountId(5);
            inForm.setRecipientAccountId(4);
            inForm.setMessageSubject("メッセージ題名2に対する返信");
            inForm.setMessageBody(
                    "メッセージ本文2に対して返信します。"
                    + System.getProperty("line.separator")
                    + System.getProperty("line.separator")
                    + "------------------------------"
                    + System.getProperty("line.separator")
                    + "メッセージ本文2");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("success");
        }

        @Test
        public void testExecuteToForwardInvalid() {
            FrontReplyMessageActionForm inForm = new FrontReplyMessageActionForm();
            inForm.setReplyMessageId(32);
            inForm.setEntryId(7);
            inForm.setSenderAccountId(5);
            inForm.setRecipientAccountId(4);
            inForm.setMessageSubject("メッセージ題名2に対する返信");
            inForm.setMessageBody(
                    "メッセージ本文2に対して返信します。"
                    + System.getProperty("line.separator")
                    + System.getProperty("line.separator")
                    + "------------------------------"
                    + System.getProperty("line.separator")
                    + "メッセージ本文2");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            actionPerform();

            verifyForward("invalid");
        }

    }

}
