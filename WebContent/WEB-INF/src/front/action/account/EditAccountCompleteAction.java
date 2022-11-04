package front.action.account;

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

import dao.account.UpdateAccountDAO;
import dao.account.sql.AccountUpdateDataActionForm;
import front.actionform.account.FrontEditAccountActionForm;
import util.LogUtil;

public class EditAccountCompleteAction extends LookupDispatchAction {
    @Override
    // 押下するボタンに応じてパラメーターを設定するためのメソッド
    protected Map<String,String> getKeyMethodMap() {
        // マップを生成する。
        Map<String,String> map = new HashMap<String,String>();
        // "修正"ボタンを押下した場合のパラメーターをマップに格納する。
        map.put("button.fix", "fix");
        // "編集"ボタンを押下した場合のパラメーターをマップに格納する。
        map.put("button.edit", "edit");
        // マップを戻す。
        return map;
    }

    /**
     * アカウント編集 確認画面で"修正"ボタンを押下した場合に入力画面にフォワードするためのメソッド
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
        FrontEditAccountActionForm inForm = (FrontEditAccountActionForm) form;

        try {
            // アクションフォームのプロフィール画像削除フラグにfalseを設定する。
            inForm.setDeleteProfileImageFileFlag(false);

            // アクションフォームのプロフィール画像のファイル名とファイル情報を削除する。
            inForm.setProfileImageFileNameUpdate(null);
            inForm.setProfileImageFile(null);

            // トークンを生成する。
            saveToken(request);
            // 入力画面にフォワードする。
            return mapping.findForward("fix");
        } catch (Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontEditAccountActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

    /**
     * アカウント作成 確認画面で"編集"ボタンを押下した場合に完了画面にフォワードするためのメソッド
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request リクエスト
     * @param response レスポンス
     * @return 遷移先画面の指定
     * */
    public ActionForward edit(
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
        FrontEditAccountActionForm inForm = (FrontEditAccountActionForm) form;

        try {
            // DAOメソッドに引き渡すアクションフォームを生成して更新するデータを格納する。
            AccountUpdateDataActionForm accountUpdateDataForm = new AccountUpdateDataActionForm();
            accountUpdateDataForm.setAccountId(inForm.getAccountId()); // アカウントID
            accountUpdateDataForm.setLastName(inForm.getLastName()); // 姓
            accountUpdateDataForm.setFirstName(inForm.getFirstName()); // 名
            accountUpdateDataForm.setLastNameKana(inForm.getLastNameKana()); // 姓(フリガナ)
            accountUpdateDataForm.setFirstNameKana(inForm.getFirstNameKana()); // 名(フリガナ)
            accountUpdateDataForm.setMailAddress(inForm.getMailAddress()); // メールアドレス
            accountUpdateDataForm.setSexId(inForm.getSexId()); // 性別ID
            if(!StringUtils.isEmpty(inForm.getBirthYear())) {
                // 文字列型の「年」が空文字またはNULL以外の場合は生年月日(日付型)を生成してアクションフォームに格納する。
                accountUpdateDataForm.setBirthDate(Timestamp.valueOf(
                        inForm.getBirthYear() + "-"
                        + String.format("%2s", inForm.getBirthMonth()).replace(" ", "0") + "-"
                        + String.format("%2s", inForm.getBirthDay()).replace(" ", "0")
                        + " 00:00:00"));
            } else {
                // 上記以外の場合は生年月日NULL設定フラグにtureを格納する。
                accountUpdateDataForm.setBirthDateToNullFlag(true);
            }
            accountUpdateDataForm.setPrefectureId(inForm.getPrefectureId()); // 都道府県ID
            accountUpdateDataForm.setSelfIntroduction(inForm.getSelfIntroduction()); // 自己紹介
            accountUpdateDataForm.setProfileImageFileName(inForm.getProfileImageFileName()); // プロフィール画像ファイル名
            // プロフィール画像ファイル名(更新)を取得する。
            String profileImageFileNameUpdate = inForm.getProfileImageFileNameUpdate();
            if(!StringUtils.isEmpty(profileImageFileNameUpdate)) {
                // プロフィール画像ファイル名(更新)が存在する場合はプロフィール画像ファイル名に格納する。
                accountUpdateDataForm.setProfileImageFileName(profileImageFileNameUpdate);
            } else if(inForm.isDeleteProfileImageFileFlag()) {
                // プロフィール画像削除フラグがtrueの場合はプロフィール画像ファイル名に空文字を設定する。
                accountUpdateDataForm.setProfileImageFileName("");
            }

            // アカウントのレコードを作成する。
            new UpdateAccountDAO().updateAccount(accountUpdateDataForm);

            // セッションからアクションフォームを削除する。
            session.removeAttribute("FrontEditAccountActionForm");
            // 完了画面にフォワードする。
            return mapping.findForward("edit");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontEditAccountActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}