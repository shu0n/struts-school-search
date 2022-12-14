package dao.school;

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

import exception.ReferredByEntryException;
import test.db.DbUnitTester;

public class DeleteSchoolDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.DeleteSchoolDAOTest.xml") {
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
    public void testDeleteSchoolLogicallyToDeleteOneSchool() throws Exception {
        DeleteSchoolDAO sut = new DeleteSchoolDAO() {
            String getStringCurrentTimestamp() {
                return "2022-10-11 12:13:14.567";
            }
        };
        boolean actual = sut.deleteSchoolLogically(1);

        assertThat(actual, is(true));

        // ?????????????????????????????????????????????????????????????????????????????????????????????????????????
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.DeleteSchoolDAOTest.testDeleteSchoolLogicallyToDeleteOneSchool.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("schools");

        // ????????????????????????????????????????????????????????????????????????
        String tableName = "schools";
        String sqlQuery = "SELECT * FROM schools WHERE school_id=1";
        String[] ignoreCols = {};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test(expected = ReferredByEntryException.class)
    public void testDeleteSchoolLogicallyInCaseReferredByEntry() throws Exception {
        DeleteSchoolDAO sut = new DeleteSchoolDAO();
        sut.deleteSchoolLogically(2);
    }

    @Test
    public void testDeleteSchoolLogicallyToDeleteNoSchool() throws Exception {
        DeleteSchoolDAO sut = new DeleteSchoolDAO();
        boolean actual = sut.deleteSchoolLogically(3);
        assertThat(actual, is(false));
    }

}
