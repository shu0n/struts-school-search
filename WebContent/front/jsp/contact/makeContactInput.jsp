<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="front.actionform.contact.FrontMakeContactActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="お問合せ(入力)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>お問合せ(入力)</h2>
    <html:form action="/makeContactConfirm" onsubmit="return validateBeforeSubmitContact()">
        <div class="form-group row">
            <label class="col-md-3 col-form-label">お名前<span class="badge badge-warning">必須</span></label>
            <div class="col-md-4 input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">姓</span>
                </div>
                <html:text property="contactLastName" styleClass="form-control" styleId="contactLastName" onblur="validateRequiredText('contactLastName', 20)"/>
                <div class="invalid-feedback">20文字以内で入力してください。</div>
            </div>
            <div class="col-md-4 input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">名</span>
                </div>
                <html:text property="contactFirstName" styleClass="form-control" styleId="contactFirstName" onblur="validateRequiredText('contactFirstName', 20)"/>
                <div class="invalid-feedback">20文字以内で入力してください。</div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 col-form-label">お名前(フリガナ)<span class="badge badge-warning">必須</span></label>
            <div class="col-md-4 input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">セイ</span>
                </div>
                <html:text property="contactLastNameKana" styleClass="form-control" styleId="contactLastNameKana" onblur="validateRequiredTextKana('contactLastNameKana', 40)"/>
                <div class="invalid-feedback">40文字以内のカタカナを入力してください。</div>
            </div>
            <div class="col-md-4 input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">メイ</span>
                </div>
                <html:text property="contactFirstNameKana" styleClass="form-control" styleId="contactFirstNameKana" onblur="validateRequiredTextKana('contactFirstNameKana', 40)"/>
                <div class="invalid-feedback">40文字以内のカタカナを入力してください。</div>
            </div>
        </div>
        <div class="form-group row">
            <label for="contactMmailAddress" class="col-md-3 col-form-label">メールアドレス<span class="badge badge-warning">必須</span></label>
            <div class="col-md-9">
                <html:text property="contactMailAddress" styleClass="form-control" styleId="contactMailAddress" onblur="validateRequiredMailAddress('contactMailAddress')"/>
                <div class="invalid-feedback">メールアドレスの形式と一致しません。</div>
            </div>
        </div>
        <div class="form-group row">
            <label for="contactContent" class="col-md-3 col-form-label">お問合せ内容<span class="badge badge-warning">必須</span><small class="form-text text-muted">1000文字以内で入力してください。</small></label>
            <div class="col-md-9">
                <html:textarea property="contactContent" styleClass="form-control" styleId="contactContent" rows="5" onblur="validateRequiredText('contactContent', 1000)"/>
                <div class="invalid-feedback">1000文字以内で入力してください。</div>
            </div>
        </div>
        <div class="form-group row justify-content-end">
            <div class="col-md-9">
                <html:submit value="確認する" styleClass="btn btn-primary"/>
            </div>
        </div>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/set-form-parts.js"></script>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
<script src="<%=request.getContextPath()%>/front/js/common-form-validation.js"></script>
<script src="<%=request.getContextPath()%>/front/js/contact-form-validation.js"></script>
</body>
</html>
