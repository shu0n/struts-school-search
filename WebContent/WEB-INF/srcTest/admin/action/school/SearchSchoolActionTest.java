package admin.action.school;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.property.PropertyTester.*;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import admin.actionform.school.AdminSearchSchoolActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class SearchSchoolActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SearchSchoolActionTest.xml") {
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

        private static final String ACTION_PATH = "/searchSchool.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteInCaseSearchByArrayOfSchoolIdAndRegistrantAccountIdAndSchoolCategoryIdAndSchoolPrefectureId() {
            AdminSearchSchoolActionForm inForm = new AdminSearchSchoolActionForm();
            inForm.setStrSchoolId("1,2,3,4,5,6");
            inForm.setStrRegistrantAccountId("22,23,24,25");
            String[] schoolCategoryIdArray = {"43", "44", "45"};
            inForm.setSchoolCategoryIdArray(schoolCategoryIdArray);
            String[] schoolPrefectureIdArray = {"15", "16"};
            inForm.setSchoolPrefectureIdArray(schoolPrefectureIdArray);
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listSchool.do");

            int[] expectedSchoolIdArray = {2, 3};

            AdminSearchSchoolActionForm actual = (AdminSearchSchoolActionForm) getActionForm();

            for(int i = 0; i < actual.getSchoolExtendFormList().size(); i++) {
                assertThat(actual.getSchoolExtendFormList().get(i).getSchoolId(), is(expectedSchoolIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseSearchByArrayOfRegistrantAdminId() {
            AdminSearchSchoolActionForm inForm = new AdminSearchSchoolActionForm();
            inForm.setStrRegistrantAdminId("61,62");
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listSchool.do");

            int[] expectedSchoolIdArray = {6, 7};

            AdminSearchSchoolActionForm actual = (AdminSearchSchoolActionForm) getActionForm();

            for(int i = 0; i < actual.getSchoolExtendFormList().size(); i++) {
                assertThat(actual.getSchoolExtendFormList().get(i).getSchoolId(), is(expectedSchoolIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseSearchByRangeOfSchoolFee() {
            AdminSearchSchoolActionForm inForm = new AdminSearchSchoolActionForm();
            inForm.setMinSchoolFee("1000");
            inForm.setMaxSchoolFee("4000");
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listSchool.do");

            int[] expectedSchoolIdArray = {3, 4, 5};

            AdminSearchSchoolActionForm actual = (AdminSearchSchoolActionForm) getActionForm();

            for(int i = 0; i < actual.getSchoolExtendFormList().size(); i++) {
                assertThat(actual.getSchoolExtendFormList().get(i).getSchoolId(), is(expectedSchoolIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseSearchByRangeOfRangeOfRegisteredDateAndUpdatedDate() {
            AdminSearchSchoolActionForm inForm = new AdminSearchSchoolActionForm();
            inForm.setFromSchoolRegisteredYear("2021");
            inForm.setFromSchoolRegisteredMonth("01");
            inForm.setFromSchoolRegisteredDay("04");
            inForm.setToSchoolRegisteredYear("2021");
            inForm.setToSchoolRegisteredMonth("01");
            inForm.setToSchoolRegisteredDay("08");
            inForm.setFromSchoolUpdatedYear("2022");
            inForm.setFromSchoolUpdatedMonth("01");
            inForm.setFromSchoolUpdatedDay("03");
            inForm.setToSchoolUpdatedYear("2022");
            inForm.setToSchoolUpdatedMonth("01");
            inForm.setToSchoolUpdatedDay("07");
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listSchool.do");

            int[] expectedSchoolIdArray = {2, 3};

            AdminSearchSchoolActionForm actual = (AdminSearchSchoolActionForm) getActionForm();

            for(int i = 0; i < actual.getSchoolExtendFormList().size(); i++) {
                assertThat(actual.getSchoolExtendFormList().get(i).getSchoolId(), is(expectedSchoolIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseSearchByArrayOfReleaseProprietyAndEntryPropriety() {
            AdminSearchSchoolActionForm inForm = new AdminSearchSchoolActionForm();
            String[] schoolReleaseProprietyArray = {"0", "1"};
            inForm.setSchoolReleaseProprietyArray(schoolReleaseProprietyArray);
            String[] schoolEntryProprietyArray = {"0"};
            inForm.setSchoolEntryProprietyArray(schoolEntryProprietyArray);
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listSchool.do");

            int[] expectedSchoolIdArray = {2, 4, 5};

            AdminSearchSchoolActionForm actual = (AdminSearchSchoolActionForm) getActionForm();

            for(int i = 0; i < actual.getSchoolExtendFormList().size(); i++) {
                assertThat(actual.getSchoolExtendFormList().get(i).getSchoolId(), is(expectedSchoolIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseSearchByLikeSchoolName() {
            AdminSearchSchoolActionForm inForm = new AdminSearchSchoolActionForm();
            inForm.setLikeSchoolName("検証");
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listSchool.do");

            int[] expectedSchoolIdArray = {2, 3, 7};

            AdminSearchSchoolActionForm actual = (AdminSearchSchoolActionForm) getActionForm();

            for(int i = 0; i < actual.getSchoolExtendFormList().size(); i++) {
                assertThat(actual.getSchoolExtendFormList().get(i).getSchoolId(), is(expectedSchoolIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseLogout() {
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/listSchool.do");
        }

    }

}
