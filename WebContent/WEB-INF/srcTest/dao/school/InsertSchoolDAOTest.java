package dao.school;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.property.PropertyTester.*;

import java.math.BigDecimal;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import dao.school.sql.SchoolInsertDataActionForm;
import test.db.DbUnitTester;

public class InsertSchoolDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.InsertSchoolDAOTest.xml") {
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
    public void testInsertSchoolToCreateOneSchoolByAccount() throws Exception {
        InsertSchoolDAO sut = new InsertSchoolDAO();
        SchoolInsertDataActionForm dataForm = new SchoolInsertDataActionForm();
        dataForm.setRegistrantAccountId(2);
        dataForm.setRegistrantAdminId(0);
        dataForm.setSchoolName("テストスクール1");
        dataForm.setSchoolCategoryId(23);
        dataForm.setSchoolSummary("スクール概要1");
        dataForm.setSchoolDescription("スクール詳細1");
        dataForm.setSchoolZipCode1("123");
        dataForm.setSchoolZipCode2("4567");
        dataForm.setSchoolPrefectureId(14);
        dataForm.setSchoolCity("大阪市");
        dataForm.setSchoolAddress1("大阪区");
        dataForm.setSchoolAddress2("大阪町");
        dataForm.setSchoolFee(new BigDecimal(5432));
        dataForm.setSupplementaryFee("費用補足1");
        dataForm.setSchoolUrl("http://test1.com");
        dataForm.setSchoolReleasePropriety("1");
        dataForm.setSchoolEntryPropriety("1");
        dataForm.setSummaryImageFileName("summary_image11.png");
        dataForm.setDetailImage1FileName("detail_image11.png");
        dataForm.setDetailImage2FileName("detail_image12.png");
        dataForm.setDetailImage3FileName("detail_image13.png");
        dataForm.setDetailImage4FileName("detail_image14.png");
        dataForm.setDetailImage5FileName("detail_image15.png");
        dataForm.setDetailImage6FileName("detail_image16.png");
        dataForm.setDetailImage7FileName("detail_image17.png");
        dataForm.setDetailImage8FileName("detail_image18.png");
        int actual = sut.insertSchool(dataForm);

        assertThat(actual, is(not(0)));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.InsertSchoolDAOTest.testInsertSchoolToCreateOneSchoolByAccount.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("schools");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "schools";
        String sqlQuery = "SELECT * FROM schools WHERE registrant_account_id=2";
        String[] ignoreCols = {"school_id", "school_registered_at", "school_updated_at"};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test
    public void testInsertSchoolToCreateOneSchoolByAdmin() throws Exception {
        InsertSchoolDAO sut = new InsertSchoolDAO();
        SchoolInsertDataActionForm dataForm = new SchoolInsertDataActionForm();
        dataForm.setRegistrantAccountId(0);
        dataForm.setRegistrantAdminId(1);
        dataForm.setSchoolName("テストスクール6");
        dataForm.setSchoolCategoryId(24);
        dataForm.setSchoolSummary("スクール概要6");
        dataForm.setSchoolDescription("スクール詳細6");
        dataForm.setSchoolZipCode1("123");
        dataForm.setSchoolZipCode2("5678");
        dataForm.setSchoolPrefectureId(12);
        dataForm.setSchoolCity("東京市");
        dataForm.setSchoolAddress1("東京区");
        dataForm.setSchoolAddress2("東京町");
        dataForm.setSchoolFee(new BigDecimal(9876));
        dataForm.setSupplementaryFee("費用補足6");
        dataForm.setSchoolUrl("http://test6.com");
        dataForm.setSchoolReleasePropriety("0");
        dataForm.setSchoolEntryPropriety("0");
        dataForm.setSummaryImageFileName("summary_image61.png");
        dataForm.setDetailImage1FileName("detail_image61.png");
        dataForm.setDetailImage2FileName("detail_image62.png");
        dataForm.setDetailImage3FileName("detail_image63.png");
        dataForm.setDetailImage4FileName("detail_image64.png");
        dataForm.setDetailImage5FileName("detail_image65.png");
        dataForm.setDetailImage6FileName("detail_image66.png");
        dataForm.setDetailImage7FileName("detail_image67.png");
        dataForm.setDetailImage8FileName("detail_image68.png");
        int actual = sut.insertSchool(dataForm);

        assertThat(actual, is(not(0)));

        // 実際のテーブル情報の取得に必要な変数を生成する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.InsertSchoolDAOTest.testInsertSchoolToCreateOneSchoolByAdmin.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("schools");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "schools";
        String sqlQuery = "SELECT * FROM schools WHERE registrant_admin_id=1";
        String[] ignoreCols = {"school_id", "school_registered_at", "school_updated_at"};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

}
