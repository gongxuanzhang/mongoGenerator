package common.util;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;

/**
 * @author: gxz
 * @email: 514190950@qq.com
 **/
public class XMLUtils {
    public static Document load(String filename) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(filename));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }

    public static boolean isEmpty(Attribute attribute){
        return attribute==null || StringUtils.isEmpty(attribute.getValue());
    }

    public static Document load(URL url) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }

    }
