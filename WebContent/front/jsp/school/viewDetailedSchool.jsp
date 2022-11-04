<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="front.actionform.school.FrontViewDetailedSchoolActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="ja">
<%
// セッションからアクションフォームを取得する。
FrontViewDetailedSchoolActionForm inForm = (FrontViewDetailedSchoolActionForm) session.getAttribute("FrontViewDetailedSchoolActionForm");
%>
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="<%=inForm.getSchoolName() %>"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2><bean:write name="FrontViewDetailedSchoolActionForm" property="schoolName"/></h2>
    <div>
        <p style="margin-bottom: 0rem;">
            <small><strong>登録：</strong><bean:write name="FrontViewDetailedSchoolActionForm" property="strSchoolRegisteredAt"/></small><br class="d-sm-none">
            <small><strong>更新：</strong><bean:write name="FrontViewDetailedSchoolActionForm" property="strSchoolUpdatedAt"/></small>
        </p>
    </div>
    <div class="py-2">
        <div>
            <div class="row mb-4">
                <div id="main_visual" class="col-md-6 pt-4 carousel slide" data-ride="carousel">
                    <%
                    // 詳細画面画像のパスを格納したリストを取得する。
                    List<String> imagePathList = inForm.getDetailImageFilePathList();
                    // リストの中身を判定する。
                    if(imagePathList.isEmpty()) {
                        // 空要素の場合はデフォルト画像のパスをsrcタグに設定する。
                        String detailFilePath = request.getContextPath() + "/img/site/no_image_gray.png";
                    %>
                        <div class="carousel-inner">
                            <div class="carousel-item active">
                                <html:img styleClass="img-fluid" src="<%=detailFilePath %>"/>
                            </div>
                        </div>
                    <%
                    } else {
                        // 上記以外の場合は詳細画像のパスを取得してsrcタグに設定する。
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
                                    <th class="text-nowrap">カテゴリー</th>
                                    <td><bean:write name="FrontViewDetailedSchoolActionForm" property="schoolCategoryName"/></td>
                                </tr>
                                <tr>
                                    <th class="text-nowrap">費用</th>
                                    <td><bean:write name="FrontViewDetailedSchoolActionForm" property="schoolFee"/> 円</td>
                                </tr>
                                <tr>
                                    <th class="text-nowrap">費用補足</th>
                                    <td><bean:write name="FrontViewDetailedSchoolActionForm" property="supplementaryFee"/></td>
                                </tr>
                                <tr>
                                    <th class="text-nowrap">サイト</th>
                                    <td>
                                        <a href="<bean:write name="FrontViewDetailedSchoolActionForm" property="schoolUrl"/>" target="_blank">
                                            <bean:write name="FrontViewDetailedSchoolActionForm" property="schoolUrl"/>
                                        </a>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="text-nowrap">都道府県</th>
                                    <td>
                                        <bean:write name="FrontViewDetailedSchoolActionForm" property="schoolPrefectureName"/>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="text-nowrap">登録者</th>
                                    <%
                                    // 登録者アカウントIDが登録されているかを判定する。
                                    if(inForm.getRegistrantAccountId() == 0) {
                                        // 登録されいない場合は"School Search"を表示する。
                                    %>
                                        <td>School Search</td>
                                    <%
                                    } else {
                                        // 上記以外の場合は登録者プロフィール画面のURLを生成して登録者姓と登録者名を表示する。
                                        String registrantProfileUrl = "./viewRegistrantProfile.do?schoolId=" + inForm.getSchoolId();
                                    %>
                                        <td>
                                            <a href="<%=registrantProfileUrl %>">
                                                <bean:write name="FrontViewDetailedSchoolActionForm" property="registrantLastName"/>
                                                <bean:write name="FrontViewDetailedSchoolActionForm" property="registrantFirstName"/>
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
                    // 申込済フラグを判定する。
                    if(inForm.isEntriedFlag()) {
                        // 申込済の場合は申込済であることを表示する。
                    %>
                        <html:button property="makeEntryBtn" value="申し込み済" styleClass="btn btn-secondary" disabled="true"/>
                    <%
                    // スクール申込可否を判定する。
                    } else if("0".equals(inForm.getSchoolEntryPropriety())) {
                        // 不可の場合
                    %>
                        <html:button property="makeEntryBtn" value="申し込み不可" styleClass="btn btn-secondary" disabled="true"/>
                    <%
                    } else {
                        // 上記以外の場合は申込 入力画面へのURLを生成する。
                        String makeEntryUrl = "isDoubleClick('./makeEntryInput.do?schoolId=" + inForm.getSchoolId() + "')";
                    %>
                        <html:button property="makeEntryBtn" value="申し込む" styleClass="btn btn-primary" onclick="<%=makeEntryUrl %>"/>
                    <%
                    }

                    // お気に入り追加済フラグを判定する。
                    if(inForm.isFavoriteFlag()) {
                        // 追加されている場合は削除するためのURLを生成してボタンを表示する。
                        String deleteFavoriteUrl = "return !isDoubleClick('./deleteFavorite.do?schoolId=" + inForm.getSchoolId() + "')";
                    %>
                        <html:button property="deleteFavoriteBtn" value="お気に入りから削除" styleClass="btn btn-secondary" onclick="<%=deleteFavoriteUrl %>"/>
                    <%
                    } else {
                        // 上記以外の場合は追加するためのURLを生成してボタンを表示する。
                        String addFavoriteUrl = "return !isDoubleClick('./addFavorite.do?schoolId=" + inForm.getSchoolId() + "')";
                    %>
                        <html:button property="addFavoriteBtn" value="お気に入りに追加" styleClass="btn btn-primary" onclick="<%=addFavoriteUrl %>"/>
                    <%
                    }
                    %>
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
                                    <p style="white-space: pre-wrap;"><bean:write name="FrontViewDetailedSchoolActionForm" property="schoolDescription"/></p>
                                </td>
                            <tr>
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
                                    〒<bean:write name="FrontViewDetailedSchoolActionForm" property="schoolZipCode1"/>-<bean:write name="FrontViewDetailedSchoolActionForm" property="schoolZipCode2"/><br>
                                    <bean:write name="FrontViewDetailedSchoolActionForm" property="schoolPrefectureName"/> <bean:write name="FrontViewDetailedSchoolActionForm" property="schoolCity"/> <bean:write name="FrontViewDetailedSchoolActionForm" property="schoolAddress1"/> <bean:write name="FrontViewDetailedSchoolActionForm" property="schoolAddress2"/>
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
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
</body>
</html>
