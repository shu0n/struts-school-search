package test.matcher;

import java.util.List;

import org.apache.struts.util.LabelValueBean;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * 想定したリストと実際に取得したリストの対応する各LavelValueBeanの値を比較するためのクラス
 */
public class IsListComposedLabelValueBean extends BaseMatcher<List<LabelValueBean>> {

    private final List<LabelValueBean> expected; // 想定したリスト
    Object actual; // 実際に取得したリスト

    IsListComposedLabelValueBean(List<LabelValueBean> expected) {
        this.expected = expected;
    }

    @Override
    public boolean matches(Object actual) {
        this.actual = actual;
        if(!(actual instanceof List)) {
            // リストと異なるインスタンスタイプの場合はfalseを戻す。
            return false;
        }
        List<LabelValueBean> actualList = (List<LabelValueBean>) actual;
        if(actualList.size() != expected.size()) {
            // 実際に取得したリストと想定したリストの要素数が異なる場合はfalseを戻す。
            return false;
        }
        // 対応する各LavelValueBeanの値を比較する。
        for(int i = 0; i < actualList.size(); i++) {
            if(!actualList.get(i).getLabel().equals(expected.get(i).getLabel())) {
                // 対応するラベルの値が異なる場合はfalseを戻す。
                return false;
            }
            if(!actualList.get(i).getValue().equals(expected.get(i).getValue())) {
                // 対応するバリューの値が異なる場合はfalseを戻す。
                return false;
            }
        }

        // 対応する各LavelValueBeanに格納された値が全て一致する場合はtrueを戻す。
        return true;
    }

    @Override
    public void describeTo(Description desc) {
        desc.appendValue(expected);
    }

    public static Matcher<List<LabelValueBean>> sameComponentLavelAndValueAs(List<LabelValueBean> expected) {
        return new IsListComposedLabelValueBean(expected);
    }

}
