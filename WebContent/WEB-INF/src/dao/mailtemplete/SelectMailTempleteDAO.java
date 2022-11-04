package dao.mailtemplete;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import actionform.MailTempleteActionForm;
import dao.ConnectorDAO;
import dao.mailtemplete.sql.MailTempleteSelectWhereActionForm;

public class SelectMailTempleteDAO {
    Connection connection = null; // DB接続用の変数

    /**
     * メールテンプレートテーブルから指定した検索条件に一致するレコードを取得するためのメソッド
     * @param whereForm 検索条件を指定したデータを格納したアクションフォーム
     * @return 検索条件に一致したメールテンプレートのアクションフォームをメールテンプレートIDの昇順で格納したリスト
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public List<MailTempleteActionForm> selectMatchedMailTemplete(MailTempleteSelectWhereActionForm whereForm)
            throws ClassNotFoundException, IOException, SQLException {
        try {
            // テーブルを指定するSELECT文を生成する。
            String tableSql = "SELECT * FROM mail_templetes ";

            // 検索条件を指定するためのStringBuilderを呼び出す。
            StringBuilder whereSb = new StringBuilder();
            whereSb.append("WHERE mail_templete_delete_flag='0' ");

            // メールテンプレートID
            int mailTempleteId = whereForm.getMailTempleteId();
            if(mailTempleteId != 0) {
                // 0以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND mail_templete_id=" + mailTempleteId + " ");
            }

            // メールテンプレート名
            String mailTempleteName = whereForm.getMailTempleteName();
            if(!StringUtils.isEmpty(mailTempleteName)) {
                // NULLまたは空文字以外の場合は検索条件(AND)に追加する。
                whereSb.append("AND mail_templete_name='" + mailTempleteName + "' ");
            }

            // StringBuilderで生成した文をString型に変換した文とメールテンプレートIDの昇順を指定する文を結合してSELECT全文を生成する。
            String sql = tableSql + whereSb.toString() + "ORDER BY mail_templete_id ASC";

            // DBとの接続状態を取得する。
            connection = getConnection();
            // DBにSELECT文を送信するために変数sqlをPreparedStatementインスタンスに格納する。
            PreparedStatement ps = connection.prepareStatement(sql);
            // SELECT文の実行結果を変数に格納する。
            ResultSet rs = ps.executeQuery();

            // アクションフォームを格納するためのリストを生成する。
            List<MailTempleteActionForm> mailTempleteFormList = new ArrayList<>();
            // 取得したレコードの件数分、処理を繰り返す。
            while(rs.next()) {
                // レコードから各列のデータを取得してアクションフォームに格納する。
                MailTempleteActionForm outForm = new MailTempleteActionForm();
                outForm.setMailTempleteId(rs.getInt("mail_templete_id")); // メールテンプレートID
                outForm.setMailTempleteName(rs.getString("mail_templete_name")); // メールテンプレート名
                outForm.setMailTempleteSubject(rs.getString("mail_templete_subject")); // 件名
                outForm.setMailTempleteHeader(rs.getString("mail_templete_header")); // ヘッダー文
                outForm.setMailTempleteFooter(rs.getString("mail_templete_footer")); // フッター文
                outForm.setMailTempleteCreatedAt(rs.getTimestamp("mail_templete_created_at")); // メールテンプレート作成日時
                outForm.setMailTempleteUpdatedAt(rs.getTimestamp("mail_templete_updated_at")); // メールテンプレート更新日時
                // リストのアクションフォームを追加する。
                mailTempleteFormList.add(outForm);
            }

            // アクションフォームを格納したリストを戻す。
            return mailTempleteFormList;
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
