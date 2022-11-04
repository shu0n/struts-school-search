package admin.action.entry;

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

import actionform.EntryExtendActionForm;
import admin.actionform.entry.AdminListReceivedEntryActionForm;
import admin.actionform.entry.AdminSearchReceivedEntryActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class ListReceivedEntryActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.ListReceivedEntryActionTest.xml") {
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

        private static final String ACTION_PATH = "/listReceivedEntry.do";

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

            int[] expectedEntryIdArray = {11, 10, 9, 8, 7, 6};
            EntryExtendActionForm expectedForm1 = new EntryExtendActionForm();
            expectedForm1.setEntryId(11);
            expectedForm1.setEntrySchoolId(6);
            expectedForm1.setEntrySchoolName("試験スクール5");
            expectedForm1.setApplicantAccountId(2);
            expectedForm1.setApplicantLastName("山田");
            expectedForm1.setApplicantFirstName("次郎");
            expectedForm1.setApplicantLastNameKana("ヤマダ");
            expectedForm1.setApplicantFirstNameKana("ジロウ");
            expectedForm1.setEntryQuestion("申込時の質問6");
            expectedForm1.setEntryStatusId(3);
            expectedForm1.setEntryStatusName("キャンセル済");
            expectedForm1.setEntriedAt(Timestamp.valueOf("2022-11-12 13:14:15.789"));
            expectedForm1.setEntryUpdatedAt(Timestamp.valueOf("2022-12-13 14:15:16.891"));
            expectedForm1.setStrEntriedAt("2022/11/12 13:14:15");
            expectedForm1.setStrEntryUpdatedAt("2022/12/13 14:15:16");
            EntryExtendActionForm expectedForm2 = new EntryExtendActionForm();
            expectedForm2.setEntryId(6);
            expectedForm2.setEntrySchoolId(2);
            expectedForm2.setEntrySchoolName("テストスクール1");
            expectedForm2.setApplicantAccountId(5);
            expectedForm2.setApplicantLastName("前橋");
            expectedForm2.setApplicantFirstName("勝");
            expectedForm2.setApplicantLastNameKana("マエハシ");
            expectedForm2.setApplicantFirstNameKana("マサル");
            expectedForm2.setEntryQuestion("申込時の質問1");
            expectedForm2.setEntryStatusId(1);
            expectedForm2.setEntryStatusName("申込済");
            expectedForm2.setEntriedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            expectedForm2.setEntryUpdatedAt(Timestamp.valueOf("2022-02-03 04:05:06.789"));
            expectedForm2.setStrEntriedAt("2022/01/02 03:04:05");
            expectedForm2.setStrEntryUpdatedAt("2022/02/03 04:05:06");
            List<LabelValueBean> expectedEntryStatusList = new ArrayList<>();
            expectedEntryStatusList.add(new LabelValueBean("申込済", "1"));
            expectedEntryStatusList.add(new LabelValueBean("連絡済", "2"));
            expectedEntryStatusList.add(new LabelValueBean("キャンセル済", "3"));
            List<LabelValueBean> expectedSortKindForEntryList = new ArrayList<>();
            expectedSortKindForEntryList.add(new LabelValueBean("選択する", ""));
            expectedSortKindForEntryList.add(new LabelValueBean("申込日が新しい順", "byDescendingEntriedAt"));
            expectedSortKindForEntryList.add(new LabelValueBean("申込日が古い順", "byAccendingEntriedAt"));
            expectedSortKindForEntryList.add(new LabelValueBean("更新日が新しい順", "byDescendingUpdatedAt"));
            expectedSortKindForEntryList.add(new LabelValueBean("更新日が古い順", "byAccendingUpdatedAt"));
            int expectedTotalForm = 6;
            int expectedTotalPage = 1;
            int expectedCurrentPage = 1;

            AdminListReceivedEntryActionForm actual = (AdminListReceivedEntryActionForm) getActionForm();

            for(int i = 0; i < actual.getEntryExtendFormList().size(); i++) {
                assertThat(actual.getEntryExtendFormList().get(i).getEntryId(), is(expectedEntryIdArray[i]));
            }
            assertThat(actual.getEntryExtendFormList().get(0), is(samePropertyValueAs(expectedForm1)));
            assertThat(actual.getEntryExtendFormList().get(5), is(samePropertyValueAs(expectedForm2)));
            assertThat(actual.getEntryStatusList(), is(sameComponentLavelAndValueAs(expectedEntryStatusList)));
            assertThat(
                    actual.getSortKindForEntryList(),
                    is(sameComponentLavelAndValueAs(expectedSortKindForEntryList)));
            assertThat(actual.getSortKindForEntry(), is(nullValue()));
            assertThat(actual.getTotalForm(), is(expectedTotalForm));
            assertThat(actual.getTotalPage(), is(expectedTotalPage));
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseAdminSearchReceivedEntryActionFormIsNotNull() {
            AdminSearchReceivedEntryActionForm adminSearchReceivedForm = new AdminSearchReceivedEntryActionForm();
            List<EntryExtendActionForm> entryExtendFormList = new ArrayList<>();
            EntryExtendActionForm firstForm = new EntryExtendActionForm();
            firstForm.setEntryId(21);
            firstForm.setEntriedAt(Timestamp.valueOf("2022-02-28 09:10:11.234"));
            firstForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-31 02:03:04.567"));
            entryExtendFormList.add(firstForm);
            EntryExtendActionForm secondForm = new EntryExtendActionForm();
            secondForm.setEntryId(20);
            secondForm.setEntriedAt(Timestamp.valueOf("2022-02-27 08:09:10.123"));
            secondForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-30 01:02:03.456"));
            entryExtendFormList.add(secondForm);
            EntryExtendActionForm thirdForm = new EntryExtendActionForm();
            thirdForm.setEntryId(19);
            thirdForm.setEntriedAt(Timestamp.valueOf("2022-02-26 07:08:09.123"));
            thirdForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-29 01:02:03.456"));
            entryExtendFormList.add(thirdForm);
            EntryExtendActionForm fourthForm = new EntryExtendActionForm();
            fourthForm.setEntryId(18);
            fourthForm.setEntriedAt(Timestamp.valueOf("2022-02-25 06:07:08.912"));
            fourthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-28 09:10:11.234"));
            entryExtendFormList.add(fourthForm);
            EntryExtendActionForm fifthForm = new EntryExtendActionForm();
            fifthForm.setEntryId(17);
            fifthForm.setEntriedAt(Timestamp.valueOf("2022-02-25 05:06:07.891"));
            fifthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-27 08:09:10.123"));
            entryExtendFormList.add(fifthForm);
            EntryExtendActionForm sixthForm = new EntryExtendActionForm();
            sixthForm.setEntryId(16);
            sixthForm.setEntriedAt(Timestamp.valueOf("2022-02-24 06:07:08.912"));
            sixthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-26 07:08:09.123"));
            entryExtendFormList.add(sixthForm);
            EntryExtendActionForm seventhForm = new EntryExtendActionForm();
            seventhForm.setEntryId(15);
            seventhForm.setEntriedAt(Timestamp.valueOf("2022-02-24 05:06:07.891"));
            seventhForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-25 06:07:08.912"));
            entryExtendFormList.add(seventhForm);
            EntryExtendActionForm eighthForm = new EntryExtendActionForm();
            eighthForm.setEntryId(14);
            eighthForm.setEntriedAt(Timestamp.valueOf("2022-02-23 04:05:06.789"));
            eighthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-24 05:06:07.891"));
            entryExtendFormList.add(eighthForm);
            EntryExtendActionForm ninthForm = new EntryExtendActionForm();
            ninthForm.setEntryId(13);
            ninthForm.setEntriedAt(Timestamp.valueOf("2022-02-22 03:04:05.678"));
            ninthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-23 04:05:06.789"));
            entryExtendFormList.add(ninthForm);
            EntryExtendActionForm tenthForm = new EntryExtendActionForm();
            tenthForm.setEntryId(12);
            tenthForm.setEntriedAt(Timestamp.valueOf("2022-02-21 02:03:04.567"));
            tenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-22 03:04:05.678"));
            entryExtendFormList.add(tenthForm);
            EntryExtendActionForm eleventhForm = new EntryExtendActionForm();
            eleventhForm.setEntryId(11);
            eleventhForm.setEntriedAt(Timestamp.valueOf("2022-02-20 01:02:03.456"));
            eleventhForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-21 02:03:04.567"));
            entryExtendFormList.add(eleventhForm);
            EntryExtendActionForm twelfthForm = new EntryExtendActionForm();
            twelfthForm.setEntryId(10);
            twelfthForm.setEntriedAt(Timestamp.valueOf("2022-02-19 01:02:03.456"));
            twelfthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-20 02:03:04.567"));
            entryExtendFormList.add(twelfthForm);
            EntryExtendActionForm thirteenthForm = new EntryExtendActionForm();
            thirteenthForm.setEntryId(9);
            thirteenthForm.setEntriedAt(Timestamp.valueOf("2022-02-18 09:10:11.234"));
            thirteenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-19 01:02:03.456"));
            entryExtendFormList.add(thirteenthForm);
            EntryExtendActionForm fourteenthForm = new EntryExtendActionForm();
            fourteenthForm.setEntryId(8);
            fourteenthForm.setEntriedAt(Timestamp.valueOf("2022-02-17 08:09:10.123"));
            fourteenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-18 09:10:11.234"));
            entryExtendFormList.add(fourteenthForm);
            EntryExtendActionForm fifteenthForm = new EntryExtendActionForm();
            fifteenthForm.setEntryId(7);
            fifteenthForm.setEntriedAt(Timestamp.valueOf("2022-02-16 07:08:09.123"));
            fifteenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-17 08:09:10.234"));
            entryExtendFormList.add(fifteenthForm);
            EntryExtendActionForm sixteenthForm = new EntryExtendActionForm();
            sixteenthForm.setEntryId(6);
            sixteenthForm.setEntriedAt(Timestamp.valueOf("2022-02-15 06:07:08.912"));
            sixteenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-16 07:08:09.123"));
            entryExtendFormList.add(sixteenthForm);
            EntryExtendActionForm seventeenthForm = new EntryExtendActionForm();
            seventeenthForm.setEntryId(5);
            seventeenthForm.setEntriedAt(Timestamp.valueOf("2022-02-14 05:06:07.891"));
            seventeenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-15 06:07:08.912"));
            entryExtendFormList.add(seventeenthForm);
            EntryExtendActionForm eighteenthForm = new EntryExtendActionForm();
            eighteenthForm.setEntryId(4);
            eighteenthForm.setEntriedAt(Timestamp.valueOf("2022-02-13 04:05:06.789"));
            eighteenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-14 05:06:07.891"));
            entryExtendFormList.add(eighteenthForm);
            EntryExtendActionForm nineteenthForm = new EntryExtendActionForm();
            nineteenthForm.setEntryId(3);
            nineteenthForm.setEntriedAt(Timestamp.valueOf("2022-02-12 03:04:05.678"));
            nineteenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-13 04:05:06.789"));
            entryExtendFormList.add(nineteenthForm);
            EntryExtendActionForm twentiethForm = new EntryExtendActionForm();
            twentiethForm.setEntryId(2);
            twentiethForm.setEntriedAt(Timestamp.valueOf("2022-02-11 02:03:04.567"));
            twentiethForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-12 03:04:05.678"));
            entryExtendFormList.add(twentiethForm);
            EntryExtendActionForm twentyFirstForm = new EntryExtendActionForm();
            twentyFirstForm.setEntryId(1);
            twentyFirstForm.setEntriedAt(Timestamp.valueOf("2022-02-10 03:04:05.678"));
            twentyFirstForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-11 02:03:04.567"));
            entryExtendFormList.add(twentyFirstForm);
            adminSearchReceivedForm.setEntryExtendFormList(entryExtendFormList);
            getRequest().setAttribute("AdminSearchReceivedEntryActionForm", adminSearchReceivedForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            int[] expectedEntryIdArray =
                    {21, 20, 19, 18, 17, 16,
                    15, 14, 13, 12, 11,
                    10, 9, 8, 7, 6,
                    5, 4, 3, 2, 1};
            int[] expectedDisplayedEntryIdArray =
                    {21, 20, 19, 18, 17,
                    16, 15, 14, 13, 12,
                    11, 10, 9, 8, 7, 6,
                    5, 4, 3, 2};
            int expectedTotalForm = 21;
            int expectedTotalPage = 2;
            int expectedCurrentPage = 1;

            AdminListReceivedEntryActionForm actual = (AdminListReceivedEntryActionForm) getActionForm();

            for(int i = 0; i < actual.getEntryExtendFormList().size(); i++) {
                assertThat(actual.getEntryExtendFormList().get(i).getEntryId(), is(expectedEntryIdArray[i]));
            }
            for(int i = 0; i < actual.getDisplayedEntryList().size(); i++) {
                assertThat(
                        actual.getDisplayedEntryList().get(i).getEntryId(), is(expectedDisplayedEntryIdArray[i]));
            }
            assertThat(actual.getTotalForm(), is(expectedTotalForm));
            assertThat(actual.getTotalPage(), is(expectedTotalPage));
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseCurrentPageIsSecond() {
            AdminListReceivedEntryActionForm adminListReceivedEntryForm = new AdminListReceivedEntryActionForm();
            List<EntryExtendActionForm> entryExtendFormList = new ArrayList<>();
            EntryExtendActionForm firstForm = new EntryExtendActionForm();
            firstForm.setEntryId(21);
            firstForm.setEntriedAt(Timestamp.valueOf("2022-02-28 09:10:11.234"));
            firstForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-31 02:03:04.567"));
            entryExtendFormList.add(firstForm);
            EntryExtendActionForm secondForm = new EntryExtendActionForm();
            secondForm.setEntryId(20);
            secondForm.setEntriedAt(Timestamp.valueOf("2022-02-27 08:09:10.123"));
            secondForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-30 01:02:03.456"));
            entryExtendFormList.add(secondForm);
            EntryExtendActionForm thirdForm = new EntryExtendActionForm();
            thirdForm.setEntryId(19);
            thirdForm.setEntriedAt(Timestamp.valueOf("2022-02-26 07:08:09.123"));
            thirdForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-29 01:02:03.456"));
            entryExtendFormList.add(thirdForm);
            EntryExtendActionForm fourthForm = new EntryExtendActionForm();
            fourthForm.setEntryId(18);
            fourthForm.setEntriedAt(Timestamp.valueOf("2022-02-25 06:07:08.912"));
            fourthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-28 09:10:11.234"));
            entryExtendFormList.add(fourthForm);
            EntryExtendActionForm fifthForm = new EntryExtendActionForm();
            fifthForm.setEntryId(17);
            fifthForm.setEntriedAt(Timestamp.valueOf("2022-02-25 05:06:07.891"));
            fifthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-27 08:09:10.123"));
            entryExtendFormList.add(fifthForm);
            EntryExtendActionForm sixthForm = new EntryExtendActionForm();
            sixthForm.setEntryId(16);
            sixthForm.setEntriedAt(Timestamp.valueOf("2022-02-24 06:07:08.912"));
            sixthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-26 07:08:09.123"));
            entryExtendFormList.add(sixthForm);
            EntryExtendActionForm seventhForm = new EntryExtendActionForm();
            seventhForm.setEntryId(15);
            seventhForm.setEntriedAt(Timestamp.valueOf("2022-02-24 05:06:07.891"));
            seventhForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-25 06:07:08.912"));
            entryExtendFormList.add(seventhForm);
            EntryExtendActionForm eighthForm = new EntryExtendActionForm();
            eighthForm.setEntryId(14);
            eighthForm.setEntriedAt(Timestamp.valueOf("2022-02-23 04:05:06.789"));
            eighthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-24 05:06:07.891"));
            entryExtendFormList.add(eighthForm);
            EntryExtendActionForm ninthForm = new EntryExtendActionForm();
            ninthForm.setEntryId(13);
            ninthForm.setEntriedAt(Timestamp.valueOf("2022-02-22 03:04:05.678"));
            ninthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-23 04:05:06.789"));
            entryExtendFormList.add(ninthForm);
            EntryExtendActionForm tenthForm = new EntryExtendActionForm();
            tenthForm.setEntryId(12);
            tenthForm.setEntriedAt(Timestamp.valueOf("2022-02-21 02:03:04.567"));
            tenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-22 03:04:05.678"));
            entryExtendFormList.add(tenthForm);
            EntryExtendActionForm eleventhForm = new EntryExtendActionForm();
            eleventhForm.setEntryId(11);
            eleventhForm.setEntriedAt(Timestamp.valueOf("2022-02-20 01:02:03.456"));
            eleventhForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-21 02:03:04.567"));
            entryExtendFormList.add(eleventhForm);
            EntryExtendActionForm twelfthForm = new EntryExtendActionForm();
            twelfthForm.setEntryId(10);
            twelfthForm.setEntriedAt(Timestamp.valueOf("2022-02-19 01:02:03.456"));
            twelfthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-20 02:03:04.567"));
            entryExtendFormList.add(twelfthForm);
            EntryExtendActionForm thirteenthForm = new EntryExtendActionForm();
            thirteenthForm.setEntryId(9);
            thirteenthForm.setEntriedAt(Timestamp.valueOf("2022-02-18 09:10:11.234"));
            thirteenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-19 01:02:03.456"));
            entryExtendFormList.add(thirteenthForm);
            EntryExtendActionForm fourteenthForm = new EntryExtendActionForm();
            fourteenthForm.setEntryId(8);
            fourteenthForm.setEntriedAt(Timestamp.valueOf("2022-02-17 08:09:10.123"));
            fourteenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-18 09:10:11.234"));
            entryExtendFormList.add(fourteenthForm);
            EntryExtendActionForm fifteenthForm = new EntryExtendActionForm();
            fifteenthForm.setEntryId(7);
            fifteenthForm.setEntriedAt(Timestamp.valueOf("2022-02-16 07:08:09.123"));
            fifteenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-17 08:09:10.234"));
            entryExtendFormList.add(fifteenthForm);
            EntryExtendActionForm sixteenthForm = new EntryExtendActionForm();
            sixteenthForm.setEntryId(6);
            sixteenthForm.setEntriedAt(Timestamp.valueOf("2022-02-15 06:07:08.912"));
            sixteenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-16 07:08:09.123"));
            entryExtendFormList.add(sixteenthForm);
            EntryExtendActionForm seventeenthForm = new EntryExtendActionForm();
            seventeenthForm.setEntryId(5);
            seventeenthForm.setEntriedAt(Timestamp.valueOf("2022-02-14 05:06:07.891"));
            seventeenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-15 06:07:08.912"));
            entryExtendFormList.add(seventeenthForm);
            EntryExtendActionForm eighteenthForm = new EntryExtendActionForm();
            eighteenthForm.setEntryId(4);
            eighteenthForm.setEntriedAt(Timestamp.valueOf("2022-02-13 04:05:06.789"));
            eighteenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-14 05:06:07.891"));
            entryExtendFormList.add(eighteenthForm);
            EntryExtendActionForm nineteenthForm = new EntryExtendActionForm();
            nineteenthForm.setEntryId(3);
            nineteenthForm.setEntriedAt(Timestamp.valueOf("2022-02-12 03:04:05.678"));
            nineteenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-13 04:05:06.789"));
            entryExtendFormList.add(nineteenthForm);
            EntryExtendActionForm twentiethForm = new EntryExtendActionForm();
            twentiethForm.setEntryId(2);
            twentiethForm.setEntriedAt(Timestamp.valueOf("2022-02-11 02:03:04.567"));
            twentiethForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-12 03:04:05.678"));
            entryExtendFormList.add(twentiethForm);
            EntryExtendActionForm twentyFirstForm = new EntryExtendActionForm();
            twentyFirstForm.setEntryId(1);
            twentyFirstForm.setEntriedAt(Timestamp.valueOf("2022-02-10 03:04:05.678"));
            twentyFirstForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-11 02:03:04.567"));
            entryExtendFormList.add(twentyFirstForm);
            adminListReceivedEntryForm.setEntryExtendFormList(entryExtendFormList);
            setActionForm(adminListReceivedEntryForm);
            addRequestParameter("currentPage", "2");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            int[] expectedEntryIdArray =
                    {21, 20, 19, 18, 17, 16,
                    15, 14, 13, 12, 11,
                    10, 9, 8, 7, 6,
                    5, 4, 3, 2, 1};
            int[] expectedDisplayedEntryIdArray = {1};
            int expectedCurrentPage = 2;

            AdminListReceivedEntryActionForm actual = (AdminListReceivedEntryActionForm) getActionForm();

            for(int i = 0; i < actual.getEntryExtendFormList().size(); i++) {
                assertThat(actual.getEntryExtendFormList().get(i).getEntryId(), is(expectedEntryIdArray[i]));
            }
            for(int i = 0; i < actual.getDisplayedEntryList().size(); i++) {
                assertThat(
                        actual.getDisplayedEntryList().get(i).getEntryId(), is(expectedDisplayedEntryIdArray[i]));
            }
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseSortByAccendingUpdatedAt() {
            AdminListReceivedEntryActionForm adminListReceivedEntryForm = new AdminListReceivedEntryActionForm();
            List<EntryExtendActionForm> entryExtendFormList = new ArrayList<>();
            EntryExtendActionForm firstForm = new EntryExtendActionForm();
            firstForm.setEntryId(21);
            firstForm.setEntriedAt(Timestamp.valueOf("2022-02-28 09:10:11.234"));
            firstForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-31 02:03:04.567"));
            entryExtendFormList.add(firstForm);
            EntryExtendActionForm secondForm = new EntryExtendActionForm();
            secondForm.setEntryId(20);
            secondForm.setEntriedAt(Timestamp.valueOf("2022-02-27 08:09:10.123"));
            secondForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-30 01:02:03.456"));
            entryExtendFormList.add(secondForm);
            EntryExtendActionForm thirdForm = new EntryExtendActionForm();
            thirdForm.setEntryId(19);
            thirdForm.setEntriedAt(Timestamp.valueOf("2022-02-26 07:08:09.123"));
            thirdForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-29 01:02:03.456"));
            entryExtendFormList.add(thirdForm);
            EntryExtendActionForm fourthForm = new EntryExtendActionForm();
            fourthForm.setEntryId(18);
            fourthForm.setEntriedAt(Timestamp.valueOf("2022-02-25 06:07:08.912"));
            fourthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-28 09:10:11.234"));
            entryExtendFormList.add(fourthForm);
            EntryExtendActionForm fifthForm = new EntryExtendActionForm();
            fifthForm.setEntryId(17);
            fifthForm.setEntriedAt(Timestamp.valueOf("2022-02-25 05:06:07.891"));
            fifthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-27 08:09:10.123"));
            entryExtendFormList.add(fifthForm);
            EntryExtendActionForm sixthForm = new EntryExtendActionForm();
            sixthForm.setEntryId(16);
            sixthForm.setEntriedAt(Timestamp.valueOf("2022-02-24 06:07:08.912"));
            sixthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-26 07:08:09.123"));
            entryExtendFormList.add(sixthForm);
            EntryExtendActionForm seventhForm = new EntryExtendActionForm();
            seventhForm.setEntryId(15);
            seventhForm.setEntriedAt(Timestamp.valueOf("2022-02-24 05:06:07.891"));
            seventhForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-25 06:07:08.912"));
            entryExtendFormList.add(seventhForm);
            EntryExtendActionForm eighthForm = new EntryExtendActionForm();
            eighthForm.setEntryId(14);
            eighthForm.setEntriedAt(Timestamp.valueOf("2022-02-23 04:05:06.789"));
            eighthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-24 05:06:07.891"));
            entryExtendFormList.add(eighthForm);
            EntryExtendActionForm ninthForm = new EntryExtendActionForm();
            ninthForm.setEntryId(13);
            ninthForm.setEntriedAt(Timestamp.valueOf("2022-02-22 03:04:05.678"));
            ninthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-23 04:05:06.789"));
            entryExtendFormList.add(ninthForm);
            EntryExtendActionForm tenthForm = new EntryExtendActionForm();
            tenthForm.setEntryId(12);
            tenthForm.setEntriedAt(Timestamp.valueOf("2022-02-21 02:03:04.567"));
            tenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-22 03:04:05.678"));
            entryExtendFormList.add(tenthForm);
            EntryExtendActionForm eleventhForm = new EntryExtendActionForm();
            eleventhForm.setEntryId(11);
            eleventhForm.setEntriedAt(Timestamp.valueOf("2022-02-20 01:02:03.456"));
            eleventhForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-21 02:03:04.567"));
            entryExtendFormList.add(eleventhForm);
            EntryExtendActionForm twelfthForm = new EntryExtendActionForm();
            twelfthForm.setEntryId(10);
            twelfthForm.setEntriedAt(Timestamp.valueOf("2022-02-19 01:02:03.456"));
            twelfthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-20 02:03:04.567"));
            entryExtendFormList.add(twelfthForm);
            EntryExtendActionForm thirteenthForm = new EntryExtendActionForm();
            thirteenthForm.setEntryId(9);
            thirteenthForm.setEntriedAt(Timestamp.valueOf("2022-02-18 09:10:11.234"));
            thirteenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-19 01:02:03.456"));
            entryExtendFormList.add(thirteenthForm);
            EntryExtendActionForm fourteenthForm = new EntryExtendActionForm();
            fourteenthForm.setEntryId(8);
            fourteenthForm.setEntriedAt(Timestamp.valueOf("2022-02-17 08:09:10.123"));
            fourteenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-18 09:10:11.234"));
            entryExtendFormList.add(fourteenthForm);
            EntryExtendActionForm fifteenthForm = new EntryExtendActionForm();
            fifteenthForm.setEntryId(7);
            fifteenthForm.setEntriedAt(Timestamp.valueOf("2022-02-16 07:08:09.123"));
            fifteenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-17 08:09:10.234"));
            entryExtendFormList.add(fifteenthForm);
            EntryExtendActionForm sixteenthForm = new EntryExtendActionForm();
            sixteenthForm.setEntryId(6);
            sixteenthForm.setEntriedAt(Timestamp.valueOf("2022-02-15 06:07:08.912"));
            sixteenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-16 07:08:09.123"));
            entryExtendFormList.add(sixteenthForm);
            EntryExtendActionForm seventeenthForm = new EntryExtendActionForm();
            seventeenthForm.setEntryId(5);
            seventeenthForm.setEntriedAt(Timestamp.valueOf("2022-02-14 05:06:07.891"));
            seventeenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-15 06:07:08.912"));
            entryExtendFormList.add(seventeenthForm);
            EntryExtendActionForm eighteenthForm = new EntryExtendActionForm();
            eighteenthForm.setEntryId(4);
            eighteenthForm.setEntriedAt(Timestamp.valueOf("2022-02-13 04:05:06.789"));
            eighteenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-14 05:06:07.891"));
            entryExtendFormList.add(eighteenthForm);
            EntryExtendActionForm nineteenthForm = new EntryExtendActionForm();
            nineteenthForm.setEntryId(3);
            nineteenthForm.setEntriedAt(Timestamp.valueOf("2022-02-12 03:04:05.678"));
            nineteenthForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-13 04:05:06.789"));
            entryExtendFormList.add(nineteenthForm);
            EntryExtendActionForm twentiethForm = new EntryExtendActionForm();
            twentiethForm.setEntryId(2);
            twentiethForm.setEntriedAt(Timestamp.valueOf("2022-02-11 02:03:04.567"));
            twentiethForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-12 03:04:05.678"));
            entryExtendFormList.add(twentiethForm);
            EntryExtendActionForm twentyFirstForm = new EntryExtendActionForm();
            twentyFirstForm.setEntryId(1);
            twentyFirstForm.setEntriedAt(Timestamp.valueOf("2022-02-10 03:04:05.678"));
            twentyFirstForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-11 02:03:04.567"));
            entryExtendFormList.add(twentyFirstForm);
            adminListReceivedEntryForm.setEntryExtendFormList(entryExtendFormList);
            adminListReceivedEntryForm.setSortFlag(true);
            adminListReceivedEntryForm.setSortKindForEntry("byAccendingUpdatedAt");
            setActionForm(adminListReceivedEntryForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            int[] expectedEntryIdArray =
                    {1, 2, 3, 4, 5,
                    6, 7, 8, 9, 10,
                    11, 12, 13, 14, 15,
                    16, 17, 18, 19, 20, 21};
            int[] expectedDisplayedEntryIdArray =
                    {1, 2, 3, 4, 5,
                    6, 7, 8, 9, 10,
                    11, 12, 13, 14, 15,
                    16, 17, 18, 19, 20};
            int expectedCurrentPage = 1;

            AdminListReceivedEntryActionForm actual = (AdminListReceivedEntryActionForm) getActionForm();

            for(int i = 0; i < actual.getEntryExtendFormList().size(); i++) {
                assertThat(actual.getEntryExtendFormList().get(i).getEntryId(), is(expectedEntryIdArray[i]));
            }
            for(int i = 0; i < actual.getDisplayedEntryList().size(); i++) {
                assertThat(
                        actual.getDisplayedEntryList().get(i).getEntryId(), is(expectedDisplayedEntryIdArray[i]));
            }
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
            assertThat(actual.isSortFlag(), is(false));
        }

        @Test
        public void testExecuteInCaseLogout() {
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/listReceivedEntry.do");
        }

    }

}
