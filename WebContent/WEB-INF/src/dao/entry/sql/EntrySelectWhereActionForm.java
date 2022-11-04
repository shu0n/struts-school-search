package dao.entry.sql;

import org.apache.struts.action.ActionForm;

/**
 * 申込テーブルから取得するレコードの条件に使用するデータを格納するためのアクションフォーム
 */
public class EntrySelectWhereActionForm extends ActionForm {

    private int entryId; // 申込ID
    private String[] entryIdArray; // 申込ID配列
    private int entrySchoolId; // 申込先スクールID
    private String[] entrySchoolIdArray; // 申込者スクールID配列
    private int applicantAccountId; // 申込者アカウントID
    private String[] applicantAccountIdArray; // 申込者アカウントID配列
    private int entryStatusId; // 申込ステータスID
    private String[] entryStatusIdArray; // 申込ステータスID配列
    private String fromEntriedDate; // 申込日時(From)
    private String toEntriedDate; // 申込日時(To)
    private String fromEntryUpdatedDate; // 申込更新日時(From)
    private String toEntryUpdatedDate; // 申込更新日時(To)

    public EntrySelectWhereActionForm() {}

    /**
     * entryIdを取得する。
     */
    public int getEntryId() {
        return entryId;
    }

    /**
     * entryIdを格納する。
     */
    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    /**
     * entryIdArrayを取得する。
     */
    public String[] getEntryIdArray() {
        return entryIdArray;
    }

    /**
     * entryIdArrayを格納する。
     */
    public void setEntryIdArray(String[] entryIdArray) {
        this.entryIdArray = entryIdArray;
    }

    /**
     * entrySchoolIdを取得する。
     */
    public int getEntrySchoolId() {
        return entrySchoolId;
    }

    /**
     * entrySchoolIdを格納する。
     */
    public void setEntrySchoolId(int entrySchoolId) {
        this.entrySchoolId = entrySchoolId;
    }

    /**
     * entrySchoolIdArrayを取得する。
     */
    public String[] getEntrySchoolIdArray() {
        return entrySchoolIdArray;
    }

    /**
     * entrySchoolIdArrayを格納する。
     */
    public void setEntrySchoolIdArray(String[] entrySchoolIdArray) {
        this.entrySchoolIdArray = entrySchoolIdArray;
    }

    /**
     * applicantAccountIdを取得する。
     */
    public int getApplicantAccountId() {
        return applicantAccountId;
    }

    /**
     * applicantAccountIdを格納する。
     */
    public void setApplicantAccountId(int applicantAccountId) {
        this.applicantAccountId = applicantAccountId;
    }

    /**
     * applicantAccountIdArrayを取得する。
     */
    public String[] getApplicantAccountIdArray() {
        return applicantAccountIdArray;
    }

    /**
     * applicantAccountIdArrayを格納する。
     */
    public void setApplicantAccountIdArray(String[] applicantAccountIdArray) {
        this.applicantAccountIdArray = applicantAccountIdArray;
    }

    /**
     * entryStatusIdを取得する。
     */
    public int getEntryStatusId() {
        return entryStatusId;
    }

    /**
     * entryStatusIdを格納する。
     */
    public void setEntryStatusId(int entryStatusId) {
        this.entryStatusId = entryStatusId;
    }

    /**
     * entryStatusIdArrayを取得する。
     */
    public String[] getEntryStatusIdArray() {
        return entryStatusIdArray;
    }

    /**
     * entryStatusIdArrayを格納する。
     */
    public void setEntryStatusIdArray(String[] entryStatusIdArray) {
        this.entryStatusIdArray = entryStatusIdArray;
    }

    /**
     * fromEntriedDateを取得する。
     */
    public String getFromEntriedDate() {
        return fromEntriedDate;
    }

    /**
     * fromEntriedDateを格納する。
     */
    public void setFromEntriedDate(String fromEntriedDate) {
        this.fromEntriedDate = fromEntriedDate;
    }

    /**
     * toEntriedDateを取得する。
     */
    public String getToEntriedDate() {
        return toEntriedDate;
    }

    /**
     * toEntriedDateを格納する。
     */
    public void setToEntriedDate(String toEntriedDate) {
        this.toEntriedDate = toEntriedDate;
    }

    /**
     * fromEntryUpdatedDateを取得する。
     */
    public String getFromEntryUpdatedDate() {
        return fromEntryUpdatedDate;
    }

    /**
     * fromEntryUpdatedDateを格納する。
     */
    public void setFromEntryUpdatedDate(String fromEntryUpdatedDate) {
        this.fromEntryUpdatedDate = fromEntryUpdatedDate;
    }

    /**
     * toEntryUpdatedDateを取得する。
     */
    public String getToEntryUpdatedDate() {
        return toEntryUpdatedDate;
    }

    /**
     * toEntryUpdatedDateを格納する。
     */
    public void setToEntryUpdatedDate(String toEntryUpdatedDate) {
        this.toEntryUpdatedDate = toEntryUpdatedDate;
    }

}
