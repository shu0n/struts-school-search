package front.action.message;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.message.FrontSendMessageActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class SendMessageInputActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SendMessageInputActionTest.xml") {
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

        private static final String ACTION_PATH = "/sendMessageInput.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() {
            addRequestParameter("entryId", "7");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "2");
            actionPerform();

            verifyForward("success");

            FrontSendMessageActionForm expected = new FrontSendMessageActionForm();
            expected.setEntryId(7);
            expected.setSenderAccountId(2);
            expected.setRecipientAccountId(4);
            expected.setRecipientLastName("田中");
            expected.setRecipientFirstName("四郎");

            FrontSendMessageActionForm actual
                    = (FrontSendMessageActionForm) getSession().getAttribute("FrontSendMessageActionForm");

            assertThat(actual, is(samePropertyValueAs(expected)));
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoParameteExist() {
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "2");
            actionPerform();

            verifyForward("unexistence");
        }

        @Test
        public void testExecuteInCaseLogout() {
            addRequestParameter("entryId", "7");
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/sendMessageInput.do?entryId=7");
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoEntryExist() {
            addRequestParameter("entryId", "8");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "2");
            actionPerform();

            verifyForward("unexistence");
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoRegistrant() {
            addRequestParameter("entryId", "6");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "2");
            actionPerform();

            verifyForward("unexistence");
        }

    }

}
