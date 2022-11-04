<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="actionform.MessageExtendActionForm"%>
<%@ page import="front.actionform.message.FrontListSendedMessageActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
FrontListSendedMessageActionForm inForm = (FrontListSendedMessageActionForm) session.getAttribute("FrontListSendedMessageActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="送信メッセージ一覧"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>送信メッセージ一覧</h2>
</section>
<section class="container-fluid pt-4" style="width: 90%;">
    <div>
        <html:button property="listReceivedMessageBtn" value="受信メッセージ一覧ページへ" styleClass="btn btn-primary" onclick="return !isDoubleClick('./listReceivedMessage.do')"/>
    </div>
</section>
<section class="container-fluid pt-4" style="width: 90%;">
    <p><%=inForm.getTotalForm() %> 件の送信メッセージがあります。</p>
    <%
    // 該当する送信メッセージの件数を判定する。
    if(inForm.getTotalForm() > 0) {
        // 1件以上存在する場合
    %>
        <jsp:include page="../common/pagination.jsp">
            <jsp:param name="totalPage" value="<%=inForm.getTotalPage() %>"/>
            <jsp:param name="currentPage" value="<%=inForm.getCurrentPage() %>"/>
            <jsp:param name="listUrl" value="./listSendedMessage.do"/>
        </jsp:include>
        <div class="table-responsive-md">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col" class="text-nowrap text-center" style="width: 30%;">宛先</th>
                        <th scope="col" class="text-nowrap text-center" style="width: 50%;">件名</th>
                        <th scope="col" class="text-nowrap text-center" style="width: 20%;">送信日時</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    // リストの要素分、処理を繰り返す。
                    for(MessageExtendActionForm eachForm: inForm.getDisplayedMessageList()) {
                    %>
                        <tr>
                            <td class="align-middle text-center"><%=eachForm.getRecipientLastName() %> <%=eachForm.getRecipientFirstName() %></td>
                            <td class="align-middle text-center">
                                <a href="./viewSendedMessage.do?messageId=<%=eachForm.getMessageId() %>">
                                    <%=eachForm.getMessageSubject() %>
                                </a>
                            </td>
                            <td class="align-middle text-center"><%=eachForm.getStrSendedAt() %></td>
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
            <jsp:param name="listUrl" value="./listSendedMessage.do"/>
        </jsp:include>
    <%
    }
    %>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
</body>
</html>
