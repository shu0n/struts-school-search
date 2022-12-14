package admin.action.school;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import admin.actionform.school.AdminRegisterSchoolActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class RegisterSchoolInputActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.RegisterSchoolInputActionTest.xml") {
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

        private static final String ACTION_PATH = "/registerSchoolInput.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccessInCaseNoParameterExist() {
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            AdminRegisterSchoolActionForm expected = new AdminRegisterSchoolActionForm();
            Map<String, String> expectedRegistrantKindMap = new TreeMap<>();
            expectedRegistrantKindMap.put("admin", "????????????????????????");
            expectedRegistrantKindMap.put("account", "??????????????????????????????????????????");
            expected.setRegistrantKindMap(expectedRegistrantKindMap);
            expected.setRegistrantKind("admin");
            expected.setStrRegistrantAccountId(null);
            Map<Integer, String> expectedPrefectureMap = new TreeMap<>();
            expectedPrefectureMap.put(13, "?????????");
            expectedPrefectureMap.put(14, "?????????");
            expectedPrefectureMap.put(15, "?????????");
            expected.setSchoolPrefectureMap(expectedPrefectureMap);
            List<LabelValueBean> expectedSchoolPrefectureList = new ArrayList<>();
            expectedSchoolPrefectureList.add(new LabelValueBean("????????????????????????", ""));
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "13"));
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "14"));
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "15"));
            expected.setSchoolPrefectureList(expectedSchoolPrefectureList);
            Map<Integer, String> expectedSchoolCategoryMap = new TreeMap<>();
            expectedSchoolCategoryMap.put(43, "????????????");
            expectedSchoolCategoryMap.put(44, "??????");
            expected.setSchoolCategoryMap(expectedSchoolCategoryMap);
            List<LabelValueBean> expectedSchoolCategoryList = new ArrayList<>();
            expectedSchoolCategoryList.add(new LabelValueBean("????????????????????????", ""));
            expectedSchoolCategoryList.add(new LabelValueBean("????????????", "43"));
            expectedSchoolCategoryList.add(new LabelValueBean("??????", "44"));
            expected.setSchoolCategoryList(expectedSchoolCategoryList);
            Map<String, String> expectedSchoolReleaseProprietyMap = new TreeMap<>();
            expectedSchoolReleaseProprietyMap.put("0", "??????");
            expectedSchoolReleaseProprietyMap.put("1", "???");
            expected.setSchoolReleaseProprietyMap(expectedSchoolReleaseProprietyMap);
            expected.setSchoolReleasePropriety("0");
            Map<String, String> expectedSchoolEntryProprietyMap = new TreeMap<>();
            expectedSchoolEntryProprietyMap.put("0", "??????");
            expectedSchoolEntryProprietyMap.put("1", "???");
            expected.setSchoolEntryProprietyMap(expectedSchoolEntryProprietyMap);
            expected.setSchoolEntryPropriety("0");
            List<LabelValueBean> expectedRegistrantAccountIdList = new ArrayList<>();
            expectedRegistrantAccountIdList.add(new LabelValueBean("????????????????????????", ""));
            expectedRegistrantAccountIdList.add(new LabelValueBean("22", "22"));
            expectedRegistrantAccountIdList.add(new LabelValueBean("23", "23"));
            expectedRegistrantAccountIdList.add(new LabelValueBean("24", "24"));
            expected.setRegistrantAccountIdList(expectedRegistrantAccountIdList);

            AdminRegisterSchoolActionForm actual
                    = (AdminRegisterSchoolActionForm) getSession().getAttribute("AdminRegisterSchoolActionForm");

            assertThat(actual, is(samePropertyValueAs(expected)));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseParameterExist() {
            addRequestParameter("accountId", "1");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            AdminRegisterSchoolActionForm expected = new AdminRegisterSchoolActionForm();
            Map<String, String> expectedRegistrantKindMap = new TreeMap<>();
            expectedRegistrantKindMap.put("admin", "????????????????????????");
            expectedRegistrantKindMap.put("account", "??????????????????????????????????????????");
            expected.setRegistrantKindMap(expectedRegistrantKindMap);
            expected.setRegistrantKind("account");
            expected.setStrRegistrantAccountId("1");
            Map<Integer, String> expectedPrefectureMap = new TreeMap<>();
            expectedPrefectureMap.put(13, "?????????");
            expectedPrefectureMap.put(14, "?????????");
            expectedPrefectureMap.put(15, "?????????");
            expected.setSchoolPrefectureMap(expectedPrefectureMap);
            List<LabelValueBean> expectedSchoolPrefectureList = new ArrayList<>();
            expectedSchoolPrefectureList.add(new LabelValueBean("????????????????????????", ""));
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "13"));
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "14"));
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "15"));
            expected.setSchoolPrefectureList(expectedSchoolPrefectureList);
            Map<Integer, String> expectedSchoolCategoryMap = new TreeMap<>();
            expectedSchoolCategoryMap.put(43, "????????????");
            expectedSchoolCategoryMap.put(44, "??????");
            expected.setSchoolCategoryMap(expectedSchoolCategoryMap);
            List<LabelValueBean> expectedSchoolCategoryList = new ArrayList<>();
            expectedSchoolCategoryList.add(new LabelValueBean("????????????????????????", ""));
            expectedSchoolCategoryList.add(new LabelValueBean("????????????", "43"));
            expectedSchoolCategoryList.add(new LabelValueBean("??????", "44"));
            expected.setSchoolCategoryList(expectedSchoolCategoryList);
            Map<String, String> expectedSchoolReleaseProprietyMap = new TreeMap<>();
            expectedSchoolReleaseProprietyMap.put("0", "??????");
            expectedSchoolReleaseProprietyMap.put("1", "???");
            expected.setSchoolReleaseProprietyMap(expectedSchoolReleaseProprietyMap);
            expected.setSchoolReleasePropriety("0");
            Map<String, String> expectedSchoolEntryProprietyMap = new TreeMap<>();
            expectedSchoolEntryProprietyMap.put("0", "??????");
            expectedSchoolEntryProprietyMap.put("1", "???");
            expected.setSchoolEntryProprietyMap(expectedSchoolEntryProprietyMap);
            expected.setSchoolEntryPropriety("0");
            List<LabelValueBean> expectedRegistrantAccountIdList = new ArrayList<>();
            expectedRegistrantAccountIdList.add(new LabelValueBean("????????????????????????", ""));
            expectedRegistrantAccountIdList.add(new LabelValueBean("22", "22"));
            expectedRegistrantAccountIdList.add(new LabelValueBean("23", "23"));
            expectedRegistrantAccountIdList.add(new LabelValueBean("24", "24"));
            expected.setRegistrantAccountIdList(expectedRegistrantAccountIdList);

            AdminRegisterSchoolActionForm actual
                    = (AdminRegisterSchoolActionForm) getSession().getAttribute("AdminRegisterSchoolActionForm");

            assertThat(actual, is(samePropertyValueAs(expected)));
        }

        @Test
        public void testExecuteInCaseLogoutInCaseNoParameterExist() {
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/registerSchoolInput.do");
        }

        @Test
        public void testExecuteInCaseLogoutInCaseParameterExist() {
            addRequestParameter("accountId", "1");
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/registerSchoolInput.do?accountId=1");
        }

    }

}
