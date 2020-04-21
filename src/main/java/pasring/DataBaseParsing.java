package pasring;

import common.exception.XMLConfigException;
import common.factory.ElementParsingFactory;
import common.util.AssertUtils;
import common.util.CollectionUtils;
import common.util.XMLUtils;
import model.Template;
import model.mongo.GeneratorMongoCollection;
import model.mongo.GeneratorMongoConnection;
import model.mongo.GeneratorMongoDatabase;
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



    public static Resolver resolver() {
        return new Resolver();
    }

    /**
     * 解析器
     */
    public static class Resolver {
        public List<GeneratorMongoConnection> analyze() {
            List<GeneratorMongoConnection> connections = new ArrayList<>();
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
                GeneratorMongoConnection generatorMongoConnection = ElementParsingFactory.createMongoConnection(mongoElement);
                //mongo中的 database
                List<Element> databaseElements = mongoElement.elements("database");
                List<GeneratorMongoDatabase> databases = new ArrayList<>();
                for (Element databaseElement : databaseElements) {
                    String databaseName = databaseElement.attributeValue("name");
                    GeneratorMongoDatabase generatorMongoDatabase = new GeneratorMongoDatabase();
                    generatorMongoDatabase.setName(databaseName);
                    List<GeneratorMongoCollection> collections = new ArrayList<>();
                    List<Element> collectionElements = databaseElement.elements("collection");
                    for (Element collectionElement : collectionElements) {
                        GeneratorMongoCollection collection = ElementParsingFactory.createMongoCollection(collectionElement);
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
                    generatorMongoDatabase.setGeneratorMongoCollections(collections);
                    databases.add(generatorMongoDatabase);
                }
                generatorMongoConnection.setDatabases(databases);
                connections.add(generatorMongoConnection);
            }
            return connections;
        }
    }

}
