package test.matcher;

import java.util.Map;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * 想定したマップと実際に取得したマップの対応する各マッピングの値を比較するためのメソッド
 */
public class IsMap extends BaseMatcher<Map<?, ?>> {

    private final Map<?, ?> expected; // 想定したマップ
    Object actual; // 実際に取得したマップ

    IsMap(Map<?, ?> expected) {
        this.expected = expected;
    }

    @Override
    public boolean matches(Object actual) {
        this.actual = actual;
        if(!(actual instanceof Map<?, ?>)) {
            // マップインスタンスと異なる場合はfalseを戻す。
            return false;
        }
        Map<?, ?> actualMap = (Map<?, ?>) actual;
        if(actualMap.size() != expected.size()) {
            // 実際に取得したマップと想定したマップのマッピング数が異なる場合はfalseを戻す。
            return false;
        }
        if(!actualMap.equals(expected)) {
            // 対応するマッピングのキーとvalueの値が異なる場合はfalseを戻す。
            return false;
        }

        // 対応するマッピングのキーとvalueの値が全て一致する場合はtrueを戻す。
        return true;
    }

    @Override
    public void describeTo(Description desc) {
        desc.appendValue(expected);
    }

    public static Matcher<Map<?, ?>> sameEntrySetAs(Map<?, ?> expected) {
        return new IsMap(expected);
    }

}
