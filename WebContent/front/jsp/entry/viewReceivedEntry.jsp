<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="front.actionform.entry.FrontViewReceivedEntryActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
FrontViewReceivedEntryActionForm inForm = (FrontViewReceivedEntryActionForm) session.getAttribute("FrontViewReceivedEntryActionForm");
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
    <div>
        <%
        // メッセージ送信 入力画面へのURLを生成する。
        String sendMessageUrl = "location.href='./sendMessageInput.do?entryId=" + inForm.getEntryId() +"'";
        %>
        <html:button property="sendMessageBtn" value="メッセージ送信" styleClass="btn btn-primary" onclick="<%=sendMessageUrl %>"/>
    </div>
</section>
<section class="container-fluid pt-4" style="width: 90%;">
    <html:form action="/updateEntryStatus" onsubmit="return !isDoubleSubmit()">
        <div class="form-group row">
            <label class="col-md-3 control-label">申込ID</label>
            <div class="col-md-9">
                <bean:write name="FrontViewReceivedEntryActionForm" property="entryId"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">申込日時</label>
            <div class="col-md-9">
                <bean:write name="FrontViewReceivedEntryActionForm" property="strEntriedAt"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">更新日時</label>
            <div class="col-md-9">
                <bean:write name="FrontViewReceivedEntryActionForm" property="strEntryUpdatedAt"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="entryStatusId" class="col-md-3 control-label">申込ステータス</label>
            <div class="col-md-3">
                <html:select property="entryStatusId" styleClass="form-control" styleId="entryStatusId">
                    <html:optionsCollection name="FrontViewReceivedEntryActionForm" property="entryStatusList" label="label" value="value"/>
                </html:select>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">スクール名</label>
            <div class="col-md-9">
                <%
                // スクール詳細画面へのURLを生成する。
                String viewDetailedchoolUrl = "./viewDetailedSchool.do?schoolId=" + inForm.getEntrySchoolId();
                %>
                <a href="<%=viewDetailedchoolUrl %>"><bean:write name="FrontViewReceivedEntryActionForm" property="entrySchoolName"/></a>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">申込者姓名</label>
            <div class="col-md-9">
                <bean:write name="FrontViewReceivedEntryActionForm" property="applicantLastName"/> <bean:write name="FrontViewReceivedEntryActionForm" property="applicantFirstName"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">申込者姓名(フリガナ)</label>
            <div class="col-md-9">
                <bean:write name="FrontViewReceivedEntryActionForm" property="applicantLastNameKana"/> <bean:write name="FrontViewReceivedEntryActionForm" property="applicantFirstNameKana"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">質問</label>
            <div class="col-md-9">
                <p style="white-space: pre-wrap;"><bean:write name="FrontViewReceivedEntryActionForm" property="entryQuestion"/></p>
            </div>
        </div>
        <div class="form-group row justify-content-end">
            <div class="col-md-9">
                <%
                // 申込受付一覧画面へのURLを生成する。
                String listReceivedEntryUrl = "return !isDoubleClick('./listReceivedEntry.do?schoolId=" + inForm.getEntrySchoolId() + "')";
                %>
                <html:button property="listReceivedEntryBtn" value="戻る" styleClass="btn btn-secondary" onclick="<%=listReceivedEntryUrl %>"/>
                <html:submit value="更新する" styleClass="btn btn-primary"/>
            </div>
        </div>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
</body>
</html>
