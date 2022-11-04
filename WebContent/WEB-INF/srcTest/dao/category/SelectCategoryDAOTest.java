package dao.category;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.matcher.IsListComposedStringArray.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.CategoryActionForm;
import dao.category.sql.CategorySelectWhereActionForm;
import test.db.DbUnitTester;

public class SelectCategoryDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SelectCategoryDAOTest.xml") {
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

        SelectCategoryDAO sut = new SelectCategoryDAO();
        CategorySelectWhereActionForm whereForm = new CategorySelectWhereActionForm();
        List<CategoryActionForm> formList = sut.selectMatchedCategory(whereForm);
        String[] actual = new String[15];
        for(int i = 0; i < formList.size(); i++) {
            actual[i] = String.valueOf(formList.get(i).getCategoryId());
        }

        assertThat(actual, is(arrayContaining(expected)));
    }

    @Test
    public void testSelectMatchedCategoryToGetOneCategoryByCategoryId() throws Exception {
        CategoryActionForm expected = new CategoryActionForm();
        expected.setCategoryId(4);
        expected.setCategoryName("西洋文化");
        expected.setSeniorCategoryId(1);
        expected.setCategoryStatus("1");
        expected.setCategoryCreatedAt(Timestamp.valueOf("2020-01-08 09:10:11.345"));
        expected.setCategoryUpdatedAt(Timestamp.valueOf("2020-01-09 10:11:12.456"));

        SelectCategoryDAO sut = new SelectCategoryDAO();
        CategorySelectWhereActionForm whereForm = new CategorySelectWhereActionForm();
        whereForm.setCategoryId(4);
        CategoryActionForm actual = sut.selectMatchedCategory(whereForm).get(0);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testSelectMatchedCategoryToGetOneCategoryByCategoryName() throws Exception {
        int expected = 1;

        SelectCategoryDAO sut = new SelectCategoryDAO();
        CategorySelectWhereActionForm whereForm = new CategorySelectWhereActionForm();
        whereForm.setCategoryName("フェンシング");
        List<CategoryActionForm> actual = sut.selectMatchedCategory(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedCategoryToGetOneCategoryByMultipleCondition() throws Exception {
        int expected = 1;

        SelectCategoryDAO sut = new SelectCategoryDAO();
        CategorySelectWhereActionForm whereForm = new CategorySelectWhereActionForm();
        whereForm.setSeniorCategoryId(12);
        whereForm.setCategoryName("フェンシング");
        whereForm.setCategoryStatus("1");
        List<CategoryActionForm> actual = sut.selectMatchedCategory(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedCategoryToGetNoCategoryByMultipleCondition() throws Exception {
        int expected = 0;

        SelectCategoryDAO sut = new SelectCategoryDAO();
        CategorySelectWhereActionForm whereForm = new CategorySelectWhereActionForm();
        whereForm.setSeniorCategoryId(12);
        whereForm.setCategoryName("フェンシング");
        whereForm.setCategoryStatus("0");
        List<CategoryActionForm> actual = sut.selectMatchedCategory(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedCategoryToGetTwoCategoryByNullSeniorCategoryId() throws Exception {
        int expected = 2;

        SelectCategoryDAO sut = new SelectCategoryDAO();
        CategorySelectWhereActionForm whereForm = new CategorySelectWhereActionForm();
        whereForm.setSeniorCategoryIdNullFlag(true);
        List<CategoryActionForm> actual = sut.selectMatchedCategory(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectDescendantCategoryToGetCategoryListFromFirstLevel() throws Exception {
        List<String[]> expected = new ArrayList<>();
        String[] expectedArray1 = {"1", "文化", "1"};
        expected.add(expectedArray1);
        String[] expectedArray2 = {"3", "日本文化", "2"};
        expected.add(expectedArray2);
        String[] expectedArray3 = {"5", "茶道", "3"};
        expected.add(expectedArray3);
        String[] expectedArray4 = {"7", "表千家", "4"};
        expected.add(expectedArray4);
        String[] expectedArray5 = {"8", "裏千家", "4"};
        expected.add(expectedArray5);
        String[] expectedArray6 = {"6", "華道", "3"};
        expected.add(expectedArray6);
        String[] expectedArray7 = {"4", "西洋文化", "2"};
        expected.add(expectedArray7);
        String[] expectedArray8 = {"2", "運動", "1"};
        expected.add(expectedArray8);
        String[] expectedArray9 = {"9", "球技", "2"};
        expected.add(expectedArray9);
        String[] expectedArray10 = {"10", "サッカー", "3"};
        expected.add(expectedArray10);
        String[] expectedArray11 = {"11", "野球", "3"};
        expected.add(expectedArray11);
        String[] expectedArray12 = {"12", "剣技", "2"};
        expected.add(expectedArray12);
        String[] expectedArray13 = {"13", "剣道", "3"};
        expected.add(expectedArray13);
        String[] expectedArray14 = {"14", "フェンシング", "3"};
        expected.add(expectedArray14);

        SelectCategoryDAO sut = new SelectCategoryDAO();
        List<String[]> actual = sut.selectDescendantCategory(0);

        assertThat(actual, is(sameComponentValueAs(expected)));
    }

    @Test
    public void testSelectDescendantCategoryToGetCategoryListFromThirdLevel() throws Exception {
        List<String[]> expected = new ArrayList<>();
        String[] expectedArray1 = {"5", "茶道", "1"};
        expected.add(expectedArray1);
        String[] expectedArray2 = {"7", "表千家", "2"};
        expected.add(expectedArray2);
        String[] expectedArray3 = {"8", "裏千家", "2"};
        expected.add(expectedArray3);
        String[] expectedArray4 = {"6", "華道", "1"};
        expected.add(expectedArray4);

        SelectCategoryDAO sut = new SelectCategoryDAO();
        List<String[]> actual = sut.selectDescendantCategory(3);

        assertThat(actual, is(sameComponentValueAs(expected)));
    }

    @Test
    public void testSelectDescendantCategoryToGetNoCategoryList() throws Exception {
        List<String[]> expected = new ArrayList<>();

        SelectCategoryDAO sut = new SelectCategoryDAO();
        List<String[]> actual = sut.selectDescendantCategory(17);

        assertThat(actual, is(sameComponentValueAs(expected)));
    }

}
