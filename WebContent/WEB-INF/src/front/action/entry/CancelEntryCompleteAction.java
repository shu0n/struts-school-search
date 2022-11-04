package front.action.entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import dao.entry.UpdateEntryDAO;
import dao.entry.sql.EntryUpdateDataActionForm;
import dao.school.SelectSchoolDAO;
import dao.school.sql.SchoolSelectWhereActionForm;
import front.actionform.entry.FrontCancelEntryActionForm;
import model.entry.EntryMailModel;
import model.entry.EntryStatusModel;
import util.LogUtil;

public class CancelEntryCompleteAction extends Action {

    /**
     * 申込キャンセル 完了画面にフォワードするためのメソッド
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param request リクエスト
     * @param response レスポンス
     * @return 遷移先画面の指定
     * @throws Exception
     * */
    public ActionForward execute(
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
        FrontCancelEntryActionForm inForm = (FrontCancelEntryActionForm)form;

        try {
            // DAOメソッドに引き渡すアクションフォームを生成して更新するデータを格納する。
            EntryUpdateDataActionForm entryUpdateForm = new EntryUpdateDataActionForm();
            entryUpdateForm.setEntryId(inForm.getEntryId()); // 申込ID
            entryUpdateForm.setEntryStatusId(new EntryStatusModel().getCancelStatusId()); // 申込ステータスID
            // 申込のレコードを更新する。
            new UpdateEntryDAO().updateEntry(entryUpdateForm);

            // 申込キャンセル完了通知メールを送信する。
            new EntryMailModel().sendCancelMailToApplicantAccount(inForm.getApplicantAccountId(), inForm.getEntryId());

            // DAOメソッドに引き渡すアクションフォームを生成してスクールIDを格納する。
            SchoolSelectWhereActionForm schoolSelectWhereForm = new SchoolSelectWhereActionForm();
            schoolSelectWhereForm.setSchoolId(inForm.getEntrySchoolId());
            int registrantAccountId
                    = new SelectSchoolDAO().selectMatchedSchool(schoolSelectWhereForm).get(0).getRegistrantAccountId();
            int registrantAdminId
                    = new SelectSchoolDAO().selectMatchedSchool(schoolSelectWhereForm).get(0).getRegistrantAdminId();
            if(registrantAccountId != 0) {
                // スクール登録者がアカウントの場合はアカウントIDに紐づくメールアドレス宛に申込キャンセル受付通知メールを送信する。
                new EntryMailModel().sendCancelMailToRegistrantAccount(registrantAccountId, inForm.getEntryId());
            } else {
                // 上記以外の場合は管理者IDに紐づくメールアドレス宛に申込キャンセル受付通知メールを送信する。
                new EntryMailModel().sendCancelMailToRegistrantAdmin(registrantAdminId, inForm.getEntryId());
            }

            // セッションからアクションフォームを削除する。
            session.removeAttribute("FrontCancelEntryActionForm");
            // 完了画面にフォワードする。
            return mapping.findForward("success");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // セッションからアクションフォームを削除する。
        session.removeAttribute("FrontCancelEntryActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
