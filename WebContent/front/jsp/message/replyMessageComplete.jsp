<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="スクール返信(完了)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>メッセージ返信<br class="d-sm-none"/>(完了)</h2>
    <p>メッセージに返信しました。</p>
    <html:button property="listSendedMessageBtn" value="送信メッセージ一覧ページへ" styleClass="btn btn-primary" onclick="return !isDoubleClick('./listSendedMessage.do')"/>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
</body>
</html>
