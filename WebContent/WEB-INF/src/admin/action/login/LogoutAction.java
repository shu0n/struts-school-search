package admin.action.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import model.login.LoginAdminStatusModel;
import util.LogUtil;

public class LogoutAction extends Action {

    /**
     * ログアウト処理を実行して管理画面 ログイン画面にフォワードするためのメソッド
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
            if(!new LoginAdminStatusModel().isAdminLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return mapping.findForward("adminLogout");
            }

            // ログアウト状態にする。
            session = new LoginAdminStatusModel().stateToAdminLogout(session);

            // ログイン画面にリダイレクトする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
