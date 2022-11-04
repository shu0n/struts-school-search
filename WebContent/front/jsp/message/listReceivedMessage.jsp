<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="actionform.MessageExtendActionForm"%>
<%@ page import="front.actionform.message.FrontListReceivedMessageActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
FrontListReceivedMessageActionForm inForm = (FrontListReceivedMessageActionForm) session.getAttribute("FrontListReceivedMessageActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="受信メッセージ一覧"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>受信メッセージ一覧</h2>
</section>
<section class="container-fluid pt-4" style="width: 90%;">
    <div>
        <html:button property="listSendedMessageBtn" value="送信メッセージ一覧ページへ" styleClass="btn btn-primary" onclick="return !isDoubleClick('./listSendedMessage.do')"/>
    </div>
</section>
<section class="container-fluid pt-4" style="width: 90%;">
    <p><%=inForm.getTotalForm() %> 件の受信メッセージがあります。</p>
    <%
    // 該当する受信メッセージの件数を判定する。
    if(inForm.getTotalForm() > 0) {
        // 1件以上存在する場合
    %>
        <jsp:include page="../common/pagination.jsp">
            <jsp:param name="totalPage" value="<%=inForm.getTotalPage() %>"/>
            <jsp:param name="currentPage" value="<%=inForm.getCurrentPage() %>"/>
            <jsp:param name="listUrl" value="./listReceivedMessage.do"/>
        </jsp:include>
        <div class="table-responsive-md">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col" class="text-nowrap text-center" style="width: 30%;">差出元</th>
                        <th scope="col" class="text-nowrap text-center" style="width: 50%;">件名</th>
                        <th scope="col" class="text-nowrap text-center" style="width: 20%;">受信日時</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    // リストの要素分、処理を繰り返す。
                    for(MessageExtendActionForm eachForm: inForm.getDisplayedMessageList()) {
                    %>
                        <%
                        // 開封済フラグを判定する。
                        if("0".equals(eachForm.getOpenedFlag())) {
                            // 未開封の場合
                        %>
                            <tr class="font-weight-bold">
                        <%
                        } else {
                            // 上記以外の場合
                        %>
                            <tr class="table-secondary">
                        <%
                        }
                        %>
                            <td class="align-middle text-center"><%=eachForm.getSenderLastName() %> <%=eachForm.getSenderFirstName() %></td>
                            <td class="align-middle text-center">
                                <a href="./viewReceivedMessage.do?messageId=<%=eachForm.getMessageId() %>">
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
            <jsp:param name="listUrl" value="./listReceivedMessage.do"/>
        </jsp:include>
    <%
    }
    %>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
</body>
</html>
