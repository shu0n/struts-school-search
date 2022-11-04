package admin.action.account;

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

import actionform.AccountExtendActionForm;
import admin.actionform.account.AdminListAccountActionForm;
import admin.actionform.account.AdminSearchAccountActionForm;
import dao.account.SelectAccountJoinDAO;
import dao.account.sql.AccountSelectJoinWhereActionForm;
import model.account.AccountFormPartsModel;
import model.account.AccountListModel;
import model.login.LoginAdminStatusModel;
import util.FormPartsUtil;
import util.ListUtil;
import util.LogUtil;
import util.RedirectAdminUtil;

public class ListAccountAction extends Action {
    int DISPLAYED_RESULT = 20; // 表示件数/ページ

    /**
     * 管理画面 アカウント一覧画面にフォワードするためのメソッド
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
        AdminListAccountActionForm inForm = (AdminListAccountActionForm) form;

        try {
            if(!new LoginAdminStatusModel().isAdminLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectAdminUtil().getRedirectAdminLoginAction("/listAccount.do");
            }

            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターから現在のページ(文字列型)を取得する。
            String strCurrentPage = request.getParameter("currentPage");
            // パラメーターに現在のページが追加されているかを判定した結果を格納する変数を生成する。
            boolean isPageParamExist = true;
            if(StringUtils.isEmpty(strCurrentPage)) {
                // 取得できない場合は現在のページに"1"を格納する。
                strCurrentPage = "1";
                // 変数にfalseを格納する。
                isPageParamExist = false;
            }
            // 現在のページ(整数型)を取得する。
            int currentPage = Integer.parseInt(strCurrentPage);

            // アカウント一覧画面へのアクセス時の状態を判定する。
            if(request.getAttribute("AdminSearchAccountActionForm") != null
                    || (!isPageParamExist && !inForm.isSortFlag())
                    || CollectionUtils.isEmpty(inForm.getAccountExtendFormList())) {
                // リクエストにAdminSearchAccountActionFormが追加されている場合
                // またはパラメーターに現在のページが追加されていないかつソートフラグがfalseの場合
                // またはアクションフォームに格納されたスクールのリストの中身がNULLまたは空の場合

                // DBから取得したアカウントのアクションフォームを格納するためのリストを生成する。
                List<AccountExtendActionForm> accountExtendFormList = new ArrayList<>();
                if(request.getAttribute("AdminSearchAccountActionForm") != null) {
                    // リクエストにAdminSearchAccountActionFormが追加されている場合
                    // (検索フォームを利用して管理画面 アカウント一覧画面にアクセスした場合)

                    // リクエストからAdminSearchAccountActionFormを取得する。
                    AdminSearchAccountActionForm adminSearchAccountForm
                            = (AdminSearchAccountActionForm) request.getAttribute("AdminSearchAccountActionForm");
                    // 検索条件に一致したアカウントのアクションフォームを格納したリストを取得する。
                    accountExtendFormList = adminSearchAccountForm.getAccountExtendFormList();
                } else {
                    // 上記以外の場合(管理画面 アカウント一覧画面に初めてアクセスした場合)

                    // 全てのアカウントのアクションフォームを格納したリストを取得する。
                    accountExtendFormList
                            = new SelectAccountJoinDAO().selectMatchedAccount(new AccountSelectJoinWhereActionForm());
                }
                // アカウント作成日時の降順で並び替える。
                accountExtendFormList = new AccountListModel().sortAccountExtendFormList(
                        accountExtendFormList, "byDescendingCreatedAt");
                // アクションフォームに取得したリストを格納する。
                inForm.setAccountExtendFormList(new AccountListModel().setListAccountData(accountExtendFormList));

                // アクションフォームに取得したアカウントのアクションフォームの件数を格納する。
                inForm.setTotalForm(accountExtendFormList.size());
                // アクションフォームに全ページ数を格納する。
                inForm.setTotalPage(new ListUtil().calcTotalPage(accountExtendFormList.size(), DISPLAYED_RESULT));

                // 性別リストを取得してアクションフォームに格納する。
                inForm.setSexList(new AccountFormPartsModel().getSexListWithoutEmptyValue());
                // 都道府県リストを取得してアクションフォームに格納する。
                inForm.setPrefectureList(new FormPartsUtil().getPrefectureListWithoutEmptyValue());
                // アカウントステータスリストを取得してアクションフォームに格納する。
                inForm.setAccountStatusList(new AccountFormPartsModel().getAccountStatusList());
                // アカウントソート種別リストを取得してアクションフォームに格納する。
                inForm.setSortKindForAccountList(
                        new AccountFormPartsModel().getSortKindForAccountListWithEmptyValue());
                // falseの場合はアカウントソート種別にNULLを格納する。
                inForm.setSortKindForAccount(null);
            }

            if(inForm.isSortFlag()) {
                // ソートフラグがtrueの場合

                // リストをアカウントソート種別をもとにソートしてアクションフォームに格納する。
                inForm.setAccountExtendFormList(new AccountListModel().sortAccountExtendFormList(
                        inForm.getAccountExtendFormList(), inForm.getSortKindForAccount()));
                // ソートフラグにfalseを格納する。
                inForm.setSortFlag(false);
            }

            // アクションフォームに現在のページを格納する。
            inForm.setCurrentPage(currentPage);
            // アクションフォームに現在のページに表示するアクションフォームのリストを格納する。
            inForm.setDisplayedAccountList(new AccountListModel().makeDisplayedAccountList(
                    inForm.getAccountExtendFormList(), currentPage, DISPLAYED_RESULT));

            // アカウント一覧画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("AdminListAccountActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
