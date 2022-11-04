package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FilenameUtils;
import org.apache.struts.upload.FormFile;

public class ImageFileUtil {

    /**
     * アップロードされた画像からファイル名と格納先パスを格納したマップを取得するためのメソッド
     * @param uploadFile アップロードファイル
     * @return マップ(ファイル名、格納先パス)
     * @throws IOException
     */
    public Map<String, String> getImageMap(FormFile uploadFile) throws IOException {
        try {
            // アップロード画像のファイル名から拡張子を取得する。
            String extension = FilenameUtils.getExtension(uploadFile.getFileName());
            // 現時点のタイムスタンプ、乱数、取得した拡張子を結合してファイル名を生成する。
            String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(getCurrentTimestamp())
                    + "_" + String.valueOf(getRandomInt())
                    + "." + extension;
            // アップロード画像のファイルサイズを取得する。
            int fileSize = uploadFile.getFileSize();
            // プロパティ情報からモジュールの配置先パスを取得する。
            String modulePath = getProperties().getProperty("module.path");
            // プロパティ情報から画像の配置先パスを取得する。
            String imagePath = getProperties().getProperty("img.path");
            // ファイル名と格納先パスを格納するためのマップを生成する。
            Map<String, String> imageMap = new HashMap<>();
            if (fileSize <= 0) {
                // ファイルサイズが0以下の場合(ファイルが存在しない場合)はマップを戻す。
                return imageMap;
            } else {
                // 上記以外の場合

                // アップロード画像を読み込む。
                BufferedInputStream inBuffer = new BufferedInputStream(uploadFile.getInputStream());
                // 読み込んだ中身を出力するファイルを指定する。
                BufferedOutputStream outBuffer =
                        new BufferedOutputStream(new FileOutputStream(modulePath + imagePath + fileName));
                byte[] data = new byte[1024];
                int bytes;
                while ((bytes = inBuffer.read(data)) != -1) {
                    // 指定したファイルに読み込んだ中身を書き込む。
                    outBuffer.write(data, 0, bytes);
                }
                // 入出力ストリームを閉じる。
                outBuffer.flush();
                outBuffer.close();
                inBuffer.close();

                // アップロード画像を表示するための格納先パスを生成する。
                String imgPath = imagePath + fileName;

                // ファイル名とパスをマップに格納する。
                imageMap.put("fileName", fileName);
                imageMap.put("imgPath", imgPath);

                // ファイル名と格納先パスを格納したマップを戻す。
                return imageMap;
            }
        } finally {
                // 一時ディレクトリに保存されたアップロード画像を削除する。
                uploadFile.destroy();
        }
    }

    /**
     * ファイル名をもとに画像のパスを生成するためのメソッド
     * @param imageFileName ファイル名
     * @return 画像パス
     * @throws IOException
     */
    public String getImageFilePath(String imageFileName) throws IOException {
        // プロパティ情報から画像の配置先パスを取得する。
        String imagePath = getProperties().getProperty("img.path");
        // 画像のパスを生成する。
        String imageFilePath = imagePath + imageFileName;
        // 生成したパスを戻す。
        return imageFilePath;
    }

    /**
     * 現在日時(日時型)を取得するためのメソッド
     */
    Timestamp getCurrentTimestamp() {
        // 現在日時(日時型)を戻す。
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 0〜999の範囲からランダムな整数を取得するためのメソッド
     */
    int getRandomInt() {
        // 0〜999の範囲からランダムな整数を戻す。
        return new Random().nextInt(1000);
    }

    /**
     * プロパティ情報を取得するためのメソッド
     */
    Properties getProperties() throws IOException {
        // プロパティ情報を戻す。
        return new PropertyUtil().getProperty("environment.properties");
    }

}
