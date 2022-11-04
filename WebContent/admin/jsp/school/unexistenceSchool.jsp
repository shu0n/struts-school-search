<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="該当スクールなし"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>該当スクールなし</h2>
    <p>該当するスクールはありません。</p>
    <html:button property="listSchoolBtn" value="スクール一覧ページへ" styleClass="btn btn-primary" onclick="return !isDoubleClick('./listSchool.do')"/>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/admin/js/submit-control.js"></script>
</body>
</html>
