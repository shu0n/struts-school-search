package admin.action.entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import admin.actionform.entry.AdminListReceivedEntryActionForm;
import model.entry.EntryFileModel;
import model.login.LoginAdminStatusModel;
import util.LogUtil;
import util.RedirectAdminUtil;

public class DownloadReceivedEntryCsvAction extends Action {

    /**
     * 申込受付の情報をCSVに出力してダウンロードするためのメソッド
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

            // レスポンスにダウンロードするCSVファイルのデータを追加する。
            response = new EntryFileModel().setDownloadCsvFileData(inForm.getEntryExtendFormList(), response);

            // ダウンロードのみのためNULLを戻す。
            return null;
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // アクションメッセージのインスタンスを生成する。
        ActionMessages errors = new ActionMessages();
        // インスタンスにエラーメッセージを格納する。
        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("ダウンロードに失敗しました。", false));
        // リクエストにエラーメッセージを格納したインスタンスを追加する。
        saveErrors(request, errors);

        // スクール一覧画面にフォワードする。
        return new ActionForward("/listReceivedEntry.do");
    }

}
