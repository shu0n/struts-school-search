<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="スクール登録(入力)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>スクール登録(入力)</h2>
    <html:form action="/registerSchoolConfirm" enctype="multipart/form-data" styleClass="h-adr" onsubmit="return validateBeforeSubmitSchool(true)">
        <fieldset class="form-group">
            <div class="row">
                <legend class="col-md-3 col-form-label">登録者種別</legend>
                <div class="col-md-9" style="padding-top: calc(0.375rem + 1px);">
                    <%
                    int i = 1;
                    %>
                    <logic:iterate id="registrantKindMap" name="AdminRegisterSchoolActionForm" property="registrantKindMap">
                        <div class="form-check form-check-inline">
                            <%
                            // for属性の値を生成する。
                            String registrantKindIndex = "registrantKind" + i;
                            String onchangeValue = "enableElementBySpecificChoice('" + registrantKindIndex + "', 'account', 'divStrRegistrantAccountId', 'strRegistrantAccountId')";
                            i += 1;
                            %>
                            <html:radio idName="registrantKindMap" property="registrantKind" value="key" styleClass="form-check-input" styleId="<%=registrantKindIndex %>" onchange="<%=onchangeValue %>" onblur="validateRadioId('registrantKind')"/>
                            <label class="form-check-label" for="<%=registrantKindIndex %>"><bean:write name="registrantKindMap" property="value"/></label>
                        </div>
                    </logic:iterate>
                    <div class="invalid-feedback">登録者種別を選択してください。</div>
                </div>
            </div>
        </fieldset>
        <div class="form-group row" id="divStrRegistrantAccountId" style="display: none;">
            <label for="strRegistrantAccountId" class="col-md-3 col-form-label">アカウントID<span class="badge badge-warning">必須</span></label>
            <div class="col-md-9">
                <html:select property="strRegistrantAccountId" disabled="true" styleClass="form-control" styleId="strRegistrantAccountId" onblur="validateSelectId('strRegistrantAccountId')">
                    <html:optionsCollection property="registrantAccountIdList" label="label" value="value"/>
                </html:select>
                <div class="invalid-feedback">アカウントIDを選択してください。</div>
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
                    <logic:iterate id="schoolReleaseProprietyMap" name="AdminRegisterSchoolActionForm" property="schoolReleaseProprietyMap">
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
                    <div class="invalid-feedback">公開可否を選択してください。</div>
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
                    <logic:iterate id="schoolEntryProprietyMap" name="AdminRegisterSchoolActionForm" property="schoolEntryProprietyMap">
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
                    <div class="invalid-feedback">申込可否を選択してください。</div>
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
        for(int j = 1; j < 9; j++) {
            // 詳細画面画像を取得するためのproperty属性の値を生成する。
            String property = "detailImage" + j + "File";
        %>
            <div class="form-group row">
                <label class="col-md-3 col-form-label">詳細画面画像<%=j %><small class="form-text text-muted">対応する拡張子は.jpg、.jpeg、.pngです。</small></label>
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
        }
        %>
        <div class="form-group row justify-content-end">
            <div class="col-md-9">
                <html:button property="cancelEditBtn" value="キャンセル" styleClass="btn btn-secondary" onclick="return !isDoubleClick('./listSchool.do')"/>
                <html:submit value="確認する" styleClass="btn btn-primary"/>
            </div>
        </div>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/admin/js/number-util.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/set-form-parts.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/submit-control.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/common-form-validation.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/school-form-validation.js"></script>
<script src="https://yubinbango.github.io/yubinbango/yubinbango.js" charset="UTF-8"></script>
</body>
</html>
