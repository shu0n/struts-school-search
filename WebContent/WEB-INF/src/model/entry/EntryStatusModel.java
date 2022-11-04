package model.entry;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import actionform.EntryActionForm;
import actionform.EntryExtendActionForm;
import actionform.EntryStatusActionForm;
import dao.entry.SelectEntryDAO;
import dao.entry.SelectEntryJoinDAO;
import dao.entry.UpdateEntryDAO;
import dao.entry.sql.EntrySelectJoinWhereActionForm;
import dao.entry.sql.EntrySelectWhereActionForm;
import dao.entry.sql.EntryUpdateDataActionForm;
import dao.entrystatus.SelectEntryStatusDAO;
import dao.entrystatus.sql.EntryStatusSelectWhereActionForm;

public class EntryStatusModel {

    /**
     * 特定のスクールに申込済であるかを判定するためのメソッド
     * @param schoolId スクールID
     * @param accountId アカウントID
     * @param 判定結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean isEntried(int schoolId, int accountId) throws ClassNotFoundException, IOException, SQLException {
        // DAOメソッドに引き渡すアクションフォームを生成してスクールIDとアカウントIDを格納する。
        EntrySelectJoinWhereActionForm entryJoinSelectWhereForm = new EntrySelectJoinWhereActionForm();
        entryJoinSelectWhereForm.setEntrySchoolId(schoolId);
        entryJoinSelectWhereForm.setApplicantAccountId(accountId);
        // 申込テーブルからスクールIDとアカウントIDの両方に紐づくレコードを取得する。
        List<EntryExtendActionForm> entryExtendFormList
                = new SelectEntryJoinDAO().selectMatchedEntry(entryJoinSelectWhereForm);
        // 取得したレコードの件数分、処理を繰り返す。
        for(EntryExtendActionForm eachForm: entryExtendFormList) {
            if(!"キャンセル済".equals(eachForm.getEntryStatusName())) {
                // "キャンセル済"以外の申込ステータス名が登録されているレコードが存在する場合はtrueを戻す。
                return true;
            }
        }

        // 存在しない場合はfalseを戻す。
        return false;
    }

    /**
     * 特定の申込にアクセスしたアカウントが申込者本人であるかを判定するためのメソッド
     * @param entryId 申込ID
     * @param accountId アカウントID
     * @return 判定結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean isApplicant(int entryId, int accountId) throws ClassNotFoundException, IOException, SQLException {
        // DAOメソッドに引き渡すアクションフォームを生成して申込IDを格納する。
        EntrySelectWhereActionForm entrySelectWhereForm = new EntrySelectWhereActionForm();
        entrySelectWhereForm.setEntryId(entryId);
        // 申込テーブルから申込IDに紐づくレコードのリストを取得する。
        List<EntryActionForm> entryFormList = new SelectEntryDAO().selectMatchedEntry(entrySelectWhereForm);
        if(entryFormList.isEmpty()) {
            // 取得できない場合はfalseを戻す。
            return false;
        } else if(accountId != entryFormList.get(0).getApplicantAccountId()) {
            // 引数のアカウントIDと申込レコードに登録されているアカウントIDが一致しない場合はfalseを戻す。
            return false;
        } else {
            // 上記以外の場合はtrueを戻す。
            return true;
        }
    }

    /**
     * 特定の申込がキャンセル済であるかを判定するためのメソッド
     * @param entryId 申込ID
     * @return 判定結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean isCanceled(int entryId) throws ClassNotFoundException, IOException, SQLException {
        // DAOメソッドに引き渡すアクションフォームを生成して申込IDを格納する。
        EntrySelectJoinWhereActionForm entryJoinSelectWhereForm = new EntrySelectJoinWhereActionForm();
        entryJoinSelectWhereForm.setEntryId(entryId);
        // 申込テーブルから申込IDに紐づくレコードのリストを取得する。
        List<EntryExtendActionForm> entryExtendFormList =
                new SelectEntryJoinDAO().selectMatchedEntry(entryJoinSelectWhereForm);
        if(entryExtendFormList.isEmpty()) {
            // 取得できない場合はfalseを戻す。
            return false;
        }
        if(!entryExtendFormList.get(0).getEntryStatusName().equals("キャンセル済")) {
            // 申込ステータス名がキャンセル済以外の場合はfalseを戻す。
            return false;
        } else {
            // 上記以外の場合はtrueを戻す。
            return true;
        }
    }

    /**
     * 申込ステータス名が"申込済"の申込ステータスIDを取得するためのメソッド
     * @return 申込ステータス名が"申込済"の申込ステータスID
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public int getEtriedStatusId() throws ClassNotFoundException, IOException, SQLException {
        // DAOメソッドに引き渡すアクションフォームを生成して申込ステータス名を格納する。
        EntryStatusSelectWhereActionForm entrySelectWhereForm = new EntryStatusSelectWhereActionForm();
        entrySelectWhereForm.setEntryStatusName("申込済");
        // 申込ステータステーブルの全レコードを取得する。
        List<EntryStatusActionForm> entryStatusFormList
                = new SelectEntryStatusDAO().selectMatchedEntryStatus(entrySelectWhereForm);

        // 取得した申込ステータスIDを戻す。
        return entryStatusFormList.get(0).getEntryStatusId();
    }

    /**
     * 申込ステータス名が"キャンセル済"の申込ステータスIDを取得するためのメソッド
     * @return 申込ステータス名が"キャンセル済"の申込ステータスID
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public int getCancelStatusId() throws ClassNotFoundException, IOException, SQLException {
        // DAOメソッドに引き渡すアクションフォームを生成して申込ステータス名を格納する。
        EntryStatusSelectWhereActionForm entrySelectWhereForm = new EntryStatusSelectWhereActionForm();
        entrySelectWhereForm.setEntryStatusName("キャンセル済");
        // 申込ステータステーブルの全レコードを取得する。
        List<EntryStatusActionForm> entryStatusFormList
                = new SelectEntryStatusDAO().selectMatchedEntryStatus(entrySelectWhereForm);

        // 取得した申込ステータスIDを戻す。
        return entryStatusFormList.get(0).getEntryStatusId();
    }

    /**
     * 申込ステータスを"連絡済"に変更するためのメソッド
     * @return 実行結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean updateStatusContacted(int entryId) throws ClassNotFoundException, IOException, SQLException {
        // DAOメソッドに引き渡すアクションフォームを生成して申込ステータス名を格納する。
        EntryStatusSelectWhereActionForm entrySelectWhereForm = new EntryStatusSelectWhereActionForm();
        entrySelectWhereForm.setEntryStatusName("連絡済");
        // 申込ステータステーブルの全レコードを取得する。
        List<EntryStatusActionForm> entryStatusFormList
                = new SelectEntryStatusDAO().selectMatchedEntryStatus(entrySelectWhereForm);

        // DAOメソッドに引き渡すアクションフォームを生成して申込IDと申込ステータスIDを格納する。
        EntryUpdateDataActionForm entryUpdateDataForm = new EntryUpdateDataActionForm();
        entryUpdateDataForm.setEntryId(entryId);
        entryUpdateDataForm.setEntryStatusId(entryStatusFormList.get(0).getEntryStatusId());

        // 更新結果を戻す。
        return new UpdateEntryDAO().updateEntry(entryUpdateDataForm);
    }

}
