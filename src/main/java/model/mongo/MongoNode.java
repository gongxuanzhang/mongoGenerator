package model.mongo;

import com.sun.istack.internal.NotNull;
import common.util.AssertUtils;
import common.util.StringUtils;
import org.w3c.dom.Element;

import java.util.List;

/**
 * mongo连接对象
 *
 * @author: gxz
 * @email: 514190950@qq.com
 **/
public class MongoNode {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String source;
    private boolean auth;
    private List<DataBaseNode> databases;



    public MongoNode(Element element) {
        this.host = element.getAttribute("host");
        this.port = Integer.valueOf(element.getAttribute("port"));
        String authStr = element.getAttribute("auth");
        boolean auth = StringUtils.isEmpty(authStr) ? false : Boolean.valueOf(authStr);
        this.auth = auth;
        if (auth) {
            this.username = element.getAttribute("username");
            this.password = element.getAttribute("password");
            this.source = element.getAttribute("source");
            //如果需要认证  需要判空
            AssertUtils.attrAssert(username,"xml <mongo> username is null ");
            AssertUtils.attrAssert(password,"xml <mongo> password is null ");
            AssertUtils.attrAssert(source,"xml <mongo> source is null ");
        }
    }

    @Override
    public String toString() {
        return "MongoNode{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", source='" + source + '\'' +
                ", auth=" + auth +
                '}';
    }


    public List<DataBaseNode> getDatabases() {
        return databases;
    }

    public MongoNode setDatabases(List<DataBaseNode> databases) {
        this.databases = databases;
        return this;
    }

    public String getHost() {
        return host;
    }

    public MongoNode setHost(String host) {
        this.host = host;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public MongoNode setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public MongoNode setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MongoNode setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSource() {
        return source;
    }

    public MongoNode setSource(String source) {
        this.source = source;
        return this;
    }

    public boolean isAuth() {
        return auth;
    }

    public MongoNode setAuth(boolean auth) {
        this.auth = auth;
        return this;
    }
}
