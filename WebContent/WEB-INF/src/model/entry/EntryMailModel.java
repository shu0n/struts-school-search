package model.entry;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import actionform.MailTempleteActionForm;
import dao.account.SelectAccountDAO;
import dao.account.sql.AccountSelectWhereActionForm;
import dao.admin.SelectAdminDAO;
import dao.admin.sql.AdminSelectWhereActionForm;
import dao.mailtemplete.SelectMailTempleteDAO;
import dao.mailtemplete.sql.MailTempleteSelectWhereActionForm;
import util.MailUtil;
import util.PropertyUtil;

public class EntryMailModel {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator"); // 改行コード

    /**
     * 申込完了時に申込アカウントのメールアドレス宛に申込完了通知メールを送信するためのメソッド
     * @param accountId アカウントID
     * @param entryId 申込ID
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     * @throws AddressException
     * @throws MessagingException
     * @throws ParseException
     */
    public void sendEntryMailToApplicantAccount(int accountId, int entryId) throws
            ClassNotFoundException, IOException, SQLException, AddressException, MessagingException, ParseException {
        // DAOメソッドに引き渡すアクションフォームを生成してアカウントIDを格納する。
        AccountSelectWhereActionForm accountSelectWhereForm = new AccountSelectWhereActionForm();
        accountSelectWhereForm.setAccountId(accountId);
        // スクール申込アカウントのメールアドレスを取得する。
        String mailAddress
                = new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm).get(0).getMailAddress();
        // DAOメソッドに引き渡すアクションフォームを生成してメールテンプレート名を格納する。
        MailTempleteSelectWhereActionForm mailTempleteSelectForm = new MailTempleteSelectWhereActionForm();
        mailTempleteSelectForm.setMailTempleteName("申込完了通知");
        // メールテンプレートを取得する。
        MailTempleteActionForm mailTempleteForm
                = new SelectMailTempleteDAO().selectMatchedMailTemplete(mailTempleteSelectForm).get(0);
        String subject = mailTempleteForm.getMailTempleteSubject(); // 件名
        String header = mailTempleteForm.getMailTempleteHeader(); // ヘッダー文
        String footer = mailTempleteForm.getMailTempleteFooter(); // フッター文

        // 申込詳細画面へのURLを生成する。
        String viewDetailedEntryUrl
                = getProperteis().getProperty("site.url")
                + "/viewDetailedEntry.do"
                + "?entryId=" + entryId;

        // 本文を作成する。
        String content
                = header
                + LINE_SEPARATOR
                + "詳細は下記のURLから確認してください。"
                + LINE_SEPARATOR
                + viewDetailedEntryUrl
                + LINE_SEPARATOR
                + footer;

        // メールを送信する。
        new MailUtil().sendMail(mailAddress, subject, content);
    }

    /**
     * 申込完了時にスクール登録アカウントのメールアドレス宛に申込受付通知メールを送信するためのメソッド
     * @param accountId アカウントID
     * @param entryId 申込ID
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     * @throws AddressException
     * @throws MessagingException
     * @throws ParseException
     */
    public void sendEntryMailToRegistrantAccount(int accountId, int entryId) throws
            ClassNotFoundException, IOException, SQLException, AddressException, MessagingException, ParseException {
        // DAOメソッドに引き渡すアクションフォームを生成してアカウントIDを格納する。
        AccountSelectWhereActionForm accountSelectWhereForm = new AccountSelectWhereActionForm();
        accountSelectWhereForm.setAccountId(accountId);
        // スクール登録アカウントのメールアドレスを取得する。
        String mailAddress
                = new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm).get(0).getMailAddress();
        // DAOメソッドに引き渡すアクションフォームを生成してメールテンプレート名を格納する。
        MailTempleteSelectWhereActionForm mailTempleteSelectForm = new MailTempleteSelectWhereActionForm();
        mailTempleteSelectForm.setMailTempleteName("申込受付通知");
        // メールテンプレートを取得する。
        MailTempleteActionForm mailTempleteForm
                = new SelectMailTempleteDAO().selectMatchedMailTemplete(mailTempleteSelectForm).get(0);
        String subject = mailTempleteForm.getMailTempleteSubject(); // 件名
        String header = mailTempleteForm.getMailTempleteHeader(); // ヘッダー文
        String footer = mailTempleteForm.getMailTempleteFooter(); // フッター文

        // 申込受付詳細画面へのURLを生成する。
        String viewReceivedEntryUrl
                = getProperteis().getProperty("site.url")
                + "/viewReceivedEntry.do"
                + "?entryId=" + entryId;

        // 本文を作成する。
        String content
                = header
                + LINE_SEPARATOR
                + "詳細は下記のURLから確認してください。"
                + LINE_SEPARATOR
                + viewReceivedEntryUrl
                + LINE_SEPARATOR
                + footer;

        // メールを送信する。
        new MailUtil().sendMail(mailAddress, subject, content);
    }

    /**
     * 申込完了時にスクール登録管理者のメールアドレス宛に申込受付通知メールを送信するためのメソッド
     * @param adminId 管理者ID
     * @param entryId 申込ID
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     * @throws AddressException
     * @throws MessagingException
     * @throws ParseException
     */
    public void sendEntryMailToRegistrantAdmin(int adminId, int entryId) throws
            ClassNotFoundException, IOException, SQLException, AddressException, MessagingException, ParseException {
        // DAOメソッドに引き渡すアクションフォームを生成して管理者IDを格納する。
        AdminSelectWhereActionForm adminSelectWhereForm = new AdminSelectWhereActionForm();
        adminSelectWhereForm.setAdminId(adminId);
        // スクール登録管理者のメールアドレスを取得する。
        String mailAddress
                = new SelectAdminDAO().selectMatchedAdmin(adminSelectWhereForm).get(0).getAdminMailAddress();
        // DAOメソッドに引き渡すアクションフォームを生成してメールテンプレート名を格納する。
        MailTempleteSelectWhereActionForm mailTempleteSelectForm = new MailTempleteSelectWhereActionForm();
        mailTempleteSelectForm.setMailTempleteName("申込受付通知");
        // メールテンプレートを取得する。
        MailTempleteActionForm mailTempleteForm
                = new SelectMailTempleteDAO().selectMatchedMailTemplete(mailTempleteSelectForm).get(0);
        String subject = mailTempleteForm.getMailTempleteSubject(); // 件名
        String header = mailTempleteForm.getMailTempleteHeader(); // ヘッダー文
        String footer = mailTempleteForm.getMailTempleteFooter(); // フッター文

        // 申込受付詳細画面へのURLを生成する。
        String viewReceivedEntryUrl
                = getProperteis().getProperty("site.url")
                + "/admin/viewReceivedEntry.do"
                + "?entryId=" + entryId;

        // 本文を作成する。
        String content
                = header
                + LINE_SEPARATOR
                + "詳細は下記のURLから確認してください。"
                + LINE_SEPARATOR
                + viewReceivedEntryUrl
                + LINE_SEPARATOR
                + footer;

        // メールを送信する。
        new MailUtil().sendMail(mailAddress, subject, content);
    }

    /**
     * 申込キャンセル時に申込アカウントのメールアドレス宛に申込キャンセル完了通知メールを送信するためのメソッド
     * @param accountId アカウントID
     * @param entryId 申込ID
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     * @throws AddressException
     * @throws MessagingException
     * @throws ParseException
     */
    public void sendCancelMailToApplicantAccount(int accountId, int entryId) throws
            ClassNotFoundException, IOException, SQLException, AddressException, MessagingException, ParseException {
        // DAOメソッドに引き渡すアクションフォームを生成してアカウントIDを格納する。
        AccountSelectWhereActionForm accountSelectWhereForm = new AccountSelectWhereActionForm();
        accountSelectWhereForm.setAccountId(accountId);
        // スクール申込アカウントのメールアドレスを取得する。
        String mailAddress
                = new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm).get(0).getMailAddress();
        // DAOメソッドに引き渡すアクションフォームを生成してメールテンプレート名を格納する。
        MailTempleteSelectWhereActionForm mailTempleteSelectForm = new MailTempleteSelectWhereActionForm();
        mailTempleteSelectForm.setMailTempleteName("申込キャンセル完了通知");
        // メールテンプレートを取得する。
        MailTempleteActionForm mailTempleteForm
                = new SelectMailTempleteDAO().selectMatchedMailTemplete(mailTempleteSelectForm).get(0);
        String subject = mailTempleteForm.getMailTempleteSubject(); // 件名
        String header = mailTempleteForm.getMailTempleteHeader(); // ヘッダー文
        String footer = mailTempleteForm.getMailTempleteFooter(); // フッター文

        // 申込詳細画面へのURLを生成する。
        String viewDetailedEntryUrl
                = getProperteis().getProperty("site.url")
                + "/viewDetailedEntry.do"
                + "?entryId=" + entryId;

        // 本文を作成する。
        String content
                = header
                + LINE_SEPARATOR
                + "詳細は下記のURLから確認してください。"
                + LINE_SEPARATOR
                + viewDetailedEntryUrl
                + LINE_SEPARATOR
                + footer;

        // メールを送信する。
        new MailUtil().sendMail(mailAddress, subject, content);
    }

    /**
     * 申込キャンセル時にスクール登録アカウントのメールアドレス宛に申込キャンセル受付通知メールを送信するためのメソッド
     * @param accountId アカウントID
     * @param entryId 申込ID
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     * @throws AddressException
     * @throws MessagingException
     * @throws ParseException
     */
    public void sendCancelMailToRegistrantAccount(int accountId, int entryId) throws
            ClassNotFoundException, IOException, SQLException, AddressException, MessagingException, ParseException {
        // DAOメソッドに引き渡すアクションフォームを生成してアカウントIDを格納する。
        AccountSelectWhereActionForm accountSelectWhereForm = new AccountSelectWhereActionForm();
        accountSelectWhereForm.setAccountId(accountId);
        // スクール登録アカウントのメールアドレスを取得する。
        String mailAddress
                = new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm).get(0).getMailAddress();
        // DAOメソッドに引き渡すアクションフォームを生成してメールテンプレート名を格納する。
        MailTempleteSelectWhereActionForm mailTempleteSelectForm = new MailTempleteSelectWhereActionForm();
        mailTempleteSelectForm.setMailTempleteName("申込キャンセル受付通知");
        // メールテンプレートを取得する。
        MailTempleteActionForm mailTempleteForm
                = new SelectMailTempleteDAO().selectMatchedMailTemplete(mailTempleteSelectForm).get(0);
        String subject = mailTempleteForm.getMailTempleteSubject(); // 件名
        String header = mailTempleteForm.getMailTempleteHeader(); // ヘッダー文
        String footer = mailTempleteForm.getMailTempleteFooter(); // フッター文

        // 申込受付詳細画面へのURLを生成する。
        String viewReceivedEntryUrl
                = getProperteis().getProperty("site.url")
                + "/viewReceivedEntry.do"
                + "?entryId=" + entryId;

        // 本文を作成する。
        String content
                = header
                + LINE_SEPARATOR
                + "詳細は下記のURLから確認してください。"
                + LINE_SEPARATOR
                + viewReceivedEntryUrl
                + LINE_SEPARATOR
                + footer;

        // メールを送信する。
        new MailUtil().sendMail(mailAddress, subject, content);
    }

    /**
     * 申込キャンセル時にスクール登録管理者のメールアドレス宛に申込キャンセル受付通知メールを送信するためのメソッド
     * @param adminId 管理者ID
     * @param entryId 申込ID
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     * @throws AddressException
     * @throws MessagingException
     * @throws ParseException
     */
    public void sendCancelMailToRegistrantAdmin(int adminId, int entryId) throws
            ClassNotFoundException, IOException, SQLException, AddressException, MessagingException, ParseException {
        // DAOメソッドに引き渡すアクションフォームを生成して管理者IDを格納する。
        AdminSelectWhereActionForm adminSelectWhereForm = new AdminSelectWhereActionForm();
        adminSelectWhereForm.setAdminId(adminId);
        // スクール登録管理者のメールアドレスを取得する。
        String mailAddress
                = new SelectAdminDAO().selectMatchedAdmin(adminSelectWhereForm).get(0).getAdminMailAddress();
        // DAOメソッドに引き渡すアクションフォームを生成してメールテンプレート名を格納する。
        MailTempleteSelectWhereActionForm mailTempleteSelectForm = new MailTempleteSelectWhereActionForm();
        mailTempleteSelectForm.setMailTempleteName("申込キャンセル受付通知");
        // メールテンプレートを取得する。
        MailTempleteActionForm mailTempleteForm
                = new SelectMailTempleteDAO().selectMatchedMailTemplete(mailTempleteSelectForm).get(0);
        String subject = mailTempleteForm.getMailTempleteSubject(); // 件名
        String header = mailTempleteForm.getMailTempleteHeader(); // ヘッダー文
        String footer = mailTempleteForm.getMailTempleteFooter(); // フッター文

        // 申込受付詳細画面へのURLを生成する。
        String viewReceivedEntryUrl
                = getProperteis().getProperty("site.url")
                + "/admin/viewReceivedEntry.do"
                + "?entryId=" + entryId;

        // 本文を作成する。
        String content
                = header
                + LINE_SEPARATOR
                + "詳細は下記のURLから確認してください。"
                + LINE_SEPARATOR
                + viewReceivedEntryUrl
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
