package pasring;

import common.exception.XMLConfigException;
import model.mongo.GeneratorMongoCollection;

/**
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class GenericAbstractTokenParser extends AbstractTokenHandler {

    public GenericAbstractTokenParser(TemplateParsing templateParsing) {
        super(templateParsing);
    }

    @Override
    public String handlerToken(String parameter, GeneratorMongoCollection generatorMongoCollection) {
        switch (parameter.toLowerCase()) {
            case "databasename":
            case "db":
            case "dbname":
            case "database":
                return generatorMongoCollection.getDatabaseName();
            default:
                throw new XMLConfigException("<template> #{" + parameter + "} 无法解析");
        }
    }

    @Override
    public String dbNameHandler(String dbName) {
        return null;
    }

    @Override
    public String beanNameHandler(String collectionName) {
        return null;
    }


}
