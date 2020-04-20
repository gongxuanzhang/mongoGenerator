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

    }


}
