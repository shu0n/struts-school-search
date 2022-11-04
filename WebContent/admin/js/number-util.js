/**
 * 数値に関する操作をするためのJavaScript
 */

/**
 * 全角数字を半角数字に変化するための関数
 * @param {string} str 変換対象の要素の値
 */
function zenkakuToHankakuNumber(str) {
    return str.replace(/[０-９]/g, function(s) {
        return String.fromCharCode(s.charCodeAt(0) - 65248);
    });
}
