package util;

public class ListUtil {

    /**
     * 一覧画面で表示する全ページ数を産出するためのメソッド
     * @param listSize 全件数
     * @param displayedResult 表示件数/ページ
     * @return 全ページ数
     * @exception ArithmeticException
     */
    public int calcTotalPage(int listSize, int displayedResult) throws ArithmeticException {
        // 全件数を表示件数/ページで割って、全ページ数を算出する。
        int totalPage = listSize / displayedResult;
        if(listSize % displayedResult != 0) {
            // 全件数を表示件数/ページで割り切れない場合は全ページ数に1を加算する。
            totalPage += 1;
        }

        // 全ページ数を戻す。
        return totalPage;
    }

}
