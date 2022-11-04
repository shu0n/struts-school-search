package front.action.entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import dao.entry.repack.GetEntryDataRepack;
import front.actionform.entry.FrontCancelEntryActionForm;
import model.entry.EntryStatusModel;
import model.login.LoginStatusModel;
import util.LogUtil;
import util.RedirectUtil;

public class CancelEntryConfirmAction extends Action {

    /**
     * 申込キャンセル 確認画面にフォワードするためのメソッド
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
        FrontCancelEntryActionForm outForm = new FrontCancelEntryActionForm();

        try {
            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターから申込ID(文字列型)を取得する。
            String strEntryId = request.getParameter("entryId");
            if(StringUtils.isEmpty(strEntryId)) {
                // 取得できない場合は該当申込なしの画面にフォワードする。
                return mapping.findForward("unexistence");
            }
            // 申込ID(整数型)を取得する。
            int entryId = Integer.parseInt(strEntryId);

            if(!new LoginStatusModel().isLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectUtil().getRedirectLoginAction("/viewDetailedEntry.do", "entryId", entryId);
            }

            if(!new EntryStatusModel().isApplicant(entryId, new LoginStatusModel().getAccountId(session))
                    || new EntryStatusModel().isCanceled(entryId)) {
                // アクセスしたアカウントが申込者本人ではないまたは申込ステータスが"キャンセル済"の場合は該当申込なし画面にフォワードする。
                return mapping.findForward("unexistence");
            }

            // 申込の情報を取得する。
            outForm = (FrontCancelEntryActionForm) new GetEntryDataRepack().getEntryData(entryId, outForm);

            // トークンを生成する。
            saveToken(request);
            // セッションにアクションフォームを格納する。
            session.setAttribute("FrontCancelEntryActionForm", outForm);
            // 確認画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
