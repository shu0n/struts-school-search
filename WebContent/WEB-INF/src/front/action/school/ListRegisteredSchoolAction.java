package front.action.school;

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
import front.actionform.school.FrontListRegisteredSchoolActionForm;
import model.login.LoginStatusModel;
import model.school.SchoolListModel;
import util.ListUtil;
import util.LogUtil;
import util.RedirectUtil;

public class ListRegisteredSchoolAction extends Action {
    int DISPLAYED_RESULT = 20; // 表示件数/ページ

    /**
     * 登録スクール一覧画面にフォワードするためのメソッド
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
        FrontListRegisteredSchoolActionForm inForm = (FrontListRegisteredSchoolActionForm) form;

        try {
            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターから現在のページ(文字列型)を取得する。
            String strCurrentPage = request.getParameter("currentPage");
            // パラメータに現在のページが設定されているかを判定した結果を格納する変数を生成する。
            boolean isPageParamExist = true;
            if(StringUtils.isEmpty(strCurrentPage)) {
                // 取得できない場合は現在のページに"1"を格納する。
                strCurrentPage = "1";
                // 変数にfalseを格納する。
                isPageParamExist = false;
            }
            // 現在のページ(整数型)を取得する。
            int currentPage = Integer.parseInt(strCurrentPage);

            if(!new LoginStatusModel().isLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectUtil().getRedirectLoginAction("/listRegisteredSchool.do");
            }

            // 登録スクール一覧画面へのアクセス時の状態を判定する。
            if(!isPageParamExist || CollectionUtils.isEmpty(inForm.getSchoolExtendFormList())) {
                // パラメーターに現在のページが設定されていない
                // またはアクションフォームに格納されたスクールのリストの中身がNULLまたは空の場合
                // (登録スクール一覧画面の1ページ目または初めてアクセスした場合)

                // DAOメソッドに引き渡すアクションフォームを生成してアカウントIDを格納する。
                SchoolSelectJoinWhereActionForm schoolSelectJoinWhereForm = new SchoolSelectJoinWhereActionForm();
                schoolSelectJoinWhereForm.setRegistrantAccountId(new LoginStatusModel().getAccountId(session));
                // 全てのスクールを格納したリストを取得する。
                List<SchoolExtendActionForm> schoolExtendFormList
                        = new SelectSchoolJoinDAO().selectMatchedSchool(schoolSelectJoinWhereForm);
                // アクションフォームにスクール一覧画面で使用するスクールの情報を格納する。
                inForm.setSchoolExtendFormList(new SchoolListModel().setListSchoolData(schoolExtendFormList));

                // アクションフォームに取得したスクールのアクションフォームの件数を格納する。
                inForm.setTotalForm(schoolExtendFormList.size());
                // アクションフォームに全ページ数のリストを格納する。
                inForm.setTotalPage(new ListUtil().calcTotalPage(schoolExtendFormList.size(), DISPLAYED_RESULT));

                // スクール登録日時の降順で並び替える。
                schoolExtendFormList = new SchoolListModel().sortSchoolExtendFormList(
                        schoolExtendFormList, "byDescendingRegisteredAt");
            }
            // アクションフォームに現在のページを格納する。
            inForm.setCurrentPage(currentPage);
            // アクションフォームに現在のページに表示するアクションフォームのリストを格納する。
            inForm.setDisplayedSchoolList(new SchoolListModel().makeDisplayedSchoolList(
                    inForm.getSchoolExtendFormList(), currentPage, DISPLAYED_RESULT));

            // 登録スクール一覧画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontListRegisteredSchoolActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
