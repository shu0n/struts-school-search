package front.action.message;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import actionform.MessageExtendActionForm;
import dao.message.SelectMessageJoinDAO;
import dao.message.sql.MessageSelectJoinWhereActionForm;
import front.actionform.message.FrontListReceivedMessageActionForm;
import model.login.LoginStatusModel;
import model.message.MessageListModel;
import util.ListUtil;
import util.LogUtil;
import util.RedirectUtil;

public class ListReceivedMessageAction extends Action {
    int DISPLAYED_RESULT = 20; // 表示件数/ページ

    /**
     * 受信メッセージ一覧画面にフォワードするためのメソッド
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
        FrontListReceivedMessageActionForm inForm = (FrontListReceivedMessageActionForm) form;

        try {
            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターから現在のページ(文字列型)を取得する。
            String strCurrentPage = request.getParameter("currentPage");
            // パラメーターに現在のページが追加されているかを判定した結果を格納する変数を生成する。
            boolean isPageParamExist = true;
            if(StringUtils.isEmpty(strCurrentPage)) {
                // 取得できない場合は現在のページに"1"を格納する。
                strCurrentPage = "1";
                // 変数にfalse格納する。
                isPageParamExist = false;
            }
            // 現在のページ(整数型)を取得する。
            int currentPage = Integer.parseInt(strCurrentPage);

            if(!new LoginStatusModel().isLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectUtil().getRedirectLoginAction("/listReceivedMessage.do");
            }

            // 受信メッセージ一覧画面へのアクセス時の状態を判定する。
            if(!isPageParamExist || CollectionUtils.isEmpty(inForm.getMessageExtendFormList())) {
                // パラメーターに現在のページが設定されていない場合
                // またはアクションフォームに格納された受信メッセージのリストの中身がNULLまたは空の場合
                // (受信メッセージ一覧画面に初めてアクセスした場合)

                // DAOメソッドに引き渡すアクションフォームを生成して受取側アカウントIDと受取アカウント側削除("0"(未削除))を格納する。
                MessageSelectJoinWhereActionForm messageSelectJoinWhereForm = new MessageSelectJoinWhereActionForm();
                messageSelectJoinWhereForm.setRecipientAccountId(new LoginStatusModel().getAccountId(session));
                messageSelectJoinWhereForm.setRecipientDeleteFlag("0");
                // アカウントIDに紐づく受信メッセージを取得する。
                List<MessageExtendActionForm> messageExtendFormList
                        = new SelectMessageJoinDAO().selectMatchedMessage(messageSelectJoinWhereForm);
                // 送信日時の降順で並び替える。
                messageExtendFormList = new MessageListModel().sortMessageExtendFormList(
                        messageExtendFormList, "byDescendingSendedAt");
                // アクションフォームに取得したリストを格納する。
                inForm.setMessageExtendFormList(new MessageListModel().setListMessageData(messageExtendFormList));

                // アクションフォームに取得したメッセージのアクションフォームの件数を格納する。
                inForm.setTotalForm(messageExtendFormList.size());
                // アクションフォームに全ページ数を格納する。
                inForm.setTotalPage(new ListUtil().calcTotalPage(messageExtendFormList.size(), DISPLAYED_RESULT));
            }
            // アクションフォームに現在のページを格納する。
            inForm.setCurrentPage(currentPage);
            // アクションフォームに現在のページに表示するメッセージのリストを格納する。
            inForm.setDisplayedMessageList(new MessageListModel().makeDisplayedMessageList(
                    inForm.getMessageExtendFormList(), currentPage, DISPLAYED_RESULT));

            // 受信メッセージ一覧画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontListReceivedMessageActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
