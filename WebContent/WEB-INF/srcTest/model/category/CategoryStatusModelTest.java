package model.category;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.property.PropertyTester.*;

import org.junit.ClassRule;
import org.junit.Test;

import test.db.DbUnitTester;

public class CategoryStatusModelTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.CategoryStatusModelTest.xml") {
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
    public void testIsCategoryNameExistInCaseCategoryNameExistAtCreatingCategory() throws Exception {
        CategoryStatusModel sut = new CategoryStatusModel();
        boolean actual = sut.isCategoryNameExist("文化", 0, true);

        assertThat(actual, is(true));
    }

    @Test
    public void testIsCategoryNameExistInCaseNoCategoryNameExistAtCreatingCategory() throws Exception {
        CategoryStatusModel sut = new CategoryStatusModel();
        boolean actual = sut.isCategoryNameExist("西洋文化", 0, true);

        assertThat(actual, is(false));
    }

    @Test
    public void testIsCategoryNameExistInCaseOtherCategoryHaveCategoryNameAtEditingCategory() throws Exception {
        CategoryStatusModel sut = new CategoryStatusModel();
        boolean actual = sut.isCategoryNameExist("野球", 7, false);

        assertThat(actual, is(true));
    }

    @Test
    public void testIsCategoryNameExistInCaseSelfCategoryHaveCategoryNameAtEditingCategory() throws Exception {
        CategoryStatusModel sut = new CategoryStatusModel();
        boolean actual = sut.isCategoryNameExist("サッカー", 7, false);

        assertThat(actual, is(false));
    }

    @Test
    public void testIsCategoryNameExistInCaseNoCategoryHaveCategoryNameAtEditingCategory() throws Exception {
        CategoryStatusModel sut = new CategoryStatusModel();
        boolean actual = sut.isCategoryNameExist("ポロ", 7, false);

        assertThat(actual, is(false));
    }

    @Test
    public void testIsCategoryReferredInCaseRefferredBySchool() throws Exception {
        CategoryStatusModel sut = new CategoryStatusModel();
        boolean actual = sut.isCategoryReferred(3);

        assertThat(actual, is(true));
    }

    @Test
    public void testIsCategoryReferredInCaseRefferredByOtherCategory() throws Exception {
        CategoryStatusModel sut = new CategoryStatusModel();
        boolean actual = sut.isCategoryReferred(1);

        assertThat(actual, is(true));
    }

    @Test
    public void testIsCategoryReferredInCaseRefferredByNoSchoolAndCategory() throws Exception {
        CategoryStatusModel sut = new CategoryStatusModel();
        boolean actual = sut.isCategoryReferred(4);

        assertThat(actual, is(false));
    }

    @Test
    public void testIsCategoryEnableInCaseCategoryIsValid() throws Exception {
        CategoryStatusModel sut = new CategoryStatusModel();
        boolean actual = sut.isCategoryEnable(6);

        assertThat(actual, is(true));
    }

    @Test
    public void testIsCategoryEnableInCaseCategoryIsInvalid() throws Exception {
        CategoryStatusModel sut = new CategoryStatusModel();
        boolean actual = sut.isCategoryEnable(7);

        assertThat(actual, is(false));
    }

}
