package test.struts;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StrutsTester {

    /**
     * プロパティ情報を取得するためのメソッド
     * @param String プロパティファイルのパス
     * @return Properties 取得したプロパティ情報
     */
    public static File getContectDir() {
        Properties props = new Properties();
        InputStream inputStream
                = StrutsTester.class.getClassLoader().getResourceAsStream("test/struts/struts.properties");
        try {
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new File(props.getProperty("context.dir"));
    }

}
