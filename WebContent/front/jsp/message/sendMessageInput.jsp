<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="front.actionform.message.FrontSendMessageActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを生成する。
FrontSendMessageActionForm inForm = (FrontSendMessageActionForm) session.getAttribute("FrontSendMessageActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="メッセージ送信(入力)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>メッセージ送信<br class="d-sm-none"/>(入力)</h2>
    <html:form action="/sendMessageConfirm" onsubmit="return validateBeforeSubmitMessage()">
        <div class="form-group row">
            <label class="col-md-3 col-form-label">申込ID</label>
            <div class="col-md-9" style="padding-top: calc(0.375rem + 1px);">
                <%
                // 申込詳細画面へのURLを生成する。
                String entryUrl = "./viewReceivedEntry.do?entryId=" + inForm.getEntryId();
                %>
                <a href="<%=entryUrl %>"><bean:write name="FrontSendMessageActionForm" property="entryId"/></a>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 col-form-label">送信先アカウント姓名</label>
            <div class="col-md-9" style="padding-top: calc(0.375rem + 1px);">
                <bean:write name="FrontSendMessageActionForm" property="recipientLastName"/> <bean:write name="FrontSendMessageActionForm" property="recipientFirstName"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="messageSubject" class="col-md-3 col-form-label">件名<span class="badge badge-warning">必須</span></label>
            <div class="col-md-9">
                <html:text property="messageSubject" styleClass="form-control" styleId="messageSubject" onblur="validateRequiredText('messageSubject', 20)"/>
                <div class="invalid-feedback">20文字以内で入力してください。</div>
            </div>
        </div>
        <div class="form-group row">
            <label for="messageBody" class="col-md-3 col-form-label">本文<span class="badge badge-warning">必須</span><small class="form-text text-muted">1000文字以内で入力してください。</small></label>
            <div class="col-md-9">
                <html:textarea property="messageBody" styleClass="form-control" styleId="messageBody" rows="5" onblur="validateRequiredText('messageBody', 1000)"/>
                <div class="invalid-feedback">1000文字以内で入力してください。</div>
            </div>
        </div>
        <div class="form-group row justify-content-end">
            <div class="col-md-9">
                <%
                // 申込受付詳細画面へのURLを生成する。
                String viewReceivedEntryUrl = "return !isDoubleClick('./viewReceivedEntry.do?entryId=" + inForm.getEntryId() + "')";
                %>
                <html:button property="viewReceivedEntryBtn" value="キャンセル" styleClass="btn btn-secondary" onclick="<%=viewReceivedEntryUrl %>"/>
                <html:submit value="確認する" styleClass="btn btn-primary"/>
            </div>
        </div>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
<script src="<%=request.getContextPath()%>/front/js/common-form-validation.js"></script>
<script src="<%=request.getContextPath()%>/front/js/message-form-validation.js"></script>
</body>
</html>
