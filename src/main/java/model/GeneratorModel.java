package model;


import common.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 每一个对象属性都可以封装成此对象
 * @author gxz
 * @email 514190950@qq.com
 */

public class GeneratorModel implements Serializable {
    /***属性名**/
    private String propertyName;
    /***属性类型 对应mongodb api $type**/
    private Integer type;
    /***此属性是否是数组**/
    private boolean array = false;
    /***如果此属性是对象  那么他仍然有此类型的子类**/
    private List<GeneratorModel> child;


    public boolean hasChild(){
        final int objectType = 2;
        return Objects.equals(type,objectType) || CollectionUtils.isNotEmpty(child);
    }

    /**
     * 功能描述:2是string 3是对象 4是数组 9是时间 16是int 18 是long
     * @author : gxz
     */
    public GeneratorModel setType(Integer type) {
        this.type = type;
        return this;
    }



    public Boolean getArray() {
        return array;
    }

    public GeneratorModel setArray(boolean array) {
        this.array = array;
        return this;
    }



    public String getPropertyName() {
        return propertyName;
    }

    public GeneratorModel setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public boolean isArray() {
        return array;
    }


    public List<GeneratorModel> getChild() {
        return child;
    }

    public GeneratorModel setChild(List<GeneratorModel> child) {
        this.child = child;
        return this;
    }
}
