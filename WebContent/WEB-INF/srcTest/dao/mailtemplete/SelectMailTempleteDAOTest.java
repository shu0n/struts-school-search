package dao.mailtemplete;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.MailTempleteActionForm;
import dao.mailtemplete.sql.MailTempleteSelectWhereActionForm;
import test.db.DbUnitTester;

public class SelectMailTempleteDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SelectMailTempleteDAOTest.xml") {
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
    public void testSelectMatchedMailTempleteToGetAllMailTemplete() throws Exception {
        String[] expected = {"1", "2", "3"};

        SelectMailTempleteDAO sut = new SelectMailTempleteDAO();
        MailTempleteSelectWhereActionForm whereForm = new MailTempleteSelectWhereActionForm();
        List<MailTempleteActionForm> formList = sut.selectMatchedMailTemplete(whereForm);
        String[] actual = new String[3];
        for(int i = 0; i < formList.size(); i++) {
            actual[i] = String.valueOf(formList.get(i).getMailTempleteId());
        }

        assertThat(actual, is(arrayContaining(expected)));
    }

    @Test
    public void testSelectMatchedMailTempleteToGetOneMailTempleteByMailTempleteId() throws Exception {
        MailTempleteActionForm expected = new MailTempleteActionForm();
        expected.setMailTempleteId(3);
        expected.setMailTempleteName("メッセージ受信通知");
        expected.setMailTempleteSubject("メッセージを受信しました。");
        expected.setMailTempleteHeader("メッセージを受信しました。");
        expected.setMailTempleteFooter("このメールは送信専用です。||CHR(13)||CHR(10)||"
                + "スクール検索サイト School Serach：http://localhost:8080/school-search/");
        expected.setMailTempleteCreatedAt(Timestamp.valueOf("2020-01-06 07:08:09.123"));
        expected.setMailTempleteUpdatedAt(Timestamp.valueOf("2020-01-07 08:09:10.234"));

        SelectMailTempleteDAO sut = new SelectMailTempleteDAO();
        MailTempleteSelectWhereActionForm whereForm = new MailTempleteSelectWhereActionForm();
        whereForm.setMailTempleteId(3);
        MailTempleteActionForm actual = sut.selectMatchedMailTemplete(whereForm).get(0);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testSelectMatchedMailTempleteToGetOneMailTempleteByMailTempleteName() throws Exception {
        int expected = 1;

        SelectMailTempleteDAO sut = new SelectMailTempleteDAO();
        MailTempleteSelectWhereActionForm whereForm = new MailTempleteSelectWhereActionForm();
        whereForm.setMailTempleteName("アカウント作成完了通知");
        List<MailTempleteActionForm> actual = sut.selectMatchedMailTemplete(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedMailTempleteToGetNoMailTempleteByMailTempleteName() throws Exception {
        int expected = 0;

        SelectMailTempleteDAO sut = new SelectMailTempleteDAO();
        MailTempleteSelectWhereActionForm whereForm = new MailTempleteSelectWhereActionForm();
        whereForm.setMailTempleteName("お問合せ受付完了通知");
        List<MailTempleteActionForm> actual = sut.selectMatchedMailTemplete(whereForm);

        assertThat(actual.size(), is(expected));
    }

}
