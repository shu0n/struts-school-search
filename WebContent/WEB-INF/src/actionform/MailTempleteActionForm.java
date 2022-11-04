package actionform;

import java.sql.Timestamp;

import org.apache.struts.validator.ValidatorForm;

/**
 * メールテンプレートテーブルのデータを格納するためのアクションフォーム
 */
public class MailTempleteActionForm extends ValidatorForm {

    private int mailTempleteId; // メールテンプレートID
    private String mailTempleteName; // メールテンプレート名
    private String mailTempleteSubject; // 件名
    private String mailTempleteHeader; // ヘッダー文
    private String mailTempleteFooter; // フッター文
    private Timestamp mailTempleteCreatedAt; // メールテンプレート作成日時
    private Timestamp mailTempleteUpdatedAt; // メールテンプレート更新日時
    private String mailTempleteDeleteFlag; // メールテンプレート削除フラグ
    private Timestamp mailTempleteDeletedAt; // メールテンプレート削除日時

    public MailTempleteActionForm() {}

    /**
     * mailTempleteIdを取得する。
     */
    public int getMailTempleteId() {
        return mailTempleteId;
    }

    /**
     * mailTempleteIdを格納する。
     */
    public void setMailTempleteId(int mailTempleteId) {
        this.mailTempleteId = mailTempleteId;
    }

    /**
     * mailTempleteNameを取得する。
     */
    public String getMailTempleteName() {
        return mailTempleteName;
    }

    /**
     * mailTempleteNameを格納する。
     */
    public void setMailTempleteName(String mailTempleteName) {
        this.mailTempleteName = mailTempleteName;
    }

    /**
     * mailTempleteSubjectを取得する。
     */
    public String getMailTempleteSubject() {
        return mailTempleteSubject;
    }

    /**
     * mailTempleteSubjectを格納する。
     */
    public void setMailTempleteSubject(String mailTempleteSubject) {
        this.mailTempleteSubject = mailTempleteSubject;
    }

    /**
     * mailTempleteHeaderを取得する。
     */
    public String getMailTempleteHeader() {
        return mailTempleteHeader;
    }

    /**
     * mailTempleteHeaderを格納する。
     */
    public void setMailTempleteHeader(String mailTempleteHeader) {
        this.mailTempleteHeader = mailTempleteHeader;
    }

    /**
     * mailTempleteFooterを取得する。
     */
    public String getMailTempleteFooter() {
        return mailTempleteFooter;
    }

    /**
     * mailTempleteFooterを格納する。
     */
    public void setMailTempleteFooter(String mailTempleteFooter) {
        this.mailTempleteFooter = mailTempleteFooter;
    }

    /**
     * mailTempleteCreatedAtを取得する。
     */
    public Timestamp getMailTempleteCreatedAt() {
        return mailTempleteCreatedAt;
    }

    /**
     * mailTempleteCreatedAtを格納する。
     */
    public void setMailTempleteCreatedAt(Timestamp mailTempleteCreatedAt) {
        this.mailTempleteCreatedAt = mailTempleteCreatedAt;
    }

    /**
     * mailTempleteUpdatedAtを取得する。
     */
    public Timestamp getMailTempleteUpdatedAt() {
        return mailTempleteUpdatedAt;
    }

    /**
     * mailTempleteUpdatedAtを格納する。
     */
    public void setMailTempleteUpdatedAt(Timestamp mailTempleteUpdatedAt) {
        this.mailTempleteUpdatedAt = mailTempleteUpdatedAt;
    }

    /**
     * mailTempleteDeleteFlagを取得する。
     */
    public String getMailTempleteDeleteFlag() {
        return mailTempleteDeleteFlag;
    }

    /**
     * mailTempleteDeleteFlagを格納する。
     */
    public void setMailTempleteDeleteFlag(String mailTempleteDeleteFlag) {
        this.mailTempleteDeleteFlag = mailTempleteDeleteFlag;
    }

    /**
     * mailTempleteDeletedAtを取得する。
     */
    public Timestamp getMailTempleteDeletedAt() {
        return mailTempleteDeletedAt;
    }

    /**
     * mailTempleteDeletedAtを格納する。
     */
    public void setMailTempleteDeletedAt(Timestamp mailTempleteDeletedAt) {
        this.mailTempleteDeletedAt = mailTempleteDeletedAt;
    }

}
