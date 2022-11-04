package model.account;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.AccountExtendActionForm;
import test.db.DbUnitTester;

public class AccountListModelTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.AccountListModelTest.xml") {
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
    public void testSetListAccountDataToSetData() throws Exception {
        List<AccountExtendActionForm> expected = new ArrayList<>();
        AccountExtendActionForm expectedForm1 = new AccountExtendActionForm();
        expectedForm1.setAccountId(1);
        expectedForm1.setSexId(2);
        expectedForm1.setSexName("女性");
        expectedForm1.setBirthDate(Timestamp.valueOf("1990-01-02 00:00:00"));
        expectedForm1.setBirthYear("1990");
        expectedForm1.setBirthMonth("01");
        expectedForm1.setBirthDay("02");
        expectedForm1.setStrBirthDate("1990/01/02");
        expectedForm1.setAccountStatus("0");
        expectedForm1.setAccountStatusName("無効");
        expectedForm1.setAccountCreatedAt(Timestamp.valueOf("2022-02-03 04:04:06"));
        expectedForm1.setStrAccountCreatedAt("2022/02/03 04:04:06");
        expectedForm1.setAccountUpdatedAt(Timestamp.valueOf("2022-03-04 05:06:07"));
        expectedForm1.setStrAccountUpdatedAt("2022/03/04 05:06:07");
        expected.add(expectedForm1);
        AccountExtendActionForm expectedForm2 = new AccountExtendActionForm();
        expectedForm2.setAccountId(2);
        expectedForm2.setSexId(3);
        expectedForm2.setSexName("その他");
        expectedForm2.setBirthDate(Timestamp.valueOf("1991-04-05 00:00:00"));
        expectedForm2.setBirthYear("1991");
        expectedForm2.setBirthMonth("04");
        expectedForm2.setBirthDay("05");
        expectedForm2.setStrBirthDate("1991/04/05");
        expectedForm2.setAccountStatus("1");
        expectedForm2.setAccountStatusName("有効");
        expectedForm2.setAccountCreatedAt(Timestamp.valueOf("2022-02-04 05:06:07"));
        expectedForm2.setStrAccountCreatedAt("2022/02/04 05:06:07");
        expectedForm2.setAccountUpdatedAt(Timestamp.valueOf("2022-03-05 06:07:08"));
        expectedForm2.setStrAccountUpdatedAt("2022/03/05 06:07:08");
        expected.add(expectedForm2);
        AccountExtendActionForm expectedForm3 = new AccountExtendActionForm();
        expectedForm3.setAccountId(3);
        expectedForm3.setSexId(1);
        expectedForm3.setSexName("男性");
        expectedForm3.setBirthDate(Timestamp.valueOf("1992-07-08 00:00:00"));
        expectedForm3.setBirthYear("1992");
        expectedForm3.setBirthMonth("07");
        expectedForm3.setBirthDay("08");
        expectedForm3.setStrBirthDate("1992/07/08");
        expectedForm3.setAccountStatus("0");
        expectedForm3.setAccountStatusName("無効");
        expectedForm3.setAccountCreatedAt(Timestamp.valueOf("2022-02-05 06:07:08"));
        expectedForm3.setStrAccountCreatedAt("2022/02/05 06:07:08");
        expectedForm3.setAccountUpdatedAt(Timestamp.valueOf("2022-03-06 07:08:09"));
        expectedForm3.setStrAccountUpdatedAt("2022/03/06 07:08:09");
        expected.add(expectedForm3);
        AccountExtendActionForm expectedForm4 = new AccountExtendActionForm();
        expectedForm4.setAccountId(4);
        expectedForm4.setSexId(0);
        expectedForm4.setSexName("");
        expectedForm4.setBirthDate(Timestamp.valueOf("1990-10-11 00:00:00"));
        expectedForm4.setBirthYear("1990");
        expectedForm4.setBirthMonth("10");
        expectedForm4.setBirthDay("11");
        expectedForm4.setStrBirthDate("1990/10/11");
        expectedForm4.setAccountStatus("1");
        expectedForm4.setAccountStatusName("有効");
        expectedForm4.setAccountCreatedAt(Timestamp.valueOf("2022-02-06 07:08:09"));
        expectedForm4.setStrAccountCreatedAt("2022/02/06 07:08:09");
        expectedForm4.setAccountUpdatedAt(Timestamp.valueOf("2022-03-07 08:09:10"));
        expectedForm4.setStrAccountUpdatedAt("2022/03/07 08:09:10");
        expected.add(expectedForm4);

        AccountListModel sut = new AccountListModel();
        List<AccountExtendActionForm> actual = new ArrayList<>();
        AccountExtendActionForm actualForm1 = new AccountExtendActionForm();
        actualForm1.setAccountId(1);
        actualForm1.setSexId(2);
        actualForm1.setBirthDate(Timestamp.valueOf("1990-01-02 00:00:00"));
        actualForm1.setAccountStatus("0");
        actualForm1.setAccountCreatedAt(Timestamp.valueOf("2022-02-03 04:04:06"));
        actualForm1.setAccountUpdatedAt(Timestamp.valueOf("2022-03-04 05:06:07"));
        actual.add(actualForm1);
        AccountExtendActionForm actualForm2 = new AccountExtendActionForm();
        actualForm2.setAccountId(2);
        actualForm2.setSexId(3);
        actualForm2.setBirthDate(Timestamp.valueOf("1991-04-05 00:00:00"));
        actualForm2.setAccountStatus("1");
        actualForm2.setAccountCreatedAt(Timestamp.valueOf("2022-02-04 05:06:07"));
        actualForm2.setAccountUpdatedAt(Timestamp.valueOf("2022-03-05 06:07:08"));
        actual.add(actualForm2);
        AccountExtendActionForm actualForm3 = new AccountExtendActionForm();
        actualForm3.setAccountId(3);
        actualForm3.setSexId(1);
        actualForm3.setBirthDate(Timestamp.valueOf("1992-07-08 00:00:00"));
        actualForm3.setAccountStatus("0");
        actualForm3.setAccountCreatedAt(Timestamp.valueOf("2022-02-05 06:07:08"));
        actualForm3.setAccountUpdatedAt(Timestamp.valueOf("2022-03-06 07:08:09"));
        actual.add(actualForm3);
        AccountExtendActionForm actualForm4 = new AccountExtendActionForm();
        actualForm4.setAccountId(4);
        actualForm4.setSexId(0);
        actualForm4.setBirthDate(Timestamp.valueOf("1990-10-11 00:00:00"));
        actualForm4.setAccountStatus("1");
        actualForm4.setAccountCreatedAt(Timestamp.valueOf("2022-02-06 07:08:09"));
        actualForm4.setAccountUpdatedAt(Timestamp.valueOf("2022-03-07 08:09:10"));
        actual.add(actualForm4);
        actual = sut.setListAccountData(actual);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortAccountExtendFormListToSortByDescendingCreatedAt() {
        List<AccountExtendActionForm> expected = new ArrayList<>();
        AccountExtendActionForm expectedForm1 = new AccountExtendActionForm();
        expectedForm1.setAccountId(4);
        expectedForm1.setAccountCreatedAt(Timestamp.valueOf("2022-03-07 08:09:10"));
        expected.add(expectedForm1);
        AccountExtendActionForm expectedForm2 = new AccountExtendActionForm();
        expectedForm2.setAccountId(1);
        expectedForm2.setAccountCreatedAt(Timestamp.valueOf("2022-03-06 07:08:09"));
        expected.add(expectedForm2);
        AccountExtendActionForm expectedForm3 = new AccountExtendActionForm();
        expectedForm3.setAccountId(3);
        expectedForm3.setAccountCreatedAt(Timestamp.valueOf("2022-03-05 06:07:08"));
        expected.add(expectedForm3);
        AccountExtendActionForm expectedForm4 = new AccountExtendActionForm();
        expectedForm4.setAccountId(2);
        expectedForm4.setAccountCreatedAt(Timestamp.valueOf("2022-03-04 05:06:07"));
        expected.add(expectedForm4);

        AccountListModel sut = new AccountListModel();
        List<AccountExtendActionForm> actual = new ArrayList<>();
        AccountExtendActionForm actualForm1 = new AccountExtendActionForm();
        actualForm1.setAccountId(1);
        actualForm1.setAccountCreatedAt(Timestamp.valueOf("2022-03-06 07:08:09"));
        actual.add(actualForm1);
        AccountExtendActionForm actualForm2 = new AccountExtendActionForm();
        actualForm2.setAccountId(2);
        actualForm2.setAccountCreatedAt(Timestamp.valueOf("2022-03-04 05:06:07"));
        actual.add(actualForm2);
        AccountExtendActionForm actualForm3 = new AccountExtendActionForm();
        actualForm3.setAccountId(3);
        actualForm3.setAccountCreatedAt(Timestamp.valueOf("2022-03-05 06:07:08"));
        actual.add(actualForm3);
        AccountExtendActionForm actualForm4 = new AccountExtendActionForm();
        actualForm4.setAccountId(4);
        actualForm4.setAccountCreatedAt(Timestamp.valueOf("2022-03-07 08:09:10"));
        actual.add(actualForm4);
        actual = sut.sortAccountExtendFormList(actual, "byDescendingCreatedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortAccountExtendFormListToSortByAccendingCreatedAt() {
        List<AccountExtendActionForm> expected = new ArrayList<>();
        AccountExtendActionForm expectedForm1 = new AccountExtendActionForm();
        expectedForm1.setAccountId(4);
        expectedForm1.setAccountCreatedAt(Timestamp.valueOf("2022-03-04 05:06:07"));
        expected.add(expectedForm1);
        AccountExtendActionForm expectedForm2 = new AccountExtendActionForm();
        expectedForm2.setAccountId(1);
        expectedForm2.setAccountCreatedAt(Timestamp.valueOf("2022-03-05 06:07:08"));
        expected.add(expectedForm2);
        AccountExtendActionForm expectedForm3 = new AccountExtendActionForm();
        expectedForm3.setAccountId(3);
        expectedForm3.setAccountCreatedAt(Timestamp.valueOf("2022-03-06 07:08:09"));
        expected.add(expectedForm3);
        AccountExtendActionForm expectedForm4 = new AccountExtendActionForm();
        expectedForm4.setAccountId(2);
        expectedForm4.setAccountCreatedAt(Timestamp.valueOf("2022-03-07 08:09:10"));
        expected.add(expectedForm4);

        AccountListModel sut = new AccountListModel();
        List<AccountExtendActionForm> actual = new ArrayList<>();
        AccountExtendActionForm actualForm1 = new AccountExtendActionForm();
        actualForm1.setAccountId(1);
        actualForm1.setAccountCreatedAt(Timestamp.valueOf("2022-03-05 06:07:08"));
        actual.add(actualForm1);
        AccountExtendActionForm actualForm2 = new AccountExtendActionForm();
        actualForm2.setAccountId(2);
        actualForm2.setAccountCreatedAt(Timestamp.valueOf("2022-03-07 08:09:10"));
        actual.add(actualForm2);
        AccountExtendActionForm actualForm3 = new AccountExtendActionForm();
        actualForm3.setAccountId(3);
        actualForm3.setAccountCreatedAt(Timestamp.valueOf("2022-03-06 07:08:09"));
        actual.add(actualForm3);
        AccountExtendActionForm actualForm4 = new AccountExtendActionForm();
        actualForm4.setAccountId(4);
        actualForm4.setAccountCreatedAt(Timestamp.valueOf("2022-03-04 05:06:07"));
        actual.add(actualForm4);
        actual = sut.sortAccountExtendFormList(actual, "byAccendingCreatedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortAccountExtendFormListToSortByDescendingUpdatedAt() {
        List<AccountExtendActionForm> expected = new ArrayList<>();
        AccountExtendActionForm expectedForm1 = new AccountExtendActionForm();
        expectedForm1.setAccountId(4);
        expectedForm1.setAccountUpdatedAt(Timestamp.valueOf("2022-03-07 08:09:10"));
        expected.add(expectedForm1);
        AccountExtendActionForm expectedForm2 = new AccountExtendActionForm();
        expectedForm2.setAccountId(1);
        expectedForm2.setAccountUpdatedAt(Timestamp.valueOf("2022-03-06 07:08:09"));
        expected.add(expectedForm2);
        AccountExtendActionForm expectedForm3 = new AccountExtendActionForm();
        expectedForm3.setAccountId(3);
        expectedForm3.setAccountUpdatedAt(Timestamp.valueOf("2022-03-05 06:07:08"));
        expected.add(expectedForm3);
        AccountExtendActionForm expectedForm4 = new AccountExtendActionForm();
        expectedForm4.setAccountId(2);
        expectedForm4.setAccountUpdatedAt(Timestamp.valueOf("2022-03-04 05:06:07"));
        expected.add(expectedForm4);

        AccountListModel sut = new AccountListModel();
        List<AccountExtendActionForm> actual = new ArrayList<>();
        AccountExtendActionForm actualForm1 = new AccountExtendActionForm();
        actualForm1.setAccountId(1);
        actualForm1.setAccountUpdatedAt(Timestamp.valueOf("2022-03-06 07:08:09"));
        actual.add(actualForm1);
        AccountExtendActionForm actualForm2 = new AccountExtendActionForm();
        actualForm2.setAccountId(2);
        actualForm2.setAccountUpdatedAt(Timestamp.valueOf("2022-03-04 05:06:07"));
        actual.add(actualForm2);
        AccountExtendActionForm actualForm3 = new AccountExtendActionForm();
        actualForm3.setAccountId(3);
        actualForm3.setAccountUpdatedAt(Timestamp.valueOf("2022-03-05 06:07:08"));
        actual.add(actualForm3);
        AccountExtendActionForm actualForm4 = new AccountExtendActionForm();
        actualForm4.setAccountId(4);
        actualForm4.setAccountUpdatedAt(Timestamp.valueOf("2022-03-07 08:09:10"));
        actual.add(actualForm4);
        actual = sut.sortAccountExtendFormList(actual, "byDescendingUpdatedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortAccountExtendFormListToSortByAccendingUpdatedAt() {
        List<AccountExtendActionForm> expected = new ArrayList<>();
        AccountExtendActionForm expectedForm1 = new AccountExtendActionForm();
        expectedForm1.setAccountId(4);
        expectedForm1.setAccountUpdatedAt(Timestamp.valueOf("2022-03-04 05:06:07"));
        expected.add(expectedForm1);
        AccountExtendActionForm expectedForm2 = new AccountExtendActionForm();
        expectedForm2.setAccountId(1);
        expectedForm2.setAccountUpdatedAt(Timestamp.valueOf("2022-03-05 06:07:08"));
        expected.add(expectedForm2);
        AccountExtendActionForm expectedForm3 = new AccountExtendActionForm();
        expectedForm3.setAccountId(3);
        expectedForm3.setAccountUpdatedAt(Timestamp.valueOf("2022-03-06 07:08:09"));
        expected.add(expectedForm3);
        AccountExtendActionForm expectedForm4 = new AccountExtendActionForm();
        expectedForm4.setAccountId(2);
        expectedForm4.setAccountUpdatedAt(Timestamp.valueOf("2022-03-07 08:09:10"));
        expected.add(expectedForm4);

        AccountListModel sut = new AccountListModel();
        List<AccountExtendActionForm> actual = new ArrayList<>();
        AccountExtendActionForm actualForm1 = new AccountExtendActionForm();
        actualForm1.setAccountId(1);
        actualForm1.setAccountUpdatedAt(Timestamp.valueOf("2022-03-05 06:07:08"));
        actual.add(actualForm1);
        AccountExtendActionForm actualForm2 = new AccountExtendActionForm();
        actualForm2.setAccountId(2);
        actualForm2.setAccountUpdatedAt(Timestamp.valueOf("2022-03-07 08:09:10"));
        actual.add(actualForm2);
        AccountExtendActionForm actualForm3 = new AccountExtendActionForm();
        actualForm3.setAccountId(3);
        actualForm3.setAccountUpdatedAt(Timestamp.valueOf("2022-03-06 07:08:09"));
        actual.add(actualForm3);
        AccountExtendActionForm actualForm4 = new AccountExtendActionForm();
        actualForm4.setAccountId(4);
        actualForm4.setAccountUpdatedAt(Timestamp.valueOf("2022-03-04 05:06:07"));
        actual.add(actualForm4);
        actual = sut.sortAccountExtendFormList(actual, "byAccendingUpdatedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedAccountListInCaseCurrentPageIsFirst() {
        List<AccountExtendActionForm> expected = new ArrayList<>();
        AccountExtendActionForm expectedForm1 = new AccountExtendActionForm();
        expectedForm1.setAccountId(1);
        expected.add(expectedForm1);
        AccountExtendActionForm expectedForm2 = new AccountExtendActionForm();
        expectedForm2.setAccountId(2);
        expected.add(expectedForm2);
        AccountExtendActionForm expectedForm3 = new AccountExtendActionForm();
        expectedForm3.setAccountId(3);
        expected.add(expectedForm3);
        AccountExtendActionForm expectedForm4 = new AccountExtendActionForm();
        expectedForm4.setAccountId(4);
        expected.add(expectedForm4);
        AccountExtendActionForm expectedForm5 = new AccountExtendActionForm();
        expectedForm5.setAccountId(5);
        expected.add(expectedForm5);
        AccountExtendActionForm expectedForm6 = new AccountExtendActionForm();
        expectedForm6.setAccountId(6);
        expected.add(expectedForm6);
        AccountExtendActionForm expectedForm7 = new AccountExtendActionForm();
        expectedForm7.setAccountId(7);
        expected.add(expectedForm7);
        AccountExtendActionForm expectedForm8 = new AccountExtendActionForm();
        expectedForm8.setAccountId(8);
        expected.add(expectedForm8);
        AccountExtendActionForm expectedForm9 = new AccountExtendActionForm();
        expectedForm9.setAccountId(9);
        expected.add(expectedForm9);
        AccountExtendActionForm expectedForm10 = new AccountExtendActionForm();
        expectedForm10.setAccountId(10);
        expected.add(expectedForm10);

        AccountListModel sut = new AccountListModel();
        List<AccountExtendActionForm> actual = new ArrayList<>();
        AccountExtendActionForm actualForm1 = new AccountExtendActionForm();
        actualForm1.setAccountId(1);
        actual.add(actualForm1);
        AccountExtendActionForm actualForm2 = new AccountExtendActionForm();
        actualForm2.setAccountId(2);
        actual.add(actualForm2);
        AccountExtendActionForm actualForm3 = new AccountExtendActionForm();
        actualForm3.setAccountId(3);
        actual.add(actualForm3);
        AccountExtendActionForm actualForm4 = new AccountExtendActionForm();
        actualForm4.setAccountId(4);
        actual.add(actualForm4);
        AccountExtendActionForm actualForm5 = new AccountExtendActionForm();
        actualForm5.setAccountId(5);
        actual.add(actualForm5);
        AccountExtendActionForm actualForm6 = new AccountExtendActionForm();
        actualForm6.setAccountId(6);
        actual.add(actualForm6);
        AccountExtendActionForm actualForm7 = new AccountExtendActionForm();
        actualForm7.setAccountId(7);
        actual.add(actualForm7);
        AccountExtendActionForm actualForm8 = new AccountExtendActionForm();
        actualForm8.setAccountId(8);
        actual.add(actualForm8);
        AccountExtendActionForm actualForm9 = new AccountExtendActionForm();
        actualForm9.setAccountId(9);
        actual.add(actualForm9);
        AccountExtendActionForm actualForm10 = new AccountExtendActionForm();
        actualForm10.setAccountId(10);
        actual.add(actualForm10);
        AccountExtendActionForm actualForm11 = new AccountExtendActionForm();
        actualForm11.setAccountId(11);
        actual.add(actualForm11);
        actual = sut.makeDisplayedAccountList(actual, 1, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedAccountListInCaseCurrentPageIsSecond() {
        List<AccountExtendActionForm> expected = new ArrayList<>();
        AccountExtendActionForm expectedForm11 = new AccountExtendActionForm();
        expectedForm11.setAccountId(11);
        expected.add(expectedForm11);
        AccountExtendActionForm expectedForm12 = new AccountExtendActionForm();
        expectedForm12.setAccountId(12);
        expected.add(expectedForm12);
        AccountExtendActionForm expectedForm13 = new AccountExtendActionForm();
        expectedForm13.setAccountId(13);
        expected.add(expectedForm13);

        AccountListModel sut = new AccountListModel();
        List<AccountExtendActionForm> actual = new ArrayList<>();
        AccountExtendActionForm actualForm1 = new AccountExtendActionForm();
        actualForm1.setAccountId(1);
        actual.add(actualForm1);
        AccountExtendActionForm actualForm2 = new AccountExtendActionForm();
        actualForm2.setAccountId(2);
        actual.add(actualForm2);
        AccountExtendActionForm actualForm3 = new AccountExtendActionForm();
        actualForm3.setAccountId(3);
        actual.add(actualForm3);
        AccountExtendActionForm actualForm4 = new AccountExtendActionForm();
        actualForm4.setAccountId(4);
        actual.add(actualForm4);
        AccountExtendActionForm actualForm5 = new AccountExtendActionForm();
        actualForm5.setAccountId(5);
        actual.add(actualForm5);
        AccountExtendActionForm actualForm6 = new AccountExtendActionForm();
        actualForm6.setAccountId(6);
        actual.add(actualForm6);
        AccountExtendActionForm actualForm7 = new AccountExtendActionForm();
        actualForm7.setAccountId(7);
        actual.add(actualForm7);
        AccountExtendActionForm actualForm8 = new AccountExtendActionForm();
        actualForm8.setAccountId(8);
        actual.add(actualForm8);
        AccountExtendActionForm actualForm9 = new AccountExtendActionForm();
        actualForm9.setAccountId(9);
        actual.add(actualForm9);
        AccountExtendActionForm actualForm10 = new AccountExtendActionForm();
        actualForm10.setAccountId(10);
        actual.add(actualForm10);
        AccountExtendActionForm actualForm11 = new AccountExtendActionForm();
        actualForm11.setAccountId(11);
        actual.add(actualForm11);
        AccountExtendActionForm actualForm12 = new AccountExtendActionForm();
        actualForm12.setAccountId(12);
        actual.add(actualForm12);
        AccountExtendActionForm actualForm13 = new AccountExtendActionForm();
        actualForm13.setAccountId(13);
        actual.add(actualForm13);
        actual = sut.makeDisplayedAccountList(actual, 2, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedAccountListInCaseCurrentPageIsSecondAndEmpty() {
        List<AccountExtendActionForm> expected = new ArrayList<>();

        AccountListModel sut = new AccountListModel();
        List<AccountExtendActionForm> actual = new ArrayList<>();
        AccountExtendActionForm actualForm1 = new AccountExtendActionForm();
        actualForm1.setAccountId(1);
        actual.add(actualForm1);
        AccountExtendActionForm actualForm2 = new AccountExtendActionForm();
        actualForm2.setAccountId(2);
        actual.add(actualForm2);
        AccountExtendActionForm actualForm3 = new AccountExtendActionForm();
        actualForm3.setAccountId(3);
        actual.add(actualForm3);
        actual = sut.makeDisplayedAccountList(actual, 2, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

}
