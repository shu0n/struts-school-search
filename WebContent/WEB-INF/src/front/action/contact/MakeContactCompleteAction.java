package front.action.contact;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

import dao.contact.InsertContactDAO;
import dao.contact.sql.ContactInsertDataActionForm;
import front.actionform.contact.FrontMakeContactActionForm;
import model.contact.ContactFormPartsModel;
import model.contact.ContactMailModel;
import util.LogUtil;

public class MakeContactCompleteAction extends LookupDispatchAction {
    @Override
    // 押下するボタンに応じてパラメーターを設定するためのメソッド
    protected Map<String,String> getKeyMethodMap() {
        // マップを生成する。
        Map<String,String> map = new HashMap<String,String>();
        // "修正"ボタンを押下した場合のパラメーターをマップに格納する。
        map.put("button.fix", "fix");
        // "送信"ボタンを押下した場合のパラメーターをマップに格納する。
        map.put("button.send", "send");
        // マップを戻す。
        return map;
    }

    /**
     * お問合せ 確認画面で"修正"ボタンを押下した場合に入力画面にフォワードするためのメソッド
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

        try {
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
        session.removeAttribute("FrontMakeContactActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

    /**
     * お問合せ 確認画面で"送信"ボタンを押下した場合に完了画面にフォワードするためのメソッド
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request リクエスト
     * @param response レスポンス
     * @return 遷移先画面の指定
     * @throws Exception
     * */
    public ActionForward send(
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
        FrontMakeContactActionForm inForm = (FrontMakeContactActionForm)form;

        try {
            // DAOメソッドに引き渡すアクションフォームを生成して登録するデータを格納する。
            ContactInsertDataActionForm contactInsertDataForm = new ContactInsertDataActionForm();
            contactInsertDataForm.setContactAccountId(inForm.getContactAccountId()); // お問合せ者アカウントID
            contactInsertDataForm.setContactLastName(inForm.getContactLastName()); // お問合せ者姓
            contactInsertDataForm.setContactFirstName(inForm.getContactFirstName()); // お問合せ者名
            contactInsertDataForm.setContactLastNameKana(inForm.getContactLastNameKana()); // お問合せ者姓(フリガナ)
            contactInsertDataForm.setContactFirstNameKana(inForm.getContactFirstNameKana()); // お問合せ者名(フリガナ)
            contactInsertDataForm.setContactMailAddress(inForm.getContactMailAddress()); // お問合せ者メールアドレス
            contactInsertDataForm.setContactContent(inForm.getContactContent()); // お問合せ内容
            // お問合せステータスマップを取得する。
            Map<Integer, String> contactStatusMap = new ContactFormPartsModel().getContactStatusMap();
            // 取得したマップからお問合せステータス名が"受付済"のお問合せステータスIDを取得してアクションフォームに格納する。
            for(Map.Entry<Integer, String> entry : contactStatusMap.entrySet()) {
                if("受付済".equals(entry.getValue())) {
                    contactInsertDataForm.setContactStatusId(entry.getKey());
                    break;
                }
            }
            // お問合せのレコードを作成する。
            int contactId = new InsertContactDAO().insertContact(contactInsertDataForm);

            // お問合せアカウントのメールアドレス宛にお問合せ受付通知メールを送信する。
            new ContactMailModel().sendContactMailToInquirer(inForm.getContactMailAddress());
            // 管理者メールアドレス宛にお問合せ受付通知メールを送信する。
            new ContactMailModel().sendContactMailToAdmin(contactId);

            // セッションからアクションフォームを削除する。
            session.removeAttribute("FrontMakeContactActionForm");
            // 完了画面にフォワードする。
            return mapping.findForward("send");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontMakeContactActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
