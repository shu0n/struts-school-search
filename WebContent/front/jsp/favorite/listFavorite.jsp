<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="front.actionform.favorite.FrontListFavoriteActionForm"%>
<%@ page import="actionform.FavoriteExtendActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
FrontListFavoriteActionForm inForm = (FrontListFavoriteActionForm) session.getAttribute("FrontListFavoriteActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="お気に入りスクール一覧"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>お気に入りスクール一覧</h2>
    <p><%=inForm.getTotalForm() %> 件のスクールをお気に入りに登録しています。</p>
    <%
    // 該当するお気に入りの件数を判定する。
    if(inForm.getTotalForm() > 0) {
        // 1件以上存在する場合
    %>
        <jsp:include page="../common/pagination.jsp">
            <jsp:param name="totalPage" value="<%=inForm.getTotalPage() %>"/>
            <jsp:param name="currentPage" value="<%=inForm.getCurrentPage() %>"/>
            <jsp:param name="listUrl" value="./listSchool.do"/>
        </jsp:include>
        <div class="row justify-content-end justify-content-sm-start">
            <%
            for(FavoriteExtendActionForm eachForm: inForm.getDisplayedFavoriteList()) {
            %>
                <div class="col-sm-3 pb-3">
                    <div class="card h-100">
                        <div class="card-img-top">
                            <%
                            // リクエストから一覧画面画像のパスを取得する。
                            String summaryImageFilePath = (String) request.getAttribute("summaryImageFilePath");
                            // 一覧画面画像のパスが存在するかを判定する。
                            if(StringUtils.isEmpty(summaryImageFilePath)) {
                                // 存在しない場合はデフォルト画像を表示する。
                                String summaryFilePath = request.getContextPath() + "/img/site/no_image_gray.png";
                            %>
                                <html:img styleClass="img-fluid" src="<%=summaryFilePath %>"/>
                            <%
                            } else {
                                // 上記以外の場合はパスを整形してimgタグのsrc属性に設定する。
                                String summaryFilePath = request.getContextPath() + summaryImageFilePath;
                            %>
                                <html:img styleClass="img-fluid" src="<%=summaryFilePath %>"/>
                            <%
                            }
                            %>
                        </div>
                        <div class="card-body">
                            <h5 class="card-title"><%=eachForm.getSchoolName() %></h5>
                            <p class="card-text"><%=eachForm.getSchoolCategoryName() %></p>
                            <p class="card-text"><%=eachForm.getSchoolPrefectureName() %></p>
                            <p class="card-text" style="white-space: pre-wrap;"><%=eachForm.getSchoolSummary() %></p>
                        </div>
                        <a href="./viewDetailedSchool.do?schoolId=<%=eachForm.getSchoolId() %>" class="btn btn-primary">詳細を見る</a>
                    </div>
                </div>
            <%
            }
            %>
        </div>
            <jsp:include page="../common/pagination.jsp">
                <jsp:param name="totalPage" value="<%=inForm.getTotalPage() %>"/>
                <jsp:param name="currentPage" value="<%=inForm.getCurrentPage() %>"/>
                <jsp:param name="listUrl" value="./listSchool.do"/>
            </jsp:include>
    <%
    }
    %>
</section>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>
