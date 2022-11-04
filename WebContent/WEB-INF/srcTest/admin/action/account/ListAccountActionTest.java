package admin.action.account;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;
import static test.matcher.IsListComposedLabelValueBean.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import actionform.AccountExtendActionForm;
import admin.actionform.account.AdminListAccountActionForm;
import admin.actionform.account.AdminSearchAccountActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class ListAccountActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.ListAccountActionTest.xml") {
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

        private static final String ACTION_PATH = "/listAccount.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteToForwardSuccessInCaseFirstAccess() {
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            int[] expectedAccountIdArray = {6, 5, 4, 3, 2, 1};
            AccountExtendActionForm expectedForm1 = new AccountExtendActionForm();
            expectedForm1.setAccountId(6);
            expectedForm1.setLastName("前田");
            expectedForm1.setFirstName("四郎");
            expectedForm1.setLastNameKana("マエダ");
            expectedForm1.setFirstNameKana("シロウ");
            expectedForm1.setSexId(1);
            expectedForm1.setSexName("男性");
            expectedForm1.setBirthDate(null);
            expectedForm1.setBirthYear("");
            expectedForm1.setBirthMonth("");
            expectedForm1.setBirthDay("");
            expectedForm1.setStrBirthDate("");
            expectedForm1.setPrefectureId(3);
            expectedForm1.setPrefectureName("静岡県");
            expectedForm1.setMailAddress("maeda@test.com");
            expectedForm1.setProfileImageFileName(null);
            expectedForm1.setSelfIntroduction(null);
            expectedForm1.setAccountStatus("0");
            expectedForm1.setAccountStatusName("無効");
            expectedForm1.setActivateToken("activateToken");
            expectedForm1.setActivateEffectiveDate(Timestamp.valueOf("2022-10-11 12:13:14.567"));
            expectedForm1.setReissueToken(null);
            expectedForm1.setReissueEffctiveDate(null);
            expectedForm1.setAccountCreatedAt(Timestamp.valueOf("2022-01-12 13:14:15.678"));
            expectedForm1.setAccountUpdatedAt(Timestamp.valueOf("2022-01-13 14:15:16.789"));
            expectedForm1.setStrAccountCreatedAt("2022/01/12 13:14:15");
            expectedForm1.setStrAccountUpdatedAt("2022/01/13 14:15:16");
            AccountExtendActionForm expectedForm2 = new AccountExtendActionForm();
            expectedForm2.setAccountId(1);
            expectedForm2.setLastName("田中");
            expectedForm2.setFirstName("太郎");
            expectedForm2.setLastNameKana("タナカ");
            expectedForm2.setFirstNameKana("タロウ");
            expectedForm2.setSexId(1);
            expectedForm2.setSexName("男性");
            expectedForm2.setBirthDate(Timestamp.valueOf("1990-01-02 00:00:00"));
            expectedForm2.setBirthYear("1990");
            expectedForm2.setBirthMonth("01");
            expectedForm2.setBirthDay("02");
            expectedForm2.setStrBirthDate("1990/01/02");
            expectedForm2.setPrefectureId(2);
            expectedForm2.setPrefectureName("東京都");
            expectedForm2.setMailAddress("tanaka@example.com");
            expectedForm2.setProfileImageFileName("profileImage1.png");
            expectedForm2.setSelfIntroduction("田中の自己紹介です。");
            expectedForm2.setAccountStatus("1");
            expectedForm2.setAccountStatusName("有効");
            expectedForm2.setActivateToken(null);
            expectedForm2.setActivateEffectiveDate(null);
            expectedForm2.setReissueToken("reissueToken");
            expectedForm2.setReissueEffctiveDate(Timestamp.valueOf("2022-09-10 11:12:13.456"));
            expectedForm2.setAccountCreatedAt(Timestamp.valueOf("2021-01-02 03:04:05.678"));
            expectedForm2.setAccountUpdatedAt(Timestamp.valueOf("2021-01-03 04:05:06.789"));
            expectedForm2.setStrAccountCreatedAt("2021/01/02 03:04:05");
            expectedForm2.setStrAccountUpdatedAt("2021/01/03 04:05:06");
            List<LabelValueBean> expectedSexList = new ArrayList<>();
            expectedSexList.add(new LabelValueBean("男性", "1"));
            expectedSexList.add(new LabelValueBean("女性", "2"));
            expectedSexList.add(new LabelValueBean("その他", "3"));
            List<LabelValueBean> expectedPrefectureList = new ArrayList<>();
            expectedPrefectureList.add(new LabelValueBean("北海道", "1"));
            expectedPrefectureList.add(new LabelValueBean("東京都", "2"));
            expectedPrefectureList.add(new LabelValueBean("静岡県", "3"));
            expectedPrefectureList.add(new LabelValueBean("京都府", "4"));
            List<LabelValueBean> expectedAccountStatusList = new ArrayList<>();
            expectedAccountStatusList.add(new LabelValueBean("無効", "0"));
            expectedAccountStatusList.add(new LabelValueBean("有効", "1"));
            List<LabelValueBean> expectedSortKindForAccountList = new ArrayList<>();
            expectedSortKindForAccountList.add(new LabelValueBean("選択する", ""));
            expectedSortKindForAccountList.add(new LabelValueBean("作成日が新しい順", "byDescendingCreatedAt"));
            expectedSortKindForAccountList.add(new LabelValueBean("作成日が古い順", "byAccendingCreatedAt"));
            expectedSortKindForAccountList.add(new LabelValueBean("更新日が新しい順", "byDescendingUpdatedAt"));
            expectedSortKindForAccountList.add(new LabelValueBean("更新日が古い順", "byAccendingUpdatedAt"));
            int expectedTotalForm = 6;
            int expectedTotalPage = 1;
            int expectedCurrentPage = 1;

            AdminListAccountActionForm actual = (AdminListAccountActionForm) getActionForm();

            for(int i = 0; i < actual.getAccountExtendFormList().size(); i++) {
                assertThat(actual.getAccountExtendFormList().get(i).getAccountId(), is(expectedAccountIdArray[i]));
            }
            assertThat(actual.getAccountExtendFormList().get(0), is(samePropertyValueAs(expectedForm1)));
            assertThat(actual.getAccountExtendFormList().get(5), is(samePropertyValueAs(expectedForm2)));
            assertThat(actual.getSexList(), is(sameComponentLavelAndValueAs(expectedSexList)));
            assertThat(actual.getPrefectureList(), is(sameComponentLavelAndValueAs(expectedPrefectureList)));
            assertThat(actual.getAccountStatusList(), is(sameComponentLavelAndValueAs(expectedAccountStatusList)));
            assertThat(
                    actual.getSortKindForAccountList(),
                    is(sameComponentLavelAndValueAs(expectedSortKindForAccountList)));
            assertThat(actual.getSortKindForAccount(), is(nullValue()));
            assertThat(actual.getTotalForm(), is(expectedTotalForm));
            assertThat(actual.getTotalPage(), is(expectedTotalPage));
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseAdminSearchAccountActionFormIsNotNull() {
            AdminSearchAccountActionForm adminSearchAccountForm = new AdminSearchAccountActionForm();
            List<AccountExtendActionForm> accountExtendFormList = new ArrayList<>();
            AccountExtendActionForm firstForm = new AccountExtendActionForm();
            firstForm.setAccountId(21);
            firstForm.setAccountStatus("1");
            firstForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-28 09:10:11.234"));
            firstForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-31 02:03:04.567"));
            accountExtendFormList.add(firstForm);
            AccountExtendActionForm secondForm = new AccountExtendActionForm();
            secondForm.setAccountId(20);
            secondForm.setAccountStatus("0");
            secondForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-27 08:09:10.123"));
            secondForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-30 01:02:03.456"));
            accountExtendFormList.add(secondForm);
            AccountExtendActionForm thirdForm = new AccountExtendActionForm();
            thirdForm.setAccountId(19);
            thirdForm.setAccountStatus("1");
            thirdForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-26 07:08:09.123"));
            thirdForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-29 01:02:03.456"));
            accountExtendFormList.add(thirdForm);
            AccountExtendActionForm fourthForm = new AccountExtendActionForm();
            fourthForm.setAccountId(18);
            fourthForm.setAccountStatus("0");
            fourthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-25 06:07:08.912"));
            fourthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-28 09:10:11.234"));
            accountExtendFormList.add(fourthForm);
            AccountExtendActionForm fifthForm = new AccountExtendActionForm();
            fifthForm.setAccountId(17);
            fifthForm.setAccountStatus("1");
            fifthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-25 05:06:07.891"));
            fifthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-27 08:09:10.123"));
            accountExtendFormList.add(fifthForm);
            AccountExtendActionForm sixthForm = new AccountExtendActionForm();
            sixthForm.setAccountId(16);
            sixthForm.setAccountStatus("0");
            sixthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-24 06:07:08.912"));
            sixthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-26 07:08:09.123"));
            accountExtendFormList.add(sixthForm);
            AccountExtendActionForm seventhForm = new AccountExtendActionForm();
            seventhForm.setAccountId(15);
            seventhForm.setAccountStatus("1");
            seventhForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-24 05:06:07.891"));
            seventhForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-25 06:07:08.912"));
            accountExtendFormList.add(seventhForm);
            AccountExtendActionForm eighthForm = new AccountExtendActionForm();
            eighthForm.setAccountId(14);
            eighthForm.setAccountStatus("0");
            eighthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-23 04:05:06.789"));
            eighthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-24 05:06:07.891"));
            accountExtendFormList.add(eighthForm);
            AccountExtendActionForm ninthForm = new AccountExtendActionForm();
            ninthForm.setAccountId(13);
            ninthForm.setAccountStatus("1");
            ninthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-22 03:04:05.678"));
            ninthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-23 04:05:06.789"));
            accountExtendFormList.add(ninthForm);
            AccountExtendActionForm tenthForm = new AccountExtendActionForm();
            tenthForm.setAccountId(12);
            tenthForm.setAccountStatus("0");
            tenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-21 02:03:04.567"));
            tenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-22 03:04:05.678"));
            accountExtendFormList.add(tenthForm);
            AccountExtendActionForm eleventhForm = new AccountExtendActionForm();
            eleventhForm.setAccountId(11);
            eleventhForm.setAccountStatus("1");
            eleventhForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-20 01:02:03.456"));
            eleventhForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-21 02:03:04.567"));
            accountExtendFormList.add(eleventhForm);
            AccountExtendActionForm twelfthForm = new AccountExtendActionForm();
            twelfthForm.setAccountId(10);
            twelfthForm.setAccountStatus("0");
            twelfthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-19 01:02:03.456"));
            twelfthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-20 02:03:04.567"));
            accountExtendFormList.add(twelfthForm);
            AccountExtendActionForm thirteenthForm = new AccountExtendActionForm();
            thirteenthForm.setAccountId(9);
            thirteenthForm.setAccountStatus("1");
            thirteenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-18 09:10:11.234"));
            thirteenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-19 01:02:03.456"));
            accountExtendFormList.add(thirteenthForm);
            AccountExtendActionForm fourteenthForm = new AccountExtendActionForm();
            fourteenthForm.setAccountId(8);
            fourteenthForm.setAccountStatus("0");
            fourteenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-17 08:09:10.123"));
            fourteenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-18 09:10:11.234"));
            accountExtendFormList.add(fourteenthForm);
            AccountExtendActionForm fifteenthForm = new AccountExtendActionForm();
            fifteenthForm.setAccountId(7);
            fifteenthForm.setAccountStatus("1");
            fifteenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-16 07:08:09.123"));
            fifteenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-17 08:09:10.234"));
            accountExtendFormList.add(fifteenthForm);
            AccountExtendActionForm sixteenthForm = new AccountExtendActionForm();
            sixteenthForm.setAccountId(6);
            sixteenthForm.setAccountStatus("0");
            sixteenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-15 06:07:08.912"));
            sixteenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-16 07:08:09.123"));
            accountExtendFormList.add(sixteenthForm);
            AccountExtendActionForm seventeenthForm = new AccountExtendActionForm();
            seventeenthForm.setAccountId(5);
            seventeenthForm.setAccountStatus("1");
            seventeenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-14 05:06:07.891"));
            seventeenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-15 06:07:08.912"));
            accountExtendFormList.add(seventeenthForm);
            AccountExtendActionForm eighteenthForm = new AccountExtendActionForm();
            eighteenthForm.setAccountId(4);
            eighteenthForm.setAccountStatus("0");
            eighteenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-13 04:05:06.789"));
            eighteenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-14 05:06:07.891"));
            accountExtendFormList.add(eighteenthForm);
            AccountExtendActionForm nineteenthForm = new AccountExtendActionForm();
            nineteenthForm.setAccountId(3);
            nineteenthForm.setAccountStatus("1");
            nineteenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-12 03:04:05.678"));
            nineteenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-13 04:05:06.789"));
            accountExtendFormList.add(nineteenthForm);
            AccountExtendActionForm twentiethForm = new AccountExtendActionForm();
            twentiethForm.setAccountId(2);
            twentiethForm.setAccountStatus("0");
            twentiethForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-11 02:03:04.567"));
            twentiethForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-12 03:04:05.678"));
            accountExtendFormList.add(twentiethForm);
            AccountExtendActionForm twentyFirstForm = new AccountExtendActionForm();
            twentyFirstForm.setAccountId(1);
            twentyFirstForm.setAccountStatus("1");
            twentyFirstForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-10 03:04:05.678"));
            twentyFirstForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-11 02:03:04.567"));
            accountExtendFormList.add(twentyFirstForm);
            adminSearchAccountForm.setAccountExtendFormList(accountExtendFormList);
            getRequest().setAttribute("AdminSearchAccountActionForm", adminSearchAccountForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            int[] expectedAccountIdArray =
                    {21, 20, 19, 18, 17, 16,
                    15, 14, 13, 12, 11,
                    10, 9, 8, 7, 6,
                    5, 4, 3, 2, 1};
            int[] expectedDisplayedAccountIdArray =
                    {21, 20, 19, 18, 17,
                    16, 15, 14, 13, 12,
                    11, 10, 9, 8, 7, 6,
                    5, 4, 3, 2};
            int expectedTotalForm = 21;
            int expectedTotalPage = 2;
            int expectedCurrentPage = 1;

            AdminListAccountActionForm actual = (AdminListAccountActionForm) getActionForm();

            for(int i = 0; i < actual.getAccountExtendFormList().size(); i++) {
                assertThat(actual.getAccountExtendFormList().get(i).getAccountId(), is(expectedAccountIdArray[i]));
            }
            for(int i = 0; i < actual.getDisplayedAccountList().size(); i++) {
                assertThat(
                        actual.getDisplayedAccountList().get(i).getAccountId(), is(expectedDisplayedAccountIdArray[i]));
            }
            assertThat(actual.getTotalForm(), is(expectedTotalForm));
            assertThat(actual.getTotalPage(), is(expectedTotalPage));
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseCurrentPageIsSecond() {
            AdminListAccountActionForm adminListAccountForm = new AdminListAccountActionForm();
            List<AccountExtendActionForm> accountExtendFormList = new ArrayList<>();
            AccountExtendActionForm firstForm = new AccountExtendActionForm();
            firstForm.setAccountId(21);
            firstForm.setAccountStatus("1");
            firstForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-28 09:10:11.234"));
            firstForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-31 02:03:04.567"));
            accountExtendFormList.add(firstForm);
            AccountExtendActionForm secondForm = new AccountExtendActionForm();
            secondForm.setAccountId(20);
            secondForm.setAccountStatus("0");
            secondForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-27 08:09:10.123"));
            secondForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-30 01:02:03.456"));
            accountExtendFormList.add(secondForm);
            AccountExtendActionForm thirdForm = new AccountExtendActionForm();
            thirdForm.setAccountId(19);
            thirdForm.setAccountStatus("1");
            thirdForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-26 07:08:09.123"));
            thirdForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-29 01:02:03.456"));
            accountExtendFormList.add(thirdForm);
            AccountExtendActionForm fourthForm = new AccountExtendActionForm();
            fourthForm.setAccountId(18);
            fourthForm.setAccountStatus("0");
            fourthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-25 06:07:08.912"));
            fourthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-28 09:10:11.234"));
            accountExtendFormList.add(fourthForm);
            AccountExtendActionForm fifthForm = new AccountExtendActionForm();
            fifthForm.setAccountId(17);
            fifthForm.setAccountStatus("1");
            fifthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-25 05:06:07.891"));
            fifthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-27 08:09:10.123"));
            accountExtendFormList.add(fifthForm);
            AccountExtendActionForm sixthForm = new AccountExtendActionForm();
            sixthForm.setAccountId(16);
            sixthForm.setAccountStatus("0");
            sixthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-24 06:07:08.912"));
            sixthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-26 07:08:09.123"));
            accountExtendFormList.add(sixthForm);
            AccountExtendActionForm seventhForm = new AccountExtendActionForm();
            seventhForm.setAccountId(15);
            seventhForm.setAccountStatus("1");
            seventhForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-24 05:06:07.891"));
            seventhForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-25 06:07:08.912"));
            accountExtendFormList.add(seventhForm);
            AccountExtendActionForm eighthForm = new AccountExtendActionForm();
            eighthForm.setAccountId(14);
            eighthForm.setAccountStatus("0");
            eighthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-23 04:05:06.789"));
            eighthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-24 05:06:07.891"));
            accountExtendFormList.add(eighthForm);
            AccountExtendActionForm ninthForm = new AccountExtendActionForm();
            ninthForm.setAccountId(13);
            ninthForm.setAccountStatus("1");
            ninthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-22 03:04:05.678"));
            ninthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-23 04:05:06.789"));
            accountExtendFormList.add(ninthForm);
            AccountExtendActionForm tenthForm = new AccountExtendActionForm();
            tenthForm.setAccountId(12);
            tenthForm.setAccountStatus("0");
            tenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-21 02:03:04.567"));
            tenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-22 03:04:05.678"));
            accountExtendFormList.add(tenthForm);
            AccountExtendActionForm eleventhForm = new AccountExtendActionForm();
            eleventhForm.setAccountId(11);
            eleventhForm.setAccountStatus("1");
            eleventhForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-20 01:02:03.456"));
            eleventhForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-21 02:03:04.567"));
            accountExtendFormList.add(eleventhForm);
            AccountExtendActionForm twelfthForm = new AccountExtendActionForm();
            twelfthForm.setAccountId(10);
            twelfthForm.setAccountStatus("0");
            twelfthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-19 01:02:03.456"));
            twelfthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-20 02:03:04.567"));
            accountExtendFormList.add(twelfthForm);
            AccountExtendActionForm thirteenthForm = new AccountExtendActionForm();
            thirteenthForm.setAccountId(9);
            thirteenthForm.setAccountStatus("1");
            thirteenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-18 09:10:11.234"));
            thirteenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-19 01:02:03.456"));
            accountExtendFormList.add(thirteenthForm);
            AccountExtendActionForm fourteenthForm = new AccountExtendActionForm();
            fourteenthForm.setAccountId(8);
            fourteenthForm.setAccountStatus("0");
            fourteenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-17 08:09:10.123"));
            fourteenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-18 09:10:11.234"));
            accountExtendFormList.add(fourteenthForm);
            AccountExtendActionForm fifteenthForm = new AccountExtendActionForm();
            fifteenthForm.setAccountId(7);
            fifteenthForm.setAccountStatus("1");
            fifteenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-16 07:08:09.123"));
            fifteenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-17 08:09:10.234"));
            accountExtendFormList.add(fifteenthForm);
            AccountExtendActionForm sixteenthForm = new AccountExtendActionForm();
            sixteenthForm.setAccountId(6);
            sixteenthForm.setAccountStatus("0");
            sixteenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-15 06:07:08.912"));
            sixteenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-16 07:08:09.123"));
            accountExtendFormList.add(sixteenthForm);
            AccountExtendActionForm seventeenthForm = new AccountExtendActionForm();
            seventeenthForm.setAccountId(5);
            seventeenthForm.setAccountStatus("1");
            seventeenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-14 05:06:07.891"));
            seventeenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-15 06:07:08.912"));
            accountExtendFormList.add(seventeenthForm);
            AccountExtendActionForm eighteenthForm = new AccountExtendActionForm();
            eighteenthForm.setAccountId(4);
            eighteenthForm.setAccountStatus("0");
            eighteenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-13 04:05:06.789"));
            eighteenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-14 05:06:07.891"));
            accountExtendFormList.add(eighteenthForm);
            AccountExtendActionForm nineteenthForm = new AccountExtendActionForm();
            nineteenthForm.setAccountId(3);
            nineteenthForm.setAccountStatus("1");
            nineteenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-12 03:04:05.678"));
            nineteenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-13 04:05:06.789"));
            accountExtendFormList.add(nineteenthForm);
            AccountExtendActionForm twentiethForm = new AccountExtendActionForm();
            twentiethForm.setAccountId(2);
            twentiethForm.setAccountStatus("0");
            twentiethForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-11 02:03:04.567"));
            twentiethForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-12 03:04:05.678"));
            accountExtendFormList.add(twentiethForm);
            AccountExtendActionForm twentyFirstForm = new AccountExtendActionForm();
            twentyFirstForm.setAccountId(1);
            twentyFirstForm.setAccountStatus("1");
            twentyFirstForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-10 03:04:05.678"));
            twentyFirstForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-11 02:03:04.567"));
            accountExtendFormList.add(twentyFirstForm);
            adminListAccountForm.setAccountExtendFormList(accountExtendFormList);
            setActionForm(adminListAccountForm);
            addRequestParameter("currentPage", "2");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            int[] expectedAccountIdArray =
                    {21, 20, 19, 18, 17, 16,
                    15, 14, 13, 12, 11,
                    10, 9, 8, 7, 6,
                    5, 4, 3, 2, 1};
            int[] expectedDisplayedAccountIdArray = {1};
            int expectedCurrentPage = 2;

            AdminListAccountActionForm actual = (AdminListAccountActionForm) getActionForm();

            for(int i = 0; i < actual.getAccountExtendFormList().size(); i++) {
                assertThat(actual.getAccountExtendFormList().get(i).getAccountId(), is(expectedAccountIdArray[i]));
            }
            for(int i = 0; i < actual.getDisplayedAccountList().size(); i++) {
                assertThat(
                        actual.getDisplayedAccountList().get(i).getAccountId(), is(expectedDisplayedAccountIdArray[i]));
            }
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseSortByAccendingUpdatedAt() {
            AdminListAccountActionForm adminListAccountForm = new AdminListAccountActionForm();
            List<AccountExtendActionForm> accountExtendFormList = new ArrayList<>();
            AccountExtendActionForm firstForm = new AccountExtendActionForm();
            firstForm.setAccountId(21);
            firstForm.setAccountStatus("1");
            firstForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-28 09:10:11.234"));
            firstForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-31 02:03:04.567"));
            accountExtendFormList.add(firstForm);
            AccountExtendActionForm secondForm = new AccountExtendActionForm();
            secondForm.setAccountId(20);
            secondForm.setAccountStatus("0");
            secondForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-27 08:09:10.123"));
            secondForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-30 01:02:03.456"));
            accountExtendFormList.add(secondForm);
            AccountExtendActionForm thirdForm = new AccountExtendActionForm();
            thirdForm.setAccountId(19);
            thirdForm.setAccountStatus("1");
            thirdForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-26 07:08:09.123"));
            thirdForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-29 01:02:03.456"));
            accountExtendFormList.add(thirdForm);
            AccountExtendActionForm fourthForm = new AccountExtendActionForm();
            fourthForm.setAccountId(18);
            fourthForm.setAccountStatus("0");
            fourthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-25 06:07:08.912"));
            fourthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-28 09:10:11.234"));
            accountExtendFormList.add(fourthForm);
            AccountExtendActionForm fifthForm = new AccountExtendActionForm();
            fifthForm.setAccountId(17);
            fifthForm.setAccountStatus("1");
            fifthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-25 05:06:07.891"));
            fifthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-27 08:09:10.123"));
            accountExtendFormList.add(fifthForm);
            AccountExtendActionForm sixthForm = new AccountExtendActionForm();
            sixthForm.setAccountId(16);
            sixthForm.setAccountStatus("0");
            sixthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-24 06:07:08.912"));
            sixthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-26 07:08:09.123"));
            accountExtendFormList.add(sixthForm);
            AccountExtendActionForm seventhForm = new AccountExtendActionForm();
            seventhForm.setAccountId(15);
            seventhForm.setAccountStatus("1");
            seventhForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-24 05:06:07.891"));
            seventhForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-25 06:07:08.912"));
            accountExtendFormList.add(seventhForm);
            AccountExtendActionForm eighthForm = new AccountExtendActionForm();
            eighthForm.setAccountId(14);
            eighthForm.setAccountStatus("0");
            eighthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-23 04:05:06.789"));
            eighthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-24 05:06:07.891"));
            accountExtendFormList.add(eighthForm);
            AccountExtendActionForm ninthForm = new AccountExtendActionForm();
            ninthForm.setAccountId(13);
            ninthForm.setAccountStatus("1");
            ninthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-22 03:04:05.678"));
            ninthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-23 04:05:06.789"));
            accountExtendFormList.add(ninthForm);
            AccountExtendActionForm tenthForm = new AccountExtendActionForm();
            tenthForm.setAccountId(12);
            tenthForm.setAccountStatus("0");
            tenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-21 02:03:04.567"));
            tenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-22 03:04:05.678"));
            accountExtendFormList.add(tenthForm);
            AccountExtendActionForm eleventhForm = new AccountExtendActionForm();
            eleventhForm.setAccountId(11);
            eleventhForm.setAccountStatus("1");
            eleventhForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-20 01:02:03.456"));
            eleventhForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-21 02:03:04.567"));
            accountExtendFormList.add(eleventhForm);
            AccountExtendActionForm twelfthForm = new AccountExtendActionForm();
            twelfthForm.setAccountId(10);
            twelfthForm.setAccountStatus("0");
            twelfthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-19 01:02:03.456"));
            twelfthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-20 02:03:04.567"));
            accountExtendFormList.add(twelfthForm);
            AccountExtendActionForm thirteenthForm = new AccountExtendActionForm();
            thirteenthForm.setAccountId(9);
            thirteenthForm.setAccountStatus("1");
            thirteenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-18 09:10:11.234"));
            thirteenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-19 01:02:03.456"));
            accountExtendFormList.add(thirteenthForm);
            AccountExtendActionForm fourteenthForm = new AccountExtendActionForm();
            fourteenthForm.setAccountId(8);
            fourteenthForm.setAccountStatus("0");
            fourteenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-17 08:09:10.123"));
            fourteenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-18 09:10:11.234"));
            accountExtendFormList.add(fourteenthForm);
            AccountExtendActionForm fifteenthForm = new AccountExtendActionForm();
            fifteenthForm.setAccountId(7);
            fifteenthForm.setAccountStatus("1");
            fifteenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-16 07:08:09.123"));
            fifteenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-17 08:09:10.234"));
            accountExtendFormList.add(fifteenthForm);
            AccountExtendActionForm sixteenthForm = new AccountExtendActionForm();
            sixteenthForm.setAccountId(6);
            sixteenthForm.setAccountStatus("0");
            sixteenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-15 06:07:08.912"));
            sixteenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-16 07:08:09.123"));
            accountExtendFormList.add(sixteenthForm);
            AccountExtendActionForm seventeenthForm = new AccountExtendActionForm();
            seventeenthForm.setAccountId(5);
            seventeenthForm.setAccountStatus("1");
            seventeenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-14 05:06:07.891"));
            seventeenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-15 06:07:08.912"));
            accountExtendFormList.add(seventeenthForm);
            AccountExtendActionForm eighteenthForm = new AccountExtendActionForm();
            eighteenthForm.setAccountId(4);
            eighteenthForm.setAccountStatus("0");
            eighteenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-13 04:05:06.789"));
            eighteenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-14 05:06:07.891"));
            accountExtendFormList.add(eighteenthForm);
            AccountExtendActionForm nineteenthForm = new AccountExtendActionForm();
            nineteenthForm.setAccountId(3);
            nineteenthForm.setAccountStatus("1");
            nineteenthForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-12 03:04:05.678"));
            nineteenthForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-13 04:05:06.789"));
            accountExtendFormList.add(nineteenthForm);
            AccountExtendActionForm twentiethForm = new AccountExtendActionForm();
            twentiethForm.setAccountId(2);
            twentiethForm.setAccountStatus("0");
            twentiethForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-11 02:03:04.567"));
            twentiethForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-12 03:04:05.678"));
            accountExtendFormList.add(twentiethForm);
            AccountExtendActionForm twentyFirstForm = new AccountExtendActionForm();
            twentyFirstForm.setAccountId(1);
            twentyFirstForm.setAccountStatus("1");
            twentyFirstForm.setAccountCreatedAt(Timestamp.valueOf("2022-02-10 03:04:05.678"));
            twentyFirstForm.setAccountUpdatedAt(Timestamp.valueOf("2022-01-11 02:03:04.567"));
            accountExtendFormList.add(twentyFirstForm);
            adminListAccountForm.setAccountExtendFormList(accountExtendFormList);
            adminListAccountForm.setSortFlag(true);
            adminListAccountForm.setSortKindForAccount("byAccendingUpdatedAt");
            setActionForm(adminListAccountForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            int[] expectedAccountIdArray =
                    {1, 2, 3, 4, 5,
                    6, 7, 8, 9, 10,
                    11, 12, 13, 14, 15,
                    16, 17, 18, 19, 20, 21};
            int[] expectedDisplayedAccountIdArray =
                    {1, 2, 3, 4, 5,
                    6, 7, 8, 9, 10,
                    11, 12, 13, 14, 15,
                    16, 17, 18, 19, 20};
            int expectedCurrentPage = 1;

            AdminListAccountActionForm actual = (AdminListAccountActionForm) getActionForm();

            for(int i = 0; i < actual.getAccountExtendFormList().size(); i++) {
                assertThat(actual.getAccountExtendFormList().get(i).getAccountId(), is(expectedAccountIdArray[i]));
            }
            for(int i = 0; i < actual.getDisplayedAccountList().size(); i++) {
                assertThat(
                        actual.getDisplayedAccountList().get(i).getAccountId(), is(expectedDisplayedAccountIdArray[i]));
            }
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
            assertThat(actual.isSortFlag(), is(false));
        }

        @Test
        public void testExecuteInCaseLogout() {
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/listAccount.do");
        }

    }

}
