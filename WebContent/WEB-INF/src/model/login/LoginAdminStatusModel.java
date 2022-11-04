package model.login;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import actionform.AdminActionForm;
import dao.admin.SelectAdminDAO;
import dao.admin.sql.AdminSelectWhereActionForm;
import exception.NoDataExistException;

public class LoginAdminStatusModel {

    /**
     * 管理者IDをもとにログイン状態にするためのメソッド
     * @param adminId 管理者ID
     * @param session セッション
     * @return 管理者ID、管理者姓、ログイン済を示すフラグを格納したマップまたはNULL
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     * @throws NoDataExistException
     */
    public HttpSession stateToAdminLogined(int adminId, HttpSession session)
            throws ClassNotFoundException, IOException, SQLException, NoDataExistException {
        // DAOメソッドに引き渡すアクションフォームを生成してアカウントIDとアカウントステータス("1"(有効))を格納する。
        AdminSelectWhereActionForm adminSelectWhereForm = new AdminSelectWhereActionForm();
        adminSelectWhereForm.setAdminId(adminId);
        adminSelectWhereForm.setAdminStatus("1");
        // アカウントテーブルからアカウントIDとアカウントステータスに紐づくレコードを取得する。
        List<AdminActionForm> adminFormList = new SelectAdminDAO().selectMatchedAdmin(adminSelectWhereForm);
        if(adminFormList.isEmpty()) {
            // 取得できない場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        } else {
            // 上記以外の場合

            // 管理者IDと管理者姓を取得してログイン済を示すフラグを加えてセッションに格納してログイン状態にする。
            session.setAttribute("adminId", String.valueOf(adminId));
            session.setAttribute("adminLastName", adminFormList.get(0).getAdminLastName());
            session.setAttribute("adminLogined", "true");

            // 管理者ID、管理者姓、ログイン済を示すフラグを格納したセッションを戻す。
            return session;
        }
    }

    /**
     * ログアウト状態にするためのメソッド
     * @param session セッション
     * @return 管理者ID、管理者姓、ログイン済を示すフラグを削除したセッション
     */
    public HttpSession stateToAdminLogout(HttpSession session) {
        // セッションからアカウントID、姓、ログイン済を示すフラグを削除してログアウト状態にする。
        session.removeAttribute("adminId");
        session.removeAttribute("adminLastName");
        session.removeAttribute("adminLogined");

        // セッションからアカウントID、姓、ログイン済を示すフラグを削除したセッションを戻す。
        return session;
    }

    /**
     * ログイン済であるかを判定するためのメソッド
     * @param session セッション
     * @return 判定結果
     */
    public boolean isAdminLogined(HttpSession session) {
        // セッションからログイン状態を取得する。
        String adminLogined = (String) session.getAttribute("adminLogined");
        if(StringUtils.isEmpty(adminLogined) || !adminLogined.equals("true")) {
            // ログインしていない場合はfalseを戻す。
            return false;
        } else {
            // 上記以外の場合はtrueを戻す。
            return true;
        }
    }

    /**
     * セッションから管理者IDを取得するためのメソッド
     * @param session セッション
     * @return 管理者ID
     * @throws NoDataExistException
     */
    public int getAdminId(HttpSession session) throws NoDataExistException {
        // セッションから管理者ID(文字列型)を取得する。
        String strAdminId = (String) session.getAttribute("adminId");
        if(StringUtils.isEmpty(strAdminId)) {
            // 取得した管理者ID(文字列型)がNULLまたは空文字の場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        } else {
            // 上記以外の場合は管理者ID(文字列型)を整数型に変換して戻す。
            return Integer.parseInt(strAdminId);
        }
    }

    /**
     * セッションから管理者姓を取得するためのメソッド
     * @param session セッション
     * @return 姓
     * @throws NoDataExistException
     */
    public String getAdminLastName(HttpSession session) throws NoDataExistException {
        // セッションから管理者姓を取得する。
        String adminLastName = (String) session.getAttribute("adminLastName");
        if(StringUtils.isEmpty(adminLastName)) {
            // 取得した管理者姓がNULLまたは空文字の場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        } else {
            // 上記以外の場合は姓を戻す。
            return adminLastName;
        }
    }

}
