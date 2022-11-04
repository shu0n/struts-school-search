package front.action.entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import dao.entry.SelectEntryJoinDAO;
import dao.entry.sql.EntrySelectJoinWhereActionForm;
import front.actionform.entry.FrontSearchReceivedEntryActionForm;
import model.login.LoginStatusModel;
import model.school.SchoolRegistrantModel;
import util.DateUtil;
import util.LogUtil;
import util.RedirectUtil;

public class SearchReceivedEntryAction extends Action {

    /**
     * 検索条件に一致する申込を取得して申込受付一覧画面にフォワードするためのメソッド
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
        FrontSearchReceivedEntryActionForm inForm = (FrontSearchReceivedEntryActionForm) form;

        try {
            if(!new LoginStatusModel().isLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectUtil()
                        .getRedirectLoginAction("/listReceivedEntry.do?schoolId=" + inForm.getEntrySchoolId());
            }

            if(!new SchoolRegistrantModel().isRegistrant(
                    inForm.getEntrySchoolId(), new LoginStatusModel().getAccountId(session))) {
                // スクール登録者以外のアカウントの場合

                // リクエストからアクションフォームを削除する。
                request.removeAttribute("FrontSearchReceivedEntryActionForm");
                // 該当申込なし画面にフォワードする。
                return mapping.findForward("unexistence");
            }

            // DAOメソッドに引き渡すアクションフォームを生成する。
            EntrySelectJoinWhereActionForm entryJoinSelectWhereForm = new EntrySelectJoinWhereActionForm();

            // 申込スクールID
            entryJoinSelectWhereForm.setEntrySchoolId(inForm.getEntrySchoolId());

            // 申込者姓(類似)
            String likeApplicantLastName = inForm.getLikeApplicantLastName();
            if(!StringUtils.isEmpty(likeApplicantLastName)) {
                // 入力されている場合はアクションフォームに追加する。
                entryJoinSelectWhereForm.setLikeApplicantLastName(likeApplicantLastName);
            }

            // 申込者名(類似)
            String likeApplicantFirstName = inForm.getLikeApplicantFirstName();
            if(!StringUtils.isEmpty(likeApplicantFirstName)) {
                // 入力されている場合はアクションフォームに追加する。
                entryJoinSelectWhereForm.setLikeApplicantFirstName(likeApplicantFirstName);
            }

            // 申込者姓(フリガナ、類似)
            String likeApplicantLastNameKana = inForm.getLikeApplicantLastNameKana();
            if(!StringUtils.isEmpty(likeApplicantLastNameKana)) {
                // 入力されている場合はアクションフォームに追加する。
                entryJoinSelectWhereForm.setLikeApplicantLastNameKana(likeApplicantLastNameKana);
            }

            // 申込者名(フリガナ、類似)
            String likeApplicantFirstNameKana = inForm.getLikeApplicantFirstNameKana();
            if(!StringUtils.isEmpty(likeApplicantFirstNameKana)) {
                // 入力されている場合はアクションフォームに追加する。
                entryJoinSelectWhereForm.setLikeApplicantFirstNameKana(likeApplicantFirstNameKana);
            }

            // お問合せステータスID配列
            String[] entryStatusIdArray = inForm.getEntryStatusIdArray();
            if(!StringUtils.isAllEmpty(entryStatusIdArray)) {
                // 選択されている場合はアクションフォームに追加する。
                entryJoinSelectWhereForm.setEntryStatusIdArray(entryStatusIdArray);
            }

            // 申込日時(From)
            String fromEntriedYear = inForm.getFromEntriedYear(); // 年(From)
            String fromEntriedMonth = inForm.getFromEntriedMonth(); // 月(From)
            String fromEntriedDay = inForm.getFromEntriedDay(); // 日(From)
            if(!StringUtils.isEmpty(fromEntriedYear)
                    || !StringUtils.isEmpty(fromEntriedMonth)
                    || !StringUtils.isEmpty(fromEntriedDay)) {
                // 入力されている場合は「年(From)」「月(From)」「日(From)」を結合してアクションフォームに格納する。
                entryJoinSelectWhereForm.setFromEntriedDate(new DateUtil().joinedStrDateToTimestamp(
                        fromEntriedYear, fromEntriedMonth, fromEntriedDay, false));
            }

            // 申込日時(To)
            String toEntriedYear = inForm.getToEntriedYear(); // 年(To)
            String toEntriedMonth = inForm.getToEntriedMonth(); // 月(To)
            String toEntriedDay = inForm.getToEntriedDay(); // 日(To)
            if(!StringUtils.isEmpty(toEntriedYear)
                    || !StringUtils.isEmpty(toEntriedMonth)
                    || !StringUtils.isEmpty(toEntriedDay)) {
                // 入力されている場合は「年(From)」「月(From)」「日(From)」を結合して変換してアクションフォームに格納する。
                entryJoinSelectWhereForm.setToEntriedDate(new DateUtil().joinedStrDateToTimestamp(
                        toEntriedYear, toEntriedMonth, toEntriedDay, true));
            }

            // 申込更新日時(From)
            String fromEntryUpdatedYear = inForm.getFromEntryUpdatedYear(); // 年(From)
            String fromEntryUpdatedMonth = inForm.getFromEntryUpdatedMonth(); // 月(From)
            String fromEntryUpdatedDay = inForm.getFromEntryUpdatedDay(); // 日(From)
            if(!StringUtils.isEmpty(fromEntryUpdatedYear)
                    || !StringUtils.isEmpty(fromEntryUpdatedMonth)
                    || !StringUtils.isEmpty(fromEntryUpdatedDay)) {
                // 入力されている場合は「年(From)」「月(From)」「日(From)」を結合してアクションフォームに格納する。
                entryJoinSelectWhereForm.setFromEntryUpdatedDate(new DateUtil().joinedStrDateToTimestamp(
                        fromEntryUpdatedYear, fromEntryUpdatedMonth, fromEntryUpdatedDay, false));
            }

            // 申込更新日時(To)
            String toEntryUpdatedYear = inForm.getToEntryUpdatedYear(); // 年(To)
            String toEntryUpdatedMonth = inForm.getToEntryUpdatedMonth(); // 月(To)
            String toEntryUpdatedDay = inForm.getToEntryUpdatedDay(); // 日(To)
            if(!StringUtils.isEmpty(toEntryUpdatedYear)
                    || !StringUtils.isEmpty(toEntryUpdatedMonth)
                    || !StringUtils.isEmpty(toEntryUpdatedDay)) {
                // 入力されている場合は「年(From)」「月(From)」「日(From)」を結合して変換してアクションフォームに格納する。
                entryJoinSelectWhereForm.setToEntryUpdatedDate(new DateUtil().joinedStrDateToTimestamp(
                        toEntryUpdatedYear, toEntryUpdatedMonth, toEntryUpdatedDay, true));
            }

            // アクションフォームに検索条件に該当するお問合せのリストを格納する。
            inForm.setEntryExtendFormList(new SelectEntryJoinDAO().selectMatchedEntry(entryJoinSelectWhereForm));

            // 申込受付一覧画面にフォワードする。
            return new ActionForward("/listReceivedEntry.do?schoolId=" + inForm.getEntrySchoolId());
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // リクエストからアクションフォームを削除する。
        request.removeAttribute("FrontSearchReceivedEntryActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
