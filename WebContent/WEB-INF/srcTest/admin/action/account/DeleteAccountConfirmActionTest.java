package admin.action.account;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import admin.actionform.account.AdminDeleteAccountActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class DeleteAccountConfirmActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.DeleteAccountConfirmActionTest.xml") {
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

        private static final String ACTION_PATH = "/deleteAccountConfirm.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() {
            addRequestParameter("accountId", "1");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            AdminDeleteAccountActionForm expected = new AdminDeleteAccountActionForm();
            expected.setAccountId(1);
            expected.setLastName("田中");
            expected.setFirstName("太郎");
            expected.setLastNameKana("タナカ");
            expected.setFirstNameKana("タロウ");
            expected.setSexId(0);
            expected.setSexName(null);
            expected.setBirthDate(null);
            expected.setBirthYear("");
            expected.setBirthMonth("");
            expected.setBirthDay("");
            expected.setPrefectureId(2);
            expected.setPrefectureName("東京都");
            expected.setMailAddress("tanaka@example.com");
            expected.setProfileImageFileName(null);
            expected.setProfileImageFilePath("");
            expected.setSelfIntroduction(null);
            expected.setAccountStatus("1");
            expected.setAccountCreatedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            expected.setAccountUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));

            AdminDeleteAccountActionForm actual
                    = (AdminDeleteAccountActionForm) getSession().getAttribute("AdminDeleteAccountActionForm");

            assertThat(actual, is(samePropertyValueAs(expected)));
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoParameterExist() {
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("unexistence");
        }

        @Test
        public void testExecuteInCaseLogout() {
            addRequestParameter("accountId", "3");
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/deleteAccountConfirm.do?accountId=3");
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoAccountExist() {
            addRequestParameter("accountId", "2");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("unexistence");
        }

    }

}
