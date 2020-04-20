import com.mongodb.*;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 数据库解析器
 *
 * @author: gxz
 * @date: 2020/4/20 10:07
 **/
public class DataBaseAnalyze {

    public static void main(String[] args) {
        List<ServerAddress> adds = new ArrayList<>();
//ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress("172.16.1.102", 27017);
        adds.add(serverAddress);

        List<MongoCredential> credentials = new ArrayList<>();
//MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential("tincery", "secret_reconnaissance_pro", "Txy123.com".toCharArray());
        credentials.add(mongoCredential);

//通过连接认证获取MongoDB连接
        MongoClient mongoClient = new MongoClient(adds, credentials);
        MongoDatabase secret_reconnaissance_pro = mongoClient.getDatabase("secret_reconnaissance_pro");
        long x509cert = secret_reconnaissance_pro.getCollection("x509cert").count();
        System.out.println(x509cert);
    }
}
