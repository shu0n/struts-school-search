package dao.entrystatus;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.EntryStatusActionForm;
import dao.entrystatus.sql.EntryStatusSelectWhereActionForm;
import test.db.DbUnitTester;

public class SelectEntryStatusDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SelectEntryStatusDAOTest.xml") {
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
    public void testSelectMatchedEntryStatusToGetAllEntryStatus() throws Exception {
        String[] expected = {"1", "2", "3"};

        SelectEntryStatusDAO sut = new SelectEntryStatusDAO();
        EntryStatusSelectWhereActionForm whereForm = new EntryStatusSelectWhereActionForm();
        List<EntryStatusActionForm> formList = sut.selectMatchedEntryStatus(whereForm);
        String[] actual = new String[3];
        for(int i = 0; i < formList.size(); i++) {
            actual[i] = String.valueOf(formList.get(i).getEntryStatusId());
        }

        assertThat(actual, is(arrayContainingInAnyOrder(expected)));
    }

    @Test
    public void testSelectMatchedEntryStatusToGetOneEntryStatusByEntryStatusId() throws Exception {
        EntryStatusActionForm expected = new EntryStatusActionForm();
        expected.setEntryStatusId(2);
        expected.setEntryStatusName("連絡済");

        SelectEntryStatusDAO sut = new SelectEntryStatusDAO();
        EntryStatusSelectWhereActionForm whereForm = new EntryStatusSelectWhereActionForm();
        whereForm.setEntryStatusId(2);
        EntryStatusActionForm actual = sut.selectMatchedEntryStatus(whereForm).get(0);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testSelectMatchedEntryStatusToGetOneEntryStatusByEntryStatusName() throws Exception {
        int expected = 1;

        SelectEntryStatusDAO sut = new SelectEntryStatusDAO();
        EntryStatusSelectWhereActionForm whereForm = new EntryStatusSelectWhereActionForm();
        whereForm.setEntryStatusName("申込済");
        List<EntryStatusActionForm> actual = sut.selectMatchedEntryStatus(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedEntryStatusToGetNoEntryStatusByEntryStatusName() throws Exception {
        int expected = 0;

        SelectEntryStatusDAO sut = new SelectEntryStatusDAO();
        EntryStatusSelectWhereActionForm whereForm = new EntryStatusSelectWhereActionForm();
        whereForm.setEntryStatusName("申込済み");
        List<EntryStatusActionForm> actual = sut.selectMatchedEntryStatus(whereForm);

        assertThat(actual.size(), is(expected));
    }

}
