package admin.action.account;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import admin.actionform.account.AdminEditAccountActionForm;
import dao.account.repack.GetAccountDataRepack;
import dao.account.sql.AccountSelectJoinWhereActionForm;
import exception.NoDataExistException;
import model.account.AccountFormPartsModel;
import model.login.LoginAdminStatusModel;
import util.FormPartsUtil;
import util.LogUtil;
import util.RedirectAdminUtil;

public class EditAccountInputAction extends Action {

    /**
     * 管理画面 アカウント編集 入力画面にフォワードするためのメソッド
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
        AdminEditAccountActionForm outForm = new AdminEditAccountActionForm();

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
                        "/editAccountInput.do", "accountId", strAccountId);
            }

            // DAOメソッドに引き渡すためのアクションフォームを生成してアカウントIDを格納する。
            AccountSelectJoinWhereActionForm accountSelectJoinWhereForm = new AccountSelectJoinWhereActionForm();
            accountSelectJoinWhereForm.setAccountId(accountId);
            try {
                // アカウントIDに紐づくアカウント情報を取得する。
                outForm = (AdminEditAccountActionForm) new GetAccountDataRepack().getAccountData(
                        accountSelectJoinWhereForm, outForm);
            } catch(NoDataExistException e) {
                // データ不存在例外を受け取った場合は該当アカウントなし画面にフォワードする。
                return mapping.findForward("unexistence");
            }

            // アクションフォームに性別マップを格納する。
            outForm.setSexMap(new AccountFormPartsModel().getSexMap());
            // アクションフォームに生年月日(年)リストを格納する。
            outForm.setBirthYearList(new AccountFormPartsModel().getBirthYearListWithEmptyValue());
            // アクションフォームに生年月日(月)リストを格納する。
            outForm.setBirthMonthList(new AccountFormPartsModel().getBirthMonthListWithEmptyValue());
            // アクションフォームに生年月日(日)リストを格納する。
            outForm.setBirthDayList(new AccountFormPartsModel().getBirthDayListWithEmptyValue());
            // アクションフォームに都道府県マップと都道府県リストを格納する。
            outForm.setPrefectureMap(new FormPartsUtil().getPrefectureMap());
            outForm.setPrefectureList(new FormPartsUtil().getPrefectureListWithEmptyValue());
            // アクションフォームにアカウントステータスマップを格納する。
            outForm.setAccountStatusMap(new AccountFormPartsModel().getAccountStatusMap());
            // レコードに登録されているアカウントステータス名を設定する。
            outForm.setAccountStatusName(outForm.getAccountStatusMap().get(outForm.getAccountStatus()));
            // アカウント作成日時(日時型)をアカウント作成日時(文字列型)に変換してアクションフォームに格納する。
            outForm.setStrAccountCreatedAt(
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(outForm.getAccountCreatedAt()));
            // アカウント更新日時(日時型)をアカウント更新日時(文字列型)に変換してアクションフォームに格納する。
            outForm.setStrAccountUpdatedAt(
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(outForm.getAccountUpdatedAt()));

            // トークンを生成する。
            saveToken(request);
            // アクションフォームをセッションに格納する。
            session.setAttribute("AdminEditAccountActionForm", outForm);
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
