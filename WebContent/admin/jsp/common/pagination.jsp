<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%
//リクエストから全ページ数(文字列型)、現在のページ(文字列型)、一覧画面のURL、パラメーター有無を取得する。
String strTotalPage = (String) request.getParameter("totalPage");
String strCurrentPage = (String) request.getParameter("currentPage");
String listUrl = (String) request.getParameter("listUrl");
String isPrecedingParamExist = (String) request.getParameter("isPrecedingParamExist");
//取得結果を判定する。
if(!StringUtils.isEmpty(strTotalPage) && !StringUtils.isEmpty(strCurrentPage)) {
    // 全ページ数(整数型)と現在のページ(整数型)を取得する。
    int totalPage = Integer.parseInt(strTotalPage);
    int currentPage = Integer.parseInt(strCurrentPage);

    // 現在のページの前後で表示するページの範囲を変数に格納する。
    int displayedRange = 2;
    // ページ番号を格納するリストを生成する。
    List<Integer> displayedPageList = new ArrayList<Integer>(); // ページ番号のみを格納するリスト
    List<String> displayedPageListWithDot = new ArrayList<String>(); // ドット表記(省略表記)を含んだリスト

    for(int i = 1; i <= totalPage; i++) {
        if(i == 1
                || i == totalPage
                || (i >= currentPage - displayedRange && i <= currentPage + displayedRange)) {
            displayedPageList.add(i);
        }
    }

    // 1つ前のページを格納する変数を生成する。
    int previousPage = 0;
    for(int i: displayedPageList) {
        if(previousPage > 0) {
            if(i - previousPage == 2) {
                displayedPageListWithDot.add(String.valueOf(previousPage + 1));
            } else if(i - previousPage != 1) {
                displayedPageListWithDot.add("...");
            }
        }
        displayedPageListWithDot.add(String.valueOf(i));
        previousPage = i;
    }
%>
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <%
            String listUrlWithParameter = "";
            // パラーメーター有無を判定する。
            if(StringUtils.isEmpty(isPrecedingParamExist)) {
                // パラメーターが存在しない場合
                listUrlWithParameter = listUrl + "?currentPage=";
            } else {
                // パラメーターが存在する場合
                listUrlWithParameter = listUrl + "&currentPage=";
            }
            if(currentPage == 1) {
            %>
                <li class="page-item disabled">
                    <a class="page-link" href="#">最初へ</a>
                </li>
                <li class="page-item disabled">
                    <a class="page-link" href="#">前へ</a>
                </li>
            <%
            } else {
                String firstUrlWithParameter = listUrlWithParameter + 1;
                String previousUrlWithParameter = listUrlWithParameter + String.valueOf(currentPage - 1);
            %>
                <li class="page-item">
                    <a class="page-link" href="<%=firstUrlWithParameter %>">最初へ</a>
                </li>
                <li class="page-item">
                    <a class="page-link" href="<%=previousUrlWithParameter %>">前へ</a>
                </li>
            <%
            }

            for(String str: displayedPageListWithDot) {
                if("...".equals(str)) {
            %>
                    <li class="page-item"><a class="page-link" href="#"><%=str %></a></li>
                <%
                } else {
                    String pageUrl = listUrlWithParameter +str;
                %>
                    <li class="page-item"><a class="page-link" href="<%=pageUrl %>"><%=str %></a></li>
            <%
                }
            }

            if(totalPage == 1 || totalPage - currentPage <= 0) {
            %>
                <li class="page-item disabled">
                    <a class="page-link" href="#">次へ</a>
                </li>
                <li class="page-item disabled">
                    <a class="page-link" href="#">最後へ</a>
                </li>
            <%
            } else {
                String nextUrlWithParameter = listUrlWithParameter + String.valueOf(currentPage + 1);
                String lastUrlWithParameter = listUrlWithParameter + totalPage;
            %>
                <li class="page-item">
                    <a class="page-link" href="<%=nextUrlWithParameter %>">次へ</a>
                </li>
                <li class="page-item">
                    <a class="page-link" href="<%=lastUrlWithParameter %>">最後へ</a>
                </li>
            <%
            }
            %>
        </ul>
    </nav>
<%
}
%>
