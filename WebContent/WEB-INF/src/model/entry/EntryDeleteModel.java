package model.entry;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import actionform.EntryActionForm;
import dao.entry.DeleteEntryDAO;
import dao.entry.SelectEntryDAO;
import dao.entry.sql.EntrySelectWhereActionForm;

public class EntryDeleteModel {

    /**
     * 退会するアカウントに紐づく申込を論理削除するためのメソッド
     * @param accountId アカウントID
     * @return 実行結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public void deleteEntryForDeleteAccount(int accountId)
            throws ClassNotFoundException, IOException, SQLException {
        // DAOメソッドに引き渡すアクションフォームを生成してアカウントIDを格納する。
        EntrySelectWhereActionForm entrySelectWhereForm = new EntrySelectWhereActionForm();
        entrySelectWhereForm.setApplicantAccountId(accountId);
        // アカウントIDに紐づく申込のリストを取得する。
        List<EntryActionForm> entryFormList = new SelectEntryDAO().selectMatchedEntry(entrySelectWhereForm);
        // 申込テーブルの削除処理を扱うクラスのインスタンスを生成する。
        DeleteEntryDAO deleteEntryDao = new DeleteEntryDAO();
        // リストの件数分、処理を繰り返す。
        for(EntryActionForm eachForm: entryFormList) {
            // 申込IDに紐づくレコードを論理削除する。
            deleteEntryDao.deleteEntryLogically(eachForm.getEntryId());
        }
    }

}
