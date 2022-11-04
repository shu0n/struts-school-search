package dao.favorite.sql;

import org.apache.struts.action.ActionForm;

/**
 * お気に入りテーブルから取得するレコードの条件に使用するデータを格納するためのアクションフォーム
 */
public class FavoriteSelectWhereActionForm extends ActionForm {

    private int favoriteId; // お気に入りID
    private int accountId; // アカウントID
    private int schoolId; // スクールID

    public FavoriteSelectWhereActionForm() {}

    /**
     * favoriteIdを取得する。
     */
    public int getFavoriteId() {
        return favoriteId;
    }

    /**
     * favoriteIdを格納する。
     */
    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

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
     * schoolIdを取得する。
     */
    public int getSchoolId() {
        return schoolId;
    }

    /**
     * schoolIdを格納する。
     */
    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

}
