package writer;


import common.util.StringUtils;
import model.GeneratorFileInfo;
import model.MongoDefinition;
import model.Template;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gxz
 * @date 2020/4/22 21:12
 */
public class TemplateWriterSupport implements WriterSupport {


    private void importPackage(StringBuilder builder) {
        builder.append("import java.time.LocalDateTime;\r\n");
        builder.append("import java.util.List;\r\n");
        builder.append("import java.util.Map;\r\n");
        builder.append("import java.time.LocalDateTime;\r\n");
    }



    @Override
    public List<GeneratorFileInfo> conformity(MongoDefinition mongoDefinition) {
        List<Template> templates = mongoDefinition.getTemplate();
        List<GeneratorFileInfo> result = new ArrayList<>();
        for (Template template : templates) {
            GeneratorFileInfo info = new GeneratorFileInfo();
            StringBuilder builder = new StringBuilder();
            importPackage(builder);
            builder.append(template.getContent());
            info.setContent(builder.toString());
            info.setFilePath(template.getConfigPackage());
            info.setFileName(template.getName());
            result.add(info);
        }
        return result;
    }
}
