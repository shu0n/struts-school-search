package front.action.school;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsRegex.*;
import static test.property.PropertyTester.*;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.school.FrontRegisterSchoolActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.generator.GenerateFormFile;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class RegisterSchoolConfirmActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.RegisterSchoolConfirmActionTest.xml") {
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

        private static final String ACTION_PATH = "/registerSchoolConfirm.do";
        private static final String VALID_TOKEN = "validToken";
        private static final String INVALID_TOKEN = "invalidToken";

        // テスト時に生成される画像ファイルのファイル名を格納するためのリストを生成する。
        private List<String> fileNameList = new ArrayList<>();

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        public void tearDown() throws Exception {
            super.tearDown();
            String modulePath = getProperty("environment.properties").getProperty("module.path");
            String imgPath = getProperty("environment.properties").getProperty("img.path");
            // テスト時に生成された画像ファイルを削除する。
            if(!fileNameList.isEmpty()) {
                for(String fileName: fileNameList) {
                    Path filePath = Paths.get(modulePath + imgPath + fileName);
                    Files.delete(filePath);
                }
            }
            fileNameList = new ArrayList<>();
        }

        @Test
        public void testExecuteToForwardSuccess() {
            FrontRegisterSchoolActionForm inForm = new FrontRegisterSchoolActionForm();
            inForm.setRegistrantAccountId(22);
            inForm.setSchoolName("テストスクール1");
            inForm.setSchoolCategoryId(44);
            inForm.setSchoolSummary("スクール概要1");
            inForm.setSchoolDescription("スクール詳細1");
            inForm.setSchoolZipCode1("123");
            inForm.setSchoolZipCode2("4567");
            inForm.setSchoolPrefectureId(15);
            inForm.setSchoolCity("大阪市");
            inForm.setSchoolAddress1("大阪区");
            inForm.setSchoolAddress2("大阪町");
            inForm.setStrSchoolFee("4321");
            inForm.setSupplementaryFee("費用補足1");
            inForm.setSchoolUrl("http://test1.com");
            inForm.setSchoolReleasePropriety("1");
            inForm.setSchoolEntryPropriety("1");
            inForm.setSummaryImageFile(new GenerateFormFile(
                    "updateSummaryImage.png", "admin/action/school/updateSummaryImage.png", 1187));
            inForm.setDetailImage1File(new GenerateFormFile(
                    "updateDetailImage1.png", "admin/action/school/updateDetailImage1.png", 1187));
            inForm.setDetailImage2File(new GenerateFormFile(
                    "updateDetailImage2.png", "admin/action/school/updateDetailImage2.png", 1187));
            inForm.setDetailImage3File(new GenerateFormFile(
                    "updateDetailImage3.png", "admin/action/school/updateDetailImage3.png", 1187));
            inForm.setDetailImage4File(new GenerateFormFile(
                    "updateDetailImage4.png", "admin/action/school/updateDetailImage4.png", 1187));
            inForm.setDetailImage5File(new GenerateFormFile(
                    "updateDetailImage5.png", "admin/action/school/updateDetailImage5.png", 1187));
            inForm.setDetailImage6File(new GenerateFormFile(
                    "updateDetailImage6.png", "admin/action/school/updateDetailImage6.png", 1187));
            inForm.setDetailImage7File(new GenerateFormFile(
                    "updateDetailImage7.png", "admin/action/school/updateDetailImage7.png", 1187));
            inForm.setDetailImage8File(new GenerateFormFile(
                    "updateDetailImage8.png", "admin/action/school/updateDetailImage8.png", 1187));
            Map<Integer, String> schoolCategoryMap = new TreeMap<>();
            schoolCategoryMap.put(43, "日本文化");
            schoolCategoryMap.put(44, "茶道");
            schoolCategoryMap.put(45, "球技");
            schoolCategoryMap.put(46, "野球");
            inForm.setSchoolCategoryMap(schoolCategoryMap);
            Map<Integer, String> schoolPrefectureMap = new TreeMap<>();
            schoolPrefectureMap.put(12, "東京都");
            schoolPrefectureMap.put(13, "愛知県");
            schoolPrefectureMap.put(14, "大阪府");
            schoolPrefectureMap.put(15, "山口県");
            schoolPrefectureMap.put(16, "鹿児島県");
            inForm.setSchoolPrefectureMap(schoolPrefectureMap);
            Map<String, String> schoolReleaseProprietyMap = new TreeMap<>();
            schoolReleaseProprietyMap.put("0", "不可");
            schoolReleaseProprietyMap.put("1", "可");
            inForm.setSchoolReleaseProprietyMap(schoolReleaseProprietyMap);
            Map<String, String> schoolEntryProprietyMap = new TreeMap<>();
            schoolEntryProprietyMap.put("0", "不可");
            schoolEntryProprietyMap.put("1", "可");
            inForm.setSchoolEntryProprietyMap(schoolEntryProprietyMap);
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("success");

            int expectedRegistrantAccountId = 22;
            String expectedRegistrantLastName = "佐藤";
            String expectedRegistrantFirstName = "次郎";
            String expectedSchoolCategoryName = "茶道";
            String expectedSchoolPrefectureName = "山口県";
            BigDecimal expectedSchoolFee = new BigDecimal("4321");
            String expectedSchoolReleaseProprietyName = "可";
            String expectedSchoolEntryProprietyName = "可";
            String expectedFilePath = "/img/\\d{17}_\\d{1,3}.png";
            String expectedFileName = "\\d{17}_\\d{1,3}.png";

            FrontRegisterSchoolActionForm actual = (FrontRegisterSchoolActionForm) getActionForm();
            HttpServletRequest actualRequest = getRequest();
            fileNameList.add((String) actualRequest.getAttribute("summaryImageFileName"));
            fileNameList.add((String) actualRequest.getAttribute("detailImage1FileName"));
            fileNameList.add((String) actualRequest.getAttribute("detailImage2FileName"));
            fileNameList.add((String) actualRequest.getAttribute("detailImage3FileName"));
            fileNameList.add((String) actualRequest.getAttribute("detailImage4FileName"));
            fileNameList.add((String) actualRequest.getAttribute("detailImage5FileName"));
            fileNameList.add((String) actualRequest.getAttribute("detailImage6FileName"));
            fileNameList.add((String) actualRequest.getAttribute("detailImage7FileName"));
            fileNameList.add((String) actualRequest.getAttribute("detailImage8FileName"));

            assertThat(actual.getRegistrantAccountId(), is(expectedRegistrantAccountId));
            assertThat(actual.getRegistrantLastName(), is(expectedRegistrantLastName));
            assertThat(actual.getRegistrantFirstName(), is(expectedRegistrantFirstName));
            assertThat(actual.getSchoolCategoryName(), is(expectedSchoolCategoryName));
            assertThat(actual.getSchoolPrefectureName(), is(expectedSchoolPrefectureName));
            assertThat(actual.getSchoolFee(), is(expectedSchoolFee));
            assertThat(actual.getSchoolReleaseProprietyName(), is(expectedSchoolReleaseProprietyName));
            assertThat(actual.getSchoolEntryProprietyName(), is(expectedSchoolEntryProprietyName));
            assertThat((String) actualRequest.getAttribute("summaryImageFilePath"),
                    is(matchPatternAs(expectedFilePath)));
            assertThat((String) actualRequest.getAttribute("summaryImageFileName"),
                    is(matchPatternAs(expectedFileName)));
            assertThat((String) actualRequest.getAttribute("detailImage1FilePath"),
                    is(matchPatternAs(expectedFilePath)));
            assertThat((String) actualRequest.getAttribute("detailImage1FileName"),
                    is(matchPatternAs(expectedFileName)));
            assertThat((String) actualRequest.getAttribute("detailImage2FilePath"),
                    is(matchPatternAs(expectedFilePath)));
            assertThat((String) actualRequest.getAttribute("detailImage2FileName"),
                    is(matchPatternAs(expectedFileName)));
            assertThat((String) actualRequest.getAttribute("detailImage3FilePath"),
                    is(matchPatternAs(expectedFilePath)));
            assertThat((String) actualRequest.getAttribute("detailImage3FileName"),
                    is(matchPatternAs(expectedFileName)));
            assertThat((String) actualRequest.getAttribute("detailImage4FilePath"),
                    is(matchPatternAs(expectedFilePath)));
            assertThat((String) actualRequest.getAttribute("detailImage4FileName"),
                    is(matchPatternAs(expectedFileName)));
            assertThat((String) actualRequest.getAttribute("detailImage5FilePath"),
                    is(matchPatternAs(expectedFilePath)));
            assertThat((String) actualRequest.getAttribute("detailImage5FileName"),
                    is(matchPatternAs(expectedFileName)));
            assertThat((String) actualRequest.getAttribute("detailImage6FilePath"),
                    is(matchPatternAs(expectedFilePath)));
            assertThat((String) actualRequest.getAttribute("detailImage6FileName"),
                    is(matchPatternAs(expectedFileName)));
            assertThat((String) actualRequest.getAttribute("detailImage7FilePath"),
                    is(matchPatternAs(expectedFilePath)));
            assertThat((String) actualRequest.getAttribute("detailImage7FileName"),
                    is(matchPatternAs(expectedFileName)));
            assertThat((String) actualRequest.getAttribute("detailImage8FilePath"),
                    is(matchPatternAs(expectedFilePath)));
            assertThat((String) actualRequest.getAttribute("detailImage8FileName"),
                    is(matchPatternAs(expectedFileName)));
        }

        @Test
        public void testExecuteToForwardInvalid() {
            FrontRegisterSchoolActionForm inForm = new FrontRegisterSchoolActionForm();
            inForm.setRegistrantAccountId(22);
            inForm.setSchoolName("テストスクール1");
            inForm.setSchoolCategoryId(44);
            inForm.setSchoolSummary("スクール概要1");
            inForm.setSchoolDescription("スクール詳細1");
            inForm.setSchoolZipCode1("123");
            inForm.setSchoolZipCode2("4567");
            inForm.setSchoolPrefectureId(15);
            inForm.setSchoolCity("大阪市");
            inForm.setSchoolAddress1("大阪区");
            inForm.setSchoolAddress2("大阪町");
            inForm.setStrSchoolFee("4321");
            inForm.setSupplementaryFee("費用補足1");
            inForm.setSchoolUrl("http://test1.com");
            inForm.setSchoolReleasePropriety("0");
            inForm.setSchoolEntryPropriety("0");
            inForm.setSummaryImageFile(new GenerateFormFile(
                    "updateSummaryImage.png", "admin/action/school/updateSummaryImage.png", 1187));
            inForm.setDetailImage1File(new GenerateFormFile(
                    "updateDetailImage1.png", "admin/action/school/updateDetailImage1.png", 1187));
            inForm.setDetailImage2File(new GenerateFormFile(
                    "updateDetailImage2.png", "admin/action/school/updateDetailImage2.png", 1187));
            inForm.setDetailImage3File(new GenerateFormFile(
                    "updateDetailImage3.png", "admin/action/school/updateDetailImage3.png", 1187));
            inForm.setDetailImage4File(new GenerateFormFile(
                    "updateDetailImage4.png", "admin/action/school/updateDetailImage4.png", 1187));
            inForm.setDetailImage5File(new GenerateFormFile(
                    "updateDetailImage5.png", "admin/action/school/updateDetailImage5.png", 1187));
            inForm.setDetailImage6File(new GenerateFormFile(
                    "updateDetailImage6.png", "admin/action/school/updateDetailImage6.png", 1187));
            inForm.setDetailImage7File(new GenerateFormFile(
                    "updateDetailImage7.png", "admin/action/school/updateDetailImage7.png", 1187));
            inForm.setDetailImage8File(new GenerateFormFile(
                    "updateDetailImage8.png", "admin/action/school/updateDetailImage8.png", 1187));
            Map<Integer, String> schoolCategoryMap = new TreeMap<>();
            schoolCategoryMap.put(43, "日本文化");
            schoolCategoryMap.put(44, "茶道");
            schoolCategoryMap.put(45, "球技");
            schoolCategoryMap.put(46, "野球");
            inForm.setSchoolCategoryMap(schoolCategoryMap);
            Map<Integer, String> schoolPrefectureMap = new TreeMap<>();
            schoolPrefectureMap.put(12, "東京都");
            schoolPrefectureMap.put(13, "愛知県");
            schoolPrefectureMap.put(14, "大阪府");
            schoolPrefectureMap.put(15, "山口県");
            schoolPrefectureMap.put(16, "鹿児島県");
            inForm.setSchoolPrefectureMap(schoolPrefectureMap);
            Map<String, String> schoolReleaseProprietyMap = new TreeMap<>();
            schoolReleaseProprietyMap.put("0", "不可");
            schoolReleaseProprietyMap.put("1", "可");
            inForm.setSchoolReleaseProprietyMap(schoolReleaseProprietyMap);
            Map<String, String> schoolEntryProprietyMap = new TreeMap<>();
            schoolEntryProprietyMap.put("0", "不可");
            schoolEntryProprietyMap.put("1", "可");
            inForm.setSchoolEntryProprietyMap(schoolEntryProprietyMap);
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            actionPerform();

            verifyForward("invalid");
        }

    }

}
