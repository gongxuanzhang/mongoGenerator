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

public class MongoScan implements Serializable {
    /***属性名**/
    private String propertyName;
    /***属性类型 对应mongodb api $type   如果没有类型 表示这是一个顶层实体  而不是内嵌属性**/
    private Integer type;
    /***此属性是否是数组**/
    private boolean array = false;
    /***如果此属性是对象  那么他仍然有此类型的子类**/
    private List<MongoScan> child;


    public boolean hasChild() {
        final int objectType = 3;
        return type == null || Objects.equals(type, objectType) || CollectionUtils.isNotEmpty(child);
    }

    public boolean primaryBean() {
        return type == null;
    }


    /**
     * 功能描述:2是string 3是对象 4是数组 9是时间 16是int 18 是long
     *
     * @author : gxz
     */
    public MongoScan setType(Integer type) {
        this.type = type;
        return this;
    }




    public Boolean getArray() {
        return array;
    }

    public MongoScan setArray(boolean array) {
        this.array = array;
        return this;
    }


    public String getPropertyName() {
        return propertyName;
    }

    public MongoScan setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public boolean isArray() {
        return array;
    }


    public List<MongoScan> getChild() {
        return child;
    }

    public MongoScan setChild(List<MongoScan> child) {
        this.child = child;
        return this;
    }
}
