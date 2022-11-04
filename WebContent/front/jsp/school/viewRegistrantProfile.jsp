<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="front.actionform.school.FrontViewRegistrantProfileActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="ja">
<%
// セッションからアクションフォームを取得する。
FrontViewRegistrantProfileActionForm inForm = (FrontViewRegistrantProfileActionForm) session.getAttribute("FrontViewRegistrantProfileActionForm");
%>
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="スクール登録者プロフィール"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2><%=inForm.getSchoolName() %><br>登録者プロフィール</h2>
    <div class="py-2">
        <div>
            <div class="row md-4">
                <div class="col-md-3 pt-4">
                    <%
                    // アクションフォームからプロフィール画像のパスを取得する。
                    String profileImageFilePath = inForm.getProfileImageFilePath();
                    // パスが存在するかを判定する。
                    if(StringUtils.isEmpty(profileImageFilePath)) {
                        // 存在しない場合はデフォルト画像のパスをsrcタグに設定する。
                        String profileFilePath = request.getContextPath() + "/img/site/no_image_gray.png";
                    %>
                        <div class="carousel-inner">
                            <div class="carousel-item active">
                                <html:img styleClass="img-fluid" src="<%=profileFilePath %>"/>
                            </div>
                        </div>
                    <%
                    } else {
                        // 上記以外の場合はプロフィール画像のパスをsrc属性に設定する。
                        String profileFilePath = request.getContextPath() + profileImageFilePath;
                    %>
                        <div class="carousel-inner">
                            <div class="carousel-item active">
                                <html:img styleClass="img-fluid" src="<%=profileFilePath %>"/>
                            </div>
                        </div>
                    <%
                    }
                    %>
                </div>
                <div class="col-md-9 pt-4">
                    <table class="table">
                        <tbody>
                            <tr>
                                <th style="width:30%">登録者の名前</th>
                                <td style="width:70%"><bean:write name="FrontViewRegistrantProfileActionForm" property="registrantLastName"/> <bean:write name="FrontViewRegistrantProfileActionForm" property="registrantFirstName"/></td>
                            </tr>
                            <tr>
                                <th style="width:30%">登録者のフリガナ</th>
                                <td style="width:70%"><bean:write name="FrontViewRegistrantProfileActionForm" property="registrantLastNameKana"/> <bean:write name="FrontViewRegistrantProfileActionForm" property="registrantFirstNameKana"/></td>
                            </tr>
                            <tr>
                                <th style="width:30%">自己紹介</th>
                                <td style="width:70%; white-space:pre-wrap;"><bean:write name="FrontViewRegistrantProfileActionForm" property="selfIntroduction"/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="pb-3">
        <%
        // スクール詳細画面へのURLを生成する。
        String viewDetailedSchoolUrl = "return !isDoubleClick('./viewDetailedSchool.do?schoolId=" + inForm.getSchoolId() + "')";
        %>
        <html:button property="viewDetailedSchoolSchoolBtn" value="スクール詳細ページに戻る" styleClass="btn btn-primary" onclick="<%=viewDetailedSchoolUrl %>"/>
    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
</body>
</html>
