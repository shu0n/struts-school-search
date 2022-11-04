<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="パスワード再発行(入力)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>パスワード<br class="d-sm-none" />再発行(入力)</h2>
    <html:form action="/executeReissueComplete" onsubmit="return validateBeforeSubmitNewPassword()">
        <div class="form-group row">
            <label for="newPassword" class="col-md-3 col-form-label">新しいパスワード<small class="form-text text-muted">8〜16文字の半角英数字で入力してください。</small></label>
            <div class="col-md-9">
                <html:password property="password" redisplay="false" styleClass="form-control" styleId="password" onblur="validatePassword('password', 8, 16)"/>
                <div class="invalid-feedback">8〜16文字の半角英数字を入力してください。</div>
            </div>
        </div>
        <div class="form-group row">
            <label for="confirmPassword" class="col-md-3 col-form-label">新しいパスワード(確認)</label>
            <div class="col-md-9">
                <html:password property="confirmNewPassword" redisplay="false" styleClass="form-control" styleId="confirmNewPassword" onblur="validateConfirmPassword('password', 'confirmNewPassword')"/>
                <div class="invalid-feedback">パスワードが一致しません。</div>
            </div>
        </div>
        <div class="form-group row justify-content-end">
            <div class="col-md-9">
                <html:submit value="再発行する" styleClass="btn btn-primary"/>
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
