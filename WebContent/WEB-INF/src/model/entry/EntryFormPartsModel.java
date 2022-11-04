package model.entry;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import actionform.EntryStatusActionForm;
import dao.entrystatus.SelectEntryStatusDAO;
import dao.entrystatus.sql.EntryStatusSelectWhereActionForm;

public class EntryFormPartsModel {

    /**
     * 全ての申込ステータスIDと申込ステータス名を格納したマップを取得するためのメソッド
     * @return マップ(キー：申込ステータスID、バリュー：申込ステータス名)
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public Map<Integer, String> getEntryStatusMap() throws ClassNotFoundException, IOException, SQLException {
        // 申込ステータステーブルの全レコードを取得する。
        List<EntryStatusActionForm> entryStatusFormList
                = new SelectEntryStatusDAO().selectMatchedEntryStatus(new EntryStatusSelectWhereActionForm());

        // 申込ステータスIDと申込ステータス名を格納するマップを生成する。
        Map<Integer, String> entryStatusMap = new TreeMap<>();
        // 取得したレコードの件数分、処理を繰り返す。
        for(EntryStatusActionForm value : entryStatusFormList) {
            // 申込ステータスIDと申込ステータス名を取得してマップに格納する。
            entryStatusMap.put(value.getEntryStatusId(), value.getEntryStatusName());
        }

        // 申込ステータスIDと申込ステータス名を格納したマップを戻す。
        return entryStatusMap;
    }

    /**
     * 全ての申込ステータス名と申込ステータスIDを格納したリストを取得するためのメソッド
     * @return リスト(ラベル：申込ステータス名、バリュー：申込ステータスID)
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<LabelValueBean> getEntryStatusListWithEmptyValue()
            throws ClassNotFoundException, IOException, SQLException {
        // 申込ステータスマップを取得する。
        Map<Integer, String> entryStatusMap = getEntryStatusMap();
        // 申込ステータス名と申込ステータスIDを格納するリストを生成する。
        List<LabelValueBean> entryStatusList = new ArrayList<>();
        entryStatusList.add(new LabelValueBean("選択してください", ""));
        // マップの要素数分、処理を繰り返す。
        for(Map.Entry<Integer, String> entry : entryStatusMap.entrySet()) {
            // 申込ステータス名と申込ステータスIDを取得してリストに格納する。
            entryStatusList.add(new LabelValueBean(entry.getValue(), String.valueOf(entry.getKey())));
        }

        // 申込ステータス名と申込ステータスIDを格納したリストを戻す。
        return entryStatusList;
    }

    /**
     * 全ての申込ステータス名と申込ステータスIDを格納したリストを取得するためのメソッド
     * @return リスト(ラベル：申込ステータス名、バリュー：申込ステータスID)
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<LabelValueBean> getEntryStatusListWithoutEmptyValue()
            throws ClassNotFoundException, IOException, SQLException {
        // 申込ステータスマップを取得する。
        Map<Integer, String> entryStatusMap = getEntryStatusMap();
        // 申込ステータス名と申込ステータスIDを格納するリストを生成する。
        List<LabelValueBean> entryStatusList = new ArrayList<>();
        // マップの要素数分、処理を繰り返す。
        for(Map.Entry<Integer, String> entry : entryStatusMap.entrySet()) {
            // 申込ステータス名と申込ステータスIDを取得してリストに格納する。
            entryStatusList.add(new LabelValueBean(entry.getValue(), String.valueOf(entry.getKey())));
        }

        // 申込ステータス名と申込ステータスIDを格納したリストを戻す。
        return entryStatusList;
    }

    /**
     * 申込の全てのソート種別IDとソート種別名を格納したマップを取得するためのメソッド
     * @return マップ(キー：申込ソート種別ID、バリュー：申込ソート種別名)
     */
    public Map<String, String> getSortKindForEntryMap() {
        // 申込ソート種別IDと申込ソート種別名を格納するマップを生成する。
        Map<String, String> sortKindForEntryMap = new LinkedHashMap<>();
        // 申込ソート種別IDと申込ソート種別名を取得してマップに格納する。
        sortKindForEntryMap.put("byDescendingEntriedAt", "申込日が新しい順");
        sortKindForEntryMap.put("byAccendingEntriedAt", "申込日が古い順");
        sortKindForEntryMap.put("byDescendingUpdatedAt", "更新日が新しい順");
        sortKindForEntryMap.put("byAccendingUpdatedAt", "更新日が古い順");

        // 申込ソート種別IDと申込ソート種別名を格納したマップを戻す。
        return sortKindForEntryMap;
    }

    /**
     * 申込の全てのソート種別名とソート種別IDを格納したリストを取得するためのメソッド
     * @return リスト(ラベル：申込ソート種別名、バリュー：申込ソート種別ID)
     */
    public List<LabelValueBean> getSortKindForEntryListWithEmptyValue() {
        // 申込ソート種別マップを取得する。
        Map<String, String> sortKindForEntryMap = getSortKindForEntryMap();
        // 申込ソート種別名と申込ソート種別IDを格納するリストを生成する。
        List<LabelValueBean> sortKindForEntryList = new ArrayList<>();
        sortKindForEntryList.add(new LabelValueBean("選択する", ""));
        // マップの要素数分、処理を繰り返す。
        for(Map.Entry<String, String> entry : sortKindForEntryMap.entrySet()) {
            // 申込ソート種別名と申込ソート種別IDをリストに格納する。
            sortKindForEntryList.add(new LabelValueBean(entry.getValue(), String.valueOf(entry.getKey())));
        }

        // 申込ソート種別名と申込ソート種別IDを格納したリストを戻す。
        return sortKindForEntryList;
    }

}
