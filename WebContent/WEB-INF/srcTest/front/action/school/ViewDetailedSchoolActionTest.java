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

import front.actionform.school.FrontViewDetailedSchoolActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class ViewDetailedSchoolActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.ViewDetailedSchoolActionTest.xml") {
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

        private static final String ACTION_PATH = "/viewDetailedSchool.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccessInCaseNonRegistrant() {
            addRequestParameter("schoolId", "1");
            actionPerform();

            verifyForward("success");

            FrontViewDetailedSchoolActionForm expected = new FrontViewDetailedSchoolActionForm();
            expected.setSchoolId(1);
            expected.setRegistrantAccountId(22);
            expected.setRegistrantLastName("佐藤");
            expected.setRegistrantFirstName("次郎");
            expected.setRegistrantLastNameKana("サトウ");
            expected.setRegistrantFirstNameKana("ジロウ");
            expected.setRegistrantAdminId(0);
            expected.setSchoolName("テストスクール1");
            expected.setSchoolCategoryId(43);
            expected.setSchoolCategoryName("日本文化");
            expected.setSchoolSummary("スクール概要1");
            expected.setSchoolDescription("スクール詳細1");
            expected.setSchoolZipCode1("123");
            expected.setSchoolZipCode2("4567");
            expected.setSchoolPrefectureId(14);
            expected.setSchoolPrefectureName("大阪府");
            expected.setSchoolCity("大阪市");
            expected.setSchoolAddress1("大阪区");
            expected.setSchoolAddress2("大阪町");
            expected.setSchoolFee(new BigDecimal(5432));
            expected.setStrSchoolFee("5432");
            expected.setSupplementaryFee("費用補足1");
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
            List<String> detailImageFilePathList = new ArrayList<>();
            detailImageFilePathList.add("/img/detail_image11.png");
            detailImageFilePathList.add("/img/detail_image12.png");
            detailImageFilePathList.add("/img/detail_image13.png");
            detailImageFilePathList.add("/img/detail_image14.png");
            detailImageFilePathList.add("/img/detail_image15.png");
            detailImageFilePathList.add("/img/detail_image16.png");
            detailImageFilePathList.add("/img/detail_image17.png");
            detailImageFilePathList.add("/img/detail_image18.png");
            expected.setDetailImageFilePathList(detailImageFilePathList);
            expected.setEntriedFlag(false);
            expected.setFavoriteFlag(false);

            FrontViewDetailedSchoolActionForm actual
                    = (FrontViewDetailedSchoolActionForm) getSession().getAttribute("FrontViewDetailedSchoolActionForm");

            assertThat(actual, is(samePropertyValueAs(expected)));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseEntriedAndFavorited() {
            addRequestParameter("schoolId", "1");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "23");
            actionPerform();

            verifyForward("success");

            FrontViewDetailedSchoolActionForm actual
                    = (FrontViewDetailedSchoolActionForm) getSession().getAttribute("FrontViewDetailedSchoolActionForm");

            assertThat(actual.isEntriedFlag(), is(true));
            assertThat(actual.isFavoriteFlag(), is(true));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseSchoolIsUnreleaseAndRegistrantAccess() {
            addRequestParameter("schoolId", "6");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "23");
            actionPerform();

            verifyForward("success");
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoParameterExist() {
            actionPerform();

            verifyForward("unexistence");

            FrontViewDetailedSchoolActionForm actual
                    = (FrontViewDetailedSchoolActionForm) getSession().getAttribute("FrontViewDetailedSchoolActionForm");

            assertThat(actual, is(nullValue()));
        }

        @Test
        public void testExecuteToForwardUnreleaseInCaseSchoolIsUnreleaseAndLogout() {
            addRequestParameter("schoolId", "6");
            actionPerform();

            verifyForward("unrelease");

            FrontViewDetailedSchoolActionForm actual
                    = (FrontViewDetailedSchoolActionForm) getSession().getAttribute("FrontViewDetailedSchoolActionForm");

            assertThat(actual, is(nullValue()));
        }

        @Test
        public void testExecuteToForwardUnreleaseInCaseSchoolIsUnreleaseAndNonRegistrant() {
            addRequestParameter("schoolId", "6");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "22");
            actionPerform();

            verifyForward("unrelease");

            FrontViewDetailedSchoolActionForm actual
                    = (FrontViewDetailedSchoolActionForm) getSession().getAttribute("FrontViewDetailedSchoolActionForm");

            assertThat(actual, is(nullValue()));
        }

    }

}
