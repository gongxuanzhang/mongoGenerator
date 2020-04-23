import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import common.factory.GeneratorModelAnalysisFactory;
import model.mongo.GeneratorMongoConnection;
import org.bson.Document;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import pasring.DataBaseParsing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author gxz
 * @date 2020/4/21 21:35
 */
public class Test {
    public static void main(String[] args) {
            aaa();
    }
    public static void aaa(){
        List<GeneratorMongoConnection> analyze = DataBaseParsing.resolver().analyze();
        MongoClient mongoClient1 = GeneratorModelAnalysisFactory.getMongoClient(analyze.get(0));
        MongoCollection<Document> collection =   mongoClient1.getDatabase("secret_reconnaissance_pro").getCollection("alarm");
        Document query = new Document();
        BasicDBObject filed = new BasicDBObject("_id",false).append("eventdata",true);
        System.out.println(filed);

        FindIterable<Document> projection = collection.find(query).projection(filed);

        Set<String> innerNames = new HashSet<>();
        long l = System.currentTimeMillis();
        for (Document document : projection) {
            JSONArray eventdata = new JSONObject(document).getJSONArray("eventdata");
            for (Object eventdatum : eventdata) {
                JSONObject eventdatum1 = (JSONObject) eventdatum;
                innerNames.addAll(eventdatum1.keySet());
            }
        }
        System.out.println(System.currentTimeMillis()-l);
        System.out.println(innerNames.size());
        System.out.println(innerNames);

    }
}
