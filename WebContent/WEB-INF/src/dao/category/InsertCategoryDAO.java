package dao.category;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.ConnectorDAO;
import dao.category.sql.CategoryInsertDataActionForm;

public class InsertCategoryDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * カテゴリーテーブルにレコードを作成するためのメソッド
     * @param insertDataForm 登録するデータを格納したアクションフォーム
     * @return 発番されたカテゴリーID
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public int insertCategory(CategoryInsertDataActionForm insertDataForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルを指定するためのINSERT文を生成する。
            String tableSql = "INSERT INTO categories (";

            // 列名を追加するためのStringBuilderを呼び出す。
            StringBuilder columnSb = new StringBuilder();
            // データを追加するためのStringBuilderを呼び出す。
            StringBuilder dataSb = new StringBuilder();

            // カテゴリー名
            String categoryName = insertDataForm.getCategoryName();
            columnSb.append("category_name");
            dataSb.append("'" + categoryName + "'");

            // NULL可またはデフォルト値がある項目は入力値によって列名とデータを追加する。
            // カテゴリーステータス
            String categoryStatus = insertDataForm.getCategoryStatus();
            if(categoryStatus != null) {
                // NULL以外の場合は追加する。
                columnSb.append(", category_status");
                dataSb.append(", '" + categoryStatus + "'");
            }

            // 上位カテゴリーID
            int seniorCategoryId = insertDataForm.getSeniorCategoryId();
            if(seniorCategoryId != 0) {
                // 0以外の場合は追加する。
                columnSb.append(", senior_category_id");
                dataSb.append(", " + seniorCategoryId);
            }

            columnSb.append(") VALUES(");
            dataSb.append(")");

            // StringBuilderで生成した文をString型に変換して結合してINSERT全文を生成する。
            String sql =  tableSql + columnSb.toString() + dataSb.toString();

            // 主キーの列名を変数に格納する。
            String[] keyColNames = {"category_id"};
            // DBとの接続状態を取得する。
            connection = getConnection();
            // DBにINSERT文を送信するために変数sqlと主キーの列名を格納した変数をPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql, keyColNames);
            // INSERT文を実行する。
            ps.executeUpdate();
            // 実行結果から自動採番されるカテゴリーIDを取得して変数に格納する。
            ResultSet rs = ps.getGeneratedKeys();

            // カテゴリーIDを格納するための変数を生成する。
            int categoryId = 0;
            while(rs.next()) {
                // 取得したカテゴリーIDを変数に格納する。
                categoryId = rs.getInt(1);
                break;
            }

            // カテゴリーIDを戻す。
            return categoryId;
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
