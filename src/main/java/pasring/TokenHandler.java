package pasring;

import model.mongo.MongoCollection;

/**
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public interface TokenHandler {

    /***
     * 参数处理器
     * @author gxz
     * @param parameter 通配符中的参数名  比如 #{aaa}  这个参数就是aaa
     * @param mongoCollection 解析之后的collection对象
     * @return String  处理之后的内容
     **/
    String handlerToken(String parameter, MongoCollection mongoCollection);


}
