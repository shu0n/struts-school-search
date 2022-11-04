package util;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import servletunit.HttpServletRequestSimulator;
import servletunit.ServletContextSimulator;

public class PasswordUtilTest {

    @Test
    public void testGetSafetyPassword() throws Exception {
        String expected = "b4b285e7a0b1c15ec23aa716bd3b1aeb2d951f192133393e8cd4cdb39c89bf99";

        PasswordUtil sut = new PasswordUtil();
        String actual = sut.getSafetyPassword("test@example.com", "password");

        assertThat(actual, is(expected));
    }

    @Test
    public void testSetMaskedPassword() {
        String expected = "●●●●●●●●●●●●●●";

        String password = "123passwordABC";

        HttpServletRequestSimulator stub = new HttpServletRequestSimulator(new ServletContextSimulator());

        PasswordUtil sut = new PasswordUtil();
        HttpServletRequest actual = sut.setMaskedPassword(password, stub);

        assertThat(actual.getAttribute("maskedPassword"), is(expected));
    }

}
