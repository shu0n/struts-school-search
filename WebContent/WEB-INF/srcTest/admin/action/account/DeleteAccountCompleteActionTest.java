package admin.action.account;

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
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import admin.actionform.account.AdminDeleteAccountActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class DeleteAccountCompleteActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.DeleteAccountCompleteActionTest.xml") {
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

    public static class executeTest extends MockStrutsTestCase {

        private static final String ACTION_PATH = "/deleteAccountComplete.do";
        private static final String VALID_TOKEN = "validToken";
        private static final String INVALID_TOKEN = "invalidToken";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccess() throws Exception {
            AdminDeleteAccountActionForm inForm = new AdminDeleteAccountActionForm();
            inForm.setAccountId(1);
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("success");

            AdminDeleteAccountActionForm actual
                    = (AdminDeleteAccountActionForm) getSession().getAttribute("AdminDeleteAccountActionForm");

            assertThat(actual, is(nullValue()));

            // 期待値を記載したデータセットを編集して比較用のアカウントテーブル情報を取得する。
            IDataSet expectedAccountDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.DeleteAccountCompleteActionTest.testExecuteToForwardSuccess.accountsTable.xml"));
            ReplacementDataSet replacementAccountDataSet = new ReplacementDataSet(expectedAccountDataSet);
            replacementAccountDataSet.addReplacementObject("[null]", null);
            ITable expectedAccountTable = replacementAccountDataSet.getTable("accounts");

            // 実際のアカウントテーブル情報の取得に必要な変数を生成する。
            String tableNameForAccountsTable = "accounts";
            String sqlQueryForAccountsTable = "SELECT * FROM accounts WHERE account_id=1";
            String[] ignoreColsForAccountsTable = {"account_created_at", "account_updated_at", "account_deleted_at"};

            Assertion.assertEqualsByQuery(
                    expectedAccountTable,
                    dbUnitTester.getConnection(),
                    tableNameForAccountsTable,
                    sqlQueryForAccountsTable,
                    ignoreColsForAccountsTable);

            // 期待値を記載したデータセットを編集して比較用の申込テーブル情報を取得する。
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.DeleteAccountCompleteActionTest.testExecuteToForwardSuccess.entriesTable.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("entries");

            // 実際の申込テーブル情報の取得に必要な変数を生成する。
            String tableNameForEntriesTable = "entries";
            String sqlQueryForEntriesTable = "SELECT * FROM entries WHERE applicant_account_id=1";
            String[] ignoreColsForEntroesTable = {"entried_at", "entry_updated_at", "entry_deleted_at"};

            Assertion.assertEqualsByQuery(
                    expectedTable,
                    dbUnitTester.getConnection(),
                    tableNameForEntriesTable,
                    sqlQueryForEntriesTable,
                    ignoreColsForEntroesTable);
        }

        @Test
        public void testExecuteToForwardInvalid() {
            AdminDeleteAccountActionForm inForm = new AdminDeleteAccountActionForm();
            inForm.setAccountId(3);
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            actionPerform();

            verifyForward("invalid");
        }

        @Test
        public void testExecuteInCaseReferredBySchoolException() {
            AdminDeleteAccountActionForm inForm = new AdminDeleteAccountActionForm();
            inForm.setAccountId(2);
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForwardPath("/deleteAccountConfirm.do?accountId=2");
            verifyActionErrors(new String[] {
                    "スクールの登録者アカウントに登録されています。"
                    + "削除するためには当該アカウントで登録しているスクールを削除してください。"});
        }

    }

}
