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

import dao.school.sql.SchoolUpdateDataActionForm;
import test.db.DbUnitTester;

public class UpdateSchoolDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.UpdateSchoolDAOTest.xml") {
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
    public void testUpdateSchoolToUpdateOneSchoolByAccount() throws Exception {
        UpdateSchoolDAO sut = new UpdateSchoolDAO() {
            String getStringCurrentTimestamp() {
                return "2022-03-04 05:06:07.891";
            }
        };
        SchoolUpdateDataActionForm dataForm = new SchoolUpdateDataActionForm();
        dataForm.setSchoolId(1);
        dataForm.setRegistrantAccountId(0);
        dataForm.setRegistrantAdminId(61);
        dataForm.setSchoolName("更新テストスクール1");
        dataForm.setSchoolCategoryId(41);
        dataForm.setSchoolSummary("更新スクール概要1");
        dataForm.setSchoolDescription("更新スクール詳細1");
        dataForm.setSchoolZipCode1("234");
        dataForm.setSchoolZipCode2("5678");
        dataForm.setSchoolPrefectureId(11);
        dataForm.setSchoolCity("北海道市");
        dataForm.setSchoolAddress1("北海道区");
        dataForm.setSchoolAddress2("北海道町");
        dataForm.setSchoolFee(new BigDecimal(4321));
        dataForm.setSupplementaryFee("更新費用補足1");
        dataForm.setSchoolUrl("http://updatetest1.com");
        dataForm.setSchoolReleasePropriety("0");
        dataForm.setSchoolEntryPropriety("0");
        dataForm.setSummaryImageFileName("update_summary_image11.png");
        dataForm.setDetailImage1FileName("update_detail_image11.png");
        dataForm.setDetailImage2FileName("update_detail_image12.png");
        dataForm.setDetailImage3FileName("update_detail_image13.png");
        dataForm.setDetailImage4FileName("update_detail_image14.png");
        dataForm.setDetailImage5FileName("update_detail_image15.png");
        dataForm.setDetailImage6FileName("update_detail_image16.png");
        dataForm.setDetailImage7FileName("update_detail_image17.png");
        dataForm.setDetailImage8FileName("update_detail_image18.png");
        boolean actual = sut.updateSchool(dataForm);

        assertThat(actual, is(true));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.UpdateSchoolDAOTest.testUpdateSchoolToUpdateOneSchoolByAccount.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("schools");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "schools";
        String sqlQuery = "SELECT * FROM schools WHERE school_id=1";
        String[] ignoreCols = {};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test
    public void testUpdateSchoolToUpdateOneSchoolByAdmin() throws Exception {
        UpdateSchoolDAO sut = new UpdateSchoolDAO() {
            String getStringCurrentTimestamp() {
                return "2022-03-04 05:06:07.891";
            }
        };
        SchoolUpdateDataActionForm dataForm = new SchoolUpdateDataActionForm();
        dataForm.setSchoolId(6);
        dataForm.setRegistrantAccountId(23);
        dataForm.setRegistrantAdminId(0);
        dataForm.setSchoolName("更新テストスクール6");
        dataForm.setSchoolCategoryId(42);
        dataForm.setSchoolSummary("更新スクール概要6");
        dataForm.setSchoolDescription("更新スクール詳細6");
        dataForm.setSchoolZipCode1("345");
        dataForm.setSchoolZipCode2("6789");
        dataForm.setSchoolPrefectureId(13);
        dataForm.setSchoolCity("愛知市");
        dataForm.setSchoolAddress1("愛知区");
        dataForm.setSchoolAddress2("愛知町");
        dataForm.setSchoolFee(new BigDecimal(8765));
        dataForm.setSupplementaryFee("更新費用補足6");
        dataForm.setSchoolUrl("http://updatetest6.com");
        dataForm.setSchoolReleasePropriety("1");
        dataForm.setSchoolEntryPropriety("1");
        dataForm.setSummaryImageFileName("update_summary_image61.png");
        dataForm.setDetailImage1FileName("update_detail_image61.png");
        dataForm.setDetailImage2FileName("update_detail_image62.png");
        dataForm.setDetailImage3FileName("update_detail_image63.png");
        dataForm.setDetailImage4FileName("update_detail_image64.png");
        dataForm.setDetailImage5FileName("update_detail_image65.png");
        dataForm.setDetailImage6FileName("update_detail_image66.png");
        dataForm.setDetailImage7FileName("update_detail_image67.png");
        dataForm.setDetailImage8FileName("update_detail_image68.png");
        boolean actual = sut.updateSchool(dataForm);

        assertThat(actual, is(true));

        // 期待値を記載したデータセットを編集して比較用のテーブル情報を取得する。
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream(
                "expectedDataSet.UpdateSchoolDAOTest.testUpdateSchoolToUpdateOneSchoolByAdmin.xml"));
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(expectedDataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        ITable expectedTable = replacementDataSet.getTable("schools");

        // 実際のテーブル情報の取得に必要な変数を生成する。
        String tableName = "schools";
        String sqlQuery = "SELECT * FROM schools WHERE school_id=6";
        String[] ignoreCols = {};

        Assertion.assertEqualsByQuery(expectedTable, dbUnitTester.getConnection(), tableName, sqlQuery, ignoreCols);
    }

    @Test
    public void testUpdateAccountToUpdateNoAccountInCaseNoSchoolIdExist() throws Exception {
        UpdateSchoolDAO sut = new UpdateSchoolDAO();
        SchoolUpdateDataActionForm dataForm = new SchoolUpdateDataActionForm();
        dataForm.setSchoolId(2);
        dataForm.setSchoolName("更新失敗テストスクール2");
        boolean actual = sut.updateSchool(dataForm);

        assertThat(actual, is(false));
    }

    @Test
    public void testUpdateAccountToUpdateNoAccountInCaseRegistrantAccountIdAndRegistrantAdminIdExist()
            throws Exception {
        UpdateSchoolDAO sut = new UpdateSchoolDAO();
        SchoolUpdateDataActionForm dataForm = new SchoolUpdateDataActionForm();
        dataForm.setSchoolId(1);
        dataForm.setRegistrantAccountId(21);
        dataForm.setRegistrantAdminId(62);
        dataForm.setSchoolName("更新失敗テストスクール1");
        boolean actual = sut.updateSchool(dataForm);

        assertThat(actual, is(false));
    }

}
