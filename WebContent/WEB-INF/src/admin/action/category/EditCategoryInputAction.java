package admin.action.category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import admin.actionform.category.AdminEditCategoryActionForm;
import dao.category.repack.GetCategoryDataRepack;
import dao.category.sql.CategorySelectJoinWhereActionForm;
import exception.NoDataExistException;
import model.category.CategoryFormPartsModel;
import model.login.LoginAdminStatusModel;
import util.LogUtil;
import util.RedirectAdminUtil;

public class EditCategoryInputAction extends Action {

    /**
     * 管理画面 カテゴリー編集 入力画面にフォワードするためのメソッド
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
        AdminEditCategoryActionForm outForm = new AdminEditCategoryActionForm();

        try {
            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターからカテゴリーID(文字列型)を取得する。
            String strCategoryId = request.getParameter("categoryId");
            if(StringUtils.isEmpty(strCategoryId)) {
                // カテゴリーIDが取得できない場合は該当カテゴリーなし画面にフォワードする。
                return mapping.findForward("unexistence");
            }
            // カテゴリーID(整数型)を取得する。
            int categoryId = Integer.parseInt(strCategoryId);

            if(!new LoginAdminStatusModel().isAdminLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectAdminUtil().getRedirectAdminLoginAction(
                        "/editCategoryInput.do", "categoryId", strCategoryId);
            }

            // DAOメソッドに引き渡すアクションフォームを生成してカテゴリーIDを格納する。
            CategorySelectJoinWhereActionForm categorySelectJoinWhereForm = new CategorySelectJoinWhereActionForm();
            categorySelectJoinWhereForm.setCategoryId(categoryId);
            try {
                // カテゴリーIDに紐づくカテゴリー情報を取得する。
                outForm = (AdminEditCategoryActionForm)
                        new GetCategoryDataRepack().getCategoryData(categorySelectJoinWhereForm, outForm);
            } catch(NoDataExistException e) {
                // データ不存在例外を受け取った場合は該当カテゴリーなし画面にフォワードする。
                return mapping.findForward("unexistence");
            }

            // アクションフォームにカテゴリーリストを格納する。
            outForm.setSeniorCategoryList(
                    new CategoryFormPartsModel().getCategoryListWithEmptyValueExcludeSelfCategoryGroup(categoryId));
            // アクションフォームにカテゴリーステータスマップを格納する。
            outForm.setCategoryStatusMap(new CategoryFormPartsModel().getCategoryStatusMap());

            // トークンを生成する。
            saveToken(request);
            // アクションフォームをセッションに格納する。
            session.setAttribute("AdminEditCategoryActionForm", outForm);
            // カテゴリー編集 入力画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
