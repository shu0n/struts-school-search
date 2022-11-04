package exception;

/*
 * データ不存在例外<br>
 * 該当するデータが存在しない場合に投げる例外
 */
public class NoDataExistException extends Exception {
    // 警告を回避するための宣言
    private static final long serialVersionUID = 1L;

    // エラーメッセージを受け取るためのコンストラクタ
    public NoDataExistException(String msg) {
        super(msg);
    }

}
