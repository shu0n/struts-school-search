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

import admin.actionform.category.AdminEditCategoryActionForm;
import dao.category.UpdateCategoryDAO;
import dao.category.sql.CategoryUpdateDataActionForm;
import exception.ReferredByCategoryException;
import exception.ReferredBySchoolException;
import model.category.CategoryStatusModel;
import util.LogUtil;

public class EditCategoryCompleteAction extends Action {

    /**
     * カテゴリーを編集するためのメソッド
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
        AdminEditCategoryActionForm inForm = (AdminEditCategoryActionForm) form;

        try {
            if(new CategoryStatusModel().isCategoryNameExist(inForm.getCategoryName(), inForm.getCategoryId(), false)) {
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

            // DAOメソッドに引き渡すアクションフォームを生成して更新するデータを格納する。
            CategoryUpdateDataActionForm categoryUpdateDataForm = new CategoryUpdateDataActionForm();
            categoryUpdateDataForm.setCategoryId(inForm.getCategoryId()); // カテゴリーID
            categoryUpdateDataForm.setCategoryName(inForm.getCategoryName()); // カテゴリー名
            if(inForm.getSeniorCategoryId() != 0) {
                // 上位カテゴリーIDが0以外の場合は上位カテゴリーIDを格納する。
                categoryUpdateDataForm.setSeniorCategoryId(inForm.getSeniorCategoryId());
            } else {
                // 上記以外の場合は上位カテゴリーID-NULL設定フラグにtrueを格納する。
                categoryUpdateDataForm.setSeniorCategoryIdToNullFlag(true);
            }
            categoryUpdateDataForm.setCategoryStatus(inForm.getCategoryStatus()); // カテゴリーステータス
            try {
                // カテゴリーのレコードを更新する。
                new UpdateCategoryDAO().updateCategory(categoryUpdateDataForm);
            } catch(ReferredBySchoolException e) {
                // スクール参照例外を受け取った場合

                // アクションメッセージのインスタンスを生成する。
                ActionMessages errors = new ActionMessages();
                // インスタンスにエラーメッセージを格納する。
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                        e.getMessage()
                        + "1件以上のスクールのカテゴリーとして登録されている場合はカテゴリーステータスを無効にできません。",
                        false));
                // リクエストにエラーメッセージを格納したインスタンスを追加する。
                saveErrors(request, errors);

                // カテゴリー編集 入力画面にフォワードする。
                return new ActionForward("/editCategoryInput.do?categoryId=" + inForm.getCategoryId());
            } catch(ReferredByCategoryException e) {
                // カテゴリー参照例外を受け取った場合

                // アクションメッセージのインスタンスを生成する。
                ActionMessages errors = new ActionMessages();
                // インスタンスにエラーメッセージを格納する。
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                        e.getMessage()
                        + "他のカテゴリーの上位カテゴリーに設定されている場合はカテゴリーステータスを無効にできません。",
                        false));
                // リクエストにエラーメッセージを格納したインスタンスを追加する。
                saveErrors(request, errors);

                // カテゴリー編集 入力画面にフォワードする。
                return new ActionForward("/editCategoryInput.do?categoryId=" + inForm.getCategoryId());
            }

            // セッションからアクションフォームを削除する。
            session.removeAttribute("AdminEditCategoryActionForm");
            // カテゴリー画面にリダイレクトする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("AdminEditCategoryActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
