package dao.contact;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.property.PropertyTester.*;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import dao.contact.sql.ContactInsertDataActionForm;
import test.db.DbUnitTester;

public class InsertContactDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.InsertContactDAOTest.xml") {
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
    public void testInsertContactToCreateOneContact() throws Exception {
        InsertContactDAO sut = new InsertContactDAO();
        ContactInsertDataActionForm dataForm = new ContactInsertDataActionForm();
        dataForm.setContactAccountId(1);
        dataForm.setContactLastName("田中中");
        dataForm.setContactFirstName("太郎郎");
        dataForm.setContactLastNameKana("タナカナカ");
        dataForm.setContactFirstNameKana("タロウロウ");
        dataForm.setContactMailAddress("tanakanaka@example.com");
        dataForm.setContactContent("問い合わせ3");
        dataForm.setContactStatusId(3);
        int actual = sut.insertContact(dataForm);

        assertThat(actual, is(not(0)));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.InsertContactDAOTest.testInsertContactToCreateOneContact.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("contacts");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "contacts";
        String sqlQuery = "SELECT * FROM contacts WHERE contact_account_id=1";
        String[] ignoreCols = {"contact_id", "contacted_at", "contact_updated_at"};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

}
