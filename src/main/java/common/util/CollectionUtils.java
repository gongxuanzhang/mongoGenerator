package common.util;

import java.io.IOException;
import java.util.Collection;

/**
 * @author: gxz
 * @date: 2020/4/20 10:44
 **/
public class CollectionUtils {

    public static boolean isEmpty(Collection coll) {
        return (coll == null || coll.isEmpty());
    }

    public static boolean isNotEmpty(Collection coll) {
        return !isEmpty(coll);
    }

    public static void main(String[] args) throws IOException {

    }


}
