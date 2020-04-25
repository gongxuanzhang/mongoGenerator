package common.util;

import common.exception.XMLConfigException;

/**
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class AssertUtils {


    public static void attrAssert(String attrValue, String errMessage){
        if(StringUtils.isEmpty(attrValue)){
            throw new XMLConfigException(errMessage);
        }
    }
}
