package dao.sex;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.SexActionForm;
import dao.sex.sql.SexSelectWhereActionForm;
import test.db.DbUnitTester;

public class SelectSexDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SelectSexDAOTest.xml") {
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
    public void testSelectMatchedSexToGetAllSex() throws Exception {
        String[] expected = {"1", "2", "3"};

        SelectSexDAO sut = new SelectSexDAO();
        SexSelectWhereActionForm whereForm = new SexSelectWhereActionForm();
        List<SexActionForm> formList = sut.selectMatchedSex(whereForm);
        String[] actual = new String[3];
        for(int i = 0; i < formList.size(); i++) {
            actual[i] = String.valueOf(formList.get(i).getSexId());
        }

        assertThat(actual, is(arrayContainingInAnyOrder(expected)));
    }

    @Test
    public void testSelectMatchedSexToGetOneSexBySexId() throws Exception {
        SexActionForm expected = new SexActionForm();
        expected.setSexId(3);
        expected.setSexName("その他");

        SelectSexDAO sut = new SelectSexDAO();
        SexSelectWhereActionForm whereForm = new SexSelectWhereActionForm();
        whereForm.setSexId(3);
        SexActionForm actual = sut.selectMatchedSex(whereForm).get(0);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testSelectMatchedSexToGetOneSexBySexName() throws Exception {
        int expected = 1;

        SelectSexDAO sut = new SelectSexDAO();
        SexSelectWhereActionForm whereForm = new SexSelectWhereActionForm();
        whereForm.setSexName("男性");
        List<SexActionForm> actual = sut.selectMatchedSex(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedSexToGetNoSexBySexName() throws Exception {
        int expected = 0;

        SelectSexDAO sut = new SelectSexDAO();
        SexSelectWhereActionForm whereForm = new SexSelectWhereActionForm();
        whereForm.setSexName("中性");
        List<SexActionForm> actual = sut.selectMatchedSex(whereForm);

        assertThat(actual.size(), is(expected));
    }

}
