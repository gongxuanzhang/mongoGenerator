package model.mongo;

import java.util.List;

/** mongo的database实体
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class GeneratorMongoDatabase {
        private String name;
        private List<GeneratorMongoCollection> generatorMongoCollections;
        private String beanClose;

    public String getName() {
        return name;
    }

    public GeneratorMongoDatabase setName(String name) {
        this.name = name;
        return this;
    }

    public List<GeneratorMongoCollection> getGeneratorMongoCollections() {
        return generatorMongoCollections;
    }

    public GeneratorMongoDatabase setGeneratorMongoCollections(List<GeneratorMongoCollection> generatorMongoCollections) {
        this.generatorMongoCollections = generatorMongoCollections;
        return this;
    }

    public String getBeanClose() {
        return beanClose;
    }

    public GeneratorMongoDatabase setBeanClose(String beanClose) {
        this.beanClose = beanClose;
        return this;
    }
}
