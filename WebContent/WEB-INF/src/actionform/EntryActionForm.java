package actionform;

import java.sql.Timestamp;

import org.apache.struts.validator.ValidatorForm;

/**
 * 申込テーブルのデータを格納するためのアクションフォーム
 */
public class EntryActionForm extends ValidatorForm {

    private int entryId; // 申込ID
    private int entrySchoolId; // 申込先スクールID
    private int applicantAccountId; // 申込者アカウントID
    private String entryQuestion; // 質問
    private int entryStatusId; // 申込ステータスID
    private Timestamp entriedAt; // 申込日時
    private Timestamp entryUpdatedAt; // 申込更新日時
    private String entryDeleteFlag; // 申込削除フラグ
    private Timestamp entryDeletedAt; // 申込削除日時

    public EntryActionForm() {}

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
     * entryQuestionを取得する。
     */
    public String getEntryQuestion() {
        return entryQuestion;
    }

    /**
     * entryQuestionを格納する。
     */
    public void setEntryQuestion(String entryQuestion) {
        this.entryQuestion = entryQuestion;
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
     * entriedAtを取得する。
     */
    public Timestamp getEntriedAt() {
        return entriedAt;
    }

    /**
     * entriedAtを格納する。
     */
    public void setEntriedAt(Timestamp entriedAt) {
        this.entriedAt = entriedAt;
    }

    /**
     * entryUpdatedAtを取得する。
     */
    public Timestamp getEntryUpdatedAt() {
        return entryUpdatedAt;
    }

    /**
     * entryUpdatedAtを格納する。
     */
    public void setEntryUpdatedAt(Timestamp entryUpdatedAt) {
        this.entryUpdatedAt = entryUpdatedAt;
    }

    /**
     * entryDeleteFlagを取得する。
     */
    public String getEntryDeleteFlag() {
        return entryDeleteFlag;
    }

    /**
     * entryDeleteFlagを格納する。
     */
    public void setEntryDeleteFlag(String entryDeleteFlag) {
        this.entryDeleteFlag = entryDeleteFlag;
    }

    /**
     * entryDeletedAtを取得する。
     */
    public Timestamp getEntryDeletedAt() {
        return entryDeletedAt;
    }

    /**
     * entryDeletedAtを格納する。
     */
    public void setEntryDeletedAt(Timestamp entryDeletedAt) {
        this.entryDeletedAt = entryDeletedAt;
    }

}
