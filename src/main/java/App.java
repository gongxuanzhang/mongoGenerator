import com.mongodb.MongoNamespace;
import com.mongodb.client.MongoCollection;
import common.factory.GeneratorModelAnalysisFactory;
import model.GeneratorModel;
import model.mongo.GeneratorMongoCollection;
import model.mongo.GeneratorMongoConnection;
import model.mongo.GeneratorMongoDatabase;
import org.bson.Document;
import pasring.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gxz
 * @date 2020/4/20 21:47
 */
public class App {
    public static void main(String[] args) {
        List<GeneratorMongoConnection> analyze = DataBaseParsing.resolver().analyze();

        GeneratorMongoDatabase generatorMongoDatabase1 = analyze.get(0).getDatabases().get(0);
        GeneratorMongoCollection collection = generatorMongoDatabase1.getGeneratorMongoCollections().get(0);
        String content = collection.getTemplates().get(0).getContent();
        GenericTokenParser genericTokenParser = new GenericTokenParser();
        TemplateParsing templateParsing = new DefaultTemplateParsing("#{","}",genericTokenParser);
        System.out.println(templateParsing.analyzeContent(content,collection));
        for (GeneratorMongoConnection generatorMongoConnection : analyze) {
            Map<GeneratorMongoCollection, MongoCollection<Document>> mongoCollection = GeneratorModelAnalysisFactory.getMongoCollection(generatorMongoConnection);
            for (MongoCollection<Document> value : mongoCollection.values()) {
                MongoNamespace namespace = value.getNamespace();
                System.out.println("数据库["+namespace.getDatabaseName()+"]表["+namespace.getCollectionName()+"]有"+value.countDocuments()+"条记录");
                MongoParsing mongoParsing = new MongoParsing(value,400000);
                //Set<String> a = mongoParsing.getNextParameterNames("eventdata.extension");
                GeneratorModel clientlocation = mongoParsing.processNameType("clientlocation");
                //GeneratorModel generatorModel = mongoParsing.process();
                System.out.println(1);
            }
        }
    }
}
