package dao.entry.repack;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import actionform.AccountActionForm;
import actionform.EntryExtendActionForm;
import actionform.SchoolExtendActionForm;
import dao.account.SelectAccountDAO;
import dao.account.sql.AccountSelectWhereActionForm;
import dao.entry.SelectEntryJoinDAO;
import dao.entry.sql.EntrySelectJoinWhereActionForm;
import dao.school.SelectSchoolJoinDAO;
import dao.school.sql.SchoolSelectJoinWhereActionForm;
import exception.NoDataExistException;

public class GetEntryDataRepack {

    /**
     * 申込テーブルから申込の情報を取得するためのメソッド
     * @param entryId 申込ID
     * @param inForm 申込のデータを格納するアクションフォーム
     * @return 検索条件に一致した申込のアクションフォーム
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoDataExistException
     */
    public EntryExtendActionForm getEntryData(int entryId, EntryExtendActionForm inForm)
            throws ClassNotFoundException, IOException, SQLException, NoDataExistException {
        // DAOメソッドに引き渡すアクションフォームを生成して申込IDを格納する。
        EntrySelectJoinWhereActionForm entryJoinSelectWhereForm = new EntrySelectJoinWhereActionForm();
        entryJoinSelectWhereForm.setEntryId(entryId);
        // 申込テーブルから検索条件に一致するレコードを取得する。
        List<EntryExtendActionForm> entryExtendFormList
                = new SelectEntryJoinDAO().selectMatchedEntry(entryJoinSelectWhereForm);
        if(entryExtendFormList.isEmpty()) {
            // 取得結果が0件の場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        }

        // リストの1件目を取得する。
        EntryExtendActionForm entryExtendForm = entryExtendFormList.get(0);
        // アクションフォームに取得したレコードのデータを格納する。
        inForm.setEntryId(entryExtendForm.getEntryId()); // 申込ID
        inForm.setEntrySchoolId(entryExtendForm.getEntrySchoolId()); // 申込先スクールID
        inForm.setEntrySchoolName(entryExtendForm.getEntrySchoolName()); // 申込先スクール名
        inForm.setApplicantAccountId(entryExtendForm.getApplicantAccountId()); // 申込者アカウントID
        inForm.setEntryQuestion(entryExtendForm.getEntryQuestion()); // 質問
        inForm.setEntryStatusId(entryExtendForm.getEntryStatusId()); // 申込ステータスID
        inForm.setEntryStatusName(entryExtendForm.getEntryStatusName()); // 申込ステータス名
        inForm.setEntriedAt(entryExtendForm.getEntriedAt()); // 申込日時
        inForm.setEntryUpdatedAt(entryExtendForm.getEntryUpdatedAt()); // 申込更新日時

        // 文字列型に変換した申込日時をアクションフォームに格納する。
        inForm.setStrEntriedAt(getStrTimestamp(entryExtendForm.getEntriedAt()));
        // 文字列型に変換した更新日時をアクションフォームに格納する。
        inForm.setStrEntryUpdatedAt(getStrTimestamp(entryExtendForm.getEntryUpdatedAt()));

        // 申込の情報を格納したアクションフォームを戻す。
        return inForm;
    }

    /**
     * 申込テーブルから申込スクールの情報を取得すためのメソッド
     * @param entrySchoolId 申込先スクールID
     * @param applicantAccountId 申込者アカウントID
     * @param inForm 申込のデータを格納するアクションフォーム
     * @return 検索条件に一致した申込のアクションフォーム
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoDataExistException
     */
    public EntryExtendActionForm getEntrySchoolData(
            int entrySchoolId,
            int applicantAccountId,
            EntryExtendActionForm inForm)
            throws ClassNotFoundException, IOException, SQLException, NoDataExistException {
        // DAOメソッドに引き渡すアクションフォームを生成して申込先スクールIDを格納する。
        SchoolSelectJoinWhereActionForm schoolSelectJoinWhereForm = new SchoolSelectJoinWhereActionForm();
        schoolSelectJoinWhereForm.setSchoolId(entrySchoolId);
        // スクールIDに紐づくレコードを取得する。
        List<SchoolExtendActionForm> schoolExtendFormList
                = new SelectSchoolJoinDAO().selectMatchedSchool(schoolSelectJoinWhereForm);
        if(schoolExtendFormList.isEmpty()) {
            // 取得結果が0件の場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        }

        // リストの1件目を取得する。
        SchoolExtendActionForm schoolExtendForm = schoolExtendFormList.get(0);
        // アクションフォームに取得したレコードのデータを格納する。
        inForm.setEntrySchoolId(entrySchoolId); // スクールID
        inForm.setApplicantAccountId(applicantAccountId); // アカウントID
        inForm.setEntrySchoolName(schoolExtendForm.getSchoolName()); // 申込先スクール名
        inForm.setSchoolCategoryName(schoolExtendForm.getSchoolCategoryName()); // スクールカテゴリー名
        inForm.setSchoolFee(schoolExtendForm.getSchoolFee()); // スクール費用
        inForm.setRegistrantAccountId(schoolExtendForm.getRegistrantAccountId()); // スクール登録者アカウントID
        inForm.setRegistrantLastName(schoolExtendForm.getRegistrantLastName()); // スクール登録者姓
        inForm.setRegistrantFirstName(schoolExtendForm.getRegistrantFirstName()); // スクール登録者名
        inForm.setRegistrantLastNameKana(schoolExtendForm.getRegistrantLastNameKana()); // スクール登録者姓(フリガナ)
        inForm.setRegistrantFirstNameKana(schoolExtendForm.getRegistrantFirstNameKana()); // スクール登録者名(フリガナ)
        inForm.setRegistrantAdminId(schoolExtendForm.getRegistrantAdminId()); // スクール登録者管理者ID

        // スクールの情報を格納したアクションフォームを戻す。
        return inForm;
    }

    /**
     * 申込テーブルから申込者アカウントの情報を取得するためのメソッド
     * @param applicantAccountId 申込者アカウントID
     * @param inForm 申込のデータを格納するアクションフォーム
     * @return 検索条件に一致した申込のデータを格納したアクションフォーム
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoDataExistException
     */
    public EntryExtendActionForm getApplicantAccountData(int applicantAccountId, EntryExtendActionForm inForm)
            throws ClassNotFoundException, IOException, SQLException, NoDataExistException {
        // DAOメソッドに引き渡すアクションフォームを生成して申込者アカウントIDを格納する。
        AccountSelectWhereActionForm accountSelectWhereForm = new AccountSelectWhereActionForm();
        accountSelectWhereForm.setAccountId(applicantAccountId);
        // アカウントテーブルの申込者のアカウントIDに紐づくレコードを取得する。
        List<AccountActionForm> accountFormList
                = new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm);
        if(accountFormList.isEmpty()) {
            // 取得結果が0件の場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        }

        // リストから1件目のアクションフォームを取得する。
        AccountActionForm accountForm = accountFormList.get(0);
        // アクションフォームに取得したレコードのデータを格納する。
        inForm.setApplicantAccountId(accountForm.getAccountId()); // 申込者アカウントID
        inForm.setApplicantLastName(accountForm.getLastName()); // 申込者姓
        inForm.setApplicantFirstName(accountForm.getFirstName()); // 申込者名
        inForm.setApplicantLastNameKana(accountForm.getLastNameKana()); // 申込者姓(フリガナ)
        inForm.setApplicantFirstNameKana(accountForm.getFirstNameKana()); // 申込者名(フリガナ)

        // アカウントの情報を格納したアクションフォームを戻す。
        return inForm;
    }

    /**
     * 日時(文字列型)を取得するためのメソッド
     */
    String getStrTimestamp(Timestamp timstamp) {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(timstamp);
    }

}
