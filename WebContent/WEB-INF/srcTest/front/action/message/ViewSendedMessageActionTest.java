package front.action.message;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import actionform.MessageExtendActionForm;
import front.actionform.message.FrontViewSendedMessageActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class ViewSendedMessageActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.ViewSendedMessageActionTest.xml") {
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

        private static final String ACTION_PATH = "/viewSendedMessage.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() throws Exception {
            addRequestParameter("messageId", "32");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "5");
            actionPerform();

            verifyForward("success");

            FrontViewSendedMessageActionForm expectedForm = new FrontViewSendedMessageActionForm();
            expectedForm.setMessageId(32);
            expectedForm.setReplyMessageId(31);
            expectedForm.setEntryId(7);
            expectedForm.setSenderAccountId(5);
            expectedForm.setRecipientAccountId(4);
            expectedForm.setRecipientLastName("田中");
            expectedForm.setRecipientFirstName("四郎");
            expectedForm.setMessageSubject("メッセージ題名2");
            expectedForm.setMessageBody("メッセージ本文2");
            expectedForm.setStrSendedAt("2022/01/03 04:05:06");
            List<MessageExtendActionForm> expectedReplySourceMessageFormList = new ArrayList<>();
            MessageExtendActionForm replySourceMessageForm = new MessageExtendActionForm();
            replySourceMessageForm.setMessageId(31);
            replySourceMessageForm.setReplyMessageId(0);
            replySourceMessageForm.setEntryId(6);
            replySourceMessageForm.setSenderAccountId(4);
            replySourceMessageForm.setSenderLastName("田中");
            replySourceMessageForm.setSenderFirstName("四郎");
            replySourceMessageForm.setRecipientAccountId(5);
            replySourceMessageForm.setRecipientLastName("田中");
            replySourceMessageForm.setRecipientFirstName("五郎");
            replySourceMessageForm.setMessageSubject("メッセージ題名1");
            replySourceMessageForm.setMessageBody("メッセージ本文1");
            replySourceMessageForm.setSendedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            replySourceMessageForm.setStrSendedAt("2022/01/02 03:04:05");
            replySourceMessageForm.setOpenedFlag("0");
            replySourceMessageForm.setOpenedAt(null);
            replySourceMessageForm.setSenderDeleteFlag("0");
            replySourceMessageForm.setSenderFlagUpdatedAt(null);
            replySourceMessageForm.setRecipientDeleteFlag("0");
            replySourceMessageForm.setRecipientFlagUpdatedAt(null);
            expectedReplySourceMessageFormList.add(replySourceMessageForm);

            FrontViewSendedMessageActionForm actualForm
                    = (FrontViewSendedMessageActionForm) getSession().getAttribute("FrontViewSendedMessageActionForm");

            String[] excludeProperties = {"replySourceMessageList"};
            assertThat(actualForm, is(samePropertyValueAs(expectedForm, excludeProperties)));
            for(int i = 0; i < actualForm.getReplySourceMessageList().size(); i++) {
                assertThat(
                        actualForm.getReplySourceMessageList().get(i),
                        is(samePropertyValueAs(expectedReplySourceMessageFormList.get(i))));
            }
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoParameterExist() throws Exception {
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "5");
            actionPerform();

            verifyForward("unexistence");

            FrontViewSendedMessageActionForm actual
                    = (FrontViewSendedMessageActionForm) getSession().getAttribute("FrontViewSendedMessageActionForm");

            assertThat(actual, is(nullValue()));
        }

        @Test
        public void testExecuteInCaseLogout() throws Exception {
            addRequestParameter("messageId", "32");
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/viewSendedMessage.do?messageId=32");
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoRecipient() throws Exception {
            addRequestParameter("messageId", "31");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "5");
            actionPerform();

            verifyForward("unexistence");

            FrontViewSendedMessageActionForm actual
                    = (FrontViewSendedMessageActionForm) getSession().getAttribute("FrontViewSendedMessageActionForm");

            assertThat(actual, is(nullValue()));
        }

    }

}
