package front.action.favorite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import dao.favorite.DeleteFavoriteDAO;
import model.login.LoginStatusModel;
import util.LogUtil;
import util.RedirectUtil;

public class DeleteFavoriteAction extends Action {

    /**
     * 特定のスクールをお気に入りから削除するためのメソッド
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
                return new RedirectUtil().getRedirectLoginAction("/deleteFavorite.do", "schoolId", schoolId);
            }

            // スクールをお気に入りから削除する。
            new DeleteFavoriteDAO().deleteFavorite(Integer.parseInt(
                    String.valueOf(new LoginStatusModel().getAccountId(session)) + String.valueOf(schoolId)));

            // セッションからアクションフォームを削除する。
            session.removeAttribute("FrontDeleteFavoriteActionForm");
            // スクール詳細 公開画面にフォワードする。
            return new RedirectUtil().getRedirectAction("/viewDetailedSchool.do", "schoolId", schoolId);
        } catch (Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontDeleteFavoriteActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
