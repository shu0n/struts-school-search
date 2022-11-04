package actionform;

import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

/**
 * アカウント機能で使用するデータを格納するためのアクションフォーム
 */
public class AccountExtendActionForm extends AccountActionForm {

    private Map<Integer, String> sexMap; // 性別マップ
    private List<LabelValueBean> sexList; // 性別リスト
    private String sexName; // 性別名
    private List<LabelValueBean> birthYearList; // 生年月日(年)リスト
    private String birthYear; // 生年月日(年)
    private List<LabelValueBean> birthMonthList; // 生年月日(月)リスト
    private String birthMonth; // 生年月日(月)
    private List<LabelValueBean> birthDayList; // 生年月日(日)リスト
    private String birthDay; // 生年月日(日)
    private String strBirthDate; // 生年月日(文字列型)
    private Map<Integer, String> prefectureMap; // 都道府県マップ
    private List<LabelValueBean> prefectureList; // 都道府県リスト
    private String prefectureName; // 都道府県名
    private FormFile profileImageFile; // プロフィール画像
    private String profileImageFilePath; // プロフィール画像のパス
    private Map<String, String> accountStatusMap; // アカウントステータスマップ
    private List<LabelValueBean> accountStatusList; // アカウントステータスリスト
    private String accountStatusName; // アカウントステータス名
    private String strAccountCreatedAt; // アカウント作成日時(文字列型)
    private String strAccountUpdatedAt; // アカウント更新日時(文字列型)

    public AccountExtendActionForm() {}

    /**
     * sexMapを取得する。
     */
    public Map<Integer, String> getSexMap() {
        return sexMap;
    }

    /**
     * sexMapを格納する。
     */
    public void setSexMap(Map<Integer, String> sexMap) {
        this.sexMap = sexMap;
    }

    /**
     * sexListを取得する。
     */
    public List<LabelValueBean> getSexList() {
        return sexList;
    }

    /**
     * sexListを格納する。
     */
    public void setSexList(List<LabelValueBean> sexList) {
        this.sexList = sexList;
    }

    /**
     * sexNameを取得する。
     */
    public String getSexName() {
        return sexName;
    }

    /**
     * sexNameを格納する。
     */
    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    /**
     * birthYearListを取得する。
     */
    public List<LabelValueBean> getBirthYearList() {
        return birthYearList;
    }

    /**
     * birthYearListを格納する。
     */
    public void setBirthYearList(List<LabelValueBean> birthYearList) {
        this.birthYearList = birthYearList;
    }

    /**
     * birthYearを取得する。
     */
    public String getBirthYear() {
        return birthYear;
    }

    /**
     * birthYearを格納する。
     */
    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    /**
     * birthMonthListを取得する。
     */
    public List<LabelValueBean> getBirthMonthList() {
        return birthMonthList;
    }

    /**
     * birthMonthListを格納する。
     */
    public void setBirthMonthList(List<LabelValueBean> birthMonthList) {
        this.birthMonthList = birthMonthList;
    }

    /**
     * birthMonthを取得する。
     */
    public String getBirthMonth() {
        return birthMonth;
    }

    /**
     * birthMonthを格納する。
     */
    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    /**
     * birthDayListを取得する。
     */
    public List<LabelValueBean> getBirthDayList() {
        return birthDayList;
    }

    /**
     * birthDayListを格納する。
     */
    public void setBirthDayList(List<LabelValueBean> birthDayList) {
        this.birthDayList = birthDayList;
    }

    /**
     * birthDayを取得する。
     */
    public String getBirthDay() {
        return birthDay;
    }

    /**
     * birthDayを格納する。
     */
    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    /**
     * strBirthDateを取得する。
     */
    public String getStrBirthDate() {
        return strBirthDate;
    }

    /**
     * strBirthDateを格納する。
     */
    public void setStrBirthDate(String strBirthDate) {
        this.strBirthDate = strBirthDate;
    }

    /**
     * prefectureMapを取得する。
     */
    public Map<Integer, String> getPrefectureMap() {
        return prefectureMap;
    }

    /**
     * prefectureMapを格納する。
     */
    public void setPrefectureMap(Map<Integer, String> prefectureMap) {
        this.prefectureMap = prefectureMap;
    }

    /**
     * prefectureListを取得する。
     */
    public List<LabelValueBean> getPrefectureList() {
        return prefectureList;
    }

    /**
     * prefectureListを格納する。
     */
    public void setPrefectureList(List<LabelValueBean> prefectureList) {
        this.prefectureList = prefectureList;
    }

    /**
     * prefectureNameを取得する。
     */
    public String getPrefectureName() {
        return prefectureName;
    }

    /**
     * prefectureNameを格納する。
     */
    public void setPrefectureName(String prefectureName) {
        this.prefectureName = prefectureName;
    }

    /**
     * profileImageFileを取得する。
     */
    public FormFile getProfileImageFile() {
        return profileImageFile;
    }

    /**
     * profileImageFileを格納する。
     */
    public void setProfileImageFile(FormFile profileImageFile) {
        this.profileImageFile = profileImageFile;
    }

    /**
     * profileImageFilePathを取得する。
     */
    public String getProfileImageFilePath() {
        return profileImageFilePath;
    }

    /**
     * profileImageFilePathを格納する。
     */
    public void setProfileImageFilePath(String profileImageFilePath) {
        this.profileImageFilePath = profileImageFilePath;
    }

    /**
     * accountStatusMapを取得する。
     */
    public Map<String, String> getAccountStatusMap() {
        return accountStatusMap;
    }

    /**
     * accountStatusMapを格納する。
     */
    public void setAccountStatusMap(Map<String, String> accountStatusMap) {
        this.accountStatusMap = accountStatusMap;
    }

    /**
     * accountStatusListを取得する。
     */
    public List<LabelValueBean> getAccountStatusList() {
        return accountStatusList;
    }

    /**
     * accountStatusListを格納する。
     */
    public void setAccountStatusList(List<LabelValueBean> accountStatusList) {
        this.accountStatusList = accountStatusList;
    }

    /**
     * accountStatusNameを取得する。
     */
    public String getAccountStatusName() {
        return accountStatusName;
    }

    /**
     * accountStatusNameを格納する。
     */
    public void setAccountStatusName(String accountStatusName) {
        this.accountStatusName = accountStatusName;
    }

    /**
     * strAccountCreatedAtを取得する。
     */
    public String getStrAccountCreatedAt() {
        return strAccountCreatedAt;
    }

    /**
     * strAccountCreatedAtを格納する。
     */
    public void setStrAccountCreatedAt(String strAccountCreatedAt) {
        this.strAccountCreatedAt = strAccountCreatedAt;
    }

    /**
     * strAccountUpdatedAtを取得する。
     */
    public String getStrAccountUpdatedAt() {
        return strAccountUpdatedAt;
    }

    /**
     * strAccountUpdatedAtを格納する。
     */
    public void setStrAccountUpdatedAt(String strAccountUpdatedAt) {
        this.strAccountUpdatedAt = strAccountUpdatedAt;
    }

}
