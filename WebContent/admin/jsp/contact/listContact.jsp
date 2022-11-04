<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="admin.actionform.contact.AdminListContactActionForm"%>
<%@ page import="actionform.ContactExtendActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
AdminListContactActionForm inForm = (AdminListContactActionForm) session.getAttribute("AdminListContactActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="お問合せ一覧"/>
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
                    お問合せを検索する
                </a>
            </h5>
        </div>
        <div class="collapse" role="tabpanel" id="collapseListGroup1" aria-labelledby="collapseListGroupHeading1" aria-expanded="false">
            <div class="card-body">
                <html:form action="/searchContact" onsubmit="return validateBeforeSubmitSearchContact(this)">
                    <div class="form-group row">
                        <label for="strContactId" class="col-md-3 control-label">お問合せID</label>
                        <div class="col-md-9">
                            <html:text property="strContactId" styleClass="form-control" styleId="strContactId" onblur="validateVariousIds('strContactId', 50)"/>
                            <div class="invalid-feedback">ID,ID,...,IDの形式で入力していください。※IDとカンマは半角数字</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="strContactAccountId" class="col-md-3 control-label">お問合せアカウントID</label>
                        <div class="col-md-9">
                            <html:text property="strContactAccountId" styleClass="form-control" styleId="strContactAccountId" onblur="validateVariousIds('strContactAccountId', 50)"/>
                            <div class="invalid-feedback">ID,ID,...,IDの形式で入力していください。※IDとカンマは半角数字</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="likeContactLastName" class="col-md-3 control-label">お問い合わせ者姓名</label>
                        <div class="col-md-3 input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">姓</span>
                            </div>
                            <html:text property="likeContactLastName" styleClass="form-control" styleId="likeContactLastName" onblur="validateMaxLength('likeContactLastName', 20)"/>
                            <div class="invalid-feedback">20文字以内で入力してください。</div>
                        </div>
                        <div class="col-md-3 input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">名</span>
                            </div>
                            <html:text property="likeContactFirstName" styleClass="form-control" styleId="likeContactFirstName" onblur="validateMaxLength('likeContactFirstName', 20)"/>
                            <div class="invalid-feedback">20文字以内で入力してください。</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="likeContactLastNameKana" class="col-md-3 control-label">お問い合わせ者名(フリガナ)</label>
                        <div class="col-md-3 input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">セイ</span>
                            </div>
                            <html:text property="likeContactLastNameKana" styleClass="form-control" styleId="likeContactLastNameKana" onblur="validateMaxLength('likeContactLastNameKana', 40)"/>
                            <div class="invalid-feedback">40文字以内で入力してください。</div>
                        </div>
                        <div class="col-md-3 input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">メイ</span>
                            </div>
                            <html:text property="likeContactFirstNameKana" styleClass="form-control" styleId="likeContactFirstNameKana" onblur="validateMaxLength('likeContactFirstNameKana', 40)"/>
                            <div class="invalid-feedback">40文字以内で入力してください。</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="likeContactMailAddress" class="col-md-3 control-label">メールアドレス</label>
                        <div class="col-md-9">
                            <html:text property="likeContactMailAddress" styleClass="form-control" styleId="likeContactMailAddress" onblur="validateMailAddress('likeContactMailAddress')"/>
                            <div class="invalid-feedback">メールアドレスの形式と一致しません。</div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="selectYear1" class="col-md-3 col-form-label">お問合せ日時(From)</label>
                        <div class="col-md-3 input-group">
                            <html:select property="fromContactedYear" styleClass="custom-select" styleId="selectYear1"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectYear1">年</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="fromContactedMonth" styleClass="custom-select" styleId="selectMonth1"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectMonth1">月</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="fromContactedDay" styleClass="custom-select" styleId="selectDay1"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectDay1">日</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="selectYear2" class="col-md-3 col-form-label">お問合せ日時(To)</label>
                        <div class="col-md-3 input-group">
                            <html:select property="toContactedYear" styleClass="custom-select" styleId="selectYear2"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectYear2">年</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="toContactedMonth" styleClass="custom-select" styleId="selectMonth2"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectMonth2">月</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="toContactedDay" styleClass="custom-select" styleId="selectDay2"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectDay2">日</label>
                            </div>
                        </div>
                    </div>
                  <div class="form-group row">
                        <label for="selectYear3" class="col-md-3 col-form-label">お問合せ更新日時(From)</label>
                        <div class="col-md-3 input-group">
                            <html:select property="fromContactUpdatedYear" styleClass="custom-select" styleId="selectYear3"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectYear3">年</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="fromContactUpdatedMonth" styleClass="custom-select" styleId="selectMonth3"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectMonth3">月</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="fromContactUpdatedDay" styleClass="custom-select" styleId="selectDay3"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectDay3">日</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="selectYear4" class="col-md-3 col-form-label">問合せ更新日時(To)</label>
                        <div class="col-md-3 input-group">
                            <html:select property="toContactUpdatedYear" styleClass="custom-select" styleId="selectYear4"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectYear4">年</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="toContactUpdatedMonth" styleClass="custom-select" styleId="selectMonth4"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectMonth4">月</label>
                            </div>
                        </div>
                        <div class="col-md-3 input-group">
                            <html:select property="toContactUpdatedDay" styleClass="custom-select" styleId="selectDay4"/>
                            <div class="input-group-append">
                                <label class="input-group-text" for="selectDay4">日</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="contactStatus" class="col-md-3 control-label">お問合せステータス</label>
                        <div class="col-md-3">
                            <html:select property="contactStatusIdArray" styleClass="form-control multiselect" styleId="contactStatus" multiple="true">
                                <html:optionsCollection name="AdminListContactActionForm" property="contactStatusList" label="label" value="value"/>
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
    <html:button property="csvDownloadBtn" value="CSVダウンロード" styleClass="btn btn-primary btn-sm mt-1" onclick="return !isDoubleClick('./downloadContactCsv.do')"/>
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
    <p><%=inForm.getTotalForm() %> 件のお問合せがヒットしました。</p>
    <%
    // 該当するお問合せの件数を判定する。
    if(inForm.getTotalForm() > 0) {
        // 1件以上ある場合
    %>
        <html:form action="/listContact" styleClass="form-inline pb-3" style="flex-wrap: nowrap;" onsubmit="return !isDoubleSubmit()">
            <html:select property="sortKindForContact" styleClass="custom-select">
                <html:optionsCollection property="sortKindForContactList" label="label" value="value"/>
            </html:select>
            <html:submit value="並び替える" styleClass="btn btn-primary"/>
            <html:hidden property="sortFlag" value="true"/>
        </html:form>
        <jsp:include page="../common/pagination.jsp">
            <jsp:param name="totalPage" value="<%=inForm.getTotalPage() %>"/>
            <jsp:param name="currentPage" value="<%=inForm.getCurrentPage() %>"/>
            <jsp:param name="listUrl" value="./listContact.do"/>
        </jsp:include>
        <div class="table-responsive-md">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col" class="text-nowrap text-center">ID</th>
                        <th scope="col" class="text-nowrap text-center">お問合せアカウントID</th>
                        <th scope="col" class="text-nowrap text-center">お問合せ者姓名</th>
                        <th scope="col" class="text-nowrap text-center">お問合せステータス</th>
                        <th scope="col" class="text-nowrap text-center">お問合せ日時</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    // リストの要素数分、処理を繰り返す。
                    for(ContactExtendActionForm eachForm: inForm.getDisplayedContactList()) {
                    %>
                        <tr>
                            <td class="align-middle text-center"><%=eachForm.getContactId() %></td>
                            <%
                            // お問合せアカウントIDを判定する。
                            if(eachForm.getContactAccountId() != 0) {
                                // 0以外の場合
                            %>
                                <td class="align-middle text-center"><a href="./editAccountInput.do?accountId=<%=eachForm.getContactAccountId() %>"><%=eachForm.getContactAccountId() %></a></td>
                            <%
                            } else {
                                // 上記以外の場合
                            %>
                                <td class="align-middle text-center">非会員</td>
                            <%
                            }
                            %>
                            <td class="align-middle text-center"><%=eachForm.getContactLastName() %> <%=eachForm.getContactFirstName() %></td>
                            <td class="align-middle text-center"><%=eachForm.getContactStatusName() %></td>
                            <td class="align-middle text-center"><%=eachForm.getStrContactedAt() %></td>
                            <td class="align-middle text-center">
                                <div class="btn-group-vertical" role="group">
                                    <%
                                    // お問合せ詳細画面へのURLを生成する。
                                    String viewDetailedContactUrl = "location.href='./viewDetailedContact.do?contactId=" + eachForm.getContactId() +"'";
                                    %>
                                    <html:button property="viewDetailedContactBtn" value="詳細" styleClass="btn btn-primary btn-sm" onclick="<%=viewDetailedContactUrl %>"/>
                                </div>
                            </td>
                        </tr>
                    <%
                    }
                    %>
                </tbody>
           </table>
       </div>
        <jsp:include page="../common/pagination.jsp">
            <jsp:param name="totalPage" value="<%=inForm.getTotalPage() %>"/>
            <jsp:param name="currentPage" value="<%=inForm.getCurrentPage() %>"/>
            <jsp:param name="listUrl" value="./listContact.do"/>
        </jsp:include>
    <%
    }
    %>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/admin/js/set-form-parts.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/submit-control.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/common-form-validation.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/contact-form-validation.js"></script>
</body>
</html>
