package util;

import org.apache.struts.action.RedirectingActionForward;

public class RedirectUtil {

    /**
     * ログイン後の遷移先URLを指定してログイン画面にリダレクトするためのメソッド
     * @param redirectUrl リダイレクト先URL
     * @return ログイン後の遷移先URLを指定したログイン画面へのリダイレクト情報
     */
    public RedirectingActionForward getRedirectLoginAction(String redirectUrl) {
        // ログイン後の遷移先URLを指定したログイン画面へのリダイレクト情報を生成して戻す。
        return new RedirectingActionForward("/login.do?redirectUrl=" + redirectUrl);
    }

    /**
     * ログイン後の遷移先URL(パラメーター付き)を指定してログイン画面にリダレクトするためのメソッド
     * @param redirectUrl リダイレクトURL
     * @param parameterName パラメーター名
     * @param parameterValue パラメーター値
     * @return ログイン後の遷移先URL(パラメーター付き)を指定したログイン画面へのリダイレクト情報
     */
    public RedirectingActionForward getRedirectLoginAction(
            String redirectUrl,
            String parameterName,
            int parameterValue) {
        // パラメーターを生成する。
        String parameter = "?" + parameterName + "=" + parameterValue;

        // ログイン後の遷移先URL(パラメーター付き)を指定したログイン画面へのリダイレクト情報を生成して戻す。
        return new RedirectingActionForward("/login.do?redirectUrl=" + redirectUrl + parameter);
    }

    /**
     * 遷移先URLにリダレクトするためのメソッド
     * @param redirectUrl リダイレクトURL
     * @return 遷移先URLを指定したリダイレクト情報
     */
    public RedirectingActionForward getRedirectAction(String redirectUrl) {
        // 遷移先URLを指定したリダイレクト情報を生成して戻す。
        return new RedirectingActionForward(redirectUrl);
    }

    /**
     * 遷移先URL(パラメーター付き)にリダレクトするためのメソッド
     * @param redirectUrl リダイレクトURL
     * @param parameterName パラメーター名
     * @param parameterValue パラメーター値
     * @return 遷移先URL(パラメーター付き)を指定したリダイレクト情報
     */
    public RedirectingActionForward getRedirectAction(
            String redirectUrl,
            String parameterName,
            int parameterValue) {
        // パラメーターを生成する。
        String parameter = "?" + parameterName + "=" + parameterValue;

        // 遷移先URL(パラメーター付き)を指定したリダイレクト情報を生成して戻す。
        return new RedirectingActionForward(redirectUrl + parameter);
    }

}
