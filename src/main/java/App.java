import com.mongodb.client.MongoCollection;
import common.factory.MongoClientFactory;
import common.util.CollectionUtils;
import config.XMLConfigLoader;
import model.MongoDefinition;
import model.Template;
import model.mongo.MongoNode;
import writer.BeanWriterSupport;
import model.mongo.CollectionNode;
import org.bson.Document;
import pasring.*;
import writer.TemplateWriterSupport;
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
            Generator.generator();
    }



}
