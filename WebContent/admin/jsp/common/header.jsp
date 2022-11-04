<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="model.login.LoginAdminStatusModel"%>
<%
// ログイン状態を取得する。
boolean loginAdminStatus = new LoginAdminStatusModel().isAdminLogined(session);
%>
<script src="<%=request.getContextPath()%>/admin/js/jquery-3.6.0.min.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/bootstrap.bundle.js"></script>
<header>
<nav class="navbar fixed-top navbar-expand-sm navbar-dark bg-secondary py-2" style="padding: 0rem 0rem;">
    <div class="container-fluid" style="width: 90%;">
        <a class="navbar-brand" href="./index.do">School Search 管理画面</a>
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <%
                // ログイン状態を判定する。
                if(loginAdminStatus) {
                    // ログイン済の場合に表示するメニュー
                %>
                    <li class="nav-item">
                        <a class="nav-link" href="./listAccount.do">アカウント一覧</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="./listSchool.do">スクール一覧</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="./listReceivedEntry.do">申込受付一覧</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="./logout.do">ログアウト</a>
                    </li>
                <%
                }
                %>
            </ul>
        </div>
        <div class="collapse navbar-collapse" id="navbarText">
            <%
            // ログイン状態を判定する。
            if(loginAdminStatus) {
                // ログイン済の場合は"管理者姓"を表示する。
            %>
                <span class="navbar-text ml-auto">ようこそ <%=new LoginAdminStatusModel().getAdminLastName(session)%> さま</span>
            <%
            }
            %>
        </div>
    </div>
</nav>
</header>
