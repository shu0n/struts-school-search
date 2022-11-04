package admin.action.school;

import java.util.ArrayList;
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

import actionform.SchoolExtendActionForm;
import admin.actionform.school.AdminListSchoolActionForm;
import admin.actionform.school.AdminSearchSchoolActionForm;
import dao.school.SelectSchoolJoinDAO;
import dao.school.sql.SchoolSelectJoinWhereActionForm;
import model.category.CategoryFormPartsModel;
import model.login.LoginAdminStatusModel;
import model.school.SchoolFormPartsModel;
import model.school.SchoolListModel;
import util.FormPartsUtil;
import util.ListUtil;
import util.LogUtil;
import util.RedirectAdminUtil;

public class ListSchoolAction extends Action {
    int DISPLAYED_RESULT = 20; // 表示件数/ページ

    /**
     * 管理画面 スクール一覧画面にフォワードするためのメソッド
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
        AdminListSchoolActionForm inForm = (AdminListSchoolActionForm) form;

        try {
            if(!new LoginAdminStatusModel().isAdminLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectAdminUtil().getRedirectAdminLoginAction("/listSchool.do");
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

            // スクール一覧画面へのアクセス時の状態を判定する。
            if(request.getAttribute("AdminSearchSchoolActionForm") != null
                    || (!isPageParamExist && !inForm.isSortFlag())
                    || CollectionUtils.isEmpty(inForm.getSchoolExtendFormList())) {
                // リクエストにAdminSearchSchoolActionFormが追加されている場合
                // またはパラメーターに現在のページが追加されていないかつソートフラグがfalseの場合
                // またはアクションフォームに格納されたスクールのリストの中身がNULLまたは空の場合

                // DBから取得したスクールのアクションフォームを格納するためのリストを生成する。
                List<SchoolExtendActionForm> schoolExtendFormList = new ArrayList<>();
                if(request.getAttribute("AdminSearchSchoolActionForm") != null) {
                    // リクエストにAdminSearchSchoolActionFormが追加されている場合
                    // (検索フォームを利用して管理画面 スクール一覧画面にアクセスした場合)

                    // リクエストからAdminSearchSchoolActionFormを取得する。
                    AdminSearchSchoolActionForm adminSearchSchoolForm
                            = (AdminSearchSchoolActionForm) request.getAttribute("AdminSearchSchoolActionForm");
                    // 検索条件に一致したスクールのアクションフォームを格納したリストを取得する。
                    schoolExtendFormList = adminSearchSchoolForm.getSchoolExtendFormList();
                } else {
                    // 上記以外の場合(スクール一覧画面に初めてアクセスした場合)

                    // 全てのスクールのアクションフォームを格納したリストを取得する。
                    schoolExtendFormList =
                            new SelectSchoolJoinDAO().selectMatchedSchool(new SchoolSelectJoinWhereActionForm());
                }
                // スクール登録日時の降順で並び替える。
                schoolExtendFormList = new SchoolListModel().sortSchoolExtendFormList(
                        schoolExtendFormList, "byDescendingRegisteredAt");
                // アクションフォームに取得したリストを格納する。
                inForm.setSchoolExtendFormList(new SchoolListModel().setListSchoolData(schoolExtendFormList));

                // アクションフォームに取得したスクールのアクションフォームの件数を格納する。
                inForm.setTotalForm(schoolExtendFormList.size());
                // アクションフォームに全ページ数を格納する。
                inForm.setTotalPage(new ListUtil().calcTotalPage(schoolExtendFormList.size(), DISPLAYED_RESULT));

                // カテゴリーリストを取得してアクションフォームに格納する。
                inForm.setSchoolCategoryList(new CategoryFormPartsModel().getCategoryListWithoutEmptyValue());
                // 都道府県リストを取得してアクションフォームに格納する。
                inForm.setSchoolPrefectureList(new FormPartsUtil().getPrefectureListWithoutEmptyValue());
                // 公開可否リストを取得してアクションフォームに格納する。
                inForm.setSchoolReleaseProprietyList(new SchoolFormPartsModel().getReleaseProprietyList());
                // 申込リストを取得してアクションフォームに格納する。
                inForm.setSchoolEntryProprietyList(new SchoolFormPartsModel().getEntryProprietyList());
                // スクールソート種別リストを取得してアクションフォームに格納する。
                inForm.setSortKindForSchoolList(new SchoolFormPartsModel().getSortKindForSchoolListWithEmptyValue());
                // falseの場合はスクールソート種別にNULLを格納する。
                inForm.setSortKindForSchool(null);
            }

            if(inForm.isSortFlag()) {
                // ソートフラグがtrueの場合

                // リストをスクールソート種別をもとにソートしてアクションフォームに格納する。
                inForm.setSchoolExtendFormList(new SchoolListModel().sortSchoolExtendFormList(
                        inForm.getSchoolExtendFormList(), inForm.getSortKindForSchool()));
                // ソートフラグにfalseを格納する。
                inForm.setSortFlag(false);
            }

            // アクションフォームに現在のページを格納する。
            inForm.setCurrentPage(currentPage);
            // アクションフォームに現在のページに表示するアクションフォームのリストを格納する。
            inForm.setDisplayedSchoolList(new SchoolListModel().makeDisplayedSchoolList(
                    inForm.getSchoolExtendFormList(), currentPage, DISPLAYED_RESULT));

            // トークンを生成する。
            saveToken(request);
            // スクール一覧画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("AdminListSchoolActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
