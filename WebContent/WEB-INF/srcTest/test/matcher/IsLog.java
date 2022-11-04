package test.matcher;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * 想定したログファイルと実際に取得したログファイルの中身を比較するためのメソッド
 */
public class IsLog extends BaseMatcher<File> {

    private final File expected; // 想定したログファイル
    Object actual; // 実際に取得したログファイル

    IsLog(File expected) {
        this.expected = expected;
    }

    @Override
    public boolean matches(Object actual){
        this.actual = actual;
        if(!(actual instanceof File)) {
            // ファイルインスタンスと異なる場合はfalseを戻す。
            return false;
        }
        File actualLog = (File) actual;
        try {
            if(!FileUtils.contentEquals(actualLog, expected)) {
                // 実際に取得したログファイルと想定したログファイルの中身が異なる場合はfalseを戻す。
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 実際に取得したログファイルと想定したログファイルの中身が一致する場合はtrueを戻す。
        return true;
    }

    @Override
    public void describeTo(Description desc) {
        desc.appendValue(expected);
    }

    public static Matcher<File> containOfSameDescription(File expected) {
        return new IsLog(expected);
    }

}
