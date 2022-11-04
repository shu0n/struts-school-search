package dao.entry.sql;

import org.apache.struts.action.ActionForm;

/**
 * 申込テーブルに作成するレコードのデータを格納するためのアクションフォーム
 */
public class EntryInsertDataActionForm extends ActionForm {

    private int entrySchoolId; // 申込先スクールID
    private int applicantAccountId; // 申込者アカウントID
    private String entryQuestion; // 質問
    private int entryStatusId; // 申込ステータスID

    public EntryInsertDataActionForm() {}

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

}
