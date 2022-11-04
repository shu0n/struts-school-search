package dao.favorite;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.property.PropertyTester.*;

import java.sql.SQLException;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import dao.favorite.sql.FavoriteInsertDataActionForm;
import test.db.DbUnitTester;

public class InsertFavoriteDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.InsertFavoriteDAOTest.xml") {
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
    public void testInsertFavoriteToCreateOneFavorite() throws Exception {
        int expected = 23;

        InsertFavoriteDAO sut = new InsertFavoriteDAO();
        FavoriteInsertDataActionForm dataForm = new FavoriteInsertDataActionForm();
        dataForm.setAccountId(2);
        dataForm.setSchoolId(3);
        int actual = sut.insertFavorite(dataForm);

        assertThat(actual, is(expected));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.InsertFavoriteDAOTest.testInsertFavoriteToCreateOneFavorite.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("favorites");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "favorites";
        String sqlQuery = "SELECT * FROM favorites WHERE favorite_id=23";
        String[] ignoreCols = {"favorite_added_at"};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test(expected = SQLException.class)
    public void testInsertFavoriteToCreateNoFavorite() throws Exception {
        InsertFavoriteDAO sut = new InsertFavoriteDAO();
        FavoriteInsertDataActionForm dataForm = new FavoriteInsertDataActionForm();
        dataForm.setAccountId(1);
        dataForm.setSchoolId(4);
        sut.insertFavorite(dataForm);
    }

}
