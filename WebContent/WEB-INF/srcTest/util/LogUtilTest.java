package util;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsLog.*;
import static test.property.PropertyTester.*;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Test;

public class LogUtilTest {

    // テスト時に生成されるエラーログのファイル名を格納するためのリストを生成する。
    private List<String> errorLogFileNameList = new ArrayList<>();
    // テスト時に生成されるメールログのファイル名を格納するためのリストを生成する。
    private List<String> mailLogFileNameList = new ArrayList<>();

    @After
    public void after() throws Exception {
        String errorLogPath = getProperty("util/LogUtilTest.properties").getProperty("error.log.path");
        // テスト時に生成されたエラーログを削除する。
        if(!errorLogFileNameList.isEmpty()) {
            for(String fileName: errorLogFileNameList) {
                Path filePath = Paths.get(errorLogPath + fileName);
                Files.delete(filePath);
            }
        }
        errorLogFileNameList = new ArrayList<>();

        String mailLogPath = getProperty("util/LogUtilTest.properties").getProperty("mail.log.path");
        // テスト時に生成されたメールログを削除する。
        if(!mailLogFileNameList.isEmpty()) {
            for(String fileName: mailLogFileNameList) {
                Path filePath = Paths.get(mailLogPath + fileName);
                Files.delete(filePath);
            }
        }
        mailLogFileNameList = new ArrayList<>();
    }

    @Test
    public void testWriteExceptionLogToMakeNewLog() throws Exception {
        Properties props = getProperty("util/LogUtilTest.properties");
        String errorLogPath = props.getProperty("error.log.path");
        File expected = new File(errorLogPath + "/LogUtilTest.testWriteExceptionLogToMakeNewLog.log");

        NullPointerException nullPointerException = new NullPointerException("NullPointerException is occured");

        LogUtil sut = new LogUtil() {
            Properties getProperties() throws IOException {
                return getProperty("util/LogUtilTest.properties");
            }

            Date getDate() throws ParseException {
                String date = "1970/01/01 09:00:00";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                return sdf.parse(date);
            }

            Timestamp getCurrentTimestamp() {
                return new Timestamp(0);
            }
        };
        sut.writeExceptionLog(this.getClass().getName(), nullPointerException);
        File actual = new File(errorLogPath + "/error.1970-01-01.log");
        errorLogFileNameList.add("/error.1970-01-01.log");

        assertThat(actual, is(containOfSameDescription(expected)));
    }

    @Test
    public void testWriteExceptionLogToAddLog() throws Exception {
        Properties props = getProperty("util/LogUtilTest.properties");
        String errorLogPath = props.getProperty("error.log.path");
        File expected = new File(errorLogPath + "/LogUtilTest.testWriteExceptionLogToAddLog.log");

        NullPointerException nullPointerException
                = new NullPointerException("NullPointerException is occured");
        IllegalArgumentException illegalArgumentException
                = new IllegalArgumentException("IllegalArgumentException is occured");

        LogUtil sut = new LogUtil() {
            Properties getProperties() throws IOException {
                return getProperty("util/LogUtilTest.properties");
            }

            Date getDate() throws ParseException {
                String date = "1970/01/02 09:00:00";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                return sdf.parse(date);
            }

            Timestamp getCurrentTimestamp() {
                return new Timestamp(86400000);
            }
        };
        sut.writeExceptionLog(this.getClass().getName(), nullPointerException);
        sut.writeExceptionLog(this.getClass().getName(), illegalArgumentException);
        File actual = new File(errorLogPath + "/error.1970-01-02.log");
        errorLogFileNameList.add("/error.1970-01-02.log");

        assertThat(actual, is(containOfSameDescription(expected)));
    }

    @Test
    public void testDeleteExpiredErrorLog() throws Exception {
        int expected = 5;

        Properties props = getProperty("util/LogUtilTest.properties");
        String errorLogPath = props.getProperty("error.log.path");

        Path expiredLogPath1 = Paths.get(errorLogPath + "/error.1969-12-26.log");
        Path expiredLogPath2 = Paths.get(errorLogPath + "/error.1969-12-27.log");
        Files.createFile(expiredLogPath1);
        Files.createFile(expiredLogPath2);

        Path notExpiredLogPath1 = Paths.get(errorLogPath + "/error.1969-12-28.log");
        Path notExpiredLogPath2 = Paths.get(errorLogPath + "/error.1969-12-29.log");
        Path notExpiredLogPath3 = Paths.get(errorLogPath + "/error.1969-12-30.log");
        Path notExpiredLogPath4 = Paths.get(errorLogPath + "/error.1969-12-31.log");
        Path notExpiredLogPath5 = Paths.get(errorLogPath + "/error.1970-01-01.log");
        Files.createFile(notExpiredLogPath1);
        Files.createFile(notExpiredLogPath2);
        Files.createFile(notExpiredLogPath3);
        Files.createFile(notExpiredLogPath4);
        Files.createFile(notExpiredLogPath5);

        errorLogFileNameList.add("/error.1969-12-28.log");
        errorLogFileNameList.add("/error.1969-12-29.log");
        errorLogFileNameList.add("/error.1969-12-30.log");
        errorLogFileNameList.add("/error.1969-12-31.log");
        errorLogFileNameList.add("/error.1970-01-01.log");

        LogUtil sut = new LogUtil() {
            Properties getProperties() throws IOException {
                return getProperty("util/LogUtilTest.properties");
            }

            Date getDate() throws ParseException {
                String date = "1970/01/01 09:00:00";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                return sdf.parse(date);
            }

            Timestamp getCurrentTimestamp() {
                return new Timestamp(0);
            }
        };
        sut.deleteExpiredErrorLog();

        // 生成されたエラーログの配列を取得する。
        File errorLogDirectory = new File(errorLogPath);
        FilenameFilter searchFilter = new FilenameFilter() {
            public boolean accept(File file, String str){
                if (str.indexOf("error.") != -1){
                    return true;
                } else {
                    return false;
                }
            }
        };
        File[] errorLogFileArray = errorLogDirectory.listFiles(searchFilter);
        int actual = errorLogFileArray.length;

        assertThat(actual, is(expected));
    }

    @Test
    public void testWriteMailLogToMakeNewLog() throws Exception {
        Properties props = getProperty("util/LogUtilTest.properties");
        String mailLogPath = props.getProperty("mail.log.path");
        File expected = new File(mailLogPath + "/LogUtilTest.testWriteMailLogToMakeNewLog.log");

        LogUtil sut = new LogUtil() {
            Properties getProperties() throws IOException {
                return getProperty("util/LogUtilTest.properties");
            }

            Date getDate() throws ParseException {
                String date = "1970/01/01 09:00:00";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                return sdf.parse(date);
            }

            Timestamp getCurrentTimestamp() {
                return new Timestamp(0);
            }
        };
        sut.writeMailLog("test@example.com", "テスト件名");
        File actual = new File(mailLogPath + "/mail.1970-01-01.log");
        mailLogFileNameList.add("/mail.1970-01-01.log");

        assertThat(actual, is(containOfSameDescription(expected)));
    }

    @Test
    public void testWriteMailLogToAddLog() throws Exception {
        Properties props = getProperty("util/LogUtilTest.properties");
        String mailLogPath = props.getProperty("mail.log.path");
        File expected = new File(mailLogPath + "/LogUtilTest.testWriteMailLogToAddLog.log");

        LogUtil sut = new LogUtil() {
            Properties getProperties() throws IOException {
                return getProperty("util/LogUtilTest.properties");
            }

            Date getDate() throws ParseException {
                String date = "1970/01/02 09:00:00";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                return sdf.parse(date);
            }

            Timestamp getCurrentTimestamp() {
                return new Timestamp(86400000);
            }
        };
        sut.writeMailLog("test1@example.com", "テスト件名１");
        sut.writeMailLog("test2@example.com", "テスト件名２");
        File actual = new File(mailLogPath + "/mail.1970-01-02.log");
        mailLogFileNameList.add("/mail.1970-01-02.log");

        assertThat(actual, is(containOfSameDescription(expected)));
    }

    @Test
    public void testDeleteExpiredMailLog() throws Exception {
        int expected = 5;

        Properties props = getProperty("util/LogUtilTest.properties");
        String mailLogPath = props.getProperty("mail.log.path");

        Path expiredLogPath1 = Paths.get(mailLogPath + "/mail.1969-12-26.log");
        Path expiredLogPath2 = Paths.get(mailLogPath + "/mail.1969-12-27.log");
        Files.createFile(expiredLogPath1);
        Files.createFile(expiredLogPath2);

        Path notExpiredLogPath1 = Paths.get(mailLogPath + "/mail.1969-12-28.log");
        Path notExpiredLogPath2 = Paths.get(mailLogPath + "/mail.1969-12-29.log");
        Path notExpiredLogPath3 = Paths.get(mailLogPath + "/mail.1969-12-30.log");
        Path notExpiredLogPath4 = Paths.get(mailLogPath + "/mail.1969-12-31.log");
        Path notExpiredLogPath5 = Paths.get(mailLogPath + "/mail.1970-01-01.log");
        Files.createFile(notExpiredLogPath1);
        Files.createFile(notExpiredLogPath2);
        Files.createFile(notExpiredLogPath3);
        Files.createFile(notExpiredLogPath4);
        Files.createFile(notExpiredLogPath5);

        mailLogFileNameList.add("/mail.1969-12-28.log");
        mailLogFileNameList.add("/mail.1969-12-29.log");
        mailLogFileNameList.add("/mail.1969-12-30.log");
        mailLogFileNameList.add("/mail.1969-12-31.log");
        mailLogFileNameList.add("/mail.1970-01-01.log");

        LogUtil sut = new LogUtil() {
            Properties getProperties() throws IOException {
                return getProperty("util/LogUtilTest.properties");
            }

            Date getDate() throws ParseException {
                String date = "1970/01/01 09:00:00";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                return sdf.parse(date);
            }

            Timestamp getCurrentTimestamp() {
                return new Timestamp(0);
            }
        };
        sut.deleteExpiredMailLog();

        // 生成されたメールログの配列を取得する。
        File mailLogDirectory = new File(mailLogPath);
        FilenameFilter searchFilter = new FilenameFilter() {
            public boolean accept(File file, String str){
                if (str.indexOf("mail.") != -1){
                    return true;
                } else {
                    return false;
                }
            }
        };
        File[] mailLogFileArray = mailLogDirectory.listFiles(searchFilter);
        int actual = mailLogFileArray.length;

        assertThat(actual, is(expected));
    }

}
