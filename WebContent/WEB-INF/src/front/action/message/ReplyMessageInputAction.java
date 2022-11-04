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
import front.actionform.message.FrontReplyMessageActionForm;
import model.login.LoginStatusModel;
import model.message.MessageStatusModel;
import util.LogUtil;
import util.RedirectUtil;

public class ReplyMessageInputAction extends Action {

    /**
     * メッセージ返信 入力画面にフォワードするためのメソッド
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
        // アクションフォームを生成する。
        FrontReplyMessageActionForm outForm = new FrontReplyMessageActionForm();

        try {
            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストからメッセージID(文字列型)を取得する。
            String strMessageId = request.getParameter("messageId");
            if(StringUtils.isEmpty(strMessageId)) {
                // 取得できない場合は該当メッセージなし画面にフォワードする。
                return mapping.findForward("unexistence");
            }
            // メッセージID(整数型)を取得する。
            int messageId = Integer.parseInt(strMessageId);

            if(!new LoginStatusModel().isLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectUtil().getRedirectLoginAction("/viewReceivedMessage.do", "messageId", messageId);
            }

            if(!new MessageStatusModel().isRecipient(messageId, new LoginStatusModel().getAccountId(session))) {
                // アクセスしたアカウントが受取側アカウントではないまたは削除されている場合は該当メッセージなし画面にフォワードする。
                return mapping.findForward("unexistence");
            }

            // メッセージの返信に必要な情報を取得する。
            outForm = (FrontReplyMessageActionForm) new GetMessageDataRepack().getReplyMessageData(
                    messageId, new LoginStatusModel().getAccountId(session), outForm);

            // トークンを作成する。
            saveToken(request);
            // セッションにアクションフォームを格納する。
            session.setAttribute("FrontReplyMessageActionForm", outForm);
            // 入力画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // 正常に処理できなかった場合はエラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
