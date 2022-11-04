package front.action.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import front.actionform.account.FrontCreateAccountActionForm;
import model.account.AccountFileModel;
import model.account.AccountStatusModel;
import util.LogUtil;
import util.PasswordUtil;

public class CreateAccountConfirmAction extends Action {

    /**
     * アカウント作成 確認画面にフォワードするためのメソッド
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
            // トークンが一致しない場合は入力画面にリダイレクトする。
            return mapping.findForward("invalid");
        }
        // セッションを取得する。
        HttpSession session = request.getSession();
        // アクションフォームを取得する。
        FrontCreateAccountActionForm inForm = (FrontCreateAccountActionForm) form;

        try {
            if(new AccountStatusModel().isMailAddressExist(inForm.getMailAddress(), inForm.getAccountId(), true)) {
                // 入力されたメールアドレスが登録済の場合

                // アクションメッセージのインスタンスを生成する。
                ActionMessages errors = new ActionMessages();
                // インスタンスにエラーメッセージを格納する。
                errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("登録済のメールアドレスです。", false));
                // リクエストにエラーメッセージを格納したインスタンスを追加する。
                saveErrors(request, errors);

                // トークンを作成する。
                saveToken(request);
                // 入力画面にフォワードする。
                return mapping.findForward("redo");
            }

            // 性別IDをもとに性別名を取得してアクションフォームに格納する。
            inForm.setSexName(inForm.getSexMap().get(inForm.getSexId()));
            // 都道府県IDをもとに都道府県名を取得してアクションフォームに格納する。
            inForm.setPrefectureName(inForm.getPrefectureMap().get(inForm.getPrefectureId()));

            // リクエストにマスク化したパスワードを格納する。
            request = new PasswordUtil().setMaskedPassword(inForm.getPassword(), request);

            if(inForm.getProfileImageFile() != null) {
                // プロフィール画像がアップロードされている場合はリクエストにアップロードされた画像ファイルの情報を格納する。
                request = new AccountFileModel().setFileInfo(inForm.getProfileImageFile(), request);
            }

            // トークンを生成する。
            saveToken(request);
            // 確認画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontCreateAccountActionForm");
        // 正常に処理ができなかった場合はエラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
