package front.action.school;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

import dao.school.InsertSchoolDAO;
import dao.school.sql.SchoolInsertDataActionForm;
import front.actionform.school.FrontRegisterSchoolActionForm;
import util.LogUtil;

public class RegisterSchoolCompleteAction extends LookupDispatchAction {
    @Override
    // 押下するボタンに応じてパラメーターを設定するためのメソッド
    protected Map<String,String> getKeyMethodMap() {
        // マップを生成する。
        Map<String,String> map = new HashMap<String,String>();
        // "修正"ボタンを押下した場合のパラメーターをマップに格納する。
        map.put("button.fix", "fix");
        // "登録"ボタンを押下した場合のパラメーターをマップに格納する。
        map.put("button.register", "register");
        // マップを戻す。
        return map;
    }

    /**
     * スクール登録 確認画面で"修正"ボタンを押下した場合に入力画面にフォワードするためのメソッド
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request リクエスト
     * @param response レスポンス
     * @return 遷移先画面の指定
     */
    public ActionForward fix(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {
        if(!isTokenValid(request, true)) {
            // トークンが一致しない場合は入力画面にリダイレクトする。
            return mapping.findForward("invalid");
        }
        // セッションを取得する。
        HttpSession session = request.getSession();
        // アクションフォームを取得する。
        FrontRegisterSchoolActionForm inForm = (FrontRegisterSchoolActionForm) form;

        try {
            // アクションフォームの一覧画面画像および詳細画面画像のファイル名とファイル情報を削除する。
            inForm.setSummaryImageFileName(null);
            inForm.setDetailImage1FileName(null);
            inForm.setDetailImage2FileName(null);
            inForm.setDetailImage3FileName(null);
            inForm.setDetailImage4FileName(null);
            inForm.setDetailImage5FileName(null);
            inForm.setDetailImage6FileName(null);
            inForm.setDetailImage7FileName(null);
            inForm.setDetailImage8FileName(null);
            inForm.setSummaryImageFile(null);
            inForm.setDetailImage1File(null);
            inForm.setDetailImage2File(null);
            inForm.setDetailImage3File(null);
            inForm.setDetailImage4File(null);
            inForm.setDetailImage5File(null);
            inForm.setDetailImage6File(null);
            inForm.setDetailImage7File(null);
            inForm.setDetailImage8File(null);

            // トークンを生成する。
            saveToken(request);
            // 入力画面にフォワードする。
            return mapping.findForward("fix");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // トークンを削除する。
        resetToken(request);
        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontRegisterSchoolActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

    /**
     * スクール登録 確認画面で"登録"ボタンを押下した場合に完了画面にフォワードするためのメソッド
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request リクエスト
     * @param response レスポンス
     * @return 遷移先画面の指定
     * @throws Exception
     */
    public ActionForward register(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        if(!isTokenValid(request, true)) {
            // トークンが一致しない場合は入力画面にリダイレクトする。
            return mapping.findForward("invalid");
        }
        // セッションを取得する。
        HttpSession session = request.getSession();
        // アクションフォームを取得する。
        FrontRegisterSchoolActionForm inForm = (FrontRegisterSchoolActionForm) form;

        try {
            // DAOメソッドに引き渡すアクションフォームを生成して登録するデータを格納する。
            SchoolInsertDataActionForm schoolInsertDataForm = new SchoolInsertDataActionForm();
            schoolInsertDataForm.setRegistrantAccountId(inForm.getRegistrantAccountId()); // 登録者アカウントID
            schoolInsertDataForm.setSchoolName(inForm.getSchoolName()); // スクール名
            schoolInsertDataForm.setSchoolCategoryId(inForm.getSchoolCategoryId()); // スクールカテゴリーID
            schoolInsertDataForm.setSchoolSummary(inForm.getSchoolSummary()); // スクール概要
            schoolInsertDataForm.setSchoolDescription(inForm.getSchoolDescription()); // スクール詳細
            schoolInsertDataForm.setSchoolZipCode1(inForm.getSchoolZipCode1()); // スクール郵便番号1
            schoolInsertDataForm.setSchoolZipCode2(inForm.getSchoolZipCode2()); // スクール郵便番号2
            schoolInsertDataForm.setSchoolPrefectureId(inForm.getSchoolPrefectureId()); // スクール都道府県ID
            schoolInsertDataForm.setSchoolCity(inForm.getSchoolCity()); // スクール市区町村名
            schoolInsertDataForm.setSchoolAddress1(inForm.getSchoolAddress1()); // スクール住所1
            schoolInsertDataForm.setSchoolAddress2(inForm.getSchoolAddress2()); // スクール住所2
            schoolInsertDataForm.setSchoolFee(inForm.getSchoolFee()); // スクール費用
            schoolInsertDataForm.setSupplementaryFee(inForm.getSupplementaryFee()); // 費用補足
            schoolInsertDataForm.setSchoolUrl(inForm.getSchoolUrl()); // スクールサイトURL
            schoolInsertDataForm.setSchoolReleasePropriety(inForm.getSchoolReleasePropriety()); // スクール公開可否
            schoolInsertDataForm.setSchoolEntryPropriety(inForm.getSchoolEntryPropriety()); // スクール申込可否
            schoolInsertDataForm.setSummaryImageFileName(inForm.getSummaryImageFileName()); // 一覧画面画像ファイル名
            schoolInsertDataForm.setDetailImage1FileName(inForm.getDetailImage1FileName()); // 詳細画面画像1ファイル名
            schoolInsertDataForm.setDetailImage2FileName(inForm.getDetailImage2FileName()); // 詳細画面画像2ファイル名
            schoolInsertDataForm.setDetailImage3FileName(inForm.getDetailImage3FileName()); // 詳細画面画像3ファイル名
            schoolInsertDataForm.setDetailImage4FileName(inForm.getDetailImage4FileName()); // 詳細画面画像4ファイル名
            schoolInsertDataForm.setDetailImage5FileName(inForm.getDetailImage5FileName()); // 詳細画面画像5ファイル名
            schoolInsertDataForm.setDetailImage6FileName(inForm.getDetailImage6FileName()); // 詳細画面画像6ファイル名
            schoolInsertDataForm.setDetailImage7FileName(inForm.getDetailImage7FileName()); // 詳細画面画像7ファイル名
            schoolInsertDataForm.setDetailImage8FileName(inForm.getDetailImage8FileName()); // 詳細画面画像8ファイル名
            // スクールのレコードを作成する。
            new InsertSchoolDAO().insertSchool(schoolInsertDataForm);

            // セッションからアクションフォームを削除する。
            session.removeAttribute("FrontRegisterSchoolActionForm");
            // 完了画面にフォワードする。
            return mapping.findForward("register");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontRegisterSchoolActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
