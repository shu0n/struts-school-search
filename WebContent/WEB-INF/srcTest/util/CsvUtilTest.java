package util;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static test.matcher.IsHttpResponseHeader.*;
import static test.matcher.IsListComposedStringArray.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;
import org.junit.Test;

import servletunit.HttpServletResponseSimulator;
import test.generator.GenerateFormFile;

public class CsvUtilTest {

    @Test
    public void testWriteCsv() throws Exception {
        String[] headerArray = new String[3];
        headerArray[0] = "A";
        headerArray[1] = "B";
        headerArray[2] = "C";

        List<String[]> dataArrayList = new ArrayList<>();
        String[] dataArray = new String[3];
        dataArray[0] = "a";
        dataArray[1] = "b";
        dataArray[2] = "c";
        dataArrayList.add(dataArray);

        String fileName = "writeCsv.csv";

        HttpServletResponse stub = new HttpServletResponseSimulator();

        CsvUtil sut = new CsvUtil();
        HttpServletResponse actual = sut.writeCsv(headerArray, dataArrayList, fileName, stub);

        assertThat(actual, is(contentOf("text/csv;charset=UTF8", fileName)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWriteCsvInCaseIlleagalDataList() throws Exception {
        String[] headerArray = {};

        List<String[]> dataArrayList = new ArrayList<>();
        String[] dataArray = {"1", "2", "3"};
        dataArrayList.add(dataArray);

        String fileName = "writeCsv.csv";

        HttpServletResponse stub = new HttpServletResponseSimulator();

        CsvUtil sut = new CsvUtil();
        sut.writeCsv(headerArray, dataArrayList, fileName, stub);
    }

    @Test
    public void testReadCsv() throws Exception {
        List<String[]> expected = new ArrayList<>();

        String[] headerArray = new String[3];
        headerArray[0] = "A";
        headerArray[1] = "B";
        headerArray[2] = "C";
        expected.add(headerArray);

        String[] dataArray = new String[3];
        dataArray[0] = "a";
        dataArray[1] = "b";
        dataArray[2] = "c";
        expected.add(dataArray);

        FormFile formFile = new GenerateFormFile(null, "util/CsvUtilTest.testReadCsv.csv", 0);

        CsvUtil sut = new CsvUtil();
        List<String[]> actual = sut.readCsv(formFile);

        assertThat(actual, is(sameComponentValueAs(expected)));
    }

}
