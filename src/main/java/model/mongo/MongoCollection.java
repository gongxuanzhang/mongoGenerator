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
    private String name;
    private Integer scannerCount;
    private RepetitionStrategy repetitionStrategy;
    private NameStrategy nameStrategy;
    private String primaryPackage;
    private String innerPackage;
    private List<String> templateNames;
    private List<Template> templates;

    public static MongoCollection fromElement(Element element){
        MongoCollection result =  new MongoCollection();
        String name = element.attributeValue("name");
        String primaryPackage = element.attributeValue("primaryPackage");

        AssertUtils.attrAssert(name,"XML collection name is empty");
        AssertUtils.attrAssert(primaryPackage,"XML primaryPackage is empty");

        String scannerCountFromXML = element.attributeValue("scannerCount");
        int scannerCount = StringUtils.isEmpty(scannerCountFromXML)?400000:Integer.valueOf(scannerCountFromXML);
        result.setName(name).setScannerCount(scannerCount);

        String repetitionStrategyFromXML = element.attributeValue("repetitionStrategy");
        String nameStrategyFromXML = element.attributeValue("nameStrategy");
        //重复策略 默认是JSON 命名策略 默认是AUTO
        NameStrategy nameStrategy
                = StringUtils.isEmpty(nameStrategyFromXML)?
                NameStrategy.AUTO:NameStrategy.valueOf(nameStrategyFromXML.toUpperCase());
        result.setNameStrategy(nameStrategy);
        RepetitionStrategy repetitionStrategy
                = StringUtils.isEmpty(repetitionStrategyFromXML)?
                RepetitionStrategy.JSON:RepetitionStrategy.valueOf(repetitionStrategyFromXML.toUpperCase());
        result.setRepetitionStrategy(repetitionStrategy);
        //复杂实体的生成路径 如果没填写 默认是主包路径
        result.setPrimaryPackage(primaryPackage);
        String innerPackage = element.attributeValue("innerPackage");
        result.setInnerPackage(StringUtils.isEmpty(innerPackage)?primaryPackage:innerPackage);
        String templateNameStr = element.attributeValue("template");
        if(StringUtils.isNotEmpty(templateNameStr)){
            List<String> templateNames = templateNames2list(templateNameStr.trim());
            result.setTemplateNames(templateNames);
        }
        return result;
    }

    private static List<String> templateNames2list(String templateNames){
        List<String> result = new ArrayList<>();
        if(StringUtils.isEmpty(templateNames)){
            return null;
        }
        if(templateNames.contains(",")){
            String[] names = templateNames.split(",");
            result.addAll(Arrays.asList(names));
        }
        return result;

    }

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
