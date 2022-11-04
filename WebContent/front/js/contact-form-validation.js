/**
 * お問合せ機能のフォームの入力値を検証するためのJavaScript
 */

/**
 * メールアドレスが入力されいるおよび入力形式と一致しているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 */
function validateRequiredMailAddress(idValue) {
    // 入力値を検証する。
    if (!validateRequiredInput(idValue)) {
        // 未入力の場合

        // falseを戻す。
        return false;
    } else if (!validateMaxLength(idValue, 250)) {
        // 250文字超過の場合

        // falseを戻す。
        return false;
    } else if (!validateMailAddress(idValue)) {
        // 入力形式に一致しない場合

        // falseを戻す。
        return false;
    } else {
        // 上記の場合

        // trueを戻す。
        return true;
    }
}

/**
 * お問合せのフォーム送信前に入力・選択値を検証するための関数
 */
function validateBeforeSubmitContact() {
    if(isDoubleSubmit()) {
        // 二重送信の場合はfalseを戻す。
        return false;
    }

    // 検証結果が不正の要素を格納するリストを生成する。
    let invalidElementsList = [];

    // 姓
    if (!validateRequiredText("contactLastName", 20)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("contactLastName"));
    }

    // 名
    if (!validateRequiredText("contactFirstName", 20)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("contactFirstName"));
    }

    // 姓(フリガナ)
    if (!validateRequiredTextKana("contactLastNameKana", 40)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("contactLastNameKana"));
    }

    // 名(フリガナ)
    if (!validateRequiredTextKana("contactFirstNameKana", 40)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("contactFirstNameKana"));
    }

    // メールアドレス
    if (!validateRequiredMailAddress("contactMailAddress")) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("contactMailAddress"));
    }

    // お問合せ内容
    if (!validateMaxLength("contactContent", 1000)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("contactContent"));
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

