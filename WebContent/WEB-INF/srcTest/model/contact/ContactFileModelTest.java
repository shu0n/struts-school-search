package model.contact;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsHttpResponseHeader.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import actionform.ContactExtendActionForm;
import servletunit.HttpServletResponseSimulator;

public class ContactFileModelTest {

    @Test
    public void testSetdownloadCsvFileDataToSetData() throws IOException {
        String expected = "contacts_19700101090000000.csv";

        ContactFileModel sut = new ContactFileModel() {
            Timestamp getCurrentTimestamp() {
                return Timestamp.valueOf("1970-01-01 09:00:00.000");
            }
        };
        List<ContactExtendActionForm> contactExtendFormList = new ArrayList<>();
        ContactExtendActionForm form1 = new ContactExtendActionForm();
        form1.setContactId(1);
        form1.setContactAccountId(2);
        form1.setContactedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        form1.setContactStatusId(3);
        form1.setContactStatusName("お問合せ済");
        form1.setContactLastName("田中");
        form1.setContactFirstName("太郎");
        form1.setContactLastNameKana("タナカ");
        form1.setContactFirstNameKana("タロウ");
        form1.setContactContent("お問合せ１");
        form1.setContactUpdatedAt(Timestamp.valueOf("2022-01-03 04:04:06.789"));
        contactExtendFormList.add(form1);
        ContactExtendActionForm form2 = new ContactExtendActionForm();
        form2.setContactId(2);
        form2.setContactAccountId(3);
        form2.setContactedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        form2.setContactStatusId(4);
        form2.setContactStatusName("連絡済");
        form2.setContactLastName("高橋");
        form2.setContactFirstName("次郎");
        form2.setContactLastNameKana("タカハシ");
        form2.setContactFirstNameKana("ジロウ");
        form2.setContactContent("お問合せ２");
        form2.setContactUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        contactExtendFormList.add(form2);
        ContactExtendActionForm form3 = new ContactExtendActionForm();
        form3.setContactId(3);
        form3.setContactAccountId(4);
        form3.setContactedAt(Timestamp.valueOf("2022-01-06 07:08:09.123"));
        form3.setContactStatusId(5);
        form3.setContactStatusName("返信済");
        form3.setContactLastName("山田");
        form3.setContactFirstName("三郎");
        form3.setContactLastNameKana("ヤマダ");
        form3.setContactFirstNameKana("サブロウ");
        form3.setContactContent("お問合せ３");
        form3.setContactUpdatedAt(Timestamp.valueOf("2022-01-07 08:09:10.234"));
        contactExtendFormList.add(form3);
        HttpServletResponse stub = new HttpServletResponseSimulator();
        HttpServletResponse actual = sut.setDownloadCsvFileData(contactExtendFormList, stub);

        assertThat(actual, is(contentOf("text/csv;charset=UTF8", expected)));
    }

}
