package dao.contactstatus;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import actionform.ContactStatusActionForm;
import dao.ConnectorDAO;
import dao.contactstatus.sql.ContactStatusSelectWhereActionForm;

public class SelectContactStatusDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * お問合せステータステーブルから指定した検索条件に一致するレコードを取得するためのメソッド
     * @param whereForm 検索条件を指定するデータを格納したアクションフォームフォーム
     * @return 検索条件に一致したお問合せステータスのアクションフォームをお問合せステータスIDの昇順で格納したリスト
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<ContactStatusActionForm> selectMatchedContactStatus(ContactStatusSelectWhereActionForm whereForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // レコードを取得するためのSELECT文を変数に格納する。
            String sql = "SELECT * FROM contact_statuses ";

            // 検索条件の指定有無を格納する変数を生成してfalseを設定する。
            boolean isSpecified = false;

            // 検索条件を指定するためのStringBuilderを呼び出す。
            StringBuilder whereSb = new StringBuilder();
            whereSb.append("WHERE ");

            // お問合せステータスID
            int contactStatusId = whereForm.getContactStatusId();
            if(contactStatusId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("contact_status_id=" + contactStatusId + " AND ");
                // 変数にtrueを設定する。
                isSpecified = true;
            }

            // お問合せステータス名
            String contactStatusName = whereForm.getContactStatusName();
            if(!StringUtils.isEmpty(contactStatusName)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("contact_status_name='" + contactStatusName + "' AND ");
                // 変数にtrueを設定する。
                isSpecified = true;
            }

            if(isSpecified) {
                // 検索条件が指定されている場合

                // 末尾の" AND "を削除する。
                whereSb.delete(whereSb.length()-5, whereSb.length());
                // StringBuilderで生成した文をString型に変換した文と昇順を指定する文をSELECT文に結合する。
                sql += whereSb.toString() + "ORDER BY contact_status_id ASC";
            } else {
                // 上記以外の場合は昇順を指定する文を結合する。
                sql += "ORDER BY contact_status_id ASC";
            }

            // DBとの接続状態を取得する。
            connection = getConnection();
            // DBにSELECT文を送信するために変数sqlをPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // SELECT文の実行結果を変数に格納する。
            ResultSet rs = ps.executeQuery();

            // アクションフォームを格納するためのリストを生成する。
            List<ContactStatusActionForm> contactStatusForList = new ArrayList<>();
            // 取得したレコードの件数分、処理を繰り返す。
            while(rs.next()) {
                // レコードから各列のデータを取得してアクションフォームに格納する。
                ContactStatusActionForm outForm = new ContactStatusActionForm();
                outForm.setContactStatusId(rs.getInt("contact_status_id")); // お問合せステータスID
                outForm.setContactStatusName(rs.getString("contact_status_name")); // お問合せステータス名
                // リストにアクションフォームを追加する。
                contactStatusForList.add(outForm);
            }

            // アクションフォームを格納したリストを戻す。
            return contactStatusForList;
        } finally {
            if(connection != null) {
                // DBに接続されている場合
                try {
                    // DB接続を切断する。
                    connection.close();
                } catch(SQLException e) {
                    // SQL例外を投げる。
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
