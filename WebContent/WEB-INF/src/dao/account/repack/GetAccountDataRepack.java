package dao.account.repack;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import actionform.AccountExtendActionForm;
import dao.account.SelectAccountJoinDAO;
import dao.account.sql.AccountSelectJoinWhereActionForm;
import exception.NoDataExistException;
import util.ImageFileUtil;

public class GetAccountDataRepack {

    /**
     * アカウントテーブルから検索条件に一致するアカウントのデータを取得してアクションフォームに格納し直すためのメソッド
     * @param whereForm 検索条件を指定するデータを格納したアクションフォーム
     * @param inForm アカウントのデータを格納するためのアクションフォーム
     * @return アカウントのデータを格納したアクションフォーム
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoDataExistException
     */
    public AccountExtendActionForm getAccountData(
            AccountSelectJoinWhereActionForm whereForm,
            AccountExtendActionForm inForm)
            throws ClassNotFoundException, IOException, SQLException, NoDataExistException {
        // アカウントテーブルから指定した条件に一致したレコードのリストを取得する。
        List<AccountExtendActionForm> accountExtendFormFormList
                = new SelectAccountJoinDAO().selectMatchedAccount(whereForm);
        if(accountExtendFormFormList.isEmpty()) {
            // 取得結果が0件の場合はデータ不存在例外を投げる。
            throw new NoDataExistException("該当するデータが存在しません。");
        }

        // 取得したリストの1件目のアクションフォームを取得する。
        AccountExtendActionForm accountExtendForm = accountExtendFormFormList.get(0);
        // アクションフォームに取得したレコードのデータを格納する。
        inForm.setAccountId(accountExtendForm.getAccountId()); // アカウントID
        inForm.setLastName(accountExtendForm.getLastName()); // 姓
        inForm.setFirstName(accountExtendForm.getFirstName()); // 名
        inForm.setLastNameKana(accountExtendForm.getLastNameKana()); // 姓(フリガナ)
        inForm.setFirstNameKana(accountExtendForm.getFirstNameKana()); // 名(フリガナ)
        inForm.setSexId(accountExtendForm.getSexId()); // 性別ID
        inForm.setSexName(accountExtendForm.getSexName()); // 性別名
        inForm.setBirthDate(accountExtendForm.getBirthDate()); // 生年月日
        inForm.setPrefectureId(accountExtendForm.getPrefectureId()); // 都道府県ID
        inForm.setPrefectureName(accountExtendForm.getPrefectureName()); // 都道府県名
        inForm.setMailAddress(accountExtendForm.getMailAddress()); // メールアドレス
        inForm.setProfileImageFileName(accountExtendForm.getProfileImageFileName()); // プロフィール画像ファイル名
        inForm.setSelfIntroduction(accountExtendForm.getSelfIntroduction()); // 自己紹介
        inForm.setAccountStatus(accountExtendForm.getAccountStatus()); // アカウントステータス
        inForm.setAccountCreatedAt(accountExtendForm.getAccountCreatedAt()); // アカウント作成日時
        inForm.setAccountUpdatedAt(accountExtendForm.getAccountUpdatedAt()); // アカウント更新日時

        // アクションフォームから生年月日を取得する。
        Timestamp birthDate = inForm.getBirthDate();
        if(birthDate != null) {
            // 生年月日がNULLではない場合

            // 生年月日から文字列型の年、月、日を取得してアクションフォームに格納する。
            inForm.setBirthYear(new SimpleDateFormat("yyyy").format(birthDate));
            inForm.setBirthMonth(new SimpleDateFormat("MM").format(birthDate));
            inForm.setBirthDay(new SimpleDateFormat("dd").format(birthDate));
        } else {
            // 上記以外の場合は空文字を格納する。
            inForm.setBirthYear("");
            inForm.setBirthMonth("");
            inForm.setBirthDay("");
        }

        // アクションフォームからプロフィール画像のファイル名を取得する。
        String profileImageFileName = accountExtendForm.getProfileImageFileName();
        if(!StringUtils.isEmpty(profileImageFileName)) {
            // ファイル名が存在する場合は画像パスを取得してアクションフォームに格納する。
            String profileImageFilePath = new ImageFileUtil().getImageFilePath(profileImageFileName);
            inForm.setProfileImageFilePath(profileImageFilePath);
        } else {
            // 上記以外の場合は空文字を設定する。
            inForm.setProfileImageFilePath("");
        }

        // アカウントのデータを格納したアクションフォームを戻す。
        return inForm;
    }

}
