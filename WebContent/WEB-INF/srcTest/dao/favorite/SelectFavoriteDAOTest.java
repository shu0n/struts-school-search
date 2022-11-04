package dao.favorite;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.FavoriteActionForm;
import dao.favorite.sql.FavoriteSelectWhereActionForm;
import test.db.DbUnitTester;

public class SelectFavoriteDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SelectFavoriteDAOTest.xml") {
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
    public void testSelectMatchedFavoriteToGetAllFavorite() throws Exception {
        String[] expected = {"16", "25", "26", "34"};

        SelectFavoriteDAO sut = new SelectFavoriteDAO();
        FavoriteSelectWhereActionForm whereForm = new FavoriteSelectWhereActionForm();
        List<FavoriteActionForm> formList = sut.selectMatchedFavorite(whereForm);
        String[] actual = new String[4];
        for(int i = 0; i < formList.size(); i++) {
            actual[i] = String.valueOf(formList.get(i).getFavoriteId());
        }

        assertThat(actual, is(arrayContaining(expected)));
    }

    @Test
    public void testSelectMatchedFavoriteToGetOneFavoriteByFavoriteId() throws Exception {
        FavoriteActionForm expected = new FavoriteActionForm();
        expected.setFavoriteId(34);
        expected.setAccountId(3);
        expected.setSchoolId(4);
        expected.setFavoriteAddedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));

        SelectFavoriteDAO sut = new SelectFavoriteDAO();
        FavoriteSelectWhereActionForm whereForm = new FavoriteSelectWhereActionForm();
        whereForm.setFavoriteId(34);
        FavoriteActionForm actual = sut.selectMatchedFavorite(whereForm).get(0);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testSelectMatchedFavoriteToGetOneFavoriteByMultipleCondition() throws Exception {
        int expected = 1;

        SelectFavoriteDAO sut = new SelectFavoriteDAO();
        FavoriteSelectWhereActionForm whereForm = new FavoriteSelectWhereActionForm();
        whereForm.setAccountId(2);
        whereForm.setSchoolId(5);
        List<FavoriteActionForm> actual = sut.selectMatchedFavorite(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedFavoriteToGetNoFavorite() throws Exception {
        int expected = 0;

        SelectFavoriteDAO sut = new SelectFavoriteDAO();
        FavoriteSelectWhereActionForm whereForm = new FavoriteSelectWhereActionForm();
        whereForm.setAccountId(2);
        whereForm.setSchoolId(4);
        List<FavoriteActionForm> actual = sut.selectMatchedFavorite(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedFavoriteToGetTwoFavorite() throws Exception {
        int expected = 2;

        SelectFavoriteDAO sut = new SelectFavoriteDAO();
        FavoriteSelectWhereActionForm whereForm = new FavoriteSelectWhereActionForm();
        whereForm.setAccountId(2);
        List<FavoriteActionForm> actual = sut.selectMatchedFavorite(whereForm);

        assertThat(actual.size(), is(expected));
    }

}
