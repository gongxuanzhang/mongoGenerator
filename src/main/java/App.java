import com.mongodb.client.MongoCollection;
import common.factory.GeneratorModelAnalysisFactory;
import common.util.Writer;
import model.GeneratorModel;
import model.mongo.GeneratorMongoCollection;
import model.mongo.GeneratorMongoConnection;
import org.bson.Document;
import pasring.*;

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
        List<GeneratorModel> init = init();
        Writer.generator(init);
    }



    public static List<GeneratorModel> init(){
        List<GeneratorMongoConnection> analyze = DataBaseParsing.resolver().analyze();
        List<GeneratorModel> generatorModels = new ArrayList<>();
        for (GeneratorMongoConnection generatorMongoConnection : analyze) {
            Map<GeneratorMongoCollection, MongoCollection<Document>> mongoCollection = GeneratorModelAnalysisFactory.getMongoCollection(generatorMongoConnection);
            mongoCollection.forEach((genColl,mongoColl)->{
                MongoParsing mongoParsing = new MongoParsing(mongoColl,genColl.getScannerCount());
                GeneratorModel process = mongoParsing.process();
                GenericAbstractTokenParser tokenParser = new GenericAbstractTokenParser();
                TemplateParsing templateParsing = new DefaultTemplateParsing("#{", "}", tokenParser);
                process.fill(genColl,templateParsing);
                generatorModels.add(process);
            });
        }
        return generatorModels;
    }
}
