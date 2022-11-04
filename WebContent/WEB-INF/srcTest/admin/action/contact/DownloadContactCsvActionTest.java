package admin.action.contact;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsRegex.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import actionform.ContactExtendActionForm;
import admin.actionform.contact.AdminListContactActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class DownloadContactCsvActionTest {

    public static class executeTest extends MockStrutsTestCase {

        private static final String ACTION_PATH = "/downloadContactCsv.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteInCaseDownloadActualDataCsvSuccessfully() {
            AdminListContactActionForm inForm = new AdminListContactActionForm();
            List<ContactExtendActionForm> contactExtendFormList = new ArrayList<>();
            ContactExtendActionForm firstForm = new ContactExtendActionForm();
            firstForm.setContactId(1);
            firstForm.setContactAccountId(0);
            firstForm.setContactedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            firstForm.setContactStatusId(2);
            firstForm.setContactStatusName("連絡済");
            firstForm.setContactLastName("田中");
            firstForm.setContactFirstName("太郎");
            firstForm.setContactLastNameKana("タナカ");
            firstForm.setContactFirstNameKana("タロウ");
            firstForm.setContactContent("田中のお問合せです。");
            firstForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            contactExtendFormList.add(firstForm);
            ContactExtendActionForm secondForm = new ContactExtendActionForm();
            secondForm.setContactId(2);
            secondForm.setContactAccountId(3);
            secondForm.setContactedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            secondForm.setContactStatusId(1);
            secondForm.setContactStatusName("受付済");
            secondForm.setContactLastName("高橋");
            secondForm.setContactFirstName("次郎");
            secondForm.setContactLastNameKana("タカハシ");
            secondForm.setContactFirstNameKana("ジロウ");
            secondForm.setContactContent("高橋のお問合せです。");
            secondForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
            contactExtendFormList.add(secondForm);
            ContactExtendActionForm thirdForm = new ContactExtendActionForm();
            thirdForm.setContactId(3);
            thirdForm.setContactAccountId(0);
            thirdForm.setContactedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
            thirdForm.setContactStatusId(2);
            thirdForm.setContactStatusName("連絡済");
            thirdForm.setContactLastName("棚橋");
            thirdForm.setContactFirstName("三郎");
            thirdForm.setContactLastNameKana("タナハシ");
            thirdForm.setContactFirstNameKana("サブロウ");
            thirdForm.setContactContent("棚橋のお問合せです。");
            thirdForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
            contactExtendFormList.add(thirdForm);
            inForm.setContactExtendFormList(contactExtendFormList);
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward(null);

            String expectedContentType = "text/csv;charset=UTF8";
            String expectedContentDisposition = "attachment; filename=\"contacts_\\d{17}.csv\"";

            HttpServletResponse actual = getResponse();

            assertThat((String) actual.getContentType(), is(expectedContentType));
            assertThat(
                    (String) actual.getHeader("Content-Disposition"),
                    is(matchPatternAs(expectedContentDisposition)));
        }

        @Test
        public void testExecuteInCaseLogout() {
            AdminListContactActionForm inForm = new AdminListContactActionForm();
            List<ContactExtendActionForm> contactExtendFormList = new ArrayList<>();
            ContactExtendActionForm firstForm = new ContactExtendActionForm();
            firstForm.setContactId(1);
            firstForm.setContactAccountId(0);
            firstForm.setContactedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            firstForm.setContactStatusId(2);
            firstForm.setContactStatusName("連絡済");
            firstForm.setContactLastName("田中");
            firstForm.setContactFirstName("太郎");
            firstForm.setContactLastNameKana("タナカ");
            firstForm.setContactFirstNameKana("タロウ");
            firstForm.setContactContent("田中のお問合せです。");
            firstForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            contactExtendFormList.add(firstForm);
            ContactExtendActionForm secondForm = new ContactExtendActionForm();
            secondForm.setContactId(2);
            secondForm.setContactAccountId(3);
            secondForm.setContactedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            secondForm.setContactStatusId(1);
            secondForm.setContactStatusName("受付済");
            secondForm.setContactLastName("高橋");
            secondForm.setContactFirstName("次郎");
            secondForm.setContactLastNameKana("タカハシ");
            secondForm.setContactFirstNameKana("ジロウ");
            secondForm.setContactContent("高橋のお問合せです。");
            secondForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
            contactExtendFormList.add(secondForm);
            ContactExtendActionForm thirdForm = new ContactExtendActionForm();
            thirdForm.setContactId(3);
            thirdForm.setContactAccountId(0);
            thirdForm.setContactedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
            thirdForm.setContactStatusId(2);
            thirdForm.setContactStatusName("連絡済");
            thirdForm.setContactLastName("棚橋");
            thirdForm.setContactFirstName("三郎");
            thirdForm.setContactLastNameKana("タナハシ");
            thirdForm.setContactFirstNameKana("サブロウ");
            thirdForm.setContactContent("棚橋のお問合せです。");
            thirdForm.setContactUpdatedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
            contactExtendFormList.add(thirdForm);
            inForm.setContactExtendFormList(contactExtendFormList);
            setActionForm(inForm);
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/listContact.do");
        }

        @Test
        public void testExecuteInCaseError() {
            AdminListContactActionForm inForm = new AdminListContactActionForm();
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listContact.do");
            verifyActionErrors(new String[] {"ダウンロードに失敗しました。"});
        }

    }

}
