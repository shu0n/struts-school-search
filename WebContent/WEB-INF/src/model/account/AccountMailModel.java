package model.account;

import java.io.IOException;
import java.net.URLEncoder;
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

public class AccountMailModel {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator"); // 改行コード

    /**
     * アカウント作成完了時にアカウント作成完了通知メールを送信するためのメソッド
     * @param mailAddress メールアドレス
     * @param activateToken 有効化トークン
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     * @throws AddressException
     * @throws MessagingException
     * @throws ParseException
     */
    public void sendCreateAccountMail(String mailAddress, String activateToken) throws
            ClassNotFoundException, IOException, SQLException, AddressException, MessagingException, ParseException {
        // DAOメソッドに引き渡すアクションフォームを生成してメールテンプレート名を格納する。
        MailTempleteSelectWhereActionForm mailTempleteSelectForm = new MailTempleteSelectWhereActionForm();
        mailTempleteSelectForm.setMailTempleteName("アカウント作成完了通知");
        // メールテンプレートを取得する。
        MailTempleteActionForm mailTempleteForm
                = new SelectMailTempleteDAO().selectMatchedMailTemplete(mailTempleteSelectForm).get(0);
        String subject = mailTempleteForm.getMailTempleteSubject(); // 件名
        String header = mailTempleteForm.getMailTempleteHeader(); // ヘッダー文
        String footer = mailTempleteForm.getMailTempleteFooter(); // フッター文

        // アカウントを有効にするためのURLを生成する。
        String encodeMailAddress = URLEncoder.encode(mailAddress, "UTF-8");
        String activateUrl
                = getProperteis().getProperty("site.url")
                + "activateAccount.do"
                + "?mailAddress=" + encodeMailAddress
                + "&activateToken=" + activateToken;

        // 本文を作成する。
        String content
                = header
                + LINE_SEPARATOR
                + "下記のURLにアクセスしてアカウントを有効にしてください。"
                + LINE_SEPARATOR
                + activateUrl
                + LINE_SEPARATOR
                + footer;

        // メールを送信する。
        new MailUtil().sendMail(mailAddress, subject, content);
    }

    /**
     * パスワード再発行申請時にパスワード再発行申請受付通知メールを送信するためのメソッド
     * @param mailAddress メールアドレス
     * @param reissueToken 再発行トークン
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     * @throws AddressException
     * @throws MessagingException
     * @throws ParseException
     */
    public void sendReissueMail(String mailAddress, String reissueToken) throws
            ClassNotFoundException, IOException, SQLException, AddressException, MessagingException, ParseException {
        // DAOメソッドに引き渡すアクションフォームを生成してメールテンプレート名を格納する。
        MailTempleteSelectWhereActionForm mailTempleteSelectForm = new MailTempleteSelectWhereActionForm();
        mailTempleteSelectForm.setMailTempleteName("パスワード再発行申請受付完了通知");
        // メールテンプレートを取得する。
        MailTempleteActionForm mailTempleteForm
                = new SelectMailTempleteDAO().selectMatchedMailTemplete(mailTempleteSelectForm).get(0);
        String subject = mailTempleteForm.getMailTempleteSubject(); // 件名
        String header = mailTempleteForm.getMailTempleteHeader(); // ヘッダー文
        String footer = mailTempleteForm.getMailTempleteFooter(); // フッター文

        // パスワード再発行 新しいパスワード入力 入力画面のURLを生成する。
        String encodeMailAddress = URLEncoder.encode(mailAddress, "UTF-8");
        String reissueUrl =
                getProperteis().getProperty("site.url")
                + "executeReissueInput.do"
                + "?mailAddress=" + encodeMailAddress
                + "&reissueToken=" + reissueToken;

        // 本文を作成する。
        String content
                = header
                + LINE_SEPARATOR
                + "下記のURLにアクセスしてアカウントを有効にしてください。"
                + LINE_SEPARATOR
                + reissueUrl
                + LINE_SEPARATOR
                + footer;

        // メールを送信する。
        new MailUtil().sendMail(mailAddress, subject, content);
    }

    /**
     * 退会完了時に退会完了通知メールを送信するためのメソッド
     * @param mailAddress メールアドレス
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     * @throws AddressException
     * @throws MessagingException
     * @throws ParseException
     */
    public void sendDeleteAccountMail(String mailAddress) throws
            ClassNotFoundException, IOException, SQLException, AddressException, MessagingException, ParseException {
        // DAOメソッドに引き渡すアクションフォームを生成してメールテンプレート名を格納する。
        MailTempleteSelectWhereActionForm mailTempleteSelectForm = new MailTempleteSelectWhereActionForm();
        mailTempleteSelectForm.setMailTempleteName("退会完了通知");
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
                + "退会が完了しました。"
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
