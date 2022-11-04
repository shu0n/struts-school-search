<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.text.SimpleDateFormat"%>
<footer class="py-2 bg-dark text-light">
    <div class="container-fluid" style="width: 90%;">
        <ul class="nav justify-content-center">
            <li class="nav-item">
                <a class="nav-link" href="./index.do">トップ</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="./aboutSite.do">サイトについて</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="./makeContactInput.do">お問合せ</a>
            </li>
        </ul>
        <p class="text-center">
            <small>Copyright &copy;<%=new SimpleDateFormat("yyyy").format(new Timestamp(System.currentTimeMillis())) %> School Search, All Rights Reserved.</small>
        </p>
    </div>
</footer>