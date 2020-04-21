package pasring;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import common.exception.XMLConfigException;
import common.factory.ElementParsingFactory;
import common.util.AssertUtils;
import common.util.CollectionUtils;
import common.util.XMLUtils;
import model.Template;
import model.mongo.MongoCollection;
import model.mongo.MongoConnection;
import org.dom4j.Document;
import org.dom4j.Element;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库解析器
 *
 * @author: gxz
 * @date: 2020/4/20 10:07
 **/
public class DataBaseParsing {
    private static final String CONFIG_PATH = "aaa.xml";

    public static void main(String[] args) {
        List<ServerAddress> adds = new ArrayList<>();
//ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress("172.16.1.102", 27017);
        adds.add(serverAddress);

        List<MongoCredential> credentials = new ArrayList<>();
//MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential("tincery", "secret_reconnaissance_pro", "Txy123.com".toCharArray());
        credentials.add(mongoCredential);

//通过连接认证获取MongoDB连接
        MongoClient mongoClient = new MongoClient(adds, credentials);
        MongoDatabase secret_reconnaissance_pro = mongoClient.getDatabase("secret_reconnaissance_pro");
        long x509cert = secret_reconnaissance_pro.getCollection("x509cert").count();
    }

    public static Resolver resolver() {
        return new Resolver();
    }

    /**
     * 解析器
     */
    public static class Resolver {
        public List<MongoConnection> analyze() {
            List<MongoConnection> connections = new ArrayList<>();
            URL resource = CollectionUtils.class.getClassLoader().getResource(CONFIG_PATH);
            Document load = XMLUtils.load(resource);
            Element rootElement = load.getRootElement();
            List<Element> templateElements = rootElement.elements("template");
            Map<String, Template> templateMap = new HashMap<>();
            for (Element templateElement : templateElements) {
                Template template = new Template();
                String templateId = templateElement.attributeValue("id");
                AssertUtils.attrAssert(templateId, "<template> id is empty");
                template.setId(templateId);
                template.setContent(templateElement.getData().toString());
                templateMap.put(templateId, template);
            }

            //拿到mongo标签
            List<Element> mongoElements = rootElement.elements("mongo");
            for (Element mongoElement : mongoElements) {
                MongoConnection mongoConnection = ElementParsingFactory.createMongoConnection(mongoElement);
                //mongo中的 database
                List<Element> databaseElements = mongoElement.elements("database");
                List<model.mongo.MongoDatabase> databases = new ArrayList<>();
                for (Element databaseElement : databaseElements) {
                    String databaseName = databaseElement.attributeValue("name");
                    model.mongo.MongoDatabase mongoDatabase = new model.mongo.MongoDatabase();
                    mongoDatabase.setName(databaseName);
                    List<MongoCollection> collections = new ArrayList<>();
                    List<Element> collectionElements = databaseElement.elements("collection");
                    for (Element collectionElement : collectionElements) {
                        MongoCollection collection = ElementParsingFactory.createMongoCollection(collectionElement);
                        collection.setDatabaseName(databaseName);
                        List<String> templateNames = collection.getTemplateNames();
                        if (CollectionUtils.isNotEmpty(templateNames)) {
                            List<Template> templates = new ArrayList<>();
                            for (String templateName : templateNames) {
                                Template findTemplate = templateMap.getOrDefault(templateName, null);
                                templates.add(findTemplate);
                                if (findTemplate == null) {
                                    throw new XMLConfigException("<collection id=" + collection.getName() + "> template["+templateName+"] cannot find in <template>");
                                }
                            }
                            collection.setTemplates(templates);
                        }
                        collections.add(collection);
                    }
                    mongoDatabase.setMongoCollections(collections);
                    databases.add(mongoDatabase);
                }
                mongoConnection.setDatabases(databases);
                connections.add(mongoConnection);
            }
            return connections;
        }
    }

}
