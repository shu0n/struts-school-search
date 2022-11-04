<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="admin.actionform.account.AdminListAccountActionForm"%>
<%@ page import="actionform.AccountExtendActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
AdminListAccountActionForm inForm = (AdminListAccountActionForm) session.getAttribute("AdminListAccountActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="アカウント一覧"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<script src="<%=request.getContextPath()%>/admin/js/bootstrap-multiselect.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/enable-multiselect.js"></script>
<section class="container-fluid pt-4" style="width: 90%;">
    <div class="card">
        <div class="card-header" role="tab" id="collapseListGroupHeading1">
            <h5 class="mb-0 text-center">
                <a href="#collapseListGroup1" class="collapsed text-body d-block p-3 m-n3" role="button" data-toggle="collapse" aria-expanded="false" aria-controls="collapseListGroup1">
                    アカウントを検索する
                </a>
            </h5>
        </div>
        <div class="collapse" role="tabpanel" id="collapseListGroup1" aria-labelledby="collapseListGroupHeading1" aria-expanded="false">
            <div class="card-body">
                <html:form action="/searchAccount" onsubmit="return validateBeforeSubmitSearchAccount(this)">
                    <div class="form-group row">
                        <label for="strAccountId" class="col-md-3 control-label">アカウントID</label>
                        <div class="col-md-9">
                            <html:text property="strAccountId" styleClass="form-control" styleId="strAccountId" onblur="validateAccountIds('strAccountId', 50)"/>
                            <div class="invalid-feedback">ID,ID,...,IDの形式で入力していください。※IDとカンマは半角数字</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="lastName" class="col-md-3 control-label">姓名</label>
                        <div class="col-md-3 input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">姓</span>
                            </div>
                            <html:text property="likeLastName" styleClass="form-control" styleId="lastName" onblur="validateMaxLength('lastName', 20)"/>
                            <div class="invalid-feedback">20文字以内で入力してください。</div>
                        </div>
                        <div class="col-md-3 input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">名</span>
                            </div>
                            <html:text property="likeFirstName" styleClass="form-control" styleId="firstName" onblur="validateMaxLength('firstName', 20)"/>
                            <div class="invalid-feedback">20文字以内で入力してください。</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="lastNameKana" class="col-md-3 control-label">姓名(フリガナ)</label>
                        <div class="col-md-3 input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">セイ</span>
                            </div>
                            <html:text property="likeLastNameKana" styleClass="form-control" styleId="lastNameKana" onblur="validateTextKana('lastNameKana', 40)"/>
                            <div class="invalid-feedback">40文字以内で入力してください。</div>
                        </div>
                        <div class="col-md-3 input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">メイ</span>
                            </div>
                            <html:text property="likeFirstNameKana" styleClass="form-control" styleId="firstNameKana" onblur="validateTextKana('firstNameKana', 40)"/>
                            <div class="invalid-feedback">40文字以内で入力してください。</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="mailAddress" class="col-md-3 control-label">メールアドレス</label>
                        <div class="col-md-9">
                            <html:text property="likeMailAddress" styleClass="form-control" styleId="mailAddress" onblur="validateMailAddress('mailAddress')"/>
                            <div class="invalid-feedback">メールアドレスの形式と一致しません。</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="selectYear1" class="col-md-3 col-form-label">生年月日(From)</label>
                        <div class="col-md-3 input-group">
                            <html:select property="fromBirthYear" styleClass="custom-select" styleId="selectYear1"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectYear1">年</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="fromBirthMonth" styleClass="custom-select" styleId="selectMonth1"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectMonth1">月</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="fromBirthDay" styleClass="custom-select" styleId="selectDay1"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectDay1">日</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="selectYear2" class="col-md-3 col-form-label">生年月日(To)</label>
                        <div class="col-md-3 input-group">
                            <html:select property="toBirthYear" styleClass="custom-select" styleId="selectYear2"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectYear1">年</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="toBirthMonth" styleClass="custom-select" styleId="selectMonth2"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectMonth1">月</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="toBirthDay" styleClass="custom-select" styleId="selectDay2"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectDay1">日</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="selectYear3" class="col-md-3 col-form-label">作成日時(From)</label>
                        <div class="col-md-3 input-group">
                            <html:select property="fromAccountCreatedYear" styleClass="custom-select" styleId="selectYear3"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectYear3">年</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="fromAccountCreatedMonth" styleClass="custom-select" styleId="selectMonth3"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectMonth3">月</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="fromAccountCreatedDay" styleClass="custom-select" styleId="selectDay3"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectDay3">日</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="selectYear4" class="col-md-3 col-form-label">作成日時(To)</label>
                        <div class="col-md-3 input-group">
                            <html:select property="toAccountCreatedYear" styleClass="custom-select" styleId="selectYear4"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectYear4">年</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="toAccountCreatedMonth" styleClass="custom-select" styleId="selectMonth4"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectMonth4">月</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="toAccountCreatedDay" styleClass="custom-select" styleId="selectDay4"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectDay4">日</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="selectYear5" class="col-md-3 col-form-label">更新日時(From)</label>
                        <div class="col-md-3 input-group">
                            <html:select property="fromAccountUpdatedYear" styleClass="custom-select" styleId="selectYear5"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectYear5">年</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="fromAccountUpdatedMonth" styleClass="custom-select" styleId="selectMonth5"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectMonth5">月</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="fromAccountUpdatedDay" styleClass="custom-select" styleId="selectDay5"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectDay5">日</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="selectYear6" class="col-md-3 col-form-label">更新日時(From)</label>
                        <div class="col-md-3 input-group">
                            <html:select property="toAccountUpdatedYear" styleClass="custom-select" styleId="selectYear6"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectYear6">年</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="toAccountUpdatedMonth" styleClass="custom-select" styleId="selectMonth6"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectMonth6">月</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="toAccountUpdatedDay" styleClass="custom-select" styleId="selectDay6"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectDay6">日</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="accountStatusId" class="col-md-3 control-label">アカウントステータス</label>
                        <div class="col-md-3">
                            <html:select property="accountStatusArray" styleClass="form-control multiselect" styleId="accountStatusId" multiple="true">
                                <html:optionsCollection name="AdminListAccountActionForm" property="accountStatusList" label="label" value="value"/>
                            </html:select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="sexId" class="col-md-3 control-label">性別</label>
                        <div class="col-md-3">
                            <html:select property="sexIdArray" styleClass="form-control multiselect" styleId="sexId" multiple="true">
                                <html:optionsCollection name="AdminListAccountActionForm" property="sexList" label="label" value="value"/>
                            </html:select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="prefectureId" class="col-md-3 control-label">都道府県</label>
                        <div class="col-md-3">
                            <html:select property="prefectureIdArray" styleClass="form-control multiselect" styleId="prefectureId" multiple="true">
                                <html:optionsCollection name="AdminListAccountActionForm" property="prefectureList" label="label" value="value"/>
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
    <html:button property="registerBtn" value="新規アカウント登録" styleClass="btn btn-primary btn-sm mt-1" onclick="return !isDoubleClick('./createAccountInput.do')"/>
    <html:button property="csvDownloadBtn" value="CSVダウンロード" styleClass="btn btn-secondary btn-sm mt-1" onclick="return !isDoubleClick('./downloadAccountCsv.do')"/>
    <logic:messagesPresent>
        <div class="alert alert-danger alert-dismissible fade show text-left" role="alert">
            <html:messages id="msg">
                <bean:write name="msg" ignore="true"/>
            </html:messages>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </logic:messagesPresent>
</section>
<section class="container-fluid pt-4" style="width: 90%;">
    <p><%=inForm.getTotalForm() %>件のアカウントがヒットしました。</p>
    <%
    // 該当するアカウントの件数を判定する。
    if(inForm.getTotalForm() > 0) {
        // 1件以上存在する場合
    %>
        <html:form action="/listAccount" styleClass="form-inline pb-3" style="flex-wrap: nowrap;" onsubmit="return !isDoubleSubmit()">
            <html:select property="sortKindForAccount" styleClass="custom-select">
                <html:optionsCollection property="sortKindForAccountList" label="label" value="value"/>
            </html:select>
            <html:submit value="並び替える" styleClass="btn btn-primary"/>
            <html:hidden property="sortFlag" value="true"/>
        </html:form>
        <jsp:include page="../common/pagination.jsp">
            <jsp:param name="totalPage" value="<%=inForm.getTotalPage() %>"/>
            <jsp:param name="currentPage" value="<%=inForm.getCurrentPage() %>"/>
            <jsp:param name="listUrl" value="./listAccount.do"/>
        </jsp:include>
       <div class="table-responsive-md">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col" class="text-nowrap text-center">ID</th>
                        <th scope="col" class="text-nowrap text-center">姓名</th>
                        <th scope="col" class="text-nowrap text-center">アカウントステータス</th>
                        <th scope="col" class="text-nowrap text-center">作成日時</th>
                        <th></th>
                    </tr>
                    <%
                    for(AccountExtendActionForm eachForm: inForm.getDisplayedAccountList()) {
                    %>
                        <tr>
                            <td class="align-middle text-center"><%=eachForm.getAccountId() %></td>
                            <td class="align-middle text-center"><%=eachForm.getLastName() %> <%=eachForm.getFirstName() %></td>
                            <td class="align-middle text-center"><%=eachForm.getAccountStatusName() %></td>
                            <td class="align-middle text-center"><%=eachForm.getStrAccountCreatedAt() %></td>
                            <td class="align-middle">
                                <div class="btn-group-vertical" role="group">
                                    <%
                                    // アカウント編集 入力画面へのURLを生成する。
                                    String editAccountUrl = "return !isDoubleClick('./editAccountInput.do?accountId=" + eachForm.getAccountId() +"')";
                                    %>
                                    <html:button property="editAccountBtn" value="編集" styleClass="btn btn-primary btn-sm" onclick="<%=editAccountUrl %>"/>
                                </div>
                            </td>
                        </tr>
                    <%
                    }
                    %>
                </thead>
            </table>
        </div>
        <jsp:include page="../common/pagination.jsp">
            <jsp:param name="totalPage" value="<%=inForm.getTotalPage() %>"/>
            <jsp:param name="currentPage" value="<%=inForm.getCurrentPage() %>"/>
            <jsp:param name="listUrl" value="./listAccount.do"/>
        </jsp:include>
    <%
    }
    %>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/admin/js/set-form-parts.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/submit-control.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/common-form-validation.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/account-form-validation.js"></script>
</body>
</html>
