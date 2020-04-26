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
@FunctionalInterface
public interface TemplateParsing {

    String analyzeContent(String content, CollectionNode collectionNode);

    /**
     * 默认实现方法 根据不同analyzeContent的实现而改变
     * 可以算是模板方法
     * @param mongoDefinitions
     */
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
