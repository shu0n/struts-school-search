package util;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.apache.struts.action.RedirectingActionForward;
import org.junit.Test;

public class RedirectUtilTest {

    @Test
    public void testGetRedirectLoginAction() {
        String expected = "/login.do?redirectUrl=http://www.example.com";

        RedirectUtil sut = new RedirectUtil();
        RedirectingActionForward actual = sut.getRedirectLoginAction("http://www.example.com");

        assertThat(actual.getPath(), is(expected));
    }

    @Test
    public void testGetRedirectLoginActionWithParameter() {
        String expected = "/login.do?redirectUrl=http://www.example.com?parameterName=123";

        RedirectUtil sut = new RedirectUtil();
        RedirectingActionForward actual
            = sut.getRedirectLoginAction("http://www.example.com", "parameterName", 123);

        assertThat(actual.getPath(), is(expected));
    }

    @Test
    public void testGetRedirectAction() {
        String expected = "http://example.com";

        RedirectUtil sut = new RedirectUtil();
        RedirectingActionForward actual
            = sut.getRedirectAction("http://example.com");

        assertThat(actual.getPath(), is(expected));
    }

    @Test
    public void testGetRedirectActionWithParameter() {
        String expected = "http://example.com?parameterKey=456";

        RedirectUtil sut = new RedirectUtil();
        RedirectingActionForward actual
            = sut.getRedirectAction("http://example.com", "parameterKey", 456);

        assertThat(actual.getPath(), is(expected));
    }

}
