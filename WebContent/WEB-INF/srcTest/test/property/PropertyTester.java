package test.property;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyTester {

    /**
     * プロパティ情報を取得するためのメソッド
     * @param String プロパティファイルのパス
     * @return Properties 取得したプロパティ情報
     */
    public static Properties getProperty(String propertyFilePath) {
        Properties props = new Properties();
        InputStream inputStream = PropertyTester.class.getClassLoader().getResourceAsStream(propertyFilePath);
        try {
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return props;
    }

}
