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

import dao.contact.sql.ContactUpdateDataActionForm;
import test.db.DbUnitTester;

public class UpdateContactDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.UpdateContactDAOTest.xml") {
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
    public void testUpdateContactToUpdateOneContact() throws Exception {
        UpdateContactDAO sut = new UpdateContactDAO() {
            String getStringCurrentTimestamp() {
                return "2022-03-04 05:06:07.891";
            }
        };
        ContactUpdateDataActionForm dataForm = new ContactUpdateDataActionForm();
        dataForm.setContactId(3);
        dataForm.setContactAccountId(2);
        dataForm.setContactLastName("佐藤藤");
        dataForm.setContactFirstName("次郎郎");
        dataForm.setContactLastNameKana("サトウトウ");
        dataForm.setContactFirstNameKana("ジロウロウ");
        dataForm.setContactMailAddress("satoto@example.com");
        dataForm.setContactContent("更新問い合わせ3");
        dataForm.setContactStatusId(1);
        boolean actual = sut.updateContact(dataForm);

        assertThat(actual, is(true));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.UpdateContactDAOTest.testUpdateContactToUpdateOneContact.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("contacts");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "contacts";
        String sqlQuery = "SELECT * FROM contacts WHERE contact_id=3";
        String[] ignoreCols = {};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test
    public void testUpdateContactToUpdateNoContact() throws Exception {
        UpdateContactDAO sut = new UpdateContactDAO() {
            String getStringCurrentTimestamp() {
                return "2022-03-04 05:06:07.891";
            }
        };
        ContactUpdateDataActionForm dataForm = new ContactUpdateDataActionForm();
        dataForm.setContactId(4);
        dataForm.setContactAccountId(3);
        dataForm.setContactLastName("高橋橋");
        dataForm.setContactFirstName("三郎郎");
        dataForm.setContactLastNameKana("タカハシハシ");
        dataForm.setContactFirstNameKana("サブロウロウ");
        dataForm.setContactMailAddress("takahashihashi@example.com");
        dataForm.setContactContent("更新問い合わせ4");
        dataForm.setContactStatusId(2);
        boolean actual = sut.updateContact(dataForm);

        assertThat(actual, is(false));
    }

}
