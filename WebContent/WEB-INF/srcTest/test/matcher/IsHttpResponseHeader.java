package test.matcher;

import javax.servlet.http.HttpServletResponse;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * レスポンスヘッダーに想定したファイル名が含まれているかを検証するためのクラス
 */
public class IsHttpResponseHeader extends BaseMatcher<HttpServletResponse> {

    private final String contentType; // 想定したコンテンツタイプ
    private final String fileName; // 想定したファイル名
    Object actual; // 実際に取得したレスポンス

    IsHttpResponseHeader(String contentType, String fileName) {
        this.contentType = contentType;
        this.fileName = fileName;
    }

    @Override
    public boolean matches(Object actual) {
        this.actual = actual;
        if(!(actual instanceof HttpServletResponse)) {
            // レスポンスと異なるインスタンスタイプの場合はfalseを戻す。
            return false;
        }
        HttpServletResponse response = (HttpServletResponse) actual;
        if(!response.getContentType().equals(contentType)) {
            // 想定したコンテンツタイプと異なる場合はfalseを戻す。
            return false;
        }
        if(!response.getHeader("Content-Disposition").equals("attachment; filename=\"" + fileName + "\"")) {
            // 想定したファイル名と異なる場合はfalseを戻す。
            return false;
        }

        // レスポンスに想定したコンテンツタイプおよびファイル名が格納されている場合はtrueを戻す。
        return true;
    }

    @Override
    public void describeTo(Description desc) {
        desc.appendText(" contenttype: ");
        desc.appendValue(contentType);
        desc.appendText(" filename: ");
        desc.appendValue(fileName);
    }

    public static Matcher<HttpServletResponse> contentOf(String contentType, String fileName) {
        return new IsHttpResponseHeader(contentType, fileName);
    }

}
