package admin.actionform.school;

import actionform.SchoolExtendActionForm;

/**
 * 管理画面 スクール編集 確認画面、完了画面で使用するデータを格納するためのアクションフォーム
 */
public class AdminEditSchoolActionForm extends SchoolExtendActionForm {

    private String registrantKindName; // 登録者種別名
    private String strRegistrantAdminId; // 登録者管理者ID(文字列型)
    private String strRegistrantAccountId; // 登録者アカウントID(文字列型)

    public AdminEditSchoolActionForm() {}

    /**
     * registrantKindNameを取得する。
     */
    public String getRegistrantKindName() {
        return registrantKindName;
    }

    /**
     * registrantKindNameを格納する。
     */
    public void setRegistrantKindName(String registrantKindName) {
        this.registrantKindName = registrantKindName;
    }

    /**
     * strRegistrantAdminIdを取得する。
     */
    public String getStrRegistrantAdminId() {
        return strRegistrantAdminId;
    }

    /**
     * strRegistrantAdminIdを格納する。
     */
    public void setStrRegistrantAdminId(String strRegistrantAdminId) {
        this.strRegistrantAdminId = strRegistrantAdminId;
    }

    /**
     * strRegistrantAccountIdを取得する。
     */
    public String getStrRegistrantAccountId() {
        return strRegistrantAccountId;
    }

    /**
     * strRegistrantAccountIdを格納する。
     */
    public void setStrRegistrantAccountId(String strRegistrantAccountId) {
        this.strRegistrantAccountId = strRegistrantAccountId;
    }

}
