package dao.account;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import dao.account.sql.AccountUpdateDataActionForm;
import test.db.DbUnitTester;

public class UpdateAccountDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.UpdateAccountDAOTest.xml") {
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
    public void testUpdateAccountToUpdateOneAccount() throws Exception {
        UpdateAccountDAO sut = new UpdateAccountDAO() {
            String getStringCurrentTimestamp() {
                return "2022-03-04 05:06:07.891";
            }
        };
        AccountUpdateDataActionForm dataForm = new AccountUpdateDataActionForm();
        dataForm.setAccountId(1);
        dataForm.setLastName("更新山田");
        dataForm.setFirstName("更新花子");
        dataForm.setLastNameKana("コウシンヤマダ");
        dataForm.setFirstNameKana("コウシンハナコ");
        dataForm.setSexId(3);
        dataForm.setBirthDate(Timestamp.valueOf("1991-02-03 00:00:00"));
        dataForm.setPrefectureId(4);
        dataForm.setMailAddress("updateYamada@example.com");
        dataForm.setProfileImageFileName("updateImage.png");
        dataForm.setSelfIntroduction("更新自己紹介です。");
        dataForm.setPassword("updatePbssword");
        dataForm.setAccountStatus("1");
        dataForm.setActivateToken("updateActivatetoken");
        dataForm.setActivateEffectiveDate(Timestamp.valueOf("2022-02-03 04:05:06.789"));
        dataForm.setReissueToken("updateReissuetoken");
        dataForm.setReissueEffctiveDate(Timestamp.valueOf("2022-02-04 05:06:07.891"));
        boolean actual = sut.updateAccount(dataForm);

        assertThat(actual, is(true));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.UpdateAccountDAOTest.testUpdateAccountToUpdateOneAccount.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("accounts");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "accounts";
        String sqlQuery = "SELECT * FROM accounts WHERE account_id=1";
        String[] ignoreCols = {};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test
    public void testUpdateAccountToSetNull() throws Exception {
        UpdateAccountDAO sut = new UpdateAccountDAO() {
            String getStringCurrentTimestamp() {
                return "2022-03-04 05:06:07.891";
            }
        };
        AccountUpdateDataActionForm dataForm = new AccountUpdateDataActionForm();
        dataForm.setAccountId(2);
        dataForm.setBirthDateToNullFlag(true);
        dataForm.setActivateTokenToToNullFlag(true);
        dataForm.setActivateEffectiveDateToNullFlag(true);
        dataForm.setReissueTokenToNullFlag(true);
        dataForm.setReissueEffctiveDateToNULLFlag(true);
        boolean actual = sut.updateAccount(dataForm);

        assertThat(actual, is(true));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.UpdateAccountDAOTest.testUpdateAccountToSetNull.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("accounts");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "accounts";
        String sqlQuery = "SELECT * FROM accounts WHERE account_id=2";
        String[] ignoreCols = {};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test
    public void testUpdateAccountToUpdateNoAccount() throws Exception {
        UpdateAccountDAO sut = new UpdateAccountDAO();
        AccountUpdateDataActionForm dataForm = new AccountUpdateDataActionForm();
        dataForm.setAccountId(3);
        dataForm.setLastName("佐藤");
        dataForm.setFirstName("次郎");
        dataForm.setLastNameKana("サトウ");
        dataForm.setFirstNameKana("ジロウ");
        dataForm.setSexId(1);
        dataForm.setBirthDate(Timestamp.valueOf("1992-03-04 00:00:00"));
        dataForm.setPrefectureId(1);
        dataForm.setMailAddress("satou@example.com");
        dataForm.setProfileImageFileName("imagf.png");
        dataForm.setSelfIntroduction("自己紹介でございます。");
        dataForm.setPassword("qassword");
        dataForm.setAccountStatus("1");
        dataForm.setActivateToken("adtivatetoken");
        dataForm.setActivateEffectiveDate(Timestamp.valueOf("2022-02-03 04:05:06.789"));
        dataForm.setReissueToken("rfissuetoken");
        dataForm.setReissueEffctiveDate(Timestamp.valueOf("2022-02-04 05:06:07.891"));
        boolean actual = sut.updateAccount(dataForm);

        assertThat(actual, is(false));
    }

}
