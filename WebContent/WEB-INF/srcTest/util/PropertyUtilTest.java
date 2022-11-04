package util;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.junit.Test;

public class PropertyUtilTest {

    @Test
    public void testGetProperties() throws Exception {
        String expected = "SchoolSearch";

        PropertyUtil sut = new PropertyUtil();
        String actual = sut.getProperty("environment.properties").getProperty("site.name");

        assertThat(actual, is(expected));
    }

    @Test(expected = IOException.class)
    public void testGetPropertiesInCaseWrongFileName() throws Exception {
        PropertyUtil sut = new PropertyUtil();
        sut.getProperty("enviromenu.properties");
    }

}
