package model;


import java.io.Serializable;
import java.util.List;


public class GeneratorModel implements Serializable {
    private String propertyName;
    private Integer type;
    private boolean array = false;
    private List<GeneratorModel> child;


    public Boolean getArray() {
        return array;
    }

    public GeneratorModel setArray(boolean array) {
        this.array = array;
        return this;
    }
    /**
     * 功能描述:2是string 3是对象 4是数组 9是时间 16是int 18 是long
     *
     * @author: gxz
     * @param:
     * @return:
     * @date: 2019/7/1 17:47
     */
    public GeneratorModel setType(Integer type) {
        this.type = type;
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
