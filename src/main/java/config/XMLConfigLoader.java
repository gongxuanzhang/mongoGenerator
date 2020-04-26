package config;

import com.mongodb.client.MongoCollection;
import common.factory.MongoClientFactory;
import model.MongoDefinition;
import model.mongo.CollectionNode;
import model.mongo.MongoNode;
import org.bson.Document;
import pasring.MongoScanner;
import pasring.XmlReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class XMLConfigLoader {


    public static List<MongoDefinition> read() {
        XmlReader xmlReader = new XmlReader("config.xml");
        List<MongoNode> mongoNodes = xmlReader.getMongoNodes();
        List<MongoDefinition> mongoDefinitions = new ArrayList<>();
        for (MongoNode mongoNode : mongoNodes) {
            Map<CollectionNode, MongoCollection<Document>> mongoCollection = MongoClientFactory.getMongoCollection(mongoNode);
            mongoCollection.forEach((info, collection) -> {
                MongoScanner mongoScanner = new MongoScanner(collection, info);
                mongoDefinitions.add(mongoScanner.getProduct().fill());
            });
        }
        return mongoDefinitions;
    }

}
