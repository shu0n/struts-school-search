package dao.prefecture;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.PrefectureActionForm;
import dao.prefecture.sql.PrefectureSelectWhereActionForm;
import test.db.DbUnitTester;

public class SelectPrefectureDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SelectPrefectureDAOTest.xml") {
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
    public void testSelectMatchedPrefectureToGetAllPrefecture() throws Exception {
        String[] expected = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        SelectPrefectureDAO sut = new SelectPrefectureDAO();
        PrefectureSelectWhereActionForm whereForm = new PrefectureSelectWhereActionForm();
        List<PrefectureActionForm> formList = sut.selectMatchedPrefecture(whereForm);
        String[] actual = new String[10];
        for(int i = 0; i < formList.size(); i++) {
            actual[i] = String.valueOf(formList.get(i).getPrefectureId());
        }

        assertThat(actual, is(arrayContainingInAnyOrder(expected)));
    }

    @Test
    public void testSelectMatchedPrefectureToGetOnePrefectureByPrefectureId() throws Exception {
        PrefectureActionForm expected =new PrefectureActionForm();
        expected.setPrefectureId(6);
        expected.setPrefectureName("山形県");

        SelectPrefectureDAO sut = new SelectPrefectureDAO();
        PrefectureSelectWhereActionForm whereForm = new PrefectureSelectWhereActionForm();
        whereForm.setPrefectureId(6);
        PrefectureActionForm actual = sut.selectMatchedPrefecture(whereForm).get(0);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testSelectMatchedPrefectureToGetOnePrefecturePrefectureName() throws Exception {
        int expected = 1;

        SelectPrefectureDAO sut = new SelectPrefectureDAO();
        PrefectureSelectWhereActionForm whereForm = new PrefectureSelectWhereActionForm();
        whereForm.setPrefectureName("山形県");
        List<PrefectureActionForm> actual = sut.selectMatchedPrefecture(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedPrefectureToGetNoPrefecturePrefectureName() throws Exception {
        int expected = 0;

        SelectPrefectureDAO sut = new SelectPrefectureDAO();
        PrefectureSelectWhereActionForm whereForm = new PrefectureSelectWhereActionForm();
        whereForm.setPrefectureName("山型県");
        List<PrefectureActionForm> actual = sut.selectMatchedPrefecture(whereForm);

        assertThat(actual.size(), is(expected));
    }

}
