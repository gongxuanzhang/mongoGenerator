package model;

/**
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class GeneratorFileInfo {
    private String filePath;
    private String fileName;
    private String content;


    public String getFileName() {
        return fileName;
    }

    public GeneratorFileInfo setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public GeneratorFileInfo setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String getContent() {
        return content;
    }

    public GeneratorFileInfo setContent(String content) {
        this.content = content;
        return this;
    }
}
