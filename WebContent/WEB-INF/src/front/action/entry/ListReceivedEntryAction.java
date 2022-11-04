package front.action.entry;

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

import actionform.EntryExtendActionForm;
import dao.entry.SelectEntryJoinDAO;
import dao.entry.sql.EntrySelectJoinWhereActionForm;
import dao.school.SelectSchoolDAO;
import dao.school.sql.SchoolSelectWhereActionForm;
import front.actionform.entry.FrontListReceivedEntryActionForm;
import front.actionform.entry.FrontSearchReceivedEntryActionForm;
import model.entry.EntryFormPartsModel;
import model.entry.EntryListModel;
import model.login.LoginStatusModel;
import model.school.SchoolRegistrantModel;
import util.ListUtil;
import util.LogUtil;
import util.RedirectUtil;

public class ListReceivedEntryAction extends Action {
    int DISPLAYED_RESULT = 20; // 表示件数/ページ

    /**
     * 申込受付一覧画面にフォワードするためのメソッド
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
        FrontListReceivedEntryActionForm inForm = (FrontListReceivedEntryActionForm) form;

        try {
            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");

            // リクエストパラメーターからスクールID(文字列型)を取得する。
            String strSchoolId = request.getParameter("schoolId");
            if(StringUtils.isEmpty(strSchoolId) && !inForm.isSortFlag()) {
                // 取得できないかつソートフラグがfalseの場合

                // セッションからアクションフォームを削除する。
                session.removeAttribute("ListReceivedEntryActionForm");
                // 該当申込なしの画面にフォワードする。
                return mapping.findForward("unexistence");
            }

            // スクールIDを格納する変数を生成する。
            int schoolId = 0;
            if(!inForm.isSortFlag()) {
                // ソートフラグがfalseの場合

                // スクールID(文字列型)からスクールID(整数型)を取得する。
                schoolId = Integer.parseInt(strSchoolId);
                // アクションフォームにスクールIDを格納する。
                inForm.setEntrySchoolId(schoolId);
            } else {
                // 上記以外の場合

                // アクションフォームからスクールIDを取得する。
                schoolId = inForm.getEntrySchoolId();
            }

            if(!new LoginStatusModel().isLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectUtil().getRedirectLoginAction("/listReceivedEntry.do", "schoolId", schoolId);
            }

            // リクエストパラメーターから現在のページ(文字列型)を取得する。
            String strCurrentPage = request.getParameter("currentPage");
            // パラメータに現在のページが追加されているかを判定した結果を格納する変数を生成する。
            boolean isPageParamExist = true;
            if(StringUtils.isEmpty(strCurrentPage)) {
                // 取得できない場合は現在のページに"1"を格納する。
                strCurrentPage = "1";

                if(!inForm.isSortFlag()) {
                    // ソートフラグがfalseの場合は変数にfalseを格納する。
                    isPageParamExist = false;
                }
            }
            // 現在のページ(整数型)を取得する。
            int currentPage = Integer.parseInt(strCurrentPage);

            if(!new SchoolRegistrantModel().isRegistrant(schoolId, new LoginStatusModel().getAccountId(session))) {
                // アクセスしたアカウントがスクール登録者本人ではない場合

                // セッションからアクションフォームを削除する。
                session.removeAttribute("FrontListReceivedEntryActionForm");
                // 該当申込なし画面にフォワードする。
                return mapping.findForward("unexistence");
            }

            // 申込受付画面へのアクセス時の状態を判定する。
            if(request.getAttribute("FrontSearchReceivedEntryActionForm") != null
                    || (!isPageParamExist && !inForm.isSortFlag())
                    || CollectionUtils.isEmpty(inForm.getEntryExtendFormList())) {
                // リクエストにSearchReceivedEntryActionFormが追加されている場合
                // パラメーターに現在のページが設定されていないかつソートフラグフラグがfalseの場合
                // またはアクションフォームに格納された申込のリストの中身がNULLまたは空の場合(申込一覧画面に初めてアクセスした場合)

                // DBから取得したアカウントのアクションフォームを格納するためのリストを生成する。
                List<EntryExtendActionForm> entryExtendFormList = new ArrayList<>();
                if(request.getAttribute("FrontSearchReceivedEntryActionForm") != null) {
                    // リクエストにSearchReceivedEntryActionFormが追加されている場合
                    // (検索フォームを利用して申込受付一覧画面にアクセスした場合)

                    // リクエストからSearchReceivedEntryActionFormを取得する。
                    FrontSearchReceivedEntryActionForm frontSearchReceivedEntryForm
                            = (FrontSearchReceivedEntryActionForm) request.getAttribute("FrontSearchReceivedEntryActionForm");
                    // 検索条件に一致した申込のアクションフォームを格納したリストを取得する。
                    entryExtendFormList = frontSearchReceivedEntryForm.getEntryExtendFormList();
                } else {
                    // 上記以外の場合

                    // DAOメソッドに引き渡すアクションフォームを生成してスクールIDを格納する。
                    SchoolSelectWhereActionForm schoolSelectWhereForm = new SchoolSelectWhereActionForm();
                    schoolSelectWhereForm.setSchoolId(schoolId);
                    // アクションフォームにスクール名を格納する。
                    inForm.setEntrySchoolName(
                            new SelectSchoolDAO().selectMatchedSchool(schoolSelectWhereForm).get(0).getSchoolName());
                    // DAOメソッドに引き渡すアクションフォームを生成してスクールIDを格納する。
                    EntrySelectJoinWhereActionForm entryJoinSelectWhereForm = new EntrySelectJoinWhereActionForm();
                    entryJoinSelectWhereForm.setEntrySchoolId(schoolId);
                    // スクールIDに紐づく申込を取得する。
                    entryExtendFormList = new SelectEntryJoinDAO().selectMatchedEntry(entryJoinSelectWhereForm);
                }
                // 申込日時の降順で並び替える。
                entryExtendFormList = new EntryListModel().sortEntryExtendFormList(
                        entryExtendFormList, "byDescendingEntriedAt");
                // アクションフォームに取得したリストを格納する。
                inForm.setEntryExtendFormList(new EntryListModel().setListEntryData(entryExtendFormList));

                // アクションフォームに取得した申込のアクションフォームの件数を格納する。
                inForm.setTotalForm(entryExtendFormList.size());
                // アクションフォームに全ページ数を格納する。
                inForm.setTotalPage(new ListUtil().calcTotalPage(entryExtendFormList.size(), DISPLAYED_RESULT));
                // 申込ステータスリストを取得してアクションフォームに格納する。
                inForm.setEntryStatusList(new EntryFormPartsModel().getEntryStatusListWithoutEmptyValue());
                // 申込ソート種別リストを取得してアクションフォームに格納する。
                inForm.setSortKindForEntryList(new EntryFormPartsModel().getSortKindForEntryListWithEmptyValue());
                // 申込ソート種別にNULLを格納する。
                inForm.setSortKindForEntry(null);
            }

            if(inForm.isSortFlag()) {
                // ソートフラグがtrueの場合

                // リストを申込ソート種別をもとにソートしてアクションフォームに格納する。
                inForm.setEntryExtendFormList(new EntryListModel().sortEntryExtendFormList(
                        inForm.getEntryExtendFormList(), inForm.getSortKindForEntry()));
                // ソートフラグにfalseを格納する。
                inForm.setSortFlag(false);
            }

            // アクションフォームに現在のページを格納する。
            inForm.setCurrentPage(currentPage);
            // アクションフォームに現在のページに表示する申込のリストを格納する。
            inForm.setDisplayedEntryList(new EntryListModel().makeDisplayedEntryList(
                    inForm.getEntryExtendFormList(), currentPage, DISPLAYED_RESULT));

            // 申込受付一覧画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontListReceivedEntryActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
