<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="admin.actionform.contact.AdminViewDetailedContactActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
AdminViewDetailedContactActionForm inForm = (AdminViewDetailedContactActionForm) session.getAttribute("AdminViewDetailedContactActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="お問合せ詳細"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>お問合せ詳細</h2>
</section>
<section class="container-fluid pt-4" style="width: 90%;">
    <html:form action="/updateContactStatus" onsubmit="return !isDoubleSubmit()">
        <div class="form-group row">
            <label class="col-md-3 control-label">お問合せID</label>
            <div class="col-md-9">
                <bean:write name="AdminViewDetailedContactActionForm" property="contactId"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">お問合せアカウントID</label>
            <div class="col-md-9">
                <%
                // お問合せアカウントIDを判定する。
                if(inForm.getContactAccountId() != 0) {
                    // 0以外の場合はアカウント編集 入力画面へのURLを生成する。
                    String editAccountUrl = "./editAccountInput.do?accountId=" + inForm.getContactAccountId();
                %>
                    <a href="<%=editAccountUrl %>"><bean:write name="AdminViewDetailedContactActionForm" property="contactAccountId"/></a>
                <%
                } else {
                %>
                    <p>非会員</p>
                <%
                }
                %>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">お問合せ日時</label>
            <div class="col-md-9">
                <bean:write name="AdminViewDetailedContactActionForm" property="strContactedAt"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">更新日時</label>
            <div class="col-md-9">
                <bean:write name="AdminViewDetailedContactActionForm" property="strContactUpdatedAt"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="contactStatusId" class="col-md-3 control-label">お問合せステータス</label>
            <div class="col-md-3">
                <html:select property="contactStatusId" styleClass="form-control" styleId="contactStatusId">
                    <html:optionsCollection name="AdminViewDetailedContactActionForm" property="contactStatusList" label="label" value="value"/>
                </html:select>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">お問合せ者姓名</label>
            <div class="col-md-9">
                <bean:write name="AdminViewDetailedContactActionForm" property="contactLastName"/> <bean:write name="AdminViewDetailedContactActionForm" property="contactFirstName"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">お問合せ者姓名(フリガナ)</label>
            <div class="col-md-9">
                <bean:write name="AdminViewDetailedContactActionForm" property="contactLastNameKana"/> <bean:write name="AdminViewDetailedContactActionForm" property="contactFirstNameKana"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">お問合せ者メールアドレス</label>
            <div class="col-md-9">
                <bean:write name="AdminViewDetailedContactActionForm" property="contactMailAddress"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">お問合せ内容</label>
            <div class="col-md-9">
                <p style="white-space: pre-wrap;"><bean:write name="AdminViewDetailedContactActionForm" property="contactContent"/></p>
            </div>
        </div>
      <div class="form-group row justify-content-end">
            <div class="col-md-9">
                <html:button property="listContactBtn" value="戻る" styleClass="btn btn-secondary" onclick="return !isDoubleClick('./listContact.do')"/>
                <html:submit value="更新する" styleClass="btn btn-primary"/>
            </div>
        </div>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/admin/js/submit-control.js"></script>
</body>
</html>
