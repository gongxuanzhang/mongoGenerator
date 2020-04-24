package model.mongo;

import common.exception.XMLConfigException;
import common.util.StringUtils;
import model.Template;
import org.w3c.dom.Element;

import java.util.*;

/** mongo的文档实体
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class CollectionNode {
    private static final String separator = ",";

    private DataBaseNode dataBase;
    private String name;
    private Integer scannerCount;
    private String primaryPackage;
    private String innerPackage;
    private List<String> templateNames;
    private List<Template> templates;
    private String beanClose;

    public CollectionNode(Element element, Map<String,Template> templateMap) {
        this.name = element.getAttribute("name");
        this.scannerCount = Integer.valueOf(element.getAttribute("scannerCount"));
        this.primaryPackage = element.getAttribute("package");
        String innerPackage = element.getAttribute("innerPackage");
        this.innerPackage = StringUtils.isEmpty(innerPackage)?primaryPackage:innerPackage;
        String templateNameStr = element.getAttribute("template");
        this.templateNames = Arrays.asList(templateNameStr.split(separator));
        templates = new ArrayList<>(this.templateNames.size());
        for (String templateName : this.templateNames) {
            Template template = templateMap.getOrDefault(templateName,null);
             if(template == null){
                 throw new XMLConfigException("<collection> templateName["+templateName+"] can't found in <template>");
             }
            templates.add(template);
        }
    }



    @Override
    public String toString() {
        return "CollectionNode{" +
                "dataBase=" + dataBase +
                ", name='" + name + '\'' +
                ", scannerCount=" + scannerCount +
                ", primaryPackage='" + primaryPackage + '\'' +
                ", innerPackage='" + innerPackage + '\'' +
                ", templateNames=" + templateNames +
                ", templates=" + templates +
                ", beanClose='" + beanClose + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionNode that = (CollectionNode) o;
        return Objects.equals(dataBase, that.dataBase) &&
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
        return Objects.hash(dataBase, name, scannerCount, primaryPackage, innerPackage, templateNames, templates, beanClose);
    }

    public DataBaseNode getDataBase() {
        return dataBase;
    }

    public CollectionNode setDataBase(DataBaseNode dataBase) {
        this.dataBase = dataBase;
        return this;
    }

    public String getName() {
        return name;
    }

    public CollectionNode setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getScannerCount() {
        return scannerCount;
    }

    public CollectionNode setScannerCount(Integer scannerCount) {
        this.scannerCount = scannerCount;
        return this;
    }

    public String getPrimaryPackage() {
        return primaryPackage;
    }

    public CollectionNode setPrimaryPackage(String primaryPackage) {
        this.primaryPackage = primaryPackage;
        return this;
    }

    public String getInnerPackage() {
        return innerPackage;
    }

    public CollectionNode setInnerPackage(String innerPackage) {
        this.innerPackage = innerPackage;
        return this;
    }

    public List<String> getTemplateNames() {
        return templateNames;
    }

    public CollectionNode setTemplateNames(List<String> templateNames) {
        this.templateNames = templateNames;
        return this;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public CollectionNode setTemplates(List<Template> templates) {
        this.templates = templates;
        return this;
    }

    public String getBeanClose() {
        return beanClose;
    }

    public CollectionNode setBeanClose(String beanClose) {
        this.beanClose = beanClose;
        return this;
    }
}
