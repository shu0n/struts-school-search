package model.category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import actionform.CategoryExtendActionForm;

public class CategoryListModel {

    /**
     * アクションフォームにカテゴリー一覧機能で使用するカテゴリーの情報を格納するためのメソッド
     * @param categoryExtendFormList カテゴリーのアクションフォームを格納したリスト(編集前)
     * @return カテゴリーのアクションフォームを格納したリスト(編集後)
     */
    public List<CategoryExtendActionForm> setListCategoryData(List<CategoryExtendActionForm> categoryExtendFormList) {
        for(int i = 0; i < categoryExtendFormList.size(); i++) {
            // リストからi番目のアクションフォームを取得する。
            CategoryExtendActionForm eachForm = categoryExtendFormList.get(i);

            // カテゴリーステータス名を取得してアクションフォームに格納する。
            String categoryStatusName
                    = new CategoryFormPartsModel().getCategoryStatusMap().get(eachForm.getCategoryStatus());
            eachForm.setCategoryStatusName(categoryStatusName);

            // リストにアクションフォームを追加する。
            categoryExtendFormList.set(i, eachForm);
        }

        // アクションフォームを格納したリストを戻す。
        return categoryExtendFormList;
    }

    /**
     * カテゴリーのアクションフォームを格納したリストを指定したカテゴリーソート種別でソートするためのメソッド
     * @param categoryExtendFormList カテゴリーのアクションフォームを格納したリスト(ソート前)
     * @param sortKindForCategory カテゴリーソート種別
     * @return カテゴリーのアクションフォームを格納したリスト(ソート後)
     */
    public List<CategoryExtendActionForm> sortCategoryExtendFormList(
            List<CategoryExtendActionForm> categoryExtendFormList,
            String sortKindForCategory) {
        // お問合せソート種別を判定する。
        if("byDescendingCreatedAt".equals(sortKindForCategory)) {
            // "作成日時が新しい順"の場合はカテゴリー作成日時の降順でソートする。
            categoryExtendFormList.sort(
                    Comparator.comparing(CategoryExtendActionForm::getCategoryCreatedAt).reversed());
        } else if("byAccendingCreatedAt".equals(sortKindForCategory)) {
            // "作成日時が古い順"の場合はカテゴリー作成日時の昇順でソートする。
            categoryExtendFormList.sort(
                    Comparator.comparing(CategoryExtendActionForm::getCategoryCreatedAt));
        } else if("byDescendingUpdatedAt".equals(sortKindForCategory)) {
            // "更新日時が新しい順"の場合はカテゴリー更新日時の降順でソートする。
            categoryExtendFormList.sort(
                    Comparator.comparing(CategoryExtendActionForm::getCategoryUpdatedAt).reversed());
        } else if("byAccendingUpdatedAt".equals(sortKindForCategory)) {
            // "更新日時が古い順"の場合はカテゴリー更新日時の昇順でソートする。
            categoryExtendFormList.sort(
                    Comparator.comparing(CategoryExtendActionForm::getCategoryUpdatedAt));
        }

        // ソートしたリストを戻す。
        return categoryExtendFormList;
    }

    /**
     * 現在のページに表示するカテゴリーのアクションフォームを格納したリストを生成するためのメソッド
     * @param categoryExtendFormList カテゴリーのアクションフォームを格納したリスト
     * @param categoryExtendFormList 現在のページ番号
     * @param displayedResult 表示件数/ページ
     * @return 画面に表示するカテゴリーのアクションフォームを格納したリスト
     */
    public List<CategoryExtendActionForm> makeDisplayedCategoryList(
            List<CategoryExtendActionForm> categoryExtendFormList,
            int currentPageNum,
            int displayedResult) {
        // 表示するカテゴリーのアクションフォームを格納するリストを生成する。
        List<CategoryExtendActionForm> displayedCategoryFormList = new ArrayList<>();
        // 最初に表示するカテゴリーの順番を算出する。
        int sequence = (currentPageNum - 1) * displayedResult;
        // リストの件数分、処理を繰り返す。
        for(int i = 0; i < categoryExtendFormList.size(); i++) {
            if(sequence <= i && displayedCategoryFormList.size() < displayedResult) {
                // 最初に表示する順番以上かつリストに格納されたアクションフォームの件数が表示件数に満たない場合は
                // リストにi番目のアクションフォームを追加する。
                displayedCategoryFormList.add(categoryExtendFormList.get(i));
            }
            if(displayedCategoryFormList.size() == displayedResult) {
                // 表示件数分のアクションフォームが格納されている場合は繰り返し処理を終了する。
                break;
            }
        }

        // アクションフォームを格納したリストを戻す。
        return displayedCategoryFormList;
    }

}
