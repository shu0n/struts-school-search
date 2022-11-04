package test.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.struts.upload.FormFile;

public class GenerateFormFile implements FormFile {

    private final String fileName;
    private final String resourceName;
    private final int fileSize;

    public GenerateFormFile(String fileName, String resourceName, int fileSize) {
        this.fileName = fileName;
        this.resourceName = resourceName;
        this.fileSize = fileSize;
    }

    @Override
    public void destroy() {}

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public byte[] getFileData() throws FileNotFoundException, IOException {
        return null;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public int getFileSize() {
        return fileSize;
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException, IOException {
        if(resourceName == null) {
            return null;
        } else {
            // テスト用の画像ファイルの情報を取得する。
            String path = getClass().getClassLoader().
                    getResource(resourceName).getPath();
            File file = new File(path);

            return new java.io.FileInputStream(file);
        }
    }

    @Override
    public void setContentType(String arg0) {}

    @Override
    public void setFileName(String arg0) {}

    @Override
    public void setFileSize(int arg0) {}

    public FormFile generateFormFile(String fileName, String resourceName, int fileSize) {
        return new GenerateFormFile(fileName, resourceName, fileSize);
    }

}
