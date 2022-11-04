<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="申込キャンセル(完了)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>申込キャンセル(完了)</h2>
    <p>申込をキャンセルしました。</p>
    <html:button property="listEntryBtn" value="申込一覧ページへ" styleClass="btn btn-primary" onclick="return !isDoubleClick('./listEntry.do')"/>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
</body>
</html>
