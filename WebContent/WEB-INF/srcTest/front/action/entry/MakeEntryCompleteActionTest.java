package front.action.entry;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.nullValue;
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

import front.actionform.entry.FrontMakeEntryActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class MakeEntryCompleteActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.MakeEntryCompleteActionTest.xml") {
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

        private static final String ACTION_PATH = "/makeEntryComplete.do";
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
        public void testFixToForwardFix() {
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "修正する");
            actionPerform();

            verifyForward("fix");
        }

        @Test
        public void testFixToForwardInvalid() {
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            addRequestParameter("dispatch", "修正する");
            actionPerform();

            verifyForward("invalid");
        }

        @Test
        public void testSendToForwardMakeInCaseEntrySchoolRegisteredByAccount() throws Exception {
            FrontMakeEntryActionForm inForm = new FrontMakeEntryActionForm();
            inForm.setEntrySchoolId(41);
            inForm.setRegistrantAccountId(21);
            inForm.setRegistrantAdminId(0);
            inForm.setApplicantAccountId(22);
            inForm.setEntryQuestion("申込の質問です。");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "申し込む");
            actionPerform();

            verifyForward("make");

            String mailLogPath = getProperty("environment.properties").getProperty("mail.log.path");
            String expectedFirstLog
                    = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3} "
                    + "To:tanaka2@example.com Subject:スクールへの申込が完了しました。";
            String expectedSecondLog
                    = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3} "
                    + "To:tanaka1@example.com Subject:スクールに申込がありました。";

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
            String actualFirstLog = br.readLine();
            String actualSecondLog = br.readLine();
            br.close();
            fileNameList.add("/" + mailLogFileArray[0].getName());

            assertThat(actualFirstLog, is(matchPatternAs(expectedFirstLog)));
            assertThat(actualSecondLog, is(matchPatternAs(expectedSecondLog)));

            FrontMakeEntryActionForm actualForm = (FrontMakeEntryActionForm) getActionForm();

            assertThat(actualForm, is(nullValue()));

            // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.MakeEntryCompleteActionTest.testMakeToForwardMakeInCaseEntrySchoolRegisteredByAccount.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("entries");

            // 実際のテーブル情報の取得に必要な変数を生成する。
            String tableName = "entries";
            String sqlQuery = "SELECT * FROM entries WHERE entry_school_id=41";
            String[] ignoreCols =
                    {"entry_id", "entried_at", "entry_updated_at", "entry_delete_flag", "entry_deleted_at"};

            Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
        }

        @Test
        public void testSendToForwardMakeInCaseEntrySchoolRegisteredByAdmin() throws Exception {
            FrontMakeEntryActionForm inForm = new FrontMakeEntryActionForm();
            inForm.setEntrySchoolId(42);
            inForm.setRegistrantAccountId(0);
            inForm.setRegistrantAdminId(61);
            inForm.setApplicantAccountId(23);
            inForm.setEntryQuestion("申込に伴う質問です。");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "申し込む");
            actionPerform();

            verifyForward("make");

            String mailLogPath = getProperty("environment.properties").getProperty("mail.log.path");
            String expectedFirstLog
                    = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3} "
                    + "To:tanaka3@example.com Subject:スクールへの申込が完了しました。";
            String expectedSecondLog
                    = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3} "
                    + "To:system@example.com Subject:スクールに申込がありました。";

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
            String actualFirstLog = br.readLine();
            String actualSecondLog = br.readLine();
            br.close();
            fileNameList.add("/" + mailLogFileArray[0].getName());

            assertThat(actualFirstLog, is(matchPatternAs(expectedFirstLog)));
            assertThat(actualSecondLog, is(matchPatternAs(expectedSecondLog)));

            FrontMakeEntryActionForm actualForm = (FrontMakeEntryActionForm) getActionForm();

            assertThat(actualForm, is(nullValue()));

            // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.MakeEntryCompleteActionTest.testMakeToForwardMakeInCaseEntrySchoolRegisteredByAdmin.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("entries");

            // 実際のテーブル情報の取得に必要な変数を生成する。
            String tableName = "entries";
            String sqlQuery = "SELECT * FROM entries WHERE entry_school_id=42";
            String[] ignoreCols =
                    {"entry_id", "entried_at", "entry_updated_at", "entry_delete_flag", "entry_deleted_at"};

            Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
        }

        @Test
        public void testSendToForwardInvalid() throws Exception {
            FrontMakeEntryActionForm inForm = new FrontMakeEntryActionForm();
            inForm.setEntrySchoolId(42);
            inForm.setApplicantAccountId(22);
            inForm.setEntryQuestion("申込時の質問です。");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            addRequestParameter("dispatch", "申し込む");
            actionPerform();

            verifyForward("invalid");
        }

    }

}
