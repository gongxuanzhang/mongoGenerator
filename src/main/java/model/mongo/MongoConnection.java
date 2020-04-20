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

    public static MongoConnection fromElement(Element element){
        MongoConnection result = new MongoConnection();
        Attribute host = element.attribute("host");
        AssertUtils.attrAssert(host,"xml <mongo> host is null ");
        Attribute port = element.attribute("port");
        AssertUtils.attrAssert(port,"xml <mongo> port is null ");
        Attribute authAttr = element.attribute("auth");
        boolean auth =authAttr==null?true:Boolean.valueOf(authAttr.getValue());
        result.setHost(host.getValue()).setPort(Integer.valueOf(port.getValue()));
        result.setAuth(auth);
        if(result.auth){
            Attribute username = element.attribute("username");
            AssertUtils.attrAssert(username,"xml <mongo> username is null ");
            Attribute password = element.attribute("password");
            AssertUtils.attrAssert(password,"xml <mongo> password is null ");
            Attribute source = element.attribute("source");
            AssertUtils.attrAssert(source,"xml <mongo> source is null ");
            result.setUsername(username.getValue()).setPassword(password.getValue());
            result.setSource(source.getValue());
        }
        return result;
    }



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
