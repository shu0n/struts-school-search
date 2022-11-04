package dao.category;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import actionform.CategoryExtendActionForm;
import dao.ConnectorDAO;
import dao.category.sql.CategorySelectWhereActionForm;

public class SelectCategoryJoinDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * カテゴリーテーブルにカテゴーリテーブル(上位カテゴリー)を外部結合して検索条件に一致するレコードを取得するためのメソッド
     * @param whereForm 検索条件を指定するデータを格納したアクションフォームフォーム
     * @return 検索条件に一致したカテゴリーのアクションフォームをカテゴリーIDの昇順で格納したリスト
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<CategoryExtendActionForm> selectMatchedCategory(CategorySelectWhereActionForm whereForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // 外部結合するためのSELECT文を生成する。
            String joinSql
                    = "SELECT "
                    + "junior.category_id, junior.category_name AS junior_category_name, "
                    + "junior.senior_category_id, senior.category_name AS senior_category_name, "
                    + "junior.category_status, junior.category_created_at, junior.category_updated_at "
                    + "FROM (categories junior LEFT OUTER JOIN categories senior "
                    + "ON junior.senior_category_id=senior.category_id) ";

            // 検索条件を指定するためのStringBuilderを呼び出す。
            StringBuilder whereSb = new StringBuilder();
            whereSb.append("WHERE junior.category_delete_flag='0' ");

            // カテゴリーID
            int categoryId = whereForm.getCategoryId();
            if(categoryId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND junior.category_id=" + categoryId + " ");
            }

            // カテゴリー名
            String categoryName = whereForm.getCategoryName();
            if(!StringUtils.isEmpty(categoryName)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND junior.category_name='" + categoryName + "' ");
            }

            // 上位カテゴリーID
            int seniorCategoryId = whereForm.getSeniorCategoryId();
            if(seniorCategoryId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND junior.senior_category_id=" + seniorCategoryId + " ");
            } else if(whereForm.isSeniorCategoryIdNullFlag()) {
                // カテゴリーID-NULL判定フラグがtrueの場合は検索条件(AND)に追加する。
                whereSb.append("AND junior.senior_category_id IS NULL ");
            }

            // カテゴリーステータス
            String categoryStatus = whereForm.getCategoryStatus();
            if(!StringUtils.isEmpty(categoryStatus)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND junior.category_status='" + categoryStatus + "' ");
            }

            // StringBuilderで生成した文をString型に変換した文とカテゴリーIDの昇順を指定する文を結合してSELECT全文を生成する。
            String sql = joinSql + whereSb.toString() + "ORDER BY category_id ASC";

            // DBとの接続状態を取得する。
            connection = getConnection();
            // DBにSELECT文を送信するために変数sqlをPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // SELECT文の実行結果を変数に格納する。
            ResultSet rs = ps.executeQuery();

            // アクションフォームを格納するためのリストを生成する。
            List<CategoryExtendActionForm> categoryFormlist = new ArrayList<>();
            // 取得したレコードの件数分、処理を繰り返す。
            while(rs.next()) {
                // レコードから各列のデータを取得してアクションフォームに格納する。
                CategoryExtendActionForm outForm = new CategoryExtendActionForm();
                outForm.setCategoryId(rs.getInt("category_id")); // カテゴリーID
                outForm.setCategoryName(rs.getString("junior_category_name")); // カテゴリー名
                outForm.setSeniorCategoryId(rs.getInt("senior_category_id")); // 上位カテゴリーID
                outForm.setSeniorCategoryName(rs.getString("senior_category_name")); // 上位カテゴリー名
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
     * DBとの接続状態を取得するためのメソッド
     */
    Connection getConnection() throws ClassNotFoundException, IOException, SQLException {
        // DBとの接続状態を戻す。
        return new ConnectorDAO().connectDatabase();
    }

}
