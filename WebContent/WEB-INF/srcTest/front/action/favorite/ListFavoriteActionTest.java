package front.action.favorite;

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

import actionform.FavoriteExtendActionForm;
import front.actionform.favorite.FrontListFavoriteActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class ListFavoriteActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.ListFavoriteActionTest.xml") {
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

        private static final String ACTION_PATH = "/listFavorite.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccessInCaseFirstAccess() {
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "21");
            actionPerform();

            verifyForward("success");

            int[] expectedFavoriteIdArray = {216, 213, 212, 211};
            FavoriteExtendActionForm expectedForm1 = new FavoriteExtendActionForm();
            expectedForm1.setFavoriteId(216);
            expectedForm1.setAccountId(21);
            expectedForm1.setSchoolId(6);
            expectedForm1.setSchoolName("テストスクール6");
            expectedForm1.setSchoolCategoryId(43);
            expectedForm1.setSchoolCategoryName("日本文化");
            expectedForm1.setRegistrantAccountId(0);
            expectedForm1.setRegistrantLastName(null);
            expectedForm1.setRegistrantFirstName(null);
            expectedForm1.setSchoolSummary("スクール概要6");
            expectedForm1.setSchoolFee(new BigDecimal("9876"));
            expectedForm1.setSchoolPrefectureId(12);
            expectedForm1.setSchoolPrefectureName("東京都");
            expectedForm1.setSummaryImageFileName(null);
            expectedForm1.setSummaryImageFilePath(null);
            expectedForm1.setRegistrantAdminId(61);
            expectedForm1.setFavoriteAddedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
            expectedForm1.setStrFavoriteAddedAt("2022/01/05 06:07:08");
            FavoriteExtendActionForm expectedForm2 = new FavoriteExtendActionForm();
            expectedForm2.setFavoriteId(211);
            expectedForm2.setAccountId(21);
            expectedForm2.setSchoolId(1);
            expectedForm2.setSchoolName("テストスクール1");
            expectedForm2.setSchoolCategoryId(42);
            expectedForm2.setSchoolCategoryName("運動");
            expectedForm2.setRegistrantAccountId(22);
            expectedForm2.setRegistrantLastName("佐藤");
            expectedForm2.setRegistrantFirstName("次郎");
            expectedForm2.setSchoolSummary("スクール概要1");
            expectedForm2.setSchoolFee(new BigDecimal("5432"));
            expectedForm2.setSchoolPrefectureId(14);
            expectedForm2.setSchoolPrefectureName("大阪府");
            expectedForm2.setSummaryImageFileName("summary_image11.png");
            expectedForm2.setSummaryImageFilePath("/img/summary_image11.png");
            expectedForm2.setRegistrantAdminId(0);
            expectedForm2.setFavoriteAddedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            expectedForm2.setStrFavoriteAddedAt("2022/01/02 03:04:05");
            int expectedTotalForm = 4;
            int expectedTotalPage = 1;
            int expectedCurrentPage = 1;

            FrontListFavoriteActionForm actual = (FrontListFavoriteActionForm) getActionForm();

            for(int i = 0; i < actual.getFavoriteExtendFormList().size(); i++) {
                assertThat(actual.getFavoriteExtendFormList().get(i).getFavoriteId(), is(expectedFavoriteIdArray[i]));
            }
            assertThat(actual.getFavoriteExtendFormList().get(0), is(samePropertyValueAs(expectedForm1)));
            assertThat(actual.getFavoriteExtendFormList().get(3), is(samePropertyValueAs(expectedForm2)));
            assertThat(actual.getTotalForm(), is(expectedTotalForm));
            assertThat(actual.getTotalPage(), is(expectedTotalPage));
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseCurrentPageIsSecond() {
            FrontListFavoriteActionForm frontListFavoriteForm = new FrontListFavoriteActionForm();
            List<FavoriteExtendActionForm> favoriteExtendFormList = new ArrayList<>();
            FavoriteExtendActionForm firstForm = new FavoriteExtendActionForm();
            firstForm.setFavoriteId(21);
            firstForm.setFavoriteAddedAt(Timestamp.valueOf("2022-02-28 09:10:11.234"));
            favoriteExtendFormList.add(firstForm);
            FavoriteExtendActionForm secondForm = new FavoriteExtendActionForm();
            secondForm.setFavoriteId(20);
            secondForm.setFavoriteAddedAt(Timestamp.valueOf("2022-02-27 08:09:10.123"));
            favoriteExtendFormList.add(secondForm);
            FavoriteExtendActionForm thirdForm = new FavoriteExtendActionForm();
            thirdForm.setFavoriteId(19);
            thirdForm.setFavoriteAddedAt(Timestamp.valueOf("2022-02-26 07:08:09.123"));
            favoriteExtendFormList.add(thirdForm);
            FavoriteExtendActionForm fourthForm = new FavoriteExtendActionForm();
            fourthForm.setFavoriteId(18);
            fourthForm.setFavoriteAddedAt(Timestamp.valueOf("2022-02-25 06:07:08.912"));
            favoriteExtendFormList.add(fourthForm);
            FavoriteExtendActionForm fifthForm = new FavoriteExtendActionForm();
            fifthForm.setFavoriteId(17);
            fifthForm.setFavoriteAddedAt(Timestamp.valueOf("2022-02-25 05:06:07.891"));
            favoriteExtendFormList.add(fifthForm);
            FavoriteExtendActionForm sixthForm = new FavoriteExtendActionForm();
            sixthForm.setFavoriteId(16);
            sixthForm.setFavoriteAddedAt(Timestamp.valueOf("2022-02-24 06:07:08.912"));
            favoriteExtendFormList.add(sixthForm);
            FavoriteExtendActionForm seventhForm = new FavoriteExtendActionForm();
            seventhForm.setFavoriteId(15);
            seventhForm.setFavoriteAddedAt(Timestamp.valueOf("2022-02-24 05:06:07.891"));
            favoriteExtendFormList.add(seventhForm);
            FavoriteExtendActionForm eighthForm = new FavoriteExtendActionForm();
            eighthForm.setFavoriteId(14);
            eighthForm.setFavoriteAddedAt(Timestamp.valueOf("2022-02-23 04:05:06.789"));
            favoriteExtendFormList.add(eighthForm);
            FavoriteExtendActionForm ninthForm = new FavoriteExtendActionForm();
            ninthForm.setFavoriteId(13);
            ninthForm.setFavoriteAddedAt(Timestamp.valueOf("2022-02-22 03:04:05.678"));
            favoriteExtendFormList.add(ninthForm);
            setActionForm(frontListFavoriteForm);
            addRequestParameter("currentPage", "2");
            getSession().setAttribute("logined", "true");
            getSession().setAttribute("accountId", "1");
            actionPerform();

            verifyForward("success");

            int[] expectedFavoriteIdArray = {21, 20, 19, 18, 17, 16, 15, 14, 13};
            int[] expectedDisplayedFavoriteArray = {13};
            int expectedCurrentPage = 2;

            FrontListFavoriteActionForm actual = (FrontListFavoriteActionForm) getActionForm();

            for(int i = 0; i < actual.getFavoriteExtendFormList().size(); i++) {
                assertThat(
                        actual.getFavoriteExtendFormList().get(i).getFavoriteId(),
                        is(expectedFavoriteIdArray[i]));
            }
            for(int i = 0; i < actual.getDisplayedFavoriteList().size(); i++) {
                assertThat(
                        actual.getDisplayedFavoriteList().get(i).getFavoriteId(),
                        is(expectedDisplayedFavoriteArray[i]));
            }
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
        }

        @Test
        public void testExecuteInCaseLogout() {
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/listFavorite.do");
        }

    }

}
