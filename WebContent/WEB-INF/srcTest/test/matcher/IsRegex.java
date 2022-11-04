package test.matcher;

import java.util.regex.Pattern;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * 想定した正規表現と実際に取得した文字列を比較するためのクラス
 */
public class IsRegex extends BaseMatcher<String> {

    private final String expected; // 想定した正規表現を示す文字列
    Object actual; // 実際に取得した文字列

    IsRegex(String expected) {
        this.expected = expected;
    }

    @Override
    public boolean matches(Object actual) {
        this.actual = actual;
        if(!(actual instanceof String)) {
            // 文字列型と異なるインスタンスタイプの場合はfalseを戻す。
            return false;
        }
        String actualString = (String) actual;
        Pattern p = Pattern.compile(expected);
        if(!p.matcher(actualString).find()) {
            // 実際に取得した文字列が想定した正規表現に一致しない場合はfalseを戻す。
            return false;
        }

        // 実際に取得した文字列が想定した正規表現に一致する場合はtrueを戻す。
        return true;
    }

    @Override
    public void describeTo(Description desc) {
        desc.appendValue(expected);
    }

    public static Matcher<String> matchPatternAs(String expected) {
        return new IsRegex(expected);
    }

}
