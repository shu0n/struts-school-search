<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="admin.actionform.category.AdminListCategoryActionForm"%>
<%@ page import="actionform.CategoryExtendActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
AdminListCategoryActionForm inForm = (AdminListCategoryActionForm) session.getAttribute("AdminListCategoryActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="カテゴリー一覧"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <div class="card">
        <div class="card-header" role="tab" id="collapseListGroupHeading1">
            <h5 class="mb-0 text-center">
                <a href="#collapseListGroup1" class="collapsed text-body d-block p-3 m-n3" role="button" data-toggle="collapse" aria-expanded="false" aria-controls="collapseListGroup1">
                    カテゴリーを一括で登録・更新
                </a>
            </h5>
        </div>
        <div class="collapse" role="tabpanel" id="collapseListGroup1" aria-labelledby="collapseListGroupHeading1" aria-expanded="false">
            <div class="card-body">
                <html:form action="/uploadCategoryCsv" enctype="multipart/form-data" onsubmit="return validateBeforeSubmitCategoryCsv()">
                    <div class="form-group row">
                        <div class="col-md-12 input-group">
                            <label class="input-group-btn">
                                <span class="btn btn-primary">
                                    参照<input type="file" name="categoryCsv" accept=".csv" class="form-control" id="uploadCategoryCsv" style="display:none"/>
                                </span>
                            </label>
                            <input type="text" class="form-control" readonly="">
                        </div>
                        <div class="col-md-12 invalid-feedback">アップロードするCSVファイルを選択してください。</div>
                    </div>
                    <div class="form-group row justify-content-end">
                        <div class="col-md-12">
                            <html:submit value="アップロード" styleClass="btn btn-primary"/>
                        </div>
                    </div>
                </html:form>
                <html:button property="csvDownloadBtn" value="テンプレートCSVダウンロード" styleClass="btn btn-secondary btn-sm mt-1" onclick="return !isDoubleClick('./downloadCategoryCsv.do?template=true')"/>
            </div>
        </div>
    </div>
</section>
<section class="container-fluid pt-4" style="width: 90%;">
    <html:button property="registerBtn" value="新規カテゴリー作成" styleClass="btn btn-primary btn-sm mt-1" onclick="return !isDoubleClick('./createCategoryInput.do')"/>
    <html:button property="csvDownloadBtn" value="CSVダウンロード" styleClass="btn btn-secondary btn-sm mt-1" onclick="return !isDoubleClick('./downloadCategoryCsv.do')"/>
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
    <p><%=inForm.getTotalForm() %> 件のカテゴリーがヒットしました。</p>
    <%
    // 該当するカテゴリーの件数を判定する。
    if(inForm.getTotalForm() > 0) {
       // 1件以上存在する場合
    %>
        <jsp:include page="../common/pagination.jsp">
            <jsp:param name="totalPage" value="<%=inForm.getTotalPage() %>"/>
            <jsp:param name="currentPage" value="<%=inForm.getCurrentPage() %>"/>
            <jsp:param name="listUrl" value="./listCategory.do"/>
        </jsp:include>
        <div class="table-responsive-md">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col" class="text-nowrap text-center">ID</th>
                        <th scope="col" class="text-nowrap text-center">カテゴリー名</th>
                        <th scope="col" class="text-nowrap text-center">上位カテゴリー名</th>
                        <th scope="col" class="text-nowrap text-center">カテゴリーステータス</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    // リストの要素数分、処理を繰り返す。
                    for(CategoryExtendActionForm eachForm: inForm.getDisplayedCategoryList()) {
                    %>
                        <tr>
                            <td class="align-middle text-center"><%=eachForm.getCategoryId() %></td>
                            <td class="align-middle text-center"><%=eachForm.getCategoryName() %></td>
                            <td class="align-middle text-center">
                                <%
                                // 上位カテゴリー名を判定する。
                                if(!StringUtils.isEmpty(eachForm.getSeniorCategoryName())) {
                                    // NULLおよび空文字以外の場合
                                %>
                                    <%=eachForm.getSeniorCategoryName() %>
                                <%
                                }
                                %>
                            </td>
                            <td class="align-middle text-center"><%=eachForm.getCategoryStatusName() %></td>
                            <td class="align-middle">
                                <div>
                                    <%
                                    // カテゴリー編集画面へのURLを生成する。
                                    String editCategoryUrl = "location.href='./editCategoryInput.do?categoryId=" + eachForm.getCategoryId() +"'";
                                    %>
                                    <html:button property="editCategoryBtn" value="編集" styleClass="btn btn-primary btn-sm" onclick="<%=editCategoryUrl %>"/>
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
            <jsp:param name="listUrl" value="./listCategroy.do"/>
        </jsp:include>
    <%
    }
    %>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/admin/js/set-form-parts.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/submit-control.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/common-form-validation.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/category-form-validation.js"></script>
</body>
</html>
