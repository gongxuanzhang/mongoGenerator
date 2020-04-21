import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.HashSet;
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
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoCollection<Document> collection = mongoClient.getDatabase("tincery_pro").getCollection("alarm");
        Document query = new Document();
        BasicDBObject filed = new BasicDBObject("_id",false).append("eventdata",true);
        System.out.println(filed);
        FindIterable<Document> projection = collection.find(query).projection(filed);

        Set<String> innerNames = new HashSet<>();
        for (Document document : projection) {
            JSONArray eventdata = new JSONObject(document).getJSONArray("eventdata");
            for (Object eventdatum : eventdata) {
                JSONObject eventdatum1 = (JSONObject) eventdatum;
                innerNames.addAll(eventdatum1.keySet());
            }
        }
        System.out.println(innerNames.size());
        System.out.println(innerNames);

    }
}
