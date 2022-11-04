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
import front.actionform.message.FrontListReceivedMessageActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class ListReceivedMessageActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.ListReceivedMessageActionTest.xml") {
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

        private static final String ACTION_PATH = "/listReceivedMessage.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccessInCaseFirstAccess() {
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "4");
            actionPerform();

            verifyForward("success");

            int[] expectedMessageIdArray = {34, 33, 31};
            MessageExtendActionForm expectedForm1 = new MessageExtendActionForm();
            expectedForm1.setMessageId(34);
            expectedForm1.setReplyMessageId(33);
            expectedForm1.setEntryId(9);
            expectedForm1.setSenderAccountId(2);
            expectedForm1.setSenderLastName("田中");
            expectedForm1.setSenderFirstName("次郎");
            expectedForm1.setRecipientAccountId(4);
            expectedForm1.setRecipientLastName("田中");
            expectedForm1.setRecipientFirstName("四郎");
            expectedForm1.setMessageSubject("メッセージ題名4");
            expectedForm1.setMessageBody("メッセージ本文4");
            expectedForm1.setSendedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
            expectedForm1.setStrSendedAt("2022/01/06 07:08:09");
            expectedForm1.setOpenedFlag("1");
            expectedForm1.setOpenedAt(Timestamp.valueOf("2022-01-07 08:09:10.234"));
            expectedForm1.setSenderDeleteFlag("1");
            expectedForm1.setSenderFlagUpdatedAt(Timestamp.valueOf("2022-01-08 09:10:11.345"));
            expectedForm1.setRecipientDeleteFlag("0");
            expectedForm1.setRecipientFlagUpdatedAt(null);
            MessageExtendActionForm expectedForm2 = new MessageExtendActionForm();
            expectedForm2.setMessageId(31);
            expectedForm2.setReplyMessageId(0);
            expectedForm2.setEntryId(6);
            expectedForm2.setSenderAccountId(3);
            expectedForm2.setSenderLastName("田中");
            expectedForm2.setSenderFirstName("三郎");
            expectedForm2.setRecipientAccountId(4);
            expectedForm2.setRecipientLastName("田中");
            expectedForm2.setRecipientFirstName("四郎");
            expectedForm2.setMessageSubject("メッセージ題名1");
            expectedForm2.setMessageBody("メッセージ本文1");
            expectedForm2.setSendedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            expectedForm2.setStrSendedAt("2022/01/02 03:04:05");
            expectedForm2.setOpenedFlag("0");
            expectedForm2.setOpenedAt(null);
            expectedForm2.setSenderDeleteFlag("0");
            expectedForm2.setSenderFlagUpdatedAt(null);
            expectedForm2.setRecipientDeleteFlag("0");
            expectedForm2.setRecipientFlagUpdatedAt(null);
            int expectedTotalForm = 3;
            int expectedTotalPage = 1;
            int expectedCurrentPage = 1;

            FrontListReceivedMessageActionForm actual = (FrontListReceivedMessageActionForm) getActionForm();

            for(int i = 0; i < actual.getMessageExtendFormList().size(); i++) {
                assertThat(actual.getMessageExtendFormList().get(i).getMessageId(), is(expectedMessageIdArray[i]));
            }
            assertThat(actual.getMessageExtendFormList().get(0), is(samePropertyValueAs(expectedForm1)));
            assertThat(actual.getMessageExtendFormList().get(2), is(samePropertyValueAs(expectedForm2)));
            assertThat(actual.getTotalForm(), is(expectedTotalForm));
            assertThat(actual.getTotalPage(), is(expectedTotalPage));
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseCurrentPageIsSecond() {
            FrontListReceivedMessageActionForm frontListReceivedMessageForm = new FrontListReceivedMessageActionForm();
            List<MessageExtendActionForm> messageExtendFormList = new ArrayList<>();
            MessageExtendActionForm firstForm = new MessageExtendActionForm();
            firstForm.setMessageId(21);
            firstForm.setSendedAt(Timestamp.valueOf("2022-02-28 09:10:11.234"));
            messageExtendFormList.add(firstForm);
            MessageExtendActionForm secondForm = new MessageExtendActionForm();
            secondForm.setMessageId(20);
            secondForm.setSendedAt(Timestamp.valueOf("2022-02-27 08:09:10.123"));
            messageExtendFormList.add(secondForm);
            MessageExtendActionForm thirdForm = new MessageExtendActionForm();
            thirdForm.setMessageId(19);
            thirdForm.setSendedAt(Timestamp.valueOf("2022-02-26 07:08:09.123"));
            messageExtendFormList.add(thirdForm);
            MessageExtendActionForm fourthForm = new MessageExtendActionForm();
            fourthForm.setMessageId(18);
            fourthForm.setSendedAt(Timestamp.valueOf("2022-02-25 06:07:08.912"));
            messageExtendFormList.add(fourthForm);
            MessageExtendActionForm fifthForm = new MessageExtendActionForm();
            fifthForm.setMessageId(17);
            fifthForm.setSendedAt(Timestamp.valueOf("2022-02-25 05:06:07.891"));
            messageExtendFormList.add(fifthForm);
            MessageExtendActionForm sixthForm = new MessageExtendActionForm();
            sixthForm.setMessageId(16);
            sixthForm.setSendedAt(Timestamp.valueOf("2022-02-24 06:07:08.912"));
            messageExtendFormList.add(sixthForm);
            MessageExtendActionForm seventhForm = new MessageExtendActionForm();
            seventhForm.setMessageId(15);
            seventhForm.setSendedAt(Timestamp.valueOf("2022-02-24 05:06:07.891"));
            messageExtendFormList.add(seventhForm);
            MessageExtendActionForm eighthForm = new MessageExtendActionForm();
            eighthForm.setMessageId(14);
            eighthForm.setSendedAt(Timestamp.valueOf("2022-02-23 04:05:06.789"));
            messageExtendFormList.add(eighthForm);
            MessageExtendActionForm ninthForm = new MessageExtendActionForm();
            ninthForm.setMessageId(13);
            ninthForm.setSendedAt(Timestamp.valueOf("2022-02-22 03:04:05.678"));
            messageExtendFormList.add(ninthForm);
            setActionForm(frontListReceivedMessageForm);
            addRequestParameter("currentPage", "2");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "1");
            actionPerform();

            verifyForward("success");

            int[] expectedMessageIdArray = {21, 20, 19, 18, 17, 16, 15, 14, 13};
            int[] expectedDisplayedMessageArray = {13};
            int expectedCurrentPage = 2;

            FrontListReceivedMessageActionForm actual = (FrontListReceivedMessageActionForm) getActionForm();

            for(int i = 0; i < actual.getMessageExtendFormList().size(); i++) {
                assertThat(
                        actual.getMessageExtendFormList().get(i).getMessageId(),
                        is(expectedMessageIdArray[i]));
            }
            for(int i = 0; i < actual.getDisplayedMessageList().size(); i++) {
                assertThat(
                        actual.getDisplayedMessageList().get(i).getMessageId(),
                        is(expectedDisplayedMessageArray[i]));
            }
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
        }

        @Test
        public void testExecuteInCaseLogout() {
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/listReceivedMessage.do");
        }

    }

}
