package dao.contact.repack;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import actionform.ContactExtendActionForm;
import dao.contact.SelectContactJoinDAO;
import dao.contact.sql.ContactSelectJoinWhereActionForm;
import exception.NoDataExistException;

public class GetContactDataRepack {

    /**
     * お問合せIDに紐づくお問合せの情報を取得するためのメソッド
     * @param whereForm 検索条件を指定するデータを格納したアクションフォーム
     * @param inForm お問合せのデータを格納するアクションフォーム
     * @return 検索条件に一致したお問合せのデータを格納したアクションフォーム
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoDataExistException
     */
    public ContactExtendActionForm getContactData(
            ContactSelectJoinWhereActionForm whereForm,
            ContactExtendActionForm inForm)
            throws ClassNotFoundException, IOException, SQLException, NoDataExistException {
        // お問合せテーブルから検索条件に一致するレコードを取得する。
        List<ContactExtendActionForm> contactExtendFormList
                = new SelectContactJoinDAO().selectMatchedContact(whereForm);
        if(contactExtendFormList.isEmpty()) {
            // 取得結果が0件の場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        }

        // リストの1件目を取得する。
        ContactExtendActionForm contactExtendForm = contactExtendFormList.get(0);
        // アクションフォームに取得したレコードのデータを格納する。
        inForm.setContactId(contactExtendForm.getContactId()); // お問合せID
        inForm.setContactAccountId(contactExtendForm.getContactAccountId()); // お問合せ者アカウントID
        inForm.setContactLastName(contactExtendForm.getContactLastName()); // お問合せ者姓
        inForm.setContactFirstName(contactExtendForm.getContactFirstName()); // お問合せ者名
        inForm.setContactLastNameKana(contactExtendForm.getContactLastNameKana()); // お問合せ者姓(フリガナ)
        inForm.setContactFirstNameKana(contactExtendForm.getContactFirstNameKana()); // お問合せ者名(フリガナ)
        inForm.setContactMailAddress(contactExtendForm.getContactMailAddress()); // お問合せ者メールアドレス
        inForm.setContactContent(contactExtendForm.getContactContent()); // お問合せ内容
        inForm.setContactStatusId(contactExtendForm.getContactStatusId()); // お問合せステータスID
        inForm.setContactStatusName(contactExtendForm.getContactStatusName()); // お問合せステータス名
        inForm.setContactedAt(contactExtendForm.getContactedAt()); // お問合せ日時
        inForm.setContactUpdatedAt(contactExtendForm.getContactUpdatedAt()); // お問合せ更新日時

        // 文字列型に変換したお問合せ日時をアクションフォームに格納する。
        inForm.setStrContactedAt(getStrTimestamp(contactExtendForm.getContactedAt()));
        // 文字列型に変換したお問合せ更新日時をアクションフォームに格納する。
        inForm.setStrContactUpdatedAt(getStrTimestamp(contactExtendForm.getContactUpdatedAt()));

        // お問合せの情報を格納したアクションフォームを戻す。
        return inForm;
    }

    /**
     * 日時(文字列型)を取得するためのメソッド
     */
    String getStrTimestamp(Timestamp timstamp) {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(timstamp);
    }
}
