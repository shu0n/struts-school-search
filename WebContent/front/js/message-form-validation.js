/**
 * メッセージ機能のフォームの入力値を検証するためのJavaScript
 */

/**
 * メッセージ送信・返信のフォーム送信前に入力値を検証するための関数
 */
function validateBeforeSubmitMessage() {
    if(isDoubleSubmit()) {
        // 二重送信の場合はfalseを戻す。
        return false;
    }

    // 検証結果が不正の要素を格納するリストを生成する。
    let invalidElementsList = [];

    // 件名
    if (!validateRequiredText("messageSubject", 20)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("messageSubject"));
    }

    // 本文
    if (!validateRequiredText("messageBody", 1000)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("messageBody"));
    }

    // リストの要素を判定する。
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
