package admin.action.category;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.matcher.IsListComposedLabelValueBean.*;
import static test.matcher.IsMap.*;
import static test.property.PropertyTester.*;

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

import admin.actionform.category.AdminEditCategoryActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class EditCategoryInputActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.EditCategoryInputActionTest.xml") {
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

        private static final String ACTION_PATH = "/editCategoryInput.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() {
            addRequestParameter("categoryId", "5");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            AdminEditCategoryActionForm expected = new AdminEditCategoryActionForm();
            List<LabelValueBean> expectedCategoryListWithEmpty = new ArrayList<>();
            expectedCategoryListWithEmpty.add(new LabelValueBean("????????????????????????", ""));
            expectedCategoryListWithEmpty.add(new LabelValueBean("??????", "1"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("???????????????", "3"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("????????????", "6"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("???????????????", "4"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("??????", "2"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("?????????", "9"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("??????????????????", "10"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("????????????", "11"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("?????????", "12"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("????????????", "13"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("????????????????????????", "14"));
            Map<String, String> expectedCategoryStatusMap = new TreeMap<>();
            expectedCategoryStatusMap.put("0", "??????");
            expectedCategoryStatusMap.put("1", "??????");
            expected.setCategoryId(5);
            expected.setCategoryName("??????");
            expected.setSeniorCategoryId(3);
            expected.setSeniorCategoryName("????????????");
            expected.setCategoryStatus("1");
            expected.setCategoryCreatedAt(Timestamp.valueOf("2020-01-10 11:12:13.567"));
            expected.setCategoryUpdatedAt(Timestamp.valueOf("2020-01-11 12:13:14.678"));

            AdminEditCategoryActionForm actual
                    = (AdminEditCategoryActionForm) getSession().getAttribute("AdminEditCategoryActionForm");

            assertThat(actual.getSeniorCategoryList(), is(sameComponentLavelAndValueAs(expectedCategoryListWithEmpty)));
            assertThat(actual.getCategoryStatusMap(), is(sameEntrySetAs(expectedCategoryStatusMap)));
            String[] excludeProperties = new String[] {"seniorCategoryList", "categoryStatusMap"};
            assertThat(actual, is(samePropertyValueAs(expected, excludeProperties)));
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoParameterExist() {
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("unexistence");
        }

        @Test
        public void testExecuteInCaseLogout() {
            addRequestParameter("categoryId", "5");
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/editCategoryInput.do?categoryId=5");
        }

        @Test
        public void testExecuteToForwardUnexistenceInCaseNoCategoryExist() {
            addRequestParameter("categoryId", "16");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("unexistence");
        }

    }

}
