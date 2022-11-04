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
import front.actionform.entry.FrontViewDetailedEntryActionForm;
import model.entry.EntryStatusModel;
import model.login.LoginStatusModel;
import util.LogUtil;
import util.RedirectUtil;

public class ViewDetailedEntryAction extends Action {

    /**
     * 申込詳細画面にフォワードするためのメソッド
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
        FrontViewDetailedEntryActionForm inForm = (FrontViewDetailedEntryActionForm) form;

        try {
            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターから申込ID(文字列型)を取得する。
            String strEntryId = request.getParameter("entryId");
            if(StringUtils.isEmpty(strEntryId)) {
                // 取得できない場合

                // セッションからアカウションフォームを削除する。
                session.removeAttribute("FrontViewDetailedEntryActionForm");
                // 該当申込なしの画面にフォワードする。
                return mapping.findForward("unexistence");
            }
            // 申込ID(整数型)を取得する。
            int entryId = Integer.parseInt(strEntryId);

            if(!new LoginStatusModel().isLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectUtil().getRedirectLoginAction("/viewDetailedEntry.do", "entryId", entryId);
            }

            if(!new EntryStatusModel().isApplicant(entryId, new LoginStatusModel().getAccountId(session))) {
                // アクセスしたアカウントが申込者本人ではない場合

                // セッションからアカウションフォームを削除する。
                session.removeAttribute("FrontViewDetailedEntryActionForm");
                // 該当申込なし画面にフォワードする。
                return mapping.findForward("unexistence");
            }

            // 申込の情報を取得する。
            inForm = (FrontViewDetailedEntryActionForm) new GetEntryDataRepack().getEntryData(entryId, inForm);

            // 上記以外の場合は申込詳細画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアカウションフォームを削除する。
        session.removeAttribute("FrontViewDetailedEntryActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
