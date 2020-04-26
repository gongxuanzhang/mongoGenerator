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

    public static String upperCaseFirst(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }


    public static void main(String[] args) {
        System.out.println(upperCaseFirst("aaaa"));
    }
}
