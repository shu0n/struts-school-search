package test.matcher;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * 想定したリストと実際に取得したリストの対応する各配列に格納された値を比較するためのクラス
 */
public class IsListComposedStringArray extends BaseMatcher<List<String[]>> {

    private final List<String[]> expected;
    Object actual;

    IsListComposedStringArray(List<String[]> expected) {
        this.expected = expected;
    }

    @Override
    public boolean matches(Object actual) {
        this.actual = actual;
        if(!(actual instanceof List)) {
            // リストと異なるインスタンスタイプの場合はfalseを戻す。
            return false;
        }
        List<String[]> actualList = (List<String[]>) actual;
        if(actualList.size() != expected.size()) {
            // 実際に取得したリストと想定したリストの要素数が異なる場合はfalseを戻す。
            return false;
        }
        for(int i = 0; i < actualList.size(); i++) {
            if(!Arrays.equals(actualList.get(i), expected.get(i))) {
                // 対応する配列に格納された値が異なる場合はfalseを戻す。
                return false;
            }
        }

        // 対応する配列に格納された値が全て一致する場合はtrueを戻す。
        return true;
    }

    @Override
    public void describeTo(Description desc) {
        desc.appendValue(expected);
    }

    public static Matcher<List<String[]>> sameComponentValueAs(List<String[]> expected) {
        return new IsListComposedStringArray(expected);
    }

}
