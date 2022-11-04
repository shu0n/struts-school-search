<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="actionform.EntryExtendActionForm"%>
<%@ page import="front.actionform.entry.FrontListEntryActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
FrontListEntryActionForm inForm = (FrontListEntryActionForm) session.getAttribute("FrontListEntryActionForm");
%>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="申込一覧"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>申込一覧</h2>
    <p><%=inForm.getTotalForm() %> 件の申込があります。</p>
    <%
    // 該当する申込の件数を判定する。
    if(inForm.getTotalForm() > 0) {
        // 1件以上存在する場合
    %>
        <jsp:include page="../common/pagination.jsp">
            <jsp:param name="totalPage" value="<%=inForm.getTotalPage() %>"/>
            <jsp:param name="currentPage" value="<%=inForm.getCurrentPage() %>"/>
            <jsp:param name="listUrl" value="./listEntry.do"/>
        </jsp:include>
        <div class="table-responsive-md">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col" class="text-nowrap text-center">ID</th>
                        <th scope="col" class="text-nowrap text-center">スクール名</th>
                        <th scope="col" class="d-none d-md-table-cell text-center">申込ステータス</th>
                        <th scope="col" class="d-none d-md-table-cell text-center">申込日時</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    // リストの要素数分、処理を繰り返す
                    for(EntryExtendActionForm eachForm: inForm.getDisplayedEntryList()) {
                    %>
                        <tr>
                            <td class="align-middle text-center"><%=eachForm.getEntryId() %></td>
                            <td class="align-middle text-center"><a href="./viewDetailedSchool.do?schoolId=<%=eachForm.getEntrySchoolId() %>"><%=eachForm.getEntrySchoolName() %></a></td>
                            <td class="d-none d-md-table-cell align-middle text-center"><%=eachForm.getEntryStatusName() %></td>
                            <td class="d-none d-md-table-cell align-middle text-center"><%=eachForm.getStrEntriedAt() %></td>
                            <td>
                                <div class="btn-group-vertical" role="group">
                                    <%
                                    // 申込詳細画面へのURLを生成する。
                                    String viewDetailedEntryUrl = "return !isDoubleClick('./viewDetailedEntry.do?entryId=" + eachForm.getEntryId() +"')";
                                    %>
                                    <html:button property="viewDetailedEntryBtn" value="詳細" styleClass="btn btn-primary btn-sm" onclick="<%=viewDetailedEntryUrl %>"/>
                                </div>
                            </td>
                        </tr>
                    <%
                    }
                    %>
            </table>
        </div>
        <jsp:include page="../common/pagination.jsp">
            <jsp:param name="totalPage" value="<%=inForm.getTotalPage() %>"/>
            <jsp:param name="currentPage" value="<%=inForm.getCurrentPage() %>"/>
            <jsp:param name="listUrl" value="./listEntry.do"/>
        </jsp:include>
    <%
    }
    %>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
</body>
</html>
