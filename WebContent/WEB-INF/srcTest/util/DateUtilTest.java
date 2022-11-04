package util;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.sql.Timestamp;

import org.junit.Test;

public class DateUtilTest {

    @Test
    public void testGetAddedTimestamp() {
        Timestamp expexted = new Timestamp(86400000);

        DateUtil sut = new DateUtil() {
            long nowTimeMillis() {
                return 0;
            }
        };
        Timestamp actual = sut.getAddedTimestamp(86400000);

        assertThat(actual, is(expexted));
    }

    @Test
    public void testJoinedStringTimestampWithYmdAndPointStart() {
        String expected = "2001-02-03 00:00:00.000";

        DateUtil sut = new DateUtil();
        String actual = sut.joinedStrDateToTimestamp("2001", "02", "03", false);

        assertThat(actual, is(expected));
    }

    @Test
    public void testJoinedStringTimestampWithYmdAndPointEnd() {
        String expected = "2004-05-06 23:59:59.999";

        DateUtil sut = new DateUtil();
        String actual = sut.joinedStrDateToTimestamp("2004", "5", "6", true);

        assertThat(actual, is(expected));
    }

    @Test
    public void testJoinedStringTimestampWithoutYmdAndPointStart() {
        String expected = "1920-01-01 00:00:00.000";

        DateUtil sut = new DateUtil();
        String actual = sut.joinedStrDateToTimestamp("", "", "", false);

        assertThat(actual, is(expected));
    }

    @Test
    public void testJoinedStringTimestampWithoutYmdAndPointEnd() {
        String expected = "1970-12-31 23:59:59.999";

        DateUtil sut = new DateUtil() {
            long nowTimeMillis() {
                return 0;
            }
        };
        String actual = sut.joinedStrDateToTimestamp("", "", "", true);

        assertThat(actual, is(expected));
    }


    @Test
    public void testJoinedStringTimestampWithoutDayAndPointEnd() {
        String expected = "1920-02-29 23:59:59.999";

        DateUtil sut = new DateUtil();
        String actual = sut.joinedStrDateToTimestamp("1920", "2", "", true);

        assertThat(actual, is(expected));
    }

}
