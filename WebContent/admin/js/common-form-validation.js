/**
 * フォームの入力値を検証するためのJavaScript
 */

// カタカナの入力形式を指定する正規表現
const KANA_REGEX = new RegExp(/^[ァ-ヴー]*$/);
// メールアドレスの入力形式を指定する正規表現
const MAIL_REGEX = new RegExp( /[\w\.\-]+@[\w\-]+\.[\w\.\-]+/);
// 郵便番号1の入力形式を指定する正規表現
const ZIPCODE1_REGEX = new RegExp(/^[０-９0-9]{3}$/);
// 郵便番号2の入力形式を指定する正規表現
const ZIPCODE2_REGEX = new RegExp(/^[０-９0-9]{4}$/);
// 数値の入力形式を指定する正規表現
const NUMBER_REGEX = new RegExp(/^[０-９0-9]*$/);
// URLの入力形式を指定する正規表現
const URL_REGEX = new RegExp(/^(https?|http)(:\/\/[\w\/:%#\$&\?\(\)~\.=\+\-]+)/);
// ID(カンマ区切り)の形式を指定する正規表現
const IDS_SEPARETED_BY_COMMA_REGEX = new RegExp(/^[0-9]*(?:,[0-9]+)*$/);

/**
 * 必須項目が入力されているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 */
 function validateRequiredInput(idValue) {
    // id属性値をもとに要素をを取得する。
    let elem = document.getElementById(idValue);
    if (elem.value === "") {
        // 空文字の場合

        // クラス属性に"is-invalid"を追加する。
        elem.classList.add("is-invalid");

        // falseを戻す。
        return false;
    } else {
        // 上記以外の場合

        // クラス属性から"is-invalid"クラスを削除する。
        elem.classList.remove("is-invalid");

        // trueを戻す。
        return true;
    }
}

/**
 * 入力値が最小字数以上であるかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 * @param {number} minLength 最小文字数
 */
function validateMinLength(idValue, minLength) {
    // id属性値をもとに要素をを取得する。
    let elem = document.getElementById(idValue);
    if (elem.value.length < minLength) {
        // 最大文字数に満たない場合

        // クラス属性に"is-invalid"を追加する。
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
 * 入力値が最大文字数超過であるかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 * @param {number} maxLength 最大文字数
 */
function validateMaxLength(idValue, maxLength) {
    // id属性値をもとに要素をを取得する。
    let elem = document.getElementById(idValue);
    if (maxLength < elem.value.length) {
        // 空文字または最大文字数を超える場合

        // クラス属性に"is-invalid"を追加する。
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
 * 入力値がカタカナであるかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 */
function validateKana(idValue) {
    // id属性値をもとに要素をを取得する。
    let elem = document.getElementById(idValue);
    if (elem.value !== "") {
        // 空文字以外の場合は入力値を検証する。
        if (!KANA_REGEX.test(elem.value)) {
            // カタカナ以外が入力されている場合

            // クラス属性に"is-invalid"を追加する。
            elem.classList.add("is-invalid");

            // falseを戻す。
            return false;
        } else {
            // 上記以外の場合

            // クラス属性から"is-invalid"クラスを削除する。
            elem.classList.remove("is-invalid");

            // trueを戻す。
            return true;
        }
    } else {
        // 上記以外の場合

        // クラス属性から"is-invalid"クラスを削除する。
        elem.classList.remove("is-invalid");

        // trueを戻す。
        return true;
    }
}

/**
 * 入力値が最大文字数以内で入力されているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 * @param {number} maxLength 最大文字数
 */
function validateRequiredText(idValue, maxLength) {
    // 入力値を検証する。
    if (!validateRequiredInput(idValue)) {
        // 未入力の場合

        // falseを戻す。
        return false;
    } else if (!validateMaxLength(idValue, maxLength)) {
        // 最大文字数超過の場合

        // falseを戻す。
        return false;
    } else {
        // 上記の場合

        //trueを戻す。
        return true;
    }
}

/**
 * 入力値が最大文字数以内のカタカナで入力されているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 * @param {number} maxLength 最大文字数
 */
function validateRequiredTextKana(idValue, maxLength) {
    // 入力値を検証する。
    if (!validateRequiredInput(idValue)) {
        // 未入力の場合

        // falseを戻す。
        return false;
    } else if (!validateKana(idValue)) {
        // カタカナ以外が入力されている場合

        // falseを戻す。
        return false;
    } else if (!validateMaxLength(idValue, maxLength)) {
        // 最大文字数超過の場合

        // falseを戻す。
        return false;
    } else {
        // 上記の場合

        // trueを戻す。
        return true;
    }
}

/**
 * "年月日"セレクトボックスの選択状態を検証するための関数
 * @param {number} i インデックス
 */
function validateDate(i) {
    // id属性が"selectYeari"、"selectMonthi"、"selectDayi"の要素を取得する。
    let selectYear = document.getElementById("selectYear" + i);
    let selectMonth = document.getElementById("selectMonth" + i);
    let selectDay = document.getElementById("selectDay" + i);
    if (selectYear.value === "" && selectMonth.value === "" && selectDay.value === "") {
        // "年"、"月"、"日"の全てが未選択の場合

        // 上記の場合は各要素のクラス属性から"is-invalid"クラスを削除する。
        selectYear.classList.remove("is-invalid");
        selectMonth.classList.remove("is-invalid");
        selectDay.classList.remove("is-invalid");

        // trueを戻す。
        return true;
    } else if (selectYear.value !== "" && selectMonth.value !== "" && selectDay.value !== "") {
        // "年"、"月"、"日"の全てが選択済の場合

        // 各要素のクラス属性から"is-invalid"クラスを削除する。
        selectYear.classList.remove("is-invalid");
        selectMonth.classList.remove("is-invalid");
        selectDay.classList.remove("is-invalid");

        // trueを戻す。
        return true;
    } else {
        // 上記以外の場合

        if (selectYear.value === "") {
            // "年"が未選択の場合はクラス属性に"is-invalid"を追加する。
            selectYear.classList.add("is-invalid");
        } else {
            // 上記以外の場合はクラス属性から"is-invalid"クラスを削除する。
            selectYear.classList.remove("is-invalid");
        }

        if (selectMonth.value === "") {
            // "月"が未選択の場合はクラス属性に"is-invalid"を追加する。
            selectMonth.classList.add("is-invalid");
        } else {
            // 上記以外の場合はクラス属性から"is-invalid"クラスを削除する。
            selectMonth.classList.remove("is-invalid");
        }

        if (selectDay.value === "") {
            // "日"が未選択の場合はクラス属性に"is-invalid"を追加する。
            selectDay.classList.add("is-invalid");
        } else {
            // 上記以外の場合はクラス属性から"is-invalid"クラスを削除する。
            selectDay.classList.remove("is-invalid");
        }

        // falseを戻す。
        return false;
    }
}

/**
 * セレクトボックスの選択状態を検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 */
function validateSelectId(idValue) {
    // id属性値をもとに要素をを取得する。
    let elem = document.getElementById(idValue);
    if(elem.value === "") {
        // 未選択の場合

        // クラス属性に"is-invalid"を追加する。
        elem.classList.add("is-invalid");

        // falseを戻す。
        return false;
    } else if (elem.value !== "") {
        // 選択済の場合

        // クラス属性から"is-invalid"クラスを削除する。
        elem.classList.remove("is-invalid");

        // trueを戻す。
        return true;
    }
}

/**
 * 入力値が"メールアドレス"の入力形式に一致しているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 */
function validateMailAddress(idValue) {
    // サーバーのメールアドレス検証が不正(登録済メールアドレス)の場合に生成される要素を取得する。
    let errorMailAddress = document.getElementById("errorMailAddress");
    if(errorMailAddress !== null) {
        // 要素が存在する場合はエラーメッセージを変更する。
        errorMailAddress.innerHTML = "メールアドレスの形式と一致しません。";
    }

    // id属性値をもとに要素をを取得する。
    let elem = document.getElementById(idValue);
    if (elem.value !== "") {
        // 空文字以外の場合は入力値を検証する。
        if (!MAIL_REGEX.test(elem.value)) {
            // メールアドレスの形式に合わない場合

            // クラス属性に"is-invalid"を追加する。
            elem.classList.add("is-invalid");

            // falseを戻す。
            return false;
        } else if (!validateMaxLength(idValue, 250)) {
            // 250文字超過の場合

            // falseを戻す。
            return false;
        } else {
            // 上記以外の場合

            // クラス属性から"is-invalid"クラスを削除する。
            elem.classList.remove("is-invalid");

            // trueを戻す。
            return true;
        }
    } else {
        // 上記以外の場合

        // クラス属性から"is-invalid"クラスを削除する。
        elem.classList.remove("is-invalid");

        // trueを戻す。
        return true;
    }
}

/**
 * 入力値が"郵便番号1"の入力形式に一致しているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 */
function validateZipCode1(idValue) {
    // id属性値をもとに要素をを取得する。
    let elem = document.getElementById(idValue);
    if (elem.value !== "") {
        // 空文字以外の場合は入力値を検証する。
        if (!ZIPCODE1_REGEX.test(elem.value)) {
            // 郵便番号1以外の形式で入力されている場合

            // クラス属性に"is-invalid"を追加する。
            elem.classList.add("is-invalid");

            // falseを戻す。
            return false;
        } else {
            // 上記以外の場合

            // 入力された数字を半角数字に変換する。
            elem.value = zenkakuToHankakuNumber(elem.value);
            // クラス属性から"is-invalid"クラスを削除する。
            elem.classList.remove("is-invalid");

            // trueを戻す。
            return true;
        }
    } else {
        // 上記以外の場合

        // クラス属性から"is-invalid"クラスを削除する。
        elem.classList.remove("is-invalid");

        // trueを戻す。
        return true;
    }
}

/**
 * 入力値が"郵便番号2"の入力形式に一致しているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 */
function validateZipCode2(idValue) {
    // id属性値をもとに要素をを取得する。
    let elem = document.getElementById(idValue);
    if (elem.value !== "") {
        // 空文字以外の場合は入力値を検証する。
        if (!ZIPCODE2_REGEX.test(elem.value)) {
            // 郵便番号1以外の形式で入力されている場合

            // クラス属性に"is-invalid"を追加する。
            elem.classList.add("is-invalid");

            // falseを戻す。
            return false;
        } else {
            // 上記の場合

            // 入力された数字を半角数字に変換する。
            elem.value = zenkakuToHankakuNumber(elem.value);
            // クラス属性から"is-invalid"クラスを削除する。
            elem.classList.remove("is-invalid");

            // trueを戻す。
            return true;
        }
    } else {
        // 上記以外の場合

        // クラス属性から"is-invalid"クラスを削除する。
        elem.classList.remove("is-invalid");

        // trueを戻す。
        return true;
    }
}

/**
 * 入力値が"数値"の入力形式に一致しているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 */
function validateNumber(idValue) {
    // id属性値をもとに要素をを取得する。
    let elem = document.getElementById(idValue);
    if (elem.value !== "") {
        // 空文字以外の場合は入力値を検証する。
        if (!NUMBER_REGEX.test(elem.value)) {
            // 数値以外が入力されている場合

            // クラス属性に"is-invalid"を追加する。
            elem.classList.add("is-invalid");

            // falseを戻す。
            return false;
        } else {
            // 上記の場合

            // 入力された数字を半角数字に変換する。
            elem.value = zenkakuToHankakuNumber(elem.value);
            // クラス属性から"is-invalid"クラスを削除する。
            elem.classList.remove("is-invalid");

            // trueを戻す。
            return true;
        }
    } else {
        // 上記以外の場合

        // クラス属性から"is-invalid"クラスを削除する。
        elem.classList.remove("is-invalid");

        // trueを戻す。
        return true;
    }
}

/**
 * 入力値が"URL"の入力形式に一致しているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 */
function validateUrl(idValue) {
    // id属性値をもとに要素をを取得する。
    let elem = document.getElementById(idValue);
    if (elem.value !== "") {
        // 空文字以外の場合は入力値を検証する。
        if (!URL_REGEX.test(elem.value)) {
            // URL以外が入力されている場合

            // クラス属性に"is-invalid"を追加する。
            elem.classList.add("is-invalid");

            // falseを戻す。
            return false;
        } else {
            // 上記以外の場合

            // クラス属性から"is-invalid"クラスを削除する。
            elem.classList.remove("is-invalid");

            // trueを戻す。
            return true;
        }
    } else {
        // 上記以外の場合

        // クラス属性から"is-invalid"クラスを削除する。
        elem.classList.remove("is-invalid");

        // trueを戻す。
        return true;
    }
}

/**
 * ラジオボタンのチェック状態を検証するための関数
 * @param {string} nameValue 検証する要素のname属性値
 */
function validateRadioId(nameValue) {
    // 判定するためのカウンター変数を生成する。
    let counter = 0;
    // name属性値が設定されている要素ごとにチェック状態を判定する。
    $('input[name=' + nameValue + ']').each(function () {
        if ($(this).prop('checked')) {
            // チェックされている場合はカウンター変数をインクリメントする。
            counter++;
        }
    });

    // name属性値が設定された要素を取得する。
    let elems = document.getElementsByName(nameValue);
    if (!counter) {
        // 0の場合

        // 取得した要素数分、処理を繰り返す。
        for (let i = 0; i < elems.length; i++) {
            // 対象の要素と親要素のクラス属性に"is-invalid"を追加する。
            elems[i].classList.add("is-invalid");
            elems[i].parentNode.classList.add("is-invalid");
        }

        // falseを戻す。
        return false;
    } else {
        // 上記以外の場合

        // 取得した要素数分、処理を繰り返す。
        for (let i = 0; i < elems.length; i++) {
            // 対象の要素と親要素のクラス属性から"is-invalid"クラスを削除する。
            elems[i].classList.remove("is-invalid");
            elems[i].parentNode.classList.remove("is-invalid");
        }

        // trueを戻す。
        return true;
    }
}

/**
 * 入力値が"ID(カンマ区切り)"の入力形式に一致しているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 */
function validateIds(idValue) {
    // id属性値をもとに要素をを取得する。
    let elem = document.getElementById(idValue);
    if (elem.value !== "") {
        // 空文字以外の場合は入力値を検証する。
        if (!IDS_SEPARETED_BY_COMMA_REGEX.test(elem.value)) {
            // "ID(カンマ区切り)"の入力形式に一致しない場合

            // クラス属性に"is-invalid"を追加する。
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
    } else {
        // 上記以外の場合

        // クラス属性から"is-invalid"クラスを削除する。
        elem.classList.remove("is-invalid");

        // trueを戻す。
        return true;
    }
}

/**
 * アップロードするファイルが入力形式に一致しているかを検証するための関数
 * @param {string} idValue 検証する要素のid属性値
 */
function validateUploadFile(idValue) {
    // id属性値をもとに要素をを取得する。
    let elem = document.getElementById(idValue);
    // 取得した要素からFileListオブジェクトを取得する。
    let files = elem.files;
    if(files.length === 0) {
        // アップロードするファイルが選択されていない場合

        // 対象の要素と親要素のクラス属性に"is-invalid"を追加する。
        elem.classList.add("is-invalid");
        elem.closest("div").classList.add("is-invalid");

        // falseを戻す。
        return false;
    } else {
        // 上記以外の場合

        // クラス属性から"is-invalid"クラスを削除する。
        elem.classList.remove("is-invalid");

        // trueを戻す。
        return true;
    }
}
