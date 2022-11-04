package front.action.school;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.property.PropertyTester.*;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.school.FrontViewRegistrantProfileActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class ViewRegistrantProfileActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.ViewRegistrantProfileActionTest.xml") {
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

        private static final String ACTION_PATH = "/viewRegistrantProfile.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccessInCaseNonRegistrant() {
            addRequestParameter("schoolId", "1");
            actionPerform();

            verifyForward("success");

            FrontViewRegistrantProfileActionForm expected = new FrontViewRegistrantProfileActionForm();
            expected.setSchoolId(1);
            expected.setRegistrantLastName("佐藤");
            expected.setRegistrantFirstName("次郎");
            expected.setRegistrantLastNameKana("サトウ");
            expected.setRegistrantFirstNameKana("ジロウ");
            expected.setSelfIntroduction("自己紹介2");
            expected.setSchoolName("テストスクール1");
            expected.setProfileImageFilePath("/Application/struts-school-search/img/profile_image2.png");
        }

        @Test
        public void testExecuteToForwardSuccessInCaseSchoolIsUnreleaseAndRegistrantAccess() {
            addRequestParameter("schoolId", "6");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "23");
            actionPerform();

            verifyForward("success");
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoParameterExist() {
            actionPerform();

            verifyForward("unexistence");
        }

        @Test
        public void testExecuteToForwardUnreleaseInCaseSchoolIsUnreleaseAndLogout() {
            addRequestParameter("schoolId", "6");
            actionPerform();

            verifyForward("unrelease");

            FrontViewRegistrantProfileActionForm actual
                    = (FrontViewRegistrantProfileActionForm) getSession().getAttribute("FrontViewRegistrantProfileActionForm");

            assertThat(actual, is(nullValue()));
        }

        @Test
        public void testExecuteToForwardUnreleaseInCaseSchoolIsUnreleaseAndNonRegistrant() {
            addRequestParameter("schoolId", "6");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "22");
            actionPerform();

            verifyForward("unrelease");

            FrontViewRegistrantProfileActionForm actual
                    = (FrontViewRegistrantProfileActionForm) getSession().getAttribute("FrontViewRegistrantProfileActionForm");

            assertThat(actual, is(nullValue()));
        }

    }

}
