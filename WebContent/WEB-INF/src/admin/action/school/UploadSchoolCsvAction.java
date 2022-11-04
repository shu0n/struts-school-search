package admin.action.school;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import admin.actionform.school.AdminUploadSchoolCsvActionForm;
import model.login.LoginAdminStatusModel;
import model.school.SchoolFileModel;
import util.LogUtil;
import util.RedirectAdminUtil;

public class UploadSchoolCsvAction extends Action {

    /**
     * スクールの情報を記載したCSVをアップロードしてデータベースに登録するためのメソッド
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
        AdminUploadSchoolCsvActionForm inForm = (AdminUploadSchoolCsvActionForm) form;

        try {
            if(!new LoginAdminStatusModel().isAdminLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectAdminUtil().getRedirectAdminLoginAction("/listSchool.do");
            }

            try {
                // アップロードされたCSVファイルをもとにレコードを登録または更新する。
                new SchoolFileModel().upsertCsvFileData(inForm.getSchoolCsv());
            } catch(IllegalArgumentException e) {
                // 不正引数例外を受け取った場合

                // アクションメッセージのインスタンスを生成する。
                ActionMessages errors = new ActionMessages();
                // インスタンスにエラーメッセージを格納する。
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
                // リクエストにエラーメッセージを格納したインスタンスを追加する。
                saveErrors(request, errors);

                // スクール一覧画面にフォワードする。
                return new ActionForward("/listSchool.do");
            }

            // スクール一覧画面にフォワードする。
            return new ActionForward("/listSchool.do");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // アクションメッセージのインスタンスを生成する。
        ActionMessages errors = new ActionMessages();
        // インスタンスにエラーメッセージを格納する。
        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("アップロードに失敗しました。", false));
        // リクエストにエラーメッセージを格納したインスタンスを追加する。
        saveErrors(request, errors);

        // スクール一覧画面にフォワードする。
        return new ActionForward("/listSchool.do");
    }

}
