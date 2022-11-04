package actionform;

import org.apache.struts.validator.ValidatorForm;

/**
 * 都道府県テーブルのデータを格納するためのアクションフォーム
 */
public class PrefectureActionForm extends ValidatorForm {

    private int prefectureId; // 都道府県ID
    private String prefectureName; // 都道府県名

    public PrefectureActionForm() {}

    /**
     * prefectureIdを取得する。
     */
    public int getPrefectureId() {
        return prefectureId;
    }

    /**
     * prefectureIdを格納する。
     */
    public void setPrefectureId(int prefectureId) {
        this.prefectureId = prefectureId;
    }

    /**
     * prefectureNameを取得する。
     */
    public String getPrefectureName() {
        return prefectureName;
    }

    /**
     * prefectureNameを格納する。
     */
    public void setPrefectureName(String prefectureName) {
        this.prefectureName = prefectureName;
    }

}
