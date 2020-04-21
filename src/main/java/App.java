import model.mongo.MongoCollection;
import model.mongo.MongoConnection;
import model.mongo.MongoDatabase;
import pasring.DataBaseParsing;
import pasring.DefaultTemplateParsing;
import pasring.GenericTokenParser;
import pasring.TemplateParsing;

import java.util.List;

/**
 * @author gxz
 * @date 2020/4/20 21:47
 */
public class App {
    public static void main(String[] args) {
        List<MongoConnection> analyze = DataBaseParsing.resolver().analyze();
        MongoDatabase mongoDatabase = analyze.get(0).getDatabases().get(0);
        MongoCollection collection = mongoDatabase.getMongoCollections().get(0);
        String content = collection.getTemplates().get(0).getContent();
        GenericTokenParser genericTokenParser = new GenericTokenParser();
        TemplateParsing templateParsing = new DefaultTemplateParsing("#{","}",genericTokenParser);
        System.out.println(templateParsing.analyzeContent(content,collection));
    }
}
