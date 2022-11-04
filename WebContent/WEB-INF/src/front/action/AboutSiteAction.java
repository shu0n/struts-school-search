package front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import front.actionform.FrontAboutSiteActionForm;
import util.LogUtil;
import util.PropertyUtil;

public class AboutSiteAction extends Action {

    /**
     * サイトについて画面にフォワードするためのメソッド
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
        // アクションフォームを取得する。
        FrontAboutSiteActionForm inForm = (FrontAboutSiteActionForm) form;

        try {
            // プロパティ情報からサイトURLを取得してアクションフォームに格納する。
            inForm.setSiteUrl(new PropertyUtil().getProperty("environment.properties").getProperty("site.url"));

            // サイトについて画面にフォワードする。
            return mapping.findForward("success");
        } catch (Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("AboutSiteActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
