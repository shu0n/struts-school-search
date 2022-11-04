/**
 * お問合せ機能のフォームの入力値を検証するためのJavaScript
 */

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
 * お問合せ検索のフォーム送信前に入力・選択値に不正がないかを検証するための関数
 * @param {element} elem 要素
 */
function validateBeforeSubmitSearchContact(elem) {
    // 二重送信であるかを判定する。
    if(isDoubleSubmit()) {
        // 二重送信の場合はfalseを戻す。
        return false;
    }

    // 引数の要素の配下でclass属性に"is-invalid"が含まれる要素を取得する。
    let elemList = elem.querySelectorAll(".is-invalid");
    if (elemList.length > 0) {
        // 要素が存在する場合

        // 送信済フラグを初期化する。
        initSubmittedFlag();

        // falseを戻す。
        return false;
    } else {
        // 上記以外の場合はtrueを戻す。
        return true;
    }
}
