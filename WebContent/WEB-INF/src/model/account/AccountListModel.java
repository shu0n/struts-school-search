package model.account;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import actionform.AccountExtendActionForm;

public class AccountListModel {

    /**
     * アクションフォームにアカウント一覧機能で使用する情報を格納するためのメソッド
     * @param accountExtendFormList アカウントのアクションフォームを格納したリスト(編集前)
     * @return アカウントのアクションフォームを格納したリスト(編集後)
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<AccountExtendActionForm> setListAccountData(List<AccountExtendActionForm> accountExtendFormList)
            throws ClassNotFoundException, IOException, SQLException {
        for(int i = 0; i < accountExtendFormList.size(); i++) {
            // リストからi番目のアクションフォームを取得する。
            AccountExtendActionForm eachForm = accountExtendFormList.get(i);

            // アクションフォームから性別を取得する。
            int sexId = eachForm.getSexId();
            if(sexId != 0) {
                // 性別が登録されている場合は性別名を取得してアクションフォームに格納する。
                eachForm.setSexName(new AccountFormPartsModel().getSexMap().get(sexId));
            } else {
                // 上記以外の場合は空文字を設定する。
                eachForm.setSexName("");
            }

            // アクションフォームから生年月日を取得する。
            Timestamp birthDate = eachForm.getBirthDate();
            if(birthDate != null) {
                // 生年月日がNULLではない場合

                // 生年月日から文字列型の年、月、日、生年月日を取得してアクションフォームに格納する。
                eachForm.setBirthYear(new SimpleDateFormat("yyyy").format(birthDate));
                eachForm.setBirthMonth(new SimpleDateFormat("MM").format(birthDate));
                eachForm.setBirthDay(new SimpleDateFormat("dd").format(birthDate));
                eachForm.setStrBirthDate(new SimpleDateFormat("yyyy/MM/dd").format(birthDate));
            } else {
                // 上記以外の場合は空文字を格納する。
                eachForm.setBirthYear("");
                eachForm.setBirthMonth("");
                eachForm.setBirthDay("");
                eachForm.setStrBirthDate("");
            }

            // アカウントステータス名を取得してアクションフォームに格納する。
            eachForm.setAccountStatusName(
                    new AccountFormPartsModel().getAccountStatusMap().get(eachForm.getAccountStatus()));

            // 作成日時(日時型)を作成日時(文字列型)に変換してアクションフォームに格納する。
            eachForm.setStrAccountCreatedAt(
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(eachForm.getAccountCreatedAt()));
            // 更新日時(日時型)を更新日時(文字列型)に変換してアクションフォームに格納する。
            eachForm.setStrAccountUpdatedAt(
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(eachForm.getAccountUpdatedAt()));

            // リストにアクションフォームを追加する。
            accountExtendFormList.set(i, eachForm);
        }

        // アクションフォームを格納したリストを戻す。
        return accountExtendFormList;
    }

    /**
     * アカウントのアクションフォームを格納したリストを指定したアカウントソート種別でソートするためのメソッド
     * @param accountExtendFormList アカウントのアクションフォームを格納したリスト(ソート前)
     * @param sortKindForAccount アカウントソート種別
     * @return アカウントのアクションフォームを格納したリスト(ソート後)
     */
    public List<AccountExtendActionForm> sortAccountExtendFormList(
            List<AccountExtendActionForm> accountExtendFormList,
            String sortKindForAccount) {
        if("byDescendingCreatedAt".equals(sortKindForAccount)) {
            // アカウントソート種別が"作成日時が新しい順"の場合はアカウント作成日時の降順でソートする。
            accountExtendFormList.sort(Comparator.comparing(AccountExtendActionForm::getAccountCreatedAt).reversed());
        } else if("byAccendingCreatedAt".equals(sortKindForAccount)) {
            // アカウントソート種別が"作成日時が古い順"の場合はアカウント作成日時の昇順でソートする。
            accountExtendFormList.sort(Comparator.comparing(AccountExtendActionForm::getAccountCreatedAt));
        } else if("byDescendingUpdatedAt".equals(sortKindForAccount)) {
            // アカウントソート種別が"更新日時が新しい順"の場合はアカウント更新日時の降順でソートする。
            accountExtendFormList.sort(Comparator.comparing(AccountExtendActionForm::getAccountUpdatedAt).reversed());
        } else if("byAccendingUpdatedAt".equals(sortKindForAccount)) {
            // アカウントソート種別が"更新日時が古い順"の場合はアカウント更新日時の昇順でソートする。
            accountExtendFormList.sort(Comparator.comparing(AccountExtendActionForm::getAccountUpdatedAt));
        }

        // ソートしたリストを戻す。
        return accountExtendFormList;
    }

    /**
     * 画面に表示するアカウントのアクションフォームを格納したリストを生成するためのメソッド
     * @param accountExtendFormList アカウントのアクションフォームを格納したリスト
     * @param currentPageNum 現在のページ番号
     * @param displayedResult 表示件数/ページ
     * @return 画面に表示するアカウントのアクションフォームを格納したリスト
     */
    public List<AccountExtendActionForm> makeDisplayedAccountList(
            List<AccountExtendActionForm> accountExtendFormList,
            int currentPageNum,
            int displayedResult) {
        // 表示するアカウントのアクションフォームを格納するリストを生成する。
        List<AccountExtendActionForm> displayedAccountFormList = new ArrayList<>();
        // 最初に表示するアカウントの順番を算出する。
        int sequence = (currentPageNum - 1) * displayedResult;
        // リストの件数分、処理を繰り返す。
        for(int i = 0; i < accountExtendFormList.size(); i++) {
            if(sequence <= i && displayedAccountFormList.size() < displayedResult) {
                // 最初に表示する順番以上かつリストに格納されたアクションフォームの件数が表示件数に満たない場合は
                // リストにi番目のアクションフォームを追加する。
                displayedAccountFormList.add(accountExtendFormList.get(i));
            }
            if(displayedAccountFormList.size() == displayedResult) {
                // 表示件数分のアクションフォームが格納されている場合は繰り返し処理を終了する。
                break;
            }
        }

        // 表示するアカウントのアクションフォームを格納したリストを戻す。
        return displayedAccountFormList;
    }

}
