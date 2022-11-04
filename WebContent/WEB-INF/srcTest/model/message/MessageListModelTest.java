package model.message;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.MessageExtendActionForm;
import test.db.DbUnitTester;

public class MessageListModelTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.MessageListModelTest.xml") {
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
    public void testSortMessageExtendFormListToSortByDescendingSendedAt() {
        List<MessageExtendActionForm> expected = new ArrayList<>();
        MessageExtendActionForm expectedForm1 = new MessageExtendActionForm();
        expectedForm1.setMessageId(3);
        expectedForm1.setSendedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        expected.add(expectedForm1);
        MessageExtendActionForm expectedForm2 = new MessageExtendActionForm();
        expectedForm2.setMessageId(4);
        expectedForm2.setSendedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(expectedForm2);
        MessageExtendActionForm expectedForm3 = new MessageExtendActionForm();
        expectedForm3.setMessageId(1);
        expectedForm3.setSendedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expected.add(expectedForm3);
        MessageExtendActionForm expectedForm4 = new MessageExtendActionForm();
        expectedForm4.setMessageId(2);
        expectedForm4.setSendedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(expectedForm4);

        MessageListModel sut = new MessageListModel();
        List<MessageExtendActionForm> actual = new ArrayList<>();
        MessageExtendActionForm actualForm1 = new MessageExtendActionForm();
        actualForm1.setMessageId(1);
        actualForm1.setSendedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        actual.add(actualForm1);
        MessageExtendActionForm actualForm2 = new MessageExtendActionForm();
        actualForm2.setMessageId(2);
        actualForm2.setSendedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actual.add(actualForm2);
        MessageExtendActionForm actualForm3 = new MessageExtendActionForm();
        actualForm3.setMessageId(3);
        actualForm3.setSendedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        actual.add(actualForm3);
        MessageExtendActionForm actualForm4 = new MessageExtendActionForm();
        actualForm4.setMessageId(4);
        actualForm4.setSendedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actual.add(actualForm4);
        actual = sut.sortMessageExtendFormList(actual, "byDescendingSendedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortMessageExtendFormListToSortByAccendingSendedAt() {
        List<MessageExtendActionForm> expected = new ArrayList<>();
        MessageExtendActionForm expectedForm1 = new MessageExtendActionForm();
        expectedForm1.setMessageId(2);
        expectedForm1.setSendedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(expectedForm1);
        MessageExtendActionForm expectedForm2 = new MessageExtendActionForm();
        expectedForm2.setMessageId(1);
        expectedForm2.setSendedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expected.add(expectedForm2);
        MessageExtendActionForm expectedForm3 = new MessageExtendActionForm();
        expectedForm3.setMessageId(4);
        expectedForm3.setSendedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(expectedForm3);
        MessageExtendActionForm expectedForm4 = new MessageExtendActionForm();
        expectedForm4.setMessageId(3);
        expectedForm4.setSendedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        expected.add(expectedForm4);

        MessageListModel sut = new MessageListModel();
        List<MessageExtendActionForm> actual = new ArrayList<>();
        MessageExtendActionForm actualForm1 = new MessageExtendActionForm();
        actualForm1.setMessageId(1);
        actualForm1.setSendedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        actual.add(actualForm1);
        MessageExtendActionForm actualForm2 = new MessageExtendActionForm();
        actualForm2.setMessageId(2);
        actualForm2.setSendedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actual.add(actualForm2);
        MessageExtendActionForm actualForm3 = new MessageExtendActionForm();
        actualForm3.setMessageId(3);
        actualForm3.setSendedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        actual.add(actualForm3);
        MessageExtendActionForm actualForm4 = new MessageExtendActionForm();
        actualForm4.setMessageId(4);
        actualForm4.setSendedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actual.add(actualForm4);
        actual = sut.sortMessageExtendFormList(actual, "byAccendingSendedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testGetReplySourceMessageList() throws Exception {
        List<MessageExtendActionForm> expected = new ArrayList<>();
        MessageExtendActionForm expectedForm1 = new MessageExtendActionForm();
        expectedForm1.setMessageId(4);
        expectedForm1.setSendedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        expected.add(expectedForm1);
        MessageExtendActionForm expectedForm2 = new MessageExtendActionForm();
        expectedForm2.setMessageId(3);
        expectedForm2.setSendedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(expectedForm2);
        MessageExtendActionForm expectedForm3 = new MessageExtendActionForm();
        expectedForm3.setMessageId(2);
        expectedForm3.setSendedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expected.add(expectedForm3);
        MessageExtendActionForm expectedForm4 = new MessageExtendActionForm();
        expectedForm4.setMessageId(1);
        expectedForm4.setSendedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(expectedForm4);

        MessageListModel sut = new MessageListModel();
        List<MessageExtendActionForm> actual = sut.getReplySourceMessageList(4);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i).getMessageId(), is(expected.get(i).getMessageId()));
            assertThat(actual.get(i).getSendedAt(), is(expected.get(i).getSendedAt()));
        }
    }

    @Test
    public void testSetListMessageDataToSetData() {
        List<MessageExtendActionForm> expected = new ArrayList<>();
        MessageExtendActionForm expectedForm1 = new MessageExtendActionForm();
        expectedForm1.setMessageId(1);
        expectedForm1.setSendedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expectedForm1.setStrSendedAt("2022/01/02 03:04:05");
        expected.add(expectedForm1);
        MessageExtendActionForm expectedForm2 = new MessageExtendActionForm();
        expectedForm2.setMessageId(2);
        expectedForm2.setSendedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expectedForm2.setStrSendedAt("2022/01/03 04:05:06");
        expected.add(expectedForm2);
        MessageExtendActionForm expectedForm3 = new MessageExtendActionForm();
        expectedForm3.setMessageId(3);
        expectedForm3.setSendedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expectedForm3.setStrSendedAt("2022/01/04 05:06:07");
        expected.add(expectedForm3);
        MessageExtendActionForm expectedForm4 = new MessageExtendActionForm();
        expectedForm4.setMessageId(4);
        expectedForm4.setSendedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        expectedForm4.setStrSendedAt("2022/01/05 06:07:08");
        expected.add(expectedForm4);

        MessageListModel sut = new MessageListModel();
        List<MessageExtendActionForm> actual = new ArrayList<>();
        MessageExtendActionForm actualForm1 = new MessageExtendActionForm();
        actualForm1.setMessageId(1);
        actualForm1.setSendedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actual.add(actualForm1);
        MessageExtendActionForm actualForm2 = new MessageExtendActionForm();
        actualForm2.setMessageId(2);
        actualForm2.setSendedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        actual.add(actualForm2);
        MessageExtendActionForm actualForm3 = new MessageExtendActionForm();
        actualForm3.setMessageId(3);
        actualForm3.setSendedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actual.add(actualForm3);
        MessageExtendActionForm actualForm4 = new MessageExtendActionForm();
        actualForm4.setMessageId(4);
        actualForm4.setSendedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        actual.add(actualForm4);
        actual = sut.setListMessageData(actual);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedMessageListInCaseCurrentPageIsFirst() {
        List<MessageExtendActionForm> expected = new ArrayList<>();
        MessageExtendActionForm expectedForm1 = new MessageExtendActionForm();
        expectedForm1.setMessageId(1);
        expected.add(expectedForm1);
        MessageExtendActionForm expectedForm2 = new MessageExtendActionForm();
        expectedForm2.setMessageId(2);
        expected.add(expectedForm2);
        MessageExtendActionForm expectedForm3 = new MessageExtendActionForm();
        expectedForm3.setMessageId(3);
        expected.add(expectedForm3);
        MessageExtendActionForm expectedForm4 = new MessageExtendActionForm();
        expectedForm4.setMessageId(4);
        expected.add(expectedForm4);
        MessageExtendActionForm expectedForm5 = new MessageExtendActionForm();
        expectedForm5.setMessageId(5);
        expected.add(expectedForm5);
        MessageExtendActionForm expectedForm6 = new MessageExtendActionForm();
        expectedForm6.setMessageId(6);
        expected.add(expectedForm6);
        MessageExtendActionForm expectedForm7 = new MessageExtendActionForm();
        expectedForm7.setMessageId(7);
        expected.add(expectedForm7);
        MessageExtendActionForm expectedForm8 = new MessageExtendActionForm();
        expectedForm8.setMessageId(8);
        expected.add(expectedForm8);
        MessageExtendActionForm expectedForm9 = new MessageExtendActionForm();
        expectedForm9.setMessageId(9);
        expected.add(expectedForm9);
        MessageExtendActionForm expectedForm10 = new MessageExtendActionForm();
        expectedForm10.setMessageId(10);
        expected.add(expectedForm10);

        MessageListModel sut = new MessageListModel();
        List<MessageExtendActionForm> actual = new ArrayList<>();
        MessageExtendActionForm actualForm1 = new MessageExtendActionForm();
        actualForm1.setMessageId(1);
        actual.add(actualForm1);
        MessageExtendActionForm actualForm2 = new MessageExtendActionForm();
        actualForm2.setMessageId(2);
        actual.add(actualForm2);
        MessageExtendActionForm actualForm3 = new MessageExtendActionForm();
        actualForm3.setMessageId(3);
        actual.add(actualForm3);
        MessageExtendActionForm actualForm4 = new MessageExtendActionForm();
        actualForm4.setMessageId(4);
        actual.add(actualForm4);
        MessageExtendActionForm actualForm5 = new MessageExtendActionForm();
        actualForm5.setMessageId(5);
        actual.add(actualForm5);
        MessageExtendActionForm actualForm6 = new MessageExtendActionForm();
        actualForm6.setMessageId(6);
        actual.add(actualForm6);
        MessageExtendActionForm actualForm7 = new MessageExtendActionForm();
        actualForm7.setMessageId(7);
        actual.add(actualForm7);
        MessageExtendActionForm actualForm8 = new MessageExtendActionForm();
        actualForm8.setMessageId(8);
        actual.add(actualForm8);
        MessageExtendActionForm actualForm9 = new MessageExtendActionForm();
        actualForm9.setMessageId(9);
        actual.add(actualForm9);
        MessageExtendActionForm actualForm10 = new MessageExtendActionForm();
        actualForm10.setMessageId(10);
        actual.add(actualForm10);
        MessageExtendActionForm actualForm11 = new MessageExtendActionForm();
        actualForm11.setMessageId(11);
        actual.add(actualForm11);
        actual = sut.makeDisplayedMessageList(actual, 1, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedMessageListInCaseCurrentPageIsSecond() {
        List<MessageExtendActionForm> expected = new ArrayList<>();
        MessageExtendActionForm expectedForm11 = new MessageExtendActionForm();
        expectedForm11.setMessageId(11);
        expected.add(expectedForm11);
        MessageExtendActionForm expectedForm12 = new MessageExtendActionForm();
        expectedForm12.setMessageId(12);
        expected.add(expectedForm12);
        MessageExtendActionForm expectedForm13 = new MessageExtendActionForm();
        expectedForm13.setMessageId(13);
        expected.add(expectedForm13);

        MessageListModel sut = new MessageListModel();
        List<MessageExtendActionForm> actual = new ArrayList<>();
        MessageExtendActionForm actualForm1 = new MessageExtendActionForm();
        actualForm1.setMessageId(1);
        actual.add(actualForm1);
        MessageExtendActionForm actualForm2 = new MessageExtendActionForm();
        actualForm2.setMessageId(2);
        actual.add(actualForm2);
        MessageExtendActionForm actualForm3 = new MessageExtendActionForm();
        actualForm3.setMessageId(3);
        actual.add(actualForm3);
        MessageExtendActionForm actualForm4 = new MessageExtendActionForm();
        actualForm4.setMessageId(4);
        actual.add(actualForm4);
        MessageExtendActionForm actualForm5 = new MessageExtendActionForm();
        actualForm5.setMessageId(5);
        actual.add(actualForm5);
        MessageExtendActionForm actualForm6 = new MessageExtendActionForm();
        actualForm6.setMessageId(6);
        actual.add(actualForm6);
        MessageExtendActionForm actualForm7 = new MessageExtendActionForm();
        actualForm7.setMessageId(7);
        actual.add(actualForm7);
        MessageExtendActionForm actualForm8 = new MessageExtendActionForm();
        actualForm8.setMessageId(8);
        actual.add(actualForm8);
        MessageExtendActionForm actualForm9 = new MessageExtendActionForm();
        actualForm9.setMessageId(9);
        actual.add(actualForm9);
        MessageExtendActionForm actualForm10 = new MessageExtendActionForm();
        actualForm10.setMessageId(10);
        actual.add(actualForm10);
        MessageExtendActionForm actualForm11 = new MessageExtendActionForm();
        actualForm11.setMessageId(11);
        actual.add(actualForm11);
        MessageExtendActionForm actualForm12 = new MessageExtendActionForm();
        actualForm12.setMessageId(12);
        actual.add(actualForm12);
        MessageExtendActionForm actualForm13 = new MessageExtendActionForm();
        actualForm13.setMessageId(13);
        actual.add(actualForm13);
        actual = sut.makeDisplayedMessageList(actual, 2, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedMessageListInCaseCurrentPageIsSecondAndEmpty() {
        List<MessageExtendActionForm> expected = new ArrayList<>();

        MessageListModel sut = new MessageListModel();
        List<MessageExtendActionForm> actual = new ArrayList<>();
        MessageExtendActionForm actualForm1 = new MessageExtendActionForm();
        actualForm1.setMessageId(1);
        actual.add(actualForm1);
        MessageExtendActionForm actualForm2 = new MessageExtendActionForm();
        actualForm2.setMessageId(2);
        actual.add(actualForm2);
        MessageExtendActionForm actualForm3 = new MessageExtendActionForm();
        actualForm3.setMessageId(3);
        actual.add(actualForm3);
        actual = sut.makeDisplayedMessageList(actual, 2, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }
}
