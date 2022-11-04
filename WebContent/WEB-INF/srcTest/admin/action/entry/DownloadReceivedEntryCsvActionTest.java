package admin.action.entry;

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

import actionform.EntryExtendActionForm;
import admin.actionform.entry.AdminListReceivedEntryActionForm;
import servletunit.struts.MockStrutsTestCase;
import test.struts.StrutsTester;

@RunWith(Enclosed.class)
public class DownloadReceivedEntryCsvActionTest {

    public static class executeTest extends MockStrutsTestCase {

        private static final String ACTION_PATH = "/downloadReceivedEntryCsv.do";

        public void setUp() throws Exception {
            super.setUp();
            setContextDirectory(StrutsTester.getContectDir());
            setConfigFile("/WEB-INF/struts-config-admin.xml");
            setRequestPathInfo(ACTION_PATH);
        }

        @Test
        public void testExecuteInCaseDownloadActualDataCsvSuccessfully() {
            AdminListReceivedEntryActionForm inForm = new AdminListReceivedEntryActionForm();
            List<EntryExtendActionForm> entryExtendFormList = new ArrayList<>();
            EntryExtendActionForm firstForm = new EntryExtendActionForm();
            firstForm.setEntryId(1);
            firstForm.setRegistrantAccountId(2);
            firstForm.setEntriedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            firstForm.setEntryStatusId(3);
            firstForm.setEntryStatusName("キャンセル済");
            firstForm.setEntrySchoolId(4);
            firstForm.setEntrySchoolName("申込スクール１");
            firstForm.setRegistrantLastName("田中");
            firstForm.setRegistrantFirstName("太郎");
            firstForm.setRegistrantLastNameKana("タナカ");
            firstForm.setRegistrantFirstNameKana("タロウ");
            firstForm.setEntryQuestion("申込１の質問です。");
            firstForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            entryExtendFormList.add(firstForm);
            EntryExtendActionForm secondForm = new EntryExtendActionForm();
            secondForm.setEntryId(2);
            secondForm.setRegistrantAccountId(3);
            secondForm.setEntriedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            secondForm.setEntryStatusId(1);
            secondForm.setEntryStatusName("申込済");
            secondForm.setEntrySchoolId(5);
            secondForm.setEntrySchoolName("申込スクール２");
            secondForm.setRegistrantLastName("高橋");
            secondForm.setRegistrantFirstName("次郎");
            secondForm.setRegistrantLastNameKana("タカハシ");
            secondForm.setRegistrantFirstNameKana("ジロウ");
            secondForm.setEntryQuestion("申込２の質問です。");
            secondForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
            entryExtendFormList.add(secondForm);
            EntryExtendActionForm thirdForm = new EntryExtendActionForm();
            thirdForm.setEntryId(3);
            thirdForm.setRegistrantAccountId(4);
            thirdForm.setEntriedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
            thirdForm.setEntryStatusId(2);
            thirdForm.setEntryStatusName("連絡済");
            thirdForm.setEntrySchoolId(6);
            thirdForm.setEntrySchoolName("申込スクール３");
            thirdForm.setRegistrantLastName("前田");
            thirdForm.setRegistrantFirstName("三郎");
            thirdForm.setRegistrantLastNameKana("マエダ");
            thirdForm.setRegistrantFirstNameKana("サブロウ");
            thirdForm.setEntryQuestion("申込３の質問です。");
            thirdForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
            entryExtendFormList.add(thirdForm);
            inForm.setEntryExtendFormList(entryExtendFormList);
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForward(null);

            String expectedContentType = "text/csv;charset=UTF8";
            String expectedContentDisposition = "attachment; filename=\"receivedEntries_\\d{17}.csv\"";

            HttpServletResponse actual = getResponse();

            assertThat((String) actual.getContentType(), is(expectedContentType));
            assertThat(
                    (String) actual.getHeader("Content-Disposition"),
                    is(matchPatternAs(expectedContentDisposition)));
        }

        @Test
        public void testExecuteInCaseLogout() {
            AdminListReceivedEntryActionForm inForm = new AdminListReceivedEntryActionForm();
            List<EntryExtendActionForm> entryExtendFormList = new ArrayList<>();
            EntryExtendActionForm firstForm = new EntryExtendActionForm();
            firstForm.setEntryId(1);
            firstForm.setRegistrantAccountId(2);
            firstForm.setEntriedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
            firstForm.setEntryStatusId(3);
            firstForm.setEntryStatusName("キャンセル済");
            firstForm.setEntrySchoolId(4);
            firstForm.setEntrySchoolName("申込スクール１");
            firstForm.setRegistrantLastName("田中");
            firstForm.setRegistrantFirstName("太郎");
            firstForm.setRegistrantLastNameKana("タナカ");
            firstForm.setRegistrantFirstNameKana("タロウ");
            firstForm.setEntryQuestion("申込１の質問です。");
            firstForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            entryExtendFormList.add(firstForm);
            EntryExtendActionForm secondForm = new EntryExtendActionForm();
            secondForm.setEntryId(2);
            secondForm.setRegistrantAccountId(3);
            secondForm.setEntriedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
            secondForm.setEntryStatusId(1);
            secondForm.setEntryStatusName("申込済");
            secondForm.setEntrySchoolId(5);
            secondForm.setEntrySchoolName("申込スクール２");
            secondForm.setRegistrantLastName("高橋");
            secondForm.setRegistrantFirstName("次郎");
            secondForm.setRegistrantLastNameKana("タカハシ");
            secondForm.setRegistrantFirstNameKana("ジロウ");
            secondForm.setEntryQuestion("申込２の質問です。");
            secondForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
            entryExtendFormList.add(secondForm);
            EntryExtendActionForm thirdForm = new EntryExtendActionForm();
            thirdForm.setEntryId(3);
            thirdForm.setRegistrantAccountId(4);
            thirdForm.setEntriedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
            thirdForm.setEntryStatusId(2);
            thirdForm.setEntryStatusName("連絡済");
            thirdForm.setEntrySchoolId(6);
            thirdForm.setEntrySchoolName("申込スクール３");
            thirdForm.setRegistrantLastName("前田");
            thirdForm.setRegistrantFirstName("三郎");
            thirdForm.setRegistrantLastNameKana("マエダ");
            thirdForm.setRegistrantFirstNameKana("サブロウ");
            thirdForm.setEntryQuestion("申込３の質問です。");
            thirdForm.setEntryUpdatedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
            entryExtendFormList.add(thirdForm);
            inForm.setEntryExtendFormList(entryExtendFormList);
            setActionForm(inForm);
            actionPerform();

            verifyForwardPath("/login.do?redirectUrl=/listReceivedEntry.do");
        }

        @Test
        public void testExecuteInCaseError() {
            AdminListReceivedEntryActionForm inForm = new AdminListReceivedEntryActionForm();
            setActionForm(inForm);
            getSession().setAttribute("adminLogined", "true");
            actionPerform();

            verifyForwardPath("/listReceivedEntry.do");
            verifyActionErrors(new String[] {"ダウンロードに失敗しました。"});
        }

    }

}
