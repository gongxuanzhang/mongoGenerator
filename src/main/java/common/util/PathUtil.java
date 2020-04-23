package common.util;

import java.io.File;
import java.io.IOException;

/**
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class PathUtil {

    public static String getPathJava() {
        File file = new File("");
        String path = null;
        final String sep = File.separator;
        try {
            path = file.getCanonicalPath();
            path += sep + "src" + sep + "main" + sep + "java" + sep;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static void main(String[] args) {
        getPathJava();
    }
}
