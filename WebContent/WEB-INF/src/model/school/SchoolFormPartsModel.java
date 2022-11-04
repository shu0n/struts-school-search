package model.school;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

public class SchoolFormPartsModel {

    /**
     * 全ての登録者種別と登録者種別名を格納したマップを取得するためのメソッド
     * @return マップ(キー：登録者種別、バリュー：登録者種別名)
     */
    public Map<String, String> getRegistrantKindMap() {
        // 登録者種別と登録者種別名を格納するマップを生成する。
        Map<String, String> registrantProprietyMap = new TreeMap<>();
        // 登録者種別と登録者種別名をマップに格納する。
        registrantProprietyMap.put("admin", "管理者として登録");
        registrantProprietyMap.put("account", "選択するアカウントとして登録");

        // 登録者種別と登録者種別名を格納したマップを戻す。
        return registrantProprietyMap;
    }

    /**
     * 全ての公開可否IDと公開可否名を格納したマップを取得するためのメソッド
     * @return マップ(キー：公開可否ID、バリュー：公開可否名)
     */
    public Map<String, String> getReleaseProprietyMap() {
        // 公開可否IDと公開可否名を格納するマップを生成する。
        Map<String, String> releaseProprietyMap = new TreeMap<>();
        // 公開可否IDと公開可否名をマップに格納する。
        releaseProprietyMap.put("0", "不可");
        releaseProprietyMap.put("1", "可");

        // 公開可否IDと公開可否名を格納したマップを戻す。
        return releaseProprietyMap;
    }

    /**
     * 全ての公開可否名と公開可否IDを格納したリストを取得するためのメソッド
     * @return リスト(ラベル：公開可否名、バリュー：公開可否ID)
     */
    public List<LabelValueBean> getReleaseProprietyList() {
        // 公開可否マップを取得する。
        Map<String, String> releaseProprietyMap = getReleaseProprietyMap();
        // 公開可否名と公開可否IDを格納するbeanを生成する。
        List<LabelValueBean> releaseProprietyList = new ArrayList<>();
        for(Map.Entry<String, String> entry: releaseProprietyMap.entrySet()) {
            // 公開可否名と公開可否IDをリストに格納する。
            releaseProprietyList.add(new LabelValueBean(entry.getValue(), String.valueOf(entry.getKey())));
        }

        // 公開可否名と公開可否IDを格納したリストを戻す。
        return releaseProprietyList;
    }

    /**
     * 全ての申込可否IDと申込可否名を格納したマップを取得するためのメソッド
     * @return マップ(キー：申込可否ID、バリュー：申込可否名)
     */
    public Map<String, String> getEntryProprietyMap() {
        // 申込可否IDと申込可否名を格納するマップを生成する。
        Map<String, String> entryProprietyMap = new TreeMap<>();
        // 申込可否IDと申込可否名をマップに格納する。
        entryProprietyMap.put("0", "不可");
        entryProprietyMap.put("1", "可");

        // 申込可否IDと申込可否名を格納したマップを戻す。
        return entryProprietyMap;
    }

    /**
     * 全ての申込可否名と申込可否IDを格納したリストを取得するためのメソッド
     * @return リスト(ラベル：申込可否名、バリュー：申込可否ID)
     */
    public List<LabelValueBean> getEntryProprietyList() {
        // 申込可否マップを取得する。
        Map<String, String> entryProprietyMap = getEntryProprietyMap();
        // 申込可否名と申込可否IDを格納するリストを生成する。
        List<LabelValueBean> entryProprietyList = new ArrayList<>();
        // マップの要素数分、処理を繰り返す。
        for(Map.Entry<String, String> entry : entryProprietyMap.entrySet()) {
            // 申込可否名と申込可否IDをリストに格納する。
            entryProprietyList.add(new LabelValueBean(entry.getValue(), String.valueOf(entry.getKey())));
        }

        // 申込可否名と申込可否IDを格納したリストを戻す。
        return entryProprietyList;
    }

    /**
     * スクールの全てのソート種別IDとソート種別名を格納したマップを取得するためのメソッド
     * @return マップ(キー：スクールソート種別ID、バリュー：スクールソート種別名)
     */
    public Map<String, String> getSortKindForSchoolMap() {
        // スクールソート種別IDとスクールソート種別名を格納するマップを生成する。
        Map<String, String> sortKindForSchoolMap = new LinkedHashMap<>();
        // スクールソート種別IDとスクールソート種別名を取得してマップに格納する。
        sortKindForSchoolMap.put("byDescendingRegisteredAt", "登録日が新しい順");
        sortKindForSchoolMap.put("byAccendingRegisteredAt", "登録日が古い順");
        sortKindForSchoolMap.put("byDescendingUpdatedAt", "更新日が新しい順");
        sortKindForSchoolMap.put("byAccendingUpdatedAt", "更新日が古い順");
        sortKindForSchoolMap.put("byDescendingSchoolFee", "費用の高い順");
        sortKindForSchoolMap.put("byAccendingSchoolFee", "費用の安い順");

        // スクールソート種別IDとスクールソート種別名を格納したマップを戻す。
        return sortKindForSchoolMap;
    }

    /**
     * スクールの全てのソート種別名とソート種別IDを格納したリストを取得するためのメソッド
     * @return リスト(ラベル：スクールソート種別名、バリュー：スクールソート種別ID)
     */
    public List<LabelValueBean> getSortKindForSchoolListWithEmptyValue() {
        // スクールソート種別マップを取得する。
        Map<String, String> sortKindForSchoolMap = getSortKindForSchoolMap();
        // スクールソート種別名とスクールソート種別IDを格納するリストを生成する。
        List<LabelValueBean> sortKindForSchoolList = new ArrayList<>();
        sortKindForSchoolList.add(new LabelValueBean("選択する", ""));
        // マップの要素数分、処理を繰り返す。
        for(Map.Entry<String, String> entry : sortKindForSchoolMap.entrySet()) {
            // スクールソート種別名とスクールソート種別IDをリストに格納する。
            sortKindForSchoolList.add(new LabelValueBean(entry.getValue(), String.valueOf(entry.getKey())));
        }

        // スクールソート種別名とスクールソート種別IDを格納したリストを戻す。
        return sortKindForSchoolList;
    }

}
