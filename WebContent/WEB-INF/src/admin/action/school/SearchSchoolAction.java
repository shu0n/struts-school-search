package admin.action.school;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import admin.actionform.school.AdminSearchSchoolActionForm;
import dao.category.SelectCategoryDAO;
import dao.school.SelectSchoolJoinDAO;
import dao.school.sql.SchoolSelectJoinWhereActionForm;
import model.login.LoginAdminStatusModel;
import util.DateUtil;
import util.LogUtil;
import util.RedirectAdminUtil;

public class SearchSchoolAction extends Action {

    /**
     * 検索条件に一致するスクールを取得して管理画面 スクール一覧画面にフォワードするためのメソッド
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
        AdminSearchSchoolActionForm inForm = (AdminSearchSchoolActionForm) form;

        try {
            if(!new LoginAdminStatusModel().isAdminLogined(session)) {
                // 未ログインの場合はログイン画面にリダイレクトする。
                return new RedirectAdminUtil().getRedirectAdminLoginAction("/listSchool.do");
            }

            // DAOメソッドに引き渡すアクションフォームを生成する。
            SchoolSelectJoinWhereActionForm schoolSelectJoinWhereForm = new SchoolSelectJoinWhereActionForm();

            // スクールID(文字列型)
            String strSchoolId = inForm.getStrSchoolId();
            if(!StringUtils.isEmpty(strSchoolId)) {
                // 入力されている場合はカンマ区切りでスクールIDの配列を作成する。
                String[] schoolIdArray = strSchoolId.split(",");
                // アクションフォームにスクールIDの配列を格納する。
                schoolSelectJoinWhereForm.setSchoolIdArray(schoolIdArray);
            }

            // 登録者アカウントID(文字列型)
            String strRegistrantAccountId = inForm.getStrRegistrantAccountId();
            if(!StringUtils.isEmpty(strRegistrantAccountId)) {
                // 入力されている場合はカンマ区切りでアカウントIDの配列を作成する。
                String[] registrantAccountIdArray = strRegistrantAccountId.split(",");
                // アクションフォームにアカウントIDの配列を格納する。
                schoolSelectJoinWhereForm.setRegistrantAccountIdArray(registrantAccountIdArray);
            }

            // 登録者管理者ID(文字列型)
            String strRegistrantAdminId = inForm.getStrRegistrantAdminId();
            if(!StringUtils.isEmpty(strRegistrantAdminId)) {
                // 入力されている場合はカンマ区切りで管理者IDの配列を作成する。
                String[] registrantAdminIdArray = strRegistrantAdminId.split(",");
                // アクションフォームに管理者IDの配列を格納する。
                schoolSelectJoinWhereForm.setRegistrantAdminIdArray(registrantAdminIdArray);
            }

            // スクール名(類似)
            String likeSchoolName = inForm.getLikeSchoolName();
            if(!StringUtils.isEmpty(likeSchoolName)) {
                // 入力されている場合はアクションフォームに格納する。
                schoolSelectJoinWhereForm.setLikeSchoolName(likeSchoolName);
            }

            // スクールカテゴリーID配列
            String[] schoolCategoryIdArray = inForm.getSchoolCategoryIdArray();
            if(!StringUtils.isAllEmpty(schoolCategoryIdArray)) {
                // 選択されている場合

                // 下位カテゴリーIDを格納するリストを生成する。
                List<String> descendantCategoryIdList = new ArrayList<>();
                for(String categoryId: schoolCategoryIdArray) {
                    // 配列に格納されたカテゴリーIDの下位カテゴリーIDを格納したリストを取得する。
                    List<String[]> descendantCategoryList
                            = new SelectCategoryDAO().selectDescendantCategory(Integer.parseInt(categoryId));
                    for(String[] descendantCategoryArray: descendantCategoryList) {
                        // リストに取得したリストに格納されているカテゴリーIDを格納する。
                        descendantCategoryIdList.add(String.valueOf(descendantCategoryArray[0]));
                    }
                    // リストにカテゴリーID自体を格納する。
                    descendantCategoryIdList.add(categoryId);
                }
                // カテゴリーIDと下位カテゴリーIDを格納したリストを配列に変換する。
                String[] schoolCategoryIdListWithdescendantCategoryIdArray
                        = descendantCategoryIdList.toArray(new String[descendantCategoryIdList.size()]);
                // アクションフォームに変換した配列を格納する。
                schoolSelectJoinWhereForm.setSchoolCategoryIdArray(schoolCategoryIdListWithdescendantCategoryIdArray);
            }

            // スクール都道府県ID配列
            String[] schoolPrefectureIdArray = inForm.getSchoolPrefectureIdArray();
            if(!StringUtils.isAllEmpty(schoolPrefectureIdArray)) {
                // 選択されている場合はアクションフォームに格納する。
                schoolSelectJoinWhereForm.setSchoolPrefectureIdArray(schoolPrefectureIdArray);
            }

            // 費用(下限)
            String minSchoolFee = inForm.getMinSchoolFee();
            if(!StringUtils.isEmpty(minSchoolFee)) {
                // 入力されている場合はアクションフォームに格納する。
                schoolSelectJoinWhereForm.setMinSchoolFee(minSchoolFee);
            }

            // 費用(上限)
            String maxSchoolFee = inForm.getMaxSchoolFee();
            if(!StringUtils.isEmpty(maxSchoolFee)) {
                // 入力されている場合はアクションフォームに格納する。
                schoolSelectJoinWhereForm.setMaxSchoolFee(maxSchoolFee);
            }

            // 公開可否ID配列
            String[] schoolReleaseProprietyArray = inForm.getSchoolReleaseProprietyArray();
            if(!StringUtils.isAllEmpty(schoolReleaseProprietyArray)) {
                // 選択されている場合はアクションフォームに格納する。
                schoolSelectJoinWhereForm.setSchoolReleaseProprietyArray(schoolReleaseProprietyArray);
            }

            // 申込可否ID配列
            String[] schoolEntryProprietyArray = inForm.getSchoolEntryProprietyArray();
            if(!StringUtils.isAllEmpty(schoolEntryProprietyArray)) {
                // 選択されている場合はアクションフォームに格納する。
                schoolSelectJoinWhereForm.setSchoolEntryProprietyArray(schoolEntryProprietyArray);
            }

            // スクール登録日時(From)
            String fromSchoolRegisteredYear = inForm.getFromSchoolRegisteredYear(); // 年(From)
            String fromSchoolRegisteredMonth = inForm.getFromSchoolRegisteredMonth(); // 月(From)
            String fromSchoolRegisteredDay = inForm.getFromSchoolRegisteredDay(); // 日(From)
            if(!StringUtils.isEmpty(fromSchoolRegisteredYear)
                    || !StringUtils.isEmpty(fromSchoolRegisteredMonth)
                    || !StringUtils.isEmpty(fromSchoolRegisteredDay)) {
                // 入力されている場合は「年(From)」「月(From)」「日(From)」を結合してアクションフォームに格納する。
                schoolSelectJoinWhereForm.setFromSchoolRegisteredDate(new DateUtil().joinedStrDateToTimestamp(
                        fromSchoolRegisteredYear, fromSchoolRegisteredMonth, fromSchoolRegisteredDay, false));
            }

            // スクール登録日時(To)
            String toSchoolRegisteredYear = inForm.getToSchoolRegisteredYear(); // 年(To)
            String toSchoolRegisteredMonth = inForm.getToSchoolRegisteredMonth(); // 月(To)
            String toSchoolRegisteredDay = inForm.getToSchoolRegisteredDay(); // 日(To)
            if(!StringUtils.isEmpty(toSchoolRegisteredYear)
                    || !StringUtils.isEmpty(toSchoolRegisteredMonth)
                    || !StringUtils.isEmpty(toSchoolRegisteredDay)) {
                // 入力されている場合は「年(From)」「月(From)」「日(From)」を結合して変換してアクションフォームに格納する。
                schoolSelectJoinWhereForm.setToSchoolRegisteredDate(new DateUtil().joinedStrDateToTimestamp(
                        toSchoolRegisteredYear, toSchoolRegisteredMonth, toSchoolRegisteredDay, true));
            }

            // スクール更新日時(From)
            String fromSchoolUpdatedYear = inForm.getFromSchoolUpdatedYear(); // 年(From)
            String fromSchoolUpdatedMonth = inForm.getFromSchoolUpdatedMonth(); // 月(From)
            String fromSchoolUpdatedDay = inForm.getFromSchoolUpdatedDay(); // 日(From)
            if(!StringUtils.isEmpty(fromSchoolUpdatedYear)
                    || !StringUtils.isEmpty(fromSchoolUpdatedMonth)
                    || !StringUtils.isEmpty(fromSchoolUpdatedDay)) {
                // 入力されている場合は「年(From)」「月(From)」「日(From)」を結合してアクションフォームに格納する。
                schoolSelectJoinWhereForm.setFromSchoolUpdatedDate(new DateUtil().joinedStrDateToTimestamp(
                        fromSchoolUpdatedYear, fromSchoolUpdatedMonth, fromSchoolUpdatedDay, false));
            }

            // スクール更新日時(To)
            String toSchoolUpdatedYear = inForm.getToSchoolUpdatedYear(); // 年(To)
            String toSchoolUpdatedMonth = inForm.getToSchoolUpdatedMonth(); // 月(To)
            String toSchoolUpdatedDay = inForm.getToSchoolUpdatedDay(); // 日(To)
            if(!StringUtils.isEmpty(toSchoolUpdatedYear)
                    || !StringUtils.isEmpty(toSchoolUpdatedMonth)
                    || !StringUtils.isEmpty(toSchoolUpdatedDay)) {
                // 入力されている場合は「年(From)」「月(From)」「日(From)」を結合して日付型に変換してアクションフォームに格納する。
                schoolSelectJoinWhereForm.setToSchoolUpdatedDate(new DateUtil().joinedStrDateToTimestamp(
                        toSchoolUpdatedYear, toSchoolUpdatedMonth, toSchoolUpdatedDay, true));
            }

            // アクションフォームに検索条件に該当するスクールのリストを格納する。
            inForm.setSchoolExtendFormList(new SelectSchoolJoinDAO().selectMatchedSchool(schoolSelectJoinWhereForm));

            // スクール一覧画面にフォワードする。
            return new ActionForward("/listSchool.do");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // リクエストからアクションフォームを削除する。
        request.removeAttribute("AdminSearchSchoolActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
