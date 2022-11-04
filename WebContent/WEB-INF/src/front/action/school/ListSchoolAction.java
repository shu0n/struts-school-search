package front.action.school;

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
import dao.school.SelectSchoolJoinDAO;
import dao.school.sql.SchoolSelectJoinWhereActionForm;
import front.actionform.school.FrontListSchoolActionForm;
import front.actionform.school.FrontSearchSchoolActionForm;
import model.category.CategoryFormPartsModel;
import model.school.SchoolFormPartsModel;
import model.school.SchoolListModel;
import util.FormPartsUtil;
import util.ListUtil;
import util.LogUtil;

public class ListSchoolAction extends Action {
    int DISPLAYED_RESULT = 8; // 表示件数/ページ

    /**
     * スクール一覧画面にフォワードするためのメソッド
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
        FrontListSchoolActionForm inForm = (FrontListSchoolActionForm) form;

        try {
            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターから現在のページ(文字列型)を取得する。
            String strCurrentPage = request.getParameter("currentPage");
            // パラメーターに現在のページが設定されているかを判定した結果を格納する変数を生成する。
            boolean isPageParamExist = true;
            if(StringUtils.isEmpty(strCurrentPage)) {
                // 取得できない場合は現在のページに"1"を格納する。
                strCurrentPage = "1";
                // 変数にfalseを格納する。
                isPageParamExist = false;
            }
            // 現在のページ(整数型)を取得する。
            int currentPage = Integer.parseInt(strCurrentPage);
            // リクエストパラメーターからカテゴリーID(文字列型)を取得する。
            String strCategoryId = request.getParameter("categoryId");

            // スクール一覧画面へのアクセス時の状態を判定する。
            if(request.getAttribute("FrontSearchSchoolActionForm") != null
                    || !StringUtils.isEmpty(strCategoryId)
                    || (!isPageParamExist && !inForm.isSortFlag())
                    || CollectionUtils.isEmpty(inForm.getSchoolExtendFormList())) {
                // リクエストにSearchSchoolActionFormが追加されている場合
                // またはパラメーターにカテゴリーIDが追加されている場合
                // またはパラメーターに現在のページが追加されていないかつソートフラグがfalseの場合
                // またはアクションフォームに格納されたスクールのリストの中身がNULLまたは空の場合

                // DBから取得したスクールのアクションフォームを格納するためのリストを生成する。
                List<SchoolExtendActionForm> schoolExtendFormList = new ArrayList<>();
                if(request.getAttribute("FrontSearchSchoolActionForm") != null) {
                    // リクエストにSearchSchoolActionFormが追加されている場合(検索フォームを利用してスクール一覧画面にアクセスした場合)

                    // リクエストからSearchSchoolActionFormを取得する。
                    FrontSearchSchoolActionForm frontSearchSchoolForm
                            = (FrontSearchSchoolActionForm) request.getAttribute("FrontSearchSchoolActionForm");
                    // 検索条件に一致したスクールのアクションフォームを格納したリストを取得する。
                    schoolExtendFormList = frontSearchSchoolForm.getSchoolExtendFormList();
                } else if(!StringUtils.isEmpty(strCategoryId)) {
                    // リクエストパラメーターにカテゴリーID(文字列型)が追加されている場合

                    // DAOメソッドに引き渡すアクションフォームを生成する。
                    SchoolSelectJoinWhereActionForm schoolSelectJoinWhereForm = new SchoolSelectJoinWhereActionForm();
                    // カテゴリーIDを配列に格納する。
                    String[] categoryIdArray = {strCategoryId};
                    // アクションフォームにカテゴリーIDを格納した配列を格納する。
                    schoolSelectJoinWhereForm.setSchoolCategoryIdArray(categoryIdArray);
                    // アクションフォームにスクール公開可否"1"(可)を格納する。
                    schoolSelectJoinWhereForm.setSchoolReleasePropriety("1");
                    // カテゴリーIDに紐づくスクールを格納したリストを取得する。
                    schoolExtendFormList = new SelectSchoolJoinDAO().selectMatchedSchool(schoolSelectJoinWhereForm);
                } else {
                    // 上記以外の場合(スクール一覧画面に初めてアクセスした場合)

                    // DAOメソッドに引き渡すアクションフォームを生成する。
                    SchoolSelectJoinWhereActionForm schoolSelectJoinWhereForm = new SchoolSelectJoinWhereActionForm();
                    // アクションフォームにスクール公開可否"1"(可)を格納する。
                    schoolSelectJoinWhereForm.setSchoolReleasePropriety("1");
                    // 全てのスクールを格納したリストを取得する。
                    schoolExtendFormList =
                            new SelectSchoolJoinDAO().selectMatchedSchool(schoolSelectJoinWhereForm);
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
                // スクールソート種別リストを取得してアクションフォームに格納する。
                inForm.setSortKindForSchoolList(new SchoolFormPartsModel().getSortKindForSchoolListWithEmptyValue());
                // falseの場合はスクールソート種別にNULLを格納する。
                inForm.setSortKindForSchool(null);
            }

            if(inForm.isSortFlag()) {
                // ソートフラグがtrueの場合

                //リストをスクールソート種別をもとにソートしてアクションフォームに格納する。
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

            // スクール一覧画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontListSchoolActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
