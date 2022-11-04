package model.category;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.property.PropertyTester.*;

import org.junit.ClassRule;
import org.junit.Test;

import dao.category.sql.CategoryInsertDataActionForm;
import dao.category.sql.CategoryUpdateDataActionForm;
import test.db.DbUnitTester;

public class CategoryValidatorModelTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.CategoryValidatorModelTest.xml") {
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
    public void testValidateInsertCategoryDataInCaseValidData() throws Exception {
        CategoryValidatorModel sut = new CategoryValidatorModel();
        CategoryInsertDataActionForm insertDataForm = new CategoryInsertDataActionForm();
        insertDataForm.setCategoryName(
                "カテゴリーああああああああああああああああああああああああああああああああああああああああああああ");
        insertDataForm.setSeniorCategoryId(1);
        insertDataForm.setCategoryStatus("1");
        boolean actual = sut.validateInsertCategoryData(insertDataForm);

        assertThat(actual, is(true));
    }

    @Test
    public void testValidateInsertCategoryDataInCaseCategoryNameIsTooLong() throws Exception {
        CategoryValidatorModel sut = new CategoryValidatorModel();
        CategoryInsertDataActionForm insertDataForm = new CategoryInsertDataActionForm();
        insertDataForm.setCategoryName(
                "カテゴリーああああああああああああああああああああああああああああああああああああああああああああい");
        boolean actual = sut.validateInsertCategoryData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertCategoryDataInCaseSeniorCategoryIsNull() throws Exception {
        CategoryValidatorModel sut = new CategoryValidatorModel();
        CategoryInsertDataActionForm insertDataForm = new CategoryInsertDataActionForm();
        insertDataForm.setSeniorCategoryId(8);
        boolean actual = sut.validateInsertCategoryData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertCategoryDataInCaseInvalidCategoryStatus() throws Exception {
        CategoryValidatorModel sut = new CategoryValidatorModel();
        CategoryInsertDataActionForm insertDataForm = new CategoryInsertDataActionForm();
        insertDataForm.setCategoryStatus("2");
        boolean actual = sut.validateInsertCategoryData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateCategoryDataInCaseValidData() throws Exception {
        CategoryValidatorModel sut = new CategoryValidatorModel();
        CategoryUpdateDataActionForm updateDataForm = new CategoryUpdateDataActionForm();
        updateDataForm.setCategoryId(7);
        updateDataForm.setCategoryName(
                "カテゴリーあああああああああああああああああああああああああああああああああああああああああああああ");
        updateDataForm.setSeniorCategoryId(5);
        updateDataForm.setCategoryStatus("1");
        boolean actual = sut.validateUpdateCategoryData(updateDataForm);

        assertThat(actual, is(true));
    }

    @Test
    public void testValidateUpdateCategoryDataInCaseNoCategoryIdIsSetted() throws Exception {
        CategoryValidatorModel sut = new CategoryValidatorModel();
        CategoryUpdateDataActionForm updateDataForm = new CategoryUpdateDataActionForm();
        updateDataForm.setCategoryId(0);
        boolean actual = sut.validateUpdateCategoryData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateCategoryDataInCaseNoCategoryIdExist() throws Exception {
        CategoryValidatorModel sut = new CategoryValidatorModel();
        CategoryUpdateDataActionForm updateDataForm = new CategoryUpdateDataActionForm();
        updateDataForm.setCategoryId(8);
        boolean actual = sut.validateUpdateCategoryData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateCategoryDataInCaseCategoryNameIsTooLong() throws Exception {
        CategoryValidatorModel sut = new CategoryValidatorModel();
        CategoryUpdateDataActionForm updateDataForm = new CategoryUpdateDataActionForm();
        updateDataForm.setCategoryId(6);
        updateDataForm.setCategoryName(
                "カテゴリーああああああああああああああああああああああああああああああああああああああああああああい");
        boolean actual = sut.validateUpdateCategoryData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateCategoryDataInCaseCategoryNameIsAlreadyExist() throws Exception {
        CategoryValidatorModel sut = new CategoryValidatorModel();
        CategoryUpdateDataActionForm updateDataForm = new CategoryUpdateDataActionForm();
        updateDataForm.setCategoryId(6);
        updateDataForm.setCategoryName("運動");
        boolean actual = sut.validateUpdateCategoryData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateCategoryDataInCaseNoSeniorCategoryIdExist() throws Exception {
        CategoryValidatorModel sut = new CategoryValidatorModel();
        CategoryUpdateDataActionForm updateDataForm = new CategoryUpdateDataActionForm();
        updateDataForm.setCategoryId(5);
        updateDataForm.setCategoryName("運動");
        updateDataForm.setSeniorCategoryId(9);
        boolean actual = sut.validateUpdateCategoryData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateCategoryDataInCaseSeniorCategoryIdIsDescendantCategoryId() throws Exception {
        CategoryValidatorModel sut = new CategoryValidatorModel();
        CategoryUpdateDataActionForm updateDataForm = new CategoryUpdateDataActionForm();
        updateDataForm.setCategoryId(3);
        updateDataForm.setCategoryName("日本文化");
        updateDataForm.setSeniorCategoryId(4);
        boolean actual = sut.validateUpdateCategoryData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateCategoryDataInCaseCategoryStatusIsInvalid() throws Exception {
        CategoryValidatorModel sut = new CategoryValidatorModel();
        CategoryUpdateDataActionForm updateDataForm = new CategoryUpdateDataActionForm();
        updateDataForm.setCategoryId(3);
        updateDataForm.setCategoryName("日本文化");
        updateDataForm.setSeniorCategoryId(1);
        updateDataForm.setCategoryStatus("2");
        boolean actual = sut.validateUpdateCategoryData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateCategoryDataInCaseReferredByOtherCategory() throws Exception {
        CategoryValidatorModel sut = new CategoryValidatorModel();
        CategoryUpdateDataActionForm updateDataForm = new CategoryUpdateDataActionForm();
        updateDataForm.setCategoryId(3);
        updateDataForm.setCategoryName("日本文化");
        updateDataForm.setSeniorCategoryId(1);
        updateDataForm.setCategoryStatus("0");
        boolean actual = sut.validateUpdateCategoryData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateCategoryDataInCaseReferredBySchool() throws Exception {
        CategoryValidatorModel sut = new CategoryValidatorModel();
        CategoryUpdateDataActionForm updateDataForm = new CategoryUpdateDataActionForm();
        updateDataForm.setCategoryId(6);
        updateDataForm.setCategoryName("野球");
        updateDataForm.setSeniorCategoryId(5);
        updateDataForm.setCategoryStatus("0");
        boolean actual = sut.validateUpdateCategoryData(updateDataForm);

        assertThat(actual, is(false));
    }

}
