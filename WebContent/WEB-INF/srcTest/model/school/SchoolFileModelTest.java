package model.school;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.matcher.IsHttpResponseHeader.*;
import static test.matcher.IsRegex.*;
import static test.property.PropertyTester.*;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

import actionform.SchoolExtendActionForm;
import servletunit.HttpServletRequestSimulator;
import servletunit.HttpServletResponseSimulator;
import servletunit.ServletContextSimulator;
import test.db.DbUnitTester;
import test.generator.GenerateFormFile;

public class SchoolFileModelTest {

    // テスト時に生成される画像ファイルのファイル名を格納するためのリストを生成する。
    private List<String> fileNameList = new ArrayList<>();

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SchoolFileModelTest.xml") {
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

    @After
    public void after() throws Exception {
        String modulePath = getProperty("model/school/environment.properties").getProperty("module.path");
        String imgPath = getProperty("model/school/environment.properties").getProperty("img.path");
        // テスト時に生成された画像ファイルを削除する。
        if(!fileNameList.isEmpty()) {
            for(String fileName: fileNameList) {
                Path filePath = Paths.get(modulePath + imgPath + fileName);
                Files.delete(filePath);
            }
        }
        fileNameList = new ArrayList<>();
    }

    @Test
    public void testSetFileInfoInCaseFormFileExist() throws Exception {
        String expectedFilePath = "/img/\\d{17}_\\d{1,3}.png";
        String expectedFileName = "\\d{17}_\\d{1,3}.png";

        SchoolFileModel sut = new SchoolFileModel();
        SchoolExtendActionForm inForm = new SchoolExtendActionForm();
        inForm.setSummaryImageFile(new GenerateFormFile("summaryImage.png", "model/school/summaryImage.png", 1187));
        inForm.setDetailImage1File(new GenerateFormFile("detailImage1.png", "model/school/detailImage1.png", 1187));
        inForm.setDetailImage2File(new GenerateFormFile("detailImage2.png", "model/school/detailImage2.png", 1187));
        inForm.setDetailImage3File(new GenerateFormFile("detailImage3.png", "model/school/detailImage3.png", 1187));
        inForm.setDetailImage4File(new GenerateFormFile("detailImage4.png", "model/school/detailImage4.png", 1187));
        inForm.setDetailImage5File(new GenerateFormFile("detailImage5.png", "model/school/detailImage5.png", 1187));
        inForm.setDetailImage6File(new GenerateFormFile("detailImage6.png", "model/school/detailImage6.png", 1187));
        inForm.setDetailImage7File(new GenerateFormFile("detailImage7.png", "model/school/detailImage7.png", 1187));
        inForm.setDetailImage8File(new GenerateFormFile("detailImage8.png", "model/school/detailImage8.png", 1187));
        HttpServletRequest stub = new HttpServletRequestSimulator(new ServletContextSimulator());
        HttpServletRequest actual = sut.setFileInfo(inForm, stub);

        assertThat((String) actual.getAttribute("summaryImageFilePath"), is(matchPatternAs(expectedFilePath)));
        assertThat((String) actual.getAttribute("summaryImageFileName"), is(matchPatternAs(expectedFileName)));
        fileNameList.add((String) actual.getAttribute("summaryImageFileName"));
        for(int i = 1; i < 9; i++) {
            fileNameList.add((String) actual.getAttribute("detailImage" + String.valueOf(i) + "FileName"));
            assertThat(
                    (String) actual.getAttribute("detailImage" + String.valueOf(i) + "FilePath"),
                    is(matchPatternAs(expectedFilePath)));
            assertThat(
                    (String) actual.getAttribute("detailImage" + String.valueOf(i) + "FileName"),
                    is(matchPatternAs(expectedFileName)));
        }
    }

    @Test
    public void testSetFileInfoInCaseDeleteFlagIsTrue() throws Exception {
        SchoolFileModel sut = new SchoolFileModel();
        SchoolExtendActionForm inForm = new SchoolExtendActionForm();
        inForm.setSummaryImageFile(new GenerateFormFile(null, null, 0));
        inForm.setDeleteSummaryImageFileFlag(true);
        inForm.setDetailImage1File(new GenerateFormFile(null, null, 0));
        inForm.setDeleteDetailImage1FileFlag(true);
        inForm.setDetailImage2File(new GenerateFormFile(null, null, 0));
        inForm.setDeleteDetailImage2FileFlag(true);
        inForm.setDetailImage2File(new GenerateFormFile(null, null, 0));
        inForm.setDeleteDetailImage3FileFlag(true);
        inForm.setDetailImage3File(new GenerateFormFile(null, null, 0));
        inForm.setDeleteDetailImage4FileFlag(true);
        inForm.setDetailImage4File(new GenerateFormFile(null, null, 0));
        inForm.setDeleteDetailImage5FileFlag(true);
        inForm.setDetailImage5File(new GenerateFormFile(null, null, 0));
        inForm.setDeleteDetailImage6FileFlag(true);
        inForm.setDetailImage6File(new GenerateFormFile(null, null, 0));
        inForm.setDeleteDetailImage7FileFlag(true);
        inForm.setDetailImage7File(new GenerateFormFile(null, null, 0));
        inForm.setDeleteDetailImage8FileFlag(true);
        inForm.setDetailImage8File(new GenerateFormFile(null, null, 0));
        HttpServletRequest stub = new HttpServletRequestSimulator(new ServletContextSimulator());
        HttpServletRequest actual = sut.setFileInfo(inForm, stub);

        assertThat(actual.getAttribute("summaryImageFileName"), is(""));
        for(int i = 1; i < 9; i++) {
            assertThat(actual.getAttribute("detailImage" + String.valueOf(i) + "FileName"), is(""));
        }
    }

    @Test
    public void testSetFileInfoInCaseFilePathExist() throws Exception {
        SchoolFileModel sut = new SchoolFileModel();
        SchoolExtendActionForm inForm = new SchoolExtendActionForm();
        inForm.setSummaryImageFile(new GenerateFormFile(null, null, 0));
        inForm.setSummaryImageFilePath("/img/summaryImage.png");
        inForm.setDetailImage1File(new GenerateFormFile(null, null, 0));
        inForm.setDetailImage1FilePath("/img/detailImage1.png");
        inForm.setDetailImage2FilePath("/img/detailImage2.png");
        inForm.setDetailImage2File(new GenerateFormFile(null, null, 0));
        inForm.setDetailImage3FilePath("/img/detailImage3.png");
        inForm.setDetailImage3File(new GenerateFormFile(null, null, 0));
        inForm.setDetailImage4FilePath("/img/detailImage4.png");
        inForm.setDetailImage4File(new GenerateFormFile(null, null, 0));
        inForm.setDetailImage5FilePath("/img/detailImage5.png");
        inForm.setDetailImage5File(new GenerateFormFile(null, null, 0));
        inForm.setDetailImage6FilePath("/img/detailImage6.png");
        inForm.setDetailImage6File(new GenerateFormFile(null, null, 0));
        inForm.setDetailImage7FilePath("/img/detailImage7.png");
        inForm.setDetailImage7File(new GenerateFormFile(null, null, 0));
        inForm.setDetailImage8FilePath("/img/detailImage8.png");
        inForm.setDetailImage8File(new GenerateFormFile(null, null, 0));
        HttpServletRequest stub = new HttpServletRequestSimulator(new ServletContextSimulator());
        HttpServletRequest actual = sut.setFileInfo(inForm, stub);

        assertThat(actual.getAttribute("summaryImageFilePath"), is("/img/summaryImage.png"));
        for(int i = 1; i < 9; i++) {
            assertThat(
                    actual.getAttribute("detailImage" + String.valueOf(i) + "FilePath"),
                    is("/img/detailImage" + String.valueOf(i) + ".png"));
        }
    }

    @Test
    public void testSetUpdateFileNameInCaseUpdateFileExist() {
        SchoolExtendActionForm expected = new SchoolExtendActionForm();
        expected.setSummaryImageFileNameUpdate("summaryImage.png");
        expected.setSummaryImageFileName("summaryImage.png");
        expected.setDetailImage1FileNameUpdate("detailImage1.png");
        expected.setDetailImage1FileName("detailImage1.png");
        expected.setDetailImage2FileNameUpdate("detailImage2.png");
        expected.setDetailImage2FileName("detailImage2.png");
        expected.setDetailImage3FileNameUpdate("detailImage3.png");
        expected.setDetailImage3FileName("detailImage3.png");
        expected.setDetailImage4FileNameUpdate("detailImage4.png");
        expected.setDetailImage4FileName("detailImage4.png");
        expected.setDetailImage5FileNameUpdate("detailImage5.png");
        expected.setDetailImage5FileName("detailImage5.png");
        expected.setDetailImage6FileNameUpdate("detailImage6.png");
        expected.setDetailImage6FileName("detailImage6.png");
        expected.setDetailImage7FileNameUpdate("detailImage7.png");
        expected.setDetailImage7FileName("detailImage7.png");
        expected.setDetailImage8FileNameUpdate("detailImage8.png");
        expected.setDetailImage8FileName("detailImage8.png");

        SchoolFileModel sut = new SchoolFileModel();
        SchoolExtendActionForm actual = new SchoolExtendActionForm();
        actual.setSummaryImageFileNameUpdate("summaryImage.png");
        actual.setDetailImage1FileNameUpdate("detailImage1.png");
        actual.setDetailImage2FileNameUpdate("detailImage2.png");
        actual.setDetailImage3FileNameUpdate("detailImage3.png");
        actual.setDetailImage4FileNameUpdate("detailImage4.png");
        actual.setDetailImage5FileNameUpdate("detailImage5.png");
        actual.setDetailImage6FileNameUpdate("detailImage6.png");
        actual.setDetailImage7FileNameUpdate("detailImage7.png");
        actual.setDetailImage8FileNameUpdate("detailImage8.png");
        sut.setUpdateFileName(actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testSetUpdateFileNameInCaseNoUpdateFileExist() {
        SchoolExtendActionForm expected = new SchoolExtendActionForm();
        expected.setSummaryImageFileName("");
        expected.setDetailImage1FileName("");
        expected.setDetailImage2FileName("");
        expected.setDetailImage3FileName("");
        expected.setDetailImage4FileName("");
        expected.setDetailImage5FileName("");
        expected.setDetailImage6FileName("");
        expected.setDetailImage7FileName("");
        expected.setDetailImage8FileName("");

        SchoolFileModel sut = new SchoolFileModel();
        SchoolExtendActionForm actual = new SchoolExtendActionForm();
        sut.setUpdateFileName(actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testListDetailImageFilePathInCaseAllFilePathExist() {
        List<String> expected = new ArrayList<>();
        expected.add("/img/detailImage1.png");
        expected.add("/img/detailImage2.png");
        expected.add("/img/detailImage3.png");
        expected.add("/img/detailImage4.png");
        expected.add("/img/detailImage5.png");
        expected.add("/img/detailImage6.png");
        expected.add("/img/detailImage7.png");
        expected.add("/img/detailImage8.png");

        SchoolFileModel sut = new SchoolFileModel();
        SchoolExtendActionForm inForm = new SchoolExtendActionForm();
        inForm.setDetailImage1FilePath("/img/detailImage1.png");
        inForm.setDetailImage2FilePath("/img/detailImage2.png");
        inForm.setDetailImage3FilePath("/img/detailImage3.png");
        inForm.setDetailImage4FilePath("/img/detailImage4.png");
        inForm.setDetailImage5FilePath("/img/detailImage5.png");
        inForm.setDetailImage6FilePath("/img/detailImage6.png");
        inForm.setDetailImage7FilePath("/img/detailImage7.png");
        inForm.setDetailImage8FilePath("/img/detailImage8.png");
        List<String> actual = sut.listDetailImageFilePath(inForm);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(expected.get(i)));
        }
    }

    @Test
    public void testListDetailImageFilePathInCaseTwoFilePathExist() {
        List<String> expected = new ArrayList<>();
        expected.add("/img/detailImage1.png");
        expected.add("/img/detailImage7.png");

        SchoolFileModel sut = new SchoolFileModel();
        SchoolExtendActionForm inForm = new SchoolExtendActionForm();
        inForm.setDetailImage1FilePath("/img/detailImage1.png");
        inForm.setDetailImage7FilePath("/img/detailImage7.png");
        List<String> actual = sut.listDetailImageFilePath(inForm);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(expected.get(i)));
        }
    }

    @Test
    public void testSetDownloadCsvFileDataToSetData() throws Exception {
        String expected = "schools_20220102030405678.csv";

        SchoolFileModel sut = new SchoolFileModel() {
            Timestamp getCurrentTimestamp() {
                return Timestamp.valueOf("2022-01-02 03:04:05.678");
            }
        };
        List<SchoolExtendActionForm> schoolExtendFormList = new ArrayList<>();
        SchoolExtendActionForm form1 = new SchoolExtendActionForm();
        form1.setSchoolId(1);
        form1.setRegistrantAccountId(2);
        form1.setRegistrantAdminId(0);
        form1.setSchoolName("スクール１");
        form1.setSchoolCategoryId(3);
        form1.setSchoolCategoryName("カテゴリー１");
        form1.setSchoolSummary("概要１");
        form1.setSchoolDescription("詳細１");
        form1.setSchoolZipCode1("123");
        form1.setSchoolZipCode2("4567");
        form1.setSchoolPrefectureId(4);
        form1.setSchoolPrefectureName("北海道");
        form1.setSchoolCity("北海道市");
        form1.setSchoolAddress1("北海道町");
        form1.setSchoolAddress2("北海道区");
        form1.setSchoolFee(new BigDecimal("1000"));
        form1.setSupplementaryFee("費用補足１");
        form1.setSchoolUrl("http://test1.com");
        form1.setSchoolReleasePropriety("1");
        form1.setSchoolEntryPropriety("1");
        form1.setSummaryImageFileName("summaryImage11.png");
        form1.setDetailImage1FileName("detailImage11.png");
        form1.setDetailImage2FileName("detailImage21.png");
        form1.setDetailImage3FileName("detailImage31.png");
        form1.setDetailImage4FileName("detailImage41.png");
        form1.setDetailImage5FileName("detailImage51.png");
        form1.setDetailImage6FileName("detailImage61.png");
        form1.setDetailImage7FileName("detailImage71.png");
        form1.setDetailImage8FileName("detailImage81.png");
        form1.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        form1.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        schoolExtendFormList.add(form1);
        SchoolExtendActionForm form2 = new SchoolExtendActionForm();
        form2.setSchoolId(2);
        form2.setRegistrantAccountId(0);
        form2.setRegistrantAdminId(1);
        form2.setSchoolName("スクール２");
        form2.setSchoolCategoryId(4);
        form2.setSchoolCategoryName("カテゴリー２");
        form2.setSchoolSummary("概要２");
        form2.setSchoolDescription("詳細２");
        form2.setSchoolZipCode1("234");
        form2.setSchoolZipCode2("5678");
        form2.setSchoolPrefectureId(5);
        form2.setSchoolPrefectureName("青森県");
        form2.setSchoolCity("青森市");
        form2.setSchoolAddress1("青森町");
        form2.setSchoolAddress2("青森区");
        form2.setSchoolFee(new BigDecimal("2000"));
        form2.setSupplementaryFee("費用補足２");
        form2.setSchoolUrl("http://test2.com");
        form2.setSchoolReleasePropriety("0");
        form2.setSchoolEntryPropriety("0");
        form2.setSummaryImageFileName("summaryImage12.png");
        form2.setDetailImage1FileName("detailImage12.png");
        form2.setDetailImage2FileName("detailImage22.png");
        form2.setDetailImage3FileName("detailImage32.png");
        form2.setDetailImage4FileName("detailImage42.png");
        form2.setDetailImage5FileName("detailImage52.png");
        form2.setDetailImage6FileName("detailImage62.png");
        form2.setDetailImage7FileName("detailImage72.png");
        form2.setDetailImage8FileName("detailImage82.png");
        form2.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-04 05:06:07.789"));
        form2.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        schoolExtendFormList.add(form2);
        SchoolExtendActionForm form3 = new SchoolExtendActionForm();
        form3.setSchoolId(3);
        form3.setRegistrantAccountId(2);
        form3.setRegistrantAdminId(0);
        form3.setSchoolName("スクール３");
        form3.setSchoolCategoryId(5);
        form3.setSchoolCategoryName("カテゴリー３");
        form3.setSchoolSummary("概要３");
        form3.setSchoolDescription("詳細３");
        form3.setSchoolZipCode1("345");
        form3.setSchoolZipCode2("6789");
        form3.setSchoolPrefectureId(6);
        form3.setSchoolPrefectureName("岩手県");
        form3.setSchoolCity("岩手市");
        form3.setSchoolAddress1("岩手町");
        form3.setSchoolAddress2(null);
        form3.setSchoolFee(new BigDecimal("3000"));
        form3.setSupplementaryFee(null);
        form3.setSchoolUrl(null);
        form3.setSchoolReleasePropriety("1");
        form3.setSchoolEntryPropriety("1");
        form3.setSummaryImageFileName(null);
        form3.setDetailImage1FileName(null);
        form3.setDetailImage2FileName(null);
        form3.setDetailImage3FileName(null);
        form3.setDetailImage4FileName(null);
        form3.setDetailImage5FileName(null);
        form3.setDetailImage6FileName(null);
        form3.setDetailImage7FileName(null);
        form3.setDetailImage8FileName(null);
        form3.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        form3.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-07 08:09:10.234"));
        schoolExtendFormList.add(form3);
        HttpServletResponse stub = new HttpServletResponseSimulator();
        HttpServletResponse actual = sut.setDownloadCsvFileData(schoolExtendFormList, stub);

        assertThat(actual, is(contentOf("text/csv;charset=UTF8", expected)));
    }

    @Test
    public void testSetDownloadTemplateCsvFileData() throws Exception {
        String expected = "schools_for_upload_template.csv";

        SchoolFileModel sut = new SchoolFileModel();
        HttpServletResponse stub = new HttpServletResponseSimulator();
        HttpServletResponse actual = sut.setDownloadTemplateCsvFileData(stub);

        assertThat(actual, is(contentOf("text/csv;charset=UTF8", expected)));
    }

    @Test
    public void testUpsertCsvFileDataToUpsertDataSuccessfully() throws Exception {
        SchoolFileModel sut = new SchoolFileModel();
        FormFile formFile = new GenerateFormFile(
                null,
                "model/school/SchoolFileModelTest.testUpsertCsvFileDataToUpsertDataSuccessfully.csv",
                0);
        sut.upsertCsvFileData(formFile);

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.SchoolFileModelTest.testUpsertCsvFileDataToUpsertDataSuccessfully.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("schools");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "schools";
        String sqlQuery = "SELECT * FROM schools ORDER BY school_id ASC";
        String[] ignoreCols =
                {"school_id", "school_registered_at", "school_updated_at",
                "school_delete_flag", "school_deleted_at"};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpsertCsvFileDataToThrowIllegalArgumentExceptionAtInsertingData() throws Exception {
        SchoolFileModel sut = new SchoolFileModel();
        FormFile formFile = new GenerateFormFile(
                null,
                "model/school/SchoolFileModelTest.testUpsertCsvFileDataToThrowIllegalArgumentExceptionAtInsertingData.csv",
                0);
        sut.upsertCsvFileData(formFile);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpsertCsvFileDataToThrowIllegalArgumentExceptionAtUpdateingData() throws Exception {
        SchoolFileModel sut = new SchoolFileModel();
        FormFile formFile = new GenerateFormFile(
                null,
                "model/school/SchoolFileModelTest.testUpsertCsvFileDataToThrowIllegalArgumentExceptionAtUpdatingData.csv",
                0);
        sut.upsertCsvFileData(formFile);
    }

}
