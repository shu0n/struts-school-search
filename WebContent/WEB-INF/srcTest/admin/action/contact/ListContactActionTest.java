package admin.action.contact;

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

import actionform.ContactExtendActionForm;
import admin.actionform.contact.AdminListContactActionForm;
import admin.actionform.contact.AdminSearchContactActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.db.DbUnitTester;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class ListContactActionTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.ListContactActionTest.xml") {
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

        private static final String ACTION_PATH = "/listContact.do";

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

            int[] expectedContactIdArray = {6, 5, 4, 3, 2, 1};
            ContactExtendActionForm expectedForm1 = new ContactExtendActionForm();
            expectedForm1.setContactId(6);
            expectedForm1.setContactAccountId(3);
            expectedForm1.setContactLastName("高橋橋");
            expectedForm1.setContactFirstName("三郎郎");
            expectedForm1.setContactLastNameKana("サトウトウ");
            expectedForm1.setContactFirstNameKana("サブロウロウ");
            expectedForm1.setContactMailAddress("takahashihashi@example.com");
            expectedForm1.setContactContent("問い合わせ6");
            expectedForm1.setContactStatusId(2);
            expectedForm1.setContactStatusName("連絡済");
            expectedForm1.setContactedAt(Timestamp.valueOf("2022-01-12 13:14:15.789"));
            expectedForm1.setContactUpdatedAt(Timestamp.valueOf("2022-01-13 14:15:16.891"));
            expectedForm1.setStrContactedAt("2022/01/12 13:14:15");
            expectedForm1.setStrContactUpdatedAt("2022/01/13 14:15:16");
            ContactExtendActionForm expectedForm2 = new ContactExtendActionForm();
            expectedForm2.setContactId(1);
            expectedForm2.setContactAccountId(0);
            expectedForm2.setContactLastName("前田");
            expectedForm2.setContactFirstName("五郎");
            expectedForm2.setContactLastNameKana("マエダ");
            expectedForm2.setContactFirstNameKana("ゴロウ");
            expectedForm2.setContactMailAddress("maeda@example.com");
            expectedForm2.setContactContent("問い合わせ1");
            expectedForm2.setContactStatusId(2);
            expectedForm2.setContactStatusName("連絡済");
            expectedForm2.setContactedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            expectedForm2.setContactUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            expectedForm2.setStrContactedAt("2022/01/02 03:04:05");
            expectedForm2.setStrContactUpdatedAt("2022/01/03 04:05:06");
            List<LabelValueBean> expectedContactStatusList = new ArrayList<>();
            expectedContactStatusList.add(new LabelValueBean("受付済", "1"));
            expectedContactStatusList.add(new LabelValueBean("連絡済", "2"));
            expectedContactStatusList.add(new LabelValueBean("電話済", "3"));
            List<LabelValueBean> expectedSortKindForContactList = new ArrayList<>();
            expectedSortKindForContactList.add(new LabelValueBean("選択する", ""));
            expectedSortKindForContactList.add(new LabelValueBean("お問合せ日が新しい順", "byDescendingContactedAt"));
            expectedSortKindForContactList.add(new LabelValueBean("お問合せ日が古い順", "byAccendingContactedAt"));
            expectedSortKindForContactList.add(new LabelValueBean("更新日が新しい順", "byDescendingUpdatedAt"));
            expectedSortKindForContactList.add(new LabelValueBean("更新日が古い順", "byAccendingUpdatedAt"));
            int expectedTotalForm = 6;
            int expectedTotalPage = 1;
            int expectedCurrentPage = 1;

            AdminListContactActionForm actual = (AdminListContactActionForm) getActionForm();

            for(int i = 0; i < actual.getContactExtendFormList().size(); i++) {
                assertThat(actual.getContactExtendFormList().get(i).getContactId(), is(expectedContactIdArray[i]));
            }
            assertThat(actual.getContactExtendFormList().get(0), is(samePropertyValueAs(expectedForm1)));
            assertThat(actual.getContactExtendFormList().get(5), is(samePropertyValueAs(expectedForm2)));
            assertThat(actual.getContactStatusList(), is(sameComponentLavelAndValueAs(expectedContactStatusList)));
            assertThat(
                    actual.getSortKindForContactList(),
                    is(sameComponentLavelAndValueAs(expectedSortKindForContactList)));
            assertThat(actual.getSortKindForContact(), is(nullValue()));
            assertThat(actual.getTotalForm(), is(expectedTotalForm));
            assertThat(actual.getTotalPage(), is(expectedTotalPage));
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseAdminSearchContactActionFormIsNotNull() {
            AdminSearchContactActionForm adminSearchContactForm = new AdminSearchContactActionForm();
            List<ContactExtendActionForm> contactExtendFormList = new ArrayList<>();
            ContactExtendActionForm firstForm = new ContactExtendActionForm();
            firstForm.setContactId(21);
            firstForm.setContactedAt(Timestamp.valueOf("2022-02-28 09:10:11.234"));
            firstForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-31 02:03:04.567"));
            contactExtendFormList.add(firstForm);
            ContactExtendActionForm secondForm = new ContactExtendActionForm();
            secondForm.setContactId(20);
            secondForm.setContactedAt(Timestamp.valueOf("2022-02-27 08:09:10.123"));
            secondForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-30 01:02:03.456"));
            contactExtendFormList.add(secondForm);
            ContactExtendActionForm thirdForm = new ContactExtendActionForm();
            thirdForm.setContactId(19);
            thirdForm.setContactedAt(Timestamp.valueOf("2022-02-26 07:08:09.123"));
            thirdForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-29 01:02:03.456"));
            contactExtendFormList.add(thirdForm);
            ContactExtendActionForm fourthForm = new ContactExtendActionForm();
            fourthForm.setContactId(18);
            fourthForm.setContactedAt(Timestamp.valueOf("2022-02-25 06:07:08.912"));
            fourthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-28 09:10:11.234"));
            contactExtendFormList.add(fourthForm);
            ContactExtendActionForm fifthForm = new ContactExtendActionForm();
            fifthForm.setContactId(17);
            fifthForm.setContactedAt(Timestamp.valueOf("2022-02-25 05:06:07.891"));
            fifthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-27 08:09:10.123"));
            contactExtendFormList.add(fifthForm);
            ContactExtendActionForm sixthForm = new ContactExtendActionForm();
            sixthForm.setContactId(16);
            sixthForm.setContactedAt(Timestamp.valueOf("2022-02-24 06:07:08.912"));
            sixthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-26 07:08:09.123"));
            contactExtendFormList.add(sixthForm);
            ContactExtendActionForm seventhForm = new ContactExtendActionForm();
            seventhForm.setContactId(15);
            seventhForm.setContactedAt(Timestamp.valueOf("2022-02-24 05:06:07.891"));
            seventhForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-25 06:07:08.912"));
            contactExtendFormList.add(seventhForm);
            ContactExtendActionForm eighthForm = new ContactExtendActionForm();
            eighthForm.setContactId(14);
            eighthForm.setContactedAt(Timestamp.valueOf("2022-02-23 04:05:06.789"));
            eighthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-24 05:06:07.891"));
            contactExtendFormList.add(eighthForm);
            ContactExtendActionForm ninthForm = new ContactExtendActionForm();
            ninthForm.setContactId(13);
            ninthForm.setContactedAt(Timestamp.valueOf("2022-02-22 03:04:05.678"));
            ninthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-23 04:05:06.789"));
            contactExtendFormList.add(ninthForm);
            ContactExtendActionForm tenthForm = new ContactExtendActionForm();
            tenthForm.setContactId(12);
            tenthForm.setContactedAt(Timestamp.valueOf("2022-02-21 02:03:04.567"));
            tenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-22 03:04:05.678"));
            contactExtendFormList.add(tenthForm);
            ContactExtendActionForm eleventhForm = new ContactExtendActionForm();
            eleventhForm.setContactId(11);
            eleventhForm.setContactedAt(Timestamp.valueOf("2022-02-20 01:02:03.456"));
            eleventhForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-21 02:03:04.567"));
            contactExtendFormList.add(eleventhForm);
            ContactExtendActionForm twelfthForm = new ContactExtendActionForm();
            twelfthForm.setContactId(10);
            twelfthForm.setContactedAt(Timestamp.valueOf("2022-02-19 01:02:03.456"));
            twelfthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-20 02:03:04.567"));
            contactExtendFormList.add(twelfthForm);
            ContactExtendActionForm thirteenthForm = new ContactExtendActionForm();
            thirteenthForm.setContactId(9);
            thirteenthForm.setContactedAt(Timestamp.valueOf("2022-02-18 09:10:11.234"));
            thirteenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-19 01:02:03.456"));
            contactExtendFormList.add(thirteenthForm);
            ContactExtendActionForm fourteenthForm = new ContactExtendActionForm();
            fourteenthForm.setContactId(8);
            fourteenthForm.setContactedAt(Timestamp.valueOf("2022-02-17 08:09:10.123"));
            fourteenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-18 09:10:11.234"));
            contactExtendFormList.add(fourteenthForm);
            ContactExtendActionForm fifteenthForm = new ContactExtendActionForm();
            fifteenthForm.setContactId(7);
            fifteenthForm.setContactedAt(Timestamp.valueOf("2022-02-16 07:08:09.123"));
            fifteenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-17 08:09:10.234"));
            contactExtendFormList.add(fifteenthForm);
            ContactExtendActionForm sixteenthForm = new ContactExtendActionForm();
            sixteenthForm.setContactId(6);
            sixteenthForm.setContactedAt(Timestamp.valueOf("2022-02-15 06:07:08.912"));
            sixteenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-16 07:08:09.123"));
            contactExtendFormList.add(sixteenthForm);
            ContactExtendActionForm seventeenthForm = new ContactExtendActionForm();
            seventeenthForm.setContactId(5);
            seventeenthForm.setContactedAt(Timestamp.valueOf("2022-02-14 05:06:07.891"));
            seventeenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-15 06:07:08.912"));
            contactExtendFormList.add(seventeenthForm);
            ContactExtendActionForm eighteenthForm = new ContactExtendActionForm();
            eighteenthForm.setContactId(4);
            eighteenthForm.setContactedAt(Timestamp.valueOf("2022-02-13 04:05:06.789"));
            eighteenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-14 05:06:07.891"));
            contactExtendFormList.add(eighteenthForm);
            ContactExtendActionForm nineteenthForm = new ContactExtendActionForm();
            nineteenthForm.setContactId(3);
            nineteenthForm.setContactedAt(Timestamp.valueOf("2022-02-12 03:04:05.678"));
            nineteenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-13 04:05:06.789"));
            contactExtendFormList.add(nineteenthForm);
            ContactExtendActionForm twentiethForm = new ContactExtendActionForm();
            twentiethForm.setContactId(2);
            twentiethForm.setContactedAt(Timestamp.valueOf("2022-02-11 02:03:04.567"));
            twentiethForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-12 03:04:05.678"));
            contactExtendFormList.add(twentiethForm);
            ContactExtendActionForm twentyFirstForm = new ContactExtendActionForm();
            twentyFirstForm.setContactId(1);
            twentyFirstForm.setContactedAt(Timestamp.valueOf("2022-02-10 03:04:05.678"));
            twentyFirstForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-11 02:03:04.567"));
            contactExtendFormList.add(twentyFirstForm);
            adminSearchContactForm.setContactExtendFormList(contactExtendFormList);
            getRequest().setAttribute("AdminSearchContactActionForm", adminSearchContactForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            int[] expectedContactIdArray =
                    {21, 20, 19, 18, 17, 16,
                    15, 14, 13, 12, 11,
                    10, 9, 8, 7, 6,
                    5, 4, 3, 2, 1};
            int[] expectedDisplayedContactIdArray =
                    {21, 20, 19, 18, 17,
                    16, 15, 14, 13, 12,
                    11, 10, 9, 8, 7, 6,
                    5, 4, 3, 2};
            int expectedTotalForm = 21;
            int expectedTotalPage = 2;
            int expectedCurrentPage = 1;

            AdminListContactActionForm actual = (AdminListContactActionForm) getActionForm();

            for(int i = 0; i < actual.getContactExtendFormList().size(); i++) {
                assertThat(actual.getContactExtendFormList().get(i).getContactId(), is(expectedContactIdArray[i]));
            }
            for(int i = 0; i < actual.getDisplayedContactList().size(); i++) {
                assertThat(
                        actual.getDisplayedContactList().get(i).getContactId(), is(expectedDisplayedContactIdArray[i]));
            }
            assertThat(actual.getTotalForm(), is(expectedTotalForm));
            assertThat(actual.getTotalPage(), is(expectedTotalPage));
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseCurrentPageIsSecond() {
            AdminListContactActionForm adminListContactForm = new AdminListContactActionForm();
            List<ContactExtendActionForm> contactExtendFormList = new ArrayList<>();
            ContactExtendActionForm firstForm = new ContactExtendActionForm();
            firstForm.setContactId(21);
            firstForm.setContactedAt(Timestamp.valueOf("2022-02-28 09:10:11.234"));
            firstForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-31 02:03:04.567"));
            contactExtendFormList.add(firstForm);
            ContactExtendActionForm secondForm = new ContactExtendActionForm();
            secondForm.setContactId(20);
            secondForm.setContactedAt(Timestamp.valueOf("2022-02-27 08:09:10.123"));
            secondForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-30 01:02:03.456"));
            contactExtendFormList.add(secondForm);
            ContactExtendActionForm thirdForm = new ContactExtendActionForm();
            thirdForm.setContactId(19);
            thirdForm.setContactedAt(Timestamp.valueOf("2022-02-26 07:08:09.123"));
            thirdForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-29 01:02:03.456"));
            contactExtendFormList.add(thirdForm);
            ContactExtendActionForm fourthForm = new ContactExtendActionForm();
            fourthForm.setContactId(18);
            fourthForm.setContactedAt(Timestamp.valueOf("2022-02-25 06:07:08.912"));
            fourthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-28 09:10:11.234"));
            contactExtendFormList.add(fourthForm);
            ContactExtendActionForm fifthForm = new ContactExtendActionForm();
            fifthForm.setContactId(17);
            fifthForm.setContactedAt(Timestamp.valueOf("2022-02-25 05:06:07.891"));
            fifthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-27 08:09:10.123"));
            contactExtendFormList.add(fifthForm);
            ContactExtendActionForm sixthForm = new ContactExtendActionForm();
            sixthForm.setContactId(16);
            sixthForm.setContactedAt(Timestamp.valueOf("2022-02-24 06:07:08.912"));
            sixthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-26 07:08:09.123"));
            contactExtendFormList.add(sixthForm);
            ContactExtendActionForm seventhForm = new ContactExtendActionForm();
            seventhForm.setContactId(15);
            seventhForm.setContactedAt(Timestamp.valueOf("2022-02-24 05:06:07.891"));
            seventhForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-25 06:07:08.912"));
            contactExtendFormList.add(seventhForm);
            ContactExtendActionForm eighthForm = new ContactExtendActionForm();
            eighthForm.setContactId(14);
            eighthForm.setContactedAt(Timestamp.valueOf("2022-02-23 04:05:06.789"));
            eighthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-24 05:06:07.891"));
            contactExtendFormList.add(eighthForm);
            ContactExtendActionForm ninthForm = new ContactExtendActionForm();
            ninthForm.setContactId(13);
            ninthForm.setContactedAt(Timestamp.valueOf("2022-02-22 03:04:05.678"));
            ninthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-23 04:05:06.789"));
            contactExtendFormList.add(ninthForm);
            ContactExtendActionForm tenthForm = new ContactExtendActionForm();
            tenthForm.setContactId(12);
            tenthForm.setContactedAt(Timestamp.valueOf("2022-02-21 02:03:04.567"));
            tenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-22 03:04:05.678"));
            contactExtendFormList.add(tenthForm);
            ContactExtendActionForm eleventhForm = new ContactExtendActionForm();
            eleventhForm.setContactId(11);
            eleventhForm.setContactedAt(Timestamp.valueOf("2022-02-20 01:02:03.456"));
            eleventhForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-21 02:03:04.567"));
            contactExtendFormList.add(eleventhForm);
            ContactExtendActionForm twelfthForm = new ContactExtendActionForm();
            twelfthForm.setContactId(10);
            twelfthForm.setContactedAt(Timestamp.valueOf("2022-02-19 01:02:03.456"));
            twelfthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-20 02:03:04.567"));
            contactExtendFormList.add(twelfthForm);
            ContactExtendActionForm thirteenthForm = new ContactExtendActionForm();
            thirteenthForm.setContactId(9);
            thirteenthForm.setContactedAt(Timestamp.valueOf("2022-02-18 09:10:11.234"));
            thirteenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-19 01:02:03.456"));
            contactExtendFormList.add(thirteenthForm);
            ContactExtendActionForm fourteenthForm = new ContactExtendActionForm();
            fourteenthForm.setContactId(8);
            fourteenthForm.setContactedAt(Timestamp.valueOf("2022-02-17 08:09:10.123"));
            fourteenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-18 09:10:11.234"));
            contactExtendFormList.add(fourteenthForm);
            ContactExtendActionForm fifteenthForm = new ContactExtendActionForm();
            fifteenthForm.setContactId(7);
            fifteenthForm.setContactedAt(Timestamp.valueOf("2022-02-16 07:08:09.123"));
            fifteenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-17 08:09:10.234"));
            contactExtendFormList.add(fifteenthForm);
            ContactExtendActionForm sixteenthForm = new ContactExtendActionForm();
            sixteenthForm.setContactId(6);
            sixteenthForm.setContactedAt(Timestamp.valueOf("2022-02-15 06:07:08.912"));
            sixteenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-16 07:08:09.123"));
            contactExtendFormList.add(sixteenthForm);
            ContactExtendActionForm seventeenthForm = new ContactExtendActionForm();
            seventeenthForm.setContactId(5);
            seventeenthForm.setContactedAt(Timestamp.valueOf("2022-02-14 05:06:07.891"));
            seventeenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-15 06:07:08.912"));
            contactExtendFormList.add(seventeenthForm);
            ContactExtendActionForm eighteenthForm = new ContactExtendActionForm();
            eighteenthForm.setContactId(4);
            eighteenthForm.setContactedAt(Timestamp.valueOf("2022-02-13 04:05:06.789"));
            eighteenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-14 05:06:07.891"));
            contactExtendFormList.add(eighteenthForm);
            ContactExtendActionForm nineteenthForm = new ContactExtendActionForm();
            nineteenthForm.setContactId(3);
            nineteenthForm.setContactedAt(Timestamp.valueOf("2022-02-12 03:04:05.678"));
            nineteenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-13 04:05:06.789"));
            contactExtendFormList.add(nineteenthForm);
            ContactExtendActionForm twentiethForm = new ContactExtendActionForm();
            twentiethForm.setContactId(2);
            twentiethForm.setContactedAt(Timestamp.valueOf("2022-02-11 02:03:04.567"));
            twentiethForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-12 03:04:05.678"));
            contactExtendFormList.add(twentiethForm);
            ContactExtendActionForm twentyFirstForm = new ContactExtendActionForm();
            twentyFirstForm.setContactId(1);
            twentyFirstForm.setContactedAt(Timestamp.valueOf("2022-02-10 03:04:05.678"));
            twentyFirstForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-11 02:03:04.567"));
            contactExtendFormList.add(twentyFirstForm);
            adminListContactForm.setContactExtendFormList(contactExtendFormList);
            setActionForm(adminListContactForm);
            addRequestParameter("currentPage", "2");
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            int[] expectedContactIdArray =
                    {21, 20, 19, 18, 17, 16,
                    15, 14, 13, 12, 11,
                    10, 9, 8, 7, 6,
                    5, 4, 3, 2, 1};
            int[] expectedDisplayedContactIdArray = {1};
            int expectedCurrentPage = 2;

            AdminListContactActionForm actual = (AdminListContactActionForm) getActionForm();

            for(int i = 0; i < actual.getContactExtendFormList().size(); i++) {
                assertThat(actual.getContactExtendFormList().get(i).getContactId(), is(expectedContactIdArray[i]));
            }
            for(int i = 0; i < actual.getDisplayedContactList().size(); i++) {
                assertThat(
                        actual.getDisplayedContactList().get(i).getContactId(), is(expectedDisplayedContactIdArray[i]));
            }
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
        }

        @Test
        public void testExecuteToForwardSuccessInCaseSortByAccendingUpdatedAt() {
            AdminListContactActionForm adminListContactForm = new AdminListContactActionForm();
            List<ContactExtendActionForm> contactExtendFormList = new ArrayList<>();
            ContactExtendActionForm firstForm = new ContactExtendActionForm();
            firstForm.setContactId(21);
            firstForm.setContactedAt(Timestamp.valueOf("2022-02-28 09:10:11.234"));
            firstForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-31 02:03:04.567"));
            contactExtendFormList.add(firstForm);
            ContactExtendActionForm secondForm = new ContactExtendActionForm();
            secondForm.setContactId(20);
            secondForm.setContactedAt(Timestamp.valueOf("2022-02-27 08:09:10.123"));
            secondForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-30 01:02:03.456"));
            contactExtendFormList.add(secondForm);
            ContactExtendActionForm thirdForm = new ContactExtendActionForm();
            thirdForm.setContactId(19);
            thirdForm.setContactedAt(Timestamp.valueOf("2022-02-26 07:08:09.123"));
            thirdForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-29 01:02:03.456"));
            contactExtendFormList.add(thirdForm);
            ContactExtendActionForm fourthForm = new ContactExtendActionForm();
            fourthForm.setContactId(18);
            fourthForm.setContactedAt(Timestamp.valueOf("2022-02-25 06:07:08.912"));
            fourthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-28 09:10:11.234"));
            contactExtendFormList.add(fourthForm);
            ContactExtendActionForm fifthForm = new ContactExtendActionForm();
            fifthForm.setContactId(17);
            fifthForm.setContactedAt(Timestamp.valueOf("2022-02-25 05:06:07.891"));
            fifthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-27 08:09:10.123"));
            contactExtendFormList.add(fifthForm);
            ContactExtendActionForm sixthForm = new ContactExtendActionForm();
            sixthForm.setContactId(16);
            sixthForm.setContactedAt(Timestamp.valueOf("2022-02-24 06:07:08.912"));
            sixthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-26 07:08:09.123"));
            contactExtendFormList.add(sixthForm);
            ContactExtendActionForm seventhForm = new ContactExtendActionForm();
            seventhForm.setContactId(15);
            seventhForm.setContactedAt(Timestamp.valueOf("2022-02-24 05:06:07.891"));
            seventhForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-25 06:07:08.912"));
            contactExtendFormList.add(seventhForm);
            ContactExtendActionForm eighthForm = new ContactExtendActionForm();
            eighthForm.setContactId(14);
            eighthForm.setContactedAt(Timestamp.valueOf("2022-02-23 04:05:06.789"));
            eighthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-24 05:06:07.891"));
            contactExtendFormList.add(eighthForm);
            ContactExtendActionForm ninthForm = new ContactExtendActionForm();
            ninthForm.setContactId(13);
            ninthForm.setContactedAt(Timestamp.valueOf("2022-02-22 03:04:05.678"));
            ninthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-23 04:05:06.789"));
            contactExtendFormList.add(ninthForm);
            ContactExtendActionForm tenthForm = new ContactExtendActionForm();
            tenthForm.setContactId(12);
            tenthForm.setContactedAt(Timestamp.valueOf("2022-02-21 02:03:04.567"));
            tenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-22 03:04:05.678"));
            contactExtendFormList.add(tenthForm);
            ContactExtendActionForm eleventhForm = new ContactExtendActionForm();
            eleventhForm.setContactId(11);
            eleventhForm.setContactedAt(Timestamp.valueOf("2022-02-20 01:02:03.456"));
            eleventhForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-21 02:03:04.567"));
            contactExtendFormList.add(eleventhForm);
            ContactExtendActionForm twelfthForm = new ContactExtendActionForm();
            twelfthForm.setContactId(10);
            twelfthForm.setContactedAt(Timestamp.valueOf("2022-02-19 01:02:03.456"));
            twelfthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-20 02:03:04.567"));
            contactExtendFormList.add(twelfthForm);
            ContactExtendActionForm thirteenthForm = new ContactExtendActionForm();
            thirteenthForm.setContactId(9);
            thirteenthForm.setContactedAt(Timestamp.valueOf("2022-02-18 09:10:11.234"));
            thirteenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-19 01:02:03.456"));
            contactExtendFormList.add(thirteenthForm);
            ContactExtendActionForm fourteenthForm = new ContactExtendActionForm();
            fourteenthForm.setContactId(8);
            fourteenthForm.setContactedAt(Timestamp.valueOf("2022-02-17 08:09:10.123"));
            fourteenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-18 09:10:11.234"));
            contactExtendFormList.add(fourteenthForm);
            ContactExtendActionForm fifteenthForm = new ContactExtendActionForm();
            fifteenthForm.setContactId(7);
            fifteenthForm.setContactedAt(Timestamp.valueOf("2022-02-16 07:08:09.123"));
            fifteenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-17 08:09:10.234"));
            contactExtendFormList.add(fifteenthForm);
            ContactExtendActionForm sixteenthForm = new ContactExtendActionForm();
            sixteenthForm.setContactId(6);
            sixteenthForm.setContactedAt(Timestamp.valueOf("2022-02-15 06:07:08.912"));
            sixteenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-16 07:08:09.123"));
            contactExtendFormList.add(sixteenthForm);
            ContactExtendActionForm seventeenthForm = new ContactExtendActionForm();
            seventeenthForm.setContactId(5);
            seventeenthForm.setContactedAt(Timestamp.valueOf("2022-02-14 05:06:07.891"));
            seventeenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-15 06:07:08.912"));
            contactExtendFormList.add(seventeenthForm);
            ContactExtendActionForm eighteenthForm = new ContactExtendActionForm();
            eighteenthForm.setContactId(4);
            eighteenthForm.setContactedAt(Timestamp.valueOf("2022-02-13 04:05:06.789"));
            eighteenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-14 05:06:07.891"));
            contactExtendFormList.add(eighteenthForm);
            ContactExtendActionForm nineteenthForm = new ContactExtendActionForm();
            nineteenthForm.setContactId(3);
            nineteenthForm.setContactedAt(Timestamp.valueOf("2022-02-12 03:04:05.678"));
            nineteenthForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-13 04:05:06.789"));
            contactExtendFormList.add(nineteenthForm);
            ContactExtendActionForm twentiethForm = new ContactExtendActionForm();
            twentiethForm.setContactId(2);
            twentiethForm.setContactedAt(Timestamp.valueOf("2022-02-11 02:03:04.567"));
            twentiethForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-12 03:04:05.678"));
            contactExtendFormList.add(twentiethForm);
            ContactExtendActionForm twentyFirstForm = new ContactExtendActionForm();
            twentyFirstForm.setContactId(1);
            twentyFirstForm.setContactedAt(Timestamp.valueOf("2022-02-10 03:04:05.678"));
            twentyFirstForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-11 02:03:04.567"));
            contactExtendFormList.add(twentyFirstForm);
            adminListContactForm.setContactExtendFormList(contactExtendFormList);
            adminListContactForm.setSortFlag(true);
            adminListContactForm.setSortKindForContact("byAccendingUpdatedAt");
            setActionForm(adminListContactForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward("success");

            int[] expectedContactIdArray =
                    {1, 2, 3, 4, 5,
                    6, 7, 8, 9, 10,
                    11, 12, 13, 14, 15,
                    16, 17, 18, 19, 20, 21};
            int[] expectedDisplayedContactIdArray =
                    {1, 2, 3, 4, 5,
                    6, 7, 8, 9, 10,
                    11, 12, 13, 14, 15,
                    16, 17, 18, 19, 20};
            int expectedCurrentPage = 1;

            AdminListContactActionForm actual = (AdminListContactActionForm) getActionForm();

            for(int i = 0; i < actual.getContactExtendFormList().size(); i++) {
                assertThat(actual.getContactExtendFormList().get(i).getContactId(), is(expectedContactIdArray[i]));
            }
            for(int i = 0; i < actual.getDisplayedContactList().size(); i++) {
                assertThat(
                        actual.getDisplayedContactList().get(i).getContactId(), is(expectedDisplayedContactIdArray[i]));
            }
            assertThat(actual.getCurrentPage(), is(expectedCurrentPage));
            assertThat(actual.isSortFlag(), is(false));
        }

        @Test
        public void testExecuteInCaseLogout() {
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/listContact.do");
        }

    }

}
