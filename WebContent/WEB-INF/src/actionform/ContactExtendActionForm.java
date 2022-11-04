package actionform;

import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

/**
 * お問合せ機能で使用するデータを格納するためのアクションフォーム
 */
public class ContactExtendActionForm extends ContactActionForm {

    private Map<Integer, String> contactStatusMap; // お問合せステータスマップ
    private List<LabelValueBean> contactStatusList; // お問合せステータスリスト
    private String contactStatusName; // お問合せステータス名
    private String strContactedAt; // お問合せ日時(文字列型)
    private String strContactUpdatedAt; // お問合せ更新日時(文字列型)

    public ContactExtendActionForm() {}

    /**
     * contactStatusMapを取得する。
     */
    public Map<Integer, String> getContactStatusMap() {
        return contactStatusMap;
    }

    /**
     * contactStatusMapを格納する。
     */
    public void setContactStatusMap(Map<Integer, String> contactStatusMap) {
        this.contactStatusMap = contactStatusMap;
    }

    /**
     * contactStatusListを取得する。
     */
    public List<LabelValueBean> getContactStatusList() {
        return contactStatusList;
    }

    /**
     * contactStatusListを格納する。
     */
    public void setContactStatusList(List<LabelValueBean> contactStatusList) {
        this.contactStatusList = contactStatusList;
    }

    /**
     * contactStatusNameを取得する。
     */
    public String getContactStatusName() {
        return contactStatusName;
    }

    /**
     * contactStatusNameを格納する。
     */
    public void setContactStatusName(String contactStatusName) {
        this.contactStatusName = contactStatusName;
    }

    /**
     * strContactedAtを取得する。
     */
    public String getStrContactedAt() {
        return strContactedAt;
    }

    /**
     * strContactedAtを格納する。
     */
    public void setStrContactedAt(String strContactedAt) {
        this.strContactedAt = strContactedAt;
    }

    /**
     * strContactUpdatedAtを取得する。
     */
    public String getStrContactUpdatedAt() {
        return strContactUpdatedAt;
    }

    /**
     * strContactUpdatedAtを格納する。
     */
    public void setStrContactUpdatedAt(String strContactUpdatedAt) {
        this.strContactUpdatedAt = strContactUpdatedAt;
    }

}
