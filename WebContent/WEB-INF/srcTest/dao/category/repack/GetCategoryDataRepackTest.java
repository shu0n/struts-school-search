package dao.category.repack;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.CategoryExtendActionForm;
import dao.category.sql.CategorySelectJoinWhereActionForm;
import exception.NoDataExistException;
import test.db.DbUnitTester;

public class GetCategoryDataRepackTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.GetCategoryDataRepackTest.xml") {
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
    public void testGetCategoryDataInCaseOneMatchedCategoryExist() throws Exception {
        CategoryExtendActionForm expected = new CategoryExtendActionForm();
        expected.setCategoryId(3);
        expected.setCategoryName("日本文化");
        expected.setSeniorCategoryId(1);
        expected.setSeniorCategoryName("文化");
        expected.setCategoryStatus("1");
        expected.setCategoryCreatedAt(Timestamp.valueOf("2020-01-04 05:06:07.891"));
        expected.setCategoryUpdatedAt(Timestamp.valueOf("2020-01-05 06:07:08.912"));

        GetCategoryDataRepack sut = new GetCategoryDataRepack();
        CategorySelectJoinWhereActionForm whereForm = new CategorySelectJoinWhereActionForm();
        whereForm.setCategoryId(3);
        CategoryExtendActionForm actual = new CategoryExtendActionForm();
        actual = sut.getCategoryData(whereForm, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testGetCategoryDataInCaseTwoMatchedCategoryExist() throws Exception {
        CategoryExtendActionForm expected = new CategoryExtendActionForm();
        expected.setCategoryId(7);
        expected.setCategoryName("表千家");
        expected.setSeniorCategoryId(5);
        expected.setSeniorCategoryName("茶道");
        expected.setCategoryStatus("1");
        expected.setCategoryCreatedAt(Timestamp.valueOf("2020-01-08 09:10:11.345"));
        expected.setCategoryUpdatedAt(Timestamp.valueOf("2020-01-09 10:11:12.456"));

        GetCategoryDataRepack sut = new GetCategoryDataRepack();
        CategorySelectJoinWhereActionForm whereForm = new CategorySelectJoinWhereActionForm();
        whereForm.setSeniorCategoryId(5);
        CategoryExtendActionForm actual = new CategoryExtendActionForm();
        actual = sut.getCategoryData(whereForm, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testGetCategoryDataInCaseOneMatchedCategoryHavingNullColumnExist() throws Exception {
        CategoryExtendActionForm expected = new CategoryExtendActionForm();
        expected.setCategoryId(1);
        expected.setCategoryName("文化");
        expected.setSeniorCategoryId(0);
        expected.setSeniorCategoryName(null);
        expected.setCategoryStatus("1");
        expected.setCategoryCreatedAt(Timestamp.valueOf("2020-01-02 03:04:05.678"));
        expected.setCategoryUpdatedAt(Timestamp.valueOf("2020-01-03 04:05:06.789"));

        GetCategoryDataRepack sut = new GetCategoryDataRepack();
        CategorySelectJoinWhereActionForm whereForm = new CategorySelectJoinWhereActionForm();
        whereForm.setCategoryId(1);
        CategoryExtendActionForm actual = new CategoryExtendActionForm();
        actual = sut.getCategoryData(whereForm, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test(expected = NoDataExistException.class)
    public void testGetCategoryDataInCaseNoMatchedCategoryExist() throws Exception {
        GetCategoryDataRepack sut = new GetCategoryDataRepack();
        CategorySelectJoinWhereActionForm whereForm = new CategorySelectJoinWhereActionForm();
        whereForm.setCategoryId(9);
        CategoryExtendActionForm actual = new CategoryExtendActionForm();
        sut.getCategoryData(whereForm, actual);
    }

}
