<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="admin.actionform.entry.AdminListReceivedEntryActionForm"%>
<%@ page import="actionform.EntryExtendActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
AdminListReceivedEntryActionForm inForm = (AdminListReceivedEntryActionForm) session.getAttribute("AdminListReceivedEntryActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="申込受付一覧"/>
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
                    申込受付を検索する
                </a>
            </h5>
        </div>
        <div class="collapse" role="tabpanel" id="collapseListGroup1" aria-labelledby="collapseListGroupHeading1" aria-expanded="false">
            <div class="card-body">
                <html:form action="/searchReceivedEntry" onsubmit="return validateBeforeSubmitSearchReceivedEntry(this)">
                    <div class="form-group row">
                        <label for="strEntryId" class="col-md-3 control-label">申込ID</label>
                        <div class="col-md-9">
                            <html:text property="strEntryId" styleClass="form-control" styleId="strEntryId" onblur="validateVariousIds('strEntryId', 50)"/>
                            <div class="invalid-feedback">ID,ID,...,IDの形式で入力していください。※IDとカンマは半角数字</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="strEntrySchoolId" class="col-md-3 control-label">スクールID</label>
                        <div class="col-md-9">
                            <html:text property="strEntrySchoolId" styleClass="form-control" styleId="strEntrySchoolId" onblur="validateVariousIds('strEntrySchoolId', 50)"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="strApplicantAccountId" class="col-md-3 control-label">申込アカウントID</label>
                        <div class="col-md-9">
                            <html:text property="strApplicantAccountId" styleClass="form-control" styleId="strApplicantAccountId" onblur="validateVariousIds('strApplicantAccountId', 50)"/>
                            <div class="invalid-feedback">ID,ID,...,IDの形式で入力していください。※IDとカンマは半角数字</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="entrySchoolName" class="col-md-3 control-label">スクール名</label>
                        <div class="col-md-9">
                            <html:text property="likeEntrySchoolName" styleClass="form-control" styleId="entrySchoolName" onblur="validateMaxLength('entrySchoolName', 50)"/>
                            <div class="invalid-feedback">50文字以内で入力してください。</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="applicantLastName" class="col-md-3 control-label">申込者姓名</label>
                        <div class="col-md-3 input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">姓</span>
                            </div>
                            <html:text property="likeApplicantLastName" styleClass="form-control" styleId="applicantLastName" onblur="validateMaxLength('applicantLastName', 20)"/>
                            <div class="invalid-feedback">20文字以内で入力してください。</div>
                        </div>
                        <div class="col-md-3 input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">名</span>
                            </div>
                            <html:text property="likeApplicantFirstName" styleClass="form-control" styleId="applicantFirstName" onblur="validateMaxLength('applicantFirstName', 20)"/>
                            <div class="invalid-feedback">20文字以内で入力してください。</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="applicantLastNameKana" class="col-md-3 control-label">申込者姓名(フリガナ)</label>
                        <div class="col-md-3 input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">セイ</span>
                            </div>
                            <html:text property="likeApplicantLastNameKana" styleClass="form-control" styleId="applicantLastNameKana" onblur="validateMaxLength('applicantLastNameKana', 40)"/>
                            <div class="invalid-feedback">40文字以内で入力してください。</div>
                        </div>
                        <div class="col-md-3 input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">メイ</span>
                            </div>
                            <html:text property="likeApplicantFirstNameKana" styleClass="form-control" styleId="applicantFirstNameKana" onblur="validateMaxLength('applicantFirstNameKana', 40)"/>
                            <div class="invalid-feedback">40文字以内で入力してください。</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="selectYear1" class="col-md-3 col-form-label">申込日時(From)</label>
                        <div class="col-md-3 input-group">
                            <html:select property="fromEntriedYear" styleClass="custom-select" styleId="selectYear1"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectYear1">年</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="fromEntriedMonth" styleClass="custom-select" styleId="selectMonth1"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectMonth1">月</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="fromEntriedDay" styleClass="custom-select" styleId="selectDay1"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectDay1">日</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="selectYear2" class="col-md-3 col-form-label">申込日時(To)</label>
                        <div class="col-md-3 input-group">
                            <html:select property="toEntriedYear" styleClass="custom-select" styleId="selectYear2"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectYear2">年</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="toEntriedMonth" styleClass="custom-select" styleId="selectMonth2"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectMonth2">月</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="toEntriedDay" styleClass="custom-select" styleId="selectDay2"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectDay2">日</label>
                            </div>
                        </div>
                    </div>
                  <div class="form-group row">
                        <label for="selectYear3" class="col-md-3 col-form-label">申込更新日時(From)</label>
                        <div class="col-md-3 input-group">
                            <html:select property="fromEntryUpdatedYear" styleClass="custom-select" styleId="selectYear3"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectYear3">年</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="fromEntryUpdatedMonth" styleClass="custom-select" styleId="selectMonth3"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectMonth3">月</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="fromEntryUpdatedDay" styleClass="custom-select" styleId="selectDay3"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectDay3">日</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="selectYear4" class="col-md-3 col-form-label">申込更新日時(To)</label>
                        <div class="col-md-3 input-group">
                            <html:select property="toEntryUpdatedYear" styleClass="custom-select" styleId="selectYear4"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectYear4">年</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="toEntryUpdatedMonth" styleClass="custom-select" styleId="selectMonth4"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectMonth4">月</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="toEntryUpdatedDay" styleClass="custom-select" styleId="selectDay4"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectDay4">日</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="entryStaus" class="col-md-3 control-label">申込ステータス</label>
                        <div class="col-md-3">
                            <html:select property="entryStatusIdArray" styleClass="form-control multiselect" styleId="entryStaus" multiple="true">
                                <html:optionsCollection name="AdminListReceivedEntryActionForm" property="entryStatusList" label="label" value="value"/>
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
    <html:button property="csvDownloadBtn" value="CSVダウンロード" styleClass="btn btn-primary btn-sm mt-1" onclick="return !isDoubleClick('./downloadReceivedEntryCsv.do')"/>
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
    <p><%=inForm.getTotalForm() %> 件の申込受付がヒットしました。</p>
    <%
    // 該当する申込受付の件数を判定する。
    if(inForm.getTotalForm() > 0) {
        // 1件以上ある場合
    %>
        <html:form action="/listReceivedEntry" styleClass="form-inline pb-3" style="flex-wrap: nowrap;" onsubmit="return !isDoubleSubmit()">
            <html:select property="sortKindForEntry" styleClass="custom-select">
                <html:optionsCollection property="sortKindForEntryList" label="label" value="value"/>
            </html:select>
            <html:submit value="並び替える" styleClass="btn btn-primary"/>
            <html:hidden property="sortFlag" value="true"/>
        </html:form>
        <jsp:include page="../common/pagination.jsp">
            <jsp:param name="totalPage" value="<%=inForm.getTotalPage() %>"/>
            <jsp:param name="currentPage" value="<%=inForm.getCurrentPage() %>"/>
            <jsp:param name="listUrl" value="./listReceivedEntry.do"/>
        </jsp:include>
        <div class="table-responsive-md">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col" class="text-nowrap text-center">ID</th>
                        <th scope="col" class="text-nowrap text-center">申込者姓名</th>
                        <th scope="col" class="text-nowrap text-center">申込ステータス</th>
                        <th scope="col" class="text-nowrap text-center">申込日時</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    // リストの要素数分、処理を繰り返す。
                    for(EntryExtendActionForm eachForm: inForm.getDisplayedEntryList()) {
                    %>
                        <tr>
                            <td class="align-middle text-center"><%=eachForm.getEntryId() %></td>
                            <td class="align-middle text-center"><%=eachForm.getApplicantLastName() %> <%=eachForm.getApplicantFirstName() %></td>
                            <td class="align-middle text-center"><%=eachForm.getEntryStatusName() %></td>
                            <td class="align-middle text-center"><%=eachForm.getStrEntriedAt() %></td>
                            <td class="align-middle">
                                <div class="btn-group-vertical" role="group">
                                    <%
                                    // 申込受付詳細画面へのURLを生成する。
                                    String viewReceivedEntryUrl = "location.href='./viewReceivedEntry.do?entryId=" + eachForm.getEntryId() +"'";
                                    %>
                                    <html:button property="viewReceivedEntryBtn" value="詳細" styleClass="btn btn-primary btn-sm" onclick="<%=viewReceivedEntryUrl %>"/>
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
            <jsp:param name="listUrl" value="./listReceivedEntry.do"/>
        </jsp:include>
    <%
    }
    %>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/admin/js/set-form-parts.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/submit-control.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/common-form-validation.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/entry-form-validation.js"></script>
</body>
</html>
