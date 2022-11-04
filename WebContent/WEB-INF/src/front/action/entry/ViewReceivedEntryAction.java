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
import exception.NoDataExistException;
import front.actionform.entry.FrontViewReceivedEntryActionForm;
import model.entry.EntryFormPartsModel;
import model.login.LoginStatusModel;
import model.school.SchoolRegistrantModel;
import util.LogUtil;
import util.RedirectUtil;

public class ViewReceivedEntryAction extends Action {

    /**
     * 申込受付詳細画面にフォワードするためのメソッド
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
        FrontViewReceivedEntryActionForm inForm = (FrontViewReceivedEntryActionForm) form;

        try {
            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターから申込ID(文字列型)を取得する。
            String strEntryId = request.getParameter("entryId");
            if(StringUtils.isEmpty(strEntryId)) {
                // 取得できない場合

                // セッションからアカウションフォームを削除する。
                session.removeAttribute("FrontViewReceivedEntryActionForm");
                // 該当申込なしの画面にフォワードする。
                return mapping.findForward("unexistence");
            }
            // 申込ID(整数型)を取得する。
            int entryId = Integer.parseInt(strEntryId);

            if(!new LoginStatusModel().isLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectUtil().getRedirectLoginAction("/viewReceivedEntry.do", "entryId", entryId);
            }

            try {
                // 申込の情報を取得する。
                inForm = (FrontViewReceivedEntryActionForm) new GetEntryDataRepack().getEntryData(entryId, inForm);
            } catch(NoDataExistException e) {
                // データ不存在例外を受け取った場合

                // セッションからアカウションフォームを削除する。
                session.removeAttribute("FrontViewReceivedEntryActionForm");
                // 該当申込なし画面にフォワードする。
                return mapping.findForward("unexistence");
            }

            if(!new SchoolRegistrantModel().isRegistrant(
                    inForm.getEntrySchoolId(), new LoginStatusModel().getAccountId(session))) {
                // アクセスしたアカウントがスクール登録者アカウント本人ではない場合

                // セッションからアカウションフォームを削除する。
                session.removeAttribute("FrontViewReceivedEntryActionForm");
                //該当申込なし画面にフォワードする。
                return mapping.findForward("unexistence");
            }

            // 申込者アカウントの情報を取得する。
            inForm = (FrontViewReceivedEntryActionForm) new GetEntryDataRepack().getApplicantAccountData(
                    inForm.getApplicantAccountId(), inForm);

            // アクションフォームに申込ステータスリストを格納する。
            inForm.setEntryStatusList(new EntryFormPartsModel().getEntryStatusListWithEmptyValue());

            // トークンを作成する。
            saveToken(request);
            // 申込受付詳細画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアカウションフォームを削除する。
        session.removeAttribute("FrontViewReceivedEntryActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
