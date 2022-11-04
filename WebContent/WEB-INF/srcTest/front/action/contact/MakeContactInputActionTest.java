package front.action.contact;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.contact.FrontMakeContactActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class MakeContactInputActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.MakeContactInputActionTest.xml") {
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

        private static final String ACTION_PATH = "/makeContactInput.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccessInCaseLogout() {
            actionPerform();

            verifyForward("success");

            FrontMakeContactActionForm actual
                    = (FrontMakeContactActionForm) getSession().getAttribute("FrontMakeContactActionForm");

            assertThat(actual, is(notNullValue()));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseLogined() {
            getSession().setAttribute("accountId", "1");
            actionPerform();

            verifyForward("success");

            FrontMakeContactActionForm expected = new FrontMakeContactActionForm();
            expected.setContactAccountId(1);
            expected.setContactLastName("田中");
            expected.setContactFirstName("太郎");
            expected.setContactLastNameKana("タナカ");
            expected.setContactFirstNameKana("タロウ");
            expected.setContactMailAddress("tanaka@example.com");

            FrontMakeContactActionForm actual
                    = (FrontMakeContactActionForm) getSession().getAttribute("FrontMakeContactActionForm");

            assertThat(actual, is(samePropertyValueAs(expected)));
        }

    }

}
