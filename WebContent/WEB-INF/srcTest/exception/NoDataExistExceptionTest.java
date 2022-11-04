package exception;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class NoDataExistExceptionTest {

    @Test
    public void testNoDataExistException() {
        String expected = "データ不存在例外";

        NoDataExistException actual = new NoDataExistException("データ不存在例外");

        assertThat(actual.getMessage(), is(expected));
    }

}
