/**
 * フォーム部品を生成するためのJavaScript
 */

/**
 * select要素のoption要素を生成するための関数
 * @param {element} elem 変更したいselectの要素
 * @param {string} text 表示されるテキスト
 * @param {number} value 送信される値
 */
function createOptionForElements(elem, text, value) {
    let option = document.createElement("option");
    option.text = text;
    option.value = value;
    elem.appendChild(option);
}

/**
 * 選択された"年"と"月"に応じて日付を変更するための関数
 * @param {element} year "年"の要素
 * @param {element} month "月"の要素
 * @param {element} day "日"の要素
 */
function changeTheDay(year, month, day) {
    //日付の要素を削除する。
    day.innerHTML = "";

    //選択された年月の最終日を計算する。
    let lastDayOfTheMonth = new Date(year.value, month.value, 0).getDate();

    //選択された年月の日付を生成する。
    createOptionForElements(day, "選択してください", "");
    for (let i = 1; i <= lastDayOfTheMonth; i++) {
        createOptionForElements(day, i, i);
    }
}

// カウンター変数を生成する。
var counter = 1;
// 要素の存在有無を格納する変数を生成する。
var isExistElement = true;
// 現在年を取得する。
var currentYear = new Date().getFullYear()
do {
    // id属性が"selectYearN"、"selectMonthN"、"selectDayN"の要素を取得する。
    let selectYear = document.getElementById("selectYear" + counter);
    let selectMonth = document.getElementById("selectMonth" + counter);
    let selectDay = document.getElementById("selectDay" + counter);

    if (selectYear !== null) {
        // "selectYearN"がNULL以外の場合

        // "月"の要素が選択された場合に"日"の要素を再生成する。
        selectYear.addEventListener("change", function() {
            changeTheDay(selectYear, selectMonth, selectDay);
            if (selectDay.classList.contains("required")) {
                validateDate(counter);
            }
        });

        // "月"の要素が選択された場合に"日"の要素を再生成する。
        selectMonth.addEventListener("change", function() {
            changeTheDay(selectYear, selectMonth, selectDay);
            if (selectDay.classList.contains("required")) {
                validateDate(counter);
            }
        });

        // カウンター変数に1を加算する。
        counter += 1
    } else {
        // 上記以外の場合は要素の存在有無を格納する変数にfalseを格納する。
        isExistElement = false;
        // カウンターを初期化する。
        counter = 1;
    }
} while (isExistElement);

 $(document).on('change', ':file', function() {
    var input = $(this),
    numFiles = input.get(0).files ? input.get(0).files.length : 1,
    label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
    input.parent().parent().next(':text').val(label);
});
