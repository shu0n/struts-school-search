package model.entry;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsListComposedLabelValueBean.*;
import static test.matcher.IsMap.*;
import static test.property.PropertyTester.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;
import org.junit.ClassRule;
import org.junit.Test;

import test.db.DbUnitTester;

public class EntryFormPartsModelTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.EntryFormPartsModelTest.xml") {
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
    public void testGetEntryStatusMapToGetMap() throws Exception {
        Map<Integer, String> expected = new TreeMap<>();
        expected.put(1, "申込済");
        expected.put(2, "連絡済");
        expected.put(3, "キャンセル済");

        EntryFormPartsModel sut = new EntryFormPartsModel();
        Map<Integer, String> actual = sut.getEntryStatusMap();

        assertThat(actual, is(sameEntrySetAs(expected)));
    }

    @Test
    public void testGetEntryStatusListWithEmptyValueToGetList() throws Exception{
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("選択してください", ""));
        expected.add(new LabelValueBean("申込済", "1"));
        expected.add(new LabelValueBean("連絡済", "2"));
        expected.add(new LabelValueBean("キャンセル済", "3"));

        EntryFormPartsModel sut = new EntryFormPartsModel();
        List<LabelValueBean> actual = sut.getEntryStatusListWithEmptyValue();

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

    @Test
    public void testGetEntryStatusListWithoutEmptyValueToGetList() throws Exception{
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("申込済", "1"));
        expected.add(new LabelValueBean("連絡済", "2"));
        expected.add(new LabelValueBean("キャンセル済", "3"));

        EntryFormPartsModel sut = new EntryFormPartsModel();
        List<LabelValueBean> actual = sut.getEntryStatusListWithoutEmptyValue();

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

    @Test
    public void testGetSortKindForEntryMap() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("byDescendingEntriedAt", "申込日が新しい順");
        expected.put("byAccendingEntriedAt", "申込日が古い順");
        expected.put("byDescendingUpdatedAt", "更新日が新しい順");
        expected.put("byAccendingUpdatedAt", "更新日が古い順");

        EntryFormPartsModel sut = new EntryFormPartsModel();
        Map<String, String> actual = sut.getSortKindForEntryMap();

        assertThat(actual, is(sameEntrySetAs(expected)));
    }

    @Test
    public void testGetSortKindForEntryListWithEmptyValue() throws Exception {
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("選択する", ""));
        expected.add(new LabelValueBean("申込日が新しい順", "byDescendingEntriedAt"));
        expected.add(new LabelValueBean("申込日が古い順", "byAccendingEntriedAt"));
        expected.add(new LabelValueBean("更新日が新しい順", "byDescendingUpdatedAt"));
        expected.add(new LabelValueBean("更新日が古い順", "byAccendingUpdatedAt"));

        EntryFormPartsModel sut = new EntryFormPartsModel();
        List<LabelValueBean> actual = sut.getSortKindForEntryListWithEmptyValue();

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

}
