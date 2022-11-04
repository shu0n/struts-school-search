package admin.action.account;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;

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
import admin.actionform.account.AdminEditAccountActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.generator.GenerateFormFile;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class EditAccountCompleteActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.EditAccountCompleteActionTest.xml") {
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

        private static final String ACTION_PATH = "/editAccountComplete.do";
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
            AdminEditAccountActionForm inForm = new AdminEditAccountActionForm();
            inForm.setAccountId(1);
            inForm.setLastName("更新田中");
            inForm.setFirstName("更新太郎");
            inForm.setLastNameKana("コウシンタナカ");
            inForm.setFirstNameKana("コウシンタロウ");
            inForm.setSexId(3);
            inForm.setSexName("その他");
            inForm.setBirthDate(Timestamp.valueOf("1991-02-04 00:00:00"));
            inForm.setBirthYear("1991");
            inForm.setBirthMonth("02");
            inForm.setBirthDay("04");
            inForm.setPrefectureId(4);
            inForm.setPrefectureName("京都府");
            inForm.setMailAddress("updateTest@example.com");
            inForm.setProfileImageFile(
                    new GenerateFormFile("profileImage1.png", "admin/action/account/profileImage.png", 1187));
            inForm.setProfileImageFileName("profileImage.png");
            inForm.setProfileImageFileNameUpdate("updateProfileImge1.png");
            inForm.setDeleteProfileImageFileFlag(true);
            inForm.setSelfIntroduction("自己紹介の修正です。");
            inForm.setChangePasswordFlag(true);
            inForm.setNewPassword("newPassword");
            inForm.setAccountStatus("0");
            inForm.setAccountStatusName("無効");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "修正する");
            actionPerform();

            verifyForward("fix");

            AdminEditAccountActionForm actual = (AdminEditAccountActionForm) getActionForm();

            assertThat(actual.isDeleteProfileImageFileFlag(), is(false));
            assertThat(actual.isChangePasswordFlag(), is(false));
            assertThat(actual.getProfileImageFileNameUpdate(), is(nullValue()));
            assertThat(actual.getProfileImageFile(), is(nullValue()));
        }

        @Test
        public void testFixToForwardInvalid() throws Exception {
            AdminEditAccountActionForm inForm = new AdminEditAccountActionForm();
            inForm.setAccountId(1);
            inForm.setLastName("更新田中");
            inForm.setFirstName("更新太郎");
            inForm.setLastNameKana("コウシンタナカ");
            inForm.setFirstNameKana("コウシンタロウ");
            inForm.setSexId(3);
            inForm.setSexName("その他");
            inForm.setBirthDate(Timestamp.valueOf("1991-02-04 00:00:00"));
            inForm.setBirthYear("1991");
            inForm.setBirthMonth("02");
            inForm.setBirthDay("04");
            inForm.setPrefectureId(4);
            inForm.setPrefectureName("京都府");
            inForm.setMailAddress("updateTest@example.com");
            inForm.setProfileImageFile(
                    new GenerateFormFile("profileImage1.png", "admin/action/account/profileImage.png", 1187));
            inForm.setProfileImageFileName("profileImage.png");
            inForm.setProfileImageFileNameUpdate("updateProfileImge1.png");
            inForm.setDeleteProfileImageFileFlag(true);
            inForm.setSelfIntroduction("自己紹介の修正です。");
            inForm.setChangePasswordFlag(true);
            inForm.setNewPassword("newPassword");
            inForm.setAccountStatus("0");
            inForm.setAccountStatusName("無効");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            addRequestParameter("dispatch", "修正する");
            actionPerform();

            verifyForward("invalid");
        }

        @Test
        public void testEditToForwardEditInCaseCahngeAllItem() throws Exception {
            AdminEditAccountActionForm inForm = new AdminEditAccountActionForm();
            inForm.setAccountId(1);
            inForm.setLastName("山田");
            inForm.setFirstName("次郎");
            inForm.setLastNameKana("ヤマダ");
            inForm.setFirstNameKana("ジロウ");
            inForm.setSexId(3);
            inForm.setBirthYear("1991");
            inForm.setBirthMonth("02");
            inForm.setBirthDay("03");
            inForm.setPrefectureId(4);
            inForm.setMailAddress("yamada@example.com");
            inForm.setProfileImageFileName("updateProfileImage.png");
            inForm.setSelfIntroduction("山田の自己紹介です。");
            inForm.setChangePasswordFlag(true);
            inForm.setNewPassword("pbssword");
            inForm.setAccountStatus("0");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "編集する");
            actionPerform();

            verifyForward("edit");

            AdminCreateAccountActionForm actual
                    = (AdminCreateAccountActionForm) getSession().getAttribute("AdminCreateAccountActionForm");

            assertThat(actual, is(nullValue()));

            // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.EditAccountCompleteActionTest.testEditToForwardEditInCaseChangeAllItem.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("accounts");

            // 実際のテーブル情報の取得に必要な変数を生成する。
            String tableName = "accounts";
            String sqlQuery = "SELECT * FROM accounts WHERE account_id=1";
            String[] ignoreCols =
                    {"activate_token", "activate_effective_date",
                    "reissue_token", "reissue_effective_date",
                    "account_created_at", "account_updated_at",
                    "account_delete_flag", "account_deleted_at"};

            Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
        }

        @Test
        public void testEditToForwardEditInCaseChangeSomeItem() throws Exception {
            AdminEditAccountActionForm inForm = new AdminEditAccountActionForm();
            inForm.setAccountId(2);
            inForm.setLastName("棚橋");
            inForm.setFirstName("四郎");
            inForm.setLastNameKana("タナハシ");
            inForm.setFirstNameKana("シロウ");
            inForm.setSexId(1);
            inForm.setPrefectureId(3);
            inForm.setMailAddress("tanahashi@example.com");
            inForm.setDeleteProfileImageFileFlag(true);
            inForm.setSelfIntroduction("棚橋の自己紹介です。");
            inForm.setAccountStatus("0");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "編集する");
            actionPerform();

            verifyForward("edit");

            AdminCreateAccountActionForm actual
                    = (AdminCreateAccountActionForm) getSession().getAttribute("AdminCreateAccountActionForm");

            assertThat(actual, is(nullValue()));

            // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.EditAccountCompleteActionTest.testEditToForwardEditInCaseChangeSomeItem.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("accounts");

            // 実際のテーブル情報の取得に必要な変数を生成する。
            String tableName = "accounts";
            String sqlQuery = "SELECT * FROM accounts WHERE account_id=2";
            String[] ignoreCols =
                    {"activate_token", "activate_effective_date",
                    "reissue_token", "reissue_effective_date",
                    "account_created_at", "account_updated_at",
                    "account_delete_flag", "account_deleted_at"};

            Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
        }

        @Test
        public void testEditToForwardInvalid() throws Exception {
            AdminEditAccountActionForm inForm = new AdminEditAccountActionForm();
            inForm.setAccountId(3);
            inForm.setLastName("羽村");
            inForm.setFirstName("六郎");
            inForm.setLastNameKana("ハムラ");
            inForm.setFirstNameKana("ロクロウ");
            inForm.setSexId(2);
            inForm.setPrefectureId(3);
            inForm.setMailAddress("hamura@example.com");
            inForm.setSelfIntroduction("羽村の自己紹介です。");
            inForm.setAccountStatus("0");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            addRequestParameter("dispatch", "編集する");
            actionPerform();

            verifyForward("invalid");
        }

    }

}
