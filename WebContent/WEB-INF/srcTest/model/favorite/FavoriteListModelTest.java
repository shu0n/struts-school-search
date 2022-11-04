package model.favorite;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import actionform.FavoriteExtendActionForm;

public class FavoriteListModelTest {

    @Test
    public void testSetListFavoriteDataToSetData() {
        List<FavoriteExtendActionForm> expected = new ArrayList<>();
        FavoriteExtendActionForm expectedForm1 = new FavoriteExtendActionForm();
        expectedForm1.setFavoriteId(1);
        expectedForm1.setFavoriteAddedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expectedForm1.setStrFavoriteAddedAt("2022/01/02 03:04:05");
        expected.add(expectedForm1);
        FavoriteExtendActionForm expectedForm2 = new FavoriteExtendActionForm();
        expectedForm2.setFavoriteId(2);
        expectedForm2.setFavoriteAddedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expectedForm2.setStrFavoriteAddedAt("2022/01/03 04:05:06");
        expected.add(expectedForm2);
        FavoriteExtendActionForm expectedForm3 = new FavoriteExtendActionForm();
        expectedForm3.setFavoriteId(3);
        expectedForm3.setFavoriteAddedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expectedForm3.setStrFavoriteAddedAt("2022/01/04 05:06:07");
        expected.add(expectedForm3);

        FavoriteListModel sut = new FavoriteListModel();
        List<FavoriteExtendActionForm> actual = new ArrayList<>();
        FavoriteExtendActionForm actualForm1 = new FavoriteExtendActionForm();
        actualForm1.setFavoriteId(1);
        actualForm1.setFavoriteAddedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(actualForm1);
        FavoriteExtendActionForm actualForm2 = new FavoriteExtendActionForm();
        actualForm2.setFavoriteId(2);
        actualForm2.setFavoriteAddedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expected.add(actualForm2);
        FavoriteExtendActionForm actualForm3 = new FavoriteExtendActionForm();
        actualForm3.setFavoriteId(3);
        actualForm3.setFavoriteAddedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(actualForm3);
        actual = sut.setListFavoriteData(actual);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortFavoriteExtendFormListToSortByDescendingAddedAt() {
        List<FavoriteExtendActionForm> expected = new ArrayList<>();
        FavoriteExtendActionForm expectedForm1 = new FavoriteExtendActionForm();
        expectedForm1.setFavoriteId(2);
        expectedForm1.setFavoriteAddedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        expected.add(expectedForm1);
        FavoriteExtendActionForm expectedForm2 = new FavoriteExtendActionForm();
        expectedForm2.setFavoriteId(4);
        expectedForm2.setFavoriteAddedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(expectedForm2);
        FavoriteExtendActionForm expectedForm3 = new FavoriteExtendActionForm();
        expectedForm3.setFavoriteId(1);
        expectedForm3.setFavoriteAddedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expected.add(expectedForm3);
        FavoriteExtendActionForm expectedForm4 = new FavoriteExtendActionForm();
        expectedForm4.setFavoriteId(3);
        expectedForm4.setFavoriteAddedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(expectedForm4);

        FavoriteListModel sut = new FavoriteListModel();
        List<FavoriteExtendActionForm> actual = new ArrayList<>();
        FavoriteExtendActionForm actualForm1 = new FavoriteExtendActionForm();
        actualForm1.setFavoriteId(1);
        actualForm1.setFavoriteAddedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        actual.add(actualForm1);
        FavoriteExtendActionForm actualForm2 = new FavoriteExtendActionForm();
        actualForm2.setFavoriteId(2);
        actualForm2.setFavoriteAddedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        actual.add(actualForm2);
        FavoriteExtendActionForm actualForm3 = new FavoriteExtendActionForm();
        actualForm3.setFavoriteId(3);
        actualForm3.setFavoriteAddedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actual.add(actualForm3);
        FavoriteExtendActionForm actualForm4 = new FavoriteExtendActionForm();
        actualForm4.setFavoriteId(4);
        actualForm4.setFavoriteAddedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actual.add(actualForm4);
        actual = sut.sortFavoriteExtendFormList(actual, "byDescendingAddedAt");

        for(int i = 0; i< actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortFavoriteExtendFormListToSortByAccendingAddedAt() {
        List<FavoriteExtendActionForm> expected = new ArrayList<>();
        FavoriteExtendActionForm expectedForm1 = new FavoriteExtendActionForm();
        expectedForm1.setFavoriteId(3);
        expectedForm1.setFavoriteAddedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(expectedForm1);
        FavoriteExtendActionForm expectedForm2 = new FavoriteExtendActionForm();
        expectedForm2.setFavoriteId(1);
        expectedForm2.setFavoriteAddedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expected.add(expectedForm2);
        FavoriteExtendActionForm expectedForm3 = new FavoriteExtendActionForm();
        expectedForm3.setFavoriteId(4);
        expectedForm3.setFavoriteAddedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(expectedForm3);
        FavoriteExtendActionForm expectedForm4 = new FavoriteExtendActionForm();
        expectedForm4.setFavoriteId(2);
        expectedForm4.setFavoriteAddedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        expected.add(expectedForm4);

        FavoriteListModel sut = new FavoriteListModel();
        List<FavoriteExtendActionForm> actual = new ArrayList<>();
        FavoriteExtendActionForm actualForm1 = new FavoriteExtendActionForm();
        actualForm1.setFavoriteId(1);
        actualForm1.setFavoriteAddedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        actual.add(actualForm1);
        FavoriteExtendActionForm actualForm2 = new FavoriteExtendActionForm();
        actualForm2.setFavoriteId(2);
        actualForm2.setFavoriteAddedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        actual.add(actualForm2);
        FavoriteExtendActionForm actualForm3 = new FavoriteExtendActionForm();
        actualForm3.setFavoriteId(3);
        actualForm3.setFavoriteAddedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actual.add(actualForm3);
        FavoriteExtendActionForm actualForm4 = new FavoriteExtendActionForm();
        actualForm4.setFavoriteId(4);
        actualForm4.setFavoriteAddedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actual.add(actualForm4);
        actual = sut.sortFavoriteExtendFormList(actual, "byAccendingAddedAt");

        for(int i = 0; i< actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedFavoriteListInCaseCurrentPageIsFirst() throws Exception {
        List<FavoriteExtendActionForm> expected = new ArrayList<>();
        FavoriteExtendActionForm expectedForm1 = new FavoriteExtendActionForm();
        expectedForm1.setFavoriteId(1);
        expectedForm1.setSummaryImageFileName("image1.png");
        expectedForm1.setSummaryImageFilePath("/img/image1.png");
        expected.add(expectedForm1);
        FavoriteExtendActionForm expectedForm2 = new FavoriteExtendActionForm();
        expectedForm2.setFavoriteId(2);
        expectedForm2.setSummaryImageFileName("image2.png");
        expectedForm2.setSummaryImageFilePath("/img/image2.png");
        expected.add(expectedForm2);
        FavoriteExtendActionForm expectedForm3 = new FavoriteExtendActionForm();
        expectedForm3.setFavoriteId(3);
        expectedForm3.setSummaryImageFileName("");
        expectedForm3.setSummaryImageFilePath(null);
        expected.add(expectedForm3);
        FavoriteExtendActionForm expectedForm4 = new FavoriteExtendActionForm();
        expectedForm4.setFavoriteId(4);
        expectedForm4.setSummaryImageFileName(null);
        expectedForm4.setSummaryImageFilePath(null);
        expected.add(expectedForm4);
        FavoriteExtendActionForm expectedForm5 = new FavoriteExtendActionForm();
        expectedForm5.setFavoriteId(5);
        expectedForm5.setSummaryImageFileName(null);
        expectedForm5.setSummaryImageFilePath(null);
        expected.add(expectedForm5);
        FavoriteExtendActionForm expectedForm6 = new FavoriteExtendActionForm();
        expectedForm6.setFavoriteId(6);
        expectedForm6.setSummaryImageFileName("image6.png");
        expectedForm6.setSummaryImageFilePath("/img/image6.png");
        expected.add(expectedForm6);
        FavoriteExtendActionForm expectedForm7 = new FavoriteExtendActionForm();
        expectedForm7.setFavoriteId(7);
        expectedForm7.setSummaryImageFileName("");
        expectedForm7.setSummaryImageFilePath(null);
        expected.add(expectedForm7);
        FavoriteExtendActionForm expectedForm8 = new FavoriteExtendActionForm();
        expectedForm8.setFavoriteId(8);
        expectedForm8.setSummaryImageFileName("");
        expectedForm8.setSummaryImageFilePath(null);
        expected.add(expectedForm8);
        FavoriteExtendActionForm expectedForm9 = new FavoriteExtendActionForm();
        expectedForm9.setFavoriteId(9);
        expectedForm9.setSummaryImageFileName(null);
        expectedForm9.setSummaryImageFilePath(null);
        expected.add(expectedForm9);
        FavoriteExtendActionForm expectedForm10 = new FavoriteExtendActionForm();
        expectedForm10.setFavoriteId(10);
        expectedForm10.setSummaryImageFileName(null);
        expectedForm10.setSummaryImageFilePath(null);
        expected.add(expectedForm10);

        FavoriteListModel sut = new FavoriteListModel();
        List<FavoriteExtendActionForm> actual = new ArrayList<>();
        FavoriteExtendActionForm actualForm1 = new FavoriteExtendActionForm();
        actualForm1.setFavoriteId(1);
        actualForm1.setSummaryImageFileName("image1.png");
        actual.add(actualForm1);
        FavoriteExtendActionForm actualForm2 = new FavoriteExtendActionForm();
        actualForm2.setFavoriteId(2);
        actualForm2.setSummaryImageFileName("image2.png");
        actual.add(actualForm2);
        FavoriteExtendActionForm actualForm3 = new FavoriteExtendActionForm();
        actualForm3.setFavoriteId(3);
        actualForm3.setSummaryImageFileName("");
        actual.add(actualForm3);
        FavoriteExtendActionForm actualForm4 = new FavoriteExtendActionForm();
        actualForm4.setFavoriteId(4);
        actualForm4.setSummaryImageFileName(null);
        actual.add(actualForm4);
        FavoriteExtendActionForm actualForm5 = new FavoriteExtendActionForm();
        actualForm5.setFavoriteId(5);
        actualForm5.setSummaryImageFileName(null);
        actual.add(actualForm5);
        FavoriteExtendActionForm actualForm6 = new FavoriteExtendActionForm();
        actualForm6.setFavoriteId(6);
        actualForm6.setSummaryImageFileName("image6.png");
        actual.add(actualForm6);
        FavoriteExtendActionForm actualForm7 = new FavoriteExtendActionForm();
        actualForm7.setFavoriteId(7);
        actualForm7.setSummaryImageFileName("");
        actual.add(actualForm7);
        FavoriteExtendActionForm actualForm8 = new FavoriteExtendActionForm();
        actualForm8.setFavoriteId(8);
        actualForm8.setSummaryImageFileName("");
        actual.add(actualForm8);
        FavoriteExtendActionForm actualForm9 = new FavoriteExtendActionForm();
        actualForm9.setFavoriteId(9);
        actualForm9.setSummaryImageFileName(null);
        actual.add(actualForm9);
        FavoriteExtendActionForm actualForm10 = new FavoriteExtendActionForm();
        actualForm10.setFavoriteId(10);
        actualForm10.setSummaryImageFileName(null);
        actual.add(actualForm10);
        FavoriteExtendActionForm actualForm11 = new FavoriteExtendActionForm();
        actualForm11.setFavoriteId(11);
        actualForm11.setSummaryImageFileName(null);
        actual.add(actualForm11);
        actual = sut.makeDisplayedFavoriteList(actual, 1, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedFavoriteListInCaseCurrentPageIsSecond() throws Exception {
        List<FavoriteExtendActionForm> expected = new ArrayList<>();
        FavoriteExtendActionForm expectedForm11 = new FavoriteExtendActionForm();
        expectedForm11.setFavoriteId(11);
        expectedForm11.setSummaryImageFileName(null);
        expectedForm11.setSummaryImageFilePath(null);
        expected.add(expectedForm11);
        FavoriteExtendActionForm expectedForm12 = new FavoriteExtendActionForm();
        expectedForm12.setFavoriteId(12);
        expectedForm12.setSummaryImageFileName("image12.png");
        expectedForm12.setSummaryImageFilePath("/img/image12.png");
        expected.add(expectedForm12);
        FavoriteExtendActionForm expectedForm13 = new FavoriteExtendActionForm();
        expectedForm13.setFavoriteId(13);
        expectedForm13.setSummaryImageFileName("");
        expectedForm13.setSummaryImageFilePath(null);
        expected.add(expectedForm13);

        FavoriteListModel sut = new FavoriteListModel();
        List<FavoriteExtendActionForm> actual = new ArrayList<>();
        FavoriteExtendActionForm actualForm1 = new FavoriteExtendActionForm();
        actualForm1.setFavoriteId(1);
        actualForm1.setSummaryImageFileName("image1.png");
        actual.add(actualForm1);
        FavoriteExtendActionForm actualForm2 = new FavoriteExtendActionForm();
        actualForm2.setFavoriteId(2);
        actualForm2.setSummaryImageFileName("image2.png");
        actual.add(actualForm2);
        FavoriteExtendActionForm actualForm3 = new FavoriteExtendActionForm();
        actualForm3.setFavoriteId(3);
        actualForm3.setSummaryImageFileName("");
        actual.add(actualForm3);
        FavoriteExtendActionForm actualForm4 = new FavoriteExtendActionForm();
        actualForm4.setFavoriteId(4);
        actualForm4.setSummaryImageFileName(null);
        actual.add(actualForm4);
        FavoriteExtendActionForm actualForm5 = new FavoriteExtendActionForm();
        actualForm5.setFavoriteId(5);
        actualForm5.setSummaryImageFileName(null);
        actual.add(actualForm5);
        FavoriteExtendActionForm actualForm6 = new FavoriteExtendActionForm();
        actualForm6.setFavoriteId(6);
        actualForm6.setSummaryImageFileName("image6.png");
        actual.add(actualForm6);
        FavoriteExtendActionForm actualForm7 = new FavoriteExtendActionForm();
        actualForm7.setFavoriteId(7);
        actualForm7.setSummaryImageFileName("");
        actual.add(actualForm7);
        FavoriteExtendActionForm actualForm8 = new FavoriteExtendActionForm();
        actualForm8.setFavoriteId(8);
        actualForm8.setSummaryImageFileName("");
        actual.add(actualForm8);
        FavoriteExtendActionForm actualForm9 = new FavoriteExtendActionForm();
        actualForm9.setFavoriteId(9);
        actualForm9.setSummaryImageFileName(null);
        actual.add(actualForm9);
        FavoriteExtendActionForm actualForm10 = new FavoriteExtendActionForm();
        actualForm10.setFavoriteId(10);
        actualForm10.setSummaryImageFileName(null);
        actual.add(actualForm10);
        FavoriteExtendActionForm actualForm11 = new FavoriteExtendActionForm();
        actualForm11.setFavoriteId(11);
        actualForm11.setSummaryImageFileName(null);
        actual.add(actualForm11);
        FavoriteExtendActionForm actualForm12 = new FavoriteExtendActionForm();
        actualForm12.setFavoriteId(12);
        actualForm12.setSummaryImageFileName("image12.png");
        actual.add(actualForm12);
        FavoriteExtendActionForm actualForm13 = new FavoriteExtendActionForm();
        actualForm13.setFavoriteId(13);
        actualForm13.setSummaryImageFileName("");
        actual.add(actualForm13);
        actual = sut.makeDisplayedFavoriteList(actual, 2, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedFavoriteListInCaseCurrentPageIsSecondAndEmpty() throws Exception {
        List<FavoriteExtendActionForm> expected = new ArrayList<>();

        FavoriteListModel sut = new FavoriteListModel();
        List<FavoriteExtendActionForm> actual = new ArrayList<>();
        FavoriteExtendActionForm actualForm1 = new FavoriteExtendActionForm();
        actualForm1.setFavoriteId(1);
        actualForm1.setSummaryImageFileName("image1.png");
        actual.add(actualForm1);
        FavoriteExtendActionForm actualForm2 = new FavoriteExtendActionForm();
        actualForm2.setFavoriteId(2);
        actualForm2.setSummaryImageFileName("image2.png");
        actual.add(actualForm2);
        FavoriteExtendActionForm actualForm3 = new FavoriteExtendActionForm();
        actualForm3.setFavoriteId(3);
        actualForm3.setSummaryImageFileName("");
        actual.add(actualForm3);
        actual = sut.makeDisplayedFavoriteList(actual, 2, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

}
