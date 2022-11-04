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
            expectedCategoryListWithEmpty.add(new LabelValueBean("選択してください", ""));
            expectedCategoryListWithEmpty.add(new LabelValueBean("文化", "1"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("　日本文化", "3"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("　　華道", "6"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("　西洋文化", "4"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("運動", "2"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("　球技", "9"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("　　サッカー", "10"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("　　野球", "11"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("　剣技", "12"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("　　剣道", "13"));
            expectedCategoryListWithEmpty.add(new LabelValueBean("　　フェンシング", "14"));
            Map<String, String> expectedCategoryStatusMap = new TreeMap<>();
            expectedCategoryStatusMap.put("0", "無効");
            expectedCategoryStatusMap.put("1", "有効");
            expected.setCategoryId(5);
            expected.setCategoryName("茶道");
            expected.setSeniorCategoryId(3);
            expected.setSeniorCategoryName("日本文化");
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
