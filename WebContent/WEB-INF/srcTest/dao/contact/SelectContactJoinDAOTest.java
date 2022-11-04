package dao.contact;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.ContactExtendActionForm;
import dao.contact.sql.ContactSelectJoinWhereActionForm;
import test.db.DbUnitTester;

public class SelectContactJoinDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SelectContactJoinDAOTest.xml") {
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
    public void testSelectMatchedContactToGetAllContact() throws Exception {
        String[] expected = {"1", "2", "3", "4", "5", "6"};

        SelectContactJoinDAO sut = new SelectContactJoinDAO();
        ContactSelectJoinWhereActionForm whereForm = new ContactSelectJoinWhereActionForm();
        List<ContactExtendActionForm> formList = sut.selectMatchedContact(whereForm);
        String[] actual = new String[6];
        for(int i = 0; i < formList.size(); i++) {
            actual[i] = String.valueOf(formList.get(i).getContactId());
        }

        assertThat(actual, is(arrayContaining(expected)));
    }

    @Test
    public void testSelectMatchedContactToGetOneContactByContactId() throws Exception {
        ContactExtendActionForm expected = new ContactExtendActionForm();
        expected.setContactId(3);
        expected.setContactAccountId(1);
        expected.setContactLastName("田中中");
        expected.setContactFirstName("太郎郎");
        expected.setContactLastNameKana("タナカナカ");
        expected.setContactFirstNameKana("タロウロウ");
        expected.setContactMailAddress("tanakanaka@example.com");
        expected.setContactContent("問い合わせ3");
        expected.setContactStatusId(2);
        expected.setContactStatusName("連絡済");
        expected.setContactedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        expected.setContactUpdatedAt(Timestamp.valueOf("2022-01-07 08:09:10.234"));

        SelectContactJoinDAO sut = new SelectContactJoinDAO();
        ContactSelectJoinWhereActionForm whereForm = new ContactSelectJoinWhereActionForm();
        whereForm.setContactId(3);
        ContactExtendActionForm actual = sut.selectMatchedContact(whereForm).get(0);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testSelectMatchedContactToGetTwoContactByContactAccountId() throws Exception {
        int expected = 2;

        SelectContactJoinDAO sut = new SelectContactJoinDAO();
        ContactSelectJoinWhereActionForm whereForm = new ContactSelectJoinWhereActionForm();
        whereForm.setContactAccountId(2);
        List<ContactExtendActionForm> actual = sut.selectMatchedContact(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedContactToGetOneContactByMultipleCondition() throws Exception {
        int expected = 1;

        SelectContactJoinDAO sut = new SelectContactJoinDAO();
        ContactSelectJoinWhereActionForm whereForm = new ContactSelectJoinWhereActionForm();
        whereForm.setContactLastName("前田");
        whereForm.setContactFirstName("五郎");
        whereForm.setContactLastNameKana("マエダ");
        whereForm.setContactFirstNameKana("ゴロウ");
        whereForm.setContactMailAddress("maeda@example.com");
        whereForm.setContactStatusId(2);
        List<ContactExtendActionForm> actual = sut.selectMatchedContact(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedContactToGetTwoContactByLikeContactLastNameAndContactFirstName() throws Exception {
        int expected = 2;

        SelectContactJoinDAO sut = new SelectContactJoinDAO();
        ContactSelectJoinWhereActionForm whereForm = new ContactSelectJoinWhereActionForm();
        whereForm.setLikeContactLastName("田");
        whereForm.setLikeContactFirstName("郎");
        List<ContactExtendActionForm> actual = sut.selectMatchedContact(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedContactToGetOneContactByLikeContactLastNameKanaAndContactFirstNameKana()
            throws Exception {
        int expected = 1;

        SelectContactJoinDAO sut = new SelectContactJoinDAO();
        ContactSelectJoinWhereActionForm whereForm = new ContactSelectJoinWhereActionForm();
        whereForm.setLikeContactLastNameKana("タナ");
        whereForm.setLikeContactFirstNameKana("ロウ");
        List<ContactExtendActionForm> actual = sut.selectMatchedContact(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedContactToGetTwoContactByLikeContactMailAddress() throws Exception {
        int expected = 2;

        SelectContactJoinDAO sut = new SelectContactJoinDAO();
        ContactSelectJoinWhereActionForm whereForm = new ContactSelectJoinWhereActionForm();
        whereForm.setLikeContactMailAddress("ta");
        List<ContactExtendActionForm> actual = sut.selectMatchedContact(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedContactToGetTwoContactByRangeOfContactedDateAndContactUpdatedDate() throws Exception {
        int expected = 2;

        SelectContactJoinDAO sut = new SelectContactJoinDAO();
        ContactSelectJoinWhereActionForm whereForm = new ContactSelectJoinWhereActionForm();
        whereForm.setFromContactedDate("2022-01-06 00:00:00");
        whereForm.setToContactedDate("2022-01-10 23:59:59");
        whereForm.setFromContactUpdatedDate("2022-01-09 00:00:00");
        whereForm.setToContactUpdatedDate("2022-01-11 23:59:59");
        List<ContactExtendActionForm> actual = sut.selectMatchedContact(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedContactToGetTwoContactByArrayOfContactIdAndContactAccountIdAndContactStatusId()
            throws Exception {
        int expected = 2;

        SelectContactJoinDAO sut = new SelectContactJoinDAO();
        ContactSelectJoinWhereActionForm whereForm = new ContactSelectJoinWhereActionForm();
        String[] contactIdArray = {"2", "3", "4", "5", "6"};
        whereForm.setContactIdArray(contactIdArray);
        String[] contactAccountIdArray = {"2", "3"};
        whereForm.setContactAccountIdArray(contactAccountIdArray);
        String[] contactStatusIdArray = {"1", "3"};
        whereForm.setContactStatusIdArray(contactStatusIdArray);
        List<ContactExtendActionForm> actual = sut.selectMatchedContact(whereForm);

        assertThat(actual.size(), is(expected));
    }

}
