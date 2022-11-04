<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="front.actionform.entry.FrontViewDetailedEntryActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
//セッションからアクションフォームを取得する。
FrontViewDetailedEntryActionForm inForm = (FrontViewDetailedEntryActionForm) session.getAttribute("FrontViewDetailedEntryActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="申込詳細"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>申込詳細</h2>
    <div class="py-2">
        <table class="table">
            <tbody>
                <tr>
                    <th>申込ID</th>
                    <td><bean:write name="FrontViewDetailedEntryActionForm" property="entryId"/></td>
                </tr>
                <tr>
                    <th>スクール名</th>
                    <td>
                        <%
                        int entrySchoolId = inForm.getEntrySchoolId();
                        String schoolUrl = "./viewDetailedSchool.do?schoolId=" + entrySchoolId;
                        %>
                        <a href="<%=schoolUrl %>"><bean:write name="FrontViewDetailedEntryActionForm" property="entrySchoolName"/></a>
                    </td>
                </tr>
                <tr>
                    <th>申込ステータス</th>
                    <td><bean:write name="FrontViewDetailedEntryActionForm" property="entryStatusName"/></td>
                </tr>
                <tr>
                    <th>申込日時</th>
                    <td><bean:write name="FrontViewDetailedEntryActionForm" property="strEntriedAt"/></td>
                </tr>
                <tr>
                    <th>質問</th>
                    <td>
                        <p style="white-space: pre-wrap;"><bean:write name="FrontViewDetailedEntryActionForm" property="entryQuestion"/></p>
                    </td>
                </tr>
            </tbody>
        </table>
        <html:button property="listEntryBtn" value="戻る" styleClass="btn btn-secondary" onclick="return !isDoubleClick('./listEntry.do')"/>
        <%
        // 申込キャンセル 確認画面へのURLを生成する。
        String cancelEntryUrl = "return !isDoubleClick('./cancelEntryConfirm.do?entryId=" + inForm.getEntryId() + "')";
        %>
        <html:button property="cancelEntryBtn" value="キャンセル" styleClass="btn btn-primary" onclick="<%=cancelEntryUrl %>"/>
    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
</body>
</html>
