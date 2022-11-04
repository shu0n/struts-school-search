package dao.message.repack;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.MessageExtendActionForm;
import exception.NoDataExistException;
import test.db.DbUnitTester;

public class GetMessageDataRepackTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.GetMessageDataRepackTest.xml") {
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
    public void testGetSendMessageDataInCaseOneMatchedMessageExist() throws Exception {
        MessageExtendActionForm expected = new MessageExtendActionForm();
        expected.setEntryId(6);
        expected.setSenderAccountId(1);
        expected.setRecipientAccountId(5);
        expected.setRecipientLastName("棚橋");
        expected.setRecipientFirstName("五郎");

        GetMessageDataRepack sut = new GetMessageDataRepack();
        MessageExtendActionForm actual = new MessageExtendActionForm();
        actual = sut.getSendMessageData(6, 1, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test(expected = NoDataExistException.class)
    public void testGetSendMessageDataInCaseNoMatchedMessageExist() throws Exception {
        GetMessageDataRepack sut = new GetMessageDataRepack();
        MessageExtendActionForm actual = new MessageExtendActionForm();
        sut.getSendMessageData(11, 1, actual);
    }

    @Test
    public void testGetReplyMessageDataInCaseOneMatchedMessageExist() throws Exception {
        MessageExtendActionForm expected = new MessageExtendActionForm();
        expected.setReplyMessageId(21);
        expected.setEntryId(6);
        expected.setSenderAccountId(4);
        expected.setRecipientAccountId(3);
        expected.setRecipientLastName("鈴木");
        expected.setRecipientFirstName("三郎");
        expected.setMessageSubject("メッセージ題名1");
        expected.setMessageBody(
                System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + "------------------------------"
                + System.getProperty("line.separator")
                +"メッセージ本文1"
                );

        GetMessageDataRepack sut = new GetMessageDataRepack();
        MessageExtendActionForm actual = new MessageExtendActionForm();
        actual = sut.getReplyMessageData(21, 4, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test(expected = NoDataExistException.class)
    public void testGetReplyMessageDataInCaseNoMatchedMessageExist() throws Exception {
        GetMessageDataRepack sut = new GetMessageDataRepack();
        MessageExtendActionForm actual = new MessageExtendActionForm();
        actual = sut.getReplyMessageData(12, 2, actual);
    }

    @Test
    public void testGetReceivedMessageDataInCaseOneMatchedMessageExist() throws Exception {
        MessageExtendActionForm expected = new MessageExtendActionForm();
        expected.setMessageId(22);
        expected.setReplyMessageId(21);
        expected.setEntryId(7);
        expected.setSenderAccountId(4);
        expected.setSenderLastName("横田");
        expected.setSenderFirstName("四郎");
        expected.setRecipientAccountId(5);
        expected.setMessageSubject("メッセージ題名2");
        expected.setMessageBody("メッセージ本文2");
        expected.setStrSendedAt("2022/01/03 04:05:06");

        GetMessageDataRepack sut = new GetMessageDataRepack();
        MessageExtendActionForm actual = new MessageExtendActionForm();
        actual = sut.getReceivedMessageData(22, 5, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test(expected = NoDataExistException.class)
    public void testGetReceivedMessageDataInCaseNoMatchedMessageExist() throws Exception {
        GetMessageDataRepack sut = new GetMessageDataRepack();
        MessageExtendActionForm actual = new MessageExtendActionForm();
        actual = sut.getReceivedMessageData(24, 6, actual);
    }

    @Test
    public void testGetSendedMessageDataInCaseOneMatchedMessageExist() throws Exception {
        MessageExtendActionForm expected = new MessageExtendActionForm();
        expected.setMessageId(23);
        expected.setReplyMessageId(22);
        expected.setEntryId(8);
        expected.setSenderAccountId(1);
        expected.setRecipientAccountId(5);
        expected.setRecipientLastName("棚橋");
        expected.setRecipientFirstName("五郎");
        expected.setMessageSubject("メッセージ題名3");
        expected.setMessageBody("メッセージ本文3");
        expected.setStrSendedAt("2022/01/04 05:06:07");

        GetMessageDataRepack sut = new GetMessageDataRepack();
        MessageExtendActionForm actual = new MessageExtendActionForm();
        actual = sut.getSendedMessageData(23, 1, actual);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test(expected = NoDataExistException.class)
    public void testGetSendedMessageDataInCaseNoMatchedMessageExist() throws Exception {
        GetMessageDataRepack sut = new GetMessageDataRepack();
        MessageExtendActionForm actual = new MessageExtendActionForm();
        actual = sut.getSendedMessageData(24, 3, actual);
    }

}
