/**
 * 申込機能のフォームの入力値を検証するためのJavaScript
 */

/**
 * 申込のフォーム送信前に入力値を検証するための関数
 */
function validateBeforeSubmitEntry() {
    // 二重送信であるかを判定する。
    if(isDoubleSubmit()) {
        // trueの場合はfalseを戻す。
        return false;
    }

    // 戻り値を格納する変数を生成する。
    let isValid = true;

    // 質問
    if (!validateMaxLength("entryQuestion", 1000)) {
        // 不正な場合

        // 送信済フラグを初期化する。
        initSubmittedFlag();
        // 変数にfalseを格納する。
        isValid = false;
    }

    // 変数を戻す。
    return isValid;
}

/**
 * 申込受付検索のフォーム送信前に入力・選択値に不正がないかを検証するための関数
 * @param {element} elem 要素
 */
function validateBeforeSubmitSearchReceivedEntry(elem) {
    if(isDoubleSubmit()) {
        // 二重送信の場合はfalseを戻す。
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
