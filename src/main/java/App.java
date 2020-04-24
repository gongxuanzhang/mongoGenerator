import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import common.factory.MongoClientFactory;
import model.MongoDefinition;
import model.mongo.MongoNode;
import writer.BeanWriter;
import model.mongo.CollectionNode;
import org.bson.Document;
import pasring.*;
import writer.Writer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gxz
 * @date 2020/4/20 21:47
 */
public class App {
    public static void main(String[] args) throws IOException {
        List<MongoDefinition> init = init();
        Writer beanWriter = new BeanWriter();
        //beanWriter.generator(init);
    }



    public static List<MongoDefinition> init(){
        XmlReader xmlReader = new XmlReader("aaa.xml");
        List<MongoNode> mongoNodes = xmlReader.getMongoNodes();
        for (MongoNode mongoNode : mongoNodes) {
            MongoClient mongoClient = MongoClientFactory.getMongoClient(mongoNode);

        }




        List<MongoDefinition> mongoDefinitions = new ArrayList<>();
        for (MongoNode mongoNode : mongoNodes) {
            Map<CollectionNode, MongoCollection<Document>> mongoCollection = MongoClientFactory.getMongoCollection(mongoNode);
            mongoCollection.forEach((genColl,mongoColl)->{
                MongoParsing mongoParsing = new MongoParsing(mongoColl,genColl.getScannerCount());
                MongoDefinition process = mongoParsing.process();
                GenericAbstractTokenParser tokenParser = new GenericAbstractTokenParser();
                TemplateParsing templateParsing = new DefaultTemplateParsing("#{", "}", tokenParser);
                process.fill(genColl,templateParsing);
                mongoDefinitions.add(process);
            });
        }
        return mongoDefinitions;
    }
}
