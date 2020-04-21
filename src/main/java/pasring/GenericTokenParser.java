package pasring;

import model.mongo.MongoCollection;

/**
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class GenericTokenParser implements TokenHandler{
    @Override
    public String handlerToken(String parameter, MongoCollection mongoCollection) {
        return "asdf";
    }
}
