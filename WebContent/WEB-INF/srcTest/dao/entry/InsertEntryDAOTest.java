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

import dao.entry.sql.EntryInsertDataActionForm;
import test.db.DbUnitTester;

public class InsertEntryDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.InsertEntryDAOTest.xml") {
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
    public void testInsertEntryToCreateOneEntry() throws Exception {
        InsertEntryDAO sut = new InsertEntryDAO();
        EntryInsertDataActionForm dataForm = new EntryInsertDataActionForm();
        dataForm.setEntrySchoolId(4);
        dataForm.setApplicantAccountId(2);
        dataForm.setEntryQuestion("?????????????????????????????????????????????");
        dataForm.setEntryStatusId(1);
        int actual = sut.insertEntry(dataForm);

        assertThat(actual, is(not(0)));

        // ?????????????????????????????????????????????????????????????????????????????????????????????????????????
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.InsertEntryDAOTest.testInsertEntryToCreateOneEntry.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("entries");

        // ????????????????????????????????????????????????????????????????????????
        String tableName = "entries";
        String sqlQuery = "SELECT * FROM entries WHERE entry_school_id=4";
        String[] ignoreCols = {"entry_id", "entried_at", "entry_updated_at"};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

}
