<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="../common/head.jsp">
    <jsp:param name="title" value="アカウント作成(入力)"/>
</jsp:include>
<body>
<jsp:include page="../common/header.jsp"/>
<section class="container-fluid pt-4" style="width: 90%;">
    <h2>アカウント作成(入力)</h2>
    <html:form action="/createAccountConfirm" enctype="multipart/form-data" onsubmit="return validateBeforeSubmitAccount(true)">
        <div class="form-group row">
            <label for="lastName" class="col-md-3 col-form-label">お名前<span class="badge badge-warning">必須</span></label>
            <div class="col-md-4 input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">姓</span>
                </div>
                <html:text property="lastName" styleClass="form-control" styleId="lastName" onblur="validateRequiredText('lastName', 20)"/>
                <div class="invalid-feedback">20文字以内で入力してください。</div>
            </div>
            <div class="col-md-4 input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">名</span>
                </div>
                <html:text property="firstName" styleClass="form-control" styleId="firstName" onblur="validateRequiredText('firstName', 20)"/>
                <div class="invalid-feedback">20文字以内で入力してください。</div>
            </div>
        </div>
        <div class="form-group row">
            <label for="lastNameKana" class="col-md-3 col-form-label">お名前(フリガナ)<span class="badge badge-warning">必須</span></label>
            <div class="col-md-4 input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">セイ</span>
                </div>
                <html:text property="lastNameKana" styleClass="form-control" styleId="lastNameKana" onblur="validateRequiredTextKana('lastNameKana', 40)"/>
                <div class="invalid-feedback">40文字以内のカタカナを入力してください。</div>
            </div>
            <div class="col-md-4 input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">メイ</span>
                </div>
                <html:text property="firstNameKana" styleClass="form-control" styleId="firstNameKana" onblur="validateRequiredTextKana('firstNameKana', 40)"/>
                <div class="invalid-feedback">40文字以内のカタカナを入力してください。</div>
            </div>
        </div>
        <fieldset class="form-group">
            <div class="row">
                <legend class="col-md-3 col-form-label">性別</legend>
                <div class="col-md-9" style="padding-top: calc(0.375rem + 1px);">
                    <%
                    int i = 1;
                    %>
                    <logic:iterate id="sexMap" name="FrontCreateAccountActionForm" property="sexMap">
                        <div class="form-check form-check-inline">
                            <%
                            // for属性の値を生成する。
                            String sexIdIndex = "sexId" + i;
                            i += 1;
                            %>
                            <html:radio idName="sexMap" property="sexId" value="key" styleClass="form-check-input" styleId="<%=sexIdIndex %>"/>
                            <label class="form-check-label" for="<%=sexIdIndex %>"><bean:write name="sexMap" property="value"/></label>
                        </div>
                    </logic:iterate>
                </div>
            </div>
        </fieldset>
        <div class="form-group row">
            <label for="selectYear1" class="col-md-3 col-form-label">生年月日</label>
            <div class="col-md-3 input-group">
                <html:select property="birthYear" styleClass="custom-select" styleId="selectYear1" onchange="validateDate(1)">
                    <html:optionsCollection property="birthYearList" label="label" value="value"/>
                </html:select>
                <div class="input-group-append">
                    <label class="input-group-text" for="selectYear1">年</label>
                </div>
                <div class="invalid-feedback">年を選択してください。</div>
            </div>
            <div class="col-md-3 input-group">
                <html:select property="birthMonth" styleClass="custom-select" styleId="selectMonth1" onchange="validateDate(1)">
                    <html:optionsCollection property="birthMonthList" label="label" value="value"/>
                </html:select>
                <div class="input-group-append">
                    <label class="input-group-text" for="selectMonth1">月</label>
                </div>
                <div class="invalid-feedback">月を選択してください。</div>
            </div>
            <div class="col-md-3 input-group">
                <html:select property="birthDay" styleClass="custom-select required" styleId="selectDay1" onchange="validateDate(1)">
                    <html:optionsCollection property="birthDayList" label="label" value="value"/>
                </html:select>
                <div class="input-group-append">
                    <label class="input-group-text" for="selectDay1">日</label>
                </div>
                <div class="invalid-feedback">日を選択してください。</div>
            </div>
        </div>
        <div class="form-group row">
            <label for="prefectureId" class="col-md-3 col-form-label">都道府県<span class="badge badge-warning">必須</span></label>
            <div class="col-md-3">
                <html:select property="prefectureId" styleClass="custom-select" styleId="prefectureId" onblur="validateSelectId('prefectureId')" onchange="validateSelectId('prefectureId')">
                    <html:optionsCollection property="prefectureList" label="label" value="value"/>
                </html:select>
                <div class="invalid-feedback">都道府県を選択してください。</div>
            </div>
        </div>
        <div class="form-group row">
            <label for="mailAddress" class="col-md-3 col-form-label">メールアドレス<span class="badge badge-warning">必須</span></label>
            <div class="col-md-9">
                <logic:messagesNotPresent>
                    <html:text property="mailAddress" styleClass="form-control" styleId="mailAddress" onblur="validateRequiredMailAddress('mailAddress')"/>
                    <div class="invalid-feedback">メールアドレスの形式と一致しません。</div>
                </logic:messagesNotPresent>
                <logic:messagesPresent>
                    <html:text property="mailAddress" styleClass="form-control is-invalid" styleId="mailAddress" onblur="validateRequiredMailAddress('mailAddress')"/>
                    <div class="invalid-feedback" id="errorMailAddress">
                        <html:messages id="msg">
                            <bean:write name="msg" ignore="true"/>
                        </html:messages>
                    </div>
                </logic:messagesPresent>
            </div>
        </div>
        <div class="form-group row">
            <label for="selfIntroduction" class="col-md-3 col-form-label">自己紹介<small class="form-text text-muted">150文字以内で入力してください。</small></label>
            <div class="col-md-9">
                <html:textarea property="selfIntroduction" styleClass="form-control" styleId="selfIntroduction" rows="3" onblur="validateMaxLength('selfIntroduction', 150)"/>
                <div class="invalid-feedback">150文字以内で入力してください。</div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-md-3 col-form-label">プロフィール画像<small class="form-text text-muted">対応する拡張子は.jpg、.jpeg、.pngです。</small></label>
            <div class="col-md-9 input-group">
                <label class="input-group-btn">
                    <span class="btn btn-primary">
                        画像を選択<input type="file" name="profileImageFile" accept=".jpg,.jpeg,.png" style="display:none"/>
                    </span>
                </label>
                <input type="text" class="form-control" readonly="">
            </div>
        </div>
        <div class="form-group row">
            <label for="password" class="col-md-3 col-form-label">パスワード<span class="badge badge-warning">必須</span><small class="form-text text-muted">8〜16文字の半角英数字で入力してください。</small></label>
            <div class="col-md-9">
                <html:password property="password" redisplay="false" styleClass="form-control" styleId="password" onblur="validatePassword('password', 8, 16)"/>
                <div class="invalid-feedback">8〜16文字の半角英数字を入力してください。</div>
            </div>
        </div>
        <div class="form-group row">
            <label for="confirmPassword" class="col-md-3 col-form-label">パスワード(確認)<span class="badge badge-warning">必須</span></label>
            <div class="col-md-9">
                <html:password property="confirmPassword" redisplay="false" styleClass="form-control" styleId="confirmPassword" onblur="validateConfirmPassword('password', 'confirmPassword')"/>
                <div class="invalid-feedback">パスワードが一致しません。</div>
            </div>
        </div>
        <div class="form-group row justify-content-end">
            <div class="col-md-9">
                <html:button property="topBtn" value="キャンセル" styleClass="btn btn-secondary" onclick="return !isDoubleClick('./')"/>
                <html:submit value="確認する" styleClass="btn btn-primary"/>
            </div>
        </div>
    </html:form>
</section>
<jsp:include page="../common/footer.jsp"/>
<script src="<%=request.getContextPath()%>/front/js/set-form-parts.js"></script>
<script src="<%=request.getContextPath()%>/front/js/submit-control.js"></script>
<script src="<%=request.getContextPath()%>/front/js/common-form-validation.js"></script>
<script src="<%=request.getContextPath()%>/front/js/account-form-validation.js"></script>
</body>
</html>
