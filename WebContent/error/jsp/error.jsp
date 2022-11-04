<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta charset="utf-8">
<meta name="Viewport" content="width=device-width, initial-scale=1, shrink-tofit=no">
<link rel="stylesheet" href="<%=request.getContextPath() %>/error/css/bootstrap.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/error/css/common.css">
<title>エラー | School Search</title>
</head>
<body>
<script src="<%=request.getContextPath()%>/error/js/jquery-3.6.0.min.js"></script>
<script src="<%=request.getContextPath()%>/error/js/bootstrap.bundle.js"></script>
<header>
<nav class="navbar fixed-top navbar-expand-sm navbar-dark bg-warning py-2" style="padding: 0rem 0rem;">
    <div class="container-fluid" style="width: 90%;">
        <a class="navbar-brand" href="./index.do">School Search</a>
    </div>
</nav>
</header>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>エラーが発生しました。</h2>
    <%
    // トップ画面へのURLを生成する。
    String topUrl = "return !isDoubleClick('" + request.getContextPath() + "/index.do')";
    %>
    <html:button property="topBtn" value="トップページへ" styleClass="btn btn-primary" onclick="<%=topUrl %>"/>
</section>
<footer class="py-2 bg-warning text-light">
    <div class="container-fluid" style="width: 90%;">
        <p class="text-center">
            <small>Copyright &copy;<%=new SimpleDateFormat("yyyy").format(new Timestamp(System.currentTimeMillis())) %> School Search, All Rights Reserved.</small>
        </p>
    </div>
</footer>
<script src="<%=request.getContextPath()%>/error/js/submit-control.js"></script>
</body>
</html>
