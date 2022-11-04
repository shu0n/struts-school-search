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
import front.actionform.school.FrontViewRegistrantProfileActionForm;
import model.login.LoginStatusModel;
import model.school.SchoolRegistrantModel;
import model.school.SchoolStatusModel;
import util.LogUtil;

public class ViewRegistrantProfileAction extends Action {

    /**
     * スクール登録者プロフィール画面にフォワードするためのメソッド
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
        FrontViewRegistrantProfileActionForm inForm = (FrontViewRegistrantProfileActionForm) form;

        try {
            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターからスクールID(文字列型)を取得する。
            String strSchoolId = request.getParameter("schoolId");
            if(StringUtils.isEmpty(strSchoolId)) {
                // 取得できない場合

                // セッションからアクションフォームを削除する。
                session.removeAttribute("FrontViewRegistrantProfileActionForm");
                // 該当スクールなし画面にフォワードする。
                return mapping.findForward("unexistence");
            }
            // スクールID(整数型)を取得する。
            int schoolId = Integer.parseInt(strSchoolId);

            if(!new LoginStatusModel().isLogined(session) && !new SchoolStatusModel().isReleased(schoolId)) {
                // 未ログインかつ公開不可の場合

                // セッションからアクションフォームを削除する。
                session.removeAttribute("FrontViewRegistrantProfileActionForm");
                //非公開画面にフォワードする。
                return mapping.findForward("unrelease");
            }

            if(new LoginStatusModel().isLogined(session)) {
                // ログイン済の場合

                // アカウントIDを取得する。
                int accountId = new LoginStatusModel().getAccountId(session);

                if(!new SchoolRegistrantModel().isRegistrant(schoolId, accountId)
                        && !new SchoolStatusModel().isReleased(schoolId)) {
                    // アクセスしたアカウントがスクール登録者本人ではないかつ公開不可の場合

                    // セッションからアクションフォームを削除する。
                    session.removeAttribute("FrontViewRegistrantProfileActionForm");
                    //非公開画面にフォワードする。
                    return mapping.findForward("unrelease");
                }
            }

            // スクール登録者のプロフィール情報を取得する。
            inForm = (FrontViewRegistrantProfileActionForm) new GetSchoolDataRepack().getRegistrantProfile(
                    schoolId, inForm);

            // スクール登録者プロフィール画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontViewRegistrantProfileActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
