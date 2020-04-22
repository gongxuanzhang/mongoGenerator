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

    /*** 数据库名解析
     * @author gxz
     * @param dbName 数据库名
     **/
    String dbNameHandler(String dbName);

    /*** 实体类解析
     * @author gxz
     * @param collectionName mongo的集合名
     **/
    String beanNameHandler(String collectionName);
}
