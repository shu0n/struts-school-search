package front.action.contact;

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

import front.actionform.contact.FrontMakeContactActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class MakeContactCompleteActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.MakeContactCompleteActionTest.xml") {
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

        private static final String ACTION_PATH = "/makeContactComplete.do";
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
            FrontMakeContactActionForm inForm = new FrontMakeContactActionForm();
            inForm.setContactAccountId(1);
            inForm.setContactLastName("田中");
            inForm.setContactFirstName("太郎");
            inForm.setContactLastNameKana("タナカ");
            inForm.setContactFirstNameKana("タロウ");
            inForm.setContactMailAddress("tanaka@example.com");
            inForm.setContactContent("問い合わせです。");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "修正する");
            actionPerform();

            verifyForward("fix");
        }

        @Test
        public void testFixToForwardInvalid() {
            FrontMakeContactActionForm inForm = new FrontMakeContactActionForm();
            inForm.setContactAccountId(1);
            inForm.setContactLastName("田中");
            inForm.setContactFirstName("太郎");
            inForm.setContactLastNameKana("タナカ");
            inForm.setContactFirstNameKana("タロウ");
            inForm.setContactMailAddress("tanaka@example.com");
            inForm.setContactContent("問い合わせです。");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            addRequestParameter("dispatch", "修正する");
            actionPerform();

            verifyForward("invalid");
        }

        @Test
        public void testSendToForwardSendInCaseContactByNonAccount() throws Exception {
            FrontMakeContactActionForm inForm = new FrontMakeContactActionForm();
            inForm.setContactAccountId(0);
            inForm.setContactLastName("佐藤");
            inForm.setContactFirstName("次郎");
            inForm.setContactLastNameKana("サトウ");
            inForm.setContactFirstNameKana("ジロウ");
            inForm.setContactMailAddress("sato@example.com");
            inForm.setContactContent("非会員からの問い合わせです。");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "送信する");
            actionPerform();

            verifyForward("send");

            String mailLogPath = getProperty("environment.properties").getProperty("mail.log.path");
            String expectedFirstLog
                    = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3} "
                    + "To:sato@example.com Subject:お問合せを受け付けました。";
            String expectedSecondLog
                    = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3} "
                    + "To:user@mail.example.com Subject:お問合せがありました。";

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

            FrontMakeContactActionForm actualForm = (FrontMakeContactActionForm) getActionForm();

            assertThat(actualForm, is(nullValue()));

            // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.MakeContactCompleteActionTest.testSendToForwardSendInCaseContactByNonAccount.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("contacts");

            // 実際のテーブル情報の取得に必要な変数を生成する。
            String tableName = "contacts";
            String sqlQuery = "SELECT * FROM contacts WHERE contact_mail_address='sato@example.com'";
            String[] ignoreCols =
                    {"contact_id", "contacted_at", "contact_updated_at", "contact_delete_flag", "contact_deleted_at"};

            Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
        }

        @Test
        public void testSendToForwardSendInCaseContactByAccount() throws Exception {
            FrontMakeContactActionForm inForm = new FrontMakeContactActionForm();
            inForm.setContactAccountId(1);
            inForm.setContactLastName("山田");
            inForm.setContactFirstName("三郎");
            inForm.setContactLastNameKana("ヤマダ");
            inForm.setContactFirstNameKana("サブロウ");
            inForm.setContactMailAddress("yamada@example.com");
            inForm.setContactContent("会員からの問い合わせです。");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "送信する");
            actionPerform();

            verifyForward("send");

            String mailLogPath = getProperty("environment.properties").getProperty("mail.log.path");
            String expectedFirstLog
                    = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3} "
                    + "To:yamada@example.com Subject:お問合せを受け付けました。";
            String expectedSecondLog
                    = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3} "
                    + "To:user@mail.example.com Subject:お問合せがありました。";

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

            FrontMakeContactActionForm actualForm = (FrontMakeContactActionForm) getActionForm();

            assertThat(actualForm, is(nullValue()));

            // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.MakeContactCompleteActionTest.testSendToForwardSendInCaseContactByAccount.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("contacts");

            // 実際のテーブル情報の取得に必要な変数を生成する。
            String tableName = "contacts";
            String sqlQuery = "SELECT * FROM contacts WHERE contact_mail_address='yamada@example.com'";
            String[] ignoreCols =
                    {"contact_id", "contacted_at", "contact_updated_at", "contact_delete_flag", "contact_deleted_at"};

            Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
        }

        @Test
        public void testSendToForwardInvalid() throws Exception {
            FrontMakeContactActionForm inForm = new FrontMakeContactActionForm();
            inForm.setContactAccountId(1);
            inForm.setContactLastName("山田");
            inForm.setContactFirstName("三郎");
            inForm.setContactLastNameKana("ヤマダ");
            inForm.setContactFirstNameKana("サブロウ");
            inForm.setContactMailAddress("yamada@example.com");
            inForm.setContactContent("会員からの問い合わせです。");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            addRequestParameter("dispatch", "送信する");
            actionPerform();

            verifyForward("invalid");
        }

    }

}
