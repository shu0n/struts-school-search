<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="ログイン"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <div class="row justify-content-end justify-content-sm-start">
        <div class="col-sm-4 pb-3 mx-auto">
            <div class="card text-center">
                <div class="card-body">
                    <h4 >ログイン</h4>
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
                    <html:form action="/loginExecute" onsubmit="return validateBeforeSubmitLogin()">
                        <div class="form-group">
                            <label for="mailAddress">メールアドレス</label>
                            <html:text property="adminMailAddress" styleClass="form-control" styleId="adminMailAddress" onblur="validateRequiredMailAddress('adminMailAddress')"/>
                            <div class="invalid-feedback">メールアドレスの形式と一致しません。</div>
                        </div>
                        <div class="form-group">
                            <label for="password">パスワード</label>
                            <html:password property="adminPassword" redisplay="false" styleClass="form-control" styleId="adminPassword" onblur="validatePassword('adminPassword', 8, 16)"/>
                            <div class="invalid-feedback">8〜16文字の半角英数字を入力してください。</div>
                        </div>
                        <html:submit styleClass="btn btn-primary" value="ログイン"/>
                        <%
                            String redirectUrl = (String) request.getAttribute("redirectUrl");
                            if(!StringUtils.isEmpty(redirectUrl)) {
                        %>
                            <html:hidden property="redirectUrl" value="<%=redirectUrl %>"/>
                        <%
                        }
                        %>
                        </html:form>
                 </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/admin/js/submit-control.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/common-form-validation.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/login-form-validation.js"></script>
</body>
</html>
