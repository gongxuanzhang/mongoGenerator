package common.util;

/**
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
