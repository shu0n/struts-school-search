import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsRegex.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.standardinout.StandardInputSnatcher;
import test.standardinout.StandardOutputSnatcher;

public class TokenGeneratorForCommandLineTest {

    private StandardInputSnatcher in = new StandardInputSnatcher();
    private StandardOutputSnatcher out = new StandardOutputSnatcher();

    @Before
    public void before() {
        System.setOut(out);
        System.setIn(in);
    }

    @After
    public void after() {
        System.setOut(null);
        System.setIn(null);
    }

    @Test
    public void testtestMainToGenerateToken() {
        String expectedFirstLine = "メールアドレスを入力してください。";
        String expectedToken = "生成されたトークン：[a-zA-Z1-9 -~]";

        in.inputln("test@example.com");
        TokenGeneratorForCommandLine sut = new TokenGeneratorForCommandLine();
        sut.main(null);

        assertThat(out.readLine(), is(expectedFirstLine));
        assertThat(out.readLine(), is(matchPatternAs(expectedToken)));
    }

}
