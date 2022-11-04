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

public class AccountPasswordModelTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.AccountPasswordModelTest.xml") {
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
    public void testChangeAccountPasswordToChangPasswordSuccessfully() throws Exception {
        AccountPasswordModel sut = new AccountPasswordModel();
        boolean actual = sut.changeAccountPassword(1, "password", "newPassword");

        assertThat(actual, is(true));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.AccountPasswordModelTest.testChangeAccountPasswordToChangePassword.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("accounts");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "accounts";
        String sqlQuery = "SELECT * FROM accounts where account_id=1";
        String[] ignoreCols =
                {"sex_id", "birth_date", "profile_image_file_name", "self_introduction",
                "activate_token", "activate_effective_date", "reissue_token", "reissue_effective_date",
                "account_created_at", "account_updated_at", "account_delete_flag", "account_deleted_at"};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test(expected = NoDataExistException.class)
    public void testChangeAccountPasswordInCaseNoMatchedAccountExist() throws Exception {
        AccountPasswordModel sut = new AccountPasswordModel();
        sut.changeAccountPassword(2, "password", "newPassword");
    }

    @Test(expected = NoDataExistException.class)
    public void testChangeAccountPasswordInCaseInvalidPassword() throws Exception {
        AccountPasswordModel sut = new AccountPasswordModel();
        sut.changeAccountPassword(3, "pbssword", "newPassword");
    }

    @Test
    public void testSetReissueInfoToSetInfoSuccessfully() throws Exception {
        String expected = "f4GAUQxFOPX73-uftk2HMUIUVo6uF2DKxuCsC1r9mEtDPYqq-InA8oCulnnZ5EwL";

        AccountPasswordModel sut = new AccountPasswordModel() {
            String getToken(String mailAddress) {
                return "f4GAUQxFOPX73-uftk2HMUIUVo6uF2DKxuCsC1r9mEtDPYqq-InA8oCulnnZ5EwL";
            }

            Timestamp getAddedTimestamp(long effectiveTime) {
                return Timestamp.valueOf("2022-01-02 03:04:05.678");
            }
        };
        String actual = sut.setReissueInfo("test2@example.com");

        assertThat(actual, is(expected));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.AccountPasswordModelTest.testSetReissueInfoToSetInfoSuccessfully.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("accounts");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "accounts";
        String sqlQuery = "SELECT * FROM accounts where account_id=4";
        String[] ignoreCols =
                {"sex_id", "birth_date", "profile_image_file_name", "self_introduction",
                "activate_token", "activate_effective_date",
                "account_created_at", "account_updated_at", "account_delete_flag", "account_deleted_at"};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test(expected = NoDataExistException.class)
    public void testSetReissueInfoInCaseNomatchedAccountExist() throws Exception {
        AccountPasswordModel sut = new AccountPasswordModel();
        sut.setReissueInfo("testt2@example.com");
    }

    @Test
    public void testCheckReissueStatusInCaseStatusIsValid() throws Exception {
        int expected = 5;

        AccountPasswordModel sut = new AccountPasswordModel() {
            Timestamp getCurrentTimestamp() {
                return Timestamp.valueOf("2022-01-02 03:04:05.567");
            }
        };
        int actual = sut.checkReissueStatus(
                "test3@example.com",
                "SYPNs2GPwKLxQfidlxEAi-FGJgSp80A-6OAXnveG6EgRiEuGQ1LboM7Xfbpu30wE");

        assertThat(actual, is(expected));
    }

    @Test(expected = NoDataExistException.class)
    public void testCheckReissueStatusInCaseReissueEffectiveDateIsExpired() throws Exception {
        int expected = 0;

        AccountPasswordModel sut = new AccountPasswordModel() {
            Timestamp getCurrentTimestamp() {
                return Timestamp.valueOf("2022-01-03 04:05:06.790");
            }
        };
        int actual = sut.checkReissueStatus(
                "test4@example.com",
                "DKMUCz3FS0_lD2a4cr-K1F4Z471W7iE2Di2GD145Hen8OkFjbklYc020_eQSkvpI");

        assertThat(actual, is(expected));
    }

    @Test(expected = NoDataExistException.class)
    public void testCheckReissueStatusInCaseNoMatchedAccountExist() throws Exception {
        AccountPasswordModel sut = new AccountPasswordModel();
        sut.checkReissueStatus(
                "test4@example.com",
                "SYPNs2GPwKLxQfidlxEAi-FGJgSp80A-6OAXnveG6EgRiEuGQ1LboM7Xfbpu30wE");
    }

    @Test
    public void testExecuteReissueToUpdatePasswordSuccessfully() throws Exception {
        AccountPasswordModel sut = new AccountPasswordModel();
        boolean actual = sut.executeReissue(7, "test5@example.com", "newPassword");

        assertThat(actual, is(true));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.AccountPasswordModelTest.testExecuteReissueToUpdatePasswordSuccessfully.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("accounts");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "accounts";
        String sqlQuery = "SELECT * FROM accounts where account_id=7";
        String[] ignoreCols =
                {"sex_id", "birth_date", "profile_image_file_name", "self_introduction",
                "activate_token", "activate_effective_date", "reissue_token", "reissue_effective_date",
                "account_created_at", "account_updated_at", "account_delete_flag", "account_deleted_at"};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

}
