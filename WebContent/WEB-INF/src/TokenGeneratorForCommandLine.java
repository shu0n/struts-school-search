import java.util.Scanner;

import util.LogUtil;
import util.TokenUtil;

/**
 * コマンドラインでDBに登録するためのパスワードを生成するためのクラス
 */
public class TokenGeneratorForCommandLine {

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        try {
            // コマンドラインからの入力を扱うクラスのインスタンスを生成する
            Scanner scanner = new Scanner(System.in);

            // メールアドレスの入力を受け付ける
            System.out.println("メールアドレスを入力してください。");
            String mailAddress = scanner.next();

            // 入力されたメールアドレスをもとにトークンを生成する。
            String token = new TokenUtil().generateToken(mailAddress);

            // 生成されたトークンをコマンドラインに表示する。
            System.out.println("生成されたトークン：" + token);
        } catch (Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog("TokenGeneratorForCommandLine", e);
        }
    }

}
