package pasring;

import common.exception.XMLConfigException;
import model.Template;
import model.mongo.CollectionNode;
import model.mongo.MongoNode;
import model.mongo.DataBaseNode;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class XmlReader {

    private final String configPath;

    private Map<String, Template> templatePool;

    private Document xmlDocument;

    private List<MongoNode> mongoNodes;

    private int collectionCount = 0;

    public XmlReader(String configPath) {
        this.configPath = configPath;
        init();
    }

    private void init() {
        loadXml();
        loadTemplate();
        loadMongo();
        xmlDocument = null;
        templatePool = null;
    }

    public List<MongoNode> getMongoNodes() {
        return mongoNodes;
    }
    public int getCollectionCount(){
        return collectionCount;
    }

    private void loadMongo() {
        if (mongoNodes == null) {
            mongoNodes = new ArrayList<>();
        }
        NodeList mongoNodeList = xmlDocument.getElementsByTagName("mongo");
        for (int i = 0; i < mongoNodeList.getLength(); i++) {
            // 创建mongoNode
            Node mongoNode = mongoNodeList.item(i);
            if (mongoNode instanceof Element) {
                Element mongoElement = (Element) mongoNode;
                MongoNode mongo = new MongoNode(mongoElement);
                this.mongoNodes.add(mongo);
                NodeList databaseList = mongoElement.getElementsByTagName("database");
                List<DataBaseNode> dataBaseNodeList = new ArrayList<>(databaseList.getLength());
                mongo.setDatabases(dataBaseNodeList);
                for (int j = 0; j < databaseList.getLength(); j++) {
                    // 创建databaseNode
                    Node databaseNode = databaseList.item(j);
                    if (databaseNode instanceof Element) {
                        Element databaseElement = (Element) databaseNode;
                        DataBaseNode dataBaseNode = new DataBaseNode(databaseElement);
                        dataBaseNode.setMongoNode(mongo);
                        dataBaseNodeList.add(dataBaseNode);
                        NodeList collectionNodeList = databaseElement.getElementsByTagName("collection");
                        List<CollectionNode> collectionNodes = new ArrayList<>(collectionNodeList.getLength());
                        dataBaseNode.setCollectionNodes(collectionNodes);
                        for (int k = 0; k < collectionNodeList.getLength(); k++) {
                            // 创建collectionNode
                            Node collectionNode = collectionNodeList.item(k);
                            if (collectionNode instanceof Element) {
                                CollectionNode collectionNodeItem = new CollectionNode((Element) collectionNode,templatePool,dataBaseNode);
                                collectionCount++;
                                collectionNodes.add(collectionNodeItem);
                            }
                        }
                    }
                }
            }
        }
    }


    private void loadTemplate() {
        if (this.templatePool == null) {
            templatePool = new HashMap<>(16);
        }
        NodeList templateNodeList = xmlDocument.getElementsByTagName("template");
        for (int i = 0; i < templateNodeList.getLength(); i++) {
            Node node = templateNodeList.item(i);
            if (node instanceof Element) {
                Element e = (Element) node;
                String id = e.getAttribute("id");
                //判断是否重复
                if (templatePool.containsKey(id)) {
                    throw new XMLConfigException("<template> id [" + id + "] repetition");
                }
                Template template = new Template(e);
                String aPackage = e.getAttribute("package");
                String name = e.getAttribute("name");
                template.setConfigPackage(aPackage).setName(name);
                templatePool.put(id, template);
            }
        }
    }

    private void loadXml() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            URL resource = this.getClass().getClassLoader().getResource(configPath);
            xmlDocument = builder.parse(resource.getPath());
            builder = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
