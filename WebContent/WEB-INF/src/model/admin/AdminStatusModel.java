package model.admin;

import java.io.IOException;
import java.sql.SQLException;

import dao.admin.SelectAdminDAO;
import dao.admin.sql. AdminSelectWhereActionForm;

public class AdminStatusModel {

    /**
     * 特定の管理者IDが"有効"のステータスで管理者テーブルに登録されているかを判定するためのメソッド
     * @param adminId 管理者ID
     * @return 判定結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean isAdminEnable(int adminId) throws ClassNotFoundException, IOException, SQLException {
        // DAOメソッドに引き渡すアクションフォームを生成して管理者IDを格納する。
        AdminSelectWhereActionForm adminSelectWhereForm = new AdminSelectWhereActionForm();
        adminSelectWhereForm.setAdminId(adminId);
        adminSelectWhereForm.setAdminStatus("1");
        if(new SelectAdminDAO().selectMatchedAdmin(adminSelectWhereForm).isEmpty()) {
            // 管理者テーブルから条件に一致するレコードが取得できない場合はfalseを戻す。
            return false;
        } else {
            // 上記以外の場合はtrueを戻す。
            return true;
        }
    }

}
