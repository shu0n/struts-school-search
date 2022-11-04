package front.action.school;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.property.PropertyTester.*;

import java.math.BigDecimal;
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

import front.actionform.school.FrontEditSchoolActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.generator.GenerateFormFile;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class EditSchoolCompleteActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.EditSchoolCompleteActionTest.xml") {
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

        private static final String ACTION_PATH = "/editSchoolComplete.do";
        private static final String VALID_TOKEN = "validToken";
        private static final String INVALID_TOKEN = "invalidToken";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-front.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testFixToForwardFix() {
            FrontEditSchoolActionForm inForm = new FrontEditSchoolActionForm();
            inForm.setSchoolId(1);
            inForm.setRegistrantAccountId(22);
            inForm.setRegistrantLastName("佐藤");
            inForm.setRegistrantFirstName("次郎");
            inForm.setRegistrantLastNameKana("サトウ");
            inForm.setRegistrantFirstNameKana("ジロウ");
            inForm.setRegistrantAdminId(0);
            inForm.setSchoolName("テストスクール1");
            inForm.setSchoolCategoryId(44);
            inForm.setSchoolCategoryName("日本文化");
            inForm.setSchoolSummary("スクール概要1");
            inForm.setSchoolDescription("スクール詳細1");
            inForm.setSchoolZipCode1("123");
            inForm.setSchoolZipCode2("4567");
            inForm.setSchoolPrefectureId(15);
            inForm.setSchoolPrefectureName("大阪府");
            inForm.setSchoolCity("大阪市");
            inForm.setSchoolAddress1("大阪区");
            inForm.setSchoolAddress2("大阪町");
            inForm.setSchoolFee(new BigDecimal(5432));
            inForm.setStrSchoolFee("4321");
            inForm.setSupplementaryFee("費用補足1");
            inForm.setSchoolUrl("http://test1.com");
            inForm.setSchoolReleasePropriety("0");
            inForm.setSchoolEntryPropriety("0");
            inForm.setSummaryImageFileName("summary_image11.png");
            inForm.setDetailImage1FileName("detail_image11.png");
            inForm.setDetailImage2FileName("detail_image12.png");
            inForm.setDetailImage3FileName("detail_image13.png");
            inForm.setDetailImage4FileName("detail_image14.png");
            inForm.setDetailImage5FileName("detail_image15.png");
            inForm.setDetailImage6FileName("detail_image16.png");
            inForm.setDetailImage7FileName("detail_image17.png");
            inForm.setDetailImage8FileName("detail_image18.png");
            inForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            inForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            inForm.setStrSchoolRegisteredAt("2022/01/02 03:04:05");
            inForm.setStrSchoolUpdatedAt("2022/01/03 04:05:06");
            inForm.setSummaryImageFilePath("/img/summary_image11.png");
            inForm.setDetailImage1FilePath("/img/detail_image11.png");
            inForm.setDetailImage2FilePath("/img/detail_image12.png");
            inForm.setDetailImage3FilePath("/img/detail_image13.png");
            inForm.setDetailImage4FilePath("/img/detail_image14.png");
            inForm.setDetailImage5FilePath("/img/detail_image15.png");
            inForm.setDetailImage6FilePath("/img/detail_image16.png");
            inForm.setDetailImage7FilePath("/img/detail_image17.png");
            inForm.setDetailImage8FilePath("/img/detail_image18.png");
            inForm.setSummaryImageFileNameUpdate("updateSummaryImage.png");
            inForm.setDetailImage1FileNameUpdate("updateDetailImage1.png");
            inForm.setDetailImage2FileNameUpdate("updateDetailImage2.png");
            inForm.setDetailImage3FileNameUpdate("updateDetailImage3.png");
            inForm.setDetailImage4FileNameUpdate("updateDetailImage4.png");
            inForm.setDetailImage5FileNameUpdate("updateDetailImage5.png");
            inForm.setDetailImage6FileNameUpdate("updateDetailImage6.png");
            inForm.setDetailImage7FileNameUpdate("updateDetailImage7.png");
            inForm.setDetailImage8FileNameUpdate("updateDetailImage8.png");
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
            inForm.setDeleteSummaryImageFileFlag(true);
            inForm.setDeleteDetailImage1FileFlag(true);
            inForm.setDeleteDetailImage2FileFlag(true);
            inForm.setDeleteDetailImage3FileFlag(true);
            inForm.setDeleteDetailImage4FileFlag(true);
            inForm.setDeleteDetailImage5FileFlag(true);
            inForm.setDeleteDetailImage6FileFlag(true);
            inForm.setDeleteDetailImage7FileFlag(true);
            inForm.setDeleteDetailImage8FileFlag(true);
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "修正する");
            actionPerform();

            verifyForward("fix");

            FrontEditSchoolActionForm actual = (FrontEditSchoolActionForm) getActionForm();

            assertThat(actual.isDeleteSummaryImageFileFlag(), is(false));
            assertThat(actual.isDeleteDetailImage1FileFlag(), is(false));
            assertThat(actual.isDeleteDetailImage2FileFlag(), is(false));
            assertThat(actual.isDeleteDetailImage3FileFlag(), is(false));
            assertThat(actual.isDeleteDetailImage4FileFlag(), is(false));
            assertThat(actual.isDeleteDetailImage5FileFlag(), is(false));
            assertThat(actual.isDeleteDetailImage6FileFlag(), is(false));
            assertThat(actual.isDeleteDetailImage7FileFlag(), is(false));
            assertThat(actual.isDeleteDetailImage8FileFlag(), is(false));
            assertThat(actual.getSummaryImageFileNameUpdate(), is(nullValue()));
            assertThat(actual.getDetailImage1FileNameUpdate(), is(nullValue()));
            assertThat(actual.getDetailImage2FileNameUpdate(), is(nullValue()));
            assertThat(actual.getDetailImage3FileNameUpdate(), is(nullValue()));
            assertThat(actual.getDetailImage4FileNameUpdate(), is(nullValue()));
            assertThat(actual.getDetailImage5FileNameUpdate(), is(nullValue()));
            assertThat(actual.getDetailImage6FileNameUpdate(), is(nullValue()));
            assertThat(actual.getDetailImage7FileNameUpdate(), is(nullValue()));
            assertThat(actual.getDetailImage8FileNameUpdate(), is(nullValue()));
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
            FrontEditSchoolActionForm inForm = new FrontEditSchoolActionForm();
            inForm.setSchoolId(1);
            inForm.setRegistrantAccountId(22);
            inForm.setRegistrantLastName("佐藤");
            inForm.setRegistrantFirstName("次郎");
            inForm.setRegistrantLastNameKana("サトウ");
            inForm.setRegistrantFirstNameKana("ジロウ");
            inForm.setRegistrantAdminId(0);
            inForm.setSchoolName("テストスクール1");
            inForm.setSchoolCategoryId(44);
            inForm.setSchoolCategoryName("日本文化");
            inForm.setSchoolSummary("スクール概要1");
            inForm.setSchoolDescription("スクール詳細1");
            inForm.setSchoolZipCode1("123");
            inForm.setSchoolZipCode2("4567");
            inForm.setSchoolPrefectureId(15);
            inForm.setSchoolPrefectureName("大阪府");
            inForm.setSchoolCity("大阪市");
            inForm.setSchoolAddress1("大阪区");
            inForm.setSchoolAddress2("大阪町");
            inForm.setSchoolFee(new BigDecimal(5432));
            inForm.setStrSchoolFee("4321");
            inForm.setSupplementaryFee("費用補足1");
            inForm.setSchoolUrl("http://test1.com");
            inForm.setSchoolReleasePropriety("0");
            inForm.setSchoolEntryPropriety("0");
            inForm.setSummaryImageFileName("summary_image11.png");
            inForm.setDetailImage1FileName("detail_image11.png");
            inForm.setDetailImage2FileName("detail_image12.png");
            inForm.setDetailImage3FileName("detail_image13.png");
            inForm.setDetailImage4FileName("detail_image14.png");
            inForm.setDetailImage5FileName("detail_image15.png");
            inForm.setDetailImage6FileName("detail_image16.png");
            inForm.setDetailImage7FileName("detail_image17.png");
            inForm.setDetailImage8FileName("detail_image18.png");
            inForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            inForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            inForm.setStrSchoolRegisteredAt("2022/01/02 03:04:05");
            inForm.setStrSchoolUpdatedAt("2022/01/03 04:05:06");
            inForm.setSummaryImageFilePath("/img/summary_image11.png");
            inForm.setDetailImage1FilePath("/img/detail_image11.png");
            inForm.setDetailImage2FilePath("/img/detail_image12.png");
            inForm.setDetailImage3FilePath("/img/detail_image13.png");
            inForm.setDetailImage4FilePath("/img/detail_image14.png");
            inForm.setDetailImage5FilePath("/img/detail_image15.png");
            inForm.setDetailImage6FilePath("/img/detail_image16.png");
            inForm.setDetailImage7FilePath("/img/detail_image17.png");
            inForm.setDetailImage8FilePath("/img/detail_image18.png");
            inForm.setSummaryImageFileNameUpdate("updateSummaryImage.png");
            inForm.setDetailImage1FileNameUpdate("updateDetailImage1.png");
            inForm.setDetailImage2FileNameUpdate("updateDetailImage2.png");
            inForm.setDetailImage3FileNameUpdate("updateDetailImage3.png");
            inForm.setDetailImage4FileNameUpdate("updateDetailImage4.png");
            inForm.setDetailImage5FileNameUpdate("updateDetailImage5.png");
            inForm.setDetailImage6FileNameUpdate("updateDetailImage6.png");
            inForm.setDetailImage7FileNameUpdate("updateDetailImage7.png");
            inForm.setDetailImage8FileNameUpdate("updateDetailImage8.png");
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
            inForm.setDeleteSummaryImageFileFlag(true);
            inForm.setDeleteDetailImage1FileFlag(true);
            inForm.setDeleteDetailImage2FileFlag(true);
            inForm.setDeleteDetailImage3FileFlag(true);
            inForm.setDeleteDetailImage4FileFlag(true);
            inForm.setDeleteDetailImage5FileFlag(true);
            inForm.setDeleteDetailImage6FileFlag(true);
            inForm.setDeleteDetailImage7FileFlag(true);
            inForm.setDeleteDetailImage8FileFlag(true);
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            addRequestParameter("dispatch", "修正する");
            actionPerform();

            verifyForward("invalid");
        }

        @Test
        public void testEditToForwardEditInCaseUpdateImage() throws Exception {
            FrontEditSchoolActionForm inForm = new FrontEditSchoolActionForm();
            inForm.setSchoolId(1);
            inForm.setRegistrantAccountId(22);
            inForm.setRegistrantLastName("佐藤");
            inForm.setRegistrantFirstName("次郎");
            inForm.setRegistrantLastNameKana("サトウ");
            inForm.setRegistrantFirstNameKana("ジロウ");
            inForm.setRegistrantAdminId(0);
            inForm.setSchoolName("更新テストスクール1");
            inForm.setSchoolCategoryId(45);
            inForm.setSchoolCategoryName("日本文化");
            inForm.setSchoolSummary("更新スクール概要1");
            inForm.setSchoolDescription("更新スクール詳細1");
            inForm.setSchoolZipCode1("124");
            inForm.setSchoolZipCode2("4568");
            inForm.setSchoolPrefectureId(15);
            inForm.setSchoolPrefectureName("山口県");
            inForm.setSchoolCity("山口市");
            inForm.setSchoolAddress1("山口区");
            inForm.setSchoolAddress2("山口町");
            inForm.setSchoolFee(new BigDecimal(4321));
            inForm.setStrSchoolFee("4321");
            inForm.setSupplementaryFee("更新費用補足1");
            inForm.setSchoolUrl("http://updatetest1.com");
            inForm.setSchoolReleasePropriety("0");
            inForm.setSchoolEntryPropriety("0");
            inForm.setSummaryImageFileName("summary_image11.png");
            inForm.setDetailImage1FileName("detail_image11.png");
            inForm.setDetailImage2FileName("detail_image12.png");
            inForm.setDetailImage3FileName("detail_image13.png");
            inForm.setDetailImage4FileName("detail_image14.png");
            inForm.setDetailImage5FileName("detail_image15.png");
            inForm.setDetailImage6FileName("detail_image16.png");
            inForm.setDetailImage7FileName("detail_image17.png");
            inForm.setDetailImage8FileName("detail_image18.png");
            inForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            inForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            inForm.setStrSchoolRegisteredAt("2022/01/02 03:04:05");
            inForm.setStrSchoolUpdatedAt("2022/01/03 04:05:06");
            inForm.setSummaryImageFilePath("/img/summary_image11.png");
            inForm.setDetailImage1FilePath("/img/detail_image11.png");
            inForm.setDetailImage2FilePath("/img/detail_image12.png");
            inForm.setDetailImage3FilePath("/img/detail_image13.png");
            inForm.setDetailImage4FilePath("/img/detail_image14.png");
            inForm.setDetailImage5FilePath("/img/detail_image15.png");
            inForm.setDetailImage6FilePath("/img/detail_image16.png");
            inForm.setDetailImage7FilePath("/img/detail_image17.png");
            inForm.setDetailImage8FilePath("/img/detail_image18.png");
            inForm.setSummaryImageFileNameUpdate("updateSummaryImage.png");
            inForm.setDetailImage1FileNameUpdate("updateDetailImage1.png");
            inForm.setDetailImage2FileNameUpdate("updateDetailImage2.png");
            inForm.setDetailImage3FileNameUpdate("updateDetailImage3.png");
            inForm.setDetailImage4FileNameUpdate("updateDetailImage4.png");
            inForm.setDetailImage5FileNameUpdate("updateDetailImage5.png");
            inForm.setDetailImage6FileNameUpdate("updateDetailImage6.png");
            inForm.setDetailImage7FileNameUpdate("updateDetailImage7.png");
            inForm.setDetailImage8FileNameUpdate("updateDetailImage8.png");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "編集する");
            actionPerform();

            verifyForward("edit");

            FrontEditSchoolActionForm actual
                    = (FrontEditSchoolActionForm) getSession().getAttribute("FrontEditSchoolActionForm");

            assertThat(actual, is(nullValue()));

            // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.EditSchoolCompleteActionTest.testEditToForwardEditInCaseUpdateImage.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("schools");

            // 実際のテーブル情報の取得に必要な変数を生成する。
            String tableName = "schools";
            String sqlQuery = "SELECT * FROM schools WHERE school_id=1";
            String[] ignoreCols
                    = {"school_registered_at", "school_updated_at", "school_delete_flag", "school_deleted_at"};

            Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
        }

        @Test
        public void testEditToForwardEditInCaseDeleteImage() throws Exception {
            FrontEditSchoolActionForm inForm = new FrontEditSchoolActionForm();
            inForm.setSchoolId(6);
            inForm.setRegistrantAccountId(23);
            inForm.setRegistrantLastName("高橋");
            inForm.setRegistrantFirstName("三郎");
            inForm.setRegistrantLastNameKana("タカハシ");
            inForm.setRegistrantFirstNameKana("サブロウ");
            inForm.setRegistrantAdminId(0);
            inForm.setSchoolName("更新テストスクール6");
            inForm.setSchoolCategoryId(46);
            inForm.setSchoolCategoryName("茶道");
            inForm.setSchoolSummary("更新スクール概要6");
            inForm.setSchoolDescription("更新スクール詳細6");
            inForm.setSchoolZipCode1("124");
            inForm.setSchoolZipCode2("6789");
            inForm.setSchoolPrefectureId(13);
            inForm.setSchoolPrefectureName("愛知県");
            inForm.setSchoolCity("愛知市");
            inForm.setSchoolAddress1("愛知区");
            inForm.setSchoolAddress2("");
            inForm.setSchoolFee(new BigDecimal(8765));
            inForm.setStrSchoolFee("8765");
            inForm.setSupplementaryFee("");
            inForm.setSchoolUrl("");
            inForm.setSchoolReleasePropriety("0");
            inForm.setSchoolEntryPropriety("0");
            inForm.setSummaryImageFileName("summary_image21.png");
            inForm.setDetailImage1FileName("detail_image21.png");
            inForm.setDetailImage2FileName("detail_image22.png");
            inForm.setDetailImage3FileName("detail_image23.png");
            inForm.setDetailImage4FileName("detail_image24.png");
            inForm.setDetailImage5FileName("detail_image25.png");
            inForm.setDetailImage6FileName("detail_image26.png");
            inForm.setDetailImage7FileName("detail_image27.png");
            inForm.setDetailImage8FileName("detail_image28.png");
            inForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-12 13:14:15.789"));
            inForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-13 14:15:16.891"));
            inForm.setStrSchoolRegisteredAt("2022/01/12 13:14:15");
            inForm.setStrSchoolUpdatedAt("2022/01/13 14:15:16");
            inForm.setSummaryImageFilePath("/img/summary_image21.png");
            inForm.setDetailImage1FilePath("/img/detail_image21.png");
            inForm.setDetailImage2FilePath("/img/detail_image22.png");
            inForm.setDetailImage3FilePath("/img/detail_image23.png");
            inForm.setDetailImage4FilePath("/img/detail_image24.png");
            inForm.setDetailImage5FilePath("/img/detail_image25.png");
            inForm.setDetailImage6FilePath("/img/detail_image26.png");
            inForm.setDetailImage7FilePath("/img/detail_image27.png");
            inForm.setDetailImage8FilePath("/img/detail_image28.png");
            inForm.setDeleteSummaryImageFileFlag(true);
            inForm.setDeleteDetailImage1FileFlag(true);
            inForm.setDeleteDetailImage2FileFlag(true);
            inForm.setDeleteDetailImage3FileFlag(true);
            inForm.setDeleteDetailImage4FileFlag(true);
            inForm.setDeleteDetailImage5FileFlag(true);
            inForm.setDeleteDetailImage6FileFlag(true);
            inForm.setDeleteDetailImage7FileFlag(true);
            inForm.setDeleteDetailImage8FileFlag(true);
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            addRequestParameter("dispatch", "編集する");
            actionPerform();

            verifyForward("edit");

            FrontEditSchoolActionForm actual
                    = (FrontEditSchoolActionForm) getSession().getAttribute("FrontEditSchoolActionForm");

            assertThat(actual, is(nullValue()));

            // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                    "expectedDataSet.EditSchoolCompleteActionTest.testEditToForwardEditInCaseDeleteImage.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            ITable expectedTable = replacementDataSet.getTable("schools");

            // 実際のテーブル情報の取得に必要な変数を生成する。
            String tableName = "schools";
            String sqlQuery = "SELECT * FROM schools WHERE school_id=6";
            String[] ignoreCols
                    = {"school_registered_at", "school_updated_at", "school_delete_flag", "school_deleted_at"};

            Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
        }

        @Test
        public void testEditToForwardInvalid() throws Exception {
            FrontEditSchoolActionForm inForm = new FrontEditSchoolActionForm();
            inForm.setSchoolId(1);
            inForm.setRegistrantAccountId(22);
            inForm.setRegistrantLastName("佐藤");
            inForm.setRegistrantFirstName("次郎");
            inForm.setRegistrantLastNameKana("サトウ");
            inForm.setRegistrantFirstNameKana("ジロウ");
            inForm.setRegistrantAdminId(0);
            inForm.setSchoolName("更新テストスクール6");
            inForm.setSchoolCategoryId(45);
            inForm.setSchoolCategoryName("日本文化");
            inForm.setSchoolSummary("更新スクール概要6");
            inForm.setSchoolDescription("更新スクール詳細6");
            inForm.setSchoolZipCode1("124");
            inForm.setSchoolZipCode2("4568");
            inForm.setSchoolPrefectureId(15);
            inForm.setSchoolPrefectureName("山口県");
            inForm.setSchoolCity("山口市");
            inForm.setSchoolAddress1("山口区");
            inForm.setSchoolAddress2("山口町");
            inForm.setSchoolFee(new BigDecimal(4321));
            inForm.setStrSchoolFee("4321");
            inForm.setSupplementaryFee("");
            inForm.setSchoolUrl("http://updatetest1.com");
            inForm.setSchoolReleasePropriety("0");
            inForm.setSchoolEntryPropriety("0");
            inForm.setSummaryImageFileName("summary_image11.png");
            inForm.setDetailImage1FileName("detail_image11.png");
            inForm.setDetailImage2FileName("detail_image12.png");
            inForm.setDetailImage3FileName("detail_image13.png");
            inForm.setDetailImage4FileName("detail_image14.png");
            inForm.setDetailImage5FileName("detail_image15.png");
            inForm.setDetailImage6FileName("detail_image16.png");
            inForm.setDetailImage7FileName("detail_image17.png");
            inForm.setDetailImage8FileName("detail_image18.png");
            inForm.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            inForm.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            inForm.setStrSchoolRegisteredAt("2022/01/02 03:04:05");
            inForm.setStrSchoolUpdatedAt("2022/01/03 04:05:06");
            inForm.setSummaryImageFilePath("/img/summary_image11.png");
            inForm.setDetailImage1FilePath("/img/detail_image11.png");
            inForm.setDetailImage2FilePath("/img/detail_image12.png");
            inForm.setDetailImage3FilePath("/img/detail_image13.png");
            inForm.setDetailImage4FilePath("/img/detail_image14.png");
            inForm.setDetailImage5FilePath("/img/detail_image15.png");
            inForm.setDetailImage6FilePath("/img/detail_image16.png");
            inForm.setDetailImage7FilePath("/img/detail_image17.png");
            inForm.setDetailImage8FilePath("/img/detail_image18.png");
            inForm.setSummaryImageFileNameUpdate("updateSummaryImage.png");
            inForm.setDetailImage1FileNameUpdate("updateDetailImage1.png");
            inForm.setDetailImage2FileNameUpdate("updateDetailImage2.png");
            inForm.setDetailImage3FileNameUpdate("updateDetailImage3.png");
            inForm.setDetailImage4FileNameUpdate("updateDetailImage4.png");
            inForm.setDetailImage5FileNameUpdate("updateDetailImage5.png");
            inForm.setDetailImage6FileNameUpdate("updateDetailImage6.png");
            inForm.setDetailImage7FileNameUpdate("updateDetailImage7.png");
            inForm.setDetailImage8FileNameUpdate("updateDetailImage8.png");
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            addRequestParameter("dispatch", "編集する");
            actionPerform();

            verifyForward("invalid");
        }

    }

}
