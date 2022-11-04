package dao.message.repack;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import actionform.AccountActionForm;
import actionform.EntryActionForm;
import actionform.MessageExtendActionForm;
import dao.account.SelectAccountDAO;
import dao.account.sql.AccountSelectWhereActionForm;
import dao.entry.SelectEntryDAO;
import dao.entry.sql.EntrySelectWhereActionForm;
import dao.message.SelectMessageJoinDAO;
import dao.message.sql.MessageSelectJoinWhereActionForm;
import exception.NoDataExistException;

public class GetMessageDataRepack {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator"); // 改行コード

    /**
     * メッセージの送信に必要な情報を取得するためのメソッド
     * @param entryId 申込ID
     * @param accountId 差出アカウントID
     * @param inForm メッセージのデータを格納するアクションフォーム
     * @return 検索条件に一致したメッセージのアクションフォーム
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoDataExistException
     */
    public MessageExtendActionForm getSendMessageData(
            int entryId,
            int accountId,
            MessageExtendActionForm inForm)
            throws ClassNotFoundException, IOException, SQLException, NoDataExistException {
        // DAOメソッドに引き渡すアクションフォームを生成して申込IDを格納する。
        EntrySelectWhereActionForm entrySelectWhereForm = new EntrySelectWhereActionForm();
        entrySelectWhereForm.setEntryId(entryId);
        // 申込テーブルから申込IDに紐づくレコードを取得する。
        List<EntryActionForm> entryFormList = new SelectEntryDAO().selectMatchedEntry(entrySelectWhereForm);
        if(entryFormList.isEmpty()) {
            // 取得結果が0件の場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        }
        // 受取アカウントIDを取得する。
        int recipientAccountId = entryFormList.get(0).getApplicantAccountId();

        // DAOメソッドに引き渡すアクションフォームを生成してアカウントIDを格納する。
        AccountSelectWhereActionForm accountSelectWhereForm = new AccountSelectWhereActionForm();
        accountSelectWhereForm.setAccountId(recipientAccountId);
        // 受取側アカウントのレコードを取得する。
        List<AccountActionForm> accountFormList = new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm);
        if(accountFormList.isEmpty()) {
            // 取得できない場合はNULLを戻す。
            return null;
        }

        // アクションフォームに取得したレコードのデータを格納する。
        inForm.setEntryId(entryId); // 申込ID
        inForm.setSenderAccountId(accountId); // 差出側アカウントID
        inForm.setRecipientAccountId(recipientAccountId); // 受取側アカウントID
        inForm.setRecipientLastName(accountFormList.get(0).getLastName()); // 受取側アカウント(姓)
        inForm.setRecipientFirstName(accountFormList.get(0).getFirstName()); // 受取側アカウント(名)

        // データを格納したアクションフォームを戻す。
        return inForm;
    }

    /**
     * メッセージの返信に必要な情報を取得するためのメソッド
     * @param messageId メッセージID
     * @param senderAccountId 差出アカウントID
     * @param inForm メッセージのデータを格納するアクションフォーム
     * @return 検索条件に一致したメッセージのアクションフォーム
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoDataExistException
     */
    public MessageExtendActionForm getReplyMessageData(
            int messageId,
            int senderAccountId,
            MessageExtendActionForm inForm)
            throws ClassNotFoundException, IOException, SQLException, NoDataExistException {
        // DAOメソッドに引き渡すアクションフォームを生成してメッセージIDを格納する。
        MessageSelectJoinWhereActionForm messageSelectJoinWhereForm = new MessageSelectJoinWhereActionForm();
        messageSelectJoinWhereForm.setMessageId(messageId);
        // メッセージテーブルからメッセージIDに紐づくレコードを取得する。
        List<MessageExtendActionForm> messageExtendFormList
                = new SelectMessageJoinDAO().selectMatchedMessage(messageSelectJoinWhereForm);
        if(messageExtendFormList.isEmpty()) {
            // 取得結果が0件の場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        }

        // 取得したレコードのデータをアクションフォームに格納する。
        inForm.setReplyMessageId(messageId); // 返信メッセージID
        inForm.setEntryId(messageExtendFormList.get(0).getEntryId()); // 申込ID
        inForm.setSenderAccountId(senderAccountId); // 差出アカウントID
        inForm.setRecipientAccountId(messageExtendFormList.get(0).getSenderAccountId()); // 受取アカウントID
        inForm.setRecipientLastName(messageExtendFormList.get(0).getSenderLastName()); // 受取アカウント(姓)
        inForm.setRecipientFirstName(messageExtendFormList.get(0).getSenderFirstName()); // 受取アカウント(名)
        inForm.setMessageSubject(messageExtendFormList.get(0).getMessageSubject()); // 件名
        inForm.setMessageBody(
                LINE_SEPARATOR
                + LINE_SEPARATOR
                + "------------------------------"
                + LINE_SEPARATOR
                + messageExtendFormList.get(0).getMessageBody()); // 本文

        // データを格納したアクションフォームを戻す。
        return inForm;
    }

    /**
     * 受信済メッセージの情報を取得するためのメソッド
     * @param messageId メッセージID
     * @param recipientAccountId 受取アカウントID
     * @param inForm アクションフォーム
     * @return 検索条件に一致したメッセージのアクションフォーム
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoDataExistException
     */
    public MessageExtendActionForm getReceivedMessageData(
            int messageId,
            int recipientAccountId,
            MessageExtendActionForm inForm)
            throws ClassNotFoundException, IOException, SQLException, NoDataExistException {
        // DAOメソッドに引き渡すアクションフォームを生成してメッセージIDを格納する。
        MessageSelectJoinWhereActionForm messageSelectJoinWhereForm = new MessageSelectJoinWhereActionForm();
        messageSelectJoinWhereForm.setMessageId(messageId);
        // メッセージテーブルからメッセージIDに紐づくレコードを取得する。
        List<MessageExtendActionForm> messageExtendFormList
                = new SelectMessageJoinDAO().selectMatchedMessage(messageSelectJoinWhereForm);
        if(messageExtendFormList.isEmpty()) {
            // 取得結果が0件の場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        }

        // 取得したレコードのデータをアクションフォームに格納する。
        inForm.setMessageId(messageId); // メッセージID
        inForm.setReplyMessageId(messageExtendFormList.get(0).getReplyMessageId()); // 返信メッセージID
        inForm.setEntryId(messageExtendFormList.get(0).getEntryId()); // 申込ID
        inForm.setSenderAccountId(messageExtendFormList.get(0).getSenderAccountId()); // 差出アカウントID
        inForm.setSenderLastName(messageExtendFormList.get(0).getSenderLastName()); // 差出アカウント(姓)
        inForm.setSenderFirstName(messageExtendFormList.get(0).getSenderFirstName()); // 差出アカウント(名)
        inForm.setRecipientAccountId(recipientAccountId); // 受取アカウントID
        inForm.setMessageSubject(messageExtendFormList.get(0).getMessageSubject()); // 件名
        inForm.setMessageBody(messageExtendFormList.get(0).getMessageBody()); // 本文

        // 文字列型に変換した送信日時をアクションフォームに格納する。
        inForm.setStrSendedAt(getStrTimestamp(messageExtendFormList.get(0).getSendedAt()));

        // 受信済メッセージのデータを格納したアクションフォームを戻す。
        return inForm;
    }

    /**
     * 送信済メッセージの情報を取得するためのメソッド
     * @param messageId メッセージID
     * @param senderAccountId 差出アカウントID
     * @param inForm メッセージのデータを格納するアクションフォーム
     * @return 検索条件に一致したメッセージのアクションフォーム
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoDataExistException
     */
    public MessageExtendActionForm getSendedMessageData(
            int messageId,
            int senderAccountId,
            MessageExtendActionForm inForm)
            throws ClassNotFoundException, IOException, SQLException, NoDataExistException {
        // DAOメソッドに引き渡すアクションフォームを生成してメッセージIDを格納する。
        MessageSelectJoinWhereActionForm messageSelectJoinWhereForm = new MessageSelectJoinWhereActionForm();
        messageSelectJoinWhereForm.setMessageId(messageId);
        // メッセージテーブルからメッセージIDに紐づくレコードを取得する。
        List<MessageExtendActionForm> messageExtendFormList
                = new SelectMessageJoinDAO().selectMatchedMessage(messageSelectJoinWhereForm);
        if(messageExtendFormList.isEmpty()) {
            // 取得結果が0件の場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        }

        // 取得したレコードのデータをアクションフォームに格納する。
        inForm.setMessageId(messageId); // メッセージID
        inForm.setReplyMessageId(messageExtendFormList.get(0).getReplyMessageId()); // 返信メッセージID
        inForm.setEntryId(messageExtendFormList.get(0).getEntryId()); // 申込ID
        inForm.setSenderAccountId(senderAccountId); // 差出アカウントID
        inForm.setRecipientAccountId(messageExtendFormList.get(0).getRecipientAccountId()); // 受取アカウントID
        inForm.setRecipientLastName(messageExtendFormList.get(0).getRecipientLastName()); // 受取アカウント(姓)
        inForm.setRecipientFirstName(messageExtendFormList.get(0).getRecipientFirstName()); // 受取アカウント(名)
        inForm.setMessageSubject(messageExtendFormList.get(0).getMessageSubject()); // 件名
        inForm.setMessageBody(messageExtendFormList.get(0).getMessageBody()); // 本文

        // 文字列型に変換した送信日時をアクションフォームに格納する。
        inForm.setStrSendedAt(getStrTimestamp(messageExtendFormList.get(0).getSendedAt()));

        // 送信済メッセージのデータを格納したアクションフォームを戻す。
        return inForm;
    }

    /**
     * 日時(文字列型)を取得するためのメソッド
     */
    String getStrTimestamp(Timestamp timstamp) {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(timstamp);
    }

}
