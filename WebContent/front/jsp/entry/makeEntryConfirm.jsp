<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="front.actionform.entry.FrontMakeEntryActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
FrontMakeEntryActionForm inForm = (FrontMakeEntryActionForm) session.getAttribute("FrontMakeEntryActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="申込(確認)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>申込(確認)</h2>
    <html:form action="/makeEntryComplete" onsubmit="return !isDoubleSubmit()">
        <div class="form-group row">
            <label class="col-md-3 control-label">スクール名</label>
            <div class="col-md-3">
                <bean:write name="FrontMakeEntryActionForm" property="entrySchoolName"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">費用</label>
            <div class="col-md-3">
                <bean:write name="FrontMakeEntryActionForm" property="schoolFee"/> 円
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">スクール登録</label>
            <div class="col-md-3">
                <%
                // スクール登録者アカウントIDの有無を判定する。
                if(inForm.getRegistrantAccountId() == 0) {
                    // 存在しない場合は"School Search"を表示する。
                %>
                    <p class="form-text">School Search</p>
                <%
                } else {
                    // 上記以外の場合はスクール登録者の姓と名を表示する。
                %>
                    <bean:write name="FrontMakeEntryActionForm" property="registrantLastName"/> <bean:write name="FrontMakeEntryActionForm" property="registrantFirstName"/>
                <%
                }
                %>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">質問</label>
            <div class="col-md-3">
                <p style="white-space: pre-wrap;"><bean:write name="FrontMakeEntryActionForm" property="entryQuestion"/></p>
            </div>
        </div>
        <div class="form-group row justify-content-end">
            <div class="col-md-9">
                <html:submit property="dispatch" styleClass="btn btn-secondary">
                    <bean:message key="button.fix"/>
                </html:submit>
                <html:submit property="dispatch" styleClass="btn btn-primary">
                    <bean:message key="button.make"/>
                </html:submit>
            </div>
        </div>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
</body>
</html>
