package exception;

/*
 * 申込参照例外<br>
 * 申込テーブルのレコードから参照されている場合に投げる例外
 */
public class ReferredByEntryException extends Exception {
    // 警告を回避するための宣言
    private static final long serialVersionUID = 1L;

    // エラーメッセージを受け取るためのコンストラクタ
    public ReferredByEntryException(String msg) {
        super(msg);
    }

}
