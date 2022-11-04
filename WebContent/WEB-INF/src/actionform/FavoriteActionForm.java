package actionform;

import java.sql.Timestamp;

import org.apache.struts.validator.ValidatorForm;

/**
 * お気に入りテーブルのデータを格納するためのアクションフォーム
 */
public class FavoriteActionForm extends ValidatorForm {

    private int favoriteId; // お気に入りID
    private int accountId; // アカウントID
    private int schoolId; // スクールID
    private Timestamp favoriteAddedAt; // お気に入り追加日時

    public FavoriteActionForm() {}

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

    /**
     * favoriteAddedAtを取得する。
     */
    public Timestamp getFavoriteAddedAt() {
        return favoriteAddedAt;
    }

    /**
     * favoriteAddedAtを格納する。
     */
    public void setFavoriteAddedAt(Timestamp favoriteAddedAt) {
        this.favoriteAddedAt = favoriteAddedAt;
    }

}
