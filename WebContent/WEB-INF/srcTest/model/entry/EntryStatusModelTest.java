package model.entry;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.property.PropertyTester.*;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import test.db.DbUnitTester;

public class EntryStatusModelTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.EntryStatusModelTest.xml") {
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
    public void testIsEntriedInCaseEntryExistNotCanceled() throws Exception{
        EntryStatusModel sut = new EntryStatusModel();
        boolean actual = sut.isEntried(1, 5);

        assertThat(actual, is(true));
    }

    @Test
    public void testIsEntriedInCaseNoEntryExistNotCanceled() throws Exception{
        EntryStatusModel sut = new EntryStatusModel();
        boolean actual = sut.isEntried(3, 1);

        assertThat(actual, is(false));
    }

    @Test
    public void testIsApplicantInCaseNoEntryExist() throws Exception{
        EntryStatusModel sut = new EntryStatusModel();
        boolean actual = sut.isApplicant(12, 1);

        assertThat(actual, is(false));
    }

    @Test
    public void testIsApplicantInCaseNotApplicant() throws Exception{
        EntryStatusModel sut = new EntryStatusModel();
        boolean actual = sut.isApplicant(9, 3);

        assertThat(actual, is(false));
    }

    @Test
    public void testIsApplicantInCaseApplicant() throws Exception{
        EntryStatusModel sut = new EntryStatusModel();
        boolean actual = sut.isApplicant(9, 4);

        assertThat(actual, is(true));
    }

    @Test
    public void testIsCanceledInCaseNoEntry() throws Exception{
        EntryStatusModel sut = new EntryStatusModel();
        boolean actual = sut.isCanceled(13);

        assertThat(actual, is(false));
    }

    @Test
    public void testIsCanceledInCaseEntryIsNotCanceled() throws Exception{
        EntryStatusModel sut = new EntryStatusModel();
        boolean actual = sut.isCanceled(11);

        assertThat(actual, is(false));
    }

    @Test
    public void testIsCanceledInCaseEntryIsCanceled() throws Exception{
        EntryStatusModel sut = new EntryStatusModel();
        boolean actual = sut.isCanceled(10);

        assertThat(actual, is(true));
    }

    @Test
    public void testGetEtriedStatusIdToGetEntryStatusId() throws Exception{
        int expected = 1;

        EntryStatusModel sut = new EntryStatusModel();
        int actual = sut.getEtriedStatusId();

        assertThat(actual, is(expected));
    }

    @Test
    public void testGetCancelStatusIdToGetEntryStatusId() throws Exception{
        int expected = 3;

        EntryStatusModel sut = new EntryStatusModel();
        int actual = sut.getCancelStatusId();

        assertThat(actual, is(expected));
    }

    @Test
    public void testUpdateStatusContactedToUpdateStatus() throws Exception{
        EntryStatusModel sut = new EntryStatusModel();
        boolean actual = sut.updateStatusContacted(8);

        assertThat(actual, is(true));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.EntryStatusModelTest.testUpdateStatusContactedToUpdateStatus.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("entries");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "entries";
        String sqlQuery = "SELECT * FROM entries WHERE entry_id=8";
        String[] ignoreCols = {"entried_at", "entry_updated_at", "entry_delete_flag", "entry_deleted_at"};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

}
