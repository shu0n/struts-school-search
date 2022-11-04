package admin.action.school;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsRegex.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import actionform.SchoolExtendActionForm;
import admin.actionform.school.AdminListSchoolActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class DownloadSchoolCsvActionTest {

    public static class executeTest extends MockStrutsTestCase {

        private static final String ACTION_PATH = "/downloadSchoolCsv.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteInCaseDownloadTempleteCsvSuccessfully() {
            AdminListSchoolActionForm inForm = new AdminListSchoolActionForm();
            List<SchoolExtendActionForm> schoolExtendFormList = new ArrayList<>();
            SchoolExtendActionForm firstForm = new SchoolExtendActionForm();
            firstForm.setSchoolId(1);
            firstForm.setRegistrantAccountId(2);
            firstForm.setRegistrantAdminId(0);
            firstForm.setSchoolName("スクール１");
            firstForm.setSchoolCategoryId(3);
            firstForm.setSchoolCategoryName("カテゴリー１");
            firstForm.setSchoolSummary("概要１");
            firstForm.setSchoolDescription("詳細１");
            firstForm.setSchoolZipCode1("123");
            firstForm.setSchoolZipCode2("4567");
            firstForm.setSchoolPrefectureId(4);
            firstForm.setSchoolPrefectureName("北海道");
            firstForm.setSchoolCity("北海道市");
            firstForm.setSchoolAddress1("北海道町");
            firstForm.setSchoolAddress2("北海道区");
            firstForm.setSchoolFee(new BigDecimal("1000"));
            firstForm.setSupplementaryFee("費用補足１");
            firstForm.setSchoolUrl("http://test1.com");
            firstForm.setSchoolReleasePropriety("1");
            firstForm.setSchoolEntryPropriety("1");
            firstForm.setSummaryImageFileName("summaryImage11.png");
            firstForm.setDetailImage1FileName("detailImage11.png");
            firstForm.setDetailImage2FileName("detailImage21.png");
            firstForm.setDetailImage3FileName("detailImage31.png");
            firstForm.setDetailImage4FileName("detailImage41.png");
            firstForm.setDetailImage5FileName("detailImage51.png");
            firstForm.setDetailImage6FileName("detailImage61.png");
            firstForm.setDetailImage7FileName("detailImage71.png");
            firstForm.setDetailImage8FileName("detailImage81.png");
            firstForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            firstForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            schoolExtendFormList.add(firstForm);
            SchoolExtendActionForm secondForm = new SchoolExtendActionForm();
            secondForm.setSchoolId(2);
            secondForm.setRegistrantAccountId(0);
            secondForm.setRegistrantAdminId(1);
            secondForm.setSchoolName("スクール２");
            secondForm.setSchoolCategoryId(4);
            secondForm.setSchoolCategoryName("カテゴリー２");
            secondForm.setSchoolSummary("概要２");
            secondForm.setSchoolDescription("詳細２");
            secondForm.setSchoolZipCode1("234");
            secondForm.setSchoolZipCode2("5678");
            secondForm.setSchoolPrefectureId(5);
            secondForm.setSchoolPrefectureName("青森県");
            secondForm.setSchoolCity("青森市");
            secondForm.setSchoolAddress1("青森町");
            secondForm.setSchoolAddress2("青森区");
            secondForm.setSchoolFee(new BigDecimal("2000"));
            secondForm.setSupplementaryFee("費用補足２");
            secondForm.setSchoolUrl("http://test2.com");
            secondForm.setSchoolReleasePropriety("0");
            secondForm.setSchoolEntryPropriety("0");
            secondForm.setSummaryImageFileName("summaryImage12.png");
            secondForm.setDetailImage1FileName("detailImage12.png");
            secondForm.setDetailImage2FileName("detailImage22.png");
            secondForm.setDetailImage3FileName("detailImage32.png");
            secondForm.setDetailImage4FileName("detailImage42.png");
            secondForm.setDetailImage5FileName("detailImage52.png");
            secondForm.setDetailImage6FileName("detailImage62.png");
            secondForm.setDetailImage7FileName("detailImage72.png");
            secondForm.setDetailImage8FileName("detailImage82.png");
            secondForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-04 05:06:07.789"));
            secondForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
            schoolExtendFormList.add(secondForm);
            SchoolExtendActionForm thirdForm = new SchoolExtendActionForm();
            thirdForm.setSchoolId(3);
            thirdForm.setRegistrantAccountId(2);
            thirdForm.setRegistrantAdminId(0);
            thirdForm.setSchoolName("スクール３");
            thirdForm.setSchoolCategoryId(5);
            thirdForm.setSchoolCategoryName("カテゴリー３");
            thirdForm.setSchoolSummary("概要３");
            thirdForm.setSchoolDescription("詳細３");
            thirdForm.setSchoolZipCode1("345");
            thirdForm.setSchoolZipCode2("6789");
            thirdForm.setSchoolPrefectureId(6);
            thirdForm.setSchoolPrefectureName("岩手県");
            thirdForm.setSchoolCity("岩手市");
            thirdForm.setSchoolAddress1("岩手町");
            thirdForm.setSchoolAddress2(null);
            thirdForm.setSchoolFee(new BigDecimal("3000"));
            thirdForm.setSupplementaryFee(null);
            thirdForm.setSchoolUrl(null);
            thirdForm.setSchoolReleasePropriety("1");
            thirdForm.setSchoolEntryPropriety("1");
            thirdForm.setSummaryImageFileName(null);
            thirdForm.setDetailImage1FileName(null);
            thirdForm.setDetailImage2FileName(null);
            thirdForm.setDetailImage3FileName(null);
            thirdForm.setDetailImage4FileName(null);
            thirdForm.setDetailImage5FileName(null);
            thirdForm.setDetailImage6FileName(null);
            thirdForm.setDetailImage7FileName(null);
            thirdForm.setDetailImage8FileName(null);
            thirdForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
            thirdForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-07 08:09:10.234"));
            schoolExtendFormList.add(thirdForm);
            inForm.setSchoolExtendFormList(schoolExtendFormList);
            setActionForm(inForm);
            addRequestParameter("template", "true");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward(null);

            String expectedContentType = "text/csv;charset=UTF8";
            String expectedContentDisposition = "attachment; filename=\"schools_for_upload_template.csv\"";

            HttpServletResponse actual = getResponse();

            assertThat((String) actual.getContentType(), is(expectedContentType));
            assertThat(
                    (String) actual.getHeader("Content-Disposition"),
                    is(matchPatternAs(expectedContentDisposition)));
        }

        @Test
        public void testExecuteInCaseDownloadActualDataCsvSuccessfully() {
            AdminListSchoolActionForm inForm = new AdminListSchoolActionForm();
            List<SchoolExtendActionForm> schoolExtendFormList = new ArrayList<>();
            SchoolExtendActionForm firstForm = new SchoolExtendActionForm();
            firstForm.setSchoolId(1);
            firstForm.setRegistrantAccountId(2);
            firstForm.setRegistrantAdminId(0);
            firstForm.setSchoolName("スクール１");
            firstForm.setSchoolCategoryId(3);
            firstForm.setSchoolCategoryName("カテゴリー１");
            firstForm.setSchoolSummary("概要１");
            firstForm.setSchoolDescription("詳細１");
            firstForm.setSchoolZipCode1("123");
            firstForm.setSchoolZipCode2("4567");
            firstForm.setSchoolPrefectureId(4);
            firstForm.setSchoolPrefectureName("北海道");
            firstForm.setSchoolCity("北海道市");
            firstForm.setSchoolAddress1("北海道町");
            firstForm.setSchoolAddress2("北海道区");
            firstForm.setSchoolFee(new BigDecimal("1000"));
            firstForm.setSupplementaryFee("費用補足１");
            firstForm.setSchoolUrl("http://test1.com");
            firstForm.setSchoolReleasePropriety("1");
            firstForm.setSchoolEntryPropriety("1");
            firstForm.setSummaryImageFileName("summaryImage11.png");
            firstForm.setDetailImage1FileName("detailImage11.png");
            firstForm.setDetailImage2FileName("detailImage21.png");
            firstForm.setDetailImage3FileName("detailImage31.png");
            firstForm.setDetailImage4FileName("detailImage41.png");
            firstForm.setDetailImage5FileName("detailImage51.png");
            firstForm.setDetailImage6FileName("detailImage61.png");
            firstForm.setDetailImage7FileName("detailImage71.png");
            firstForm.setDetailImage8FileName("detailImage81.png");
            firstForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            firstForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            schoolExtendFormList.add(firstForm);
            SchoolExtendActionForm secondForm = new SchoolExtendActionForm();
            secondForm.setSchoolId(2);
            secondForm.setRegistrantAccountId(0);
            secondForm.setRegistrantAdminId(1);
            secondForm.setSchoolName("スクール２");
            secondForm.setSchoolCategoryId(4);
            secondForm.setSchoolCategoryName("カテゴリー２");
            secondForm.setSchoolSummary("概要２");
            secondForm.setSchoolDescription("詳細２");
            secondForm.setSchoolZipCode1("234");
            secondForm.setSchoolZipCode2("5678");
            secondForm.setSchoolPrefectureId(5);
            secondForm.setSchoolPrefectureName("青森県");
            secondForm.setSchoolCity("青森市");
            secondForm.setSchoolAddress1("青森町");
            secondForm.setSchoolAddress2("青森区");
            secondForm.setSchoolFee(new BigDecimal("2000"));
            secondForm.setSupplementaryFee("費用補足２");
            secondForm.setSchoolUrl("http://test2.com");
            secondForm.setSchoolReleasePropriety("0");
            secondForm.setSchoolEntryPropriety("0");
            secondForm.setSummaryImageFileName("summaryImage12.png");
            secondForm.setDetailImage1FileName("detailImage12.png");
            secondForm.setDetailImage2FileName("detailImage22.png");
            secondForm.setDetailImage3FileName("detailImage32.png");
            secondForm.setDetailImage4FileName("detailImage42.png");
            secondForm.setDetailImage5FileName("detailImage52.png");
            secondForm.setDetailImage6FileName("detailImage62.png");
            secondForm.setDetailImage7FileName("detailImage72.png");
            secondForm.setDetailImage8FileName("detailImage82.png");
            secondForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-04 05:06:07.789"));
            secondForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
            schoolExtendFormList.add(secondForm);
            SchoolExtendActionForm thirdForm = new SchoolExtendActionForm();
            thirdForm.setSchoolId(3);
            thirdForm.setRegistrantAccountId(2);
            thirdForm.setRegistrantAdminId(0);
            thirdForm.setSchoolName("スクール３");
            thirdForm.setSchoolCategoryId(5);
            thirdForm.setSchoolCategoryName("カテゴリー３");
            thirdForm.setSchoolSummary("概要３");
            thirdForm.setSchoolDescription("詳細３");
            thirdForm.setSchoolZipCode1("345");
            thirdForm.setSchoolZipCode2("6789");
            thirdForm.setSchoolPrefectureId(6);
            thirdForm.setSchoolPrefectureName("岩手県");
            thirdForm.setSchoolCity("岩手市");
            thirdForm.setSchoolAddress1("岩手町");
            thirdForm.setSchoolAddress2(null);
            thirdForm.setSchoolFee(new BigDecimal("3000"));
            thirdForm.setSupplementaryFee(null);
            thirdForm.setSchoolUrl(null);
            thirdForm.setSchoolReleasePropriety("1");
            thirdForm.setSchoolEntryPropriety("1");
            thirdForm.setSummaryImageFileName(null);
            thirdForm.setDetailImage1FileName(null);
            thirdForm.setDetailImage2FileName(null);
            thirdForm.setDetailImage3FileName(null);
            thirdForm.setDetailImage4FileName(null);
            thirdForm.setDetailImage5FileName(null);
            thirdForm.setDetailImage6FileName(null);
            thirdForm.setDetailImage7FileName(null);
            thirdForm.setDetailImage8FileName(null);
            thirdForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
            thirdForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-07 08:09:10.234"));
            schoolExtendFormList.add(thirdForm);
            inForm.setSchoolExtendFormList(schoolExtendFormList);
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward(null);

            String expectedContentType = "text/csv;charset=UTF8";
            String expectedContentDisposition = "attachment; filename=\"schools_\\d{17}.csv\"";

            HttpServletResponse actual = getResponse();

            assertThat((String) actual.getContentType(), is(expectedContentType));
            assertThat(
                    (String) actual.getHeader("Content-Disposition"),
                    is(matchPatternAs(expectedContentDisposition)));
        }

        @Test
        public void testExecuteInCaseLogout() {
            AdminListSchoolActionForm inForm = new AdminListSchoolActionForm();
            List<SchoolExtendActionForm> schoolExtendFormList = new ArrayList<>();
            SchoolExtendActionForm firstForm = new SchoolExtendActionForm();
            firstForm.setSchoolId(1);
            firstForm.setRegistrantAccountId(2);
            firstForm.setRegistrantAdminId(0);
            firstForm.setSchoolName("スクール１");
            firstForm.setSchoolCategoryId(3);
            firstForm.setSchoolCategoryName("カテゴリー１");
            firstForm.setSchoolSummary("概要１");
            firstForm.setSchoolDescription("詳細１");
            firstForm.setSchoolZipCode1("123");
            firstForm.setSchoolZipCode2("4567");
            firstForm.setSchoolPrefectureId(4);
            firstForm.setSchoolPrefectureName("北海道");
            firstForm.setSchoolCity("北海道市");
            firstForm.setSchoolAddress1("北海道町");
            firstForm.setSchoolAddress2("北海道区");
            firstForm.setSchoolFee(new BigDecimal("1000"));
            firstForm.setSupplementaryFee("費用補足１");
            firstForm.setSchoolUrl("http://test1.com");
            firstForm.setSchoolReleasePropriety("1");
            firstForm.setSchoolEntryPropriety("1");
            firstForm.setSummaryImageFileName("summaryImage11.png");
            firstForm.setDetailImage1FileName("detailImage11.png");
            firstForm.setDetailImage2FileName("detailImage21.png");
            firstForm.setDetailImage3FileName("detailImage31.png");
            firstForm.setDetailImage4FileName("detailImage41.png");
            firstForm.setDetailImage5FileName("detailImage51.png");
            firstForm.setDetailImage6FileName("detailImage61.png");
            firstForm.setDetailImage7FileName("detailImage71.png");
            firstForm.setDetailImage8FileName("detailImage81.png");
            firstForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            firstForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            schoolExtendFormList.add(firstForm);
            SchoolExtendActionForm secondForm = new SchoolExtendActionForm();
            secondForm.setSchoolId(2);
            secondForm.setRegistrantAccountId(0);
            secondForm.setRegistrantAdminId(1);
            secondForm.setSchoolName("スクール２");
            secondForm.setSchoolCategoryId(4);
            secondForm.setSchoolCategoryName("カテゴリー２");
            secondForm.setSchoolSummary("概要２");
            secondForm.setSchoolDescription("詳細２");
            secondForm.setSchoolZipCode1("234");
            secondForm.setSchoolZipCode2("5678");
            secondForm.setSchoolPrefectureId(5);
            secondForm.setSchoolPrefectureName("青森県");
            secondForm.setSchoolCity("青森市");
            secondForm.setSchoolAddress1("青森町");
            secondForm.setSchoolAddress2("青森区");
            secondForm.setSchoolFee(new BigDecimal("2000"));
            secondForm.setSupplementaryFee("費用補足２");
            secondForm.setSchoolUrl("http://test2.com");
            secondForm.setSchoolReleasePropriety("0");
            secondForm.setSchoolEntryPropriety("0");
            secondForm.setSummaryImageFileName("summaryImage12.png");
            secondForm.setDetailImage1FileName("detailImage12.png");
            secondForm.setDetailImage2FileName("detailImage22.png");
            secondForm.setDetailImage3FileName("detailImage32.png");
            secondForm.setDetailImage4FileName("detailImage42.png");
            secondForm.setDetailImage5FileName("detailImage52.png");
            secondForm.setDetailImage6FileName("detailImage62.png");
            secondForm.setDetailImage7FileName("detailImage72.png");
            secondForm.setDetailImage8FileName("detailImage82.png");
            secondForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-04 05:06:07.789"));
            secondForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
            schoolExtendFormList.add(secondForm);
            SchoolExtendActionForm thirdForm = new SchoolExtendActionForm();
            thirdForm.setSchoolId(3);
            thirdForm.setRegistrantAccountId(2);
            thirdForm.setRegistrantAdminId(0);
            thirdForm.setSchoolName("スクール３");
            thirdForm.setSchoolCategoryId(5);
            thirdForm.setSchoolCategoryName("カテゴリー３");
            thirdForm.setSchoolSummary("概要３");
            thirdForm.setSchoolDescription("詳細３");
            thirdForm.setSchoolZipCode1("345");
            thirdForm.setSchoolZipCode2("6789");
            thirdForm.setSchoolPrefectureId(6);
            thirdForm.setSchoolPrefectureName("岩手県");
            thirdForm.setSchoolCity("岩手市");
            thirdForm.setSchoolAddress1("岩手町");
            thirdForm.setSchoolAddress2(null);
            thirdForm.setSchoolFee(new BigDecimal("3000"));
            thirdForm.setSupplementaryFee(null);
            thirdForm.setSchoolUrl(null);
            thirdForm.setSchoolReleasePropriety("1");
            thirdForm.setSchoolEntryPropriety("1");
            thirdForm.setSummaryImageFileName(null);
            thirdForm.setDetailImage1FileName(null);
            thirdForm.setDetailImage2FileName(null);
            thirdForm.setDetailImage3FileName(null);
            thirdForm.setDetailImage4FileName(null);
            thirdForm.setDetailImage5FileName(null);
            thirdForm.setDetailImage6FileName(null);
            thirdForm.setDetailImage7FileName(null);
            thirdForm.setDetailImage8FileName(null);
            thirdForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
            thirdForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-07 08:09:10.234"));
            schoolExtendFormList.add(thirdForm);
            inForm.setSchoolExtendFormList(schoolExtendFormList);
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/listSchool.do");
        }

        @Test
        public void testExecuteInCaseError() {
            AdminListSchoolActionForm inForm = new AdminListSchoolActionForm();
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listSchool.do");
            verifyActionErrors(new String[] {"ダウンロードに失敗しました。"});
        }

    }

}
