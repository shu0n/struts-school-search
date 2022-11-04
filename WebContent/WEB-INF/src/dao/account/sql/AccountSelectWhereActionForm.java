package dao.account.sql;

import java.sql.Timestamp;

import org.apache.struts.action.ActionForm;

/**
 * アカウントテーブルから取得するレコードの条件に使用するデータを格納するためのアクションフォーム
 */
public class AccountSelectWhereActionForm extends ActionForm {

    private int accountId; // アカウントID
    private String lastName; // 姓
    private String likeLastName; // 姓(類似)
    private String firstName; // 名
    private String likeFirstName; // 名(類似)
    private String lastNameKana; // 姓(フリガナ)
    private String likeLastNameKana; // 姓(フリガナ、類似)
    private String firstNameKana; // 名(フリガナ)
    private String likeFirstNameKana; // 名(フリガナ、類似)
    private String[] accountIdArray; // アカウントID配列
    private int sexId; // 性別ID
    private String[] sexIdArray; // 性別ID配列
    private Timestamp birthDate; // 生年月日
    private String fromBirthDate; // 生年月日(From)
    private String toBirthDate; // 生年月日(To)
    private int prefectureId; // 都道府県ID
    private String[] prefectureIdArray; // 都道府県ID配列
    private String mailAddress; // メールアドレス
    private String likeMailAddress; // メールアドレス(類似)
    private String password; // パスワード
    private String accountStatus; // アカウントステータス
    private String activateToken; // 有効化トークン
    private String reissueToken; // 再発行トークン
    private String[] accountStatusArray; // アカウントステータス配列
    private String fromAccountCreatedDate; // アカウント作成日時(From)
    private String toAccountCreatedDate; // アカウント作成日時(To)
    private String fromAccountUpdatedDate; // アカウント更新日時(From)
    private String toAccountUpdatedDate; // アカウント更新日時(To)

    public AccountSelectWhereActionForm() {}

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
     * likeLastNameを取得する。
     */
    public String getLikeLastName() {
        return likeLastName;
    }

    /**
     * likeLastNameを格納する。
     */
    public void setLikeLastName(String likeLastName) {
        this.likeLastName = likeLastName;
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
     * likeFirstNameを取得する。
     */
    public String getLikeFirstName() {
        return likeFirstName;
    }

    /**
     * likeFirstNameを格納する。
     */
    public void setLikeFirstName(String likeFirstName) {
        this.likeFirstName = likeFirstName;
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
     * likeLastNameKanaを取得する。
     */
    public String getLikeLastNameKana() {
        return likeLastNameKana;
    }

    /**
     * likeLastNameKanaを格納する。
     */
    public void setLikeLastNameKana(String likeLastNameKana) {
        this.likeLastNameKana = likeLastNameKana;
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
     * likeFirstNameKanaを取得する。
     */
    public String getLikeFirstNameKana() {
        return likeFirstNameKana;
    }

    /**
     * likeFirstNameKanaを格納する。
     */
    public void setLikeFirstNameKana(String likeFirstNameKana) {
        this.likeFirstNameKana = likeFirstNameKana;
    }

    /**
     * accountIdArrayを取得する。
     */
    public String[] getAccountIdArray() {
        return accountIdArray;
    }

    /**
     * accountIdArrayを格納する。
     */
    public void setAccountIdArray(String[] accountIdArray) {
        this.accountIdArray = accountIdArray;
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
     * sexIdArrayを取得する。
     */
    public String[] getSexIdArray() {
        return sexIdArray;
    }

    /**
     * sexIdArrayを格納する。
     */
    public void setSexIdArray(String[] sexIdArray) {
        this.sexIdArray = sexIdArray;
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
     * fromBirthDateを取得する。
     */
    public String getFromBirthDate() {
        return fromBirthDate;
    }

    /**
     * fromBirthDateを格納する。
     */
    public void setFromBirthDate(String fromBirthDate) {
        this.fromBirthDate = fromBirthDate;
    }

    /**
     * toBirthDateを取得する。
     */
    public String getToBirthDate() {
        return toBirthDate;
    }

    /**
     * toBirthDateを格納する。
     */
    public void setToBirthDate(String toBirthDate) {
        this.toBirthDate = toBirthDate;
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
     * prefectureIdArrayを取得する。
     */
    public String[] getPrefectureIdArray() {
        return prefectureIdArray;
    }

    /**
     * prefectureIdArrayを格納する。
     */
    public void setPrefectureIdArray(String[] prefectureIdArray) {
        this.prefectureIdArray = prefectureIdArray;
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
     * likeMailAddressを取得する。
     */
    public String getLikeMailAddress() {
        return likeMailAddress;
    }

    /**
     * likeMailAddressを格納する。
     */
    public void setLikeMailAddress(String likeMailAddress) {
        this.likeMailAddress = likeMailAddress;
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
     * accountStatusArrayを取得する。
     */
    public String[] getAccountStatusArray() {
        return accountStatusArray;
    }

    /**
     * accountStatusArrayを格納する。
     */
    public void setAccountStatusArray(String[] accountStatusArray) {
        this.accountStatusArray = accountStatusArray;
    }

    /**
     * fromAccountCreatedDateを取得する。
     */
    public String getFromAccountCreatedDate() {
        return fromAccountCreatedDate;
    }

    /**
     * fromAccountCreatedDateを格納する。
     */
    public void setFromAccountCreatedDate(String fromAccountCreatedDate) {
        this.fromAccountCreatedDate = fromAccountCreatedDate;
    }

    /**
     * toAccountCreatedDateを取得する。
     */
    public String getToAccountCreatedDate() {
        return toAccountCreatedDate;
    }

    /**
     * toAccountCreatedDateを格納する。
     */
    public void setToAccountCreatedDate(String toAccountCreatedDate) {
        this.toAccountCreatedDate = toAccountCreatedDate;
    }

    /**
     * fromAccountUpdatedDateを取得する。
     */
    public String getFromAccountUpdatedDate() {
        return fromAccountUpdatedDate;
    }

    /**
     * fromAccountUpdatedDateを格納する。
     */
    public void setFromAccountUpdatedDate(String fromAccountUpdatedDate) {
        this.fromAccountUpdatedDate = fromAccountUpdatedDate;
    }

    /**
     * toAccountUpdatedDateを取得する。
     */
    public String getToAccountUpdatedDate() {
        return toAccountUpdatedDate;
    }

    /**
     * toAccountUpdatedDateを格納する。
     */
    public void setToAccountUpdatedDate(String toAccountUpdatedDate) {
        this.toAccountUpdatedDate = toAccountUpdatedDate;
    }

}
