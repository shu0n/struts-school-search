<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="アカウント編集(入力)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>パスワード変更(入力)</h2>
    <html:form action="/changePasswordComplete" onsubmit="return validateBeforeSubmitNewPassword()">
        <div class="form-group row">
            <label for="password" class="col-md-3 col-form-label">現在のパスワード</label>
            <div class="col-md-9">
                <logic:messagesNotPresent>
                    <html:password property="password" redisplay="false" styleClass="form-control" styleId="password" onblur="validatePassword('password', 8, 16)"/>
                    <div class="invalid-feedback">8〜16文字の半角英数字を入力してください。</div>
                </logic:messagesNotPresent>
                <logic:messagesPresent>
                    <html:password property="password" redisplay="false" styleClass="form-control is-invalid" styleId="password" onblur="validatePassword('password', 8, 16)"/>
                    <div class="invalid-feedback" id="errorPassword">
                        <html:messages id="msg">
                            <bean:write name="msg" ignore="true"/>
                        </html:messages>
                    </div>
                </logic:messagesPresent>
            </div>
        </div>
        <div class="form-group row">
            <label for="newPassword" class="col-md-3 col-form-label">新しいパスワード<small class="form-text text-muted">8〜16文字の半角英数字で入力してください。</small></label>
            <div class="col-md-9">
                <html:password property="newPassword" redisplay="false" styleClass="form-control" styleId="newPassword" onblur="validatePassword('newPassword', 8, 16)"/>
                <div class="invalid-feedback">8〜16文字の半角英数字を入力してください。</div>
            </div>
        </div>
        <div class="form-group row">
            <label for="confirmNewPassword" class="col-md-3 col-form-label">新しいパスワード(確認)</label>
            <div class="col-md-9">
                <html:password property="confirmNewPassword" redisplay="false" styleClass="form-control" styleId="confirmNewPassword" onblur="validateConfirmPassword('newPassword', 'confirmNewPassword')"/>
                <div class="invalid-feedback">パスワードが一致しません。</div>
            </div>
        </div>
        <div class="form-group row justify-content-end">
            <div class="col-md-9">
                <html:button property="cancelEditBtn" value="キャンセル" styleClass="btn btn-secondary" onclick="return !isDoubleClick('./manageAccount.do')"/>
                <html:submit value="変更する" styleClass="btn btn-primary"/>
            </div>
        </div>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
<script src="<%=request.getContextPath()%>/front/js/common-form-validation.js"></script>
<script src="<%=request.getContextPath()%>/front/js/account-form-validation.js"></script>
</body>
</html>
