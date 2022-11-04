package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    /**
     * プロパティファイルを読み込むためのメソッド
     * @param propertyFilePath プロパティファイルのパス
     * @return プロパティ情報
     * @throws IOException
     */
    public Properties getProperty(String propertyFilePath) throws IOException {
        // プロパティ情報を扱うクラスのインスタンスを生成する。
        Properties props = new Properties();
        // プロパティファイル名をもとに内容を読み込む。
        InputStream inputStream = PropertyUtil.class.getClassLoader().getResourceAsStream(propertyFilePath);
        if(inputStream == null) {
            // 読み込んだオブジェクトがNULLの場合はI/O例外を投げる。
            throw new IOException();
        }
        // 読み込んだ内容をもとにプロパティ情報を生成する。
        props.load(inputStream);

        // 生成したプロパティ情報を戻す。
        return props;
    }

}
