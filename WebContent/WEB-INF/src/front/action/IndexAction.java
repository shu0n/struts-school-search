package front.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import actionform.SchoolExtendActionForm;
import dao.school.SelectSchoolJoinDAO;
import dao.school.sql.SchoolSelectJoinWhereActionForm;
import front.actionform.FrontIndexActionForm;
import model.school.SchoolListModel;
import util.LogUtil;

public class IndexAction extends Action {

    /**
     * トップ画面にフォワードするためのメソッド
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
        FrontIndexActionForm inForm = (FrontIndexActionForm) form;

        try {
            // DAOメソッドに引き渡すアクションフォームを生成する。
            SchoolSelectJoinWhereActionForm schoolSelectJoinWhereForm = new SchoolSelectJoinWhereActionForm();
            // アクションフォームにスクール公開可否"1"(可)を格納する。
            schoolSelectJoinWhereForm.setSchoolReleasePropriety("1");
            // スクールを取得する。
            List<SchoolExtendActionForm> schoolExtendFormList
                    = new SelectSchoolJoinDAO().selectMatchedSchool(schoolSelectJoinWhereForm);
            // スクール登録日時の降順で並び替える。
            schoolExtendFormList = new SchoolListModel().sortSchoolExtendFormList(
                    schoolExtendFormList, "byDescendingRegisteredAt");
            // アクションフォームに取得したリストを格納する。
            inForm.setSchoolExtendFormList(schoolExtendFormList);
            // アクションフォームにトップ画面に表示する8件のアクションフォームのリストを格納する。
            inForm.setDisplayedSchoolList(new SchoolListModel().makeDisplayedSchoolList(schoolExtendFormList, 1, 8));

            // トップ画面にフォワードする。
            return mapping.findForward("success");
        } catch (Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("IndexActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
