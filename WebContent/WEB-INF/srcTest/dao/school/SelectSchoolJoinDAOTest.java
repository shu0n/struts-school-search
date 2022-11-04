package dao.school;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.SchoolExtendActionForm;
import dao.school.sql.SchoolSelectJoinWhereActionForm;
import test.db.DbUnitTester;

public class SelectSchoolJoinDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SelectSchoolJoinDAOTest.xml") {
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
    public void testSelectMatchedSchoolToGetAllSchool() throws Exception {
        String[] expected = {"1", "2", "3", "4", "5", "6", "7"};

        SelectSchoolJoinDAO sut = new SelectSchoolJoinDAO();
        SchoolSelectJoinWhereActionForm whereForm = new SchoolSelectJoinWhereActionForm();
        List<SchoolExtendActionForm> formList = sut.selectMatchedSchool(whereForm);
        String[] actual = new String[7];
        for(int i = 0; i < formList.size(); i++) {
            actual[i] = String.valueOf(formList.get(i).getSchoolId());
        }

        assertThat(actual, is(arrayContaining(expected)));
    }

    @Test
    public void testSelectMatchedSchoolToGetOneSchoolBySchoolId() throws Exception {
        SchoolExtendActionForm expected = new SchoolExtendActionForm();
        expected.setSchoolId(1);
        expected.setRegistrantAccountId(22);
        expected.setRegistrantLastName("佐藤");
        expected.setRegistrantFirstName("次郎");
        expected.setRegistrantLastNameKana("サトウ");
        expected.setRegistrantFirstNameKana("ジロウ");
        expected.setProfileImageFileName("profile_image2.png");
        expected.setSelfIntroduction("自己紹介2");
        expected.setRegistrantAdminId(0);
        expected.setSchoolName("テストスクール1");
        expected.setSchoolCategoryId(43);
        expected.setSchoolCategoryName("日本文化");
        expected.setSchoolSummary("スクール概要1");
        expected.setSchoolDescription("スクール詳細1");
        expected.setSchoolZipCode1("123");
        expected.setSchoolZipCode2("4567");
        expected.setSchoolPrefectureId(14);
        expected.setSchoolPrefectureName("大阪府");
        expected.setSchoolCity("大阪市");
        expected.setSchoolAddress1("大阪区");
        expected.setSchoolAddress2("大阪町");
        expected.setSchoolFee(new BigDecimal(5432));
        expected.setSupplementaryFee("費用補足1");
        expected.setSchoolUrl("http://test1.com");
        expected.setSchoolReleasePropriety("1");
        expected.setSchoolEntryPropriety("1");
        expected.setSummaryImageFileName("summary_image11.png");
        expected.setDetailImage1FileName("detail_image11.png");
        expected.setDetailImage2FileName("detail_image12.png");
        expected.setDetailImage3FileName("detail_image13.png");
        expected.setDetailImage4FileName("detail_image14.png");
        expected.setDetailImage5FileName("detail_image15.png");
        expected.setDetailImage6FileName("detail_image16.png");
        expected.setDetailImage7FileName("detail_image17.png");
        expected.setDetailImage8FileName("detail_image18.png");
        expected.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));

        SelectSchoolJoinDAO sut = new SelectSchoolJoinDAO();
        SchoolSelectJoinWhereActionForm whereForm = new SchoolSelectJoinWhereActionForm();
        whereForm.setSchoolId(1);
        SchoolExtendActionForm actual = sut.selectMatchedSchool(whereForm).get(0);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testSelectMatchedSchoolToGetFourSchoolBySchoolReleasePropriety() throws Exception {
        int expected = 4;

        SelectSchoolJoinDAO sut = new SelectSchoolJoinDAO();
        SchoolSelectJoinWhereActionForm whereForm = new SchoolSelectJoinWhereActionForm();
        whereForm.setSchoolReleasePropriety("1");
        List<SchoolExtendActionForm> actual = sut.selectMatchedSchool(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedSchoolByRegistrantAccountToGetOneSchoolByMultipleCondition() throws Exception {
        int expected = 1;

        SelectSchoolJoinDAO sut = new SelectSchoolJoinDAO();
        SchoolSelectJoinWhereActionForm whereForm = new SchoolSelectJoinWhereActionForm();
        whereForm.setSchoolName("テストスクール4");
        whereForm.setRegistrantAccountId(25);
        whereForm.setSchoolCategoryId(41);
        whereForm.setSchoolPrefectureId(17);
        whereForm.setSchoolFee("2109");
        whereForm.setSchoolReleasePropriety("0");
        whereForm.setSchoolEntryPropriety("0");
        List<SchoolExtendActionForm> actual = sut.selectMatchedSchool(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedSchoolByRegistrantAccountToGetNoSchoolByMultipleCondition() throws Exception {
        int expected = 0;

        SelectSchoolJoinDAO sut = new SelectSchoolJoinDAO();
        SchoolSelectJoinWhereActionForm whereForm = new SchoolSelectJoinWhereActionForm();
        whereForm.setSchoolName("テストスクール1");
        whereForm.setRegistrantAccountId(22);
        whereForm.setSchoolCategoryId(41);
        whereForm.setSchoolPrefectureId(17);
        whereForm.setSchoolFee("2109");
        whereForm.setSchoolReleasePropriety("1");
        whereForm.setSchoolEntryPropriety("0");
        List<SchoolExtendActionForm> actual = sut.selectMatchedSchool(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedSchoolByRegistrantAdminToGetOneSchoolByMultipleCondition() throws Exception {
        int expected = 1;

        SelectSchoolJoinDAO sut = new SelectSchoolJoinDAO();
        SchoolSelectJoinWhereActionForm whereForm = new SchoolSelectJoinWhereActionForm();
        whereForm.setSchoolName("テストスクール6");
        whereForm.setRegistrantAdminId(61);
        whereForm.setSchoolCategoryId(43);
        whereForm.setSchoolPrefectureId(12);
        whereForm.setSchoolFee("9876");
        whereForm.setSchoolReleasePropriety("1");
        whereForm.setSchoolEntryPropriety("1");
        List<SchoolExtendActionForm> actual = sut.selectMatchedSchool(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedSchoolByRegistrantAdminToGetNoSchoolByMultipleCondition() throws Exception {
        int expected = 0;

        SelectSchoolJoinDAO sut = new SelectSchoolJoinDAO();
        SchoolSelectJoinWhereActionForm whereForm = new SchoolSelectJoinWhereActionForm();
        whereForm.setSchoolName("テストスクール6");
        whereForm.setRegistrantAdminId(61);
        whereForm.setSchoolCategoryId(43);
        whereForm.setSchoolPrefectureId(12);
        whereForm.setSchoolFee("9876");
        whereForm.setSchoolReleasePropriety("1");
        whereForm.setSchoolEntryPropriety("0");
        List<SchoolExtendActionForm> actual = sut.selectMatchedSchool(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedSchoolToGetFiveSchoolByLikeSchoolName() throws Exception {
        int expected = 5;

        SelectSchoolJoinDAO sut = new SelectSchoolJoinDAO();
        SchoolSelectJoinWhereActionForm whereForm = new SchoolSelectJoinWhereActionForm();
        whereForm.setLikeSchoolName("テスト");
        List<SchoolExtendActionForm> actual = sut.selectMatchedSchool(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedSchoolToGetThreeSchoolByRangeOfSchoolFee() throws Exception {
        int expected = 3;

        SelectSchoolJoinDAO sut = new SelectSchoolJoinDAO();
        SchoolSelectJoinWhereActionForm whereForm = new SchoolSelectJoinWhereActionForm();
        whereForm.setMinSchoolFee("1000");
        whereForm.setMaxSchoolFee("4000");
        List<SchoolExtendActionForm> actual = sut.selectMatchedSchool(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedSchoolToGetThreeSchoolByRangeOfSchoolRegisteredDateAndSchoolUpdatedDate()
            throws Exception {
        int expected = 3;

        SelectSchoolJoinDAO sut = new SelectSchoolJoinDAO();
        SchoolSelectJoinWhereActionForm whereForm = new SchoolSelectJoinWhereActionForm();
        whereForm.setFromSchoolRegisteredDate("2022-01-04 00:00:00");
        whereForm.setToSchoolRegisteredDate("2022-01-08 23:59:59");
        whereForm.setFromSchoolUpdatedDate("2022-01-03 00:00:00");
        whereForm.setToSchoolUpdatedDate("2022-01-09 23:59:59");
        List<SchoolExtendActionForm> actual = sut.selectMatchedSchool(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedSchoolToGetTwoSchoolByArrayOfRegistrantAccountIdAndSchoolCategoryIdAndSchoolPrefectureIdAndSchoolCategoryId()
            throws Exception {
        int expected = 2;

        SelectSchoolJoinDAO sut = new SelectSchoolJoinDAO();
        SchoolSelectJoinWhereActionForm whereForm = new SchoolSelectJoinWhereActionForm();
        String[] registrantAccountIdArray = {"22", "23", "24", "25"};
        whereForm.setRegistrantAccountIdArray(registrantAccountIdArray);
        String[] schoolCategoryIdArray = {"41" ,"42", "44"};
        whereForm.setSchoolCategoryIdArray(schoolCategoryIdArray);
        String[] schoolPrefectureIdArray = {"15", "17"};
        whereForm.setSchoolPrefectureIdArray(schoolPrefectureIdArray);
        List<SchoolExtendActionForm> actual = sut.selectMatchedSchool(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedSchoolToGetTwoSchoolByArrayOfRegistrantAdminId()
            throws Exception {
        int expected = 2;

        SelectSchoolJoinDAO sut = new SelectSchoolJoinDAO();
        SchoolSelectJoinWhereActionForm whereForm = new SchoolSelectJoinWhereActionForm();
        String[] registrantAdminIdArray = {"61", "62", "63"};
        whereForm.setRegistrantAdminIdArray(registrantAdminIdArray);
        List<SchoolExtendActionForm> actual = sut.selectMatchedSchool(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedSchoolToGetThreeSchoolByArrayOfReleaseProprietyAndEntryPropriety()
            throws Exception {
        int expected = 3;

        SelectSchoolJoinDAO sut = new SelectSchoolJoinDAO();
        SchoolSelectJoinWhereActionForm whereForm = new SchoolSelectJoinWhereActionForm();
        String[] schoolReleaseProprietyArray = {"0", "1"};
        whereForm.setSchoolReleaseProprietyArray(schoolReleaseProprietyArray);
        String[] schoolEntryProprietyArray = {"0"};
        whereForm.setSchoolEntryProprietyArray(schoolEntryProprietyArray);
        List<SchoolExtendActionForm> actual = sut.selectMatchedSchool(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedSchoolToGetFourSchoolByArrayOfReleaseProprietyAndEntryPropriety()
            throws Exception {
        int expected = 4;

        SelectSchoolJoinDAO sut = new SelectSchoolJoinDAO();
        SchoolSelectJoinWhereActionForm whereForm = new SchoolSelectJoinWhereActionForm();
        String[] schoolReleaseProprietyArray = {"1"};
        whereForm.setSchoolReleaseProprietyArray(schoolReleaseProprietyArray);
        String[] schoolEntryProprietyArray = {"0", "1"};
        whereForm.setSchoolEntryProprietyArray(schoolEntryProprietyArray);
        List<SchoolExtendActionForm> actual = sut.selectMatchedSchool(whereForm);

        assertThat(actual.size(), is(expected));
    }

}
