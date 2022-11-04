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

import dao.message.sql.MessageInsertDataActionForm;
import test.db.DbUnitTester;

public class InsertMessageDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.InsertMessageDAOTest.xml") {
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
    public void testInsertMessageToCreateOneNewMessage() throws Exception {
        InsertMessageDAO sut = new InsertMessageDAO();
        MessageInsertDataActionForm dataForm = new MessageInsertDataActionForm();
        dataForm.setReplyMessageId(0);
        dataForm.setEntryId(6);
        dataForm.setSenderAccountId(3);
        dataForm.setRecipientAccountId(4);
        dataForm.setMessageSubject("メッセージ題名1");
        dataForm.setMessageBody("メッセージ本文1");
        int actual = sut.insertMessage(dataForm);

        assertThat(actual, is(not(0)));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.InsertMessageDAOTest.testInsertMessageToCreateOneNewMessage.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("messages");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "messages";
        String sqlQuery = "SELECT * FROM messages WHERE entry_id=6";
        String[] ignoreCols = {"message_id", "sended_at"};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test
    public void testInsertMessageToCreateOneReplyMessage() throws Exception {
        InsertMessageDAO sut = new InsertMessageDAO();
        MessageInsertDataActionForm dataForm = new MessageInsertDataActionForm();
        dataForm.setReplyMessageId(31);
        dataForm.setEntryId(8);
        dataForm.setSenderAccountId(1);
        dataForm.setRecipientAccountId(4);
        dataForm.setMessageSubject("メッセージ題名3");
        dataForm.setMessageBody("メッセージ本文3");
        int actual = sut.insertMessage(dataForm);

        assertThat(actual, is(not(0)));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.InsertMessageDAOTest.testInsertMessageToCreateOneReplyMessage.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("messages");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "messages";
        String sqlQuery = "SELECT * FROM messages WHERE entry_id=8";
        String[] ignoreCols = {"message_id", "sended_at"};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

}
