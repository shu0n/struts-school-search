<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="アカウント管理"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>アカウント管理</h2>
    <div class="table-responsive-md">
        <table class="table">
            <tbody>
                <tr>
                    <td><a href="./editAccountInput.do">アカウント編集</a></td>
                </tr>
                <tr>
                    <td><a href="./changePasswordInput.do">パスワード変更</a></td>
                </tr>
                <tr>
                    <td><a href="./deleteAccountConfirm.do">退会</a></td>
                </tr>
                <tr>
                    <td><a href="./listEntry.do">申込一覧</a></td>
                </tr>
                <tr>
                    <td><a href="./listFavorite.do">お気に入りスクール一覧</a></td>
                </tr>
                <tr>
                    <td><a href="./listRegisteredSchool.do">登録スクール一覧</a></td>
                </tr>
                <tr>
                    <td><a href="./registerSchoolInput.do">スクール登録</a></td>
                </tr>
                <tr>
                    <td><a href="./listReceivedMessage.do">受信メッセージ一覧</a></td>
                </tr>
            </tbody>
        </table>
    </div>
</section>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>
