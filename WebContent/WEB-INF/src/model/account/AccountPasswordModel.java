package model.account;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import actionform.AccountActionForm;
import dao.account.SelectAccountDAO;
import dao.account.UpdateAccountDAO;
import dao.account.sql.AccountSelectWhereActionForm;
import dao.account.sql.AccountUpdateDataActionForm;
import exception.NoDataExistException;
import util.DateUtil;
import util.PasswordUtil;
import util.PropertyUtil;
import util.TokenUtil;

public class AccountPasswordModel {

    /**
     * パスワードを変更するためのメソッド
     * @param accountId アカウントID
     * @param password 現在のパスワード
     * @param newPassword 新しいパスワード
     * @return 実行結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     * @throws NoDataExistException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws RuntimeException
     */
    public boolean changeAccountPassword(int accountId, String password, String newPassword) throws
            ClassNotFoundException,
            IOException,
            SQLException,
            NoDataExistException,
            InvalidKeySpecException,
            NoSuchAlgorithmException,
            RuntimeException {
        // DAOメソッドに引き渡すためのアクションフォームを生成してアカウントIDを格納する。
        AccountSelectWhereActionForm accountSelectWhereForm = new AccountSelectWhereActionForm();
        accountSelectWhereForm.setAccountId(accountId);
        // アカウントテーブルからアカウントIDに紐づくレコードを取得する。
        List<AccountActionForm> accountFormList = new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm);
        if(accountFormList.isEmpty()) {
            // 取得できない場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        }

        // メールアドレスを取得する。
        String mailAddress = accountFormList.get(0).getMailAddress();
        // 入力された現在のパスワードを安全なパスワードに変換する。
        String safetyPassword = new PasswordUtil().getSafetyPassword(password, mailAddress);
        // DAOメソッドに引き渡すためのアクションフォームを初期化してアカウントIDを格納する。
        accountSelectWhereForm = new AccountSelectWhereActionForm();
        accountSelectWhereForm.setMailAddress(mailAddress);
        accountSelectWhereForm.setPassword(safetyPassword);
        // アカウントテーブルからメールアドレスと安全なパスワードに紐づくレコードを取得する。
        accountFormList = new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm);
        if(accountFormList.isEmpty()) {
            // 取得できない場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        }

        // 新しいパスワードを安全なパスワードに変換する。
        String safetyNewPassword = new PasswordUtil().getSafetyPassword(newPassword, mailAddress);
        // DAOメソッドに引き渡すためのアクションフォームを生成して安全なパスワードを格納する。
        AccountUpdateDataActionForm accountUpdateDataForm = new AccountUpdateDataActionForm();
        accountUpdateDataForm.setAccountId(accountId);
        accountUpdateDataForm.setPassword(safetyNewPassword);

        // パスワードを更新して更新結果を戻す。
        return new UpdateAccountDAO().updateAccount(accountUpdateDataForm);
    }

    /**
     * パスワード再発行に必要な情報を登録するためのメソッド
     * @param mailAddress メールアドレス
     * @return 再発行トークン
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     * @throws NoDataExistException
     */
    public String setReissueInfo(String mailAddress)
            throws ClassNotFoundException, IOException, SQLException, NoDataExistException {
        // DAOメソッドに引き渡すためのアクションフォームを生成してメールアドレスを格納する。
        AccountSelectWhereActionForm accountSelectWhereForm = new AccountSelectWhereActionForm();
        accountSelectWhereForm.setMailAddress(mailAddress);
        // メールアドレスに紐づくレコードのリストを取得する。
        List<AccountActionForm> accountFormList = new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm);
        if(accountFormList.isEmpty()) {
            // 取得できない場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        }

        // 再発行トークンを生成する。
        String reissueToken = getToken(mailAddress);

        // プロパティ情報を取得する。
        Properties props = getProperteis();
        // 有効期間を取得する。
        long effectiveTime = Long.parseLong(props.getProperty("reissue.effective.time"));
        // 現在日時から有効期間後の日時を取得する。
        Timestamp reissueEffectiveDate = getAddedTimestamp(effectiveTime);

        // DAOメソッドに引き渡すためのアクションフォームを生成して安全なパスワードを格納する。
        AccountUpdateDataActionForm accountUpdateDataForm = new AccountUpdateDataActionForm();
        accountUpdateDataForm.setAccountId(accountFormList.get(0).getAccountId());
        accountUpdateDataForm.setReissueToken(reissueToken);
        accountUpdateDataForm.setReissueEffctiveDate(reissueEffectiveDate);
        // 再発行トークンと再発行有効期限を更新する。
        new UpdateAccountDAO().updateAccount(accountUpdateDataForm);

        // 再発行トークンを戻す。
        return reissueToken;
    }

    /**
     * パスワード再発行ステータスが有効かをチェックするためのメソッド
     * @param mailAddress メールアドレス
     * @param reissueToken 再発行トークン
     * @return アカウントID
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     * @throws NoDataExistException
     */
    public int checkReissueStatus(String mailAddress, String reissueToken)
            throws ClassNotFoundException, IOException, SQLException, NoDataExistException {
        // DAOメソッドに引き渡すためのアクションフォームを生成してメールアドレスと再発行トークンを格納する。
        AccountSelectWhereActionForm accountSelectWhereForm = new AccountSelectWhereActionForm();
        accountSelectWhereForm.setMailAddress(mailAddress);
        accountSelectWhereForm.setReissueToken(reissueToken);
        // メールアドレスと再発行トークンをもとにアカウントテーブルからアカウントIDと再発行有効期限に紐づくレコードを取得する。
        List<AccountActionForm> accountFormList = new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm);
        if(accountFormList.isEmpty()) {
            // 取得できない場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        }

        int accountId = accountFormList.get(0).getAccountId(); // アカウントID
        Timestamp reissueEffectiveDate = accountFormList.get(0).getReissueEffctiveDate(); // 再発行有効期限

        if(getCurrentTimestamp().after(reissueEffectiveDate)) {
            // 現時点が有効期限より後の場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        } else {
            // 上記以外の場合はアカウントIDを戻す。
            return accountId;
        }
    }

    /**
     * 再発行されたパスワードに更新するためのメソッド
     * @param accountId アカウントID
     * @param mailAddress メールアドレス
     * @param password パスワード
     * @return 実行結果
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws RuntimeException
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean executeReissue(int accountId, String mailAddress, String password) throws
            InvalidKeySpecException,
            NoSuchAlgorithmException,
            RuntimeException,
            ClassNotFoundException,
            IOException,
            SQLException {
        // 変更後のパスワードとメールアドレスをもとに安全なパスワードを取得する。
        String safetyPassword = new PasswordUtil().getSafetyPassword(password, mailAddress);

        // DAOメソッドに引き渡すためのアクションフォームを生成して安全なパスワードを格納する。
        AccountUpdateDataActionForm accountUpdateDataForm = new AccountUpdateDataActionForm();
        accountUpdateDataForm.setAccountId(accountId);
        accountUpdateDataForm.setPassword(safetyPassword);
        accountUpdateDataForm.setReissueTokenToNullFlag(true);
        accountUpdateDataForm.setReissueEffctiveDateToNULLFlag(true);

        // パスワードを更新して更新結果を戻す。
        return new UpdateAccountDAO().updateAccount(accountUpdateDataForm);
    }

    /**
     * トークンを生成するためのメソッド
     */
    String getToken(String mailAddress) {
        return new TokenUtil().generateToken(mailAddress);
    }

    /**
     * プロパティ情報を取得するためのメソッド
     */
    Properties getProperteis() throws IOException {
        return new PropertyUtil().getProperty("environment.properties");
    }

    /**
     * 現在日時から有効期間後の日時を取得するためのメソッド
     */
    Timestamp getAddedTimestamp(long effectiveTime) {
        return new DateUtil().getAddedTimestamp(effectiveTime);
    }

    /**
     * 現在日時(日時型)を取得するためのメソッド
     */
    Timestamp getCurrentTimestamp() {
        // 現在日時(日時型)を戻す。
        return new Timestamp(System.currentTimeMillis());
    }

}
