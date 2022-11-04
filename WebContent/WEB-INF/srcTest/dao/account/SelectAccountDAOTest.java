package dao.account;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.AccountActionForm;
import dao.account.sql.AccountSelectWhereActionForm;
import test.db.DbUnitTester;

public class SelectAccountDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SelectAccountDAOTest.xml") {
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
    public void testSelectMatchedAccountToGetAllAccount() throws Exception {
        String[] expected = {"1", "2", "3", "4", "5"};

        SelectAccountDAO sut = new SelectAccountDAO();
        AccountSelectWhereActionForm whereForm = new AccountSelectWhereActionForm();
        List<AccountActionForm> formList = sut.selectMatchedAccount(whereForm);
        String[] actual = new String[5];
        for(int i = 0; i < formList.size(); i++) {
            actual[i] = String.valueOf(formList.get(i).getAccountId());
        }

        assertThat(actual, is(arrayContaining(expected)));
    }

    @Test
    public void testSelectMatchedAccountToGetOneAccountByAccountId() throws Exception {
        AccountActionForm expected = new AccountActionForm();
        expected.setAccountId(4);
        expected.setLastName("高橋");
        expected.setFirstName("京子");
        expected.setLastNameKana("タカハシ");
        expected.setFirstNameKana("キョウコ");
        expected.setSexId(2);
        expected.setBirthDate(Timestamp.valueOf("1993-01-07 00:00:00"));
        expected.setPrefectureId(3);
        expected.setMailAddress("takahashi@test.com");
        expected.setProfileImageFileName("image.png");
        expected.setSelfIntroduction("自己紹介です。");
        expected.setAccountStatus("1");
        expected.setActivateToken("bctivatetoken");
        expected.setActivateEffectiveDate(Timestamp.valueOf("2022-10-11 12:13:14.567"));
        expected.setReissueToken("reissuetoken");
        expected.setReissueEffctiveDate(Timestamp.valueOf("2023-02-03 04:05:06.789"));
        expected.setAccountCreatedAt(Timestamp.valueOf("2022-01-07 08:09:10.123"));
        expected.setAccountUpdatedAt(Timestamp.valueOf("2022-01-08 09:10:11.234"));

        SelectAccountDAO sut = new SelectAccountDAO();
        AccountSelectWhereActionForm whereForm = new AccountSelectWhereActionForm();
        whereForm.setAccountId(4);
        AccountActionForm actual = sut.selectMatchedAccount(whereForm).get(0);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testSelectMatchedAccountToGetFourAccountByAccountStatus() throws Exception {
        int expected = 4;

        SelectAccountDAO sut = new SelectAccountDAO();
        AccountSelectWhereActionForm whereForm = new AccountSelectWhereActionForm();
        whereForm.setAccountStatus("1");
        List<AccountActionForm> actual = sut.selectMatchedAccount(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedAccountToGetOneAccountByMultipleCondition() throws Exception {
        int expected = 1;

        SelectAccountDAO sut = new SelectAccountDAO();
        AccountSelectWhereActionForm whereForm = new AccountSelectWhereActionForm();
        whereForm.setLastName("佐藤");
        whereForm.setFirstName("次郎");
        whereForm.setLastNameKana("サトウ");
        whereForm.setFirstNameKana("ジロウ");
        whereForm.setMailAddress("satou@example.com");
        whereForm.setPassword("pcssword");
        whereForm.setAccountStatus("0");
        whereForm.setActivateToken("activatetoken");
        whereForm.setReissueToken("reissuetoken");
        List<AccountActionForm> actual = sut.selectMatchedAccount(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedAccountToGetNoAccountByMultipleCondition() throws Exception {
        int expected = 0;

        SelectAccountDAO sut = new SelectAccountDAO();
        AccountSelectWhereActionForm whereForm = new AccountSelectWhereActionForm();
        whereForm.setLastName("高橋");
        whereForm.setFirstName("次郎");
        whereForm.setLastNameKana("サトウ");
        whereForm.setFirstNameKana("ジロウ");
        whereForm.setMailAddress("satou@example.com");
        whereForm.setPassword("pcssword");
        whereForm.setAccountStatus("0");
        whereForm.setActivateToken("activatetoken");
        List<AccountActionForm> actual = sut.selectMatchedAccount(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedAccountToGetTwoAccountByLikeLastNameCondition() throws Exception {
        int expected = 2;

        SelectAccountDAO sut = new SelectAccountDAO();
        AccountSelectWhereActionForm whereForm = new AccountSelectWhereActionForm();
        whereForm.setLikeLastName("橋");
        List<AccountActionForm> actual = sut.selectMatchedAccount(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedAccountToGetThreeAccountByLikeLastFirstNameCondition() throws Exception {
        int expected = 3;

        SelectAccountDAO sut = new SelectAccountDAO();
        AccountSelectWhereActionForm whereForm = new AccountSelectWhereActionForm();
        whereForm.setLikeFirstName("郎");
        List<AccountActionForm> actual = sut.selectMatchedAccount(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedAccountToGetOneAccountByLikeLastLastNameKanaCondition() throws Exception {
        int expected = 1;

        SelectAccountDAO sut = new SelectAccountDAO();
        AccountSelectWhereActionForm whereForm = new AccountSelectWhereActionForm();
        whereForm.setLikeLastNameKana("バシ");
        List<AccountActionForm> actual = sut.selectMatchedAccount(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedAccountToGetThreeAccountByLikeLastFirstNameKanaCondition() throws Exception {
        int expected = 3;

        SelectAccountDAO sut = new SelectAccountDAO();
        AccountSelectWhereActionForm whereForm = new AccountSelectWhereActionForm();
        whereForm.setLikeFirstNameKana("ロウ");
        List<AccountActionForm> actual = sut.selectMatchedAccount(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedAccountToGetTwoAccountByLikeMailAddress() throws Exception {
        int expected = 2;

        SelectAccountDAO sut = new SelectAccountDAO();
        AccountSelectWhereActionForm whereForm = new AccountSelectWhereActionForm();
        whereForm.setLikeMailAddress("test");
        List<AccountActionForm> actual = sut.selectMatchedAccount(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedAccountToGetThreeAccountByRangeOfBirthDate() throws Exception {
        int expected = 3;

        SelectAccountDAO sut = new SelectAccountDAO();
        AccountSelectWhereActionForm whereForm = new AccountSelectWhereActionForm();
        whereForm.setFromBirthDate("1991-03-01 00:00:00.00");
        whereForm.setToBirthDate("1993-05-31 23:59:59.999");
        List<AccountActionForm> actual = sut.selectMatchedAccount(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedAccountToGetTwoAccountByRangeOfCreatedDateAndUpdatedDate() throws Exception {
        int expected = 2;

        SelectAccountDAO sut = new SelectAccountDAO();
        AccountSelectWhereActionForm whereForm = new AccountSelectWhereActionForm();
        whereForm.setFromAccountCreatedDate("2021-01-01 00:00:00.000");
        whereForm.setToAccountCreatedDate("2021-01-04 00:00:00.000");
        whereForm.setFromAccountUpdatedDate("2021-01-02 00:00:00.000");
        whereForm.setToAccountUpdatedDate("2021-01-05 00:00:00.000");
        List<AccountActionForm> actual = sut.selectMatchedAccount(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedAccountToGetOneAccountByArrayOfAccountIdAndSexIdAndPrefectureIdAndAccountStatus()
            throws Exception {
        int expected = 1;

        SelectAccountDAO sut = new SelectAccountDAO();
        AccountSelectWhereActionForm whereForm = new AccountSelectWhereActionForm();
        String[] accountIdArray = {"2", "3", "4", "6"};
        whereForm.setAccountIdArray(accountIdArray);
        String[] sexIdArray = {"1" ,"2"};
        whereForm.setSexIdArray(sexIdArray);
        String[] prefectureIdArray = {"3", "4"};
        whereForm.setPrefectureIdArray(prefectureIdArray);
        String[] accountStatusArray = {"0"};
        whereForm.setAccountStatusArray(accountStatusArray);
        List<AccountActionForm> actual = sut.selectMatchedAccount(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedAccountToGetFiveAccountByArrayOfAccountStatus() throws Exception {
        int expected = 5;

        SelectAccountDAO sut = new SelectAccountDAO();
        AccountSelectWhereActionForm whereForm = new AccountSelectWhereActionForm();
        String[] accountStatusArray = {"0", "1"};
        whereForm.setAccountStatusArray(accountStatusArray);
        List<AccountActionForm> actual = sut.selectMatchedAccount(whereForm);

        assertThat(actual.size(), is(expected));
    }

}
