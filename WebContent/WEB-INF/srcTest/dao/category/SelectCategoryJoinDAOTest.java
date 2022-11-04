package dao.category;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.CategoryExtendActionForm;
import dao.category.sql.CategorySelectJoinWhereActionForm;
import test.db.DbUnitTester;

public class SelectCategoryJoinDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SelectCategoryJoinDAOTest.xml") {
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
    public void testSelectMatchedCategoryToGetAllCategory() throws Exception {
        String[] expected =
                {"1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15"};

        SelectCategoryJoinDAO sut = new SelectCategoryJoinDAO();
        CategorySelectJoinWhereActionForm whereForm = new CategorySelectJoinWhereActionForm();
        List<CategoryExtendActionForm> formList = sut.selectMatchedCategory(whereForm);
        String[] actual = new String[15];
        for(int i = 0; i < formList.size(); i++) {
            actual[i] = String.valueOf(formList.get(i).getCategoryId());
        }

        assertThat(actual, is(arrayContaining(expected)));
    }

    @Test
    public void testSelectMatchedCategoryToGetOneCategoryByCategoryId() throws Exception {
        CategoryExtendActionForm expected = new CategoryExtendActionForm();
        expected.setCategoryId(4);
        expected.setCategoryName("西洋文化");
        expected.setSeniorCategoryId(1);
        expected.setSeniorCategoryName("文化");
        expected.setCategoryStatus("1");
        expected.setCategoryCreatedAt(Timestamp.valueOf("2020-01-08 09:10:11.345"));
        expected.setCategoryUpdatedAt(Timestamp.valueOf("2020-01-09 10:11:12.456"));

        SelectCategoryJoinDAO sut = new SelectCategoryJoinDAO();
        CategorySelectJoinWhereActionForm whereForm = new CategorySelectJoinWhereActionForm();
        whereForm.setCategoryId(4);
        CategoryExtendActionForm actual = sut.selectMatchedCategory(whereForm).get(0);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testSelectMatchedCategoryToGetOneCategoryByCategoryName() throws Exception {
        int expected = 1;

        SelectCategoryJoinDAO sut = new SelectCategoryJoinDAO();
        CategorySelectJoinWhereActionForm whereForm = new CategorySelectJoinWhereActionForm();
        whereForm.setCategoryName("フェンシング");
        List<CategoryExtendActionForm> actual = sut.selectMatchedCategory(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedCategoryToGetOneCategoryByMultipleCondition() throws Exception {
        int expected = 1;

        SelectCategoryJoinDAO sut = new SelectCategoryJoinDAO();
        CategorySelectJoinWhereActionForm whereForm = new CategorySelectJoinWhereActionForm();
        whereForm.setSeniorCategoryId(12);
        whereForm.setCategoryName("フェンシング");
        whereForm.setCategoryStatus("1");
        List<CategoryExtendActionForm> actual = sut.selectMatchedCategory(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedCategoryToGetNoCategoryByMultipleCondition() throws Exception {
        int expected = 0;

        SelectCategoryJoinDAO sut = new SelectCategoryJoinDAO();
        CategorySelectJoinWhereActionForm whereForm = new CategorySelectJoinWhereActionForm();
        whereForm.setSeniorCategoryId(12);
        whereForm.setCategoryName("フェンシング");
        whereForm.setCategoryStatus("0");
        List<CategoryExtendActionForm> actual = sut.selectMatchedCategory(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedCategoryToGetTwoCategoryByNullSeniorCategoryId() throws Exception {
        int expected = 2;

        SelectCategoryJoinDAO sut = new SelectCategoryJoinDAO();
        CategorySelectJoinWhereActionForm whereForm = new CategorySelectJoinWhereActionForm();
        whereForm.setSeniorCategoryIdNullFlag(true);
        List<CategoryExtendActionForm> actual = sut.selectMatchedCategory(whereForm);

        assertThat(actual.size(), is(expected));
    }

}
