package admin.action.account;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

import admin.actionform.account.AdminCreateAccountActionForm;
import dao.account.InsertAccountDAO;
import dao.account.sql.AccountInsertDataActionForm;
import util.LogUtil;
import util.PasswordUtil;

public class CreateAccountCompleteAction extends LookupDispatchAction {
    @Override
    // 押下するボタンに応じてパラメーターを設定するためのメソッド
    protected Map<String,String> getKeyMethodMap() {
        // マップを生成する。
        Map<String, String> map = new HashMap<>();
        // "修正"ボタンを押下した場合のパラメーターをマップに格納する。
        map.put("button.fix", "fix");
        // "作成"ボタンを押下した場合のパラメーターをマップに格納する。
        map.put("button.create", "create");
        // マップを戻す。
        return map;
    }

    /**
     * 管理画面 アカウント作成 確認画面で"修正"ボタンを押下した場合に入力画面にフォワードするためのメソッド
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request リクエスト
     * @param response レスポンス
     * @return 遷移先画面の指定
     */
    public ActionForward fix(
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
        AdminCreateAccountActionForm inForm = (AdminCreateAccountActionForm) form;

        try {
            // アクションフォームのプロフィール画像のファイル名とファイル情報を削除する。
            inForm.setProfileImageFileName(null);
            inForm.setProfileImageFile(null);

            // トークンを生成する。
            saveToken(request);
            // 入力画面にフォワードする。
            return mapping.findForward("fix");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("AdminCreateAccountActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

    /**
     * 管理画面 アカウント作成 確認画面で"作成"ボタンを押下した場合に完了画面にフォワードするためのメソッド
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request リクエスト
     * @param response レスポンス
     * @return 遷移先画面の指定
     * */
    public ActionForward create(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        if(!isTokenValid(request, true)) {
            // トークンが一致しない場合は入力画面にリダイレクトする。
            return mapping.findForward("invalid");
        }
        // セッションを取得する。
        HttpSession session = request.getSession();
        // アクションフォームを取得する。
        AdminCreateAccountActionForm inForm = (AdminCreateAccountActionForm) form;

        try {
            // DAOメソッドに引き渡すアクションフォームを生成して登録するデータを格納する。
            AccountInsertDataActionForm accountInsertDataForm = new AccountInsertDataActionForm();
            accountInsertDataForm.setLastName(inForm.getLastName()); // 姓
            accountInsertDataForm.setFirstName(inForm.getFirstName()); // 名
            accountInsertDataForm.setLastNameKana(inForm.getLastNameKana()); // 姓(フリガナ)
            accountInsertDataForm.setFirstNameKana(inForm.getFirstNameKana()); // 名(フリガナ)
            accountInsertDataForm.setMailAddress(inForm.getMailAddress()); // メールアドレス
            accountInsertDataForm.setSexId(inForm.getSexId()); // 性別ID
            if(!StringUtils.isEmpty(inForm.getBirthYear())) {
                // 文字列型の「年」が空文字またはNULL以外の場合は生年月日(日付型)を生成してアクションフォームに格納する。
                accountInsertDataForm.setBirthDate(Timestamp.valueOf(
                        inForm.getBirthYear() + "-"
                        + String.format("%2s", inForm.getBirthMonth()).replace(" ", "0") + "-"
                        + String.format("%2s", inForm.getBirthDay()).replace(" ", "0")
                        + " 00:00:00"));
            }
            accountInsertDataForm.setPrefectureId(inForm.getPrefectureId()); // 都道府県ID
            accountInsertDataForm.setProfileImageFileName(inForm.getProfileImageFileName()); // プロフィール画像ファイル名
            accountInsertDataForm.setSelfIntroduction(inForm.getSelfIntroduction()); // 自己紹介
            // アクションフォームに安全なパスワードを格納する。
            accountInsertDataForm.setPassword(
                    new PasswordUtil().getSafetyPassword(inForm.getPassword(), inForm.getMailAddress()));
            accountInsertDataForm.setAccountStatus(inForm.getAccountStatus()); // アカウントステータス
            // アカウントのレコードを作成する。
            new InsertAccountDAO().insertAccount(accountInsertDataForm);

            // セッションからアクションフォームを削除する。
            session.removeAttribute("AdminCreateAccountActionForm");
            // 完了画面にフォワードする。
            return mapping.findForward("create");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("AdminCreateAccountActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
