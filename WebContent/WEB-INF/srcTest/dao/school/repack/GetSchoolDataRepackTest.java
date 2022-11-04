package dao.school.repack;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.SchoolExtendActionForm;
import exception.NoDataExistException;
import test.db.DbUnitTester;

public class GetSchoolDataRepackTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.GetSchoolDataRepackTest.xml") {
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
    public void testGetSchoolDataInCaseOneMatchedSchoolExistWithoutNull() throws Exception {
        SchoolExtendActionForm expected = new SchoolExtendActionForm();
        expected.setSchoolId(1);
        expected.setRegistrantAccountId(22);
        expected.setRegistrantLastName("佐藤");
        expected.setRegistrantFirstName("次郎");
        expected.setRegistrantLastNameKana("サトウ");
        expected.setRegistrantFirstNameKana("ジロウ");
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
        expected.setStrSchoolFee("5432");
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
        expected.setStrSchoolRegisteredAt("2022/01/02 03:04:05");
        expected.setStrSchoolUpdatedAt("2022/01/03 04:05:06");
        expected.setSummaryImageFilePath("/Application/struts-school-search/img/summary_image11.png");
        expected.setDetailImage1FilePath("/Application/struts-school-search/img/detail_image11.png");
        expected.setDetailImage2FilePath("/Application/struts-school-search/img/detail_image12.png");
        expected.setDetailImage3FilePath("/Application/struts-school-search/img/detail_image13.png");
        expected.setDetailImage4FilePath("/Application/struts-school-search/img/detail_image14.png");
        expected.setDetailImage5FilePath("/Application/struts-school-search/img/detail_image15.png");
        expected.setDetailImage6FilePath("/Application/struts-school-search/img/detail_image16.png");
        expected.setDetailImage7FilePath("/Application/struts-school-search/img/detail_image17.png");
        expected.setDetailImage8FilePath("/Application/struts-school-search/img/detail_image18.png");

        GetSchoolDataRepack sut = new GetSchoolDataRepack() {
            String getImagePath() throws IOException {
                return "/Application/struts-school-search/img/";
            }
        };
        SchoolExtendActionForm actual = new SchoolExtendActionForm();
        actual = sut.getSchoolData(1, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testGetSchoolDataInCaseOneMatchedSchoolExistWithNull() throws Exception {
        SchoolExtendActionForm expected = new SchoolExtendActionForm();
        expected.setSchoolId(5);
        expected.setRegistrantAccountId(21);
        expected.setRegistrantLastName("田中");
        expected.setRegistrantFirstName("太郎");
        expected.setRegistrantLastNameKana("タナカ");
        expected.setRegistrantFirstNameKana("タロウ");
        expected.setRegistrantAdminId(0);
        expected.setSchoolName("テストスクール5");
        expected.setSchoolCategoryId(42);
        expected.setSchoolCategoryName("運動");
        expected.setSchoolSummary("スクール概要5");
        expected.setSchoolDescription("スクール詳細5");
        expected.setSchoolZipCode1("012");
        expected.setSchoolZipCode2("3456");
        expected.setSchoolPrefectureId(11);
        expected.setSchoolPrefectureName("北海道");
        expected.setSchoolCity("北海道市");
        expected.setSchoolAddress1("北海道区");
        expected.setSchoolAddress2(null);
        expected.setSchoolFee(new BigDecimal(1098));
        expected.setStrSchoolFee("1098");
        expected.setSupplementaryFee(null);
        expected.setSchoolUrl(null);
        expected.setSchoolReleasePropriety("0");
        expected.setSchoolEntryPropriety("0");
        expected.setSummaryImageFileName(null);
        expected.setDetailImage1FileName(null);
        expected.setDetailImage2FileName(null);
        expected.setDetailImage3FileName(null);
        expected.setDetailImage4FileName(null);
        expected.setDetailImage5FileName(null);
        expected.setDetailImage6FileName(null);
        expected.setDetailImage7FileName(null);
        expected.setDetailImage8FileName(null);
        expected.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-10 11:12:13.456"));
        expected.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-11 12:13:14.567"));
        expected.setStrSchoolRegisteredAt("2022/01/10 11:12:13");
        expected.setStrSchoolUpdatedAt("2022/01/11 12:13:14");
        expected.setSummaryImageFilePath("");
        expected.setDetailImage1FilePath("");
        expected.setDetailImage2FilePath("");
        expected.setDetailImage3FilePath("");
        expected.setDetailImage4FilePath("");
        expected.setDetailImage5FilePath("");
        expected.setDetailImage6FilePath("");
        expected.setDetailImage7FilePath("");
        expected.setDetailImage8FilePath("");

        GetSchoolDataRepack sut = new GetSchoolDataRepack() {
            String getImagePath() throws IOException {
                return "/Application/struts-school-search/img/";
            }
        };
        SchoolExtendActionForm actual = new SchoolExtendActionForm();
        actual = sut.getSchoolData(5, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test(expected = NoDataExistException.class)
    public void testGetSchoolDataInCaseNoMatchedSchoolExistWithNull() throws Exception {
        GetSchoolDataRepack sut = new GetSchoolDataRepack();
        SchoolExtendActionForm actual = new SchoolExtendActionForm();
        actual = sut.getSchoolData(7, actual);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void testGetRegistrantProfileInCaseOneMatchedSchoolExistWithoutNull() throws Exception {
        SchoolExtendActionForm expected = new SchoolExtendActionForm();
        expected.setSchoolId(1);
        expected.setRegistrantLastName("佐藤");
        expected.setRegistrantFirstName("次郎");
        expected.setRegistrantLastNameKana("サトウ");
        expected.setRegistrantFirstNameKana("ジロウ");
        expected.setSelfIntroduction("自己紹介2");
        expected.setSchoolName("テストスクール1");
        expected.setProfileImageFilePath("/Application/struts-school-search/img/profile_image2.png");

        GetSchoolDataRepack sut = new GetSchoolDataRepack() {
            String getImagePath() throws IOException {
                return "/Application/struts-school-search/img/";
            }
        };
        SchoolExtendActionForm actual = new SchoolExtendActionForm();
        actual = sut.getRegistrantProfile(1, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testGetRegistrantProfileInCaseOneMatchedSchoolExistWithNull() throws Exception {
        SchoolExtendActionForm expected = new SchoolExtendActionForm();
        expected.setSchoolId(5);
        expected.setRegistrantLastName("田中");
        expected.setRegistrantFirstName("太郎");
        expected.setRegistrantLastNameKana("タナカ");
        expected.setRegistrantFirstNameKana("タロウ");
        expected.setSelfIntroduction(null);
        expected.setSchoolName("テストスクール5");
        expected.setProfileImageFilePath("");

        GetSchoolDataRepack sut = new GetSchoolDataRepack();
        SchoolExtendActionForm actual = new SchoolExtendActionForm();
        actual = sut.getRegistrantProfile(5, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testGetRegistrantProfileInCaseOneMatchedSchoolExistByAdmin() throws Exception {
        GetSchoolDataRepack sut = new GetSchoolDataRepack();
        SchoolExtendActionForm actual = new SchoolExtendActionForm();
        actual = sut.getRegistrantProfile(6, actual);

        assertThat(actual, is(nullValue()));
    }

    @Test(expected = NoDataExistException.class)
    public void testGetRegistrantProfileInCaseNoMatchedSchoolExist() throws Exception {
        GetSchoolDataRepack sut = new GetSchoolDataRepack();
        SchoolExtendActionForm actual = new SchoolExtendActionForm();
        actual = sut.getRegistrantProfile(8, actual);

        assertThat(actual, is(nullValue()));
    }

}
