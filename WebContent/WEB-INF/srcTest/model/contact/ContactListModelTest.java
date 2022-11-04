package model.contact;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import actionform.ContactExtendActionForm;

public class ContactListModelTest {

    @Test
    public void testSetListContactDataToSetData() {
        List<ContactExtendActionForm> expected = new ArrayList<>();
        ContactExtendActionForm expectedForm1 = new ContactExtendActionForm();
        expectedForm1.setContactId(1);
        expectedForm1.setContactedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expectedForm1.setContactUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expectedForm1.setStrContactedAt("2022/01/02 03:04:05");
        expectedForm1.setStrContactUpdatedAt("2022/01/03 04:05:06");
        expected.add(expectedForm1);
        ContactExtendActionForm expectedForm2 = new ContactExtendActionForm();
        expectedForm2.setContactId(2);
        expectedForm2.setContactedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expectedForm2.setContactUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        expectedForm2.setStrContactedAt("2022/01/04 05:06:07");
        expectedForm2.setStrContactUpdatedAt("2022/01/05 06:07:08");
        expected.add(expectedForm2);
        ContactExtendActionForm expectedForm3 = new ContactExtendActionForm();
        expectedForm3.setContactId(3);
        expectedForm3.setContactedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        expectedForm3.setContactUpdatedAt(Timestamp.valueOf("2022-01-07 08:09:10.234"));
        expectedForm3.setStrContactedAt("2022/01/06 07:08:09");
        expectedForm3.setStrContactUpdatedAt("2022/01/07 08:09:10");
        expected.add(expectedForm3);

        ContactListModel sut = new ContactListModel();
        List<ContactExtendActionForm> actual = new ArrayList<>();
        ContactExtendActionForm actualForm1 = new ContactExtendActionForm();
        actualForm1.setContactId(1);
        actualForm1.setContactedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actualForm1.setContactUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        actual.add(actualForm1);
        ContactExtendActionForm actualForm2 = new ContactExtendActionForm();
        actualForm2.setContactId(2);
        actualForm2.setContactedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actualForm2.setContactUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        actual.add(actualForm2);
        ContactExtendActionForm actualForm3 = new ContactExtendActionForm();
        actualForm3.setContactId(3);
        actualForm3.setContactedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        actualForm3.setContactUpdatedAt(Timestamp.valueOf("2022-01-07 08:09:10.234"));
        actual.add(actualForm3);
        actual = sut.setListContactData(actual);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortContactExtendFormListToSortByDescendingContactedAt() {
        List<ContactExtendActionForm> expected = new ArrayList<>();
        ContactExtendActionForm expectedForm1 = new ContactExtendActionForm();
        expectedForm1.setContactId(3);
        expectedForm1.setContactedAt(Timestamp.valueOf("2022-01-08 09:10:11.345"));
        expected.add(expectedForm1);
        ContactExtendActionForm expectedForm2 = new ContactExtendActionForm();
        expectedForm2.setContactId(1);
        expectedForm2.setContactedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        expected.add(expectedForm2);
        ContactExtendActionForm expectedForm3 = new ContactExtendActionForm();
        expectedForm3.setContactId(4);
        expectedForm3.setContactedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(expectedForm3);
        ContactExtendActionForm expectedForm4 = new ContactExtendActionForm();
        expectedForm4.setContactId(2);
        expectedForm4.setContactedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(expectedForm4);

        ContactListModel sut = new ContactListModel();
        List<ContactExtendActionForm> actual = new ArrayList<>();
        ContactExtendActionForm actualForm1 = new ContactExtendActionForm();
        actualForm1.setContactId(1);
        actualForm1.setContactedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        actual.add(actualForm1);
        ContactExtendActionForm actualForm2 = new ContactExtendActionForm();
        actualForm2.setContactId(2);
        actualForm2.setContactedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actual.add(actualForm2);
        ContactExtendActionForm actualForm3 = new ContactExtendActionForm();
        actualForm3.setContactId(3);
        actualForm3.setContactedAt(Timestamp.valueOf("2022-01-08 09:10:11.345"));
        actual.add(actualForm3);
        ContactExtendActionForm actualForm4 = new ContactExtendActionForm();
        actualForm4.setContactId(4);
        actualForm4.setContactedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actual.add(actualForm4);
        actual = sut.sortContactExtendFormList(actual, "byDescendingContactedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortContactExtendFormListToSortByAccendingContactedAt() {
        List<ContactExtendActionForm> expected = new ArrayList<>();
        ContactExtendActionForm expectedForm1 = new ContactExtendActionForm();
        expectedForm1.setContactId(2);
        expectedForm1.setContactedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(expectedForm1);
        ContactExtendActionForm expectedForm2 = new ContactExtendActionForm();
        expectedForm2.setContactId(4);
        expectedForm2.setContactedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(expectedForm2);
        ContactExtendActionForm expectedForm3 = new ContactExtendActionForm();
        expectedForm3.setContactId(1);
        expectedForm3.setContactedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        expected.add(expectedForm3);
        ContactExtendActionForm expectedForm4 = new ContactExtendActionForm();
        expectedForm4.setContactId(3);
        expectedForm4.setContactedAt(Timestamp.valueOf("2022-01-08 09:10:11.345"));
        expected.add(expectedForm4);

        ContactListModel sut = new ContactListModel();
        List<ContactExtendActionForm> actual = new ArrayList<>();
        ContactExtendActionForm actualForm1 = new ContactExtendActionForm();
        actualForm1.setContactId(1);
        actualForm1.setContactedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        actual.add(actualForm1);
        ContactExtendActionForm actualForm2 = new ContactExtendActionForm();
        actualForm2.setContactId(2);
        actualForm2.setContactedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actual.add(actualForm2);
        ContactExtendActionForm actualForm3 = new ContactExtendActionForm();
        actualForm3.setContactId(3);
        actualForm3.setContactedAt(Timestamp.valueOf("2022-01-08 09:10:11.345"));
        actual.add(actualForm3);
        ContactExtendActionForm actualForm4 = new ContactExtendActionForm();
        actualForm4.setContactId(4);
        actualForm4.setContactedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actual.add(actualForm4);
        actual = sut.sortContactExtendFormList(actual, "byAccendingContactedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortContactExtendFormListToSortByDescendingContactUpdatedAt() {
        List<ContactExtendActionForm> expected = new ArrayList<>();
        ContactExtendActionForm expectedForm1 = new ContactExtendActionForm();
        expectedForm1.setContactId(3);
        expectedForm1.setContactUpdatedAt(Timestamp.valueOf("2022-01-08 09:10:11.345"));
        expected.add(expectedForm1);
        ContactExtendActionForm expectedForm2 = new ContactExtendActionForm();
        expectedForm2.setContactId(1);
        expectedForm2.setContactUpdatedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        expected.add(expectedForm2);
        ContactExtendActionForm expectedForm3 = new ContactExtendActionForm();
        expectedForm3.setContactId(4);
        expectedForm3.setContactUpdatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(expectedForm3);
        ContactExtendActionForm expectedForm4 = new ContactExtendActionForm();
        expectedForm4.setContactId(2);
        expectedForm4.setContactUpdatedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(expectedForm4);

        ContactListModel sut = new ContactListModel();
        List<ContactExtendActionForm> actual = new ArrayList<>();
        ContactExtendActionForm actualForm1 = new ContactExtendActionForm();
        actualForm1.setContactId(1);
        actualForm1.setContactUpdatedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        actual.add(actualForm1);
        ContactExtendActionForm actualForm2 = new ContactExtendActionForm();
        actualForm2.setContactId(2);
        actualForm2.setContactUpdatedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actual.add(actualForm2);
        ContactExtendActionForm actualForm3 = new ContactExtendActionForm();
        actualForm3.setContactId(3);
        actualForm3.setContactUpdatedAt(Timestamp.valueOf("2022-01-08 09:10:11.345"));
        actual.add(actualForm3);
        ContactExtendActionForm actualForm4 = new ContactExtendActionForm();
        actualForm4.setContactId(4);
        actualForm4.setContactUpdatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actual.add(actualForm4);
        actual = sut.sortContactExtendFormList(actual, "byDescendingUpdatedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortContactExtendFormListToSortByAccendingContactUpdatedAt() {
        List<ContactExtendActionForm> expected = new ArrayList<>();
        ContactExtendActionForm expectedForm1 = new ContactExtendActionForm();
        expectedForm1.setContactId(2);
        expectedForm1.setContactUpdatedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(expectedForm1);
        ContactExtendActionForm expectedForm2 = new ContactExtendActionForm();
        expectedForm2.setContactId(4);
        expectedForm2.setContactUpdatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(expectedForm2);
        ContactExtendActionForm expectedForm3 = new ContactExtendActionForm();
        expectedForm3.setContactId(1);
        expectedForm3.setContactUpdatedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        expected.add(expectedForm3);
        ContactExtendActionForm expectedForm4 = new ContactExtendActionForm();
        expectedForm4.setContactId(3);
        expectedForm4.setContactUpdatedAt(Timestamp.valueOf("2022-01-08 09:10:11.345"));
        expected.add(expectedForm4);

        ContactListModel sut = new ContactListModel();
        List<ContactExtendActionForm> actual = new ArrayList<>();
        ContactExtendActionForm actualForm1 = new ContactExtendActionForm();
        actualForm1.setContactId(1);
        actualForm1.setContactUpdatedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        actual.add(actualForm1);
        ContactExtendActionForm actualForm2 = new ContactExtendActionForm();
        actualForm2.setContactId(2);
        actualForm2.setContactUpdatedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actual.add(actualForm2);
        ContactExtendActionForm actualForm3 = new ContactExtendActionForm();
        actualForm3.setContactId(3);
        actualForm3.setContactUpdatedAt(Timestamp.valueOf("2022-01-08 09:10:11.345"));
        actual.add(actualForm3);
        ContactExtendActionForm actualForm4 = new ContactExtendActionForm();
        actualForm4.setContactId(4);
        actualForm4.setContactUpdatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actual.add(actualForm4);
        actual = sut.sortContactExtendFormList(actual, "byAccendingUpdatedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedContactListInCaseCurrentPageIsFirst() {
        List<ContactExtendActionForm> expected = new ArrayList<>();
        ContactExtendActionForm expectedForm1 = new ContactExtendActionForm();
        expectedForm1.setContactId(1);
        expected.add(expectedForm1);
        ContactExtendActionForm expectedForm2 = new ContactExtendActionForm();
        expectedForm2.setContactId(2);
        expected.add(expectedForm2);
        ContactExtendActionForm expectedForm3 = new ContactExtendActionForm();
        expectedForm3.setContactId(3);
        expected.add(expectedForm3);
        ContactExtendActionForm expectedForm4 = new ContactExtendActionForm();
        expectedForm4.setContactId(4);
        expected.add(expectedForm4);
        ContactExtendActionForm expectedForm5 = new ContactExtendActionForm();
        expectedForm5.setContactId(5);
        expected.add(expectedForm5);
        ContactExtendActionForm expectedForm6 = new ContactExtendActionForm();
        expectedForm6.setContactId(6);
        expected.add(expectedForm6);
        ContactExtendActionForm expectedForm7 = new ContactExtendActionForm();
        expectedForm7.setContactId(7);
        expected.add(expectedForm7);
        ContactExtendActionForm expectedForm8 = new ContactExtendActionForm();
        expectedForm8.setContactId(8);
        expected.add(expectedForm8);
        ContactExtendActionForm expectedForm9 = new ContactExtendActionForm();
        expectedForm9.setContactId(9);
        expected.add(expectedForm9);
        ContactExtendActionForm expectedForm10 = new ContactExtendActionForm();
        expectedForm10.setContactId(10);
        expected.add(expectedForm10);

        ContactListModel sut = new ContactListModel();
        List<ContactExtendActionForm> actual = new ArrayList<>();
        ContactExtendActionForm actualForm1 = new ContactExtendActionForm();
        actualForm1.setContactId(1);
        actual.add(actualForm1);
        ContactExtendActionForm actualForm2 = new ContactExtendActionForm();
        actualForm2.setContactId(2);
        actual.add(actualForm2);
        ContactExtendActionForm actualForm3 = new ContactExtendActionForm();
        actualForm3.setContactId(3);
        actual.add(actualForm3);
        ContactExtendActionForm actualForm4 = new ContactExtendActionForm();
        actualForm4.setContactId(4);
        actual.add(actualForm4);
        ContactExtendActionForm actualForm5 = new ContactExtendActionForm();
        actualForm5.setContactId(5);
        actual.add(actualForm5);
        ContactExtendActionForm actualForm6 = new ContactExtendActionForm();
        actualForm6.setContactId(6);
        actual.add(actualForm6);
        ContactExtendActionForm actualForm7 = new ContactExtendActionForm();
        actualForm7.setContactId(7);
        actual.add(actualForm7);
        ContactExtendActionForm actualForm8 = new ContactExtendActionForm();
        actualForm8.setContactId(8);
        actual.add(actualForm8);
        ContactExtendActionForm actualForm9 = new ContactExtendActionForm();
        actualForm9.setContactId(9);
        actual.add(actualForm9);
        ContactExtendActionForm actualForm10 = new ContactExtendActionForm();
        actualForm10.setContactId(10);
        actual.add(actualForm10);
        ContactExtendActionForm actualForm11 = new ContactExtendActionForm();
        actualForm11.setContactId(11);
        actual.add(actualForm11);
        actual = sut.makeDisplayedContactList(actual, 1, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedContactListInCaseCurrentPageIsSecond() {
        List<ContactExtendActionForm> expected = new ArrayList<>();
        ContactExtendActionForm expectedForm11 = new ContactExtendActionForm();
        expectedForm11.setContactId(11);
        expected.add(expectedForm11);
        ContactExtendActionForm expectedForm12 = new ContactExtendActionForm();
        expectedForm12.setContactId(12);
        expected.add(expectedForm12);
        ContactExtendActionForm expectedForm13 = new ContactExtendActionForm();
        expectedForm13.setContactId(13);
        expected.add(expectedForm13);

        ContactListModel sut = new ContactListModel();
        List<ContactExtendActionForm> actual = new ArrayList<>();
        ContactExtendActionForm actualForm1 = new ContactExtendActionForm();
        actualForm1.setContactId(1);
        actual.add(actualForm1);
        ContactExtendActionForm actualForm2 = new ContactExtendActionForm();
        actualForm2.setContactId(2);
        actual.add(actualForm2);
        ContactExtendActionForm actualForm3 = new ContactExtendActionForm();
        actualForm3.setContactId(3);
        actual.add(actualForm3);
        ContactExtendActionForm actualForm4 = new ContactExtendActionForm();
        actualForm4.setContactId(4);
        actual.add(actualForm4);
        ContactExtendActionForm actualForm5 = new ContactExtendActionForm();
        actualForm5.setContactId(5);
        actual.add(actualForm5);
        ContactExtendActionForm actualForm6 = new ContactExtendActionForm();
        actualForm6.setContactId(6);
        actual.add(actualForm6);
        ContactExtendActionForm actualForm7 = new ContactExtendActionForm();
        actualForm7.setContactId(7);
        actual.add(actualForm7);
        ContactExtendActionForm actualForm8 = new ContactExtendActionForm();
        actualForm8.setContactId(8);
        actual.add(actualForm8);
        ContactExtendActionForm actualForm9 = new ContactExtendActionForm();
        actualForm9.setContactId(9);
        actual.add(actualForm9);
        ContactExtendActionForm actualForm10 = new ContactExtendActionForm();
        actualForm10.setContactId(10);
        actual.add(actualForm10);
        ContactExtendActionForm actualForm11 = new ContactExtendActionForm();
        actualForm11.setContactId(11);
        actual.add(actualForm11);
        ContactExtendActionForm actualForm12 = new ContactExtendActionForm();
        actualForm12.setContactId(12);
        actual.add(actualForm12);
        ContactExtendActionForm actualForm13 = new ContactExtendActionForm();
        actualForm13.setContactId(13);
        actual.add(actualForm13);
        actual = sut.makeDisplayedContactList(actual, 2, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedContactListInCaseCurrentPageIsSecondAndEmpty() {
        List<ContactExtendActionForm> expected = new ArrayList<>();

        ContactListModel sut = new ContactListModel();
        List<ContactExtendActionForm> actual = new ArrayList<>();
        ContactExtendActionForm actualForm1 = new ContactExtendActionForm();
        actualForm1.setContactId(1);
        actual.add(actualForm1);
        ContactExtendActionForm actualForm2 = new ContactExtendActionForm();
        actualForm2.setContactId(2);
        actual.add(actualForm2);
        ContactExtendActionForm actualForm3 = new ContactExtendActionForm();
        actualForm3.setContactId(3);
        actual.add(actualForm3);
        actual = sut.makeDisplayedContactList(actual, 2, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

}
