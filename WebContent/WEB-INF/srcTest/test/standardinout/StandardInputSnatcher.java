package test.standardinout;

import java.io.IOException;
import java.io.InputStream;

public class StandardInputSnatcher extends InputStream {

    private StringBuilder buffer = new StringBuilder();
    private static String crlf = System.getProperty("line.separator");

    /**
     * 文字列を入力するためのメソッド
     * @param str 入力する文字列
     */
    public void inputln(String str) {
        buffer.append(str).append(crlf);
    }

    @Override
    public int read() throws IOException {
        if(buffer.length() == 0) {
            return -1;
        }
        int result = buffer.charAt(0);
        buffer.deleteCharAt(0);
        return result;
    }

}
