package dao.contact.repack;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.ContactExtendActionForm;
import dao.contact.sql.ContactSelectJoinWhereActionForm;
import exception.NoDataExistException;
import test.db.DbUnitTester;

public class GetContactDataRepackTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.GetContactDataRepackTest.xml") {
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
    public void testGetContactDataInCaseTwoMatchedContactExist() throws Exception {
        ContactExtendActionForm expected = new ContactExtendActionForm();
        expected.setContactId(4);
        expected.setContactAccountId(1);
        expected.setContactLastName("佐藤藤");
        expected.setContactFirstName("次郎郎");
        expected.setContactLastNameKana("サトウトウ");
        expected.setContactFirstNameKana("ジロウロウ");
        expected.setContactMailAddress("satoto@example.com");
        expected.setContactContent("問い合わせ4");
        expected.setContactStatusId(1);
        expected.setContactStatusName("受付済");
        expected.setContactedAt(Timestamp.valueOf("2022-01-08 09:10:11.345"));
        expected.setContactUpdatedAt(Timestamp.valueOf("2022-01-09 10:11:12.456"));
        expected.setStrContactedAt("2022/01/08 09:10:11");
        expected.setStrContactUpdatedAt("2022/01/09 10:11:12");

        GetContactDataRepack sut = new GetContactDataRepack();
        ContactSelectJoinWhereActionForm whereForm = new ContactSelectJoinWhereActionForm();
        whereForm.setContactAccountId(1);
        ContactExtendActionForm actual = new ContactExtendActionForm();
        actual = sut.getContactData(whereForm, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test(expected = NoDataExistException.class)
    public void testGetContactDataInCaseNoMatchedContactExist() throws Exception {
        GetContactDataRepack sut = new GetContactDataRepack();
        ContactSelectJoinWhereActionForm whereForm = new ContactSelectJoinWhereActionForm();
        whereForm.setContactAccountId(2);
        ContactExtendActionForm actual = new ContactExtendActionForm();
        sut.getContactData(whereForm, actual);
    }

}
