package common.util;

import common.exception.XMLConfigException;
import org.dom4j.Attribute;

/**
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class AssertUtils {
    public static void attrAssert(Attribute attribute, String errMessage){
        if(attribute==null){
         throw new XMLConfigException(errMessage);
        }
        if(StringUtils.isEmpty(attribute.getValue())){
            throw new XMLConfigException("XML "+attribute.getName()+" is empty");
        }
    }

    public static void attrAssert(String attrValue, String errMessage){
        if(StringUtils.isEmpty(attrValue)){
            throw new XMLConfigException(errMessage);
        }
    }
}
