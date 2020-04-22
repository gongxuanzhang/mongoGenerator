package pasring;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoCommandException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import common.log.model.Type;
import common.util.StringUtils;
import model.GeneratorFileInfo;
import model.GeneratorModel;
import org.bson.Document;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author: gxz
 * @email : 514190950@qq.com
 **/
public class MongoParsing {

    final private MongoCollection<Document> collection;

    final private int scanCount;

    private List<String> colNames;
    private List<GeneratorModel> generatorModel;

    private String mongoTemplateName;
    private ExecutorService generatorPool = new ThreadPoolExecutor(5,
            Runtime.getRuntime().availableProcessors() * 2,
            300L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024),
            Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy()
    );
    private final static int[] TYPE = {3, 16, 18, 8, 9, 2, 1};
    private final static int ARRAY_TYPE = 4;
    private List<GeneratorFileInfo> generatorFileInfo = new ArrayList<>();


    public MongoParsing(MongoCollection<Document> collection, int scanCount) {
        this.collection = collection;
        this.scanCount = scanCount > 400000 ? 400000 : scanCount;
    }

    public GeneratorModel process() {
        //初始化
        initColNames();
        //解析属性值
        GeneratorModel generatorModel = processParameter();
        return generatorModel;
    }

    /**
     * 功能描述:分组发送聚合函数(获得一级属性名)
     *
     * @author : gxz
     * @date : 2019/7/4 16:24
     */
    public List<String> groupAggregation(Integer skip, Integer limit) throws MongoCommandException {
        if (skip == null) skip = 0;
        if (limit == null) limit = 100000;
        MongoCollection<Document> collection = this.collection;
        BasicDBObject $project = new BasicDBObject("$project", new BasicDBObject("arrayofkeyvalue", new BasicDBObject("$objectToArray", "$$ROOT")));
        BasicDBObject $unwind = new BasicDBObject("$unwind", "$arrayofkeyvalue");
        BasicDBObject $skip = new BasicDBObject("$skip", skip);
        BasicDBObject $limit = new BasicDBObject("$limit", limit);
        BasicDBObject basicDBObject = new BasicDBObject("_id", "null");
        basicDBObject.append("allkeys", new BasicDBObject("$addToSet", "$arrayofkeyvalue.k"));
        BasicDBObject $group = new BasicDBObject("$group", basicDBObject);
        List<BasicDBObject> dbStages = Arrays.asList($project, $skip, $limit, $unwind, $group);
        //System.out.println(dbStages);  发送的聚合函数   获得所有参数名称
        AggregateIterable<Document> aggregate = collection.aggregate(dbStages);
        Document document = aggregate.first();
        if (document == null) {
            BasicDBObject existsQuery = new BasicDBObject("$ROOT", new BasicDBObject("$exists", true));
            MongoCursor<Document> existsList = collection.find(existsQuery).limit(100).iterator();
            Set<String> keySet = new HashSet<>();
            while (existsList.hasNext()) {
                Document next = existsList.next();
                Map<String, Object> keyMap = (Document) next.get("$ROOT");
                keySet.addAll(keyMap.keySet());
            }
            return new ArrayList<>(keySet);
        } else {
            return (List<String>) document.get("allkeys");
        }

    }

    public GeneratorModel processParameter() {
        GeneratorModel result = new GeneratorModel();
        result.setPropertyName(this.collection.getNamespace().getCollectionName());
        result.setType(3).setArray(false);
        List<String> colNames = this.colNames;
        List<GeneratorModel> children = new ArrayList<>();
        for (String colName : colNames) {
            GeneratorModel generatorModel = this.processNameType(colName);
            children.add(generatorModel);
        }
        return result.setChild(children);
    }

    /**
     * 如果一个文档是对象类型  获得这个属性的下一级的属性名的集合
     * 例子: user:{name:"张三",age:12}  传入user  返回[name,age]
     *
     * @param parameterName 上层参数名  这个参数名可以包含一个或多个.
     *                      注: 参数传递之前需确认:  1.上层属性一定是对象类型
     * @return 返回这个属性内的所有属性名
     */
    public Set<String> getNextParameterNames(String parameterName) {
        long l = System.currentTimeMillis();
        Document condition = new Document(parameterName, new Document("$exists", true));
        Document match = new Document("$match", condition);
        String unwindName = parameterName;
        if (parameterName.contains(".")) {
            unwindName = parameterName.split("\\.")[0];
        }
        Document unwind = new Document("$unwind", "$" + unwindName);
        Document limit = new Document("$limit", 5000);
        Document project = new Document("$project", new Document("list", "$" + parameterName).append("_id", false));
        Document unwind2 = new Document("$unwind", "$list");
        AggregateIterable<Document> aggregate = this.collection.aggregate(Arrays.asList(match, unwind, limit, project, unwind2));
        Set<String> names = new HashSet<>();
        for (Document document : aggregate) {
            Object list = document.get("list");
            if (list instanceof Map) {
                Set<String> documentNames = ((Document) list).keySet();
                names.addAll(documentNames);
            }
        }
        System.out.println("解析"+parameterName+(System.currentTimeMillis()-l)+"毫秒");
        return names;
    }


    /**
     * 功能描述:集合中是对象的属性值 专用
     *
     * @author: gxz
     * @date: 2019/7/4 16:24
     */
    private List<String> groupAggregationListObj(Integer skip, Integer limit, String proName) throws MongoCommandException {
        if (StringUtils.isEmpty(proName)) proName = "$ROOT";
        if (skip == null) skip = 0;
        if (limit == null) limit = 100000;
        MongoCollection<Document> collection = this.collection;
        String name = proName.split("\\.")[0];
        BasicDBObject $unwind = new BasicDBObject("$unwind", "$" + name);
        BasicDBObject $skip = new BasicDBObject("$skip", skip);
        BasicDBObject $limit = new BasicDBObject("$limit", limit);
        BasicDBObject $project = new BasicDBObject("$project", new BasicDBObject("arrayofkeyvalue", new BasicDBObject("$objectToArray", "$" + proName)));
        BasicDBObject $unwind1 = new BasicDBObject("$unwind", "$arrayofkeyvalue");
        BasicDBObject basicDBObject = new BasicDBObject("_id", "null");
        basicDBObject.append("allkeys", new BasicDBObject("$addToSet", "$arrayofkeyvalue.k"));
        BasicDBObject $group = new BasicDBObject("$group", basicDBObject);
        List<BasicDBObject> dbStages = Arrays.asList($unwind, $skip, $limit, $project, $unwind1, $group);
        //System.out.println(dbStages);  发送的聚合函数   获得所有参数名称
        AggregateIterable<Document> aggregate = collection.aggregate(dbStages);
        Document document = aggregate.first();
        return (List<String>) document.get("allkeys");
    }

    private List<String> listChlidProName(String listName) {
        MongoCollection<Document> collection = this.collection;
        BasicDBObject $project = new BasicDBObject("$project", new BasicDBObject("arrayofkeyvalue", "$" + listName));
        BasicDBObject $limit = new BasicDBObject("$limit", 100000);
        BasicDBObject $unwind = new BasicDBObject("$unwind", "$arrayofkeyvalue");
        BasicDBObject basicDBObject = new BasicDBObject("_id", "null");
        basicDBObject.append("allkeys", new BasicDBObject("$addToSet", "$arrayofkeyvalue"));
        BasicDBObject $group = new BasicDBObject("$group", basicDBObject);
        List<BasicDBObject> dbStages = Arrays.asList($project, $limit, $unwind, $group);
        System.out.println(dbStages);
        //System.out.println(dbStages);  发送的聚合函数   获得所有参数名称
        AggregateIterable<Document> aggregate = collection.aggregate(dbStages);
        Document first = aggregate.first();
        List<Document> listKeyValue = (List<Document>) first.get("allkeys");
        int index = 0;
        Document max = null;
        for (Document document : listKeyValue) {
            if (document.size() > index) {
                index = document.size();
                max = document;
            }
        }
        return new ArrayList<>(max.keySet());
    }

    public List<String> groupAggregationListObj(String proName) {
        return groupAggregationListObj(null, null, proName);
    }


    /**
     * 功能描述:提供属性名 解析属性类型
     * 获取相应的属性信息  封装成generator对象
     *
     * @author: gxz
     * @param: propertyName属性名 可以是层级名  比如 name 也可以是info.name
     * @return: 解析之后的Model {@see #GeneratorModel}
     * @see GeneratorModel
     */

    public GeneratorModel processNameType(String propertyName) {
        // System.out.println(Thread.currentThread().getName() + "启动    执行[" + propertyName + "]的操作");
        long l = System.currentTimeMillis();
        MongoCollection<Document> collection = this.collection;
        GeneratorModel result = new GeneratorModel();
        if ("_id".equals(propertyName)) {
            result.setType(2);
            result.setPropertyName("_id");
            return result;
        }
        result.setPropertyName(propertyName);
        MongoCursor<Document> isArray = collection.find(new Document(propertyName, new Document("$type", ARRAY_TYPE))).limit(1).iterator();
        if (isArray.hasNext()) {
            System.out.println("解析到" + propertyName + "是数组");
            result.setArray(true);
            for (int i : TYPE) {
                MongoCursor<Document> iterator = collection.find(new Document(propertyName, new Document("$type", i))).limit(1).iterator();
                if (iterator.hasNext()) {
                    if (i == 3) {
                        result.setChild(this.produceChildList(propertyName));
                    }
                    //1是double 2是string 3是对象 4是数组 16是int 18 是long
                    result.setType(i);
                    System.out.println("解析类型"+propertyName+"["+Type.typeInfo(result.getType())+"]"+(System.currentTimeMillis()-l)+"毫秒");
                    return result;
                }
            }
        } else {
            for (int i : TYPE) {
                MongoCursor<Document> iterator = collection.find(new Document(propertyName, new Document("$type", i))).limit(1).iterator();
                if (iterator.hasNext()) {
                    if (i == 3) {
                        System.out.println("解析到" + propertyName + "是对象");
                        result.setChild(this.produceChildList(propertyName));
                    }
                    //1是double 2是string 3是对象 4是数组 16是int 18 是long
                    //到这里就是数组了
                    result.setType(i);
                    System.out.println("解析类型"+propertyName+"["+Type.typeInfo(result.getType())+"]"+(System.currentTimeMillis()-l)+"毫秒");
                    return result;
                }
            }
            result.setType(2);
        }
        System.out.println("解析类型"+propertyName+"["+Type.typeInfo(result.getType())+"]"+(System.currentTimeMillis()-l)+"毫秒");
        return result;
    }


    private List<GeneratorModel> produceChildList(String parentName) {
        Set<String> nextParameterNames = this.getNextParameterNames(parentName);
        List<GeneratorModel> childrenList = new ArrayList<>();
        for (String nextParameterName : nextParameterNames) {
            GeneratorModel generatorModel = this.processNameType(parentName+"."+nextParameterName);
            childrenList.add(generatorModel);
        }
        return childrenList;
    }

    private List<String> distinctAndJoin(List<String> a, List<String> b) {
        a.removeAll(b);
        a.addAll(b);
        return a;
    }

    private static String nameToJavaName(String name) {
        String first = name.substring(0, 1).toUpperCase();
        String end = name.substring(1);
        String result = first + end;
        while (result.contains("_")) {
            int i = result.indexOf("_");
            String before = result.substring(0, i);
            String after = result.substring(i + 1);
            String upperCase = after.substring(0, 1).toUpperCase();
            after = upperCase + after.substring(1);
            result = before + after;
        }
        return result;
    }

    /**
     * 功能描述:解析这个集合的列名  用ForkJoin框架实现
     *
     * @author: gxz
     * @date: 2019/7/5 14:48
     */
    public void initColNames() {
        long start = System.currentTimeMillis();
        int scan = this.scanCount;
        if (scan < 400000) scan = 400000;
        long count = this.collection.countDocuments();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<List<String>> task;
        if (count > (long) scan) {
            task = new ForkJoinGetProcessName(0, scan);
        } else {
            task = new ForkJoinGetProcessName(0, (int) count);
        }
        this.colNames = pool.invoke(task);
        System.out.println("collection[" + this.collection.getNamespace().getCollectionName() +
                "]初始化列名成功.....     用时: " + (System.currentTimeMillis() - start) + "毫秒");
    }


    /**
     * 功能描述:forkJoin多线程框架的实现  通过业务拆分获得属性名
     *
     * @author: gxz
     */
    class ForkJoinGetProcessName extends RecursiveTask<List<String>> {
        private int begin; //查询开始位置
        private int end;
        private final int THRESHOLD = 130000;

        ForkJoinGetProcessName(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        protected List<String> compute() {
            int count = end - begin;
            if (THRESHOLD >= count) {
                return groupAggregation(begin, count);
            } else {
                int middle = (begin + end) / 2;
                ForkJoinGetProcessName pre = new ForkJoinGetProcessName(begin, middle);
                pre.fork();
                ForkJoinGetProcessName next = new ForkJoinGetProcessName(middle + 1, end);
                next.fork();
                return distinctAndJoin(pre.join(), next.join()); //去重合并
            }
        }
    }
}
