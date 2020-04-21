package model.mongo;

import common.util.AssertUtils;
import common.util.StringUtils;
import model.Template;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** mongo的文档实体
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class MongoCollection {
    private String databaseName;
    private String name;
    private Integer scannerCount;
    private RepetitionStrategy repetitionStrategy;
    private NameStrategy nameStrategy;
    private String primaryPackage;
    private String innerPackage;
    private List<String> templateNames;
    private List<Template> templates;



    @Override
    public String toString() {
        return "MongoCollection{" +
                "name='" + name + '\'' +
                ", scannerCount=" + scannerCount +
                ", repetitionStrategy=" + repetitionStrategy +
                ", nameStrategy=" + nameStrategy +
                ", primaryPackage='" + primaryPackage + '\'' +
                ", innerPackage='" + innerPackage + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public MongoCollection setName(String name) {
        this.name = name;
        return this;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public MongoCollection setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public String getPrimaryPackage() {
        return primaryPackage;
    }

    public List<String> getTemplateNames() {
        return templateNames;
    }

    public MongoCollection setTemplateNames(List<String> templateNames) {
        this.templateNames = templateNames;
        return this;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public MongoCollection setTemplates(List<Template> templates) {
        this.templates = templates;
        return this;
    }

    public MongoCollection setPrimaryPackage(String primaryPackage) {
        this.primaryPackage = primaryPackage;
        return this;
    }

    public String getInnerPackage() {
        return innerPackage;
    }

    public MongoCollection setInnerPackage(String innerPackage) {
        this.innerPackage = innerPackage;
        return this;
    }

    public Integer getScannerCount() {
        return scannerCount;
    }

    public MongoCollection setScannerCount(Integer scannerCount) {
        this.scannerCount = scannerCount;
        return this;
    }


    public RepetitionStrategy getRepetitionStrategy() {
        return repetitionStrategy;
    }

    public MongoCollection setRepetitionStrategy(RepetitionStrategy repetitionStrategy) {
        this.repetitionStrategy = repetitionStrategy;
        return this;
    }

    public NameStrategy getNameStrategy() {
        return nameStrategy;
    }

    public MongoCollection setNameStrategy(NameStrategy nameStrategy) {
        this.nameStrategy = nameStrategy;
        return this;
    }
}
