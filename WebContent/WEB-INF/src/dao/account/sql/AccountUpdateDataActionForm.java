package dao.account.sql;

import java.sql.Timestamp;

import org.apache.struts.action.ActionForm;

/**
 * アカウントテーブルで更新するレコードのデータを格納するためのアクションフォーム
 */
public class AccountUpdateDataActionForm extends ActionForm {

    private int accountId; // アカウントID
    private String lastName; // 姓
    private String firstName; // 名
    private String lastNameKana; // 姓(フリガナ)
    private String firstNameKana; // 名(フリガナ)
    private int sexId; // 性別ID
    private Timestamp birthDate; // 生年月日
    private boolean birthDateToNullFlag; // 生年月日NULL設定フラグ
    private int prefectureId; // 都道府県名ID
    private String mailAddress; // メールアドレス
    private String profileImageFileName; // プロフィール画像ファイル名
    private String selfIntroduction; // 自己紹介
    private String password; // パスワード
    private String accountStatus; // アカウントステータス
    private String activateToken; // 有効化トークン
    private boolean activateTokenToToNullFlag; // 有効化トークンNULL設定フラグ
    private Timestamp activateEffectiveDate; // 有効化トークン有効期限
    private boolean activateEffectiveDateToNullFlag; // 有効化トークン有効期限NULL設定フラグ
    private String reissueToken; // 再発行トークン
    private boolean reissueTokenToNullFlag; // 再発行トークンNULL設定フラグ
    private Timestamp reissueEffctiveDate; // 再発行トークン有効期限
    private boolean reissueEffctiveDateToNULLFlag; // 再発行トークン有効期限NULL設定フラグ

    public AccountUpdateDataActionForm() {}

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
     * birthDateToNullFlagを取得する。
     */
    public boolean isBirthDateToNullFlag() {
        return birthDateToNullFlag;
    }

    /**
     * birthDateToNullFlagを格納する。
     */
    public void setBirthDateToNullFlag(boolean birthDateToNullFlag) {
        this.birthDateToNullFlag = birthDateToNullFlag;
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
     * activateTokenToToNullFlagを取得する。
     */
    public boolean isActivateTokenToToNullFlag() {
        return activateTokenToToNullFlag;
    }

    /**
     * activateTokenToToNullFlagを格納する。
     */
    public void setActivateTokenToToNullFlag(boolean activateTokenToToNullFlag) {
        this.activateTokenToToNullFlag = activateTokenToToNullFlag;
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
     * activateEffectiveDateToNullFlagを取得する。
     */
    public boolean isActivateEffectiveDateToNullFlag() {
        return activateEffectiveDateToNullFlag;
    }

    /**
     * activateEffectiveDateToNullFlagを格納する。
     */
    public void setActivateEffectiveDateToNullFlag(boolean activateEffectiveDateToNullFlag) {
        this.activateEffectiveDateToNullFlag = activateEffectiveDateToNullFlag;
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
     * reissueTokenToNullFlagを取得する。
     */
    public boolean isReissueTokenToNullFlag() {
        return reissueTokenToNullFlag;
    }

    /**
     * reissueTokenToNullFlagを格納する。
     */
    public void setReissueTokenToNullFlag(boolean reissueTokenToNullFlag) {
        this.reissueTokenToNullFlag = reissueTokenToNullFlag;
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
     * reissueEffctiveDateToNULLFlagを取得する。
     */
    public boolean isReissueEffctiveDateToNULLFlag() {
        return reissueEffctiveDateToNULLFlag;
    }

    /**
     * reissueEffctiveDateToNULLFlagを格納する。
     */
    public void setReissueEffctiveDateToNULLFlag(boolean reissueEffctiveDateToNULLFlag) {
        this.reissueEffctiveDateToNULLFlag = reissueEffctiveDateToNULLFlag;
    }

}
