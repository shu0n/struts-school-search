package front.action.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import front.actionform.account.FrontCreateAccountActionForm;
import model.account.AccountFormPartsModel;
import model.login.LoginStatusModel;
import util.FormPartsUtil;
import util.LogUtil;

public class CreateAccountInputAction extends Action {

    /**
     * アカウント作成 入力画面にフォワードするためのメソッド
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
        // セッションを取得する。
        HttpSession session = request.getSession();
        // アクションフォームを生成する。
        FrontCreateAccountActionForm outForm = new FrontCreateAccountActionForm();

        try {
            if(new LoginStatusModel().isLogined(session)) {
                // ログイン済の場合はトップ画面にリダイレクトする。
                return mapping.findForward("logined");
            }

            // アクションフォームに性別マップを格納する。
            outForm.setSexMap(new AccountFormPartsModel().getSexMap());
            // アクションフォームに生年月日(年)リストを格納する。
            outForm.setBirthYearList(new AccountFormPartsModel().getBirthYearListWithEmptyValue());
            // アクションフォームに生年月日(月)リストを格納する。
            outForm.setBirthMonthList(new AccountFormPartsModel().getBirthMonthListWithEmptyValue());
            // アクションフォームに生年月日(日)リストを格納する。
            outForm.setBirthDayList(new AccountFormPartsModel().getBirthDayListWithEmptyValue());
            // アクションフォームに都道府県マップと都道府県リストを格納する。
            outForm.setPrefectureMap(new FormPartsUtil().getPrefectureMap());
            outForm.setPrefectureList(new FormPartsUtil().getPrefectureListWithEmptyValue());

            // トークンを作成する。
            saveToken(request);
            // セッションにアクションフォームを格納する。
            session.setAttribute("FrontCreateAccountActionForm", outForm);
            // 入力画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
