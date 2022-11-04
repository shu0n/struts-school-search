package front.action.school;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import actionform.SchoolExtendActionForm;
import front.actionform.school.FrontListRegisteredSchoolActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class ListRegisteredSchoolActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.ListRegisteredSchoolActionTest.xml") {
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

        private static final String ACTION_PATH = "/listRegisteredSchool.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccessInCaseFirstAccess() {
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "22");
            actionPerform();

            verifyForward("success");

            int[] expectedSchoolIdArray = {5, 3, 1};
            SchoolExtendActionForm expectedForm1 = new SchoolExtendActionForm();
            expectedForm1.setSchoolId(5);
            expectedForm1.setRegistrantAccountId(22);
            expectedForm1.setRegistrantLastName("佐藤");
            expectedForm1.setRegistrantFirstName("次郎");
            expectedForm1.setRegistrantLastNameKana("サトウ");
            expectedForm1.setRegistrantFirstNameKana("ジロウ");
            expectedForm1.setProfileImageFileName("profile_image2.png");
            expectedForm1.setSelfIntroduction("自己紹介2");
            expectedForm1.setRegistrantAdminId(0);
            expectedForm1.setSchoolName("試験スクール5");
            expectedForm1.setSchoolCategoryId(42);
            expectedForm1.setSchoolCategoryName("運動");
            expectedForm1.setSchoolSummary("スクール概要5");
            expectedForm1.setSchoolDescription("スクール詳細5");
            expectedForm1.setSchoolZipCode1("012");
            expectedForm1.setSchoolZipCode2("3456");
            expectedForm1.setSchoolPrefectureId(11);
            expectedForm1.setSchoolPrefectureName("北海道");
            expectedForm1.setSchoolCity("北海道市");
            expectedForm1.setSchoolAddress1("北海道区");
            expectedForm1.setSchoolAddress2(null);
            expectedForm1.setSchoolFee(new BigDecimal("1098"));
            expectedForm1.setSupplementaryFee(null);
            expectedForm1.setSchoolUrl(null);
            expectedForm1.setSchoolReleasePropriety("0");
            expectedForm1.setSchoolReleaseProprietyName("不可");
            expectedForm1.setSchoolEntryPropriety("0");
            expectedForm1.setSchoolEntryProprietyName("不可");
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
            expectedForm1.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-10 11:12:13.567"));
            expectedForm1.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-11 12:13:14.678"));
            expectedForm1.setStrSchoolRegisteredAt("2022/01/10 11:12:13");
            expectedForm1.setStrSchoolUpdatedAt("2022/01/11 12:13:14");
            expectedForm1.setAllEntryNum(3);
            SchoolExtendActionForm expectedForm2 = new SchoolExtendActionForm();
            expectedForm2.setSchoolId(1);
            expectedForm2.setRegistrantAccountId(22);
            expectedForm2.setRegistrantLastName("佐藤");
            expectedForm2.setRegistrantFirstName("次郎");
            expectedForm2.setRegistrantLastNameKana("サトウ");
            expectedForm2.setRegistrantFirstNameKana("ジロウ");
            expectedForm2.setProfileImageFileName("profile_image2.png");
            expectedForm2.setSelfIntroduction("自己紹介2");
            expectedForm2.setRegistrantAdminId(0);
            expectedForm2.setSchoolName("テストスクール1");
            expectedForm2.setSchoolCategoryId(43);
            expectedForm2.setSchoolCategoryName("日本文化");
            expectedForm2.setSchoolSummary("スクール概要1");
            expectedForm2.setSchoolDescription("スクール詳細1");
            expectedForm2.setSchoolZipCode1("123");
            expectedForm2.setSchoolZipCode2("4567");
            expectedForm2.setSchoolPrefectureId(14);
            expectedForm2.setSchoolPrefectureName("大阪府");
            expectedForm2.setSchoolCity("大阪市");
            expectedForm2.setSchoolAddress1("大阪区");
            expectedForm2.setSchoolAddress2("大阪町");
            expectedForm2.setSchoolFee(new BigDecimal("5432"));
            expectedForm2.setSupplementaryFee("費用補足1");
            expectedForm2.setSchoolUrl("http://test1.com");
            expectedForm2.setSchoolReleasePropriety("1");
            expectedForm2.setSchoolReleaseProprietyName("可");
            expectedForm2.setSchoolEntryPropriety("1");
            expectedForm2.setSchoolEntryProprietyName("可");
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
            int expectedTotalForm = 3;
            int expectedTotalPage = 1;
            int expectedCurrentPage = 1;

            FrontListRegisteredSchoolActionForm actual = (FrontListRegisteredSchoolActionForm) getActionForm();

            for(int i = 0; i < actual.getSchoolExtendFormList().size(); i++) {
                assertThat(actual.getSchoolExtendFormList().get(i).getSchoolId(), is(expectedSchoolIdArray[i]));
            }
            assertThat(actual.getSchoolExtendFormList().get(0), is(samePropertyValueAs(expectedForm1)));
            assertThat(actual.getSchoolExtendFormList().get(2), is(samePropertyValueAs(expectedForm2)));
            assertThat(actual.getTotalForm(), is(expectedTotalForm));
            assertThat(actual.getTotalPage(), is(expectedTotalPage));
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseCurrentPageIsSecond() {
            FrontListRegisteredSchoolActionForm adminListSchoolForm = new FrontListRegisteredSchoolActionForm();
            List<SchoolExtendActionForm> schoolExtendFormList = new ArrayList<>();
            SchoolExtendActionForm firstForm = new SchoolExtendActionForm();
            firstForm.setSchoolId(21);
            firstForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-28 09:10:11.234"));
            firstForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-31 02:03:04.567"));
            schoolExtendFormList.add(firstForm);
            SchoolExtendActionForm secondForm = new SchoolExtendActionForm();
            secondForm.setSchoolId(20);
            secondForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-27 08:09:10.123"));
            secondForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-30 01:02:03.456"));
            schoolExtendFormList.add(secondForm);
            SchoolExtendActionForm thirdForm = new SchoolExtendActionForm();
            thirdForm.setSchoolId(19);
            thirdForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-26 07:08:09.123"));
            thirdForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-29 01:02:03.456"));
            schoolExtendFormList.add(thirdForm);
            SchoolExtendActionForm fourthForm = new SchoolExtendActionForm();
            fourthForm.setSchoolId(18);
            fourthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-25 06:07:08.912"));
            fourthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-28 09:10:11.234"));
            schoolExtendFormList.add(fourthForm);
            SchoolExtendActionForm fifthForm = new SchoolExtendActionForm();
            fifthForm.setSchoolId(17);
            fifthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-25 05:06:07.891"));
            fifthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-27 08:09:10.123"));
            schoolExtendFormList.add(fifthForm);
            SchoolExtendActionForm sixthForm = new SchoolExtendActionForm();
            sixthForm.setSchoolId(16);
            sixthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-24 06:07:08.912"));
            sixthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-26 07:08:09.123"));
            schoolExtendFormList.add(sixthForm);
            SchoolExtendActionForm seventhForm = new SchoolExtendActionForm();
            seventhForm.setSchoolId(15);
            seventhForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-24 05:06:07.891"));
            seventhForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-25 06:07:08.912"));
            schoolExtendFormList.add(seventhForm);
            SchoolExtendActionForm eighthForm = new SchoolExtendActionForm();
            eighthForm.setSchoolId(14);
            eighthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-23 04:05:06.789"));
            eighthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-24 05:06:07.891"));
            schoolExtendFormList.add(eighthForm);
            SchoolExtendActionForm ninthForm = new SchoolExtendActionForm();
            ninthForm.setSchoolId(13);
            ninthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-22 03:04:05.678"));
            ninthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-23 04:05:06.789"));
            schoolExtendFormList.add(ninthForm);
            SchoolExtendActionForm tenthForm = new SchoolExtendActionForm();
            tenthForm.setSchoolId(12);
            tenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-21 02:03:04.567"));
            tenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-22 03:04:05.678"));
            schoolExtendFormList.add(tenthForm);
            SchoolExtendActionForm eleventhForm = new SchoolExtendActionForm();
            eleventhForm.setSchoolId(11);
            eleventhForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-20 01:02:03.456"));
            eleventhForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-21 02:03:04.567"));
            schoolExtendFormList.add(eleventhForm);
            SchoolExtendActionForm twelfthForm = new SchoolExtendActionForm();
            twelfthForm.setSchoolId(10);
            twelfthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-19 01:02:03.456"));
            twelfthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-20 02:03:04.567"));
            schoolExtendFormList.add(twelfthForm);
            SchoolExtendActionForm thirteenthForm = new SchoolExtendActionForm();
            thirteenthForm.setSchoolId(9);
            thirteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-18 09:10:11.234"));
            thirteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-19 01:02:03.456"));
            schoolExtendFormList.add(thirteenthForm);
            SchoolExtendActionForm fourteenthForm = new SchoolExtendActionForm();
            fourteenthForm.setSchoolId(8);
            fourteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-17 08:09:10.123"));
            fourteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-18 09:10:11.234"));
            schoolExtendFormList.add(fourteenthForm);
            SchoolExtendActionForm fifteenthForm = new SchoolExtendActionForm();
            fifteenthForm.setSchoolId(7);
            fifteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-16 07:08:09.123"));
            fifteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-17 08:09:10.234"));
            schoolExtendFormList.add(fifteenthForm);
            SchoolExtendActionForm sixteenthForm = new SchoolExtendActionForm();
            sixteenthForm.setSchoolId(6);
            sixteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-15 06:07:08.912"));
            sixteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-16 07:08:09.123"));
            schoolExtendFormList.add(sixteenthForm);
            SchoolExtendActionForm seventeenthForm = new SchoolExtendActionForm();
            seventeenthForm.setSchoolId(5);
            seventeenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-14 05:06:07.891"));
            seventeenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-15 06:07:08.912"));
            schoolExtendFormList.add(seventeenthForm);
            SchoolExtendActionForm eighteenthForm = new SchoolExtendActionForm();
            eighteenthForm.setSchoolId(4);
            eighteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-13 04:05:06.789"));
            eighteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-14 05:06:07.891"));
            schoolExtendFormList.add(eighteenthForm);
            SchoolExtendActionForm nineteenthForm = new SchoolExtendActionForm();
            nineteenthForm.setSchoolId(3);
            nineteenthForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-12 03:04:05.678"));
            nineteenthForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-13 04:05:06.789"));
            schoolExtendFormList.add(nineteenthForm);
            SchoolExtendActionForm twentiethForm = new SchoolExtendActionForm();
            twentiethForm.setSchoolId(2);
            twentiethForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-11 02:03:04.567"));
            twentiethForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-12 03:04:05.678"));
            schoolExtendFormList.add(twentiethForm);
            SchoolExtendActionForm twentyFirstForm = new SchoolExtendActionForm();
            twentyFirstForm.setSchoolId(1);
            twentyFirstForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-02-10 03:04:05.678"));
            twentyFirstForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-11 02:03:04.567"));
            schoolExtendFormList.add(twentyFirstForm);
            adminListSchoolForm.setSchoolExtendFormList(schoolExtendFormList);
            setActionForm(adminListSchoolForm);
            addRequestParameter("currentPage", "2");
            getSession().setAttribute("logined", "true");
            actionPerform();

            verifyForward("success");

            int[] expectedSchoolIdArray =
                    {21, 20, 19, 18, 17, 16,
                    15, 14, 13, 12, 11,
                    10, 9, 8, 7, 6,
                    5, 4, 3, 2, 1};
            int[] expectedDisplayedSchoolIdArray = {1};
            int expectedCurrentPage = 2;

            FrontListRegisteredSchoolActionForm actual = (FrontListRegisteredSchoolActionForm) getActionForm();

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
        public void testExecuteInCaseLogout() {
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/listRegisteredSchool.do");
        }

    }

}
