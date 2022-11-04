package admin.actionform.account;

import actionform.AccountExtendActionForm;

/**
 * 管理画面 アカウント編集 確認画面、完了画面で使用するデータを格納するためのアクションフォーム
 */
public class AdminEditAccountActionForm extends AccountExtendActionForm {

    private String profileImageFileNameUpdate; // プロフィール画像ファイル名(更新)
    private boolean deleteProfileImageFileFlag; // プロフィール画像削除フラグ
    private boolean changePasswordFlag; // パスワード変更フラグ
    private String newPassword; // 新しいパスワード

    public AdminEditAccountActionForm() {}

    /**
     * profileImageFileNameUpdateを取得する。
     */
    public String getProfileImageFileNameUpdate() {
        return profileImageFileNameUpdate;
    }

    /**
     * profileImageFileNameUpdateを格納する。
     */
    public void setProfileImageFileNameUpdate(String profileImageFileNameUpdate) {
        this.profileImageFileNameUpdate = profileImageFileNameUpdate;
    }

    /**
     * deleteProfileImageFileFlagを取得する。
     */
    public boolean isDeleteProfileImageFileFlag() {
        return deleteProfileImageFileFlag;
    }

    /**
     * deleteProfileImageFileFlagを格納する。
     */
    public void setDeleteProfileImageFileFlag(boolean deleteProfileImageFileFlag) {
        this.deleteProfileImageFileFlag = deleteProfileImageFileFlag;
    }

    /**
     * changePasswordFlagを取得する。
     */
    public boolean isChangePasswordFlag() {
        return changePasswordFlag;
    }

    /**
     * changePasswordFlagを格納する。
     */
    public void setChangePasswordFlag(boolean changePasswordFlag) {
        this.changePasswordFlag = changePasswordFlag;
    }

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
