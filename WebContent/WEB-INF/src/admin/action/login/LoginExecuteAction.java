package admin.action.login;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import actionform.AdminActionForm;
import admin.actionform.login.AdminLoginExecuteActionForm;
import dao.admin.SelectAdminDAO;
import dao.admin.sql.AdminSelectWhereActionForm;
import exception.NoDataExistException;
import model.login.LoginAdminStatusModel;
import util.LogUtil;
import util.PasswordUtil;
import util.RedirectAdminUtil;

public class LoginExecuteAction extends Action {

    /**
     * ログイン処理を実行して実行結果に合わせた画面にフォワードするためのメソッド
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
        if(!isTokenValid(request, true)) {
            // トークンが一致しない場合はログイン画面にリダイレクトする。
            return mapping.findForward("invalid");
        }
        // セッションを取得する。
        HttpSession session = request.getSession();

        try {
            // アクションフォームを取得する。
            AdminLoginExecuteActionForm inForm = (AdminLoginExecuteActionForm) form;
            String adminMailAddress = inForm.getAdminMailAddress(); // 管理者メールアドレス
            String adminPassword = inForm.getAdminPassword(); // 管理者パスワード
            String redirectUrl = inForm.getRedirectUrl(); // リダイレクトURL

            // 安全なパスワードを取得する。
            String safetyPassword = new PasswordUtil().getSafetyPassword(adminPassword, adminMailAddress);

            // DAOメソッドに引き渡すアクションフォームを生成してメールアドレス、安全なパスワード、管理者ステータス("1"(有効))格納する。
            AdminSelectWhereActionForm adminSelectWhereForm = new AdminSelectWhereActionForm();
            adminSelectWhereForm.setAdminMailAddress(adminMailAddress);
            adminSelectWhereForm.setAdminPassword(safetyPassword);
            // 管理者テーブルからメールアドレス、安全なパスワード、管理者ステータスに紐づくレコードを取得する。
            List<AdminActionForm> adminFormList = new SelectAdminDAO().selectMatchedAdmin(adminSelectWhereForm);
            if(adminFormList.isEmpty()) {
                // 取得できなかった場合

                // アクションメッセージのインスタンスを生成する。
                ActionMessages errors = new ActionMessages();
                // インスタンスにエラーメッセージを格納する。
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                        "メールアドレスまたはパスワードが誤っています。", false));
                // リクエストにエラーメッセージを格納したインスタンスを追加する。
                saveErrors(request, errors);

                // トークンを作成する。
                saveToken(request);
                // ログイン画面にフォワードする。
                return mapping.findForward("redo");
            }

            try {
                // 管理者IDをもとにログイン済状態に変更する。
                session = new LoginAdminStatusModel().stateToAdminLogined(adminFormList.get(0).getAdminId(), session);
            } catch(NoDataExistException e) {
                // データ不存在例外を受け取った場合

                // アクションメッセージのインスタンスを生成する。
                ActionMessages errors = new ActionMessages();
                // インスタンスにエラーメッセージを格納する。
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("管理者ステータスが無効です。", false));
                // リクエストにエラーメッセージを格納したインスタンスを追加する。
                saveErrors(request, errors);

                // ログイン画面にフォワードする。
                return mapping.findForward("redo");
            }

            // セッションからアクションフォームを削除する。
            session.removeAttribute("AdminLoginExecuteActionForm");

            if(StringUtils.isEmpty(redirectUrl)) {
                // リダイレクトURLが指定されていない場合はトップ画面にリダイレクトする。
                return new RedirectAdminUtil().getRedirectAdminAction("/index.do");
            } else {
                // 上記以外の場合はリダイレクトURLにリダイレクトする。
                return new RedirectAdminUtil().getRedirectAdminAction(redirectUrl);
            }
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("LoginExecuteActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
