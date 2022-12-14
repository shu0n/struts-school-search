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

        // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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
            // ???????????????????????????????????????????????????????????????
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
            inForm.setLastName("??????");
            inForm.setFirstName("??????");
            inForm.setLastNameKana("?????????");
            inForm.setFirstNameKana("?????????");
            inForm.setSexId(2);
            inForm.setBirthYear("1990");
            inForm.setBirthMonth("01");
            inForm.setBirthDay("02");
            inForm.setPrefectureId(3);
            inForm.setMailAddress("test@example.com");
            inForm.setProfileImageFile(
                    new GenerateFormFile("profileImage.png", "front/action/account/profileImage.png", 1187));
            inForm.setProfileImageFileName("20220102030405678_123.png");
            inForm.setSelfIntroduction("?????????????????????");
            inForm.setPassword("pbssword");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "????????????");
            actionPerform();

            verifyForward("fix");

            FrontCreateAccountActionForm actual = (FrontCreateAccountActionForm) getActionForm();
            assertThat(actual.getProfileImageFileName(), is(nullValue()));
            assertThat(actual.getProfileImageFile(), is(nullValue()));
        }

        @Test
        public void testFixToForwardInvalid() throws Exception {
            FrontCreateAccountActionForm inForm = new FrontCreateAccountActionForm();
            inForm.setLastName("??????");
            inForm.setFirstName("??????");
            inForm.setLastNameKana("?????????");
            inForm.setFirstNameKana("?????????");
            inForm.setSexId(2);
            inForm.setBirthYear("1990");
            inForm.setBirthMonth("01");
            inForm.setBirthDay("02");
            inForm.setPrefectureId(3);
            inForm.setMailAddress("test@example.com");
            inForm.setProfileImageFile(
                    new GenerateFormFile("profileImage.png", "front/action/account/profileImage.png", 1187));
            inForm.setProfileImageFileName("20220102030405678_123.png");
            inForm.setSelfIntroduction("?????????????????????");
            inForm.setPassword("pbssword");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            addRequestParameter("dispatch", "????????????");
            actionPerform();

            verifyForward("invalid");
        }

        @Test
        public void testCreateToForwardCreate() throws Exception {
            FrontCreateAccountActionForm inForm = new FrontCreateAccountActionForm();
            inForm.setLastName("??????");
            inForm.setFirstName("??????");
            inForm.setLastNameKana("?????????");
            inForm.setFirstNameKana("?????????");
            inForm.setSexId(2);
            inForm.setBirthYear("1990");
            inForm.setBirthMonth("01");
            inForm.setBirthDay("02");
            inForm.setPrefectureId(3);
            inForm.setMailAddress("test@example.com");
            inForm.setProfileImageFile(
                    new GenerateFormFile("profileImage.png", "front/action/account/profileImage.png", 1187));
            inForm.setProfileImageFileName("20220102030405678_123.png");
            inForm.setSelfIntroduction("?????????????????????");
            inForm.setPassword("pbssword");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "????????????");
            actionPerform();

            verifyForward("create");

            String mailLogPath = getProperty("environment.properties").getProperty("mail.log.path");
            String expectedLog
                    = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3} "
                    + "To:test@example.com Subject:School Serach??????????????????????????????????????????";

            // ?????????????????????????????????????????????????????????
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

            // ?????????????????????????????????????????????????????????????????????????????????????????????????????????
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.CreateAccountCompleteActionTest.testCreateToForwardCreate.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("accounts");

            // ????????????????????????????????????????????????????????????????????????
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
            inForm.setLastName("??????");
            inForm.setFirstName("??????");
            inForm.setLastNameKana("?????????");
            inForm.setFirstNameKana("?????????");
            inForm.setSexId(2);
            inForm.setBirthYear("1990");
            inForm.setBirthMonth("01");
            inForm.setBirthDay("02");
            inForm.setPrefectureId(3);
            inForm.setMailAddress("test@example.com");
            inForm.setProfileImageFile(
                    new GenerateFormFile("profileImage.png", "front/action/account/profileImage.png", 1187));
            inForm.setProfileImageFileName("20220102030405678_123.png");
            inForm.setSelfIntroduction("?????????????????????");
            inForm.setPassword("pbssword");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            addRequestParameter("dispatch", "????????????");
            actionPerform();

            verifyForward("invalid");
        }

        /**
         * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
         */
        private Map<String, String> getActivateTokenAndActivateEffectiveDate(String sql)
                throws ClassNotFoundException, IOException, SQLException {
            Connection connection = null; // DB??????????????????

            try {
                // DB????????????????????????????????????
                connection = new ConnectDatabase().connectDatabase();
                // SELECT??????PreparedStatement????????????????????????????????????
                PreparedStatement ps = connection.prepareStatement(sql);
                // SELECT?????????????????????????????????????????????
                ResultSet rs = ps.executeQuery();

                // ????????????????????????????????????????????????????????????????????????????????????????????????
                Map<String, String> columnValueMap = new HashMap<>();
                // ???????????????????????????????????????????????????????????????
                while(rs.next()) {
                    columnValueMap.put("activate_token", rs.getString("activate_token")); // ?????????????????????
                    columnValueMap.put("activate_effective_date", rs.getTimestamp("activate_effective_date").toString()); // ?????????????????????
                }

                // ??????????????????????????????????????????????????????
                return columnValueMap;
            } finally {
                if(connection != null) {
                    // DB??????????????????????????????
                    try {
                        // DB????????????????????????
                        connection.close();
                    } catch(SQLException e) {
                        // ?????????????????????
                        throw e;
                    }
                }
            }
        }

    }

}
