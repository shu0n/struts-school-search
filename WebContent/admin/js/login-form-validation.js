/**
 * ログイン機能のフォームの入力値を検証するためのJavaScript
 */

// パスワードの形式を指定する正規表現
const PASSWORD_REGEX = new RegExp( /^[0-9a-zA-Z]*$/);

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
 * 入力値が"パスワード"の入力形式に一致しているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 * @param {number} minLength 最小文字数
 * @param {number} maxLength 最大文字数
 */
function validatePassword(idValue, minLength, maxLength) {
    // id属性値をもとに要素をを取得する。
    let elem = document.getElementById(idValue);

    // 入力値を検証する。
    if (!validateMinLength(idValue, minLength)) {
        // 最小文字数未満の場合

        // falseを戻す。
        return false;
    } else if (!validateMaxLength(idValue, maxLength)) {
        // 16文字超過の場合

        // falseを戻す。
    } else if (!PASSWORD_REGEX.test(elem.value)) {
        // "パスワード"の入力形式に一致しない場合

        // クラス属性から"is-invalid"クラスを追加する。
        elem.classList.add("is-invalid");

        // falseを戻す。
        return false;
    } else {
        // 上記の場合

        // クラス属性から"is-invalid"クラスを削除する。
        elem.classList.remove("is-invalid");

        // trueを戻す。
        return true;
    }
}

/**
 * ログインのフォーム送信時に入力・選択値を検証するための関数
 */
function validateBeforeSubmitLogin() {
    // 二重送信であるかを判定する。
    if(isDoubleSubmit()) {
        // trueの場合はfalseを戻す。
        return false;
    }

    // 検証結果が不正の要素を格納するリストを生成する。
    let invalidElementsList = [];

    if (!validateRequiredMailAddress("adminMailAddress")) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("adminMailAddress"));
    }

    // パスワード
    if (!validatePassword("adminPassword", 8, 16)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("adminPassword"));
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
