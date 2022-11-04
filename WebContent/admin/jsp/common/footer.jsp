<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.text.SimpleDateFormat"%>
<footer class="py-2 bg-secondary text-light">
    <div class="container-fluid" style="width: 90%;">
        <p class="text-center">
            <small>Copyright &copy;<%=new SimpleDateFormat("yyyy").format(new Timestamp(System.currentTimeMillis())) %> School Search, All Rights Reserved.</small>
        </p>
    </div>
</footer>