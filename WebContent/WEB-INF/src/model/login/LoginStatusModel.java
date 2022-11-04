package model.login;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import actionform.AccountActionForm;
import dao.account.SelectAccountDAO;
import dao.account.sql.AccountSelectWhereActionForm;
import exception.NoDataExistException;

public class LoginStatusModel {

    /**
     * アカウントIDをもとにログイン状態にするためのメソッド
     * @param accountId アカウントID
     * @param session セッション
     * @return アカウントID、姓、ログイン済を示すフラグを格納したセッション
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     * @throws NoDataExistException
     */
    public HttpSession stateToLogined(int accountId, HttpSession session)
            throws ClassNotFoundException, IOException, SQLException, NoDataExistException {
        // DAOメソッドに引き渡すアクションフォームを生成してアカウントIDとアカウントステータス("1"(有効))を格納する。
        AccountSelectWhereActionForm accountSelectWhereForm = new AccountSelectWhereActionForm();
        accountSelectWhereForm.setAccountId(accountId);
        accountSelectWhereForm.setAccountStatus("1");
        // アカウントテーブルからアカウントIDとアカウントステータスに紐づくレコードを取得する。
        List<AccountActionForm> accountFormList = new SelectAccountDAO().selectMatchedAccount(accountSelectWhereForm);
        if(accountFormList.isEmpty()) {
            // 取得できない場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        } else {
            // 上記以外の場合

            // アカウントIDと姓を取得してログイン済を示すフラグを加えてセッションに格納してログイン状態にする。
            session.setAttribute("accountId", String.valueOf(accountFormList.get(0).getAccountId()));
            session.setAttribute("lastName", accountFormList.get(0).getLastName());
            session.setAttribute("logined", "true");

            // アカウントID、姓、ログイン済を示すフラグを格納したセッションを戻す。
            return session;
        }
    }

    /**
     * ログアウト状態にするためのメソッド
     * @param session セッション
     * @return アカウントID、姓、ログイン済を示すフラグを削除したセッション
     */
    public HttpSession stateToLogout(HttpSession session) {
        // セッションからアカウントID、姓、ログイン済を示すフラグを削除してログアウト状態にする。
        session.removeAttribute("accountId");
        session.removeAttribute("lastName");
        session.removeAttribute("logined");

        // アカウントID、姓、ログイン済を示すフラグを削除したセッションを戻す。
        return session;
    }

    /**
     * ログイン済であるかを判定するためのメソッド
     * @param session セッション
     * @return 判定結果
     */
    public boolean isLogined(HttpSession session) {
        // セッションからログイン状態を取得する。
        String logined = (String) session.getAttribute("logined");
        if(StringUtils.isEmpty(logined) || !logined.equals("true")) {
            // ログインしていない場合はfalseを戻す。
            return false;
        }else {
            // 上記以外の場合はtrueを戻す。
            return true;
        }
    }

    /**
     * セッションからアカウントIDを取得するためのメソッド
     * @param session セッション
     * @return アカウントID
     * @throws NoDataExistException
     */
    public int getAccountId(HttpSession session) throws NoDataExistException {
        // セッションからアカウントID(文字列型)を取得する。
        String strAccountId = (String) session.getAttribute("accountId");
        if(StringUtils.isEmpty(strAccountId)) {
            // 取得したアカウントID(文字列型)がNULLまたは空文字の場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        } else {
            // 上記以外の場合はアカウントID(文字列型)を整数型に変換して戻す。
            return Integer.parseInt(strAccountId);
        }
    }

    /**
     * セッションから姓を取得するためのメソッド
     * @param session セッション
     * @return 姓
     * @throws NoDataExistException
     */
    public String getLastName(HttpSession session) throws NoDataExistException {
        // セッションから姓を取得する。
        String lastName = (String) session.getAttribute("lastName");
        if(StringUtils.isEmpty(lastName)) {
            // 取得した姓がNULLまたは空文字の場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        } else {
            // 上記以外の場合は姓を戻す。
            return lastName;
        }
    }

}
