package util;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
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

public class FormPartsUtilTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.FormPartsUtilTest.xml") {
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
    public void testGetPrefectureMap() throws Exception {
        Map<Integer, String> expected = new TreeMap<>();
        expected.put(1, "北海道");
        expected.put(2, "青森県");
        expected.put(3, "岩手県");
        expected.put(4, "宮城県");
        expected.put(5, "秋田県");
        expected.put(6, "山形県");
        expected.put(7, "福島県");
        expected.put(8, "茨城県");
        expected.put(9, "栃木県");
        expected.put(10, "群馬県");

        FormPartsUtil sut = new FormPartsUtil();
        Map<Integer, String> actual = sut.getPrefectureMap();

        assertThat(actual, is(sameEntrySetAs(expected)));
    }

    @Test
    public void testGetPrefectureListWithEmptyValue() throws Exception {
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("選択してください", ""));
        expected.add(new LabelValueBean("北海道", "1"));
        expected.add(new LabelValueBean("青森県", "2"));
        expected.add(new LabelValueBean("岩手県", "3"));
        expected.add(new LabelValueBean("宮城県", "4"));
        expected.add(new LabelValueBean("秋田県", "5"));
        expected.add(new LabelValueBean("山形県", "6"));
        expected.add(new LabelValueBean("福島県", "7"));
        expected.add(new LabelValueBean("茨城県", "8"));
        expected.add(new LabelValueBean("栃木県", "9"));
        expected.add(new LabelValueBean("群馬県", "10"));

        FormPartsUtil sut = new FormPartsUtil();
        List<LabelValueBean> actual = sut.getPrefectureListWithEmptyValue();

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

    @Test
    public void testGetPrefectureListWithoutEmptyValue() throws Exception {
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("北海道", "1"));
        expected.add(new LabelValueBean("青森県", "2"));
        expected.add(new LabelValueBean("岩手県", "3"));
        expected.add(new LabelValueBean("宮城県", "4"));
        expected.add(new LabelValueBean("秋田県", "5"));
        expected.add(new LabelValueBean("山形県", "6"));
        expected.add(new LabelValueBean("福島県", "7"));
        expected.add(new LabelValueBean("茨城県", "8"));
        expected.add(new LabelValueBean("栃木県", "9"));
        expected.add(new LabelValueBean("群馬県", "10"));

        FormPartsUtil sut = new FormPartsUtil();
        List<LabelValueBean> actual = sut.getPrefectureListWithoutEmptyValue();

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

}
