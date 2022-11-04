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
import front.actionform.school.FrontViewDetailedSchoolActionForm;
import model.entry.EntryStatusModel;
import model.favorite.FavoriteStatusModel;
import model.login.LoginStatusModel;
import model.school.SchoolFileModel;
import model.school.SchoolRegistrantModel;
import model.school.SchoolStatusModel;
import util.LogUtil;

public class ViewDetailedSchoolAction extends Action {

    /**
     * スクール詳細 公開画面にフォワードするためのメソッド
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
        FrontViewDetailedSchoolActionForm inForm = (FrontViewDetailedSchoolActionForm) form;

        try {
            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターからスクールID(文字列型)を取得する。
            String strSchoolId = request.getParameter("schoolId");
            if(StringUtils.isEmpty(strSchoolId)) {
                // 取得できない場合

                // セッションからアクションフォームを削除する。
                session.removeAttribute("FrontViewDetailedSchoolActionForm");
                // 該当スクールなし画面にフォワードする。
                return mapping.findForward("unexistence");
            }
            // スクールID(整数型)を取得する。
            int schoolId = Integer.parseInt(strSchoolId);

            if(!new LoginStatusModel().isLogined(session) && !new SchoolStatusModel().isReleased(schoolId)) {
                // 未ログインかつ公開不可の場合

                // セッションからアクションフォームを削除する。
                session.removeAttribute("FrontViewDetailedSchoolActionForm");
                // 非公開画面にフォワードする。
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
                    session.removeAttribute("FrontViewDetailedSchoolActionForm");
                    // 非公開画面にフォワードする。
                    return mapping.findForward("unrelease");
                }

                // 申込済かを判定してアクションフォームに判定結果を格納する。
                inForm.setEntriedFlag(new EntryStatusModel().isEntried(schoolId, accountId));
                //お気に入りに登録済かを判定してアクションフォームに判定結果を格納する。
                inForm.setFavoriteFlag(new FavoriteStatusModel().isFavoriteAdded(accountId, schoolId));
            }

            // スクールIDに紐づくスクールのデータを取得する。
            inForm = (FrontViewDetailedSchoolActionForm) new GetSchoolDataRepack().getSchoolData(schoolId, inForm);

            // 詳細画面画像のパスをリストに格納する。
            inForm.setDetailImageFilePathList(new SchoolFileModel().listDetailImageFilePath(inForm));

            // スクール詳細画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontViewDetailedSchoolActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
