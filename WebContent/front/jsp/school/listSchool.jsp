<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="front.actionform.school.FrontListSchoolActionForm"%>
<%@ page import="actionform.SchoolExtendActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
FrontListSchoolActionForm inForm = (FrontListSchoolActionForm) session.getAttribute("FrontListSchoolActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="スクール一覧"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/bootstrap-multiselect.js"></script>
<script src="<%=request.getContextPath()%>/front/js/enable-multiselect.js"></script>
<section class="container-fluid pt-4" style="width: 90%;">
    <div class="card">
        <div class="card-header" role="tab" id="collapseListGroupHeading1">
            <h5 class="mb-0 text-center">
                <a href="#collapseListGroup1" class="collapsed text-body d-block p-3 m-n3" role="button" data-toggle="collapse" aria-expanded="false" aria-controls="collapseListGroup1">
                    スクールを検索する
                </a>
            </h5>
        </div>
        <div class="collapse" role="tabpanel" id="collapseListGroup1" aria-labelledby="collapseListGroupHeading1" aria-expanded="false">
            <div class="card-body">
                <html:form action="/searchSchool" onsubmit="return validateBeforeSubmitSearchSchool(this)">
                    <div class="form-group row">
                        <label for="schoolName" class="col-md-3 control-label">スクール名</label>
                        <div class="col-md-9">
                            <html:text property="likeSchoolName" styleClass="form-control" styleId="schoolName" onblur="validateMaxLength('schoolName', 50)"/>
                            <div class="invalid-feedback">50文字以内で入力してください。</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="minSchoolFee" class="col-md-3 control-label">費用</label>
                        <div class="col-md-4 input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">下限</span>
                            </div>
                            <html:text property="minSchoolFee" styleClass="form-control" styleId="minSchoolFee" onblur="validateSearchSchoolFee('minSchoolFee', 8)"/>
                            <div class="input-group-append">
                                <span class="input-group-text">円</span>
                            </div>
                            <div class="invalid-feedback">8文字以内の半角数値で入力してください。</div>
                        </div>
                        <div class="col-md-4 input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">上限</span>
                            </div>
                            <html:text property="maxSchoolFee" styleClass="form-control" styleId="maxSchoolFee" onblur="validateSearchSchoolFee('maxSchoolFee', 8)"/>
                            <div class="input-group-append">
                                <span class="input-group-text">円</span>
                            </div>
                            <div class="invalid-feedback">8文字以内の半角数値で入力してください。</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="schoolCategory" class="col-md-3 control-label">カテゴリー</label>
                        <div class="col-md-3">
                            <html:select property="schoolCategoryIdArray" styleClass="form-control multiselect" styleId="schoolCategory" multiple="true" onchange="controlActivateOrInactivateForOption('schoolCategory')">
                                <html:optionsCollection name="FrontListSchoolActionForm" property="schoolCategoryList" label="label" value="value"/>
                            </html:select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="schoolPrefecture" class="col-md-3 control-label">都道府県</label>
                        <div class="col-md-3">
                            <html:select property="schoolPrefectureIdArray" styleClass="form-control multiselect" styleId="schoolPrefecture" multiple="true">
                                <html:optionsCollection name="FrontListSchoolActionForm" property="schoolPrefectureList" label="label" value="value"/>
                            </html:select>
                        </div>
                    </div>
                    <div class="form-group row justify-content-end">
                        <div class="col-md-9">
                            <html:submit value="検索" styleClass="btn btn-primary"/>
                        </div>
                    </div>
                </html:form>
            </div>
        </div>
    </div>
</section>
<section class="container-fluid pt-4" style="width: 90%;">
    <p><%=inForm.getTotalForm() %> 件のスクールがヒットしました。</p>
    <%
    // 該当するスクールの件数を判定する。
    if(inForm.getTotalForm() > 0) {
        // 1件以上存在する場合
    %>
        <html:form action="/listSchool" styleClass="form-inline pb-3" style="flex-wrap: nowrap;" onsubmit="return !isDoubleSubmit()">
            <html:select property="sortKindForSchool" styleClass="custom-select">
                <html:optionsCollection property="sortKindForSchoolList" label="label" value="value"/>
            </html:select>
            <html:submit value="並び替える" styleClass="btn btn-primary"/>
            <html:hidden property="sortFlag" value="true"/>
        </html:form>
        <jsp:include page="../common/pagination.jsp">
            <jsp:param name="totalPage" value="<%=inForm.getTotalPage() %>"/>
            <jsp:param name="currentPage" value="<%=inForm.getCurrentPage() %>"/>
            <jsp:param name="listUrl" value="./listSchool.do"/>
        </jsp:include>
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
<script src="<%=request.getContextPath()%>/front/js/number-util.js"></script>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
<script src="<%=request.getContextPath()%>/front/js/common-form-validation.js"></script>
<script src="<%=request.getContextPath()%>/front/js/school-form-validation.js"></script>
<script src="<%=request.getContextPath()%>/front/js/school-form-control.js"></script>
</body>
</html>
