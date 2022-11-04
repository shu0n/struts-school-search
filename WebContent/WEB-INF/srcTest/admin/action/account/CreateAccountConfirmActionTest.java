package admin.action.account;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsRegex.*;
import static test.property.PropertyTester.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import admin.actionform.account.AdminCreateAccountActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.generator.GenerateFormFile;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class CreateAccountConfirmActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.CreateAccountConfirmActionTest.xml") {
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

    public static class executeTest extends MockStrutsTestCase {

        private static final String ACTION_PATH = "/createAccountConfirm.do";
        private static final String VALID_TOKEN = "validToken";
        private static final String INVALID_TOKEN = "invalidToken";

        // テスト時に生成される画像ファイルのファイル名を格納するためのリストを生成する。
        private List<String> fileNameList = new ArrayList<>();

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        public void tearDown() throws Exception {
            super.tearDown();
            String modulePath = getProperty("environment.properties").getProperty("module.path");
            String imgPath = getProperty("environment.properties").getProperty("img.path");
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
        public void testExecuteToForwardSuccess() throws Exception {
            AdminCreateAccountActionForm inForm = new AdminCreateAccountActionForm();
            inForm.setLastName("田中");
            inForm.setFirstName("太郎");
            inForm.setLastNameKana("タナカ");
            inForm.setFirstNameKana("タロウ");
            inForm.setSexId(1);
            inForm.setBirthYear("1990");
            inForm.setBirthMonth("01");
            inForm.setBirthDay("02");
            inForm.setPrefectureId(2);
            inForm.setMailAddress("test@example.com");
            inForm.setProfileImageFile(
                    new GenerateFormFile("profileImage.png", "admin/action/account/profileImage.png", 1187));
            inForm.setSelfIntroduction("自己紹介です。");
            inForm.setPassword("password");
            inForm.setAccountStatus("0");
            Map<Integer, String> sexMap = new TreeMap<>();
            sexMap.put(1, "男性");
            sexMap.put(2, "女性");
            sexMap.put(3, "その他");
            inForm.setSexMap(sexMap);
            Map<Integer, String> prefectureMap = new TreeMap<>();
            prefectureMap.put(1, "北海道");
            prefectureMap.put(2, "東京都");
            prefectureMap.put(3, "静岡県");
            prefectureMap.put(4, "京都府");
            inForm.setPrefectureMap(prefectureMap);
            Map<String, String> accountStatusMap = new TreeMap<>();
            accountStatusMap.put("0", "無効");
            accountStatusMap.put("1", "有効");
            inForm.setAccountStatusMap(accountStatusMap);
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("success");

            String expectedFilePath = "/img/\\d{17}_\\d{1,3}.png";
            String expectedFileName = "\\d{17}_\\d{1,3}.png";

            HttpServletRequest actual = getRequest();
            fileNameList.add((String) actual.getAttribute("profileImageFileName"));

            assertThat((String) actual.getAttribute("profileImageFilePath"), is(matchPatternAs(expectedFilePath)));
            assertThat((String) actual.getAttribute("profileImageFileName"), is(matchPatternAs(expectedFileName)));
        }

        @Test
        public void testExecuteToForwardInvalid() throws Exception {
            AdminCreateAccountActionForm inForm = new AdminCreateAccountActionForm();
            inForm.setLastName("田中");
            inForm.setFirstName("太郎");
            inForm.setLastNameKana("タナカ");
            inForm.setFirstNameKana("タロウ");
            inForm.setSexId(1);
            inForm.setBirthYear("1990");
            inForm.setBirthMonth("01");
            inForm.setBirthDay("02");
            inForm.setPrefectureId(2);
            inForm.setMailAddress("test@example.com");
            inForm.setProfileImageFile(new GenerateFormFile(null, null, 0));
            inForm.setSelfIntroduction("自己紹介です。");
            inForm.setPassword("password");
            inForm.setAccountStatus("0");
            Map<Integer, String> sexMap = new TreeMap<>();
            sexMap.put(1, "男性");
            sexMap.put(2, "女性");
            sexMap.put(3, "その他");
            inForm.setSexMap(sexMap);
            Map<Integer, String> prefectureMap = new TreeMap<>();
            prefectureMap.put(1, "北海道");
            prefectureMap.put(2, "東京都");
            prefectureMap.put(3, "静岡県");
            prefectureMap.put(4, "京都府");
            inForm.setPrefectureMap(prefectureMap);
            Map<String, String> accountStatusMap = new TreeMap<>();
            accountStatusMap.put("0", "無効");
            accountStatusMap.put("1", "有効");
            inForm.setAccountStatusMap(accountStatusMap);
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", INVALID_TOKEN);
            actionPerform();

            verifyForward("invalid");
        }

        @Test
        public void testExecuteToForwardRedoInCaseMailAddressExist() throws Exception {
            AdminCreateAccountActionForm inForm = new AdminCreateAccountActionForm();
            inForm.setLastName("田中");
            inForm.setFirstName("太郎");
            inForm.setLastNameKana("タナカ");
            inForm.setFirstNameKana("タロウ");
            inForm.setSexId(1);
            inForm.setBirthYear("1990");
            inForm.setBirthMonth("01");
            inForm.setBirthDay("02");
            inForm.setPrefectureId(2);
            inForm.setMailAddress("tanaka@example.com");
            inForm.setPassword("password");
            inForm.setProfileImageFile(new GenerateFormFile(null, null, 0));
            inForm.setSelfIntroduction("自己紹介です。");
            inForm.setAccountStatus("0");
            Map<Integer, String> sexMap = new TreeMap<>();
            sexMap.put(1, "男性");
            sexMap.put(2, "女性");
            sexMap.put(3, "その他");
            inForm.setSexMap(sexMap);
            Map<Integer, String> prefectureMap = new TreeMap<>();
            prefectureMap.put(1, "北海道");
            prefectureMap.put(2, "東京都");
            prefectureMap.put(3, "静岡県");
            prefectureMap.put(4, "京都府");
            inForm.setPrefectureMap(prefectureMap);
            Map<String, String> accountStatusMap = new TreeMap<>();
            accountStatusMap.put("0", "無効");
            accountStatusMap.put("1", "有効");
            inForm.setAccountStatusMap(accountStatusMap);
            setActionForm(inForm);
            getSession().setAttribute("org.apache.struts.action.TOKEN", VALID_TOKEN);
            addRequestParameter("org.apache.struts.taglib.html.TOKEN", VALID_TOKEN);
            actionPerform();

            verifyForward("redo");
            verifyActionErrors(new String[] {"登録済のメールアドレスです。"});
        }

    }

}
