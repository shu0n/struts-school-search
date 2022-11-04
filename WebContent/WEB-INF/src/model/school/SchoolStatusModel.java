package model.school;

import java.io.IOException;
import java.sql.SQLException;

import dao.school.SelectSchoolJoinDAO;
import dao.school.sql.SchoolSelectJoinWhereActionForm;

public class SchoolStatusModel {

    /**
     * 特定のスクールが公開可であるかを判定するためのメソッド
     * @param schoolId スクールID
     * @return 判定結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean isReleased(int schoolId) throws ClassNotFoundException, IOException, SQLException {
        // DAOメソッドに引き渡すアクションフォームを生成してスクールIDを格納する。
        SchoolSelectJoinWhereActionForm schoolSelectJoinWhereForm = new SchoolSelectJoinWhereActionForm();
        schoolSelectJoinWhereForm.setSchoolId(schoolId);
        schoolSelectJoinWhereForm.setSchoolReleasePropriety("1");
        if(new SelectSchoolJoinDAO().selectMatchedSchool(schoolSelectJoinWhereForm).isEmpty()) {
            // スクールテーブルから条件に一致するレコードが取得できない場合はfalseを戻す。
            return false;
        } else {
            // 上記以外の場合はtrueを戻す。
            return true;
        }
    }

    /**
     * 特定のスクールが申込可であるかを判定するためのメソッド
     * @param schoolId スクールID
     * @return 判定結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean isEntryEnable(int schoolId) throws ClassNotFoundException, IOException, SQLException {
        // DAOメソッドに引き渡すアクションフォームを生成してスクールID、スクール申込可否を格納する。
        SchoolSelectJoinWhereActionForm schoolSelectJoinWhereForm = new SchoolSelectJoinWhereActionForm();
        schoolSelectJoinWhereForm.setSchoolId(schoolId);
        schoolSelectJoinWhereForm.setSchoolEntryPropriety("1");
        if(new SelectSchoolJoinDAO().selectMatchedSchool(schoolSelectJoinWhereForm).isEmpty()) {
            // スクールテーブルから条件に一致するレコードが取得できない場合はfalseを戻す。
            return false;
        } else {
            // 上記以外の場合はtrueを戻す。
            return true;
        }
    }

}
