package model.school;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import actionform.EntryActionForm;
import actionform.SchoolExtendActionForm;
import dao.entry.SelectEntryDAO;
import dao.entry.sql.EntrySelectWhereActionForm;
import util.ImageFileUtil;

public class SchoolListModel {

    /**
     * アクションフォームにスクール一覧機能で使用するスクールの情報を格納するためのメソッド
     * @param schoolExtendFormList スクールのアクションフォームを格納したリスト(編集前)
     * @return スクールのアクションフォームを格納したリスト(編集後)
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<SchoolExtendActionForm> setListSchoolData(List<SchoolExtendActionForm> schoolExtendFormList)
            throws ClassNotFoundException, IOException, SQLException {
        // リストに格納されたアクションフォームにのデータを編集する。
        for(int i = 0; i < schoolExtendFormList.size(); i++) {
            // リストからアクションフォームを取得する。
            SchoolExtendActionForm eachForm = schoolExtendFormList.get(i);

            // 公開可否名を取得してアクションフォームに格納する。
            eachForm.setSchoolReleaseProprietyName(
                    new SchoolFormPartsModel().getReleaseProprietyMap().get(eachForm.getSchoolReleasePropriety()));
            // 申込可否名を取得してアクションフォームに格納する。
            eachForm.setSchoolEntryProprietyName(
                    new SchoolFormPartsModel().getEntryProprietyMap().get(eachForm.getSchoolEntryPropriety()));

            // 登録日時(タイムスタンプ型)を登録日時(文字列型)に変換してアクションフォームに格納する。
            eachForm.setStrSchoolRegisteredAt(
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(eachForm.getSchoolRegisteredAt()));
            // 更新日時(タイムスタンプ型)を更新日時(文字列型)に変換してアクションフォームに格納する。
            eachForm.setStrSchoolUpdatedAt(
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(eachForm.getSchoolUpdatedAt()));

            // DAOメソッドに引き渡すアクションフォームを生成してスクールIDを格納する。
            EntrySelectWhereActionForm entrySelectWhereForm = new EntrySelectWhereActionForm();
            entrySelectWhereForm.setEntrySchoolId(eachForm.getSchoolId());
            // 申込件数を取得してアクションフォームに格納する。
            List<EntryActionForm> entryFormList = new SelectEntryDAO().selectMatchedEntry(entrySelectWhereForm);
            eachForm.setAllEntryNum(entryFormList.size());

            // リストにアクションフォームを格納する。
            schoolExtendFormList.set(i, eachForm);
        }

        // アクションフォームを格納したリストを戻す。
        return schoolExtendFormList;
    }

    /**
     * スクールのアクションフォームを格納したリストを指定したスクールソート種別でソートするためのメソッド
     * @param schoolExtendFormList スクールのアクションフォームを格納したリスト(ソート前)
     * @param sortKindForSchool スクールソート種別
     * @return スクールのアクションフォームを格納したリスト(ソート後)
     */
    public List<SchoolExtendActionForm> sortSchoolExtendFormList(
            List<SchoolExtendActionForm> schoolExtendFormList,
            String sortKindForSchool) {
        if("byDescendingRegisteredAt".equals(sortKindForSchool)) {
            // スクールソート種別が"登録日が新しい順"の場合はスクール登録日時の降順でソートする。
            schoolExtendFormList.sort(Comparator.comparing(SchoolExtendActionForm::getSchoolRegisteredAt).reversed());
        } else if("byAccendingRegisteredAt".equals(sortKindForSchool)) {
            // スクールソート種別が"登録日が古い順"の場合はスクール登録日時の昇順でソートする。
            schoolExtendFormList.sort(Comparator.comparing(SchoolExtendActionForm::getSchoolRegisteredAt));
        } else if("byDescendingUpdatedAt".equals(sortKindForSchool)) {
            // スクールソート種別が"更新日が新しい順"の場合はスクール更新日時の降順でソートする。
            schoolExtendFormList.sort(Comparator.comparing(SchoolExtendActionForm::getSchoolUpdatedAt).reversed());
        } else if("byAccendingUpdatedAt".equals(sortKindForSchool)) {
            // スクールソート種別が"更新日が古い順"の場合はスクール更新日時の昇順でソートする。
            schoolExtendFormList.sort(Comparator.comparing(SchoolExtendActionForm::getSchoolUpdatedAt));
        } else if("byDescendingSchoolFee".equals(sortKindForSchool)) {
            // スクールソート種別が"費用が高い順"の場合はスクール費用の降順でソートする。
            schoolExtendFormList.sort(Comparator.comparing(SchoolExtendActionForm::getSchoolFee).reversed());
        } else if("byAccendingSchoolFee".equals(sortKindForSchool)) {
            // スクールソート種別が"費用が安い順"の場合はスクール費用の昇順でソートする。
            schoolExtendFormList.sort(Comparator.comparing(SchoolExtendActionForm::getSchoolFee));
        }

        // ソートしたリストを戻す。
        return schoolExtendFormList;
    }

    /**
     * 現在のページに表示するスクールのアクションフォームを格納したリストを生成するためのメソッド
     * @param schoolExtendFormList スクールのアクションフォームを格納したリスト
     * @param currentPageNum 現在のページ番号
     * @param displayedResult 表示件数/ページ
     * @return 画面に表示するスクールのアクションフォームを格納したリスト
     * @throws IOException
     */
    public List<SchoolExtendActionForm> makeDisplayedSchoolList(
            List<SchoolExtendActionForm> schoolExtendFormList,
            int currentPageNum,
            int displayedResult) throws IOException {
        // 表示するスクールのアクションフォームを格納するリストを生成する。
        List<SchoolExtendActionForm> displayedSchoolExtendFormList = new ArrayList<>();
        // 最初に表示するスクールの順番を算出する。
        int sequence = (currentPageNum - 1) * displayedResult;
        // リストの件数分、処理を繰り返す。
        for(int i = 0; i < schoolExtendFormList.size(); i++) {
            if(sequence <= i && displayedSchoolExtendFormList.size() < displayedResult) {
                // 最初に表示する順番以上かつリストに格納されたアクションフォームの件数が表示件数に満たない場合

                // リストからアクションフォームを取得する。
                SchoolExtendActionForm eachForm = schoolExtendFormList.get(i);

                // 一覧画面画像のファイル名を取得する。
                String summaryImagefileName = eachForm.getSummaryImageFileName();
                if(summaryImagefileName != null) {
                    // 一覧画面画像のファイル名が存在する場合はパスを取得する。
                    String summaryImagefilePath = new ImageFileUtil().getImageFilePath(summaryImagefileName);
                    // アクションフォームに取得したパスを格納する。
                    eachForm.setSummaryImageFilePath(summaryImagefilePath);
                }

                // リストにアクションフォームを追加する。
                displayedSchoolExtendFormList.add(eachForm);
            }
            if(displayedSchoolExtendFormList.size() == displayedResult) {
                // 表示件数分のアクションフォームが格納されている場合は繰り返し処理を終了する。
                break;
            }
        }

        // 表示するスクールのアクションフォームを格納したリストを戻す。
        return displayedSchoolExtendFormList;
    }

}
