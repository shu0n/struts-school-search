<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<head>
<meta charset="UTF-8">
<meta charset="utf-8">
<meta name="Viewport" content="width=device-width, initial-scale=1, shrink-tofit=no">
<link rel="stylesheet" href="<%=request.getContextPath() %>/admin/css/bootstrap.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/admin/css/bootstrap-multiselect.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/admin/css/common.css">
<%
// リクエストから画面タイトルを取得する。
String title = (String) request.getParameter("title");
// 取得結果を判定する。
if(StringUtils.isEmpty(title)) {
    // 取得できない場合はサイト名のみ表示する。
%>
    <title>School Search 管理画面</title>
<%
} else {
    // 上記以外の場合は画面タイトルにサイトタイトルを結合して表示する。
%>
    <title><%=title %> | School Search 管理画面</title>
<%
}
%>
</head>