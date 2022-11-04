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
                            <html:text property="mailAddress" styleClass="form-control" styleId="mailAddress" onblur="validateRequiredMailAddress('mailAddress')"/>
                            <div class="invalid-feedback">メールアドレスの形式と一致しません。</div>
                        </div>
                        <div class="form-group">
                            <label for="password">パスワード</label>
                            <html:password property="password" redisplay="false" styleClass="form-control" styleId="password" onblur="validatePassword('password', 8, 16)"/>
                            <div class="invalid-feedback">8〜16文字の半角英数字を入力してください。</div>
                        </div>
                        <html:submit styleClass="btn btn-primary" value="ログイン"/>
                        <%
                        // リクエストからリダイレクトURLを取得する。
                        String redirectUrl = (String) request.getAttribute("redirectUrl");
                        // リダイレクトURLが存在するかを判定する。
                        if(!StringUtils.isEmpty(redirectUrl)) {
                            // 存在する場合はhiddenタグのvalue属性に設定する。
                        %>
                            <html:hidden property="redirectUrl" value="<%=redirectUrl %>"/>
                        <%
                        }
                        %>
                    </html:form>
                    <div class="pt-2">
                        <a href="./requestReissueInput.do">パスワードをお忘れの方</a>
                    </div>
                    <div class="pt-2">
                        <a href="./createAccountInput.do">アカウントをお持ちでない方</a>
                    </div>
                 </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
<script src="<%=request.getContextPath()%>/front/js/common-form-validation.js"></script>
<script src="<%=request.getContextPath()%>/front/js/login-form-validation.js"></script>
</body>
</html>
