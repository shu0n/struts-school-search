package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;

public class LogUtil {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator"); // 改行コード

    /**
     * 例外の内容をログに出力するためのメソッド
     * @param className クラス名
     * @param exception 例外
     */
    public void writeExceptionLog(String className, Exception exception) {
        try {
            // 保持期限を超過したエラーログを削除する。
            deleteExpiredErrorLog();

            // エラーログの格納先パスを取得する。
            String errorLogPath = getProperties().getProperty("error.log.path");

            // 出力先のエラーログのファイル名に使用するための現在日時を"yyyy-MM-dd"形式で取得する。
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
            // 出力先のエラーログのファイル名を生成する。
            String errorLogFileName = "error." + sdFormat.format(getDate()).toString() + ".log";

            // Timestamp型の現在日時から文字列型の現在日時を取得する。
            String currentTime = new SimpleDateFormat("yyyy/MM/dd HH:MM:ss.SSS").format(getCurrentTimestamp());

            // エラーログの格納先パスと生成したファイル名をもとに
            // PrintWriterクラスのインスタンスを追記モードかつ文字コードをUTF-8に指定して生成する。
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(errorLogPath + "/" + errorLogFileName, true), "utf-8")));
            // エラーログに"現在日時 クラス名 例外のメッセージ"を出力する。
            printWriter.write(
                    currentTime + " " + exception.getMessage()
                    + LINE_SEPARATOR
                    + className
                    + LINE_SEPARATOR);
            // エラーログにスタックトレースの内容を出力する。
            exception.printStackTrace(printWriter);
            // PrintWriterクラスのインスタンスを閉じる。
            printWriter.close();
        } catch(Exception e) {
            // スタックトレースを出力する。
            e.printStackTrace();
        }
    }

    /**
     * 保持期限を超過するエラーログを削除するためのメソッド
     * @throws IOException
     * @throws ParseException
     */
    public void deleteExpiredErrorLog() throws IOException, ParseException {
        // エラーログの格納先パスを取得する。
        String errorLogPath = getProperties().getProperty("error.log.path");
        // 取得した格納先パスをもとにエラーログファイルの一覧を取得する。
        File errorLogDirectory = new File(errorLogPath);
        // エラーログの一覧の取得時に使用するフィルタ-を作成する。
        FilenameFilter searchFilter = new FilenameFilter() {
            public boolean accept(File file, String str){
                if (str.indexOf("error.") != -1){
                    // 指定文字列が存在する場合はtrueを戻す。
                    return true;
                } else {
                    // 上記以外の場合はfalseを戻す。
                    return false;
                }
            }
        };
        // エラーログのファイル一覧を配列で取得する。
        File[] errorLogFileArray = errorLogDirectory.listFiles(searchFilter);
        if (!ArrayUtils.isEmpty(errorLogFileArray)) {
            // 得した配列の要素が1つ以上がある場合

            // エラーログの保持期限を取得する。
            String retentionPeriod = getProperties().getProperty("error.log.retention.period");

            // Date型の現在日時をCalendar型に変換する。
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(getDate());
            // 現在日時から保持期限を減算して保持期限を超過した日時を取得する。
            calendar.add(Calendar.DATE, -Integer.parseInt(retentionPeriod));
            // 取得した日時をDate型に変換する。
            Date retentionPeriodDate = calendar.getTime();

            // ファイル名から日時を示す文字列を取得するための正規表現のインスタンスを生成する。
            Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
            // 取得したエラーログの件数分、処理を繰り返す。
            for(int i = 0; i < errorLogFileArray.length; i++) {
                // 配列からエラーログを取得する。
                File errorLogFile = errorLogFileArray[i];

                // エラーログのファイル名をもとにマッチ操作を行う。
                Matcher matcher = pattern.matcher(errorLogFile.getName());
                // 一致した文字列を格納するための変数を生成する。
                String strFileDate = "";
                if(matcher.find()) {
                    // 変数に一致した文字列を格納する。
                    strFileDate = matcher.group(0);
                }
                // マッチ操作をもとに日時を示す文字列を取得してDate型に変換する。
                SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date fileDate = sdFormat.parse(strFileDate);

                if(fileDate.before(retentionPeriodDate)) {
                    // 取得した日時が保持期限を超過している場合は該当するエラーログを削除する。
                    errorLogFile.delete();
                }
            }
        }
    }

    /**
     * メール送信時の情報をログに出力するためのメソッド
     * @param to 送信先メールアドレス
     * @param subject 件名
     * @throws ParseException
     * @throws IOException
     */
    public void writeMailLog(String to, String subject) throws IOException, ParseException {
        // 保持期限を超過したメールログを削除する。
        deleteExpiredMailLog();

        // メールログの格納先パスを取得する。
        String mailLogPath = getProperties().getProperty("mail.log.path");

        // 出力先のメールログのファイル名に使用するための現在日時を"yyyy-MM-dd"形式で取得する。
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 出力先のメール送信ログのファイル名を生成する。
        String mailLogFileName = "mail." + sdFormat.format(getDate()).toString() + ".log";

        // Timestamp型の現在日時から文字列型の現在日時を取得する。
        String currentTime = new SimpleDateFormat("yyyy/MM/dd HH:MM:ss.SSS").format(getCurrentTimestamp());

        // メールログの格納先パスと生成したファイル名をもとに
        // PrintWriterクラスのインスタンスを追記モードかつ文字コードをUTF-8に指定して生成する。
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(mailLogPath + "/" + mailLogFileName, true), "utf-8")));
        // メールログに"現在日時 送信先メールアドレス 件名"を出力する。
        printWriter.write(currentTime + " To:" + to + " Subject:" + subject + LINE_SEPARATOR);
        // PrintWriterクラスのインスタンスを閉じる。
        printWriter.close();
    }

    /**
     * 保持期限を超過するメールログを削除するためのメソッド
     * @throws IOException
     * @throws ParseException
     */
    public void deleteExpiredMailLog() throws IOException, ParseException {
        // メールログの格納先パスを取得する。
        String mailLogPath = getProperties().getProperty("mail.log.path");
        // 取得した格納先パスをもとにメールログファイルの一覧を取得する。
        File mailLogDirectory = new File(mailLogPath);
        // メールログの一覧の取得時に使用するフィルタ-を作成する。
        FilenameFilter searchFilter = new FilenameFilter() {
            public boolean accept(File file, String str){
                if (str.indexOf("mail.") != -1){
                    // 指定文字列が存在する場合はtrueを戻す。
                    return true;
                } else {
                    // 上記以外の場合はfalseを戻す。
                    return false;
                }
            }
        };
        // メールログのファイル一覧を配列で取得する。
        File[] mailLogFileArray = mailLogDirectory.listFiles(searchFilter);
        if (!ArrayUtils.isEmpty(mailLogFileArray)) {
            // 得した配列の要素が1つ以上がある場合

            // メールログの保持期限を取得する。
            String retentionPeriod = getProperties().getProperty("mail.log.retention.period");

            // Date型の現在日時をCalendar型に変換する。
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(getDate());
            // 現在日時から保持期限を減算して保持期限を超過した日時を取得する。
            calendar.add(Calendar.DATE, -Integer.parseInt(retentionPeriod));
            // 取得した日時をDate型に変換する。
            Date retentionPeriodDate = calendar.getTime();

            // ファイル名から日時を示す文字列を取得するための正規表現のインスタンスを生成する。
            Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
            // 取得したメールログの件数分、処理を繰り返す。
            for(int i = 0; i < mailLogFileArray.length; i++) {
                // 配列からエラーログを取得する。
                File mailLogFile = mailLogFileArray[i];

                // メールログのファイル名をもとにマッチ操作を行う。
                Matcher matcher = pattern.matcher(mailLogFile.getName());
                // 一致した文字列を格納するための変数を生成する。
                String strFileDate = "";
                if(matcher.find()) {
                    // 変数に一致した文字列を格納する。
                    strFileDate = matcher.group(0);
                }
                // マッチ操作をもとに日時を示す文字列を取得してDate型に変換する。
                SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date fileDate = sdFormat.parse(strFileDate);

                if(fileDate.before(retentionPeriodDate)) {
                    // 取得した日時が保持期限を超過している場合は該当するメールログを削除する。
                    mailLogFile.delete();
                }
            }
        }
    }

    /**
     * プロパティ情報を取得するためのメソッド
     */
    Properties getProperties() throws IOException {
        // プロパティ情報を戻す。
        return new PropertyUtil().getProperty("environment.properties");
    }

    /**
     * 現在日時を取得するためのメソッド
     * @throws ParseException
     */
    Date getDate() throws ParseException {
        // 現在日時を戻す。
        return new Date();
    }

    /**
     * 現在日時を取得するためのメソッド
     */
    Timestamp getCurrentTimestamp() {
        // 現在日時を戻す。
        return new DateUtil().getAddedTimestamp(0);
    }

}
