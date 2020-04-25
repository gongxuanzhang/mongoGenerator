package pasring;

import model.mongo.CollectionNode;

/**
 * token处理器
 *
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public abstract class AbstractTokenHandler {


    /***
     * 参数处理器
     * @author gxz
     * @param parameter 通配符中的参数名  比如 #{aaa}  这个参数就是aaa
     * @param collectionNode 解析之后的collection对象
     * @return String  处理之后的内容
     **/
    public abstract String handlerToken(String parameter, CollectionNode collectionNode);




}
