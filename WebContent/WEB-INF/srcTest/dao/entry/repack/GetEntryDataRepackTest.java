package dao.entry.repack;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.EntryExtendActionForm;
import exception.NoDataExistException;
import test.db.DbUnitTester;

public class GetEntryDataRepackTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.GetEntryDataRepackTest.xml") {
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
    public void testGetEntryDataInCaseOneMatchedEntryExist() throws Exception {
        EntryExtendActionForm expected = new EntryExtendActionForm();
        expected.setEntryId(6);
        expected.setEntrySchoolId(9);
        expected.setEntrySchoolName("テストスクール1");
        expected.setApplicantAccountId(3);
        expected.setEntryQuestion("申込時の質問1");
        expected.setEntryStatusId(1);
        expected.setEntryStatusName("申込済");
        expected.setEntriedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.setEntryUpdatedAt(Timestamp.valueOf("2022-02-03 04:05:06.789"));
        expected.setStrEntriedAt("2022/01/02 03:04:05");
        expected.setStrEntryUpdatedAt("2022/02/03 04:05:06");

        GetEntryDataRepack sut = new GetEntryDataRepack();
        EntryExtendActionForm actual = new EntryExtendActionForm();
        actual = sut.getEntryData(6, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test(expected = NoDataExistException.class)
    public void testGetEntryDataInCaseNoMatchedEntryExist() throws Exception {
        GetEntryDataRepack sut = new GetEntryDataRepack();
        EntryExtendActionForm actual = new EntryExtendActionForm();
        sut.getEntryData(1, actual);
    }

    @Test
    public void testGetEntrySchoolDataByRegistrantAccountInCaseOneMatchedEntryExist() throws Exception {
        EntryExtendActionForm expected = new EntryExtendActionForm();
        expected.setEntrySchoolId(10);
        expected.setApplicantAccountId(4);
        expected.setEntrySchoolName("テストスクール2");
        expected.setSchoolCategoryName("文化");
        expected.setSchoolFee(new BigDecimal("2000"));
        expected.setRegistrantAccountId(5);
        expected.setRegistrantLastName("佐藤");
        expected.setRegistrantFirstName("三郎");
        expected.setRegistrantLastNameKana("サトウ");
        expected.setRegistrantFirstNameKana("サブロウ");
        expected.setRegistrantAdminId(0);

        GetEntryDataRepack sut = new GetEntryDataRepack();
        EntryExtendActionForm actual = new EntryExtendActionForm();
        actual = sut.getEntrySchoolData(10, 4, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testGetEntrySchoolDataByRegistrantAdminInCaseOneMatchedEntryExist() throws Exception {
        EntryExtendActionForm expected = new EntryExtendActionForm();
        expected.setEntrySchoolId(12);
        expected.setApplicantAccountId(5);
        expected.setEntrySchoolName("テストスクール3");
        expected.setSchoolCategoryName("文化");
        expected.setRegistrantAccountId(0);
        expected.setSchoolFee(new BigDecimal("3000"));
        expected.setRegistrantLastName(null);
        expected.setRegistrantFirstName(null);
        expected.setRegistrantLastNameKana(null);
        expected.setRegistrantFirstNameKana(null);
        expected.setRegistrantAdminId(2);

        GetEntryDataRepack sut = new GetEntryDataRepack();
        EntryExtendActionForm actual = new EntryExtendActionForm();
        actual = sut.getEntrySchoolData(12, 5, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test(expected = NoDataExistException.class)
    public void testGetEntrySchoolDataInCaseNoMatchedEntryExist() throws Exception {
        GetEntryDataRepack sut = new GetEntryDataRepack();
        EntryExtendActionForm actual = new EntryExtendActionForm();
        sut.getEntrySchoolData(15, 5, actual);
    }

    @Test
    public void testGetApplicantAccountDataInCaseOneMatchedEntryExist() throws Exception {
        EntryExtendActionForm expected = new EntryExtendActionForm();
        expected.setApplicantAccountId(5);
        expected.setApplicantLastName("佐藤");
        expected.setApplicantFirstName("三郎");
        expected.setApplicantLastNameKana("サトウ");
        expected.setApplicantFirstNameKana("サブロウ");

        GetEntryDataRepack sut = new GetEntryDataRepack();
        EntryExtendActionForm actual = new EntryExtendActionForm();
        actual = sut.getApplicantAccountData(5, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testGetApplicantAccountDataInCaseTwoMatchedEntryExist() throws Exception {
        EntryExtendActionForm expected = new EntryExtendActionForm();
        expected.setApplicantAccountId(4);
        expected.setApplicantLastName("山田");
        expected.setApplicantFirstName("次郎");
        expected.setApplicantLastNameKana("ヤマダ");
        expected.setApplicantFirstNameKana("ジロウ");

        GetEntryDataRepack sut = new GetEntryDataRepack();
        EntryExtendActionForm actual = new EntryExtendActionForm();
        actual = sut.getApplicantAccountData(4, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test(expected = NoDataExistException.class)
    public void testGetApplicantAccountDataInCaseNoMatchedEntryExist() throws Exception {
        GetEntryDataRepack sut = new GetEntryDataRepack();
        EntryExtendActionForm actual = new EntryExtendActionForm();
        sut.getApplicantAccountData(11, actual);
    }

}
