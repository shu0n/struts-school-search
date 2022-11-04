<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="メッセージ返信(確認)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>メッセージ返信<br class="d-sm-none"/>(確認)</h2>
    <html:form action="/replyMessageComplete" onsubmit="return !isDoubleSubmit()">
        <div class="form-group row">
            <label class="col-md-3 col-form-label">返信メッセージID</label>
            <div class="col-md-9" style="padding-top: calc(0.375rem + 1px);">
                <bean:write name="FrontReplyMessageActionForm" property="replyMessageId"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 col-form-label">送信先アカウント姓名</label>
            <div class="col-md-9" style="padding-top: calc(0.375rem + 1px);">
                <bean:write name="FrontReplyMessageActionForm" property="recipientLastName"/> <bean:write name="FrontReplyMessageActionForm" property="recipientFirstName"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="messageSubject" class="col-md-3 col-form-label">件名<span class="badge badge-warning">必須</span></label>
            <div class="col-md-9">
                <bean:write name="FrontReplyMessageActionForm" property="messageSubject"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="messageBody" class="col-md-3 col-form-label">本文<span class="badge badge-warning">必須</span></label>
            <div class="col-md-9">
                <p style="white-space: pre-wrap;"><bean:write name="FrontReplyMessageActionForm" property="messageBody"/></p>
            </div>
        </div>
        <div class="form-group row justify-content-end">
            <div class="col-md-9">
                <html:submit property="dispatch" styleClass="btn btn-secondary">
                    <bean:message key="button.fix"/>
                </html:submit>
                <html:submit property="dispatch" styleClass="btn btn-primary">
                    <bean:message key="button.reply"/>
                </html:submit>
            </div>
        </div>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
</body>
</html>

