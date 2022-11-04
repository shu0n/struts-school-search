package dao.favorite;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.ConnectorDAO;
import dao.favorite.sql.FavoriteInsertDataActionForm;

public class InsertFavoriteDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * お気に入りテーブルへのINSERT文を実行するためのメソッド
     * @param insertDataForm 登録するデータを格納したアクションフォーム
     * @return お気に入りID
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public int insertFavorite(FavoriteInsertDataActionForm insertDataForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルを指定するためのINSERT文を生成する。
            String tableSql = "INSERT INTO favorites (";

            // 列名を追加するためのStringBuilderを呼び出す。
            StringBuilder columnSb = new StringBuilder();
            // 各列名を一度リスト変数に格納して繰り返し処理で追加する。
            List<String> columnList = new ArrayList<>();
            columnList.addAll(Arrays.asList(
                    "favorite_id", // お気に入りID
                    "account_id", // アカウントID
                    "school_id" // スクールID
                    ));
            for(String value : columnList) {
                columnSb.append(", " + value);
            }
            columnSb.delete(0,2);

            // データを追加するためのStringBuilderを呼び出す。
            StringBuilder dataSb = new StringBuilder();
            // 各データを一度リスト変数に格納して繰り返し処理で追加する。
            List<Integer> dataList = new ArrayList<>();
            // アカウントIDとスクールIDをもとにお気に入りID(主キー)を生成する。
            int favoriteId = Integer.parseInt(
                    String.valueOf(insertDataForm.getAccountId())
                    + String.valueOf(insertDataForm.getSchoolId()));
            dataList.addAll(Arrays.asList(
                    favoriteId, // お気に入りID
                    insertDataForm.getAccountId(), // アカウントID
                    insertDataForm.getSchoolId() // スクールID
                    ));
            for(int value : dataList) {
                dataSb.append(", " + value);
            }
            dataSb.delete(0,2);

            columnSb.append(") VALUES(");
            dataSb.append(")");

            // StringBuilderで生成した文をString型に変換して結合してINSERT全文を生成する。
            String sql =  tableSql + columnSb.toString() + dataSb.toString();

            // DBとの接続状態を取得する。
            connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            // INSERT文の実行結果を変数に格納する。
            int rs = ps.executeUpdate();

            if(rs == 0) {
                // 作成されたレコードが0件の場合は0を戻す。
                return 0;
            } else {
                // 上記以外の場合はお気に入りIDを戻す。
                return favoriteId;
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
