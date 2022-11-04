package front.action.entry;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

import dao.entry.InsertEntryDAO;
import dao.entry.sql.EntryInsertDataActionForm;
import front.actionform.entry.FrontMakeEntryActionForm;
import model.entry.EntryMailModel;
import model.entry.EntryStatusModel;
import util.LogUtil;

public class MakeEntryCompleteAction extends LookupDispatchAction {
    @Override
    // 押下するボタンに応じてパラメーターを設定するためのメソッド
    protected Map<String,String> getKeyMethodMap() {
        // マップを生成する。
        Map<String,String> map = new HashMap<String,String>();
        // "修正"ボタンを押下した場合のパラメーターをマップに格納する。
        map.put("button.fix", "fix");
        // "申込"ボタンを押下した場合のパラメーターをマップに格納する。
        map.put("button.make", "make");
        // マップを戻す。
        return map;
    }

    /**
     * 申込 確認画面で"修正"ボタンを押下した場合に入力画面にフォワードするためのメソッド
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

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontMakeEntryActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

    /**
     * 申込 確認画面で"申込"ボタンを押下した場合に完了画面にフォワードするためのメソッド
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request リクエスト
     * @param response レスポンス
     * @return 遷移先画面の指定
     * @throws Exception
     * */
    public ActionForward make(
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
        FrontMakeEntryActionForm inForm = (FrontMakeEntryActionForm) form;

        try {
            // DAOメソッドに引き渡すアクションフォームを生成して登録するデータを格納する。
            EntryInsertDataActionForm entryInsertDataForm = new EntryInsertDataActionForm();
            entryInsertDataForm.setEntrySchoolId(inForm.getEntrySchoolId()); // 申込先スクールID
            entryInsertDataForm.setApplicantAccountId(inForm.getApplicantAccountId()); // 申込者アカウントID
            entryInsertDataForm.setEntryQuestion(inForm.getEntryQuestion()); // 質問
            entryInsertDataForm.setEntryStatusId(new EntryStatusModel().getEtriedStatusId()); // 申込ステータスID
            // 申込のレコードを作成する。
            int entryId = new InsertEntryDAO().insertEntry(entryInsertDataForm);

            // 申込完了通知メールを送信する。
            new EntryMailModel().sendEntryMailToApplicantAccount(inForm.getApplicantAccountId(), entryId);

            if(inForm.getRegistrantAccountId() != 0) {
                // 登録者がアカウントの場合はアカウントIDに紐づくメールアドレス宛に申込受け通知メールを送信する。
                new EntryMailModel().sendEntryMailToRegistrantAccount(inForm.getRegistrantAccountId(), entryId);
            } else {
                // 上記以外の場合は管理者IDに紐づくメールアドレス宛に申込受け通知メールを送信する。
                new EntryMailModel().sendEntryMailToRegistrantAdmin(inForm.getRegistrantAdminId(), entryId);
            }

            // セッションからアクションフォームを削除する。
            session.removeAttribute("FrontMakeEntryActionForm");
            // 完了画面にフォワードする。
            return mapping.findForward("make");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontMakeEntryActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
