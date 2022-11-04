package admin.action.account;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;
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

import admin.actionform.account.AdminEditAccountActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class EditAccountInputActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.EditAccountInputActionTest.xml") {
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

        private static final String ACTION_PATH = "/editAccountInput.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() {
            addRequestParameter("accountId", "1");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            AdminEditAccountActionForm expected = new AdminEditAccountActionForm();
            expected.setAccountId(1);
            expected.setLastName("田中");
            expected.setFirstName("太郎");
            expected.setLastNameKana("タナカ");
            expected.setFirstNameKana("タロウ");
            expected.setSexId(2);
            expected.setSexName("女性");
            expected.setBirthDate(Timestamp.valueOf("1990-01-01 00:00:00"));
            expected.setPrefectureId(3);
            expected.setPrefectureName("東京都");
            expected.setMailAddress("tanaka@example.com");
            expected.setProfileImageFileName("image1.png");
            expected.setSelfIntroduction("自己紹介です。");
            expected.setAccountStatus("1");
            expected.setAccountCreatedAt(Timestamp.valueOf("2021-01-02 03:04:05.678"));
            expected.setAccountUpdatedAt(Timestamp.valueOf("2021-01-03 04:05:06.789"));
            expected.setBirthYear("1990");
            expected.setBirthMonth("01");
            expected.setBirthDay("01");
            expected.setProfileImageFilePath("/img/image1.png");
            Map<Integer, String> expectedSexMap = new TreeMap<>();
            expectedSexMap.put(1, "男性");
            expectedSexMap.put(2, "女性");
            expectedSexMap.put(3, "その他");
            expected.setSexMap(expectedSexMap);
            List<LabelValueBean> expectedBirthYearList = new ArrayList<>();
            expectedBirthYearList.add(new LabelValueBean("選択してください", ""));
            for(int i = 1920; i <= Calendar.getInstance().get(Calendar.YEAR); i++) {
                expectedBirthYearList.add(new LabelValueBean(String.valueOf(i), String.valueOf(i)));
            }
            expected.setBirthYearList(expectedBirthYearList);
            List<LabelValueBean> expectedBirthMonthList = new ArrayList<>();
            expectedBirthMonthList.add(new LabelValueBean("選択してください", ""));
            for(int i = 1; i <= 12; i++) {
                LabelValueBean monthBean = new LabelValueBean();
                monthBean.setLabel(String.valueOf(i));
                monthBean.setValue(String.valueOf(i));
                expectedBirthMonthList.add(new LabelValueBean(String.valueOf(i), String.valueOf(i)));
            }
            expected.setBirthMonthList(expectedBirthMonthList);
            List<LabelValueBean> expectedBirthDayhList = new ArrayList<>();
            expectedBirthDayhList.add(new LabelValueBean("選択してください", ""));
            for(int i = 1; i <= 31; i++) {
                expectedBirthDayhList.add(new LabelValueBean(String.valueOf(i), String.valueOf(i)));
            }
            expected.setBirthDayList(expectedBirthDayhList);
            Map<Integer, String> expectedPrefectureMap = new TreeMap<>();
            expectedPrefectureMap.put(1, "北海道");
            expectedPrefectureMap.put(2, "福島県");
            expectedPrefectureMap.put(3, "東京都");
            expected.setPrefectureMap(expectedPrefectureMap);
            List<LabelValueBean> expectedPrefectureList = new ArrayList<>();
            expectedPrefectureList.add(new LabelValueBean("選択してください", ""));
            expectedPrefectureList.add(new LabelValueBean("北海道", "1"));
            expectedPrefectureList.add(new LabelValueBean("福島県", "2"));
            expectedPrefectureList.add(new LabelValueBean("東京都", "3"));
            expected.setPrefectureList(expectedPrefectureList);
            Map<String, String> expectedAccountStatusMap = new TreeMap<>();
            expectedAccountStatusMap.put("0", "無効");
            expectedAccountStatusMap.put("1", "有効");
            expected.setAccountStatusMap(expectedAccountStatusMap);
            expected.setAccountStatusName("有効");
            expected.setStrAccountCreatedAt("2021/01/02 03:04:05");
            expected.setStrAccountUpdatedAt("2021/01/03 04:05:06");

            AdminEditAccountActionForm actual
                    = (AdminEditAccountActionForm) getSession().getAttribute("AdminEditAccountActionForm");

            assertThat(actual, is(samePropertyValueAs(expected)));
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoParameterExist() {
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("unexistence");
        }

        @Test
        public void testExecuteInCaseLogout() {
            addRequestParameter("accountId", "1");
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/editAccountInput.do?accountId=1");
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoAccountExist() {
            addRequestParameter("accountId", "2");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("unexistence");
        }

    }

}
