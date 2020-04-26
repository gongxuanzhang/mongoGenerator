import com.mongodb.client.MongoCollection;
import common.factory.MongoClientFactory;
import common.util.CollectionUtils;
import model.MongoDefinition;
import model.Template;
import model.mongo.MongoNode;
import writer.BeanWriterSupport;
import model.mongo.CollectionNode;
import org.bson.Document;
import pasring.*;
import writer.Writer;
import writer.WriterSupport;

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

        GenericAbstractTokenParser genericAbstractTokenParser = new GenericAbstractTokenParser();
        TemplateParsing templateParsing = new DefaultTemplateParsing("#{", "}", genericAbstractTokenParser);
        for (MongoDefinition mongoDefinition : init) {
            List<Template> templates = mongoDefinition.getTemplate();
            if (CollectionUtils.isNotEmpty(templates)) {
                for (Template template : templates) {
                    String original = template.getContent();
                    String escape = templateParsing.analyzeContent(original, mongoDefinition.getInfo());
                    template.setContent(escape);
                }
            }
            WriterSupport beanWriterSupport = new BeanWriterSupport();
            Writer beanWriter = new Writer(beanWriterSupport);
            beanWriter.generator(mongoDefinition);

        }

        //beanWriter.generator(init);
    }


    public static List<MongoDefinition> init() {
        XmlReader xmlReader = new XmlReader("aaa.xml");
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
