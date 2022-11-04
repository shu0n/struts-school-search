<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="front.actionform.school.FrontListRegisteredSchoolActionForm"%>
<%@ page import="actionform.SchoolExtendActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
//セッションからアクションフォームを取得する。
FrontListRegisteredSchoolActionForm inForm = (FrontListRegisteredSchoolActionForm) session.getAttribute("FrontListRegisteredSchoolActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="登録スクール一覧"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>登録スクール一覧</h2>
</section>
<section class="container-fluid pt-4" style="width: 90%;">
    <div>
        <html:button property="registerSchoolBtn" value="スクール登録ページへ" styleClass="btn btn-primary" onclick="return !isDoubleClick('./registerSchoolInput.do')"/>
    </div>
</section>
<section class="container-fluid pt-4" style="width: 90%;">
    <p><%=inForm.getTotalPage() %> 件のスクールを登録しています。</p>
    <%
    // 該当するスクールの件数を判定する。
    if(inForm.getTotalForm() > 0) {
        // 1件以上存在する場合
    %>
        <jsp:include page="../common/pagination.jsp">
            <jsp:param name="totalPage" value="<%=inForm.getTotalPage() %>"/>
            <jsp:param name="currentPage" value="<%=inForm.getCurrentPage() %>"/>
            <jsp:param name="listUrl" value="./listRegisteredSchool.do"/>
        </jsp:include>
        <div class="table-responsive-md">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col" class="text-nowrap text-center">ID</th>
                        <th scope="col" class="text-nowrap text-center">スクール名</th>
                        <th scope="col" class="d-none d-md-table-cell text-nowrap text-center">カテゴリー</th>
                        <th scope="col" class="d-none d-md-table-cell text-center">公開可否</th>
                        <th scope="col" class="d-none d-md-table-cell text-center">申込可否</th>
                        <th scope="col" class="d-none d-md-table-cell text-center">申込件数</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    // リストの要素数分、処理を繰り返す。
                    for(SchoolExtendActionForm eachForm: inForm.getDisplayedSchoolList()) {
                    %>
                        <tr>
                            <td class="align-middle text-center"><%=eachForm.getSchoolId() %></td>
                            <td class="align-middle text-center"><a href="./viewDetailedSchool.do?schoolId=<%=eachForm.getSchoolId() %>"><%=eachForm.getSchoolName() %></a></td>
                            <td class="d-none d-md-table-cell align-middle text-center"><%=eachForm.getSchoolCategoryName() %></td>
                            <td class="d-none d-md-table-cell align-middle text-center"><%=eachForm.getSchoolReleaseProprietyName() %></td>
                            <td class="d-none d-md-table-cell align-middle text-center"><%=eachForm.getSchoolEntryProprietyName() %></td>
                            <td class="d-none d-md-table-cell align-middle text-center"><%=eachForm.getAllEntryNum() %></td>
                            <td class="align-middle text-center">
                                <div class="btn-group-vertical" role="group">
                                    <%
                                    // スクール編集画面へのURLを生成する。
                                    String editSchoolUrl = "location.href='./editSchoolInput.do?schoolId=" + eachForm.getSchoolId() +"'";
                                    %>
                                    <html:button property="editSchoolBtn" value="編集" styleClass="btn btn-primary btn-sm" onclick="<%=editSchoolUrl %>"/>
                                    <%
                                    // 申込受付一覧画面へのURLを生成する。
                                    String receivedEntryUrl = "return !isDoubleClick('./listReceivedEntry.do?schoolId=" + eachForm.getSchoolId() +"')";
                                    %>
                                    <html:button property="receivedEntryBtn" value="申込受付" styleClass="btn btn-secondary btn-sm" onclick="<%=receivedEntryUrl %>"/>
                                </div>
                            </td>
                        </tr>
                    <%
                    }
                    %>
                </tbody>
            </table>
        </div>
        <jsp:include page="../common/pagination.jsp">
            <jsp:param name="totalPage" value="<%=inForm.getTotalPage() %>"/>
            <jsp:param name="currentPage" value="<%=inForm.getCurrentPage() %>"/>
            <jsp:param name="listUrl" value="./listRegisteredSchool.do"/>
        </jsp:include>
    <%
    }
    %>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
</body>
</html>
