package model.school;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import actionform.AccountActionForm;
import dao.account.SelectAccountDAO;
import dao.account.sql.AccountSelectWhereActionForm;
import dao.school.SelectSchoolDAO;
import dao.school.sql.SchoolSelectWhereActionForm;
import exception.NoDataExistException;

public class SchoolRegistrantModel {

    /**
     * 特定のアカウントとスクール登録者アカウントが同一であるかを判定するためのメソッド
     * @param schoolId スクールID
     * @param accountId アカウントID
     * @return 判定結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean isRegistrant(int schoolId, int accountId)
            throws ClassNotFoundException, IOException, SQLException {
        if(accountId == 0) {
            // アカウントIDが0の場合はfalseを戻す。
            return false;
        }

        // DAOメソッドに引き渡すアクションフォームを生成してスクールID、アカウントID、管理者IDを格納する。
        SchoolSelectWhereActionForm schoolSelectWhereForm = new SchoolSelectWhereActionForm();
        schoolSelectWhereForm.setSchoolId(schoolId);
        schoolSelectWhereForm.setRegistrantAccountId(accountId);
        if(new SelectSchoolDAO().selectMatchedSchool(schoolSelectWhereForm).isEmpty()) {
            // スクールテーブルから条件に一致するレコードが取得できない場合はfalseを戻す。
            return false;
        } else {
            // 上記以外の場合はtrueを戻す。
            return true;
        }
    }

    /**
     * 特定の管理者とスクール登録者が同一であるかを判定するためのメソッド
     * @param schoolId スクールID
     * @param adminId 管理者ID
     * @return 判定結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean isRegistrantAdmin(int schoolId, int adminId)
            throws ClassNotFoundException, IOException, SQLException {
        if(adminId == 0) {
            // 管理者IDが0の場合はfalseを戻す。
            return false;
        }

        // DAOメソッドに引き渡すアクションフォームを生成してスクールID、アカウントID、管理者IDを格納する。
        SchoolSelectWhereActionForm schoolSelectWhereForm = new SchoolSelectWhereActionForm();
        schoolSelectWhereForm.setSchoolId(schoolId);
        schoolSelectWhereForm.setRegistrantAdminId(adminId);
        if(new SelectSchoolDAO().selectMatchedSchool(schoolSelectWhereForm).isEmpty()) {
            // スクールテーブルから条件に一致するレコードが取得できない場合はfalseを戻す。
            return false;
        } else {
            // 上記以外の場合はtrueを戻す。
            return true;
        }
    }

    /**
     * スクール登録者アカウントの姓と名を取得するためのメソッド
     * @param accountId アカウントID
     * @return スクール登録者の姓と名を格納したマップまたはNULL
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     * @throws NoDataExistException
     */
    public Map<String, String> getRegistrantName(int accountId)
            throws ClassNotFoundException, IOException, SQLException, NoDataExistException {
        // DAOメソッドに引き渡すアクションフォームを生成してアカウントIDを格納する。
        AccountSelectWhereActionForm accountSelectWhereForm = new AccountSelectWhereActionForm();
        accountSelectWhereForm.setAccountId(accountId);
        // アカウントテーブルから条件に一致するレコードを取得する。
        List<AccountActionForm> accountFormList = new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm);
        if(accountFormList.isEmpty()) {
            // 取得できない場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        } else {
            // 上記以外の場合

            // マップを生成してスクール登録者の姓と名を格納する。
            Map<String, String> registrantAccountMap = new HashMap<>();
            registrantAccountMap.put("registrantLastName", accountFormList.get(0).getLastName());
            registrantAccountMap.put("registrantFirstName", accountFormList.get(0).getFirstName());

            // スクール登録者の姓と名を格納したマップを戻す。
            return registrantAccountMap;
        }
    }

}
