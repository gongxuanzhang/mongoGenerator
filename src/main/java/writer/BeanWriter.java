package writer;


import common.util.PathUtil;
import model.GeneratorFileInfo;
import model.MongoDefinition;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gxz
 * @date 2020/4/22 21:12
 */
public class BeanWriter implements Writer {


    @Override
    public void generator(List<MongoDefinition> mongoDefinitions) throws IOException {
        for (MongoDefinition mongoDefinition : mongoDefinitions) {
            List<GeneratorFileInfo> generatorFileInfo = writeAnalyze(mongoDefinition);
            for (GeneratorFileInfo fileInfo : generatorFileInfo) {
                int i = 1;
                String configPath = fileInfo.getFilePath();
                String javaPath = null;
                if(!configPath.contains(".java")){
                    configPath = configPath.replaceAll("\\.", "/");
                    String dirPath = PathUtil.getPathJava() + configPath;
                    javaPath = dirPath + File.separator + fileInfo.getFileName() + ".java";
                }else{
                    javaPath = configPath;
                }

                File file;
                while ((file = new File(javaPath)).exists()) {
                    String[] split = javaPath.split("\\.");
                    javaPath = split[0] + i++ + "." + split[1];
                }
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(fileInfo.getContent());
                fileWriter.flush();
                fileWriter.close();
            }
        }
    }

    private List<GeneratorFileInfo> writeAnalyze(MongoDefinition mongoDefinition) {
        List<GeneratorFileInfo> result = new ArrayList<>();
        //添加主bean
        result.add(primaryBeanAnalyze(mongoDefinition));
        result.addAll(childrenBeanAnalyze(mongoDefinition.getChild()));
        return result;
    }

    private GeneratorFileInfo primaryBeanAnalyze(MongoDefinition mongoDefinition) {
        GeneratorFileInfo primaryBean = new GeneratorFileInfo();
        primaryBean.setContent(primaryModel2Content(mongoDefinition));
        primaryBean.setFileName(mongoDefinition.getPropertyName());
        return primaryBean.setFilePath(mongoDefinition.getPath());
    }

    private List<GeneratorFileInfo> childrenBeanAnalyze(List<MongoDefinition> children) {
        List<GeneratorFileInfo> result = new ArrayList<>();
        for (MongoDefinition child : children) {
            GeneratorFileInfo primaryFile = new GeneratorFileInfo();
            if(child.hasChild()){
                primaryFile.setContent(primaryModel2Content(child));
                String dirPath = PathUtil.getPathJava();
                String javaPath = dirPath + child.getPropertyName() + ".java";
                primaryFile.setFileName(child.getPropertyName()).setFilePath(javaPath);
                result.add(primaryFile);
                result.addAll(childrenBeanAnalyze(child.getChild()));
            }

        }
        return result;
    }

    private String primaryModel2Content(MongoDefinition primaryModel) {
        StringBuilder builder = new StringBuilder();
        List<MongoDefinition> child = primaryModel.getChild();
        builder.append("package " + primaryModel.getPath() + ";\r\n");
        importPackage(builder);
        builder.append("public class ").append(primaryModel.getPropertyName()).append(" {\r\n");
        for (MongoDefinition mongoDefinition : child) {
            String type = typeString(mongoDefinition);
            builder.append("private " + type + mongoDefinition.getPropertyName() + ";\r\n");
        }
        builder.append("}");
        return builder.toString();
    }

    private void importPackage(StringBuilder builder) {
        builder.append("import java.time.LocalDateTime;\r\n");
        builder.append("import java.util.List;\r\n");
        builder.append("import java.util.Map;\r\n");
        builder.append("import java.time.LocalDateTime;\r\n");
    }

    private String typeString(MongoDefinition mongoDefinition) {
        StringBuilder typeStr = new StringBuilder();
        int type = mongoDefinition.getType();
        if (mongoDefinition.getArray()) {
            typeStr.append("List<");
            if (type == 2) {
                typeStr.append("String> ");
            } else if (type == 16) {
                typeStr.append("Integer> ");
            } else if (type == 18) {
                typeStr.append("Long> ");
            } else if (type == 8) {
                typeStr.append("Boolean> ");
            } else if (type == 9) {
                typeStr.append("LocalDateTime> ");
            } else {

            }

        } else {
            if (type == 2) {
                typeStr.append("String ");
            } else if (type == 16) {
                typeStr.append("Integer ");
            } else if (type == 18) {
                typeStr.append("Long ");
            } else if (type == 8) {
                typeStr.append("Boolean ");
            } else if (type == 9) {
                typeStr.append("LocalDateTime ");
            } else {

            }
        }
        return typeStr.toString();
    }

}
