package exception;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class ReferredByCategoryExceptionTest {

    @Test
    public void testReferredByCategoryException() {
        String expected = "カテゴリー参照例外";

        ReferredByCategoryException actual = new ReferredByCategoryException("カテゴリー参照例外");

        assertThat(actual.getMessage(), is(expected));
    }

}
