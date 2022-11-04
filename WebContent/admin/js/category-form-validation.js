/**
 * カテゴリー機能のフォームの入力値を検証するためのJavaScript
 */

/**
 * カテゴリー作成・編集のフォーム送信前に入力・選択値を検証するための関数
 */
function validateBeforeSubmitCategory() {
    if(isDoubleSubmit()) {
        // 二重送信の場合はfalseを戻す。
        return false;
    }

    // 検証結果が不正の要素を格納するリストを生成する。
    let invalidElementsList = [];

    // カテゴリー名
    if (!validateRequiredText("categoryName", 50)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("categoryName"));
    }

    // カテゴリーステータス
    if (!validateRadioId("categoryStatus")) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementsByName("categoryStatus")[0]);
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
 * カテゴリーCSVアップロードのフォーム送信前に入力値を検証するための関数
 */
function validateBeforeSubmitCategoryCsv() {
    if(isDoubleSubmit()) {
        // 二重送信の場合はfalseを戻す。
        return false;
    }

    // 検証結果が不正の要素を格納するリストを生成する。
    let invalidElementsList = [];

    // アップロードするファイル
    if (!validateUploadFile("uploadCategoryCsv")) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("uploadCategoryCsv"));
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
