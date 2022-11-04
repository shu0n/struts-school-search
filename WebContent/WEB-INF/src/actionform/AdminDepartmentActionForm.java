package actionform;

import java.sql.Timestamp;

import org.apache.struts.validator.ValidatorForm;

/**
 * 管理部門テーブルのデータを格納するためのアクションフォーム
 */
public class AdminDepartmentActionForm extends ValidatorForm {

    private int adminDepartmentId; // 管理部門ID
    private String adminDepartmentName; // 管理部門名
    private String adminDepartmentAuthority; // 管理部門権限
    private String adminStatus; // 管理部門ステータス
    private Timestamp adminDepartmentCreatedAt; // 管理部門作成日時
    private Timestamp adminDepartmentUpdatedAt; // 管理部門更新日時
    private String adminDepartmentDeleteFlag; // 管理部門削除フラグ
    private Timestamp adminDepartmentDeletedAt; // 管理部門削除日時

    public AdminDepartmentActionForm() {}

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
     * adminDepartmentNameを取得する。
     */
    public String getAdminDepartmentName() {
        return adminDepartmentName;
    }

    /**
     * adminDepartmentNameを格納する。
     */
    public void setAdminDepartmentName(String adminDepartmentName) {
        this.adminDepartmentName = adminDepartmentName;
    }

    /**
     * adminDepartmentAuthorityを取得する。
     */
    public String getAdminDepartmentAuthority() {
        return adminDepartmentAuthority;
    }

    /**
     * adminDepartmentAuthorityを格納する。
     */
    public void setAdminDepartmentAuthority(String adminDepartmentAuthority) {
        this.adminDepartmentAuthority = adminDepartmentAuthority;
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
     * adminDepartmentCreatedAtを取得する。
     */
    public Timestamp getAdminDepartmentCreatedAt() {
        return adminDepartmentCreatedAt;
    }

    /**
     * adminDepartmentCreatedAtを格納する。
     */
    public void setAdminDepartmentCreatedAt(Timestamp adminDepartmentCreatedAt) {
        this.adminDepartmentCreatedAt = adminDepartmentCreatedAt;
    }

    /**
     * adminDepartmentUpdatedAtを取得する。
     */
    public Timestamp getAdminDepartmentUpdatedAt() {
        return adminDepartmentUpdatedAt;
    }

    /**
     * adminDepartmentUpdatedAtを格納する。
     */
    public void setAdminDepartmentUpdatedAt(Timestamp adminDepartmentUpdatedAt) {
        this.adminDepartmentUpdatedAt = adminDepartmentUpdatedAt;
    }

    /**
     * adminDepartmentDeleteFlagを取得する。
     */
    public String getAdminDepartmentDeleteFlag() {
        return adminDepartmentDeleteFlag;
    }

    /**
     * adminDepartmentDeleteFlagを格納する。
     */
    public void setAdminDepartmentDeleteFlag(String adminDepartmentDeleteFlag) {
        this.adminDepartmentDeleteFlag = adminDepartmentDeleteFlag;
    }

    /**
     * adminDepartmentDeletedAtを取得する。
     */
    public Timestamp getAdminDepartmentDeletedAt() {
        return adminDepartmentDeletedAt;
    }

    /**
     * adminDepartmentDeletedAtを格納する。
     */
    public void setAdminDepartmentDeletedAt(Timestamp adminDepartmentDeletedAt) {
        this.adminDepartmentDeletedAt = adminDepartmentDeletedAt;
    }

}
