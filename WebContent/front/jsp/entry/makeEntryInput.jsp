<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="front.actionform.entry.FrontMakeEntryActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
FrontMakeEntryActionForm inForm = (FrontMakeEntryActionForm) session.getAttribute("FrontMakeEntryActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="申込(入力)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>申込(入力)</h2>
    <html:form action="/makeEntryConfirm" onsubmit="return validateBeforeSubmitEntry()">
        <div class="form-group row">
            <label class="col-md-3 col-form-label">スクール名</label>
            <div class="col-md-3" style="padding-top: calc(0.375rem + 1px);">
                <bean:write name="FrontMakeEntryActionForm" property="entrySchoolName"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 col-form-label">費用</label>
            <div class="col-md-3" style="padding-top: calc(0.375rem + 1px);">
                <bean:write name="FrontMakeEntryActionForm" property="schoolFee"/> 円
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 col-form-label">スクール登録者</label>
            <div class="col-md-3" style="padding-top: calc(0.375rem + 1px);">
                <%
                // スクール登録者アカウントIDの有無を判定する。
                if(inForm.getRegistrantAccountId() == 0) {
                    // 存在しない場合は"School Search"を表示する。
                %>
                    <p class="form-text">School Search</p>
                <%
                } else {
                    // 上記以外の場合はスクール登録者の姓と名を表示する。
                %>
                    <bean:write name="FrontMakeEntryActionForm" property="registrantLastName"/> <bean:write name="FrontMakeEntryActionForm" property="registrantFirstName"/>
                <%
                }
                %>
            </div>
        </div>
        <div class="form-group row">
            <label for="entryQuestion" class="col-md-3 col-form-label">質問<small class="form-text text-muted">1000文字以内で入力してください。</small></label>
            <div class="col-md-9">
                <html:textarea property="entryQuestion" styleClass="form-control" styleId="entryQuestion" rows="5" onblur="validateMaxLength('entryQuestion', 1000)"/>
                <div class="invalid-feedback">1000文字以内で入力してください。</div>
            </div>
        </div>
        <div class="form-group row justify-content-end">
            <div class="col-md-9">
                <%
                // スクール詳細画面へのURLを生成する。
                String viewDetailedSchoolUrl = "return !isDoubleClick('./viewDetailedSchool.do?schoolId=" + inForm.getEntrySchoolId() + "')";
                %>
                <html:button property="viewDetailedSchoolBtn" value="戻る" styleClass="btn btn-secondary" onclick="<%=viewDetailedSchoolUrl %>"/>
                <html:submit value="確認する" styleClass="btn btn-primary"/>
            </div>
        </div>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
<script src="<%=request.getContextPath()%>/front/js/common-form-validation.js"></script>
<script src="<%=request.getContextPath()%>/front/js/entry-form-validation.js"></script>
</body>
</html>
