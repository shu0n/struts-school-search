package model.school;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;

import dao.prefecture.SelectPrefectureDAO;
import dao.prefecture.sql.PrefectureSelectWhereActionForm;
import dao.school.sql.SchoolInsertDataActionForm;
import dao.school.sql.SchoolUpdateDataActionForm;
import model.account.AccountStatusModel;
import model.admin.AdminStatusModel;
import model.category.CategoryStatusModel;

public class SchoolValidatorModel {

    /**
     * インポート(登録)するスクールのデータを検証するためのメソッド
     * @param insertDataForm 検証するデータを格納したアクションフォーム
     * @return 検証結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean validateInsertSchoolData(SchoolInsertDataActionForm insertDataForm)
            throws ClassNotFoundException, IOException, SQLException {
        // 登録者スクールIDおよび登録者アカウントID
        if(insertDataForm.getRegistrantAccountId() == 0 && insertDataForm.getRegistrantAdminId() == 0) {
            // 登録者アカウントIDおよび登録者管理者IDが未設定の場合はfalseを戻す。
            return false;
        } else if (insertDataForm.getRegistrantAccountId() != 0 && insertDataForm.getRegistrantAdminId() != 0) {
            // 登録者アカウントIDおよび登録者管理者IDが設定されている場合はfalseを戻す。
            return false;
        } else if(insertDataForm.getRegistrantAccountId() != 0) {
            // 登録者アカウントIDが設定されている場合は登録済かを判定する。
            if(!new AccountStatusModel().isAccountEnable(insertDataForm.getRegistrantAccountId())) {
                // 未登録の場合はfalseを戻す。
                return false;
            }
        } else {
            // 上記以外の場合

            if(!new AdminStatusModel().isAdminEnable(insertDataForm.getRegistrantAdminId())) {
                // 当該登録者管理者IDが未登録の場合はfalseを戻す。
                return false;
            }
        }

        // スクール名
        if(!isMaxLength(insertDataForm.getSchoolName(), 50)) {
            // 50文字を超える場合はfalseを戻す。
            return false;
        }

        // スクールカテゴリーID
        if(insertDataForm.getSchoolCategoryId() == 0 ||
                !new CategoryStatusModel().isCategoryEnable(insertDataForm.getSchoolCategoryId())) {
            // 設定されていないまたは未登録の場合はfalseを戻す。
            return false;
        }

        // スクール概要
        if(!isMaxLength(insertDataForm.getSchoolSummary(), 150)) {
            // 150文字を超える場合はfalseを戻す。
            return false;
        }

        // スクール詳細
        if(!isMaxLength(insertDataForm.getSchoolDescription(), 1200)) {
            // 1200文字を超える場合はfalseを戻す。
            return false;
        }

        // スクール郵便番号1
        if(!isRegexp(insertDataForm.getSchoolZipCode1(), "^\\d{3}$")) {
            // 3桁の数字以外の場合はfalseを戻す。
            return false;
        }

        // スクール郵便番号2
        if(!isRegexp(insertDataForm.getSchoolZipCode2(), "^\\d{4}$")) {
            // 4桁の数字以外の場合はfalseを戻す。
            return false;
        }

        // スクール都道府県ID
        // DAOメソッドに引き渡すアクションフォームを生成して都道府県IDを格納する。
        PrefectureSelectWhereActionForm prefectureSelectWhereForm = new PrefectureSelectWhereActionForm();
        prefectureSelectWhereForm.setPrefectureId(insertDataForm.getSchoolPrefectureId());
        if(new SelectPrefectureDAO().selectMatchedPrefecture(prefectureSelectWhereForm).isEmpty()) {
            // 未登録の場合はfalseを戻す。
            return false;
        }

        // スクール市区町村
        if(!isMaxLength(insertDataForm.getSchoolCity(), 20)) {
            // 20文字を超える場合はfalseを戻す。
            return false;
        }

        // スクール住所1
        if(!isMaxLength(insertDataForm.getSchoolAddress1(), 150)) {
            // 150文字を超える場合はfalseを戻す。
            return false;
        }

        // スクール住所2
        if(!GenericValidator.maxLength(insertDataForm.getSchoolAddress2(), 150)) {
            // 150文字を超える場合はfalseを戻す。
            return false;
        }

        // スクール費用
        if(!GenericValidator.isInRange(insertDataForm.getSchoolFee().intValue(), 1, 99999999)) {
            // 8桁を超える数値の場合はfalseを戻す。
            return false;
        }

        // スクール費用補足
        if(!GenericValidator.maxLength(insertDataForm.getSupplementaryFee(), 150)) {
            // 150文字を超える場合はfalseを戻す。
            return false;
        }

        // スクールURL
        if(!StringUtils.isEmpty(insertDataForm.getSchoolUrl())) {
            if(!GenericValidator.maxLength(insertDataForm.getSchoolUrl(), 290)
                    ||!GenericValidator.isUrl(insertDataForm.getSchoolUrl())) {
                // 290文字を超えるまたはURLの入力形式に一致しない場合はfalseを戻す。
                return false;
            }
        }

        // スクール公開可否
        if(!"0".equals(insertDataForm.getSchoolReleasePropriety())
                && !"1".equals(insertDataForm.getSchoolReleasePropriety())) {
            // "0"(無効)または"1"(有効)以外の場合はfalseを戻す。
            return false;
        }

        // スクール申込可否
        if(!"0".equals(insertDataForm.getSchoolEntryPropriety())
                && !"1".equals(insertDataForm.getSchoolEntryPropriety())) {
            // "0"(無効)または"1"(有効)以外の場合はfalseを戻す。
            return false;
        }

        // 一覧画面画像ファイル名
        if(!StringUtils.isEmpty(insertDataForm.getSummaryImageFileName())) {
            if(!GenericValidator.maxLength(insertDataForm.getSummaryImageFileName(), 290)) {
                // 290文字を超える場合はfalseを戻す。
                return false;
            }
        }

        // 詳細画面画像1ファイル名
        if(!StringUtils.isEmpty(insertDataForm.getDetailImage1FileName())) {
            if(!GenericValidator.maxLength(insertDataForm.getDetailImage1FileName(), 290)) {
                // 290文字を超える場合はfalseを戻す。
                return false;
            }
        }

        // 詳細画面画像2ファイル名
        if(!StringUtils.isEmpty(insertDataForm.getDetailImage2FileName())) {
            if(!GenericValidator.maxLength(insertDataForm.getDetailImage2FileName(), 290)) {
                // 290文字を超える場合はfalseを戻す。
                return false;
            }
        }

        // 詳細画面画像3ファイル名
        if(!StringUtils.isEmpty(insertDataForm.getDetailImage3FileName())) {
            if(!GenericValidator.maxLength(insertDataForm.getDetailImage3FileName(), 290)) {
                // 290文字を超える場合はfalseを戻す。
                return false;
            }
        }

        // 詳細画面画像4ファイル名
        if(!StringUtils.isEmpty(insertDataForm.getDetailImage4FileName())) {
            if(!GenericValidator.maxLength(insertDataForm.getDetailImage4FileName(), 290)) {
                // 290文字を超える場合はfalseを戻す。
                return false;
            }
        }

        // 詳細画面画像5ファイル名
        if(!StringUtils.isEmpty(insertDataForm.getDetailImage5FileName())) {
            if(!GenericValidator.maxLength(insertDataForm.getDetailImage5FileName(), 290)) {
                // 290文字を超える場合はfalseを戻す。
                return false;
            }
        }

        // 詳細画面画像6ファイル名
        if(!StringUtils.isEmpty(insertDataForm.getDetailImage6FileName())) {
            if(!GenericValidator.maxLength(insertDataForm.getDetailImage6FileName(), 290)) {
                // 290文字を超える場合はfalseを戻す。
                return false;
            }
        }

        // 詳細画面画像7ファイル名
        if(!StringUtils.isEmpty(insertDataForm.getDetailImage1FileName())) {
            if(!GenericValidator.maxLength(insertDataForm.getDetailImage7FileName(), 290)) {
                // 290文字を超える場合はfalseを戻す。
                return false;
            }
        }

        // 詳細画面画像8ファイル名
        if(!StringUtils.isEmpty(insertDataForm.getDetailImage8FileName())) {
            if(!GenericValidator.maxLength(insertDataForm.getDetailImage8FileName(), 290)) {
                // 290文字を超える場合はfalseを戻す。
                return false;
            }
        }

        // 検証結果を戻す。
        return true;
    }

    /**
     * インポート(更新)するスクールのデータを検証するためのメソッド
     * @param updateDataForm 検証するデータを格納したアクションフォーム
     * @return 検証結果
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public boolean validateUpdateSchoolData(SchoolUpdateDataActionForm updateDataForm)
            throws ClassNotFoundException, IOException, SQLException {
        // 登録者アカウントIDおよび登録者管理者ID
        if(updateDataForm.getRegistrantAccountId() != 0 && updateDataForm.getRegistrantAdminId() != 0) {
            // 登録者アカウントIDおよび登録者管理者IDが設定されている場合はfalseを戻す。
            return false;
        } else if (updateDataForm.getRegistrantAccountId() == 0 && updateDataForm.getRegistrantAdminId() == 0) {
            // 登録者アカウントIDおよび登録者管理者IDが未設定の場合はfalseを戻す。
            return false;
        } else if(updateDataForm.getRegistrantAccountId() != 0) {
            // 登録者アカウントIDが設定されている場合は登録済かを判定する。
            if(!new AccountStatusModel().isAccountEnable(updateDataForm.getRegistrantAccountId())) {
                // 未登録の場合はfalseを戻す。
                return false;
            }
        } else {
            // 上記以外の場合は登録者管理者IDが登録済かを判定する。
            if(!new AdminStatusModel().isAdminEnable(updateDataForm.getRegistrantAdminId())) {
                // 未登録の場合はfalseを戻す。
                return false;
            }
        }

        // スクールIDおよび(登録者アカウントID、登録者管理者ID)
        if(updateDataForm.getRegistrantAccountId() != 0) {
            // 登録者アカウントIDが設定さている場合

            if(!new SchoolRegistrantModel().isRegistrant(
                    updateDataForm.getSchoolId(), updateDataForm.getRegistrantAccountId())) {
                // スクールIDと当該登録者アカウントIDが登録されたスクールが存在しない場合はfalseを戻す。
                return false;
            }
        } else if(updateDataForm.getRegistrantAdminId() != 0) {
            // 登録者管理者IDが設定さている場合

            if(!new SchoolRegistrantModel().isRegistrantAdmin(
                    updateDataForm.getSchoolId(), updateDataForm.getRegistrantAdminId())) {
                // スクールIDと当該登録者管理者IDが登録されたスクールが存在しない場合はfalseを戻す。
                return false;
            }
        }

        // スクール名
        if(!isMaxLength(updateDataForm.getSchoolName(), 50)) {
            // 50文字を超える場合はfalseを戻す。
            return false;
        }

        // スクールカテゴリーID
        if(!new CategoryStatusModel().isCategoryEnable(updateDataForm.getSchoolCategoryId())) {
            // 設定されていないまたは未登録の場合はfalseを戻す。
            return false;
        }

        // スクール概要
        if(!isMaxLength(updateDataForm.getSchoolSummary(), 150)) {
            // 150文字を超える場合はfalseを戻す。
            return false;
        }

        // スクール詳細
        if(!isMaxLength(updateDataForm.getSchoolDescription(), 1200)) {
            // 1200文字を超える場合はfalseを戻す。
            return false;
        }

        // スクール郵便番号1
        if(!isRegexp(updateDataForm.getSchoolZipCode1(), "^\\d{3}$")) {
            // 3桁の数字以外の場合はfalseを戻す。
            return false;
        }

        // スクール郵便番号2
        if(!isRegexp(updateDataForm.getSchoolZipCode2(), "^\\d{4}$")) {
            // 4桁の数字以外の場合はfalseを戻す。
            return false;
        }

        // スクール都道府県ID
        // DAOメソッドに引き渡すアクションフォームを生成して都道府県IDを格納する。
        PrefectureSelectWhereActionForm prefectureSelectWhereForm = new PrefectureSelectWhereActionForm();
        prefectureSelectWhereForm.setPrefectureId(updateDataForm.getSchoolPrefectureId());
        if(new SelectPrefectureDAO().selectMatchedPrefecture(prefectureSelectWhereForm).isEmpty()) {
            // 未登録の場合はfalseを戻す。
            return false;
        }

        // スクール市区町村
        if(!isMaxLength(updateDataForm.getSchoolCity(), 20)) {
            // 20文字を超える場合はfalseを戻す。
            return false;
        }

        // スクール住所1
        if(!isMaxLength(updateDataForm.getSchoolAddress1(), 150)) {
            // 150文字を超える場合はfalseを戻す。
            return false;
        }

        // スクール住所2
        if(!GenericValidator.maxLength(updateDataForm.getSchoolAddress2(), 150)) {
            // 150文字を超える場合はfalseを戻す。
            return false;
        }

        // スクール費用
        if(!GenericValidator.isInRange(updateDataForm.getSchoolFee().intValue(), 1, 99999999)) {
            // 8桁を超える数値の場合はfalseを戻す。
            return false;
        }

        // スクール費用補足
        if(!GenericValidator.maxLength(updateDataForm.getSupplementaryFee(), 150)) {
            // 150文字を超える場合はfalseを戻す。
            return false;
        }

        // スクールURL
        if(!StringUtils.isEmpty(updateDataForm.getSchoolUrl())) {
            if(!GenericValidator.maxLength(updateDataForm.getSchoolUrl(), 290)
                    || !GenericValidator.isUrl(updateDataForm.getSchoolUrl())) {
                // 290文字を超えるまたはURLの入力形式に一致しない場合はfalseを戻す。
                return false;
            }
        }

        // スクール公開可否
        if(!"0".equals(updateDataForm.getSchoolReleasePropriety())
                && !"1".equals(updateDataForm.getSchoolReleasePropriety())) {
            // "0"(無効)または"1"(有効)以外の場合はfalseを戻す。
            return false;
        }

        // スクール申込可否
        if(!"0".equals(updateDataForm.getSchoolEntryPropriety())
                && !"1".equals(updateDataForm.getSchoolEntryPropriety())) {
            // "0"(無効)または"1"(有効)以外の場合はfalseを戻す。
            return false;
        }

        // 一覧画面画像ファイル名
        if(!StringUtils.isEmpty(updateDataForm.getSummaryImageFileName())) {
            if(!GenericValidator.maxLength(updateDataForm.getSummaryImageFileName(), 290)) {
                // 290文字を超える場合はfalseを戻す。
                return false;
            }
        }

        // 詳細画面画像1ファイル名
        if(!StringUtils.isEmpty(updateDataForm.getDetailImage1FileName())) {
            if(!GenericValidator.maxLength(updateDataForm.getDetailImage1FileName(), 290)) {
                // 290文字を超える場合はfalseを戻す。
                return false;
            }
        }

        // 詳細画面画像2ファイル名
        if(!StringUtils.isEmpty(updateDataForm.getDetailImage2FileName())) {
            if(!GenericValidator.maxLength(updateDataForm.getDetailImage2FileName(), 290)) {
                return false;
            }
        }

        // 詳細画面画像3ファイル名
        if(!StringUtils.isEmpty(updateDataForm.getDetailImage3FileName())) {
            if(!GenericValidator.maxLength(updateDataForm.getDetailImage3FileName(), 290)) {
                // 290文字を超える場合はfalseを戻す。
                return false;
            }
        }

        // 詳細画面画像4ファイル名
        if(!StringUtils.isEmpty(updateDataForm.getDetailImage4FileName())) {
            if(!GenericValidator.maxLength(updateDataForm.getDetailImage4FileName(), 290)) {
                // 290文字を超える場合はfalseを戻す。
                return false;
            }
        }

        // 詳細画面画像5ファイル名
        if(!StringUtils.isEmpty(updateDataForm.getDetailImage5FileName())) {
            if(!GenericValidator.maxLength(updateDataForm.getDetailImage5FileName(), 290)) {
                // 290文字を超える場合はfalseを戻す。
                return false;
            }
        }

        // 詳細画面画像5ファイル名
        if(!StringUtils.isEmpty(updateDataForm.getDetailImage5FileName())) {
            if(!GenericValidator.maxLength(updateDataForm.getDetailImage5FileName(), 290)) {
                // 290文字を超える場合はfalseを戻す。
                return false;
            }
        }

        // 詳細画面画像6ファイル名
        if(!StringUtils.isEmpty(updateDataForm.getDetailImage6FileName())) {
            if(!GenericValidator.maxLength(updateDataForm.getDetailImage6FileName(), 290)) {
                // 290文字を超える場合はfalseを戻す。
                return false;
            }
        }

        // 詳細画面画像7ファイル名
        if(!StringUtils.isEmpty(updateDataForm.getDetailImage1FileName())) {
            if(!GenericValidator.maxLength(updateDataForm.getDetailImage7FileName(), 290)) {
                // 290文字を超える場合はfalseを戻す。
                return false;
            }
        }

        // 詳細画面画像8ファイル名
        if(!StringUtils.isEmpty(updateDataForm.getDetailImage8FileName())) {
            if(!GenericValidator.maxLength(updateDataForm.getDetailImage8FileName(), 290)) {
                // 290文字を超える場合はfalseを戻す。
                return false;
            }
        }

        // 検証結果を戻す。
        return true;
    }

    /**
     * データがNULL/空文字ではなく最大文字数以下であるかを検証するためのメソッド
     * @param data 検証対象のデータ
     * @param macLength 最大文字数
     * @return 検証結果
     */
    private boolean isMaxLength(String data, int macLength) {
        if(GenericValidator.isBlankOrNull(data) || !GenericValidator.maxLength(data, macLength)) {
            // NULLまたは空文字または最大文字数を超える場合はfalseを戻す。
            return false;
        } else {
            // 上記以外の場合はtrueを戻す。
            return true;
        }
    }

    /**
     * データがNULL/空文字ではなく指定した正規表現に一致しているかを検証するためのメソッド
     * @param data 検証対象のデータ
     * @param regexp 正規表現
     * @return 検証結果
     */
    private boolean isRegexp(String data, String regexp) {
        if(GenericValidator.isBlankOrNull(data) || !GenericValidator.matchRegexp(data, regexp)) {
            // NULLまたは空文字または指定した正規表現に一致しない場合はfalseを戻す。
            return false;
        } else {
            // 上記以外の場合はtrueを戻す。
            return true;
        }
    }

}
