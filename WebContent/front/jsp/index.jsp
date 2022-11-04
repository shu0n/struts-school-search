<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="actionform.SchoolExtendActionForm"%>
<%@ page import="actionform.CategoryActionForm"%>
<%@ page import="front.actionform.FrontIndexActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
FrontIndexActionForm inForm = (FrontIndexActionForm) session.getAttribute("FrontIndexActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="common/head.jsp">
    <jsp:param name="title" value="トップ"/>
</jsp:include>
<body >
<jsp:include page="common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2 class="text-center">新着スクール</h2>
    <a href="./listSchool.do"><p class="text-center">スクール一覧ページへ</p></a>
    <%
    // リストの要素数を判定する。
    if(inForm.getSchoolExtendFormList().size() == 0) {
        // 空要素の場合
    %>
        <p class="text-center">登録されたスクールはありません。</p>
    <%
    } else {
        // 上記以外の場合
    %>
        <div class="row justify-content-end justify-content-sm-start">
            <%
            for(SchoolExtendActionForm eachForm: inForm.getDisplayedSchoolList()) {
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
    <%
    }
    %>
</section>
<jsp:include page="common/footer.jsp"/>
</body>
</html>
