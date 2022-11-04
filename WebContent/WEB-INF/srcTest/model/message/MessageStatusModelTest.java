package model.message;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import test.db.DbUnitTester;

public class MessageStatusModelTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.MessageStatusModelTest.xml") {
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
    public void testIsRecipientInCaseRecipientAccount() throws Exception {
        MessageStatusModel sut = new MessageStatusModel();
        boolean actual = sut.isRecipient(1, 4);

        assertThat(actual, is(true));
    }

    @Test
    public void testIsRecipientInCaseNotRecipientAccount() throws Exception {
        MessageStatusModel sut = new MessageStatusModel();
        boolean actual = sut.isRecipient(2, 5);

        assertThat(actual, is(false));
    }

    @Test
    public void testIsRecipientInCaseSenderAccount() throws Exception {
        MessageStatusModel sut = new MessageStatusModel();
        boolean actual = sut.isSender(3, 1);

        assertThat(actual, is(true));
    }

    @Test
    public void testIsRecipientInCaseNotSenderAccount() throws Exception {
        MessageStatusModel sut = new MessageStatusModel();
        boolean actual = sut.isSender(4, 2);

        assertThat(actual, is(false));
    }

    @Test
    public void testUpdateReceivedMessageOpenedToMakeOpened() throws Exception {
        String expected = "1";

        MessageStatusModel sut = new MessageStatusModel() {
            Timestamp getCurrentTimestamp() {
                return Timestamp.valueOf("2022-01-02 03:04:05.678");
            }
        };
        String actual = sut.updateReceivedMessageOpened(5);

        assertThat(actual, is(expected));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.MessageStatusModelTest.testUpdateReceivedMessageOpenedToMakeOpened.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("messages");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "messages";
        String sqlQuery = "SELECT * FROM messages WHERE message_id=5";
        String[] ignoreCols = {};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test
    public void testUpdateReceivedMessageOpenedNotToMakeOpened() throws Exception {
        String expected = "0";

        MessageStatusModel sut = new MessageStatusModel();
        String actual = sut.updateReceivedMessageOpened(6);

        assertThat(actual, is(expected));
    }
}
