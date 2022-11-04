package model.account;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsHttpResponseHeader.*;
import static test.matcher.IsRegex.*;
import static test.property.PropertyTester.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;
import org.junit.After;
import org.junit.Test;

import actionform.AccountExtendActionForm;
import servletunit.HttpServletRequestSimulator;
import servletunit.HttpServletResponseSimulator;
import servletunit.ServletContextSimulator;
import test.generator.GenerateFormFile;

public class AccountFileModelTest {

    // テスト時に生成される画像ファイルのファイル名を格納するためのリストを生成する。
    private List<String> fileNameList = new ArrayList<>();

    @After
    public void after() throws Exception {
        String modulePath = getProperty("model/account/environment.properties").getProperty("module.path");
        String imgPath = getProperty("model/account/environment.properties").getProperty("img.path");
        // テスト時に生成された画像ファイルを削除する。
        if(!fileNameList.isEmpty()) {
            for(String fileName: fileNameList) {
                Path filePath = Paths.get(modulePath + imgPath + fileName);
                Files.delete(filePath);
            }
        }
        fileNameList = new ArrayList<>();
    }

    @Test
    public void testSetFileInfoToSetInfo() throws Exception {
        String expectedFilePath = "/img/\\d{17}_\\d{1,3}.png";
        String expectedFileName = "\\d{17}_\\d{1,3}.png";

        AccountFileModel sut = new AccountFileModel();
        FormFile formFile= new GenerateFormFile(
                "AccountFileModelTest.testSetFileInfo.png",
                "model/account/AccountFileModelTest.testSetFileInfo.png",
                1187);
        HttpServletRequest actual = new HttpServletRequestSimulator(new ServletContextSimulator());
        actual = sut.setFileInfo(formFile, actual);
        fileNameList.add((String) actual.getAttribute("profileImageFileName"));

        assertThat((String) actual.getAttribute("profileImageFilePath"), is(matchPatternAs(expectedFilePath)));
        assertThat((String) actual.getAttribute("profileImageFileName"), is(matchPatternAs(expectedFileName)));
    }

    @Test
    public void tsetSetDownloadCsvFileDataToSetData() throws Exception {
        String expected = "accounts_19700101090000000.csv";

        AccountFileModel sut = new AccountFileModel() {
            Timestamp getCurrentTimestamp() {
                return Timestamp.valueOf("1970-01-01 09:00:00.000");
            }
        };
        List<AccountExtendActionForm> accountExtendFormList = new ArrayList<>();
        AccountExtendActionForm firstForm = new AccountExtendActionForm();
        firstForm.setLastName("田中");
        firstForm.setFirstName("太郎");
        firstForm.setLastNameKana("タナカ");
        firstForm.setFirstNameKana("タロウ");
        firstForm.setSexId(1);
        firstForm.setSexName("男性");
        firstForm.setStrBirthDate("1990/01/01");
        firstForm.setPrefectureId(1);
        firstForm.setPrefectureName("北海道");
        firstForm.setMailAddress("tanaka@example.com");
        firstForm.setProfileImageFileName("image1.png");
        firstForm.setSelfIntroduction("はじめまして");
        firstForm.setAccountStatus("0");
        firstForm.setAccountCreatedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        firstForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        accountExtendFormList.add(firstForm);
        AccountExtendActionForm secondForm = new AccountExtendActionForm();
        secondForm.setLastName("佐藤");
        secondForm.setFirstName("次郎");
        secondForm.setLastNameKana("サトウ");
        secondForm.setFirstNameKana("ジロウ");
        secondForm.setSexId(2);
        secondForm.setSexName("女性");
        secondForm.setStrBirthDate("1990/01/02");
        secondForm.setPrefectureId(2);
        secondForm.setPrefectureName("青森県");
        secondForm.setMailAddress("sato@example.com");
        secondForm.setProfileImageFileName("image2.png");
        secondForm.setSelfIntroduction("こんにちは");
        secondForm.setAccountStatus("1");
        secondForm.setAccountCreatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        secondForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        accountExtendFormList.add(secondForm);
        AccountExtendActionForm thirdForm = new AccountExtendActionForm();
        thirdForm.setLastName("高橋");
        thirdForm.setFirstName("三郎");
        thirdForm.setLastNameKana("タカハシ");
        thirdForm.setFirstNameKana("サブロウ");
        thirdForm.setSexId(0);
        thirdForm.setSexName(null);
        thirdForm.setStrBirthDate(null);
        thirdForm.setPrefectureId(3);
        thirdForm.setPrefectureName("岩手県");
        thirdForm.setMailAddress("takahashi@example.com");
        thirdForm.setProfileImageFileName(null);
        thirdForm.setSelfIntroduction(null);
        thirdForm.setAccountStatus("0");
        thirdForm.setAccountCreatedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        thirdForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-07 08:09:10.234"));
        accountExtendFormList.add(thirdForm);
        HttpServletResponse stub = new HttpServletResponseSimulator();
        HttpServletResponse actual = sut.setDownloadCsvFileData(accountExtendFormList, stub);

        assertThat(actual, is(contentOf("text/csv;charset=UTF8", expected)));
    }

}
