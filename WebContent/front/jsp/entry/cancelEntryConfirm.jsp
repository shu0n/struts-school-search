<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="front.actionform.entry.FrontCancelEntryActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
FrontCancelEntryActionForm inForm = (FrontCancelEntryActionForm) session.getAttribute("FrontCancelEntryActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="申込キャンセル(確認)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>申込キャンセル(確認)</h2>
    <p>以下の申込をキャンセルしますか？</p>
    <table class="table">
        <tbody>
            <tr>
                <th>申込ID</th>
                <td><bean:write name="FrontCancelEntryActionForm" property="entryId"/></td>
            </tr>
            <tr>
                <th>スクール名</th>
                <td><bean:write name="FrontCancelEntryActionForm" property="entrySchoolName"/></td>
            </tr>
        </tbody>
    </table>
    <html:form action="/cancelEntryComplete" onsubmit="return !isDoubleSubmit()">
        <div class="form-group row justify-content-end">
            <div class="col-md-12">
                <%
                // 申込詳細画面へのURLを生成する。
                String viewDetailedEntryUrl = "return !isDoubleClick('./viewDetailedEntry.do?entryId=" + inForm.getEntryId() + "')";
                %>
                <html:button property="viewDetailedEntryBtn" value="戻る" styleClass="btn btn-secondary" onclick="<%=viewDetailedEntryUrl %>"/>
                <html:submit value="キャンセルする" styleClass="btn btn-danger"/>
            </div>
        </div>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
</body>
</html>
