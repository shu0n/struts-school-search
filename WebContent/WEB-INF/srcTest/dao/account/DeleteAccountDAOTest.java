package dao.account;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.property.PropertyTester.*;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import exception.ReferredBySchoolException;
import test.db.DbUnitTester;

public class DeleteAccountDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.DeleteAccountDAOTest.xml") {
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
    public void testDeleteAccountLogicallyToDeleteOneAccount() throws Exception {
        DeleteAccountDAO sut = new DeleteAccountDAO() {
            String getStringCurrentTimestamp() {
                return "2022-10-11 12:13:14.567";
            }
        };
        boolean actual = sut.deleteAccountLogically(1);

        assertThat(actual, is(true));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.DeleteAccountDAOTest.testDeleteAccountLogicallyToDeleteOneAccount.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("accounts");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "accounts";
        String sqlQuery = "SELECT * FROM accounts WHERE account_id=1";
        String[] ignoreCols = {};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test(expected = ReferredBySchoolException.class)
    public void testDeleteAccountLogicallyInCaseReferredBySchool() throws Exception {
        DeleteAccountDAO sut = new DeleteAccountDAO();
        sut.deleteAccountLogically(2);
    }

    @Test
    public void testDeleteAccountLogicallyToDeleteNoAccount() throws Exception {
        DeleteAccountDAO sut = new DeleteAccountDAO();
        boolean actual = sut.deleteAccountLogically(3);
        assertThat(actual, is(false));
    }

}
