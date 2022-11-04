/**
 * スクール機能のフォームの入力値を検証するためのJavaScript
 */

/**
 * 入力値が"郵便番号1"の入力形式に一致しているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 */
function validateRequiredZipCode1(idValue) {
    // 入力値を検証する。
    if (!validateRequiredInput(idValue)) {
        // 未入力の場合

        // falseを戻す。
        return false;
    } else if (!validateZipCode1(idValue)) {
        // "郵便番号1"の入力形式に一致しない場合

        // falseを戻す。
        return false;
    } else {
        // 上記の場合

        // trueを戻す。
        return true;
    }
}

/**
 * 入力値が"郵便番号2"の入力形式に一致しているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 */
function validateRequiredZipCode2(idValue) {
    // 入力値を検証する。
    if (!validateRequiredInput(idValue)) {
        // 未入力の場合

        // falseを戻す。
        return false;
    } else if (!validateZipCode2(idValue)) {
        // "郵便番号2"の入力形式に一致しない場合

        // falseを戻す。
        return false;
    } else {
        // 上記の場合

        // trueを戻す。
        return true;
    }
}

/**
 * 入力値が最大桁数以内の数値で入力されているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 * @param {number} maxLength 最大文字数
 */
function validateSchoolFee(idValue, maxLength) {
    // 入力値を検証する。
    if (!validateRequiredText(idValue, maxLength)) {
        // 最大文字数超過の場合

        // falseを戻す。
        return false;
    } else if (!validateNumber(idValue)) {
        // 数値以外が入力されている場合

        // falseを戻す。
        return false;
    } else {
        // 上記の場合

        // trueを戻す。
        return true;
    }
}

/**
 * 入力値が最大桁数以内の数値で入力されているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 * @param {number} maxLength 最大文字数
 */
function validateSearchSchoolFee(idValue, maxLength) {
    // 入力値を検証する。
    if (!validateMaxLength(idValue, maxLength)) {
        // 最大文字数超過の場合

        // falseを戻す。
        return false;
    } else if (!validateNumber(idValue)) {
        // 数値以外が入力されている場合

        // falseを戻す。
        return false;
    } else {
        // 上記の場合

        // trueを戻す。
        return true;
    }
}

/**
 * 入力値が最大文字数以内のURLで入力されているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 * @param {number} maxLength 最大文字数
 */
function validateSchoolUrl(idValue, maxLength) {
    // 入力値を検証する。
    if (!validateMaxLength(idValue, maxLength)) {
        // 最大文字数超過の場合

        // falseを戻す。
        return false;
    } else if (!validateUrl(idValue)) {
        // "URL"の入力形式と一致しない場合

        // falseを戻す。
        return false;
    } else {
        // 上記の場合

        // trueを戻す。
        return true;
    }
}

/**
 * 入力値が"ID(カンマ区切り)"の入力形式に一致しているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 * @param {number} maxLength 最大文字数
 */
function validateVariousIds(idValue, maxLength) {
    // id属性値をもとに要素をを取得する。
    let elem = document.getElementById(idValue);

    // 入力値を検証する。
    if (!validateMaxLength(idValue, maxLength)) {
        // 最大文字数超過の場合

        // falseを戻す。
        return false;
    } else if (!validateIds(idValue)) {
        // ID(カンマ区切り)の入力形式に一致しない場合

        // falseを戻す。
        return false;
    } else {
        // 上記以外の場合

        // クラス属性に"is-invalid"を削除する。
        elem.classList.remove("is-invalid");
        // trueを戻す。
        return true;
    }
}

/**
 * スクール登録・編集のフォーム送信前に入力・選択値を検証するための関数
 * @param {boolean} isRegistrantCheck 登録者種別の検証要否
 */
function validateBeforeSubmitSchool(isRegistrantCheck) {
    // 二重送信であるかを判定する。
    if(isDoubleSubmit()) {
        // trueの場合はfalseを戻す。
        return false;
    }

    // 検証結果が不正の要素を格納するリストを生成する。
    let invalidElementsList = [];

    if (isRegistrantCheck) {
        // 登録者種別を検証する場合は

        // 登録者種別
        if (!validateRadioId("registrantKind")) {
            // チェックされていない場合は要素を格納する。
            invalidElementsList.push(document.getElementsByName("registrantKind")[0]);
        } else {
            // 上記以外の場合は登録者種別の選択肢を取得する。
            let elems = document.getElementsByName("registrantKind");
            for (let i = 0; i < elems.length; i++) {
                if (elems[i].checked && (elems[i].value === "account")) {
                    // value値が"account"の場合
                    if (!validateSelectId("strRegistrantAccountId")) {
                        // アカウントIDが選択されていない場合

                        // チェックされていない場合は要素を格納する。
                        invalidElementsList.push(elems[i]);

                        // 処理を終了する。
                        break;
                    }
                }
            }
        }
    }

    // スクール名
    if (!validateRequiredText("schoolName", 50)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("schoolName"));
    }

    // カテゴリーID
    if (!validateSelectId("schoolCategoryId")) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("schoolCategoryId"));
    }

    // 概要
    if (!validateRequiredText("schoolSummary", 150)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("schoolSummary"));
    }

    // 詳細
    if (!validateRequiredText("schoolDescription", 1200)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("schoolDescription"));
    }

    // 郵便番号1
    if (!validateRequiredZipCode1("schoolZipCode1")) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("schoolZipCode1"));
    }

    // 郵便番号2
    if (!validateRequiredZipCode2("schoolZipCode2")) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("schoolZipCode2"));
    }

    // 都道府県
    if (!validateSelectId("schoolPrefectureId")) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("schoolPrefectureId"));
    }

    // 市区町村名
    if (!validateRequiredText("schoolCity", 20)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("schoolCity"));
    }

    // 住所1
    if (!validateRequiredText("schoolAddress1", 150)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("schoolAddress1"));
    }

    // 住所2
    if (!validateMaxLength("schoolAddress2", 150)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("schoolAddress2"));
    }

    // 費用
    if (!validateSchoolFee("strSchoolFee", 8)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("strSchoolFee"));
    }

    // 費用補足
    if (!validateMaxLength("supplementaryFee", 150)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("supplementaryFee"));
    }

    // スクールURL
    if (!validateSchoolUrl("schoolUrl", 290)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("schoolUrl"));
    }

    // 公開可否
    if (!validateRadioId("schoolReleasePropriety")) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementsByName("schoolReleasePropriety")[0]);
    }

    // 申込可否
    if (!validateRadioId("schoolEntryPropriety")) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementsByName("schoolEntryPropriety")[0]);
    }

    if (invalidElementsList.length > 0) {
        // リストの要素が存在する場合

        // 送信済フラグを初期化する。
        initSubmittedFlag();
        // リストの1番目の要素にフォーカスする。
        invalidElementsList[0].focus();

        // falseを戻す。
        return false;
    } else {
        // 上記以外の場合

        // trueを戻す。
        return true;
    }
}

/**
 * スクール検索のフォーム送信前に入力・選択値に不正がないかを検証するための関数
 * @param {element} elem 要素
 */
function validateBeforeSubmitSearchSchool(elem) {
    // 二重送信であるかを判定する。
    if(isDoubleSubmit()) {
        // trueの場合はfalseを戻す。
        return false;
    }

    // 引数の要素の配下でclass属性に"is-invalid"が含まれる要素を取得する。
    let elemList = elem.querySelectorAll(".is-invalid");
    if (elemList.length > 0) {
        // リストの要素が存在する場合

        // 送信済フラグを初期化する。
        initSubmittedFlag();

        // falseを戻す。
        return false;
    } else {
        // 上記以外の場合はtrueを戻す。
        return true;
    }
}

/**
 * スクールCSVアップロードのフォーム送信前に入力値を検証するための関数
 */
function validateBeforeSubmitSchoolCsv() {
    if(isDoubleSubmit()) {
        // 二重送信の場合はfalseを戻す。
        return false;
    }

    // 検証結果が不正の要素を格納するリストを生成する。
    let invalidElementsList = [];

    // アップロードするファイル
    if (!validateUploadFile("uploadSchoolCsv")) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("uploadSchoolCsv"));
    }

    if (invalidElementsList.length > 0) {
        // リストの要素が存在する場合

        // 送信済フラグを初期化する。
        initSubmittedFlag();
        // リストの1番目の要素にフォーカスする。
        invalidElementsList[0].focus();

        // falseを戻す。
        return false;
    } else {
        // 上記以外の場合

        // trueを戻す。
        return true;
    }
}
