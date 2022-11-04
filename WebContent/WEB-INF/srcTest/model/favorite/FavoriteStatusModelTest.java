package model.favorite;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.property.PropertyTester.*;

import org.junit.ClassRule;
import org.junit.Test;

import test.db.DbUnitTester;

public class FavoriteStatusModelTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.FavoriteStatusModelTest.xml") {
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
    public void testIsFavoriteAddedInCaseFavoriteAdded() throws Exception {
        FavoriteStatusModel sut = new FavoriteStatusModel();
        boolean actual = sut.isFavoriteAdded(1, 2);

        assertThat(actual, is(true));
    }

    @Test
    public void testIsFavoriteAddedInCaseFavoriteNotAdded() throws Exception {
        FavoriteStatusModel sut = new FavoriteStatusModel();
        boolean actual = sut.isFavoriteAdded(2, 1);

        assertThat(actual, is(false));
    }

}
