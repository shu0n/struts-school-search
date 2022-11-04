<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="common/head.jsp">
    <jsp:param name="title" value="トップ"/>
</jsp:include>
<body>
<jsp:include page="common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>トップ</h2>
    <div class="table-responsive-md">
        <table class="table">
            <tbody>
                <tr>
                    <td><a href="./listAccount.do">アカウント一覧</a></td>
                </tr>
                <tr>
                    <td><a href="./createAccountInput.do">アカウント作成</a></td>
                </tr>
                <tr>
                <tr>
                    <td><a href="./listSchool.do">スクール一覧</a></td>
                </tr>
                <tr>
                    <td><a href="./registerSchoolInput.do">スクール登録</a></td>
                </tr>
                <tr>
                    <td><a href="./listReceivedEntry.do">申込受付一覧</a></td>
                </tr>
                <tr>
                    <td><a href="./listCategory.do">カテゴリー一覧</a></td>
                </tr>
                <tr>
                    <td><a href="./createCategoryInput.do">カテゴリー作成</a></td>
                </tr>
                <tr>
                    <td><a href="./listContact.do">お問合せ一覧</a></td>
                </tr>
            </tbody>
        </table>
    </div>
</section>
<jsp:include page="common/footer.jsp"/>
</body>
</html>
