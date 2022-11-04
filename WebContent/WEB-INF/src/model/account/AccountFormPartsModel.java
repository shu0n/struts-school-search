package model.account;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import actionform.AccountActionForm;
import actionform.SexActionForm;
import dao.account.SelectAccountDAO;
import dao.account.sql.AccountSelectWhereActionForm;
import dao.sex.SelectSexDAO;
import dao.sex.sql.SexSelectWhereActionForm;

public class AccountFormPartsModel {

    /**
     * 全てのアカウントステータスIDとアカウントステータス名を格納したマップを取得するためのメソッド
     * @return マップ(キー：アカウントステータスID、バリュー：アカウントステータス名)
     */
    public Map<String, String> getAccountStatusMap() {
        // アカウントステータスIDとアカウントステータス名を格納するマップを生成する。
        Map<String, String> accountStatusMap = new TreeMap<>();
        // アカウントステータスIDとアカウントステータス名を取得してマップに格納する。
        accountStatusMap.put("0", "無効");
        accountStatusMap.put("1", "有効");

        // アカウントステータスIDとアカウントステータス名を格納したマップを戻す。
        return accountStatusMap;
    }

    /**
     * 全てのアカウントステータス名とアカウントステータスIDを格納したリストを取得するためのメソッド
     * @return リスト(ラベル：アカウントステータス名、バリュー：アカウントステータスID)
     */
    public List<LabelValueBean> getAccountStatusList() {
        // アカウントステータスマップを取得する。
        Map<String, String> accountStatusMap = getAccountStatusMap();
        // アカウントステータス名を格納するリストを生成する。
        List<LabelValueBean> accountStatusList = new ArrayList<>();
        // マップの要素数分、処理を繰り返す。
        for(Map.Entry<String, String> entry : accountStatusMap.entrySet()) {
            // アカウントステータス名とアカウントステータスIDを取得してリストに格納する。
            accountStatusList.add(new LabelValueBean(entry.getValue(), String.valueOf(entry.getKey())));
        }

        // アカウントステータス名とアカウントステータスIDを格納したリストを戻す。
        return accountStatusList;
    }

    /**
     * 全ての性別IDと性別名を格納したマップを取得するためのメソッド
     * @return マップ(キー：性別ID、バリュー：性別名)
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public Map<Integer, String> getSexMap() throws ClassNotFoundException, IOException, SQLException {
        // 性別テーブルから全レコードを取得する。
        List<SexActionForm> sexFormList
                = new SelectSexDAO().selectMatchedSex(new SexSelectWhereActionForm());

        // 性別IDと性別名を格納するマップを生成する。
        Map<Integer, String> sexMap = new TreeMap<>();
        // 取得したレコードの件数分、処理を繰り返す。
        for(SexActionForm value : sexFormList) {
            // 性別IDと性別名を取得してマップに格納する。
            sexMap.put(value.getSexId(), value.getSexName());
        }

        // 性別IDと性別名を格納したマップを戻す。
        return sexMap;
    }

    /**
     * 全ての性別名と性別IDを格納したリストを取得するためのメソッド
     * @return リスト(ラベル：性別名、バリュー：性別ID)
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<LabelValueBean> getSexListWithoutEmptyValue()
            throws ClassNotFoundException, IOException, SQLException {
        // 性別マップを取得する。
        Map<Integer, String> sexMap = getSexMap();
        // 性別名と性別IDを格納するリストを生成する。
        List<LabelValueBean> sexList = new ArrayList<>();
        // マップの要素数分、処理を繰り返す。
        for(Map.Entry<Integer, String> entry : sexMap.entrySet()) {
            // 性別名と性別IDを取得してリストに格納する。
            sexList.add(new LabelValueBean(entry.getValue(), String.valueOf(entry.getKey())));
        }

        // 性別名と性別IDを格納したリストを戻す。
        return sexList;
    }

    /**
     * 1920年から現在年までの年を格納したリストを取得するためのメソッド
     * @return リスト(ラベル：年、バリュー：年)
     */
    public List<LabelValueBean> getBirthYearListWithEmptyValue() {
        // 生年月日(年)を格納するリストを生成する。
        List<LabelValueBean> birthYearList = new ArrayList<>();
        birthYearList.add(new LabelValueBean("選択してください", ""));
        // 1920年から現在年まで処理を繰り返す。
        for(int i = 1920;
                i <= Integer.parseInt(new SimpleDateFormat("yyyy").format(new Timestamp(nowTimeMillis())));
                i++) {
            // 年をリストに格納する。
            birthYearList.add(new LabelValueBean(String.valueOf(i), String.valueOf(i)));
        }

        // 年を格納したリストを戻す。
        return birthYearList;
    }

    /**
     * 1月から12月までの月を格納したリストを取得するためのメソッド
     * @return リスト(ラベル：月、バリュー：月)
     */
    public List<LabelValueBean> getBirthMonthListWithEmptyValue() {
        // 生年月日(月)を格納するリストを生成する。
        List<LabelValueBean> birthMonthList = new ArrayList<>();
        birthMonthList.add(new LabelValueBean("選択してください", ""));
        // 1月から12月まで処理を繰り返す。
        for(int i = 1; i <= 12; i++) {
            // 月をリストに格納する。
            birthMonthList.add(new LabelValueBean(String.valueOf(i), String.valueOf(i)));
        }

        // 月を格納したリストを戻す。
        return birthMonthList;
    }

    /**
     * 1日から31日までの月を格納したリストを取得するためのメソッド
     * @return リスト(ラベル：日、バリュー：日)
     */
    public List<LabelValueBean> getBirthDayListWithEmptyValue() {
        // 生年月日(日)を格納するリストを生成する。
        List<LabelValueBean> birthDayList = new ArrayList<>();
        birthDayList.add(new LabelValueBean("選択してください", ""));
        // 1日から31日まで処理を繰り返す。
        for(int i = 1; i <= 31; i++) {
            // 日をリストに格納する。
            birthDayList.add(new LabelValueBean(String.valueOf(i), String.valueOf(i)));
        }

        // 日を格納したリストを戻す。
        return birthDayList;
    }

    /**
     * 全てのアカウントIDを格納したリストを取得するためのメソッド
     * @return リスト(ラベル：アカウントID、バリュー：アカウントID)
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<LabelValueBean> getAccountIdListWithEmptyValue()
            throws ClassNotFoundException, IOException, SQLException {
        // アカウントテーブルから全レコードを取得する。
        List<AccountActionForm> accountFormList
                = new SelectAccountDAO().selectMatchedAccount(new AccountSelectWhereActionForm());
        // アカウントIDを格納するリストを生成する。
        List<LabelValueBean> accountList = new ArrayList<>();
        accountList.add(new LabelValueBean("選択してください", ""));
        // 取得したレコードの件数分、処理を繰り返す。
        for(AccountActionForm eachForm: accountFormList) {
            // アカウントIDを取得してリストに格納する。
            accountList.add(new LabelValueBean(
                    String.valueOf(eachForm.getAccountId()),
                    String.valueOf(eachForm.getAccountId())));
        }

        // アカウントIDを格納したリストを戻す。
        return accountList;
    }

    /**
     * アカウントの全てのソート種別IDとソート種別名を格納したマップを取得するためのメソッド
     * @return マップ(キー：アカウントソート種別ID、バリュー：アカウントソート種別名)
     */
    public Map<String, String> getSortKindForAccountMap() {
        // アカウントソート種別IDとアカウントソート種別名を格納するマップを生成する。
        Map<String, String> sortKindForAccountMap = new LinkedHashMap<>();
        // アカウントソート種別IDとアカウントソート種別名を取得してマップに格納する。
        sortKindForAccountMap.put("byDescendingCreatedAt", "作成日が新しい順");
        sortKindForAccountMap.put("byAccendingCreatedAt", "作成日が古い順");
        sortKindForAccountMap.put("byDescendingUpdatedAt", "更新日が新しい順");
        sortKindForAccountMap.put("byAccendingUpdatedAt", "更新日が古い順");

        // アカウントソート種別IDとアカウントソート種別名を格納したマップを戻す。
        return sortKindForAccountMap;
    }

    /**
     * アカウントの全てのソート種別名とソート種別IDを格納したリストを取得するためのメソッド
     * @return リスト(ラベル：アカウントソート種別名、バリュー：アカウントソート種別ID)
     */
    public List<LabelValueBean> getSortKindForAccountListWithEmptyValue() {
        // アカウントソート種別マップを取得する。
        Map<String, String> sortKindForAccountMap = getSortKindForAccountMap();
        // アカウントソート種別名とアカウントソート種別IDを格納するリストを生成する。
        List<LabelValueBean> sortKindForAccountList = new ArrayList<>();
        sortKindForAccountList.add(new LabelValueBean("選択する", ""));
        // マップの要素数分、処理を繰り返す。
        for(Map.Entry<String, String> entry : sortKindForAccountMap.entrySet()) {
            // アカウントソート種別名とアカウントソート種別IDをリストに格納する。
            sortKindForAccountList.add(new LabelValueBean(entry.getValue(), String.valueOf(entry.getKey())));
        }

        // アカウントソート種別名とアカウントソート種別IDを格納したリストを戻す。
        return sortKindForAccountList;
    }

    /**
     * UTC(協定世界時)：1970/01/01 09:00:00.000と現在日時(ローカル)との差をミリ秒で取得するためのメソッド
     */
    long nowTimeMillis() {
        // UTC(協定世界時)：1970/01/01 09:00:00.000と現在日時(ローカル)との差をミリ秒で戻す。
        return System.currentTimeMillis();
    }

}
