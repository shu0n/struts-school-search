package dao.message;

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

import dao.message.sql.MessageUpdateDataActionForm;
import test.db.DbUnitTester;

public class UpdateMessageDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.UpdateMessageDAOTest.xml") {
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
    public void testUpdateMessageToUpdateOneMessage() throws Exception {
        UpdateMessageDAO sut = new UpdateMessageDAO();
        MessageUpdateDataActionForm dataForm = new MessageUpdateDataActionForm();
        dataForm.setMessageId(31);
        dataForm.setOpenedFlag("1");
        dataForm.setOpenedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        boolean actual = sut.updateMessage(dataForm);

        assertThat(actual, is(true));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.UpdateMessageDAOTest.testUpdateMessageToUpdateOneMessage.xml"));
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
    public void testUpdateMessageToUpdatNoMessage() throws Exception {
        UpdateMessageDAO sut = new UpdateMessageDAO();
        MessageUpdateDataActionForm dataForm = new MessageUpdateDataActionForm();
        dataForm.setMessageId(32);
        dataForm.setOpenedFlag("1");
        dataForm.setOpenedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        boolean actual = sut.updateMessage(dataForm);

        assertThat(actual, is(false));
    }

}
