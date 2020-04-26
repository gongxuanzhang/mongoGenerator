package writer;

import common.util.PathUtil;
import model.GeneratorFileInfo;
import model.MongoDefinition;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class Writer {

    final private WriterSupport writerSupport;

    public Writer(WriterSupport writerSupport) {
        this.writerSupport = writerSupport;
    }

    public void generator(MongoDefinition mongoDefinition) throws IOException{
        List<GeneratorFileInfo> fileInfos = writerSupport.conformity(mongoDefinition);
        for (GeneratorFileInfo fileInfo : fileInfos) {
            File file = existsFilter(fileInfo);
            // existsFilter方法中已经确定返回的File一定不存在 所以不用根据返回值判断文件是否创建了
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(fileInfo.getContent());
            fileWriter.flush();
            fileWriter.close();
        }

    }

    /***
     * 保证创建的名字不重复，暂时顺延标号 以后会增加策略模式
     * @author gxz
     * @param
     * @param writtenWord
     **/
    public File existsFilter(GeneratorFileInfo writtenWord) throws IOException {
        String configPath = writtenWord.getFilePath();
        String fileName = writtenWord.getFileName();
        if (configPath.contains(".")) {
            configPath = configPath.replaceAll("\\.", "/");
        }
        String dirPath = PathUtil.getPathJava() + configPath;
        int i = 1;
        String javaPath;
        javaPath = dirPath + File.separator + fileName + ".java";
        File file;
        while ((file = new File(javaPath)).exists()) {
            String[] split = javaPath.split("\\.");
            javaPath = split[0] + i++ + "." + split[1];
        }
        return file;

    }
}
