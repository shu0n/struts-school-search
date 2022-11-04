<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="front.actionform.school.FrontEditSchoolActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
FrontEditSchoolActionForm inForm = (FrontEditSchoolActionForm) session.getAttribute("FrontEditSchoolActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="スクール編集(プレビュー)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>スクール編集<br class="d-sm-none" />(プレビュー)</h2>
    <div>
        <p>
            <strong>スクールID：</strong><bean:write name="FrontEditSchoolActionForm" property="schoolId"/><br>
            <strong>公開可否：</strong><bean:write name="FrontEditSchoolActionForm" property="schoolReleaseProprietyName"/><br class="d-sm-none">
            <strong>申込可否：</strong><bean:write name="FrontEditSchoolActionForm" property="schoolEntryProprietyName"/>
        </p>
    </div>
</section>
<section class="container-fluid pt-4" style="width: 90%;">
    <h3>一覧画面</h3>
    <div class="row justify-content-end justify-content-sm-start">
        <div class="col-sm-3 pb-3">
            <div class="card h-100">
                <div class="card-img-top">
                <%
                // リクエストから一覧画面画像のパスを取得する。
                String summaryImageFilePath = (String) request.getAttribute("summaryImageFilePath");
                // 一覧画面画像のパスが存在するかを判定する。
                if(StringUtils.isEmpty(summaryImageFilePath)) {
                    // 存在しない場合はデフォルト画像を表示する。
                    String detailFilePath = request.getContextPath() + "/img/site/no_image_gray.png";
                %>
                    <html:img styleClass="img-fluid" src="<%=detailFilePath %>"/>
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
                    <h5 class="card-title"><bean:write name="FrontEditSchoolActionForm" property="schoolName"/></h5>
                    <p class="card-text"><bean:write name="FrontEditSchoolActionForm" property="schoolCategoryName"/></p>
                    <p class="card-text"><bean:write name="FrontEditSchoolActionForm" property="schoolPrefectureName"/></p>
                    <p class="card-text" style="white-space: pre-wrap;"><bean:write name="FrontEditSchoolActionForm" property="schoolSummary"/></p>
                </div>
                <a href="#" class="btn btn-primary disabled">詳細を見る</a>
            </div>
        </div>
    </div>
</section>
<section class="container-fluid pt-4" style="width: 90%;">
    <h3>詳細画面</h3>
    <h2><bean:write name="FrontEditSchoolActionForm" property="schoolName"/></h2>
        <div>
        <p>
            <small><strong>登録：</strong><bean:write name="FrontEditSchoolActionForm" property="strSchoolRegisteredAt"/></small><br class="d-sm-none">
            <small><strong>更新：</strong>YYYY年MM月DD日 hh時mm分ss秒</small>
        </p>
    </div>
    <div class="py-2">
        <div>
            <div class="row mb-4">
                <div id="main_visual" class="col-md-6 carousel slide" data-ride="carousel">
                    <%
                    // 詳細画面画像のパスを格納するためのリストを生成する。
                    List<String> imagePathList = new ArrayList<String>();
                    for(int i = 1; i < 9; i++) {
                        String detailImageFilePath = (String) request.getAttribute("detailImage" + i + "FilePath");
                        if(!StringUtils.isEmpty(detailImageFilePath)) {
                            imagePathList.add(detailImageFilePath);
                        }
                    }

                    // リストの要素数を判定する。
                    if(imagePathList.isEmpty()) {
                        // 空要素の場合はデフォルト画像を表示する。
                        String detailFilePath = request.getContextPath() + "/img/site/no_image_gray.png";
                    %>
                        <div class="carousel-inner">
                            <div class="carousel-item active">
                                <html:img styleClass="img-fluid" src="<%=detailFilePath %>"/>
                            </div>
                        </div>
                    <%
                    } else {
                        // 上記以外の場合
                    %>
                        <ol class="carousel-indicators">
                            <%
                            // リストの要素数分、処理を繰り返す。
                            for(int i = 0; i < imagePathList.size(); i++) {
                                // インデックスを判定する。
                                if(i == 0) {
                                    // 0の場合
                            %>
                                    <li data-target="#main_visual" data-slide-to="<%=i %>" class="active"></li>
                                <%
                                } else {
                                    // 上記以外の場合
                                %>
                                    <li data-target="#main_visual" data-slide-to="<%=i %>"></li>
                            <%
                                }
                            }
                            %>
                        </ol>
                        <div class="carousel-inner">
                            <%
                            // 詳細画像のパスを取得してsrc属性に設定する。
                            for(int i = 0; i < imagePathList.size(); i++) {
                                String detailFilePath = request.getContextPath() + imagePathList.get(i);
                                // 画面のインデックスを判定する。
                                if(i == 0) {
                                    // 0の場合
                            %>
                                    <div class="carousel-item active">
                                <%
                                } else {
                                    // 上記以外の場合
                                %>
                                    <div class="carousel-item">
                                <%
                                }
                                %>
                                    <html:img styleClass="img-fluid" src="<%=detailFilePath %>"/>
                                </div>
                            <%
                            }
                            %>
                        </div>
                        <a class="carousel-control-prev" href="#main_visual" role="button" data-slide="prev"></a>
                        <a class="carousel-control-next" href="#main_visual" role="button" data-slide="next"></a>
                    <%
                    }
                    %>
                </div>
                <div class="col-md-6 pt-4">
                    <h4 class="mb-3">概要</h4>
                    <div class="table-responsive">
                        <table class="table">
                            <tbody>
                                <tr>
                                    <th>カテゴリー</th>
                                    <td><bean:write name="FrontEditSchoolActionForm" property="schoolCategoryName"/></td>
                                </tr>
                                <tr>
                                    <th>費用</th>
                                    <td><bean:write name="FrontEditSchoolActionForm" property="schoolFee"/> 円</td>
                                </tr>
                                <tr>
                                    <th>費用補足</th>
                                    <td><bean:write name="FrontEditSchoolActionForm" property="supplementaryFee"/></td>
                                </tr>
                                <tr>
                                    <th class="text-nowrap">サイト</th>
                                    <td>
                                        <a href="<bean:write name="FrontEditSchoolActionForm" property="schoolUrl"/>" target="_blank">
                                            <bean:write name="FrontEditSchoolActionForm" property="schoolUrl"/>
                                        </a>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="text-nowrap">都道府県</th>
                                    <td>
                                        <bean:write name="FrontEditSchoolActionForm" property="schoolPrefectureName"/>
                                    </td>
                                </tr>
                                <tr>
                                    <th>登録者</th>
                                    <%
                                    // 登録者アカウントIDが登録されているかを判定する。
                                    if(inForm.getRegistrantAccountId() == 0) {
                                        // 登録されいない場合は"School Search"を表示する。
                                    %>
                                        <td>School Search</td>
                                    <%
                                    } else {
                                        // 上記以外の場合は登録者プロフィール画面のURLを生成して登録者姓と登録者名を表示する。
                                    %>
                                        <td>
                                            <a href="#" class="disabled">
                                                <bean:write name="FrontEditSchoolActionForm" property="registrantLastName"/>
                                                <bean:write name="FrontEditSchoolActionForm" property="registrantFirstName"/>
                                            </a>
                                        </td>
                                    <%
                                    }
                                    %>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <%
                    // スクール申込可否を判定する。
                    if("0".equals(inForm.getSchoolEntryPropriety())) {
                        // 不可の場合
                    %>
                        <html:button property="makeEntryBtn" value="申し込み不可" styleClass="btn btn-secondary" disabled="true"/>
                    <%
                    } else {
                        // 上記以外の場合
                    %>
                        <html:button property="makeEntryBtn" value="申し込む" styleClass="btn btn-primary" onclick="#" disabled="true"/>
                    <%
                    }
                    %>
                    <html:button property="addFavoriteBtn" value="お気に入り追加" styleClass="btn btn-primary" onclick="#" disabled="true"/>
                </div>
            </div>
        </div>
    </div>
    <div class="py-2">
        <div>
            <div class="row mb-4">
                <div class="col-md-12 pt-4">
                    <h4 class="mb-3">詳細</h4>
                    <table class="table">
                        <tbody>
                            <tr>
                                <td>
                                    <p style="white-space: pre-wrap;"><bean:write name="FrontEditSchoolActionForm" property="schoolDescription"/></p>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="py-2">
        <div>
            <div class="row mb-4">
                <div class="col-md-6 pt-4">
                    <h4 class="mb-3">住所</h4>
                    <table class="table">
                        <tbody>
                            <tr>
                                <td>
                                    〒<bean:write name="FrontEditSchoolActionForm" property="schoolZipCode1"/>-<bean:write name="FrontEditSchoolActionForm" property="schoolZipCode2"/><br>
                                    <bean:write name="FrontEditSchoolActionForm" property="schoolPrefectureName"/> <bean:write name="FrontEditSchoolActionForm" property="schoolCity"/> <bean:write name="FrontEditSchoolActionForm" property="schoolAddress1"/> <bean:write name="FrontEditSchoolActionForm" property="schoolAddress2"/>
                                </td>
                            <tr>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-6 pt-4">
                    <h4 class="mb-3">アクセス</h4>
                    <div class="embed-responsive embed-responsive-4by3">
                        <%
                        // スクール住所2を判定する。
                        if(StringUtils.isEmpty(inForm.getSchoolAddress2())) {
                            // NULLまたは空文字の場合は空文字を設定する。
                            inForm.setSchoolAddress2("");
                        }

                        // GoogleMapで指定する住所を生成する。
                        String qAddress = inForm.getSchoolPrefectureName() + inForm.getSchoolCity() + inForm.getSchoolAddress1() + inForm.getSchoolAddress2();
                        %>
                        <iframe src="https://maps.google.co.jp/maps?output=embed&q=<%=qAddress %>&t=m&z=15" width="800" height="600" frameborder="0" style="border:0" allowfullscreen></iframe>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<section class="container-fluid pt-4" style="width: 90%;">
    <html:form action="/editSchoolComplete" onsubmit="return !isDoubleSubmit()">
        <div class="form-group row justify-content-end">
            <div class="col-md-9">
                <html:submit property="dispatch" styleClass="btn btn-secondary">
                    <bean:message key="button.fix"/>
                </html:submit>
                <html:submit property="dispatch" styleClass="btn btn-primary">
                    <bean:message key="button.edit"/>
                </html:submit>
            </div>
        </div>
    <%
       String summaryImageFileName = (String)request.getAttribute("summaryImageFileName");
       if(!StringUtils.isEmpty(summaryImageFileName)) {
    %>
           <html:hidden property="summaryImageFileNameUpdate" value="<%=summaryImageFileName %>"/>
    <% } %>
    <%
       for(int i = 1; i < 9; i++) {
           String detailImageFileName = (String)request.getAttribute("detailImage" + i + "FileName");
           if(!StringUtils.isEmpty(detailImageFileName)) {
               String property = "detailImage" + i + "FileNameUpdate";
    %>
           <html:hidden property="<%=property %>" value="<%=detailImageFileName %>"/>
    <%
           }
       }
    %>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
</body>
</html>
