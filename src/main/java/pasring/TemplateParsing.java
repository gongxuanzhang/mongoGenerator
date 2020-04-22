package pasring;


import model.Template;
import model.mongo.GeneratorMongoCollection;

/**
 * 模板解析接口  可自定义实现
 * @author gxz
 * @email gxz
 */
public interface TemplateParsing {

    String analyzeContent(String content, GeneratorMongoCollection generatorMongoCollection);

}
