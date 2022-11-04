package model.login;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.property.PropertyTester.*;

import javax.servlet.http.HttpSession;

import org.junit.ClassRule;
import org.junit.Test;

import exception.NoDataExistException;
import servletunit.HttpServletRequestSimulator;
import servletunit.ServletContextSimulator;
import test.db.DbUnitTester;

public class LoginStatusModelTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.LoginStatusModelTest.xml") {
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
    public void testStateToLoginedSucessfully() throws Exception {
        LoginStatusModel sut = new LoginStatusModel();
        HttpSession actual = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        actual = sut.stateToLogined(1, actual);

        assertThat(actual.getAttribute("accountId"), is("1"));
        assertThat(actual.getAttribute("lastName"), is("田中"));
        assertThat(actual.getAttribute("logined"), is("true"));
    }

    @Test(expected = NoDataExistException.class)
    public void testStateToLoginedToThrowNoDataExistException() throws Exception {
        LoginStatusModel sut = new LoginStatusModel();
        HttpSession actual = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        sut.stateToLogined(2, actual);
    }

    @Test
    public void testStateToLogoutSucessfully() {
        LoginStatusModel sut = new LoginStatusModel();
        HttpSession actual = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        actual.setAttribute("accountId", "1");
        actual.setAttribute("lastName", "田中");
        actual.setAttribute("logined", "true");
        actual = sut.stateToLogout(actual);

        assertThat(actual.getAttribute("accountId"), is(nullValue()));
        assertThat(actual.getAttribute("lastName"), is(nullValue()));
        assertThat(actual.getAttribute("logined"), is(nullValue()));
    }

    @Test
    public void testIsLoginedInCaseLogined() throws Exception {
        LoginStatusModel sut = new LoginStatusModel();
        HttpSession session = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        session.setAttribute("logined", "true");
        boolean actual = sut.isLogined(session);

        assertThat(actual, is(true));
    }

    @Test
    public void testIsLoginedInCaseNotLoginedWithInvalidValue() throws Exception {
        LoginStatusModel sut = new LoginStatusModel();
        HttpSession session = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        session.setAttribute("logined", "truu");
        boolean actual = sut.isLogined(session);

        assertThat(actual, is(false));
    }

    @Test
    public void testIsLoginedInCaseNotLoginedWithNullValue() throws Exception {
        LoginStatusModel sut = new LoginStatusModel();
        HttpSession session = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        boolean actual = sut.isLogined(session);

        assertThat(actual, is(false));
    }

    @Test
    public void testGetAccountIdSuccessfully() throws Exception {
        int expected = 3;

        LoginStatusModel sut = new LoginStatusModel();
        HttpSession session = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        session.setAttribute("accountId", "3");
        int actual = sut.getAccountId(session);

        assertThat(actual, is(expected));
    }

    @Test(expected = NoDataExistException.class)
    public void testGetAccountIdToThrowNoDataExistException() throws Exception {
        LoginStatusModel sut = new LoginStatusModel();
        HttpSession session = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        sut.getAccountId(session);
    }

    @Test
    public void testGetLastNameSuccessfully() throws Exception {
         String expected = "山田";

        LoginStatusModel sut = new LoginStatusModel();
        HttpSession session = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        session.setAttribute("lastName", "山田");
        String actual = sut.getLastName(session);

        assertThat(actual, is(expected));
    }

    @Test(expected = NoDataExistException.class)
    public void testGetLastNameToThrowNoDataExistException() throws Exception {
        LoginStatusModel sut = new LoginStatusModel();
        HttpSession session = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        sut.getLastName(session);
    }

}
