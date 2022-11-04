package front.action.entry;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.property.PropertyTester.*;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.entry.FrontSearchReceivedEntryActionForm;
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
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteInCaseSearchByArrayOfEntryStatusId() {
            FrontSearchReceivedEntryActionForm inForm = new FrontSearchReceivedEntryActionForm();
            inForm.setEntrySchoolId(2);
            String[] entryStatusId = {"1", "2", "4"};
            inForm.setEntryStatusIdArray(entryStatusId);
            setActionForm(inForm);
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "1");
            actionPerform();

            verifyForwardPath("/listReceivedEntry.do?schoolId=2");

            int[] expectedEntryIdArray = {6, 7};

            FrontSearchReceivedEntryActionForm actual = (FrontSearchReceivedEntryActionForm) getActionForm();

            for(int i = 0; i < actual.getEntryExtendFormList().size(); i++) {
                assertThat(actual.getEntryExtendFormList().get(i).getEntryId(), is(expectedEntryIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseSearchByLikeApplicantLastNameAndLikeApplicantLastNameKanaAndLikeApplicantFirstNameAndLikeApplicantFirstNameKana() {
            FrontSearchReceivedEntryActionForm inForm = new FrontSearchReceivedEntryActionForm();
            inForm.setEntrySchoolId(2);
            inForm.setLikeApplicantLastName("橋");
            inForm.setLikeApplicantFirstName("郎");
            inForm.setLikeApplicantLastNameKana("ハシ");
            inForm.setLikeApplicantFirstNameKana("ロウ");
            setActionForm(inForm);
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "1");
            actionPerform();

            verifyForwardPath("/listReceivedEntry.do?schoolId=2");

            int[] expectedEntryIdArray = {7, 8};

            FrontSearchReceivedEntryActionForm actual = (FrontSearchReceivedEntryActionForm) getActionForm();

            for(int i = 0; i < actual.getEntryExtendFormList().size(); i++) {
                assertThat(actual.getEntryExtendFormList().get(i).getEntryId(), is(expectedEntryIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseSearchByRangeOfEntrieDateAndEntryUpdatedDate() {
            FrontSearchReceivedEntryActionForm inForm = new FrontSearchReceivedEntryActionForm();
            inForm.setEntrySchoolId(6);
            inForm.setFromEntriedYear("2022");
            inForm.setFromEntriedMonth("07");
            inForm.setFromEntriedDay("02");
            inForm.setToEntriedYear("2022");
            inForm.setToEntriedMonth("09");
            inForm.setToEntriedDay("11");
            inForm.setFromEntryUpdatedYear("2022");
            inForm.setFromEntryUpdatedMonth("08");
            inForm.setFromEntryUpdatedDay("07");
            inForm.setToEntryUpdatedYear("2022");
            inForm.setToEntryUpdatedMonth("08");
            inForm.setToEntryUpdatedDay("09");
            setActionForm(inForm);
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "5");
            actionPerform();

            verifyForwardPath("/listReceivedEntry.do?schoolId=6");

            int[] expectedEntryIdArray = {9};

            FrontSearchReceivedEntryActionForm actual = (FrontSearchReceivedEntryActionForm) getActionForm();

            for(int i = 0; i < actual.getEntryExtendFormList().size(); i++) {
                assertThat(actual.getEntryExtendFormList().get(i).getEntryId(), is(expectedEntryIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseLogout() {
            FrontSearchReceivedEntryActionForm inForm = new FrontSearchReceivedEntryActionForm();
            inForm.setEntrySchoolId(2);
            String[] entryStatusId = {"1", "2", "4"};
            inForm.setEntryStatusIdArray(entryStatusId);
            setActionForm(inForm);
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/listReceivedEntry.do?schoolId=2");
        }

        @Test
        public void testExecuteInCaseNoRegistrant() {
            FrontSearchReceivedEntryActionForm inForm = new FrontSearchReceivedEntryActionForm();
            inForm.setEntrySchoolId(2);
            String[] entryStatusId = {"1", "2", "4"};
            inForm.setEntryStatusIdArray(entryStatusId);
            setActionForm(inForm);
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "3");
            actionPerform();

            verifyForward("unexistence");

            FrontSearchReceivedEntryActionForm actual = (FrontSearchReceivedEntryActionForm) getActionForm();

            assertThat(actual, is(nullValue()));
        }

    }

}
