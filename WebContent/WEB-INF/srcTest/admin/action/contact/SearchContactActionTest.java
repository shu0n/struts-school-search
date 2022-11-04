package admin.action.contact;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.property.PropertyTester.*;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import admin.actionform.contact.AdminSearchContactActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class SearchContactActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SearchContactActionTest.xml") {
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

        private static final String ACTION_PATH = "/searchContact.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteInCaseSearchByArrayOfContactIdAndContactAccountIdAndContactStatusId() {
            AdminSearchContactActionForm inForm = new AdminSearchContactActionForm();
            inForm.setStrContactAccountId("2,3,4,5,6");
            inForm.setStrContactAccountId("2,3");
            String[] contactStatusIdArray = {"1", "3"};
            inForm.setContactStatusIdArray(contactStatusIdArray);
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listContact.do");

            int[] expectedContactIdArray = {4, 5};

            AdminSearchContactActionForm actual = (AdminSearchContactActionForm) getActionForm();

            for(int i = 0; i < actual.getContactExtendFormList().size(); i++) {
                assertThat(actual.getContactExtendFormList().get(i).getContactId(), is(expectedContactIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseSearchByLikeContactLastNameAndContactFirstName() {
            AdminSearchContactActionForm inForm = new AdminSearchContactActionForm();
            inForm.setLikeContactLastName("田");
            inForm.setLikeContactFirstName("郎");
            inForm.setLikeContactLastNameKana("タナ");
            inForm.setLikeContactFirstNameKana("ロウ");
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listContact.do");

            int[] expectedContactIdArray = {1, 3};

            AdminSearchContactActionForm actual = (AdminSearchContactActionForm) getActionForm();

            for(int i = 0; i < actual.getContactExtendFormList().size(); i++) {
                assertThat(actual.getContactExtendFormList().get(i).getContactId(), is(expectedContactIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseSearchByMailAddress() {
            AdminSearchContactActionForm inForm = new AdminSearchContactActionForm();
            inForm.setLikeContactMailAddress("ta");
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listContact.do");

            int[] expectedContactIdArray = {3, 6};

            AdminSearchContactActionForm actual = (AdminSearchContactActionForm) getActionForm();

            for(int i = 0; i < actual.getContactExtendFormList().size(); i++) {
                assertThat(actual.getContactExtendFormList().get(i).getContactId(), is(expectedContactIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseSearchByRangeOfContactedDateAndContactUpdatedDate() {
            AdminSearchContactActionForm inForm = new AdminSearchContactActionForm();
            inForm.setFromContactedYear("2022");
            inForm.setFromContactedMonth("01");
            inForm.setFromContactedDay("06");
            inForm.setToContactedYear("2022");
            inForm.setToContactedMonth("01");
            inForm.setToContactedDay("10");
            inForm.setFromContactUpdatedYear("2022");
            inForm.setFromContactUpdatedMonth("01");
            inForm.setFromContactUpdatedDay("09");
            inForm.setToContactUpdatedYear("2022");
            inForm.setToContactUpdatedMonth("01");
            inForm.setToContactUpdatedDay("11");
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listContact.do");

            int[] expectedContactIdArray = {4, 5};

            AdminSearchContactActionForm actual = (AdminSearchContactActionForm) getActionForm();

            for(int i = 0; i < actual.getContactExtendFormList().size(); i++) {
                assertThat(actual.getContactExtendFormList().get(i).getContactId(), is(expectedContactIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseLogout() {
            AdminSearchContactActionForm inForm = new AdminSearchContactActionForm();
            inForm.setStrContactAccountId("2,3,4,5,6");
            inForm.setStrContactAccountId("2,3");
            String[] contactStatusIdArray = {"1", "3"};
            inForm.setContactStatusIdArray(contactStatusIdArray);
            setActionForm(inForm);
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/listContact.do");
        }

    }

}
