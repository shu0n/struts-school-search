package admin.action.contact;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import admin.actionform.contact.AdminSearchContactActionForm;
import dao.contact.SelectContactJoinDAO;
import dao.contact.sql.ContactSelectJoinWhereActionForm;
import model.login.LoginAdminStatusModel;
import util.DateUtil;
import util.LogUtil;
import util.RedirectAdminUtil;

public class SearchContactAction extends Action {

    /**
     * 検索条件に一致するお問合せを取得して管理画面 お問合せ一覧画面にフォワードするためのメソッド
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
        AdminSearchContactActionForm inForm = (AdminSearchContactActionForm) form;

        try {
            if(!new LoginAdminStatusModel().isAdminLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectAdminUtil().getRedirectAdminLoginAction("/listContact.do");
            }

            // DAOメソッドに引き渡すアクションフォームを生成する。
            ContactSelectJoinWhereActionForm contactSelectJoinWhereForm = new ContactSelectJoinWhereActionForm();

            // お問合せID(文字列型)
            String strContactId = inForm.getStrContactId();
            if(!StringUtils.isEmpty(strContactId)) {
                // 入力されている場合はカンマ区切りでお問合せIDの配列を作成する。
                String[] contactIdArray = strContactId.split(",");
                // アクションフォームにお問合せIDの配列を格納する。
                contactSelectJoinWhereForm.setContactIdArray(contactIdArray);
            }

            // お問合せ者アカウントID(文字列型)
            String strAccountId = inForm.getStrContactAccountId();
            if(!StringUtils.isEmpty(strAccountId)) {
                // 入力されている場合はカンマ区切りでアカウントIDの配列を作成する。
                String[] accountIdArray = strAccountId.split(",");
                // アクションフォームにアカウントIDの配列を格納する。
                contactSelectJoinWhereForm.setContactAccountIdArray(accountIdArray);
            }

            // お問合せ者姓(類似)
            String likelastName = inForm.getLikeContactLastName();
            if(!StringUtils.isEmpty(likelastName)) {
                // 入力されている場合はアクションフォームに追加する。
                contactSelectJoinWhereForm.setLikeContactLastName(likelastName);
            }

            // お問合せ者名(類似)
            String likeFirstName = inForm.getLikeContactFirstName();
            if(!StringUtils.isEmpty(likeFirstName)) {
                // 入力されている場合はアクションフォームに追加する。
                contactSelectJoinWhereForm.setLikeContactFirstName(likeFirstName);
            }

            // お問合せ者姓(フリガナ、類似)
            String likeLastNameKana = inForm.getLikeContactLastNameKana();
            if(!StringUtils.isEmpty(likeLastNameKana)) {
                // 入力されている場合はアクションフォームに追加する。
                contactSelectJoinWhereForm.setLikeContactLastNameKana(likeLastNameKana);
            }

            // お問合せ者名(フリガナ、類似)
            String likeFirstNameKana = inForm.getLikeContactFirstNameKana();
            if(!StringUtils.isEmpty(likeFirstNameKana)) {
                // 入力されている場合はアクションフォームに追加する。
                contactSelectJoinWhereForm.setLikeContactFirstNameKana(likeFirstNameKana);
            }

            // お問合せ者メールアドレス(類似)
            String likeContactMailAddress = inForm.getLikeContactMailAddress();
            if(!StringUtils.isEmpty(likeContactMailAddress)) {
                // 入力されている場合はアクションフォームに追加する。
                contactSelectJoinWhereForm.setLikeContactMailAddress(likeContactMailAddress);
            }

            // お問合せステータスID配列
            String[] contactStatusIdArray = inForm.getContactStatusIdArray();
            if(!StringUtils.isAllEmpty(contactStatusIdArray)) {
                // 選択されている場合はアクションフォームに追加する。
                contactSelectJoinWhereForm.setContactStatusIdArray(contactStatusIdArray);
            }

            // お問合せ日時(From)
            String fromContactedYear = inForm.getFromContactedYear(); // 年(From)
            String fromContactedMonth = inForm.getFromContactedMonth(); // 月(From)
            String fromContactedDay = inForm.getFromContactedDay(); // 日(From)
            if(!StringUtils.isEmpty(fromContactedYear)
                    || !StringUtils.isEmpty(fromContactedMonth)
                    || !StringUtils.isEmpty(fromContactedDay)) {
                // 入力されている場合は「年(From)」「月(From)」「日(From)」を結合してアクションフォームに格納する。
                contactSelectJoinWhereForm.setFromContactedDate(new DateUtil().joinedStrDateToTimestamp(
                        fromContactedYear, fromContactedMonth, fromContactedDay, false));
            }

            // お問合せ日時(To)
            String toContactedYear = inForm.getToContactedYear(); // 年(To)
            String toContactedMonth = inForm.getToContactedMonth(); // 月(To)
            String toContactedDay = inForm.getToContactedDay(); // 日(To)
            if(!StringUtils.isEmpty(toContactedYear)
                    || !StringUtils.isEmpty(toContactedMonth)
                    || !StringUtils.isEmpty(toContactedDay)) {
                // 入力されている場合は「年(From)」「月(From)」「日(From)」を結合して変換してアクションフォームに格納する。
                contactSelectJoinWhereForm.setToContactedDate(new DateUtil().joinedStrDateToTimestamp(
                        toContactedYear, toContactedMonth, toContactedDay, true));
            }

            // お問合せ更新日時(From)
            String fromContactUpdatededYear = inForm.getFromContactUpdatedYear(); // 年(From)
            String fromContactUpdatededMonth = inForm.getFromContactUpdatedMonth(); // 月(From)
            String fromContactUpdatededDay = inForm.getFromContactUpdatedDay(); // 日(From)
            if(!StringUtils.isEmpty(fromContactUpdatededYear)
                    || !StringUtils.isEmpty(fromContactUpdatededMonth)
                    || !StringUtils.isEmpty(fromContactUpdatededDay)) {
                // 入力されている場合は「年(From)」「月(From)」「日(From)」を結合してアクションフォームに格納する。
                contactSelectJoinWhereForm.setFromContactUpdatedDate(new DateUtil().joinedStrDateToTimestamp(
                        fromContactUpdatededYear, fromContactUpdatededMonth, fromContactUpdatededDay, false));
            }

            // お問合せ更新日時(To)
            String toContactUpdatededYear = inForm.getToContactUpdatedYear(); // 年(To)
            String toContactUpdatededMonth = inForm.getToContactUpdatedMonth(); // 月(To)
            String toContactUpdatededDay = inForm.getToContactUpdatedDay(); // 日(To)
            if(!StringUtils.isEmpty(toContactUpdatededYear)
                    || !StringUtils.isEmpty(toContactUpdatededMonth)
                    || !StringUtils.isEmpty(toContactUpdatededDay)) {
                // 入力されている場合は「年(From)」「月(From)」「日(From)」を結合して変換してアクションフォームに格納する。
                contactSelectJoinWhereForm.setToContactUpdatedDate(new DateUtil().joinedStrDateToTimestamp(
                        toContactUpdatededYear, toContactUpdatededMonth, toContactUpdatededDay, true));
            }

            // アクションフォームに検索条件に該当するお問合せのリストを格納する。
            inForm.setContactExtendFormList(
                    new SelectContactJoinDAO().selectMatchedContact(contactSelectJoinWhereForm));

            // お問合せ一覧画面にフォワードする。
            return new ActionForward("/listContact.do");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // リクエストからアクションフォームを削除する。
        request.removeAttribute("AdminSearchContactActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
