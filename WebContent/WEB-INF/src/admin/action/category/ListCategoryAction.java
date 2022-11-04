package admin.action.category;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import actionform.CategoryExtendActionForm;
import admin.actionform.category.AdminListCategoryActionForm;
import dao.category.SelectCategoryJoinDAO;
import dao.category.sql.CategorySelectWhereActionForm;
import model.category.CategoryListModel;
import model.login.LoginAdminStatusModel;
import util.ListUtil;
import util.LogUtil;
import util.RedirectAdminUtil;

public class ListCategoryAction extends Action {
    int DISPLAYED_RESULT = 20; // 表示件数/ページ

    /**
     * 管理画面 カテゴリー一覧画面にフォワードするためのメソッド
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
        AdminListCategoryActionForm inForm = (AdminListCategoryActionForm) form;

        try {
            if(!new LoginAdminStatusModel().isAdminLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectAdminUtil().getRedirectAdminLoginAction("/listCategory.do");
            }

            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターから現在のページ(文字列型)を取得する。
            String strCurrentPage = request.getParameter("currentPage");
            // パラメータに現在のページが追加されているかを判定した結果を格納する変数を生成する。
            boolean isPageParamExist = true;
            if(StringUtils.isEmpty(strCurrentPage)) {
                // 取得できない場合は現在のページに"1"を格納する。
                strCurrentPage = "1";
                // 変数にfalseを格納する。
                isPageParamExist = false;
            }
            // 現在のページ(整数型)を取得する。
            int currentPage = Integer.parseInt(strCurrentPage);

            // カテゴリー一覧画面へのアクセス時の状態を判定する。
            if(!isPageParamExist || CollectionUtils.isEmpty(inForm.getCategoryExtendFormList())) {
                // パラメーターに現在のページが設定されていない場合
                // またはアクションフォームに格納されたカテゴリーのリストの中身がNULLまたは空の場合
                // (カテゴリー一覧画面に初めてアクセスした場合)

                // 全てのカテゴリーを格納したリストを取得する。
                List<CategoryExtendActionForm> categoryExtendFormList
                        = new SelectCategoryJoinDAO().selectMatchedCategory(new CategorySelectWhereActionForm());
                // カテゴリー作成日時の降順で並び替える。
                categoryExtendFormList = new CategoryListModel().sortCategoryExtendFormList(
                        categoryExtendFormList, "byDescendingCreatedAt");
                // アクションフォームに取得したリストを格納する。
                inForm.setCategoryExtendFormList(new CategoryListModel().setListCategoryData(categoryExtendFormList));

                // アクションフォームに取得したカテゴリーのアクションフォームの件数を格納する。
                inForm.setTotalForm(categoryExtendFormList.size());
                // アクションフォームに全ページ数を格納する。
                inForm.setTotalPage(new ListUtil().calcTotalPage(categoryExtendFormList.size(), DISPLAYED_RESULT));
            }
            // アクションフォームに現在のページを格納する。
            inForm.setCurrentPage(currentPage);
            // アクションフォームに現在のページに表示するアクションフォームのリストを格納する。
            inForm.setDisplayedCategoryList(new CategoryListModel().makeDisplayedCategoryList(
                    inForm.getCategoryExtendFormList(), currentPage, DISPLAYED_RESULT));

            // カテゴリー一覧画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("AdminListCategoryActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
