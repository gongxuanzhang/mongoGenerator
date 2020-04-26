package model;


import org.w3c.dom.Element;

/**
 * @author gxz
 * @date 2020/4/20 21:56
 */
public class Template {
    private String id;
    private String content;
    private String configPackage;
    private String name;

    public Template() {
    }

    public Template(Element element){
        this.id = element.getAttribute("id");
        this.content = element.getTextContent();
    }

    public String getConfigPackage() {
        return configPackage;
    }

    public Template setConfigPackage(String configPackage) {
        this.configPackage = configPackage;
        return this;
    }

    public String getName() {
        return name;
    }

    public Template setName(String name) {
        this.name = name;
        return this;
    }



    public String getId() {
        return id;
    }

    public Template setId(String id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Template setContent(String content) {
        this.content = content;
        return this;
    }
}
