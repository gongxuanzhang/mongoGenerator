package model;


import common.util.CollectionUtils;
import model.mongo.GeneratorMongoCollection;
import pasring.TemplateParsing;

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

    private String path;

    private String innerPath;

    private List<Template> template;


    public boolean hasChild(){
        final int objectType = 3;
        return Objects.equals(type,objectType) || CollectionUtils.isNotEmpty(child);
    }

    public void fill(GeneratorMongoCollection generatorMongoCollection, TemplateParsing templateParsing){
        this.setPropertyName(generatorMongoCollection.getName()+generatorMongoCollection.getBeanClose());
        List<Template> templates = generatorMongoCollection.getTemplates();
        for (Template template : templates) {
            String templateContent
                    = templateParsing.analyzeContent(template.getContent(), generatorMongoCollection);
            template.setContent(templateContent);
        }
        this.setPath(generatorMongoCollection.getPrimaryPackage());
        this.setInnerPath(generatorMongoCollection.getInnerPackage());
        this.setTemplate(generatorMongoCollection.getTemplates());
    }

    /**
     * 功能描述:2是string 3是对象 4是数组 9是时间 16是int 18 是long
     * @author : gxz
     */
    public GeneratorModel setType(Integer type) {
        this.type = type;
        return this;
    }

    public List<Template> getTemplate() {
        return template;
    }

    public String getPath() {
        return path;
    }

    public GeneratorModel setPath(String path) {
        this.path = path;
        return this;
    }

    public String getInnerPath() {
        return innerPath;
    }

    public GeneratorModel setInnerPath(String innerPath) {
        this.innerPath = innerPath;
        return this;
    }

    public GeneratorModel setTemplate(List<Template> template) {
        this.template = template;
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
