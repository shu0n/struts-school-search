package front.action.entry;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.math.BigDecimal;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.entry.FrontMakeEntryActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class MakeEntryInputActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.MakeEntryInputActionTest.xml") {
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

        private static final String ACTION_PATH = "/makeEntryInput.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccessInCaseEntrySchoolRegisteredByAccount() {
            addRequestParameter("schoolId", "1");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "4");
            actionPerform();

            verifyForward("success");

            FrontMakeEntryActionForm expected = new FrontMakeEntryActionForm();
            expected.setEntrySchoolId(1);
            expected.setApplicantAccountId(4);
            expected.setEntrySchoolName("テストスクール1");
            expected.setSchoolCategoryName("文化");
            expected.setSchoolFee(new BigDecimal("1000"));
            expected.setRegistrantAccountId(1);
            expected.setRegistrantLastName("田中");
            expected.setRegistrantFirstName("太郎");
            expected.setRegistrantLastNameKana("タナカ");
            expected.setRegistrantFirstNameKana("タロウ");
            expected.setRegistrantAdminId(0);

            FrontMakeEntryActionForm actual
                    = (FrontMakeEntryActionForm) getSession().getAttribute("FrontMakeEntryActionForm");

            assertThat(actual, is(samePropertyValueAs(expected)));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseEntrySchoolRegisteredByAdmin() {
            addRequestParameter("schoolId", "4");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "1");
            actionPerform();

            verifyForward("success");

            FrontMakeEntryActionForm expected = new FrontMakeEntryActionForm();
            expected.setEntrySchoolId(4);
            expected.setApplicantAccountId(1);
            expected.setEntrySchoolName("テストスクール4");
            expected.setSchoolCategoryName("文化");
            expected.setSchoolFee(new BigDecimal("1000"));
            expected.setRegistrantAccountId(0);
            expected.setRegistrantLastName(null);
            expected.setRegistrantFirstName(null);
            expected.setRegistrantLastNameKana(null);
            expected.setRegistrantFirstNameKana(null);
            expected.setRegistrantAdminId(61);

            FrontMakeEntryActionForm actual
                    = (FrontMakeEntryActionForm) getSession().getAttribute("FrontMakeEntryActionForm");

            assertThat(actual, is(samePropertyValueAs(expected)));
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoParameterExist() {
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "4");
            actionPerform();

            verifyForward("unexistence");
        }

        @Test
        public void testExecuteToForwardUnEntryInCaseEntryDisable() {
            addRequestParameter("schoolId", "3");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "4");
            actionPerform();

            verifyForward("unentry");
        }

        @Test
        public void testExecuteToForwardUnEntryInCaseNoSchoolExist() {
            addRequestParameter("schoolId", "5");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "4");
            actionPerform();

            verifyForward("unentry");
        }

        @Test
        public void testExecuteInCaseLogout() {
            addRequestParameter("schoolId", "1");
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/makeEntryInput.do?schoolId=1");
        }

        @Test
        public void testExecuteInCaseEntried() {
            addRequestParameter("schoolId", "2");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "4");
            actionPerform();

            verifyForwardPath("/viewDetailedSchool.do?schoolId=2");
        }

    }

}
