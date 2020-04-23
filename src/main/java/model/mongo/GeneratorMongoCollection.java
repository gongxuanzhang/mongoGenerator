package model.mongo;

import model.Template;

import java.util.List;
import java.util.Objects;

/** mongo的文档实体
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class GeneratorMongoCollection {
    private String databaseName;
    private String name;
    private Integer scannerCount;
    private String primaryPackage;
    private String innerPackage;
    private List<String> templateNames;
    private List<Template> templates;
    private String beanClose;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneratorMongoCollection that = (GeneratorMongoCollection) o;
        return Objects.equals(databaseName, that.databaseName) &&
                Objects.equals(name, that.name) &&
                Objects.equals(scannerCount, that.scannerCount) &&
                Objects.equals(primaryPackage, that.primaryPackage) &&
                Objects.equals(innerPackage, that.innerPackage) &&
                Objects.equals(templateNames, that.templateNames) &&
                Objects.equals(templates, that.templates) &&
                Objects.equals(beanClose, that.beanClose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(databaseName, name, scannerCount, primaryPackage, innerPackage, templateNames, templates, beanClose);
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public GeneratorMongoCollection setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public String getName() {
        return name;
    }

    public GeneratorMongoCollection setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getScannerCount() {
        return scannerCount;
    }

    public GeneratorMongoCollection setScannerCount(Integer scannerCount) {
        this.scannerCount = scannerCount;
        return this;
    }

    public String getPrimaryPackage() {
        return primaryPackage;
    }

    public GeneratorMongoCollection setPrimaryPackage(String primaryPackage) {
        this.primaryPackage = primaryPackage;
        return this;
    }

    public String getInnerPackage() {
        return innerPackage;
    }

    public GeneratorMongoCollection setInnerPackage(String innerPackage) {
        this.innerPackage = innerPackage;
        return this;
    }

    public List<String> getTemplateNames() {
        return templateNames;
    }

    public GeneratorMongoCollection setTemplateNames(List<String> templateNames) {
        this.templateNames = templateNames;
        return this;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public GeneratorMongoCollection setTemplates(List<Template> templates) {
        this.templates = templates;
        return this;
    }

    public String getBeanClose() {
        return beanClose;
    }

    public GeneratorMongoCollection setBeanClose(String beanClose) {
        this.beanClose = beanClose;
        return this;
    }
}
