package admin.action.category;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import actionform.CategoryExtendActionForm;
import admin.actionform.category.AdminListCategoryActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class ListCategoryActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.ListCategoryActionTest.xml") {
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

        private static final String ACTION_PATH = "/listCategory.do";

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

            int[] expectedCategoryIdArray =
                    {21, 20, 19, 18, 17,
                    16, 15, 14, 13, 12,
                    11, 10, 9, 8, 7,
                    6, 5, 4, 3, 2, 1};
            CategoryExtendActionForm expectedForm1 = new CategoryExtendActionForm();
            expectedForm1.setCategoryId(21);
            expectedForm1.setCategoryName("ポロ");
            expectedForm1.setSeniorCategoryId(12);
            expectedForm1.setSeniorCategoryName("球技");
            expectedForm1.setCategoryStatus("0");
            expectedForm1.setCategoryStatusName("無効");
            expectedForm1.setCategoryCreatedAt(Timestamp.valueOf("2020-02-10 11:12:13.567"));
            expectedForm1.setCategoryUpdatedAt(Timestamp.valueOf("2020-02-11 12:13:14.678"));
            CategoryExtendActionForm expectedForm2 = new CategoryExtendActionForm();
            expectedForm2.setCategoryId(1);
            expectedForm2.setCategoryName("文化");
            expectedForm2.setSeniorCategoryId(0);
            expectedForm2.setSeniorCategoryName(null);
            expectedForm2.setCategoryStatus("1");
            expectedForm2.setCategoryStatusName("有効");
            expectedForm2.setCategoryCreatedAt(Timestamp.valueOf("2020-01-02 03:04:05.678"));
            expectedForm2.setCategoryUpdatedAt(Timestamp.valueOf("2020-01-03 04:05:06.789"));
            int expectedTotalForm = 21;
            int expectedTotalPage = 2;
            int expectedCurrentPage = 1;

            AdminListCategoryActionForm actual = (AdminListCategoryActionForm) getActionForm();

            for(int i = 0; i < actual.getCategoryExtendFormList().size(); i++) {
                assertThat(actual.getCategoryExtendFormList().get(i).getCategoryId(), is(expectedCategoryIdArray[i]));
            }
            assertThat(actual.getCategoryExtendFormList().get(0), is(samePropertyValueAs(expectedForm1)));
            assertThat(actual.getCategoryExtendFormList().get(20), is(samePropertyValueAs(expectedForm2)));
            assertThat(actual.getTotalForm(), is(expectedTotalForm));
            assertThat(actual.getTotalPage(), is(expectedTotalPage));
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseCurrentPageIsSecond() {
            AdminListCategoryActionForm adminListCategoryForm = new AdminListCategoryActionForm();
            List<CategoryExtendActionForm> categoryExtendFormList = new ArrayList<>();
            CategoryExtendActionForm firstForm = new CategoryExtendActionForm();
            firstForm.setCategoryId(21);
            firstForm.setCategoryStatus("1");
            categoryExtendFormList.add(firstForm);
            CategoryExtendActionForm secondForm = new CategoryExtendActionForm();
            secondForm.setCategoryId(20);
            secondForm.setCategoryStatus("0");
            categoryExtendFormList.add(secondForm);
            CategoryExtendActionForm thirdForm = new CategoryExtendActionForm();
            thirdForm.setCategoryId(19);
            thirdForm.setCategoryStatus("1");
            categoryExtendFormList.add(thirdForm);
            CategoryExtendActionForm fourthForm = new CategoryExtendActionForm();
            fourthForm.setCategoryId(18);
            fourthForm.setCategoryStatus("0");
            categoryExtendFormList.add(fourthForm);
            CategoryExtendActionForm fifthForm = new CategoryExtendActionForm();
            fifthForm.setCategoryId(17);
            fifthForm.setCategoryStatus("1");
            categoryExtendFormList.add(fifthForm);
            CategoryExtendActionForm sixthForm = new CategoryExtendActionForm();
            sixthForm.setCategoryId(16);
            sixthForm.setCategoryStatus("0");
            categoryExtendFormList.add(sixthForm);
            CategoryExtendActionForm seventhForm = new CategoryExtendActionForm();
            seventhForm.setCategoryId(15);
            seventhForm.setCategoryStatus("1");
            categoryExtendFormList.add(seventhForm);
            CategoryExtendActionForm eighthForm = new CategoryExtendActionForm();
            eighthForm.setCategoryId(14);
            eighthForm.setCategoryStatus("0");
            categoryExtendFormList.add(eighthForm);
            CategoryExtendActionForm ninthForm = new CategoryExtendActionForm();
            ninthForm.setCategoryId(13);
            ninthForm.setCategoryStatus("1");
            categoryExtendFormList.add(ninthForm);
            CategoryExtendActionForm tenthForm = new CategoryExtendActionForm();
            tenthForm.setCategoryId(12);
            tenthForm.setCategoryStatus("0");
            categoryExtendFormList.add(tenthForm);
            CategoryExtendActionForm eleventhForm = new CategoryExtendActionForm();
            eleventhForm.setCategoryId(11);
            eleventhForm.setCategoryStatus("1");
            categoryExtendFormList.add(eleventhForm);
            CategoryExtendActionForm twelfthForm = new CategoryExtendActionForm();
            twelfthForm.setCategoryId(10);
            twelfthForm.setCategoryStatus("0");
            categoryExtendFormList.add(twelfthForm);
            CategoryExtendActionForm thirteenthForm = new CategoryExtendActionForm();
            thirteenthForm.setCategoryId(9);
            thirteenthForm.setCategoryStatus("1");
            categoryExtendFormList.add(thirteenthForm);
            CategoryExtendActionForm fourteenthForm = new CategoryExtendActionForm();
            fourteenthForm.setCategoryId(8);
            fourteenthForm.setCategoryStatus("0");
            categoryExtendFormList.add(fourteenthForm);
            CategoryExtendActionForm fifteenthForm = new CategoryExtendActionForm();
            fifteenthForm.setCategoryId(7);
            fifteenthForm.setCategoryStatus("1");
            categoryExtendFormList.add(fifteenthForm);
            CategoryExtendActionForm sixteenthForm = new CategoryExtendActionForm();
            sixteenthForm.setCategoryId(6);
            sixteenthForm.setCategoryStatus("0");
            categoryExtendFormList.add(sixteenthForm);
            CategoryExtendActionForm seventeenthForm = new CategoryExtendActionForm();
            seventeenthForm.setCategoryId(5);
            seventeenthForm.setCategoryStatus("1");
            categoryExtendFormList.add(seventeenthForm);
            CategoryExtendActionForm eighteenthForm = new CategoryExtendActionForm();
            eighteenthForm.setCategoryId(4);
            eighteenthForm.setCategoryStatus("0");
            categoryExtendFormList.add(eighteenthForm);
            CategoryExtendActionForm nineteenthForm = new CategoryExtendActionForm();
            nineteenthForm.setCategoryId(3);
            nineteenthForm.setCategoryStatus("1");
            categoryExtendFormList.add(nineteenthForm);
            CategoryExtendActionForm twentiethForm = new CategoryExtendActionForm();
            twentiethForm.setCategoryId(2);
            twentiethForm.setCategoryStatus("0");
            categoryExtendFormList.add(twentiethForm);
            CategoryExtendActionForm twentyFirstForm = new CategoryExtendActionForm();
            twentyFirstForm.setCategoryId(1);
            twentyFirstForm.setCategoryStatus("1");
            categoryExtendFormList.add(twentyFirstForm);
            adminListCategoryForm.setCategoryExtendFormList(categoryExtendFormList);
            setActionForm(adminListCategoryForm);
            addRequestParameter("currentPage", "2");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            int[] expectedCategoryIdArray =
                    {21, 20, 19, 18, 17, 16,
                    15, 14, 13, 12, 11,
                    10, 9, 8, 7, 6,
                    5, 4, 3, 2, 1};
            int[] expectedDisplayedCategoryIdArray = {1};
            int expectedCurrentPage = 2;

            AdminListCategoryActionForm actual = (AdminListCategoryActionForm) getActionForm();

            for(int i = 0; i < actual.getCategoryExtendFormList().size(); i++) {
                assertThat(actual.getCategoryExtendFormList().get(i).getCategoryId(), is(expectedCategoryIdArray[i]));
            }
            for(int i = 0; i < actual.getDisplayedCategoryList().size(); i++) {
                assertThat(
                        actual.getDisplayedCategoryList().get(i).getCategoryId(),
                        is(expectedDisplayedCategoryIdArray[i]));
            }
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
        }

        @Test
        public void testExecuteInCaseLogout() {
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/listCategory.do");
        }

    }

}
