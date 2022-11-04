package admin.action.category;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.property.PropertyTester.*;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import admin.actionform.category.AdminDeleteCategoryActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class DeleteCategoryActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.DeleteCategoryActionTest.xml") {
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

        private static final String ACTION_PATH = "/deleteCategory.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccessInCaseDeleteCategorySuccessfully() {
            addRequestParameter("categoryId", "4");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            AdminDeleteCategoryActionForm actual
                    = (AdminDeleteCategoryActionForm) getSession().getAttribute("AdminDeleteCategoryActionForm");

            assertThat(actual, is(nullValue()));
        }

        @Test
        public void testExecuteToFowardSuccessInCaseNoCategoryExist() {
            addRequestParameter("categoryId", "2");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            AdminDeleteCategoryActionForm actual
                    = (AdminDeleteCategoryActionForm) getSession().getAttribute("AdminDeleteCategoryActionForm");

            assertThat(actual, is(nullValue()));
        }

        @Test
        public void testExecuteToForwardUnexistence() {
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("unexistence");
        }

        @Test
        public void testExecuteToInCaseLogout() {
            addRequestParameter("categoryId", "3");
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/editCategoryInput.do?categoryId=3");
        }

        @Test
        public void testExecuteToInCaseRefferedBySchool() {
            addRequestParameter("categoryId", "5");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/editCategoryInput.do?categoryId=5");
            verifyActionErrors(new String[] {"スクールのカテゴリーに登録されています。"});
        }

        @Test
        public void testExecuteToInCaseRefferedByCategory() {
            addRequestParameter("categoryId", "3");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/editCategoryInput.do?categoryId=3");
            verifyActionErrors(new String[] {"上位カテゴリーに登録されています。"});
        }

    }

}
