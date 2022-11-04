package util;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.apache.struts.action.RedirectingActionForward;
import org.junit.Test;

public class RedirectAdminUtilTest {

    @Test
    public void testGetRedirectAdminLoginAction() {
        String expected = "/login.do?redirectUrl=http://www.example.com";

        RedirectAdminUtil sut = new RedirectAdminUtil();
        RedirectingActionForward actual = sut.getRedirectAdminLoginAction("http://www.example.com");

        assertThat(actual.getPath(), is(expected));
    }

    @Test
    public void testGetRedirectLoginActionWithParameter() {
        String expected = "/login.do?redirectUrl=http://www.example.com?parameterName=123";

        RedirectAdminUtil sut = new RedirectAdminUtil();
        RedirectingActionForward actual
            = sut.getRedirectAdminLoginAction("http://www.example.com", "parameterName", "123");

        assertThat(actual.getPath(), is(expected));
    }

    @Test
    public void testGetRedirectAction() {
        String expected = "http://example.com";

        RedirectAdminUtil sut = new RedirectAdminUtil();
        RedirectingActionForward actual
            = sut.getRedirectAdminAction("http://example.com");

        assertThat(actual.getPath(), is(expected));
    }

    @Test
    public void testGetRedirectActionWithParameter() {
        String expected = "http://example.com?parameterKey=456";

        RedirectAdminUtil sut = new RedirectAdminUtil();
        RedirectingActionForward actual
            = sut.getRedirectAdminAction("http://example.com", "parameterKey", "456");

        assertThat(actual.getPath(), is(expected));
    }

}
