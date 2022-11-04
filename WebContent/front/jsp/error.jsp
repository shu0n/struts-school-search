<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="./common/head.jsp">
    <jsp:param name="title" value="エラーが発生しました"/>
</jsp:include>
<body>
<jsp:include page="./common/headerIrregular.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>エラーが発生しました。</h2>
    <html:button property="topBtn" value="トップページへ" styleClass="btn btn-primary" onclick="return !isDoubleClick('./')"/>
</section>
<jsp:include page="./common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
</body>
</html>
