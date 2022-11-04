import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.standardinout.StandardInputSnatcher;
import test.standardinout.StandardOutputSnatcher;

public class PasswordGeneratorForCommandLineTest {

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
    public void testMainToGeneratePassword() {
        String expectedFirstLine = "メールアドレスを入力してください。";
        String expectedSecondLine = "パスワードを入力してください。";
        String expectedPassword = "生成された安全なパスワード：b93085b8907a11898e0b090007948e67611e1bba8ebc4a4084cb63f8846d18f8";

        in.inputln("test@example.com");
        in.inputln("password");
        PasswordGeneratorForCommandLine sut = new PasswordGeneratorForCommandLine();
        sut.main(null);

        assertThat(out.readLine(), is(expectedFirstLine));
        assertThat(out.readLine(), is(expectedSecondLine));
        assertThat(out.readLine(), is(expectedPassword));
    }

}
