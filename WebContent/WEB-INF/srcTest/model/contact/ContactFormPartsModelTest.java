package model.contact;

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

public class ContactFormPartsModelTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.ContactFormPartsModelTest.xml") {
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
    public void testGetContactStatusMapToGetMap() throws Exception {
        Map<Integer, String> expected = new TreeMap<>();
        expected.put(1, "受付済");
        expected.put(2, "連絡済");

        ContactFormPartsModel sut = new ContactFormPartsModel();
        Map<Integer, String> actual = sut.getContactStatusMap();

        assertThat(actual, is(sameEntrySetAs(expected)));
    }

    @Test
    public void testGetContactStatusListToGetList() throws Exception {
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("受付済", "1"));
        expected.add(new LabelValueBean("連絡済", "2"));

        ContactFormPartsModel sut = new ContactFormPartsModel();
        List<LabelValueBean> actual = sut.getContactStatusListWithoutEmptyValue();

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

    @Test
    public void testGetSortKindForContactMap() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("byDescendingContactedAt", "お問合せ日が新しい順");
        expected.put("byAccendingContactedAt", "お問合せ日が古い順");
        expected.put("byDescendingUpdatedAt", "更新日が新しい順");
        expected.put("byAccendingUpdatedAt", "更新日が古い順");

        ContactFormPartsModel sut = new ContactFormPartsModel();
        Map<String, String> actual = sut.getSortKindForContactMap();

        assertThat(actual, is(sameEntrySetAs(expected)));
    }

    @Test
    public void testGetSortKindForContactListWithEmptyValueToGetList() {
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("選択する", ""));
        expected.add(new LabelValueBean("お問合せ日が新しい順", "byDescendingContactedAt"));
        expected.add(new LabelValueBean("お問合せ日が古い順", "byAccendingContactedAt"));
        expected.add(new LabelValueBean("更新日が新しい順", "byDescendingUpdatedAt"));
        expected.add(new LabelValueBean("更新日が古い順", "byAccendingUpdatedAt"));

        ContactFormPartsModel sut = new ContactFormPartsModel();
        List<LabelValueBean> actual = sut.getSortKindForContactListWithEmptyValue();

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

}
