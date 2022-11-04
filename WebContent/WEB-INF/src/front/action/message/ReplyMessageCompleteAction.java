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
import front.actionform.message.FrontReplyMessageActionForm;
import model.message.MessageMaillModel;
import util.LogUtil;

public class ReplyMessageCompleteAction extends LookupDispatchAction {
    @Override
    // 押下するボタンに応じてパラメーターを設定するためのメソッド
    protected Map<String,String> getKeyMethodMap() {
        // マップを生成する。
        Map<String,String> map = new HashMap<String,String>();
        // "修正"ボタンを押下した場合のパラメーターをマップに格納する。
        map.put("button.fix", "fix");
        // "返信"ボタンを押下した場合のパラメーターをマップに格納する。
        map.put("button.reply", "reply");
        // マップを戻す。
        return map;
    }

    /**
     * メッセージ返信 確認画面で"修正"ボタンを押下した場合に入力画面にフォワードするためのメソッド
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

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontReplyMessageActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

    /**
     * メッセージ返信 確認画面で"返信"ボタンを押下した場合に完了画面にフォワードするためのメソッド
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request リクエスト
     * @param response レスポンス
     * @return 遷移先画面の指定
     * @throws Exception
     * */
    public ActionForward reply(
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
        FrontReplyMessageActionForm inForm = (FrontReplyMessageActionForm) form;

        try {
            // DAOメソッドに引き渡すアクションフォームを生成して登録するデータを格納する。
            MessageInsertDataActionForm messageInsertDataForm = new MessageInsertDataActionForm();
            messageInsertDataForm.setReplyMessageId(inForm.getReplyMessageId()); // 返信メッセージID
            messageInsertDataForm.setEntryId(inForm.getEntryId()); // 申込ID
            messageInsertDataForm.setSenderAccountId(inForm.getSenderAccountId()); // 差出アカウントID
            messageInsertDataForm.setRecipientAccountId(inForm.getRecipientAccountId()); // 受取アカウントID
            messageInsertDataForm.setMessageSubject(inForm.getMessageSubject()); // 件名
            messageInsertDataForm.setMessageBody(inForm.getMessageBody()); // 本文
            // メッセージのレコードを作成してメッセージIDを取得する。
            int messageId = new InsertMessageDAO().insertMessage(messageInsertDataForm);

            // メッセージ受信通知メールを送信する。
            new MessageMaillModel().sendMailToRecipient(inForm.getRecipientAccountId(), messageId);

            // セッションからアクションフォームを削除する。
            session.removeAttribute("FrontReplyMessageActionForm");
            // 完了画面にフォワードする。
            return mapping.findForward("reply");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontReplyMessageActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
