package front.actionform.account;

import actionform.AccountExtendActionForm;

/**
 * アカウント編集 確認画面、完了画面で使用するデータを格納するためのアクションフォーム
 */
public class FrontEditAccountActionForm extends AccountExtendActionForm {

    private String profileImageFileNameUpdate; // プロフィール画像ファイル名(更新)
    private boolean deleteProfileImageFileFlag; // プロフィール画像削除フラグ

    public FrontEditAccountActionForm() {}

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

}
