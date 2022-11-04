package front.action.school;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import dao.school.repack.GetSchoolDataRepack;
import front.actionform.school.FrontDeleteSchoolActionForm;
import model.login.LoginStatusModel;
import model.school.SchoolRegistrantModel;
import util.LogUtil;
import util.RedirectUtil;

public class DeleteSchoolConfirmAction extends Action {

    /**
     * スクール削除 確認画面にフォワードするためのメソッド
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
        FrontDeleteSchoolActionForm outForm = new FrontDeleteSchoolActionForm();

        try {
            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターからスクールID(文字列型)を取得する。
            String strSchoolId = request.getParameter("schoolId");
            if(StringUtils.isEmpty(strSchoolId)) {
                // 取得できない場合は該当スクールなし画面にフォワードする。
                return mapping.findForward("unexistence");
            }
            // スクールID(整数型)を取得する。
            int schoolId = Integer.parseInt(strSchoolId);

            if(!new LoginStatusModel().isLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectUtil().getRedirectLoginAction("/editSchoolInput.do", "schoolId", schoolId);
            }

            if(!new SchoolRegistrantModel().isRegistrant(schoolId, new LoginStatusModel().getAccountId(session))) {
                // アクセスしたアカウントがスクール登録者本人ではない場合はスクール詳細 公開画面にリダイレクトする。
                return new RedirectUtil().getRedirectAction("/viewDetailedSchool.do", "schoolId", schoolId);
            }

            // スクールIDに紐づくスクールのデータを取得する。
            outForm = (FrontDeleteSchoolActionForm) new GetSchoolDataRepack().getSchoolData(schoolId, outForm);

            // トークンを生成する。
            saveToken(request);
            // アクションフォームをセッションに格納する。
            session.setAttribute("FrontDeleteSchoolActionForm", outForm);
            // スクール削除 確認画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
