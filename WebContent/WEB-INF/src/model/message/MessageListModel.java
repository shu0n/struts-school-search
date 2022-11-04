package model.message;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import actionform.MessageExtendActionForm;
import dao.message.SelectMessageJoinDAO;
import dao.message.sql.MessageSelectJoinWhereActionForm;

public class MessageListModel {

    /**
     * メッセージのアクションフォームを格納したリストを指定したメッセージソート種別でソートするためのメソッド
     * @param messageExtendFormList メッセージのアクションフォームを格納したリスト(ソート前)
     * @param sortKindForMessage メッセージソート種別
     * @return メッセージのアクションフォームを格納したリスト(ソート後)
     */
    public List<MessageExtendActionForm> sortMessageExtendFormList(
            List<MessageExtendActionForm> messageExtendFormList,
            String sortKindForMessage) {
        if("byDescendingSendedAt".equals(sortKindForMessage)) {
            // メッセージソート種別が"送信日時が新しい順"の場合は送信日時の降順でソートする。
            messageExtendFormList.sort(Comparator.comparing(MessageExtendActionForm::getSendedAt).reversed());
        } else if("byAccendingSendedAt".equals(sortKindForMessage)) {
            // メッセージソート種別が"送信日時が古い順"の場合は送信日時の昇順でソートする。
            messageExtendFormList.sort(Comparator.comparing(MessageExtendActionForm::getSendedAt));
        }

        // ソートしたリストを戻す。
        return messageExtendFormList;
    }

    /**
     * 引数の返信メッセージIDを起点とした返信元のメッセージを格納したリストを取得するためのメソッド
     * @param replyMessageId 返信メッセージID
     * @return 返信元メッセージのアクションフォームを格納したリスト
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<MessageExtendActionForm> getReplySourceMessageList(int replyMessageId)
            throws ClassNotFoundException, IOException, SQLException {
        // 返信元メッセージのアクションフォームを格納するリストを生成する。
        List<MessageExtendActionForm> replySourceMessageFormList = new ArrayList<>();
        // DAOのインスタンスを生成する。
        SelectMessageJoinDAO selectMessageDao = new SelectMessageJoinDAO();
        // 返信元メッセージIDが取得できなくなる(最初のメッセージ)まで処理を繰り返す。
        while(replyMessageId != 0) {
            // DAOメソッドに引き渡すアクションフォームを生成して返信メッセージIDを格納する。
            MessageSelectJoinWhereActionForm messageSelectJoinWhereForm = new MessageSelectJoinWhereActionForm();
            messageSelectJoinWhereForm.setMessageId(replyMessageId);
            // メッセージテーブルからメッセージIDに紐づくレコードを取得する。
            MessageExtendActionForm messageExtendForm =
                    selectMessageDao.selectMatchedMessage(messageSelectJoinWhereForm).get(0);
            // 送信日時(タイムスタンプ型)を送信日時(文字列型)に変換してアクションフォームに格納する。
            messageExtendForm.setStrSendedAt(
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(messageExtendForm.getSendedAt()));
            // リストに取得したアクションフォームを追加する。
            replySourceMessageFormList.add(messageExtendForm);
            // 引数の返信メッセージIDに取得したレコードの返信メッセージIDを格納する。
            replyMessageId = messageExtendForm.getReplyMessageId();
        }

        // 送信日時の降順で並び替える。
        replySourceMessageFormList = sortMessageExtendFormList(replySourceMessageFormList, "byDescendingSendedAt");

        // 返信元メッセージのアクションフォームを格納したリストを戻す。
        return replySourceMessageFormList;
    }

    /**
     * アクションフォームにメッセージ一覧機能で使用するスクールの情報を格納するためのメソッド
     * @param messageExtendFormList メッセージのアクションフォームを格納したリスト(編集前)
     * @return メッセージのアクションフォームを格納したリスト(編集後)
     */
    public List<MessageExtendActionForm> setListMessageData(List<MessageExtendActionForm> messageExtendFormList) {
        // リストに格納されたアクションフォームの情報を編集する。
        for(int i = 0; i < messageExtendFormList.size(); i++) {
            // リストからi番目のアクションフォームを取得する。
            MessageExtendActionForm eachForm = messageExtendFormList.get(i);

            // 送信日時(タイムスタンプ型)を送信日時(文字列型)に変換してアクションフォームに格納する。
            eachForm.setStrSendedAt(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(eachForm.getSendedAt()));

            // リストにアクションフォームを追加する。
            messageExtendFormList.set(i, eachForm);
        }

        // アクションフォームを格納したリストを戻す。
        return messageExtendFormList;
    }

    /**
     * 現在のページに表示するメッセージのアクションフォームを格納したリストを生成するためのメソッド
     * @param messageExtendFormList 複数のアクションフォームを格納したリスト
     * @param currentPageNum 現在のページ番号
     * @param displayedResult 表示件数/ページ
     * @return 画面に表示するメッセージのアクションフォームを格納したリスト
     */
    public List<MessageExtendActionForm> makeDisplayedMessageList(
            List<MessageExtendActionForm> messageExtendFormList,
            int currentPageNum,
            int displayedResult) {
        // 表示するアクションフォームを格納するリストを生成する。
        List<MessageExtendActionForm> displayedMessageFormList = new ArrayList<>();
        // 最初に表示するメッセージの順番を算出する。
        int sequence = (currentPageNum - 1) * displayedResult;
        // リストの件数分、処理を繰り返す。
        for(int i = 0; i < messageExtendFormList.size(); i++) {
            if(sequence <= i && displayedMessageFormList.size() < displayedResult) {
                // 最初に表示する順番以上かつリストに格納されたアクションフォームの件数が表示件数に満たない場合は
                // リストにi番目のアクションフォームを追加する。
                displayedMessageFormList.add(messageExtendFormList.get(i));
            }
            // 表示件数分のアクションフォームが格納されているかを判定する。
            if(displayedMessageFormList.size() == displayedResult) {
                // 格納されている場合は繰り返し処理を終了する。
                break;
            }
        }

        // 表示するメッセージのアクションフォームを格納したリストを戻す。
        return displayedMessageFormList;
    }

}
