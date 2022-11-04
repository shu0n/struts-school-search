package front.action.entry;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.entry.FrontCancelEntryActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class CancelEntryConfirmActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.CancelEntryConfirmActionTest.xml") {
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

        private static final String ACTION_PATH = "/cancelEntryConfirm.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() {
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "5");
            addRequestParameter("entryId", "6");
            actionPerform();

            verifyForward("success");

            FrontCancelEntryActionForm expected = new FrontCancelEntryActionForm();
            expected.setEntryId(6);
            expected.setEntrySchoolId(1);
            expected.setEntrySchoolName("テストスクール1");
            expected.setApplicantAccountId(5);
            expected.setEntryQuestion("申込時の質問1");
            expected.setEntryStatusId(2);
            expected.setEntryStatusName("連絡済");
            expected.setEntriedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            expected.setEntryUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            expected.setStrEntriedAt("2022/01/02 03:04:05");
            expected.setStrEntryUpdatedAt("2022/01/03 04:05:06");

            FrontCancelEntryActionForm actual
                    = (FrontCancelEntryActionForm) getSession().getAttribute("FrontCancelEntryActionForm");

            assertThat(actual, is(samePropertyValueAs(expected)));
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoParameterExist() {
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "5");
            actionPerform();

            verifyForward("unexistence");
        }

        @Test
        public void testExecuteInCaseLogout() {
            addRequestParameter("entryId", "6");
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/viewDetailedEntry.do?entryId=6");
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoMatchedEntry() {
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "5");
            addRequestParameter("entryId", "9");
            actionPerform();

            verifyForward("unexistence");
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseEntryIsAlreadyCanceled() {
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "5");
            addRequestParameter("entryId", "8");
            actionPerform();

            verifyForward("unexistence");
        }

    }

}
