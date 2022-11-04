package dao.favorite;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.FavoriteExtendActionForm;
import dao.favorite.sql.FavoriteSelectJoinWhereActionForm;
import test.db.DbUnitTester;

public class SelectFavoriteJoinDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SelectFavoriteJoinDAOTest.xml") {
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
        String[] expected = {"14", "24", "25", "36"};

        SelectFavoriteJoinDAO sut = new SelectFavoriteJoinDAO();
        FavoriteSelectJoinWhereActionForm whereForm = new FavoriteSelectJoinWhereActionForm();
        List<FavoriteExtendActionForm> formList = sut.selectMatchedFavorite(whereForm);
        String[] actual = new String[4];
        for(int i = 0; i < formList.size(); i++) {
            actual[i] = String.valueOf(formList.get(i).getFavoriteId());
        }

        assertThat(actual, is(arrayContaining(expected)));
    }

    @Test
    public void testSelectMatchedFavoriteByRegistrantAccountToGetOneFavoriteByFavoriteId() throws Exception {
        FavoriteExtendActionForm expected = new FavoriteExtendActionForm();
        expected.setFavoriteId(14);
        expected.setAccountId(1);
        expected.setSchoolId(4);
        expected.setSchoolName("テストスクール1");
        expected.setSchoolCategoryId(1);
        expected.setSchoolCategoryName("文化");
        expected.setRegistrantAccountId(3);
        expected.setRegistrantLastName("田中");
        expected.setRegistrantFirstName("三郎");
        expected.setSchoolSummary("スクール概要1");
        expected.setSchoolFee(new BigDecimal("1000"));
        expected.setSchoolPrefectureId(11);
        expected.setSchoolPrefectureName("京都府");
        expected.setSummaryImageFileName("image1.png");
        expected.setRegistrantAdminId(0);
        expected.setFavoriteAddedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));

        SelectFavoriteJoinDAO sut = new SelectFavoriteJoinDAO();
        FavoriteSelectJoinWhereActionForm whereForm = new FavoriteSelectJoinWhereActionForm();
        whereForm.setFavoriteId(14);
        FavoriteExtendActionForm actual = sut.selectMatchedFavorite(whereForm).get(0);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testSelectMatchedFavoriteByRegistrantAdminToGetOneFavoriteByFavoriteId() throws Exception {
        FavoriteExtendActionForm expected = new FavoriteExtendActionForm();
        expected.setFavoriteId(36);
        expected.setAccountId(3);
        expected.setSchoolId(6);
        expected.setSchoolName("テストスクール3");
        expected.setSchoolCategoryId(1);
        expected.setSchoolCategoryName("文化");
        expected.setRegistrantAccountId(0);
        expected.setRegistrantLastName(null);
        expected.setRegistrantFirstName(null);
        expected.setSchoolSummary("スクール概要3");
        expected.setSchoolFee(new BigDecimal("1000"));
        expected.setSchoolPrefectureId(11);
        expected.setSchoolPrefectureName("京都府");
        expected.setSummaryImageFileName("image3.png");
        expected.setRegistrantAdminId(31);
        expected.setFavoriteAddedAt(Timestamp.valueOf("2022-03-04 05:06:07.891"));

        SelectFavoriteJoinDAO sut = new SelectFavoriteJoinDAO();
        FavoriteSelectJoinWhereActionForm whereForm = new FavoriteSelectJoinWhereActionForm();
        whereForm.setFavoriteId(36);
        FavoriteExtendActionForm actual = sut.selectMatchedFavorite(whereForm).get(0);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testSelectMatchedFavoriteToGetOneFavoriteByMultipleCondition() throws Exception {
        int expected = 1;

        SelectFavoriteJoinDAO sut = new SelectFavoriteJoinDAO();
        FavoriteSelectJoinWhereActionForm whereForm = new FavoriteSelectJoinWhereActionForm();
        whereForm.setAccountId(2);
        whereForm.setSchoolId(5);
        List<FavoriteExtendActionForm> actual = sut.selectMatchedFavorite(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedFavoriteToGetNoFavorite() throws Exception {
        int expected = 0;

        SelectFavoriteJoinDAO sut = new SelectFavoriteJoinDAO();
        FavoriteSelectJoinWhereActionForm whereForm = new FavoriteSelectJoinWhereActionForm();
        whereForm.setAccountId(1);
        whereForm.setSchoolId(6);
        List<FavoriteExtendActionForm> actual = sut.selectMatchedFavorite(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedFavoriteToGetTwoFavorite() throws Exception {
        int expected = 2;

        SelectFavoriteJoinDAO sut = new SelectFavoriteJoinDAO();
        FavoriteSelectJoinWhereActionForm whereForm = new FavoriteSelectJoinWhereActionForm();
        whereForm.setAccountId(2);
        List<FavoriteExtendActionForm> actual = sut.selectMatchedFavorite(whereForm);

        assertThat(actual.size(), is(expected));
    }

}
