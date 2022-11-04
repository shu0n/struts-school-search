package front.action.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import exception.NoDataExistException;
import model.account.AccountStatusModel;
import model.login.LoginStatusModel;
import util.LogUtil;

public class ActivateAccountAction extends Action {

    /**
     * 新規に作成されたアカウントを有効化するためのメソッド
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

        try {
            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターからメールアドレスと有効化トークンを取得する。
            String mailAddress = request.getParameter("mailAddress"); // メールアドレス
            String activateToken = request.getParameter("activateToken"); // 有効化トークン
            if(StringUtils.isEmpty(mailAddress) || StringUtils.isEmpty(activateToken)) {
                // メールアドレスまたは有効化トークンがNULLまたは空文字の場合

                // セッションからアクションフォームを削除する。
                session.removeAttribute("FrontActivateAccountActionForm");
                // 失敗画面にフォワードする。
                return mapping.findForward("fail");
            }

            try {
                // メールアドレスと有効化トークンをもとにアカウントを有効化してアカウントIDを取得する。
                int accountId = new AccountStatusModel().activateAccount(mailAddress, activateToken);

                // アカウントIDをもとにログイン済状態に変更する。
                session = new LoginStatusModel().stateToLogined(accountId, session);
            } catch(IllegalArgumentException | NoDataExistException e) {
                // 不正引数例外またはデータ不存在例外を受け取った場合

                // セッションからアクションフォームを削除する。
                session.removeAttribute("FrontActivateAccountActionForm");
                // エラー画面にフォワードする。
                return mapping.findForward("fail");
            }

            // セッションからアクションフォームを削除する。
            session.removeAttribute("FrontActivateAccountActionForm");
            // 完了画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontActivateAccountActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
