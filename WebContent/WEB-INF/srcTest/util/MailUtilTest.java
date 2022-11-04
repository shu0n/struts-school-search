package util;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsRegex.*;
import static test.property.PropertyTester.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.junit.After;
import org.junit.Test;

public class MailUtilTest {

    // テスト時に生成されるメールログのファイル名を格納するためのリストを生成する。
    private List<String> fileNameList = new ArrayList<>();

    @After
    public void after() throws Exception {
        String mailLogPath = getProperty("environment.properties").getProperty("mail.log.path");
        // テスト時に生成されたメールログを削除する。
        if(!fileNameList.isEmpty()) {
            for(String fileName: fileNameList) {
                Path filePath = Paths.get(mailLogPath + fileName);
                Files.delete(filePath);
            }
        }
        fileNameList = new ArrayList<>();
    }

    @Test
    public void testSendMailToSendToTestMailAddress() throws Exception {
        String mailLogPath = getProperty("environment.properties").getProperty("mail.log.path");
        String expectedLog
                = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3} "
                + "To:test@example.com Subject:テストメールの件名";

        String to = "test@example.com";
        String subject = "テストメールの件名";
        String content = "テストメールです。";
        MailUtil sut = new MailUtil() {
            Properties getProperties() throws IOException {
                return getProperty("util/MailUtilTest.properties");
            }
        };
        sut.sendMail(to, subject, content);

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
        BufferedReader br = new BufferedReader(new FileReader(mailLogFileArray[0]));
        String actual = br.readLine();
        br.close();
        fileNameList.add("/" + mailLogFileArray[0].getName());

        assertThat(actual, is(matchPatternAs(expectedLog)));
    }

    @Test(expected = AddressException.class)
    public void testSendMailInCaseEmptyMailAddress() throws Exception {
        String to = "";
        String subject = "テストメールの件名";
        String content = "テストメールです。";
        MailUtil sut = new MailUtil() {
            Properties getProperties() throws IOException {
                return getProperty("util/MailUtilTest.properties");
            }
        };
        sut.sendMail(to, subject, content);
    }

    @Test(expected = MessagingException.class)
    public void testSendMailInCaseWrongPort() throws Exception {
        String to = "test@example.com";
        String subject = "テストメールの件名";
        String content = "テストメールです。";
        MailUtil sut = new MailUtil() {
            Properties getProperties() throws IOException {
                return getProperty("util/MailUtilTest.testSendMailInCaseWrongPort.properties");
            }
        };
        sut.sendMail(to, subject, content);
    }

}
