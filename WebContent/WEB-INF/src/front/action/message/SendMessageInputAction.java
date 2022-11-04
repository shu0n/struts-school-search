package front.action.message;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import actionform.EntryActionForm;
import dao.entry.SelectEntryDAO;
import dao.entry.sql.EntrySelectWhereActionForm;
import dao.message.repack.GetMessageDataRepack;
import front.actionform.message.FrontSendMessageActionForm;
import model.login.LoginStatusModel;
import model.school.SchoolRegistrantModel;
import util.LogUtil;
import util.RedirectUtil;

public class SendMessageInputAction extends Action {

    /**
     * メッセージ送信 入力画面にフォワードするためのメソッド
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
        FrontSendMessageActionForm outForm = new FrontSendMessageActionForm();

        try {
            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターから申込IDを取得する。
            String strEntryId = request.getParameter("entryId");
            // 取得結果を判定する。
            if(StringUtils.isEmpty(strEntryId)) {
                // 取得できない場合は該当申込なし画面にフォワードする。
                return mapping.findForward("unexistence");
            }
            // 申込ID(整数型)を取得する。
            int entryId = Integer.parseInt(strEntryId);

            if(!new LoginStatusModel().isLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectUtil().getRedirectLoginAction("/sendMessageInput.do", "entryId", entryId);
            }

            // DAOメソッドに引き渡すアクションフォームを生成して申込IDを格納する。
            EntrySelectWhereActionForm entrySelectWhereForm = new EntrySelectWhereActionForm();
            entrySelectWhereForm.setEntryId(entryId);
            // スクールテーブルから申込IDに紐づくレコードを取得する。
            List<EntryActionForm> lisEntryFormLis = new SelectEntryDAO().selectMatchedEntry(entrySelectWhereForm);
            if(lisEntryFormLis.isEmpty()) {
                // レコードが取得できない場合は該当申込なし画面にフォワードする。
                return mapping.findForward("unexistence");
            } else if(!new SchoolRegistrantModel().isRegistrant(
                    lisEntryFormLis.get(0).getEntrySchoolId(), new LoginStatusModel().getAccountId(session))) {
                // アクセスしたアカウントがスクール登録者本人ではない場合は該当申込なし画面にフォワードする。
                return mapping.findForward("unexistence");
            }

            outForm = (FrontSendMessageActionForm) new GetMessageDataRepack().getSendMessageData(
                    entryId, new LoginStatusModel().getAccountId(session), outForm);

            // トークンを作成する。
            saveToken(request);
            // セッションにアクションフォームを格納する。
            session.setAttribute("FrontSendMessageActionForm", outForm);
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
