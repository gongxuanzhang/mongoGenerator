package model.mongo;

import common.util.AssertUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.util.List;

/** mongo连接对象
 * @author: gxz
 * @email: 514190950@qq.com
 **/
public class MongoConnection {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String source;
    private boolean auth;
    private List<MongoDatabase> databases;



    @Override
    public String toString() {
        return "MongoConnection{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", source='" + source + '\'' +
                ", auth=" + auth +
                '}';
    }


    public List<MongoDatabase> getDatabases() {
        return databases;
    }

    public MongoConnection setDatabases(List<MongoDatabase> databases) {
        this.databases = databases;
        return this;
    }

    public String getHost() {
        return host;
    }

    public MongoConnection setHost(String host) {
        this.host = host;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public MongoConnection setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public MongoConnection setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MongoConnection setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSource() {
        return source;
    }

    public MongoConnection setSource(String source) {
        this.source = source;
        return this;
    }

    public boolean isAuth() {
        return auth;
    }

    public MongoConnection setAuth(boolean auth) {
        this.auth = auth;
        return this;
    }
}
