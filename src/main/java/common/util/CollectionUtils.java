package common.util;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

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

    public static <T> List<T> mergeList(List<T> list1, List<T> list2){
            list1.addAll(list2);
            return list1;
    }


    public static void main(String[] args) throws IOException {

    }


}
