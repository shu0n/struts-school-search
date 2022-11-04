<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="お問合せ(確認)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>お問合せ(確認)</h2>
    <html:form action="/makeContactComplete" onsubmit="return !isDoubleSubmit()">
        <div class="form-group row">
            <label class="col-md-3 control-label">お名前<span class="badge badge-warning">必須</span></label>
            <div class="col-md-3">
                <bean:write name="FrontMakeContactActionForm" property="contactLastName"/>
                <bean:write name="FrontMakeContactActionForm" property="contactFirstName"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">お名前(フリガナ)<span class="badge badge-warning">必須</span></label>
            <div class="col-md-3">
                <bean:write name="FrontMakeContactActionForm" property="contactLastNameKana"/>
                <bean:write name="FrontMakeContactActionForm" property="contactFirstNameKana"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">メールアドレス<span class="badge badge-warning">必須</span></label>
            <div class="col-md-9">
                <bean:write name="FrontMakeContactActionForm" property="contactMailAddress"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">お問合せ内容<span class="badge badge-warning">必須</span></label>
            <div class="col-md-9">
                <p style="white-space: pre-wrap;"><bean:write name="FrontMakeContactActionForm" property="contactContent"/></p>
            </div>
        </div>
        <div class="form-group row justify-content-end">
            <div class="col-md-9">
                <html:submit property="dispatch" styleClass="btn btn-secondary">
                    <bean:message key="button.fix"/>
                </html:submit>
                <html:submit property="dispatch" styleClass="btn btn-primary">
                    <bean:message key="button.send"/>
                </html:submit>
            </div>
        </div>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
</body>
</html>