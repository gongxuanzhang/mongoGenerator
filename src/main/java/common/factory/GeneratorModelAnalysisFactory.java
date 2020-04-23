package common.factory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.sun.istack.internal.NotNull;
import common.util.CollectionUtils;
import model.mongo.GeneratorMongoCollection;
import model.mongo.GeneratorMongoConnection;
import model.mongo.GeneratorMongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 解析工厂  主要针对xml解析成的对象 返回连接实体
 *
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class GeneratorModelAnalysisFactory {

    private GeneratorModelAnalysisFactory(){
        throw new RuntimeException("cannot create instance");
    }

    public static MongoClient getMongoClient(GeneratorMongoConnection connection) {
        List<ServerAddress> adds = new ArrayList<>();
        //ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress(connection.getHost(), connection.getPort());
        adds.add(serverAddress);

        // MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        if(connection.isAuth()){
            MongoCredential mongoCredential
                    = MongoCredential.createScramSha1Credential(connection.getUsername(), connection.getSource(), connection.getPassword().toCharArray());
            MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
            // 通过连接认证获取MongoDB连接
            return new MongoClient(adds,mongoCredential,mongoClientOptions);
        }
        return new MongoClient(adds);
    }
    public static Map<GeneratorMongoCollection, MongoCollection<Document>> getMongoCollection
            (GeneratorMongoConnection connection){
        return getMongoCollection(connection,new HashMap<>(16));
    }

    public static Map<GeneratorMongoCollection, MongoCollection<Document>> getMongoCollection
            (GeneratorMongoConnection connection,@NotNull Map<GeneratorMongoCollection, MongoCollection<Document>> map){
        List<GeneratorMongoDatabase> databases = connection.getDatabases();
        if(CollectionUtils.isEmpty(databases)){
                return map;
        }
        MongoClient mongoClient = getMongoClient(connection);
        for (GeneratorMongoDatabase databaseInfo : databases) {
            MongoDatabase database = mongoClient.getDatabase(databaseInfo.getName());
            List<GeneratorMongoCollection> collectionInfos = databaseInfo.getGeneratorMongoCollections();
            for (GeneratorMongoCollection collectionInfo : collectionInfos) {
                MongoCollection<Document> collection = database.getCollection(collectionInfo.getName());
                map.put(collectionInfo,collection);
            }
        }
        return map;
    }

}
