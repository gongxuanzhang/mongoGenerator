package model.mongo;

import org.w3c.dom.Element;

import java.util.List;

/**
 * mongo的database实体
 *
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class DataBaseNode {
    private MongoNode mongoNode;
    private String name;
    private List<CollectionNode> collectionNodes;
    private String beanClose;


    public DataBaseNode(Element databaseElement) {
        this.name = databaseElement.getAttribute("name");
        this.beanClose = databaseElement.getAttribute("beanClose");
    }


    public MongoNode getMongoNode() {
        return mongoNode;
    }

    public DataBaseNode setMongoNode(MongoNode mongoNode) {
        this.mongoNode = mongoNode;
        return this;
    }

    public String getName() {
        return name;
    }

    public DataBaseNode setName(String name) {
        this.name = name;
        return this;
    }

    public List<CollectionNode> getCollectionNodes() {
        return collectionNodes;
    }

    public DataBaseNode setCollectionNodes(List<CollectionNode> collectionNodes) {
        this.collectionNodes = collectionNodes;
        return this;
    }

    public String getBeanClose() {
        return beanClose;
    }

    public DataBaseNode setBeanClose(String beanClose) {
        this.beanClose = beanClose;
        return this;
    }
}
