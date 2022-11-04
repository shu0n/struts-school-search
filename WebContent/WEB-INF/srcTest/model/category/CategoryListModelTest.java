package model.category;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsForm.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import actionform.CategoryExtendActionForm;

public class CategoryListModelTest {

    @Test
    public void testSetListCategoryDatatoSetData() {
        List<CategoryExtendActionForm> expected = new ArrayList<>();
        CategoryExtendActionForm expectedForm1 = new CategoryExtendActionForm();
        expectedForm1.setCategoryId(1);
        expectedForm1.setCategoryStatus("0");
        expectedForm1.setCategoryStatusName("無効");
        expected.add(expectedForm1);
        CategoryExtendActionForm expectedForm2 = new CategoryExtendActionForm();
        expectedForm2.setCategoryId(1);
        expectedForm2.setCategoryStatus("1");
        expectedForm2.setCategoryStatusName("有効");
        expected.add(expectedForm2);
        CategoryExtendActionForm expectedForm3 = new CategoryExtendActionForm();
        expectedForm3.setCategoryId(1);
        expectedForm3.setCategoryStatus("0");
        expectedForm3.setCategoryStatusName("無効");
        expected.add(expectedForm3);

        CategoryListModel sut = new CategoryListModel();
        List<CategoryExtendActionForm> actual = new ArrayList<>();
        CategoryExtendActionForm actualForm1 = new CategoryExtendActionForm();
        actualForm1.setCategoryId(1);
        actualForm1.setCategoryStatus("0");
        expected.add(actualForm1);
        CategoryExtendActionForm actualForm2 = new CategoryExtendActionForm();
        actualForm2.setCategoryId(1);
        actualForm2.setCategoryStatus("1");
        expected.add(actualForm2);
        CategoryExtendActionForm actualForm3 = new CategoryExtendActionForm();
        actualForm3.setCategoryId(1);
        actualForm3.setCategoryStatus("0");
        expected.add(actualForm3);
        actual = sut.setListCategoryData(actual);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortCategoryExtendFormListToSortByDescendingCreatedAt() {
        List<CategoryExtendActionForm> expected = new ArrayList<>();
        CategoryExtendActionForm expectedForm1 = new CategoryExtendActionForm();
        expectedForm1.setCategoryId(2);
        expectedForm1.setCategoryCreatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        expected.add(expectedForm1);
        CategoryExtendActionForm expectedForm2 = new CategoryExtendActionForm();
        expectedForm2.setCategoryId(4);
        expectedForm2.setCategoryCreatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(expectedForm2);
        CategoryExtendActionForm expectedForm3 = new CategoryExtendActionForm();
        expectedForm3.setCategoryId(1);
        expectedForm3.setCategoryCreatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expected.add(expectedForm3);
        CategoryExtendActionForm expectedForm4 = new CategoryExtendActionForm();
        expectedForm4.setCategoryId(3);
        expectedForm4.setCategoryCreatedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(expectedForm4);

        CategoryListModel sut = new CategoryListModel();
        List<CategoryExtendActionForm> actual = new ArrayList<>();
        CategoryExtendActionForm actualForm1 = new CategoryExtendActionForm();
        actualForm1.setCategoryId(1);
        actualForm1.setCategoryCreatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        actual.add(actualForm1);
        CategoryExtendActionForm actualForm2 = new CategoryExtendActionForm();
        actualForm2.setCategoryId(2);
        actualForm2.setCategoryCreatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        actual.add(actualForm2);
        CategoryExtendActionForm actualForm3 = new CategoryExtendActionForm();
        actualForm3.setCategoryId(3);
        actualForm3.setCategoryCreatedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actual.add(actualForm3);
        CategoryExtendActionForm actualForm4 = new CategoryExtendActionForm();
        actualForm4.setCategoryId(4);
        actualForm4.setCategoryCreatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actual.add(actualForm4);
        actual = sut.sortCategoryExtendFormList(actual, "byDescendingCreatedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortCategoryExtendFormListToSortByAccendingCreatedAt() {
        List<CategoryExtendActionForm> expected = new ArrayList<>();
        CategoryExtendActionForm expectedForm1 = new CategoryExtendActionForm();
        expectedForm1.setCategoryId(3);
        expectedForm1.setCategoryCreatedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(expectedForm1);
        CategoryExtendActionForm expectedForm2 = new CategoryExtendActionForm();
        expectedForm2.setCategoryId(1);
        expectedForm2.setCategoryCreatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expected.add(expectedForm2);
        CategoryExtendActionForm expectedForm3 = new CategoryExtendActionForm();
        expectedForm3.setCategoryId(4);
        expectedForm3.setCategoryCreatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(expectedForm3);
        CategoryExtendActionForm expectedForm4 = new CategoryExtendActionForm();
        expectedForm4.setCategoryId(2);
        expectedForm4.setCategoryCreatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        expected.add(expectedForm4);

        CategoryListModel sut = new CategoryListModel();
        List<CategoryExtendActionForm> actual = new ArrayList<>();
        CategoryExtendActionForm actualForm1 = new CategoryExtendActionForm();
        actualForm1.setCategoryId(1);
        actualForm1.setCategoryCreatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        actual.add(actualForm1);
        CategoryExtendActionForm actualForm2 = new CategoryExtendActionForm();
        actualForm2.setCategoryId(2);
        actualForm2.setCategoryCreatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        actual.add(actualForm2);
        CategoryExtendActionForm actualForm3 = new CategoryExtendActionForm();
        actualForm3.setCategoryId(3);
        actualForm3.setCategoryCreatedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actual.add(actualForm3);
        CategoryExtendActionForm actualForm4 = new CategoryExtendActionForm();
        actualForm4.setCategoryId(4);
        actualForm4.setCategoryCreatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actual.add(actualForm4);
        actual = sut.sortCategoryExtendFormList(actual, "byAccendingCreatedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortCategoryExtendFormListToSortByDescendingUpdatedAt() {
        List<CategoryExtendActionForm> expected = new ArrayList<>();
        CategoryExtendActionForm expectedForm1 = new CategoryExtendActionForm();
        expectedForm1.setCategoryId(2);
        expectedForm1.setCategoryUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        expected.add(expectedForm1);
        CategoryExtendActionForm expectedForm2 = new CategoryExtendActionForm();
        expectedForm2.setCategoryId(4);
        expectedForm2.setCategoryUpdatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(expectedForm2);
        CategoryExtendActionForm expectedForm3 = new CategoryExtendActionForm();
        expectedForm3.setCategoryId(1);
        expectedForm3.setCategoryUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expected.add(expectedForm3);
        CategoryExtendActionForm expectedForm4 = new CategoryExtendActionForm();
        expectedForm4.setCategoryId(3);
        expectedForm4.setCategoryUpdatedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(expectedForm4);

        CategoryListModel sut = new CategoryListModel();
        List<CategoryExtendActionForm> actual = new ArrayList<>();
        CategoryExtendActionForm actualForm1 = new CategoryExtendActionForm();
        actualForm1.setCategoryId(1);
        actualForm1.setCategoryUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        actual.add(actualForm1);
        CategoryExtendActionForm actualForm2 = new CategoryExtendActionForm();
        actualForm2.setCategoryId(2);
        actualForm2.setCategoryUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        actual.add(actualForm2);
        CategoryExtendActionForm actualForm3 = new CategoryExtendActionForm();
        actualForm3.setCategoryId(3);
        actualForm3.setCategoryUpdatedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actual.add(actualForm3);
        CategoryExtendActionForm actualForm4 = new CategoryExtendActionForm();
        actualForm4.setCategoryId(4);
        actualForm4.setCategoryUpdatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actual.add(actualForm4);
        actual = sut.sortCategoryExtendFormList(actual, "byDescendingUpdatedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testSortCategoryExtendFormListToSortByAccendingUpdatedAt() {
        List<CategoryExtendActionForm> expected = new ArrayList<>();
        CategoryExtendActionForm expectedForm1 = new CategoryExtendActionForm();
        expectedForm1.setCategoryId(3);
        expectedForm1.setCategoryUpdatedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        expected.add(expectedForm1);
        CategoryExtendActionForm expectedForm2 = new CategoryExtendActionForm();
        expectedForm2.setCategoryId(1);
        expectedForm2.setCategoryUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        expected.add(expectedForm2);
        CategoryExtendActionForm expectedForm3 = new CategoryExtendActionForm();
        expectedForm3.setCategoryId(4);
        expectedForm3.setCategoryUpdatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        expected.add(expectedForm3);
        CategoryExtendActionForm expectedForm4 = new CategoryExtendActionForm();
        expectedForm4.setCategoryId(2);
        expectedForm4.setCategoryUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        expected.add(expectedForm4);

        CategoryListModel sut = new CategoryListModel();
        List<CategoryExtendActionForm> actual = new ArrayList<>();
        CategoryExtendActionForm actualForm1 = new CategoryExtendActionForm();
        actualForm1.setCategoryId(1);
        actualForm1.setCategoryUpdatedAt(Timestamp.valueOf("2022-01-03 04:05:06.789"));
        actual.add(actualForm1);
        CategoryExtendActionForm actualForm2 = new CategoryExtendActionForm();
        actualForm2.setCategoryId(2);
        actualForm2.setCategoryUpdatedAt(Timestamp.valueOf("2022-01-05 06:07:08.912"));
        actual.add(actualForm2);
        CategoryExtendActionForm actualForm3 = new CategoryExtendActionForm();
        actualForm3.setCategoryId(3);
        actualForm3.setCategoryUpdatedAt(Timestamp.valueOf("2022-01-02 03:04:05.678"));
        actual.add(actualForm3);
        CategoryExtendActionForm actualForm4 = new CategoryExtendActionForm();
        actualForm4.setCategoryId(4);
        actualForm4.setCategoryUpdatedAt(Timestamp.valueOf("2022-01-04 05:06:07.891"));
        actual.add(actualForm4);
        actual = sut.sortCategoryExtendFormList(actual, "byAccendingUpdatedAt");

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedCategoryListInCaseCurrentPageIsFirst() {
        List<CategoryExtendActionForm> expected = new ArrayList<>();
        CategoryExtendActionForm expectedForm1 = new CategoryExtendActionForm();
        expectedForm1.setCategoryId(1);
        expected.add(expectedForm1);
        CategoryExtendActionForm expectedForm2 = new CategoryExtendActionForm();
        expectedForm2.setCategoryId(2);
        expected.add(expectedForm2);
        CategoryExtendActionForm expectedForm3 = new CategoryExtendActionForm();
        expectedForm3.setCategoryId(3);
        expected.add(expectedForm3);
        CategoryExtendActionForm expectedForm4 = new CategoryExtendActionForm();
        expectedForm4.setCategoryId(4);
        expected.add(expectedForm4);
        CategoryExtendActionForm expectedForm5 = new CategoryExtendActionForm();
        expectedForm5.setCategoryId(5);
        expected.add(expectedForm5);
        CategoryExtendActionForm expectedForm6 = new CategoryExtendActionForm();
        expectedForm6.setCategoryId(6);
        expected.add(expectedForm6);
        CategoryExtendActionForm expectedForm7 = new CategoryExtendActionForm();
        expectedForm7.setCategoryId(7);
        expected.add(expectedForm7);
        CategoryExtendActionForm expectedForm8 = new CategoryExtendActionForm();
        expectedForm8.setCategoryId(8);
        expected.add(expectedForm8);
        CategoryExtendActionForm expectedForm9 = new CategoryExtendActionForm();
        expectedForm9.setCategoryId(9);
        expected.add(expectedForm9);
        CategoryExtendActionForm expectedForm10 = new CategoryExtendActionForm();
        expectedForm10.setCategoryId(10);
        expected.add(expectedForm10);

        CategoryListModel sut = new CategoryListModel();
        List<CategoryExtendActionForm> actual = new ArrayList<>();
        CategoryExtendActionForm actualForm1 = new CategoryExtendActionForm();
        actualForm1.setCategoryId(1);
        actual.add(actualForm1);
        CategoryExtendActionForm actualForm2 = new CategoryExtendActionForm();
        actualForm2.setCategoryId(2);
        actual.add(actualForm2);
        CategoryExtendActionForm actualForm3 = new CategoryExtendActionForm();
        actualForm3.setCategoryId(3);
        actual.add(actualForm3);
        CategoryExtendActionForm actualForm4 = new CategoryExtendActionForm();
        actualForm4.setCategoryId(4);
        actual.add(actualForm4);
        CategoryExtendActionForm actualForm5 = new CategoryExtendActionForm();
        actualForm5.setCategoryId(5);
        actual.add(actualForm5);
        CategoryExtendActionForm actualForm6 = new CategoryExtendActionForm();
        actualForm6.setCategoryId(6);
        actual.add(actualForm6);
        CategoryExtendActionForm actualForm7 = new CategoryExtendActionForm();
        actualForm7.setCategoryId(7);
        actual.add(actualForm7);
        CategoryExtendActionForm actualForm8 = new CategoryExtendActionForm();
        actualForm8.setCategoryId(8);
        actual.add(actualForm8);
        CategoryExtendActionForm actualForm9 = new CategoryExtendActionForm();
        actualForm9.setCategoryId(9);
        actual.add(actualForm9);
        CategoryExtendActionForm actualForm10 = new CategoryExtendActionForm();
        actualForm10.setCategoryId(10);
        actual.add(actualForm10);
        CategoryExtendActionForm actualForm11 = new CategoryExtendActionForm();
        actualForm11.setCategoryId(11);
        actual.add(actualForm11);
        actual = sut.makeDisplayedCategoryList(actual, 1, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedCategoryListInCaseCurrentPageIsSecond() {
        List<CategoryExtendActionForm> expected = new ArrayList<>();
        CategoryExtendActionForm expectedForm11 = new CategoryExtendActionForm();
        expectedForm11.setCategoryId(11);
        expected.add(expectedForm11);
        CategoryExtendActionForm expectedForm12 = new CategoryExtendActionForm();
        expectedForm12.setCategoryId(12);
        expected.add(expectedForm12);
        CategoryExtendActionForm expectedForm13 = new CategoryExtendActionForm();
        expectedForm13.setCategoryId(13);
        expected.add(expectedForm13);

        CategoryListModel sut = new CategoryListModel();
        List<CategoryExtendActionForm> actual = new ArrayList<>();
        CategoryExtendActionForm actualForm1 = new CategoryExtendActionForm();
        actualForm1.setCategoryId(1);
        actual.add(actualForm1);
        CategoryExtendActionForm actualForm2 = new CategoryExtendActionForm();
        actualForm2.setCategoryId(2);
        actual.add(actualForm2);
        CategoryExtendActionForm actualForm3 = new CategoryExtendActionForm();
        actualForm3.setCategoryId(3);
        actual.add(actualForm3);
        CategoryExtendActionForm actualForm4 = new CategoryExtendActionForm();
        actualForm4.setCategoryId(4);
        actual.add(actualForm4);
        CategoryExtendActionForm actualForm5 = new CategoryExtendActionForm();
        actualForm5.setCategoryId(5);
        actual.add(actualForm5);
        CategoryExtendActionForm actualForm6 = new CategoryExtendActionForm();
        actualForm6.setCategoryId(6);
        actual.add(actualForm6);
        CategoryExtendActionForm actualForm7 = new CategoryExtendActionForm();
        actualForm7.setCategoryId(7);
        actual.add(actualForm7);
        CategoryExtendActionForm actualForm8 = new CategoryExtendActionForm();
        actualForm8.setCategoryId(8);
        actual.add(actualForm8);
        CategoryExtendActionForm actualForm9 = new CategoryExtendActionForm();
        actualForm9.setCategoryId(9);
        actual.add(actualForm9);
        CategoryExtendActionForm actualForm10 = new CategoryExtendActionForm();
        actualForm10.setCategoryId(10);
        actual.add(actualForm10);
        CategoryExtendActionForm actualForm11 = new CategoryExtendActionForm();
        actualForm11.setCategoryId(11);
        actual.add(actualForm11);
        CategoryExtendActionForm actualForm12 = new CategoryExtendActionForm();
        actualForm12.setCategoryId(12);
        actual.add(actualForm12);
        CategoryExtendActionForm actualForm13 = new CategoryExtendActionForm();
        actualForm13.setCategoryId(13);
        actual.add(actualForm13);
        actual = sut.makeDisplayedCategoryList(actual, 2, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

    @Test
    public void testMakeDisplayedCategoryListInCaseCurrentPageIsSecondAndEmpty() {
        List<CategoryExtendActionForm> expected = new ArrayList<>();

        CategoryListModel sut = new CategoryListModel();
        List<CategoryExtendActionForm> actual = new ArrayList<>();
        CategoryExtendActionForm actualForm1 = new CategoryExtendActionForm();
        actualForm1.setCategoryId(1);
        actual.add(actualForm1);
        CategoryExtendActionForm actualForm2 = new CategoryExtendActionForm();
        actualForm2.setCategoryId(2);
        actual.add(actualForm2);
        CategoryExtendActionForm actualForm3 = new CategoryExtendActionForm();
        actualForm3.setCategoryId(3);
        actual.add(actualForm3);
        actual = sut.makeDisplayedCategoryList(actual, 2, 10);

        for(int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i), is(samePropertyValueAs(expected.get(i))));
        }
    }

}
