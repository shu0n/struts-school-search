package admin.action.school;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.property.PropertyTester.*;

import java.math.BigDecimal;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import admin.actionform.school.AdminRegisterSchoolActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.generator.GenerateFormFile;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class RegisterSchoolCompleteActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.RegisterSchoolCompleteActionTest.xml") {
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

        private static final String ACTION_PATH = "/registerSchoolComplete.do";
        private static final String VALID_TOKEN = "validToken";
        private static final String INVALID_TOKEN = "invalidToken";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testFixToForwardFix() {
            AdminRegisterSchoolActionForm inForm = new AdminRegisterSchoolActionForm();
            inForm.setRegistrantKind("account");
            inForm.setRegistrantKindName("アカウント");
            inForm.setRegistrantAccountId(22);
            inForm.setRegistrantAdminId(0);
            inForm.setStrRegistrantAccountId("22");
            inForm.setStrRegistrantAdminId(null);
            inForm.setSchoolName("テストスクール1");
            inForm.setSchoolCategoryId(43);
            inForm.setSchoolCategoryName("日本文化");
            inForm.setSchoolSummary("スクール概要1");
            inForm.setSchoolDescription("スクール詳細1");
            inForm.setSchoolZipCode1("123");
            inForm.setSchoolZipCode2("4567");
            inForm.setSchoolPrefectureId(14);
            inForm.setSchoolPrefectureName("大阪府");
            inForm.setSchoolCity("大阪市");
            inForm.setSchoolAddress1("大阪区");
            inForm.setSchoolAddress2("大阪町");
            inForm.setStrSchoolFee("4321");
            inForm.setSchoolFee(new BigDecimal("4321"));
            inForm.setSupplementaryFee("費用補足1");
            inForm.setSchoolUrl("http://test1.com");
            inForm.setSchoolReleasePropriety("0");
            inForm.setSchoolEntryPropriety("0");
            inForm.setSchoolReleaseProprietyName("不可");
            inForm.setSchoolEntryProprietyName("不可");
            inForm.setSummaryImageFileName("summaryImage.png");
            inForm.setDetailImage1FileName("detailImage1.png");
            inForm.setDetailImage2FileName("detailImage2.png");
            inForm.setDetailImage3FileName("detailImage3.png");
            inForm.setDetailImage4FileName("detailImage4.png");
            inForm.setDetailImage5FileName("detailImage5.png");
            inForm.setDetailImage6FileName("detailImage6.png");
            inForm.setDetailImage7FileName("detailImage7.png");
            inForm.setDetailImage8FileName("detailImage8.png");
            inForm.setSummaryImageFile(new GenerateFormFile(
                    "updateSummaryImage.png", "admin/action/school/updateSummaryImage.png", 1187));
            inForm.setDetailImage1File(new GenerateFormFile(
                    "updateDetailImage1.png", "admin/action/school/updateDetailImage1.png", 1187));
            inForm.setDetailImage2File(new GenerateFormFile(
                    "updateDetailImage2.png", "admin/action/school/updateDetailImage2.png", 1187));
            inForm.setDetailImage3File(new GenerateFormFile(
                    "updateDetailImage3.png", "admin/action/school/updateDetailImage3.png", 1187));
            inForm.setDetailImage4File(new GenerateFormFile(
                    "updateDetailImage4.png", "admin/action/school/updateDetailImage4.png", 1187));
            inForm.setDetailImage5File(new GenerateFormFile(
                    "updateDetailImage5.png", "admin/action/school/updateDetailImage5.png", 1187));
            inForm.setDetailImage6File(new GenerateFormFile(
                    "updateDetailImage6.png", "admin/action/school/updateDetailImage6.png", 1187));
            inForm.setDetailImage7File(new GenerateFormFile(
                    "updateDetailImage7.png", "admin/action/school/updateDetailImage7.png", 1187));
            inForm.setDetailImage8File(new GenerateFormFile(
                    "updateDetailImage8.png", "admin/action/school/updateDetailImage8.png", 1187));
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "修正する");
            actionPerform();

            verifyForward("fix");

            AdminRegisterSchoolActionForm actual = (AdminRegisterSchoolActionForm) getActionForm();

            assertThat(actual.getSummaryImageFileName(), is(nullValue()));
            assertThat(actual.getDetailImage1FileName(), is(nullValue()));
            assertThat(actual.getDetailImage2FileName(), is(nullValue()));
            assertThat(actual.getDetailImage3FileName(), is(nullValue()));
            assertThat(actual.getDetailImage4FileName(), is(nullValue()));
            assertThat(actual.getDetailImage5FileName(), is(nullValue()));
            assertThat(actual.getDetailImage6FileName(), is(nullValue()));
            assertThat(actual.getDetailImage7FileName(), is(nullValue()));
            assertThat(actual.getDetailImage8FileName(), is(nullValue()));
            assertThat(actual.getSummaryImageFile(), is(nullValue()));
            assertThat(actual.getDetailImage1File(), is(nullValue()));
            assertThat(actual.getDetailImage2File(), is(nullValue()));
            assertThat(actual.getDetailImage3File(), is(nullValue()));
            assertThat(actual.getDetailImage4File(), is(nullValue()));
            assertThat(actual.getDetailImage5File(), is(nullValue()));
            assertThat(actual.getDetailImage6File(), is(nullValue()));
            assertThat(actual.getDetailImage7File(), is(nullValue()));
            assertThat(actual.getDetailImage8File(), is(nullValue()));
        }

        @Test
        public void testFixToForwardInvalid() {
            AdminRegisterSchoolActionForm inForm = new AdminRegisterSchoolActionForm();
            inForm.setRegistrantKind("account");
            inForm.setRegistrantKindName("アカウント");
            inForm.setRegistrantAccountId(22);
            inForm.setRegistrantAdminId(0);
            inForm.setStrRegistrantAccountId("22");
            inForm.setStrRegistrantAdminId(null);
            inForm.setSchoolName("テストスクール1");
            inForm.setSchoolCategoryId(43);
            inForm.setSchoolCategoryName("日本文化");
            inForm.setSchoolSummary("スクール概要1");
            inForm.setSchoolDescription("スクール詳細1");
            inForm.setSchoolZipCode1("123");
            inForm.setSchoolZipCode2("4567");
            inForm.setSchoolPrefectureId(14);
            inForm.setSchoolPrefectureName("大阪府");
            inForm.setSchoolCity("大阪市");
            inForm.setSchoolAddress1("大阪区");
            inForm.setSchoolAddress2("大阪町");
            inForm.setStrSchoolFee("4321");
            inForm.setSchoolFee(new BigDecimal("4321"));
            inForm.setSupplementaryFee("費用補足1");
            inForm.setSchoolUrl("http://test1.com");
            inForm.setSchoolReleasePropriety("0");
            inForm.setSchoolEntryPropriety("0");
            inForm.setSchoolReleaseProprietyName("不可");
            inForm.setSchoolEntryProprietyName("不可");
            inForm.setSummaryImageFileName("summaryImage.png");
            inForm.setDetailImage1FileName("detailImage1.png");
            inForm.setDetailImage2FileName("detailImage2.png");
            inForm.setDetailImage3FileName("detailImage3.png");
            inForm.setDetailImage4FileName("detailImage4.png");
            inForm.setDetailImage5FileName("detailImage5.png");
            inForm.setDetailImage6FileName("detailImage6.png");
            inForm.setDetailImage7FileName("detailImage7.png");
            inForm.setDetailImage8FileName("detailImage8.png");
            inForm.setSummaryImageFile(new GenerateFormFile(
                    "updateSummaryImage.png", "admin/action/school/updateSummaryImage.png", 1187));
            inForm.setDetailImage1File(new GenerateFormFile(
                    "updateDetailImage1.png", "admin/action/school/updateDetailImage1.png", 1187));
            inForm.setDetailImage2File(new GenerateFormFile(
                    "updateDetailImage2.png", "admin/action/school/updateDetailImage2.png", 1187));
            inForm.setDetailImage3File(new GenerateFormFile(
                    "updateDetailImage3.png", "admin/action/school/updateDetailImage3.png", 1187));
            inForm.setDetailImage4File(new GenerateFormFile(
                    "updateDetailImage4.png", "admin/action/school/updateDetailImage4.png", 1187));
            inForm.setDetailImage5File(new GenerateFormFile(
                    "updateDetailImage5.png", "admin/action/school/updateDetailImage5.png", 1187));
            inForm.setDetailImage6File(new GenerateFormFile(
                    "updateDetailImage6.png", "admin/action/school/updateDetailImage6.png", 1187));
            inForm.setDetailImage7File(new GenerateFormFile(
                    "updateDetailImage7.png", "admin/action/school/updateDetailImage7.png", 1187));
            inForm.setDetailImage8File(new GenerateFormFile(
                    "updateDetailImage8.png", "admin/action/school/updateDetailImage8.png", 1187));
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            addRequestParameter("dispatch", "修正する");
            actionPerform();

            verifyForward("invalid");
        }

        @Test
        public void testRegisterToForwardRegisterInCaseRegisterAsAccount() throws Exception {
            AdminRegisterSchoolActionForm inForm = new AdminRegisterSchoolActionForm();
            inForm.setRegistrantKind("account");
            inForm.setRegistrantKindName("アカウント");
            inForm.setRegistrantAccountId(22);
            inForm.setRegistrantAdminId(0);
            inForm.setStrRegistrantAccountId("22");
            inForm.setStrRegistrantAdminId(null);
            inForm.setSchoolName("テストスクール1");
            inForm.setSchoolCategoryId(43);
            inForm.setSchoolCategoryName("日本文化");
            inForm.setSchoolSummary("スクール概要1");
            inForm.setSchoolDescription("スクール詳細1");
            inForm.setSchoolZipCode1("123");
            inForm.setSchoolZipCode2("4567");
            inForm.setSchoolPrefectureId(14);
            inForm.setSchoolPrefectureName("大阪府");
            inForm.setSchoolCity("大阪市");
            inForm.setSchoolAddress1("大阪区");
            inForm.setSchoolAddress2("大阪町");
            inForm.setStrSchoolFee("4321");
            inForm.setSchoolFee(new BigDecimal("4321"));
            inForm.setSupplementaryFee("費用補足1");
            inForm.setSchoolUrl("http://test1.com");
            inForm.setSchoolReleasePropriety("0");
            inForm.setSchoolEntryPropriety("0");
            inForm.setSchoolReleaseProprietyName("不可");
            inForm.setSchoolEntryProprietyName("不可");
            inForm.setSummaryImageFileName("summaryImage.png");
            inForm.setDetailImage1FileName("detailImage1.png");
            inForm.setDetailImage2FileName("detailImage2.png");
            inForm.setDetailImage3FileName("detailImage3.png");
            inForm.setDetailImage4FileName("detailImage4.png");
            inForm.setDetailImage5FileName("detailImage5.png");
            inForm.setDetailImage6FileName("detailImage6.png");
            inForm.setDetailImage7FileName("detailImage7.png");
            inForm.setDetailImage8FileName("detailImage8.png");
            inForm.setSummaryImageFile(new GenerateFormFile(
                    "updateSummaryImage.png", "admin/action/school/updateSummaryImage.png", 1187));
            inForm.setDetailImage1File(new GenerateFormFile(
                    "updateDetailImage1.png", "admin/action/school/updateDetailImage1.png", 1187));
            inForm.setDetailImage2File(new GenerateFormFile(
                    "updateDetailImage2.png", "admin/action/school/updateDetailImage2.png", 1187));
            inForm.setDetailImage3File(new GenerateFormFile(
                    "updateDetailImage3.png", "admin/action/school/updateDetailImage3.png", 1187));
            inForm.setDetailImage4File(new GenerateFormFile(
                    "updateDetailImage4.png", "admin/action/school/updateDetailImage4.png", 1187));
            inForm.setDetailImage5File(new GenerateFormFile(
                    "updateDetailImage5.png", "admin/action/school/updateDetailImage5.png", 1187));
            inForm.setDetailImage6File(new GenerateFormFile(
                    "updateDetailImage6.png", "admin/action/school/updateDetailImage6.png", 1187));
            inForm.setDetailImage7File(new GenerateFormFile(
                    "updateDetailImage7.png", "admin/action/school/updateDetailImage7.png", 1187));
            inForm.setDetailImage8File(new GenerateFormFile(
                    "updateDetailImage8.png", "admin/action/school/updateDetailImage8.png", 1187));
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "登録する");
            actionPerform();

            verifyForward("register");

            AdminRegisterSchoolActionForm actual
                    = (AdminRegisterSchoolActionForm) getSession().getAttribute("AdminRegisterSchoolActionForm");

            assertThat(actual, is(nullValue()));

            // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.RegisterSchoolCompleteActionTest.testRegisterToForwardRegisterInCaseRegisterAsAccount.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("schools");

            // 実際のテーブル情報の取得に必要な変数を生成する。
            String tableName = "schools";
            String sqlQuery = "SELECT * FROM schools WHERE registrant_account_id=22";
            String[] ignoreCols =
                    {"school_id", "school_registered_at", "school_updated_at",
                    "school_delete_flag", "school_deleted_at"};

            Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
        }

        @Test
        public void testRegisterToForwardRegisterInCaseRegisterAsAdmin() throws Exception {
            AdminRegisterSchoolActionForm inForm = new AdminRegisterSchoolActionForm();
            inForm.setRegistrantKind("admin");
            inForm.setRegistrantKindName("管理者");
            inForm.setRegistrantAccountId(0);
            inForm.setRegistrantAdminId(61);
            inForm.setStrRegistrantAccountId(null);
            inForm.setStrRegistrantAdminId("61");
            inForm.setSchoolName("テストスクール2");
            inForm.setSchoolCategoryId(44);
            inForm.setSchoolCategoryName("茶道");
            inForm.setSchoolSummary("スクール概要2");
            inForm.setSchoolDescription("スクール詳細2");
            inForm.setSchoolZipCode1("124");
            inForm.setSchoolZipCode2("4568");
            inForm.setSchoolPrefectureId(15);
            inForm.setSchoolPrefectureName("山口県");
            inForm.setSchoolCity("山口市");
            inForm.setSchoolAddress1("山口区");
            inForm.setSchoolAddress2("山口町");
            inForm.setStrSchoolFee("3210");
            inForm.setSchoolFee(new BigDecimal("3210"));
            inForm.setSupplementaryFee("費用補足2");
            inForm.setSchoolUrl("http://test2.com");
            inForm.setSchoolReleasePropriety("1");
            inForm.setSchoolEntryPropriety("1");
            inForm.setSchoolReleaseProprietyName("可");
            inForm.setSchoolEntryProprietyName("可");
            inForm.setSummaryImageFileName("summaryImage11.png");
            inForm.setDetailImage1FileName("detailImage11.png");
            inForm.setDetailImage2FileName("detailImage12.png");
            inForm.setDetailImage3FileName("detailImage13.png");
            inForm.setDetailImage4FileName("detailImage14.png");
            inForm.setDetailImage5FileName("detailImage15.png");
            inForm.setDetailImage6FileName("detailImage16.png");
            inForm.setDetailImage7FileName("detailImage17.png");
            inForm.setDetailImage8FileName("detailImage18.png");
            inForm.setSummaryImageFile(new GenerateFormFile(
                    "updateSummaryImage.png", "admin/action/school/updateSummaryImage.png", 1187));
            inForm.setDetailImage1File(new GenerateFormFile(
                    "updateDetailImage1.png", "admin/action/school/updateDetailImage1.png", 1187));
            inForm.setDetailImage2File(new GenerateFormFile(
                    "updateDetailImage2.png", "admin/action/school/updateDetailImage2.png", 1187));
            inForm.setDetailImage3File(new GenerateFormFile(
                    "updateDetailImage3.png", "admin/action/school/updateDetailImage3.png", 1187));
            inForm.setDetailImage4File(new GenerateFormFile(
                    "updateDetailImage4.png", "admin/action/school/updateDetailImage4.png", 1187));
            inForm.setDetailImage5File(new GenerateFormFile(
                    "updateDetailImage5.png", "admin/action/school/updateDetailImage5.png", 1187));
            inForm.setDetailImage6File(new GenerateFormFile(
                    "updateDetailImage6.png", "admin/action/school/updateDetailImage6.png", 1187));
            inForm.setDetailImage7File(new GenerateFormFile(
                    "updateDetailImage7.png", "admin/action/school/updateDetailImage7.png", 1187));
            inForm.setDetailImage8File(new GenerateFormFile(
                    "updateDetailImage8.png", "admin/action/school/updateDetailImage8.png", 1187));
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "登録する");
            actionPerform();

            verifyForward("register");

            AdminRegisterSchoolActionForm actual
                    = (AdminRegisterSchoolActionForm) getSession().getAttribute("AdminRegisterSchoolActionForm");

            assertThat(actual, is(nullValue()));

            // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.RegisterSchoolCompleteActionTest.testRegisterToForwardRegisterInCaseRegisterAsAdmin.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("schools");

            // 実際のテーブル情報の取得に必要な変数を生成する。
            String tableName = "schools";
            String sqlQuery = "SELECT * FROM schools WHERE registrant_admin_id=61";
            String[] ignoreCols =
                    {"school_id", "school_registered_at", "school_updated_at",
                    "school_delete_flag", "school_deleted_at"};

            Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
        }

        @Test
        public void testRegisterToForwardInvalid() throws Exception {
            AdminRegisterSchoolActionForm inForm = new AdminRegisterSchoolActionForm();
            inForm.setRegistrantKind("admin");
            inForm.setRegistrantKindName("管理者");
            inForm.setRegistrantAccountId(0);
            inForm.setRegistrantAdminId(61);
            inForm.setStrRegistrantAccountId(null);
            inForm.setStrRegistrantAdminId("61");
            inForm.setSchoolName("テストスクール2");
            inForm.setSchoolCategoryId(44);
            inForm.setSchoolCategoryName("茶道");
            inForm.setSchoolSummary("スクール概要2");
            inForm.setSchoolDescription("スクール詳細2");
            inForm.setSchoolZipCode1("124");
            inForm.setSchoolZipCode2("4568");
            inForm.setSchoolPrefectureId(15);
            inForm.setSchoolPrefectureName("山口県");
            inForm.setSchoolCity("山口市");
            inForm.setSchoolAddress1("山口区");
            inForm.setSchoolAddress2("山口町");
            inForm.setStrSchoolFee("3210");
            inForm.setSchoolFee(new BigDecimal("3210"));
            inForm.setSupplementaryFee("費用補足2");
            inForm.setSchoolUrl("http://test2.com");
            inForm.setSchoolReleasePropriety("1");
            inForm.setSchoolEntryPropriety("1");
            inForm.setSchoolReleaseProprietyName("可");
            inForm.setSchoolEntryProprietyName("可");
            inForm.setSummaryImageFileName("summaryImage11.png");
            inForm.setDetailImage1FileName("detailImage11.png");
            inForm.setDetailImage2FileName("detailImage12.png");
            inForm.setDetailImage3FileName("detailImage13.png");
            inForm.setDetailImage4FileName("detailImage14.png");
            inForm.setDetailImage5FileName("detailImage15.png");
            inForm.setDetailImage6FileName("detailImage16.png");
            inForm.setDetailImage7FileName("detailImage17.png");
            inForm.setDetailImage8FileName("detailImage18.png");
            inForm.setSummaryImageFile(new GenerateFormFile(
                    "updateSummaryImage.png", "admin/action/school/updateSummaryImage.png", 1187));
            inForm.setDetailImage1File(new GenerateFormFile(
                    "updateDetailImage1.png", "admin/action/school/updateDetailImage1.png", 1187));
            inForm.setDetailImage2File(new GenerateFormFile(
                    "updateDetailImage2.png", "admin/action/school/updateDetailImage2.png", 1187));
            inForm.setDetailImage3File(new GenerateFormFile(
                    "updateDetailImage3.png", "admin/action/school/updateDetailImage3.png", 1187));
            inForm.setDetailImage4File(new GenerateFormFile(
                    "updateDetailImage4.png", "admin/action/school/updateDetailImage4.png", 1187));
            inForm.setDetailImage5File(new GenerateFormFile(
                    "updateDetailImage5.png", "admin/action/school/updateDetailImage5.png", 1187));
            inForm.setDetailImage6File(new GenerateFormFile(
                    "updateDetailImage6.png", "admin/action/school/updateDetailImage6.png", 1187));
            inForm.setDetailImage7File(new GenerateFormFile(
                    "updateDetailImage7.png", "admin/action/school/updateDetailImage7.png", 1187));
            inForm.setDetailImage8File(new GenerateFormFile(
                    "updateDetailImage8.png", "admin/action/school/updateDetailImage8.png", 1187));
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            addRequestParameter("dispatch", "登録する");
            actionPerform();

            verifyForward("invalid");
        }

    }

}
