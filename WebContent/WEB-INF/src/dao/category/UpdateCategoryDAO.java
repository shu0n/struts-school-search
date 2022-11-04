package dao.category;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import dao.ConnectorDAO;
import dao.category.sql.CategoryUpdateDataActionForm;
import exception.ReferredByCategoryException;
import exception.ReferredBySchoolException;

public class UpdateCategoryDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * 特定のカテゴリーIDに紐づくカテゴリーテーブルのレコードを更新するためのメソッド
     * @param updateDataForm 更新するデータを格納したアクションフォーム
     * @return 実行結果
     * @throws ReferredBySchoolException
     * @throws ReferredByCategoryException
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean updateCategory(CategoryUpdateDataActionForm updateDataForm) throws
            ReferredBySchoolException,
            ReferredByCategoryException,
            ClassNotFoundException,
            IOException,
            SQLException {
        try {
            // テーブルを指定するためのUPDATE文を生成する。
            String tableSql = "UPDATE categories SET ";

            // 更新する列名とデータを指定するためのStringBuilderを呼び出す。
            StringBuilder dataSb = new StringBuilder();

            // カテゴリー名
            String categoryName = updateDataForm.getCategoryName();
            if(categoryName != null) {
                // NULL以外の場合は追加する。
                dataSb.append(", category_name='" + categoryName +"'");
            }

            // 上位カテゴリーID
            int seniorCategoryId = updateDataForm.getSeniorCategoryId();
            if(seniorCategoryId != 0) {
                // 0以外の場合は追加する。
                dataSb.append(", senior_category_id=" + seniorCategoryId);
            } else if(updateDataForm.isSeniorCategoryIdToNullFlag()) {
                // 上位カテゴリーID-NULL設定フラグがtrueの場合は値にNULLを設定する。
                dataSb.append(", senior_category_id=null");
            }

            // カテゴリーステータス
            String categoryStatus = updateDataForm.getCategoryStatus();
            if(categoryStatus != null) {
                // カテゴリーステータスがNULL以外の場合
                if("0".equals(updateDataForm.getCategoryStatus())) {
                    // カテゴリーステータスが"0"(無効)の場合
                    if(isReferredBySchool(updateDataForm.getCategoryId())) {
                        // 当該カテゴリーがスクールから参照されている場合はスクール参照例外を投げる。
                        throw new ReferredBySchoolException("スクールのカテゴリーに登録されています。");
                    } else if(isReferredByJuniorCategory(updateDataForm.getCategoryId())) {
                        // 当該カテゴリーが他のカテゴリーから参照されている場合はカテゴリー参照例外を投げる。
                        throw new ReferredByCategoryException("上位カテゴリーに登録されています。");
                    }
                }

                // スクールまたは他のカテゴリーから参照されていない場合は追加する。
                dataSb.append(", category_status='" + categoryStatus + "'");
            }

            // 先頭の", "を削除する。
            dataSb.delete(0,2);

            // 現在日時(文字列型)を取得する。
            String now = getStringCurrentTimestamp();
            // カテゴリー更新日時
            dataSb.append(
                    ", category_updated_at=TO_TIMESTAMP('"
                    + now
                    + "', 'YYYY-MM-DD HH24:MI:SS.FF3') ");

            // 更新するレコードを指定するためのWHERE文を生成する。
            String whereSql = "WHERE category_id=" + updateDataForm.getCategoryId();

            // StringBuilderで生成した文をString型に変換して結合してUPDATE全文を生成する。
            String sql = tableSql + dataSb.toString() + whereSql;

            // DBとの接続状態を取得する。
            connection = getConnection();
            // DBにUPDATE文を送信するために変数sqlをPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // UPDATE文の実行結果を変数に格納する。
            int rs = ps.executeUpdate();

            if(rs == 0) {
                // 更新されたレコードが0件の場合はfalseを戻す。
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
     * 指定したカテゴリーIDがスクールから参照されているかを判定するためのメソッド
     * @param categoryId アカウントID
     * @return 判定結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    private boolean isReferredBySchool(int categoryId)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // SELECT文を生成する。
            String sql
                    = "SELECT school_id FROM schools "
                    + "WHERE school_delete_flag='0' "
                    + "AND school_category_id=" + categoryId;

            // DBとの接続状態を取得する。
            connection = new ConnectorDAO().connectDatabase();
            // SELECT文をPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // SELECT文の実行結果を変数に格納する。
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                // レコードが存在する場合はtrueを戻す。
                return true;
            }

            // レコードが存在しない場合はfalseを戻す。
            return false;
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
     * 指定したカテゴリーIDが他のカテゴリーから参照されているかを判定するためのメソッド
     * @param categoryId カテゴリーID
     * @return 判定結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    private boolean isReferredByJuniorCategory(int categoryId)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // SELECT文を生成する。
            String sql
                    = "SELECT category_id FROM categories "
                    + "WHERE category_delete_flag='0' "
                    + "AND senior_category_id=" + categoryId;

            // DBとの接続状態を取得する。
            connection = new ConnectorDAO().connectDatabase();
            // DBにSELECT文を送信するために変数sqlをPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // SELECT文の実行結果を変数に格納する。
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                // レコードが存在する場合はtrueを戻す。
                return true;
            }

            // レコードが存在しない場合はfalseを戻す。
            return false;
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
     * 現在日時(文字列型)を取得するためのメソッド
     */
    String getStringCurrentTimestamp() {
        // 現在日時(文字列型)を戻す。
        return new Timestamp(System.currentTimeMillis()).toString();
    }

    /**
     * DBとの接続状態を取得するためのメソッド
     */
    Connection getConnection() throws ClassNotFoundException, IOException, SQLException {
        // DBとの接続状態を戻す。
        return new ConnectorDAO().connectDatabase();
    }

}
