package dao.entry.sql;

/**
 * 外部結合した申込テーブルから取得するレコードの条件に使用するデータを格納するためのアクションフォーム
 */
public class EntrySelectJoinWhereActionForm extends EntrySelectWhereActionForm {

    private String entrySchoolName; // 申込先スクール名
    private String likeEntrySchoolName; // 申込先スクール名(類似)
    private String applicantLastName; // 申込者姓
    private String likeApplicantLastName; // 申込者姓(類似)
    private String applicantFirstName; // 申込者名
    private String likeApplicantFirstName; // 申込者名(類似)
    private String applicantLastNameKana; // 申込者姓(フリガナ)
    private String likeApplicantLastNameKana; // 申込者姓(フリガナ、類似)
    private String applicantFirstNameKana; // 申込者名(フリガナ)
    private String likeApplicantFirstNameKana; // 申込者名(フリガナ、類似)

    public EntrySelectJoinWhereActionForm() {}

    /**
     * entrySchoolNameを取得する。
     */
    public String getEntrySchoolName() {
        return entrySchoolName;
    }

    /**
     * entrySchoolNameを格納する。
     */
    public void setEntrySchoolName(String entrySchoolName) {
        this.entrySchoolName = entrySchoolName;
    }

    /**
     * likeEntrySchoolNameを取得する。
     */
    public String getLikeEntrySchoolName() {
        return likeEntrySchoolName;
    }

    /**
     * likeEntrySchoolNameを格納する。
     */
    public void setLikeEntrySchoolName(String likeEntrySchoolName) {
        this.likeEntrySchoolName = likeEntrySchoolName;
    }

    /**
     * applicantLastNameを取得する。
     */
    public String getApplicantLastName() {
        return applicantLastName;
    }

    /**
     * applicantLastNameを格納する。
     */
    public void setApplicantLastName(String applicantLastName) {
        this.applicantLastName = applicantLastName;
    }

    /**
     * likeApplicantLastNameを取得する。
     */
    public String getLikeApplicantLastName() {
        return likeApplicantLastName;
    }

    /**
     * likeApplicantLastNameを格納する。
     */
    public void setLikeApplicantLastName(String likeApplicantLastName) {
        this.likeApplicantLastName = likeApplicantLastName;
    }

    /**
     * applicantFirstNameを取得する。
     */
    public String getApplicantFirstName() {
        return applicantFirstName;
    }

    /**
     * applicantFirstNameを格納する。
     */
    public void setApplicantFirstName(String applicantFirstName) {
        this.applicantFirstName = applicantFirstName;
    }

    /**
     * likeApplicantFirstNameを取得する。
     */
    public String getLikeApplicantFirstName() {
        return likeApplicantFirstName;
    }

    /**
     * likeApplicantFirstNameを格納する。
     */
    public void setLikeApplicantFirstName(String likeApplicantFirstName) {
        this.likeApplicantFirstName = likeApplicantFirstName;
    }

    /**
     * applicantLastNameKanaを取得する。
     */
    public String getApplicantLastNameKana() {
        return applicantLastNameKana;
    }

    /**
     * applicantLastNameKanaを格納する。
     */
    public void setApplicantLastNameKana(String applicantLastNameKana) {
        this.applicantLastNameKana = applicantLastNameKana;
    }

    /**
     * likeApplicantLastNameKanaを取得する。
     */
    public String getLikeApplicantLastNameKana() {
        return likeApplicantLastNameKana;
    }

    /**
     * likeApplicantLastNameKanaを格納する。
     */
    public void setLikeApplicantLastNameKana(String likeApplicantLastNameKana) {
        this.likeApplicantLastNameKana = likeApplicantLastNameKana;
    }

    /**
     * applicantFirstNameKanaを取得する。
     */
    public String getApplicantFirstNameKana() {
        return applicantFirstNameKana;
    }

    /**
     * applicantFirstNameKanaを格納する。
     */
    public void setApplicantFirstNameKana(String applicantFirstNameKana) {
        this.applicantFirstNameKana = applicantFirstNameKana;
    }

    /**
     * likeApplicantFirstNameKanaを取得する。
     */
    public String getLikeApplicantFirstNameKana() {
        return likeApplicantFirstNameKana;
    }

    /**
     * likeApplicantFirstNameKanaを格納する。
     */
    public void setLikeApplicantFirstNameKana(String likeApplicantFirstNameKana) {
        this.likeApplicantFirstNameKana = likeApplicantFirstNameKana;
    }

}
