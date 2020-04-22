package common.util;


import model.GeneratorFileInfo;
import model.GeneratorModel;
import model.Template;
import pasring.TemplateParsing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gxz
 * @date 2020/4/22 21:12
 */
public class Writer {


    public static void generator(List<GeneratorModel> generatorModels) throws IOException {
        for (GeneratorModel generatorModel : generatorModels) {
            List<GeneratorFileInfo> generatorFileInfo = writeAnalyze(generatorModel);
            for (GeneratorFileInfo fileInfo : generatorFileInfo) {
                int i = 1;
                String filePath = fileInfo.getFilePath();
                File file = new File(filePath);
                if(file.exists()){
                    while (!(file = new File(filePath)).exists()){
                        String[] split = filePath.split("\\.");
                        filePath = split[0]+ i++ +split[1];
                    }
                }
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(fileInfo.getContent());
                fileWriter.flush();
                fileWriter.close();
            }
        }
    }

    private static List<GeneratorFileInfo> writeAnalyze(GeneratorModel generatorModel){
        List<Template> templates = generatorModel.getTemplate();
        List<GeneratorModel> child = generatorModel.getChild();

        return null;
    }
    private static GeneratorFileInfo primaryBeanAnalyze(GeneratorModel generatorModel){
        String propertyName = generatorModel.getPropertyName();
        List<Template> template = generatorModel.getTemplate();
        return null;
    }
    private static List<GeneratorFileInfo> childrenBeanAnalyze(List<GeneratorModel> children){
        List<GeneratorFileInfo> result = new ArrayList<>();
        for (GeneratorModel child : children) {
            GeneratorFileInfo primaryFile = new GeneratorFileInfo();
            String content = primaryModel2Content(child);
            System.out.println(content);
        }
        return null;
    }

    private static String primaryModel2Content(GeneratorModel primaryModel){
        StringBuilder builder = new StringBuilder();
        List<GeneratorModel> child = primaryModel.getChild();
        builder.append("package "+primaryModel.getPath()+"\r\n");
        importPackage(builder);
        builder.append("public class ").append(primaryModel.getPropertyName()).append(" {");
        for (GeneratorModel generatorModel : child) {
            String type = typeString(generatorModel);
            builder.append("private "+type+" "+generatorModel.getPropertyName()+"\r\n");
        }
        builder.append("}");
        return builder.toString();
    }

    private static void importPackage(StringBuilder builder){
        builder.append("import java.time.LocalDateTime;\r\n");
        builder.append("import java.util.List;\r\n");
        builder.append("import java.util.Map;\r\n");
        builder.append("import java.time.LocalDateTime;\r\n");
    }

    private static String typeString(GeneratorModel generatorModel){
        StringBuilder typeStr = new StringBuilder();
        int type =  generatorModel.getType();
        if (generatorModel.getArray()) {
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
