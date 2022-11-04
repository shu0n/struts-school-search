package front.action.entry;

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
import front.actionform.entry.FrontListEntryActionForm;
import model.entry.EntryListModel;
import model.login.LoginStatusModel;
import util.ListUtil;
import util.LogUtil;
import util.RedirectUtil;

public class ListEntryAction extends Action {
    int DISPLAYED_RESULT = 20; // 表示件数/ページ

    /**
     * 申込一覧画面にフォワードするためのメソッド
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
        FrontListEntryActionForm inForm = (FrontListEntryActionForm) form;

        try {
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

            if(!new LoginStatusModel().isLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectUtil().getRedirectLoginAction("/listEntry.do");
            }

            // 申込一覧画面へのアクセス時の状態を判定する。
            if(!isPageParamExist || CollectionUtils.isEmpty(inForm.getEntryExtendFormList())) {
                // パラメーターに現在のページが設定されていない場合
                // またはアクションフォームに格納された申込のリストの中身がNULLまたは空の場合(申込一覧画面に初めてアクセスした場合)

                // DAOメソッドに引き渡すアクションフォームを生成してアカウントIDを格納する。
                EntrySelectJoinWhereActionForm entryJoinSelectWhereForm = new EntrySelectJoinWhereActionForm();
                entryJoinSelectWhereForm.setApplicantAccountId(new LoginStatusModel().getAccountId(session));
                // アカウントIDに紐づく申込を取得する。
                List<EntryExtendActionForm> entryExtendFormList =
                        new SelectEntryJoinDAO().selectMatchedEntry(entryJoinSelectWhereForm);
                // 申込日時の降順で並び替える。
                entryExtendFormList = new EntryListModel().sortEntryExtendFormList(
                        entryExtendFormList, "byDescendingEntriedAt");
                // アクションフォームに取得したリストを格納する。
                inForm.setEntryExtendFormList(new EntryListModel().setListEntryData(entryExtendFormList));

                // アクションフォームに取得した申込のアクションフォームの件数を格納する。
                inForm.setTotalForm(entryExtendFormList.size());
                // アクションフォームに全ページ数を格納する。
                inForm.setTotalPage(new ListUtil().calcTotalPage(entryExtendFormList.size(), DISPLAYED_RESULT));
            }
            // アクションフォームに現在のページを格納する。
            inForm.setCurrentPage(currentPage);
            // アクションフォームに現在のページに表示する申込のリストを格納する。
            inForm.setDisplayedEntryList(new EntryListModel().makeDisplayedEntryList(
                    inForm.getEntryExtendFormList(), currentPage, DISPLAYED_RESULT));

            // 申込一覧画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontListEntryActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
