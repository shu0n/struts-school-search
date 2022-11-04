package admin.actionform.account;

import java.util.List;

import actionform.AccountExtendActionForm;

/**
 * 管理画面 アカウント一覧画面の検索で使用するデータを格納するためのアクションフォーム
 */
public class AdminSearchAccountActionForm extends AccountExtendActionForm {

    private String strAccountId; // アカウントID(文字列型)
    private String likeLastName; // 姓(類似)
    private String likeFirstName; // 名(類似)
    private String likeLastNameKana; // 姓(フリガナ、類似)
    private String likeFirstNameKana; // 名(フリガナ、類似)
    private String fromBirthYear; // 生年月日(年、From)
    private String fromBirthMonth; // 生年月日(月、From)
    private String fromBirthDay; // 生年月日(日、From)
    private String toBirthYear; // 生年月日(年、To)
    private String toBirthMonth; // 生年月日(月、To)
    private String toBirthDay; // 生年月日(日、To)
    private String[] sexIdArray; // 性別ID配列
    private String[] prefectureIdArray; // 都道府県ID配列
    private String likeMailAddress; // メールアドレス(類似)
    private String[] accountStatusArray; // アカウントステータス配列
    private String fromAccountCreatedYear; // アカウント作成日時(年、From)
    private String fromAccountCreatedMonth; // アカウント作成日時(月、From)
    private String fromAccountCreatedDay; // アカウント作成日時(日、From)
    private String toAccountCreatedYear; // アカウント作成日時(年、To)
    private String toAccountCreatedMonth; // アカウント作成日時(月、To)
    private String toAccountCreatedDay; // アカウント作成日時(日、To)
    private String fromAccountUpdatedYear; // アカウント更新日時(年、From)
    private String fromAccountUpdatedMonth; // アカウント更新日時(月、From)
    private String fromAccountUpdatedDay; // アカウント更新日時(日、From)
    private String toAccountUpdatedYear; // アカウント更新日時(年、To)
    private String toAccountUpdatedMonth; // アカウント更新日時(月、To)
    private String toAccountUpdatedDay; // アカウント更新日時(日、To)
    private List<AccountExtendActionForm> accountExtendFormList; // アカウントのリスト

    public AdminSearchAccountActionForm() {}

    /**
     * strAccountIdを取得する。
     */
    public String getStrAccountId() {
        return strAccountId;
    }

    /**
     * strAccountIdを格納する。
     */
    public void setStrAccountId(String strAccountId) {
        this.strAccountId = strAccountId;
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
     * fromBirthYearを取得する。
     */
    public String getFromBirthYear() {
        return fromBirthYear;
    }

    /**
     * fromBirthYearを格納する。
     */
    public void setFromBirthYear(String fromBirthYear) {
        this.fromBirthYear = fromBirthYear;
    }

    /**
     * fromBirthMonthを取得する。
     */
    public String getFromBirthMonth() {
        return fromBirthMonth;
    }

    /**
     * fromBirthMonthを格納する。
     */
    public void setFromBirthMonth(String fromBirthMonth) {
        this.fromBirthMonth = fromBirthMonth;
    }

    /**
     * fromBirthDayを取得する。
     */
    public String getFromBirthDay() {
        return fromBirthDay;
    }

    /**
     * fromBirthDayを格納する。
     */
    public void setFromBirthDay(String fromBirthDay) {
        this.fromBirthDay = fromBirthDay;
    }

    /**
     * toBirthYearを取得する。
     */
    public String getToBirthYear() {
        return toBirthYear;
    }

    /**
     * toBirthYearを格納する。
     */
    public void setToBirthYear(String toBirthYear) {
        this.toBirthYear = toBirthYear;
    }

    /**
     * toBirthMonthを取得する。
     */
    public String getToBirthMonth() {
        return toBirthMonth;
    }

    /**
     * toBirthMonthを格納する。
     */
    public void setToBirthMonth(String toBirthMonth) {
        this.toBirthMonth = toBirthMonth;
    }

    /**
     * toBirthDayを取得する。
     */
    public String getToBirthDay() {
        return toBirthDay;
    }

    /**
     * toBirthDayを格納する。
     */
    public void setToBirthDay(String toBirthDay) {
        this.toBirthDay = toBirthDay;
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
     * fromAccountCreatedYearを取得する。
     */
    public String getFromAccountCreatedYear() {
        return fromAccountCreatedYear;
    }

    /**
     * fromAccountCreatedYearを格納する。
     */
    public void setFromAccountCreatedYear(String fromAccountCreatedYear) {
        this.fromAccountCreatedYear = fromAccountCreatedYear;
    }

    /**
     * fromAccountCreatedMonthを取得する。
     */
    public String getFromAccountCreatedMonth() {
        return fromAccountCreatedMonth;
    }

    /**
     * fromAccountCreatedMonthを格納する。
     */
    public void setFromAccountCreatedMonth(String fromAccountCreatedMonth) {
        this.fromAccountCreatedMonth = fromAccountCreatedMonth;
    }

    /**
     * fromAccountCreatedDayを取得する。
     */
    public String getFromAccountCreatedDay() {
        return fromAccountCreatedDay;
    }

    /**
     * fromAccountCreatedDayを格納する。
     */
    public void setFromAccountCreatedDay(String fromAccountCreatedDay) {
        this.fromAccountCreatedDay = fromAccountCreatedDay;
    }

    /**
     * toAccountCreatedYearを取得する。
     */
    public String getToAccountCreatedYear() {
        return toAccountCreatedYear;
    }

    /**
     * toAccountCreatedYearを格納する。
     */
    public void setToAccountCreatedYear(String toAccountCreatedYear) {
        this.toAccountCreatedYear = toAccountCreatedYear;
    }

    /**
     * toAccountCreatedMonthを取得する。
     */
    public String getToAccountCreatedMonth() {
        return toAccountCreatedMonth;
    }

    /**
     * toAccountCreatedMonthを格納する。
     */
    public void setToAccountCreatedMonth(String toAccountCreatedMonth) {
        this.toAccountCreatedMonth = toAccountCreatedMonth;
    }

    /**
     * toAccountCreatedDayを取得する。
     */
    public String getToAccountCreatedDay() {
        return toAccountCreatedDay;
    }

    /**
     * toAccountCreatedDayを格納する。
     */
    public void setToAccountCreatedDay(String toAccountCreatedDay) {
        this.toAccountCreatedDay = toAccountCreatedDay;
    }

    /**
     * fromAccountUpdatedYearを取得する。
     */
    public String getFromAccountUpdatedYear() {
        return fromAccountUpdatedYear;
    }

    /**
     * fromAccountUpdatedYearを格納する。
     */
    public void setFromAccountUpdatedYear(String fromAccountUpdatedYear) {
        this.fromAccountUpdatedYear = fromAccountUpdatedYear;
    }

    /**
     * fromAccountUpdatedMonthを取得する。
     */
    public String getFromAccountUpdatedMonth() {
        return fromAccountUpdatedMonth;
    }

    /**
     * fromAccountUpdatedMonthを格納する。
     */
    public void setFromAccountUpdatedMonth(String fromAccountUpdatedMonth) {
        this.fromAccountUpdatedMonth = fromAccountUpdatedMonth;
    }

    /**
     * fromAccountUpdatedDayを取得する。
     */
    public String getFromAccountUpdatedDay() {
        return fromAccountUpdatedDay;
    }

    /**
     * fromAccountUpdatedDayを格納する。
     */
    public void setFromAccountUpdatedDay(String fromAccountUpdatedDay) {
        this.fromAccountUpdatedDay = fromAccountUpdatedDay;
    }

    /**
     * toAccountUpdatedYearを取得する。
     */
    public String getToAccountUpdatedYear() {
        return toAccountUpdatedYear;
    }

    /**
     * toAccountUpdatedYearを格納する。
     */
    public void setToAccountUpdatedYear(String toAccountUpdatedYear) {
        this.toAccountUpdatedYear = toAccountUpdatedYear;
    }

    /**
     * toAccountUpdatedMonthを取得する。
     */
    public String getToAccountUpdatedMonth() {
        return toAccountUpdatedMonth;
    }

    /**
     * toAccountUpdatedMonthを格納する。
     */
    public void setToAccountUpdatedMonth(String toAccountUpdatedMonth) {
        this.toAccountUpdatedMonth = toAccountUpdatedMonth;
    }

    /**
     * toAccountUpdatedDayを取得する。
     */
    public String getToAccountUpdatedDay() {
        return toAccountUpdatedDay;
    }

    /**
     * toAccountUpdatedDayを格納する。
     */
    public void setToAccountUpdatedDay(String toAccountUpdatedDay) {
        this.toAccountUpdatedDay = toAccountUpdatedDay;
    }

    /**
     * accountExtendFormListを取得する。
     */
    public List<AccountExtendActionForm> getAccountExtendFormList() {
        return accountExtendFormList;
    }

    /**
     * accountExtendFormListを格納する。
     */
    public void setAccountExtendFormList(List<AccountExtendActionForm> accountExtendFormList) {
        this.accountExtendFormList = accountExtendFormList;
    }

}
