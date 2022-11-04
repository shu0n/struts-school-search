<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.collections4.CollectionUtils" %>
<%@ page import="actionform.MessageExtendActionForm"%>
<%@ page import="front.actionform.message.FrontViewSendedMessageActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
FrontViewSendedMessageActionForm inForm = (FrontViewSendedMessageActionForm) session.getAttribute("FrontViewSendedMessageActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="送信メッセージ詳細"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>送信メッセージ詳細</h2>
    <div class="py-2">
        <table class="table">
            <tbody>
                <tr>
                    <th>送信日時</th>
                    <td><bean:write name="FrontViewSendedMessageActionForm" property="strSendedAt"/></td>
                </tr>
                <tr>
                    <th>受取アカウント姓名</th>
                    <td>
                        <bean:write name="FrontViewSendedMessageActionForm" property="recipientLastName"/> <bean:write name="FrontViewSendedMessageActionForm" property="recipientFirstName"/>
                    </td>
                </tr>
                <tr>
                    <th>件名</th>
                    <td><bean:write name="FrontViewSendedMessageActionForm" property="messageSubject"/></td>
                </tr>
                <tr>
                    <th>本文</th>
                    <td>
                        <p style="white-space: pre-wrap;"><bean:write name="FrontViewSendedMessageActionForm" property="messageBody"/></p>
                    </td>
                </tr>
            </tbody>
        </table>
        <%
        // 送信メッセージ一覧画面へのURLを生成する。
        String listSendedMessageUrl = "location.href='./listSendedMessage.do'";
        %>
        <html:button property="listSendedMessageBtn" value="戻る" styleClass="btn btn-secondary" onclick="<%=listSendedMessageUrl %>"/>
        <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteSendedMessageModal">削除する</button>
        <div class="modal fade" id="deleteSendedMessageModal" tabindex="-1" role="dialog" aria-labelledby="deleteSendedMessageModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="deleteSendedMessageModalLabel">送信メッセージ削除</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                    </div>
                    <div class="modal-body">
                        <p>このメッセージを削除しますか？</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">閉じる</button>
                        <%
                        // 送信メッセージ削除のURLを生成する。
                        String deleteSendedMessageUrl = "return !isDoubleClick('./deleteSendedMessage.do?messageId=" + inForm.getMessageId() + "')";
                        %>
                        <button type="button" class="btn btn-danger" onclick="<%=deleteSendedMessageUrl %>">削除</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<%
// 該当する返信元メッセージの件数を判定する。
if(!CollectionUtils.isEmpty(inForm.getReplySourceMessageList())) {
    // 1件以上存在する場合
%>
    <section class="container-fluid pt-4" style="width: 90%;">
        <h2>メッセージ履歴</h2>
        <div class="table-responsive-md">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th scope="col" class="text-nowrap" style="width: 30%;">差出元</th>
                        <th scope="col" class="text-nowrap" style="width: 50%;">件名</th>
                        <th scope="col" class="text-nowrap" style="width: 20%;">受信日時</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    // リストの要素分、処理を繰り返す。
                    for(MessageExtendActionForm eachForm: inForm.getReplySourceMessageList()) {
                    %>
                        <tr>
                            <td><%=eachForm.getSenderLastName() %> <%=eachForm.getSenderFirstName() %></td>
                            <td>
                                <a href="./viewReceivedMessage.do?messageId=<%=eachForm.getMessageId() %>">
                                    <%=eachForm.getMessageSubject() %>
                                </a>
                            </td>
                            <td><%=eachForm.getStrSendedAt() %></td>
                        </tr>
                    <%
                    }
                    %>
                </tbody>
            </table>
        </div>
    </section>
<%
}
%>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
</body>
</html>
