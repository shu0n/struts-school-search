package admin.action.entry;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.property.PropertyTester.*;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import admin.actionform.entry.AdminSearchReceivedEntryActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class SearchReceivedEntryActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SearchReceivedEntryActionTest.xml") {
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

        private static final String ACTION_PATH = "/searchReceivedEntry.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteInCaseSearchByArrayOfEntryIdAndEntrySchoolIdAndApplicantAccountIdAndEntryStatusId() {
            AdminSearchReceivedEntryActionForm inForm = new AdminSearchReceivedEntryActionForm();
            inForm.setStrEntryId("9,10,11");
            inForm.setStrEntrySchoolId("5,6");
            inForm.setStrApplicantAccountId("1,2,3,6");
            String[] entryStatusId = {"1", "2", "4"};
            inForm.setEntryStatusIdArray(entryStatusId);
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listReceivedEntry.do");

            int[] expectedEntryIdArray = {9, 10};

            AdminSearchReceivedEntryActionForm actual = (AdminSearchReceivedEntryActionForm) getActionForm();

            for(int i = 0; i < actual.getEntryExtendFormList().size(); i++) {
                assertThat(actual.getEntryExtendFormList().get(i).getEntryId(), is(expectedEntryIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseSearchByLikeEntrySchoolNameAndLikeApplicantLastNameAndLikeApplicantLastNameKanaAndLikeApplicantFirstNameAndLikeApplicantFirstNameKana() {
            AdminSearchReceivedEntryActionForm inForm = new AdminSearchReceivedEntryActionForm();
            inForm.setLikeEntrySchoolName("テスト");
            inForm.setLikeApplicantLastName("橋");
            inForm.setLikeApplicantFirstName("郎");
            inForm.setLikeApplicantLastNameKana("ハシ");
            inForm.setLikeApplicantFirstNameKana("ロウ");
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listReceivedEntry.do");

            int[] expectedEntryIdArray = {7};

            AdminSearchReceivedEntryActionForm actual = (AdminSearchReceivedEntryActionForm) getActionForm();

            for(int i = 0; i < actual.getEntryExtendFormList().size(); i++) {
                assertThat(actual.getEntryExtendFormList().get(i).getEntryId(), is(expectedEntryIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseSearchByRangeOfEntrieDateAndEntryUpdatedDate() {
            AdminSearchReceivedEntryActionForm inForm = new AdminSearchReceivedEntryActionForm();
            inForm.setFromEntriedYear("2022");
            inForm.setFromEntriedMonth("01");
            inForm.setFromEntriedDay("01");
            inForm.setToEntriedYear("2022");
            inForm.setToEntriedMonth("05");
            inForm.setToEntriedDay("06");
            inForm.setFromEntryUpdatedYear("2022");
            inForm.setFromEntryUpdatedMonth("04");
            inForm.setFromEntryUpdatedDay("05");
            inForm.setToEntryUpdatedYear("2022");
            inForm.setToEntryUpdatedMonth("08");
            inForm.setToEntryUpdatedDay("09");
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listReceivedEntry.do");

            int[] expectedEntryIdArray = {7, 8};

            AdminSearchReceivedEntryActionForm actual = (AdminSearchReceivedEntryActionForm) getActionForm();

            for(int i = 0; i < actual.getEntryExtendFormList().size(); i++) {
                assertThat(actual.getEntryExtendFormList().get(i).getEntryId(), is(expectedEntryIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseLogout() {
            AdminSearchReceivedEntryActionForm inForm = new AdminSearchReceivedEntryActionForm();
            inForm.setStrEntryId("9,10,11");
            inForm.setStrEntrySchoolId("5,6");
            inForm.setStrApplicantAccountId("1,2,3,6");
            String[] entryStatusId = {"1", "2", "4"};
            inForm.setEntryStatusIdArray(entryStatusId);
            setActionForm(inForm);
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/listReceivedEntry.do");
        }

    }

}
