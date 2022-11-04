package front.actionform.account;

import actionform.AccountExtendActionForm;

/**
 * パスワード変更 完了画面で使用するデータを格納するためのアクションフォーム
 */
public class FrontChangePasswordActionForm extends AccountExtendActionForm {

     private String newPassword; // 新しいパスワード

    public FrontChangePasswordActionForm() {}

    /**
     * newPasswordを取得する。
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * newPasswordを格納する。
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
