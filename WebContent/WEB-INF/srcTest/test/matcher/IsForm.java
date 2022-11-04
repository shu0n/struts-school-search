package test.matcher;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

import org.apache.struts.action.ActionForm;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * 想定したアクションフォームと実際に取得したアクションフォームの対応する各プロパティの値を比較するためのクラス
 */
public class IsForm<T extends ActionForm> extends BaseMatcher<T> {

    private final T expected; // 想定したアクションフォーム
    private final String[] excludeProperties; // 比較の対象外とするプロパティ名
    Object actual; // 実際に取得したアクションフォーム

    IsForm(T expected) {
        this(expected, new String[0]);
    }

    IsForm(T expected, String[] excludeProperties) {
        this.expected = expected;
        this.excludeProperties = excludeProperties;
    }

    @Override
    public boolean matches(Object actual) {
        this.actual = actual;
        if(actual == null || expected == null) {
            // いずれかのアクションフォームがNULLの場合はfalseを戻す。
            return false;
        }
        T actualForm = (T) actual;
        try {
            BeanInfo bi = Introspector.getBeanInfo(actualForm.getClass());
            PropertyDescriptor[] pds = bi.getPropertyDescriptors();
            // 比較から除外するプロパティ名を格納した配列を生成する。
            String[] exceptProperties =
                {"class", "multipartRequestHandler", "page", "resultValueMap",
                "servlet", "servletWrapper", "validatorResults"};
            // 対応する各プロパティの値を比較する。
            for (PropertyDescriptor pd: pds) {
                if(Arrays.asList(exceptProperties).contains(pd.getName())) {
                    // プロパティ名が除外対象に含まれている場合は次のプロパティの検証に移る。
                    continue;
                }
                if (Arrays.asList(excludeProperties).contains(pd.getName())) {
                    // プロパティ名が比較の対象外に含まれている場合は次のプロパティの検証に移る。
                    continue;
                }
                Method getter = pd.getReadMethod();
                Object val1 = getter.invoke(actualForm);
                Object val2 = getter.invoke(expected);
                if (!Objects.equals(val1, val2)) {
                    // 対応するプロパティの値が一致しない場合はfalseを戻す。
                    return false;
                }
            }

            // 対応するプロパティの値が全て一致する場合はtrueを戻す。
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void describeTo(Description desc) {
        desc.appendValue(expected);
    }

    public static <T extends ActionForm> Matcher<T> samePropertyValueAs(T expected) {
        return new IsForm<T>(expected);
    }

    public static <T extends ActionForm> Matcher<T> samePropertyValueAs(T expected, String[] excludeProperties) {
        return new IsForm<T>(expected, excludeProperties);
    }

}
