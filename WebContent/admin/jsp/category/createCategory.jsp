<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="admin.actionform.category.AdminCreateCategoryActionForm"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="カテゴリー作成(入力)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>カテゴリー作成</h2>
    <html:form action="/createCategoryComplete" enctype="multipart/form-data" onsubmit="return validateBeforeSubmitCategory()">
        <div class="form-group row">
            <label for="categoryName" class="col-md-3 col-form-label">カテゴリー名</label>
            <div class="col-md-9">
                <logic:messagesNotPresent>
                    <html:text property="categoryName" styleClass="form-control" styleId="categoryName" onblur="validateRequiredText('categoryName', 50)"/>
                    <div class="invalid-feedback">50文字以内で入力してください。</div>
                </logic:messagesNotPresent>
                <logic:messagesPresent>
                    <html:text property="categoryName" styleClass="form-control is-invalid" styleId="categoryName" onblur="validateRequiredText('categoryName', 50)"/>
                    <div class="invalid-feedback" id="errorCategoryName">
                        <html:messages id="msg">
                            <bean:write name="msg" ignore="true"/>
                        </html:messages>
                    </div>
                </logic:messagesPresent>
            </div>
        </div>
        <div class="form-group row">
            <label for="seniorCategoryId" class="col-md-3 col-form-label">上位カテゴリー名</label>
            <div class="col-md-3">
                <html:select property="seniorCategoryId" styleClass="custom-select" styleId="seniorCategoryId">
                    <html:optionsCollection property="seniorCategoryList" label="label" value="value"/>
                </html:select>
            </div>
        </div>
        <fieldset class="form-group">
            <div class="row">
                <legend class="col-md-3 col-form-label">カテゴリーステータス</legend>
                <div class="col-md-9" style="padding-top: calc(0.375rem + 1px);">
                    <%
                    int i = 1;
                    %>
                    <logic:iterate id="categoryStatusMap" name="AdminCreateCategoryActionForm" property="categoryStatusMap">
                        <div class="form-check form-check-inline">
                            <%
                            // for属性の値を生成する。
                            String categoryStatusIndex = "categoryStatus" + i;
                            i += 1;
                            %>
                            <html:radio idName="categoryStatusMap" property="categoryStatus" value="key" styleClass="form-check-input" styleId="<%=categoryStatusIndex %>" onblur="validateRadioId('categoryStatus')"/>
                            <label class="form-check-label" for="<%=categoryStatusIndex %>"><bean:write name="categoryStatusMap" property="value"/></label>
                        </div>
                    </logic:iterate>
                    <div class="invalid-feedback">カテゴリーステータスを選択してください。</div>
                </div>
            </div>
        </fieldset>
        <div class="form-group row justify-content-end">
            <div class="col-md-9">
                <html:button property="listCategoryBtn" value="キャンセル" styleClass="btn btn-secondary" onclick="return !isDoubleClick('./listCategory.do')"/>
                <html:submit value="作成する" styleClass="btn btn-primary"/>
            </div>
        </div>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/admin/js/set-form-parts.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/submit-control.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/common-form-validation.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/category-form-validation.js"></script>
</body>
</html>
