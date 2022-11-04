package dao.category;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import actionform.CategoryActionForm;
import dao.ConnectorDAO;
import dao.category.sql.CategorySelectWhereActionForm;

public class SelectCategoryDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * カテゴリーテーブルから指定した検索条件に一致するレコードを取得するためのメソッド
     * @param whereForm 検索条件を指定するデータを格納したアクションフォームフォーム
     * @return 検索条件に一致したカテゴリーのアクションフォームをカテゴリーIDの昇順で格納したリスト
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<CategoryActionForm> selectMatchedCategory(CategorySelectWhereActionForm whereForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルを指定するSELECT文を生成する。
            String tableSql = "SELECT * FROM categories ";

            // 検索条件を指定するためのStringBuilderを呼び出す。
            StringBuilder whereSb = new StringBuilder();
            whereSb.append("WHERE category_delete_flag='0' ");

            // カテゴリーID
            int categoryId = whereForm.getCategoryId();
            if(categoryId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND category_id=" + categoryId + " ");
            }

            // カテゴリー名
            String categoryName = whereForm.getCategoryName();
            if(!StringUtils.isEmpty(categoryName)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND category_name='" + categoryName + "' ");
            }

            // 上位カテゴリーID
            int seniorCategoryId = whereForm.getSeniorCategoryId();
            if(seniorCategoryId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND senior_category_id=" + seniorCategoryId + " ");
            } else if(whereForm.isSeniorCategoryIdNullFlag()) {
                // カテゴリーID-NULL判定フラグがtrueの場合は検索条件(AND)に追加する。
                whereSb.append("AND senior_category_id IS NULL ");
            }

            // カテゴリーステータス
            String categoryStatus = whereForm.getCategoryStatus();
            if(!StringUtils.isEmpty(categoryStatus)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND category_status='" + categoryStatus + "' ");
            }

            // StringBuilderで生成した文をString型に変換した文とカテゴリーIDの昇順を指定する文を結合してSELECT全文を生成する。
            String sql = tableSql + whereSb.toString() + "ORDER BY category_id ASC";

            // DBとの接続状態を取得する。
            connection = getConnection();
            // DBにSELECT文を送信するために変数sqlをPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // SELECT文の実行結果を変数に格納する。
            ResultSet rs = ps.executeQuery();

            // アクションフォームを格納するためのリストを生成する。
            List<CategoryActionForm> categoryFormlist = new ArrayList<>();
            // 取得したレコードの件数分、処理を繰り返す。
            while(rs.next()) {
                // レコードから各列のデータを取得してアクションフォームに格納する。
                CategoryActionForm outForm = new CategoryActionForm();
                outForm.setCategoryId(rs.getInt("category_id")); // カテゴリーID
                outForm.setCategoryName(rs.getString("category_name")); // カテゴリー名
                outForm.setSeniorCategoryId(rs.getInt("senior_category_id")); // 上位カテゴリーID
                outForm.setCategoryStatus(rs.getString("category_status")); // カテゴリーステータス
                outForm.setCategoryCreatedAt(rs.getTimestamp("category_created_at")); // カテゴリー作成日時
                outForm.setCategoryUpdatedAt(rs.getTimestamp("category_updated_at")); // カテゴリー更新日時
                // リストにアクションフォームを格納する。
                categoryFormlist.add(outForm);
            }

            // アクションフォームを格納したリストを戻す。
            return categoryFormlist;
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
     * 特定のカテゴリーIDの直系の下位カテゴリーID、下位カテゴリー名、階層レベルの配列を格納したリストを取得するためのメソッド
     * @param rootCategoryId 階層のルートにするカテゴリーID
     * @return カテゴリーID、カテゴリー名、階層レベルの配列を格納したリスト
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<String[]> selectDescendantCategory(int rootCategoryId)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルを指定するSELECT文を生成する。
            String sql
                    = "SELECT category_id, category_name, LEVEL FROM categories "
                    + "WHERE category_status='1' AND category_delete_flag='0' ";

            // 階層のルートを指定する文を格納する変数を生成する。
            String startWithClause = "";
            if(rootCategoryId == 0 ) {
                // 階層のルートにするカテゴリーIDが0の場合はルートにNULLを指定する文を変数に格納する。
                startWithClause = "START WITH senior_category_id IS NULL ";
            } else {
                // 上記以外の場合はルートに引数のカテゴリーIDを指定する文を変数に格納する。
                startWithClause = "START WITH senior_category_id=" + String.valueOf(rootCategoryId) + " ";
            }

            // 階層のルートを指定する文と親子の関連を指定する文を結合してSELECT全文を生成する。
            sql += startWithClause + "CONNECT BY prior category_id=senior_category_id";

            // DBとの接続状態を取得する。
            connection = getConnection();
            // DBにSELECT文を送信するために変数sqlをPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // SELECT文の実行結果を変数に格納する。
            ResultSet rs = ps.executeQuery();

            // カテゴリーID、カテゴリー名、階層レベルの配列を格納するためのリストを生成する。
            List<String[]> descendantCategoryList = new ArrayList<>();
            // 取得したレコードの件数分、処理を繰り返す。
            while(rs.next()) {
                // レコードからカテゴリーID、カテゴリー名、階層レベルを取得して配列に格納する。
                String[] categoryArray = {
                        String.valueOf(rs.getInt("category_id")),
                        rs.getString("category_name"),
                        String.valueOf(rs.getInt("LEVEL"))};
                // リストに配列を追加する。
                descendantCategoryList.add(categoryArray);
            }

            // カテゴリーID、カテゴリー名、階層レベルの配列を格納したリストを戻す。
            return descendantCategoryList;
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
