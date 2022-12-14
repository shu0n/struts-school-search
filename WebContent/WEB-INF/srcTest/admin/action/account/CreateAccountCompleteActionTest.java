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

import admin.actionform.account.AdminCreateAccountActionForm;
import servletunit.struts.MockStrutsTestCase;
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

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testFixToForwardFix() throws Exception {
            AdminCreateAccountActionForm inForm = new AdminCreateAccountActionForm();
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
                    new GenerateFormFile("profileImage.png", "admin/action/account/profileImage.png", 1187));
            inForm.setProfileImageFileName("20220102030405678_123.png");
            inForm.setSelfIntroduction("?????????????????????");
            inForm.setPassword("pbssword");
            inForm.setAccountStatus("0");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "????????????");
            actionPerform();

            verifyForward("fix");

            AdminCreateAccountActionForm actual = (AdminCreateAccountActionForm) getActionForm();
            assertThat(actual.getProfileImageFileName(), is(nullValue()));
            assertThat(actual.getProfileImageFile(), is(nullValue()));
        }

        @Test
        public void testFixToForwardInvalid() throws Exception {
            AdminCreateAccountActionForm inForm = new AdminCreateAccountActionForm();
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
                    new GenerateFormFile("profileImage.png", "admin/action/account/profileImage.png", 1187));
            inForm.setProfileImageFileName("20220102030405678_123.png");
            inForm.setSelfIntroduction("?????????????????????");
            inForm.setPassword("pbssword");
            inForm.setAccountStatus("0");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            addRequestParameter("dispatch", "????????????");
            actionPerform();

            verifyForward("invalid");
        }

        @Test
        public void testCreateToForwardCreate() throws Exception {
            AdminCreateAccountActionForm inForm = new AdminCreateAccountActionForm();
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
                    new GenerateFormFile("profileImage.png", "admin/action/account/profileImage.png", 1187));
            inForm.setProfileImageFileName("20220102030405678_123.png");
            inForm.setSelfIntroduction("?????????????????????");
            inForm.setPassword("pbssword");
            inForm.setAccountStatus("0");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "????????????");
            actionPerform();

            verifyForward("create");

            AdminCreateAccountActionForm actual
                    = (AdminCreateAccountActionForm) getSession().getAttribute("AdminCreateAccountActionForm");

            assertThat(actual, is(nullValue()));

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
        }

        @Test
        public void testCreateToForwardInvalid() throws Exception {
            AdminCreateAccountActionForm inForm = new AdminCreateAccountActionForm();
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
                    new GenerateFormFile("profileImage.png", "admin/action/account/profileImage.png", 1187));
            inForm.setProfileImageFileName("20220102030405678_123.png");
            inForm.setSelfIntroduction("?????????????????????");
            inForm.setPassword("pbssword");
            inForm.setAccountStatus("0");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            addRequestParameter("dispatch", "????????????");
            actionPerform();

            verifyForward("invalid");
        }

    }

}
