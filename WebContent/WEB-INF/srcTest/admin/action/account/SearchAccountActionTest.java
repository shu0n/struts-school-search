package admin.action.account;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.property.PropertyTester.*;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import admin.actionform.account.AdminSearchAccountActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class SearchAccountActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SearchAccountActionTest.xml") {
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

        private static final String ACTION_PATH = "/searchAccount.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteInCaseSearchByArrayOfAccountIdAndSexIdAndPrefectureIdAndAccountStatus() {
            AdminSearchAccountActionForm inForm = new AdminSearchAccountActionForm();
            inForm.setStrAccountId("2,3,4");
            String[] sexIdArray = {"1" ,"2"};
            inForm.setSexIdArray(sexIdArray);
            String[] prefectureIdArray = {"3", "4"};
            inForm.setPrefectureIdArray(prefectureIdArray);
            String[] accountStatusArray = {"0"};
            inForm.setAccountStatusArray(accountStatusArray);
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listAccount.do");

            int[] expectedAccountIdArray = {3};

            AdminSearchAccountActionForm actual = (AdminSearchAccountActionForm) getActionForm();

            for(int i = 0; i < actual.getAccountExtendFormList().size(); i++) {
                assertThat(actual.getAccountExtendFormList().get(i).getAccountId(), is(expectedAccountIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseSearchIsLikeLastNameAndFirstNameAndLikeLastNameKanaAndFirstNameKana() {
            AdminSearchAccountActionForm inForm = new AdminSearchAccountActionForm();
            inForm.setLikeLastName("橋");
            inForm.setLikeFirstName("郎");
            inForm.setLikeLastNameKana("バシ");
            inForm.setLikeFirstNameKana("ロウ");
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listAccount.do");

            int[] expectedAccountIdArray = {4, 5};

            AdminSearchAccountActionForm actual = (AdminSearchAccountActionForm) getActionForm();

            for(int i = 0; i < actual.getAccountExtendFormList().size(); i++) {
                assertThat(actual.getAccountExtendFormList().get(i).getAccountId(), is(expectedAccountIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseSearchByBirthDate() {
            AdminSearchAccountActionForm inForm = new AdminSearchAccountActionForm();
            inForm.setFromBirthYear("1991");
            inForm.setFromBirthMonth("03");
            inForm.setFromBirthDay("01");
            inForm.setToBirthYear("1993");
            inForm.setToBirthMonth("05");
            inForm.setToBirthDay("31");
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listAccount.do");

            int[] expectedAccountIdArray = {2, 3, 4};

            AdminSearchAccountActionForm actual = (AdminSearchAccountActionForm) getActionForm();

            for(int i = 0; i < actual.getAccountExtendFormList().size(); i++) {
                assertThat(actual.getAccountExtendFormList().get(i).getAccountId(), is(expectedAccountIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseSearchByLikeMailAddress() {
            AdminSearchAccountActionForm inForm = new AdminSearchAccountActionForm();
            inForm.setLikeMailAddress("test");
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listAccount.do");

            int[] expectedAccountIdArray = {4, 5};

            AdminSearchAccountActionForm actual = (AdminSearchAccountActionForm) getActionForm();

            for(int i = 0; i < actual.getAccountExtendFormList().size(); i++) {
                assertThat(actual.getAccountExtendFormList().get(i).getAccountId(), is(expectedAccountIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseSearchByRangeOfCreatedDateAndUpdatedDate() {
            AdminSearchAccountActionForm inForm = new AdminSearchAccountActionForm();
            inForm.setFromAccountCreatedYear("2021");
            inForm.setFromAccountCreatedMonth("01");
            inForm.setFromAccountCreatedDay("01");
            inForm.setToAccountCreatedYear("2021");
            inForm.setToAccountCreatedMonth("01");
            inForm.setToAccountCreatedDay("04");
            inForm.setFromAccountUpdatedYear("2021");
            inForm.setFromAccountUpdatedMonth("01");
            inForm.setFromAccountUpdatedDay("02");
            inForm.setToAccountUpdatedYear("2021");
            inForm.setToAccountUpdatedMonth("01");
            inForm.setToAccountUpdatedDay("05");
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listAccount.do");

            int[] expectedAccountIdArray = {1, 2};

            AdminSearchAccountActionForm actual = (AdminSearchAccountActionForm) getActionForm();

            for(int i = 0; i < actual.getAccountExtendFormList().size(); i++) {
                assertThat(actual.getAccountExtendFormList().get(i).getAccountId(), is(expectedAccountIdArray[i]));
            }
        }

        @Test
        public void testExecuteInCaseLogout() {
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/listAccount.do");
        }

    }

}
