package util;

import java.nio.ByteBuffer;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class TokenUtil {

    /**
     * 引数の文字列をもとにトークンを生成するためのメソッド
     * @param baseString トークン生成のための基底文字列
     * @return トークン
     */
    public String generateToken(String baseString) {
        // 容量を48バイトとした新しいバイトバッファを割り当てる。
        ByteBuffer buf = ByteBuffer.allocate(48);

        // 16バイトの乱数バイト配列を取得する。
        byte[] randomBytes = getRandomBytes();
        // 生成した乱数バイト配列をバイトバッファに格納する。
        buf.put(randomBytes);

        // 引数の文字列を利用して32バイトハッシュ値を生成する。
        byte[] HashBytes = DigestUtils.sha256(baseString + Hex.encodeHexString(randomBytes));
        // 生成したハッシュ値をバイトバッファに格納する。
        buf.put(HashBytes);

        // ハッシュ値をもとにトークンを生成する。
        String token = Base64.encodeBase64URLSafeString(buf.array());

        // 生成したトークンを戻す。
        return token;
    }

    /**
     * 16バイトの乱数バイト数を取得するためのメソッド
     */
    byte[] getRandomBytes() {
        // 16バイトのバイト配列を生成する。
        byte[] randomBytes = new byte[16];
        // 16バイトの乱数バイト配列を生成する。
        new SecureRandom().nextBytes(randomBytes);
        // 生成した乱数バイト配列を戻す。
        return randomBytes;
    }

}
