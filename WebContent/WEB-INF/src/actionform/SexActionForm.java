package actionform;

import org.apache.struts.validator.ValidatorForm;

/**
 * 性別テーブルのデータを格納するためのアクションフォーム
 */
public class SexActionForm extends ValidatorForm {

    private int sexId; // 性別ID
    private String sexName; // 性別名

    public SexActionForm() {}

    public int getSexId() {
        return sexId;
    }

    public void setSexId(int sexId) {
        this.sexId = sexId;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

}
