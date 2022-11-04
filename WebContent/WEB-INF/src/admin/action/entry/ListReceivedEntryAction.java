package admin.action.entry;

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
import admin.actionform.entry.AdminListReceivedEntryActionForm;
import admin.actionform.entry.AdminSearchReceivedEntryActionForm;
import dao.entry.SelectEntryJoinDAO;
import dao.entry.sql.EntrySelectJoinWhereActionForm;
import model.entry.EntryFormPartsModel;
import model.entry.EntryListModel;
import model.login.LoginAdminStatusModel;
import util.ListUtil;
import util.LogUtil;
import util.RedirectAdminUtil;

public class ListReceivedEntryAction extends Action {
    int DISPLAYED_RESULT = 20; // 表示件数/ページ

    /**
     * 管理画面 申込受付一覧画面にフォワードするためのメソッド
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
        AdminListReceivedEntryActionForm inForm = (AdminListReceivedEntryActionForm) form;

        try {
            if(!new LoginAdminStatusModel().isAdminLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectAdminUtil().getRedirectAdminLoginAction("/listReceivedEntry.do");
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

            // 申込受付一覧画面へのアクセス時の状態を判定する。
            if(request.getAttribute("AdminSearchReceivedEntryActionForm") != null
                    || (!isPageParamExist && !inForm.isSortFlag())
                    || CollectionUtils.isEmpty(inForm.getEntryExtendFormList())) {
                // リクエストにAdminSearchReceivedEntryActionFormが追加されている場合
                // またはパラメーターに現在のページが追加されていないかつソートフラグがfalseの場合
                // またはアクションフォームに格納されたスクールのリストの中身がNULLまたは空の場合

                // DBから取得したアカウントのアクションフォームを格納するためのリストを生成する。
                List<EntryExtendActionForm> entryExtendFormList = new ArrayList<>();
                if(request.getAttribute("AdminSearchReceivedEntryActionForm") != null) {
                    // リクエストにAdminSearchReceivedEntryActionFormが追加されている場合
                    // (検索フォームを利用して申込受付一覧画面にアクセスした場合)

                    // リクエストからAdminSearchReceivedEntryActionFormを取得する。
                    AdminSearchReceivedEntryActionForm adminSearchReceivedEntryForm
                            = (AdminSearchReceivedEntryActionForm) request.getAttribute(
                                    "AdminSearchReceivedEntryActionForm");
                    // 検索条件に一致した申込のアクションフォームを格納したリストを取得する。
                    entryExtendFormList = adminSearchReceivedEntryForm.getEntryExtendFormList();
                } else {
                    // 上記以外の場合(申込受付一覧画面に初めてアクセスした場合)

                    // 全ての申込のアクションフォームを格納したリストを取得する。
                    entryExtendFormList =
                            new SelectEntryJoinDAO().selectMatchedEntry(new EntrySelectJoinWhereActionForm());
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
                // falseの場合はスクールソート種別にNULLを格納する。
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
        session.removeAttribute("AdminListReceivedEntryActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
