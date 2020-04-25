package model;


import org.w3c.dom.Element;

/**
 * @author gxz
 * @date 2020/4/20 21:56
 */
public class Template {
    private String id;
    private String content;

    public Template() {
    }

    public Template(Element element){
        this.id = element.getAttribute("id");
        this.content = element.getTextContent();
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
