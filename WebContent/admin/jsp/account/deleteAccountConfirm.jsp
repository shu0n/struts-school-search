<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="admin.actionform.account.AdminDeleteAccountActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
AdminDeleteAccountActionForm inForm = (AdminDeleteAccountActionForm) session.getAttribute("AdminDeleteAccountActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="アカウント削除(確認)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>アカウント削除<br class="d-sm-none" />(確認)</h2>
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
    <p>以下のアカウントを削除しますか？</p>
    <html:form action="/deleteAccountComplete" onsubmit="return !isDoubleSubmit()">
        <div class="form-group row">
            <label class="col-md-3 col-form-label">アカウントID</label>
            <div class="col-md-9" style="padding-top: calc(0.375rem + 1px);">
                <bean:write name="AdminDeleteAccountActionForm" property="accountId"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">お名前</label>
            <div class="col-md-3">
                <bean:write name="AdminDeleteAccountActionForm" property="lastName"/>
                <bean:write name="AdminDeleteAccountActionForm" property="firstName"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">お名前(フリガナ)</label>
            <div class="col-md-3">
                <bean:write name="AdminDeleteAccountActionForm" property="lastNameKana"/>
                <bean:write name="AdminDeleteAccountActionForm" property="firstNameKana"/>
            </div>
        </div>
        <div class="form-group row justify-content-end">
            <div class="col-md-12">
                <%
                // アカウント編集 入力画面へのURLを生成する。
                String editAccountUrl = "return !isDoubleClick('./editAccountInput.do?accountId=" + inForm.getAccountId() + "')";
                %>
                <html:button property="editAccountBtn" value="キャンセル" styleClass="btn btn-secondary" onclick="<%=editAccountUrl %>"/>
                <html:submit value="削除する" styleClass="btn btn-danger"/>
            </div>
        </div>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/admin/js/submit-control.js"></script>
</body>
</html>
