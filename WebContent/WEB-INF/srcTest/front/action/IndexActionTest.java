package front.action;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import actionform.SchoolExtendActionForm;
import front.actionform.FrontIndexActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class IndexActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.IndexActionTest.xml") {
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

        private static final String ACTION_PATH = "/index.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() {
            actionPerform();

            verifyForward("success");

            int[] expectedSchoolIdArray = {10, 9, 7, 6, 5, 4, 3, 2, 1};
            int[] expectedDisplayedSchoolIdArray = {10, 9, 7, 6, 5, 4, 3, 2};
            SchoolExtendActionForm expectedForm1 = new SchoolExtendActionForm();
            expectedForm1.setSchoolId(10);
            expectedForm1.setRegistrantAccountId(24);
            expectedForm1.setRegistrantLastName("斉藤");
            expectedForm1.setRegistrantFirstName("四郎");
            expectedForm1.setRegistrantLastNameKana("サイトウ");
            expectedForm1.setRegistrantFirstNameKana("シロウ");
            expectedForm1.setProfileImageFileName("profile_image4.png");
            expectedForm1.setSelfIntroduction("自己紹介4");
            expectedForm1.setRegistrantAdminId(0);
            expectedForm1.setSchoolName("テストスクール10");
            expectedForm1.setSchoolCategoryId(44);
            expectedForm1.setSchoolCategoryName("茶道");
            expectedForm1.setSchoolSummary("スクール概要10");
            expectedForm1.setSchoolDescription("スクール詳細10");
            expectedForm1.setSchoolZipCode1("126");
            expectedForm1.setSchoolZipCode2("9012");
            expectedForm1.setSchoolPrefectureId(16);
            expectedForm1.setSchoolPrefectureName("鹿児島県");
            expectedForm1.setSchoolCity("鹿児島市");
            expectedForm1.setSchoolAddress1("鹿児島区");
            expectedForm1.setSchoolAddress2(null);
            expectedForm1.setSchoolFee(new BigDecimal("5432"));
            expectedForm1.setSupplementaryFee(null);
            expectedForm1.setSchoolUrl(null);
            expectedForm1.setSchoolReleasePropriety("1");
            expectedForm1.setSchoolEntryPropriety("1");
            expectedForm1.setSummaryImageFileName(null);
            expectedForm1.setDetailImage1FileName(null);
            expectedForm1.setDetailImage2FileName(null);
            expectedForm1.setDetailImage3FileName(null);
            expectedForm1.setDetailImage4FileName(null);
            expectedForm1.setDetailImage5FileName(null);
            expectedForm1.setDetailImage6FileName(null);
            expectedForm1.setDetailImage7FileName(null);
            expectedForm1.setDetailImage8FileName(null);
            expectedForm1.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-20 21:22:23.567"));
            expectedForm1.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-21 22:23:24.678"));
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
            expectedForm2.setSchoolEntryPropriety("1");
            expectedForm2.setSummaryImageFileName("summary_image11.png");
            expectedForm2.setDetailImage1FileName("detail_image11.png");
            expectedForm2.setDetailImage2FileName("detail_image12.png");
            expectedForm2.setDetailImage3FileName("detail_image13.png");
            expectedForm2.setDetailImage4FileName("detail_image14.png");
            expectedForm2.setDetailImage5FileName("detail_image15.png");
            expectedForm2.setDetailImage6FileName("detail_image16.png");
            expectedForm2.setDetailImage7FileName("detail_image17.png");
            expectedForm2.setDetailImage8FileName("detail_image18.png");
            expectedForm2.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            expectedForm2.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            expectedForm1.setAllEntryNum(0);
            String expectedSummaryImageFilePath1 = null;
            String expectedSummaryImageFilePath2 = "/img/summary_image21.png";

            FrontIndexActionForm actual = (FrontIndexActionForm) getActionForm();

            for(int i = 0; i < actual.getSchoolExtendFormList().size(); i++) {
                assertThat(actual.getSchoolExtendFormList().get(i).getSchoolId(), is(expectedSchoolIdArray[i]));
            }
            assertThat(actual.getSchoolExtendFormList().get(0), is(samePropertyValueAs(expectedForm1)));
            assertThat(actual.getSchoolExtendFormList().get(8), is(samePropertyValueAs(expectedForm2)));
            for(int i = 0; i < actual.getDisplayedSchoolList().size(); i++) {
                assertThat(actual.getDisplayedSchoolList().get(i).getSchoolId(), is(expectedDisplayedSchoolIdArray[i]));
            }
            assertThat(
                    actual.getDisplayedSchoolList().get(0).getSummaryImageFilePath(),
                    is(expectedSummaryImageFilePath1));
            assertThat(
                    actual.getDisplayedSchoolList().get(7).getSummaryImageFilePath(),
                    is(expectedSummaryImageFilePath2));
        }

    }

}
