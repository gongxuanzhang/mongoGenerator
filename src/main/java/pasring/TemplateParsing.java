package pasring;


import model.Template;
import model.mongo.MongoCollection;

/**
 * 模板解析接口  可自定义实现
 * @author gxz
 * @email gxz
 */
public interface TemplateParsing {

    String analyzeContent(String content, MongoCollection mongoCollection);

    String analyzeContent(Template template, MongoCollection mongoCollection);

}
