package dao;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.property.PropertyTester.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

import org.junit.Test;

public class ConnectorDAOTest {

    @Test
    public void testConnectDatabase() throws Exception {
        ConnectorDAO sut = new ConnectorDAO() {
            Properties getProperties() throws IOException {
                return getProperty("dao/db.properties");
            }
        };
        Connection actual = sut.connectDatabase();

        assertThat(actual, is(notNullValue()));
    }

}
