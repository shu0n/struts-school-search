package front.action.account;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsRegex.*;
import static test.property.PropertyTester.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.account.FrontCreateAccountActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.ConnectDatabase;
import test.db.DbUnitTester;
import test.generator.GenerateFormFile;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class CreateAccountCompleteActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.CreateAccountCompleteActionTest.xml") {
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

        private static final String ACTION_PATH = "/createAccountComplete.do";
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
        public void testFixToForwardFix() throws Exception {
            FrontCreateAccountActionForm inForm = new FrontCreateAccountActionForm();
            inForm.setLastName("山田");
            inForm.setFirstName("次郎");
            inForm.setLastNameKana("ヤマダ");
            inForm.setFirstNameKana("ジロウ");
            inForm.setSexId(2);
            inForm.setBirthYear("1990");
            inForm.setBirthMonth("01");
            inForm.setBirthDay("02");
            inForm.setPrefectureId(3);
            inForm.setMailAddress("test@example.com");
            inForm.setProfileImageFile(
                    new GenerateFormFile("profileImage.png", "front/action/account/profileImage.png", 1187));
            inForm.setProfileImageFileName("20220102030405678_123.png");
            inForm.setSelfIntroduction("自己紹介です。");
            inForm.setPassword("pbssword");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "修正する");
            actionPerform();

            verifyForward("fix");

            FrontCreateAccountActionForm actual = (FrontCreateAccountActionForm) getActionForm();
            assertThat(actual.getProfileImageFileName(), is(nullValue()));
            assertThat(actual.getProfileImageFile(), is(nullValue()));
        }

        @Test
        public void testFixToForwardInvalid() throws Exception {
            FrontCreateAccountActionForm inForm = new FrontCreateAccountActionForm();
            inForm.setLastName("山田");
            inForm.setFirstName("次郎");
            inForm.setLastNameKana("ヤマダ");
            inForm.setFirstNameKana("ジロウ");
            inForm.setSexId(2);
            inForm.setBirthYear("1990");
            inForm.setBirthMonth("01");
            inForm.setBirthDay("02");
            inForm.setPrefectureId(3);
            inForm.setMailAddress("test@example.com");
            inForm.setProfileImageFile(
                    new GenerateFormFile("profileImage.png", "front/action/account/profileImage.png", 1187));
            inForm.setProfileImageFileName("20220102030405678_123.png");
            inForm.setSelfIntroduction("自己紹介です。");
            inForm.setPassword("pbssword");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            addRequestParameter("dispatch", "修正する");
            actionPerform();

            verifyForward("invalid");
        }

        @Test
        public void testCreateToForwardCreate() throws Exception {
            FrontCreateAccountActionForm inForm = new FrontCreateAccountActionForm();
            inForm.setLastName("山田");
            inForm.setFirstName("次郎");
            inForm.setLastNameKana("ヤマダ");
            inForm.setFirstNameKana("ジロウ");
            inForm.setSexId(2);
            inForm.setBirthYear("1990");
            inForm.setBirthMonth("01");
            inForm.setBirthDay("02");
            inForm.setPrefectureId(3);
            inForm.setMailAddress("test@example.com");
            inForm.setProfileImageFile(
                    new GenerateFormFile("profileImage.png", "front/action/account/profileImage.png", 1187));
            inForm.setProfileImageFileName("20220102030405678_123.png");
            inForm.setSelfIntroduction("自己紹介です。");
            inForm.setPassword("pbssword");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "作成する");
            actionPerform();

            verifyForward("create");

            String mailLogPath = getProperty("environment.properties").getProperty("mail.log.path");
            String expectedLog
                    = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3} "
                    + "To:test@example.com Subject:School Serachにアカウントを作成しました。";

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

            FrontCreateAccountActionForm actualForm
                    = (FrontCreateAccountActionForm) getSession().getAttribute("FrontCreateAccountActionForm");

            assertThat(actualForm, is(nullValue()));

            // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.CreateAccountCompleteActionTest.testCreateToForwardCreate.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("accounts");

            // 実際のテーブル情報の取得に必要な変数を生成する。
            String tableName = "accounts";
            String sqlQuery = "SELECT * FROM accounts WHERE mail_address='test@example.com'";
            String[] ignoreCols =
                    {"account_id", "activate_token", "activate_effective_date",
                    "reissue_token", "reissue_effective_date",
                    "account_created_at", "account_updated_at",
                    "account_delete_flag", "account_deleted_at"};

            Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);

            String expectedReissueToken = "[a-zA-Z1-9 -~]";
            String activateEffectiveTime
                    = getProperty("environment.properties").getProperty("activate.effective.time");

            String sql = "SELECT * FROM accounts WHERE mail_address='test@example.com'";
            Map<String, String> actualColumnValueMap = getActivateTokenAndActivateEffectiveDate(sql);

            assertThat(actualColumnValueMap.get("activate_token"), is(matchPatternAs(expectedReissueToken)));
            long differenceTime = ChronoUnit.MILLIS.between(
                    LocalDateTime.now(),
                    LocalDateTime.parse(
                            actualColumnValueMap.get("activate_effective_date"),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
            assertTrue(Long.parseLong(activateEffectiveTime) - 3600000 < differenceTime);
            assertTrue(differenceTime <= Long.parseLong(activateEffectiveTime));
        }

        @Test
        public void testCreateToForwardInvalid() throws Exception {
            FrontCreateAccountActionForm inForm = new FrontCreateAccountActionForm();
            inForm.setLastName("山田");
            inForm.setFirstName("次郎");
            inForm.setLastNameKana("ヤマダ");
            inForm.setFirstNameKana("ジロウ");
            inForm.setSexId(2);
            inForm.setBirthYear("1990");
            inForm.setBirthMonth("01");
            inForm.setBirthDay("02");
            inForm.setPrefectureId(3);
            inForm.setMailAddress("test@example.com");
            inForm.setProfileImageFile(
                    new GenerateFormFile("profileImage.png", "front/action/account/profileImage.png", 1187));
            inForm.setProfileImageFileName("20220102030405678_123.png");
            inForm.setSelfIntroduction("自己紹介です。");
            inForm.setPassword("pbssword");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            addRequestParameter("dispatch", "作成する");
            actionPerform();

            verifyForward("invalid");
        }

        /**
         * アカウントテーブルから有効化トークンと有効化有効期限を取得するためのメソッド
         */
        private Map<String, String> getActivateTokenAndActivateEffectiveDate(String sql)
                throws ClassNotFoundException, IOException, SQLException {
            Connection connection = null; // DB接続用の変数

            try {
                // DBとの接続状態を取得する。
                connection = new ConnectDatabase().connectDatabase();
                // SELECT文をPreparedStatementインスタンスに格納する。
                PreparedStatement ps = connection.prepareStatement(sql);
                // SELECT文の実行結果を変数に格納する。
                ResultSet rs = ps.executeQuery();

                // 取得したレコードのカラム名と値を格納するためのマップを生成する。
                Map<String, String> columnValueMap = new HashMap<>();
                // 取得したレコードの件数分、処理を繰り返す。
                while(rs.next()) {
                    columnValueMap.put("activate_token", rs.getString("activate_token")); // 有効化トークン
                    columnValueMap.put("activate_effective_date", rs.getTimestamp("activate_effective_date").toString()); // 有効化有効期限
                }

                // カラム名と値を格納したマップを戻す。
                return columnValueMap;
            } finally {
                if(connection != null) {
                    // DBに接続されている場合
                    try {
                        // DB接続を切断する。
                        connection.close();
                    } catch(SQLException e) {
                        // 例外を投げる。
                        throw e;
                    }
                }
            }
        }

    }

}
