package dao.entry;

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

import dao.entry.sql.EntryUpdateDataActionForm;
import test.db.DbUnitTester;

public class UpdateEntryDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.UpdateEntryDAOTest.xml") {
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
    public void testUpdateEntryToUpdateOneEntry() throws Exception {
        UpdateEntryDAO sut = new UpdateEntryDAO() {
            String getStringCurrentTimestamp() {
                return "2022-03-04 05:06:07.891";
            }
        };
        EntryUpdateDataActionForm dataForm = new EntryUpdateDataActionForm();
        dataForm.setEntryId(6);
        dataForm.setEntrySchoolId(3);
        dataForm.setApplicantAccountId(1);
        dataForm.setEntryQuestion("更新された申込時の質問1");
        dataForm.setEntryStatusId(2);
        boolean actual = sut.updateEntry(dataForm);

        assertThat(actual, is(true));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.UpdateEntryDAOTest.testUpdateEntryToUpdateOneEntry.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("entries");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "entries";
        String sqlQuery = "SELECT * FROM entries WHERE entry_id=6";
        String[] ignoreCols = {};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test
    public void testUpdateEntryToUpdateNoEntry() throws Exception {
        UpdateEntryDAO sut = new UpdateEntryDAO();

        EntryUpdateDataActionForm dataForm = new EntryUpdateDataActionForm();
        dataForm.setEntryId(7);
        dataForm.setEntrySchoolId(3);
        dataForm.setApplicantAccountId(4);
        dataForm.setEntryQuestion("更新された申込時の質問2");
        dataForm.setEntryStatusId(3);
        boolean actual = sut.updateEntry(dataForm);

        assertThat(actual, is(false));
    }

}
