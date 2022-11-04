package front.action.school;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import dao.category.SelectCategoryDAO;
import dao.school.SelectSchoolJoinDAO;
import dao.school.sql.SchoolSelectJoinWhereActionForm;
import front.actionform.school.FrontSearchSchoolActionForm;
import util.LogUtil;

public class SearchSchoolAction extends Action {

    /**
     * 検索条件に一致するスクールを取得してスクール一覧画面にフォワードするためのメソッド
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
        // アクションフォームを取得する。
        FrontSearchSchoolActionForm inForm = (FrontSearchSchoolActionForm) form;

        try {
            // DAOメソッドに引き渡すアクションフォームを生成する。
            SchoolSelectJoinWhereActionForm schoolSelectJoinWhereForm = new SchoolSelectJoinWhereActionForm();

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
                // NULLおよび空文字以外に場合はアクションフォームに格納する。
                schoolSelectJoinWhereForm.setMaxSchoolFee(maxSchoolFee);
            }

            // スクール公開可否
            schoolSelectJoinWhereForm.setSchoolReleasePropriety("1");

            // アクションフォームに検索条件に該当するスクールのリストを格納する。
            inForm.setSchoolExtendFormList(new SelectSchoolJoinDAO().selectMatchedSchool(schoolSelectJoinWhereForm));

            // スクール一覧画面にフォワードする。
            return new ActionForward("/listSchool.do");
        } catch(Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog(this.getClass().getName(), e);
        }

        // リクエストからアクションフォームを削除する。
        request.removeAttribute("FrontSearchSchoolActionForm");
        // エラー画面にフォワードする。
        return mapping.findForward("error");
    }

}
