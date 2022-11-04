package util;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static test.property.PropertyTester.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.struts.upload.FormFile;
import org.junit.After;
import org.junit.Test;

import test.generator.GenerateFormFile;

public class ImageFileUtilTest {

    private final List<String> fileNameList = new ArrayList<>();

    @After
    public void after() throws Exception {
        String modulePath = getProperty("util/ImageFileUtilTest.properties").getProperty("module.path");

        // テスト時に生成された画像ファイルを削除する。
        for(String fileName: fileNameList) {
            Path filePath = Paths.get(modulePath + fileName);
            Files.delete(filePath);
        }
    }

    @Test
    public void testGetImageMap() throws Exception {
        Map<String, String> expected = new HashMap<>();
        expected.put("fileName", "19700101090000000_123.png");
        expected.put("imgPath", "/WebContent/WEB-INF/propsTest/util/19700101090000000_123.png");

        ImageFileUtil sut = new ImageFileUtil() {
            Timestamp getCurrentTimestamp() {
                return Timestamp.valueOf("1970-01-01 09:00:00.000");
            }

            int getRandomInt() {
                return 123;
            }

            Properties getProperties() throws IOException {
                return getProperty("util/ImageFileUtilTest.properties");
            }
        };
        FormFile formFile = new GenerateFormFile(
                "ImageFileUtilTest.testGetImageMap.png",
                "util/ImageFileUtilTest.testGetImageMap.png",
                1187);
        Map<String, String> actual = sut.getImageMap(formFile);
        fileNameList.add(actual.get("imgPath"));

        assertThat(actual, is(expected));
    }

    @Test
    public void testGetImageMapWithoutFileSize() throws Exception {
        Map<String, String> expected = new HashMap<>();

        ImageFileUtil sut = new ImageFileUtil();
        FormFile formFile = new GenerateFormFile(null, null, 0);

        Map<String, String> actual = sut.getImageMap(formFile);

        assertThat(actual, is(expected));
    }

    @Test
    public void testGetImageFilePath() throws Exception {
        String expected = "/WebContent/WEB-INF/propsTest/util/testImage.png";

        ImageFileUtil sut = new ImageFileUtil() {
            Properties getProperties() throws IOException {
                return getProperty("util/ImageFileUtilTest.properties");
            }
        };
        String actual = sut.getImageFilePath("testImage.png");

        assertThat(actual, is(expected));
    }

}
