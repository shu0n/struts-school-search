package admin.action.category;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsRegex.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import actionform.CategoryExtendActionForm;
import admin.actionform.category.AdminListCategoryActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class DownloadCategoryCsvActionTest {

    public static class executeTest extends MockStrutsTestCase {

        private static final String ACTION_PATH = "/downloadCategoryCsv.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteInCaseDownloadTempleteCsvSuccessfully() {
            AdminListCategoryActionForm inForm = new AdminListCategoryActionForm();
            List<CategoryExtendActionForm> categoryExtendFormList = new ArrayList<>();
            CategoryExtendActionForm firstForm = new CategoryExtendActionForm();
            firstForm.setCategoryId(1);
            firstForm.setCategoryName("カテゴリー１");
            firstForm.setSeniorCategoryId(0);
            firstForm.setSeniorCategoryName(null);
            firstForm.setCategoryStatus("1");
            categoryExtendFormList.add(firstForm);
            CategoryExtendActionForm secondForm = new CategoryExtendActionForm();
            secondForm.setCategoryId(2);
            secondForm.setCategoryName("カテゴリー２");
            secondForm.setSeniorCategoryId(1);
            secondForm.setSeniorCategoryName("カテゴリー１");
            secondForm.setCategoryStatus("1");
            categoryExtendFormList.add(secondForm);
            CategoryExtendActionForm thirdForm = new CategoryExtendActionForm();
            thirdForm.setCategoryId(3);
            thirdForm.setCategoryName("カテゴリー３");
            thirdForm.setSeniorCategoryId(1);
            thirdForm.setSeniorCategoryName("カテゴリー１");
            thirdForm.setCategoryStatus("0");
            categoryExtendFormList.add(thirdForm);
            inForm.setCategoryExtendFormList(categoryExtendFormList);
            setActionForm(inForm);
            addRequestParameter("template", "true");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward(null);

            String expectedContentType = "text/csv;charset=UTF8";
            String expectedContentDisposition = "attachment; filename=\"categories_for_upload_template.csv\"";

            HttpServletResponse actual = getResponse();

            assertThat((String) actual.getContentType(), is(expectedContentType));
            assertThat(
                    (String) actual.getHeader("Content-Disposition"),
                    is(matchPatternAs(expectedContentDisposition)));
        }

        @Test
        public void testExecuteInCaseDownloadActualDataCsvSuccessfully() {
            AdminListCategoryActionForm inForm = new AdminListCategoryActionForm();
            List<CategoryExtendActionForm> categoryExtendFormList = new ArrayList<>();
            CategoryExtendActionForm firstForm = new CategoryExtendActionForm();
            firstForm.setCategoryId(1);
            firstForm.setCategoryName("カテゴリー１");
            firstForm.setSeniorCategoryId(0);
            firstForm.setSeniorCategoryName(null);
            firstForm.setCategoryStatus("1");
            categoryExtendFormList.add(firstForm);
            CategoryExtendActionForm secondForm = new CategoryExtendActionForm();
            secondForm.setCategoryId(2);
            secondForm.setCategoryName("カテゴリー２");
            secondForm.setSeniorCategoryId(1);
            secondForm.setSeniorCategoryName("カテゴリー１");
            secondForm.setCategoryStatus("1");
            categoryExtendFormList.add(secondForm);
            CategoryExtendActionForm thirdForm = new CategoryExtendActionForm();
            thirdForm.setCategoryId(3);
            thirdForm.setCategoryName("カテゴリー３");
            thirdForm.setSeniorCategoryId(1);
            thirdForm.setSeniorCategoryName("カテゴリー１");
            thirdForm.setCategoryStatus("0");
            categoryExtendFormList.add(thirdForm);
            inForm.setCategoryExtendFormList(categoryExtendFormList);
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward(null);

            String expectedContentType = "text/csv;charset=UTF8";
            String expectedContentDisposition = "attachment; filename=\"categories_\\d{17}.csv\"";

            HttpServletResponse actual = getResponse();

            assertThat((String) actual.getContentType(), is(expectedContentType));
            assertThat(
                    (String) actual.getHeader("Content-Disposition"),
                    is(matchPatternAs(expectedContentDisposition)));
        }

        @Test
        public void testExecuteInCaseLogout() {
            AdminListCategoryActionForm inForm = new AdminListCategoryActionForm();
            List<CategoryExtendActionForm> categoryExtendFormList = new ArrayList<>();
            CategoryExtendActionForm firstForm = new CategoryExtendActionForm();
            firstForm.setCategoryId(1);
            firstForm.setCategoryName("カテゴリー１");
            firstForm.setSeniorCategoryId(0);
            firstForm.setSeniorCategoryName(null);
            firstForm.setCategoryStatus("1");
            categoryExtendFormList.add(firstForm);
            CategoryExtendActionForm secondForm = new CategoryExtendActionForm();
            secondForm.setCategoryId(2);
            secondForm.setCategoryName("カテゴリー２");
            secondForm.setSeniorCategoryId(1);
            secondForm.setSeniorCategoryName("カテゴリー１");
            secondForm.setCategoryStatus("1");
            categoryExtendFormList.add(secondForm);
            CategoryExtendActionForm thirdForm = new CategoryExtendActionForm();
            thirdForm.setCategoryId(3);
            thirdForm.setCategoryName("カテゴリー３");
            thirdForm.setSeniorCategoryId(1);
            thirdForm.setSeniorCategoryName("カテゴリー１");
            thirdForm.setCategoryStatus("0");
            categoryExtendFormList.add(thirdForm);
            inForm.setCategoryExtendFormList(categoryExtendFormList);
            setActionForm(inForm);
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/listCategory.do");
        }

        @Test
        public void testExecuteInCaseError() {
            AdminListCategoryActionForm inForm = new AdminListCategoryActionForm();
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listCategory.do");
            verifyActionErrors(new String[] {"ダウンロードに失敗しました。"});
        }

    }

}
