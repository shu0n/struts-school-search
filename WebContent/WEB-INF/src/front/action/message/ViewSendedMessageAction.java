package front.action.message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import dao.message.repack.GetMessageDataRepack;
import front.actionform.message.FrontViewSendedMessageActionForm;
import model.login.LoginStatusModel;
import model.message.MessageListModel;
import model.message.MessageStatusModel;
import util.LogUtil;
import util.RedirectUtil;

public class ViewSendedMessageAction extends Action {

    /**
     * 送信メッセージ詳細画面にフォワードするためのメソッド
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request リクエスト
     * @param response レスポンス
     * @return 遷移先画面の指定
     */
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {
        // セッションを取得する。
        HttpSession session = request.getSession();
        // アクションフォームを取得する。
        FrontViewSendedMessageActionForm inForm = (FrontViewSendedMessageActionForm) form;

        try {
            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターからメッセージID(文字列型)を取得する。
            String strMessageId = request.getParameter("messageId");
            if(StringUtils.isEmpty(strMessageId)) {
                // 取得できない場合

                // セッションからアクションフォームを削除する。
                session.removeAttribute("FrontViewSendedMessageActionForm");
                // 該当メッセージなし画面にフォワードする。
                return mapping.findForward("unexistence");
            }
            // メッセージID(整数型)を取得する。
            int messageId = Integer.parseInt(strMessageId);

            if(!new LoginStatusModel().isLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectUtil().getRedirectLoginAction("/viewSendedMessage.do", "messageId", messageId);
            }

            if(!new MessageStatusModel().isSender(messageId, new LoginStatusModel().getAccountId(session))) {
                // アクセスしたアカウントが差出側アカウントではないまたは削除されている場合

                // セッションからアクションフォームを削除する。
                session.removeAttribute("FrontViewSendedMessageActionForm");
                // 該当メッセージなし画面にフォワードする。
                return mapping.findForward("unexistence");
            }

            // 送信済メッセージの情報を取得する。
            inForm = (FrontViewSendedMessageActionForm) new GetMessageDataRepack().getSendedMessageData(
                    messageId, new LoginStatusModel().getAccountId(session), inForm);

            // 返信元メッセージのアクションフォームのリストにNULLを格納する。
            if(inForm.getReplyMessageId() != 0) {
                // 返信メッセージIDが0以外の場合は返信元メッセージのリストを取得してアクションフォームに格納する。
                inForm.setReplySourceMessageList(
                        new MessageListModel().getReplySourceMessageList(inForm.getReplyMessageId()));
            }

            // 送信メッセージ詳細画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontViewSendedMessageActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
