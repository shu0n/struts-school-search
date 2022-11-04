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

public class LoginAdminStatusModelTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.LoginAdminStatusModelTest.xml") {
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
    public void testStateToAdminLoginedSucessfully() throws Exception {
        LoginAdminStatusModel sut = new LoginAdminStatusModel();
        HttpSession actual = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        actual = sut.stateToAdminLogined(1, actual);

        assertThat(actual.getAttribute("adminId"), is("1"));
        assertThat(actual.getAttribute("adminLastName"), is("管理者"));
        assertThat(actual.getAttribute("adminLogined"), is("true"));
    }

    @Test(expected = NoDataExistException.class)
    public void testStateToAdminLoginedToThrowNoDataExistException() throws Exception {
        LoginAdminStatusModel sut = new LoginAdminStatusModel();
        HttpSession actual = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        sut.stateToAdminLogined(2, actual);
    }

    @Test
    public void testStateToAdminLogoutSuccessfully() {
        LoginAdminStatusModel sut = new LoginAdminStatusModel();
        HttpSession actual = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        actual.setAttribute("adminId", "1");
        actual.setAttribute("adminLastName", "管理者");
        actual.setAttribute("adminLogined", "true");
        actual = sut.stateToAdminLogout(actual);

        assertThat(actual.getAttribute("adminId"), is(nullValue()));
        assertThat(actual.getAttribute("adminLastName"), is(nullValue()));
        assertThat(actual.getAttribute("adminLogined"), is(nullValue()));
    }

    @Test
    public void testIsAdminLoginedInCaseLogined() throws Exception {
        LoginAdminStatusModel sut = new LoginAdminStatusModel();
        HttpSession session = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        session.setAttribute("adminLogined", "true");
        boolean actual = sut.isAdminLogined(session);

        assertThat(actual, is(true));
    }

    @Test
    public void testIsAdminLoginedInCaseNotLoginedWithInvalidValue() throws Exception {
        LoginAdminStatusModel sut = new LoginAdminStatusModel();
        HttpSession session = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        session.setAttribute("adminLogined", "truu");
        boolean actual = sut.isAdminLogined(session);

        assertThat(actual, is(false));
    }

    @Test
    public void testIsAdminLoginedInCaseNotLoginedWithNullValue() throws Exception {
        LoginAdminStatusModel sut = new LoginAdminStatusModel();
        HttpSession session = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        boolean actual = sut.isAdminLogined(session);

        assertThat(actual, is(false));
    }

    @Test
    public void testGetAdminIdSuccessfully() throws Exception {
        int expected = 2;

        LoginAdminStatusModel sut = new LoginAdminStatusModel();
        HttpSession session = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        session.setAttribute("adminId", "2");
        int actual = sut.getAdminId(session);

        assertThat(actual, is(expected));
    }

    @Test(expected = NoDataExistException.class)
    public void testGetAdminIdToThrowNoDataExistException() throws Exception {
        LoginAdminStatusModel sut = new LoginAdminStatusModel();
        HttpSession session = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        sut.getAdminId(session);
    }

    @Test
    public void testGetAdminLastNameSuccessfully() throws Exception {
         String expected = "システム管理者";

        LoginAdminStatusModel sut = new LoginAdminStatusModel();
        HttpSession session = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        session.setAttribute("adminLastName", "システム管理者");
        String actual = sut.getAdminLastName(session);

        assertThat(actual, is(expected));
    }

    @Test(expected = NoDataExistException.class)
    public void testGetAdminLastNameToThrowNoDataExistException() throws Exception {
        LoginAdminStatusModel sut = new LoginAdminStatusModel();
        HttpSession session = new HttpServletRequestSimulator(new ServletContextSimulator()).getSession();
        sut.getAdminLastName(session);
    }

}
