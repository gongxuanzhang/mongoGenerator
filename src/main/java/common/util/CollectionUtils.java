package common.util;

import model.mongo.MongoCollection;
import model.mongo.MongoConnection;
import model.mongo.MongoDatabase;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

/**
 * @author: gxz
 * @date: 2020/4/20 10:44
 **/
public class CollectionUtils {

    public static boolean isEmpty(Collection coll) {
        return (coll == null || coll.isEmpty());
    }

    public static boolean isNotEmpty(Collection coll) {
        return !isEmpty(coll);
    }

    public static void main(String[] args) throws IOException {
        URL resource = CollectionUtils.class.getClassLoader().getResource("aaa.xml");
        Document load = XMLUtils.load(resource);
        Element rootElement = load.getRootElement();
        List<Element> template = rootElement.elements("template");
        for (Element element : template) {
            String data = element.getData().toString();
            System.out.println(data);
        }

        List<Element> mongoElements = rootElement.elements("mongo");
        for (Element mongoElement : mongoElements) {
            MongoConnection mongoConnection = MongoConnection.fromElement(mongoElement);
            List<Element> databaseElements = mongoElement.elements("database");
            for (Element databaseElement : databaseElements) {
                List<Element> collectionElements = databaseElement.elements("collection");
                for (Element collectionElement : collectionElements) {
                    MongoCollection collection = MongoCollection.fromElement(collectionElement);
                    System.out.println(collection);
                }

            }

            System.out.println(mongoConnection);

        }
    }


}
