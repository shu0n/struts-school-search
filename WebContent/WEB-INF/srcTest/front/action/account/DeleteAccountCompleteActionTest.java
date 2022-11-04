package front.action.account;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsRegex.*;
import static test.property.PropertyTester.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.account.FrontDeleteAccountActionForm;
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

        // テスト時に生成されるメールログのファイル名を格納するためのリストを生成する。
        private List<String> fileNameList = new ArrayList<>();

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        public void tearDown() throws Exception {
            super.tearDown();
            String mailLogPath = getProperty("environment.properties").getProperty("mail.log.path");
            // テスト時に生成されたメールログを削除する。
            if(!fileNameList.isEmpty()) {
                for(String fileName: fileNameList) {
                    Path filePath = Paths.get(mailLogPath + fileName);
                    Files.delete(filePath);
                }
            }
            fileNameList = new ArrayList<>();
        }

        @Test
        public void testExecuteToForwardSuccess() throws Exception {
            FrontDeleteAccountActionForm inForm = new FrontDeleteAccountActionForm();
            inForm.setAccountId(1);
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("success");

            String mailLogPath = getProperty("environment.properties").getProperty("mail.log.path");
            String expectedLog
                    = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3} "
                    + "To:tanaka@example.com Subject:School Serachから退会しました。";

            // 生成されたメールログの配列を取得する。
            File mailLogDirectory = new File(mailLogPath);
            FilenameFilter searchFilter = new FilenameFilter() {
                public boolean accept(File file, String str){
                    if (str.indexOf("mail.") != -1){
                        return true;
                    } else {
                        return false;
                    }
                }
            };
            File[] mailLogFileArray = mailLogDirectory.listFiles(searchFilter);
            BufferedReader br = new BufferedReader(new FileReader(mailLogFileArray[0]));
            String actualLog = br.readLine();
            br.close();
            fileNameList.add("/" + mailLogFileArray[0].getName());

            assertThat(actualLog, is(matchPatternAs(expectedLog)));

            FrontDeleteAccountActionForm actualForm
                    = (FrontDeleteAccountActionForm) getSession().getAttribute("FrontDeleteAccountActionForm");

            assertThat(actualForm, is(nullValue()));

            // 期待値を記載したデータセットを編集して比較用のアカウントテーブル情報を取得する。
            IDataSet expectedAccountDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.DeleteAccountCompleteActionTest.testExecuteToForwardSuccess.accountsTable.xml"));
            ReplacementDataSet replacementAccountDataSet = new ReplacementDataSet(expectedAccountDataSet);
            replacementAccountDataSet.addReplacementObject("[null]", null);
            ITable expectedAccountsTable = replacementAccountDataSet.getTable("accounts");

            // 実際のアカウントテーブル情報の取得に必要な変数を生成する。
            String tableNameForAccountsTable = "accounts";
            String sqlQueryForAccountsTable = "SELECT * FROM accounts WHERE account_id=1";
            String[] ignoreColsForAccountsTable = {"account_created_at", "account_updated_at", "account_deleted_at"};

            Assertion.assertEqualsByQuery(
                    expectedAccountsTable,
                    dbUnitTester.getConnection(),
                    tableNameForAccountsTable,
                    sqlQueryForAccountsTable,
                    ignoreColsForAccountsTable);

            // 期待値を記載したデータセットを編集して比較用の申込テーブル情報を取得する。
            IDataSet expectedEntryDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.DeleteAccountCompleteActionTest.testExecuteToForwardSuccess.entriesTable.xml"));
            ReplacementDataSet replacementEntryDataSet = new ReplacementDataSet(expectedEntryDataSet);
            replacementEntryDataSet.addReplacementObject("[null]", null);
            ITable expectedEntriesTable = replacementEntryDataSet.getTable("entries");

            // 実際の申込テーブル情報の取得に必要な変数を生成する。
            String tableNameForEntriesTable = "entries";
            String sqlQueryForEntriesTable = "SELECT * FROM entries WHERE applicant_account_id=1";
            String[] ignoreColsForEntroesTable = {"entried_at", "entry_updated_at", "entry_deleted_at"};

            Assertion.assertEqualsByQuery(
                    expectedEntriesTable,
                    dbUnitTester.getConnection(),
                    tableNameForEntriesTable,
                    sqlQueryForEntriesTable,
                    ignoreColsForEntroesTable);
        }

        @Test
        public void testExecuteToForwardInvalid() {
            FrontDeleteAccountActionForm inForm = new FrontDeleteAccountActionForm();
            inForm.setAccountId(3);
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            actionPerform();

            verifyForward("invalid");
        }

        @Test
        public void testExecuteInCaseReferredBySchoolException() {
            FrontDeleteAccountActionForm inForm = new FrontDeleteAccountActionForm();
            inForm.setAccountId(2);
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("redo");
            verifyActionErrors(new String[] {
                    "スクールの登録者アカウントに登録されています。"
                    + "スクールを1件以上登録している場合は退会できません。"});
        }

    }

}
