package actionform;

import java.sql.Timestamp;

import org.apache.struts.validator.ValidatorForm;

/**
 * アカウントテーブルのデータを格納するためのアクションフォーム
 */
public class AccountActionForm extends ValidatorForm {

    private int accountId; // アカウントID
    private String lastName; // 姓
    private String firstName; // 名
    private String lastNameKana; // 姓(フリガナ)
    private String firstNameKana; // 名(フリガナ)
    private int sexId; // 性別ID
    private Timestamp birthDate; // 生年月日
    private int prefectureId; // 都道府県名ID
    private String mailAddress; // メールアドレス
    private String profileImageFileName; // プロフィール画像ファイル名
    private String selfIntroduction; // 自己紹介
    private String password; // パスワード
    private String accountStatus; // アカウントステータス
    private String activateToken; // 有効化トークン
    private Timestamp activateEffectiveDate; // 有効化有効期限
    private String reissueToken; // 再発行トークン
    private Timestamp reissueEffctiveDate; // 再発行有効期限
    private Timestamp accountCreatedAt; // アカウント作成日時
    private Timestamp accountUpdatedAt; // アカウント更新日時
    private String accountDeleteFlag; // アカウント削除フラグ
    private Timestamp accountDeletedAt; // アカウント削除日時

    public AccountActionForm() {}

    /**
     * accountIdを取得する。
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * accountIdを格納する。
     */
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    /**
     * lastNameを取得する。
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * lastNameを格納する。
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * firstNameを取得する。
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * firstNameを格納する。
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * lastNameKanaを取得する。
     */
    public String getLastNameKana() {
        return lastNameKana;
    }

    /**
     * lastNameKanaを格納する。
     */
    public void setLastNameKana(String lastNameKana) {
        this.lastNameKana = lastNameKana;
    }

    /**
     * firstNameKanaを取得する。
     */
    public String getFirstNameKana() {
        return firstNameKana;
    }

    /**
     * firstNameKanaを格納する。
     */
    public void setFirstNameKana(String firstNameKana) {
        this.firstNameKana = firstNameKana;
    }

    /**
     * sexIdを取得する。
     */
    public int getSexId() {
        return sexId;
    }

    /**
     * sexIdを格納する。
     */
    public void setSexId(int sexId) {
        this.sexId = sexId;
    }

    /**
     * birthDateを取得する。
     */
    public Timestamp getBirthDate() {
        return birthDate;
    }

    /**
     * birthDateを格納する。
     */
    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * prefectureIdを取得する。
     */
    public int getPrefectureId() {
        return prefectureId;
    }

    /**
     * prefectureIdを格納する。
     */
    public void setPrefectureId(int prefectureId) {
        this.prefectureId = prefectureId;
    }

    /**
     * mailAddressを取得する。
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**
     * mailAddressを格納する。
     */
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    /**
     * profileImageFileNameを取得する。
     */
    public String getProfileImageFileName() {
        return profileImageFileName;
    }

    /**
     * profileImageFileNameを格納する。
     */
    public void setProfileImageFileName(String profileImageFileName) {
        this.profileImageFileName = profileImageFileName;
    }

    /**
     * selfIntroductionを取得する。
     */
    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    /**
     * selfIntroductionを格納する。
     */
    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    /**
     * passwordを取得する。
     */
    public String getPassword() {
        return password;
    }

    /**
     * passwordを格納する。
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * accountStatusを取得する。
     */
    public String getAccountStatus() {
        return accountStatus;
    }

    /**
     * accountStatusを格納する。
     */
    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    /**
     * activateTokenを取得する。
     */
    public String getActivateToken() {
        return activateToken;
    }

    /**
     * activateTokenを格納する。
     */
    public void setActivateToken(String activateToken) {
        this.activateToken = activateToken;
    }

    /**
     * activateEffectiveDateを取得する。
     */
    public Timestamp getActivateEffectiveDate() {
        return activateEffectiveDate;
    }

    /**
     * activateEffectiveDateを格納する。
     */
    public void setActivateEffectiveDate(Timestamp activateEffectiveDate) {
        this.activateEffectiveDate = activateEffectiveDate;
    }

    /**
     * reissueTokenを取得する。
     */
    public String getReissueToken() {
        return reissueToken;
    }

    /**
     * reissueTokenを格納する。
     */
    public void setReissueToken(String reissueToken) {
        this.reissueToken = reissueToken;
    }

    /**
     * reissueEffctiveDateを取得する。
     */
    public Timestamp getReissueEffctiveDate() {
        return reissueEffctiveDate;
    }

    /**
     * reissueEffctiveDateを格納する。
     */
    public void setReissueEffctiveDate(Timestamp reissueEffctiveDate) {
        this.reissueEffctiveDate = reissueEffctiveDate;
    }

    /**
     * accountCreatedAtを取得する。
     */
    public Timestamp getAccountCreatedAt() {
        return accountCreatedAt;
    }

    /**
     * accountCreatedAtを格納する。
     */
    public void setAccountCreatedAt(Timestamp accountCreatedAt) {
        this.accountCreatedAt = accountCreatedAt;
    }

    /**
     * accountUpdatedAtを取得する。
     */
    public Timestamp getAccountUpdatedAt() {
        return accountUpdatedAt;
    }

    /**
     * accountUpdatedAtを格納する。
     */
    public void setAccountUpdatedAt(Timestamp accountUpdatedAt) {
        this.accountUpdatedAt = accountUpdatedAt;
    }

    /**
     * accountDeleteFlagを取得する。
     */
    public String getAccountDeleteFlag() {
        return accountDeleteFlag;
    }

    /**
     * accountDeleteFlagを格納する。
     */
    public void setAccountDeleteFlag(String accountDeleteFlag) {
        this.accountDeleteFlag = accountDeleteFlag;
    }

    /**
     * accountDeletedAtを取得する。
     */
    public Timestamp getAccountDeletedAt() {
        return accountDeletedAt;
    }

    /**
     * accountDeletedAtを格納する。
     */
    public void setAccountDeletedAt(Timestamp accountDeletedAt) {
        this.accountDeletedAt = accountDeletedAt;
    }

}
