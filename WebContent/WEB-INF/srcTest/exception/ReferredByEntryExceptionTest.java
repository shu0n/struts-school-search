package exception;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class ReferredByEntryExceptionTest {

    @Test
    public void testReferredByEntryException() {
        String expected = "申込参照例外";

        ReferredByEntryException actual = new ReferredByEntryException("申込参照例外");

        assertThat(actual.getMessage(), is(expected));
    }

}
