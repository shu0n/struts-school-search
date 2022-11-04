package dao.admin;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.AdminActionForm;
import dao.admin.sql.AdminSelectWhereActionForm;
import test.db.DbUnitTester;

public class SelectAdminDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SelectAdminDAOTest.xml") {
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
    public void testSelectMatchedAdminToGetAllAdmin() throws Exception {
        String[] expected = {"1", "2", "3"};

        SelectAdminDAO sut = new SelectAdminDAO();
        AdminSelectWhereActionForm whereForm = new AdminSelectWhereActionForm();
        List<AdminActionForm> formList = sut.selectMatchedAdmin(whereForm);
        String[] actual = new String[3];
        for(int i = 0; i < formList.size(); i++) {
            actual[i] = String.valueOf(formList.get(i).getAdminId());
        }

        assertThat(actual, is(arrayContaining(expected)));
    }

    @Test
    public void testSelectMatchedAdminToGetOneAdminByAdminId() throws Exception {
        AdminActionForm expected = new AdminActionForm();
        expected.setAdminId(2);
        expected.setAdminDepartmentId(1);
        expected.setAdminLastName("サブ管理者");
        expected.setAdminFirstName("次郎");
        expected.setAdminLastNameKana("サブカンリシャ");
        expected.setAdminFirstNameKana("ジロウ");
        expected.setAdminMailAddress("subsystem@example.com");
        expected.setAdminAuthority("8");
        expected.setAdminStatus("0");
        expected.setAdminCreatedAt(Timestamp.valueOf("2020-03-04 05:06:07.892"));
        expected.setAdminUpdatedAt(Timestamp.valueOf("2020-04-05 06:07:08.912"));

        SelectAdminDAO sut = new SelectAdminDAO();
        AdminSelectWhereActionForm whereForm = new AdminSelectWhereActionForm();
        whereForm.setAdminId(2);
        AdminActionForm actual = sut.selectMatchedAdmin(whereForm).get(0);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testSelectMatchedAdminToGetOneAdminByMultipleCondition() throws Exception {
        int expected = 1;

        SelectAdminDAO sut = new SelectAdminDAO();
        AdminSelectWhereActionForm whereForm = new AdminSelectWhereActionForm();
        whereForm.setAdminDepartmentId(1);
        whereForm.setAdminStatus("1");
        List<AdminActionForm> actual = sut.selectMatchedAdmin(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedAdminToGetOneAdminByMultipleConditionExceptAdminId() throws Exception {
        int expected = 1;

        SelectAdminDAO sut = new SelectAdminDAO();
        AdminSelectWhereActionForm whereForm = new AdminSelectWhereActionForm();
        whereForm.setAdminDepartmentId(2);
        whereForm.setAdminLastName("管理者");
        whereForm.setAdminFirstName("太郎");
        whereForm.setAdminLastNameKana("カンリシャ");
        whereForm.setAdminFirstNameKana("タロウ");
        whereForm.setAdminMailAddress("system@example.com");
        whereForm.setAdminPassword("password");
        whereForm.setAdminAuthority("9");
        whereForm.setAdminStatus("1");
        List<AdminActionForm> actual = sut.selectMatchedAdmin(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedAdminToGetNoAdminByMultipleConditionExceptAdminId() throws Exception {
        int expected = 0;

        SelectAdminDAO sut = new SelectAdminDAO();
        AdminSelectWhereActionForm whereForm = new AdminSelectWhereActionForm();
        whereForm.setAdminDepartmentId(2);
        whereForm.setAdminLastName("管理者");
        whereForm.setAdminFirstName("太郎");
        whereForm.setAdminLastNameKana("カンリシャ");
        whereForm.setAdminFirstNameKana("ジロウ");
        whereForm.setAdminMailAddress("system@example.com");
        whereForm.setAdminPassword("password");
        whereForm.setAdminAuthority("9");
        whereForm.setAdminStatus("1");
        List<AdminActionForm> actual = sut.selectMatchedAdmin(whereForm);

        assertThat(actual.size(), is(expected));
    }

}
