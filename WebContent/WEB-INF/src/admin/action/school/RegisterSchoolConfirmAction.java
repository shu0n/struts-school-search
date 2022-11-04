package admin.action.school;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import admin.actionform.school.AdminRegisterSchoolActionForm;
import model.login.LoginAdminStatusModel;
import model.school.SchoolFileModel;
import util.LogUtil;

public class RegisterSchoolConfirmAction extends Action {

    /**
     * 管理画面 スクール登録 確認画面にフォワードするためのメソッド
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request リクエスト
     * @param response レスポンス
     * @return 遷移先画面の指定
     */
    public ActionForward execute(
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
        AdminRegisterSchoolActionForm inForm = (AdminRegisterSchoolActionForm) form;

        try {
            if("account".equals(inForm.getRegistrantKind())) {
                // 登録者種別が"account"(管理者)の場合

                // アクションフォームに登録者種別名を格納する。
                inForm.setRegistrantKindName("アカウント");
                // 上記以外の場合は登録者アカウントID(文字列型)を登録者アカウントID(整数型)に変換してアクションフォームに格納する。
                inForm.setRegistrantAccountId(Integer.parseInt(inForm.getStrRegistrantAccountId()));
            } else {
                // 上記以外の場合

                // アクションフォームに登録者種別名を格納する。
                inForm.setRegistrantKindName("管理者");
                //アクションフォームに登録者管理者ID(文字列型)と登録者管理者ID(整数型)を格納する。
                inForm.setRegistrantAdminId(new LoginAdminStatusModel().getAdminId(session));
                inForm.setStrRegistrantAdminId(String.valueOf(new LoginAdminStatusModel().getAdminId(session)));
            }
            // スクールカテゴリーIDをもとにスクールカテゴリー名を取得してアクションフォームに格納する。
            inForm.setSchoolCategoryName(inForm.getSchoolCategoryMap().get(inForm.getSchoolCategoryId()));
            // スクール都道府県IDをもとにスクール都道府県名を取得してアクションフォームに格納する。
            inForm.setSchoolPrefectureName(inForm.getSchoolPrefectureMap().get(inForm.getSchoolPrefectureId()));
            // スクール費用(文字列型)を真数値型に変換してアクションフォームに格納する。
            inForm.setSchoolFee(new BigDecimal(inForm.getStrSchoolFee()));
            // 公開ステータスIDをもとに公開ステータス名を取得する。
            inForm.setSchoolReleaseProprietyName(
                    inForm.getSchoolReleaseProprietyMap().get(inForm.getSchoolReleasePropriety()));
            // 申込ステータスIDをもとに申込ステータス名を取得する。
            inForm.setSchoolEntryProprietyName(
                    inForm.getSchoolEntryProprietyMap().get(inForm.getSchoolEntryPropriety()));

            // リクエストに一覧画面画像と詳細画面画像のパスとファイル名を格納する。
            request = new SchoolFileModel().setFileInfo(inForm, request);

            // トークンを生成する。
            saveToken(request);
            // 確認画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("AdminRegisterSchoolActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
