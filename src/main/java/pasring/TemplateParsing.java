package pasring;


import common.util.CollectionUtils;
import model.MongoDefinition;
import model.Template;
import model.mongo.CollectionNode;

import java.util.List;

/**
 * 模板解析接口  可自定义实现
 * @author gxz
 * @email gxz
 */
public interface TemplateParsing {

    String analyzeContent(String content, CollectionNode collectionNode);

    default void  fillTemplate(List<MongoDefinition> mongoDefinitions){
        for (MongoDefinition mongoDefinition : mongoDefinitions) {
            List<Template> templates = mongoDefinition.getTemplate();
            if (CollectionUtils.isNotEmpty(templates)) {
                for (Template template : templates) {
                    String original = template.getContent();
                    String escape = analyzeContent(original, mongoDefinition.getInfo());
                    template.setContent(escape);
                }
            }
        }
    }

}
