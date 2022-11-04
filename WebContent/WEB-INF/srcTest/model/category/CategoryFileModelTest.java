package model.category;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsHttpResponseHeader.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import actionform.CategoryExtendActionForm;
import servletunit.HttpServletResponseSimulator;
import test.db.DbUnitTester;
import test.generator.GenerateFormFile;

public class CategoryFileModelTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.CategoryFileModelTest.xml") {
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

    @Test
    public void testSetDownloadCsvFileDataToSetData() throws Exception {
        String expected = "categories_19700101090000000.csv";

        CategoryFileModel sut = new CategoryFileModel() {
            Timestamp getCurrentTimestamp() {
                return Timestamp.valueOf("1970-01-01 09:00:00.000");
            }
        };
        List<CategoryExtendActionForm> categoryExtendFormList = new ArrayList<>();
        CategoryExtendActionForm form1 = new CategoryExtendActionForm();
        form1.setCategoryId(1);
        form1.setCategoryName("カテゴリー１");
        form1.setSeniorCategoryId(0);
        form1.setSeniorCategoryName(null);
        form1.setCategoryStatus("1");
        categoryExtendFormList.add(form1);
        CategoryExtendActionForm form2 = new CategoryExtendActionForm();
        form2.setCategoryId(2);
        form2.setCategoryName("カテゴリー２");
        form2.setSeniorCategoryId(0);
        form2.setSeniorCategoryName(null);
        form2.setCategoryStatus("1");
        categoryExtendFormList.add(form2);
        CategoryExtendActionForm form3 = new CategoryExtendActionForm();
        form3.setCategoryId(2);
        form3.setCategoryName("カテゴリー３");
        form3.setSeniorCategoryId(1);
        form3.setSeniorCategoryName("カテゴリー１");
        form3.setCategoryStatus("1");
        categoryExtendFormList.add(form3);
        CategoryExtendActionForm form4 = new CategoryExtendActionForm();
        form4.setCategoryId(4);
        form4.setCategoryName("カテゴリー４");
        form4.setSeniorCategoryId(2);
        form4.setSeniorCategoryName("カテゴリー２");
        form4.setCategoryStatus("0");
        categoryExtendFormList.add(form4);
        HttpServletResponse stub = new HttpServletResponseSimulator();
        HttpServletResponse actual = sut.setDownloadCsvFileData(categoryExtendFormList, stub);

        assertThat(actual, is(contentOf("text/csv;charset=UTF8", expected)));
    }

    @Test
    public void testSetDownloadTemplateCsvFileDataToSetTemplateData() throws Exception{
        String expected = "categories_for_upload_template.csv";

        CategoryFileModel sut = new CategoryFileModel();
        HttpServletResponse stub = new HttpServletResponseSimulator();
        HttpServletResponse actual = sut.setDownloadTemplateCsvFileData(stub);

        assertThat(actual, is(contentOf("text/csv;charset=UTF8", expected)));
    }

    @Test
    public void testUpsertCsvFileDataToUpsertDataSuccessfully() throws Exception {
        CategoryFileModel sut = new CategoryFileModel();
        FormFile formFile= new GenerateFormFile(
                null,
                "model/category/CategoryFileModelTest.testUpsertCsvFileDataToUpsertDataSuccessfully.csv",
                0);
        sut.upsertCsvFileData(formFile);

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.CategoryListModelTest.testUpsertCsvFileDataToUpsertDataSuccessfully.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("categories");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "categories";
        String sqlQuery = "SELECT * FROM categories ORDER BY category_id ASC";
        String[] ignoreCols =
                {"category_id", "category_created_at", "category_updated_at",
                "category_delete_flag", "category_deleted_at"};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpsertCsvFileDataToThrowIllegalArgumentExceptionAtInsertingData() throws Exception {
        CategoryFileModel sut = new CategoryFileModel();
        FormFile formFile = new GenerateFormFile(
                null,
                "model/category/CategoryFileModelTest.testUpsertCsvFileDataToThrowIllegalArgumentExceptionAtInsertingData.csv",
                0);
        sut.upsertCsvFileData(formFile);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpsertCsvFileDataToThrowIllegalArgumentExceptionAtUpdatingData() throws Exception {
        CategoryFileModel sut = new CategoryFileModel();
        FormFile formFile = new GenerateFormFile(
                null,
                "model/category/CategoryFileModelTest.testUpsertCsvFileDataToThrowIllegalArgumentExceptionAtUpdatingData.csv",
                0);
        sut.upsertCsvFileData(formFile);
    }

}
