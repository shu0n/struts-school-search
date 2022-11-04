package front.action.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import model.login.LoginStatusModel;
import util.LogUtil;

public class LoginAction extends Action {

    /**
     * ログイン画面にフォワードするためのメソッド
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
            if(new LoginStatusModel().isLogined(session)) {
                // ログイン済の場合はトップ画面にリダイレクトする。
                return mapping.findForward("logined");
            }

            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // パラメーターからリダイレクトURLを取得してリクエストに格納する。
            String redirectUrl = request.getParameter("redirectUrl");
            request.setAttribute("redirectUrl", redirectUrl);

            // トークンを作成する。
            saveToken(request);
            // ログイン画面にフォワードする。
            return mapping.findForward("success");
        } catch (Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
