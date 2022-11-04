package util;

import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
    final String CHARSET = "UTF-8"; // 文字コード
    final String ENCODING = "base64"; // エンコーディング

    /**
     * 特定のメールアドレス宛にメールを送信するためのメソッド
     * @param to 送信先メールアドレス
     * @param subject 件名
     * @param content 本文
     * @return 実行結果
     * @throws IOException
     * @throws AddressException
     * @throws MessagingException
     * @throws ParseException
     */
    public void sendMail(String to, String subject, String content)
            throws IOException, AddressException, MessagingException, ParseException {
        // プロパティ情報を取得する。
        Properties props = getProperties();

        // 設定データをもとにセッションを生成する。
        Session session = Session.getInstance(props);
        // セッションをもとにメッセージのインスタンスを生成する。
        MimeMessage message = new MimeMessage(session);
        // 送信元メールアドレスを設定する。
        String from = props.getProperty("mail.smtp.from");
        message.setFrom(new InternetAddress(from));
        // 送信先メールアドレスを設定する。
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        // 件名を設定する。
        message.setSubject(subject, CHARSET);
        // 本文を設定する。
        message.setText(content, CHARSET);
        // メールヘッダーにエンコードを設定する。
        message.setHeader("Content-Transfer-Encoding", ENCODING);
        // メールを送信する。
        Transport.send(message);
        // メール送信時の情報をログに出力する。
        new LogUtil().writeMailLog(to, subject);
    }

    /**
     * プロパティ情報を取得するためのメソッド
     */
    Properties getProperties() throws IOException {
        // プロパティ情報を戻す。
        return new PropertyUtil().getProperty("environment.properties");
    }

}
