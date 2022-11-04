package front.action.school;

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

import front.actionform.school.FrontRegisterSchoolActionForm;
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
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() {
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "1");
            actionPerform();

            verifyForward("success");

            FrontRegisterSchoolActionForm expected = new FrontRegisterSchoolActionForm();
            expected.setRegistrantAccountId(1);
            Map<Integer, String> expectedPrefectureMap = new TreeMap<>();
            expectedPrefectureMap.put(13, "愛知県");
            expectedPrefectureMap.put(14, "大阪府");
            expectedPrefectureMap.put(15, "山口県");
            expected.setSchoolPrefectureMap(expectedPrefectureMap);
            List<LabelValueBean> expectedSchoolPrefectureList = new ArrayList<>();
            expectedSchoolPrefectureList.add(new LabelValueBean("選択してください", ""));
            expectedSchoolPrefectureList.add(new LabelValueBean("愛知県", "13"));
            expectedSchoolPrefectureList.add(new LabelValueBean("大阪府", "14"));
            expectedSchoolPrefectureList.add(new LabelValueBean("山口県", "15"));
            expected.setSchoolPrefectureList(expectedSchoolPrefectureList);
            Map<Integer, String> expectedSchoolCategoryMap = new TreeMap<>();
            expectedSchoolCategoryMap.put(43, "日本文化");
            expectedSchoolCategoryMap.put(44, "茶道");
            expected.setSchoolCategoryMap(expectedSchoolCategoryMap);
            List<LabelValueBean> expectedSchoolCategoryList = new ArrayList<>();
            expectedSchoolCategoryList.add(new LabelValueBean("選択してください", ""));
            expectedSchoolCategoryList.add(new LabelValueBean("日本文化", "43"));
            expectedSchoolCategoryList.add(new LabelValueBean("茶道", "44"));
            expected.setSchoolCategoryList(expectedSchoolCategoryList);
            Map<String, String> expectedSchoolReleaseProprietyMap = new TreeMap<>();
            expectedSchoolReleaseProprietyMap.put("0", "不可");
            expectedSchoolReleaseProprietyMap.put("1", "可");
            expected.setSchoolReleaseProprietyMap(expectedSchoolReleaseProprietyMap);
            expected.setSchoolReleasePropriety("0");
            Map<String, String> expectedSchoolEntryProprietyMap = new TreeMap<>();
            expectedSchoolEntryProprietyMap.put("0", "不可");
            expectedSchoolEntryProprietyMap.put("1", "可");
            expected.setSchoolEntryProprietyMap(expectedSchoolEntryProprietyMap);
            expected.setSchoolEntryPropriety("0");

            FrontRegisterSchoolActionForm actual
                    = (FrontRegisterSchoolActionForm) getSession().getAttribute("FrontRegisterSchoolActionForm");

            assertThat(actual, is(samePropertyValueAs(expected)));
        }

        @Test
        public void testExecuteInCaseLogout() {
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/registerSchoolInput.do");
        }

    }

}
