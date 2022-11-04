package dao.mailtemplete.sql;

import org.apache.struts.action.ActionForm;

/**
 * メールテンプレートテーブルから取得するレコードの条件に使用するデータを格納するためのアクションフォーム
 */
public class MailTempleteSelectWhereActionForm extends ActionForm {

    private int mailTempleteId; // メールテンプレートID
    private String mailTempleteName; // メールテンプレート名

    public MailTempleteSelectWhereActionForm() {}

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

}
