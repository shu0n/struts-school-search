/**
 * アカウント機能のフォームの入力値を検証するためのJavaScript
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
    // サーバーのパスワード検証が不正(誤ったパスワード)の場合に生成される要素を取得する。
    let errorPassword = document.getElementById("errorPassword");
    if(errorPassword !== null) {
        // 要素が存在する場合はエラーメッセージを変更する。
        errorPassword.innerHTML = "8〜16文字の半角英数字を入力してください。";
    }

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
 * 入力値が"パスワード"の入力値と一致しているかを検証するための関数
 * @param {string} originalIdValue 比較先パスワード
 * @param {string} confirmIdValue 比較元パスワード
 */
function validateConfirmPassword(originalIdValue, confirmIdValue) {
    // パスワード
    let password = document.getElementById(originalIdValue);
    // パスワード(確認)
    let confirmPassword = document.getElementById(confirmIdValue);

    if (password.value !== confirmPassword.value) {
        // "パスワード"と"パスワード(確認)"の値が一致しない場合

        // クラス属性に"is-invalid"を追加する。
        confirmPassword.classList.add("is-invalid");

        // falseを戻す。
        return false;
    } else {
        // 上記の場合

        // クラス属性から"is-invalid"クラスを削除する。
        confirmPassword.classList.remove("is-invalid");

        // trueを戻す。
        return true;
    }
}

/**
 * 入力値が"ID(カンマ区切り)"の入力形式に一致しているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 * @param {number} maxLength 最大文字数
 */
function validateAccountIds(idValue, maxLength) {
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
 * 入力値が最大文字数以内のカタカナで入力されているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 * @param {number} maxLength 最大文字数
 */
function validateTextKana(idValue, maxLength) {
    // id属性値をもとに要素をを取得する。
    let elem = document.getElementById(idValue);

    // 入力値を検証する。
    if (!validateMaxLength(idValue, maxLength)) {
        // 最大文字数超過の場合

        // falseを戻す。
        return false;
    } else if (!validateKana(idValue)) {
        // カタカナ以外が入力されていた場合

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
 * アカウント作成・編集のフォーム送信前に入力・選択値を検証するための関数
 */
function validateBeforeSubmitAccount() {
    if(isDoubleSubmit()) {
        // 二重送信の場合はfalseを戻す。
        return false;
    }

    // 検証結果が不正の要素を格納するリストを生成する。
    let invalidElementsList = [];

    // アカウントステータス
    if (!validateRadioId("accountStatus")) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementsByName("accountStatus")[0]);
    }

    // 姓
    if (!validateRequiredText("lastName", 20)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("lastName"));
    }

    // 名
    if (!validateRequiredText("firstName", 20)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("firstName"));
    }

    // 姓(フリガナ)
    if (!validateRequiredTextKana("lastNameKana", 40)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("lastNameKana"));
    }

    // 名(フリガナ)
    if (!validateRequiredTextKana("firstNameKana", 40)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("firstNameKana"));
    }

    // 生年月日
    if (!validateDate(1)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("selectYear1"));
    }

    // 都道府県
    if (!validateSelectId("prefectureId")) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("prefectureId"));
    }

    // メールアドレス
    if (!validateRequiredMailAddress("mailAddress")) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("mailAddress"));
    }

    // 自己紹介
    if (!validateMaxLength("selfIntroduction", 150)) {
        // 不正な場合は要素を格納する。
        invalidElementsList.push(document.getElementById("selfIntroduction"));
    }

    // パスワードを変更する
    let changePassword = document.getElementById("isPasswordChanged");
    if (changePassword !== null && !changePassword.checked) {
        // NULL以外かつチェックされていない場合は何もしない
    } else {
        // 上記以外の場合

        // パスワード
        if (!validatePassword("password", 8, 16)) {
            // 不正な場合は要素を格納する。
            invalidElementsList.push(document.getElementById("password"));
        }

        // パスワード(確認)
        if (!validateConfirmPassword("password", "confirmPassword")) {
            // 不正な場合は要素を格納する。
            invalidElementsList.push(document.getElementById("confirmPassword"));
        }
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
 * アカウント検索のフォーム送信前に入力・選択値に不正がないかを検証するための関数
 * @param {element} elem 要素
 */
function validateBeforeSubmitSearchAccount(elem) {
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
