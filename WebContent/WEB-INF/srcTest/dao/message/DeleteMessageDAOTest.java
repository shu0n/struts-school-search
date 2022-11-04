package dao.message;

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

import test.db.DbUnitTester;

public class DeleteMessageDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.DeleteMessageDAOTest.xml") {
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
    public void testDeleteReceivedMessageLogicallyToDeleteOneReceivedMessage() throws Exception {
        DeleteMessageDAO sut = new DeleteMessageDAO() {
            String getStringCurrentTimestamp() {
                return "2022-10-11 12:13:14.567";
            }
        };
        boolean actual = sut.deleteReceivedMessageLogically(31);

        assertThat(actual, is(true));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.DeleteMessageDAOTest.testDeleteReceivedMessageLogicallyToDeleteOneReceivedMessage.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("messages");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "messages";
        String sqlQuery = "SELECT * FROM messages WHERE message_id=31";
        String[] ignoreCols = {};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test
    public void testDeleteReceivedMessageLogicallyToDeleteNoReceivedMessage() throws Exception {
        DeleteMessageDAO sut = new DeleteMessageDAO();
        boolean actual = sut.deleteReceivedMessageLogically(33);

        assertThat(actual, is(false));
    }

    @Test
    public void testDeleteSendedMessageLogicallyToDeleteOneSendedMessage() throws Exception {
        DeleteMessageDAO sut = new DeleteMessageDAO() {
            String getStringCurrentTimestamp() {
                return "2022-10-11 12:13:14.567";
            }
        };
        boolean actual = sut.deleteSendedMessageLogically(32);

        assertThat(actual, is(true));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.DeleteMessageDAOTest.testDeleteSendedMessageLogicallyToDeleteOneSendedMessage.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("messages");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "messages";
        String sqlQuery = "SELECT * FROM messages WHERE message_id=32";
        String[] ignoreCols = {};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test
    public void testDeleteSendedMessageLogicallyToDeleteNoSendedMessage() throws Exception {
        DeleteMessageDAO sut = new DeleteMessageDAO();
        boolean actual = sut.deleteSendedMessageLogically(34);

        assertThat(actual, is(false));
    }

}
