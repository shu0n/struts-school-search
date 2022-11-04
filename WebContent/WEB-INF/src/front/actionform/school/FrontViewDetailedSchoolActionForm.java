package front.actionform.school;

import java.util.List;

import actionform.SchoolExtendActionForm;

/**
 * スクール詳細画面で使用するデータを格納するためのアクションフォーム
 */
public class FrontViewDetailedSchoolActionForm extends SchoolExtendActionForm {

    private List<String> detailImageFilePathList; // 詳細画面画像リスト
    private boolean entriedFlag; // 申込済フラグ
    private boolean favoriteFlag; // お気に入り追加済フラグ

    public FrontViewDetailedSchoolActionForm() {}

    /**
     * detailImageFilePathListを取得する。
     */
    public List<String> getDetailImageFilePathList() {
        return detailImageFilePathList;
    }

    /**
     * detailImageFilePathListを格納する。
     */
    public void setDetailImageFilePathList(List<String> detailImageFilePathList) {
        this.detailImageFilePathList = detailImageFilePathList;
    }

    /**
     * entriedFlagを取得する。
     */
    public boolean isEntriedFlag() {
        return entriedFlag;
    }

    /**
     * entriedFlagを格納する。
     */
    public void setEntriedFlag(boolean entriedFlag) {
        this.entriedFlag = entriedFlag;
    }

    public boolean isFavoriteFlag() {
        return favoriteFlag;
    }

    public void setFavoriteFlag(boolean favoriteFlag) {
        this.favoriteFlag = favoriteFlag;
    }

}
