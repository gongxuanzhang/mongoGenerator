package common.factory;

import common.util.AssertUtils;
import common.util.StringUtils;
import model.mongo.GeneratorMongoCollection;
import model.mongo.GeneratorMongoConnection;
import model.mongo.NameStrategy;
import model.mongo.RepetitionStrategy;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class ElementParsingFactory {

    private static final String TEMPLATE_NAME_SEPARATOR = ",";

     public static GeneratorMongoCollection createMongoCollection(Element element){
         GeneratorMongoCollection result =  new GeneratorMongoCollection();
         String name = element.attributeValue("name");
         String primaryPackage = element.attributeValue("primaryPackage");

         AssertUtils.attrAssert(name,"XML collection name is empty");
         AssertUtils.attrAssert(primaryPackage,"XML primaryPackage is empty");

         String scannerCountFromXML = element.attributeValue("scannerCount");
         int scannerCount = StringUtils.isEmpty(scannerCountFromXML)?400000:Integer.valueOf(scannerCountFromXML);
         result.setName(name).setScannerCount(scannerCount);

         String repetitionStrategyFromXML = element.attributeValue("repetitionStrategy");
         String nameStrategyFromXML = element.attributeValue("nameStrategy");
         //重复策略 默认是JSON 命名策略 默认是AUTO
         NameStrategy nameStrategy
                 = StringUtils.isEmpty(nameStrategyFromXML)?
                 NameStrategy.AUTO:NameStrategy.valueOf(nameStrategyFromXML.toUpperCase());
         result.setNameStrategy(nameStrategy);
         RepetitionStrategy repetitionStrategy
                 = StringUtils.isEmpty(repetitionStrategyFromXML)?
                 RepetitionStrategy.JSON:RepetitionStrategy.valueOf(repetitionStrategyFromXML.toUpperCase());
         result.setRepetitionStrategy(repetitionStrategy);
         //复杂实体的生成路径 如果没填写 默认是主包路径
         result.setPrimaryPackage(primaryPackage);
         String innerPackage = element.attributeValue("innerPackage");
         result.setInnerPackage(StringUtils.isEmpty(innerPackage)?primaryPackage:innerPackage);
         String templateNameStr = element.attributeValue("template");
         if(StringUtils.isNotEmpty(templateNameStr)){
             List<String> templateNames = templateNames2list(templateNameStr.trim());
             result.setTemplateNames(templateNames);
         }
         return result;
     }

     public static GeneratorMongoConnection createMongoConnection(Element element){
         GeneratorMongoConnection result = new GeneratorMongoConnection();
         Attribute host = element.attribute("host");
         AssertUtils.attrAssert(host,"xml <mongo> host is null ");
         Attribute port = element.attribute("port");
         AssertUtils.attrAssert(port,"xml <mongo> port is null ");
         Attribute authAttr = element.attribute("auth");
         boolean auth =authAttr==null?true:Boolean.valueOf(authAttr.getValue());
         result.setHost(host.getValue()).setPort(Integer.valueOf(port.getValue()));
         result.setAuth(auth);
         if(result.isAuth()){
             Attribute username = element.attribute("username");
             AssertUtils.attrAssert(username,"xml <mongo> username is null ");
             Attribute password = element.attribute("password");
             AssertUtils.attrAssert(password,"xml <mongo> password is null ");
             Attribute source = element.attribute("source");
             AssertUtils.attrAssert(source,"xml <mongo> source is null ");
             result.setUsername(username.getValue()).setPassword(password.getValue());
             result.setSource(source.getValue());
         }
         return result;
     }


    private static List<String> templateNames2list(String templateNames){
        List<String> result = new ArrayList<>();
        if(StringUtils.isEmpty(templateNames)){
            return null;
        }
        if(templateNames.contains(TEMPLATE_NAME_SEPARATOR)){
            String[] names = templateNames.split(TEMPLATE_NAME_SEPARATOR);
            result.addAll(Arrays.asList(names));
        }
        return result;

    }
}
