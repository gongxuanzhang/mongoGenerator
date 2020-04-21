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
    private RepetitionStrategy repetitionStrategy;
    private NameStrategy nameStrategy;
    private String primaryPackage;
    private String innerPackage;
    private List<String> templateNames;
    private List<Template> templates;



    @Override
    public String toString() {
        return "GeneratorMongoCollection{" +
                "name='" + name + '\'' +
                ", scannerCount=" + scannerCount +
                ", repetitionStrategy=" + repetitionStrategy +
                ", nameStrategy=" + nameStrategy +
                ", primaryPackage='" + primaryPackage + '\'' +
                ", innerPackage='" + innerPackage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneratorMongoCollection that = (GeneratorMongoCollection) o;
        return Objects.equals(databaseName, that.databaseName) &&
                Objects.equals(name, that.name) &&
                Objects.equals(scannerCount, that.scannerCount) &&
                repetitionStrategy == that.repetitionStrategy &&
                nameStrategy == that.nameStrategy &&
                Objects.equals(primaryPackage, that.primaryPackage) &&
                Objects.equals(innerPackage, that.innerPackage) &&
                Objects.equals(templateNames, that.templateNames) &&
                Objects.equals(templates, that.templates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(databaseName, name, scannerCount, repetitionStrategy, nameStrategy, primaryPackage, innerPackage, templateNames, templates);
    }

    public String getName() {
        return name;
    }

    public GeneratorMongoCollection setName(String name) {
        this.name = name;
        return this;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public GeneratorMongoCollection setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public String getPrimaryPackage() {
        return primaryPackage;
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

    public Integer getScannerCount() {
        return scannerCount;
    }

    public GeneratorMongoCollection setScannerCount(Integer scannerCount) {
        this.scannerCount = scannerCount;
        return this;
    }


    public RepetitionStrategy getRepetitionStrategy() {
        return repetitionStrategy;
    }

    public GeneratorMongoCollection setRepetitionStrategy(RepetitionStrategy repetitionStrategy) {
        this.repetitionStrategy = repetitionStrategy;
        return this;
    }

    public NameStrategy getNameStrategy() {
        return nameStrategy;
    }

    public GeneratorMongoCollection setNameStrategy(NameStrategy nameStrategy) {
        this.nameStrategy = nameStrategy;
        return this;
    }
}
