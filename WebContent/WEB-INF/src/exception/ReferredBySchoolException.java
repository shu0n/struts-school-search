package exception;

/*
 * スクール参照例外<br>
 * スクールテーブルのレコードから参照されている場合に投げる例外
 */
public class ReferredBySchoolException extends Exception {
    // 警告を回避するための宣言
    private static final long serialVersionUID = 1L;

    // エラーメッセージを受け取るためのコンストラクタ
    public ReferredBySchoolException(String msg) {
        super(msg);
    }

}
