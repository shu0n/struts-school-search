package dao.prefecture.sql;

import org.apache.struts.action.ActionForm;

/**
 * 都道府県テーブルから取得するレコードの条件に使用するデータを格納するためのアクションフォーム
 */
public class PrefectureSelectWhereActionForm extends ActionForm {

    private int prefectureId; // 都道府県ID
    private String prefectureName; // 都道府県名

    public PrefectureSelectWhereActionForm() {}

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
