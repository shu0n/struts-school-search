package front.action.school;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import front.actionform.school.FrontRegisterSchoolActionForm;
import model.category.CategoryFormPartsModel;
import model.login.LoginStatusModel;
import model.school.SchoolFormPartsModel;
import util.FormPartsUtil;
import util.LogUtil;
import util.RedirectUtil;

public class RegisterSchoolInputAction extends Action {

    /**
     * スクール登録 入力画面にフォワードするためのメソッド
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
        FrontRegisterSchoolActionForm outForm = new FrontRegisterSchoolActionForm();

        try {
            if(!new LoginStatusModel().isLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectUtil().getRedirectLoginAction("/registerSchoolInput.do");
            }

            // セッションからアカウントIDを取得してアクションフォームに格納する。
            outForm.setRegistrantAccountId(new LoginStatusModel().getAccountId(session));
            // アクションフォームに都道府県マップと都道府県リストを格納する。
            outForm.setSchoolPrefectureMap(new FormPartsUtil().getPrefectureMap());
            outForm.setSchoolPrefectureList(new FormPartsUtil().getPrefectureListWithEmptyValue());
            // アクションフォームにカテゴリーマップとカテゴリーリストを格納する。
            outForm.setSchoolCategoryMap(new CategoryFormPartsModel().getCategoryMap());
            outForm.setSchoolCategoryList(new CategoryFormPartsModel().getCategoryListWithEmptyValue());
            // アクションフォームに公開可否マップを格納する。
            outForm.setSchoolReleaseProprietyMap(new SchoolFormPartsModel().getReleaseProprietyMap());
            // アクションフォームに申込可否マップを格納する。
            outForm.setSchoolEntryProprietyMap(new SchoolFormPartsModel().getEntryProprietyMap());
            // 公開可否に"不可"(デフォルト値)を設定する。
            outForm.setSchoolReleasePropriety("0");
            // 申込可否名に"不可"(デフォルト値)を設定する。
            outForm.setSchoolEntryPropriety("0");

            // トークンを作成する。
            saveToken(request);
            // セッションにアクションフォームを格納する。
            session.setAttribute("FrontRegisterSchoolActionForm", outForm);
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
