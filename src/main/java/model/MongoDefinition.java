package model;


import common.util.CollectionUtils;
import model.mongo.CollectionNode;
import pasring.TemplateParsing;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 解析表之后得到的信息实体   此实体包含准备写出的所有内容
 * 换句话说这个类就是一张mongo一张表的内容
 *
 * @author gxz
 * @email 514190950@qq.com
 */

public class MongoDefinition implements Serializable {
    /***属性名**/
    private String propertyName;
    /***属性类型 对应mongodb api $type   如果没有类型 表示这是一个顶层实体  而不是内嵌属性**/
    private Integer type;
    /***此属性是否是数组**/
    private boolean array = false;
    /***如果此属性是对象  那么他仍然有此类型的子类**/
    private List<MongoDefinition> child;
    /***生成的文件位置*/
    private String path;
    /***子文件生成的位置*/
    private String innerPath;
    /***联系的模板 只有主类才拥有**/
    private List<Template> template;


    public boolean hasChild() {
        final int objectType = 3;
        return type == null || Objects.equals(type, objectType) || CollectionUtils.isNotEmpty(child);
    }

    public boolean primaryBean() {
        return type == null;
    }

    public void fill(CollectionNode collectionNode, TemplateParsing templateParsing) {
        this.setPropertyName(collectionNode.getName() + collectionNode.getBeanClose());
        List<Template> templates = collectionNode.getTemplates();
        for (Template template : templates) {
            String templateContent
                    = templateParsing.analyzeContent(template.getContent(), collectionNode);
            template.setContent(templateContent);
        }
        this.setPath(collectionNode.getPrimaryPackage());
        this.setInnerPath(collectionNode.getInnerPackage());
        this.setTemplate(collectionNode.getTemplates());
    }

    /**
     * 功能描述:2是string 3是对象 4是数组 9是时间 16是int 18 是long
     *
     * @author : gxz
     */
    public MongoDefinition setType(Integer type) {
        this.type = type;
        return this;
    }

    public List<Template> getTemplate() {
        return template;
    }

    public String getPath() {
        return path;
    }

    public MongoDefinition setPath(String path) {
        this.path = path;
        return this;
    }

    public String getInnerPath() {
        return innerPath;
    }

    public MongoDefinition setInnerPath(String innerPath) {
        this.innerPath = innerPath;
        return this;
    }

    public MongoDefinition setTemplate(List<Template> template) {
        this.template = template;
        return this;
    }

    public Boolean getArray() {
        return array;
    }

    public MongoDefinition setArray(boolean array) {
        this.array = array;
        return this;
    }


    public String getPropertyName() {
        return propertyName;
    }

    public MongoDefinition setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public boolean isArray() {
        return array;
    }


    public List<MongoDefinition> getChild() {
        return child;
    }

    public MongoDefinition setChild(List<MongoDefinition> child) {
        this.child = child;
        return this;
    }
}
