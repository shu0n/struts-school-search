package admin.action.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import admin.actionform.account.AdminSearchAccountActionForm;
import dao.account.SelectAccountJoinDAO;
import dao.account.sql.AccountSelectJoinWhereActionForm;
import model.login.LoginAdminStatusModel;
import util.DateUtil;
import util.LogUtil;
import util.RedirectAdminUtil;

public class SearchAccountAction extends Action {

    /**
     * 検索条件に一致するアカウントを取得して管理画面 アカウント一覧画面にフォワードするためのメソッド
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
        AdminSearchAccountActionForm inForm = (AdminSearchAccountActionForm) form;

        try {
            // ログイン状態を判定する。
            if(!new LoginAdminStatusModel().isAdminLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectAdminUtil().getRedirectAdminLoginAction("/listAccount.do");
            }

            // DAOメソッドに引き渡すアクションフォームを生成する。
            AccountSelectJoinWhereActionForm accountJoinSelectForm = new AccountSelectJoinWhereActionForm();

            // アカウントID(文字列型)
            String strAccountId = inForm.getStrAccountId();
            if(!StringUtils.isEmpty(strAccountId)) {
                // 入力されている場合はカンマ区切りでアカウントIDの配列を作成する。
                String[] accountIdArray = strAccountId.split(",");
                // アクションフォームにアカウントIDの配列を格納する。
                accountJoinSelectForm.setAccountIdArray(accountIdArray);
            }

            // 姓(類似)
            String likeLastName = inForm.getLikeLastName();
            if(!StringUtils.isEmpty(likeLastName)) {
                // 入力されている場合ははアクションフォームに格納する。
                accountJoinSelectForm.setLikeLastName(likeLastName);
            }

            // 名(類似)
            String likeFirstName = inForm.getLikeFirstName();
            if(!StringUtils.isEmpty(likeFirstName)) {
                // 入力されている場合ははアクションフォームに格納する。
                accountJoinSelectForm.setLikeFirstName(likeFirstName);
            }

            // 姓(フリガナ、類似)
            String likeLastNameKana = inForm.getLikeLastNameKana();
            if(!StringUtils.isEmpty(likeLastNameKana)) {
                // 入力されている場合ははアクションフォームに格納する。
                accountJoinSelectForm.setLikeFirstNameKana(likeLastNameKana);
            }

            // 名(フリガナ、類似)
            String likeFirstNameKana = inForm.getLikeFirstNameKana();
            if(!StringUtils.isEmpty(likeFirstNameKana)) {
                // 入力されている場合ははアクションフォームに追加する。
                accountJoinSelectForm.setLikeFirstNameKana(likeFirstNameKana);
            }

            // 生年月日(From)
            String fromBirthYear = inForm.getFromBirthYear(); // 年(From)
            String fromBirthMonth = inForm.getFromBirthMonth(); // 月(From)
            String fromBirthDay = inForm.getFromBirthDay(); // 日(From)
            if(!StringUtils.isEmpty(fromBirthYear)
                    || !StringUtils.isEmpty(fromBirthMonth)
                    || !StringUtils.isEmpty(fromBirthDay)) {
                // 入力されている場合は「年(From)」「月(From)」「日(From)」を結合して日時型に変換してアクションフォームに格納する。
                accountJoinSelectForm.setFromBirthDate(
                        new DateUtil().joinedStrDateToTimestamp(fromBirthYear, fromBirthMonth, fromBirthDay, false));
            }

            // 生年月日(To)
            String toBirthYear = inForm.getToBirthYear(); // 年(To)
            String toBirthMonth = inForm.getToBirthMonth(); // 月(To)
            String toBirthDay = inForm.getToBirthDay(); // 日(To)
            if(!StringUtils.isEmpty(toBirthYear)
                    || !StringUtils.isEmpty(toBirthMonth)
                    || !StringUtils.isEmpty(toBirthDay)) {
                // 入力されている場合は「年(From)」「月(From)」「日(From)」を結合して日時型に変換してアクションフォームに格納する。
                accountJoinSelectForm.setToBirthDate(
                        new DateUtil().joinedStrDateToTimestamp(toBirthYear, toBirthMonth, toBirthDay, true));
            }

            // 性別ID配列
            String[] sexIdArray = inForm.getSexIdArray();
            if(!StringUtils.isAllEmpty(sexIdArray)) {
                // 選択されている場合はアクションフォームに追加する。
                accountJoinSelectForm.setSexIdArray(sexIdArray);
            }

            // 都道府県ID配列
            String[] prefectureIdArray = inForm.getPrefectureIdArray();
            if(!StringUtils.isAllEmpty(prefectureIdArray)) {
                // 選択されている場合はアクションフォームに追加する。
                accountJoinSelectForm.setPrefectureIdArray(prefectureIdArray);
            }

            // メールアドレス(類似)
            String likeMailAddress = inForm.getLikeMailAddress();
            if(!StringUtils.isEmpty(likeMailAddress)) {
                // 入力されている場合ははアクションフォームに追加する。
                accountJoinSelectForm.setLikeMailAddress(likeMailAddress);
            }

            // アカウントステータス配列
            String[] accountStatusArray = inForm.getAccountStatusArray();
            if(!StringUtils.isAllBlank(accountStatusArray)) {
                // 選択されている場合はアクションフォームに追加する。
                accountJoinSelectForm.setAccountStatusArray(accountStatusArray);
            }

            // アカウント作成日時(From)
            String fromAccountCreatedYear = inForm.getFromAccountCreatedYear(); // 年(From)
            String fromAccountCreatedMonth = inForm.getFromAccountCreatedMonth(); // 月(From)
            String fromAccountCreatedDay = inForm.getFromAccountCreatedDay(); // 日(From)
            if(!StringUtils.isEmpty(fromAccountCreatedYear)
                    || !StringUtils.isEmpty(fromAccountCreatedMonth)
                    || !StringUtils.isEmpty(fromAccountCreatedDay)) {
                // 入力されている場合は「年(From)」「月(From)」「日(From)」を結合して日時型に変換してアクションフォームに格納する。
                accountJoinSelectForm.setFromAccountCreatedDate(new DateUtil().joinedStrDateToTimestamp(
                        fromAccountCreatedYear, fromAccountCreatedMonth, fromAccountCreatedDay, false));
            }

            // アカウント作成日時(To)
            String toAccountCreatedYear = inForm.getToAccountCreatedYear(); // 年(To)
            String toAccountCreatedMonth = inForm.getToAccountCreatedMonth(); // 月(To)
            String toAccountCreatedDay = inForm.getToAccountCreatedDay(); // 日(To)
            if(!StringUtils.isEmpty(toAccountCreatedYear)
                    || !StringUtils.isEmpty(toAccountCreatedMonth)
                    || !StringUtils.isEmpty(toAccountCreatedDay)) {
                // 入力されている場合は「年(From)」「月(From)」「日(From)」を結合して日時型に変換してアクションフォームに格納する。
                accountJoinSelectForm.setToAccountCreatedDate(new DateUtil().joinedStrDateToTimestamp(
                        toAccountCreatedYear, toAccountCreatedMonth, toAccountCreatedDay, true));
            }

            // アカウント更新日時(From)
            String fromAccountUpdatedYear = inForm.getFromAccountUpdatedYear(); // 年(From)
            String fromAccountUpdatedMonth = inForm.getFromAccountUpdatedMonth(); // 月(From)
            String fromAccountUpdatedDay = inForm.getFromAccountUpdatedDay(); // 日(From)
            if(!StringUtils.isEmpty(fromAccountUpdatedYear)
                    || !StringUtils.isEmpty(fromAccountUpdatedMonth)
                    || !StringUtils.isEmpty(fromAccountUpdatedDay)) {
                // 入力されている場合は「年(From)」「月(From)」「日(From)」を結合して日時型に変換してアクションフォームに格納する。
                accountJoinSelectForm.setFromAccountUpdatedDate(new DateUtil().joinedStrDateToTimestamp(
                        fromAccountUpdatedYear, fromAccountUpdatedMonth, fromAccountUpdatedDay, false));
            }

            // アカウント更新日時(To)
            String toAccountUpdatedYear = inForm.getToAccountUpdatedYear(); // 年(To)
            String toAccountUpdatedMonth = inForm.getToAccountUpdatedMonth(); // 月(To)
            String toAccountUpdatedDay = inForm.getToAccountUpdatedDay(); // 日(To)
            if(!StringUtils.isEmpty(toAccountUpdatedYear)
                    || !StringUtils.isEmpty(toAccountUpdatedMonth)
                    || !StringUtils.isEmpty(toAccountUpdatedDay)) {
                // 入力されている場合は「年(From)」「月(From)」「日(From)」を結合して日時型に変換してアクションフォームに格納する。
                accountJoinSelectForm.setToAccountUpdatedDate(new DateUtil().joinedStrDateToTimestamp(
                        toAccountUpdatedYear, toAccountUpdatedMonth, toAccountUpdatedDay, true));
            }

            // アクションフォームに検索条件に該当するスクールのリストを格納する。
            inForm.setAccountExtendFormList(new SelectAccountJoinDAO().selectMatchedAccount(accountJoinSelectForm));

            // アカウント一覧画面にフォワードする。
            return new ActionForward("/listAccount.do");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // リクエストからアクションフォームを削除する。
        request.removeAttribute("AdminSearchAccountActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
