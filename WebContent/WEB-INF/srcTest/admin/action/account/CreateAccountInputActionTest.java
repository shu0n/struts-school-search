package admin.action.account;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import admin.actionform.account.AdminCreateAccountActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class CreateAccountInputActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.CreateAccountInputActionTest.xml") {
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

        private static final String ACTION_PATH = "/createAccountInput.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() {
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            AdminCreateAccountActionForm expected = new AdminCreateAccountActionForm();
            Map<Integer, String> expectedSexMap = new TreeMap<>();
            expectedSexMap.put(1, "??????");
            expectedSexMap.put(2, "??????");
            expectedSexMap.put(3, "?????????");
            expected.setSexMap(expectedSexMap);
            List<LabelValueBean> expectedBirthYearList = new ArrayList<>();
            expectedBirthYearList.add(new LabelValueBean("????????????????????????", ""));
            for(int i = 1920; i <= Calendar.getInstance().get(Calendar.YEAR); i++) {
                expectedBirthYearList.add(new LabelValueBean(String.valueOf(i), String.valueOf(i)));
            }
            expected.setBirthYearList(expectedBirthYearList);
            List<LabelValueBean> expectedBirthMonthList = new ArrayList<>();
            expectedBirthMonthList.add(new LabelValueBean("????????????????????????", ""));
            for(int i = 1; i <= 12; i++) {
                LabelValueBean monthBean = new LabelValueBean();
                monthBean.setLabel(String.valueOf(i));
                monthBean.setValue(String.valueOf(i));
                expectedBirthMonthList.add(new LabelValueBean(String.valueOf(i), String.valueOf(i)));
            }
            expected.setBirthMonthList(expectedBirthMonthList);
            List<LabelValueBean> expectedBirthDayhList = new ArrayList<>();
            expectedBirthDayhList.add(new LabelValueBean("????????????????????????", ""));
            for(int i = 1; i <= 31; i++) {
                expectedBirthDayhList.add(new LabelValueBean(String.valueOf(i), String.valueOf(i)));
            }
            expected.setBirthDayList(expectedBirthDayhList);
            Map<Integer, String> expectedPrefectureMap = new TreeMap<>();
            expectedPrefectureMap.put(1, "?????????");
            expectedPrefectureMap.put(2, "?????????");
            expectedPrefectureMap.put(3, "?????????");
            expectedPrefectureMap.put(4, "?????????");
            expected.setPrefectureMap(expectedPrefectureMap);
            List<LabelValueBean> expectedPrefectureList = new ArrayList<>();
            expectedPrefectureList.add(new LabelValueBean("????????????????????????", ""));
            expectedPrefectureList.add(new LabelValueBean("?????????", "1"));
            expectedPrefectureList.add(new LabelValueBean("?????????", "2"));
            expectedPrefectureList.add(new LabelValueBean("?????????", "3"));
            expectedPrefectureList.add(new LabelValueBean("?????????", "4"));
            expected.setPrefectureList(expectedPrefectureList);
            Map<String, String> expectedAccountStatusMap = new TreeMap<>();
            expectedAccountStatusMap.put("0", "??????");
            expectedAccountStatusMap.put("1", "??????");
            expected.setAccountStatusMap(expectedAccountStatusMap);
            expected.setAccountStatus("0");
            AdminCreateAccountActionForm actual
                    = (AdminCreateAccountActionForm) getSession().getAttribute("AdminCreateAccountActionForm");

            assertThat(actual, is(samePropertyValueAs(expected)));
        }

        @Test
        public void testExecuteInCaseLogout() {
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/createAccountInput.do");
        }

    }

}
