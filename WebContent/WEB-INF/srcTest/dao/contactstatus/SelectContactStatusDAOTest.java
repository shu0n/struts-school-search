package dao.contactstatus;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.ContactStatusActionForm;
import dao.contactstatus.sql.ContactStatusSelectWhereActionForm;
import test.db.DbUnitTester;

public class SelectContactStatusDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SelectContactStatusDAOTest.xml") {
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
    public void testSelectMatchedContactStatusToGetAllContactStatus() throws Exception {
        String[] expected = {"1", "2"};

        SelectContactStatusDAO sut = new SelectContactStatusDAO();
        ContactStatusSelectWhereActionForm whereForm = new ContactStatusSelectWhereActionForm();
        List<ContactStatusActionForm> formList = sut.selectMatchedContactStatus(whereForm);
        String[] actual = new String[2];
        for(int i = 0; i < formList.size(); i++) {
            actual[i] = String.valueOf(formList.get(i).getContactStatusId());
        }

        assertThat(actual, arrayContainingInAnyOrder(expected));
    }

    @Test
    public void testSelectMatchedContactStatusToGetOneContactStatusByContactStatusId() throws Exception {
        ContactStatusActionForm expected = new ContactStatusActionForm();
        expected.setContactStatusId(1);
        expected.setContactStatusName("受付済");

        SelectContactStatusDAO sut = new SelectContactStatusDAO();
        ContactStatusSelectWhereActionForm whereForm = new ContactStatusSelectWhereActionForm();
        whereForm.setContactStatusId(1);
        ContactStatusActionForm actual = sut.selectMatchedContactStatus(whereForm).get(0);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testSelectMatchedContactStatusToGetOneContactStatusByContactStatusName() throws Exception {
        int expected = 1;

        SelectContactStatusDAO sut = new SelectContactStatusDAO();
        ContactStatusSelectWhereActionForm whereForm = new ContactStatusSelectWhereActionForm();
        whereForm.setContactStatusName("連絡済");
        List<ContactStatusActionForm> actual = sut.selectMatchedContactStatus(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedContactStatusToGetNoContactStatusByContactStatusName() throws Exception {
        int expected = 0;

        SelectContactStatusDAO sut = new SelectContactStatusDAO();
        ContactStatusSelectWhereActionForm whereForm = new ContactStatusSelectWhereActionForm();
        whereForm.setContactStatusName("連絡済み");
        List<ContactStatusActionForm> actual = sut.selectMatchedContactStatus(whereForm);

        assertThat(actual.size(), is(expected));
    }

}
