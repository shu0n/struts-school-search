package util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

public class DateUtil {

    /**
     * 現在日時から指定期間後の日時(日時型)を算出するためのメソッド
     * @param timeMillis 加算するミリ秒
     * @return 現在日時にミリ秒を加算して日時型に変換した日時
     */
    public Timestamp getAddedTimestamp(long timeMillis) {
        // 現在日時のミリ秒に引数で指定したミリ秒を加算する。
        long addedTimemillis = nowTimeMillis() + timeMillis;

        // 算出したミリ秒から日時を算出して戻す。
        return new Timestamp(addedTimemillis);
    }

    /**
     * 「年」「月」「日」を結合した文字列を生成するためのメソッド
     * @param year 年
     * @param month 月
     * @param day 日
     * @oaram isEndFlag 末日指定フラグ
     * @return 文字列型の年月日(yyyy-MM-dd HH:mm:ss.SSS)
     */
    public String joinedStrDateToTimestamp(String year, String month, String day, boolean isEndFlag) {
        if(StringUtils.isEmpty(year)) {
            // 年が入力されていない場合は末日指定フラグを判定する。
            if(!isEndFlag) {
                // falseの場合は"1920"を格納する。
                year = "1920";
            } else {
                // 上記以外の場合は現在日時の"年"を格納する。
                year = new SimpleDateFormat("yyyy").format(new Timestamp(nowTimeMillis()));
            }
        }
        if(StringUtils.isEmpty(month)) {
            // 月が入力されていない場合は末日指定フラグを判定する。
            if(!isEndFlag) {
                // falseの場合は"01"を格納する。
                month = "01";
            } else {
                // 上記以外の場合は"12"を格納する。
                month = "12";
            }
        }
        if(StringUtils.isEmpty(day)) {
            // 日が入力されていない場合は末日指定フラグを判定する。
            if(!isEndFlag) {
                // falseの場合は"01"を格納する。
                day = "01";
            } else {
                // 上記以外の場合

                // カレンダークラスのインスタンスを生成する。
                Calendar calendar = Calendar.getInstance();
                // 年のカレンダ・フィールドを引数の年に設定します。
                calendar.set(Calendar.YEAR, Integer.parseInt(year));
                // 月のカレンダ・フィールドを引数の月に設定します。
                calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
                // 設定した年と月の組み合わせの場合の最終日を取得して格納する。
                day = String.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            }
        }

        // 戻すための変数を生成する。
        String strDate = "";
        // 「年」「月」「日」を"yyyy-MM-dd"に結合する。
        if(!isEndFlag) {
            // 末日指定フラグがfalseの場合
            strDate = year
                    + "-" + String.format("%2s", month).replace(" ", "0")
                    + "-" + String.format("%2s", day).replace(" ", "0")
                    + " 00:00:00.000";
        } else {
            // 上記以外の場合
            strDate = year
                    + "-" + String.format("%2s", month).replace(" ", "0")
                    + "-" + String.format("%2s", day).replace(" ", "0")
                    + " 23:59:59.999";
        }

        // 結合した年月日を戻す。
        return strDate;
    }

    /**
     * UTC(協定世界時)：1970/01/01 09:00:00.000と現在日時(ローカル)との差をミリ秒で取得するためのメソッド
     */
    long nowTimeMillis() {
        // UTC(協定世界時)：1970/01/01 09:00:00.000と現在日時(ローカル)との差をミリ秒で戻す。
        return System.currentTimeMillis();
    }

}
