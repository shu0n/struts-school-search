package dao.entry;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.matcher.IsForm.*;
import static test.property.PropertyTester.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;

import actionform.EntryExtendActionForm;
import dao.entry.sql.EntrySelectJoinWhereActionForm;
import test.db.DbUnitTester;

public class SelectEntryJoinDAOTest {

    @ClassRule
    public static DbUnitTester dbUnitTester = new DbUnitTester(
            getProperty("dao/db.properties").getProperty("driverClassName"),
            getProperty("dao/db.properties").getProperty("db.url"),
            getProperty("dao/db.properties").getProperty("db.user"),
            getProperty("dao/db.properties").getProperty("db.password"),
            getProperty("dao/db.properties").getProperty("db.schema"),
            "setUpDataSet.SelectEntryJoinDAOTest.xml") {
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

    @Test
    public void testSelectMatchedEntryToGetAllEntry() throws Exception {
        String[] expected = {"6", "7", "8", "9", "10", "11"};

        SelectEntryJoinDAO sut = new SelectEntryJoinDAO();
        EntrySelectJoinWhereActionForm whereForm = new EntrySelectJoinWhereActionForm();
        List<EntryExtendActionForm> formList = sut.selectMatchedEntry(whereForm);
        String[] actual = new String[6];
        for(int i = 0; i < formList.size(); i++) {
            actual[i] = String.valueOf(formList.get(i).getEntryId());
        }

        assertThat(actual, is(arrayContaining(expected)));
    }

    @Test
    public void testSelectMatchedEntryToGetOneEntryByEntryId() throws Exception {
        EntryExtendActionForm expected = new EntryExtendActionForm();
        expected.setEntryId(7);
        expected.setEntrySchoolId(3);
        expected.setEntrySchoolName("テストスクール2");
        expected.setApplicantAccountId(4);
        expected.setApplicantLastName("高橋");
        expected.setApplicantFirstName("四郎");
        expected.setApplicantLastNameKana("タカハシ");
        expected.setApplicantFirstNameKana("シロウ");
        expected.setEntryQuestion("申込時の質問2");
        expected.setEntryStatusId(2);
        expected.setEntryStatusName("連絡済");
        expected.setEntriedAt(Timestamp.valueOf("2022-03-04 05:06:07.891"));
        expected.setEntryUpdatedAt(Timestamp.valueOf("2022-04-05 06:07:08.912"));

        SelectEntryJoinDAO sut = new SelectEntryJoinDAO();
        EntrySelectJoinWhereActionForm whereForm = new EntrySelectJoinWhereActionForm();
        whereForm.setEntryId(7);
        EntryExtendActionForm actual = sut.selectMatchedEntry(whereForm).get(0);

        assertThat(actual, is(samePropertyValueAs(expected)));
    }

    @Test
    public void testSelectMatchedEntryToGetOneEntryByEntrySchoolId() throws Exception {
        int expected = 1;

        SelectEntryJoinDAO sut = new SelectEntryJoinDAO();
        EntrySelectJoinWhereActionForm whereForm = new EntrySelectJoinWhereActionForm();
        whereForm.setEntrySchoolId(2);
        List<EntryExtendActionForm> actual = sut.selectMatchedEntry(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedEntryToGetNoEntryByMultipleCondition() throws Exception {
        int expected = 0;

        SelectEntryJoinDAO sut = new SelectEntryJoinDAO();
        EntrySelectJoinWhereActionForm whereForm = new EntrySelectJoinWhereActionForm();
        whereForm.setEntrySchoolId(3);
        whereForm.setApplicantAccountId(3);
        whereForm.setEntryStatusId(2);
        List<EntryExtendActionForm> actual = sut.selectMatchedEntry(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedEntryToGetThreeEntryByRangeOfEntrieDate() throws Exception {
        int expected = 3;

        SelectEntryJoinDAO sut = new SelectEntryJoinDAO();
        EntrySelectJoinWhereActionForm whereForm = new EntrySelectJoinWhereActionForm();
        whereForm.setFromEntriedDate("2022-01-01 00:00:00.000");
        whereForm.setToEntriedDate("2022-05-06 23:59:59.999");
        List<EntryExtendActionForm> actual = sut.selectMatchedEntry(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedEntryToGetTwoEntryByRangeOfEntryUpdatedDate() throws Exception {
        int expected = 2;

        SelectEntryJoinDAO sut = new SelectEntryJoinDAO();
        EntrySelectJoinWhereActionForm whereForm = new EntrySelectJoinWhereActionForm();
        whereForm.setFromEntryUpdatedDate("2022-06-07 00:00:00.000");
        whereForm.setToEntryUpdatedDate("2022-08-09 23:59:59.999");
        List<EntryExtendActionForm> actual = sut.selectMatchedEntry(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedEntryToGetOneEntryByArrayOfEntryId() throws Exception {
        int expected = 1;

        SelectEntryJoinDAO sut = new SelectEntryJoinDAO();
        EntrySelectJoinWhereActionForm whereForm = new EntrySelectJoinWhereActionForm();
        String[] EntryIdArray = {"11"};
        whereForm.setEntryIdArray(EntryIdArray);
        List<EntryExtendActionForm> actual = sut.selectMatchedEntry(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedEntryToGetThreeEntryByArrayOfEntryId() throws Exception {
        int expected = 3;

        SelectEntryJoinDAO sut = new SelectEntryJoinDAO();
        EntrySelectJoinWhereActionForm whereForm = new EntrySelectJoinWhereActionForm();
        String[] EntryIdArray = {"9", "10", "11", "12"};
        whereForm.setEntryIdArray(EntryIdArray);
        List<EntryExtendActionForm> actual = sut.selectMatchedEntry(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedEntryToGetFourEntryByArrayOfEntrySchoolId() throws Exception {
        int expected = 4;

        SelectEntryJoinDAO sut = new SelectEntryJoinDAO();
        EntrySelectJoinWhereActionForm whereForm = new EntrySelectJoinWhereActionForm();
        String[] EntrySchoolIdArray = {"2", "3", "6"};
        whereForm.setEntrySchoolIdArray(EntrySchoolIdArray);
        List<EntryExtendActionForm> actual = sut.selectMatchedEntry(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedEntryToGetFourEntryByArrayOfApplicantAccountId() throws Exception {
        int expected = 4;

        SelectEntryJoinDAO sut = new SelectEntryJoinDAO();
        EntrySelectJoinWhereActionForm whereForm = new EntrySelectJoinWhereActionForm();
        String[] applicantAccountIdArray = {"1", "2", "3", "6"};
        whereForm.setApplicantAccountIdArray(applicantAccountIdArray);
        List<EntryExtendActionForm> actual = sut.selectMatchedEntry(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedEntryToGetFourEntryByArrayOfEntryStatusId() throws Exception {
        int expected = 4;

        SelectEntryJoinDAO sut = new SelectEntryJoinDAO();
        EntrySelectJoinWhereActionForm whereForm = new EntrySelectJoinWhereActionForm();
        String[] entryStatusIdArray = {"1", "2", "4"};
        whereForm.setEntryStatusIdArray(entryStatusIdArray);
        List<EntryExtendActionForm> actual = sut.selectMatchedEntry(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedEntryToGetOneEntryByArrayOfEntrySchoolIdAndApplicantAccountIdAndEntryStatusId()
            throws Exception {
        int expected = 1;

        SelectEntryJoinDAO sut = new SelectEntryJoinDAO();
        EntrySelectJoinWhereActionForm whereForm = new EntrySelectJoinWhereActionForm();
        String[] EntrySchoolIdArray = {"6"};
        whereForm.setEntrySchoolIdArray(EntrySchoolIdArray);
        String[] applicantAccountIdArray = {"1"};
        whereForm.setApplicantAccountIdArray(applicantAccountIdArray);
        String[] entryStatusIdArray = {"2"};
        whereForm.setEntryStatusIdArray(entryStatusIdArray);
        List<EntryExtendActionForm> actual = sut.selectMatchedEntry(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedEntryToGetFourEntryByLikeEntrySchoolName() throws Exception {
        int expected = 4;

        SelectEntryJoinDAO sut = new SelectEntryJoinDAO();
        EntrySelectJoinWhereActionForm whereForm = new EntrySelectJoinWhereActionForm();
        whereForm.setLikeEntrySchoolName("テスト");
        List<EntryExtendActionForm> actual = sut.selectMatchedEntry(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedEntryToGetTwoEntryByLikeApplicantLastNameAndLikeApplicantLastNameKana()
            throws Exception {
        int expected = 2;

        SelectEntryJoinDAO sut = new SelectEntryJoinDAO();
        EntrySelectJoinWhereActionForm whereForm = new EntrySelectJoinWhereActionForm();
        whereForm.setLikeApplicantLastName("橋");
        whereForm.setLikeApplicantLastNameKana("ハシ");
        List<EntryExtendActionForm> actual = sut.selectMatchedEntry(whereForm);

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void testSelectMatchedEntryToGetFiveEntryByLikeApplicantFirstNameAndLikeApplicantFirstNameKana()
            throws Exception {
        int expected = 5;

        SelectEntryJoinDAO sut = new SelectEntryJoinDAO();
        EntrySelectJoinWhereActionForm whereForm = new EntrySelectJoinWhereActionForm();
        whereForm.setLikeApplicantFirstName("郎");
        whereForm.setLikeApplicantFirstNameKana("ロウ");
        List<EntryExtendActionForm> actual = sut.selectMatchedEntry(whereForm);

        assertThat(actual.size(), is(expected));
    }

}
