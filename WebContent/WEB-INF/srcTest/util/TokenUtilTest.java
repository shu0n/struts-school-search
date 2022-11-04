package util;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class TokenUtilTest {

    @Test
    public void testGenerateToken() {
        String expected = "Rtk8cVn1WcO2tAFpxLkjZjQqq7mdUEUrCw6LfSsFPrXoGJ2Jcn9kUVE-hQWIHsVC";

        TokenUtil sut = new TokenUtil() {
            byte[] getRandomBytes() {
                byte[] bytes = {70, -39, 60, 113, 89, -11, 89, -61, -74, -76, 1, 105, -60, -71, 35, 102};
                return bytes;
            }
        };
        String actual = sut.generateToken("test@example.com");

        assertThat(actual, is(expected));
    }

}
