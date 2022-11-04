<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="admin.actionform.school.AdminEditSchoolActionForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%
// セッションからアクションフォームを取得する。
AdminEditSchoolActionForm inForm = (AdminEditSchoolActionForm) session.getAttribute("AdminEditSchoolActionForm");
%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="スクール編集(入力)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>スクール編集(入力)</h2>
</section>
<section class="container-fluid pt-4" style="width: 90%;">
    <html:form action="/editSchoolConfirm" enctype="multipart/form-data" styleClass="h-adr" onsubmit="return validateBeforeSubmitSchool(false)">
        <div class="form-group row">
            <label class="col-md-3 col-form-label">スクールID</label>
            <div class="col-md-9" style="padding-top: calc(0.375rem + 1px);">
                <bean:write name="AdminEditSchoolActionForm" property="schoolId"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 col-form-label">登録者種別</label>
            <div class="col-md-9" style="padding-top: calc(0.375rem + 1px);">
                <bean:write name="AdminEditSchoolActionForm" property="registrantKindName"/>
            </div>
        </div>
        <div class="form-group row">
            <%
            // 登録者種別を判定する。
            if(inForm.getRegistrantAccountId() != 0) {
                // アカウントIDが0以外の場合
            %>
                <label class="col-md-3 col-form-label">登録アカウントID</label>
                <div class="col-md-9" style="padding-top: calc(0.375rem + 1px);">
                    <a href="./editAccountInput.do?accountId=<%=inForm.getRegistrantAccountId() %>"><%=inForm.getRegistrantAccountId() %></a>
                </div>
            <%
            } else {
                // 上記以外の場合
            %>
                <label class="col-md-3 col-form-label">登録管理者ID</label>
                <div class="col-md-9" style="padding-top: calc(0.375rem + 1px);">
                    <bean:write name="AdminEditSchoolActionForm" property="registrantAdminId"/>
                </div>
            <%
            }
            %>
        </div>
        <div class="form-group row">
            <label class="col-md-3 col-form-label">登録日時</label>
            <div class="col-md-9" style="padding-top: calc(0.375rem + 1px);">
                <bean:write name="AdminEditSchoolActionForm" property="strSchoolRegisteredAt"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 col-form-label">前回更新日時</label>
            <div class="col-md-9" style="padding-top: calc(0.375rem + 1px);">
                <bean:write name="AdminEditSchoolActionForm" property="strSchoolUpdatedAt"/>
            </div>
        </div>
        <div class="form-group row">
            <label for="schoolName" class="col-md-3 col-form-label">スクール名<span class="badge badge-warning">必須</span></label>
            <div class="col-md-9">
                <html:text property="schoolName" styleClass="form-control" styleId="schoolName" onblur="validateRequiredText('schoolName', 50)"/>
                <div class="invalid-feedback">50文字以内で入力してください。</div>
            </div>
        </div>
        <div class="form-group row">
            <label for="schoolCategoryId" class="col-md-3 col-form-label">カテゴリー<span class="badge badge-warning">必須</span></label>
            <div class="col-md-9">
                <html:select property="schoolCategoryId" styleClass="form-control" styleId="schoolCategoryId" onblur="validateSelectId('schoolCategoryId')">
                    <html:optionsCollection property="schoolCategoryList" label="label" value="value"/>
                </html:select>
                <div class="invalid-feedback">カテゴリーを選択してください。</div>
            </div>
        </div>
       <div class="form-group row">
            <label for="schoolSummary" class="col-md-3 col-form-label">概要<span class="badge badge-warning">必須</span><small class="form-text text-muted">150文字以内で入力してください。</small></label>
            <div class="col-md-9">
                <html:textarea property="schoolSummary" styleClass="form-control" styleId="schoolSummary" rows="3" onblur="validateRequiredText('schoolSummary', 150)"/>
                <div class="invalid-feedback">150文字以内で入力してください。</div>
            </div>
        </div>
        <div class="form-group row">
            <label for="schoolDescription" class="col-md-3 col-form-label">詳細<span class="badge badge-warning">必須</span><small class="form-text text-muted">1200文字以内で入力してください。</small></label>
            <div class="col-md-9">
                <html:textarea property="schoolDescription" styleClass="form-control" styleId="schoolDescription" rows="5" onblur="validateRequiredText('schoolDescription', 1200)"/>
                <div class="invalid-feedback">1200文字以内で入力してください。</div>
            </div>
        </div>
        <span class="p-country-name" style="display:none;">Japan</span>
        <div class="form-group row">
            <label class="col-md-3 col-form-label">郵便番号<span class="badge badge-warning">必須</span></label>
            <div class="col-md-4">
                <div class="input-group">
                    <html:text property="schoolZipCode1" styleClass="form-control p-postal-code" styleId="schoolZipCode1" size="3" onblur="validateRequiredZipCode1('schoolZipCode1')"/>
                    <span class="col-form-label">-</span>
                    <html:text property="schoolZipCode2" styleClass="form-control p-postal-code" styleId="schoolZipCode2" size="4" onblur="validateRequiredZipCode2('schoolZipCode2')"/>
                    <div class="invalid-feedback">3桁-4桁の半角数字で入力してください。</div>
                </div>
            </div>
        </div>
        <div class="form-group row">
            <label for="schoolPrefectureId" class="col-md-3 col-form-label">都道府県<span class="badge badge-warning">必須</span></label>
            <div class="col-md-9">
                <html:select property="schoolPrefectureId" styleClass="form-control p-region-id" styleId="schoolPrefectureId" onblur="validateSelectId('schoolPrefectureId')" onchange="validateSelectId('schoolPrefectureId')">
                    <html:optionsCollection property="schoolPrefectureList" label="label" value="value"/>
                </html:select>
                <div class="invalid-feedback">都道府県を選択してください。</div>
            </div>
        </div>
        <div class="form-group row">
            <label for="schoolCity" class="col-md-3 col-form-label">市区町村<span class="badge badge-warning">必須</span></label>
            <div class="col-md-9">
                <html:text property="schoolCity" styleClass="form-control p-locality" styleId="schoolCity" onblur="validateRequiredText('schoolCity', 20)"/>
                <div class="invalid-feedback">20文字以内で入力してください。</div>
            </div>
        </div>
        <div class="form-group row">
            <label for="schoolAddress1" class="col-md-3 col-form-label">町・番地<span class="badge badge-warning">必須</span></label>
            <div class="col-md-9">
                <html:text property="schoolAddress1" styleClass="form-control p-street-address" styleId="schoolAddress1" onblur="validateRequiredText('schoolAddress1', 150)"/>
                <div class="invalid-feedback">150文字以内で入力してください。</div>
            </div>
        </div>
        <div class="form-group row">
            <label for="schoolAddress2" class="col-md-3 col-form-label">マンション・ビル</label>
            <div class="col-md-9">
                <html:text property="schoolAddress2" styleClass="form-control p-extended-address" styleId="schoolAddress2" onblur="validateMaxLength('schoolAddress2', 150)"/>
                <div class="invalid-feedback">150文字以内で入力してください。</div>
            </div>
        </div>
        <div class="form-group row">
            <label for="strSchoolFee" class="col-md-3 col-form-label">費用<span class="badge badge-warning">必須</span></label>
            <div class="col-md-9 input-group">
                <html:text property="strSchoolFee" styleClass="form-control" styleId="strSchoolFee" onblur="validateSchoolFee('strSchoolFee', 8)"/>
                <div class="input-group-append">
                    <span class="input-group-text">円</span>
                </div>
                <div class="invalid-feedback">8桁以内の半角数字で入力してくだい。</div>
            </div>
        </div>
        <div class="form-group row">
            <label for="supplementaryFee" class="col-md-3 col-form-label">費用補足<small class="form-text text-muted">150文字以内で入力してください。</small></label>
            <div class="col-md-9">
                <html:textarea property="supplementaryFee" styleClass="form-control" styleId="supplementaryFee" rows="3" onblur="validateMaxLength('supplementaryFee', 150)"/>
                <div class="invalid-feedback">150文字以内で入力してください。</div>
            </div>
        </div>
        <div class="form-group row">
            <label for="schoolUrl" class="col-md-3 col-form-label">スクールURL</label>
            <div class="col-md-9">
                <html:text property="schoolUrl" styleClass="form-control" styleId="schoolUrl" onblur="validateSchoolUrl('schoolUrl', 290)"/>
                <div class="invalid-feedback">URLの形式と一致しません。</div>
            </div>
        </div>
        <fieldset class="form-group">
            <div class="row">
                <legend class="col-md-3 col-form-label">公開可否<span class="badge badge-warning">必須</span></legend>
                <div class="col-md-9" style="padding-top: calc(0.375rem + 1px);">
                    <%
                    int index = 1;
                    %>
                    <logic:iterate id="schoolReleaseProprietyMap" name="AdminEditSchoolActionForm" property="schoolReleaseProprietyMap">
                        <div class="form-check form-check-inline">
                            <%
                            // for属性の値を生成する。
                            String schoolReleaseProprietyIndex = "schoolReleasePropriety" + index;
                            index += 1;
                            %>
                            <html:radio idName="schoolReleaseProprietyMap" property="schoolReleasePropriety" value="key" styleClass="form-check-input" styleId="<%=schoolReleaseProprietyIndex %>" onblur="validateRadioId('schoolReleasePropriety')">
                                <label class="form-check-label" for="<%=schoolReleaseProprietyIndex %>"><bean:write name="schoolReleaseProprietyMap" property="value"/></label>
                            </html:radio>
                        </div>
                    </logic:iterate>
                    <div class="invalid-feedback">選択してください。</div>
                </div>
            </div>
        </fieldset>
        <fieldset class="form-group">
            <div class="row">
            <legend class="col-md-3 col-form-label">申込可否<span class="badge badge-warning">必須</span></legend>
                <div class="col-md-9" style="padding-top: calc(0.375rem + 1px);">
                    <%
                    index = 1;
                    %>
                    <logic:iterate id="schoolEntryProprietyMap" name="AdminEditSchoolActionForm" property="schoolEntryProprietyMap">
                        <div class="form-check form-check-inline">
                            <%
                            // for属性の値を生成する。
                            String schoolEntryProprietyIndex = "schoolEntryPropriety" + index;
                            index += 1;
                            %>
                            <html:radio idName="schoolEntryProprietyMap" property="schoolEntryPropriety" value="key" styleClass="form-check-input" styleId="<%=schoolEntryProprietyIndex %>" onblur="validateRadioId('schoolEntryPropriety')">
                                <label class="form-check-label" for="<%=schoolEntryProprietyIndex %>"><bean:write name="schoolEntryProprietyMap" property="value"/></label>
                            </html:radio>
                        </div>
                    </logic:iterate>
                    <div class="invalid-feedback">選択してください。</div>
                </div>
            </div>
        </fieldset>
        <div class="form-group row">
            <label class="col-md-3 col-form-label">一覧画面画像<small class="form-text text-muted">対応する拡張子は.jpg、.jpeg、.pngです。</small></label>
             <div class="col-md-9 input-group">
                <label class="input-group-btn">
                    <span class="btn btn-primary">
                        画像を選択<input type="file" name="summaryImageFile" accept=".jpg,.jpeg,.png" style="display:none"/>
                    </span>
                </label>
                <input type="text" class="form-control" readonly="">
            </div>
        </div>
        <%
        String summaryImagePath = inForm.getSummaryImageFilePath();
        if(!StringUtils.isEmpty(summaryImagePath)) {
            summaryImagePath = "." + summaryImagePath;
        %>
            <div class="form-group row">
                <label class="col-md-3 col-form-label"></label>
                <div class="col-md-4">
                    <html:checkbox property="deleteSummaryImageFileFlag" value="true">一覧画面画像を削除する。</html:checkbox>
                    <div class="img-fluid">
                        <html:img src="<%=summaryImagePath %>"/>
                    </div>
                </div>
            </div>
        <%
        }
        %>
        <%
        for(int i = 1; i < 9; i++) {
            String property = "detailImage" + i + "File";
        %>
            <div class="form-group row">
                <label class="col-md-3 col-form-label">詳細画面画像<%=i %><small class="form-text text-muted">対応する拡張子は.jpg、.jpeg、.pngです。</small></label>
                <div class="col-md-9 input-group">
                    <label class="input-group-btn">
                        <span class="btn btn-primary">
                            画像を選択<input type="file" name="<%=property %>" accept=".jpg,.jpeg,.png" style="display:none"/>
                        </span>
                    </label>
                    <input type="text" class="form-control" readonly="">
                </div>
            </div>
            <%
                String detailImagePath = "";
                String deleteDetailImageFlag = "";
                switch(i) {
                    case 1:
                        detailImagePath = inForm.getDetailImage1FilePath();
                        deleteDetailImageFlag = "deleteDetailImage1FileFlag";
                        break;
                    case 2:
                        detailImagePath = inForm.getDetailImage2FilePath();
                        deleteDetailImageFlag = "deleteDetailImage2FileFlag";
                        break;
                    case 3:
                        detailImagePath = inForm.getDetailImage3FilePath();
                        deleteDetailImageFlag = "deleteDetailImage3FileFlag";
                        break;
                    case 4:
                        detailImagePath = inForm.getDetailImage4FilePath();
                        deleteDetailImageFlag = "deleteDetailImage4FileFlag";
                        break;
                    case 5:
                        detailImagePath = inForm.getDetailImage5FilePath();
                        deleteDetailImageFlag = "deleteDetailImage5FileFlag";
                        break;
                    case 6:
                        detailImagePath = inForm.getDetailImage6FilePath();
                        deleteDetailImageFlag = "deleteDetailImage6FileFlag";
                        break;
                    case 7:
                        detailImagePath = inForm.getDetailImage7FilePath();
                        deleteDetailImageFlag = "deleteDetailImage7FileFlag";
                        break;
                    case 8:
                        detailImagePath = inForm.getDetailImage8FilePath();
                        deleteDetailImageFlag = "deleteDetailImage8FileFlag";
                        break;
                }
                if(!StringUtils.isEmpty(detailImagePath)) {
                    detailImagePath = "." + detailImagePath;
            %>
                    <div class="form-group row">
                        <label class="col-md-3 col-form-label"></label>
                        <div class="col-md-4">
                            <html:checkbox property="deleteSummaryImageFileFlag" value="true">詳細画面画像<%=i %>を削除する。</html:checkbox>
                            <div class="img-fluid">
                                <html:img src="<%=detailImagePath %>"/>
                            </div>
                        </div>
                    </div>
            <%
                }
            %>
        <%
        }
        %>
        <div class="form-group row justify-content-end">
            <div class="col-md-9">
                <html:button property="listSchoolBtn" value="キャンセル" styleClass="btn btn-secondary" onclick="return !isDoubleClick('./listSchool.do')"/>
                <html:submit value="確認する" styleClass="btn btn-primary"/>
                <%
                // スクール削除 確認画面へのURLを生成する。
                String deleteSchoolUrl = "return !isDoubleClick('./deleteSchoolConfirm.do?schoolId=" + inForm.getSchoolId() + "')";
                %>
                <html:button property="deleteSchoolBtn" value="削除する" styleClass="btn btn-danger" onclick="<%=deleteSchoolUrl %>"/>
            </div>
        </div>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/admin/js/set-form-parts.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/number-util.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/submit-control.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/common-form-validation.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/school-form-validation.js"></script>
<script src="https://yubinbango.github.io/yubinbango/yubinbango.js" charset="UTF-8"></script>
</body>
</html>
