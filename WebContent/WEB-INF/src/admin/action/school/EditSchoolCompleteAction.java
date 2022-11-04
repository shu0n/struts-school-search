package admin.action.school;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

import admin.actionform.school.AdminEditSchoolActionForm;
import dao.school.UpdateSchoolDAO;
import dao.school.sql.SchoolUpdateDataActionForm;
import model.school.SchoolFileModel;
import util.LogUtil;

public class EditSchoolCompleteAction extends LookupDispatchAction {
    @Override
    // 押下するボタンに応じてパラメーターを設定するためのメソッド
    protected Map<String,String> getKeyMethodMap() {
        // マップを生成する。
        Map<String,String> map = new HashMap<String,String>();
        // "修正"ボタンを押下した場合のパラメーターをマップに格納する。
        map.put("button.fix", "fix");
        // "編集"ボタンを押下した場合のパラメーターをマップに格納する。
        map.put("button.edit", "edit");
        // マップを戻す。
        return map;
    }

    /**
     * 管理画面 スクール編集 確認画面で"修正"ボタンを押下した場合に入力画面にフォワードするためのメソッド
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
        AdminEditSchoolActionForm inForm = (AdminEditSchoolActionForm) form;

        try {
            // アクションフォームの一覧画面画像削除フラグおよび詳細画面画像削除フラグにfalseを設定する。
            inForm.setDeleteSummaryImageFileFlag(false);
            inForm.setDeleteDetailImage1FileFlag(false);
            inForm.setDeleteDetailImage2FileFlag(false);
            inForm.setDeleteDetailImage3FileFlag(false);
            inForm.setDeleteDetailImage4FileFlag(false);
            inForm.setDeleteDetailImage5FileFlag(false);
            inForm.setDeleteDetailImage6FileFlag(false);
            inForm.setDeleteDetailImage7FileFlag(false);
            inForm.setDeleteDetailImage8FileFlag(false);
            // アクションフォームの一覧画面画像および詳細画面画像のファイル名とファイル情報を削除する。
            inForm.setSummaryImageFileNameUpdate(null);
            inForm.setDetailImage1FileNameUpdate(null);
            inForm.setDetailImage2FileNameUpdate(null);
            inForm.setDetailImage3FileNameUpdate(null);
            inForm.setDetailImage4FileNameUpdate(null);
            inForm.setDetailImage5FileNameUpdate(null);
            inForm.setDetailImage6FileNameUpdate(null);
            inForm.setDetailImage7FileNameUpdate(null);
            inForm.setDetailImage8FileNameUpdate(null);
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
        session.removeAttribute("AdminEditSchoolActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

    /**
     * スクール編集 確認画面で"編集"ボタンを押下した場合に完了画面にフォワードするためのメソッド
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request リクエスト
     * @param response レスポンス
     * @return 遷移先画面の指定
     * */
    public ActionForward edit(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response){
        if(!isTokenValid(request, true)) {
            // トークンが一致しない場合は入力画面にリダイレクトする。
            return mapping.findForward("invalid");
        }
        // セッションを取得する。
        HttpSession session = request.getSession();
        // アクションフォームを取得する。
        AdminEditSchoolActionForm inForm = (AdminEditSchoolActionForm) form;

        try {
            // アクションフォームに更新された一覧画面画像ファイル名と詳細画面画像ファイル名を格納する。
            inForm = (AdminEditSchoolActionForm) new SchoolFileModel().setUpdateFileName(inForm);

            // DAOメソッドに引き渡すアクションフォームを生成して登録するデータを格納する。
            SchoolUpdateDataActionForm schoolUpdateDataForm = new SchoolUpdateDataActionForm();
            schoolUpdateDataForm.setSchoolId(inForm.getSchoolId()); // スクール名
            schoolUpdateDataForm.setRegistrantAccountId(inForm.getRegistrantAccountId()); // 登録者アカウントID
            schoolUpdateDataForm.setRegistrantAdminId(inForm.getRegistrantAdminId()); // 登録者管理者ID
            schoolUpdateDataForm.setSchoolName(inForm.getSchoolName()); // スクール名
            schoolUpdateDataForm.setSchoolCategoryId(inForm.getSchoolCategoryId()); // スクールカテゴリーID
            schoolUpdateDataForm.setSchoolSummary(inForm.getSchoolSummary()); // スクール概要
            schoolUpdateDataForm.setSchoolDescription(inForm.getSchoolDescription()); // スクール詳細
            schoolUpdateDataForm.setSchoolZipCode1(inForm.getSchoolZipCode1()); // スクール郵便番号1
            schoolUpdateDataForm.setSchoolZipCode2(inForm.getSchoolZipCode2()); // スクール郵便番号2
            schoolUpdateDataForm.setSchoolPrefectureId(inForm.getSchoolPrefectureId()); // スクール都道府県ID
            schoolUpdateDataForm.setSchoolCity(inForm.getSchoolCity()); // スクール市区町村
            schoolUpdateDataForm.setSchoolAddress1(inForm.getSchoolAddress1()); // スクール住所1
            schoolUpdateDataForm.setSchoolAddress2(inForm.getSchoolAddress2()); // スクール住所2
            schoolUpdateDataForm.setSchoolFee(inForm.getSchoolFee()); // スクール費用
            schoolUpdateDataForm.setSupplementaryFee(inForm.getSupplementaryFee()); // 費用補足
            schoolUpdateDataForm.setSchoolUrl(inForm.getSchoolUrl()); // スクールサイトURL
            schoolUpdateDataForm.setSchoolReleasePropriety(inForm.getSchoolReleasePropriety()); // スクール公開可否
            schoolUpdateDataForm.setSchoolEntryPropriety(inForm.getSchoolEntryPropriety()); // スクール申込可否
            // 一覧画面画像ファイル名(更新)を取得する。
            String summaryImageFileNameUpdate = inForm.getSummaryImageFileNameUpdate();
            if(!StringUtils.isEmpty(summaryImageFileNameUpdate)) {
                // 一覧画面画像ファイル名(更新)が存在する場合は一覧画面画像ファイル名に格納する。
                schoolUpdateDataForm.setSummaryImageFileName(summaryImageFileNameUpdate);
            } else if(inForm.isDeleteSummaryImageFileFlag()) {
                // 一覧画面画像削除フラグがtrueの場合は一覧画面画像ファイル名に空文字を設定する。
                schoolUpdateDataForm.setSummaryImageFileName("");
            }
            // 詳細画面画像1ファイル名(更新)を取得する。
            String detailImage1FileNameUpdate = inForm.getDetailImage1FileNameUpdate();
            if(!StringUtils.isEmpty(detailImage1FileNameUpdate)) {
                // 詳細画面画像1ファイル名(更新)が存在する場合は詳細画面1画像ファイル名に格納する。
                schoolUpdateDataForm.setDetailImage1FileName(detailImage1FileNameUpdate);
            } else if(inForm.isDeleteDetailImage1FileFlag()) {
                // 詳細画面画像1削除フラグがtrueの場合は詳細画面画像1ファイル名に空文字を設定する。
                schoolUpdateDataForm.setDetailImage1FileName("");
            }
            // 詳細画面画像2ファイル名(更新)を取得する。
            String detailImage2FileNameUpdate = inForm.getDetailImage2FileNameUpdate();
            if(!StringUtils.isEmpty(detailImage2FileNameUpdate)) {
                // 詳細画面画像2ファイル名(更新)が存在する場合は詳細画面2画像ファイル名に格納する。
                schoolUpdateDataForm.setDetailImage2FileName(detailImage2FileNameUpdate);
            } else if(inForm.isDeleteDetailImage2FileFlag()) {
                // 詳細画面画像2削除フラグがtrueの場合は詳細画面画像2ファイル名に空文字を設定する。
                schoolUpdateDataForm.setDetailImage2FileName("");
            }
            // 詳細画面画像3ファイル名(更新)を取得する。
            String detailImage3FileNameUpdate = inForm.getDetailImage3FileNameUpdate();
            if(!StringUtils.isEmpty(detailImage3FileNameUpdate)) {
                // 詳細画面画像3ファイル名(更新)が存在する場合は詳細画面3画像ファイル名に格納する。
                schoolUpdateDataForm.setDetailImage3FileName(detailImage3FileNameUpdate);
            } else if(inForm.isDeleteDetailImage3FileFlag()) {
                // 詳細画面画像3削除フラグがtrueの場合は詳細画面画像3ファイル名に空文字を設定する。
                schoolUpdateDataForm.setDetailImage3FileName("");
            }
            // 詳細画面画像4ファイル名(更新)を取得する。
            String detailImage4FileNameUpdate = inForm.getDetailImage4FileNameUpdate();
            if(!StringUtils.isEmpty(detailImage4FileNameUpdate)) {
                // 詳細画面画像4ファイル名(更新)が存在する場合は詳細画面4画像ファイル名に格納する。
                schoolUpdateDataForm.setDetailImage4FileName(detailImage4FileNameUpdate);
            } else if(inForm.isDeleteDetailImage4FileFlag()) {
                // 詳細画面画像4削除フラグがtrueの場合は詳細画面画像4ファイル名に空文字を設定する。
                schoolUpdateDataForm.setDetailImage4FileName("");
            }
            // 詳細画面画像5ファイル名(更新)を取得する。
            String detailImage5FileNameUpdate = inForm.getDetailImage5FileNameUpdate();
            if(!StringUtils.isEmpty(detailImage5FileNameUpdate)) {
                // 詳細画面画像5ファイル名(更新)が存在する場合は詳細画面5画像ファイル名に格納する。
                schoolUpdateDataForm.setDetailImage5FileName(detailImage5FileNameUpdate);
            } else if(inForm.isDeleteDetailImage1FileFlag()) {
                // 詳細画面画像5削除フラグがtrueの場合は詳細画面画像5ファイル名に空文字を設定する。
                schoolUpdateDataForm.setDetailImage5FileName("");
            }
            // 詳細画面画像6ファイル名(更新)を取得する。
            String detailImage6FileNameUpdate = inForm.getDetailImage6FileNameUpdate();
            if(!StringUtils.isEmpty(detailImage1FileNameUpdate)) {
                // 詳細画面画像6ファイル名(更新)が存在する場合は詳細画面6画像ファイル名に格納する。
                schoolUpdateDataForm.setDetailImage6FileName(detailImage6FileNameUpdate);
            } else if(inForm.isDeleteDetailImage6FileFlag()) {
                // 詳細画面画像6削除フラグがtrueの場合は詳細画面画像6ファイル名に空文字を設定する。
                schoolUpdateDataForm.setDetailImage6FileName("");
            }
            // 詳細画面画像7ファイル名(更新)を取得する。
            String detailImage7FileNameUpdate = inForm.getDetailImage7FileNameUpdate();
            if(!StringUtils.isEmpty(detailImage7FileNameUpdate)) {
                // 詳細画面画像7ファイル名(更新)が存在する場合は詳細画面7画像ファイル名に格納する。
                schoolUpdateDataForm.setDetailImage7FileName(detailImage7FileNameUpdate);
            } else if(inForm.isDeleteDetailImage7FileFlag()) {
                // 詳細画面画像7削除フラグがtrueの場合は詳細画面画像7ファイル名に空文字を設定する。
                schoolUpdateDataForm.setDetailImage7FileName("");
            }
            // 詳細画面画像8ファイル名(更新)を取得する。
            String detailImage8FileNameUpdate = inForm.getDetailImage8FileNameUpdate();
            if(!StringUtils.isEmpty(detailImage8FileNameUpdate)) {
                // 詳細画面画像8ファイル名(更新)が存在する場合は詳細画面8画像ファイル名に格納する。
                schoolUpdateDataForm.setDetailImage8FileName(detailImage8FileNameUpdate);
            } else if(inForm.isDeleteDetailImage8FileFlag()) {
                // 詳細画面画像8削除フラグがtrueの場合は詳細画面画像8ファイル名に空文字を設定する。
                schoolUpdateDataForm.setDetailImage8FileName("");
            }
            // スクールIDに紐づくスクールのレコードを更新する。
            new UpdateSchoolDAO().updateSchool(schoolUpdateDataForm);

            // セッションからアクションフォームを削除する。
            session.removeAttribute("AdminEditSchoolActionForm");
            // 完了画面にフォワードする。
            return mapping.findForward("edit");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("AdminEditSchoolActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
