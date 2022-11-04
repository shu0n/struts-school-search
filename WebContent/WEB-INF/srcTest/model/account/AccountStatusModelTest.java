package model.account;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import exception.NoDataExistException;
import test.db.DbUnitTester;

public class AccountStatusModelTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.AccountStatusModelTest.xml") {
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
    public void testIsMailAddressExistInCaseMailAddressExistAtCreatingAccount() throws Exception {
        AccountStatusModel sut = new AccountStatusModel();
        boolean actual = sut.isMailAddressExist("test1@example.com", 0, true);

        assertThat(actual, is(true));
    }

    @Test
    public void testIsMailAddressExistInCaseNoMailAddressExistAtCreatingAccount() throws Exception {
        AccountStatusModel sut = new AccountStatusModel();
        boolean actual = sut.isMailAddressExist("test11@example.com", 0, true);

        assertThat(actual, is(false));
    }

    @Test
    public void testIsMailAddressExistInCaseOtherAccountHaveMailAddressAtEditingAccount() throws Exception {
        AccountStatusModel sut = new AccountStatusModel();
        boolean actual = sut.isMailAddressExist("test2@example.com", 4, false);

        assertThat(actual, is(true));
    }

    @Test
    public void testIsMailAddressExistInCaseSelfAccountHaveMailAddressAtEditingAccount() throws Exception {
        AccountStatusModel sut = new AccountStatusModel();
        boolean actual = sut.isMailAddressExist("test3@example.com", 4, false);

        assertThat(actual, is(false));
    }

    @Test
    public void testIsMailAddressExistInCaseNoAccountHaveMailAddressAtEditingAccount() throws Exception {
        AccountStatusModel sut = new AccountStatusModel();
        boolean actual = sut.isMailAddressExist("test12@example.com", 4, false);

        assertThat(actual, is(false));
    }

    @Test
    public void testActivateAccountToActivateAccountSuccessfully() throws Exception {
        int expected = 5;

        AccountStatusModel sut = new AccountStatusModel() {
            Timestamp getCurrentTimestamp() {
                return Timestamp.valueOf("2022-01-02 03:04:05.677");
            }
        };
        int actual = sut.activateAccount("test4@example.com", "activateToken123");

        assertThat(actual, is(expected));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.AccountStatusModelTest.testActivateAccountToActivateAccountSuccessfully.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("accounts");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "accounts";
        String sqlQuery = "SELECT * FROM accounts where account_id=5";
        String[] ignoreCols =
                {"sex_id", "birth_date", "profile_image_file_name", "self_introduction",
                "reissue_token", "reissue_effective_date",
                "account_created_at", "account_updated_at", "account_delete_flag", "account_deleted_at"};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test(expected = NoDataExistException.class)
    public void testActivateAccountInCaseNoAccountExist() throws Exception {
        AccountStatusModel sut = new AccountStatusModel();
        sut.activateAccount("test5@example.com", "activateToken123");
    }

    @Test(expected = NoDataExistException.class)
    public void testActivateAccountInCaseActivateTokenIsExpired() throws Exception {
        AccountStatusModel sut = new AccountStatusModel() {
            Timestamp getCurrentTimestamp() {
                return Timestamp.valueOf("2022-01-04 05:06:07.892");
            }
        };
        sut.activateAccount("test6@example.com", "activateToken789");
    }

    @Test(expected = NoDataExistException.class)
    public void testActivateAccountInCaseAccountStatusIsValid() throws Exception {
        AccountStatusModel sut = new AccountStatusModel() {
            Timestamp getCurrentTimestamp() {
                return Timestamp.valueOf("2022-01-05 06:07:08.911");
            }
        };
        sut.activateAccount("test7@example.com", "activateToken123456");
    }

    @Test
    public void testIsAccountEnableInCaseAccountIsValid() throws Exception {
        AccountStatusModel sut = new AccountStatusModel();
        boolean actual = sut.isAccountEnable(9);

        assertThat(actual, is(true));
    }

    @Test
    public void testIsAccountEnableInCaseAccountIsInvalid() throws Exception {
        AccountStatusModel sut = new AccountStatusModel();
        boolean actual = sut.isAccountEnable(10);

        assertThat(actual, is(false));
    }

}
