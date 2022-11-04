package model.contact;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import actionform.MailTempleteActionForm;
import dao.mailtemplete.SelectMailTempleteDAO;
import dao.mailtemplete.sql.MailTempleteSelectWhereActionForm;
import util.MailUtil;
import util.PropertyUtil;

public class ContactMailModel {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator"); // 改行コード

    /**
     * お問合せ受付時にお問合せアカウントのメールアドレス宛にお問合せ通知メールを送信するためのメソッド
     * @param mailAddress メールアドレス
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     * @throws AddressException
     * @throws MessagingException
     * @throws ParseException
     */
    public void sendContactMailToInquirer(String mailAddress) throws
            ClassNotFoundException,
            IOException,
            SQLException,
            AddressException,
            MessagingException,
            ParseException {
        // DAOメソッドに引き渡すアクションフォームを生成してメールテンプレート名を格納する。
        MailTempleteSelectWhereActionForm mailTempleteSelectForm = new MailTempleteSelectWhereActionForm();
        mailTempleteSelectForm.setMailTempleteName("お問合せ受付完了通知");
        // メールテンプレートを取得する。
        MailTempleteActionForm mailTempleteForm
                = new SelectMailTempleteDAO().selectMatchedMailTemplete(mailTempleteSelectForm).get(0);
        String subject = mailTempleteForm.getMailTempleteSubject(); // 件名
        String header = mailTempleteForm.getMailTempleteHeader(); // ヘッダー文
        String footer = mailTempleteForm.getMailTempleteFooter(); // フッター文
        // 本文を作成する。
        String content
                = header
                + LINE_SEPARATOR
                + footer;

        // メールを送信する。
        new MailUtil().sendMail(mailAddress, subject, content);
    }

    /**
     * お問合せ受付時に管理者のメールアドレス宛にお問合せ受付通知メールを送信するためのメソッド
     * @param contactId お問合せID
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws AddressException
     * @throws MessagingException
     * @throws ParseException
     */
    public void sendContactMailToAdmin(int contactId) throws
            IOException,
            ClassNotFoundException,
            SQLException,
            AddressException,
            MessagingException,
            ParseException {
        // プロパティ情報を取得する。
        Properties props = getProperteis();

        // 管理者(お問合せ先)メールアドレスを取得する。
        String mailAddress = props.getProperty("contact.mailaddress");
        // DAOメソッドに引き渡すアクションフォームを生成してメールテンプレート名を格納する。
        MailTempleteSelectWhereActionForm mailTempleteSelectForm = new MailTempleteSelectWhereActionForm();
        mailTempleteSelectForm.setMailTempleteName("お問合せ受付通知");
        // メールテンプレートを取得する。
        MailTempleteActionForm mailTempleteForm
                = new SelectMailTempleteDAO().selectMatchedMailTemplete(mailTempleteSelectForm).get(0);
        String subject = mailTempleteForm.getMailTempleteSubject(); // 件名
        String header = mailTempleteForm.getMailTempleteHeader(); // ヘッダー文
        String footer = mailTempleteForm.getMailTempleteFooter(); // フッター文

        // 管理画面 お問合せ詳細画面へのURLを生成する。
        String viewDetailedContactUrl
                = props.getProperty("site.url")
                + "/admin/viewDetailedContact.do"
                + "?contactId=" + contactId;

        // 本文を作成する。
        String content
                = header
                + LINE_SEPARATOR
                + "詳細は下記のURLから確認してください。"
                + LINE_SEPARATOR
                + viewDetailedContactUrl
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
