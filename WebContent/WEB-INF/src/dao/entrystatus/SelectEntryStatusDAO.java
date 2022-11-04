package dao.entrystatus;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import actionform.EntryStatusActionForm;
import dao.ConnectorDAO;
import dao.entrystatus.sql.EntryStatusSelectWhereActionForm;

public class SelectEntryStatusDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * 申込ステータステーブルから指定した検索条件に一致するレコードを取得するためのメソッド
     * @param whereForm 検索条件を指定したデータを格納したアクションフォーム
     * @return 検索条件に一致した申込ステータスのアクションフォームを申込ステータスIDの昇順で格納したリスト
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<EntryStatusActionForm> selectMatchedEntryStatus(EntryStatusSelectWhereActionForm whereForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // SELECT文を生成する。
            String sql = "SELECT * FROM entry_statuses ";

            // 検索条件の指定有無を格納する変数を生成してfalseを設定する。
            boolean isSpecified = false;

            // 検索条件を指定するためのStringBuilderを呼び出す。
            StringBuilder whereSb = new StringBuilder();
            whereSb.append("WHERE ");

            // 申込ステータスID
            int entryStatusId = whereForm.getEntryStatusId();
            if(entryStatusId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("entry_status_id=" + entryStatusId + " AND ");
                // 変数にtrueを設定する。
                isSpecified = true;
            }

            // 申込ステータス名
            String entryStatusName = whereForm.getEntryStatusName();
            if(!StringUtils.isEmpty(entryStatusName)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("entry_status_name='" + entryStatusName + "' AND ");
                // 変数にtrueを設定する。
                isSpecified = true;
            }

            if(isSpecified) {
                // 検索条件が指定されている場合

                // 末尾の" AND "を削除する。
                whereSb.delete(whereSb.length()-5, whereSb.length());
                // StringBuilderで生成した文をString型に変換した文と申込ステータスIDの昇順を指定する文をSELECT文に結合する。
                sql += whereSb.toString() + "ORDER BY entry_status_id ASC";
            } else {
                // 上記以外の場合は昇順を指定する文を結合する。
                sql += "ORDER BY entry_status_id";
            }

            // DBとの接続状態を取得する。
            connection = getConnection();
            // DBにSELECT文を送信するために変数sqlをPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // SELECT文の実行結果を変数に格納する。
            ResultSet rs = ps.executeQuery();

            // アクションフォームを格納するためのリストを生成する。
            List<EntryStatusActionForm> entryStatusFormList = new ArrayList<>();
            // 取得したレコードの件数分、処理を繰り返す。
            while(rs.next()) {
                // レコードから各列のデータを取得してアクションフォームに格納する。
                EntryStatusActionForm outForm = new EntryStatusActionForm();
                outForm.setEntryStatusId(rs.getInt("entry_status_id")); // 申込ステータスID
                outForm.setEntryStatusName(rs.getString("entry_status_name")); // 申込ステータス名
                entryStatusFormList.add(outForm);
            }

            // アクションフォームを格納したリストを戻す。
            return entryStatusFormList;
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
