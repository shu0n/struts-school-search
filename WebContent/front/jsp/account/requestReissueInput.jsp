<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="パスワード再発行申請(入力)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>パスワード再発行<br class="d-sm-none" />申請(入力)</h2>
    <html:form action="/requestReissueComplete" onsubmit="return (validateRequiredMailAddress('mailAddress') & !sDoubleSubmit()">
        <div class="form-group row">
            <label for="mailAddress" class="col-md-3 col-form-label">メールアドレス</label>
            <div class="col-md-9">
                <logic:messagesNotPresent>
                    <html:text property="mailAddress" styleClass="form-control" styleId="mailAddress" onblur="validateRequiredMailAddress('mailAddress')"/>
                    <div class="invalid-feedback">メールアドレスの形式と一致しません。</div>
                </logic:messagesNotPresent>
                <logic:messagesPresent>
                    <html:text property="mailAddress" styleClass="form-control is-invalid" styleId="mailAddress" onblur="validateRequiredMailAddress('mailAddress')"/>
                    <div class="invalid-feedback" id="errorMailAddress">
                        <html:messages id="msg">
                            <bean:write name="msg" ignore="true"/>
                        </html:messages>
                    </div>
                </logic:messagesPresent>
            </div>
        </div>
        <div class="form-group row justify-content-end">
            <div class="col-md-9">
                <html:button property="cancelEditBtn" value="キャンセル" styleClass="btn btn-secondary" onclick="return !isDoubleClick('./login.do')"/>
                <html:submit value="申請する" styleClass="btn btn-primary"/>
            </div>
        </div>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
<script src="<%=request.getContextPath()%>/front/js/common-form-validation.js"></script>
<script src="<%=request.getContextPath()%>/front/js/account-form-validation.js"></script>
</body>
</html>
