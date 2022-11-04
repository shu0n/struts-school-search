<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="model.login.LoginStatusModel"%>
<%
// ログイン状態を取得する。
boolean loginStatus = new LoginStatusModel().isLogined(session);
%>
<script src="<%=request.getContextPath()%>/front/js/jquery-3.6.0.min.js"></script>
<script src="<%=request.getContextPath()%>/front/js/bootstrap.bundle.js"></script>
<header>
<nav class="navbar fixed-top navbar-expand-sm navbar-dark bg-dark py-2" style="padding: 0rem 0rem;">
    <div class="container-fluid" style="width: 90%;">
        <a class="navbar-brand" href="./index.do">School Search</a>
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="./listSchool.do">スクール一覧</a>
                </li>
                <%
                // ログイン状態を判定する。
                if(!loginStatus) {
                    // 未ログインの場合に表示するメニュー
                %>
                    <li class="nav-item">
                        <a class="nav-link" href="./createAccountInput.do">アカウント作成</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="./login.do">ログイン</a>
                    </li>
                <%
                } else {
                    // ログイン済の場合に表示するメニュー
                %>
                    <li class="nav-item">
                        <a class="nav-link" href="./manageAccount.do">アカウント管理</a>
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
            if(!loginStatus) {
                // 未ログインの場合は"ゲスト"を表示する。
            %>
                <span class="navbar-text ml-auto">ようこそ ゲスト さま</span>
            <%
            } else {
                // 上記以外の場合は"姓"を表示する。
            %>
                <span class="navbar-text ml-auto">ようこそ <%=new LoginStatusModel().getLastName(session)%> さま</span>
            <%
            }
            %>
        </div>
    </div>
</nav>
</header>
