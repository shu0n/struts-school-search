package dao.account;

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

import dao.account.sql.AccountInsertDataActionForm;
import test.db.DbUnitTester;

public class InsertAccountDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.InsertAccountDAOTest.xml") {
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
    public void testInsertAccountToCreateOneAccount() throws Exception {
        InsertAccountDAO sut = new InsertAccountDAO();
        AccountInsertDataActionForm dataForm = new AccountInsertDataActionForm();
        dataForm.setLastName("田中");
        dataForm.setFirstName("太郎");
        dataForm.setLastNameKana("タナカ");
        dataForm.setFirstNameKana("タロウ");
        dataForm.setSexId(1);
        dataForm.setBirthDate(Timestamp.valueOf("1990-01-02 00:00:00"));
        dataForm.setPrefectureId(2);
        dataForm.setMailAddress("test@example.com");
        dataForm.setProfileImageFileName("image_test.png");
        dataForm.setSelfIntroduction("自己紹介です。");
        dataForm.setPassword("password");
        dataForm.setAccountStatus("1");
        dataForm.setActivateToken("activateToken");
        dataForm.setActivateEffectiveDate(Timestamp.valueOf("2022-12-31 01:02:03.456"));
        int actual = sut.insertAccount(dataForm);

        assertThat(actual, is(not(0)));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.InsertAccountDAOTest.testInsertAccountToCreateOneAccount.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("accounts");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "accounts";
        String sqlQuery = "SELECT * FROM accounts";
        String[] ignoreCols = {"account_id", "account_created_at", "account_updated_at"};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

}
