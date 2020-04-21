package common.util;


import com.alibaba.fastjson.JSONObject;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gxz
 * @date 2020/4/21 23:29
 */
public class JSONUtils {
    /**
     * 深度访问json  但属性值必须是document
     * @param parameterName
     * @param jsonObject
     * @return
     */
    public static <T> T depthGet(String parameterName, JSONObject jsonObject, Class<T> tClass){
        String[] names = parameterName.split("\\.");
        Document ex = new Document(jsonObject);
        for (String name : names) {
            Object next = ex.get(name);
            if(next instanceof Map){
                ex = (Document) next;
            }else{
                return tClass.cast(next);
            }
        }
       return tClass.cast(ex);
    }

    public static void main(String[] args) {

        Document a = new Document();
        Document b = new Document();
        Document c = new Document();
        Document d = new Document();
        d.put("d", "d");
        c.put("c", d);
        b.put("b", c);
        a.put("a",b);
        JSONObject jsonObject = new JSONObject(a);
        System.out.println(jsonObject);
        Map jsonObject1 = depthGet("a.b.c", jsonObject,Map.class);
        String jsonObject2 = depthGet("a.b.c", jsonObject,String.class);
        System.out.println(jsonObject1);
        System.out.println(jsonObject2);
    }
}
