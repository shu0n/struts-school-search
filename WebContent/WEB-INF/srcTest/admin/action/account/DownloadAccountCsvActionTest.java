package admin.action.account;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import actionform.AccountExtendActionForm;
import admin.actionform.account.AdminListAccountActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class DownloadAccountCsvActionTest {

    public static class executeTest extends MockStrutsTestCase {

        private static final String ACTION_PATH = "/downloadAccountCsv.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteInCaseDownloadSuccessfully() {
            AdminListAccountActionForm inForm = new AdminListAccountActionForm();
            List<AccountExtendActionForm> accountExtendAFormList = new ArrayList<>();
            AccountExtendActionForm firstForm = new AccountExtendActionForm();
            firstForm.setAccountId(1);
            firstForm.setLastName("田中");
            firstForm.setFirstName("太郎");
            firstForm.setLastNameKana("タナカ");
            firstForm.setFirstNameKana("タロウ");
            firstForm.setSexId(11);
            firstForm.setSexName("男性");
            firstForm.setBirthDay("1990/01/02");
            firstForm.setPrefectureId(21);
            firstForm.setPrefectureName("北海道");
            firstForm.setMailAddress("tanaka@example.com");
            firstForm.setProfileImageFileName("image1.png");
            firstForm.setSelfIntroduction("田中の自己紹介です。");
            firstForm.setAccountStatus("1");
            firstForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-03 04:05:06.789"));
            firstForm.setAccountUpdatedAt(Timestamp.valueOf("2022-02-04 05:06:07.891"));
            accountExtendAFormList.add(firstForm);
            AccountExtendActionForm secondForm = new AccountExtendActionForm();
            secondForm.setAccountId(2);
            secondForm.setLastName("高橋");
            secondForm.setFirstName("次郎");
            secondForm.setLastNameKana("タカハシ");
            secondForm.setFirstNameKana("ジロウ");
            secondForm.setSexId(12);
            secondForm.setSexName("女性");
            secondForm.setBirthDay("1990/01/03");
            secondForm.setPrefectureId(21);
            secondForm.setPrefectureName("東京都");
            secondForm.setMailAddress("takahashi@example.com");
            secondForm.setProfileImageFileName("image2.png");
            secondForm.setSelfIntroduction("高橋の自己紹介です。");
            secondForm.setAccountStatus("1");
            secondForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-05 06:07:08.912"));
            secondForm.setAccountUpdatedAt(Timestamp.valueOf("2022-02-06 07:08:09.123"));
            accountExtendAFormList.add(secondForm);
            AccountExtendActionForm thirdForm = new AccountExtendActionForm();
            thirdForm.setAccountId(3);
            thirdForm.setLastName("山田");
            thirdForm.setFirstName("三郎");
            thirdForm.setLastNameKana("ヤマダ");
            thirdForm.setFirstNameKana("サブロウ");
            thirdForm.setSexId(0);
            thirdForm.setSexName(null);
            thirdForm.setBirthDay(null);
            thirdForm.setPrefectureId(22);
            thirdForm.setPrefectureName("福島県");
            thirdForm.setMailAddress("yamada@example.com");
            thirdForm.setProfileImageFileName(null);
            thirdForm.setSelfIntroduction(null);
            thirdForm.setAccountStatus("1");
            thirdForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-07 08:09:10.234"));
            thirdForm.setAccountUpdatedAt(Timestamp.valueOf("2022-02-08 09:10:11.345"));
            accountExtendAFormList.add(secondForm);
            inForm.setAccountExtendFormList(accountExtendAFormList);
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward(null);
        }

        @Test
        public void testExecuteInCaseLogout() {
            AdminListAccountActionForm inForm = new AdminListAccountActionForm();
            List<AccountExtendActionForm> accountExtendAFormList = new ArrayList<>();
            AccountExtendActionForm firstForm = new AccountExtendActionForm();
            firstForm.setAccountId(1);
            firstForm.setLastName("田中");
            firstForm.setFirstName("太郎");
            firstForm.setLastNameKana("タナカ");
            firstForm.setFirstNameKana("タロウ");
            firstForm.setSexId(11);
            firstForm.setSexName("男性");
            firstForm.setBirthDay("1990/01/02");
            firstForm.setPrefectureId(21);
            firstForm.setPrefectureName("北海道");
            firstForm.setMailAddress("tanaka@example.com");
            firstForm.setProfileImageFileName("image1.png");
            firstForm.setSelfIntroduction("田中の自己紹介です。");
            firstForm.setAccountStatus("1");
            firstForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-03 04:05:06.789"));
            firstForm.setAccountUpdatedAt(Timestamp.valueOf("2022-02-04 05:06:07.891"));
            accountExtendAFormList.add(firstForm);
            AccountExtendActionForm secondForm = new AccountExtendActionForm();
            secondForm.setAccountId(2);
            secondForm.setLastName("高橋");
            secondForm.setFirstName("次郎");
            secondForm.setLastNameKana("タカハシ");
            secondForm.setFirstNameKana("ジロウ");
            secondForm.setSexId(12);
            secondForm.setSexName("女性");
            secondForm.setBirthDay("1990/01/03");
            secondForm.setPrefectureId(21);
            secondForm.setPrefectureName("東京都");
            secondForm.setMailAddress("takahashi@example.com");
            secondForm.setProfileImageFileName("image2.png");
            secondForm.setSelfIntroduction("高橋の自己紹介です。");
            secondForm.setAccountStatus("1");
            secondForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-05 06:07:08.912"));
            secondForm.setAccountUpdatedAt(Timestamp.valueOf("2022-02-06 07:08:09.123"));
            accountExtendAFormList.add(secondForm);
            AccountExtendActionForm thirdForm = new AccountExtendActionForm();
            thirdForm.setAccountId(3);
            thirdForm.setLastName("山田");
            thirdForm.setFirstName("三郎");
            thirdForm.setLastNameKana("ヤマダ");
            thirdForm.setFirstNameKana("サブロウ");
            thirdForm.setSexId(0);
            thirdForm.setSexName(null);
            thirdForm.setBirthDay(null);
            thirdForm.setPrefectureId(22);
            thirdForm.setPrefectureName("福島県");
            thirdForm.setMailAddress("yamada@example.com");
            thirdForm.setProfileImageFileName(null);
            thirdForm.setSelfIntroduction(null);
            thirdForm.setAccountStatus("1");
            thirdForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-07 08:09:10.234"));
            thirdForm.setAccountUpdatedAt(Timestamp.valueOf("2022-02-08 09:10:11.345"));
            accountExtendAFormList.add(secondForm);
            inForm.setAccountExtendFormList(accountExtendAFormList);
            setActionForm(inForm);
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/listAccount.do");
        }

        @Test
        public void testExecuteInCaseError() {
            AdminListAccountActionForm inForm = new AdminListAccountActionForm();
            List<AccountExtendActionForm> accountExtendAFormList = new ArrayList<>();
            AccountExtendActionForm firstForm = new AccountExtendActionForm();
            firstForm.setAccountId(1);
            firstForm.setLastName("田中");
            firstForm.setFirstName("太郎");
            firstForm.setLastNameKana("タナカ");
            firstForm.setFirstNameKana("タロウ");
            firstForm.setSexId(11);
            firstForm.setSexName("男性");
            firstForm.setBirthDay("1990/01/02");
            firstForm.setPrefectureId(21);
            firstForm.setPrefectureName("北海道");
            firstForm.setMailAddress("tanaka@example.com");
            firstForm.setProfileImageFileName("image1.png");
            firstForm.setSelfIntroduction("田中の自己紹介です。");
            firstForm.setAccountStatus("1");
            firstForm.setAccountCreatedAt(null);
            firstForm.setAccountUpdatedAt(Timestamp.valueOf("2022-02-04 05:06:07.891"));
            accountExtendAFormList.add(firstForm);
            AccountExtendActionForm secondForm = new AccountExtendActionForm();
            secondForm.setAccountId(2);
            secondForm.setLastName("高橋");
            secondForm.setFirstName("次郎");
            secondForm.setLastNameKana("タカハシ");
            secondForm.setFirstNameKana("ジロウ");
            secondForm.setSexId(12);
            secondForm.setSexName("女性");
            secondForm.setBirthDay("1990/01/03");
            secondForm.setPrefectureId(21);
            secondForm.setPrefectureName("東京都");
            secondForm.setMailAddress("takahashi@example.com");
            secondForm.setProfileImageFileName("image2.png");
            secondForm.setSelfIntroduction("高橋の自己紹介です。");
            secondForm.setAccountStatus("1");
            secondForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-05 06:07:08.912"));
            secondForm.setAccountUpdatedAt(Timestamp.valueOf("2022-02-06 07:08:09.123"));
            accountExtendAFormList.add(secondForm);
            AccountExtendActionForm thirdForm = new AccountExtendActionForm();
            thirdForm.setAccountId(3);
            thirdForm.setLastName("山田");
            thirdForm.setFirstName("三郎");
            thirdForm.setLastNameKana("ヤマダ");
            thirdForm.setFirstNameKana("サブロウ");
            thirdForm.setSexId(0);
            thirdForm.setSexName(null);
            thirdForm.setBirthDay(null);
            thirdForm.setPrefectureId(22);
            thirdForm.setPrefectureName("福島県");
            thirdForm.setMailAddress("yamada@example.com");
            thirdForm.setProfileImageFileName(null);
            thirdForm.setSelfIntroduction(null);
            thirdForm.setAccountStatus("1");
            thirdForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-07 08:09:10.234"));
            thirdForm.setAccountUpdatedAt(Timestamp.valueOf("2022-02-08 09:10:11.345"));
            accountExtendAFormList.add(secondForm);
            inForm.setAccountExtendFormList(accountExtendAFormList);
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listAccount.do");
            verifyActionErrors(new String[] {"ダウンロードに失敗しました。"});
        }

    }

}
