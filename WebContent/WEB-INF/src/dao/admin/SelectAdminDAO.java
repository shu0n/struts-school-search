package dao.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import actionform.AdminActionForm;
import dao.ConnectorDAO;
import dao.admin.sql.AdminSelectWhereActionForm;

public class SelectAdminDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * 管理者テーブルから指定した検索条件に一致するレコードを取得するためのメソッド
     * @param whereForm 検索条件を指定するデータを格納したアクションフォーム
     * @return 検索条件に一致した管理者のアクションフォームを管理者IDの昇順で格納したリスト
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<AdminActionForm> selectMatchedAdmin(AdminSelectWhereActionForm whereForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルを指定するSELECT文を生成する。
            String tableSql = "SELECT * FROM admins ";

            // 検索条件を指定するためのStringBuilderを呼び出す。
            StringBuilder whereSb = new StringBuilder();
            whereSb.append("WHERE admin_delete_flag='0' ");

            // 管理者ID
            int adminId = whereForm.getAdminId();
            if(adminId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND admin_id=" + adminId + " ");
            }

            // 管理部門ID
            int adminDepartmentId = whereForm.getAdminDepartmentId();
            if(adminDepartmentId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND admin_department_id=" + adminDepartmentId + " ");
            }

            // 管理者姓
            String adminLastName = whereForm.getAdminLastName();
            if(!StringUtils.isEmpty(adminLastName)) {
                // NULLまたは空文字以外の場合は検索条件に追加する。
                whereSb.append("AND admin_last_name='" + adminLastName + "' ");
            }

            // 管理者名
            String adminFirstName = whereForm.getAdminFirstName();
            if(!StringUtils.isEmpty(adminFirstName)) {
                // NULLまたは空文字以外の場合は検索条件に追加する。
                whereSb.append("AND admin_first_name='" + adminFirstName + "' ");
            }

            // 管理者姓(フリガナ)
            String adminLastNameKana = whereForm.getAdminLastNameKana();
            if(!StringUtils.isEmpty(adminLastName)) {
                // NULLまたは空文字以外の場合は検索条件に追加する。
                whereSb.append("AND admin_last_name_kana='" + adminLastNameKana + "' ");
            }

            // 管理者名(フリガナ)
            String adminFirstNameKana = whereForm.getAdminFirstNameKana();
            if(!StringUtils.isEmpty(adminFirstNameKana)) {
                // NULLまたは空文字以外の場合は検索条件に追加する。
                whereSb.append("AND admin_first_name_kana='" + adminFirstNameKana + "' ");
            }

            // 管理者メールアドレス
            String adminMailAddress = whereForm.getAdminMailAddress();
            if(!StringUtils.isEmpty(adminMailAddress)) {
                // NULLまたは空文字以外の場合は検索条件に追加する。
                whereSb.append("AND admin_mail_address='" + adminMailAddress + "' ");
            }

            // 管理者パスワード
            String adminPassword = whereForm.getAdminPassword();
            if(!StringUtils.isEmpty(adminPassword)) {
                // NULLまたは空文字以外の場合は検索条件に追加する。
                whereSb.append("AND admin_password='" + adminPassword + "' ");
            }

            // 管理者権限
            String adminAuthority = whereForm.getAdminAuthority();
            if(!StringUtils.isEmpty(adminAuthority)) {
                // NULLまたは空文字以外の場合は検索条件に追加する。
                whereSb.append("AND admin_authority='" + adminAuthority + "' ");
            }

            // 管理者ステータス
            String adminStatus = whereForm.getAdminStatus();
            if(!StringUtils.isEmpty(adminStatus)) {
                // NULLまたは空文字以外の場合は検索条件に追加する。
                whereSb.append("AND admin_status='" + adminStatus + "' ");
            }

            // StringBuilderで生成した文をString型に変換した文と管理者IDの昇順を指定する文を結合してSELECT全文を生成する。
            String sql = tableSql + whereSb.toString() + "ORDER BY admin_id ASC";

            // DBとの接続状態を取得する。
            connection = getConnection();
            // SELECT文をPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // SELECT文の実行結果を変数に格納する。
            ResultSet rs = ps.executeQuery();

            // アクションフォームを格納するためのリストを生成する。
            List<AdminActionForm> admwhereFormList = new ArrayList<>();
            // 取得したレコードの件数分、処理を繰り返す。
            while(rs.next()) {
                // レコードから各列のデータを取得してアクションフォームに格納する。
                AdminActionForm outForm = new AdminActionForm();
                outForm.setAdminId(rs.getInt("admin_id")); // 管理者ID
                outForm.setAdminDepartmentId(rs.getInt("admin_department_id")); // 管理部門ID
                outForm.setAdminLastName(rs.getString("admin_last_name")); // 管理者姓
                outForm.setAdminFirstName(rs.getString("admin_first_name")); // 管理者名
                outForm.setAdminLastNameKana(rs.getString("admin_last_name_kana")); // 管理者姓(フリガナ)
                outForm.setAdminFirstNameKana(rs.getString("admin_first_name_kana")); // 管理者名(フリガナ)
                outForm.setAdminMailAddress(rs.getString("admin_mail_address")); // 管理者メールアドレス
                outForm.setAdminAuthority(rs.getString("admin_authority")); // 管理者権限
                outForm.setAdminStatus(rs.getString("admin_status")); // 管理者ステータス
                outForm.setAdminCreatedAt(rs.getTimestamp("admin_created_at")); // 管理者作成日時
                outForm.setAdminUpdatedAt(rs.getTimestamp("admin_updated_at")); // 管理者更新日時
                // リストにアクションフォームを追加する。
                admwhereFormList.add(outForm);
            }

            // アクションフォームを格納しt戻す。
            return admwhereFormList;
        } finally {
            if(connection != null) {
                // DBに接続されている場合
                try {
                    // DB接続を切断する。
                    connection.close();
                } catch(SQLException e) {
                    // 例外を投げる。
                    throw e;
                }
            }
        }
    }

    /**
     * DBとの接続状態を取得するためのメソッド
     */
    Connection getConnection() throws ClassNotFoundException, IOException, SQLException {
        // DBとの接続状態を戻す。
        return new ConnectorDAO().connectDatabase();
    }

}
