package model.school;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsListComposedLabelValueBean.*;
import static test.matcher.IsMap.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;
import org.junit.Test;

public class SchoolFormPartsModelTest {

    @Test
    public void testGetRegistrantKindMapToGetMap() {
        Map<String, String> expected = new TreeMap<>();
        expected.put("admin", "管理者として登録");
        expected.put("account", "選択するアカウントとして登録");

        SchoolFormPartsModel sut = new SchoolFormPartsModel();
        Map<String, String> actual = sut.getRegistrantKindMap();

        assertThat(actual, is(sameEntrySetAs(expected)));
    }

    @Test
    public void testGetReleaseProprietyMapToGetMap() {
        Map<String, String> expected = new TreeMap<>();
        expected.put("0", "不可");
        expected.put("1", "可");

        SchoolFormPartsModel sut = new SchoolFormPartsModel();
        Map<String, String> actual = sut.getReleaseProprietyMap();

        assertThat(actual, is(sameEntrySetAs(expected)));
    }

    @Test
    public void testGetReleaseProprietyListToGetList() {
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("不可", "0"));
        expected.add(new LabelValueBean("可", "1"));

        SchoolFormPartsModel sut = new SchoolFormPartsModel();
        List<LabelValueBean> actual = sut.getReleaseProprietyList();

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

    @Test
    public void testGetEntryProprietyMapToGetMap() {
        Map<String, String> expected = new TreeMap<>();
        expected.put("0", "不可");
        expected.put("1", "可");

        SchoolFormPartsModel sut = new SchoolFormPartsModel();
        Map<String, String> actual = sut.getEntryProprietyMap();

        assertThat(actual, is(sameEntrySetAs(expected)));
    }

    @Test
    public void testGetEntryProprietyListToGetList() {
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("不可", "0"));
        expected.add(new LabelValueBean("可", "1"));

        SchoolFormPartsModel sut = new SchoolFormPartsModel();
        List<LabelValueBean> actual = sut.getEntryProprietyList();

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

    @Test
    public void testGetSortKindForSchoolMapToGetMap() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("byDescendingRegisteredAt", "登録日が新しい順");
        expected.put("byAccendingRegisteredAt", "登録日が古い順");
        expected.put("byDescendingUpdatedAt", "更新日が新しい順");
        expected.put("byAccendingUpdatedAt", "更新日が古い順");
        expected.put("byDescendingSchoolFee", "費用の高い順");
        expected.put("byAccendingSchoolFee", "費用の安い順");

        SchoolFormPartsModel sut = new SchoolFormPartsModel();
        Map<String, String> actual = sut.getSortKindForSchoolMap();

        assertThat(actual, is(sameEntrySetAs(expected)));
    }

    @Test
    public void testGetSortKindForSchoolListWithEmptyValueToGetList() {
        List<LabelValueBean> expected = new ArrayList<>();
        expected.add(new LabelValueBean("選択する", ""));
        expected.add(new LabelValueBean("登録日が新しい順", "byDescendingRegisteredAt"));
        expected.add(new LabelValueBean("登録日が古い順", "byAccendingRegisteredAt"));
        expected.add(new LabelValueBean("更新日が新しい順", "byDescendingUpdatedAt"));
        expected.add(new LabelValueBean("更新日が古い順", "byAccendingUpdatedAt"));
        expected.add(new LabelValueBean("費用の高い順", "byDescendingSchoolFee"));
        expected.add(new LabelValueBean("費用の安い順", "byAccendingSchoolFee"));

        SchoolFormPartsModel sut = new SchoolFormPartsModel();
        List<LabelValueBean> actual = sut.getSortKindForSchoolListWithEmptyValue();

        assertThat(actual, is(sameComponentLavelAndValueAs(expected)));
    }

}
