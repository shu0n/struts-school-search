package front.action.entry;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.matcher.IsListComposedLabelValueBean.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.entry.FrontViewReceivedEntryActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class ViewReceivedEntryActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.ViewReceivedEntryActionTest.xml") {
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

        private static final String ACTION_PATH = "/viewReceivedEntry.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() {
            addRequestParameter("entryId", "6");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "1");
            actionPerform();

            verifyForward("success");

            FrontViewReceivedEntryActionForm expected = new FrontViewReceivedEntryActionForm();
            expected.setEntryId(6);
            expected.setEntrySchoolId(2);
            expected.setEntrySchoolName("テストスクール1");
            expected.setApplicantAccountId(5);
            expected.setApplicantLastName("田中");
            expected.setApplicantFirstName("五郎");
            expected.setApplicantLastNameKana("タナカ");
            expected.setApplicantFirstNameKana("ゴロウ");
            expected.setEntryQuestion("申込時の質問1");
            expected.setEntryStatusId(1);
            expected.setEntryStatusName("申込済");
            expected.setEntriedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            expected.setEntryUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            expected.setStrEntriedAt("2022/01/02 03:04:05");
            expected.setStrEntryUpdatedAt("2022/01/03 04:05:06");
            List<LabelValueBean> expectedEntryStatusList = new ArrayList<>();
            expectedEntryStatusList.add(new LabelValueBean("選択してください", ""));
            expectedEntryStatusList.add(new LabelValueBean("申込済", "1"));
            expectedEntryStatusList.add(new LabelValueBean("連絡済", "2"));
            expectedEntryStatusList.add(new LabelValueBean("キャンセル済", "3"));

            FrontViewReceivedEntryActionForm actual = (FrontViewReceivedEntryActionForm) getActionForm();

            assertThat(actual.getEntryStatusList(), is(sameComponentLavelAndValueAs(expectedEntryStatusList)));
            String[] excludeProperties = new String[] {"entryStatusList"};
            assertThat(actual, is(samePropertyValueAs(expected, excludeProperties)));
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoParameterExist() {
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "1");
            actionPerform();

            verifyForward("unexistence");

            FrontViewReceivedEntryActionForm actual = (FrontViewReceivedEntryActionForm) getActionForm();

            assertThat(actual, is(nullValue()));
        }

        @Test
        public void testExecuteInCaseLogout() {
            addRequestParameter("entryId", "6");
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/viewReceivedEntry.do?entryId=6");
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoEntryExist() {
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "1");
            actionPerform();

            verifyForward("unexistence");

            FrontViewReceivedEntryActionForm actual = (FrontViewReceivedEntryActionForm) getActionForm();

            assertThat(actual, is(nullValue()));
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNonRegistrant() {
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "5");
            actionPerform();

            verifyForward("unexistence");

            FrontViewReceivedEntryActionForm actual = (FrontViewReceivedEntryActionForm) getActionForm();

            assertThat(actual, is(nullValue()));
        }

    }

}
