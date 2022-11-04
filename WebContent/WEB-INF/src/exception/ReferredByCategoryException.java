package exception;

/*
 * カテゴリー参照例外<br>
 * カテゴリーテーブルのレコードから参照されている場合に投げる例外
 */
public class ReferredByCategoryException extends Exception {
    // 警告を回避するための宣言
    private static final long serialVersionUID = 1L;

    // エラーメッセージを受け取るためのコンストラクタ
    public ReferredByCategoryException(String msg) {
        super(msg);
    }

}
