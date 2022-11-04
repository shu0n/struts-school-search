package admin.actionform.school;

import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import actionform.SchoolExtendActionForm;

/**
 * 管理画面 スクール登録 確認画面、完了画面で使用するデータを格納するためのアクションフォーム
 */
public class AdminRegisterSchoolActionForm extends SchoolExtendActionForm {

    private String registrantKind; // 登録者種別
    private String registrantKindName; // 登録者種別名
    private Map<String, String> registrantKindMap; // 登録者種別マップ
    private String strRegistrantAdminId; // 登録者管理者ID(文字列型)
    private String strRegistrantAccountId; // 登録者アカウントID(文字列型)
    private List<LabelValueBean> registrantAccountIdList; // 登録者アカウントIDリスト

    public AdminRegisterSchoolActionForm() {}

    /**
     * registrantKindを取得する。
     */
    public String getRegistrantKind() {
        return registrantKind;
    }

    /**
     * registrantKindを格納する。
     */
    public void setRegistrantKind(String registrantKind) {
        this.registrantKind = registrantKind;
    }

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
     * registrantKindMapを取得する。
     */
    public Map<String, String> getRegistrantKindMap() {
        return registrantKindMap;
    }

    /**
     * registrantKindMapを格納する。
     */
    public void setRegistrantKindMap(Map<String, String> registrantKindMap) {
        this.registrantKindMap = registrantKindMap;
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

    /**
     * registrantAccountIdListを取得する。
     */
    public List<LabelValueBean> getRegistrantAccountIdList() {
        return registrantAccountIdList;
    }

    /**
     * registrantAccountIdListを格納する。
     */
    public void setRegistrantAccountIdList(List<LabelValueBean> registrantAccountIdList) {
        this.registrantAccountIdList = registrantAccountIdList;
    }

}
