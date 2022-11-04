<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="admin.actionform.school.AdminListSchoolActionForm"%>
<%@ page import="actionform.SchoolExtendActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
AdminListSchoolActionForm inForm = (AdminListSchoolActionForm) session.getAttribute("AdminListSchoolActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="スクール一覧"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<script src="<%=request.getContextPath()%>/admin/js/bootstrap-multiselect.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/enable-multiselect.js"></script>
<section class="container-fluid pt-4" style="width: 90%;">
    <div class="card">
        <div class="card-header" role="tab" id="collapseListGroupHeading1">
            <h5 class="mb-0 text-center">
                <a href="#collapseListGroup1" class="collapsed text-body d-block p-3 m-n3" role="button" data-toggle="collapse" aria-expanded="false" aria-controls="collapseListGroup1">
                    スクールを検索する
                </a>
            </h5>
        </div>
        <div class="collapse" role="tabpanel" id="collapseListGroup1" aria-labelledby="collapseListGroupHeading1" aria-expanded="false">
            <div class="card-body">
                <html:form action="/searchSchool" onsubmit="return validateBeforeSubmitSearchSchool(this)">
                    <div class="form-group row">
                        <label for="strSchoolId" class="col-md-3 control-label">スクールID</label>
                        <div class="col-md-9">
                            <html:text property="strSchoolId" styleClass="form-control" styleId="strSchoolId" onblur="validateAccountIds('strSchoolId', 50)"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="strRegistrantAccountId" class="col-md-3 control-label">登録者アカウントID</label>
                        <div class="col-md-9">
                            <html:text property="strRegistrantAccountId" styleClass="form-control" styleId="strRegistrantAccountId" onblur="validateAccountIds('strRegistrantAccountId', 50)"/>
                            <div class="invalid-feedback">ID,ID,...,IDの形式で入力していください。※IDとカンマは半角数字</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="strRegistrantAdminId" class="col-md-3 control-label">登録者管理者ID</label>
                        <div class="col-md-9">
                            <html:text property="strRegistrantAdminId" styleClass="form-control" styleId="strRegistrantAdminId" onblur="validateAccountIds('strRegistrantAdminId', 50)"/>
                            <div class="invalid-feedback">ID,ID,...,IDの形式で入力していください。※IDとカンマは半角数字</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="schoolName" class="col-md-3 control-label">スクール名</label>
                        <div class="col-md-9">
                            <html:text property="likeSchoolName" styleClass="form-control" styleId="schoolName" onblur="validateMaxLength('schoolName', 50)"/>
                            <div class="invalid-feedback">50文字以内で入力してください。</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="minSchoolFee" class="col-md-3 control-label">費用</label>
                        <div class="col-md-4 input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">下限</span>
                            </div>
                            <html:text property="minSchoolFee" styleClass="form-control" styleId="minSchoolFee" onblur="validateSearchSchoolFee('minSchoolFee', 8)"/>
                            <div class="input-group-append">
                                <span class="input-group-text">円</span>
                            </div>
                            <div class="invalid-feedback">8文字以内の半角数字で入力してください。</div>
                        </div>
                        <div class="col-md-4 input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">上限</span>
                            </div>
                            <html:text property="maxSchoolFee" styleClass="form-control" styleId="maxSchoolFee" onblur="validateSearchSchoolFee('maxSchoolFee', 8)"/>
                            <div class="input-group-append">
                                <span class="input-group-text">円</span>
                            </div>
                            <div class="invalid-feedback">8文字以内の半角数字で入力してください。</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="selectYear1" class="col-md-3 col-form-label">スクール登録日時(From)</label>
                        <div class="col-md-3 input-group">
                            <html:select property="fromSchoolRegisteredYear" styleClass="custom-select" styleId="selectYear1"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectYear1">年</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="fromSchoolRegisteredMonth" styleClass="custom-select" styleId="selectMonth1"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectMonth1">月</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="fromSchoolRegisteredDay" styleClass="custom-select" styleId="selectDay1"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectDay1">日</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="selectYear2" class="col-md-3 col-form-label">スクール登録日時(To)</label>
                        <div class="col-md-3 input-group">
                            <html:select property="toSchoolRegisteredYear" styleClass="custom-select" styleId="selectYear2"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectYear2">年</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="toSchoolRegisteredMonth" styleClass="custom-select" styleId="selectMonth2"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectMonth2">月</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="toSchoolRegisteredDay" styleClass="custom-select" styleId="selectDay2"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectDay2">日</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="selectYear3" class="col-md-3 col-form-label">スクール更新日時(From)</label>
                        <div class="col-md-3 input-group">
                            <html:select property="fromSchoolUpdatedYear" styleClass="custom-select" styleId="selectYear3"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectYear3">年</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="fromSchoolUpdatedMonth" styleClass="custom-select" styleId="selectMonth3"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectMonth3">月</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="fromSchoolUpdatedDay" styleClass="custom-select" styleId="selectDay3"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectDay3">日</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="selectYear4" class="col-md-3 col-form-label">スクール更新日時(From)</label>
                        <div class="col-md-3 input-group">
                            <html:select property="toSchoolUpdatedYear" styleClass="custom-select" styleId="selectYear4"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectYear4">年</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="toSchoolUpdatedMonth" styleClass="custom-select" styleId="selectMonth4"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectMonth4">月</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="toSchoolUpdatedDay" styleClass="custom-select" styleId="selectDay4"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectDay4">日</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="schoolCategory" class="col-md-3 control-label">カテゴリー</label>
                        <div class="col-md-3">
                            <html:select property="schoolCategoryIdArray" styleClass="form-control multiselect" styleId="schoolCategory" multiple="true" onchange="controlActivateOrInactivateForOption('schoolCategory')">
                                <html:optionsCollection name="AdminListSchoolActionForm" property="schoolCategoryList" label="label" value="value"/>
                            </html:select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="schoolPrefecture" class="col-md-3 control-label">都道府県</label>
                        <div class="col-md-3">
                            <html:select property="schoolPrefectureIdArray" styleClass="form-control multiselect" styleId="schoolPrefecture" multiple="true">
                                <html:optionsCollection name="AdminListSchoolActionForm" property="schoolPrefectureList" label="label" value="value"/>
                            </html:select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="schoolReleasePropriety" class="col-md-3 control-label">公開可否</label>
                        <div class="col-md-3">
                            <html:select property="schoolReleaseProprietyArray" styleClass="form-control multiselect" styleId="schoolReleasePropriety" multiple="true">
                                <html:optionsCollection name="AdminListSchoolActionForm" property="schoolReleaseProprietyList" label="label" value="value"/>
                            </html:select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="schoolEntryPropriety" class="col-md-3 control-label">申込可否</label>
                        <div class="col-md-3">
                            <html:select property="schoolEntryProprietyArray" styleClass="form-control multiselect" styleId="schoolEntryPropriety" multiple="true">
                                <html:optionsCollection name="AdminListSchoolActionForm" property="schoolEntryProprietyList" label="label" value="value"/>
                            </html:select>
                        </div>
                    </div>
                    <div class="form-group row justify-content-end">
                        <div class="col-md-9">
                            <html:submit value="検索" styleClass="btn btn-primary"/>
                        </div>
                    </div>
                </html:form>
            </div>
        </div>
    </div>
</section>
<section class="container-fluid pt-4" style="width: 90%;">
    <div class="card">
        <div class="card-header" role="tab" id="collapseListGroupHeading2">
            <h5 class="mb-0 text-center">
                <a href="#collapseListGroup2" class="collapsed text-body d-block p-3 m-n3" role="button" data-toggle="collapse" aria-expanded="false" aria-controls="collapseListGroup2">
                    スクールを一括で登録・更新
                </a>
            </h5>
        </div>
        <div class="collapse" role="tabpanel" id="collapseListGroup2" aria-labelledby="collapseListGroupHeading2" aria-expanded="false">
            <div class="card-body">
                <html:form action="/uploadSchoolCsv" enctype="multipart/form-data" onsubmit="return validateBeforeSubmitSchoolCsv()">
                    <div class="form-group row">
                        <div class="col-md-12 input-group">
                            <label class="input-group-btn">
                                <span class="btn btn-primary">
                                    参照<input type="file" name="schoolCsv" accept=".csv" class="form-control" id="uploadSchoolCsv" style="display:none"/>
                                </span>
                            </label>
                            <input type="text" class="form-control" readonly="">
                        </div>
                        <div class="col-md-12 invalid-feedback">アップロードするCSVファイルを選択してください。</div>
                    </div>
                    <div class="form-group row justify-content-end">
                        <div class="col-md-12">
                            <html:submit value="アップロード" styleClass="btn btn-primary"/>
                        </div>
                    </div>
                </html:form>
                <html:button property="csvDownloadBtn" value="テンプレートCSVダウンロード" styleClass="btn btn-secondary btn-sm mt-1" onclick="return !isDoubleClick('./downloadSchoolCsv.do?template=true')"/>
            </div>
        </div>
    </div>
</section>
<section class="container-fluid pt-4" style="width: 90%;">
    <html:button property="registerBtn" value="新規スクール登録" styleClass="btn btn-primary btn-sm mt-1" onclick="return !isDoubleClick('./registerSchoolInput.do')"/>
    <html:button property="csvDownloadBtn" value="CSVダウンロード" styleClass="btn btn-secondary btn-sm mt-1" onclick="return !isDoubleClick('./downloadSchoolCsv.do')"/>
    <logic:messagesPresent>
        <div class="alert alert-danger alert-dismissible fade show text-left" role="alert">
            <html:messages id="msg">
                <bean:write name="msg" ignore="true"/>
            </html:messages>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </logic:messagesPresent>
</section>
<section class="container-fluid pt-4" style="width: 90%;">
    <p><%=inForm.getTotalForm() %>件のスクールがヒットしました。</p>
    <%
    // 該当するスクールの件数を判定する。
    if(inForm.getTotalForm() > 0) {
        // 1件以上存在する場合
    %>
        <html:form action="/listSchool" styleClass="form-inline pb-3" style="flex-wrap: nowrap;">
            <html:select property="sortKindForSchool" styleClass="custom-select">
                <html:optionsCollection property="sortKindForSchoolList" label="label" value="value"/>
            </html:select>
            <html:submit value="並び替える" styleClass="btn btn-primary"/>
            <html:hidden property="sortFlag" value="true"/>
        </html:form>
        <jsp:include page="../common/pagination.jsp">
            <jsp:param name="totalPage" value="<%=inForm.getTotalPage() %>"/>
            <jsp:param name="currentPage" value="<%=inForm.getCurrentPage() %>"/>
            <jsp:param name="listUrl" value="./listAccount.do"/>
        </jsp:include>
        <div class="table-responsive-md">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col" class="text-nowrap text-center">ID</th>
                        <th scope="col" class="text-nowrap text-center">登録者</th>
                        <th scope="col" class="text-nowrap text-center">スクール名</th>
                        <th scope="col" class="text-nowrap text-center">カテゴリー</th>
                        <th scope="col" class="text-nowrap text-center">登録日時</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                <%
                // リストの要素分、処理を繰り返す。
                for(SchoolExtendActionForm eachForm: inForm.getDisplayedSchoolList()) {
                %>
                    <tr>
                        <td class="align-middle text-center"><%=eachForm.getSchoolId() %></td>
                        <%
                        // 登録者種別を判定する。
                        if(eachForm.getRegistrantAccountId() != 0) {
                            // 登録者アカウントIDが0以外の場合
                        %>
                            <td class="align-middle text-center">アカウントID：<%=eachForm.getRegistrantAccountId() %></td>
                        <%
                        } else {
                            // 上記以外の場合
                        %>
                            <td class="align-middle text-center">管理者ID：<%=eachForm.getRegistrantAdminId() %></td>
                        <%
                        }
                        %>
                        <td class="align-middle text-center"><%=eachForm.getSchoolName() %></td>
                        <td class="align-middle text-center"><%=eachForm.getSchoolCategoryName() %></td>
                        <td class="align-middle text-center"><%=eachForm.getStrSchoolRegisteredAt() %></td>
                        <td class="align-middle">
                            <div>
                                <%
                                // スクール編集 入力画面へのURLを生成する。
                                String editSchoolUrl = "location.href='./editSchoolInput.do?schoolId=" + eachForm.getSchoolId() +"'";
                                %>
                                <html:button property="editSchoolBtn" value="編集" styleClass="btn btn-primary btn-sm" onclick="<%=editSchoolUrl %>"/>
                            </div>
                        </td>
                    </tr>
                <%
                }
                %>
                </tbody>
            </table>
        </div>
        <jsp:include page="../common/pagination.jsp">
            <jsp:param name="totalPage" value="<%=inForm.getTotalPage() %>"/>
            <jsp:param name="currentPage" value="<%=inForm.getCurrentPage() %>"/>
            <jsp:param name="listUrl" value="./listSchool.do"/>
        </jsp:include>
    <%
    }
    %>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/admin/js/number-util.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/set-form-parts.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/submit-control.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/common-form-validation.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/school-form-validation.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/school-form-control.js"></script>
</body>
</html>
