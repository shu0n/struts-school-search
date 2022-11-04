package model.contact;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import actionform.ContactStatusActionForm;
import dao.contactstatus.SelectContactStatusDAO;
import dao.contactstatus.sql.ContactStatusSelectWhereActionForm;

public class ContactFormPartsModel {

    /**
     * 全てのお問合せステータスIDとお問合せステータス名を格納したマップを取得するためのメソッド
     * @return マップ(キー：お問合せステータスID、バリュー：お問合せステータス名)
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public Map<Integer, String> getContactStatusMap() throws ClassNotFoundException, IOException, SQLException {
        // お問合せステータステーブルの全レコードを取得する。
        List<ContactStatusActionForm> contactStatusFormList
                = new SelectContactStatusDAO().selectMatchedContactStatus(new ContactStatusSelectWhereActionForm());

        // お問合せステータスIDとお問合せステータス名を格納するマップを生成する。
        Map<Integer, String> contactStatusMap = new TreeMap<>();
        // 取得したレコードの件数分、処理を繰り返す。
        for(ContactStatusActionForm value : contactStatusFormList) {
            // お問合せステータスIDとお問合せステータス名を取得してマップに格納する。
            contactStatusMap.put(value.getContactStatusId(), value.getContactStatusName());
        }

        // お問合せステータスIDとお問合せステータス名を格納したマップを戻す。
        return contactStatusMap;
    }

    /**
     * 全てのお問合せステータス名とお問合せステータスIDを格納したリストを取得するためのメソッド
     * @return リスト(ラベル：お問合せステータス名、バリュー：お問合せステータス名)
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<LabelValueBean> getContactStatusListWithoutEmptyValue()
            throws ClassNotFoundException, IOException, SQLException {
        // お問合せステータスマップを取得する。
        Map<Integer, String> contactStatusMap = getContactStatusMap();
        // お問合せステータス名とお問合せステータスIDを格納するリストを生成する。
        List<LabelValueBean> contactStatusList = new ArrayList<>();
        // マップの要素数分、処理を繰り返す。
        for(Map.Entry<Integer, String> entry : contactStatusMap.entrySet()) {
            // お問合せステータス名とお問合せIDを取得してリストに格納する。
            contactStatusList.add(new LabelValueBean(entry.getValue(), String.valueOf(entry.getKey())));
        }

        // お問合せステータス名とお問合せステータスIDを格納したリストを戻す。
        return contactStatusList;
    }

    /**
     * お問合せの全てのソート種別IDとソート種別名を格納したマップを取得するためのメソッド
     * @return マップ(キー：お問合せソート種別ID、バリュー：お問合せソート種別名)
     */
    public Map<String, String> getSortKindForContactMap() {
        // お問合せソート種別IDとお問合せソート種別名を格納するマップを生成する。
        Map<String, String> sortKindForContactMap = new LinkedHashMap<>();
        // お問合せソート種別IDとお問合せソート種別名を取得してマップに格納する。
        sortKindForContactMap.put("byDescendingContactedAt", "お問合せ日が新しい順");
        sortKindForContactMap.put("byAccendingContactedAt", "お問合せ日が古い順");
        sortKindForContactMap.put("byDescendingUpdatedAt", "更新日が新しい順");
        sortKindForContactMap.put("byAccendingUpdatedAt", "更新日が古い順");

        // お問合せソート種別IDとお問わせソート種別名を格納したマップを戻す。
        return sortKindForContactMap;
    }

    /**
     * お問合せの全てのソート種別名とソート種別IDを格納したリストを取得するためのメソッド
     * @return リスト(ラベル：お問合せソート種別名、バリュー：お問合せソート種別ID)
     */
    public List<LabelValueBean> getSortKindForContactListWithEmptyValue() {
        // お問合せソート種別マップを取得する。
        Map<String, String> sortKindForContactMap = getSortKindForContactMap();
        // お問合せソート種別名とお問合せソート種別IDを格納するリストを生成する。
        List<LabelValueBean> sortKindForContactList = new ArrayList<>();
        sortKindForContactList.add(new LabelValueBean("選択する", ""));
        // マップの要素数分、処理を繰り返す。
        for(Map.Entry<String, String> entry : sortKindForContactMap.entrySet()) {
            // お問合せソート種別名とお問合せソート種別IDをリストに格納する。
            sortKindForContactList.add(new LabelValueBean(entry.getValue(), String.valueOf(entry.getKey())));
        }

        // お問合せソート種別名とお問合せソート種別IDを格納したリストを戻す。
        return sortKindForContactList;
    }

}
