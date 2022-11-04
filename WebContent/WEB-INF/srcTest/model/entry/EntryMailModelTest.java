package model.entry;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsRegex.*;
import static test.property.PropertyTester.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

import test.db.DbUnitTester;

public class EntryMailModelTest {

    // テスト時に生成されるメールログのファイル名を格納するためのリストを生成する。
    private List<String> fileNameList = new ArrayList<>();

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.EntryMailModelTest.xml") {
        @Override
        protected void before() throws Exception {
            executeQuery("DELETE FROM mail_templetes");
            executeQuery("DELETE FROM contacts");
            executeQuery("DELETE FROM contact_statuses");
            executeQuery("DELETE FROM favorites");
            executeQuery("DELETE FROM messages");
            executeQuery("DELETE FROM entries");
            executeQuery("DELETE FROM entry_statuses");
            executeQuery("DELETE FROM schools");
            executeQuery("DELETE FROM categories");
            executeQuery("DELETE FROM accounts");
            executeQuery("DELETE FROM prefectures");
            executeQuery("DELETE FROM sexes");
            executeQuery("DELETE FROM admins");
            executeQuery("DELETE FROM admin_departments");
        }
    };

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
    public void testSendEntryMailToApplicantToSendMail() throws Exception {
        String mailLogPath = getProperty("environment.properties").getProperty("mail.log.path");
        String expectedLog
                = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3} "
                + "To:tanaka@example.com Subject:スクールへの申込が完了しました。";

        EntryMailModel sut = new EntryMailModel();
        sut.sendEntryMailToApplicantAccount(1, 2);

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

    @Test
    public void testSendEntryMailToRegistrantToSendMail() throws Exception {
        String mailLogPath = getProperty("environment.properties").getProperty("mail.log.path");
        String expectedLog
                = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3} "
                + "To:yamada@example.com Subject:スクールに申込がありました。";

        EntryMailModel sut = new EntryMailModel();
        sut.sendEntryMailToRegistrantAccount(2, 3);

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

    @Test
    public void testSendEntryMailToAdminRegistrantToSendMail() throws Exception {
        String mailLogPath = getProperty("environment.properties").getProperty("mail.log.path");
        String expectedLog
                = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3} "
                + "To:system@example.com Subject:スクールに申込がありました。";

        EntryMailModel sut = new EntryMailModel();
        sut.sendEntryMailToRegistrantAdmin(1, 4);

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

    @Test
    public void testSendCancelMailToApplicantToSendMail() throws Exception {
        String mailLogPath = getProperty("environment.properties").getProperty("mail.log.path");
        String expectedLog
                = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3} "
                + "To:satou@example.com Subject:申込のキャンセルが完了しました。";

        EntryMailModel sut = new EntryMailModel();
        sut.sendCancelMailToApplicantAccount(3, 5);

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

    @Test
    public void testSendCancelMailToRegistrant() throws Exception {
        String mailLogPath = getProperty("environment.properties").getProperty("mail.log.path");
        String expectedLog
                = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3} "
                + "To:takahashi@test.com Subject:申込がキャンセルされました。";

        EntryMailModel sut = new EntryMailModel();
        sut.sendCancelMailToRegistrantAccount(4, 6);

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

    @Test
    public void testSendCancelMailToAdminRegistrant() throws Exception {
        String mailLogPath = getProperty("environment.properties").getProperty("mail.log.path");
        String expectedLog
                = "\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3} "
                + "To:subsystem@example.com Subject:申込がキャンセルされました。";

        EntryMailModel sut = new EntryMailModel();
        sut.sendCancelMailToRegistrantAdmin(2, 7);

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

}
