package dao.favorite;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import actionform.FavoriteActionForm;
import dao.ConnectorDAO;
import dao.favorite.sql.FavoriteSelectWhereActionForm;

public class SelectFavoriteDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * お気に入りテーブルから指定した検索条件に一致するレコードを取得するためのメソッド
     * @param whereForm 検索条件を指定したデータを格納したアクションフォーム
     * @return 検索条件に一致したお気に入りのアクションフォームをお気に入りIDの昇順で格納したリスト
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<FavoriteActionForm> selectMatchedFavorite(FavoriteSelectWhereActionForm whereForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // SELECT文を生成する。
            String sql = "SELECT * FROM favorites ";

            // 検索条件の指定有無を格納する変数を生成してfalseを設定する。
            boolean isSpecified = false;

            // 検索条件を指定するためのStringBuilderを呼び出す。
            StringBuilder whereSb = new StringBuilder();
            whereSb.append("WHERE ");

            // お気に入りID
            int favoriteId = whereForm.getFavoriteId();
            if(favoriteId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("favorite_id=" + favoriteId + " AND ");
                // 変数にtrueを設定する。
                isSpecified = true;
            }

            // アカウントID
            int accountId = whereForm.getAccountId();
            if(accountId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("account_id=" + accountId + " AND ");
                // 変数にtrueを設定する。
                isSpecified = true;
            }

            // スクールID
            int schoolId = whereForm.getSchoolId();
            if(schoolId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("school_id=" + schoolId + " AND ");
                // 変数にtrueを設定する。
                isSpecified = true;
            }

            if(isSpecified) {
                // 検索条件が指定されている場合

                // 末尾の" AND "を削除する。
                whereSb.delete(whereSb.length()-5, whereSb.length());
                // StringBuilderで生成した文をString型に変換した文とお気に入りIDの昇順を指定する文を結合してSELECT全文を生成する。
                sql += whereSb.toString() + "ORDER BY favorite_id ASC";
            } else {
                // 上記以外の場合はお気に入りIDの昇順を指定する文を結合してSELECT全文を生成する。
                sql += "ORDER BY favorite_id ASC";
            }

            // DBとの接続状態を取得する。
            connection = getConnection();
            // SELECT文をPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // SELECT文の実行結果を変数に格納する。
            ResultSet rs = ps.executeQuery();

            // アクションフォームを格納するためのリストを生成する。
            List<FavoriteActionForm> favoriteFormList = new ArrayList<>();
            // 取得したレコードの件数分、処理を繰り返す。
            while(rs.next()) {
                // レコードから各列のデータを取得してアクションフォームに格納する。
                FavoriteActionForm outForm = new FavoriteActionForm();
                outForm.setFavoriteId(rs.getInt("favorite_id")); // お気に入りID
                outForm.setAccountId(rs.getInt("account_id")); // アカウントID
                outForm.setSchoolId(rs.getInt("school_id")); // スクールID
                outForm.setFavoriteAddedAt(rs.getTimestamp("favorite_added_at")); // お気に入り追加日時
                // リストにアクションフォームを追加する。
                favoriteFormList.add(outForm);
            }

            // アクションフォームを格納したリストを戻す。
            return favoriteFormList;
        } finally {
            // DBに接続されている場合
            if(connection != null) {
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
