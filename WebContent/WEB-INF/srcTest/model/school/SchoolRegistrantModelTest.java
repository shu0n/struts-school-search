package model.school;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsMap.*;
import static test.property.PropertyTester.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.Test;

import exception.NoDataExistException;
import test.db.DbUnitTester;

public class SchoolRegistrantModelTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SchoolRegistrantModelTest.xml") {
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
    public void testIsRegistrantInCaseMatchedAccountExist() throws Exception {
        SchoolRegistrantModel sut = new SchoolRegistrantModel();
        boolean actual = sut.isRegistrant(1, 2);

        assertThat(actual, is(true));
    }

    @Test
    public void testIsRegistrantInCaseNoMatchedAccountExist() throws Exception {
        SchoolRegistrantModel sut = new SchoolRegistrantModel();
        boolean actual = sut.isRegistrant(1, 3);

        assertThat(actual, is(false));
    }

    @Test
    public void testIsRegistrantInCaseAccountIdIsZero() throws Exception {
        SchoolRegistrantModel sut = new SchoolRegistrantModel();
        boolean actual = sut.isRegistrant(1, 0);

        assertThat(actual, is(false));
    }

    @Test
    public void testIsRegistrantInCaseMatchedAdminExist() throws Exception {
        SchoolRegistrantModel sut = new SchoolRegistrantModel();
        boolean actual = sut.isRegistrantAdmin(2, 1);

        assertThat(actual, is(true));
    }

    @Test
    public void testIsRegistrantInCaseNoMatchedAdminExist() throws Exception {
        SchoolRegistrantModel sut = new SchoolRegistrantModel();
        boolean actual = sut.isRegistrantAdmin(2, 3);

        assertThat(actual, is(false));
    }

    @Test
    public void testIsRegistrantInCaseAdminIdIsZero() throws Exception {
        SchoolRegistrantModel sut = new SchoolRegistrantModel();
        boolean actual = sut.isRegistrant(2, 0);

        assertThat(actual, is(false));
    }

    @Test
    public void testGetRegistrantNameInCaseMatchedAccountExist() throws Exception {
        Map<String, String> expected = new HashMap<>();
        expected.put("registrantLastName", "田中");
        expected.put("registrantFirstName", "太郎");

        SchoolRegistrantModel sut = new SchoolRegistrantModel();
        Map<String, String> actual = sut.getRegistrantName(1);

        assertThat(actual, is(sameEntrySetAs(expected)));
    }

    @Test(expected = NoDataExistException.class)
    public void testGetRegistrantNameInCaseNoMatchedAccountExist() throws Exception {
        SchoolRegistrantModel sut = new SchoolRegistrantModel();
        sut.getRegistrantName(7);
    }

}
