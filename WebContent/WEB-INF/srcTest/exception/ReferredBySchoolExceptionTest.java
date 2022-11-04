package exception;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class ReferredBySchoolExceptionTest {

    @Test
    public void testReferredBySchoolException() {
        String expected = "スクール参照例外";

        ReferredBySchoolException actual = new ReferredBySchoolException("スクール参照例外");

        assertThat(actual.getMessage(), is(expected));
    }

}
