<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="admin.actionform.entry.AdminViewReceivedEntryActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
AdminViewReceivedEntryActionForm inForm = (AdminViewReceivedEntryActionForm) session.getAttribute("AdminViewReceivedEntryActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="申込受付詳細"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>申込受付詳細</h2>
</section>
<section class="container-fluid pt-4" style="width: 90%;">
    <html:form action="/updateEntryStatus" onsubmit="return !isDoubleSubmit()">
        <div class="form-group row">
            <label class="col-md-3 control-label">申込ID</label>
            <div class="col-md-9">
                <bean:write name="AdminViewReceivedEntryActionForm" property="entryId"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">申込日時</label>
            <div class="col-md-9">
                <bean:write name="AdminViewReceivedEntryActionForm" property="strEntriedAt"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">更新日時</label>
            <div class="col-md-9">
                <bean:write name="AdminViewReceivedEntryActionForm" property="strEntryUpdatedAt"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="entryStatusId" class="col-md-3 control-label">申込ステータス</label>
            <div class="col-md-3">
                <html:select property="entryStatusId" styleClass="form-control" styleId="entryStatusId">
                    <html:optionsCollection name="AdminViewReceivedEntryActionForm" property="entryStatusList" label="label" value="value"/>
                </html:select>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">スクールID</label>
            <div class="col-md-9">
                <%
                // スクール編集 入力画面へのURLを生成する。
                String schoolUrl = "./editSchoolInput.do?schoolId=" + inForm.getEntrySchoolId();
                %>
                <a href="<%=schoolUrl %>"><bean:write name="AdminViewReceivedEntryActionForm" property="entrySchoolId"/></a>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">スクール名</label>
            <div class="col-md-9">
                <bean:write name="AdminViewReceivedEntryActionForm" property="entrySchoolName"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">申込アカウントID</label>
            <div class="col-md-9">
                <%
                // アカウント編集 入力画面へのURLを生成する。
                String accountUrl = "./editAccountInput.do?accountId=" + inForm.getApplicantAccountId();
                %>
                <a href="<%=accountUrl %>"><bean:write name="AdminViewReceivedEntryActionForm" property="applicantAccountId"/></a>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">申込者姓名</label>
            <div class="col-md-9">
                <bean:write name="AdminViewReceivedEntryActionForm" property="applicantLastName"/> <bean:write name="AdminViewReceivedEntryActionForm" property="applicantFirstName"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">申込者姓名(フリガナ)</label>
            <div class="col-md-9">
                <bean:write name="AdminViewReceivedEntryActionForm" property="applicantLastNameKana"/> <bean:write name="AdminViewReceivedEntryActionForm" property="applicantFirstNameKana"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">質問</label>
            <div class="col-md-9">
                <p style="white-space: pre-wrap;"><bean:write name="AdminViewReceivedEntryActionForm" property="entryQuestion"/></p>
            </div>
        </div>
        <div class="form-group row justify-content-end">
            <div class="col-md-9">
                <%
                // 申込受付一覧画面へのURLを生成する。
                String listReceivedEntryUrl = "location.href='./listReceivedEntry.do?schoolId=" + inForm.getEntrySchoolId() + "'";
                %>
                <html:button property="listReceivedEntryBtn" value="戻る" styleClass="btn btn-secondary" onclick="return !isDoubleClick('./listReceivedEntry.do')"/>
                <html:submit value="更新する" styleClass="btn btn-primary"/>
            </div>
        </div>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/admin/js/submit-control.js"></script>
</body>
</html>
