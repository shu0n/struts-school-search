package util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import actionform.PrefectureActionForm;
import dao.prefecture.SelectPrefectureDAO;
import dao.prefecture.sql.PrefectureSelectWhereActionForm;

public class FormPartsUtil {

    /**
     * 全ての都道府県IDと都道府県名を格納したマップを取得するためのメソッド
     * @return マップ(キー：都道府県ID、バリュー：都道府県名)
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public Map<Integer, String> getPrefectureMap() throws ClassNotFoundException, IOException, SQLException {
        // 都道府県テーブルの全レコードを取得する。
        List<PrefectureActionForm> prefectureFormList
                = new SelectPrefectureDAO().selectMatchedPrefecture(new PrefectureSelectWhereActionForm());

        // 都道府県IDと都道府県名を格納するマップを生成する。
        Map<Integer, String> prefectureMap = new TreeMap<>();
        // 取得したレコードの件数分、処理を繰り返す。
        for(PrefectureActionForm value : prefectureFormList) {
            // 都道府県IDと都道府県名を取得してマップに格納する。
            prefectureMap.put(value.getPrefectureId(), value.getPrefectureName());
        }

        // 都道府県IDと都道府県名を格納したマップを戻す。
        return prefectureMap;
    }

    /**
     * 全ての都道府県名を格納したリストを取得するためのメソッド
     * @return リスト(ラベル：都道府県名、バリュー：都道府県ID)
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<LabelValueBean> getPrefectureListWithEmptyValue()
            throws ClassNotFoundException, IOException, SQLException {
        // 都道府県マップを取得する。
        Map<Integer, String> prefectureMap = getPrefectureMap();
        // 都道府県名と都道府県IDを格納するリストを生成する。
        List<LabelValueBean> prefectureList = new ArrayList<>();
        prefectureList.add(new LabelValueBean("選択してください", ""));
        // マップの要素数分、処理を繰り返す。
        for(Map.Entry<Integer, String> entry : prefectureMap.entrySet()) {
            // 都道府県名を取得してリストに格納する。
            prefectureList.add(new LabelValueBean(entry.getValue(), String.valueOf(entry.getKey())));
        }

        // 都道府県名と都道府県IDを格納したリストを戻す。
        return prefectureList;
    }

    /**
     * 全ての都道府県名と都道府県IDを格納したリストを取得するためのメソッド
     * @return リスト(ラベル：都道府県名、バリュー：都道府県ID)
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<LabelValueBean> getPrefectureListWithoutEmptyValue()
            throws ClassNotFoundException, IOException, SQLException {
        // 都道府県マップを取得する。
        Map<Integer, String> prefectureMap = getPrefectureMap();
        // 都道府県名と都道府県IDを格納するリストを生成する。
        List<LabelValueBean> prefectureList = new ArrayList<>();
        // マップの要素数分、処理を繰り返す。
        for(Map.Entry<Integer, String> entry : prefectureMap.entrySet()) {
            // 都道府県名と都道府県IDを取得してリストに格納する。
            prefectureList.add(new LabelValueBean(entry.getValue(), String.valueOf(entry.getKey())));
        }

        // 都道府県名と都道府県IDを格納したリストを戻す。
        return prefectureList;
    }

}
