package model;


/**
 * @author gxz
 * @date 2020/4/20 21:56
 */
public class Template {
    private String id;
    private String content;

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
