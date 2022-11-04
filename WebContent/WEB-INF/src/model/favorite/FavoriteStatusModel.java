package model.favorite;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import actionform.FavoriteActionForm;
import dao.favorite.SelectFavoriteDAO;
import dao.favorite.sql.FavoriteSelectWhereActionForm;

public class FavoriteStatusModel {

    /**
     * 特定のスクールをお気に入りに追加しているかを判定するためのメソッド
     * @param accountId アカウントID
     * @param schoolId スクールID
     * @return 判定結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean isFavoriteAdded(int accountId, int schoolId)
            throws ClassNotFoundException, IOException, SQLException {
        // DAOメソッドに引き渡すアクションフォームを生成してアカウントIDとスクールIDを格納する。
        FavoriteSelectWhereActionForm favoriteSelectForm = new FavoriteSelectWhereActionForm();
        favoriteSelectForm.setAccountId(accountId);
        favoriteSelectForm.setSchoolId(schoolId);
        // お気に入りテーブルからアカウントIDとスクールIDの組み合わせに紐づくレコードを取得する。
        List<FavoriteActionForm> favoriteFormList = new SelectFavoriteDAO().selectMatchedFavorite(favoriteSelectForm);
        if(favoriteFormList.isEmpty()) {
            // 取得できない場合はfalseを戻す。
            return false;
        } else {
            // 上記以外の場合はtrueを戻す。
            return true;
        }
    }

}
