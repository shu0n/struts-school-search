package front.action.message;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

import dao.message.InsertMessageDAO;
import dao.message.sql.MessageInsertDataActionForm;
import front.actionform.message.FrontSendMessageActionForm;
import model.entry.EntryStatusModel;
import model.message.MessageMaillModel;
import util.LogUtil;

public class SendMessageCompleteAction extends LookupDispatchAction {
    @Override
    // 押下するボタンに応じてパラメーターを設定するためのメソッド
    protected Map<String,String> getKeyMethodMap() {
        // マップを生成する。
        Map<String,String> map = new HashMap<String,String>();
        // "修正"ボタンを押下した場合のパラメーターをマップに格納する。
        map.put("button.fix", "fix");
        // "送信"ボタンを押下した場合のパラメーターをマップに格納する。
        map.put("button.send", "send");
        // マップを戻す。
        return map;
    }

    /**
     * メッセージ送信 確認画面で"修正"ボタンを押下した場合に入力画面にフォワードするためのメソッド
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request リクエスト
     * @param response レスポンス
     * @return 遷移先画面の指定
     */
    public ActionForward fix(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {
        if(!isTokenValid(request, true)) {
            // トークンが一致しない場合は入力画面にリダイレクトする。
            return mapping.findForward("invalid");
        }
        // セッションを取得する。
        HttpSession session = request.getSession();

        try {
            // トークンを生成する。
            saveToken(request);
            // 入力画面にフォワードする。
            return mapping.findForward("fix");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // トークンを削除する。
        resetToken(request);
        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontSendMessageActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

    /**
     * メッセージ送信 確認画面で"送信"ボタンを押下した場合に完了画面にフォワードするためのメソッド
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request リクエスト
     * @param response レスポンス
     * @return 遷移先画面の指定
     * @throws Exception
     * */
    public ActionForward send(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        if(!isTokenValid(request, true)) {
            // トークンが一致しない場合は入力画面にリダイレクトする。
            return mapping.findForward("invalid");
        }
        // セッションを取得する。
        HttpSession session = request.getSession();
        // アクションフォームを取得する。
        FrontSendMessageActionForm inForm = (FrontSendMessageActionForm) form;

        try {
            // DAOメソッドに引き渡すアクションフォームを生成して登録するデータを格納する。
            MessageInsertDataActionForm messageInsertDataForm = new MessageInsertDataActionForm();
            messageInsertDataForm.setEntryId(inForm.getEntryId()); // 申込ID
            messageInsertDataForm.setSenderAccountId(inForm.getSenderAccountId()); // 差出アカウントID
            messageInsertDataForm.setRecipientAccountId(inForm.getRecipientAccountId()); // 受取アカウントID
            messageInsertDataForm.setMessageSubject(inForm.getMessageSubject()); // 件名
            messageInsertDataForm.setMessageBody(inForm.getMessageBody()); // 本文
            // メッセージのレコードを作成して申込IDを取得する。
            int messageId = new InsertMessageDAO().insertMessage(messageInsertDataForm);

            // 申込ステータスを"連絡済"に更新する。
            new EntryStatusModel().updateStatusContacted(inForm.getEntryId());

            // メッセージ受信通知メールを送信する。
            new MessageMaillModel().sendMailToRecipient(inForm.getRecipientAccountId(), messageId);

            // セッションからアクションフォームを削除する。
            session.removeAttribute("FrontSendMessageActionForm");
            // 完了画面にフォワードする。
            return mapping.findForward("send");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontSendMessageActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
