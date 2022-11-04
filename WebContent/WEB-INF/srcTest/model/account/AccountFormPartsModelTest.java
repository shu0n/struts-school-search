package model.account;

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

public class AccountFormPartsModelTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.AccountFormPartsModelTest.xml") {
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
    public void testGetAccountStatusMapToGetMap() {
        Map<String, String> expected = new TreeMap<>();
        expected.put("0", "無効");
        expected.put("1", "有効");

        AccountFormPartsModel sut = new AccountFormPartsModel();
        Map<String, String> actual = sut.getAccountStatusMap();

        assertThat(actual, is(sameEntrySetAs(expected)));
    }

    @Test
    public void testGetAccountStatusListToGetList() {
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("無効", "0"));
        expected.add(new LabelValueBean("有効", "1"));

        AccountFormPartsModel sut = new AccountFormPartsModel();
        List<LabelValueBean> actual = sut.getAccountStatusList();

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

    @Test
    public void testGetSexMapToGetMap() throws Exception {
        Map<Integer, String> expected = new TreeMap<>();
        expected.put(1, "男性");
        expected.put(2, "女性");
        expected.put(3, "その他");

        AccountFormPartsModel sut = new AccountFormPartsModel();
        Map<Integer, String> actual = sut.getSexMap();

        assertThat(actual, is(sameEntrySetAs(expected)));
    }

    @Test
    public void testGetSexListWithoutEmptyValueToGetList() throws Exception {
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("男性", "1"));
        expected.add(new LabelValueBean("女性", "2"));
        expected.add(new LabelValueBean("その他", "3"));

        AccountFormPartsModel sut = new AccountFormPartsModel();
        List<LabelValueBean> actual = sut.getSexListWithoutEmptyValue();

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

    @Test
    public void testGetBirthYearListWithEmptyValueToGetList() throws Exception {
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("選択してください", ""));
        expected.add(new LabelValueBean("1920", "1920"));
        expected.add(new LabelValueBean("1921", "1921"));
        expected.add(new LabelValueBean("1922", "1922"));
        expected.add(new LabelValueBean("1923", "1923"));
        expected.add(new LabelValueBean("1924", "1924"));
        expected.add(new LabelValueBean("1925", "1925"));
        expected.add(new LabelValueBean("1926", "1926"));
        expected.add(new LabelValueBean("1927", "1927"));
        expected.add(new LabelValueBean("1928", "1928"));
        expected.add(new LabelValueBean("1929", "1929"));
        expected.add(new LabelValueBean("1930", "1930"));
        expected.add(new LabelValueBean("1931", "1931"));
        expected.add(new LabelValueBean("1932", "1932"));
        expected.add(new LabelValueBean("1933", "1933"));
        expected.add(new LabelValueBean("1934", "1934"));
        expected.add(new LabelValueBean("1935", "1935"));
        expected.add(new LabelValueBean("1936", "1936"));
        expected.add(new LabelValueBean("1937", "1937"));
        expected.add(new LabelValueBean("1938", "1938"));
        expected.add(new LabelValueBean("1939", "1939"));
        expected.add(new LabelValueBean("1940", "1940"));
        expected.add(new LabelValueBean("1941", "1941"));
        expected.add(new LabelValueBean("1942", "1942"));
        expected.add(new LabelValueBean("1943", "1943"));
        expected.add(new LabelValueBean("1944", "1944"));
        expected.add(new LabelValueBean("1945", "1945"));
        expected.add(new LabelValueBean("1946", "1946"));
        expected.add(new LabelValueBean("1947", "1947"));
        expected.add(new LabelValueBean("1948", "1948"));
        expected.add(new LabelValueBean("1949", "1949"));
        expected.add(new LabelValueBean("1950", "1950"));
        expected.add(new LabelValueBean("1951", "1951"));
        expected.add(new LabelValueBean("1952", "1952"));
        expected.add(new LabelValueBean("1953", "1953"));
        expected.add(new LabelValueBean("1954", "1954"));
        expected.add(new LabelValueBean("1955", "1955"));
        expected.add(new LabelValueBean("1956", "1956"));
        expected.add(new LabelValueBean("1957", "1957"));
        expected.add(new LabelValueBean("1958", "1958"));
        expected.add(new LabelValueBean("1959", "1959"));
        expected.add(new LabelValueBean("1960", "1960"));
        expected.add(new LabelValueBean("1961", "1961"));
        expected.add(new LabelValueBean("1962", "1962"));
        expected.add(new LabelValueBean("1963", "1963"));
        expected.add(new LabelValueBean("1964", "1964"));
        expected.add(new LabelValueBean("1965", "1965"));
        expected.add(new LabelValueBean("1966", "1966"));
        expected.add(new LabelValueBean("1967", "1967"));
        expected.add(new LabelValueBean("1968", "1968"));
        expected.add(new LabelValueBean("1969", "1969"));
        expected.add(new LabelValueBean("1970", "1970"));

        AccountFormPartsModel sut = new AccountFormPartsModel() {
            long nowTimeMillis() {
                return 0;
            }
        };
        List<LabelValueBean> actual = sut.getBirthYearListWithEmptyValue();

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

    @Test
    public void testGetBirthMonthListWithEmptyValueToGetList() throws Exception {
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("選択してください", ""));
        expected.add(new LabelValueBean("1", "1"));
        expected.add(new LabelValueBean("2", "2"));
        expected.add(new LabelValueBean("3", "3"));
        expected.add(new LabelValueBean("4", "4"));
        expected.add(new LabelValueBean("5", "5"));
        expected.add(new LabelValueBean("6", "6"));
        expected.add(new LabelValueBean("7", "7"));
        expected.add(new LabelValueBean("8", "8"));
        expected.add(new LabelValueBean("9", "9"));
        expected.add(new LabelValueBean("10", "10"));
        expected.add(new LabelValueBean("11", "11"));
        expected.add(new LabelValueBean("12", "12"));

        AccountFormPartsModel sut = new AccountFormPartsModel();
        List<LabelValueBean> actual = sut.getBirthMonthListWithEmptyValue();

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

    @Test
    public void testGetBirthDayListWithEmptyValueToGetList() throws Exception {
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("選択してください", ""));
        expected.add(new LabelValueBean("1", "1"));
        expected.add(new LabelValueBean("2", "2"));
        expected.add(new LabelValueBean("3", "3"));
        expected.add(new LabelValueBean("4", "4"));
        expected.add(new LabelValueBean("5", "5"));
        expected.add(new LabelValueBean("6", "6"));
        expected.add(new LabelValueBean("7", "7"));
        expected.add(new LabelValueBean("8", "8"));
        expected.add(new LabelValueBean("9", "9"));
        expected.add(new LabelValueBean("10", "10"));
        expected.add(new LabelValueBean("11", "11"));
        expected.add(new LabelValueBean("12", "12"));
        expected.add(new LabelValueBean("13", "13"));
        expected.add(new LabelValueBean("14", "14"));
        expected.add(new LabelValueBean("15", "15"));
        expected.add(new LabelValueBean("16", "16"));
        expected.add(new LabelValueBean("17", "17"));
        expected.add(new LabelValueBean("18", "18"));
        expected.add(new LabelValueBean("19", "19"));
        expected.add(new LabelValueBean("20", "20"));
        expected.add(new LabelValueBean("21", "21"));
        expected.add(new LabelValueBean("22", "22"));
        expected.add(new LabelValueBean("23", "23"));
        expected.add(new LabelValueBean("24", "24"));
        expected.add(new LabelValueBean("25", "25"));
        expected.add(new LabelValueBean("26", "26"));
        expected.add(new LabelValueBean("27", "27"));
        expected.add(new LabelValueBean("28", "28"));
        expected.add(new LabelValueBean("29", "29"));
        expected.add(new LabelValueBean("30", "30"));
        expected.add(new LabelValueBean("31", "31"));

        AccountFormPartsModel sut = new AccountFormPartsModel();
        List<LabelValueBean> actual = sut.getBirthDayListWithEmptyValue();

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

    @Test
    public void testGetAccountIdListWithEmptyValueToGetList() throws Exception {
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("選択してください", ""));
        expected.add(new LabelValueBean("1", "1"));
        expected.add(new LabelValueBean("3", "3"));
        expected.add(new LabelValueBean("6", "6"));
        expected.add(new LabelValueBean("8", "8"));

        AccountFormPartsModel sut = new AccountFormPartsModel();
        List<LabelValueBean> actual = sut.getAccountIdListWithEmptyValue();

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

    @Test
    public void testGetSortKindForAccountMapToGetMap() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("byDescendingCreatedAt", "作成日が新しい順");
        expected.put("byAccendingCreatedAt", "作成日が古い順");
        expected.put("byDescendingUpdatedAt", "更新日が新しい順");
        expected.put("byAccendingUpdatedAt", "更新日が古い順");

        AccountFormPartsModel sut = new AccountFormPartsModel();
        Map<String, String> actual = sut.getSortKindForAccountMap();

        assertThat(actual, is(sameEntrySetAs(expected)));
    }

    @Test
    public void testGetSortKindForAccountListWithEmptyValueToGetList() {
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("選択する", ""));
        expected.add(new LabelValueBean("作成日が新しい順", "byDescendingCreatedAt"));
        expected.add(new LabelValueBean("作成日が古い順", "byAccendingCreatedAt"));
        expected.add(new LabelValueBean("更新日が新しい順", "byDescendingUpdatedAt"));
        expected.add(new LabelValueBean("更新日が古い順", "byAccendingUpdatedAt"));

        AccountFormPartsModel sut = new AccountFormPartsModel();
        List<LabelValueBean> actual = sut.getSortKindForAccountListWithEmptyValue();

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

}
