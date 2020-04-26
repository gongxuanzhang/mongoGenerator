package common.factory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.istack.internal.NotNull;
import common.util.CollectionUtils;
import model.mongo.CollectionNode;
import model.mongo.MongoNode;
import model.mongo.DataBaseNode;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析工厂  主要针对xml解析成的对象 返回连接实体
 *
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class MongoClientFactory {

    private MongoClientFactory(){
        throw new RuntimeException("cannot create instance");
    }
    /***
     * 通过配置信息返回对应的mongoClient
     * @author gxz
     * @date  2020/4/24
     * @param connection 通过xml解析成的节点
     **/
    private static MongoClient getMongoClient(MongoNode connection) {
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

    public static Map<CollectionNode, MongoCollection<Document>> getMongoCollection(MongoNode connection){
        return getMongoCollection(connection,new HashMap<>(16));
    }

    /***
     * 每一个collectionNode都对应一个数据库连接查询对象
     * @author gxz
     **/
    public static Map<CollectionNode, MongoCollection<Document>> getMongoCollection(MongoNode connection, @NotNull Map<CollectionNode, MongoCollection<Document>> map){
        List<DataBaseNode> databases = connection.getDatabases();
        if(CollectionUtils.isEmpty(databases)){
                return map;
        }
        MongoClient mongoClient = getMongoClient(connection);
        for (DataBaseNode databaseInfo : databases) {
            MongoDatabase database = mongoClient.getDatabase(databaseInfo.getName());
            List<CollectionNode> collectionInfos = databaseInfo.getCollectionNodes();
            for (CollectionNode collectionInfo : collectionInfos) {
                MongoCollection<Document> collection = database.getCollection(collectionInfo.getName());
                map.put(collectionInfo,collection);
            }
        }
        return map;
    }

}
