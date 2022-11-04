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
import front.actionform.account.FrontExecuteReissueActionForm;
import model.account.AccountPasswordModel;
import util.LogUtil;

public class ExecuteReissueInputAction extends Action {

    /**
     * パスワード再発行 新しいパスワード 入力画面にフォワードするためのメソッド
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
        FrontExecuteReissueActionForm outForm = new FrontExecuteReissueActionForm();

        try {
            // リクエストパラメーターの文字コードを指定する。
            request.setCharacterEncoding("UTF-8");
            // リクエストパラメーターからメールアドレスと再発行トークンを取得する。
            String mailAddress = request.getParameter("mailAddress"); // メールアドレス
            String reissueToken = request.getParameter("reissueToken"); // 再発行トークン
            if(StringUtils.isEmpty(mailAddress) || StringUtils.isEmpty(reissueToken)) {
                // メールアドレスまたは再発行トークンがNULLまたは空文字の場合は失敗画面にフォワードする。
                return mapping.findForward("fail");
            }

            // アカウントIDを格納する変数を生成する。
            int accountId;
            try {
                // 再発行ステータスをチェックする。
                accountId = new AccountPasswordModel().checkReissueStatus(mailAddress, reissueToken);
            } catch(NoDataExistException e) {
                // データ不存在例外を受け取った場合

                // 失敗画面にフォワードする。
                return mapping.findForward("fail");
            }

            // アクションフォームにアカウントIDとメールアドレスを格納する。
            outForm.setAccountId(accountId); // アカウントID
            outForm.setMailAddress(mailAddress); // メールアドレス

            // トークンを作成する。
            saveToken(request);
            // セッションにアクションフォームを格納する。
            session.setAttribute("FrontExecuteReissueActionForm", outForm);
            // 入力画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
