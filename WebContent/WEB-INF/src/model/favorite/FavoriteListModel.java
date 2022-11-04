package model.favorite;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import actionform.FavoriteExtendActionForm;
import util.ImageFileUtil;

public class FavoriteListModel {

    /**
     * アクションフォームにお気に入り一覧機能で使用するスクールの情報を格納するためのメソッド
     * @param favoriteExtendFormList お気に入りのアクションフォームを格納したリスト(編集前)
     * @return お気に入りのアクションフォームを格納したリスト(編集後)
     */
    public List<FavoriteExtendActionForm> setListFavoriteData(List<FavoriteExtendActionForm> favoriteExtendFormList) {
        // リストに格納されたアクションフォームの情報を編集する。
        for(int i = 0; i < favoriteExtendFormList.size(); i++) {
                // リストからi番目のアクションフォームを取得する。
                FavoriteExtendActionForm eachForm = favoriteExtendFormList.get(i);

                // お気に入り追加日時(タイムスタンプ型)をお気に入り登録日時(文字列型)に変換してアクションフォームに格納する。
                eachForm.setStrFavoriteAddedAt(
                        new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(eachForm.getFavoriteAddedAt()));

                // リストにアクションフォームを格納する。
                favoriteExtendFormList.set(i, eachForm);
        }

        // アクションフォームを格納したリストを戻す。
        return favoriteExtendFormList;
    }

    /**
     * お気に入りのアクションフォームを格納したリストを指定したお気に入りソート種別でソートするためのメソッド
     * @param favoriteExtendFormList お気に入りのアクションフォームを格納したリスト(ソート前)
     * @param sortKindForFavorite お気に入りソート種別
     * @return お気に入りのアクションフォームを格納したリスト(ソート後)
     */
    public List<FavoriteExtendActionForm> sortFavoriteExtendFormList(
            List<FavoriteExtendActionForm> favoriteExtendFormList,
            String sortKindForFavorite) {
        // お気に入りソート種別を判定する。
        if("byDescendingAddedAt".equals(sortKindForFavorite)) {
            // "追加日時が新しい順"の場合はお気に入り追加日時の降順でソートする。
            favoriteExtendFormList.sort(Comparator.comparing(FavoriteExtendActionForm::getFavoriteAddedAt).reversed());
        } else if("byAccendingAddedAt".equals(sortKindForFavorite)) {
            // "追加日時が古い順"の場合はお気に入り追加日時の昇順でソートする。
            favoriteExtendFormList.sort(Comparator.comparing(FavoriteExtendActionForm::getFavoriteAddedAt));
        }

        // ソートしたリストを戻す。
        return favoriteExtendFormList;
    }

    /**
     * 現在のページに表示するお気に入りのアクションフォームを格納したリストを生成するためのメソッド
     * @param favoriteExtendFormList お気に入りのアクションフォームを格納したリスト
     * @param currentPageNum 現在のページ番号
     * @param displayedResult 表示件数/ページ
     * @return 現在に表示するお気に入りのアクションフォームを格納したリスト
     * @throws IOException
     */
    public List<FavoriteExtendActionForm> makeDisplayedFavoriteList(
            List<FavoriteExtendActionForm> favoriteExtendFormList,
            int currentPageNum,
            int displayedResult) throws IOException{
        // 表示するアクションフォームを格納するリストを生成する。
        List<FavoriteExtendActionForm> displayedFavoriteFormList = new ArrayList<>();
        // 最初に表示するお気に入りの順番を算出する。
        int sequence = (currentPageNum - 1) * displayedResult;
        // リストの件数分、処理を繰り返す。
        for(int i = 0; i < favoriteExtendFormList.size(); i++) {
            if(sequence <= i && displayedFavoriteFormList.size() < displayedResult) {
                // 最初に表示する順番以上かつリストに格納されたアクションフォームの件数が表示件数に満たない場合

                // リストからi番目のアクションフォームを取得する。
                FavoriteExtendActionForm eachForm = favoriteExtendFormList.get(i);

                // 一覧画面画像のファイル名を取得する。
                String summaryImageFileName = eachForm.getSummaryImageFileName();
                if(!StringUtils.isEmpty(summaryImageFileName)) {
                    // 一覧画面画像のファイル名が存在する場合はパスを取得する。
                    String summaryImageFilePath = new ImageFileUtil().getImageFilePath(summaryImageFileName);
                    // アクションフォームに取得したパスを格納する。
                    eachForm.setSummaryImageFilePath(summaryImageFilePath);
                }

                // リストにアクションフォームを追加する。
                displayedFavoriteFormList.add(eachForm);
            }
            if(displayedFavoriteFormList.size() == displayedResult) {
                // 表示件数分のアクションフォームが格納されている場合は繰り返し処理を終了する。
                break;
            }
        }

        // アクションフォームを格納したリストを戻す。
        return displayedFavoriteFormList;
    }

}
