<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="アカウント編集(確認)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>アカウント編集(確認)</h2>
    <html:form action="/editAccountComplete" onsubmit="return !isDoubleSubmit()">
        <div class="form-group row">
            <label class="col-md-3 control-label">お名前<span class="badge badge-warning">必須</span></label>
            <div class="col-md-3">
                <bean:write name="FrontEditAccountActionForm" property="lastName"/>
                <bean:write name="FrontEditAccountActionForm" property="firstName"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">お名前(フリガナ)<span class="badge badge-warning">必須</span></label>
            <div class="col-md-3">
                <bean:write name="FrontEditAccountActionForm" property="lastNameKana"/>
                <bean:write name="FrontEditAccountActionForm" property="firstNameKana"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">性別</label>
            <div class="col-md-9">
                <bean:write name="FrontEditAccountActionForm" property="sexName"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">生年月日</label>
            <div class="col-md-9">
                <logic:notEmpty name="FrontEditAccountActionForm" property="birthYear">
                    <bean:write name="FrontEditAccountActionForm" property="birthYear"/>年
                    <bean:write name="FrontEditAccountActionForm" property="birthMonth"/>月
                    <bean:write name="FrontEditAccountActionForm" property="birthDay"/>日
                </logic:notEmpty>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">都道府県<span class="badge badge-warning">必須</span></label>
            <div class="col-md-9">
                <bean:write name="FrontEditAccountActionForm" property="prefectureName"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">メールアドレス<span class="badge badge-warning">必須</span></label>
            <div class="col-md-9">
                <bean:write name="FrontEditAccountActionForm" property="mailAddress"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">自己紹介</label>
            <div class="col-md-9">
                <p style="white-space: pre-wrap;"><bean:write name="FrontEditAccountActionForm" property="selfIntroduction"/></p>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 control-label">プロフィール画像</label>
            <%
            // リクエストからプロフィール画像のパスを取得する。
            String profileImageFilePath = (String) request.getAttribute("profileImageFilePath");
            // パスが存在するかを判定する。
            if(!StringUtils.isEmpty(profileImageFilePath)) {
                // 存在する場合はパスを整形してsrcタグに設定する。
                String profileFilePath = request.getContextPath() + profileImageFilePath;
            %>
                <div class="col-md-3">
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <html:img styleClass="img-fluid" src="<%=profileFilePath %>"/>
                        </div>
                    </div>
                </div>
            <%
            }
            %>
        </div>
        <div class="form-group row justify-content-end">
            <div class="col-md-9">
                <html:submit property="dispatch" styleClass="btn btn-secondary">
                    <bean:message key="button.fix"/>
                </html:submit>
                <html:submit property="dispatch" styleClass="btn btn-primary">
                    <bean:message key="button.edit"/>
                </html:submit>
            </div>
        </div>
        <%
        // リクエストからプロフィール画像のファイル名を取得する。
        String profileImageFileName = (String) request.getAttribute("profileImageFileName");
        // ファイル名が存在するかを判定する。
        if(!StringUtils.isEmpty(profileImageFileName)) {
            // 存在する場合はhiddenタグのvalue属性に設定する。
        %>
            <html:hidden property="profileImageFileNameUpdate" value="<%=profileImageFileName %>"/>
        <%
        }
        %>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
</body>
</html>
