package model.school;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.SchoolExtendActionForm;
import test.db.DbUnitTester;

public class SchoolListModelTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SchoolListModelTest.xml") {
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
    public void testSetListSchoolDataToSetData() throws Exception {
        List<SchoolExtendActionForm> expected = new ArrayList<>();
        SchoolExtendActionForm expectedForm1 = new SchoolExtendActionForm();
        expectedForm1.setSchoolId(1);
        expectedForm1.setSchoolReleasePropriety("1");
        expectedForm1.setSchoolReleaseProprietyName("可");
        expectedForm1.setSchoolEntryPropriety("1");
        expectedForm1.setSchoolEntryProprietyName("可");
        expectedForm1.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expectedForm1.setStrSchoolRegisteredAt("2022/01/02 03:04:05");
        expectedForm1.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expectedForm1.setStrSchoolUpdatedAt("2022/01/03 04:05:06");
        expectedForm1.setAllEntryNum(3);
        expected.add(expectedForm1);
        SchoolExtendActionForm expectedForm2 = new SchoolExtendActionForm();
        expectedForm2.setSchoolId(2);
        expectedForm2.setSchoolReleasePropriety("0");
        expectedForm2.setSchoolReleaseProprietyName("不可");
        expectedForm2.setSchoolEntryPropriety("0");
        expectedForm2.setSchoolEntryProprietyName("不可");
        expectedForm2.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expectedForm2.setStrSchoolRegisteredAt("2022/01/04 05:06:07");
        expectedForm2.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        expectedForm2.setStrSchoolUpdatedAt("2022/01/05 06:07:08");
        expectedForm2.setAllEntryNum(2);
        expected.add(expectedForm2);
        SchoolExtendActionForm expectedForm3 = new SchoolExtendActionForm();
        expectedForm3.setSchoolId(3);
        expectedForm3.setSchoolReleasePropriety("1");
        expectedForm3.setSchoolReleaseProprietyName("可");
        expectedForm3.setSchoolEntryPropriety("0");
        expectedForm3.setSchoolEntryProprietyName("不可");
        expectedForm3.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        expectedForm3.setStrSchoolRegisteredAt("2022/01/06 07:08:09");
        expectedForm3.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-07 08:09:10.234"));
        expectedForm3.setStrSchoolUpdatedAt("2022/01/07 08:09:10");
        expectedForm3.setAllEntryNum(2);
        expected.add(expectedForm3);
        SchoolExtendActionForm expectedForm4 = new SchoolExtendActionForm();
        expectedForm4.setSchoolId(4);
        expectedForm4.setSchoolReleasePropriety("0");
        expectedForm4.setSchoolReleaseProprietyName("不可");
        expectedForm4.setSchoolEntryPropriety("1");
        expectedForm4.setSchoolEntryProprietyName("可");
        expectedForm4.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-08 09:10:11.345"));
        expectedForm4.setStrSchoolRegisteredAt("2022/01/08 09:10:11");
        expectedForm4.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-09 10:11:12.456"));
        expectedForm4.setStrSchoolUpdatedAt("2022/01/09 10:11:12");
        expectedForm4.setAllEntryNum(0);
        expected.add(expectedForm4);

        SchoolListModel sut = new SchoolListModel();
        List<SchoolExtendActionForm> actual = new ArrayList<>();
        SchoolExtendActionForm actualForm1 = new SchoolExtendActionForm();
        actualForm1.setSchoolId(1);
        actualForm1.setSchoolReleasePropriety("1");
        actualForm1.setSchoolEntryPropriety("1");
        actualForm1.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actualForm1.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        actual.add(actualForm1);
        SchoolExtendActionForm actualForm2 = new SchoolExtendActionForm();
        actualForm2.setSchoolId(2);
        actualForm2.setSchoolReleasePropriety("0");
        actualForm2.setSchoolEntryPropriety("0");
        actualForm2.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actualForm2.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        actual.add(actualForm2);
        SchoolExtendActionForm actualForm3 = new SchoolExtendActionForm();
        actualForm3.setSchoolId(3);
        actualForm3.setSchoolReleasePropriety("1");
        actualForm3.setSchoolEntryPropriety("0");
        actualForm3.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        actualForm3.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-07 08:09:10.234"));
        actual.add(actualForm3);
        SchoolExtendActionForm actualForm4 = new SchoolExtendActionForm();
        actualForm4.setSchoolId(4);
        actualForm4.setSchoolReleasePropriety("0");
        actualForm4.setSchoolEntryPropriety("1");
        actualForm4.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-08 09:10:11.345"));
        actualForm4.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-09 10:11:12.456"));
        actual.add(actualForm4);
        actual = sut.setListSchoolData(actual);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortSchoolExtendFormListToSortByDescendingRegisteredAt() {
        List<SchoolExtendActionForm> expected = new ArrayList<>();
        SchoolExtendActionForm expectedForm1 = new SchoolExtendActionForm();
        expectedForm1.setSchoolId(4);
        expectedForm1.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-08 09:10:11.345"));
        expected.add(expectedForm1);
        SchoolExtendActionForm expectedForm2 = new SchoolExtendActionForm();
        expectedForm2.setSchoolId(1);
        expectedForm2.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        expected.add(expectedForm2);
        SchoolExtendActionForm expectedForm3 = new SchoolExtendActionForm();
        expectedForm3.setSchoolId(3);
        expectedForm3.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(expectedForm3);
        SchoolExtendActionForm expectedForm4 = new SchoolExtendActionForm();
        expectedForm4.setSchoolId(2);
        expectedForm4.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(expectedForm4);

        SchoolListModel sut = new SchoolListModel();
        List<SchoolExtendActionForm> actual = new ArrayList<>();
        SchoolExtendActionForm actualForm1 = new SchoolExtendActionForm();
        actualForm1.setSchoolId(1);
        actualForm1.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        actual.add(actualForm1);
        SchoolExtendActionForm actualForm2 = new SchoolExtendActionForm();
        actualForm2.setSchoolId(2);
        actualForm2.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actual.add(actualForm2);
        SchoolExtendActionForm actualForm3 = new SchoolExtendActionForm();
        actualForm3.setSchoolId(3);
        actualForm3.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actual.add(actualForm3);
        SchoolExtendActionForm actualForm4 = new SchoolExtendActionForm();
        actualForm4.setSchoolId(4);
        actualForm4.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-08 09:10:11.345"));
        actual.add(actualForm4);
        actual = sut.sortSchoolExtendFormList(actual, "byDescendingRegisteredAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortSchoolExtendFormListToSortByAccendingRegisteredAt() {
        List<SchoolExtendActionForm> expected = new ArrayList<>();
        SchoolExtendActionForm expectedForm1 = new SchoolExtendActionForm();
        expectedForm1.setSchoolId(2);
        expectedForm1.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(expectedForm1);
        SchoolExtendActionForm expectedForm2 = new SchoolExtendActionForm();
        expectedForm2.setSchoolId(3);
        expectedForm2.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(expectedForm2);
        SchoolExtendActionForm expectedForm3 = new SchoolExtendActionForm();
        expectedForm3.setSchoolId(1);
        expectedForm3.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        expected.add(expectedForm3);
        SchoolExtendActionForm expectedForm4 = new SchoolExtendActionForm();
        expectedForm4.setSchoolId(4);
        expectedForm4.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-08 09:10:11.345"));
        expected.add(expectedForm4);

        SchoolListModel sut = new SchoolListModel();
        List<SchoolExtendActionForm> actual = new ArrayList<>();
        SchoolExtendActionForm actualForm1 = new SchoolExtendActionForm();
        actualForm1.setSchoolId(1);
        actualForm1.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        actual.add(actualForm1);
        SchoolExtendActionForm actualForm2 = new SchoolExtendActionForm();
        actualForm2.setSchoolId(2);
        actualForm2.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actual.add(actualForm2);
        SchoolExtendActionForm actualForm3 = new SchoolExtendActionForm();
        actualForm3.setSchoolId(3);
        actualForm3.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actual.add(actualForm3);
        SchoolExtendActionForm actualForm4 = new SchoolExtendActionForm();
        actualForm4.setSchoolId(4);
        actualForm4.setSchoolRegisteredAt(Timestamp.valueOf("2022-01-08 09:10:11.345"));
        actual.add(actualForm4);
        actual = sut.sortSchoolExtendFormList(actual, "byAccendingRegisteredAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortSchoolExtendFormListToSortByDescendingUpdatedAt() throws Exception {
        List<SchoolExtendActionForm> expected = new ArrayList<>();
        SchoolExtendActionForm expectedForm1 = new SchoolExtendActionForm();
        expectedForm1.setSchoolId(3);
        expectedForm1.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-09 10:11:12.456"));
        expected.add(expectedForm1);
        SchoolExtendActionForm expectedForm2 = new SchoolExtendActionForm();
        expectedForm2.setSchoolId(1);
        expectedForm2.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-07 08:09:10.234"));
        expected.add(expectedForm2);
        SchoolExtendActionForm expectedForm3 = new SchoolExtendActionForm();
        expectedForm3.setSchoolId(4);
        expectedForm3.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        expected.add(expectedForm3);
        SchoolExtendActionForm expectedForm4 = new SchoolExtendActionForm();
        expectedForm4.setSchoolId(2);
        expectedForm4.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expected.add(expectedForm4);

        SchoolListModel sut = new SchoolListModel();
        List<SchoolExtendActionForm> actual = new ArrayList<>();
        SchoolExtendActionForm actualForm1 = new SchoolExtendActionForm();
        actualForm1.setSchoolId(1);
        actualForm1.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-07 08:09:10.234"));
        actual.add(actualForm1);
        SchoolExtendActionForm actualForm2 = new SchoolExtendActionForm();
        actualForm2.setSchoolId(2);
        actualForm2.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        actual.add(actualForm2);
        SchoolExtendActionForm actualForm3 = new SchoolExtendActionForm();
        actualForm3.setSchoolId(3);
        actualForm3.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-09 10:11:12.456"));
        actual.add(actualForm3);
        SchoolExtendActionForm actualForm4 = new SchoolExtendActionForm();
        actualForm4.setSchoolId(4);
        actualForm4.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        actual.add(actualForm4);
        actual = sut.sortSchoolExtendFormList(actual, "byDescendingUpdatedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortSchoolExtendFormListToSortByAccendingUpdatedAt() throws Exception {
        List<SchoolExtendActionForm> expected = new ArrayList<>();
        SchoolExtendActionForm expectedForm1 = new SchoolExtendActionForm();
        expectedForm1.setSchoolId(2);
        expectedForm1.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expected.add(expectedForm1);
        SchoolExtendActionForm expectedForm2 = new SchoolExtendActionForm();
        expectedForm2.setSchoolId(4);
        expectedForm2.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        expected.add(expectedForm2);
        SchoolExtendActionForm expectedForm3 = new SchoolExtendActionForm();
        expectedForm3.setSchoolId(1);
        expectedForm3.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-07 08:09:10.234"));
        expected.add(expectedForm3);
        SchoolExtendActionForm expectedForm4 = new SchoolExtendActionForm();
        expectedForm4.setSchoolId(3);
        expectedForm4.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-09 10:11:12.456"));
        expected.add(expectedForm4);

        SchoolListModel sut = new SchoolListModel();
        List<SchoolExtendActionForm> actual = new ArrayList<>();
        SchoolExtendActionForm actualForm1 = new SchoolExtendActionForm();
        actualForm1.setSchoolId(1);
        actualForm1.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-07 08:09:10.234"));
        actual.add(actualForm1);
        SchoolExtendActionForm actualForm2 = new SchoolExtendActionForm();
        actualForm2.setSchoolId(2);
        actualForm2.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        actual.add(actualForm2);
        SchoolExtendActionForm actualForm3 = new SchoolExtendActionForm();
        actualForm3.setSchoolId(3);
        actualForm3.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-09 10:11:12.456"));
        actual.add(actualForm3);
        SchoolExtendActionForm actualForm4 = new SchoolExtendActionForm();
        actualForm4.setSchoolId(4);
        actualForm4.setSchoolUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        actual.add(actualForm4);
        actual = sut.sortSchoolExtendFormList(actual, "byAccendingUpdatedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortSchoolExtendFormListToSortByDescendingSchoolFee() throws Exception {
        List<SchoolExtendActionForm> expected = new ArrayList<>();
        SchoolExtendActionForm expectedForm1 = new SchoolExtendActionForm();
        expectedForm1.setSchoolId(3);
        expectedForm1.setSchoolFee(new BigDecimal("4000"));
        expected.add(expectedForm1);
        SchoolExtendActionForm expectedForm2 = new SchoolExtendActionForm();
        expectedForm2.setSchoolId(1);
        expectedForm2.setSchoolFee(new BigDecimal("3000"));
        expected.add(expectedForm2);
        SchoolExtendActionForm expectedForm3 = new SchoolExtendActionForm();
        expectedForm3.setSchoolId(4);
        expectedForm3.setSchoolFee(new BigDecimal("2000"));
        expected.add(expectedForm3);
        SchoolExtendActionForm expectedForm4 = new SchoolExtendActionForm();
        expectedForm4.setSchoolId(2);
        expectedForm4.setSchoolFee(new BigDecimal("1000"));
        expected.add(expectedForm4);

        SchoolListModel sut = new SchoolListModel();
        List<SchoolExtendActionForm> actual = new ArrayList<>();
        SchoolExtendActionForm actualForm1 = new SchoolExtendActionForm();
        actualForm1.setSchoolId(1);
        actualForm1.setSchoolFee(new BigDecimal("3000"));
        actual.add(actualForm1);
        SchoolExtendActionForm actualForm2 = new SchoolExtendActionForm();
        actualForm2.setSchoolId(2);
        actualForm2.setSchoolFee(new BigDecimal("1000"));
        actual.add(actualForm2);
        SchoolExtendActionForm actualForm3 = new SchoolExtendActionForm();
        actualForm3.setSchoolId(3);
        actualForm3.setSchoolFee(new BigDecimal("4000"));
        actual.add(actualForm3);
        SchoolExtendActionForm actualForm4 = new SchoolExtendActionForm();
        actualForm4.setSchoolId(4);
        actualForm4.setSchoolFee(new BigDecimal("2000"));
        actual.add(actualForm4);
        actual = sut.sortSchoolExtendFormList(actual, "byDescendingSchoolFee");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortSchoolExtendFormListToSortByAccendingSchoolFee() throws Exception {
        List<SchoolExtendActionForm> expected = new ArrayList<>();
        SchoolExtendActionForm expectedForm1 = new SchoolExtendActionForm();
        expectedForm1.setSchoolId(2);
        expectedForm1.setSchoolFee(new BigDecimal("1000"));
        expected.add(expectedForm1);
        SchoolExtendActionForm expectedForm2 = new SchoolExtendActionForm();
        expectedForm2.setSchoolId(4);
        expectedForm2.setSchoolFee(new BigDecimal("2000"));
        expected.add(expectedForm2);
        SchoolExtendActionForm expectedForm3 = new SchoolExtendActionForm();
        expectedForm3.setSchoolId(1);
        expectedForm3.setSchoolFee(new BigDecimal("3000"));
        expected.add(expectedForm3);
        SchoolExtendActionForm expectedForm4 = new SchoolExtendActionForm();
        expectedForm4.setSchoolId(3);
        expectedForm4.setSchoolFee(new BigDecimal("4000"));
        expected.add(expectedForm4);

        SchoolListModel sut = new SchoolListModel();
        List<SchoolExtendActionForm> actual = new ArrayList<>();
        SchoolExtendActionForm actualForm1 = new SchoolExtendActionForm();
        actualForm1.setSchoolId(1);
        actualForm1.setSchoolFee(new BigDecimal("3000"));
        actual.add(actualForm1);
        SchoolExtendActionForm actualForm2 = new SchoolExtendActionForm();
        actualForm2.setSchoolId(2);
        actualForm2.setSchoolFee(new BigDecimal("1000"));
        actual.add(actualForm2);
        SchoolExtendActionForm actualForm3 = new SchoolExtendActionForm();
        actualForm3.setSchoolId(3);
        actualForm3.setSchoolFee(new BigDecimal("4000"));
        actual.add(actualForm3);
        SchoolExtendActionForm actualForm4 = new SchoolExtendActionForm();
        actualForm4.setSchoolId(4);
        actualForm4.setSchoolFee(new BigDecimal("2000"));
        actual.add(actualForm4);
        actual = sut.sortSchoolExtendFormList(actual, "byAccendingSchoolFee");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedSchoolListInCaseCurrentPageIsFirst() throws Exception {
        List<SchoolExtendActionForm> expected = new ArrayList<>();
        SchoolExtendActionForm expectedForm1 = new SchoolExtendActionForm();
        expectedForm1.setSchoolId(1);
        expectedForm1.setSummaryImageFileName("image1.png");
        expectedForm1.setSummaryImageFilePath("/img/image1.png");
        expected.add(expectedForm1);
        SchoolExtendActionForm expectedForm2 = new SchoolExtendActionForm();
        expectedForm2.setSchoolId(2);
        expectedForm2.setSummaryImageFileName(null);
        expectedForm2.setSummaryImageFilePath(null);
        expected.add(expectedForm2);
        SchoolExtendActionForm expectedForm3 = new SchoolExtendActionForm();
        expectedForm3.setSchoolId(3);
        expectedForm3.setSummaryImageFileName(null);
        expectedForm3.setSummaryImageFilePath(null);
        expected.add(expectedForm3);
        SchoolExtendActionForm expectedForm4 = new SchoolExtendActionForm();
        expectedForm4.setSchoolId(4);
        expectedForm4.setSummaryImageFileName(null);
        expectedForm4.setSummaryImageFilePath(null);
        expected.add(expectedForm4);
        SchoolExtendActionForm expectedForm5 = new SchoolExtendActionForm();
        expectedForm5.setSchoolId(5);
        expectedForm5.setSummaryImageFileName(null);
        expectedForm5.setSummaryImageFilePath(null);
        expected.add(expectedForm5);
        SchoolExtendActionForm expectedForm6 = new SchoolExtendActionForm();
        expectedForm6.setSchoolId(6);
        expectedForm6.setSummaryImageFileName(null);
        expectedForm6.setSummaryImageFilePath(null);
        expected.add(expectedForm6);
        SchoolExtendActionForm expectedForm7 = new SchoolExtendActionForm();
        expectedForm7.setSchoolId(7);
        expectedForm7.setSummaryImageFileName(null);
        expectedForm7.setSummaryImageFilePath(null);
        expected.add(expectedForm7);
        SchoolExtendActionForm expectedForm8 = new SchoolExtendActionForm();
        expectedForm8.setSchoolId(8);
        expectedForm8.setSummaryImageFileName("image8.png");
        expectedForm8.setSummaryImageFilePath("/img/image8.png");
        expected.add(expectedForm8);
        SchoolExtendActionForm expectedForm9 = new SchoolExtendActionForm();
        expectedForm9.setSchoolId(9);
        expectedForm9.setSummaryImageFileName("image9.png");
        expectedForm9.setSummaryImageFilePath("/img/image9.png");
        expected.add(expectedForm9);
        SchoolExtendActionForm expectedForm10 = new SchoolExtendActionForm();
        expectedForm10.setSchoolId(10);
        expectedForm10.setSummaryImageFileName(null);
        expectedForm10.setSummaryImageFilePath(null);
        expected.add(expectedForm10);

        SchoolListModel sut = new SchoolListModel();
        List<SchoolExtendActionForm> actual = new ArrayList<>();
        SchoolExtendActionForm actualForm1 = new SchoolExtendActionForm();
        actualForm1.setSchoolId(1);
        actualForm1.setSummaryImageFileName("image1.png");
        actual.add(actualForm1);
        SchoolExtendActionForm actualForm2 = new SchoolExtendActionForm();
        actualForm2.setSchoolId(2);
        actualForm2.setSummaryImageFileName(null);
        actual.add(actualForm2);
        SchoolExtendActionForm actualForm3 = new SchoolExtendActionForm();
        actualForm3.setSchoolId(3);
        actualForm3.setSummaryImageFileName(null);
        actual.add(actualForm3);
        SchoolExtendActionForm actualForm4 = new SchoolExtendActionForm();
        actualForm4.setSchoolId(4);
        actualForm4.setSummaryImageFileName(null);
        actual.add(actualForm4);
        SchoolExtendActionForm actualForm5 = new SchoolExtendActionForm();
        actualForm5.setSchoolId(5);
        actualForm5.setSummaryImageFileName(null);
        actual.add(actualForm5);
        SchoolExtendActionForm actualForm6 = new SchoolExtendActionForm();
        actualForm6.setSchoolId(6);
        actualForm6.setSummaryImageFileName(null);
        actual.add(actualForm6);
        SchoolExtendActionForm actualForm7 = new SchoolExtendActionForm();
        actualForm7.setSchoolId(7);
        actualForm7.setSummaryImageFileName(null);
        actual.add(actualForm7);
        SchoolExtendActionForm actualForm8 = new SchoolExtendActionForm();
        actualForm8.setSchoolId(8);
        actualForm8.setSummaryImageFileName("image8.png");
        actual.add(actualForm8);
        SchoolExtendActionForm actualForm9 = new SchoolExtendActionForm();
        actualForm9.setSchoolId(9);
        actualForm9.setSummaryImageFileName("image9.png");
        actual.add(actualForm9);
        SchoolExtendActionForm actualForm10 = new SchoolExtendActionForm();
        actualForm10.setSchoolId(10);
        actualForm10.setSummaryImageFileName(null);
        actual.add(actualForm10);
        SchoolExtendActionForm actualForm11 = new SchoolExtendActionForm();
        actualForm11.setSchoolId(11);
        actualForm11.setSummaryImageFileName("image11.png");
        actual.add(actualForm11);
        actual = sut.makeDisplayedSchoolList(actual, 1, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedSchoolListInCaseCurrentPageIsSecond() throws Exception {
        List<SchoolExtendActionForm> expected = new ArrayList<>();
        SchoolExtendActionForm expectedForm11 = new SchoolExtendActionForm();
        expectedForm11.setSchoolId(11);
        expectedForm11.setSummaryImageFileName("image11.png");
        expectedForm11.setSummaryImageFilePath("/img/image11.png");
        expected.add(expectedForm11);
        SchoolExtendActionForm expectedForm12 = new SchoolExtendActionForm();
        expectedForm12.setSchoolId(12);
        expectedForm12.setSummaryImageFileName(null);
        expectedForm12.setSummaryImageFilePath(null);
        expected.add(expectedForm12);
        SchoolExtendActionForm expectedForm13 = new SchoolExtendActionForm();
        expectedForm13.setSchoolId(13);
        expectedForm13.setSummaryImageFileName("image13.png");
        expectedForm13.setSummaryImageFilePath("/img/image13.png");
        expected.add(expectedForm13);

        SchoolListModel sut = new SchoolListModel();
        List<SchoolExtendActionForm> actual = new ArrayList<>();
        SchoolExtendActionForm actualForm1 = new SchoolExtendActionForm();
        actualForm1.setSchoolId(1);
        actualForm1.setSummaryImageFileName("image1.png");
        actual.add(actualForm1);
        SchoolExtendActionForm actualForm2 = new SchoolExtendActionForm();
        actualForm2.setSchoolId(2);
        actualForm2.setSummaryImageFileName(null);
        actual.add(actualForm2);
        SchoolExtendActionForm actualForm3 = new SchoolExtendActionForm();
        actualForm3.setSchoolId(3);
        actualForm3.setSummaryImageFileName(null);
        actual.add(actualForm3);
        SchoolExtendActionForm actualForm4 = new SchoolExtendActionForm();
        actualForm4.setSchoolId(4);
        actualForm4.setSummaryImageFileName(null);
        actual.add(actualForm4);
        SchoolExtendActionForm actualForm5 = new SchoolExtendActionForm();
        actualForm5.setSchoolId(5);
        actualForm5.setSummaryImageFileName(null);
        actual.add(actualForm5);
        SchoolExtendActionForm actualForm6 = new SchoolExtendActionForm();
        actualForm6.setSchoolId(6);
        actualForm6.setSummaryImageFileName(null);
        actual.add(actualForm6);
        SchoolExtendActionForm actualForm7 = new SchoolExtendActionForm();
        actualForm7.setSchoolId(7);
        actualForm7.setSummaryImageFileName(null);
        actual.add(actualForm7);
        SchoolExtendActionForm actualForm8 = new SchoolExtendActionForm();
        actualForm8.setSchoolId(8);
        actualForm8.setSummaryImageFileName("image8.png");
        actual.add(actualForm8);
        SchoolExtendActionForm actualForm9 = new SchoolExtendActionForm();
        actualForm9.setSchoolId(9);
        actualForm9.setSummaryImageFileName("image9.png");
        actual.add(actualForm9);
        SchoolExtendActionForm actualForm10 = new SchoolExtendActionForm();
        actualForm10.setSchoolId(10);
        actualForm10.setSummaryImageFileName(null);
        actual.add(actualForm10);
        SchoolExtendActionForm actualForm11 = new SchoolExtendActionForm();
        actualForm11.setSchoolId(11);
        actualForm11.setSummaryImageFileName("image11.png");
        actual.add(actualForm11);
        SchoolExtendActionForm actualForm12 = new SchoolExtendActionForm();
        actualForm12.setSchoolId(12);
        actualForm12.setSummaryImageFileName(null);
        actual.add(actualForm12);
        SchoolExtendActionForm actualForm13 = new SchoolExtendActionForm();
        actualForm13.setSchoolId(13);
        actualForm13.setSummaryImageFileName("image13.png");
        actual.add(actualForm13);
        actual = sut.makeDisplayedSchoolList(actual, 2, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedSchoolListInCaseCurrentPageIsSecondAndEmpty() throws Exception {
        List<SchoolExtendActionForm> expected = new ArrayList<>();

        SchoolListModel sut = new SchoolListModel();
        List<SchoolExtendActionForm> actual = new ArrayList<>();
        SchoolExtendActionForm actualForm1 = new SchoolExtendActionForm();
        actualForm1.setSchoolId(1);
        actualForm1.setSummaryImageFileName("image1.png");
        actual.add(actualForm1);
        SchoolExtendActionForm actualForm2 = new SchoolExtendActionForm();
        actualForm2.setSchoolId(2);
        actualForm2.setSummaryImageFileName("image2.png");
        actual.add(actualForm2);
        SchoolExtendActionForm actualForm3 = new SchoolExtendActionForm();
        actualForm3.setSchoolId(3);
        actualForm3.setSummaryImageFileName(null);
        actual.add(actualForm3);
        actual = sut.makeDisplayedSchoolList(actual, 2, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

}
