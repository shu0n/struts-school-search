package model.entry;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import actionform.EntryExtendActionForm;

public class EntryListModel {

    /**
     * アクションフォームに申込一覧機能で使用する申込の情報を格納するためのメソッド
     * @param entryExtendFormList 申込のアクションフォームを格納したリスト(編集前)
     * @return 申込のアクションフォームを格納したリスト(編集後)
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<EntryExtendActionForm> setListEntryData(List<EntryExtendActionForm> entryExtendFormList)
            throws ClassNotFoundException, IOException, SQLException {
        // リストに格納されたアクションフォームの情報を編集する。
        for(int i = 0; i < entryExtendFormList.size(); i++) {
            // リストからi番目のアクションフォームを取得する。
            EntryExtendActionForm eachForm = entryExtendFormList.get(i);

            // 申込ステータス名を取得してアクションフォームに格納する。
            String entryStatusName = new EntryFormPartsModel().getEntryStatusMap().get(eachForm.getEntryStatusId());
            eachForm.setEntryStatusName(entryStatusName);

            // 申込日時(タイムスタンプ型)を申込日時(文字列型)に変換してアクションフォームに格納する。
            eachForm.setStrEntriedAt(
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(eachForm.getEntriedAt()));
            // 申込更新日時(タイムスタンプ型)を申込更新日時(文字列型)に変換してアクションフォームに格納する。
            eachForm.setStrEntryUpdatedAt(
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(eachForm.getEntryUpdatedAt()));

            // リストにアクションフォームを格納する。
            entryExtendFormList.set(i, eachForm);
        }

        // アクションフォームを格納したリストを戻す。
        return entryExtendFormList;
    }

    /**
     * 申込のアクションフォームを格納したリストを指定した申込ソート種別でソートするためのメソッド
     * @param entryExtendFormList 申込のアクションフォームを格納したリスト(ソート前)
     * @param sortKindForEntry 申込ソート種別
     * @return 申込のアクションフォームを格納したリスト(ソート後)
     */
    public List<EntryExtendActionForm> sortEntryExtendFormList(
            List<EntryExtendActionForm> entryExtendFormList,
            String sortKindForEntry) {
        if("byDescendingEntriedAt".equals(sortKindForEntry)) {
            // 申込ソート種別が"申込日が新しい順"の場合は申込日時の降順でソートする。
            entryExtendFormList.sort(Comparator.comparing(EntryExtendActionForm::getEntriedAt).reversed());
        } else if("byAccendingEntriedAt".equals(sortKindForEntry)) {
            // 申込ソート種別が"申込日が古い順"の場合は申込日時の昇順でソートする。
            entryExtendFormList.sort(Comparator.comparing(EntryExtendActionForm::getEntriedAt));
        } else if("byDescendingUpdatedAt".equals(sortKindForEntry)) {
            // 申込ソート種別が"更新日が新しい順"の場合は申込更新日時の降順でソートする。
            entryExtendFormList.sort(Comparator.comparing(EntryExtendActionForm::getEntryUpdatedAt).reversed());
        } else if("byAccendingUpdatedAt".equals(sortKindForEntry)) {
            // 申込ソート種別が"更新日が古い順"の場合は申込更新日時の昇順でソートする。
            entryExtendFormList.sort(Comparator.comparing(EntryExtendActionForm::getEntryUpdatedAt));
        }

        // ソートしたリストを戻す。
        return entryExtendFormList;
    }

    /**
     * 現在のページに表示する申込のアクションフォームを格納したリストを生成するためのメソッド
     * @param entryExtendFormList 申込のアクションフォームを格納したリスト
     * @param currentPageNum 現在のページ番号
     * @param displayedResult 表示件数/ページ
     * @return 画面に表示する申込のアクションフォームを格納したリスト
     */
    public List<EntryExtendActionForm> makeDisplayedEntryList(
            List<EntryExtendActionForm> entryExtendFormList,
            int currentPageNum,
            int displayedResult) {
        // 表示する申込のアクションフォームを格納するリストを生成する。
        List<EntryExtendActionForm> displayedEntryFormList = new ArrayList<>();
        // 最初に表示する申込の順番を算出する。
        int sequence = (currentPageNum - 1) * displayedResult;
        // リストの件数分、処理を繰り返す。
        for(int i = 0; i < entryExtendFormList.size(); i++) {
            if(sequence <= i && displayedEntryFormList.size() < displayedResult) {
                // 最初に表示する順番以上かつリストに格納されたアクションフォームの件数が表示件数に満たない場合は
                // リストにi番目のアクションフォームを追加する。
                displayedEntryFormList.add(entryExtendFormList.get(i));
            }
            // 表示件数分のアクションフォームが格納されているかを判定する。
            if(displayedEntryFormList.size() == displayedResult) {
                // 格納されている場合は繰り返し処理を終了する。
                break;
            }
        }

        // アクションフォームを格納したリストを戻す。
        return displayedEntryFormList;
    }

}
