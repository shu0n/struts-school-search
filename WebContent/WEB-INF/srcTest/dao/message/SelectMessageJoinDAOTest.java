package dao.message;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.MessageExtendActionForm;
import dao.message.sql.MessageSelectJoinWhereActionForm;
import test.db.DbUnitTester;

public class SelectMessageJoinDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SelectMessageJoinDAOTest.xml") {
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
    public void testSelectMatchedMessageToGetAllMessage() throws Exception {
        String[] expected = {"31", "32", "33", "34", "35"};

        SelectMessageJoinDAO sut = new SelectMessageJoinDAO();
        MessageSelectJoinWhereActionForm whereForm = new MessageSelectJoinWhereActionForm();
        List<MessageExtendActionForm> formList = sut.selectMatchedMessage(whereForm);
        String[] actual = new String[5];
        for(int i = 0; i < formList.size(); i++) {
            actual[i] = String.valueOf(formList.get(i).getMessageId());
        }

        assertThat(actual, is(arrayContaining(expected)));
    }

    @Test
    public void testSelectMatchedMessageToGetOneMessageByMessageId() throws Exception {
        MessageExtendActionForm expected = new MessageExtendActionForm();
        expected.setMessageId(35);
        expected.setReplyMessageId(34);
        expected.setEntryId(10);
        expected.setSenderAccountId(3);
        expected.setSenderLastName("田中");
        expected.setSenderFirstName("三郎");
        expected.setRecipientAccountId(1);
        expected.setRecipientLastName("田中");
        expected.setRecipientFirstName("太郎");
        expected.setMessageSubject("メッセージ題名5");
        expected.setMessageBody("メッセージ本文5");
        expected.setSendedAt(Timestamp.valueOf("2022-01-09 10:11:12.456"));
        expected.setOpenedFlag("1");
        expected.setOpenedAt(Timestamp.valueOf("2022-01-10 11:12:13.567"));
        expected.setSenderDeleteFlag("1");
        expected.setSenderFlagUpdatedAt(Timestamp.valueOf("2022-01-11 12:13:14.678"));
        expected.setRecipientDeleteFlag("1");
        expected.setRecipientFlagUpdatedAt(Timestamp.valueOf("2022-01-12 13:14:15.789"));

        SelectMessageJoinDAO sut = new SelectMessageJoinDAO();
        MessageSelectJoinWhereActionForm whereForm = new MessageSelectJoinWhereActionForm();
        whereForm.setMessageId(35);
        MessageExtendActionForm actual = sut.selectMatchedMessage(whereForm).get(0);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testSelectMatchedMessageToGetOneMessageByMultipleCondition() throws Exception {
        int expected = 1;

        SelectMessageJoinDAO sut = new SelectMessageJoinDAO();
        MessageSelectJoinWhereActionForm whereForm = new MessageSelectJoinWhereActionForm();
        whereForm.setReplyMessageId(33);
        whereForm.setEntryId(9);
        whereForm.setSenderAccountId(2);
        whereForm.setRecipientAccountId(5);
        whereForm.setOpenedFlag("1");
        whereForm.setSenderDeleteFlag("1");
        whereForm.setRecipientDeleteFlag("0");
        List<MessageExtendActionForm> actual = sut.selectMatchedMessage(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedMessageToGetNoMessage() throws Exception {
        int expected = 0;

        SelectMessageJoinDAO sut = new SelectMessageJoinDAO();
        MessageSelectJoinWhereActionForm whereForm = new MessageSelectJoinWhereActionForm();
        whereForm.setReplyMessageId(33);
        whereForm.setEntryId(9);
        whereForm.setSenderAccountId(2);
        whereForm.setRecipientAccountId(5);
        whereForm.setOpenedFlag("1");
        whereForm.setSenderDeleteFlag("1");
        whereForm.setRecipientDeleteFlag("1");
        List<MessageExtendActionForm> actual = sut.selectMatchedMessage(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedMessageToGetTwoMessageByMultipleCondition() throws Exception {
        int expected = 2;

        SelectMessageJoinDAO sut = new SelectMessageJoinDAO();
        MessageSelectJoinWhereActionForm whereForm = new MessageSelectJoinWhereActionForm();
        whereForm.setRecipientAccountId(5);
        List<MessageExtendActionForm> actual = sut.selectMatchedMessage(whereForm);

        assertThat(actual.size(), is(expected));
    }

}
