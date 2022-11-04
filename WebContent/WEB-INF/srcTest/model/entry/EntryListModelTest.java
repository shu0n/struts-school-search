package model.entry;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import actionform.EntryExtendActionForm;

public class EntryListModelTest {

    @Test
    public void testSetListEntryDataToSetData() throws Exception {
        List<EntryExtendActionForm> expected = new ArrayList<>();
        EntryExtendActionForm expectedForm1 = new EntryExtendActionForm();
        expectedForm1.setEntryId(1);
        expectedForm1.setEntriedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expectedForm1.setEntryUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expectedForm1.setStrEntriedAt("2022/01/02 03:04:05");
        expectedForm1.setStrEntryUpdatedAt("2022/01/03 04:05:06");
        expected.add(expectedForm1);
        EntryExtendActionForm expectedForm2 = new EntryExtendActionForm();
        expectedForm2.setEntryId(2);
        expectedForm2.setEntriedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expectedForm2.setEntryUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        expectedForm2.setStrEntriedAt("2022/01/04 05:06:07");
        expectedForm2.setStrEntryUpdatedAt("2022/01/05 06:07:08");
        expected.add(expectedForm2);
        EntryExtendActionForm expectedForm3 = new EntryExtendActionForm();
        expectedForm3.setEntryId(3);
        expectedForm3.setEntriedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        expectedForm3.setEntryUpdatedAt(Timestamp.valueOf("2022-01-07 08:09:10.213"));
        expectedForm3.setStrEntriedAt("2022/01/06 07:08:09");
        expectedForm3.setStrEntryUpdatedAt("2022/01/07 08:09:10");
        expected.add(expectedForm3);

        EntryListModel sut = new EntryListModel();
        List<EntryExtendActionForm> actual = new ArrayList<>();
        EntryExtendActionForm actualForm1 = new EntryExtendActionForm();
        actualForm1.setEntryId(1);
        actualForm1.setEntriedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actualForm1.setEntryUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        actual.add(actualForm1);
        EntryExtendActionForm actualForm2 = new EntryExtendActionForm();
        actualForm2.setEntryId(2);
        actualForm2.setEntriedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actualForm2.setEntryUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        actual.add(actualForm2);
        EntryExtendActionForm actualForm3 = new EntryExtendActionForm();
        actualForm3.setEntryId(3);
        actualForm3.setEntriedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        actualForm3.setEntryUpdatedAt(Timestamp.valueOf("2022-01-07 08:09:10.213"));
        actual.add(actualForm3);
        actual = sut.setListEntryData(actual);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortEntryExtendFormListToSortByDescendingEntriedAt() {
        List<EntryExtendActionForm> expected = new ArrayList<>();
        EntryExtendActionForm expectedForm1 = new EntryExtendActionForm();
        expectedForm1.setEntryId(3);
        expectedForm1.setEntriedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        expected.add(expectedForm1);
        EntryExtendActionForm expectedForm2 = new EntryExtendActionForm();
        expectedForm2.setEntryId(1);
        expectedForm2.setEntriedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(expectedForm2);
        EntryExtendActionForm expectedForm3 = new EntryExtendActionForm();
        expectedForm3.setEntryId(4);
        expectedForm3.setEntriedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expected.add(expectedForm3);
        EntryExtendActionForm expectedForm4 = new EntryExtendActionForm();
        expectedForm4.setEntryId(2);
        expectedForm4.setEntriedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(expectedForm4);

        EntryListModel sut = new EntryListModel();
        List<EntryExtendActionForm> actual = new ArrayList<>();
        EntryExtendActionForm actualForm1 = new EntryExtendActionForm();
        actualForm1.setEntryId(1);
        actualForm1.setEntriedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actual.add(actualForm1);
        EntryExtendActionForm actualForm2 = new EntryExtendActionForm();
        actualForm2.setEntryId(2);
        actualForm2.setEntriedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actual.add(actualForm2);
        EntryExtendActionForm actualForm3 = new EntryExtendActionForm();
        actualForm3.setEntryId(3);
        actualForm3.setEntriedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        actual.add(actualForm3);
        EntryExtendActionForm actualForm4 = new EntryExtendActionForm();
        actualForm4.setEntryId(4);
        actualForm4.setEntriedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        actual.add(actualForm4);
        actual = sut.sortEntryExtendFormList(actual, "byDescendingEntriedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortEntryExtendFormListToSortByAccendingEntriedAt() {
        List<EntryExtendActionForm> expected = new ArrayList<>();
        EntryExtendActionForm expectedForm1 = new EntryExtendActionForm();
        expectedForm1.setEntryId(2);
        expectedForm1.setEntriedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(expectedForm1);
        EntryExtendActionForm expectedForm2 = new EntryExtendActionForm();
        expectedForm2.setEntryId(4);
        expectedForm2.setEntriedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expected.add(expectedForm2);
        EntryExtendActionForm expectedForm3 = new EntryExtendActionForm();
        expectedForm3.setEntryId(1);
        expectedForm3.setEntriedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(expectedForm3);
        EntryExtendActionForm expectedForm4 = new EntryExtendActionForm();
        expectedForm4.setEntryId(3);
        expectedForm4.setEntriedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        expected.add(expectedForm4);

        EntryListModel sut = new EntryListModel();
        List<EntryExtendActionForm> actual = new ArrayList<>();
        EntryExtendActionForm actualForm1 = new EntryExtendActionForm();
        actualForm1.setEntryId(1);
        actualForm1.setEntriedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actual.add(actualForm1);
        EntryExtendActionForm actualForm2 = new EntryExtendActionForm();
        actualForm2.setEntryId(2);
        actualForm2.setEntriedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actual.add(actualForm2);
        EntryExtendActionForm actualForm3 = new EntryExtendActionForm();
        actualForm3.setEntryId(3);
        actualForm3.setEntriedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        actual.add(actualForm3);
        EntryExtendActionForm actualForm4 = new EntryExtendActionForm();
        actualForm4.setEntryId(4);
        actualForm4.setEntriedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        actual.add(actualForm4);
        actual = sut.sortEntryExtendFormList(actual, "byAccendingEntriedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortEntryExtendFormListToSortByDescendingEntryUpdatedAt() {
        List<EntryExtendActionForm> expected = new ArrayList<>();
        EntryExtendActionForm expectedForm1 = new EntryExtendActionForm();
        expectedForm1.setEntryId(3);
        expectedForm1.setEntryUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        expected.add(expectedForm1);
        EntryExtendActionForm expectedForm2 = new EntryExtendActionForm();
        expectedForm2.setEntryId(1);
        expectedForm2.setEntryUpdatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(expectedForm2);
        EntryExtendActionForm expectedForm3 = new EntryExtendActionForm();
        expectedForm3.setEntryId(4);
        expectedForm3.setEntryUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expected.add(expectedForm3);
        EntryExtendActionForm expectedForm4 = new EntryExtendActionForm();
        expectedForm4.setEntryId(2);
        expectedForm4.setEntryUpdatedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(expectedForm4);

        EntryListModel sut = new EntryListModel();
        List<EntryExtendActionForm> actual = new ArrayList<>();
        EntryExtendActionForm actualForm1 = new EntryExtendActionForm();
        actualForm1.setEntryId(1);
        actualForm1.setEntryUpdatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actual.add(actualForm1);
        EntryExtendActionForm actualForm2 = new EntryExtendActionForm();
        actualForm2.setEntryId(2);
        actualForm2.setEntryUpdatedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actual.add(actualForm2);
        EntryExtendActionForm actualForm3 = new EntryExtendActionForm();
        actualForm3.setEntryId(3);
        actualForm3.setEntryUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        actual.add(actualForm3);
        EntryExtendActionForm actualForm4 = new EntryExtendActionForm();
        actualForm4.setEntryId(4);
        actualForm4.setEntryUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        actual.add(actualForm4);
        actual = sut.sortEntryExtendFormList(actual, "byDescendingUpdatedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortEntryExtendFormListToSortByAccendingEntryUpdatedAt() {
        List<EntryExtendActionForm> expected = new ArrayList<>();
        EntryExtendActionForm expectedForm1 = new EntryExtendActionForm();
        expectedForm1.setEntryId(2);
        expectedForm1.setEntryUpdatedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(expectedForm1);
        EntryExtendActionForm expectedForm2 = new EntryExtendActionForm();
        expectedForm2.setEntryId(4);
        expectedForm2.setEntryUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expected.add(expectedForm2);
        EntryExtendActionForm expectedForm3 = new EntryExtendActionForm();
        expectedForm3.setEntryId(1);
        expectedForm3.setEntryUpdatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(expectedForm3);
        EntryExtendActionForm expectedForm4 = new EntryExtendActionForm();
        expectedForm4.setEntryId(3);
        expectedForm4.setEntryUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        expected.add(expectedForm4);

        EntryListModel sut = new EntryListModel();
        List<EntryExtendActionForm> actual = new ArrayList<>();
        EntryExtendActionForm actualForm1 = new EntryExtendActionForm();
        actualForm1.setEntryId(1);
        actualForm1.setEntryUpdatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actual.add(actualForm1);
        EntryExtendActionForm actualForm2 = new EntryExtendActionForm();
        actualForm2.setEntryId(2);
        actualForm2.setEntryUpdatedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actual.add(actualForm2);
        EntryExtendActionForm actualForm3 = new EntryExtendActionForm();
        actualForm3.setEntryId(3);
        actualForm3.setEntryUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        actual.add(actualForm3);
        EntryExtendActionForm actualForm4 = new EntryExtendActionForm();
        actualForm4.setEntryId(4);
        actualForm4.setEntryUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        actual.add(actualForm4);
        actual = sut.sortEntryExtendFormList(actual, "byAccendingUpdatedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedEntryListInCaseCurrentPageIsFirst() {
        List<EntryExtendActionForm> expected = new ArrayList<>();
        EntryExtendActionForm expectedForm1 = new EntryExtendActionForm();
        expectedForm1.setEntryId(1);
        expected.add(expectedForm1);
        EntryExtendActionForm expectedForm2 = new EntryExtendActionForm();
        expectedForm2.setEntryId(2);
        expected.add(expectedForm2);
        EntryExtendActionForm expectedForm3 = new EntryExtendActionForm();
        expectedForm3.setEntryId(3);
        expected.add(expectedForm3);
        EntryExtendActionForm expectedForm4 = new EntryExtendActionForm();
        expectedForm4.setEntryId(4);
        expected.add(expectedForm4);
        EntryExtendActionForm expectedForm5 = new EntryExtendActionForm();
        expectedForm5.setEntryId(5);
        expected.add(expectedForm5);
        EntryExtendActionForm expectedForm6 = new EntryExtendActionForm();
        expectedForm6.setEntryId(6);
        expected.add(expectedForm6);
        EntryExtendActionForm expectedForm7 = new EntryExtendActionForm();
        expectedForm7.setEntryId(7);
        expected.add(expectedForm7);
        EntryExtendActionForm expectedForm8 = new EntryExtendActionForm();
        expectedForm8.setEntryId(8);
        expected.add(expectedForm8);
        EntryExtendActionForm expectedForm9 = new EntryExtendActionForm();
        expectedForm9.setEntryId(9);
        expected.add(expectedForm9);
        EntryExtendActionForm expectedForm10 = new EntryExtendActionForm();
        expectedForm10.setEntryId(10);
        expected.add(expectedForm10);

        EntryListModel sut = new EntryListModel();
        List<EntryExtendActionForm> actual = new ArrayList<>();
        EntryExtendActionForm actualForm1 = new EntryExtendActionForm();
        actualForm1.setEntryId(1);
        actual.add(actualForm1);
        EntryExtendActionForm actualForm2 = new EntryExtendActionForm();
        actualForm2.setEntryId(2);
        actual.add(actualForm2);
        EntryExtendActionForm actualForm3 = new EntryExtendActionForm();
        actualForm3.setEntryId(3);
        actual.add(actualForm3);
        EntryExtendActionForm actualForm4 = new EntryExtendActionForm();
        actualForm4.setEntryId(4);
        actual.add(actualForm4);
        EntryExtendActionForm actualForm5 = new EntryExtendActionForm();
        actualForm5.setEntryId(5);
        actual.add(actualForm5);
        EntryExtendActionForm actualForm6 = new EntryExtendActionForm();
        actualForm6.setEntryId(6);
        actual.add(actualForm6);
        EntryExtendActionForm actualForm7 = new EntryExtendActionForm();
        actualForm7.setEntryId(7);
        actual.add(actualForm7);
        EntryExtendActionForm actualForm8 = new EntryExtendActionForm();
        actualForm8.setEntryId(8);
        actual.add(actualForm8);
        EntryExtendActionForm actualForm9 = new EntryExtendActionForm();
        actualForm9.setEntryId(9);
        actual.add(actualForm9);
        EntryExtendActionForm actualForm10 = new EntryExtendActionForm();
        actualForm10.setEntryId(10);
        actual.add(actualForm10);
        EntryExtendActionForm actualForm11 = new EntryExtendActionForm();
        actualForm11.setEntryId(11);
        actual.add(actualForm11);
        actual = sut.makeDisplayedEntryList(actual, 1, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedEntryListInCaseCurrentPageIsSecond() {
        List<EntryExtendActionForm> expected = new ArrayList<>();
        EntryExtendActionForm expectedForm11 = new EntryExtendActionForm();
        expectedForm11.setEntryId(11);
        expected.add(expectedForm11);
        EntryExtendActionForm expectedForm12 = new EntryExtendActionForm();
        expectedForm12.setEntryId(12);
        expected.add(expectedForm12);
        EntryExtendActionForm expectedForm13 = new EntryExtendActionForm();
        expectedForm13.setEntryId(13);
        expected.add(expectedForm13);

        EntryListModel sut = new EntryListModel();
        List<EntryExtendActionForm> actual = new ArrayList<>();
        EntryExtendActionForm actualForm1 = new EntryExtendActionForm();
        actualForm1.setEntryId(1);
        actual.add(actualForm1);
        EntryExtendActionForm actualForm2 = new EntryExtendActionForm();
        actualForm2.setEntryId(2);
        actual.add(actualForm2);
        EntryExtendActionForm actualForm3 = new EntryExtendActionForm();
        actualForm3.setEntryId(3);
        actual.add(actualForm3);
        EntryExtendActionForm actualForm4 = new EntryExtendActionForm();
        actualForm4.setEntryId(4);
        actual.add(actualForm4);
        EntryExtendActionForm actualForm5 = new EntryExtendActionForm();
        actualForm5.setEntryId(5);
        actual.add(actualForm5);
        EntryExtendActionForm actualForm6 = new EntryExtendActionForm();
        actualForm6.setEntryId(6);
        actual.add(actualForm6);
        EntryExtendActionForm actualForm7 = new EntryExtendActionForm();
        actualForm7.setEntryId(7);
        actual.add(actualForm7);
        EntryExtendActionForm actualForm8 = new EntryExtendActionForm();
        actualForm8.setEntryId(8);
        actual.add(actualForm8);
        EntryExtendActionForm actualForm9 = new EntryExtendActionForm();
        actualForm9.setEntryId(9);
        actual.add(actualForm9);
        EntryExtendActionForm actualForm10 = new EntryExtendActionForm();
        actualForm10.setEntryId(10);
        actual.add(actualForm10);
        EntryExtendActionForm actualForm11 = new EntryExtendActionForm();
        actualForm11.setEntryId(11);
        actual.add(actualForm11);
        EntryExtendActionForm actualForm12 = new EntryExtendActionForm();
        actualForm12.setEntryId(12);
        actual.add(actualForm12);
        EntryExtendActionForm actualForm13 = new EntryExtendActionForm();
        actualForm13.setEntryId(13);
        actual.add(actualForm13);
        actual = sut.makeDisplayedEntryList(actual, 2, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedEntryListInCaseCurrentPageIsSecondAndEmpty() {
        List<EntryExtendActionForm> expected = new ArrayList<>();

        EntryListModel sut = new EntryListModel();
        List<EntryExtendActionForm> actual = new ArrayList<>();
        EntryExtendActionForm actualForm1 = new EntryExtendActionForm();
        actualForm1.setEntryId(1);
        actual.add(actualForm1);
        EntryExtendActionForm actualForm2 = new EntryExtendActionForm();
        actualForm2.setEntryId(2);
        actual.add(actualForm2);
        EntryExtendActionForm actualForm3 = new EntryExtendActionForm();
        actualForm3.setEntryId(3);
        actual.add(actualForm3);
        actual = sut.makeDisplayedEntryList(actual, 2, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

}
