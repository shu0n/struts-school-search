package admin.action.category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import admin.actionform.category.AdminCreateCategoryActionForm;
import model.category.CategoryFormPartsModel;
import model.login.LoginAdminStatusModel;
import util.LogUtil;
import util.RedirectAdminUtil;

public class CreateCategoryInputAction extends Action {

    /**
     * 管理画面 カテゴリー画面にフォワードするためのメソッド
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
        AdminCreateCategoryActionForm outForm = new AdminCreateCategoryActionForm();

        try {
            if(!new LoginAdminStatusModel().isAdminLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectAdminUtil().getRedirectAdminLoginAction("/createCategoryInput.do");
            }

            // アクションフォームにカテゴリーリストを格納する。
            outForm.setSeniorCategoryList(new CategoryFormPartsModel().getCategoryListWithEmptyValue());
            // アクションフォームにカテゴリーステータスマップを格納する。
            outForm.setCategoryStatusMap(new CategoryFormPartsModel().getCategoryStatusMap());
            // カテゴリーステータスに"0"(無効(デフォルト値))を格納する。
            outForm.setCategoryStatus("0");

            // トークンを作成する。
            saveToken(request);
            // セッションにアクションフォームを格納する。
            session.setAttribute("AdminCreateCategoryActionForm", outForm);
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
