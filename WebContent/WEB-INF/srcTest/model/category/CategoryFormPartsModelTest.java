package model.category;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsListComposedLabelValueBean.*;
import static test.matcher.IsMap.*;
import static test.property.PropertyTester.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;
import org.junit.ClassRule;
import org.junit.Test;

import test.db.DbUnitTester;

public class CategoryFormPartsModelTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.CategoryFormPartsModelTest.xml") {
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
    public void testGetCategoryMapToGetMap() throws Exception {
        Map<Integer, String> expected = new TreeMap<>();
        expected.put(1, "文化");
        expected.put(2, "運動");
        expected.put(3, "日本文化");
        expected.put(4, "西洋文化");
        expected.put(5, "茶道");
        expected.put(6, "華道");
        expected.put(7, "表千家");
        expected.put(8, "裏千家");
        expected.put(9, "球技");
        expected.put(10, "サッカー");
        expected.put(11, "野球");
        expected.put(12, "剣技");
        expected.put(13, "剣道");
        expected.put(14, "フェンシング");
        expected.put(15, "キルティング");

        CategoryFormPartsModel sut = new CategoryFormPartsModel();
        Map<Integer, String> actual = sut.getCategoryMap();

        assertThat(actual, is(sameEntrySetAs(expected)));
    }

    @Test
    public void testGetCategoryListWithEmptyValueToGetList() throws Exception {
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("選択してください", ""));
        expected.add(new LabelValueBean("文化", "1"));
        expected.add(new LabelValueBean("　日本文化", "3"));
        expected.add(new LabelValueBean("　　茶道", "5"));
        expected.add(new LabelValueBean("　　　表千家", "7"));
        expected.add(new LabelValueBean("　　　裏千家", "8"));
        expected.add(new LabelValueBean("　　華道", "6"));
        expected.add(new LabelValueBean("　西洋文化", "4"));
        expected.add(new LabelValueBean("運動", "2"));
        expected.add(new LabelValueBean("　球技", "9"));
        expected.add(new LabelValueBean("　　サッカー", "10"));
        expected.add(new LabelValueBean("　　野球", "11"));
        expected.add(new LabelValueBean("　剣技", "12"));
        expected.add(new LabelValueBean("　　剣道", "13"));
        expected.add(new LabelValueBean("　　フェンシング", "14"));

        CategoryFormPartsModel sut = new CategoryFormPartsModel();
        List<LabelValueBean> actual = sut.getCategoryListWithEmptyValue();

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

    @Test
    public void testGetCategoryListWithoutEmptyValueToGetList() throws Exception {
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("文化", "1"));
        expected.add(new LabelValueBean("　日本文化", "3"));
        expected.add(new LabelValueBean("　　茶道", "5"));
        expected.add(new LabelValueBean("　　　表千家", "7"));
        expected.add(new LabelValueBean("　　　裏千家", "8"));
        expected.add(new LabelValueBean("　　華道", "6"));
        expected.add(new LabelValueBean("　西洋文化", "4"));
        expected.add(new LabelValueBean("運動", "2"));
        expected.add(new LabelValueBean("　球技", "9"));
        expected.add(new LabelValueBean("　　サッカー", "10"));
        expected.add(new LabelValueBean("　　野球", "11"));
        expected.add(new LabelValueBean("　剣技", "12"));
        expected.add(new LabelValueBean("　　剣道", "13"));
        expected.add(new LabelValueBean("　　フェンシング", "14"));

        CategoryFormPartsModel sut = new CategoryFormPartsModel();
        List<LabelValueBean> actual = sut.getCategoryListWithoutEmptyValue();

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

    @Test
    public void testGetCategoryListWithEmptyValueExcludeSelfCategoryGroupToGetList() throws Exception {
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("選択してください", ""));
        expected.add(new LabelValueBean("文化", "1"));
        expected.add(new LabelValueBean("　日本文化", "3"));
        expected.add(new LabelValueBean("　　茶道", "5"));
        expected.add(new LabelValueBean("　　　表千家", "7"));
        expected.add(new LabelValueBean("　　　裏千家", "8"));
        expected.add(new LabelValueBean("　　華道", "6"));
        expected.add(new LabelValueBean("　西洋文化", "4"));
        expected.add(new LabelValueBean("運動", "2"));
        expected.add(new LabelValueBean("　剣技", "12"));
        expected.add(new LabelValueBean("　　剣道", "13"));
        expected.add(new LabelValueBean("　　フェンシング", "14"));

        CategoryFormPartsModel sut = new CategoryFormPartsModel();
        List<LabelValueBean> actual = sut.getCategoryListWithEmptyValueExcludeSelfCategoryGroup(9);

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

    @Test
    public void testGetCategoryStatusMapToGetMap() {
        Map<String, String> expected = new TreeMap<>();
        expected.put("0", "無効");
        expected.put("1", "有効");

        CategoryFormPartsModel sut = new CategoryFormPartsModel();
        Map<String, String> actual = sut.getCategoryStatusMap();

        assertThat(actual, is(sameEntrySetAs(expected)));
    }

}
