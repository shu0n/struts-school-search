package admin.action.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import admin.actionform.account.AdminDeleteAccountActionForm;
import dao.account.repack.GetAccountDataRepack;
import dao.account.sql.AccountSelectJoinWhereActionForm;
import exception.NoDataExistException;
import model.login.LoginAdminStatusModel;
import util.LogUtil;
import util.RedirectAdminUtil;

public class DeleteAccountConfirmAction extends Action {

    /**
     * 管理画面 アカウント削除 確認画面にフォワードするためのメソッド
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
        // アクションフォームを生成する。
        AdminDeleteAccountActionForm outForm = new AdminDeleteAccountActionForm();

        try {
            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターからアカウントID(文字列型)を取得する。
            String strAccountId = request.getParameter("accountId");
            if(StringUtils.isEmpty(strAccountId)) {
                // アカウントIDが取得できない場合は該当アカウントなし画面にフォワードする。
                return mapping.findForward("unexistence");
            }
            // アカウントID(整数型)を取得する。
            int accountId = Integer.parseInt(strAccountId);

            if(!new LoginAdminStatusModel().isAdminLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectAdminUtil().getRedirectAdminLoginAction(
                        "/deleteAccountConfirm.do", "accountId", strAccountId);
            }

            // DAOメソッドに引き渡すためのアクションフォームを生成してアカウントIDを格納する。
            AccountSelectJoinWhereActionForm accountSelectJoinWhereForm = new AccountSelectJoinWhereActionForm();
            accountSelectJoinWhereForm.setAccountId(accountId);
            try {
                // アカウントIDに紐づくアカウント情報を取得する。
                outForm = (AdminDeleteAccountActionForm)
                        new GetAccountDataRepack().getAccountData(accountSelectJoinWhereForm, outForm);
            } catch(NoDataExistException e) {
                // データ不存在例外を受け取った場合は該当アカウントなし画面にフォワードする。
                return mapping.findForward("unexistence");
            }

            // トークンを生成する。
            saveToken(request);
            // アクションフォームをセッションに格納する。
            session.setAttribute("AdminDeleteAccountActionForm", outForm);
            // アカウント編集 入力画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
