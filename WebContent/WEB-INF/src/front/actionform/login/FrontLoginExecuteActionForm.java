package front.actionform.login;

import actionform.AccountActionForm;

/**
 * ログイン画面で使用するデータを格納するためのアクションフォーム
 */
public class FrontLoginExecuteActionForm extends AccountActionForm {

    private String redirectUrl; // リダイレクトURL

    public FrontLoginExecuteActionForm() {}

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
