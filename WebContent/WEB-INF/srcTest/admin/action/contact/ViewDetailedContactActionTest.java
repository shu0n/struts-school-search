package admin.action.contact;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.matcher.IsListComposedLabelValueBean.*;
import static test.matcher.IsMap.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import admin.actionform.contact.AdminViewDetailedContactActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class ViewDetailedContactActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.ViewDetailedContactActionTest.xml") {
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

        private static final String ACTION_PATH = "/viewDetailedContact.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() {
            addRequestParameter("contactId", "3");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            AdminViewDetailedContactActionForm expected = new AdminViewDetailedContactActionForm();
            expected.setContactId(3);
            expected.setContactAccountId(1);
            expected.setContactLastName("田中中");
            expected.setContactFirstName("太郎郎");
            expected.setContactLastNameKana("タナカナカ");
            expected.setContactFirstNameKana("タロウロウ");
            expected.setContactMailAddress("tanakanaka@example.com");
            expected.setContactContent("問い合わせ3");
            expected.setContactStatusId(2);
            expected.setContactStatusName("受付済");
            expected.setContactedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            expected.setContactUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            expected.setStrContactedAt("2022/01/02 03:04:05");
            expected.setStrContactUpdatedAt("2022/01/03 04:05:06");
            Map<Integer, String >expectedContactStatusMap = new TreeMap<>();
            expectedContactStatusMap.put(2, "受付済");
            expectedContactStatusMap.put(3, "連絡済");
            expectedContactStatusMap.put(4, "電話済");
            List<LabelValueBean> expectedContactStatusList = new ArrayList<>();
            expectedContactStatusList.add(new LabelValueBean("受付済", "2"));
            expectedContactStatusList.add(new LabelValueBean("連絡済", "3"));
            expectedContactStatusList.add(new LabelValueBean("電話済", "4"));

            AdminViewDetailedContactActionForm actual = (AdminViewDetailedContactActionForm) getActionForm();

            assertThat(actual.getContactStatusMap(), is(sameEntrySetAs(expectedContactStatusMap)));
            assertThat(actual.getContactStatusList(), is(sameComponentLavelAndValueAs(expectedContactStatusList)));
            String[] excludeProperties = new String[] {"contactStatusMap", "contactStatusList"};
            assertThat(actual, is(samePropertyValueAs(expected, excludeProperties)));
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoParameterExist() {
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("unexistence");

            AdminViewDetailedContactActionForm actual = (AdminViewDetailedContactActionForm) getActionForm();

            assertThat(actual, is(nullValue()));
        }

        @Test
        public void testExecuteInCaseLogout() {
            addRequestParameter("contactId", "3");
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/viewDetailedContact.do?contactId=3");
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoContactExist() {
            addRequestParameter("contactId", "4");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("unexistence");

            AdminViewDetailedContactActionForm actual = (AdminViewDetailedContactActionForm) getActionForm();

            assertThat(actual, is(nullValue()));
        }

    }

}
