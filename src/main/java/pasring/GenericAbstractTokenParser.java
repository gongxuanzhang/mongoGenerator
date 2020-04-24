package pasring;

import common.exception.XMLConfigException;
import common.util.StringUtils;
import model.mongo.CollectionNode;

/**
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class GenericAbstractTokenParser extends AbstractTokenHandler {


    @Override
    public String handlerToken(String parameter, CollectionNode collectionNode) {
        switch (parameter.toLowerCase()) {
            case "databasename":
            case "db":
            case "dbname":
            case "database":
            case "beanname":
            case "bean":
                return StringUtils.upperCase("asdfasdf");
            default:
                throw new XMLConfigException("<template> #{" + parameter + "} 无法解析");
        }
    }



}
