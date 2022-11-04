package admin.action.school;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import admin.actionform.school.AdminRegisterSchoolActionForm;
import model.account.AccountFormPartsModel;
import model.category.CategoryFormPartsModel;
import model.login.LoginAdminStatusModel;
import model.school.SchoolFormPartsModel;
import util.FormPartsUtil;
import util.LogUtil;
import util.RedirectAdminUtil;

public class RegisterSchoolInputAction extends Action {

    /**
     * 管理画面 スクール登録 入力画面にフォワードするためのメソッド
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
        AdminRegisterSchoolActionForm outForm = new AdminRegisterSchoolActionForm();

        try {
         // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターからアカウントID(文字列型)を取得する。
            String strAccountId = request.getParameter("accountId");

            if(!new LoginAdminStatusModel().isAdminLogined(session) && !StringUtils.isEmpty(strAccountId)) {
                // 未ログインかつアカウントIDが取得できた場合は
                // リダイレクトURLのパラメーターにアカウントIDを追加してログイン画面にリダイレクトする。
                return new RedirectAdminUtil().getRedirectAdminLoginAction(
                        "/registerSchoolInput.do?accountId=" + strAccountId);
            } else if(!new LoginAdminStatusModel().isAdminLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectAdminUtil().getRedirectAdminLoginAction("/registerSchoolInput.do");
            }

            // アクションフォームに登録者種別マップを格納する。
            outForm.setRegistrantKindMap(new SchoolFormPartsModel().getRegistrantKindMap());
            if(!StringUtils.isEmpty(strAccountId)) {
                // アカウントIDが取得できた場合はアクションフォームにアカウントID(文字列型)と登録者種別"account"を格納する。
                outForm.setStrRegistrantAccountId(strAccountId);
                outForm.setRegistrantKind("account");
            } else {
                // 上記以外の場合はアクションフォームに登録者種別"admin"を格納する。
                outForm.setRegistrantKind("admin");
            }

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
            // 公開可否名に"0"(不可(デフォルト値))を設定する。
            outForm.setSchoolReleasePropriety("0");
            // 申込可否名に"0"(不可(デフォルト値))を設定する。
            outForm.setSchoolEntryPropriety("0");
            // アクションフォームにアカウントIDのリストを格納する。
            outForm.setRegistrantAccountIdList(new AccountFormPartsModel().getAccountIdListWithEmptyValue());

            // トークンを作成する。
            saveToken(request);
            // セッションにアクションフォームを格納する。
            session.setAttribute("AdminRegisterSchoolActionForm", outForm);
            // 入力画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
