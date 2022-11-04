package dao.favorite;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.ConnectorDAO;

public class DeleteFavoriteDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * お気に入りテーブルから特定のアカウントIDとスクールIDの組み合わせに紐づくレコードを削除するためのメソッド
     * @param favoriteId お気に入りID
     * @return 実行結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean deleteFavorite(int favoriteId)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // DELETE文を生成する。
            String sql = "DELETE FROM favorites WHERE favorite_id=" + favoriteId;

            // DBとの接続状態を取得する。
            connection = getConnection();
            // DBにDELETE文を送信するために変数sqlをPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // DELETE文の実行結果を変数に格納する。
            int rs = ps.executeUpdate();

            if(rs == 0) {
                // 削除されたレコードが0件の場合はfalseを戻す。
                return false;
            } else {
                // 上記以外の場合はtrueを戻す。
                return true;
            }
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
