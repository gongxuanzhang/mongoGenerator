package model.mongo;

import java.util.List;

/** mongo的database实体
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class MongoDatabase {
        private String name;
        private List<MongoCollection> mongoCollections;





    public String getName() {
        return name;
    }

    public MongoDatabase setName(String name) {
        this.name = name;
        return this;
    }

    public List<MongoCollection> getMongoCollections() {
        return mongoCollections;
    }

    public MongoDatabase setMongoCollections(List<MongoCollection> mongoCollections) {
        this.mongoCollections = mongoCollections;
        return this;
    }
}
