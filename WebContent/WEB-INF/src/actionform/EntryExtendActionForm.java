package actionform;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

/**
 * 申込機能で使用するデータを格納するためのアクションフォーム
 */
public class EntryExtendActionForm extends EntryActionForm {

    private String entrySchoolName; // スクール名
    private String schoolCategoryName; // カテゴリー名
    private BigDecimal schoolFee; // スクール費用
    private int registrantAccountId; // スクール登録者アカウントID
    private String registrantLastName; // スクール登録者姓
    private String registrantFirstName; // スクール登録者名
    private String registrantLastNameKana; // スクール登録者姓(フリガナ)
    private String registrantFirstNameKana; // スクール登録者名(フリガナ)
    private int registrantAdminId; // スクール登録管理者ID
    private String applicantLastName; // 申込者姓
    private String applicantFirstName; // 申込者名
    private String applicantLastNameKana; // 申込者姓(フリガナ)
    private String applicantFirstNameKana; // 申込者名(フリガナ)
    private Map<Integer, String> entryStatusMap; // 申込ステータスマップ
    private List<LabelValueBean> entryStatusList; // 申込ステータスリスト
    private String entryStatusName; // 申込ステータス名
    private String strEntriedAt; // 申込日時(文字列型)
    private String strEntryUpdatedAt; // 申込更新日時(文字列型)

    public EntryExtendActionForm() {}

    /**
     * entrySchoolNameを取得する。
     */
    public String getEntrySchoolName() {
        return entrySchoolName;
    }

    /**
     * entrySchoolNameを格納する。
     */
    public void setEntrySchoolName(String entrySchoolName) {
        this.entrySchoolName = entrySchoolName;
    }

    /**
     * schoolCategoryNameを取得する。
     */
    public String getSchoolCategoryName() {
        return schoolCategoryName;
    }

    /**
     * schoolCategoryNameを格納する。
     */
    public void setSchoolCategoryName(String schoolCategoryName) {
        this.schoolCategoryName = schoolCategoryName;
    }

    /**
     * schoolFeeを取得する。
     */
    public BigDecimal getSchoolFee() {
        return schoolFee;
    }

    /**
     * schoolFeeを格納する。
     */
    public void setSchoolFee(BigDecimal schoolFee) {
        this.schoolFee = schoolFee;
    }

    /**
     * registrantAccountIdを取得する。
     */
    public int getRegistrantAccountId() {
        return registrantAccountId;
    }

    /**
     * registrantAccountIdを格納する。
     */
    public void setRegistrantAccountId(int registrantAccountId) {
        this.registrantAccountId = registrantAccountId;
    }

    /**
     * registrantLastNameを取得する。
     */
    public String getRegistrantLastName() {
        return registrantLastName;
    }

    /**
     * registrantLastNameを格納する。
     */
    public void setRegistrantLastName(String registrantLastName) {
        this.registrantLastName = registrantLastName;
    }

    /**
     * registrantFirstNameを取得する。
     */
    public String getRegistrantFirstName() {
        return registrantFirstName;
    }

    /**
     * registrantFirstNameを格納する。
     */
    public void setRegistrantFirstName(String registrantFirstName) {
        this.registrantFirstName = registrantFirstName;
    }

    /**
     * registrantLastNameKanaを取得する。
     */
    public String getRegistrantLastNameKana() {
        return registrantLastNameKana;
    }

    /**
     * registrantLastNameKanaを格納する。
     */
    public void setRegistrantLastNameKana(String registrantLastNameKana) {
        this.registrantLastNameKana = registrantLastNameKana;
    }

    /**
     * registrantFirstNameKanaを取得する。
     */
    public String getRegistrantFirstNameKana() {
        return registrantFirstNameKana;
    }

    /**
     * registrantFirstNameKanaを格納する。
     */
    public void setRegistrantFirstNameKana(String registrantFirstNameKana) {
        this.registrantFirstNameKana = registrantFirstNameKana;
    }

    /**
     * registrantAdminIdを取得する。
     */
    public int getRegistrantAdminId() {
        return registrantAdminId;
    }

    /**
     * registrantAdminIdを格納する。
     */
    public void setRegistrantAdminId(int registrantAdminId) {
        this.registrantAdminId = registrantAdminId;
    }

    /**
     * applicantLastNameを取得する。
     */
    public String getApplicantLastName() {
        return applicantLastName;
    }

    /**
     * applicantLastNameを格納する。
     */
    public void setApplicantLastName(String applicantLastName) {
        this.applicantLastName = applicantLastName;
    }

    /**
     * applicantFirstNameを取得する。
     */
    public String getApplicantFirstName() {
        return applicantFirstName;
    }

    /**
     * applicantFirstNameを格納する。
     */
    public void setApplicantFirstName(String applicantFirstName) {
        this.applicantFirstName = applicantFirstName;
    }

    /**
     * applicantLastNameKanaを取得する。
     */
    public String getApplicantLastNameKana() {
        return applicantLastNameKana;
    }

    /**
     * applicantLastNameKanaを格納する。
     */
    public void setApplicantLastNameKana(String applicantLastNameKana) {
        this.applicantLastNameKana = applicantLastNameKana;
    }

    /**
     * applicantFirstNameKanaを取得する。
     */
    public String getApplicantFirstNameKana() {
        return applicantFirstNameKana;
    }

    /**
     * applicantFirstNameKanaを格納する。
     */
    public void setApplicantFirstNameKana(String applicantFirstNameKana) {
        this.applicantFirstNameKana = applicantFirstNameKana;
    }

    /**
     * entryStatusMapを取得する。
     */
    public Map<Integer, String> getEntryStatusMap() {
        return entryStatusMap;
    }

    /**
     * entryStatusMapを格納する。
     */
    public void setEntryStatusMap(Map<Integer, String> entryStatusMap) {
        this.entryStatusMap = entryStatusMap;
    }

    /**
     * entryStatusListを取得する。
     */
    public List<LabelValueBean> getEntryStatusList() {
        return entryStatusList;
    }

    /**
     * entryStatusListを格納する。
     */
    public void setEntryStatusList(List<LabelValueBean> entryStatusList) {
        this.entryStatusList = entryStatusList;
    }

    /**
     * entryStatusNameを取得する。
     */
    public String getEntryStatusName() {
        return entryStatusName;
    }

    /**
     * entryStatusNameを格納する。
     */
    public void setEntryStatusName(String entryStatusName) {
        this.entryStatusName = entryStatusName;
    }

    /**
     * strEntriedAtを取得する。
     */
    public String getStrEntriedAt() {
        return strEntriedAt;
    }

    /**
     * strEntriedAtを格納する。
     */
    public void setStrEntriedAt(String strEntriedAt) {
        this.strEntriedAt = strEntriedAt;
    }

    /**
     * strEntryUpdatedAtを取得する。
     */
    public String getStrEntryUpdatedAt() {
        return strEntryUpdatedAt;
    }

    /**
     * strEntryUpdatedAtを格納する。
     */
    public void setStrEntryUpdatedAt(String strEntryUpdatedAt) {
        this.strEntryUpdatedAt = strEntryUpdatedAt;
    }

}
