package admin.action.school;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.matcher.IsListComposedLabelValueBean.*;
import static test.property.PropertyTester.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import actionform.SchoolExtendActionForm;
import admin.actionform.school.AdminListSchoolActionForm;
import admin.actionform.school.AdminSearchSchoolActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class ListSchoolActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.ListSchoolActionTest.xml") {
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

        private static final String ACTION_PATH = "/listSchool.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccessInCaseFirstAccess() {
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            int[] expectedSchoolIdArray = {7, 6, 5, 4, 3, 2, 1};
            SchoolExtendActionForm expectedForm1 = new SchoolExtendActionForm();
            expectedForm1.setSchoolId(7);
            expectedForm1.setRegistrantAccountId(0);
            expectedForm1.setRegistrantLastName(null);
            expectedForm1.setRegistrantFirstName(null);
            expectedForm1.setRegistrantLastNameKana(null);
            expectedForm1.setRegistrantFirstNameKana(null);
            expectedForm1.setProfileImageFileName(null);
            expectedForm1.setSelfIntroduction(null);
            expectedForm1.setRegistrantAdminId(62);
            expectedForm1.setSchoolName("?????????????????????7");
            expectedForm1.setSchoolCategoryId(43);
            expectedForm1.setSchoolCategoryName("????????????");
            expectedForm1.setSchoolSummary("??????????????????7");
            expectedForm1.setSchoolDescription("??????????????????7");
            expectedForm1.setSchoolZipCode1("123");
            expectedForm1.setSchoolZipCode2("6789");
            expectedForm1.setSchoolPrefectureId(13);
            expectedForm1.setSchoolPrefectureName("?????????");
            expectedForm1.setSchoolCity("?????????");
            expectedForm1.setSchoolAddress1("?????????");
            expectedForm1.setSchoolAddress2(null);
            expectedForm1.setSchoolFee(new BigDecimal("8765"));
            expectedForm1.setSupplementaryFee(null);
            expectedForm1.setSchoolUrl(null);
            expectedForm1.setSchoolReleasePropriety("1");
            expectedForm1.setSchoolReleaseProprietyName("???");
            expectedForm1.setSchoolEntryPropriety("1");
            expectedForm1.setSchoolEntryProprietyName("???");
            expectedForm1.setSummaryImageFileName(null);
            expectedForm1.setDetailImage1FileName(null);
            expectedForm1.setDetailImage2FileName(null);
            expectedForm1.setDetailImage3FileName(null);
            expectedForm1.setDetailImage4FileName(null);
            expectedForm1.setDetailImage5FileName(null);
            expectedForm1.setDetailImage6FileName(null);
            expectedForm1.setDetailImage7FileName(null);
            expectedForm1.setDetailImage8FileName(null);
            expectedForm1.setSummaryImageFilePath(null);
            expectedForm1.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-14 15:16:17.912"));
            expectedForm1.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-15 16:17:18.123"));
            expectedForm1.setStrSchoolRegisteredAt("2022/01/14 15:16:17");
            expectedForm1.setStrSchoolUpdatedAt("2022/01/15 16:17:18");
            expectedForm1.setAllEntryNum(3);
            SchoolExtendActionForm expectedForm2 = new SchoolExtendActionForm();
            expectedForm2.setSchoolId(1);
            expectedForm2.setRegistrantAccountId(22);
            expectedForm2.setRegistrantLastName("??????");
            expectedForm2.setRegistrantFirstName("??????");
            expectedForm2.setRegistrantLastNameKana("?????????");
            expectedForm2.setRegistrantFirstNameKana("?????????");
            expectedForm2.setProfileImageFileName("profile_image2.png");
            expectedForm2.setSelfIntroduction("????????????2");
            expectedForm2.setRegistrantAdminId(0);
            expectedForm2.setSchoolName("?????????????????????1");
            expectedForm2.setSchoolCategoryId(43);
            expectedForm2.setSchoolCategoryName("????????????");
            expectedForm2.setSchoolSummary("??????????????????1");
            expectedForm2.setSchoolDescription("??????????????????1");
            expectedForm2.setSchoolZipCode1("123");
            expectedForm2.setSchoolZipCode2("4567");
            expectedForm2.setSchoolPrefectureId(14);
            expectedForm2.setSchoolPrefectureName("?????????");
            expectedForm2.setSchoolCity("?????????");
            expectedForm2.setSchoolAddress1("?????????");
            expectedForm2.setSchoolAddress2("?????????");
            expectedForm2.setSchoolFee(new BigDecimal("5432"));
            expectedForm2.setSupplementaryFee("????????????1");
            expectedForm2.setSchoolUrl("http://test1.com");
            expectedForm2.setSchoolReleasePropriety("1");
            expectedForm2.setSchoolReleaseProprietyName("???");
            expectedForm2.setSchoolEntryPropriety("1");
            expectedForm2.setSchoolEntryProprietyName("???");
            expectedForm2.setSummaryImageFileName("summary_image11.png");
            expectedForm2.setDetailImage1FileName("detail_image11.png");
            expectedForm2.setDetailImage2FileName("detail_image12.png");
            expectedForm2.setDetailImage3FileName("detail_image13.png");
            expectedForm2.setDetailImage4FileName("detail_image14.png");
            expectedForm2.setDetailImage5FileName("detail_image15.png");
            expectedForm2.setDetailImage6FileName("detail_image16.png");
            expectedForm2.setDetailImage7FileName("detail_image17.png");
            expectedForm2.setDetailImage8FileName("detail_image18.png");
            expectedForm2.setSummaryImageFilePath("/img/summary_image11.png");
            expectedForm2.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            expectedForm2.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            expectedForm2.setStrSchoolRegisteredAt("2022/01/02 03:04:05");
            expectedForm2.setStrSchoolUpdatedAt("2022/01/03 04:05:06");
            expectedForm2.setAllEntryNum(0);
            List<LabelValueBean> expectedSchoolCategoryList = new ArrayList<>();
            expectedSchoolCategoryList.add(new LabelValueBean("??????", "41"));
            expectedSchoolCategoryList.add(new LabelValueBean("??????", "42"));
            expectedSchoolCategoryList.add(new LabelValueBean("????????????", "43"));
            expectedSchoolCategoryList.add(new LabelValueBean("??????", "44"));
            expectedSchoolCategoryList.add(new LabelValueBean("??????", "45"));
            expectedSchoolCategoryList.add(new LabelValueBean("??????", "46"));
            expectedSchoolCategoryList.add(new LabelValueBean("????????????", "47"));
            List<LabelValueBean> expectedSchoolPrefectureList = new ArrayList<>();
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "11"));
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "12"));
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "13"));
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "14"));
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "15"));
            expectedSchoolPrefectureList.add(new LabelValueBean("????????????", "16"));
            expectedSchoolPrefectureList.add(new LabelValueBean("?????????", "17"));
            List<LabelValueBean> expectedSchoolReleaseProprietyList = new ArrayList<>();
            expectedSchoolReleaseProprietyList.add(new LabelValueBean("??????", "0"));
            expectedSchoolReleaseProprietyList.add(new LabelValueBean("???", "1"));
            List<LabelValueBean> expectedSchoolEntryProprietyList = new ArrayList<>();
            expectedSchoolEntryProprietyList.add(new LabelValueBean("??????", "0"));
            expectedSchoolEntryProprietyList.add(new LabelValueBean("???", "1"));
            List<LabelValueBean> expectedSortKindForSchoolList = new ArrayList<>();
            expectedSortKindForSchoolList.add(new LabelValueBean("????????????", ""));
            expectedSortKindForSchoolList.add(new LabelValueBean("????????????????????????", "byDescendingRegisteredAt"));
            expectedSortKindForSchoolList.add(new LabelValueBean("?????????????????????", "byAccendingRegisteredAt"));
            expectedSortKindForSchoolList.add(new LabelValueBean("????????????????????????", "byDescendingUpdatedAt"));
            expectedSortKindForSchoolList.add(new LabelValueBean("?????????????????????", "byAccendingUpdatedAt"));
            expectedSortKindForSchoolList.add(new LabelValueBean("??????????????????", "byDescendingSchoolFee"));
            expectedSortKindForSchoolList.add(new LabelValueBean("??????????????????", "byAccendingSchoolFee"));
            int expectedTotalForm = 7;
            int expectedTotalPage = 1;
            int expectedCurrentPage = 1;

            AdminListSchoolActionForm actual = (AdminListSchoolActionForm) getActionForm();

            for(int i = 0; i < actual.getSchoolExtendFormList().size(); i++) {
                assertThat(actual.getSchoolExtendFormList().get(i).getSchoolId(), is(expectedSchoolIdArray[i]));
            }
            assertThat(actual.getSchoolExtendFormList().get(0), is(samePropertyValueAs(expectedForm1)));
            assertThat(actual.getSchoolExtendFormList().get(6), is(samePropertyValueAs(expectedForm2)));
            assertThat(
                    actual.getSchoolCategoryList(),
                    is(sameComponentLavelAndValueAs(expectedSchoolCategoryList)));
            assertThat(
                    actual.getSchoolPrefectureList(),
                    is(sameComponentLavelAndValueAs(expectedSchoolPrefectureList)));
            assertThat(
                    actual.getSchoolReleaseProprietyList(),
                    is(sameComponentLavelAndValueAs(expectedSchoolReleaseProprietyList)));
            assertThat(
                    actual.getSchoolEntryProprietyList(),
                    is(sameComponentLavelAndValueAs(expectedSchoolEntryProprietyList)));
            assertThat(
                    actual.getSortKindForSchoolList(),
                    is(sameComponentLavelAndValueAs(expectedSortKindForSchoolList)));
            assertThat(actual.getSortKindForSchool(), is(nullValue()));
            assertThat(actual.getTotalForm(), is(expectedTotalForm));
            assertThat(actual.getTotalPage(), is(expectedTotalPage));
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseAdminSearchSchoolActionFormIsNotNull() {
            AdminSearchSchoolActionForm adminSearchSchoolForm = new AdminSearchSchoolActionForm();
            List<SchoolExtendActionForm> schoolExtendFormList = new ArrayList<>();
            SchoolExtendActionForm firstForm = new SchoolExtendActionForm();
            firstForm.setSchoolId(21);
            firstForm.setSchoolReleasePropriety("1");
            firstForm.setSchoolEntryPropriety("1");
            firstForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-28 09:10:11.234"));
            firstForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-31 02:03:04.567"));
            schoolExtendFormList.add(firstForm);
            SchoolExtendActionForm secondForm = new SchoolExtendActionForm();
            secondForm.setSchoolId(20);
            secondForm.setSchoolReleasePropriety("0");
            secondForm.setSchoolEntryPropriety("0");
            secondForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-27 08:09:10.123"));
            secondForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-30 01:02:03.456"));
            schoolExtendFormList.add(secondForm);
            SchoolExtendActionForm thirdForm = new SchoolExtendActionForm();
            thirdForm.setSchoolId(19);
            thirdForm.setSchoolReleasePropriety("1");
            thirdForm.setSchoolEntryPropriety("1");
            thirdForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-26 07:08:09.123"));
            thirdForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-29 01:02:03.456"));
            schoolExtendFormList.add(thirdForm);
            SchoolExtendActionForm fourthForm = new SchoolExtendActionForm();
            fourthForm.setSchoolId(18);
            fourthForm.setSchoolReleasePropriety("0");
            fourthForm.setSchoolEntryPropriety("0");
            fourthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-25 06:07:08.912"));
            fourthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-28 09:10:11.234"));
            schoolExtendFormList.add(fourthForm);
            SchoolExtendActionForm fifthForm = new SchoolExtendActionForm();
            fifthForm.setSchoolId(17);
            fifthForm.setSchoolReleasePropriety("1");
            fifthForm.setSchoolEntryPropriety("1");
            fifthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-25 05:06:07.891"));
            fifthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-27 08:09:10.123"));
            schoolExtendFormList.add(fifthForm);
            SchoolExtendActionForm sixthForm = new SchoolExtendActionForm();
            sixthForm.setSchoolId(16);
            sixthForm.setSchoolReleasePropriety("0");
            sixthForm.setSchoolEntryPropriety("0");
            sixthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-24 06:07:08.912"));
            sixthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-26 07:08:09.123"));
            schoolExtendFormList.add(sixthForm);
            SchoolExtendActionForm seventhForm = new SchoolExtendActionForm();
            seventhForm.setSchoolId(15);
            seventhForm.setSchoolReleasePropriety("1");
            seventhForm.setSchoolEntryPropriety("1");
            seventhForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-24 05:06:07.891"));
            seventhForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-25 06:07:08.912"));
            schoolExtendFormList.add(seventhForm);
            SchoolExtendActionForm eighthForm = new SchoolExtendActionForm();
            eighthForm.setSchoolId(14);
            eighthForm.setSchoolReleasePropriety("0");
            eighthForm.setSchoolEntryPropriety("0");
            eighthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-23 04:05:06.789"));
            eighthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-24 05:06:07.891"));
            schoolExtendFormList.add(eighthForm);
            SchoolExtendActionForm ninthForm = new SchoolExtendActionForm();
            ninthForm.setSchoolId(13);
            ninthForm.setSchoolReleasePropriety("1");
            ninthForm.setSchoolEntryPropriety("1");
            ninthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-22 03:04:05.678"));
            ninthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-23 04:05:06.789"));
            schoolExtendFormList.add(ninthForm);
            SchoolExtendActionForm tenthForm = new SchoolExtendActionForm();
            tenthForm.setSchoolId(12);
            tenthForm.setSchoolReleasePropriety("0");
            tenthForm.setSchoolEntryPropriety("0");
            tenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-21 02:03:04.567"));
            tenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-22 03:04:05.678"));
            schoolExtendFormList.add(tenthForm);
            SchoolExtendActionForm eleventhForm = new SchoolExtendActionForm();
            eleventhForm.setSchoolId(11);
            eleventhForm.setSchoolReleasePropriety("1");
            eleventhForm.setSchoolEntryPropriety("1");
            eleventhForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-20 01:02:03.456"));
            eleventhForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-21 02:03:04.567"));
            schoolExtendFormList.add(eleventhForm);
            SchoolExtendActionForm twelfthForm = new SchoolExtendActionForm();
            twelfthForm.setSchoolId(10);
            twelfthForm.setSchoolReleasePropriety("0");
            twelfthForm.setSchoolEntryPropriety("0");
            twelfthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-19 01:02:03.456"));
            twelfthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-20 02:03:04.567"));
            schoolExtendFormList.add(twelfthForm);
            SchoolExtendActionForm thirteenthForm = new SchoolExtendActionForm();
            thirteenthForm.setSchoolId(9);
            thirteenthForm.setSchoolReleasePropriety("1");
            thirteenthForm.setSchoolEntryPropriety("1");
            thirteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-18 09:10:11.234"));
            thirteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-19 01:02:03.456"));
            schoolExtendFormList.add(thirteenthForm);
            SchoolExtendActionForm fourteenthForm = new SchoolExtendActionForm();
            fourteenthForm.setSchoolId(8);
            fourteenthForm.setSchoolReleasePropriety("0");
            fourteenthForm.setSchoolEntryPropriety("0");
            fourteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-17 08:09:10.123"));
            fourteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-18 09:10:11.234"));
            schoolExtendFormList.add(fourteenthForm);
            SchoolExtendActionForm fifteenthForm = new SchoolExtendActionForm();
            fifteenthForm.setSchoolId(7);
            fifteenthForm.setSchoolReleasePropriety("1");
            fifteenthForm.setSchoolEntryPropriety("1");
            fifteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-16 07:08:09.123"));
            fifteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-17 08:09:10.234"));
            schoolExtendFormList.add(fifteenthForm);
            SchoolExtendActionForm sixteenthForm = new SchoolExtendActionForm();
            sixteenthForm.setSchoolId(6);
            sixteenthForm.setSchoolReleasePropriety("0");
            sixteenthForm.setSchoolEntryPropriety("0");
            sixteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-15 06:07:08.912"));
            sixteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-16 07:08:09.123"));
            schoolExtendFormList.add(sixteenthForm);
            SchoolExtendActionForm seventeenthForm = new SchoolExtendActionForm();
            seventeenthForm.setSchoolId(5);
            seventeenthForm.setSchoolReleasePropriety("1");
            seventeenthForm.setSchoolEntryPropriety("1");
            seventeenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-14 05:06:07.891"));
            seventeenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-15 06:07:08.912"));
            schoolExtendFormList.add(seventeenthForm);
            SchoolExtendActionForm eighteenthForm = new SchoolExtendActionForm();
            eighteenthForm.setSchoolId(4);
            eighteenthForm.setSchoolReleasePropriety("0");
            eighteenthForm.setSchoolEntryPropriety("0");
            eighteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-13 04:05:06.789"));
            eighteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-14 05:06:07.891"));
            schoolExtendFormList.add(eighteenthForm);
            SchoolExtendActionForm nineteenthForm = new SchoolExtendActionForm();
            nineteenthForm.setSchoolId(3);
            nineteenthForm.setSchoolReleasePropriety("1");
            nineteenthForm.setSchoolEntryPropriety("1");
            nineteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-12 03:04:05.678"));
            nineteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-13 04:05:06.789"));
            schoolExtendFormList.add(nineteenthForm);
            SchoolExtendActionForm twentiethForm = new SchoolExtendActionForm();
            twentiethForm.setSchoolId(2);
            twentiethForm.setSchoolReleasePropriety("0");
            twentiethForm.setSchoolEntryPropriety("0");
            twentiethForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-11 02:03:04.567"));
            twentiethForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-12 03:04:05.678"));
            schoolExtendFormList.add(twentiethForm);
            SchoolExtendActionForm twentyFirstForm = new SchoolExtendActionForm();
            twentyFirstForm.setSchoolId(1);
            twentyFirstForm.setSchoolReleasePropriety("1");
            twentyFirstForm.setSchoolEntryPropriety("1");
            twentyFirstForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-10 03:04:05.678"));
            twentyFirstForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-11 02:03:04.567"));
            schoolExtendFormList.add(twentyFirstForm);
            adminSearchSchoolForm.setSchoolExtendFormList(schoolExtendFormList);
            getRequest().setAttribute("AdminSearchSchoolActionForm", adminSearchSchoolForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            int[] expectedSchoolIdArray =
                    {21, 20, 19, 18, 17, 16,
                    15, 14, 13, 12, 11,
                    10, 9, 8, 7, 6,
                    5, 4, 3, 2, 1};
            int[] expectedDisplayedSchoolIdArray =
                    {21, 20, 19, 18, 17,
                    16, 15, 14, 13, 12,
                    11, 10, 9, 8, 7, 6,
                    5, 4, 3, 2};
            int expectedTotalForm = 21;
            int expectedTotalPage = 2;
            int expectedCurrentPage = 1;

            AdminListSchoolActionForm actual = (AdminListSchoolActionForm) getActionForm();

            for(int i = 0; i < actual.getSchoolExtendFormList().size(); i++) {
                assertThat(actual.getSchoolExtendFormList().get(i).getSchoolId(), is(expectedSchoolIdArray[i]));
            }
            for(int i = 0; i < actual.getDisplayedSchoolList().size(); i++) {
                assertThat(
                        actual.getDisplayedSchoolList().get(i).getSchoolId(), is(expectedDisplayedSchoolIdArray[i]));
            }
            assertThat(actual.getTotalForm(), is(expectedTotalForm));
            assertThat(actual.getTotalPage(), is(expectedTotalPage));
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseCurrentPageIsSecond() {
            AdminListSchoolActionForm adminListSchoolForm = new AdminListSchoolActionForm();
            List<SchoolExtendActionForm> schoolExtendFormList = new ArrayList<>();
            SchoolExtendActionForm firstForm = new SchoolExtendActionForm();
            firstForm.setSchoolId(21);
            firstForm.setSchoolReleasePropriety("1");
            firstForm.setSchoolEntryPropriety("1");
            firstForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-28 09:10:11.234"));
            firstForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-31 02:03:04.567"));
            schoolExtendFormList.add(firstForm);
            SchoolExtendActionForm secondForm = new SchoolExtendActionForm();
            secondForm.setSchoolId(20);
            secondForm.setSchoolReleasePropriety("0");
            secondForm.setSchoolEntryPropriety("0");
            secondForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-27 08:09:10.123"));
            secondForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-30 01:02:03.456"));
            schoolExtendFormList.add(secondForm);
            SchoolExtendActionForm thirdForm = new SchoolExtendActionForm();
            thirdForm.setSchoolId(19);
            thirdForm.setSchoolReleasePropriety("1");
            thirdForm.setSchoolEntryPropriety("1");
            thirdForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-26 07:08:09.123"));
            thirdForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-29 01:02:03.456"));
            schoolExtendFormList.add(thirdForm);
            SchoolExtendActionForm fourthForm = new SchoolExtendActionForm();
            fourthForm.setSchoolId(18);
            fourthForm.setSchoolReleasePropriety("0");
            fourthForm.setSchoolEntryPropriety("0");
            fourthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-25 06:07:08.912"));
            fourthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-28 09:10:11.234"));
            schoolExtendFormList.add(fourthForm);
            SchoolExtendActionForm fifthForm = new SchoolExtendActionForm();
            fifthForm.setSchoolId(17);
            fifthForm.setSchoolReleasePropriety("1");
            fifthForm.setSchoolEntryPropriety("1");
            fifthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-25 05:06:07.891"));
            fifthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-27 08:09:10.123"));
            schoolExtendFormList.add(fifthForm);
            SchoolExtendActionForm sixthForm = new SchoolExtendActionForm();
            sixthForm.setSchoolId(16);
            sixthForm.setSchoolReleasePropriety("0");
            sixthForm.setSchoolEntryPropriety("0");
            sixthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-24 06:07:08.912"));
            sixthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-26 07:08:09.123"));
            schoolExtendFormList.add(sixthForm);
            SchoolExtendActionForm seventhForm = new SchoolExtendActionForm();
            seventhForm.setSchoolId(15);
            seventhForm.setSchoolReleasePropriety("1");
            seventhForm.setSchoolEntryPropriety("1");
            seventhForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-24 05:06:07.891"));
            seventhForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-25 06:07:08.912"));
            schoolExtendFormList.add(seventhForm);
            SchoolExtendActionForm eighthForm = new SchoolExtendActionForm();
            eighthForm.setSchoolId(14);
            eighthForm.setSchoolReleasePropriety("0");
            eighthForm.setSchoolEntryPropriety("0");
            eighthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-23 04:05:06.789"));
            eighthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-24 05:06:07.891"));
            schoolExtendFormList.add(eighthForm);
            SchoolExtendActionForm ninthForm = new SchoolExtendActionForm();
            ninthForm.setSchoolId(13);
            ninthForm.setSchoolReleasePropriety("1");
            ninthForm.setSchoolEntryPropriety("1");
            ninthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-22 03:04:05.678"));
            ninthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-23 04:05:06.789"));
            schoolExtendFormList.add(ninthForm);
            SchoolExtendActionForm tenthForm = new SchoolExtendActionForm();
            tenthForm.setSchoolId(12);
            tenthForm.setSchoolReleasePropriety("0");
            tenthForm.setSchoolEntryPropriety("0");
            tenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-21 02:03:04.567"));
            tenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-22 03:04:05.678"));
            schoolExtendFormList.add(tenthForm);
            SchoolExtendActionForm eleventhForm = new SchoolExtendActionForm();
            eleventhForm.setSchoolId(11);
            eleventhForm.setSchoolReleasePropriety("1");
            eleventhForm.setSchoolEntryPropriety("1");
            eleventhForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-20 01:02:03.456"));
            eleventhForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-21 02:03:04.567"));
            schoolExtendFormList.add(eleventhForm);
            SchoolExtendActionForm twelfthForm = new SchoolExtendActionForm();
            twelfthForm.setSchoolId(10);
            twelfthForm.setSchoolReleasePropriety("0");
            twelfthForm.setSchoolEntryPropriety("0");
            twelfthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-19 01:02:03.456"));
            twelfthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-20 02:03:04.567"));
            schoolExtendFormList.add(twelfthForm);
            SchoolExtendActionForm thirteenthForm = new SchoolExtendActionForm();
            thirteenthForm.setSchoolId(9);
            thirteenthForm.setSchoolReleasePropriety("1");
            thirteenthForm.setSchoolEntryPropriety("1");
            thirteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-18 09:10:11.234"));
            thirteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-19 01:02:03.456"));
            schoolExtendFormList.add(thirteenthForm);
            SchoolExtendActionForm fourteenthForm = new SchoolExtendActionForm();
            fourteenthForm.setSchoolId(8);
            fourteenthForm.setSchoolReleasePropriety("0");
            fourteenthForm.setSchoolEntryPropriety("0");
            fourteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-17 08:09:10.123"));
            fourteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-18 09:10:11.234"));
            schoolExtendFormList.add(fourteenthForm);
            SchoolExtendActionForm fifteenthForm = new SchoolExtendActionForm();
            fifteenthForm.setSchoolId(7);
            fifteenthForm.setSchoolReleasePropriety("1");
            fifteenthForm.setSchoolEntryPropriety("1");
            fifteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-16 07:08:09.123"));
            fifteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-17 08:09:10.234"));
            schoolExtendFormList.add(fifteenthForm);
            SchoolExtendActionForm sixteenthForm = new SchoolExtendActionForm();
            sixteenthForm.setSchoolId(6);
            sixteenthForm.setSchoolReleasePropriety("0");
            sixteenthForm.setSchoolEntryPropriety("0");
            sixteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-15 06:07:08.912"));
            sixteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-16 07:08:09.123"));
            schoolExtendFormList.add(sixteenthForm);
            SchoolExtendActionForm seventeenthForm = new SchoolExtendActionForm();
            seventeenthForm.setSchoolId(5);
            seventeenthForm.setSchoolReleasePropriety("1");
            seventeenthForm.setSchoolEntryPropriety("1");
            seventeenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-14 05:06:07.891"));
            seventeenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-15 06:07:08.912"));
            schoolExtendFormList.add(seventeenthForm);
            SchoolExtendActionForm eighteenthForm = new SchoolExtendActionForm();
            eighteenthForm.setSchoolId(4);
            eighteenthForm.setSchoolReleasePropriety("0");
            eighteenthForm.setSchoolEntryPropriety("0");
            eighteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-13 04:05:06.789"));
            eighteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-14 05:06:07.891"));
            schoolExtendFormList.add(eighteenthForm);
            SchoolExtendActionForm nineteenthForm = new SchoolExtendActionForm();
            nineteenthForm.setSchoolId(3);
            nineteenthForm.setSchoolReleasePropriety("1");
            nineteenthForm.setSchoolEntryPropriety("1");
            nineteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-12 03:04:05.678"));
            nineteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-13 04:05:06.789"));
            schoolExtendFormList.add(nineteenthForm);
            SchoolExtendActionForm twentiethForm = new SchoolExtendActionForm();
            twentiethForm.setSchoolId(2);
            twentiethForm.setSchoolReleasePropriety("0");
            twentiethForm.setSchoolEntryPropriety("0");
            twentiethForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-11 02:03:04.567"));
            twentiethForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-12 03:04:05.678"));
            schoolExtendFormList.add(twentiethForm);
            SchoolExtendActionForm twentyFirstForm = new SchoolExtendActionForm();
            twentyFirstForm.setSchoolId(1);
            twentyFirstForm.setSchoolReleasePropriety("1");
            twentyFirstForm.setSchoolEntryPropriety("1");
            twentyFirstForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-10 03:04:05.678"));
            twentyFirstForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-11 02:03:04.567"));
            schoolExtendFormList.add(twentyFirstForm);
            adminListSchoolForm.setSchoolExtendFormList(schoolExtendFormList);
            setActionForm(adminListSchoolForm);
            addRequestParameter("currentPage", "2");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            int[] expectedSchoolIdArray =
                    {21, 20, 19, 18, 17, 16,
                    15, 14, 13, 12, 11,
                    10, 9, 8, 7, 6,
                    5, 4, 3, 2, 1};
            int[] expectedDisplayedSchoolIdArray = {1};
            int expectedCurrentPage = 2;

            AdminListSchoolActionForm actual = (AdminListSchoolActionForm) getActionForm();

            for(int i = 0; i < actual.getSchoolExtendFormList().size(); i++) {
                assertThat(actual.getSchoolExtendFormList().get(i).getSchoolId(), is(expectedSchoolIdArray[i]));
            }
            for(int i = 0; i < actual.getDisplayedSchoolList().size(); i++) {
                assertThat(
                        actual.getDisplayedSchoolList().get(i).getSchoolId(), is(expectedDisplayedSchoolIdArray[i]));
            }
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseSortByAccendingUpdatedAt() {
            AdminListSchoolActionForm adminListSchoolForm = new AdminListSchoolActionForm();
            List<SchoolExtendActionForm> schoolExtendFormList = new ArrayList<>();
            SchoolExtendActionForm firstForm = new SchoolExtendActionForm();
            firstForm.setSchoolId(21);
            firstForm.setSchoolReleasePropriety("1");
            firstForm.setSchoolEntryPropriety("1");
            firstForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-28 09:10:11.234"));
            firstForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-31 02:03:04.567"));
            schoolExtendFormList.add(firstForm);
            SchoolExtendActionForm secondForm = new SchoolExtendActionForm();
            secondForm.setSchoolId(20);
            secondForm.setSchoolReleasePropriety("0");
            secondForm.setSchoolEntryPropriety("0");
            secondForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-27 08:09:10.123"));
            secondForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-30 01:02:03.456"));
            schoolExtendFormList.add(secondForm);
            SchoolExtendActionForm thirdForm = new SchoolExtendActionForm();
            thirdForm.setSchoolId(19);
            thirdForm.setSchoolReleasePropriety("1");
            thirdForm.setSchoolEntryPropriety("1");
            thirdForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-26 07:08:09.123"));
            thirdForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-29 01:02:03.456"));
            schoolExtendFormList.add(thirdForm);
            SchoolExtendActionForm fourthForm = new SchoolExtendActionForm();
            fourthForm.setSchoolId(18);
            fourthForm.setSchoolReleasePropriety("0");
            fourthForm.setSchoolEntryPropriety("0");
            fourthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-25 06:07:08.912"));
            fourthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-28 09:10:11.234"));
            schoolExtendFormList.add(fourthForm);
            SchoolExtendActionForm fifthForm = new SchoolExtendActionForm();
            fifthForm.setSchoolId(17);
            fifthForm.setSchoolReleasePropriety("1");
            fifthForm.setSchoolEntryPropriety("1");
            fifthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-25 05:06:07.891"));
            fifthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-27 08:09:10.123"));
            schoolExtendFormList.add(fifthForm);
            SchoolExtendActionForm sixthForm = new SchoolExtendActionForm();
            sixthForm.setSchoolId(16);
            sixthForm.setSchoolReleasePropriety("0");
            sixthForm.setSchoolEntryPropriety("0");
            sixthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-24 06:07:08.912"));
            sixthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-26 07:08:09.123"));
            schoolExtendFormList.add(sixthForm);
            SchoolExtendActionForm seventhForm = new SchoolExtendActionForm();
            seventhForm.setSchoolId(15);
            seventhForm.setSchoolReleasePropriety("1");
            seventhForm.setSchoolEntryPropriety("1");
            seventhForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-24 05:06:07.891"));
            seventhForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-25 06:07:08.912"));
            schoolExtendFormList.add(seventhForm);
            SchoolExtendActionForm eighthForm = new SchoolExtendActionForm();
            eighthForm.setSchoolId(14);
            eighthForm.setSchoolReleasePropriety("0");
            eighthForm.setSchoolEntryPropriety("0");
            eighthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-23 04:05:06.789"));
            eighthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-24 05:06:07.891"));
            schoolExtendFormList.add(eighthForm);
            SchoolExtendActionForm ninthForm = new SchoolExtendActionForm();
            ninthForm.setSchoolId(13);
            ninthForm.setSchoolReleasePropriety("1");
            ninthForm.setSchoolEntryPropriety("1");
            ninthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-22 03:04:05.678"));
            ninthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-23 04:05:06.789"));
            schoolExtendFormList.add(ninthForm);
            SchoolExtendActionForm tenthForm = new SchoolExtendActionForm();
            tenthForm.setSchoolId(12);
            tenthForm.setSchoolReleasePropriety("0");
            tenthForm.setSchoolEntryPropriety("0");
            tenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-21 02:03:04.567"));
            tenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-22 03:04:05.678"));
            schoolExtendFormList.add(tenthForm);
            SchoolExtendActionForm eleventhForm = new SchoolExtendActionForm();
            eleventhForm.setSchoolId(11);
            eleventhForm.setSchoolReleasePropriety("1");
            eleventhForm.setSchoolEntryPropriety("1");
            eleventhForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-20 01:02:03.456"));
            eleventhForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-21 02:03:04.567"));
            schoolExtendFormList.add(eleventhForm);
            SchoolExtendActionForm twelfthForm = new SchoolExtendActionForm();
            twelfthForm.setSchoolId(10);
            twelfthForm.setSchoolReleasePropriety("0");
            twelfthForm.setSchoolEntryPropriety("0");
            twelfthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-19 01:02:03.456"));
            twelfthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-20 02:03:04.567"));
            schoolExtendFormList.add(twelfthForm);
            SchoolExtendActionForm thirteenthForm = new SchoolExtendActionForm();
            thirteenthForm.setSchoolId(9);
            thirteenthForm.setSchoolReleasePropriety("1");
            thirteenthForm.setSchoolEntryPropriety("1");
            thirteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-18 09:10:11.234"));
            thirteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-19 01:02:03.456"));
            schoolExtendFormList.add(thirteenthForm);
            SchoolExtendActionForm fourteenthForm = new SchoolExtendActionForm();
            fourteenthForm.setSchoolId(8);
            fourteenthForm.setSchoolReleasePropriety("0");
            fourteenthForm.setSchoolEntryPropriety("0");
            fourteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-17 08:09:10.123"));
            fourteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-18 09:10:11.234"));
            schoolExtendFormList.add(fourteenthForm);
            SchoolExtendActionForm fifteenthForm = new SchoolExtendActionForm();
            fifteenthForm.setSchoolId(7);
            fifteenthForm.setSchoolReleasePropriety("1");
            fifteenthForm.setSchoolEntryPropriety("1");
            fifteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-16 07:08:09.123"));
            fifteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-17 08:09:10.234"));
            schoolExtendFormList.add(fifteenthForm);
            SchoolExtendActionForm sixteenthForm = new SchoolExtendActionForm();
            sixteenthForm.setSchoolId(6);
            sixteenthForm.setSchoolReleasePropriety("0");
            sixteenthForm.setSchoolEntryPropriety("0");
            sixteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-15 06:07:08.912"));
            sixteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-16 07:08:09.123"));
            schoolExtendFormList.add(sixteenthForm);
            SchoolExtendActionForm seventeenthForm = new SchoolExtendActionForm();
            seventeenthForm.setSchoolId(5);
            seventeenthForm.setSchoolReleasePropriety("1");
            seventeenthForm.setSchoolEntryPropriety("1");
            seventeenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-14 05:06:07.891"));
            seventeenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-15 06:07:08.912"));
            schoolExtendFormList.add(seventeenthForm);
            SchoolExtendActionForm eighteenthForm = new SchoolExtendActionForm();
            eighteenthForm.setSchoolId(4);
            eighteenthForm.setSchoolReleasePropriety("0");
            eighteenthForm.setSchoolEntryPropriety("0");
            eighteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-13 04:05:06.789"));
            eighteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-14 05:06:07.891"));
            schoolExtendFormList.add(eighteenthForm);
            SchoolExtendActionForm nineteenthForm = new SchoolExtendActionForm();
            nineteenthForm.setSchoolId(3);
            nineteenthForm.setSchoolReleasePropriety("1");
            nineteenthForm.setSchoolEntryPropriety("1");
            nineteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-12 03:04:05.678"));
            nineteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-13 04:05:06.789"));
            schoolExtendFormList.add(nineteenthForm);
            SchoolExtendActionForm twentiethForm = new SchoolExtendActionForm();
            twentiethForm.setSchoolId(2);
            twentiethForm.setSchoolReleasePropriety("0");
            twentiethForm.setSchoolEntryPropriety("0");
            twentiethForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-11 02:03:04.567"));
            twentiethForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-12 03:04:05.678"));
            schoolExtendFormList.add(twentiethForm);
            SchoolExtendActionForm twentyFirstForm = new SchoolExtendActionForm();
            twentyFirstForm.setSchoolId(1);
            twentyFirstForm.setSchoolReleasePropriety("1");
            twentyFirstForm.setSchoolEntryPropriety("1");
            twentyFirstForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-10 03:04:05.678"));
            twentyFirstForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-11 02:03:04.567"));
            schoolExtendFormList.add(twentyFirstForm);
            adminListSchoolForm.setSchoolExtendFormList(schoolExtendFormList);
            adminListSchoolForm.setSortFlag(true);
            adminListSchoolForm.setSortKindForSchool("byAccendingUpdatedAt");
            setActionForm(adminListSchoolForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            int[] expectedSchoolIdArray =
                    {1, 2, 3, 4, 5,
                    6, 7, 8, 9, 10,
                    11, 12, 13, 14, 15,
                    16, 17, 18, 19, 20, 21};
            int[] expectedDisplayedSchoolIdArray =
                    {1, 2, 3, 4, 5,
                    6, 7, 8, 9, 10,
                    11, 12, 13, 14, 15,
                    16, 17, 18, 19, 20};
            int expectedCurrentPage = 1;

            AdminListSchoolActionForm actual = (AdminListSchoolActionForm) getActionForm();

            for(int i = 0; i < actual.getSchoolExtendFormList().size(); i++) {
                assertThat(actual.getSchoolExtendFormList().get(i).getSchoolId(), is(expectedSchoolIdArray[i]));
            }
            for(int i = 0; i < actual.getDisplayedSchoolList().size(); i++) {
                assertThat(
                        actual.getDisplayedSchoolList().get(i).getSchoolId(), is(expectedDisplayedSchoolIdArray[i]));
            }
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
            assertThat(actual.isSortFlag(), is(false));
        }

        @Test
        public void testExecuteInCaseLogout() {
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/listSchool.do");
        }

    }

}
