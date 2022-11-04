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

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import front.actionform.account.FrontRequestReissueActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.ConnectDatabase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class RequestReissueCompleteActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.RequestReissueCompleteActionTest.xml") {
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

        private static final String ACTION_PATH = "/requestReissueComplete.do";
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
            FrontRequestReissueActionForm inForm = new FrontRequestReissueActionForm();
            inForm.setMailAddress("tanaka@example.com");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("success");

            String mailLogPath = getProperty("environment.properties").getProperty("mail.log.path");
            String expectedLog
                    = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3} "
                    + "To:tanaka@example.com Subject:パスワード再発行の申請を受け付けました。";

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

            FrontRequestReissueActionForm actualForm = (FrontRequestReissueActionForm) getActionForm();

            assertThat(actualForm, is(nullValue()));

            String expectedReissueToken = "[a-zA-Z1-9 -~]";
            String reissueEffectiveTime
                    = getProperty("environment.properties").getProperty("reissue.effective.time");

            String sql = "SELECT * FROM accounts WHERE account_id=1";
            Map<String, String> actualColumnValueMap = getReissueTokenAndReissueEffectiveDate(sql);

            assertThat(actualColumnValueMap.get("reissue_token"), is(matchPatternAs(expectedReissueToken)));
            long differenceTime = ChronoUnit.MILLIS.between(
                    LocalDateTime.now(),
                    LocalDateTime.parse(
                            actualColumnValueMap.get("reissue_effective_date"),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
            assertTrue(Long.parseLong(reissueEffectiveTime) - 3600000 < differenceTime);
            assertTrue(differenceTime <= Long.parseLong(reissueEffectiveTime));
        }

        @Test
        public void testExecuteToForwardInvalid() throws Exception {
            FrontRequestReissueActionForm inForm = new FrontRequestReissueActionForm();
            inForm.setMailAddress("tanaka@example.com");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            actionPerform();

            verifyForward("invalid");
        }

        @Test
        public void testExecuteToForwardRedo() throws Exception {
            FrontRequestReissueActionForm inForm = new FrontRequestReissueActionForm();
            inForm.setMailAddress("yamada@example.com");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("redo");
            verifyActionErrors(new String[] {"メールアドレスが誤っています。"});
        }

        /**
         * アカウントテーブルから再発行トークンと再発行有効期限を取得するためのメソッド
         */
        private Map<String, String> getReissueTokenAndReissueEffectiveDate(String sql)
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
                    columnValueMap.put("reissue_token", rs.getString("reissue_token")); // 再発行トークン
                    columnValueMap.put("reissue_effective_date", rs.getTimestamp("reissue_effective_date").toString()); // 再発行有効期限
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
