package actionform;

import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

/**
 * スクール機能で使用するデータを格納するためのアクションフォーム
 */
public class SchoolExtendActionForm extends SchoolActionForm {

    private String registrantLastName; // 登録者姓
    private String registrantFirstName; // 登録者名
    private String registrantLastNameKana; // 登録者姓(フリガナ)
    private String registrantFirstNameKana; // 登録者名(フリガナ)
    private String profileImageFileName; // プロフィール画像のファイル名
    private String profileImageFilePath; // プロフィール画像のパス
    private String selfIntroduction; // 自己紹介
    private Map<Integer, String> schoolCategoryMap; // スクールカテゴリーマップ
    private List<LabelValueBean> schoolCategoryList; // スクールカテゴリーリスト
    private String schoolCategoryName; // スクールカテゴリー名
    private Map<Integer, String> schoolPrefectureMap; // スクール都道府県マップ
    private List<LabelValueBean> schoolPrefectureList; // スクール都道府県リスト
    private String schoolPrefectureName; // スクール都道府県名
    private String strSchoolFee; // スクール費用(文字列型)
    private Map<String, String> schoolReleaseProprietyMap; // スクール公開可否マップ
    private List<LabelValueBean> schoolReleaseProprietyList; // スクール公開可否リスト
    private String schoolReleaseProprietyName; // スクール公開可否名
    private Map<String, String> schoolEntryProprietyMap; // スクール申込可否マップ
    private List<LabelValueBean> schoolEntryProprietyList; // スクール申込可否リスト
    private String schoolEntryProprietyName; // スクール申込可否名
    private FormFile summaryImageFile; // 一覧画面画像
    private FormFile detailImage1File; // 詳細画面画像1
    private FormFile detailImage2File; // 詳細画面画像2
    private FormFile detailImage3File; // 詳細画面画像3
    private FormFile detailImage4File; // 詳細画面画像4
    private FormFile detailImage5File; // 詳細画面画像5
    private FormFile detailImage6File; // 詳細画面画像6
    private FormFile detailImage7File; // 詳細画面画像7
    private FormFile detailImage8File; // 詳細画面画像8
    private String summaryImageFilePath; // 一覧画面画像のパス
    private String detailImage1FilePath; // 詳細画面画像1のパス
    private String detailImage2FilePath; // 詳細画面画像2のパス
    private String detailImage3FilePath; // 詳細画面画像3のパス
    private String detailImage4FilePath; // 詳細画面画像4のパス
    private String detailImage5FilePath; // 詳細画面画像5のパス
    private String detailImage6FilePath; // 詳細画面画像6のパス
    private String detailImage7FilePath; // 詳細画面画像7のパス
    private String detailImage8FilePath; // 詳細画面画像8のパス
    private String summaryImageFileNameUpdate; // 一覧画面画像ファイル名(更新)
    private String detailImage1FileNameUpdate; // 詳細画面画像1ファイル名(更新)
    private String detailImage2FileNameUpdate; // 詳細画面画像2ファイル名(更新)
    private String detailImage3FileNameUpdate; // 詳細画面画像3ファイル名(更新)
    private String detailImage4FileNameUpdate; // 詳細画面画像4ファイル名(更新)
    private String detailImage5FileNameUpdate; // 詳細画面画像5ファイル名(更新)
    private String detailImage6FileNameUpdate; // 詳細画面画像6ファイル名(更新)
    private String detailImage7FileNameUpdate; // 詳細画面画像7ファイル名(更新)
    private String detailImage8FileNameUpdate; // 詳細画面画像8ファイル名(更新)
    private boolean deleteSummaryImageFileFlag; // 一覧画面画像削除フラグ
    private boolean deleteDetailImage1FileFlag; // 詳細画面画像1削除フラグ
    private boolean deleteDetailImage2FileFlag; // 詳細画面画像2削除フラグ
    private boolean deleteDetailImage3FileFlag; // 詳細画面画像3削除フラグ
    private boolean deleteDetailImage4FileFlag; // 詳細画面画像4削除フラグ
    private boolean deleteDetailImage5FileFlag; // 詳細画面画像5削除フラグ
    private boolean deleteDetailImage6FileFlag; // 詳細画面画像6削除フラグ
    private boolean deleteDetailImage7FileFlag; // 詳細画面画像7削除フラグ
    private boolean deleteDetailImage8FileFlag; // 詳細画面画像8削除フラグ
    private String strSchoolRegisteredAt; // スクール登録日時(文字列型)
    private String strSchoolUpdatedAt; // スクール更新日時(文字列型)
    private int allEntryNum; // 申込件数

    public SchoolExtendActionForm() {}

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
     * schoolCategoryMapを取得する。
     */
    public Map<Integer, String> getSchoolCategoryMap() {
        return schoolCategoryMap;
    }

    /**
     * schoolCategoryMapを格納する。
     */
    public void setSchoolCategoryMap(Map<Integer, String> schoolCategoryMap) {
        this.schoolCategoryMap = schoolCategoryMap;
    }

    /**
     * schoolCategoryListを取得する。
     */
    public List<LabelValueBean> getSchoolCategoryList() {
        return schoolCategoryList;
    }

    /**
     * schoolCategoryListを格納する。
     */
    public void setSchoolCategoryList(List<LabelValueBean> schoolCategoryList) {
        this.schoolCategoryList = schoolCategoryList;
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
     * schoolPrefectureMapを取得する。
     */
    public Map<Integer, String> getSchoolPrefectureMap() {
        return schoolPrefectureMap;
    }

    /**
     * schoolPrefectureMapを格納する。
     */
    public void setSchoolPrefectureMap(Map<Integer, String> schoolPrefectureMap) {
        this.schoolPrefectureMap = schoolPrefectureMap;
    }

    /**
     * schoolPrefectureListを取得する。
     */
    public List<LabelValueBean> getSchoolPrefectureList() {
        return schoolPrefectureList;
    }

    /**
     * schoolPrefectureListを格納する。
     */
    public void setSchoolPrefectureList(List<LabelValueBean> schoolPrefectureList) {
        this.schoolPrefectureList = schoolPrefectureList;
    }

    /**
     * schoolPrefectureNameを取得する。
     */
    public String getSchoolPrefectureName() {
        return schoolPrefectureName;
    }

    /**
     * schoolPrefectureNameを格納する。
     */
    public void setSchoolPrefectureName(String schoolPrefectureName) {
        this.schoolPrefectureName = schoolPrefectureName;
    }

    /**
     * strSchoolFeeを取得する。
     */
    public String getStrSchoolFee() {
        return strSchoolFee;
    }

    /**
     * strSchoolFeeを格納する。
     */
    public void setStrSchoolFee(String strSchoolFee) {
        this.strSchoolFee = strSchoolFee;
    }

    /**
     * schoolReleaseProprietyMapを取得する。
     */
    public Map<String, String> getSchoolReleaseProprietyMap() {
        return schoolReleaseProprietyMap;
    }

    /**
     * schoolReleaseProprietyMapを格納する。
     */
    public void setSchoolReleaseProprietyMap(Map<String, String> schoolReleaseProprietyMap) {
        this.schoolReleaseProprietyMap = schoolReleaseProprietyMap;
    }

    /**
     * schoolReleaseProprietyListを取得する。
     */
    public List<LabelValueBean> getSchoolReleaseProprietyList() {
        return schoolReleaseProprietyList;
    }

    /**
     * schoolReleaseProprietyListを格納する。
     */
    public void setSchoolReleaseProprietyList(List<LabelValueBean> schoolReleaseProprietyList) {
        this.schoolReleaseProprietyList = schoolReleaseProprietyList;
    }

    /**
     * schoolReleaseProprietyNameを取得する。
     */
    public String getSchoolReleaseProprietyName() {
        return schoolReleaseProprietyName;
    }

    /**
     * schoolReleaseProprietyNameを格納する。
     */
    public void setSchoolReleaseProprietyName(String schoolReleaseProprietyName) {
        this.schoolReleaseProprietyName = schoolReleaseProprietyName;
    }

    /**
     * schoolEntryProprietyMapを取得する。
     */
    public Map<String, String> getSchoolEntryProprietyMap() {
        return schoolEntryProprietyMap;
    }

    /**
     * schoolEntryProprietyMapを格納する。
     */
    public void setSchoolEntryProprietyMap(Map<String, String> schoolEntryProprietyMap) {
        this.schoolEntryProprietyMap = schoolEntryProprietyMap;
    }

    /**
     * schoolEntryProprietyListを取得する。
     */
    public List<LabelValueBean> getSchoolEntryProprietyList() {
        return schoolEntryProprietyList;
    }

    /**
     * schoolEntryProprietyListを格納する。
     */
    public void setSchoolEntryProprietyList(List<LabelValueBean> schoolEntryProprietyList) {
        this.schoolEntryProprietyList = schoolEntryProprietyList;
    }

    /**
     * schoolEntryProprietyNameを取得する。
     */
    public String getSchoolEntryProprietyName() {
        return schoolEntryProprietyName;
    }

    /**
     * schoolEntryProprietyNameを格納する。
     */
    public void setSchoolEntryProprietyName(String schoolEntryProprietyName) {
        this.schoolEntryProprietyName = schoolEntryProprietyName;
    }

    /**
     * summaryImageFileを取得する。
     */
    public FormFile getSummaryImageFile() {
        return summaryImageFile;
    }

    /**
     * summaryImageFileを格納する。
     */
    public void setSummaryImageFile(FormFile summaryImageFile) {
        this.summaryImageFile = summaryImageFile;
    }

    /**
     * detailImage1Fileを取得する。
     */
    public FormFile getDetailImage1File() {
        return detailImage1File;
    }

    /**
     * detailImage1Fileを格納する。
     */
    public void setDetailImage1File(FormFile detailImage1File) {
        this.detailImage1File = detailImage1File;
    }

    /**
     * detailImage2Fileを取得する。
     */
    public FormFile getDetailImage2File() {
        return detailImage2File;
    }

    /**
     * detailImage2Fileを格納する。
     */
    public void setDetailImage2File(FormFile detailImage2File) {
        this.detailImage2File = detailImage2File;
    }

    /**
     * detailImage3Fileを取得する。
     */
    public FormFile getDetailImage3File() {
        return detailImage3File;
    }

    /**
     * detailImage3Fileを格納する。
     */
    public void setDetailImage3File(FormFile detailImage3File) {
        this.detailImage3File = detailImage3File;
    }

    /**
     * detailImage4Fileを取得する。
     */
    public FormFile getDetailImage4File() {
        return detailImage4File;
    }

    /**
     * detailImage4Fileを格納する。
     */
    public void setDetailImage4File(FormFile detailImage4File) {
        this.detailImage4File = detailImage4File;
    }

    /**
     * detailImage5Fileを取得する。
     */
    public FormFile getDetailImage5File() {
        return detailImage5File;
    }

    /**
     * detailImage5Fileを格納する。
     */
    public void setDetailImage5File(FormFile detailImage5File) {
        this.detailImage5File = detailImage5File;
    }

    /**
     * detailImage6Fileを取得する。
     */
    public FormFile getDetailImage6File() {
        return detailImage6File;
    }

    /**
     * detailImage6Fileを格納する。
     */
    public void setDetailImage6File(FormFile detailImage6File) {
        this.detailImage6File = detailImage6File;
    }

    /**
     * detailImage7Fileを取得する。
     */
    public FormFile getDetailImage7File() {
        return detailImage7File;
    }

    /**
     * detailImage7Fileを格納する。
     */
    public void setDetailImage7File(FormFile detailImage7File) {
        this.detailImage7File = detailImage7File;
    }

    /**
     * detailImage8Fileを取得する。
     */
    public FormFile getDetailImage8File() {
        return detailImage8File;
    }

    /**
     * detailImage8Fileを格納する。
     */
    public void setDetailImage8File(FormFile detailImage8File) {
        this.detailImage8File = detailImage8File;
    }

    /**
     * summaryImageFilePathを取得する。
     */
    public String getSummaryImageFilePath() {
        return summaryImageFilePath;
    }

    /**
     * summaryImageFilePathを格納する。
     */
    public void setSummaryImageFilePath(String summaryImageFilePath) {
        this.summaryImageFilePath = summaryImageFilePath;
    }

    /**
     * detailImage1FilePathを取得する。
     */
    public String getDetailImage1FilePath() {
        return detailImage1FilePath;
    }

    /**
     * detailImage1FilePathを格納する。
     */
    public void setDetailImage1FilePath(String detailImage1FilePath) {
        this.detailImage1FilePath = detailImage1FilePath;
    }

    /**
     * detailImage2FilePathを取得する。
     */
    public String getDetailImage2FilePath() {
        return detailImage2FilePath;
    }

    /**
     * detailImage2FilePathを格納する。
     */
    public void setDetailImage2FilePath(String detailImage2FilePath) {
        this.detailImage2FilePath = detailImage2FilePath;
    }

    /**
     * detailImage3FilePathを取得する。
     */
    public String getDetailImage3FilePath() {
        return detailImage3FilePath;
    }

    /**
     * detailImage3FilePathを格納する。
     */
    public void setDetailImage3FilePath(String detailImage3FilePath) {
        this.detailImage3FilePath = detailImage3FilePath;
    }

    /**
     * detailImage4FilePathを取得する。
     */
    public String getDetailImage4FilePath() {
        return detailImage4FilePath;
    }

    /**
     * detailImage4FilePathを格納する。
     */
    public void setDetailImage4FilePath(String detailImage4FilePath) {
        this.detailImage4FilePath = detailImage4FilePath;
    }

    /**
     * detailImage5FilePathを取得する。
     */
    public String getDetailImage5FilePath() {
        return detailImage5FilePath;
    }

    /**
     * detailImage5FilePathを格納する。
     */
    public void setDetailImage5FilePath(String detailImage5FilePath) {
        this.detailImage5FilePath = detailImage5FilePath;
    }

    /**
     * detailImage6FilePathを取得する。
     */
    public String getDetailImage6FilePath() {
        return detailImage6FilePath;
    }

    /**
     * detailImage6FilePathを格納する。
     */
    public void setDetailImage6FilePath(String detailImage6FilePath) {
        this.detailImage6FilePath = detailImage6FilePath;
    }

    /**
     * detailImage7FilePathを取得する。
     */
    public String getDetailImage7FilePath() {
        return detailImage7FilePath;
    }

    /**
     * detailImage7FilePathを格納する。
     */
    public void setDetailImage7FilePath(String detailImage7FilePath) {
        this.detailImage7FilePath = detailImage7FilePath;
    }

    /**
     * detailImage8FilePathを取得する。
     */
    public String getDetailImage8FilePath() {
        return detailImage8FilePath;
    }

    /**
     * detailImage8FilePathを格納する。
     */
    public void setDetailImage8FilePath(String detailImage8FilePath) {
        this.detailImage8FilePath = detailImage8FilePath;
    }

    /**
     * summaryImageFileNameUpdateを取得する。
     */
    public String getSummaryImageFileNameUpdate() {
        return summaryImageFileNameUpdate;
    }

    /**
     * summaryImageFileNameUpdateを格納する。
     */
    public void setSummaryImageFileNameUpdate(String summaryImageFileNameUpdate) {
        this.summaryImageFileNameUpdate = summaryImageFileNameUpdate;
    }

    /**
     * detailImage1FileNameUpdateを取得する。
     */
    public String getDetailImage1FileNameUpdate() {
        return detailImage1FileNameUpdate;
    }

    /**
     * detailImage1FileNameUpdateを格納する。
     */
    public void setDetailImage1FileNameUpdate(String detailImage1FileNameUpdate) {
        this.detailImage1FileNameUpdate = detailImage1FileNameUpdate;
    }

    /**
     * detailImage2FileNameUpdateを取得する。
     */
    public String getDetailImage2FileNameUpdate() {
        return detailImage2FileNameUpdate;
    }

    /**
     * detailImage2FileNameUpdateを格納する。
     */
    public void setDetailImage2FileNameUpdate(String detailImage2FileNameUpdate) {
        this.detailImage2FileNameUpdate = detailImage2FileNameUpdate;
    }

    /**
     * detailImage3FileNameUpdateを取得する。
     */
    public String getDetailImage3FileNameUpdate() {
        return detailImage3FileNameUpdate;
    }

    /**
     * detailImage3FileNameUpdateを格納する。
     */
    public void setDetailImage3FileNameUpdate(String detailImage3FileNameUpdate) {
        this.detailImage3FileNameUpdate = detailImage3FileNameUpdate;
    }

    /**
     * detailImage4FileNameUpdateを取得する。
     */
    public String getDetailImage4FileNameUpdate() {
        return detailImage4FileNameUpdate;
    }

    /**
     * detailImage4FileNameUpdateを格納する。
     */
    public void setDetailImage4FileNameUpdate(String detailImage4FileNameUpdate) {
        this.detailImage4FileNameUpdate = detailImage4FileNameUpdate;
    }

    /**
     * detailImage5FileNameUpdateを取得する。
     */
    public String getDetailImage5FileNameUpdate() {
        return detailImage5FileNameUpdate;
    }

    /**
     * detailImage5FileNameUpdateを格納する。
     */
    public void setDetailImage5FileNameUpdate(String detailImage5FileNameUpdate) {
        this.detailImage5FileNameUpdate = detailImage5FileNameUpdate;
    }

    /**
     * detailImage6FileNameUpdateを取得する。
     */
    public String getDetailImage6FileNameUpdate() {
        return detailImage6FileNameUpdate;
    }

    /**
     * detailImage6FileNameUpdateを格納する。
     */
    public void setDetailImage6FileNameUpdate(String detailImage6FileNameUpdate) {
        this.detailImage6FileNameUpdate = detailImage6FileNameUpdate;
    }

    /**
     * detailImage7FileNameUpdateを取得する。
     */
    public String getDetailImage7FileNameUpdate() {
        return detailImage7FileNameUpdate;
    }

    /**
     * detailImage7FileNameUpdateを格納する。
     */
    public void setDetailImage7FileNameUpdate(String detailImage7FileNameUpdate) {
        this.detailImage7FileNameUpdate = detailImage7FileNameUpdate;
    }

    /**
     * detailImage8FileNameUpdateを取得する。
     */
    public String getDetailImage8FileNameUpdate() {
        return detailImage8FileNameUpdate;
    }

    /**
     * detailImage8FileNameUpdateを格納する。
     */
    public void setDetailImage8FileNameUpdate(String detailImage8FileNameUpdate) {
        this.detailImage8FileNameUpdate = detailImage8FileNameUpdate;
    }

    /**
     * deleteSummaryImageFileFlagを取得する。
     */
    public boolean isDeleteSummaryImageFileFlag() {
        return deleteSummaryImageFileFlag;
    }

    /**
     * deleteSummaryImageFileFlagを格納する。
     */
    public void setDeleteSummaryImageFileFlag(boolean deleteSummaryImageFileFlag) {
        this.deleteSummaryImageFileFlag = deleteSummaryImageFileFlag;
    }

    /**
     * deleteDetailImage1FileFlagを取得する。
     */
    public boolean isDeleteDetailImage1FileFlag() {
        return deleteDetailImage1FileFlag;
    }

    /**
     * deleteDetailImage1FileFlagを格納する。
     */
    public void setDeleteDetailImage1FileFlag(boolean deleteDetailImage1FileFlag) {
        this.deleteDetailImage1FileFlag = deleteDetailImage1FileFlag;
    }

    /**
     * deleteDetailImage2FileFlagを取得する。
     */
    public boolean isDeleteDetailImage2FileFlag() {
        return deleteDetailImage2FileFlag;
    }

    /**
     * deleteDetailImage2FileFlagを格納する。
     */
    public void setDeleteDetailImage2FileFlag(boolean deleteDetailImage2FileFlag) {
        this.deleteDetailImage2FileFlag = deleteDetailImage2FileFlag;
    }

    /**
     * deleteDetailImage3FileFlagを取得する。
     */
    public boolean isDeleteDetailImage3FileFlag() {
        return deleteDetailImage3FileFlag;
    }

    /**
     * deleteDetailImage3FileFlagを格納する。
     */
    public void setDeleteDetailImage3FileFlag(boolean deleteDetailImage3FileFlag) {
        this.deleteDetailImage3FileFlag = deleteDetailImage3FileFlag;
    }

    /**
     * deleteDetailImage4FileFlagを取得する。
     */
    public boolean isDeleteDetailImage4FileFlag() {
        return deleteDetailImage4FileFlag;
    }

    /**
     * deleteDetailImage4FileFlagを格納する。
     */
    public void setDeleteDetailImage4FileFlag(boolean deleteDetailImage4FileFlag) {
        this.deleteDetailImage4FileFlag = deleteDetailImage4FileFlag;
    }

    /**
     * deleteDetailImage5FileFlagを取得する。
     */
    public boolean isDeleteDetailImage5FileFlag() {
        return deleteDetailImage5FileFlag;
    }

    /**
     * deleteDetailImage5FileFlagを格納する。
     */
    public void setDeleteDetailImage5FileFlag(boolean deleteDetailImage5FileFlag) {
        this.deleteDetailImage5FileFlag = deleteDetailImage5FileFlag;
    }

    /**
     * deleteDetailImage6FileFlagを取得する。
     */
    public boolean isDeleteDetailImage6FileFlag() {
        return deleteDetailImage6FileFlag;
    }

    /**
     * deleteDetailImage6FileFlagを格納する。
     */
    public void setDeleteDetailImage6FileFlag(boolean deleteDetailImage6FileFlag) {
        this.deleteDetailImage6FileFlag = deleteDetailImage6FileFlag;
    }

    /**
     * deleteDetailImage7FileFlagを取得する。
     */
    public boolean isDeleteDetailImage7FileFlag() {
        return deleteDetailImage7FileFlag;
    }

    /**
     * deleteDetailImage7FileFlagを格納する。
     */
    public void setDeleteDetailImage7FileFlag(boolean deleteDetailImage7FileFlag) {
        this.deleteDetailImage7FileFlag = deleteDetailImage7FileFlag;
    }

    /**
     * deleteDetailImage8FileFlagを取得する。
     */
    public boolean isDeleteDetailImage8FileFlag() {
        return deleteDetailImage8FileFlag;
    }

    /**
     * deleteDetailImage8FileFlagを格納する。
     */
    public void setDeleteDetailImage8FileFlag(boolean deleteDetailImage8FileFlag) {
        this.deleteDetailImage8FileFlag = deleteDetailImage8FileFlag;
    }

    /**
     * strSchoolRegisteredAtを取得する。
     */
    public String getStrSchoolRegisteredAt() {
        return strSchoolRegisteredAt;
    }

    /**
     * strSchoolRegisteredAtを格納する。
     */
    public void setStrSchoolRegisteredAt(String strSchoolRegisteredAt) {
        this.strSchoolRegisteredAt = strSchoolRegisteredAt;
    }

    /**
     * strSchoolUpdatedAtを取得する。
     */
    public String getStrSchoolUpdatedAt() {
        return strSchoolUpdatedAt;
    }

    /**
     * strSchoolUpdatedAtを格納する。
     */
    public void setStrSchoolUpdatedAt(String strSchoolUpdatedAt) {
        this.strSchoolUpdatedAt = strSchoolUpdatedAt;
    }

    /**
     * allEntryNumを取得する。
     */
    public int getAllEntryNum() {
        return allEntryNum;
    }

    /**
     * allEntryNumを格納する。
     */
    public void setAllEntryNum(int allEntryNum) {
        this.allEntryNum = allEntryNum;
    }

}
