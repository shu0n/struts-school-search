<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="common/head.jsp">
    <jsp:param name="title" value="サイトについて"/>
</jsp:include>
<body >
<jsp:include page="common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>サイトについて</h2>
    <div class="table-responsive-md">
        <table class="table">
            <tbody>
                <tr>
                    <th scope="row" class="text-nowrap">サイト名</th>
                    <td>School Search</td>
                </tr>
                <tr>
                    <th scope="row" class="text-nowrap">URL</th>
                    <td><a href="./"><bean:write name="FrontAboutSiteActionForm" property="siteUrl"/></a></td>
                </tr>
                <tr>
                    <th scope="row" class="text-nowrap">サイト概要</th>
                    <td>各ジャンルのスクール運営者とこれから学習を始めたいと考えている個人を結ぶためのサイトです。</td>
                </tr>
            </tbody>
        </table>
    </div>
</section>
<jsp:include page="common/footer.jsp"/>
</body>
</html>
