package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.http.HttpServletRequest;

public class PasswordUtil {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256"; // アルゴリズム
    private static final int ITERATION_COUNT = 10000; // ストレッチング回数
    private static final int KEY_LENGTH = 256; // 生成される鍵の長さ

    /**
     * 平文のパスワードとソルトから安全なパスワードを生成するためのメソッド
     * @param password 平文のパスワード
     * @param salt ソルト
     * @return 安全なパスワード
     * @throws RuntimeException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public String getSafetyPassword(String password, String salt)
            throws RuntimeException, InvalidKeySpecException, NoSuchAlgorithmException {
        // パスワードをUnicode文字列にコピーする。
        char[] passCharAry = password.toCharArray();
        // ソルトをハッシュ化する。
        byte[] hashedSalt = getHashedSalt(salt);
        // Unicode文字列にコピーしたパスワード、ハッシュ化したソルト、ストレッチング回数、鍵の長さをもとに暗号化鍵仕様を生成する。
        PBEKeySpec keySpec = new PBEKeySpec(passCharAry, hashedSalt, ITERATION_COUNT, KEY_LENGTH);

        // アルゴリズムから変換用オブジェクトを生成する。
        SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);

        // 暗号化鍵仕様と変換用オブジェクトから秘密鍵を生成する。
        SecretKey secretKey = skf.generateSecret(keySpec);

        // 生成した秘密鍵からバイト配列を取得する。
        byte[] passByteAry = secretKey.getEncoded();

        // 取得したバイト配列を16進数の文字列に変換する。
        StringBuilder sb = new StringBuilder(64);
        for(byte b : passByteAry) {
            sb.append(String.format("%02x", b & 0xff));
        }

        // バイト配列(安全なパスワード)を文字列に変換する。
        String safetyPassword = sb.toString();

        // 生成した安全なパスワードを戻す。
        return safetyPassword;
    }

    /**
     * ソルトをハッシュ化するためのメソッド(ハッシュアルゴリズムはSHA-256を使用)
     * @param salt ソルbyte[] ハッシュ化されたバイト配列のソルト
     * @throws NoSuchAlgorithmException
     */
    private byte[] getHashedSalt(String salt) throws NoSuchAlgorithmException {
        // ハッシュアルゴリズムを引数にしてダイジェストのインスタンスを生成する。
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        // ソルトのバイト列をもとにダイジェストを更新する。
        messageDigest.update(salt.getBytes());

        // ハッシュ計算を完了させて値を算出する。
        byte[] hashedSalt = messageDigest.digest();

        // 算出した値を戻す。
        return hashedSalt;
    }

    /**
     * リクエストにマスク化したパスワードを追加するためのメソッド
     * @param password パスワード
     * @param request リクエスト
     * @return マスク化したパスワードを追加したリクエスト
     */
    public HttpServletRequest setMaskedPassword(String password, HttpServletRequest request) {
        // パスワードの文字列の長さを取得する。
        int num = password.length();
        // パスワードの文字列の長さ分「●」を連ねた文字列を生成する。
        String maskedPassword = "";
        for(int i = 0; i < num; i++) {
           maskedPassword += "●";
        }
        //生成した文字列をリクエストに格納する。
        request.setAttribute("maskedPassword", maskedPassword);

        // マスク化したパスワードを追加したリクエストを戻す。
        return request;
    }

}
