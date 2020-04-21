package model.mongo;

import java.util.List;

/** mongo连接对象
 * @author: gxz
 * @email: 514190950@qq.com
 **/
public class GeneratorMongoConnection {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String source;
    private boolean auth;
    private List<GeneratorMongoDatabase> databases;



    @Override
    public String toString() {
        return "GeneratorMongoConnection{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", source='" + source + '\'' +
                ", auth=" + auth +
                '}';
    }


    public List<GeneratorMongoDatabase> getDatabases() {
        return databases;
    }

    public GeneratorMongoConnection setDatabases(List<GeneratorMongoDatabase> databases) {
        this.databases = databases;
        return this;
    }

    public String getHost() {
        return host;
    }

    public GeneratorMongoConnection setHost(String host) {
        this.host = host;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public GeneratorMongoConnection setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public GeneratorMongoConnection setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public GeneratorMongoConnection setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSource() {
        return source;
    }

    public GeneratorMongoConnection setSource(String source) {
        this.source = source;
        return this;
    }

    public boolean isAuth() {
        return auth;
    }

    public GeneratorMongoConnection setAuth(boolean auth) {
        this.auth = auth;
        return this;
    }
}
