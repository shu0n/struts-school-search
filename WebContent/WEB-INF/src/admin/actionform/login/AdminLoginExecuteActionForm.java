package admin.actionform.login;

import actionform.AdminActionForm;

/**
 * 管理画面のログイン画面で使用するデータを格納するためのアクションフォーム
 */
public class AdminLoginExecuteActionForm extends AdminActionForm {

    private String redirectUrl; // リダイレクトURL

    public AdminLoginExecuteActionForm() {}

    /**
     * redirectUrlを取得する。
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * redirectUrlを格納する。
     */
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

}
