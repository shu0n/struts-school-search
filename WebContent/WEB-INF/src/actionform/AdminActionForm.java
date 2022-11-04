package actionform;

import java.sql.Timestamp;

import org.apache.struts.validator.ValidatorForm;

/**
 * 管理者テーブルのデータを格納するためのアクションフォーム
 */
public class AdminActionForm extends ValidatorForm {

    private int adminId; // 管理者ID
    private int adminDepartmentId; // 管理部門ID
    private String adminLastName; // 管理者姓
    private String adminFirstName; // 管理者名
    private String adminLastNameKana; // 管理者姓(フリガナ)
    private String adminFirstNameKana; // 管理者名(フリガナ)
    private String adminMailAddress; // 管理者メールアドレス
    private String adminPassword; // 管理者パスワード
    private String adminAuthority; // 管理者権限
    private String adminStatus; // 管理者ステータス
    private Timestamp adminCreatedAt; // 管理者作成日時
    private Timestamp adminUpdatedAt; // 管理者更新日時
    private String adminDeleteFlag; // 管理者削除フラグ
    private Timestamp adminDeletedAt; // 管理者削除日時

    public AdminActionForm() {}

    /**
     * adminIdを取得する。
     */
    public int getAdminId() {
        return adminId;
    }

    /**
     * adminIdを格納する。
     */
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    /**
     * adminDepartmentIdを取得する。
     */
    public int getAdminDepartmentId() {
        return adminDepartmentId;
    }

    /**
     * adminDepartmentIdを格納する。
     */
    public void setAdminDepartmentId(int adminDepartmentId) {
        this.adminDepartmentId = adminDepartmentId;
    }

    /**
     * adminLastNameを取得する。
     */
    public String getAdminLastName() {
        return adminLastName;
    }

    /**
     * adminLastNameを格納する。
     */
    public void setAdminLastName(String adminLastName) {
        this.adminLastName = adminLastName;
    }

    /**
     * adminFirstNameを取得する。
     */
    public String getAdminFirstName() {
        return adminFirstName;
    }

    /**
     * adminFirstNameを格納する。
     */
    public void setAdminFirstName(String adminFirstName) {
        this.adminFirstName = adminFirstName;
    }

    /**
     * adminLastNameKanaを取得する。
     */
    public String getAdminLastNameKana() {
        return adminLastNameKana;
    }

    /**
     * adminLastNameKanaを格納する。
     */
    public void setAdminLastNameKana(String adminLastNameKana) {
        this.adminLastNameKana = adminLastNameKana;
    }

    /**
     * adminFirstNameKanaを取得する。
     */
    public String getAdminFirstNameKana() {
        return adminFirstNameKana;
    }

    /**
     * adminFirstNameKanaを格納する。
     */
    public void setAdminFirstNameKana(String adminFirstNameKana) {
        this.adminFirstNameKana = adminFirstNameKana;
    }

    /**
     * adminMailAddressを取得する。
     */
    public String getAdminMailAddress() {
        return adminMailAddress;
    }

    /**
     * adminMailAddressを格納する。
     */
    public void setAdminMailAddress(String adminMailAddress) {
        this.adminMailAddress = adminMailAddress;
    }

    /**
     * adminPasswordを取得する。
     */
    public String getAdminPassword() {
        return adminPassword;
    }

    /**
     * adminPasswordを格納する。
     */
    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    /**
     * adminAuthorityを取得する。
     */
    public String getAdminAuthority() {
        return adminAuthority;
    }

    /**
     * adminAuthorityを格納する。
     */
    public void setAdminAuthority(String adminAuthority) {
        this.adminAuthority = adminAuthority;
    }

    /**
     * adminStatusを取得する。
     */
    public String getAdminStatus() {
        return adminStatus;
    }

    /**
     * adminStatusを格納する。
     */
    public void setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
    }

    /**
     * adminCreatedAtを取得する。
     */
    public Timestamp getAdminCreatedAt() {
        return adminCreatedAt;
    }

    /**
     * adminCreatedAtを格納する。
     */
    public void setAdminCreatedAt(Timestamp adminCreatedAt) {
        this.adminCreatedAt = adminCreatedAt;
    }

    /**
     * adminUpdatedAtを取得する。
     */
    public Timestamp getAdminUpdatedAt() {
        return adminUpdatedAt;
    }

    /**
     * adminUpdatedAtを格納する。
     */
    public void setAdminUpdatedAt(Timestamp adminUpdatedAt) {
        this.adminUpdatedAt = adminUpdatedAt;
    }

    /**
     * adminDeleteFlagを取得する。
     */
    public String getAdminDeleteFlag() {
        return adminDeleteFlag;
    }

    /**
     * adminDeleteFlagを格納する。
     */
    public void setAdminDeleteFlag(String adminDeleteFlag) {
        this.adminDeleteFlag = adminDeleteFlag;
    }

    /**
     * adminDeletedAtを取得する。
     */
    public Timestamp getAdminDeletedAt() {
        return adminDeletedAt;
    }

    /**
     * adminDeletedAtを格納する。
     */
    public void setAdminDeletedAt(Timestamp adminDeletedAt) {
        this.adminDeletedAt = adminDeletedAt;
    }

}
