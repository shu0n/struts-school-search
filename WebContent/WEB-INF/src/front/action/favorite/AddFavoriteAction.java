package front.action.favorite;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import dao.favorite.InsertFavoriteDAO;
import dao.favorite.sql.FavoriteInsertDataActionForm;
import model.login.LoginStatusModel;
import util.LogUtil;
import util.RedirectUtil;

public class AddFavoriteAction extends Action {

    /**
     * 特定のスクールをお気に入りに追加するためのメソッド
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
                return new RedirectUtil().getRedirectLoginAction("/addFavorite.do", "schoolId", schoolId);
            }

            // DAOメソッドに引き渡すアクションフォームを生成して登録するデータを格納する。
            FavoriteInsertDataActionForm favoriteInsertDataForm = new FavoriteInsertDataActionForm();
            favoriteInsertDataForm.setSchoolId(schoolId); // スクールID
            favoriteInsertDataForm.setAccountId(new LoginStatusModel().getAccountId(session)); // アカウントID
            try {
                // スクールをお気に入りに追加する。
                new InsertFavoriteDAO().insertFavorite(favoriteInsertDataForm);
            } catch(SQLIntegrityConstraintViolationException e) {
                // 整合性制約違反例外を受け取った場合

                // セッションからアクションフォームを削除する。
                session.removeAttribute("FrontAddFavoriteActionForm");
                // 該当スクールなし画面にフォワードする。
                return mapping.findForward("unexistence");
            }

            // セッションからアクションフォームを削除する。
            session.removeAttribute("FrontAddFavoriteActionForm");
            // スクール詳細 公開画面にフォワードする。
            return new RedirectUtil().getRedirectAction("/viewDetailedSchool.do", "schoolId", schoolId);
        } catch (Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontAddFavoriteActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
