package front.action.contact;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import actionform.AccountActionForm;
import dao.account.SelectAccountDAO;
import dao.account.sql.AccountSelectWhereActionForm;
import exception.NoDataExistException;
import front.actionform.contact.FrontMakeContactActionForm;
import model.login.LoginStatusModel;
import util.LogUtil;

public class MakeContactInputAction extends Action {

    /**
     * お問合せ 入力画面にフォワードするためのメソッド
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
        FrontMakeContactActionForm outForm = new FrontMakeContactActionForm();

        try {
            // アカウントIDを格納する変数を生成する。
            int accountId = 0;
            try {
                // アカウントIDを取得して変数に格納する。
                accountId = new LoginStatusModel().getAccountId(session);
            } catch(NoDataExistException e) {
                // データ不存在例外を受け取った場合

                // トークンを作成する。
                saveToken(request);
                // セッションにアクションフォームを格納する。
                session.setAttribute("FrontMakeContactActionForm", outForm);
                // 入力画面にフォワードする。
                return mapping.findForward("success");
            }
            // DAOメソッドに引き渡すアクションフォームを生成してアカウントIDを格納する。
            AccountSelectWhereActionForm accountSelectWhereForm = new AccountSelectWhereActionForm();
            accountSelectWhereForm.setAccountId(accountId);
            // アカウントのデータを取得する。
            AccountActionForm accountForm
                    = new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm).get(0);
            // アクションフォームにアカウントのデータを格納する。
            outForm.setContactAccountId(accountId); // お問合せ者アカウントID
            outForm.setContactLastName(accountForm.getLastName()); // お問合せ者姓
            outForm.setContactFirstName(accountForm.getFirstName()); // お問合せ者名
            outForm.setContactLastNameKana(accountForm.getLastNameKana()); // お問合せ者姓(フリガナ)
            outForm.setContactFirstNameKana(accountForm.getFirstNameKana()); // お問合せ者名(フリガナ)
            outForm.setContactMailAddress(accountForm.getMailAddress()); // お問合せ者メールアドレス

            // トークンを作成する。
            saveToken(request);
            // セッションにアクションフォームを格納する。
            session.setAttribute("FrontMakeContactActionForm", outForm);
            // 入力画面にフォワードする。
            return mapping.findForward("success");
        } catch (Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
