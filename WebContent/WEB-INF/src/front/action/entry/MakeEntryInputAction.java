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
import front.actionform.entry.FrontMakeEntryActionForm;
import model.entry.EntryStatusModel;
import model.login.LoginStatusModel;
import model.school.SchoolStatusModel;
import util.LogUtil;
import util.RedirectUtil;

public class MakeEntryInputAction extends Action {

    /**
     * 申込 入力画面にフォワードするためのメソッド
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
        FrontMakeEntryActionForm outForm = new FrontMakeEntryActionForm();

        try {
            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターからスクールID(文字列型)を取得する。
            String strSchoolId = request.getParameter("schoolId");
            if(StringUtils.isEmpty(strSchoolId)) {
                // 取得できない場合は該当スクールなしの画面にフォワードする。
                return mapping.findForward("unexistence");
            }
            // スクールID(整数型)を取得する。
            int schoolId = Integer.parseInt(strSchoolId);

            if(!new SchoolStatusModel().isEntryEnable(schoolId)) {
                // スクールが申込不可の場合は申込不可画面にフォワードする。
                return mapping.findForward("unentry");
            }

            if(!new LoginStatusModel().isLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectUtil().getRedirectLoginAction("/makeEntryInput.do", "schoolId", schoolId);
            }

            if(new EntryStatusModel().isEntried(schoolId, new LoginStatusModel().getAccountId(session))) {
                // 当該スクールに申込済の場合はスクール詳細 公開画面or非公開画面にリダイレクトする。
                return new RedirectUtil().getRedirectAction("/viewDetailedSchool.do", "schoolId", schoolId);
            }

            // 申込スクールの情報を取得する。
            outForm = (FrontMakeEntryActionForm) new GetEntryDataRepack().getEntrySchoolData(
                    schoolId, new LoginStatusModel().getAccountId(session), outForm);

            // トークンを作成する。
            saveToken(request);
            // セッションにアクションフォームを格納する。
            session.setAttribute("FrontMakeEntryActionForm", outForm);
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
