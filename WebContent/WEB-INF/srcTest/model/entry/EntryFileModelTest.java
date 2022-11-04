package model.entry;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsHttpResponseHeader.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import actionform.EntryExtendActionForm;
import servletunit.HttpServletResponseSimulator;

public class EntryFileModelTest {

    @Test
    public void testSetDownloadCsvFileDataToSetData() throws Exception {
        String expected = "receivedEntries_19700101090000000.csv";

        EntryFileModel sut = new EntryFileModel() {
            Timestamp getCurrentTimestamp() {
                return Timestamp.valueOf("1970-01-01 09:00:00.000");
            }
        };
        List<EntryExtendActionForm> entryExtendFormList = new ArrayList<>();
        EntryExtendActionForm form1 = new EntryExtendActionForm();
        form1.setEntryId(1);
        form1.setRegistrantAccountId(2);
        form1.setEntriedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        form1.setEntryStatusId(3);
        form1.setEntryStatusName("受付済");
        form1.setEntrySchoolId(4);
        form1.setEntrySchoolName("テストスクール１");
        form1.setRegistrantFirstName("田中");
        form1.setRegistrantFirstName("太郎");
        form1.setRegistrantLastNameKana("タナカ");
        form1.setRegistrantFirstNameKana("タロウ");
        form1.setEntryQuestion("申込の質問１");
        form1.setEntryUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        entryExtendFormList.add(form1);
        EntryExtendActionForm form2 = new EntryExtendActionForm();
        form2.setEntryId(2);
        form2.setRegistrantAccountId(3);
        form2.setEntriedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        form2.setEntryStatusId(4);
        form2.setEntryStatusName("連絡済");
        form2.setEntrySchoolId(5);
        form2.setEntrySchoolName("テストスクール２");
        form2.setRegistrantFirstName("山田");
        form2.setRegistrantFirstName("次郎");
        form2.setRegistrantLastNameKana("ヤマダ");
        form2.setRegistrantFirstNameKana("ジロウ");
        form2.setEntryQuestion("申込の質問２");
        form2.setEntryUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        entryExtendFormList.add(form2);
        EntryExtendActionForm form3 = new EntryExtendActionForm();
        form3.setEntryId(3);
        form3.setRegistrantAccountId(4);
        form3.setEntriedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        form3.setEntryStatusId(5);
        form3.setEntryStatusName("返信済");
        form3.setEntrySchoolId(6);
        form3.setEntrySchoolName("テストスクール３");
        form3.setRegistrantFirstName("高橋");
        form3.setRegistrantFirstName("三郎");
        form3.setRegistrantLastNameKana("タカハシ");
        form3.setRegistrantFirstNameKana("サブロウ");
        form3.setEntryQuestion("申込の質問３");
        form3.setEntryUpdatedAt(Timestamp.valueOf("2022-01-07 08:09:10.213"));
        entryExtendFormList.add(form3);
        HttpServletResponse stub = new HttpServletResponseSimulator();
        HttpServletResponse actual = sut.setDownloadCsvFileData(entryExtendFormList, stub);

        assertThat(actual, is(contentOf("text/csv;charset=UTF8", expected)));
    }

}
