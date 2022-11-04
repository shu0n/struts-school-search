package model.message;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import actionform.MailTempleteActionForm;
import dao.account.SelectAccountDAO;
import dao.account.sql.AccountSelectWhereActionForm;
import dao.mailtemplete.SelectMailTempleteDAO;
import dao.mailtemplete.sql.MailTempleteSelectWhereActionForm;
import util.MailUtil;
import util.PropertyUtil;

public class MessageMaillModel {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator"); // 改行コード

    /**
     * メッセージ送信・返信時に受取側アカウントのメールアドレス宛にメッセージ受信通知メールを送信するためのメソッド
     * @param accountId アカウントID
     * @param messageId メッセージID
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     * @throws AddressException
     * @throws MessagingException
     * @throws ParseException
     */
    public void sendMailToRecipient(int accountId, int messageId) throws
            ClassNotFoundException, IOException, SQLException, AddressException, MessagingException, ParseException {
        // DAOメソッドに引き渡すアクションフォームを生成してアカウントIDを格納する。
        AccountSelectWhereActionForm accountSelectWhereForm = new AccountSelectWhereActionForm();
        accountSelectWhereForm.setAccountId(accountId);
        // スクール申込アカウントのメールアドレスを取得する。
        String mailAddress
                = new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm).get(0).getMailAddress();
        // DAOメソッドに引き渡すアクションフォームを生成してメールテンプレート名を格納する。
        MailTempleteSelectWhereActionForm mailTempleteSelectForm = new MailTempleteSelectWhereActionForm();
        mailTempleteSelectForm.setMailTempleteName("メッセージ受信通知");
        // メールテンプレートを取得する。
        MailTempleteActionForm mailTempleteForm
                = new SelectMailTempleteDAO().selectMatchedMailTemplete(mailTempleteSelectForm).get(0);
        String subject = mailTempleteForm.getMailTempleteSubject(); // 件名
        String header = mailTempleteForm.getMailTempleteHeader(); // ヘッダー文
        String footer = mailTempleteForm.getMailTempleteFooter(); // フッター文

        // 申込詳細画面へのURLを生成する。
        String viewReceivedMessageUrl
                = getProperteis().getProperty("site.url")
                + "/viewReceivedMessage.do"
                + "?messageId=" + messageId;

        // 本文を作成する。
        String content
                = header
                + LINE_SEPARATOR
                + "詳細は下記のURLから確認してください。"
                + LINE_SEPARATOR
                + viewReceivedMessageUrl
                + LINE_SEPARATOR
                + footer;

        // メールを送信する。
        new MailUtil().sendMail(mailAddress, subject, content);
    }

    /**
     * プロパティ情報を取得するためのメソッド
     */
    Properties getProperteis() throws IOException {
        return new PropertyUtil().getProperty("environment.properties");
    }

}
