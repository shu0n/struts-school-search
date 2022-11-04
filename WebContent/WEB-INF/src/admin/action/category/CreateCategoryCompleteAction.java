package admin.action.category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import admin.actionform.category.AdminCreateCategoryActionForm;
import dao.category.InsertCategoryDAO;
import dao.category.sql.CategoryInsertDataActionForm;
import model.category.CategoryStatusModel;
import util.LogUtil;

public class CreateCategoryCompleteAction extends Action {

    /**
     * 管理画面 カテゴリー作成画面で"作成"ボタンを押下した場合にカテゴリー一覧画面へリダイレクトするためのメソッド
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
        if(!isTokenValid(request, true)) {
            // トークンが一致しない場合は入力画面にリダイレクトする。
            return mapping.findForward("invalid");
        }
        // セッションを取得する。
        HttpSession session = request.getSession();
        // アクションフォームを取得する。
        AdminCreateCategoryActionForm inForm = (AdminCreateCategoryActionForm) form;

        try {
            if(new CategoryStatusModel().isCategoryNameExist(inForm.getCategoryName(), 0, true)) {
                // カテゴリー名が登録済の場合

                // アクションメッセージのインスタンスを生成する。
                ActionMessages errors = new ActionMessages();
                // インスタンスにエラーメッセージを格納する。
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("登録済のカテゴリー名です。", false));
                // リクエストにエラーメッセージを格納したインスタンスを追加する。
                saveErrors(request, errors);

                // トークンを作成する。
                saveToken(request);
                // 入力画面にフォワードする。
                return mapping.findForward("redo");
            }

            // DAOメソッドの引き渡すアクションフォームを生成して登録するデータを格納する。
            CategoryInsertDataActionForm categoryInsertDataForm = new CategoryInsertDataActionForm();
            categoryInsertDataForm.setCategoryName(inForm.getCategoryName()); // カテゴリー名
            categoryInsertDataForm.setSeniorCategoryId(inForm.getSeniorCategoryId()); // 上位カテゴリーID
            categoryInsertDataForm.setCategoryStatus(inForm.getCategoryStatus()); // カテゴリーステータス
            // カテゴリーのレコードを作成する。
            new InsertCategoryDAO().insertCategory(categoryInsertDataForm);

            // セッションからアクションフォームを削除する。
            session.removeAttribute("AdminCreateCategoryActionForm");
            // カテゴリー一覧画面にリダイレクトする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("AdminCreateCategoryActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
