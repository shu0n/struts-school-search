import java.util.Scanner;

import util.LogUtil;
import util.PasswordUtil;

/**
 * コマンドラインでDBに登録するためのパスワードを生成するためのクラス
 */
public class PasswordGeneratorForCommandLine {

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        try {
            // コマンドラインからの入力を扱うクラスのインスタンスを生成する
            Scanner scanner = new Scanner(System.in);

            // メールアドレスの入力を受け付ける
            System.out.println("メールアドレスを入力してください。");
            String mailAddress = scanner.next();

            // パスワードをの入力を受け付ける。
            System.out.println("パスワードを入力してください。");
            String password = scanner.next();

            // 入力されたメールアドレスとパスワードをもとに安全なパスワードを生成する。
            String safetyPassword = new PasswordUtil().getSafetyPassword(password, mailAddress);

            // 生成された安全なパスワードをコマンドラインに表示する。
            System.out.println("生成された安全なパスワード：" + safetyPassword);
        } catch (Exception e) {
            // 例外を受け取った場合はログに出力する。
            new LogUtil().writeExceptionLog("PasswordGeneratorForCommandLine", e);
        }
    }

}
