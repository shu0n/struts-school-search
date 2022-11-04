package dao.favorite.sql;

import org.apache.struts.action.ActionForm;

/**
 * お気に入りテーブルに作成するレコードのデータを格納するためのアクションフォーム
 */
public class FavoriteInsertDataActionForm extends ActionForm {

    private int accountId; // アカウントID
    private int schoolId; // スクールID

    public FavoriteInsertDataActionForm() {}

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
