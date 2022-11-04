/**
 * フォーム送信前に検証するためのJavaScript
 */

// 送信済フラグ
var submittedFlag = false;

/**
 * 送信済フラグを初期化するための関数
 */
function initSubmittedFlag() {
    submittedFlag = false;
}

/**
 * 二重送信されていないかを判定するための関数
 */
function isDoubleSubmit() {
    if(!submittedFlag) {
        // 送信済フラグがfalseの場合

        // 送信済フラグにtrueを格納する。
        submittedFlag = true;

        // falseを戻す。
        return false;
    } else {
        // 上記以外の場合

        // trueを戻す。
        return true;
    }
}

// 押下済フラグ
var clickedFlag = false;

/**
 * 押下済フラグを初期化するための関数
 */
function initClickedFlag() {
    clickedFlag = false;
}

/**
 * 二重押下されていないかを判定するための関数
 * @param {string} url 遷移先URL
 */
function isDoubleClick(url) {
    if(!clickedFlag) {
        // 送信済フラグがfalseの場合

        // 押下済フラグにtrueを格納する。
        clickedFlag = true;
        // 遷移先URLを格納する。
        location.href = url;

        // falseを戻す。
        return false;
    } else {
        // 上記以外の場合

        // trueを戻す。
        return true;
    }
}
