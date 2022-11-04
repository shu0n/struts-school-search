package admin.action.category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import admin.actionform.category.AdminDeleteCategoryActionForm;
import dao.category.DeleteCategoryDAO;
import exception.ReferredByCategoryException;
import exception.ReferredBySchoolException;
import model.login.LoginAdminStatusModel;
import util.LogUtil;
import util.RedirectAdminUtil;

public class DeleteCategoryAction extends Action {

    /**
     * カテゴリーを削除するためのメソッド
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
        AdminDeleteCategoryActionForm inForm = (AdminDeleteCategoryActionForm) form;

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

            try {
                // カテゴリーを論理削除する。
                new DeleteCategoryDAO().deleteCategoryLogically(categoryId);
            } catch(ReferredBySchoolException | ReferredByCategoryException e) {
                // スクール参照例外またはカテゴリー参照例外を受け取った場合

                // アクションメッセージのインスタンスを生成する。
                ActionMessages errors = new ActionMessages();
                // インスタンスにエラーメッセージを格納する。
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
                // リクエストにエラーメッセージを格納したインスタンスを追加する。
                saveErrors(request, errors);

                // カテゴリー編集画面にフォワードする。
                return new ActionForward("/editCategoryInput.do?categoryId=" + inForm.getCategoryId());
            }

            // セッションからアクションフォームを削除する。
            session.removeAttribute("AdminDeleteCategoryActionForm");
            // カテゴリー一覧画面にリダイレクトする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("AdminDeleteCategoryActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
