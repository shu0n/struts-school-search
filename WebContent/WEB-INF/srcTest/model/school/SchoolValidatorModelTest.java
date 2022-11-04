package model.school;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.property.PropertyTester.*;

import java.math.BigDecimal;

import org.junit.ClassRule;
import org.junit.Test;

import dao.school.sql.SchoolInsertDataActionForm;
import dao.school.sql.SchoolUpdateDataActionForm;
import test.db.DbUnitTester;

public class SchoolValidatorModelTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SchoolValidatorModelTest.xml") {
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

    @Test
    public void testValidateInsertSchoolDataInCaseValidData() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(2);
        insertDataForm.setSchoolCity("北海道市");
        insertDataForm.setSchoolAddress1("北海道町");
        insertDataForm.setSchoolAddress2("北海道区");
        insertDataForm.setSchoolFee(new BigDecimal("1000"));
        insertDataForm.setSupplementaryFee("費用補足");
        insertDataForm.setSchoolUrl("http://test1.com");
        insertDataForm.setSchoolReleasePropriety("1");
        insertDataForm.setSchoolEntryPropriety("1");
        insertDataForm.setSummaryImageFileName("summaryImage1.png");
        insertDataForm.setDetailImage1FileName("detailImage1.png");
        insertDataForm.setDetailImage2FileName("detailImage2.png");
        insertDataForm.setDetailImage3FileName("detailImage3.png");
        insertDataForm.setDetailImage4FileName("detailImage4.png");
        insertDataForm.setDetailImage5FileName("detailImage5.png");
        insertDataForm.setDetailImage6FileName("detailImage6.png");
        insertDataForm.setDetailImage7FileName("detailImage7.png");
        insertDataForm.setDetailImage8FileName("detailImage8.png");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(true));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseAccountIdAndAdminIdIsZero() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(0);
        insertDataForm.setRegistrantAdminId(0);
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseAccountIdAndAdminIdIsNoZero() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(1);
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseNoMatchedAccountId() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(1);
        insertDataForm.setRegistrantAdminId(0);
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseNoMatchedAdminId() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(0);
        insertDataForm.setRegistrantAdminId(2);
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseSchoolNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName(
                ""
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああい");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseNoMatchedCategoryId() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(11);
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseSchoolSummaryIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary(
                "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああい");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseSchoolDescriptionIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription(
                "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああい");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseInvalidSchoolZipCode1() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("1234");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseInvalidSchoolZipCode2() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("45678");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseNoMatchedPrefectureId() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(5);
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseSchoolCityIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(2);
        insertDataForm.setSchoolCity("ああああああああああああああああああああい");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseSchoolAddress1IsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(2);
        insertDataForm.setSchoolCity("北海道市");
        insertDataForm.setSchoolAddress1(
                "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああい");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseSchoolAddress2IsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(2);
        insertDataForm.setSchoolCity("北海道市");
        insertDataForm.setSchoolAddress1("北海道町");
        insertDataForm.setSchoolAddress2(
                "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああい");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseSchoolFeeIsTooHigh() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(2);
        insertDataForm.setSchoolCity("北海道市");
        insertDataForm.setSchoolAddress1("北海道町");
        insertDataForm.setSchoolAddress2("北海道区");
        insertDataForm.setSchoolFee(new BigDecimal("999999999"));
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseSupplymentaryFeeIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(2);
        insertDataForm.setSchoolCity("北海道市");
        insertDataForm.setSchoolAddress1("北海道町");
        insertDataForm.setSchoolAddress2("北海道区");
        insertDataForm.setSchoolFee(new BigDecimal("1000"));
        insertDataForm.setSupplementaryFee(
                "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああい");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseSchoolUrlIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(2);
        insertDataForm.setSchoolCity("北海道市");
        insertDataForm.setSchoolAddress1("北海道町");
        insertDataForm.setSchoolAddress2("北海道区");
        insertDataForm.setSchoolFee(new BigDecimal("1000"));
        insertDataForm.setSupplementaryFee("費用補足");
        insertDataForm.setSchoolUrl("http;//test1.com");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseInvalidSchoolUrl() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(2);
        insertDataForm.setSchoolCity("北海道市");
        insertDataForm.setSchoolAddress1("北海道町");
        insertDataForm.setSchoolAddress2("北海道区");
        insertDataForm.setSchoolFee(new BigDecimal("1000"));
        insertDataForm.setSupplementaryFee("費用補足");
        insertDataForm.setSchoolUrl(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseInvalidReleasePropriety() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(2);
        insertDataForm.setSchoolCity("北海道市");
        insertDataForm.setSchoolAddress1("北海道町");
        insertDataForm.setSchoolAddress2("北海道区");
        insertDataForm.setSchoolFee(new BigDecimal("1000"));
        insertDataForm.setSupplementaryFee("費用補足");
        insertDataForm.setSchoolUrl("http://test1.com");
        insertDataForm.setSchoolReleasePropriety("2");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseInvalidEntryPropriety() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(2);
        insertDataForm.setSchoolCity("北海道市");
        insertDataForm.setSchoolAddress1("北海道町");
        insertDataForm.setSchoolAddress2("北海道区");
        insertDataForm.setSchoolFee(new BigDecimal("1000"));
        insertDataForm.setSupplementaryFee("費用補足");
        insertDataForm.setSchoolUrl("http://test1.com");
        insertDataForm.setSchoolReleasePropriety("1");
        insertDataForm.setSchoolEntryPropriety("2");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseSummaryImageFileNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(2);
        insertDataForm.setSchoolCity("北海道市");
        insertDataForm.setSchoolAddress1("北海道町");
        insertDataForm.setSchoolAddress2("北海道区");
        insertDataForm.setSchoolFee(new BigDecimal("1000"));
        insertDataForm.setSupplementaryFee("費用補足");
        insertDataForm.setSchoolUrl("http://test1.com");
        insertDataForm.setSchoolReleasePropriety("1");
        insertDataForm.setSchoolEntryPropriety("1");
        insertDataForm.setSummaryImageFileName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseDetailImage1FileNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(2);
        insertDataForm.setSchoolCity("北海道市");
        insertDataForm.setSchoolAddress1("北海道町");
        insertDataForm.setSchoolAddress2("北海道区");
        insertDataForm.setSchoolFee(new BigDecimal("1000"));
        insertDataForm.setSupplementaryFee("費用補足");
        insertDataForm.setSchoolUrl("http://test1.com");
        insertDataForm.setSchoolReleasePropriety("1");
        insertDataForm.setSchoolEntryPropriety("1");
        insertDataForm.setSummaryImageFileName("summaryImage.png");
        insertDataForm.setDetailImage1FileName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseDetailImage2FileNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(2);
        insertDataForm.setSchoolCity("北海道市");
        insertDataForm.setSchoolAddress1("北海道町");
        insertDataForm.setSchoolAddress2("北海道区");
        insertDataForm.setSchoolFee(new BigDecimal("1000"));
        insertDataForm.setSupplementaryFee("費用補足");
        insertDataForm.setSchoolUrl("http://test1.com");
        insertDataForm.setSchoolReleasePropriety("1");
        insertDataForm.setSchoolEntryPropriety("1");
        insertDataForm.setSummaryImageFileName("summaryImage.png");
        insertDataForm.setDetailImage1FileName("detailImage1.png");
        insertDataForm.setDetailImage2FileName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseDetailImage3FileNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(2);
        insertDataForm.setSchoolCity("北海道市");
        insertDataForm.setSchoolAddress1("北海道町");
        insertDataForm.setSchoolAddress2("北海道区");
        insertDataForm.setSchoolFee(new BigDecimal("1000"));
        insertDataForm.setSupplementaryFee("費用補足");
        insertDataForm.setSchoolUrl("http://test1.com");
        insertDataForm.setSchoolReleasePropriety("1");
        insertDataForm.setSchoolEntryPropriety("1");
        insertDataForm.setSummaryImageFileName("summaryImage.png");
        insertDataForm.setDetailImage1FileName("detailImage1.png");
        insertDataForm.setDetailImage2FileName("detailImage2.png");
        insertDataForm.setDetailImage3FileName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseDetailImage4FileNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(2);
        insertDataForm.setSchoolCity("北海道市");
        insertDataForm.setSchoolAddress1("北海道町");
        insertDataForm.setSchoolAddress2("北海道区");
        insertDataForm.setSchoolFee(new BigDecimal("1000"));
        insertDataForm.setSupplementaryFee("費用補足");
        insertDataForm.setSchoolUrl("http://test1.com");
        insertDataForm.setSchoolReleasePropriety("1");
        insertDataForm.setSchoolEntryPropriety("1");
        insertDataForm.setSummaryImageFileName("summaryImage.png");
        insertDataForm.setDetailImage1FileName("detailImage1.png");
        insertDataForm.setDetailImage2FileName("detailImage2.png");
        insertDataForm.setDetailImage3FileName("detailImage3.png");
        insertDataForm.setDetailImage4FileName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseDetailImage5FileNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(2);
        insertDataForm.setSchoolCity("北海道市");
        insertDataForm.setSchoolAddress1("北海道町");
        insertDataForm.setSchoolAddress2("北海道区");
        insertDataForm.setSchoolFee(new BigDecimal("1000"));
        insertDataForm.setSupplementaryFee("費用補足");
        insertDataForm.setSchoolUrl("http://test1.com");
        insertDataForm.setSchoolReleasePropriety("1");
        insertDataForm.setSchoolEntryPropriety("1");
        insertDataForm.setSummaryImageFileName("summaryImage.png");
        insertDataForm.setDetailImage1FileName("detailImage1.png");
        insertDataForm.setDetailImage2FileName("detailImage2.png");
        insertDataForm.setDetailImage3FileName("detailImage3.png");
        insertDataForm.setDetailImage4FileName("detailImage4.png");
        insertDataForm.setDetailImage5FileName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseDetailImage6FileNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(2);
        insertDataForm.setSchoolCity("北海道市");
        insertDataForm.setSchoolAddress1("北海道町");
        insertDataForm.setSchoolAddress2("北海道区");
        insertDataForm.setSchoolFee(new BigDecimal("1000"));
        insertDataForm.setSupplementaryFee("費用補足");
        insertDataForm.setSchoolUrl("http://test1.com");
        insertDataForm.setSchoolReleasePropriety("1");
        insertDataForm.setSchoolEntryPropriety("1");
        insertDataForm.setSummaryImageFileName("summaryImage.png");
        insertDataForm.setDetailImage1FileName("detailImage1.png");
        insertDataForm.setDetailImage2FileName("detailImage2.png");
        insertDataForm.setDetailImage3FileName("detailImage3.png");
        insertDataForm.setDetailImage4FileName("detailImage4.png");
        insertDataForm.setDetailImage5FileName("detailImage5.png");
        insertDataForm.setDetailImage6FileName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseDetailImage7FileNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(2);
        insertDataForm.setSchoolCity("北海道市");
        insertDataForm.setSchoolAddress1("北海道町");
        insertDataForm.setSchoolAddress2("北海道区");
        insertDataForm.setSchoolFee(new BigDecimal("1000"));
        insertDataForm.setSupplementaryFee("費用補足");
        insertDataForm.setSchoolUrl("http://test1.com");
        insertDataForm.setSchoolReleasePropriety("1");
        insertDataForm.setSchoolEntryPropriety("1");
        insertDataForm.setSummaryImageFileName("summaryImage.png");
        insertDataForm.setDetailImage1FileName("detailImage1.png");
        insertDataForm.setDetailImage2FileName("detailImage2.png");
        insertDataForm.setDetailImage3FileName("detailImage3.png");
        insertDataForm.setDetailImage4FileName("detailImage4.png");
        insertDataForm.setDetailImage5FileName("detailImage5.png");
        insertDataForm.setDetailImage6FileName("detailImage6.png");
        insertDataForm.setDetailImage7FileName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateInsertSchoolDataInCaseDetailImage8FileNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolInsertDataActionForm insertDataForm = new SchoolInsertDataActionForm();
        insertDataForm.setRegistrantAccountId(5);
        insertDataForm.setRegistrantAdminId(0);
        insertDataForm.setSchoolName("テストスクール");
        insertDataForm.setSchoolCategoryId(8);
        insertDataForm.setSchoolSummary("スクール概要");
        insertDataForm.setSchoolDescription("スクール詳細");
        insertDataForm.setSchoolZipCode1("123");
        insertDataForm.setSchoolZipCode2("4567");
        insertDataForm.setSchoolPrefectureId(2);
        insertDataForm.setSchoolCity("北海道市");
        insertDataForm.setSchoolAddress1("北海道町");
        insertDataForm.setSchoolAddress2("北海道区");
        insertDataForm.setSchoolFee(new BigDecimal("1000"));
        insertDataForm.setSupplementaryFee("費用補足");
        insertDataForm.setSchoolUrl("http://test1.com");
        insertDataForm.setSchoolReleasePropriety("1");
        insertDataForm.setSchoolEntryPropriety("1");
        insertDataForm.setSummaryImageFileName("summaryImage.png");
        insertDataForm.setDetailImage1FileName("detailImage1.png");
        insertDataForm.setDetailImage2FileName("detailImage2.png");
        insertDataForm.setDetailImage3FileName("detailImage3.png");
        insertDataForm.setDetailImage4FileName("detailImage4.png");
        insertDataForm.setDetailImage5FileName("detailImage5.png");
        insertDataForm.setDetailImage6FileName("detailImage6.png");
        insertDataForm.setDetailImage7FileName("detailImage7.png");
        insertDataForm.setDetailImage8FileName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateInsertSchoolData(insertDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseValidDataAndRegistrantAccountExist() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setSchoolId(11);
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("北海道市");
        updateDataForm.setSchoolAddress1("北海道町");
        updateDataForm.setSchoolAddress2("北海道区");
        updateDataForm.setSchoolFee(new BigDecimal("1000"));
        updateDataForm.setSupplementaryFee("費用補足");
        updateDataForm.setSchoolUrl("http://test1.com");
        updateDataForm.setSchoolReleasePropriety("1");
        updateDataForm.setSchoolEntryPropriety("1");
        updateDataForm.setSummaryImageFileName("summaryImage1.png");
        updateDataForm.setDetailImage1FileName("detailImage1.png");
        updateDataForm.setDetailImage2FileName("detailImage2.png");
        updateDataForm.setDetailImage3FileName("detailImage3.png");
        updateDataForm.setDetailImage4FileName("detailImage4.png");
        updateDataForm.setDetailImage5FileName("detailImage5.png");
        updateDataForm.setDetailImage6FileName("detailImage6.png");
        updateDataForm.setDetailImage7FileName("detailImage7.png");
        updateDataForm.setDetailImage8FileName("detailImage8.png");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(true));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseValidDataAndNoRegistrantAccountExist() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setSchoolId(11);
        updateDataForm.setRegistrantAccountId(6);
        updateDataForm.setRegistrantAdminId(0);
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseValidDataRegistrantAdminExist() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setSchoolId(12);
        updateDataForm.setRegistrantAccountId(0);
        updateDataForm.setRegistrantAdminId(1);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("北海道市");
        updateDataForm.setSchoolAddress1("北海道町");
        updateDataForm.setSchoolAddress2("北海道区");
        updateDataForm.setSchoolFee(new BigDecimal("1000"));
        updateDataForm.setSupplementaryFee("費用補足");
        updateDataForm.setSchoolUrl("http://test1.com");
        updateDataForm.setSchoolReleasePropriety("1");
        updateDataForm.setSchoolEntryPropriety("1");
        updateDataForm.setSummaryImageFileName("summaryImage1.png");
        updateDataForm.setDetailImage1FileName("detailImage1.png");
        updateDataForm.setDetailImage2FileName("detailImage2.png");
        updateDataForm.setDetailImage3FileName("detailImage3.png");
        updateDataForm.setDetailImage4FileName("detailImage4.png");
        updateDataForm.setDetailImage5FileName("detailImage5.png");
        updateDataForm.setDetailImage6FileName("detailImage6.png");
        updateDataForm.setDetailImage7FileName("detailImage7.png");
        updateDataForm.setDetailImage8FileName("detailImage8.png");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(true));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseValidDataNoRegistrantAdminExist() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setSchoolId(13);
        updateDataForm.setRegistrantAccountId(0);
        updateDataForm.setRegistrantAdminId(2);
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseAccountIdAndAdminIdIsZero() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(0);
        updateDataForm.setRegistrantAdminId(0);
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseAccountIdAndAdminIdIsNoZero() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(1);
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseNoMatchedAccountId() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(1);
        updateDataForm.setRegistrantAdminId(0);
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseNoMatchedAdminId() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(0);
        updateDataForm.setRegistrantAdminId(2);
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseSchoolNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName(""
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああい");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseNoMatchedCategoryId() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(11);
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseSchoolSummaryIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary(
                "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああい");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseSchoolDescriptionIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription(
                "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああい");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseInvalidSchoolZipCode1() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("1234");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseInvalidSchoolZipCode2() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("45678");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseNoMatchedPrefectureId() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(5);
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseSchoolCityIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("ああああああああああああああああああああい");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseSchoolAddress1IsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("北海道市");
        updateDataForm.setSchoolAddress1(
                "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああい");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseSchoolAddress2IsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("北海道市");
        updateDataForm.setSchoolAddress1("北海道町");
        updateDataForm.setSchoolAddress2(
                "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああい");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseSchoolFeeIsTooHigh() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("北海道市");
        updateDataForm.setSchoolAddress1("北海道町");
        updateDataForm.setSchoolAddress2("北海道区");
        updateDataForm.setSchoolFee(new BigDecimal("999999999"));
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseSupplymentaryFeeIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("北海道市");
        updateDataForm.setSchoolAddress1("北海道町");
        updateDataForm.setSchoolAddress2("北海道区");
        updateDataForm.setSchoolFee(new BigDecimal("1000"));
        updateDataForm.setSupplementaryFee(
                "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああ"
                + "あああああああああああああああああああああああああい");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseSchoolUrlIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("北海道市");
        updateDataForm.setSchoolAddress1("北海道町");
        updateDataForm.setSchoolAddress2("北海道区");
        updateDataForm.setSchoolFee(new BigDecimal("1000"));
        updateDataForm.setSupplementaryFee("費用補足");
        updateDataForm.setSchoolUrl(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseInvalidSchoolUrl() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("北海道市");
        updateDataForm.setSchoolAddress1("北海道町");
        updateDataForm.setSchoolAddress2("北海道区");
        updateDataForm.setSchoolFee(new BigDecimal("1000"));
        updateDataForm.setSupplementaryFee("費用補足");
        updateDataForm.setSchoolUrl("http;//test1.com");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseInvalidReleasePropriety() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("北海道市");
        updateDataForm.setSchoolAddress1("北海道町");
        updateDataForm.setSchoolAddress2("北海道区");
        updateDataForm.setSchoolFee(new BigDecimal("1000"));
        updateDataForm.setSupplementaryFee("費用補足");
        updateDataForm.setSchoolUrl("http://test1.com");
        updateDataForm.setSchoolReleasePropriety("2");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseInvalidEntryPropriety() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("北海道市");
        updateDataForm.setSchoolAddress1("北海道町");
        updateDataForm.setSchoolAddress2("北海道区");
        updateDataForm.setSchoolFee(new BigDecimal("1000"));
        updateDataForm.setSupplementaryFee("費用補足");
        updateDataForm.setSchoolUrl("http://test1.com");
        updateDataForm.setSchoolReleasePropriety("1");
        updateDataForm.setSchoolEntryPropriety("2");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseSummaryImageFileNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("北海道市");
        updateDataForm.setSchoolAddress1("北海道町");
        updateDataForm.setSchoolAddress2("北海道区");
        updateDataForm.setSchoolFee(new BigDecimal("1000"));
        updateDataForm.setSupplementaryFee("費用補足");
        updateDataForm.setSchoolUrl("http://test1.com");
        updateDataForm.setSchoolReleasePropriety("1");
        updateDataForm.setSchoolEntryPropriety("1");
        updateDataForm.setSummaryImageFileName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseDetailImage1FileNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("北海道市");
        updateDataForm.setSchoolAddress1("北海道町");
        updateDataForm.setSchoolAddress2("北海道区");
        updateDataForm.setSchoolFee(new BigDecimal("1000"));
        updateDataForm.setSupplementaryFee("費用補足");
        updateDataForm.setSchoolUrl("http://test1.com");
        updateDataForm.setSchoolReleasePropriety("1");
        updateDataForm.setSchoolEntryPropriety("1");
        updateDataForm.setSummaryImageFileName("summaryImage.png");
        updateDataForm.setDetailImage1FileName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseDetailImage2FileNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("北海道市");
        updateDataForm.setSchoolAddress1("北海道町");
        updateDataForm.setSchoolAddress2("北海道区");
        updateDataForm.setSchoolFee(new BigDecimal("1000"));
        updateDataForm.setSupplementaryFee("費用補足");
        updateDataForm.setSchoolUrl("http://test1.com");
        updateDataForm.setSchoolReleasePropriety("1");
        updateDataForm.setSchoolEntryPropriety("1");
        updateDataForm.setSummaryImageFileName("summaryImage.png");
        updateDataForm.setDetailImage1FileName("detailImage1.png");
        updateDataForm.setDetailImage2FileName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseDetailImage3FileNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("北海道市");
        updateDataForm.setSchoolAddress1("北海道町");
        updateDataForm.setSchoolAddress2("北海道区");
        updateDataForm.setSchoolFee(new BigDecimal("1000"));
        updateDataForm.setSupplementaryFee("費用補足");
        updateDataForm.setSchoolUrl("http://test1.com");
        updateDataForm.setSchoolReleasePropriety("1");
        updateDataForm.setSchoolEntryPropriety("1");
        updateDataForm.setSummaryImageFileName("summaryImage.png");
        updateDataForm.setDetailImage1FileName("detailImage1.png");
        updateDataForm.setDetailImage2FileName("detailImage2.png");
        updateDataForm.setDetailImage3FileName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseDetailImage4FileNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("北海道市");
        updateDataForm.setSchoolAddress1("北海道町");
        updateDataForm.setSchoolAddress2("北海道区");
        updateDataForm.setSchoolFee(new BigDecimal("1000"));
        updateDataForm.setSupplementaryFee("費用補足");
        updateDataForm.setSchoolUrl("http://test1.com");
        updateDataForm.setSchoolReleasePropriety("1");
        updateDataForm.setSchoolEntryPropriety("1");
        updateDataForm.setSummaryImageFileName("summaryImage.png");
        updateDataForm.setDetailImage1FileName("detailImage1.png");
        updateDataForm.setDetailImage2FileName("detailImage2.png");
        updateDataForm.setDetailImage3FileName("detailImage3.png");
        updateDataForm.setDetailImage4FileName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseDetailImage5FileNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("北海道市");
        updateDataForm.setSchoolAddress1("北海道町");
        updateDataForm.setSchoolAddress2("北海道区");
        updateDataForm.setSchoolFee(new BigDecimal("1000"));
        updateDataForm.setSupplementaryFee("費用補足");
        updateDataForm.setSchoolUrl("http://test1.com");
        updateDataForm.setSchoolReleasePropriety("1");
        updateDataForm.setSchoolEntryPropriety("1");
        updateDataForm.setSummaryImageFileName("summaryImage.png");
        updateDataForm.setDetailImage1FileName("detailImage1.png");
        updateDataForm.setDetailImage2FileName("detailImage2.png");
        updateDataForm.setDetailImage3FileName("detailImage3.png");
        updateDataForm.setDetailImage4FileName("detailImage4.png");
        updateDataForm.setDetailImage5FileName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseDetailImage6FileNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("北海道市");
        updateDataForm.setSchoolAddress1("北海道町");
        updateDataForm.setSchoolAddress2("北海道区");
        updateDataForm.setSchoolFee(new BigDecimal("1000"));
        updateDataForm.setSupplementaryFee("費用補足");
        updateDataForm.setSchoolUrl("http://test1.com");
        updateDataForm.setSchoolReleasePropriety("1");
        updateDataForm.setSchoolEntryPropriety("1");
        updateDataForm.setSummaryImageFileName("summaryImage.png");
        updateDataForm.setDetailImage1FileName("detailImage1.png");
        updateDataForm.setDetailImage2FileName("detailImage2.png");
        updateDataForm.setDetailImage3FileName("detailImage3.png");
        updateDataForm.setDetailImage4FileName("detailImage4.png");
        updateDataForm.setDetailImage5FileName("detailImage5.png");
        updateDataForm.setDetailImage6FileName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseDetailImage7FileNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("北海道市");
        updateDataForm.setSchoolAddress1("北海道町");
        updateDataForm.setSchoolAddress2("北海道区");
        updateDataForm.setSchoolFee(new BigDecimal("1000"));
        updateDataForm.setSupplementaryFee("費用補足");
        updateDataForm.setSchoolUrl("http://test1.com");
        updateDataForm.setSchoolReleasePropriety("1");
        updateDataForm.setSchoolEntryPropriety("1");
        updateDataForm.setSummaryImageFileName("summaryImage.png");
        updateDataForm.setDetailImage1FileName("detailImage1.png");
        updateDataForm.setDetailImage2FileName("detailImage2.png");
        updateDataForm.setDetailImage3FileName("detailImage3.png");
        updateDataForm.setDetailImage4FileName("detailImage4.png");
        updateDataForm.setDetailImage5FileName("detailImage5.png");
        updateDataForm.setDetailImage6FileName("detailImage6.png");
        updateDataForm.setDetailImage7FileName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testValidateUpdateSchoolDataInCaseDetailImage8FileNameIsTooLong() throws Exception {
        SchoolValidatorModel sut = new SchoolValidatorModel();
        SchoolUpdateDataActionForm updateDataForm = new SchoolUpdateDataActionForm();
        updateDataForm.setRegistrantAccountId(5);
        updateDataForm.setRegistrantAdminId(0);
        updateDataForm.setSchoolName("テストスクール");
        updateDataForm.setSchoolCategoryId(8);
        updateDataForm.setSchoolSummary("スクール概要");
        updateDataForm.setSchoolDescription("スクール詳細");
        updateDataForm.setSchoolZipCode1("123");
        updateDataForm.setSchoolZipCode2("4567");
        updateDataForm.setSchoolPrefectureId(2);
        updateDataForm.setSchoolCity("北海道市");
        updateDataForm.setSchoolAddress1("北海道町");
        updateDataForm.setSchoolAddress2("北海道区");
        updateDataForm.setSchoolFee(new BigDecimal("1000"));
        updateDataForm.setSupplementaryFee("費用補足");
        updateDataForm.setSchoolUrl("http://test1.com");
        updateDataForm.setSchoolReleasePropriety("1");
        updateDataForm.setSchoolEntryPropriety("1");
        updateDataForm.setSummaryImageFileName("summaryImage.png");
        updateDataForm.setDetailImage1FileName("detailImage1.png");
        updateDataForm.setDetailImage2FileName("detailImage2.png");
        updateDataForm.setDetailImage3FileName("detailImage3.png");
        updateDataForm.setDetailImage4FileName("detailImage4.png");
        updateDataForm.setDetailImage5FileName("detailImage5.png");
        updateDataForm.setDetailImage6FileName("detailImage6.png");
        updateDataForm.setDetailImage7FileName("detailImage7.png");
        updateDataForm.setDetailImage8FileName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
        boolean actual = sut.validateUpdateSchoolData(updateDataForm);

        assertThat(actual, is(false));
    }

}
