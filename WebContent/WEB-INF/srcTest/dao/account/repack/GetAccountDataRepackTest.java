package dao.account.repack;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.AccountExtendActionForm;
import dao.account.sql.AccountSelectJoinWhereActionForm;
import exception.NoDataExistException;
import test.db.DbUnitTester;

public class GetAccountDataRepackTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.GetAccountDataRepackTest.xml") {
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
    public void testGetAccountDataInCaseOneMatchedAccountExist() throws Exception {
        AccountExtendActionForm expected = new AccountExtendActionForm();
        expected.setAccountId(1);
        expected.setLastName("田中");
        expected.setFirstName("太郎");
        expected.setLastNameKana("タナカ");
        expected.setFirstNameKana("タロウ");
        expected.setSexId(2);
        expected.setSexName("女性");
        expected.setBirthDate(Timestamp.valueOf("1990-01-02 00:00:00"));
        expected.setPrefectureId(3);
        expected.setPrefectureName("静岡県");
        expected.setMailAddress("tanaka@example.com");
        expected.setProfileImageFileName("image.png");
        expected.setSelfIntroduction("自己紹介です。");
        expected.setAccountStatus("1");
        expected.setAccountCreatedAt(Timestamp.valueOf("2021-01-03 04:05:06.789"));
        expected.setAccountUpdatedAt(Timestamp.valueOf("2021-01-04 05:06:07.891"));
        expected.setBirthYear("1990");
        expected.setBirthMonth("01");
        expected.setBirthDay("02");
        expected.setProfileImageFilePath("/img/image.png");

        GetAccountDataRepack sut = new GetAccountDataRepack();
        AccountSelectJoinWhereActionForm whereForm = new AccountSelectJoinWhereActionForm();
        whereForm.setAccountId(1);
        AccountExtendActionForm actual = new AccountExtendActionForm();
        actual = sut.getAccountData(whereForm, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testGetAccountDataInCaseTwoMatchedAccountExist() throws Exception {
        AccountExtendActionForm expected = new AccountExtendActionForm();
        expected.setAccountId(3);
        expected.setLastName("畑中");
        expected.setFirstName("次郎");
        expected.setLastNameKana("タナカ");
        expected.setFirstNameKana("ジロウ");
        expected.setSexId(2);
        expected.setSexName("女性");
        expected.setBirthDate(Timestamp.valueOf("1992-03-04 00:00:00"));
        expected.setPrefectureId(4);
        expected.setPrefectureName("京都府");
        expected.setMailAddress("tanaka@test.com");
        expected.setProfileImageFileName(null);
        expected.setSelfIntroduction(null);
        expected.setAccountStatus("0");
        expected.setAccountCreatedAt(Timestamp.valueOf("2021-02-03 04:05:06.789"));
        expected.setAccountUpdatedAt(Timestamp.valueOf("2021-02-04 05:06:07.891"));
        expected.setBirthYear("1992");
        expected.setBirthMonth("03");
        expected.setBirthDay("04");
        expected.setProfileImageFilePath("");

        GetAccountDataRepack sut = new GetAccountDataRepack();
        AccountSelectJoinWhereActionForm whereForm = new AccountSelectJoinWhereActionForm();
        whereForm.setLastName("畑中");
        AccountExtendActionForm actual = new AccountExtendActionForm();
        actual = sut.getAccountData(whereForm, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testGetAccountDataInCaseOneMatchedAccountHavingNullColumnExist() throws Exception {
        AccountExtendActionForm expected = new AccountExtendActionForm();
        expected.setAccountId(2);
        expected.setLastName("山田");
        expected.setFirstName("花子");
        expected.setLastNameKana("ヤマダ");
        expected.setFirstNameKana("ハナコ");
        expected.setSexId(0);
        expected.setSexName(null);
        expected.setBirthDate(null);
        expected.setPrefectureId(2);
        expected.setPrefectureName("東京都");
        expected.setMailAddress("yamada@example.com");
        expected.setProfileImageFileName(null);
        expected.setSelfIntroduction(null);
        expected.setAccountStatus("1");
        expected.setAccountCreatedAt(Timestamp.valueOf("2021-01-05 06:07:08.912"));
        expected.setAccountUpdatedAt(Timestamp.valueOf("2021-01-06 07:08:09.123"));
        expected.setBirthYear("");
        expected.setBirthMonth("");
        expected.setBirthDay("");
        expected.setProfileImageFilePath("");

        GetAccountDataRepack sut = new GetAccountDataRepack();
        AccountSelectJoinWhereActionForm whereForm = new AccountSelectJoinWhereActionForm();
        whereForm.setAccountId(2);
        AccountExtendActionForm actual = new AccountExtendActionForm();
        actual = sut.getAccountData(whereForm, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test(expected = NoDataExistException.class)
    public void testGetAccountDataInCaseNoMatchedAccountExist() throws Exception {
        GetAccountDataRepack sut = new GetAccountDataRepack();
        AccountSelectJoinWhereActionForm whereForm = new AccountSelectJoinWhereActionForm();
        whereForm.setAccountId(4);
        AccountExtendActionForm actual = new AccountExtendActionForm();
        sut.getAccountData(whereForm, actual);
    }

}
