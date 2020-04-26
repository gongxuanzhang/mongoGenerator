package writer;


import common.util.PathUtil;
import common.util.StringUtils;
import model.GeneratorFileInfo;
import model.MongoDefinition;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author gxz
 * @date 2020/4/22 21:12
 */
public class BeanWriterSupport implements WriterSupport {



    private GeneratorFileInfo primaryBeanAnalyze(MongoDefinition mongoDefinition) {
        String propertyName = mongoDefinition.getPropertyName();
        GeneratorFileInfo primaryBean = new GeneratorFileInfo();
        propertyName = StringUtils.upperCaseFirst(propertyName);
        primaryBean.setFileName(propertyName);
        primaryBean.setFilePath(mongoDefinition.getPath());
        return primaryBean.setContent(primaryModel2Content(mongoDefinition,true));
    }

    private List<GeneratorFileInfo> childrenBeanAnalyze(List<MongoDefinition> children) {
        List<GeneratorFileInfo> result = new ArrayList<>();
        for (MongoDefinition child : children) {
            GeneratorFileInfo primaryFile = new GeneratorFileInfo();
            if (child.hasChild()) {
                String filename = StringUtils.upperCaseFirst(child.getPropertyName())+child.getInfo().getBeanClose();
                primaryFile.setFileName(filename).setFilePath(child.getPath());
                primaryFile.setContent(primaryModel2Content(child,false));
                result.add(primaryFile);
                result.addAll(childrenBeanAnalyze(child.getChild()));
            }
        }
        return result;
    }

    /***
     * beanWriter把传过来的信息创建成GeneratorInfo
     * @author gxz
     * @param primaryModel 主题信息
     * @param primary 是不是顶级bean 如果是 命名结尾就不需要加后缀
     * @return java.lang.String
     **/
    private String primaryModel2Content(MongoDefinition primaryModel,boolean primary) {
        StringBuilder builder = new StringBuilder();
        List<MongoDefinition> child = primaryModel.getChild();
        builder.append("package " + primaryModel.getPath() + ";\r\n");
        builder.append("import "+primaryModel.getInnerPath()+".*;\r\n");
        importPackage(builder);
        String className = StringUtils.upperCaseFirst(primaryModel.getPropertyName());
        className +=primary?"":primaryModel.getInfo().getBeanClose();
        builder.append("public class ").append(className).append(" {\r\n");
        for (MongoDefinition mongoDefinition : child) {
            String type = typeString(mongoDefinition);
            String propertyName = mongoDefinition.getPropertyName();
            if(propertyName.contains(".")){
                int lastIndex = propertyName.lastIndexOf(".");
                propertyName = propertyName.substring(lastIndex+1);
            }
            builder.append("private " + type + propertyName + ";\r\n");
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
        int type = mongoDefinition.getType();
        String typeString;
        switch (type) {
            case 2:
                typeString = "String ";
                break;
            case 16:
                typeString = "Integer ";
                break;
            case 18:
                typeString = "Long ";
                break;
            case 8:
                typeString = "Boolean ";
                break;
            case 9:
                typeString = "LocalDateTime ";
                break;
            case 1:
                typeString = "Double ";
                break;
            default:
                String propertyName = mongoDefinition.getPropertyName();
                String beanClose = mongoDefinition.getInfo().getBeanClose();
                if (propertyName.contains(".")) {
                    int i = propertyName.lastIndexOf(".");
                    propertyName = propertyName.substring(i);
                }
                typeString = propertyName + beanClose;
                break;
        }
        typeString = StringUtils.upperCaseFirst(typeString);
        if (mongoDefinition.isArray()) {
            return "List<" + typeString + "> ";
        } else {
            return typeString + " ";
        }
    }

    @Override
    public List<GeneratorFileInfo> conformity(MongoDefinition mongoDefinition) {
        List<GeneratorFileInfo> result = new ArrayList<>();
        result.add(primaryBeanAnalyze(mongoDefinition));
        List<GeneratorFileInfo> fileInfos = childrenBeanAnalyze(mongoDefinition.getChild());
        result.addAll(fileInfos);
        return result;
    }
}
