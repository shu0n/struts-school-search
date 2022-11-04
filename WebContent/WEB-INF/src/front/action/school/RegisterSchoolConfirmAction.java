package front.action.school;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import front.actionform.school.FrontRegisterSchoolActionForm;
import model.school.SchoolFileModel;
import model.school.SchoolRegistrantModel;
import util.LogUtil;

public class RegisterSchoolConfirmAction extends Action {

    /**
     * スクール登録 確認画面にフォワードするためのメソッド
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
        FrontRegisterSchoolActionForm inForm = (FrontRegisterSchoolActionForm) form;

        try {
            // スクール登録者アカウントIDをもとにスクールを登録するアカウントの姓と名を取得してアクションフォームに格納する。
            inForm.setRegistrantLastName(new SchoolRegistrantModel().getRegistrantName(
                    inForm.getRegistrantAccountId()).get("registrantLastName"));
            inForm.setRegistrantFirstName(new SchoolRegistrantModel().getRegistrantName(
                    inForm.getRegistrantAccountId()).get("registrantFirstName"));
            // カテゴリーIDをもとにカテゴリー名を取得してアクションフォームに格納する。
            inForm.setSchoolCategoryName(inForm.getSchoolCategoryMap().get(inForm.getSchoolCategoryId()));
            // 都道府県IDをもとに都道府県名を取得してアクションフォームに格納する。
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
        session.removeAttribute("FrontRegisterSchoolActionForm");
        // 正常に処理ができなかった場合はエラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
