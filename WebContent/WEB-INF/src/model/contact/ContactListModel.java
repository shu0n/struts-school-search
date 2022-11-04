package model.contact;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import actionform.ContactExtendActionForm;

public class ContactListModel {

    /**
     * アクションフォームにお問合せ一覧機能で使用するお問合せの情報を格納するためのメソッド
     * @param contactExtendFormList お問合せのアクションフォームを格納したリスト(編集前)
     * @return お問合せのアクションフォームを格納したリスト(編集後)
     */
    public List<ContactExtendActionForm> setListContactData(List<ContactExtendActionForm> contactExtendFormList) {
        // リストに格納されたアクションフォームの情報を編集する。
        for(int i = 0; i < contactExtendFormList.size(); i++) {
            // リストからi番目のアクションフォームを取得する。
            ContactExtendActionForm eachForm = contactExtendFormList.get(i);

            // お問合せ日時(タイムスタンプ型)をお問合せ日時(文字列型)に変換してアクションフォームに格納する。
            eachForm.setStrContactedAt(
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(eachForm.getContactedAt()));
            // お問合せ更新日時(タイムスタンプ型)をお問合せ更新日時(文字列型)に変換してアクションフォームに格納する。
            eachForm.setStrContactUpdatedAt(
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(eachForm.getContactUpdatedAt()));

            // リストにアクションフォームを追加する。
            contactExtendFormList.set(i, eachForm);
        }

        // アクションフォームを格納したリストを戻す。
        return contactExtendFormList;
    }

    /**
     * お問合せのアクションフォームを格納したリストを指定したお問合せソート種別でソートするためのメソッド
     * @param contactExtendFormList お問合せのアクションフォームを格納したリスト(ソート前)
     * @param sortKindForContact お問合せソート種別
     * @return お問合せのアクションフォームを格納したリスト(ソート後)
     */
    public List<ContactExtendActionForm> sortContactExtendFormList(
            List<ContactExtendActionForm> contactExtendFormList,
            String sortKindForContact) {
        // お問合せソート種別を判定する。
        if("byDescendingContactedAt".equals(sortKindForContact)) {
            // "お問合せ日時が新しい順"の場合はお問合せ日時の降順でソートする。
            contactExtendFormList.sort(Comparator.comparing(ContactExtendActionForm::getContactedAt).reversed());
        } else if("byAccendingContactedAt".equals(sortKindForContact)) {
            // "お問合せ日時が古い順"の場合はお問合せ日時の昇順でソートする。
            contactExtendFormList.sort(Comparator.comparing(ContactExtendActionForm::getContactedAt));
        } else if("byDescendingUpdatedAt".equals(sortKindForContact)) {
            // "更新日時が新しい順"の場合はお問合せ更新日時の降順でソートする。
            contactExtendFormList.sort(Comparator.comparing(ContactExtendActionForm::getContactUpdatedAt).reversed());
        } else if("byAccendingUpdatedAt".equals(sortKindForContact)) {
            // "更新日時が古い順"の場合はお問合せ更新日時の昇順でソートする。
            contactExtendFormList.sort(Comparator.comparing(ContactExtendActionForm::getContactUpdatedAt));
        }

        // ソートしたリストを戻す。
        return contactExtendFormList;
    }

    /**
     * 現在のページに表示するお問合せのアクションフォームを格納したリストを生成するためのメソッド
     * @param contactExtendFormList お問合せのアクションフォームを格納したリスト
     * @param currentPageNum 現在のページ番号
     * @param displayedResult 表示件数/ページ
     * @return 画面に表示するお問合せのアクションフォームを格納したリスト
     */
    public List<ContactExtendActionForm> makeDisplayedContactList(
            List<ContactExtendActionForm> contactExtendFormList,
            int currentPageNum,
            int displayedResult) {
        // 表示するお問合せのアクションフォームを格納するリストを生成する。
        List<ContactExtendActionForm> displayedContactFormList = new ArrayList<>();
        // 最初に表示するお問合せの順番を算出する。
        int sequence = (currentPageNum - 1) * displayedResult;
        // リストの件数分、処理を繰り返す。
        for(int i = 0; i < contactExtendFormList.size(); i++) {
            if(sequence <= i && displayedContactFormList.size() < displayedResult) {
                // 最初に表示する順番以上かつリストに格納されたアクションフォームの件数が表示件数に満たない場合

                // リストからi番目のアクションフォームを取得する。
                ContactExtendActionForm eachForm = contactExtendFormList.get(i);

                // リストにアクションフォームを追加する。
                displayedContactFormList.add(eachForm);
            }
            // 表示件数分のアクションフォームが格納されているかを判定する。
            if(displayedContactFormList.size() == displayedResult) {
                // 格納されている場合は繰り返し処理を終了する。
                break;
            }
        }

        // アクションフォームを格納したリストを戻す。
        return displayedContactFormList;
    }

}
