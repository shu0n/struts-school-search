<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="front.actionform.school.FrontDeleteSchoolActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
FrontDeleteSchoolActionForm inForm = (FrontDeleteSchoolActionForm) session.getAttribute("FrontDeleteSchoolActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="スクール削除(確認)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>スクール削除(確認)</h2>
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
    <p>以下のスクールを削除しますか？</p>
    <table class="table">
        <tbody>
            <tr>
                <th>スクール名</th>
                <td><bean:write name="FrontDeleteSchoolActionForm" property="schoolName"/></td>
            </tr>
            <tr>
                <th>カテゴリー</th>
                <td><bean:write name="FrontDeleteSchoolActionForm" property="schoolCategoryName"/></td>
            </tr>
        </tbody>
    </table>
    <html:form action="/deleteSchoolComplete" onsubmit="return !isDoubleSubmit()">
        <div class="form-group row justify-content-end">
            <div class="col-md-12">
                <%
                // スクール編集 入力画面へのURLを生成する。
                String editSchoolUrl = "return !isDoubleClick('./editSchoolInput.do?schoolId=" + inForm.getSchoolId() + "')";
                %>
                <html:button property="editSchoolBtn" value="キャンセル" styleClass="btn btn-secondary" onclick="<%=editSchoolUrl %>"/>
                <html:submit value="削除する" styleClass="btn btn-primary"/>
            </div>
        </div>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
</body>
</html>
