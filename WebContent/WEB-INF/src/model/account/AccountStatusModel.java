package model.account;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import actionform.AccountActionForm;
import dao.account.SelectAccountDAO;
import dao.account.UpdateAccountDAO;
import dao.account.sql.AccountSelectWhereActionForm;
import dao.account.sql.AccountUpdateDataActionForm;
import exception.NoDataExistException;

public class AccountStatusModel {

    /**
     * メールアドレスが登録済みであるかを判定するためのメソッド
     * @param mailAddress メールアドレス
     * @param accountId アカウントID
     * @param isCreatingAccount アカウント作成時点か否か
     * @return 判定結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean isMailAddressExist(String mailAddress, int accountId, boolean isCreatingAccount)
            throws ClassNotFoundException, IOException, SQLException {
        // DAOメソッドに引き渡すためのアクションフォームを生成してメールアドレスを格納する。
        AccountSelectWhereActionForm accountSelectWhereForm = new AccountSelectWhereActionForm();
        accountSelectWhereForm.setMailAddress(mailAddress);
        if(isCreatingAccount) {
            // アカウント作成時の場合

            if(new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm).isEmpty()) {
                // 入力されたメールアドレスに紐づくレコードが取得できない場合はfalseを戻す。
                return false;
            } else {
                // 上記以外の場合はtrueを戻す。
                return true;
            }
        } else {
            // 上記以外の場合(アカウント編集時点)

            // 入力されたメールアドレスに紐づくレコードを取得する。
            List<AccountActionForm> accountFormList
                    = new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm);
            if(!accountFormList.isEmpty()) {
                // レコードが存在する場合

                if(accountId == accountFormList.get(0).getAccountId()) {
                    // 自アカウントのメールアドレスに登録されている場合はfalseを戻す。
                    return false;
                } else {
                    // 上記以外の場合はtrueを戻す。
                    return true;
                }
            } else {
                // 上記以外の場合はfalseを戻す。
                return false;
            }
        }
    }

    /**
     * メールアドレスとアカウント有効化トークンをもとにアカウントステータスを有効化するためのメソッド
     * @param mailAddress メールアドレス
     * @param activateToken 有効化トークン
     * @return アカウントID
     * @throws NoDataExistException
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public int activateAccount(String mailAddress, String activateToken)
            throws NoDataExistException, ClassNotFoundException, IOException, SQLException {
        // DTOメソッドに引き渡すためのアクションフォームを生成してメールアドレスと有効化トークンを格納する。
        AccountSelectWhereActionForm accountSelectWhereForm = new AccountSelectWhereActionForm();
        accountSelectWhereForm.setMailAddress(mailAddress);
        accountSelectWhereForm.setActivateToken(activateToken);
        // 入力されたメールアドレスに紐づくレコードを取得する。
        List<AccountActionForm> accountFormList = new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm);
        if(accountFormList.isEmpty()) {
            // 取得できない場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        }

        Timestamp activateEffectiveDate = accountFormList.get(0).getActivateEffectiveDate(); // 有効化トークン有効期限
        if(getCurrentTimestamp().after(activateEffectiveDate)) {
            // 現時点が有効期限より後の場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        }

        if("1".equals(accountFormList.get(0).getAccountStatus())) {
            // アカウントステータスが"1"(有効)の場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        }

        // アカウントIDを取得する。
        int accountId = accountFormList.get(0).getAccountId(); // アカウントID

        // DTOメソッドに引き渡すためのアクションフォームを生成してアカウントステータスに"1"(有効)を格納する。
        AccountUpdateDataActionForm accountUpdateDataForm = new AccountUpdateDataActionForm();
        accountUpdateDataForm.setAccountId(accountId);
        accountUpdateDataForm.setAccountStatus("1");
        accountUpdateDataForm.setActivateTokenToToNullFlag(true);
        accountUpdateDataForm.setActivateEffectiveDateToNullFlag(true);
        // アカウントを有効化する。
        new UpdateAccountDAO().updateAccount(accountUpdateDataForm);

        // アカウントIDを戻す。
        return accountId;
    }

    /**
     * 特定のアカウントIDが"有効"のステータスでアカウントテーブルに登録されているかを判定するためのメソッド
     * @param accountId アカウントID
     * @return 判定結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean isAccountEnable(int accountId) throws ClassNotFoundException, IOException, SQLException {
        // DAOメソッドに引き渡すアクションフォームを生成してアカウントIDを格納する。
        AccountSelectWhereActionForm accountSelectWhereForm = new AccountSelectWhereActionForm();
        accountSelectWhereForm.setAccountId(accountId);
        accountSelectWhereForm.setAccountStatus("1");
        if(new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm).isEmpty()) {
            // アカウントテーブルから条件に一致するレコードを取得できない場合はfalseを戻す。
            return false;
        } else {
            // 上記以外の場合はtrueを戻す。
            return true;
        }
    }

    /**
     * 現在日時(日時型)を取得するためのメソッド
     */
    Timestamp getCurrentTimestamp() {
        // 現在日時(日時型)を戻す。
        return new Timestamp(System.currentTimeMillis());
    }

}
