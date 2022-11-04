package util;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class ListUtilTest {

    @Test
    public void testClacToatalPageInCaseTotalPageOne() {
        int expected = 1;

        ListUtil sut = new ListUtil();
        int actual = sut.calcTotalPage(6, 10);

        assertThat(actual, is(expected));
    }

    @Test
    public void testClacToatalPageInCaseTotalPageTwo() {
        int expected = 2;

        ListUtil sut = new ListUtil();
        int actual = sut.calcTotalPage(15, 11);

        assertThat(actual, is(expected));
    }

    @Test
    public void testClacToatalPageInCaseTotalPageTen() {
        int expected = 10;

        ListUtil sut = new ListUtil();
        int actual = sut.calcTotalPage(113, 12);

        assertThat(actual, is(expected));
    }

    @Test(expected = ArithmeticException.class)
    public void testClacToatalPageInCasedenominatorIsZero() {
        ListUtil sut = new ListUtil();
        sut.calcTotalPage(113, 0);
    }

}
