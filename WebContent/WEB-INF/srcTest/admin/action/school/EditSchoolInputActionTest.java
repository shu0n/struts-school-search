package admin.action.school;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.math.BigDecimal;
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

import admin.actionform.school.AdminEditSchoolActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class EditSchoolInputActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.EditSchoolInputActionTest.xml") {
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

        private static final String ACTION_PATH = "/editSchoolInput.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccessInCaseRegisteredByAccount() {
            addRequestParameter("schoolId", "1");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            AdminEditSchoolActionForm expected = new AdminEditSchoolActionForm();
            expected.setSchoolId(1);
            expected.setRegistrantAccountId(22);
            expected.setRegistrantLastName("??????");
            expected.setRegistrantFirstName("??????");
            expected.setRegistrantLastNameKana("?????????");
            expected.setRegistrantFirstNameKana("?????????");
            expected.setRegistrantAdminId(0);
            expected.setSchoolName("?????????????????????1");
            expected.setSchoolCategoryId(43);
            expected.setSchoolCategoryName("????????????");
            expected.setSchoolSummary("??????????????????1");
            expected.setSchoolDescription("??????????????????1");
            expected.setSchoolZipCode1("123");
            expected.setSchoolZipCode2("4567");
            expected.setSchoolPrefectureId(14);
            expected.setSchoolPrefectureName("?????????");
            expected.setSchoolCity("?????????");
            expected.setSchoolAddress1("?????????");
            expected.setSchoolAddress2("?????????");
            expected.setSchoolFee(new BigDecimal(5432));
            expected.setStrSchoolFee("5432");
            expected.setSupplementaryFee("????????????1");
            expected.setSchoolUrl("http://test1.com");
            expected.setSchoolReleasePropriety("1");
            expected.setSchoolEntryPropriety("1");
            expected.setSummaryImageFileName("summary_image11.png");
            expected.setDetailImage1FileName("detail_image11.png");
            expected.setDetailImage2FileName("detail_image12.png");
            expected.setDetailImage3FileName("detail_image13.png");
            expected.setDetailImage4FileName("detail_image14.png");
            expected.setDetailImage5FileName("detail_image15.png");
            expected.setDetailImage6FileName("detail_image16.png");
            expected.setDetailImage7FileName("detail_image17.png");
            expected.setDetailImage8FileName("detail_image18.png");
            expected.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            expected.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            expected.setStrSchoolRegisteredAt("2022/01/02 03:04:05");
            expected.setStrSchoolUpdatedAt("2022/01/03 04:05:06");
            expected.setSummaryImageFilePath("/img/summary_image11.png");
            expected.setDetailImage1FilePath("/img/detail_image11.png");
            expected.setDetailImage2FilePath("/img/detail_image12.png");
            expected.setDetailImage3FilePath("/img/detail_image13.png");
            expected.setDetailImage4FilePath("/img/detail_image14.png");
            expected.setDetailImage5FilePath("/img/detail_image15.png");
            expected.setDetailImage6FilePath("/img/detail_image16.png");
            expected.setDetailImage7FilePath("/img/detail_image17.png");
            expected.setDetailImage8FilePath("/img/detail_image18.png");
            expected.setRegistrantKindName("???????????????");
            expected.setStrRegistrantAccountId("22");
            Map<Integer, String> expectedSchoolPrefectureMap = new TreeMap<>();
            expectedSchoolPrefectureMap.put(12, "?????????");
            expectedSchoolPrefectureMap.put(13, "?????????");
            expectedSchoolPrefectureMap.put(14, "?????????");
            expectedSchoolPrefectureMap.put(15, "?????????");
            expectedSchoolPrefectureMap.put(16, "????????????");
            expected.setSchoolPrefectureMap(expectedSchoolPrefectureMap);
            List<LabelValueBean> expectedSchoolPrefectureList = new ArrayList<>();
            expectedSchoolPrefectureList.add(new LabelValueBean("????????????????????????", ""));
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "12"));
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "13"));
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "14"));
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "15"));
            expectedSchoolPrefectureList.add(new LabelValueBean("????????????", "16"));
            expected.setSchoolPrefectureList(expectedSchoolPrefectureList);
            Map<Integer, String> expectedSchoolCategoryMap = new TreeMap<>();
            expectedSchoolCategoryMap.put(43, "????????????");
            expectedSchoolCategoryMap.put(44, "??????");
            expectedSchoolCategoryMap.put(45, "??????");
            expectedSchoolCategoryMap.put(46, "??????");
            expected.setSchoolCategoryMap(expectedSchoolCategoryMap);
            List<LabelValueBean> expectedSchoolCategoryList = new ArrayList<>();
            expectedSchoolCategoryList.add(new LabelValueBean("????????????????????????", ""));
            expectedSchoolCategoryList.add(new LabelValueBean("????????????", "43"));
            expectedSchoolCategoryList.add(new LabelValueBean("??????", "44"));
            expectedSchoolCategoryList.add(new LabelValueBean("??????", "45"));
            expectedSchoolCategoryList.add(new LabelValueBean("??????", "46"));
            expected.setSchoolCategoryList(expectedSchoolCategoryList);
            Map<String, String> expectedSchoolReleaseProprietyMap = new TreeMap<>();
            expectedSchoolReleaseProprietyMap.put("0", "??????");
            expectedSchoolReleaseProprietyMap.put("1", "???");
            expected.setSchoolReleaseProprietyMap(expectedSchoolReleaseProprietyMap);
            Map<String, String> expectedSchoolEntryProprietyMap = new TreeMap<>();
            expectedSchoolEntryProprietyMap.put("0", "??????");
            expectedSchoolEntryProprietyMap.put("1", "???");
            expected.setSchoolEntryProprietyMap(expectedSchoolEntryProprietyMap);

            AdminEditSchoolActionForm actual
                    = (AdminEditSchoolActionForm) getSession().getAttribute("AdminEditSchoolActionForm");

            assertThat(actual, is(samePropertyValueAs(expected)));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseRegisteredByAdmin() {
            addRequestParameter("schoolId", "6");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            AdminEditSchoolActionForm expected = new AdminEditSchoolActionForm();
            expected.setSchoolId(6);
            expected.setRegistrantAccountId(0);
            expected.setRegistrantLastName(null);
            expected.setRegistrantFirstName(null);
            expected.setRegistrantLastNameKana(null);
            expected.setRegistrantFirstNameKana(null);
            expected.setRegistrantAdminId(61);
            expected.setSchoolName("?????????????????????6");
            expected.setSchoolCategoryId(44);
            expected.setSchoolCategoryName("??????");
            expected.setSchoolSummary("??????????????????6");
            expected.setSchoolDescription("??????????????????6");
            expected.setSchoolZipCode1("123");
            expected.setSchoolZipCode2("5678");
            expected.setSchoolPrefectureId(12);
            expected.setSchoolPrefectureName("?????????");
            expected.setSchoolCity("?????????");
            expected.setSchoolAddress1("?????????");
            expected.setSchoolAddress2(null);
            expected.setSchoolFee(new BigDecimal("9876"));
            expected.setStrSchoolFee("9876");
            expected.setSupplementaryFee(null);
            expected.setSchoolUrl(null);
            expected.setSchoolReleasePropriety("1");
            expected.setSchoolEntryPropriety("1");
            expected.setSummaryImageFileName(null);
            expected.setDetailImage1FileName(null);
            expected.setDetailImage2FileName(null);
            expected.setDetailImage3FileName(null);
            expected.setDetailImage4FileName(null);
            expected.setDetailImage5FileName(null);
            expected.setDetailImage6FileName(null);
            expected.setDetailImage7FileName(null);
            expected.setDetailImage8FileName(null);
            expected.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-12 13:14:15.789"));
            expected.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-13 14:15:16.891"));
            expected.setStrSchoolRegisteredAt("2022/01/12 13:14:15");
            expected.setStrSchoolUpdatedAt("2022/01/13 14:15:16");
            expected.setSummaryImageFilePath("");
            expected.setDetailImage1FilePath("");
            expected.setDetailImage2FilePath("");
            expected.setDetailImage3FilePath("");
            expected.setDetailImage4FilePath("");
            expected.setDetailImage5FilePath("");
            expected.setDetailImage6FilePath("");
            expected.setDetailImage7FilePath("");
            expected.setDetailImage8FilePath("");
            expected.setRegistrantKindName("?????????");
            expected.setStrRegistrantAdminId("61");
            Map<Integer, String> expectedSchoolPrefectureMap = new TreeMap<>();
            expectedSchoolPrefectureMap.put(12, "?????????");
            expectedSchoolPrefectureMap.put(13, "?????????");
            expectedSchoolPrefectureMap.put(14, "?????????");
            expectedSchoolPrefectureMap.put(15, "?????????");
            expectedSchoolPrefectureMap.put(16, "????????????");
            expected.setSchoolPrefectureMap(expectedSchoolPrefectureMap);
            List<LabelValueBean> expectedSchoolPrefectureList = new ArrayList<>();
            expectedSchoolPrefectureList.add(new LabelValueBean("????????????????????????", ""));
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "12"));
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "13"));
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "14"));
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "15"));
            expectedSchoolPrefectureList.add(new LabelValueBean("????????????", "16"));
            expected.setSchoolPrefectureList(expectedSchoolPrefectureList);
            Map<Integer, String> expectedSchoolCategoryMap = new TreeMap<>();
            expectedSchoolCategoryMap.put(43, "????????????");
            expectedSchoolCategoryMap.put(44, "??????");
            expectedSchoolCategoryMap.put(45, "??????");
            expectedSchoolCategoryMap.put(46, "??????");
            expected.setSchoolCategoryMap(expectedSchoolCategoryMap);
            List<LabelValueBean> expectedSchoolCategoryList = new ArrayList<>();
            expectedSchoolCategoryList.add(new LabelValueBean("????????????????????????", ""));
            expectedSchoolCategoryList.add(new LabelValueBean("????????????", "43"));
            expectedSchoolCategoryList.add(new LabelValueBean("??????", "44"));
            expectedSchoolCategoryList.add(new LabelValueBean("??????", "45"));
            expectedSchoolCategoryList.add(new LabelValueBean("??????", "46"));
            expected.setSchoolCategoryList(expectedSchoolCategoryList);
            Map<String, String> expectedSchoolReleaseProprietyMap = new TreeMap<>();
            expectedSchoolReleaseProprietyMap.put("0", "??????");
            expectedSchoolReleaseProprietyMap.put("1", "???");
            expected.setSchoolReleaseProprietyMap(expectedSchoolReleaseProprietyMap);
            Map<String, String> expectedSchoolEntryProprietyMap = new TreeMap<>();
            expectedSchoolEntryProprietyMap.put("0", "??????");
            expectedSchoolEntryProprietyMap.put("1", "???");
            expected.setSchoolEntryProprietyMap(expectedSchoolEntryProprietyMap);

            AdminEditSchoolActionForm actual
                    = (AdminEditSchoolActionForm) getSession().getAttribute("AdminEditSchoolActionForm");

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
            addRequestParameter("schoolId", "1");
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/editSchoolInput.do?schoolId=1");
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoAccountExist() {
            addRequestParameter("schoolId", "2");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("unexistence");
        }

    }

}
